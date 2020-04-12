create table notification
(
	id BIGINT auto_increment,
	nitifier BIGINT not null,
	receiver BIGINT not null,
	outer_id BIGINT not null,
	type int not null,
	gmt_create BIGINT not null,
	status INT default 0 not null,
	constraint notification_pk
		primary key (id)
);
