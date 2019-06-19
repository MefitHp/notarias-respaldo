use notaria;
delete from tbcfgm89;
insert into tbcfgm89 values(1,"1","c81e728d9d4c2f636f067f89cc14862c",current_timestamp,current_timestamp,current_timestamp,"",false,"",current_timestamp);
delete from tbbsnm71;
insert into tbbsnm71 set libroactual = 1215, foliosdisponibles = 23490, folioactual=11287;

-- INSERT LIBRO
delete from tbbsnm26;
insert into tbbsnm26 values (md5('chacha'),'actualizacion de libro',current_date,7001,12000,'sesion',1100,current_timestamp);
-- CATALOGOS
select 'catalogos';
delete from tbcfgm17;
delete from tbcfgm16;
delete from tbcfgm11;
delete from tbcfgm32;
delete from tbcfgm08;
delete from tbbsnm28;
delete from tbbsnm16;
delete from tbbsnm12;
delete from tbbsnm16;
delete from tbcfgm18;
delete from tbcfgm03;
delete from tbbsnm62;
delete from tbbsnm63;
delete from tbcfgm30;
delete from tbcfgm91;
delete from tbcfgm90;
delete from tbbsnm08;
insert into tbcfgm90 values(md5(1),"nacionalidad",current_timestamp,"sesion");
insert into tbcfgm90 values(md5(2),"estado_civil",current_timestamp,"sesion");
insert into tbcfgm90 values(md5(3),"ocupacion",current_timestamp,"sesion");
insert into tbcfgm90 values(md5(4),"tipo_compareciente",current_timestamp,"sesion");
insert into tbcfgm90 values(md5(5),"operacion",current_timestamp,"sesion");
insert into tbcfgm90 values(md5(6),"suboperacion",current_timestamp,"sesion");
insert into tbcfgm90 values(md5(7),"tipo_persona",current_timestamp,"sesion");
insert into tbcfgm90 values(md5(8),"procesos",current_timestamp,"sesion");
insert into tbcfgm90 values(md5(9),'tipo_dato',current_timestamp, 'sesion');
insert into tbcfgm90 values(md5(10),'tipo_documento',current_timestamp, 'sesion');
insert into tbcfgm90 values(md5(11),'vocacion',current_timestamp, 'sesion');
insert into tbcfgm90 values(md5(12),'operador',current_timestamp, 'sesion');
insert into tbcfgm90 values(md5(13),'locacion',current_timestamp, 'sesion');
INSERT INTO tbcfgm90 VALUES(MD5(14),'tipo_componente_formulario',CURRENT_TIMESTAMP, 'sesion');
-- insert into tbcfgm90 values(md5(15),'tipo_componente_sub_formulario',current_timestamp, 'sesion');
insert into tbcfgm90 values(md5(16),'status_anticipo',current_timestamp, 'sesion');
insert into tbcfgm90 values(md5(17),'medio_pago',current_timestamp, 'sesion');
insert into tbcfgm90 values(md5(18),'concepto_pago',current_timestamp, 'sesion');
insert into tbcfgm90 values(md5(19),'docs_oficiales',current_timestamp,'sesion');
insert into tbcfgm90 values(md5(20),'instituciones_gobierno',current_timestamp,'sesion');
insert into tbcfgm90 values(md5(21),'tratamiento',current_timestamp,'sesion');
insert into tbcfgm90 values(md5(22),'regimen',current_timestamp,'sesion');
-- NO USAR ID 23 PARA TBCFGM90 PORQUE SE USA EN BRANCH COMPARECIENTES
insert into tbcfgm90 values(md5("regimenfiscal"),'regimen_fiscal',current_timestamp,'sesion');
INSERT INTO tbcfgm90 VALUES(MD5(97),'Entidad',current_timestamp,'sesion');
INSERT INTO tbcfgm90 VALUES(MD5(101),'Variables Filtro',current_timestamp,'sesion');
INSERT INTO tbcfgm90 VALUES(md5("tipoadquisicion"),'tipo_adquisicion',current_timestamp,'sesion');

select 'Elementos de catalogo';

