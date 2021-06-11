alter table USER alter column ID bigint auto_increment;
alter table QUESTION alter column ID bigint auto_increment;
alter table COMMENT alter column COMMENTOR bigint not null;
alter table QUESTION alter column CREATOR bigint not null;


