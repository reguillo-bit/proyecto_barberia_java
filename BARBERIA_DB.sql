drop database if exists BARBERIA;

CREATE DATABASE BARBERIA;
USE BARBERIA;

CREATE TABLE USERS(
ID_USER INT auto_increment PRIMARY KEY unique,
N_USER VARCHAR(50),
PASS VARCHAR(50),
ID_ROLE int );

insert into USERS(N_USER,PASS,ID_ROLE) values('CREGUILLO','1234',1);
insert into USERS(N_USER,PASS,ID_ROLE) values('MCAICEDO','1234',2);

CREATE TABLE LOGIN_LOG(
    ID_LOG INT AUTO_INCREMENT PRIMARY KEY unique,
    ID_USER INT NULL,
    N_USER VARCHAR(50),
    FECHA_HORA DATETIME DEFAULT CURRENT_TIMESTAMP,
    EVENTO ENUM('LOGIN','FALLIDO','LOGOUT'),
    FOREIGN KEY (ID_USER) REFERENCES USERS(ID_USER)
);


CREATE TABLE CLIENTES(
ID_CLIENTE INT auto_increment primary key,
NOMBRE VARCHAR(50),
EDAD INT
);
insert into CLIENTES(ID_CLIENTE,NOMBRE,EDAD) values(1,'DON PICHON',67);
insert into CLIENTES(ID_CLIENTE,NOMBRE,EDAD) values(2,'NARUTO',20);

CREATE TABLE BARBERO(
ID_BARBERO INT auto_increment primary key unique,
NOMBRE VARCHAR(50),
EDAD int
);

insert into BARBERO(NOMBRE,EDAD) VALUES('DON RAMON',67);
insert into BARBERO(NOMBRE,EDAD) VALUES('DON  BARRIGA',65);
insert into BARBERO(NOMBRE,EDAD) VALUES('GODINES ',40);

CREATE TABLE SERVICIO(
ID_SERVICIO INT auto_increment primary key,
TURNO INT ,
PRECIO INT,
FECHA DATE ,
ID_USER INT,
ID_CLIENTE INT,
ID_BARBERO INT,
TIPO_SERVICIO VARCHAR(50),
FOREIGN KEY(ID_USER) REFERENCES USERS(ID_USER),
FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTES (ID_CLIENTE),
FOREIGN KEY(ID_BARBERO) REFERENCES BARBERO (ID_BARBERO)
);
insert into SERVICIO(TURNO,PRECIO,FECHA,ID_USER,ID_CLIENTE,ID_BARBERO,TIPO_SERVICIO) VALUES(1,20000, curdate(),1,1,1,'Corte clasico');
insert into SERVICIO(TURNO,PRECIO,FECHA,ID_USER,ID_CLIENTE,ID_BARBERO,TIPO_SERVICIO) VALUES(2,20000,'2026-09-09',1,2,1,'Corte clasico');

CREATE TABLE PAGOS(
ID_PAGO INT auto_increment PRIMARY KEY,
PAGO INT,
ID_BARBERO INT,
ID_SERVICIO INT,
FOREIGN KEY(ID_BARBERO) REFERENCES BARBERO(ID_BARBERO),
FOREIGN KEY(ID_SERVICIO) REFERENCES SERVICIO(ID_SERVICIO) );

insert into PAGOS(ID_PAGO,PAGO,ID_BARBERO,ID_SERVICIO) VALUES(1,8000,1,1);
insert into PAGOS(ID_PAGO,PAGO,ID_BARBERO,ID_SERVICIO) VALUES(2,8000,1,2);

SET GLOBAL log_bin_trust_function_creators = 1;

delimiter //
create procedure nombrestabla()
begin
select c.ID_CLIENTE AS ID,c.NOMBRE AS NOMBRE,s.TIPO_SERVICIO AS SERVICIO,s.TURNO AS TURNO,b.NOMBRE AS BARBERO ,s.FECHA AS FECHAS,s.PRECIO as PRECIO from CLIENTES c 
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


