alter table user alter column id bigint auto_increment;
alter table question alter column id bigint auto_increment;
alter table comment alter column commentor bigint not null;
alter table question alter column creator bigint not null;


