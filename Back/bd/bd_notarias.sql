-- GRANT INSERT,DELETE,UPDATE,SELECT,ALTER,CREATE,DROP ON notaria.* TO 'cfgnotaria'@'localhost' IDENTIFIED BY 'nPQ14Ri3SAD';
-- FLUSH PRIVILEGES;

DROP DATABASE IF EXISTS notaria;
CREATE DATABASE notaria CHARACTER SET 'utf8';
USE notaria;
-- CLIENTE
-- DROP TABLE IF EXISTS tbcfgm01;
-- CREATE TABLE tbcfgm01 (
--    cdclient VARCHAR(32) NOT NULL,
--    dsnombre VARCHAR(120) NOT NULL,
--    innumnot VARCHAR(3) NOT NULL,
--	dsrfc VARCHAR(13),
--	dslogo VARCHAR(250),
--	cddomicilio VARCHAR(32) NOT NULL,
--	tmstmp TIMESTAMP NOT NULL,
--	idsesion VARCHAR(32) NOT NULL,
 --   PRIMARY KEY(cdclient)
-- )ENGINE=INNODB;

-- DOMICILIO
-- DROP TABLE IF EXISTS tbcfgm02;
-- CREATE TABLE tbcfgm02 (
--    cddomicilio VARCHAR(32) NOT NULL,
--    dscalle VARCHAR(30) NOT NULL,
 --   dsnumext VARCHAR(6) NOT NULL,
--	dsnumint VARCHAR(30),
--	dscolonia VARCHAR(60) NOT NULL,
--	dsciudad VARCHAR(30),
--	dsestado VARCHAR(30) NOT NULL, 
--	dscodpos VARCHAR(5),
--	dstexto VARCHAR(250),
--	tmstmp TIMESTAMP NOT NULL,
--	idsesion VARCHAR(32) NOT NULL,
 --   PRIMARY KEY(cddomicilio)
-- )ENGINE=INNODB;cf

-- USUARIO
DROP TABLE IF EXISTS tbcfgm03;
CREATE TABLE tbcfgm03 (
    idusuario VARCHAR(32) NOT NULL,
    dsnombre VARCHAR(30) NOT NULL,
    dspaterno VARCHAR(30) NOT NULL,
	dsmaterno VARCHAR(30),
	idrol VARCHAR(32) NOT NULL,
	cdusuario VARCHAR(16) NOT NULL,
	dsvalenc VARCHAR(41) NOT NULL,
	dscorreo VARCHAR(120),
	dsrfc VARCHAR(13),
	dscurp VARCHAR(18),
	dsiniciales VARCHAR(5) NOT NULL,
	dsreferencia VARCHAR(250),
	isactualizapwd INT(1) NOT NULL DEFAULT FALSE,
	inestatus INT(1) NOT NULL DEFAULT TRUE,
	isactivo INT(1) NOT NULL DEFAULT TRUE,
	dsultimoacceso TIMESTAMP,
   	tmstmp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	fchinicio TIMESTAMP,
	fchfin TIMESTAMP,
	idsesion VARCHAR(32) NOT NULL,
    PRIMARY KEY(idusuario),
	UNIQUE KEY `cdusuario_UNIQUE` (`cdusuario`)	
)ENGINE=INNODB;