create procedure agregarservicio(turno int,precio int, fecha date,idc int,idb int,tiposervicio varchar(50))
begin
insert into SERVICIO(TURNO,PRECIO,FECHA,ID_CLIENTE,ID_BARBERO,TIPO_SERVICIO) VALUES (turno,precio,fecha,idc,idb,tiposervicio);
end//

create procedure maxid()
begin
select ifnull(max(c.ID_CLIENTE),0) AS ID from CLIENTES c;
end//

create procedure maxidservices()
begin
select ifnull(max(s.ID_SERVICIO),0) AS ID from SERVICIO s;
end//

create view vertabla as
select c.ID_CLIENTE AS ID,c.NOMBRE AS NOMBRE,s.TIPO_SERVICIO AS SERVICIO,s.TURNO AS TURNO,b.NOMBRE AS BARBERO ,s.FECHA AS FECHAS ,s.PRECIO as PRECIO from CLIENTES c 
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


create view verclientes as
select c.ID_CLIENTE AS ID, c.NOMBRE AS NOMBRE,c.EDAD AS EDAD from CLIENTES c;
//
Create procedure acualizarbarbero(id int,nombre varchar(50), edad int)
begin
update BARBERO set NOMBRE = nombre, EDAD = edad where  BARBERO.ID_BARBERO = id;
end//

create procedure paga(pago double,id_barbero int,id_servicio int)
begin
insert into PAGOS(PAGO,I_BARBERO,ID_SERVICIO) values(pago,id_barbero,id_servicio);
end//

create procedure clientes_por_fecha (FECHA DATE)
select c.ID_CLIENTE AS ID,c.NOMBRE AS NOMBRE,s.TIPO_SERVICIO AS SERVICIO,s.TURNO AS TURNO,b.NOMBRE AS BARBERO ,s.FECHA AS FECHAS ,s.PRECIO as PRECIO from CLIENTES c 
inner join SERVICIO s on c.ID_CLIENTE=s.ID_CLIENTE
inner join BARBERO b on s.ID_BARBERO=b.ID_BARBERO WHERE s.FECHA = FECHA;
//

create view fechas as 
select DISTINCT s.FECHA from SERVICIO s;
//


create procedure pago_barbero (id int) 
begin
select b.NOMBRE AS NOMBRE,b.EDAD AS EDAD, p.PAGO from pagos p inner join BARBERO b
on b.ID_BARBERO = p.ID_BARBERO where id = b.ID_BARBERO;
end
//


create trigger pagoss
after insert on SERVICIO
for each row
begin
INSERT INTO PAGOS(PAGO, ID_BARBERO, ID_SERVICIO)
VALUES (ROUND(NEW.PRECIO * 0.40), NEW.ID_BARBERO, NEW.ID_SERVICIO);
END//

-- ================== PROCEDIMIENTOS QUE FALTABAN ==================
create procedure acualizarcliente(id int,nombre varchar(50), edad int)
begin
update CLIENTES set NOMBRE = nombre, EDAD = edad where CLIENTES.ID_CLIENTE = id;
end//

-- Eliminar un cliente tambien elimina sus servicios y los pagos de esos servicios
create procedure eliminarcliente(id int)
begin
declare exit handler for sqlexception
begin
rollback;
resignal;
end;
start transaction;
delete from PAGOS where ID_SERVICIO in (select s.ID_SERVICIO from SERVICIO s where s.ID_CLIENTE = id);
delete from SERVICIO where SERVICIO.ID_CLIENTE = id;
delete from CLIENTES where CLIENTES.ID_CLIENTE = id;
commit;
end//

