use notaria;

/* @VICOY

REVISAR EL INTIPVAR NO EXISTE Y SETEA POR DEFAULT XXX NO ESTAN ACTUALIZADOS ESTOS INSERTS POR ESO SE COMENTAN
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion, dsfiltrado) 
VALUES (md5(1), 'numero_libro', 'numero_libro', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion', 'idescritura');                        
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(2), 'fecha_hoy', 'fecha_hoy', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion',null);
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(3), 'notaria_notario_documento', 'notaria_notario_documento', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(4), 'notaria_numero', 'notaria_numero', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(5), 'notaria_estado', 'notaria_estado', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(6), 'notaria_asociada_numero', 'notaria_asociada_numero', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(7), 'notaria_asociada_notario', 'notaria_asociada_notario', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(9), 'numero_escritura', 'numero_escritura', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(10), 'fecha_escritura', 'fecha_escritura', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(11), 'escritura_folio', 'escritura_folio', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(12), 'inmueble_foja', 'inmueble_foja', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(13), 'inmueble_precio', 'inmueble_precio', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(14), 'inmueble_domicilio', 'inmueble_domicilio', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(16), 'fecha_firma_escritura', 'fecha_firma_escritura', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idescritura');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(27), 'tramite_operacion', 'tramite_operacion', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5('escritura_elaboracion'), 'escritura_fecha_elaboracion', 'escritura_fecha_elaboracion', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5('actos'), 'actos', 'actos', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5('mismo_firma'), 'mismo_firma', 'mismo_firma', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5('escritura_notario'), 'escritura_notario', 'escritura_notario', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(''), 'escritura_notaria', 'escritura_notaria', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
	*/		
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(17),'var','escritura_notaria', 'escritura_notaria', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(17),'var','escritura_notario', 'escritura_notario', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "variable"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
			
					
					


DELETE FROM tbcfgm08 WHERE idvariable = MD5(17); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(18); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(19); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(20); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(21); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(22); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(23); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(24); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(25); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(26); 
-- DELETE FROM tbcfgm08 WHERE idvariable = MD5(27); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(28); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(29); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(30); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(31); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(32); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(33); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(34); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(35); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(36); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(37); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(38); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(39);
DELETE FROM tbcfgm08 WHERE idvariable = MD5(40); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(41); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(42); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(43); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(44); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(45); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(46);



DELETE FROM tbcfgm08 WHERE idvariable = MD5(47); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(48); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(49);
DELETE FROM tbcfgm08 WHERE idvariable = MD5(50); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(51); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(52); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(53); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(54); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(55); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(56);
DELETE FROM tbcfgm08 WHERE idvariable = MD5(57); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(58); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(59);
DELETE FROM tbcfgm08 WHERE idvariable = MD5(60); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(61); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(62); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(63); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(64); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(65); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(66);
DELETE FROM tbcfgm08 WHERE idvariable = MD5(67); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(68); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(69);
DELETE FROM tbcfgm08 WHERE idvariable = MD5(70); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(71); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(72); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(73); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(74); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(75); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(76);
DELETE FROM tbcfgm08 WHERE idvariable = MD5(77); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(78); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(79);
DELETE FROM tbcfgm08 WHERE idvariable = MD5(80); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(81); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(82); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(83); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(84); 
DELETE FROM tbcfgm08 WHERE idvariable = MD5(85); 





INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(17),'cte','comprador[representante]', 'comprador[representante]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(18),'cte', 'comprador[domicilio]', 'comprador[domicilio]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(19),'cte', 'comprador[tratamiento]', 'comprador[tratamiento]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(20),'cte', 'comprador[rfc]', 'comprador[rfc]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(21),'cte', 'comprador[curp]', 'comprador[curp]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(22),'cte', 'comprador[calculaGenero]', 'comprador[calculaGenero]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(23),'cte', 'comprador[nombreCompleto]', 'comprador[nombreCompleto]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(24),'cte', 'comprador[nombreCompletoConTratamiento]', 'comprador[nombreCompletoConTratamiento]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(25),'cte', 'comprador[nacionalidadConGenero]', 'comprador[nacionalidadConGenero]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(26),'cte', 'comprador[esRepresentada]', 'comprador[esRepresentada]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(28),'cte', 'comprador[esRepresentadaTexto]', 'comprador[esRepresentadaTexto]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');

INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(29),'cte', 'comprador[originarioConGenero]', 'comprador[originarioConGenero]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(30),'cte', 'comprador[nacimientoEstado]', 'comprador[nacimientoEstado]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(31),'cte', 'comprador[fechaNacimiento]', 'comprador[fechaNacimiento]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(32),'cte', 'comprador[estadoCivilConGenero]', 'comprador[estadoCivilConGenero]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(33),'cte', 'comprador[nombreCompletoConEstadoCivil]', 'comprador[nombreCompletoConEstadoCivil]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(34),'cte', 'comprador[comproProIndiviso]', 'comprador[comproProIndiviso]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(35),'cte', 'comprador[compraronEstadoCivil]', 'comprador[compraronEstadoCivil]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(36),'cte', 'comprador[empleo]', 'comprador[empleo]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(37), 'cte','comprador[calculaRFCRepresentada]', 'comprador[calculaRFCRepresentada]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(38),'cte', 'comprador[datosIdentificacion]', 'comprador[datosIdentificacion]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(39), 'cte','comprador[unRepresentante]', 'comprador[unRepresentante]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
	

INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(40), 'cte','comprador[nombre]', 'comprador[nombre]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(41), 'cte','comprador[apellidoPaterno]', 'comprador[apellidoPaterno]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(42), 'cte','comprador[apellidoMaterno]', 'comprador[apellidoMaterno]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(43),'cte','vendedor[representante]', 'vendedor[representante]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(44),'cte', 'vendedor[domicilio]', 'vendedor[domicilio]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado) 
VALUES (md5(45),'cte', 'vendedor[tratamiento]', 'vendedor[tratamiento]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(46),'cte', 'vendedor[rfc]', 'vendedor[rfc]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');

INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(47),'cte', 'vendedor[curp]', 'vendedor[curp]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(48),'cte', 'vendedor[calculaGenero]', 'vendedor[calculaGenero]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(49),'cte', 'vendedor[nombreCompleto]', 'vendedor[nombreCompleto]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(50),'cte', 'vendedor[nombreCompletoConTratamiento]', 'vendedor[nombreCompletoConTratamiento]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(51),'cte', 'vendedor[nacionalidadConGenero]', 'vendedor[nacionalidadConGenero]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(52),'cte', 'vendedor[esRepresentada]', 'vendedor[esRepresentada]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(53),'cte', 'vendedor[esRepresentadaTexto]', 'vendedor[esRepresentadaTexto]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(54),'cte', 'vendedor[originarioConGenero]', 'vendedor[originarioConGenero]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(55),'cte', 'vendedor[nacimientoEstado]', 'vendedor[nacimientoEstado]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');

INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(56),'cte', 'vendedor[fechaNacimiento]', 'vendedor[fechaNacimiento]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(57),'cte', 'vendedor[estadoCivilConGenero]', 'vendedor[estadoCivilConGenero]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(58),'cte', 'vendedor[nombreCompletoConEstadoCivil]', 'vendedor[nombreCompletoConEstadoCivil]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(59),'cte', 'vendedor[comproProIndiviso]', 'vendedor[comproProIndiviso]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(60),'cte', 'vendedor[compraronEstadoCivil]', 'vendedor[compraronEstadoCivil]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(61),'cte', 'vendedor[empleo]', 'vendedor[empleo]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(62), 'cte','vendedor[calculaRFCRepresentada]', 'vendedor[calculaRFCRepresentada]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar, dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(63),'cte', 'vendedor[datosIdentificacion]', 'vendedor[datosIdentificacion]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(64), 'cte','vendedor[unRepresentante]', 'vendedor[unRepresentante]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)

VALUES (md5(65), 'cte','vendedor[nombre]', 'vendedor[nombre]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(66), 'cte','vendedor[apellidoPaterno]', 'vendedor[apellidoPaterno]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(67), 'cte','vendedor[apellidoMaterno]', 'vendedor[apellidoMaterno]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(68), 'cte','comprador[domicilioCalle]', 'comprador[domicilioCalle]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(69), 'cte','comprador[domicilioNumeroExterior]', 'comprador[domicilioNumeroExterior]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(70), 'cte','comprador[domicilioNumeroInterior]', 'comprador[domicilioNumeroInterior]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(71), 'cte','comprador[domicilioColonia]', 'comprador[domicilioColonia]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');

INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(72), 'cte','comprador[domicilioMunicipio]', 'comprador[domicilioMunicipio]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(73), 'cte','comprador[domicilioCodigoPostal]', 'comprador[domicilioCodigoPostal]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(74), 'cte','comprador[domicilioEntidadFederativa]', 'comprador[domicilioEntidadFederativa]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(75), 'cte','comprador[contactoTelefono]', 'comprador[contactoTelefono]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(76), 'cte','comprador[contactoCorreoElectronico]', 'comprador[contactoCorreoElectronico]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(77), 'cte','vendedor[domicilioCalle]', 'vendedor[domicilioCalle]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(78), 'cte','vendedor[domicilioNumeroExterior]', 'vendedor[domicilioNumeroExterior]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(79), 'cte','vendedor[domicilioNumeroInterior]', 'vendedor[domicilioNumeroInterior]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(80), 'cte','vendedor[domicilioColonia]', 'vendedor[domicilioColonia]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(81), 'cte','vendedor[domicilioMunicipio]', 'vendedor[domicilioMunicipio]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');

INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(82), 'cte','vendedor[domicilioCodigoPostal]', 'vendedor[domicilioCodigoPostal]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(83), 'cte','vendedor[domicilioEntidadFederativa]', 'vendedor[domicilioEntidadFederativa]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(84), 'cte','vendedor[contactoTelefono]', 'vendedor[contactoTelefono]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');
INSERT INTO tbcfgm08 (idvariable,intipvar,dsdescripcion, dsnombre, isnulo, intipodato, isselmultiple, ispermitevalor, cdlongitud, isactivo, tmstmp, idsesion,dsfiltrado)
VALUES (md5(85), 'cte','vendedor[contactoCorreoElectronico]', 'vendedor[contactoCorreoElectronico]', 0, (select idelemento from tbcfgm91 where idcatalogo = (select idcatalogo from tbcfgm90 where dsnombre = "tipo_dato") and dselemento = "compareciente"), 0, 0, 20, 1, CURRENT_TIMESTAMP, 'sesion','idacto');





















































INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(1), 'Escritura', 'libro.innumlibro', (select idvariable from tbcfgm08 where dsnombre = "numero_libro"), 'idescritura', 'session', CURRENT_TIMESTAMP);
INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(2), 'Escritura', 'dsnumescritura', (select idvariable from tbcfgm08 where dsnombre = "numero_escritura"), 'idescritura', 'session', CURRENT_TIMESTAMP);
INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(3), 'Escritura', 'fechacreacion', (select idvariable from tbcfgm08 where dsnombre = "fecha_escritura"), 'idescritura', 'session', CURRENT_TIMESTAMP);
INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(4), 'Escritura', 'folioini', (select idvariable from tbcfgm08 where dsnombre = "escritura_folio"), 'idescritura', 'session', CURRENT_TIMESTAMP);
INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(5),'Inmueble', 'dsfoja', (select idvariable from tbcfgm08 where dsnombre = "inmueble_foja"), 'acto.idacto', 'session', CURRENT_TIMESTAMP);
INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(6), 'Inmueble', 'precio', (select idvariable from tbcfgm08 where dsnombre = "inmueble_precio"), 'acto.idacto', 'session', CURRENT_TIMESTAMP);
INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(7), 'Inmueble', 'dsdomcompleto',(select idvariable from tbcfgm08 where dsnombre = "inmueble_domicilio") , 'acto.idacto', 'session', CURRENT_TIMESTAMP);
INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(8), 'Escritura', 'fechafirma',(select idvariable from tbcfgm08 where dsnombre = "fecha_firma_escritura") , 'idescritura', 'session', CURRENT_TIMESTAMP);
INSERT INTO tbcfgm16 (idvarest, dsentidad, dspropiedad, idvariable, dsfiltro, idsesion, tmstmp) VALUES (md5(9), 'Acto', 'suboperacion.operacion.dsnombre',(select idvariable from tbcfgm08 where dsnombre = "tramite_operacion") , 'idacto', 'session', CURRENT_TIMESTAMP);