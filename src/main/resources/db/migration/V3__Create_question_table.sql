create table question
(
	id int auto_increment,
	gmt_create bigint,
	gmt_modified bigint,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	creator int,
	title varchar(50),
	description text,
	tag varchar(256),
	constraint question_pk
		primary key (id)
);