-- Un barbero con servicios registrados no se elimina (conserva el historial)
create procedure eliminarbarbero(id int)
begin
if exists(select 1 from SERVICIO s where s.ID_BARBERO = id) then
signal sqlstate '45000' set message_text = 'No se puede eliminar: el barbero tiene servicios registrados';
end if;
delete from BARBERO where BARBERO.ID_BARBERO = id;
end//

-- Detalle de pagos de un barbero (fecha, cliente, servicio, valor y pago del 40 %)
create procedure pagos_por_barbero(id int)
begin
select s.FECHA AS FECHA, c.NOMBRE AS CLIENTE, s.TIPO_SERVICIO AS SERVICIO, s.PRECIO AS VALOR, p.PAGO AS PAGO
from PAGOS p
inner join SERVICIO s on s.ID_SERVICIO = p.ID_SERVICIO
inner join CLIENTES c on c.ID_CLIENTE = s.ID_CLIENTE
where p.ID_BARBERO = id
order by s.FECHA desc, s.TURNO desc;
end//

-- ================== INFORMES ==================
-- Ingresos por periodo: servicios entre dos fechas (el total se suma en la ventana)
create procedure ingresos_por_periodo(desde date, hasta date)
begin
select c.ID_CLIENTE AS ID,c.NOMBRE AS NOMBRE,s.TIPO_SERVICIO AS SERVICIO,s.TURNO AS TURNO,b.NOMBRE AS BARBERO ,s.FECHA AS FECHAS ,s.PRECIO as PRECIO from CLIENTES c
inner join SERVICIO s on c.ID_CLIENTE=s.ID_CLIENTE
inner join BARBERO b on s.ID_BARBERO=b.ID_BARBERO
where s.FECHA between desde and hasta
order by s.FECHA, s.TURNO;
end//

-- Servicios por barbero: cantidad y valor generado (incluye barberos sin servicios)
create procedure servicios_por_barbero()
begin
select b.NOMBRE AS BARBERO, count(s.ID_SERVICIO) AS SERVICIOS, ifnull(sum(s.PRECIO),0) AS VALOR
from BARBERO b
left join SERVICIO s on s.ID_BARBERO = b.ID_BARBERO
group by b.ID_BARBERO, b.NOMBRE
order by SERVICIOS desc, b.NOMBRE;
end//

-- Clientes frecuentes en un periodo (cada servicio crea un registro de cliente,
-- por eso se agrupa por nombre y edad)
create procedure clientes_frecuentes(desde date, hasta date)
begin
select min(c.NOMBRE) AS NOMBRE, c.EDAD AS EDAD, count(s.ID_SERVICIO) AS VISITAS, sum(s.PRECIO) AS TOTAL, max(s.FECHA) AS ULTIMA_VISITA
from CLIENTES c
inner join SERVICIO s on s.ID_CLIENTE = c.ID_CLIENTE
where s.FECHA between desde and hasta
group by upper(trim(c.NOMBRE)), c.EDAD
order by VISITAS desc, TOTAL desc;
end//


delimiter ;



-- ================== DATOS DE EJEMPLO PARA PROBAR LOS INFORMES ==================
insert into CLIENTES(NOMBRE,EDAD) values('DON PICHON',67),('NARUTO',20),('DON PICHON',67),('QUICO',28),('NARUTO',20),('DON PICHON',67);
insert into SERVICIO(TURNO,PRECIO,FECHA,ID_USER,ID_CLIENTE,ID_BARBERO,TIPO_SERVICIO) values
(1,28000,curdate() - interval 6 day,1,3,2,'Corte y barba'),
(1,12000,curdate() - interval 4 day,1,4,3,'Barba'),
(2,20000,curdate() - interval 4 day,1,5,2,'Corte clasico'),
(1,35000,curdate() - interval 2 day,1,6,1,'Tinte'),
(2,20000,curdate() - interval 2 day,1,7,2,'Corte clasico'),
(2,20000,curdate(),1,8,3,'Corte clasico');

SHOW CREATE TRIGGER pagoss;