-- PETICIONES DE CONTRASEÑA
DROP TABLE IF EXISTS tbcfgm04;
CREATE TABLE tbcfgm04 (
  idpeticion VARCHAR(32) NOT NULL,
  idusuario VARCHAR(32) NOT NULL,
  dtfecha TIMESTAMP NOT NULL,
  inestatus VARCHAR(1) NOT NULL DEFAULT 'P',
  dtvencimiento TIMESTAMP NOT NULL,
  dsnewpasswd VARCHAR(32) NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY(idpeticion),
  FOREIGN KEY(idusuario)
    REFERENCES tbcfgm03(idusuario)
      ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB;


-- ACTO
-- DROP TABLE IF EXISTS tbcfgm04;
-- CREATE TABLE tbcfgm04 (  
--	idacto VARCHAR(32) NOT NULL,
--	dsnombre VARCHAR(30) NOT NULL,
--	dsdescripcion VARCHAR(60),
--	inestatus INT(1),
--	tmstmp TIMESTAMP NOT NULL,
--	idsesion VARCHAR(32) NOT NULL,
--	PRIMARY KEY(idacto)
-- )ENGINE=INNODB;

-- DOCUMENTO
-- DROP TABLE IF EXISTS tbcfgm05;
-- CREATE TABLE tbcfgm05 (
--	iddocumento VARCHAR(32) NOT NULL,
--	dstitulo VARCHAR(30) NOT NULL,
--	dsdescripcion VARCHAR(250),
--	dsversion VARCHAR(9) NOT NULL,
--	ispublicado INT(1) NOT NULL,
--	tmstmp TIMESTAMP NOT NULL,
--	idsesion VARCHAR(32) NOT NULL,
--	isrequerido INT(1) NOT NULL,
--	entipodoc ENUM('PR','PS') NOT NULL,
--	idacto VARCHAR(32) NOT NULL,
--	txplantilla LONGTEXT,
--	PRIMARY KEY(iddocumento)
-- )ENGINE=INNODB;

-- DOCUMENTO NOTARIAL
-- DROP TABLE IF EXISTS tbcfgm06;
-- CREATE TABLE tbcfgm06 (
--	iddocnot VARCHAR(32) NOT NULL,
--	dstitulo VARCHAR(30) NOT NULL,
--	dsversion VARCHAR(9) NOT NULL,
--	ispublicado INT(1) NOT NULL,
--	txplantilla LONGTEXT,
--	tmstmp TIMESTAMP NOT NULL,
--	idsesion VARCHAR(32) NOT NULL,
--	PRIMARY KEY(iddocnot)
-- )ENGINE=INNODB;

-- ROL
DROP TABLE IF EXISTS tbcfgm07;
CREATE TABLE tbcfgm07 (
	idrol VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(30) NOT NULL,
	dsdescripcion VARCHAR(60),
	dsprefijo VARCHAR(5),
	tmstmp TIMESTAMP NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	PRIMARY KEY(idrol)
)ENGINE=INNODB;	

-- PERMISOS ROL 
DROP TABLE IF EXISTS tbcfgmcr;
CREATE TABLE tbcfgmcr (
  idgescom VARCHAR(32) NOT NULL,
  idrol VARCHAR(32) NOT NULL,
  dsscreen VARCHAR(90) NOT NULL DEFAULT '/*',
  inmodrol INT(11) NOT NULL DEFAULT 0,
  tmstmp TIMESTAMP NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  PRIMARY KEY(idgescom)
)ENGINE=INNODB;	

ALTER TABLE tbcfgmcr ADD FOREIGN KEY(idrol) REFERENCES tbcfgm07(idrol) ON DELETE CASCADE ON UPDATE CASCADE;

-- VARIABLE
DROP TABLE IF EXISTS tbcfgm08;
CREATE TABLE tbcfgm08 (
  idvariable VARCHAR(32) NOT NULL,
  intipvar CHAR(3) NOT NULL DEFAULT 'VAR',
  dsdescripcion varchar(250) DEFAULT NULL,
  dsnombre varchar(250) NOT NULL,
  isnulo INT(1),
  intipodato VARCHAR(32) NOT NULL,
  isselmultiple INT(1),
  ispermitevalor INT(1),
  cdlongitud INT(3),
  dsfiltrado VARCHAR(25),
  isactivo INT(1) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  PRIMARY KEY (idvariable),
  UNIQUE KEY (dsnombre)
) ENGINE=InnoDB;

-- ALTER TABLE tbcfgm08 ADD COLUMN intipvar CHAR(3) NOT NULL DEFAULT 'VAR' AFTER idvariable;
-- UPDATE tbcfgm08 SET intipvar = 'var';

-- VALIDACION
DROP TABLE IF EXISTS tbcfgm09;
CREATE TABLE tbcfgm09 (
  idrestriccion VARCHAR(32) NOT NULL,
  idoperador varchar(32) DEFAULT NULL,
  dsexpresion varchar(60) DEFAULT NULL,
  tmstmp TIMESTAMP NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  idvariable VARCHAR(32) NOT NULL,
  PRIMARY KEY (idrestriccion)
) ENGINE=InnoDB;

-- GRUPO
DROP TABLE IF EXISTS tbcfgm10;
CREATE TABLE tbcfgm10 (
  idgrupo VARCHAR(32) NOT NULL,
  dsgrupo varchar(30) NOT NULL,
  idsdescripcion varchar(250) DEFAULT NULL,
  isactivo INT(1) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  PRIMARY KEY(idgrupo)
)ENGINE=INNODB;	

-- VARIABLE GRUPO
DROP TABLE IF EXISTS tbcfgm11;
CREATE TABLE tbcfgm11 (
  idgpovars VARCHAR(32) NOT NULL,
  idgrupo VARCHAR(32) NOT NULL,
  idvariable VARCHAR(32) NOT NULL,
  dsorden INT(2),
  PRIMARY KEY(idgpovars),
  FOREIGN KEY(idvariable)
	REFERENCES tbcfgm08(idvariable),
  FOREIGN KEY(idgrupo)
	REFERENCES tbcfgm10(idgrupo)
)ENGINE=INNODB;	

 -- EXPEDIENTE
 -- DROP TABLE IF EXISTS tbcfgm12;
-- CREATE TABLE tbcfgm12 (
--  idexpediente VARCHAR(32) NOT NULL,
--  tmstmp TIMESTAMP NOT NULL,
--  idsesion VARCHAR(32) NOT NULL,
--  PRIMARY KEY(idexpediente)
 -- )ENGINE=INNODB;  
 
 -- VALORES DOCUMENTOS se va
--   DROP TABLE IF EXISTS tbcfgm13;
-- CREATE TABLE tbcfgm13 (
--  idvaldoc VARCHAR(32) NOT NULL,
--  dscampo VARCHAR(60) NOT NULL,
--  dsvalor VARCHAR(60) NOT NULL,
--  idexpdoc VARCHAR(32) NOT NULL,
--  tmstmp TIMESTAMP NOT NULL,
--  idsesion VARCHAR(32) NOT NULL,
--  PRIMARY KEY(idvaldoc)
-- )ENGINE=INNODB; 
 
 -- EXPEDIENTE DOCUMENTO
 --   DROP TABLE IF EXISTS tbcfgm14;
-- CREATE TABLE tbcfgm14(
--  idexpdoc VARCHAR(32) NOT NULL,
--  idexpediente VARCHAR(32) NOT NULL,
--  iddocnot VARCHAR(32) NOT NULL,
--  PRIMARY KEY(idexpdoc),
--  FOREIGN KEY(idexpediente)
--	REFERENCES tbcfgm12(idexpediente),
--  FOREIGN KEY(iddocnot)
--	REFERENCES tbcfgm06(iddocnot)
 -- )ENGINE=INNODB; 
 
 -- ACTOS DOCUMENTO se va
--     DROP TABLE IF EXISTS tbcfgm15;
-- CREATE TABLE tbcfgm15(
--  idactodoc VARCHAR(32) NOT NULL,
--  iddocnot VARCHAR(32) NOT NULL,
--  idacto VARCHAR(32) NOT NULL,
--  PRIMARY KEY (idactodoc),
--  FOREIGN KEY(iddocnot)
--	REFERENCES tbcfgm06(iddocnot),
--  FOREIGN KEY(idacto)
--	REFERENCES tbcfgm04(idacto)
 -- )ENGINE=INNODB;   


--GRUPO DE TRABAJO
DROP TABLE IF EXISTS tbcfgm12
CREATE TABLE tbcfgm12(
	idgrupotrabajo VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(120) NOT NULL,
	dsdescripcion VARCHAR(250) DEFAULT NULL,
	idresponsable VARCHAR(32) NOT NULL,
	bstatus INT(1) DEFAULT 0,
	codigo VARCHAR(5) DEFAULT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY (idgrupotrabajo),
	FOREIGN KEY (idresponsable) REFERENCES tbcfgm03(idusuario)
)ENGINE=INNODB;

-- USUARIO GRUPOTRABAJO

DROP TABLE IF EXISTS tbcfgm13

CREATE TABLE tbcfgm13(

 idusuariogrupotrabajo VARCHAR(32) NOT NULL,
 idgrupotrabajo VARCHAR(32) NOT NULL,
 idusuario VARCHAR(32) NOT NULL,
 idsesion VARCHAR(32) NOT NULL,
 tmstmp TIMESTAMP NOT NULL,
 PRIMARY KEY (idusuariogrupotrabajo),
 FOREIGN KEY (idgrupotrabajo) REFERENCES tbcfgm12(idgrupotrabajo),
 FOREIGN KEY (idusuario) REFERENCES tbcfgm03(idusuario)
)ENGINE = INNODB;


-- UBICACION VAR ESTATICA
DROP TABLE IF EXISTS tbcfgm16;
CREATE TABLE tbcfgm16(
	idvarest VARCHAR(32) NOT NULL,
	dsentidad VARCHAR(120),
	dspropiedad VARCHAR(250),
	idvariable VARCHAR(32) NOT NULL,
	dsfiltro VARCHAR(250),
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY (idvarest),
	FOREIGN KEY(idvariable)
		REFERENCES tbcfgm08(idvariable)
	)ENGINE=INNODB;   

-- PROCESOS ROL
DROP TABLE IF EXISTS tbcfgm17;
CREATE TABLE tbcfgm17(
	identificador INT(5) NOT NULL,
	idrol VARCHAR(32) NOT NULL,
	idproceso VARCHAR(32) NOT NULL,
	PRIMARY KEY(idrol,idproceso)
)ENGINE=INNODB;

-- DOCUMENTO OBJETO
DROP TABLE IF EXISTS tbcfgm18;
CREATE TABLE tbcfgm18(
	iddocobjeto VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(30) NOT NULL,
	dsdescripcion VARCHAR(90),
	version INT(5),
	ispublicada INT(1),
	fchpublicacion TIMESTAMP NOT NULL,
	dscontenido LONGTEXT,
	isactivo INT(1) NOT NULL,
	idusuariopublico VARCHAR(32) DEFAULT NULL,
	idusuariocreomodifico VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	KEY version_key(version),
	KEY iddocobjeto_key(iddocobjeto),
	PRIMARY KEY(iddocobjeto,version)
)ENGINE=INNODB;

-- EDITOR TEXTO
DROP TABLE IF EXISTS tbcfgm19;
CREATE TABLE tbcfgm19(
	ideditor VARCHAR(32) NOT NULL,
	dstitulo VARCHAR(30) NOT NULL,
	txtexto LONGTEXT,
	isactivo INT(1) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY (ideditor)
)ENGINE=INNODB;
	
-- MANEJO DE SESION
DROP TABLE IF EXISTS tbcfgm89;
CREATE TABLE tbcfgm89(
	id INT(11) NOT NULL AUTO_INCREMENT,
	idsesion VARCHAR(32) NOT NULL,
	idusuario VARCHAR(32) NOT NULL,
	fchinicio TIMESTAMP NOT NULL,
	fchfinprogr TIMESTAMP NOT NULL,
	fchtermino TIMESTAMP NOT NULL,
	inip VARCHAR(15),
	islogout INT(1) NOT NULL,
	agente LONGTEXT,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(id)
)ENGINE=INNODB;

-- CATALOGO
DROP TABLE IF EXISTS tbcfgm90;
CREATE TABLE tbcfgm90 (
  idcatalogo VARCHAR(32) NOT NULL,
  dsnombre VARCHAR(60) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  PRIMARY KEY(idcatalogo)
 )ENGINE=INNODB;
 
 -- ELEMENTO CATALOGO
 DROP TABLE IF EXISTS tbcfgm91;
CREATE TABLE tbcfgm91 (
idelemento VARCHAR(32) NOT NULL,
idcatalogo VARCHAR(32) NOT NULL,
dselemento VARCHAR(120) NOT NULL,
dscodigo VARCHAR(10),
iseliminado INT(1) DEFAULT FALSE,
idsesion VARCHAR(32) NOT NULL,
tmstmp TIMESTAMP NOT NULL,
 PRIMARY KEY(idelemento),
 FOREIGN KEY(idcatalogo)
	REFERENCES tbcfgm90(idcatalogo)
 )ENGINE=INNODB;

 -- FORMATO PDF
DROP TABLE IF EXISTS tbcfgm20;
CREATE TABLE tbcfgm20 (
  identificador VARCHAR(32) NOT NULL,
  dstitulo VARCHAR(90) NOT NULL,
  idonline int(1) DEFAULT 0;
  dsruta VARCHAR(255) DEFAULT NULL,
  idtipodoc VARCHAR(32) NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  dsdescripcion VARCHAR(255) NOT NULL,
  isgestionado VARCHAR(1) DEFAULT NULL,
  PRIMARY KEY(identificador)
)ENGINE=INNODB;

-- FORMATO PDF DETALLE
DROP TABLE IF EXISTS tbcfgm21;
CREATE TABLE tbcfgm21 (
  identificador INT(11) AUTO_INCREMENT,
  idftopdf VARCHAR(32) NOT NULL,
  dscampo VARCHAR(90) NOT NULL,
  dsvariable VARCHAR(90) NOT NULL,
  idsuboperacion VARCHAR(32),
  aplicatodassuboperaciones INT(1),
  PRIMARY KEY(identificador),
  FOREIGN KEY(idftopdf)
      REFERENCES tbcfgm20(identificador)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)ENGINE=INNODB;

-- EXPRESION
DROP TABLE IF EXISTS tbcfgm22;
CREATE TABLE tbcfgm22(
	idexpresion VARCHAR(32) NOT NULL,
	idvariable VARCHAR(32) NOT NULL,
	dsexpresion TEXT,
	ifnulo VARCHAR(90),
	isvalido INT(1),
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idexpresion)
)ENGINE=INNODB;

