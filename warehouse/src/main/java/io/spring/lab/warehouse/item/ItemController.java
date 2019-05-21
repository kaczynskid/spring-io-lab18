package io.spring.lab.warehouse.item;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.lab.warehouse.error.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemService items;

    @GetMapping
    List<ItemRepresentation> findAll() {
        List<Item> list = items.findAll();
        log.info("Found {} items.", list.size());
        return list.stream()
                .map(ItemRepresentation::of)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public ItemRepresentation findOne(@PathVariable("id") long id) {
        Item item = items.findOne(id);
        log.info("Found item {}.", item.getName());
        return ItemRepresentation.of(item);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemRepresentation request) {
        Item item = items.create(request.asItem());
        log.info("Created item {}.", item.getName());
        return ResponseEntity.created(selfUriOf(item)).build();
    }

    private static URI selfUriOf(Item item) {
        return linkTo(methodOn(ItemController.class).findOne(item.getId())).toUri();
    }

    @PutMapping("/{id}/stock")
    public ItemRepresentation updateStock(@PathVariable("id") long id, @RequestBody ItemStockUpdate changes) {
        log.info("Update item stock {}.", changes);
        return ItemRepresentation.of(items.updateStock(changes.withId(id)));
    }

    @ExceptionHandler(ItemNotFound.class)
    public ResponseEntity<ErrorMessage> handleItemNotFound(ItemNotFound e) {
        return ErrorMessage.messageResponseOf(NOT_FOUND, e);
    }

    @ExceptionHandler(OutOfStock.class)
    public ResponseEntity<ErrorMessage> handleOutOfStock(OutOfStock e) {
        return ErrorMessage.messageResponseOf(BAD_REQUEST, e);
    }
}
