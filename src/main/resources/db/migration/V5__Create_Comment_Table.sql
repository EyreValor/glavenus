create table comment
(
	id BIGINT auto_increment,
	parent_id BIGINT not null,
	type int,
	commentator int,
	gmt_create BIGINT not null,
	gmt_modefied BIGINT not null,
	like_count BIGINT default 0,
	constraint comment_pk
		primary key (id)
);

