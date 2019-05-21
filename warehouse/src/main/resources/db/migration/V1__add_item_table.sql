create table item (
  id bigserial primary key,
  name text not null,
  count int not null,
  price numeric(18, 4) not null
);
