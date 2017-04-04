drop table T_CARDS if exists;

create table T_CARDS (ID bigint identity primary key, NAME varchar(30) not null,
                        RANK varchar(10) not null, SUIT varchar(10) not null, DECKNAME varchar(50) not null, SEQUENCE int not null);