-- FUNCION
DROP TABLE IF EXISTS tbcfgm31;
DROP TABLE IF EXISTS tbcfgm30;
CREATE TABLE tbcfgm30 (
  identificador VARCHAR(32) NOT NULL,
  dsdescripcion VARCHAR(250) NULL,
  dsnombre VARCHAR(60) NOT NULL,
  idretorno VARCHAR(32) NOT NULL,
  dsforma VARCHAR(32) NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY(identificador)  
)ENGINE=INNODB;

-- FUNCION_PARAMETRO
CREATE TABLE tbcfgm31 (
  cdfuncion INT AUTO_INCREMENT NOT NULL,
  idfuncion VARCHAR(32) NOT NULL,
  dsparam VARCHAR(30) NULL,
  idtipo VARCHAR(32) NOT NULL,
  isrequerido INT NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY(cdfuncion),
  FOREIGN KEY(idfuncion)
    REFERENCES tbcfgm30(identificador)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)ENGINE=INNODB;

-- VARIABLES COMPONENTE Y SIMPLES
CREATE TABLE tbcfgm32(
	idvarstipo VARCHAR(32) NOT NULL,
	idvariable VARCHAR(32),
	idcomponente VARCHAR(32),
	PRIMARY KEY(idvarstipo)
)ENGINE=INNODB;

