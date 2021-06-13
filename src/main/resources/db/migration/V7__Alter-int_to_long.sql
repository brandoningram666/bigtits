alter table user modify column id bigint auto_increment;
alter table question modify column id bigint auto_increment;
alter table comment modify column commentor bigint not null;
alter table question modify column creator bigint not null;


