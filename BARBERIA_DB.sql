drop database if exists BARBERIA;

CREATE DATABASE BARBERIA;
USE BARBERIA;

CREATE TABLE USERS(
ID_USER INT auto_increment PRIMARY KEY,
N_USER VARCHAR(50),
PASS VARCHAR(50),
ID_ROLE int );

insert into USERS(N_USER,PASS,ID_ROLE) values('CREGUILLO','1234',1);
insert into USERS(N_USER,PASS,ID_ROLE) values('MCAICEDO','1234',2);


CREATE TABLE CLIENTES(
ID_CLIENTE INT auto_increment primary key,
NOMBRE VARCHAR(50),
EDAD INT
);

CREATE TABLE BARBERO(
ID_BARBERO INT auto_increment primary key,
NOMBRE VARCHAR(50),
EDAD int
);

CREATE TABLE SERVICIO(
ID_SERVICIO INT auto_increment primary key,
TURNO INT ,
PRECIO INT,
FECHA DATE,
ID_USER INT,
ID_CLIENTE INT,
ID_BARBERO INT,
FOREIGN KEY(ID_USER) REFERENCES USERS(ID_USER),
FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTES (ID_CLIENTE),
FOREIGN KEY(ID_BARBERO) REFERENCES BARBERO (ID_BARBERO)
);

insert into BARBERO(NOMBRE,EDAD) VALUES('DON RAMON',67);
insert into BARBERO(NOMBRE,EDAD) VALUES('DON  BARRIGA',65);
insert into BARBERO(NOMBRE,EDAD) VALUES('GODINES ',40);

SET GLOBAL log_bin_trust_function_creators = 1;

delimiter //
create procedure nombrestabla()
begin
select c.ID_CLIENTE AS ID,c.NOMBRE AS NOMBRE,s.TURNO AS TURNO,b.NOMBRE AS BARBERO ,s.FECHA AS FECHAS,s.PRECIO as PRECIO from CLIENTES c 
inner join SERVICIO s on c.ID_CLIENTE=s.ID_CLIENTE
inner join BARBERO b on s.ID_BARBERO=b.ID_BARBERO
WHERE 1= 0;
end//

create procedure agregarclientes(nombre varchar(50),edad int)
begin
insert into CLIENTES(NOMBRE,EDAD) VALUES (nombre,edad);
end//
create procedure agregarbarbero(nombre varchar(50),edad int)
begin
insert into BARBERO(NOMBRE,EDAD) VALUES (nombre,edad);
end//

create procedure sumacantidad()
begin 
select sum(SERVICIO.PRECIO) as total from SERVICIO;
end//


create procedure agregarservicio(turno int,precio int, fecha date,idc int,idb int)
begin
insert into SERVICIO(TURNO,PRECIO,FECHA,ID_CLIENTE,ID_BARBERO) VALUES (turno,precio,fecha,idc,idb);
end//

create procedure maxid()
begin
select count(c.ID_CLIENTE) from CLIENTES c;
end//

create view vertabla as
select c.ID_CLIENTE AS ID,c.NOMBRE AS NOMBRE,s.TURNO AS TURNO,b.NOMBRE AS BARBERO ,s.FECHA AS FECHAS ,s.PRECIO as PRECIO from CLIENTES c 
inner join SERVICIO s on c.ID_CLIENTE=s.ID_CLIENTE
inner join BARBERO b on s.ID_BARBERO=b.ID_BARBERO;
//

create procedure cuentas()
begin
select u.N_USER as USUARIO,u.PASS as CONTRASEÑA,u.ID_ROLE as ROLL from USERS u;
end//


create view verbarbero as
select b.ID_BARBERO AS ID,b.NOMBRE AS NOMBRE,b.EDAD AS EDAD from BARBERO b;
//

delimiter ;

call nombrestabla();

select*FROM vertabla;

SELECT * FROM CLIENTES;

SELECT * FROM verbarbero;

call sumacantidad();

call maxid();

CALL cuentas();