INSERT INTO tbcfgm91 VALUES (MD5(101), (SELECT idcatalogo FROM tbcfgm90 WHERE idcatalogo=MD5(97)), 'Acto','---',false, 'sesion',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES (MD5(103), (SELECT idcatalogo FROM tbcfgm90 WHERE idcatalogo=MD5(97)), 'Escritura','---',false, 'sesion',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES (MD5(107), (SELECT idcatalogo FROM tbcfgm90 WHERE idcatalogo=MD5(101)), 'Acto','---',false, 'sesion',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES (MD5(109), (SELECT idcatalogo FROM tbcfgm90 WHERE idcatalogo=MD5(101)), 'Objeto Acto','---',false, 'sesion',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES (MD5(111), (SELECT idcatalogo FROM tbcfgm90 WHERE idcatalogo=MD5(101)), 'Escritura','---',false, 'sesion',CURRENT_TIMESTAMP);



insert into tbcfgm91 values(md5(2), (select idcatalogo from tbcfgm90 where dsnombre="tipo_documento"), 'Posterior','codigo',false, 'sesion',current_timestamp);
insert into tbcfgm91 values(md5(3), (select idcatalogo from tbcfgm90 where dsnombre="tipo_documento"), 'Previo','codigo',false, 'sesion',current_timestamp);
insert into tbcfgm91 values(md5(4), (select idcatalogo from tbcfgm90 where dsnombre="tipo_dato"), 'variable','var',false, 'sesion',current_timestamp);
insert into tbcfgm91 values(md5(5), (select idcatalogo from tbcfgm90 where dsnombre="tipo_dato"), 'compareciente','cte',false, 'sesion',current_timestamp);
--insert into tbcfgm91 values(md5(6), (select idcatalogo from tbcfgm90 where dsnombre="tipo_dato"), 'Fecha','codigo',false, 'sesion', current_timestamp);
--insert into tbcfgm91 values(md5(7), (select idcatalogo from tbcfgm90 where dsnombre="tipo_dato"), 'Moneda','codigo',false, 'sesion', current_timestamp);

insert into tbcfgm91 values(md5(8), (select idcatalogo from tbcfgm90 where dsnombre="nacionalidad"),"Mexicana",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(9), (select idcatalogo from tbcfgm90 where dsnombre="nacionalidad"),"Extranjera",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(10),(select idcatalogo from tbcfgm90 where dsnombre="estado_civil"),"Soltero",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(11),(select idcatalogo from tbcfgm90 where dsnombre="estado_civil"),"Casado",'codigo',false,"sesion",current_timestamp);
-- insert into tbcfgm91 values(md5(12),(select idcatalogo from tbcfgm90 where dsnombre="estado_civil"),"Divorciado",'codigo',false,"sesion",current_timestamp);
-- insert into tbcfgm91 values(md5(13),(select idcatalogo from tbcfgm90 where dsnombre="estado_civil"),"Viudo",'codigo',false,"sesion",current_timestamp);
-- insert into tbcfgm91 values(md5(1301),(select idcatalogo from tbcfgm90 where dsnombre="estado_civil"),"Casado Bienes Separados",'codigo',false,"sesion",current_timestamp);
-- insert into tbcfgm91 values(md5(1302),(select idcatalogo from tbcfgm90 where dsnombre="estado_civil"),"Bienes Mancomunados",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(14),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Estudiante",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(15),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Profesional",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(16),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Jubilado",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(17),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Ama de casa",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(18),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Servidor Público",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(19),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Empresario",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(20),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Empleado",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(2001),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Empleador",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(2002),(select idcatalogo from tbcfgm90 where dsnombre="ocupacion"),"Desempleado",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(21),(select idcatalogo from tbcfgm90 where dsnombre="tipo_persona"),"Física",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(22),(select idcatalogo from tbcfgm90 where dsnombre="tipo_persona"),"Moral",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(23),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Atención de clientes",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(24),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Previos",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(25),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Elaboracion",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(26),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Atencion firmas",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(27),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Posteriores",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(28),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Testimonios",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(29),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Registro",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(30),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Entrega",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(31),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Cotejo",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(3001),(select idcatalogo from tbcfgm90 where dsnombre="procesos"),"Gestion de libros",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(32),(select idcatalogo from tbcfgm90 where dsnombre="vocacion"),"Terreno",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(33),(select idcatalogo from tbcfgm90 where dsnombre="vocacion"),"Habitacional",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(34),(select idcatalogo from tbcfgm90 where dsnombre="vocacion"),"Comercial",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(35),(select idcatalogo from tbcfgm90 where dsnombre="locacion"),"Distrito Federal",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(36),(select idcatalogo from tbcfgm90 where dsnombre="locacion"),"Estado de Mexico",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(37),(select idcatalogo from tbcfgm90 where dsnombre="locacion"),"Foráneo",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(38),(select idcatalogo from tbcfgm90 where dsnombre="operador"),">",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(39),(select idcatalogo from tbcfgm90 where dsnombre="operador"),"<",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(40),(select idcatalogo from tbcfgm90 where dsnombre="operador"),">=",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(41),(select idcatalogo from tbcfgm90 where dsnombre="operador"),"<=",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(42),(select idcatalogo from tbcfgm90 where dsnombre="operador"),"==",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(43),(select idcatalogo from tbcfgm90 where dsnombre="operador"),"!=",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(44),(select idcatalogo from tbcfgm90 where dsnombre="operador"),"Exp. Reg.",'codigo',false,"sesion",current_timestamp);

INSERT INTO tbcfgm91 VALUES(MD5(45),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Campo de texto", 'text', false, "sesion", CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5(46),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Campo de  fecha", 'datetime', false, "sesion", CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5(47),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Título",'header',false,"sesion",CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5(48),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"), "SubFormulario", 'subform', false, "sesion", CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5(49),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Lista desplegable", 'select', false, "sesion", CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5(50),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Lista tipo test", 'radio', false, "sesion", CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5(51),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Casilla de verificaciÃ³n", 'check', false, "sesion", CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5(52),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Lista desplegable opciÃ³n multiple", 'sel-check', false, "sesion", CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5(53),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"SiNo", 'singlechk', false, "sesion", CURRENT_TIMESTAMP);
INSERT INTO tbcfgm91 VALUES(MD5('nosi'),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"NoSi", 'singlechkno', false, "sesion", CURRENT_TIMESTAMP);

-- INSERT INTO tbcfgm91 VALUES(MD5(47),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Fecha",'datetime',false,"sesion",CURRENT_TIMESTAMP);
-- INSERT INTO tbcfgm91 VALUES(MD5(49),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"),"Titulo",'header',false,"sesion",CURRENT_TIMESTAMP);
-- INSERT INTO tbcfgm91 VALUES(MD5(53),(SELECT idcatalogo FROM tbcfgm90 WHERE dsnombre="tipo_componente_formulario"), "SubFormulario", 'subform', false, "sesion", CURRENT_TIMESTAMP);

-- insert into tbcfgm91 values(md5(52),(select idcatalogo from tbcfgm90 where dsnombre="tipo_componente_formulario"),"Autocompletar",'codigo',false,"sesion",current_timestamp);
-- insert into tbcfgm91 values(md5(54),(select idcatalogo from tbcfgm90 where dsnombre="tipo_componente_sub_formulario"),"Texto",'codigo',false,"sesion",current_timestamp);
-- insert into tbcfgm91 values(md5(55),(select idcatalogo from tbcfgm90 where dsnombre="tipo_componente_sub_formulario"),"Lista Desplegable",'codigo',false,"sesion",current_timestamp);
-- insert into tbcfgm91 values(md5(56),(select idcatalogo from tbcfgm90 where dsnombre="tipo_componente_sub_formulario"),"Fecha",'codigo',false,"sesion",current_timestamp);

insert into tbcfgm91 values(md5(60),(select idcatalogo from tbcfgm90 where dsnombre="status_anticipo"),"Pagado",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(61),(select idcatalogo from tbcfgm90 where dsnombre="status_anticipo"),"No Pagado",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(62),(select idcatalogo from tbcfgm90 where dsnombre="status_anticipo"),"Pendiente de Pago",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(63),(select idcatalogo from tbcfgm90 where dsnombre="medio_pago"),"N/A",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(64),(select idcatalogo from tbcfgm90 where dsnombre="medio_pago"),"Efectivo",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(65),(select idcatalogo from tbcfgm90 where dsnombre="medio_pago"),"Tarjeta",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(66),(select idcatalogo from tbcfgm90 where dsnombre="medio_pago"),"Cheque",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(67),(select idcatalogo from tbcfgm90 where dsnombre="medio_pago"),"Crédito",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(68),(select idcatalogo from tbcfgm90 where dsnombre="concepto_pago"),"Impuesto",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(69),(select idcatalogo from tbcfgm90 where dsnombre="concepto_pago"),"Tramite asociado",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(70),(select idcatalogo from tbcfgm90 where dsnombre="concepto_pago"),"Liquidación de la operación",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(71),(select idcatalogo from tbcfgm90 where dsnombre="concepto_pago"),"Anticipo de la operación",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(72),(select idcatalogo from tbcfgm90 where dsnombre="concepto_pago"),"Hoja amarilla",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(73),(select idcatalogo from tbcfgm90 where dsnombre="docs_oficiales"),"Credencial INE",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(74),(select idcatalogo from tbcfgm90 where dsnombre="docs_oficiales"),"Pasaporte",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(75),(select idcatalogo from tbcfgm90 where dsnombre="docs_oficiales"),"Cartilla Militar",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(76),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Instituto Nacional Electoral",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(77),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Secretaría De Relaciones Exteriores",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(78),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Secretaría De Defensa Nacional",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(79),(select idcatalogo from tbcfgm90 where dsnombre="docs_oficiales"),"Cedula Profesional",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(80),(select idcatalogo from tbcfgm90 where dsnombre="docs_oficiales"),"Identificación Oficial",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(81),(select idcatalogo from tbcfgm90 where dsnombre="docs_oficiales"),"Documento Migratorio",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(82),(select idcatalogo from tbcfgm90 where dsnombre="docs_oficiales"),"Certificado De Matrícula Consular",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(83),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Secretaría De Educación Pública",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(84),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Gobierno Federal",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(85),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Gobierno Estatal",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(86),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Gobierno Municipal",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(87),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Gobierno Del Distrito Federal",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(88),(select idcatalogo from tbcfgm90 where dsnombre="instituciones_gobierno"),"Autoridad Competente",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(89),(select idcatalogo from tbcfgm90 where dsnombre="tratamiento"),"el señor",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(90),(select idcatalogo from tbcfgm90 where dsnombre="tratamiento"),"la señora",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(91),(select idcatalogo from tbcfgm90 where dsnombre="tratamiento"),"la señorita",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(92),(select idcatalogo from tbcfgm90 where dsnombre="regimen"),"Bienes Separados",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5(93),(select idcatalogo from tbcfgm90 where dsnombre="regimen"),"Sociedad Conyugal",'codigo',false,"sesion",current_timestamp);


-- YA NO USAR ID'S PARA TBCFGM91 PORQUE SE AGREGARON CONSECUTIVAMENTE PARA BRANCH COMPARECIENTES

-- REGIMENES FISCALES

insert into tbcfgm91 values(md5("regfiscal1"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad en Nombre Colectivo",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal2"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad en Comandita Simple",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal3"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad de Responsabilidad Limitada",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal4"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad Anónima de Capital Variable",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal5"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad en Comandita por Acciones",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal6"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad Cooperativa",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal7"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Asociación Civil",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal8"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad Cooperativa de Producción",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal9"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Instituciones de Crédito, Seguros y Fianzas",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal10"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad en Comandita Simple",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal11"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad en Comandita Simple",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("regfiscal12"),(select idcatalogo from tbcfgm90 where dsnombre="regimen_fiscal"),"Sociedad Anónima",'codigo',false,"sesion",current_timestamp);


insert into tbcfgm91 values(md5("tipoadquisicion1"),(select idcatalogo from tbcfgm90 where dsnombre="tipo_adquisicion"),"compraventa::Compraventa",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("tipoadquisicion2"),(select idcatalogo from tbcfgm90 where dsnombre="tipo_adquisicion"),"donacion::Donación",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("tipoadquisicion3"),(select idcatalogo from tbcfgm90 where dsnombre="tipo_adquisicion"),"adjudicación::Adjudicación",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("tipoadquisicion4"),(select idcatalogo from tbcfgm90 where dsnombre="tipo_adquisicion"),"trans_dominio_fideicomiso::Transmisión de dominio en ejecución de fideicomiso",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("tipoadquisicion5"),(select idcatalogo from tbcfgm90 where dsnombre="tipo_adquisicion"),"permuta::Permuta",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("tipoadquisicion6"),(select idcatalogo from tbcfgm90 where dsnombre="tipo_adquisicion"),"trans_dominio::Transmisión de dominio",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("tipoadquisicion7"),(select idcatalogo from tbcfgm90 where dsnombre="tipo_adquisicion"),"aplicacion_bienes::Aplicación de bienes",'codigo',false,"sesion",current_timestamp);
insert into tbcfgm91 values(md5("tipoadquisicion8"),(select idcatalogo from tbcfgm90 where dsnombre="tipo_adquisicion"),"dacion_pago::Dación en pago",'codigo',false,"sesion",current_timestamp);

-- NUEVOS TRATAMIENTOS PARA PERSONAS MORALES

insert into tbcfgm91 values(md5(96),(select idcatalogo from tbcfgm90 where dsnombre="tratamiento"),"",'codigo',false,"sesion",current_timestamp);


-- TASAS DE IMPUESTO 
delete from tbbsnm43;
insert into tbbsnm43 values(md5(1),"IVA","IVA",.16,.16);

-- TIPO COMPARECIENTE
select 'tipo compareciente';
delete from tbbsnm31;
insert into tbbsnm31 values(md5(1),'Comprador','desc','sesion',current_timestamp);
insert into tbbsnm31 values(md5(2),'Vendedor','desc','sesion',current_timestamp);
insert into tbbsnm31 values(md5(3),'Cónyuge','desc','sesion',current_timestamp);
insert into tbbsnm31 values(md5(4),'Representante','desc','sesion',current_timestamp);
insert into tbbsnm31 values(md5(5),'Autorizante','desc','sesion',current_timestamp);
INSERT INTO tbbsnm31 set idtipocompareciente = md5('acreedor'), dsnombre = 'Acreedor', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('deudor'), dsnombre = 'Deudor', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('otro'), dsnombre = 'Otro', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('garante'), dsnombre = 'Garante', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('poderdante'), dsnombre = 'Poderdante', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('apoderado'), dsnombre = 'Apoderado', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('socio'), dsnombre = 'Socio', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('apoderado'), dsnombre = 'Apoderado', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('ratificante'), dsnombre = 'Ratificante', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('compareciente'), dsnombre = 'Compareciente', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('solidario'), dsnombre = 'Beneficiario Solidario', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('acreditado'), dsnombre = 'Acreditado', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('acreditadoygarante'), dsnombre = 'Acreditado_y_Garante', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('cancelahipoteca'), dsnombre = 'Cancela_hipoteca', idsesion = '1';

INSERT INTO tbbsnm31 set idtipocompareciente = md5('juez'), dsnombre = 'Juez', idsesion = '1';
INSERT INTO tbbsnm31 set idtipocompareciente = md5('banco'), dsnombre = 'Banco', idsesion = '1';

INSERT INTO tbbsnm31 set idtipocompareciente = md5('testador'), dsnombre = 'Testador', idsesion = '1';


-- INSERTA OPERACIONES
select 'operaciones';
delete from tbbsnm17;
delete from tbbsnm27;
INSERT INTO tbbsnm27 VALUES(md5(1),'Compraventa','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(2),'Fideicomiso','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(3),'Garantias reales','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(4),'Constitucion de regimen de propiedad en condominio','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(5),'Fusion','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(6),'Subdivision','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(7),'Relotificacion de inmueble','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(8),'Cancelacion de hipoteca','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(9),'Cancelacion de reserva de dominio','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(10),'Aceptacion de herencia','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(11),'Testamento','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(12),'Protocolizacion de poder otorgado en el extranjero','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(13),'Poder','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(14),'Constitutivas','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(15),'Ratificacion','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(16),'Declaraciones','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(17),'Fe de hechos','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(18),'Protocolizaciones','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(19),'Aperturas de credito','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(20),'Convenio','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(21),'Revocaciones','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(22),'Notificaciones','codigo',"sesion",current_timestamp);
-- INSERT INTO tbbsnm27 VALUES(md5(23),'Cotejo','codigo',"sesion",current_timestamp);


-- INSERTA SUBOPERACIONES
select 'suboperaciones';
insert into tbbsnm17 values (md5(1),'Inmueble','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Compraventa'),'idsesion',current_timestamp);
insert into tbbsnm17 values (md5('condominio'),'Primera venta de condominio','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Compraventa'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(2),'Donacion','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Traslativa de dominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(3),'Adjudicacion por legado','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Traslativa de dominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(4),'Adjudicacion por herencia','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Traslativa de dominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(5),'Adjudicacion por remate judicial','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Traslativa de dominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(6),'Dacion de pago','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Traslativa de dominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(7),'Transmision de propiedad y ejecucion de fideicomiso y extincion parcial del mismo','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Traslativa de dominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(8),'Aportacion al fideicomiso de garantia, administracion, etc','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Fideicomiso'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(9),'Convenio modificatorio','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Fideicomiso'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(10),'Reversion de propiedad en ejecucion de fideicomiso y extension del mismo','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Fideicomiso'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(11),'Apertura de credito con garantia hipotecaria','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Garantias reales'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(12),'Mutuo con garantia hipotecaria','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Garantias reales'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(13),'Convenios modificatorios','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Garantias reales'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(14),'Reconocimiento de adeudo con garantia hipotecaria','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Garantias reales'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(15),'Prenda','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Garantias reales'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(16),'Constitucion','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Constitucion de regimen de propiedad en condominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(17),'Modificacion','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Constitucion de regimen de propiedad en condominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(18),'Fusion','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Fusion'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(19),'Subdivision','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Subdivision'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(20),'Relotificacion de inmueble','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Relotificacion de inmueble'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(21),'Cancelacion de hipoteca','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Cancelacion de hipoteca'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(22),'Cancelacion de reserva de dominio','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Cancelacion de reserva de dominio'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(23),'Testamentario','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Aceptacion de herencia'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(24),'Intestamentaria','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Aceptacion de herencia'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(25),'Testamento Publico Simple','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Testamento'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(26),'Testamento Publico Abierto','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Testamento'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(27),'ProtocolizaciÃ³n de poder otorgado en el extranjero','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Protocolizacion de poder otorgado en el extranjero'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(28),'Persona fÃ­sica','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Poder'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(29),'Persona moral','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Poder'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(30),'SA (de CV)','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Constitutivas'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(31),'SAPI (de CV)','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Constitutivas'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(32),'S de RL (de CV)','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Constitutivas'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(33),'AC','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Constitutivas'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(34),'SC','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Constitutivas'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(35),'Ratificacion','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Ratificacion'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(36),'Declaraciones','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Declaraciones'),'idsesion',current_timestamp);
-- insert into tbbsnm17 values (md5(37),'Fe de hechos','descripcion',(select idoperacion from tbbsnm27 where dsnombre='Fe de hechos'),'idsesion',current_timestamp);

select 'roles';
delete from tbcfgm07;
-- INSERTA ADMINISTRADOR
insert into tbcfgm07 values(md5(0),"Administrador","descripcion","admin",current_timestamp,"sesion");
-- ROLES
insert into tbcfgm07 values(md5(1),"Cliente","descripcion","clte",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(2),"Recepción","descripcion","recep",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(3),"Abogado","descripcion","abog",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(4),"Sc. Atencion Publico","descripcion","secap",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(5),"Caja","descripcion","caja",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(6),"Archivo","descripcion","arch",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(7),"Ditto","descripcion","ditto",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(8),"J. Mesa Control","descripcion","mesco",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(9),"Notario","descripcion","not",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(10),"Gestor","descripcion","gest",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(11),"Jefe de Pool","descripcion","jpool",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(12),"Protocolista","descripcion","proto",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(13),"Revisor","descripcion","rev",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(14),"Jefe Pasantes","descripcion","jpas",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(15),"Pasante","descripcion","pas",current_timestamp,"sesion");

insert into tbcfgm07 values(md5(16),"R. Mesa Control","descripcion","rmesc",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(17),"Tesorería","descripcion","tesor",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(18),"Notas","descripcion","notas",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(19),"Reg. Externo","descripcion","regex",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(20),"Funcion","descripcion","func",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(21),"Mensajería","descripcion","msj",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(22),"J. Cotejo","descripcion","jcote",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(23),"Cotejo","descripcion","cote",current_timestamp,"sesion");
insert into tbcfgm07 values(md5(24),"Encuadernador","descripcion","enc",current_timestamp,"sesion");
-- PROCESOS ROL
select 'procesos rol';
insert into tbcfgm17 values(1,"c4ca4238a0b923820dcc509a6f75849b","37693cfc748049e45d87b8c7d8b9aacd");
insert into tbcfgm17 values(2,"c4ca4238a0b923820dcc509a6f75849b","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(3,"c4ca4238a0b923820dcc509a6f75849b","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(4,"c4ca4238a0b923820dcc509a6f75849b","34173cb38f07f89ddbebc2ac9128303f");
insert into tbcfgm17 values(5,"c4ca4238a0b923820dcc509a6f75849b","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(6,"c81e728d9d4c2f636f067f89cc14862c","37693cfc748049e45d87b8c7d8b9aacd");
insert into tbcfgm17 values(7,"c81e728d9d4c2f636f067f89cc14862c","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(8,"c81e728d9d4c2f636f067f89cc14862c","34173cb38f07f89ddbebc2ac9128303f");
insert into tbcfgm17 values(9,"c81e728d9d4c2f636f067f89cc14862c","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(10,"eccbc87e4b5ce2fe28308fd9f2a7baf3","37693cfc748049e45d87b8c7d8b9aacd");
insert into tbcfgm17 values(11,"eccbc87e4b5ce2fe28308fd9f2a7baf3","1ff1de774005f8da13f42943881c655f");
insert into tbcfgm17 values(12,"eccbc87e4b5ce2fe28308fd9f2a7baf3","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(13,"eccbc87e4b5ce2fe28308fd9f2a7baf3","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(14,"eccbc87e4b5ce2fe28308fd9f2a7baf3","02e74f10e0327ad868d138f2b4fdd6f0");
insert into tbcfgm17 values(15,"eccbc87e4b5ce2fe28308fd9f2a7baf3","33e75ff09dd601bbe69f351039152189");
insert into tbcfgm17 values(16,"eccbc87e4b5ce2fe28308fd9f2a7baf3","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(17,"eccbc87e4b5ce2fe28308fd9f2a7baf3","34173cb38f07f89ddbebc2ac9128303f");
insert into tbcfgm17 values(18,"eccbc87e4b5ce2fe28308fd9f2a7baf3","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(19,"a87ff679a2f3e71d9181a67b7542122c","37693cfc748049e45d87b8c7d8b9aacd");
insert into tbcfgm17 values(20,"a87ff679a2f3e71d9181a67b7542122c","1ff1de774005f8da13f42943881c655f");
insert into tbcfgm17 values(21,"a87ff679a2f3e71d9181a67b7542122c","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(22,"a87ff679a2f3e71d9181a67b7542122c","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(23,"a87ff679a2f3e71d9181a67b7542122c","34173cb38f07f89ddbebc2ac9128303f");
insert into tbcfgm17 values(24,"e4da3b7fbbce2345d7772b0674a318d5","37693cfc748049e45d87b8c7d8b9aacd");
insert into tbcfgm17 values(25,"e4da3b7fbbce2345d7772b0674a318d5","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(26,"e4da3b7fbbce2345d7772b0674a318d5","02e74f10e0327ad868d138f2b4fdd6f0");
insert into tbcfgm17 values(27,"e4da3b7fbbce2345d7772b0674a318d5","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(28,"e4da3b7fbbce2345d7772b0674a318d5","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(29,"1679091c5a880faf6fb5e6087eb1b2dc","37693cfc748049e45d87b8c7d8b9aacd");
insert into tbcfgm17 values(30,"1679091c5a880faf6fb5e6087eb1b2dc","1ff1de774005f8da13f42943881c655f");
insert into tbcfgm17 values(31,"1679091c5a880faf6fb5e6087eb1b2dc","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(32,"1679091c5a880faf6fb5e6087eb1b2dc","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(33,"1679091c5a880faf6fb5e6087eb1b2dc","02e74f10e0327ad868d138f2b4fdd6f0");
insert into tbcfgm17 values(34,"1679091c5a880faf6fb5e6087eb1b2dc","33e75ff09dd601bbe69f351039152189");
insert into tbcfgm17 values(35,"1679091c5a880faf6fb5e6087eb1b2dc","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(36,"1679091c5a880faf6fb5e6087eb1b2dc","34173cb38f07f89ddbebc2ac9128303f");
insert into tbcfgm17 values(37,"1679091c5a880faf6fb5e6087eb1b2dc","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(38,"1679091c5a880faf6fb5e6087eb1b2dc","908c9a564a86426585b29f5335b619bc");
insert into tbcfgm17 values(39,"8f14e45fceea167a5a36dedd4bea2543","1ff1de774005f8da13f42943881c655f");
insert into tbcfgm17 values(40,"8f14e45fceea167a5a36dedd4bea2543","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(41,"8f14e45fceea167a5a36dedd4bea2543","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(42,"8f14e45fceea167a5a36dedd4bea2543","33e75ff09dd601bbe69f351039152189");
insert into tbcfgm17 values(43,"8f14e45fceea167a5a36dedd4bea2543","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(44,"8f14e45fceea167a5a36dedd4bea2543","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(45,"8f14e45fceea167a5a36dedd4bea2543","908c9a564a86426585b29f5335b619bc");
insert into tbcfgm17 values(46,"c9f0f895fb98ab9159f51fd0297e236d","1ff1de774005f8da13f42943881c655f");
insert into tbcfgm17 values(47,"c9f0f895fb98ab9159f51fd0297e236d","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(48,"c9f0f895fb98ab9159f51fd0297e236d","02e74f10e0327ad868d138f2b4fdd6f0");
insert into tbcfgm17 values(49,"c9f0f895fb98ab9159f51fd0297e236d","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(50,"45c48cce2e2d7fbdea1afc51c7c6ad26","1ff1de774005f8da13f42943881c655f");
insert into tbcfgm17 values(51,"45c48cce2e2d7fbdea1afc51c7c6ad26","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(52,"45c48cce2e2d7fbdea1afc51c7c6ad26","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(53,"45c48cce2e2d7fbdea1afc51c7c6ad26","02e74f10e0327ad868d138f2b4fdd6f0");
insert into tbcfgm17 values(54,"45c48cce2e2d7fbdea1afc51c7c6ad26","33e75ff09dd601bbe69f351039152189");
insert into tbcfgm17 values(55,"45c48cce2e2d7fbdea1afc51c7c6ad26","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(56,"45c48cce2e2d7fbdea1afc51c7c6ad26","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(57,"45c48cce2e2d7fbdea1afc51c7c6ad26","908c9a564a86426585b29f5335b619bc");
insert into tbcfgm17 values(58,"d3d9446802a44259755d38e6d163e820","1ff1de774005f8da13f42943881c655f");
insert into tbcfgm17 values(59,"d3d9446802a44259755d38e6d163e820","02e74f10e0327ad868d138f2b4fdd6f0");
insert into tbcfgm17 values(60,"d3d9446802a44259755d38e6d163e820","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(61,"6512bd43d9caa6e02c990b0a82652dca","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(62,"6512bd43d9caa6e02c990b0a82652dca","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(63,"6512bd43d9caa6e02c990b0a82652dca","33e75ff09dd601bbe69f351039152189");
insert into tbcfgm17 values(64,"c20ad4d76fe97759aa27a0c99bff6710","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(65,"c20ad4d76fe97759aa27a0c99bff6710","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(66,"c51ce410c124a10e0db5e4b97fc2af39","8e296a067a37563370ded05f5a3bf3ec");
insert into tbcfgm17 values(67,"c51ce410c124a10e0db5e4b97fc2af39","33e75ff09dd601bbe69f351039152189");
insert into tbcfgm17 values(68,"c51ce410c124a10e0db5e4b97fc2af39","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(69,"aab3238922bcc25a6f606eb525ffdc56","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(70,"aab3238922bcc25a6f606eb525ffdc56","908c9a564a86426585b29f5335b619bc");
insert into tbcfgm17 values(71,"9bf31c7ff062936a96d3c8bd1f8f2ff3","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(72,"9bf31c7ff062936a96d3c8bd1f8f2ff3","33e75ff09dd601bbe69f351039152189");
insert into tbcfgm17 values(73,"9bf31c7ff062936a96d3c8bd1f8f2ff3","908c9a564a86426585b29f5335b619bc");
insert into tbcfgm17 values(74,"c51ce410c124a10e0db5e4b97fc2af39","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(75,"c74d97b01eae257e44aa9d5bade97baf","4e732ced3463d06de0ca9a15b6153677");
insert into tbcfgm17 values(76,"c74d97b01eae257e44aa9d5bade97baf","02e74f10e0327ad868d138f2b4fdd6f0");
insert into tbcfgm17 values(77,"c74d97b01eae257e44aa9d5bade97baf","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(78,"70efdf2ec9b086079795c442636b55fb","02e74f10e0327ad868d138f2b4fdd6f0");
insert into tbcfgm17 values(79,"6f4922f45568161a8cdf4ad2299f6d23","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(80,"1f0e3dad99908345f7439f8ffabdffc4","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(81,"98f13708210194c475687be6106a3b84","6ea9ab1baa0efb9e19094440c317e21b");
insert into tbcfgm17 values(82,"3c59dc048e8850243be8079a5c74d079","34173cb38f07f89ddbebc2ac9128303f");
insert into tbcfgm17 values(83,"b6d767d2f8ed5d21a44b0e5886680cb9","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(84,"37693cfc748049e45d87b8c7d8b9aacd","c16a5320fa475530d9583c34fd356ef5");
insert into tbcfgm17 values(85,"1ff1de774005f8da13f42943881c655f","908c9a564a86426585b29f5335b619bc");
-- NOTARIA,USUARIOS
select 'notaria usuarios';
insert into tbbsnm12 values(md5(1),"D.F","Alvaro Obregon","D.F",false,"Col. San Angel","San Angel","Altavista","01049","","83","","","","Avenida Revolución","sesion",current_timestamp);

insert into tbbsnm28 values(md5(1),md5(21),"Gonzalo","Blanco","Ortíz","Gonzalo Blanco Ortiz","","",md5(8),"","2014-11-23",null,"sesion",current_timestamp);

-- insert into tbbsnm16 values(md5(current_timestamp+3),"cliente 1","98","rfc912345","/usr/cat/logo/",
-- (select iddomicilio from tbbsnm12 where dsestado="puebla"),current_timestamp,"sesion 1");
insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(2),"usuario",sha1("pass"),"Alan","Castillo","Perez",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"ACEP9805049L8","user@user.com",
md5(0),current_timestamp,current_timestamp,"ACP");
insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 

values (md5(1),"usuario2",sha1("pass2"),"Diego","Godinez","Perez",0,1,"23.23.23.23","ref","sesion 2",current_timestamp,"DEGP8708028K9","asdf@asdf.com",
md5(1),current_timestamp,current_timestamp,"DGP");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5("archivo1"),"archivo",sha1("archivo"),"Victor","Espinosa","Perez",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"ACEP9805049L8","im.vicoy@gmail.com",
md5(6),current_timestamp,current_timestamp,"VDEV");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5("archivo2"),"archivo2",sha1("archivo2"),"Mario","Perez","Farias",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"ACEP9805049L8","matoooon@gmail.com",
md5(6),current_timestamp,current_timestamp,"MPE");

-- FOVISSTE, INFONAVIT
insert into tbbsnm28 values(md5("fovissste"),md5(22),"FOVISSSTE",null,null,"FOVISSSTE","","",md5(8),"","2014-11-23",null,"sesion",current_timestamp);
insert into tbbsnm28 values(md5("infonavit"),md5(22),"INFONAVIT",null,null,"INFONAVIT","","",md5(8),"","2014-11-23",null,"sesion",current_timestamp);

-- INSERTA NOTARIOS
select 'inserta notarios';
insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(6),"gortiz",sha1("gortiz"),"Gonzalo M.","Ortíz","Blanco",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"GOBL7904057K8","gortiz@notarias98y24.com.mx",
(select idrol from tbcfgm07 where dsnombre ='Notario'),current_timestamp,current_timestamp,"GOB");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(7),"rduarte",sha1("rduarte"),"Luis Ricardo","Duarte","Guerra",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"LDUG7403057J3","rduarte@notarias98y24.com.mx",
(select idrol from tbcfgm07 where dsnombre ='Notario'),current_timestamp,current_timestamp,"RDG");

-- INSERT NOTARIAS
select 'notarias';
insert into tbbsnm16 values("1","98","",md5(1),false,md5(6),current_timestamp,"sesion1");
insert into tbbsnm16 values("2","24","",md5(1),true,md5(7),current_timestamp,"sesion1");

-- INSERTA ABOGADOS
select 'inserta abogados';
-- insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
-- values (md5(3),"abogado",sha1("password"),"Jose","Perez","Paz",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"JEPA7804038J9","user@user.com",
-- md5(3),current_timestamp,current_timestamp,"JPP");

-- insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
-- values (md5(4),"abogado1",sha1("pass"),"Pedro","Espinosa","Paz",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"JEPZ7907075P8","user@user.com",
-- md5(3),current_timestamp,current_timestamp,"PEP");

-- insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
-- values (md5(5),"abogado2",sha1("pass"),"Julio","Paez","Paz",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"JPAE8403046U7","user@user.com",
-- md5(3),current_timestamp,current_timestamp,"JPP");

use notaria;
select 'abogados notaria';
insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(66),"cceron",sha1("cceron"),"Carlos","Ceron","Nacif",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"","",
md5(3),current_timestamp,current_timestamp,"CCN");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(76),"golguin",sha1("golguin"),"Guillermo","Olguin","Ortega",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"","",
md5(3),current_timestamp,current_timestamp,"GOO");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(86),"ahuacuja",sha1("ahuacuja"),"Aranzazu","Huacuja","De La Torre",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"","",
md5(3),current_timestamp,current_timestamp,"AHDLT");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(96),"mguerrero",sha1("mguerrero"),"Marlen","Guerrero","Orozco",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"","",
md5(3),current_timestamp,current_timestamp,"MGO");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(106),"rlopez",sha1("rlopez"),"Rodrigo","López","Peters",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"","",
md5(3),current_timestamp,current_timestamp,"RLP");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(116),"cpizano",sha1("cpizano"),"Carlo Fabián","Pizano","Salinas",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"","",
md5(3),current_timestamp,current_timestamp,"CFPS");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(56),"sporcayo",sha1("sporcayo"),"Selene","Porcayo","",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"","",
md5(3),current_timestamp,current_timestamp,"SP");

insert into tbcfgm03 (idusuario,cdusuario,dsvalenc,dsnombre,dspaterno,dsmaterno,isactualizapwd,inestatus,dsultimoacceso,dsreferencia,idsesion,tmstmp,dsrfc,dscorreo,idrol,fchinicio,fchfin,dsiniciales) 
values (md5(666),"sysadmin",sha1("sysadmin"),"Víctor Manuel","Munguía","",0,1,"23.23.23.23","ref","sesion 1",current_timestamp,"","",
md5(0),current_timestamp,current_timestamp,"VMM");


select 'gestores notaria';
delete from tbbsnm63;
insert into tbbsnm63 values(md5(1),"Remy Omar","Alaniz","Mendoza","","","","","",0,md5(35),1,'sesion',current_timestamp);
insert into tbbsnm63 values(md5(2),"Christopher","Gonzalez","Avila","","","","","",0,md5(35),1,'sesion',current_timestamp);
insert into tbbsnm63 values(md5(3),"Reynaldo","Zarco","Ayala","","","","","",0,md5(35),1,'sesion',current_timestamp);
insert into tbbsnm63 values(md5(4),"Ernesto","Galicia","Martínez","","","","","",0,md5(35),1,'sesion',current_timestamp);
insert into tbbsnm63 values(md5(5),"Juan Carlos","Flores","Díaz","","","","","",0,md5(35),1,'sesion',current_timestamp);

select 'valuadores';
delete from tbbsnm65;
insert into tbbsnm65 values(md5(1),"Juan Pablo","Lopez","Cuevas","AVE, Asesoría, Valuación y Estudio Unidad de Valuación S.A de C.V","","","","","",0,1,'sesion',CURRENT_TIMESTAMP);
insert into tbbsnm65 values(md5(2),"Alberto","Lopez","Cuevas","AVE, Asesoría, Valuación y Estudio Unidad de Valuación S.A de C.V","","","","","",0,1,'sesion',CURRENT_TIMESTAMP);
insert into tbbsnm65 values(md5(3),"Santiago","Morales","Broc","Broc Avalúos S.C","","","","","",0,1,'sesion',CURRENT_TIMESTAMP);
-- USUARIOS CAJA
insert into tbcfgm03 values (md5('caja1'),'Marisela','Ramirez','Martinez',md5(5),'cajera',sha1("cajera"),'caja@caja.com','MPDE7908078J9','MPDE790807HDFLRM65','MRM','ref',0,1,1,'23.23.23.23',current_timestamp,
current_timestamp,current_timestamp,'sesion');

insert into tbcfgm03 values (md5('caja2'),'Octavio','Ugalde','Paz',md5(5),'cajero1',sha1("cajero1"),'caja@caja.com','OEUP7903047J8','OEUP790402HDFLRM78','OUP','ref',0,1,1,'23.23.23.23',current_timestamp,
current_timestamp,current_timestamp,'sesion');





insert into tbbsnm68 values("asdf",md5(2),md5(1),10000,11000,current_date,current_date,true,'1',current_timestamp);

insert into tbbsnm69 values("asdfasdf",md5(1),md5(2),'todo ok',10000,11000,false,'1',current_timestamp);

insert into tbbsnm70 values("asdfasdf",md5(1),'todo culero',false,'1',current_timestamp);




-- CAMPOS TARJETA AMARILLA
select 'campos tarjeta amarilla';
insert into tbbsnm62 values(1,'c4ca4238a0b923820dcc509a6f75849b','Derechos de RPP','rpp','sesion',current_timestamp);
-- insert into tbbsnm62 values(2,'c9f0f895fb98ab9159f51fd0297e236d','Derechos de RPP','rpp','sesion',current_timestamp);
-- insert into tbbsnm62 values(3,'c51ce410c124a10e0db5e4b97fc2af39','Derechos de RPP','rpp','sesion',current_timestamp);
-- insert into tbbsnm62 values(4,'aab3238922bcc25a6f606eb525ffdc56','Derechos de RPP','rpp','sesion',current_timestamp);
-- insert into tbbsnm62 values(5,'6f4922f45568161a8cdf4ad2299f6d23','Derechos de RPP','rpp','sesion',current_timestamp);
-- insert into tbbsnm62 values(6,'1f0e3dad99908345f7439f8ffabdffc4','Derechos de RPP','rpp','sesion',current_timestamp);
-- insert into tbbsnm62 values(7,'a87ff679a2f3e71d9181a67b7542122c','Derechos de RPP','rpp','sesion',current_timestamp);
-- insert into tbbsnm62 values(8,'98f13708210194c475687be6106a3b84','Derechos de RPP','rpp','sesion',current_timestamp);
-- insert into tbbsnm62 values(9,'3c59dc048e8850243be8079a5c74d079','Derechos de RPP','rpp','sesion',current_timestamp);
insert into tbbsnm62 values(10,'c4ca4238a0b923820dcc509a6f75849b','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(11,'c9f0f895fb98ab9159f51fd0297e236d','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(12,'c51ce410c124a10e0db5e4b97fc2af39','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(13,'aab3238922bcc25a6f606eb525ffdc56','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(14,'6f4922f45568161a8cdf4ad2299f6d23','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(15,'1f0e3dad99908345f7439f8ffabdffc4','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(16,'a87ff679a2f3e71d9181a67b7542122c','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(17,'98f13708210194c475687be6106a3b84','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(18,'3c59dc048e8850243be8079a5c74d079','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(19,'d3d9446802a44259755d38e6d163e820','Erogaciones','ero','sesion',current_timestamp);
-- insert into tbbsnm62 values(20,'6512bd43d9caa6e02c990b0a82652dca','Erogaciones','ero','sesion',current_timestamp);
insert into tbbsnm62 values(21,'c4ca4238a0b923820dcc509a6f75849b','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(22,'c9f0f895fb98ab9159f51fd0297e236d','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(23,'c51ce410c124a10e0db5e4b97fc2af39','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(24,'aab3238922bcc25a6f606eb525ffdc56','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(25,'6f4922f45568161a8cdf4ad2299f6d23','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(26,'1f0e3dad99908345f7439f8ffabdffc4','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(27,'a87ff679a2f3e71d9181a67b7542122c','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(28,'98f13708210194c475687be6106a3b84','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(29,'3c59dc048e8850243be8079a5c74d079','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(30,'c74d97b01eae257e44aa9d5bade97baf','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(31,'70efdf2ec9b086079795c442636b55fb','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(32,'9bf31c7ff062936a96d3c8bd1f8f2ff3','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(33,'b6d767d2f8ed5d21a44b0e5886680cb9','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(34,'c20ad4d76fe97759aa27a0c99bff6710','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(35,'37693cfc748049e45d87b8c7d8b9aacd','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(36,'d3d9446802a44259755d38e6d163e820','Honorarios','hon','sesion',current_timestamp);
-- insert into tbbsnm62 values(37,'6512bd43d9caa6e02c990b0a82652dca','Honorarios','hon','sesion',current_timestamp);
insert into tbbsnm62 values(38,'c4ca4238a0b923820dcc509a6f75849b','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(39,'c9f0f895fb98ab9159f51fd0297e236d','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(40,'c51ce410c124a10e0db5e4b97fc2af39','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(41,'aab3238922bcc25a6f606eb525ffdc56','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(42,'6f4922f45568161a8cdf4ad2299f6d23','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(43,'1f0e3dad99908345f7439f8ffabdffc4','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(44,'a87ff679a2f3e71d9181a67b7542122c','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(45,'98f13708210194c475687be6106a3b84','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(46,'3c59dc048e8850243be8079a5c74d079','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(47,'c74d97b01eae257e44aa9d5bade97baf','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(48,'70efdf2ec9b086079795c442636b55fb','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(49,'9bf31c7ff062936a96d3c8bd1f8f2ff3','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(50,'b6d767d2f8ed5d21a44b0e5886680cb9','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(51,'c20ad4d76fe97759aa27a0c99bff6710','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(52,'37693cfc748049e45d87b8c7d8b9aacd','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(53,'d3d9446802a44259755d38e6d163e820','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(54,'6512bd43d9caa6e02c990b0a82652dca','IVA','iva','sesion',current_timestamp);
-- insert into tbbsnm62 values(55,'d3d9446802a44259755d38e6d163e820','Publicaciones','pub','sesion',current_timestamp);
insert into tbbsnm62 values(56,'c4ca4238a0b923820dcc509a6f75849b','ISAI, TD, Impuestos','iti','sesion',current_timestamp);
-- insert into tbbsnm62 values(57,'a87ff679a2f3e71d9181a67b7542122c','ISAI, TD, Impuestos','iti','sesion',current_timestamp);


-- INSERTS TIPO COMPARECIENTE
-- select 'tipo compareciente';
-- insert into tbbsnm31 values(md5(1),"N/A","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(2),"ARRENDATARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(3),"JUEZ","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(4),"REPUDIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(5),"REPUDIA","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(6),"FIDUCIARIO SUSTITUTO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(7),"USUFRUCTUARIO" ,"desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(8),"CANCELA CONVENIO MODIFICATORIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(9),"MUTUARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(11),"MUTUANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(12),"DEPOSITARIO" ,"desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(13),"RENUNCIA","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(14),"REPRESENTANTE DEL COMPRADOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(15),"MANDANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(16),"MANDATARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(17),"CAUSHABIENTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(18),"FIDEICOMITANTE B","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(19),"FIDEICOMITANTE A","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(220),"EXTINCION PARCIAL DEL FIDEICOMISO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(21),"EXTINCION DE HIPOTECA" ,"desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(22),"AVAL","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(23),"FIDEICOMISARIO EN PRIMER LUGAR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(24),"FIDEICOMISARIO EN SEGUNDO LUGAR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(25),"ACREEDOR HIPOTECARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(26),"REPRESENTANTE DEL FIDEICOMISARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(27),"FIADO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(28),"FIADOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(29),"CANCELA GARANTIA FIDUCIARIA","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(30),"COMITE TECNICO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(31),"HEREDERO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(32),"LEGATARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(33),"CONSENTIMIENTO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(34),"OTORGANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(35),"REPRESENTANTE DEL FIDEICOMITENTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(36),"FIDEICOMISARIO EN TERCER LUGAR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(37),"DELEGADO FIDUCIARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(38),"CANCELACION DE RESERVA DE DOMINIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(39),"DONATARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(40),"DONANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(41),"CLIENTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(42),"PRESTADOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(43),"COMPARECIENTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(44),"COACREDITADO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(45),"FUSIONANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(46),"FUSIONADA","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(47),"OBLIGADO SOLIDARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(48),"ENAJENANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(49),"ADQUIRENTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(50),"FIDUCIARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(51),"FIDEICOMISARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(52),"FIDEICOMITENTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(53),"CESIONARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(54),"CEDENTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(55),"ACREDITADO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(56),"SUCESION DE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(57),"APODERADO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(58),"TESTIGO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(59),"DECLARANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(60),"CANCELA HIPOTECA","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(61),"ACEPTANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(62),"REPRESENTANTE DEL VENDEDOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(63),"REPRESENTANTE DEL ACREEDOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(64),"GARANTE HIPOTECARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(65),"SOCIEDAD","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(66),"SOLICITANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(67),"ADJUDICATARIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(68),"DEUDOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(69),"REVOCA","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(70),"RATIFICANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(71),"DELEGADO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(72),"ACREEDOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(73),"PODERDANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(74),"SOCIO","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(75),"ALBACEA","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(76),"REPRESENTANTE","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(77),"TESTADOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(78),"ACCIONISTA","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(79),"COMPRADOR","desc","sesion",current_timestamp);
-- insert into tbbsnm31 values(md5(80),"VENDEDOR","desc","sesion",current_timestamp);




-- DOCUMENTO OBJETO INSERTS
select 'documento objeto';
insert into tbcfgm18 values (md5(1),'editorial','clausulas editorial',0,1,current_timestamp,'

El  diseño, edición, producción y comercialización de medios
impresos  y  audiovisuales,  tales  como  libros, boletines,
gacetas,   periódicos,   revistas,   videos,   películas,  
documentales,  y  en  general  cualquier  tipo  o  medio  de
representación.   Por   lo   que   enunciativa   pero  no  
limitativamente la sociedad podrá:
I.  Ejecutar  toda  clase  de  actos  de  comercio  pudiendo
comprar,  vender,  adquirir, distribuir, importar, exportar,
producir,  fabricar,  manufacturar,  transformar,  maquilar,
comercializar  y  en  general,  negociar  con  toda clase de
artículos  y  mercancías  por  cuenta  propia o ajena, en la
República Mexicana o en el Extranjero.
II.  Contratar  con  empresas  industriales, comerciales, de
servicios,  sociedades  y asociaciones, privadas o públicas,
entidades   gubernamentales,  asociaciones  profesionales  y
organismos  sociales en general, respecto de: el diseño y la
producción  de  medios de comunicación, escritos o gráficos,
para  informar  y  difundir  la  índole  de  sus actividades
propias,  tanto  a nivel interno como externo; la prestación
de servicios de información a través de los diversos géneros
periodísticos   (nota,   reportaje,  crónica,  artículos,  
entrevistas,  etcétera),  publicando  los  mismos  tanto  en
órganos  de  comunicación propios como en ajenos, impresos o
audiovisuales,  de  acuerdo con la índole de sus actividades
propias; la prestación de servicios de asesoría editorial en
órganos  de  comunicación  y  la  prestación de servicios de
diseño   gráfico,   publicitario   y   de  representación  
audiovisual.
III.  Prestar  y  recibir  toda  clase de servicios legales,
fiscales,  de contabilidad, administrativos y de asesoría en
general,  a  empresas  en  la  República  Mexicana  o  en el
Extranjero.
IV.  Llevar a cabo por cuenta propia o de terceros programas
de  capacitación  y  desarrollo,  así  como  investigaciones
científicas  para  desarrollo  tecnológico o investigaciones
profesionales,  en  las  materias que requieran las personas
físicas  o morales a las que la sociedad preste servicios, o
a  las  que la propia sociedad considere conveniente, ya sea
directamente  o  por  medio  de  Institutos  Tecnológicos  y
Universitarios  o empresas o instituciones especializadas en
el  País o en el Extranjero o mediante asociación con dichos
institutos,   universidades,   empresas  o  instituciones  
especializadas  y proporcionar a sus clientes los resultados
de dicha investigación.
V. Obtener, adquirir, registrar, utilizar o disponer de toda
clase  de  patentes,  marcas  industriales  y  de  servicio,
certificados  de  invención o nombres comerciales, diseños y
dibujos  industriales,  derechos  de  autor y cualquier otro
tipo  de  derechos  de  propiedad  industrial,  literaria  o
artística,  y  derechos sobre ellos ya sea en México o en el
extranjero.
VI.  Obtener  por  cualquier  título, concesiones, permisos,
autorizaciones  o  licencias,  así  como  celebrar cualquier
clase  de  contratos,  con  la  administración  pública  sea
federal o local o con cualquier particular.
VII.  Dar  o  tomar  dinero  en préstamo con o sin garantía,
emitir  bonos,  obligaciones,  valores  y  otros  títulos de
crédito,  así como adquirir legalmente y negociar con bonos,
obligaciones,  acciones,  valores y otros títulos de crédito
emitidos por terceros.
VIII.  Otorgar  avales  y  obligarse solidariamente así como
constituir garantías a favor de terceros.
IX.   Formar   parte   directa  o  indirectamente  de  otras
sociedades  o asociaciones e intervenir en todos los asuntos
y derechos relacionados con ellas.
X. Establecer sucursales, oficinas, subsidiarias y agencias,
así  como representar o ser agente de empresas e intermediar
en la venta de toda clase de bienes y servicios.
XI.  Adquirir  o  poseer  por  cualquier título, usar, dar o
tomar  en  arrendamiento,  administrar, vender o disponer en
cualquier  forma,  de  todos los bienes muebles o inmuebles,
así  como  derechos  reales  o  personales  sobre ellos, que
fueren  necesarios  o  convenientes  para la realización del
objeto de la sociedad.
XI. Contratar al personal necesario.
XII.  En  general  celebrar  toda clase de contratos ya sean
civiles, mercantiles o de cualquier naturaleza.
#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,null,md5(1),'sesion',current_timestamp);

insert into tbcfgm18 values (md5(2),'constructora','clausulas de constructora',0,1,current_timestamp,'


La  realización, supervisión, dirección, diseño, proyección,
presupuesto,  administración y mantenimiento de todo tipo de
obras   de  ingeniería,  urbanización  y  arquitectura  sean
públicas  o  privadas,  relacionadas  con  la  construcción,
remodelación,  conservación, reparación o demolición de toda
clase   de   inmuebles  y  obras  en  general;  Por  lo  que
enunciativa pero no limitativamente, la sociedad podrá:
I.  Ejecutar  toda  clase  de  actos  de  comercio  pudiendo
comprar,  vender,  adquirir, distribuir, importar, exportar,
producir,  fabricar,  manufacturar,  transformar,  maquilar,
comercializar  y  en  general,  negociar  con  toda clase de
artículos  y  mercancías  por  cuenta  propia o ajena, en la
República Mexicana o en el Extranjero.
II. Comprar, vender, importar y exportar por cuenta propia o
de  terceros  toda  clase  de  materiales  y equipos para la
construcción, así como sus refacciones.
III.  Prestar  y  recibir  toda  clase de servicios legales,
fiscales,  de contabilidad, administrativos y de asesoría en
general,  a  empresas  en  la  República  Mexicana  o  en el
Extranjero.
IV.  Llevar a cabo por cuenta propia o de terceros programas
de  capacitación  y  desarrollo,  así  como  investigaciones
científicas  para  desarrollo  tecnológico o investigaciones
profesionales,  en  las  materias que requieran las personas
físicas  o morales a las que la sociedad preste servicios, o
a  las  que la propia sociedad considere conveniente, ya sea
directamente  o  por  medio  de  Institutos  Tecnológicos  y
Universitarios  o empresas o instituciones especializadas en
el  País o en el Extranjero o mediante asociación con dichos
institutos,   universidades,   empresas  o  instituciones  
especializadas  y proporcionar a sus clientes los resultados
de dicha investigación.
V. Obtener, adquirir, registrar, utilizar o disponer de toda
clase  de  patentes,  marcas  industriales  y  de  servicio,
certificados  de  invención o nombres comerciales, diseños y
dibujos  industriales,  derechos  de  autor y cualquier otro
tipo  de  derechos  de  propiedad  industrial,  literaria  o
artística,  y  derechos sobre ellos ya sea en México o en el
extranjero.
VI.  Obtener  por  cualquier  título, concesiones, permisos,
autorizaciones  o  licencias,  así  como  celebrar cualquier
clase  de  contratos,  con  la  administración  pública  sea
federal o local o con cualquier particular.
VII.  Dar  o  tomar  dinero  en préstamo con o sin garantía,
emitir  bonos,  obligaciones,  valores  y  otros  títulos de
crédito,  así como adquirir legalmente y negociar con bonos,
obligaciones,  acciones,  valores y otros títulos de crédito
emitidos por terceros.
VIII.  Otorgar  avales  y  obligarse solidariamente así como
constituir garantías a favor de terceros.
IX.   Formar   parte   directa  o  indirectamente  de  otras
sociedades  o asociaciones e intervenir en todos los asuntos
y derechos relacionados con ellas.
X. Establecer sucursales, oficinas, subsidiarias y agencias,
así  como representar o ser agente de empresas e intermediar
en la venta de toda clase de bienes y servicios.
XI.  Adquirir  o  poseer  por  cualquier título, usar, dar o
tomar  en  arrendamiento,  administrar, vender o disponer en
cualquier  forma,  de  todos los bienes muebles o inmuebles,
así  como  derechos  reales  o  personales  sobre ellos, que
fueren  necesarios  o  convenientes  para la realización del
objeto de la sociedad.
XII. Contratar al personal necesario.
XIII.  En  general  celebrar toda clase de contratos ya sean
civiles, mercantiles o de cualquier naturaleza.
#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,null,md5(1),'sesion',current_timestamp);

insert into tbcfgm18 values (md5(3),'escuela','clausulas de escuela',0,1,current_timestamp,'


La  promoción,  coordinación e impartición de la educación y
enseñanza  en  general en los grados, niveles y materias que
libremente  escoja,  tales como preescolar, primaria, media,
superior  y  universitaria,  entre otras, pudiendo evaluar y
calificar   los   resultados   alcanzados  por  los  sujetos
receptores  de la educación y enseñanza referida, expidiendo
al  efecto, en su caso, las constancias que acrediten dichos
resultados.
Por  lo  que enunciativa pero no limitativamente la sociedad
podrá:
I.   Promover,   constituir,   organizar,   operar  y  tomar
participación  en el patrimonio, de toda clase de sociedades
civiles,  organismos  o  asociaciones  tanto nacionales como
extranjeras;   que   tengan   como   objeto   preponderante,
actividades  culturales,  educativas  o  cualquier  otra que
resulte similar o afín con su propio objeto social, pudiendo
incluso,   participar  en  su  administración,  operación  y
liquidación;
II.  Adquirir  por  cualquier título de toda clase de bienes
muebles  e  inmuebles y derechos que sean necesarios para la
realización de su objeto social, así como establecer locales
para prestar los servicios de referencia.
III.  Obtener  por  cualquier título, concesiones, permisos,
autorizaciones,   licencias,   reconocimientos   o   -  -  
incorporaciones,   así  como  celebrar  cualquier  clase  de
contratos con la administración pública sea federal o local,
relacionados con su objeto.
IV.  Obtener,  adquirir,  registrar,  utilizar o disponer de
toda  clase  de patentes, marcas industriales y de servicio,
certificados  de  invención o nombres comerciales, diseños y
dibujos  industriales,  derechos  de  autor y cualquier otro
tipo  de  derechos  de  propiedad  industrial,  literaria  o
artística,  y  derechos sobre ellos ya sea en México o en el
extranjero.
V.  Otorgar  avales,  así  como  obligarse  solidariamente y
constituir garantías a favor de terceros.
VI.  Emitir,  girar, endosar, aceptar y suscribir toda clase
de  títulos  de crédito sin que constituyan una especulación
comercial.
VII. Contratar al personal necesario.
VIII.  Celebrar  toda  clase  de  contratos  y  convenios, y
ejecutar  actos  u  operaciones de cualquier naturaleza, así
como  otorgar  y suscribir cualquier clase de documentos que
sean  necesarios  o  convenientes  para la realización de su
objeto,   a   excepción  de  aquellos  que  constituyan  una
especulación comercial.
#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,null,md5(1),'sesion',current_timestamp);

insert into tbcfgm18 values (md5(4),'texto SC','clausulas de SC',0,1,current_timestamp,'


La combinación de los conocimientos, experiencias, esfuerzos
y recursos de los miembros de la sociedad para prestar, toda
clase  de  servicios de consultoría, asesoría y capacitación
en   toda   clase   de  materias,  tales  como  legales,  de
contabilidad,   administrativos,   financieros,  fiscales  e
inmobiliarios.
Por  lo  que enunciativa pero no limitativamente la sociedad
podrá:
I.  Adquirir  por  cualquier  título de toda clase de bienes
muebles  e  inmuebles y derechos que sean necesarios para la
realización   de  su  objeto  social,  así  como  establecer
oficinas para prestar los servicios de referencia.
II.  Obtener  por  cualquier  título, concesiones, permisos,
autorizaciones  o  licencias,  así  como  celebrar cualquier
clase de contratos con la administración pública sea federal
o local, relacionados con su objeto.
III.  Obtener,  adquirir,  registrar, utilizar o disponer de
toda  clase  de patentes, marcas industriales y de servicio,
certificados  de  invención o nombres comerciales, diseños y
dibujos  industriales,  derechos  de  autor y cualquier otro
tipo  de  derechos  de  propiedad  industrial,  literaria  o
artística,  y  derechos sobre ellos ya sea en México o en el
extranjero.
IV.  Otorgar  avales,  así  como  obligarse solidariamente y
constituir garantías a favor de terceros.
V. Emitir, girar, endosar, aceptar y suscribir toda clase de
títulos  de  crédito  sin  que  constituyan una especulación
comercial.
VI. Contratar al personal necesario.
VII.  Celebrar  toda  clase  de  contratos  y  convenios,  y
ejecutar  actos  u  operaciones de cualquier naturaleza, así
como  otorgar  y suscribir cualquier clase de documentos que
sean  necesarios  o  convenientes  para la realización de su
objeto,   a   excepción  de  aquellos  que  constituyan  una
especulación comercial.
#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,null,md5(1),'sesion',current_timestamp);

insert into tbcfgm18 values (md5(5),'transportes','clausulas transportes',0,1,current_timestamp,'


explotación  del Servicio Público de Autotransporte de Carga
General  y  especializada  en  materiales  peligrosos  y sus
residuos  en  todas  sus  modalidades,  en  los  caminos  de
jurisdicción  federal y/o de jurisdicción local, autorizados
mediante  las  concesiones  o permisos que para el efecto le
otorgue  a  la  Sociedad,  la Secretaría de Comunicaciones y
Transportes   y/o   el  Gobierno  Local  correspondiente,  o
mediante   las   concesiones   que  la  sociedad  reciba  en
transferencia en virtud de las concesiones o permisos que en
goce  le  aporten  sus  propios  socios  y que autoricen las
autoridades competentes.
b).-  La  explotación  del  Servicio Público Federal de Auto
Transporte  de  Carga  General,  Especializada  y Residuos y
materiales   peligrosos,   materiales   para   construcción,
materiales  a  granel,  desechos  industriales  y materiales
tóxicos  en  todos  los  caminos  de  Jurisdicción  Federal,
mediante los permisos que expida la Autoridad competente.
c).-  La explotación y operación de los servicios auxiliares
y conexos.
d).-  La  coordinación, enlace, combinaciones intercambio de
equipo  con  otras  personas  físicas o morales dedicadas al
mismo  objeto,  así  como  el  enrolamiento  interno  de los
vehículos  con  que  la empresa realiza la prestación de los
servicios tantos Federales como Locales.
e).-  La  construcción,  reparación  y  conservación  de los
equipos  y bienes necesarios para la mejor realización de su
objeto social.
f).-   La   compra   y   venta   importación,   exportación,
consignación,   comisión   y  representación  de  equipo  de
transporte  en  general,  llantas, accesorios, lubricantes y
todos  aquellos  elementos que sean necesarios para el mejor
desarrollo de sus actividades.
g).-   La   compra,   venta,   exportación,  importación,  
consignación  y comisión, etcétera, de todo tipo de bienes y
productos,   siendo   entre   otros  mobiliario,  enseres  
electrónicos  como  computadoras, copiadoras, reproductoras,
máquinas  sumadoras  y de escribir y toda clase de artículos
para  la  oficina,  el  comercio y la industria; así como la
reparación y mantenimiento de las mismas.
h).-  Obrar  como agente representante, ya sea de personas o
industrias sean mexicanas o extranjeras.
i).-  Tomar  en arrendamiento o subarrendamiento o comprar y
bajo  cualquier título, adquirir los locales necesarios para
el  establecimiento  de  sus  oficinas,  patios  operativos,
fábricas,  almacenes  o  laboratorios  siempre que esos sean
inmuebles  que  se  dediquen  exclusivamente  a  los objetos
sociales;
j).-  Dar  y  tomar  dinero  a título de préstamo y adquirir
acciones  o partes de interés en otras sociedades nacionales
o extranjeras de objeto similar al de esta sociedad, siempre
que  sea  permitido por las leyes, obteniendo en su caso los
permisos previos que se requieran;
K).-  Solicitar, obtener, comprar o adquirir cualesquiera de
todas   las  patentes,  derechos  de  patentes,  propiedades
literarias,  licencias y privilegios, invenciones, mejoras y
procedimientos,  marcas  de  fábrica,  nombres  comerciales,
membretes,  diseños  y  marcas  que  se  refieran y que sean
útiles en conexiones con cualquier negocio de la sociedad.
Desarrollar  y  conceder  licencias respecto a los mismos, o
bien   venderlos   o  cambiarlos  para  comprar  o  adquirir
cualquier tipo de bienes o servicios.
l).-   Comprar,  vender,  traspasar,  hipotecar,  empeñar  y
garantizar   hasta  donde  sea  posible  cualquiera  de  las
acciones   del   capital  social  y/o  cualesquiera  abonos,
obligaciones,   pagarés,   valores   o   cualquier  otros  
comprobantes de adeudos;
m).-  Pedir  préstamos  por  medio  de la venta o emisión de
acciones  o  bonos pagarés u otras obligaciones de cualquier
naturaleza y asegurar los mismos por medio de hipoteca.
n).-  En  general  la  celebración  de  toda clase de actos,
convenios,  comisiones,  mediaciones  y  contratos  civiles,
mercantiles  y  administrativos  que  en la forma más amplia
puedan  realizarse  para  sus  objetos  sociales,  directos,
Bconexos y afines.
ñ).-  Obtener  y  conceder préstamos, otorgando y recibiendo
garantías  específicas, emitir obligaciones, aceptar, girar,
endosar, o avalar toda clase de Títulos de Crédito y otorgar
Fianzas,  Avales  o Garantías de cualquier clase respecto de
las  obligaciones  contraídas  o  de  los Títulos emitidos o
aceptados por terceros.
#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,null,md5(1),'sesion',current_timestamp);

SELECT 'INSERTS PARA DEFINICION DE FUNCIONES: ';
INSERT INTO tbcfgm30 VALUES (MD5(2),'Condicional si','si','a87ff679a2f3e71d9181a67b7542122c','si(condicion,funcion,funcion)','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm30 VALUES (MD5(3),'Activa','activa','a87ff679a2f3e71d9181a67b7542122c','activa(componente,ciertofalso)','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm30 VALUES (MD5(5),'Visible','visible','a87ff679a2f3e71d9181a67b7542122c','visible(componente,ciertofalso)','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm30 VALUES (MD5(7),'Asignar valor','asignar','a87ff679a2f3e71d9181a67b7542122c','asignar(componente,valor)','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);

INSERT INTO tbcfgm31 VALUES (0, MD5(2),'condicion','a87ff679a2f3e71d9181a67b7542122c','1','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm31 VALUES (0, MD5(2),'funcion','a87ff679a2f3e71d9181a67b7542122c','0','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm31 VALUES (0, MD5(2),'funcion','a87ff679a2f3e71d9181a67b7542122c','0','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);

INSERT INTO tbcfgm31 VALUES (0, MD5(3),'componente','a87ff679a2f3e71d9181a67b7542122c','1','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm31 VALUES (0, MD5(3),'ciertofalso','a87ff679a2f3e71d9181a67b7542122c','1','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);

INSERT INTO tbcfgm31 VALUES (0, MD5(5),'componente','a87ff679a2f3e71d9181a67b7542122c','1','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm31 VALUES (0, MD5(5),'ciertofalso','a87ff679a2f3e71d9181a67b7542122c','1','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);

INSERT INTO tbcfgm31 VALUES (0, MD5(7),'componente','a87ff679a2f3e71d9181a67b7542122c','1','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
INSERT INTO tbcfgm31 VALUES (0, MD5(7),'valor','a87ff679a2f3e71d9181a67b7542122c','1','7ABBE1DAEADF9E2CA02A10CBABC6D9AD',CURRENT_TIMESTAMP);
