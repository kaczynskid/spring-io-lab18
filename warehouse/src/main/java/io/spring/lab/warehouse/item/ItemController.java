package io.spring.lab.warehouse.item;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

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
        return ResponseEntity.status(CREATED).build();
    }
}
