
use reservation

CREATE TABLE ROLE 
(
ID INT NOT NULL AUTO_INCREMENT,
NAME VARCHAR(20),
PRIMARY KEY (ID)
);

create table USER_ROLE(
USER_ID int,
ROLE_ID int,
FOREIGN KEY (USER_ID)
REFERENCES USER(ID),
FOREIGN KEY (ROLE_ID)
REFERENCES ROLE(ID)
);

insert into ROLE values(1,'ADMIN');

insert into USER_ROLE values(1,1);

select * from user

select * from role

select * from user_role

drop table role

drop table user_role



