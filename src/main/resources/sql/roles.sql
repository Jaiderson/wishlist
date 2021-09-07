insert into rols(rolid, rolname, description) values (1, 'ADMIN','Rol principal con todos los permisos.');
insert into rols(rolid, rolname, description) values (2, 'USER','Rol basico para usuarios el cual permite gestionar su lista de libros.');

create sequence sec_listadeseos 
  start with 1 
  increment by 1 
  maxvalue 9999999 
  minvalue 1;
