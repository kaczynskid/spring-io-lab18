create table basket (
  id bigint auto_increment primary key,
  status varchar(16) not null,
  total_count int not null,
  total_price numeric(18, 4) not null
);

create table basket_item (
  id bigint auto_increment primary key,
  basket_id bigint not null,
  item_id bigint not null,
  name text not null,
  unit_price numeric(18, 4) not null,
  unit_count int not null,
  total_price numeric(18, 4) not null,
  special_id varchar(32),
  constraint basket_item_fk foreign key (basket_id) references basket (id),
  constraint basket_item_uq unique index (basket_id, item_id)
);
