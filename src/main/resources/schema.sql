drop table reserva;  
drop table actividad;  
drop table socio; 
drop table tipoactividad;
drop table instalacion;
drop table recurso;

create table recurso(rc_nombre VARCHAR(20) primary key not null, rc_cantidad int, CHECK (rc_cantidad > 0));
create table instalacion(i_nombre VARCHAR(20) primary key not null, rc_nombre VARCHAR(20), FOREIGN KEY (rc_nombre) REFERENCES RECURSO (rc_nombre));
create table tipoactividad(ta_nombre VARCHAR(20) primary key not null, ta_intensidad VARCHAR(5) not null, i_nombre VARCHAR(20), FOREIGN KEY (i_nombre) REFERENCES INSTALACION (i_nombre), CHECK (ta_intensidad IN ('alta', 'media', 'baja')));
create table socio(s_id int primary key not null, s_nombre VARCHAR(20) not null, CHECK (s_id > 0));
create table actividad(a_id int primary key not null, ta_nombre VARCHAR(20) not null, a_dia DATE not null, a_ini int not null, a_fin int not null, a_plazas int not null, FOREIGN KEY (ta_nombre) REFERENCES TIPOACTIVIDAD (ta_nombre), CHECK (a_id > 0), CHECK (8 <= a_ini <= 22), CHECK (9 <= a_fin <= 22), CHECK (a_plazas >= 0));
create table reserva(s_id int not null, a_id key not null, PRIMARY KEY (s_id, a_id), FOREIGN KEY (s_id) REFERENCES SOCIO (s_id), FOREIGN KEY (a_id) REFERENCES ACTIVIDADLIMITADA (a_id), CHECK (s_id > 0), CHECK (a_id > 0));