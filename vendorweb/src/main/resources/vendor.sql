use projectdb;

create table vendor(
id int PRIMARY KEY,
code varchar(20),
name varchar(20),
type varchar(20),
email varchar(50),
phone varchar(20),
address varchar(100)
);

select * from vendor;

drop table vendor;
