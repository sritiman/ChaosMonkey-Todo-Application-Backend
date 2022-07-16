create table todo(
    id int primary key auto_increment,
    title varchar(200),
    body varchar(400),
    completed int default 0
);