ALTER TABLE tbcfgm30 ADD FOREIGN KEY (idretorno) REFERENCES tbcfgm91(idelemento);
ALTER TABLE tbcfgm31 ADD FOREIGN KEY (idtipo) REFERENCES tbcfgm91(idelemento);
-- ALTER TABLE tbcfgm31 ADD FOREIGN KEY (idfuncion) REFERENCES tbcfgm30(identificador);

 -- ALTER TABLE tbcfgm01 ADD FOREIGN KEY (cddomicilio) REFERENCES tbcfgm02(cddomicilio);
 ALTER TABLE tbcfgm03 ADD FOREIGN KEY (idrol) REFERENCES tbcfgm07(idrol);
 -- ALTER TABLE tbcfgm05 ADD FOREIGN KEY (idacto) REFERENCES tbcfgm04(idacto);
 ALTER TABLE tbcfgm08 ADD FOREIGN KEY (intipodato) REFERENCES tbcfgm91(idelemento);
 ALTER TABLE tbcfgm09 ADD FOREIGN KEY (idvariable) REFERENCES tbcfgm08(idvariable),
		ADD FOREIGN KEY (idoperador) REFERENCES tbcfgm91(idelemento);
-- ALTER TABLE tbcfgm13 ADD FOREIGN KEY (idexpdoc) REFERENCES tbcfgm14 (idexpdoc);
ALTER TABLE tbcfgm17 ADD FOREIGN KEY(idrol) REFERENCES tbcfgm07(idrol),
		ADD FOREIGN KEY (idproceso) REFERENCES tbcfgm91(idelemento);
ALTER TABLE tbcfgm18 ADD FOREIGN KEY(idusuariopublico) REFERENCES tbcfgm03(idusuario),
		ADD FOREIGN KEY (idusuariocreomodifico) REFERENCES tbcfgm03(idusuario);
ALTER TABLE tbcfgm20 ADD FOREIGN KEY(idtipodoc) REFERENCES tbcfgm91(idelemento);
ALTER TABLE tbcfgm22 ADD FOREIGN KEY(idvariable) REFERENCES tbcfgm32(idvarstipo);

		
