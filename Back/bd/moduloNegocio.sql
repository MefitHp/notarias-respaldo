USE notaria;

-- COMENTARIO
DROP TABLE IF EXISTS tbbsnm01;
CREATE TABLE tbbsnm01 (  
	idcomentario VARCHAR(32) NOT NULL,
	dstexto VARCHAR(120),
	idusuario VARCHAR(32),
	idobjeto VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idcomentario)
)ENGINE=INNODB;

-- TAREA PENDIENTE
DROP TABLE IF EXISTS tbbsnm02;
CREATE TABLE tbbsnm02 (
	idtareapend VARCHAR(32) NOT NULL,
	idtramite VARCHAR(32) DEFAULT NULL,
	idexpediente VARCHAR(32) DEFAULT NULL,
	ismanual INT(1),
	dsdescripcion VARCHAR(60),
	idprioridad VARCHAR(32),
	idusuarioasigna VARCHAR(32) NOT NULL,
	idusuariorecibe VARCHAR(32) NOT NULL,
	inprioritaria INT(1),
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idtareapend)
)ENGINE=INNODB;

-- BITACORA FIRMA
DROP TABLE IF EXISTS tbbsnm03;
CREATE TABLE tbbsnm03 (
	idbitfirma VARCHAR(32) NOT NULL,
	idescritura VARCHAR(32) NOT NULL,
	idusuariofirma VARCHAR(32) NOT NULL,
    	idcompareciente VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idbitfirma)
)ENGINE=INNODB;

-- DOCUMENTOS ORIGINALES
--DROP TABLE IF EXISTS tbbsnm04;
--CREATE TABLE tbbsnm04 (
--	iddocor VARCHAR(32) NOT NULL,
--	txdescripcion LONGTEXT,
--	idexpediente VARCHAR(32) NOT NULL,
--	dscomentario VARCHAR(250) DEFAULT NULL,
--	iscliente int(1) DEFAULT NULL,
--	dsrutaentrega VARCHAR(600) DEFAULT NULL,
--	dsrutarecibo VARCHAR(600),
--	dsentrega VARCHAR(250),
--	fechaRecivo date,
--	fechaEntrega date,
--	dsnombrecliente VARCHAR(250),
--	idsesion VARCHAR(32) NOT NULL,
--	tmstmp TIMESTAMP NOT NULL,
--	PRIMARY KEY(iddocor)
--)ENGINE=INNODB;

DROP TABLE IF EXISTS tbbsnm04;
CREATE TABLE tbbsnm04 (
	id VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(600) DEFAULT NULL,
	dsruta VARCHAR(600) DEFAULT NULL,
	isentregado int(1) DEFAULT NULL,
	isvalidado int(1) DEFAULT NULL,
	idacto VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(iddocor)
)ENGINE=INNODB;



-- DOCUMENTO ESCANEADO
DROP TABLE IF EXISTS tbbsnm05;
CREATE TABLE tbbsnm05 (	
	iddocescaneado VARCHAR(32) NOT NULL,
	dsruta VARCHAR(600),
	dsdescripcion VARCHAR(250),
	idexpediente VARCHAR(32) NOT NULL,
	fecha date,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(iddocescaneado)
)ENGINE=INNODB;

-- TAREA ATENDIDA
DROP TABLE IF EXISTS tbbsnm06;
CREATE TABLE tbbsnm06 (	
	idtareaaten VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(30) NOT NULL,
	dsdescripcion VARCHAR(60),
	dscomentario VARCHAR(120),
	idexpediente VARCHAR(32) NOT NULL,
	idusuarioasigna VARCHAR(32) NOT NULL,
	idusuarioatiende VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idtareaaten)
)ENGINE=INNODB;

-- BITACORA VERSIONES ESCRITURA
DROP TABLE IF EXISTS tbbsnm07;
CREATE TABLE tbbsnm07 (	
	idbitversesc VARCHAR(32) NOT NULL,
	dsruta VARCHAR(600) NOT NULL,
	dsnombre VARCHAR(120) NOT NULL,
	idescritura VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	dsversion VARCHAR(10) NOT NULL,
	idnodoalfresco VARCHAR(120),
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idbitversesc)
)ENGINE=INNODB;

-- PLANTILLA DOCUMENTO NOTARIAL
DROP TABLE IF EXISTS tbbsnm08;
CREATE TABLE tbbsnm08 (	
	iddocnot VARCHAR(32) NOT NULL,
--	idsuboperacion VARCHAR(32) NOT NULL,
	dstitulo VARCHAR(30) NOT NULL,
	inversion INT(5),
	idlocacion VARCHAR(32) NULL,
	dssesion VARCHAR(32), 
	ispublicado INT(1) NOT NULL,
	txplantilla LONGTEXT,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(iddocnot,inversion)
)ENGINE=INNODB;

-- ALTER TABLE tbbsnm08 DROP FOREIGN KEY tbbsnm08_ibfk_1;
-- ALTER TABLE tbbsnm08 DROP COLUMN idsuboperacion;

-- PLANTILLA DOCUMENTO NOTARIAL-SUBOPERACIONES ASOCIADAS
DROP TABLE IF EXISTS tbbsnm08a;
CREATE TABLE tbbsnm08a (
	idacdono VARCHAR(32) NOT NULL,
    iddocnot VARCHAR(32) NOT NULL,
    inversion INT(5) NOT NULL,
    idsuboperacion VARCHAR(32) NOT NULL,
    idsesion VARCHAR(32) NOT NULL,
    tmstmp TIMESTAMP NOT NULL,
    PRIMARY KEY(idacdono)
) ENGINE=INNODB;

ALTER TABLE tbbsnm08a ADD FOREIGN KEY(iddocnot,inversion) REFERENCES tbbsnm08(iddocnot,inversion) ON UPDATE CASCADE ON DELETE CASCADE;


DROP TABLE IF EXISTS tbbsnm08b;
CREATE TABLE tbbsnm08b (
	iddocnot VARCHAR(32) NOT NULL,
	inversion INT(5) NOT NULL,
	inorden INT(5) NOT NULL,
	inposicion INT (5) NOT NULL,
	dsvaltxt TEXT NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(iddocnot, inversion)
) ENGINE INNODB;

ALTER TABLE tbbsnm08b ADD FOREIGN KEY(iddocnot,inversion) REFERENCES tbbsnm08(iddocnot, inversion) ON DELETE CASCADE;


-- INMUEBLE
DROP TABLE IF EXISTS tbbsnm09;
CREATE TABLE tbbsnm09 (	
	idinmueble VARCHAR(32) NOT NULL,
	iddomicilio VARCHAR(32) NOT NULL,
	idacto VARCHAR(32) NOT NULL,
	idvocterreno VARCHAR(32) DEFAULT NULL,
	idvochabitacional VARCHAR(32) DEFAULT NULL,
	idvoccomercial VARCHAR(32) DEFAULT NULL,
	fchinscripcion DATE NOT NULL,
	dsdescripcion VARCHAR(60),
	cdcatastral VARCHAR(60),
	valcatastral double,
	dsctaagua VARCHAR(10),
	dspredio VARCHAR(60),
	dssuperficie VARCHAR(30),
	dscolindancias LONGTEXT,
	ininscripcion INT(3),
	dstomo VARCHAR(10),
	dsseccion VARCHAR(10),
	dsfoja VARCHAR(10),
	dsvolumen VARCHAR(10),
	dslibro VARCHAR(10),
    dsdomcompleto LONGTEXT,
    isasistido int(1),
    precio double,
    gravamen double,
    avaluos double,
    otrosavaluos TEXT,	
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idinmueble)
)ENGINE=INNODB;

-- TARJETA AMARILLA
DROP TABLE IF EXISTS tbbsnm10;
CREATE TABLE tbbsnm10 (	
	idtrjamarilla VARCHAR(32) NOT NULL,
	idusuarioelab VARCHAR(32) NOT NULL,
    idacto VARCHAR(32) NOT NULL,
	idpersona VARCHAR(32),
	isr double,
	numesc VARCHAR(32),
	dsnomcliente VARCHAR(120),
	dsrfc VARCHAR(13),
	iva double,
	trasdominio double,
	adquisicionlocal double,
	rpp double,
	erogacion double,
	ideerog double,
	honorario double,
	porcentajeIVA double,
	subhiva double,
	ide double,
	total double,
    fecha date,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idtrjamarilla)
)ENGINE=INNODB;

-- LOCALIDAD
DROP TABLE IF EXISTS tbbsnm11;
CREATE TABLE tbbsnm11 (	
	idlocalidad VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(60),
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idlocalidad)
)ENGINE=INNODB;

-- DOMICILIO
DROP TABLE IF EXISTS tbbsnm12;
CREATE TABLE tbbsnm12 (	
	iddomicilio VARCHAR(32) NOT NULL,
	dsestado VARCHAR(60),
	dsmunicipio VARCHAR(60),
    dsciudad VARCHAR(60),
	isasistido INT(1),
	dscolonia VARCHAR(60),
	dslocalidad VARCHAR(60),
	dscalle VARCHAR(60),
	dscodpos VARCHAR(5),
	dsnumint VARCHAR(30),
	dsnumext VARCHAR(6),
	dsdircompleta TEXT,
	dslote VARCHAR(255),
	dsmanzana VARCHAR(255),
    dsreferencia TEXT,  
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(iddomicilio)
)ENGINE=INNODB;

-- ESTADO
DROP TABLE IF EXISTS tbbsnm13;
CREATE TABLE tbbsnm13 (	
	idestado VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(60) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idestado)
)ENGINE=INNODB;

-- MUNICIPIO
DROP TABLE IF EXISTS tbbsnm14;
CREATE TABLE tbbsnm14 (	
	idmunicipio VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(120) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idmunicipio)
)ENGINE=INNODB;

-- COLONIA
DROP TABLE IF EXISTS tbbsnm15;
CREATE TABLE tbbsnm15 (	
	idcolonia VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(30) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idcolonia)
)ENGINE=INNODB;

-- NOTARIA 
DROP TABLE IF EXISTS tbbsnm16;
CREATE TABLE tbbsnm16 (
    idnotaria VARCHAR(32) NOT NULL,
    innumnot VARCHAR(3) NOT NULL,
    dslogo VARCHAR(250),
    iddomicilio VARCHAR(32) NOT NULL,
    isasociada INT(5) NOT NULL DEFAULT 0,
    idusuario VARCHAR(32) NOT NULL,
    tmstmp TIMESTAMP NOT NULL,
    idsesion VARCHAR(32) NOT NULL,
    PRIMARY KEY(idnotaria)
)ENGINE=INNODB;

-- SUBOPERACION
DROP TABLE IF EXISTS tbbsnm17;
CREATE TABLE tbbsnm17 (
  idsuboperacion varchar(32) NOT NULL,
  dsnombre varchar(250) NOT NULL,
  dsdescripcion varchar(250) DEFAULT NULL,
  idoperacion varchar(32) NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idsuboperacion)
) ENGINE=InnoDB;
--   KEY fk_operacion (cdoperacion),
--  CONSTRAINT fk_operacion FOREIGN KEY (cdoperacion) REFERENCES operacion (idoperacion)

-- ACTO
DROP TABLE IF EXISTS tbbsnm18;
CREATE TABLE tbbsnm18 (
  idacto varchar(32) NOT NULL,
  dsnombre varchar(120) NOT NULL,
  numacto int(11),
  dsdescripcion varchar(250) DEFAULT NULL,
  idsuboperacion varchar(32) NOT NULL,
  idexpediente varchar(32) NOT NULL,
  isactivo INT(1) NOT NULL DEFAULT TRUE,
  idsesion VARCHAR(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idacto)
) ENGINE=InnoDB;

CREATE TABLE tbbsnm18b(
	idfiltroActoCompareciente VARCHAR(32) NOT NULL,
	idtipocompareciente VARCHAR(32) NOT NULL,
	idsuboperacion VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idfiltroActoCompareciente)
)ENGINE=INNODB




-- FOREIGN KEY (cdsuboperacion) REFERENCES suboperacion (idsuboperacion)

-- BITACORA DOCUMENTO
DROP TABLE IF EXISTS tbbsnm19;
CREATE TABLE tbbsnm19 (
  idbitacoradocumento varchar(32) NOT NULL,
  iddocumentoexp varchar(32) NOT NULL,
  idacto varchar(32) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idbitacoradocumento)
) ENGINE=InnoDB;
  -- FOREIGN KEY (cddocumentoexp) REFERENCES documentoexpediente (iddocumentoexpediente),
  -- FOREIGN KEY (cdacto) REFERENCES acto (idacto)
  
-- BITACORA EXPEDIENTE
DROP TABLE IF EXISTS tbbsnm20;
CREATE TABLE tbbsnm20 (
  idbitacoraexpediente varchar(32) NOT NULL,
  idexpediente varchar(32) NOT NULL,
  cdacto varchar(32) NOT NULL,
  fechainicial date NOT NULL,
  fechafinal date NOT NULL,
  fechaesperada date DEFAULT NULL,
  idusuarioavanza varchar(32) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idbitacoraexpediente)
) ENGINE=InnoDB;
--   FOREIGN KEY (cdexpediente) REFERENCES expediente (idexpediente),
--  FOREIGN KEY (cdusuarioavanza) REFERENCES usuario (idusuario)

-- COMPARECIENTE
DROP TABLE IF EXISTS tbbsnm21;
CREATE TABLE tbbsnm21 (
  idcompareciente varchar(32) NOT NULL,
  idpersona varchar(32) NOT NULL,
  isavisoextranjero INT(1) DEFAULT NULL,
  isextranjeroinscrito INT(1) DEFAULT NULL,		
  idtipocompareciente varchar(32) NOT NULL,
  alias varchar(120),
  isrepresentante int(1),
  idacto varchar(32) NOT  NULL,
  idregri varchar(32),
  idtratamiento VARCHAR(32),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  amboscompran INT(1),
  idregimen VARCHAR(32),
  dsocupacion VARCHAR(90) DEFAULT NULL,
  idestadocivil VARCHAR(32) DEFAULT NULL,
  iddomicilio VARCHAR(32) DEFAULT NULL,
  idcontacto VARCHAR(32) DEFAULT NULL,
  PRIMARY KEY (idcompareciente)
) ENGINE=InnoDB;
--  FOREIGN KEY (cdpersona) REFERENCES persona (idpersona),
--  FOREIGN KEY (cdexpediente) REFERENCES expediente (idexpediente),
--  FOREIGN KEY (cdacto) REFERENCES acto (idacto),
--  FOREIGN KEY (cdtipocompareciente) REFERENCES tipocompareciente (idtipocompareciente)

-- DOCUMENTO
DROP TABLE IF EXISTS tbbsnm22;
CREATE TABLE tbbsnm22 (
  iddocumento varchar(32) NOT NULL,
  dstitulo varchar(90) NOT NULL,
  dsdescripcion varchar(250) DEFAULT NULL,
  version INT(5) NOT NULL,
  ispublicado int(1) NOT NULL,
  fecha date NOT NULL,
  idsesion varchar(32) NOT NULL,
  isrequerido int(1) NOT NULL,
  idtipodoc varchar(32) NOT NULL,
  txplantilla LONGTEXT,
  isactivo INT(1) DEFAULT TRUE,
  isgestionado VARCHAR(1) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  numdiasgestion int(5),
  PRIMARY KEY (iddocumento)
) ENGINE=InnoDB;
--  FOREIGN KEY (cdtipodoc) REFERENCES catalogo (idcatalogo),
--  FOREIGN KEY (cdacto) REFERENCES acto (idacto)

-- DOCUMENTO VERSIONES
DROP TABLE IF EXISTS tbbsnm22b;
CREATE TABLE tbbsnm22b(
	identificador INT(10) NOT NULL AUTO_INCREMENT,
	iddocumento VARCHAR(32) NOT NULL,
	version INT(5) NOT NULL,
	versionbase INT(5) NOT NULL,
	dstitulo VARCHAR(30) NOT NULL,
	dsdescripcion VARCHAR(250) DEFAULT NULL,
	txplantilla LONGTEXT,
	idtipodoc VARCHAR(32) NOT NULL,
	fecha DATE NOT NULL,
	ispublicado INT(1) NOT NULL,
	isrequerido INT(1) NOT NULL,
	numdiasgestion INT(5),
	isactivo INT(1) DEFAULT TRUE,
	isgestionado VARCHAR(1) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	KEY iddocumento_fk(iddocumento),
	PRIMARY KEY(identificador)
)ENGINE=INNODB;

-- DOCUMENTO EXPEDIENTE
DROP TABLE IF EXISTS tbbsnm23;
CREATE TABLE tbbsnm23 (
  iddocumentoexpediente varchar(32) NOT NULL,
  idexpediente varchar(32) NOT NULL,
  iddocumento varchar(32) NOT NULL,
  idacto varchar(32) NOT NULL,
  dsnombreVar varchar(60) NOT NULL,
  dsvalorVar varchar(120) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (iddocumentoexpediente)
) ENGINE=InnoDB;
--   FOREIGN KEY (cdexpediente) REFERENCES expediente (idexpediente),
--  FOREIGN KEY (cddocumento) REFERENCES documento (iddocumento)
  
-- ESCRITURA
DROP TABLE IF EXISTS tbbsnm24;
CREATE TABLE tbbsnm24 (
  idescritura varchar(32) NOT NULL,
  dsnumescritura varchar(32) DEFAULT NULL,
  idexpediente varchar(32) NOT NULL,
  fechacreacion date NOT NULL,
  folioini bigint DEFAULT NULL,
  foliofin bigint DEFAULT NULL,
  costo double DEFAULT NULL,
  nopaso INT(1) DEFAULT 0,
  hasproceso INT(1) DEFAULT 0,
  idtachafirma VARCHAR(32) DEFAULT NULL,
  idlibro varchar(32) DEFAULT NULL,
  idnotario varchar(32) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idescritura)
) ENGINE=InnoDB;
--  FOREIGN KEY (cdexpediente) REFERENCES expediente (idexpediente),
--  FOREIGN KEY (cdlibro) REFERENCES libro (idlibro)
  
-- GUARDIA 
DROP TABLE IF EXISTS tbbsnm25;
CREATE TABLE tbbsnm25 (
  idguardia varchar(32) NOT NULL,
  idabogado varchar(32) NOT NULL,
  fechainicial date NOT NULL,
  fechafinal date NOT NULL,
  idsesion VARCHAR(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idguardia)
) ENGINE=InnoDB;
-- FOREIGN KEY (cdabogado) REFERENCES usuario (idusuario)

-- LIBRO
DROP TABLE IF EXISTS tbbsnm26;
CREATE TABLE tbbsnm26 (
  idlibro varchar(32) NOT NULL,
  dsdescripcion varchar(250) NOT NULL,
  fecha date NOT NULL,
  infolioinicial bigint NOT NULL,
  infoliofinal bigint NOT NULL,
  idsesion varchar(32) NOT NULL,
  innumlibro bigint,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idlibro)
) ENGINE=InnoDB;

-- OPERACION
DROP TABLE IF EXISTS tbbsnm27;
CREATE TABLE tbbsnm27 (
  idoperacion varchar(32) NOT NULL,
  dsnombre varchar(120) NOT NULL,
  dsdescripcion varchar(250) DEFAULT NULL,
  idsesion VARCHAR(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idoperacion)
) ENGINE=InnoDB;


c6663143972d42014979f7967cc9618d
8078c5f0b055e3a5f1ed1370702bfbe6

-- PERSONA
DROP TABLE IF EXISTS tbbsnm28;
CREATE TABLE tbbsnm28 (
  idpersona varchar(32) NOT NULL,
  idtipopersona varchar(32) DEFAULT NULL,
  dsnombre varchar(120) NOT NULL,
  dsapellidopat varchar(30) DEFAULT NULL,
  dsapellidomat varchar(30) DEFAULT NULL,
  dsnombrecompleto VARCHAR(500),
  dsrfc varchar(13) DEFAULT NULL,
  dscurp varchar(18) DEFAULT NULL,
  idnacionalidad varchar(32) DEFAULT NULL,
  isextranjero INT(1),
  iscapitalextranjero INT(1),
  dslugarnacimiento varchar(90) DEFAULT NULL,
  fechanacimiento date,
  idregimenfiscal VARCHAR(32) DEFAULT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idpersona)
) ENGINE=InnoDB;
--   FOREIGN KEY (cdtipopersona) REFERENCES catalogo (idcatalogo),
--  FOREIGN KEY (cdnacionalidad) REFERENCES catalogo (idcatalogo),
--  FOREIGN KEY (cdestadocivil) REFERENCES catalogo (idcatalogo),
--  FOREIGN KEY (cddomicilio) REFERENCES domicilio (iddomicilio)

-- PRESUPUESTO
DROP TABLE IF EXISTS tbbsnm29;
CREATE TABLE tbbsnm29 (
  idpresupuesto varchar(32) NOT NULL,
  idacto varchar(32) NOT NULL,
  conceptoPago varchar(32) NOT NULL,
  isaplicaiva int(1),
  ispagado int(1),
  fechaPago date,
  fechaCreacion date,
  importe double DEFAULT NULL,
  iva double DEFAULT NULL,
  subtotal double DEFAULT NULL,
  total double DEFAULT NULL,
  comentario LONGTEXT,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idpresupuesto)
) ENGINE=InnoDB;
-- FOREIGN KEY (cdacto) REFERENCES acto (idacto)

-- TESTIMONIO
DROP TABLE IF EXISTS tbbsnm30;
CREATE TABLE tbbsnm30 (
  idtestimonio varchar(32) NOT NULL,
  idusuarioelaboro varchar(32) NOT NULL,
  idescritura varchar(32) NOT NULL,
  idnotario varchar(32) NOT NULL,
  
  isgenerado int(1),
  dsrutaescritura varchar(600) DEFAULT NULL,
  dsrutacaratula varchar(600) DEFAULT NULL,
  dscodigobarras varchar(600) DEFAULT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY(idtestimonio)
) ENGINE=InnoDB;
-- FOREIGN KEY (cdescritura) REFERENCES escritura (idescritura)

-- TIPO COMPARECIENTE
DROP TABLE IF EXISTS tbbsnm31;
CREATE TABLE tbbsnm31 (
  idtipocompareciente varchar(32) NOT NULL,
  dsnombre varchar(60) NOT NULL,
  dsdescripcion varchar(250) DEFAULT NULL,
  idsesion VARCHAR(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  
  PRIMARY KEY (idtipocompareciente)
) ENGINE=InnoDB;

-- EXPEDIENTE
DROP TABLE IF EXISTS tbbsnm32;
CREATE TABLE tbbsnm32 (
  idexpediente varchar(32) NOT NULL,
  dsdescripcion varchar(120) DEFAULT NULL,
  fechainicial date NOT NULL,
  fechafinal date DEFAULT NULL,
  credito double DEFAULT NULL,
  dsreferencia varchar(120) DEFAULT NULL,
  iscotejo int(1) DEFAULT FALSE,
  dsstatus varchar(120) DEFAULT NULL,
  tienecomments int(1) DEFAULT FALSE,
  idabogado varchar(32) DEFAULT NULL,
  idtramite varchar(32) DEFAULT NULL,
  enumestatus ENUM('CERRADO','CANCELADO','ABIERTO') NOT NULL,
  dsmotivocierre VARCHAR(250),
  idsesion varchar(32) NOT NULL,
  numexpediente varchar(16) DEFAULT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idexpediente)
) ENGINE=InnoDB;
--   FOREIGN KEY (cdsuboperacion) REFERENCES suboperacion (idsuboperacion),
--  FOREIGN KEY (cdnotario) REFERENCES usuario (idusuario),
--  FOREIGN KEY (cdabogado) REFERENCES usuario (idusuario)

-- ESTATUS EXPEDIENTE
-- DROP TABLE IF EXISTS tbbsnm32b;
-- CREATE TABLE tbbsnm32b (
  
  -- idexpediente VARCHAR(32) NOT NULL,
  -- estatus ENUM('Cerrado','Cancelado','Abierto') NOT NULL,
  -- motivo VARCHAR(150),
  -- idsesion VARCHAR(32) NOT NULL,
  -- tmstmp TIMESTAMP NOT NULL,
  -- PRIMARY KEY (idexpediente)
-- ) ENGINE=InnoDB;

-- DOCUMENTO NOTARIAL PARCIAL
DROP TABLE IF EXISTS tbbsnm33;
CREATE TABLE tbbsnm33 (
  iddocnotpar varchar(32) NOT NULL,
  idescritura varchar(32) NOT NULL,
  fecha date NOT NULL,
  version integer(4) NOT NULL,
  iscerrado INT(1) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  txtdoc LONGTEXT,
  PRIMARY KEY (iddocnotpar)
) ENGINE=InnoDB;

-- DOCUMENTO NOTARIAL PARCIAL BACKUP
DROP TABLE IF EXISTS tbbsnm33b;
CREATE TABLE tbbsnm33b (
  iddocnotpar varchar(32) NOT NULL,
  idescritura varchar(32) NOT NULL,
  fecha date NOT NULL,
  version integer(4) NOT NULL,
  iscerrado INT(1) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  txtdoc LONGTEXT,
  PRIMARY KEY (iddocnotpar)
) ENGINE=InnoDB;

-- CIUDAD
DROP TABLE IF EXISTS tbbsnm34;
CREATE TABLE tbbsnm34 (	
	idciudad VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(30) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idciudad)
)ENGINE=INNODB;

-- DOCUMENTO NOTARIAL MASTER
DROP TABLE IF EXISTS tbbsnm35;
CREATE TABLE tbbsnm35 (
  iddocnotmas varchar(32) NOT NULL,
  idescritura varchar(32) NOT NULL,
  fecha date NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  txtdoc LONGTEXT,
  PRIMARY KEY (iddocnotmas)
) ENGINE=InnoDB;

-- ESCRITURA_ACTO
DROP TABLE IF EXISTS tbbsnm36;
CREATE TABLE tbbsnm36 (
  idescacto varchar(32) NOT NULL,
  idescritura varchar(32) NOT NULL,
  idacto varchar(32) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idescacto)
) ENGINE=InnoDB;

-- ACTO DOCUMENTO
DROP TABLE IF EXISTS tbbsnm37;
CREATE TABLE tbbsnm37 (
  idactodoc varchar(32) NOT NULL,
  iddocumento varchar(32),
  idacto varchar(32) NOT NULL,
  idformatopdf VARCHAR(32),
  idnotario varchar(32),
  idgestor VARCHAR(32) NULL DEFAULT '', 
  idvaluador VARCHAR(32) NULL DEFAULT '',
  dsruta varchar(600) DEFAULT NULL,
  dsrutaformato varchar(600) DEFAULT NULL,
  fechasolicitud date,
  fechaentrega date,
  fechaaprobacion date,
  issolicitado int(1),
  isentregado int(1),
  isaprovado int(1),
  tienecomments int(1) DEFAULT FALSE,
  txtFormato LONGTEXT,
  fechaArchivo date,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idactodoc)
) ENGINE=InnoDB;

-- ALTER TABLE tbbsnm37 ADD COLUMN idgestor VARCHAR(32) NULL DEFAULT '' AFTER idnotario; 
-- ALTER TABLE tbbsnm37 ADD COLUMN  idvaluador VARCHAR(32) NULL DEFAULT '' AFTER idgestor;

-- ETAPA_TESTIMONIO
DROP TABLE IF EXISTS tbbsnm38;
CREATE TABLE tbbsnm38 (
  idetates varchar(32) NOT NULL,
  dsnombre varchar(120) NOT NULL,
  dsdescripcion varchar(120) DEFAULT NULL,
  inorden int(3) NOT NULL, 
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idetates)
) ENGINE=InnoDB;

-- COMPARECIENTE REPRESENTANTE
DROP TABLE IF EXISTS tbbsnm39;
CREATE TABLE tbbsnm39 (
  idcomrep varchar(32) NOT NULL,
  idrepresentante varchar(32) NOT NULL,
  idrepresentado varchar(32) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idcomrep)
) ENGINE=InnoDB;

-- COMPARECIENTE HIJO
DROP TABLE IF EXISTS tbbsnm39a;
CREATE TABLE tbbsnm39a (
  idcomhijo varchar(32) NOT NULL,
  idcompareciente varchar(32) NOT NULL,
  idhijo varchar(32) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idcomhijo)
) ENGINE=InnoDB;



-- TRAMITE
DROP TABLE IF EXISTS tbbsnm40;
CREATE TABLE tbbsnm40 (
  idtramite varchar(32) NOT NULL,
  idcliente varchar(32) NOT NULL,
  idabogado varchar(32) NOT NULL,
  idlocacion varchar(32),
  txtproposito LONGTEXT,
  idstatus varchar(32) DEFAULT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idtramite)
) ENGINE=InnoDB;


--TRAMITE USUARIO
DROP TABLE IF EXIST tbbsnm40a;
CREATE TABLE tbbsnm40a(
	idtramiteusuario varchar(32) NOT NULL,
	idusuario varchar(32) NOT NULL,
  	idtramite varchar(32) DEFAULT NULL,
	idsesion varchar(32) NOT NULL,
  	tmstmp TIMESTAMP NOT NULL,
  	PRIMARY KEY (idtramiteusuario)
) ENGINE = InnoDB;


-- ETAPA_TRAMITE
DROP TABLE IF EXISTS tbbsnm41;
CREATE TABLE tbbsnm41 (
  idetatra varchar(32) NOT NULL,
  dsnombre varchar(120) NOT NULL,
  dsdescripcion varchar(120) NOT NULL,
  inorden int(3) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idetatra)
) ENGINE=InnoDB;

-- 	BITACORA GENERAL
DROP TABLE IF EXISTS tbbsnm42;
CREATE TABLE tbbsnm42 (
  idbitgeneral varchar(32) NOT NULL,
  idusuario varchar(32) NOT NULL,
  idtramite varchar(32) DEFAULT NULL,
  idexpediente varchar(32) DEFAULT NULL,
  fecha TIMESTAMP NOT NULL,
  dsoperacion varchar(60),
  dsentidad varchar(60),
  dsdescripcion varchar(250),
  PRIMARY KEY (idbitgeneral)
) ENGINE=InnoDB;

-- 	IMPUESTO
DROP TABLE IF EXISTS tbbsnm43;
CREATE TABLE tbbsnm43 (
  idimpuesto varchar(32) NOT NULL,
  dsnombre varchar(250),
  dssiglas varchar(10),
  tasa double,
  porcentaje double,
  PRIMARY KEY (idimpuesto)
) ENGINE=InnoDB;

-- REGISTRO RI
DROP TABLE IF EXISTS tbbsnm44;
CREATE TABLE tbbsnm44 (
  idregri varchar(32) NOT NULL,
  dsnombre varchar(250),
  idexpedidopor varchar(32),
  idtipo varchar(32),
  numclave varchar(60),
  fechaadjuntado date,
  dsruta varchar(512),
  isvalidadonotario INT(1),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idregri)
) ENGINE=InnoDB;

-- ETAPA_TESTIMONIO
DROP TABLE IF EXISTS tbbsnm45;
CREATE TABLE tbbsnm45 (
  idreletapatesti varchar(32) NOT NULL,
  idtestimonio varchar(32),
  idetapatestimonio varchar(32),
  fechaaprobada TIMESTAMP,
  isaprobada int(1),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idreletapatesti)
) ENGINE=InnoDB;

-- CONF_FORMULARIO
DROP TABLE IF EXISTS tbbsnm46;
CREATE TABLE tbbsnm46 (
  idconFormulario varchar(32) NOT NULL,
  version int(5),
  dstitulo varchar(120),
  dsdescripcion varchar(250),
  dsnombrecorto varchar(250),
  ispublicado int(1),
  fechapublicacion date,
  intipoform ENUM('D','E','O','') NULL DEFAULT ' ' COMMENT 'tipo de formulario: Documento, Escritura, Operación, o selec Ninguno', 
  idlocacion VARCHAR(32) NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idconFormulario, version)
) ENGINE=InnoDB;

-- ALTER TABLE tbbsnm46 ADD COLUMN intipoform ENUM('D','E', ' ') NULL DEFAULT ' ' COMMENT 'tipo de formulario: Documento, Escritura, Ninguno' AFTER fechapublicacion; 
-- evidencia de las PENDEJADAS de ALGUIEN
-- ALTER TABLE tbbsnm46 DROP COLUMN inpermiso;
-- ALTER TABLE tbbsnm46 ADD COLUMN idlocacion VARCHAR(32) NULL AFTER intipoform;

-- ACTO FORMULARIO
DROP TABLE IF EXISTS tbbsnm47;
CREATE TABLE tbbsnm47 (
  idactoformulario varchar(32) NOT NULL,
  idsuboperacion varchar(32),
  idconformulario varchar(32),
  versionform int(5),
  inposicion INT(5) NULL DEFAULT 0 COMMENT 'Indica la posicion del formulario dentro del acto',
  inestatus ENUM('F','E',' ') NOT NULL DEFAULT ' ' COMMENT 'F esta lleno el formulario, E esta vacio',
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idactoformulario)
) ENGINE=InnoDB;


-- SELECT A.idconformulario, A.versionform, B.idsuboperacion, B.idactoformulario
  -- FROM tbbsnm51 A RIGHT JOIN tbbsnm47 B 
    -- ON (A.idconformulario = B.idconFormulario AND A.versionform = B.versionform)
    -- WHERE A.idacto = '1c58ceba642e63d95bebc5c7a3ad7aa9'; 


-- SELECT A.idconformulario, A.versionform, B.dstitulo, B.ispublicado, B.intipoform 
  -- FROM tbbsnm51 A INNER JOIN tbbsnm46 B 
    -- ON A.idconformulario = B.idconFormulario AND A.versionform = B.version 
 -- RIGHT OUTER JOIN tbbsnm47  C 
    -- ON B.idconFormulario = C.idconformulario AND B.version =  C.versionform 
 -- WHERE idacto = '1c58ceba642e63d95bebc5c7a3ad7aa9' AND C.idsuboperacion = 'c4ca4238a0b923820dcc509a6f75849b';

  
-- ALTER TABLE tbbsnm47 ADD COLUMN inposicion INT(5) NULL DEFAULT 0 COMMENT 'Indica la posicion del formulario dentro del acto' AFTER versionform;
-- ALTER TABLE tbbsnm47 ADD COLUMN inestatus ENUM('F','E',' ') NOT NULL DEFAULT ' ' COMMENT 'F esta lleno el formulario, E esta vacio' AFTER versionform;

-- PERMISO_ROL
DROP TABLE IF EXISTS tbbsnm48;
CREATE TABLE tbbsnm48 (
  idpermisorol varchar(32) NOT NULL,
  idrol varchar(32),
  idconformulario varchar(32),
  versionform int(5),
  inpermiso INT(5) DEFAULT 0,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idpermisorol)
) ENGINE=InnoDB;

-- ALTER TABLE tbbsnm48 ADD COLUMN inpermiso INT(5) DEFAULT 0 AFTER versionform;

-- CONSUBFORM
DROP TABLE IF EXISTS tbbsnm49;
CREATE TABLE tbbsnm49 (
  idconsubform varchar(32) NOT NULL,
  nombre varchar(120),
  dsnombrecorto varchar(250),
  inposicion int default 0,
  idconformulario varchar(32),
  versionform int(5),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idconsubform)
) ENGINE=InnoDB;

-- ALTER TABLE tbbsnm49 ADD COLUMN inposicion INT DEFAULT 0 AFTER dsnombrecorto;
-- ALTER TABLE tbbsnm49 ADD COLUMN inorden INT DEFAULT 0 AFTER inposicion;

-- COMPONENTE
DROP TABLE IF EXISTS tbbsnm50;
CREATE TABLE tbbsnm50 (
  idcomponente varchar(32) NOT NULL,
  idtipocomponente varchar(32),
  dsetiqueta varchar(120),
  longitudminima int(5),
  longitudmaxima int(5),
  dsexpresionvalidacion varchar (250),
  dslistavalores LONGTEXT,
  dscatalogo varchar (250),
  dstablabusqueda varchar (250),
  dsnombrevariable varchar (120),
  dsayuda varchar (600),
  isrequerido int(1),
  idconformulario varchar (32),
  versionform int(5),
  inposicion INT(5) DEFAULT 0,
  inorden INT(5) DEFAULT 0,
  idsubformulario varchar (32),
  isparasubform int(1),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idcomponente)
) ENGINE=InnoDB;

-- ALTER TABLE tbbsnm50 ADD COLUMN inposicion INT DEFAULT 0 AFTER versionform;
-- ALTER TABLE tbbsnm50 ADD COLUMN inorden INT DEFAULT 0 AFTER inposicion;

-- FORMULARIO
DROP TABLE IF EXISTS tbbsnm51;
CREATE TABLE tbbsnm51 (
  idformulario varchar(32) NOT NULL,
  idacto varchar(32),
  idconformulario varchar(32),
  versionform int(5),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idformulario)
) ENGINE=InnoDB;

-- VALOR FORMULARIO
DROP TABLE IF EXISTS tbbsnm52;
CREATE TABLE tbbsnm52 (
  idvalorform varchar(32) NOT NULL,
  idformulario varchar(32),
  idcomponente varchar(32),
  valorcadena varchar(600),
  valorentero int,
  valordoble double,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idvalorform)
) ENGINE=InnoDB;

-- VALOR_SUBFORMULARIO
DROP TABLE IF EXISTS tbbsnm53;
CREATE TABLE tbbsnm53 (
  idvalorsubform varchar(32) NOT NULL,
  idformulario varchar(32),
  idcomponente varchar(32),
  registro int,
  valorcadena varchar(600),
  valorentero int,
  valordoble double,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idvalorsubform)
) ENGINE=InnoDB;

-- SOLICITUD DE PAGO
DROP TABLE IF EXISTS tbbsnm54;
CREATE TABLE tbbsnm54 (
  idsolpago varchar(32) NOT NULL,
  idexpediente varchar(32),
  concepto varchar(600),
  valor double,
  isanticipo int(1),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idsolpago)
) ENGINE=InnoDB;

-- DATOS FISCALES PAGO
DROP TABLE IF EXISTS tbbsnm55;
CREATE TABLE tbbsnm55 (
  iddatofiscapago varchar(32) NOT NULL,
  idsolicitudpago varchar(32),
  idcompareciente varchar(32),
  innumpago int,
  dsnombrepagador varchar(600),
  dsrfc varchar(13),
  dscurp varchar (18),
  dscorreoelectronico varchar(60),
  dsdircompleta LONGTEXT,
  status varchar(32),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (iddatofiscapago)
) ENGINE=InnoDB;

-- DATOS PAGO
DROP TABLE IF EXISTS tbbsnm56;
CREATE TABLE tbbsnm56 (
  iddatopago varchar(32) NOT NULL,
  iddatofiscapago varchar(32),
  fechapago date,
  idusuarioelaboro varchar(32),
  facturaserie varchar(120),
  facturafolio varchar(120),
  isenviacorreo int(1),
  txtnota LONGTEXT,
  rutaarchivoxml varchar(600),
  idmediopago varchar(32),
  importepago double,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (iddatopago)
) ENGINE=InnoDB;


-- CALENDARIO_CITAS
DROP TABLE IF EXISTS tbbsnm57;
CREATE TABLE tbbsnm57 (
  idcita varchar(32) NOT NULL,
  version int(5) NOT NULL,
  dsestatus varchar(60),
  fechainicio TIMESTAMP NULL,
  fechatermino TIMESTAMP NULL,
  dsactividad varchar(250),
  isreprogramdo INT(1),
  dsdescripcion TEXT,  
  notificarcorreo INT(1),
  idexpediente varchar(32),
  idusragendo varchar(32),
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idcita,version)
) ENGINE=InnoDB;

-- INVITADO CALENDARIO CITA
DROP TABLE IF EXISTS tbbsnm58;
CREATE TABLE tbbsnm58 (
  idinvitado varchar(32) NOT NULL,
  idusuario varchar(32),
  idcompareciente varchar(32),
  idcita varchar(32) NOT NULL,
  version int(5) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idinvitado)
) ENGINE=InnoDB;

-- DOCUMENTO ANEXO
DROP TABLE IF EXISTS tbbsnm59;
CREATE TABLE tbbsnm59 (
  idanexo varchar(32) NOT NULL,
  iddocnotmas varchar(32),
  idcita varchar(32) NOT NULL,
  version int(5) NOT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idanexo)
) ENGINE=InnoDB;

-- ACUSE RECIBO DE ENTREGA ESCRITURA
DROP TABLE IF EXISTS tbbsnm60;
CREATE TABLE tbbsnm60(
	idacuse VARCHAR(32) NOT NULL,
	idescritura VARCHAR(32) NOT NULL,
	enumentrega ENUM('Mensajeria','Notaria'),
	fchentrega DATE NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY (idacuse)
) ENGINE=InnoDB;	

-- ACUSE RECIBO PERSONAS
DROP TABLE IF EXISTS tbbsnm60b;
CREATE TABLE tbbsnm60b(
	id INT(11) NOT NULL AUTO_INCREMENT,
	dsnombrecompleto VARCHAR(250) NOT NULL,
	idacuse VARCHAR(32) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB;

-- COMPARECIENTE CONYUGE
DROP TABLE IF EXISTS tbbsnm61;
CREATE TABLE tbbsnm61(
	idsujeto VARCHAR(32) NOT NULL,
	idconyugecompra VARCHAR(32),
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	idconyugeactual VARCHAR(32),
	PRIMARY KEY (idsujeto)
) ENGINE=InnoDB;

-- CAMPOS TARJETA AMARILLA
DROP TABLE IF EXISTS tbbsnm62;
CREATE TABLE tbbsnm62(
	identificador INT(11) NOT NULL AUTO_INCREMENT,
	idoperacion VARCHAR(32) NOT NULL,
	dscampo VARCHAR(90) NOT NULL,
	dscodigo VARCHAR(10) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY (identificador)
) ENGINE=InnoDB;

-- GESTOR
DROP TABLE IF EXISTS tbbsnm63;
CREATE TABLE tbbsnm63(
	idgestor VARCHAR(32) NOT NULL,
	dsnombre VARCHAR(60) NOT NULL,
	dspaterno VARCHAR(60) NOT NULL,
	dsmaterno VARCHAR(60) NOT NULL,
	dstelefono VARCHAR(10),
	dscorreo VARCHAR(60),
	dsrfc VARCHAR(13),
	dscurp VARCHAR(18),
	dsempresa VARCHAR(90) NOT NULL,
	inprecio DOUBLE,
	idlocacion VARCHAR(32) NOT NULL,
	inestatus INT(1) NOT NULL DEFAULT TRUE,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY (idgestor)
) ENGINE=InnoDB;

-- DOCUMENTOS SUBOPERACION
DROP TABLE IF EXISTS tbbsnm64;
CREATE TABLE tbbsnm64(
	identificador INT(11) NOT NULL AUTO_INCREMENT,
	iddocumento VARCHAR(32) NULL,
	idsuboperacion VARCHAR(32) NULL,
	idlocalidad VARCHAR(32) NULL,
	idformatopdf VARCHAR(32) NULL,
	inorden INT(11),
	isgestionado VARCHAR(1) DEFAULT '',
	PRIMARY KEY(identificador)	
)ENGINE = InnoDB;

-- VALUADOR
DROP TABLE IF EXISTS tbbsnm65;
CREATE TABLE tbbsnm65(
	idvaluador VARCHAR(255) NOT NULL,
	dsnombre VARCHAR(60) NOT NULL,
	dspaterno VARCHAR(60) NOT NULL,
	dsmaterno VARCHAR(60) NOT NULL,
	dsempresa VARCHAR(90) NOT NULL,
	dsrfc VARCHAR(13),
	dscurp VARCHAR(18),
	dscorreo VARCHAR(60),
	dstelefono VARCHAR(10),
	dsmovil VARCHAR(10),
	inprecio DOUBLE,
	inestatus INT(1) NOT NULL DEFAULT TRUE,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idvaluador)
)ENGINE = InnoDB;

-- AUTORIZANTE COMPARECIENTE
DROP TABLE IF EXISTS tbbsnm66;
CREATE TABLE tbbsnm66(
	idcompareciente VARCHAR(32) NOT NULL,
	idautorizante VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idcompareciente,idautorizante)
)ENGINE=InnoDB;

-- CONTACTO
DROP TABLE IF EXISTS tbbsnm67;
CREATE TABLE tbbsnm67(
	idcontacto VARCHAR(32) NOT NULL,
	idpersona VARCHAR(32) NOT NULL,
	dstelefono VARCHAR(30) DEFAULT NULL,
	dscorreoelectronico VARCHAR(60) DEFAULT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY (idcontacto)
)ENGINE=InnoDB;

-- SOLICITUD PRESTAMO FOLIOS
DROP TABLE IF EXISTS tbbsnm68;
CREATE TABLE tbbsnm68(
	idsolicitudprestamo VARCHAR(32) NOT NULL,
	idusuariopresta VARCHAR(32),
	idusuariorecibe VARCHAR(32),
  	dsnumescritura varchar(32) DEFAULT NULL,
	infolioinicio BIGINT,
	infoliofin BIGINT,
	fechaentrega DATE,
	fechadevolucion DATE,
	estanprestados INT(1),
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	isprestamoterminado INT(1) DEFAULT FALSE,
	PRIMARY KEY(idsolicitudprestamo)
)ENGINE=InnoDB;

-- DEVOLUCION FOLIOS
DROP TABLE IF EXISTS tbbsnm69;
CREATE TABLE tbbsnm69(
	idbitacorafolio VARCHAR(32) NOT NULL,
	idusuariorecibe VARCHAR(32),
	idusuarioentrega VARCHAR(32),
	dscomentario VARCHAR(255),
	dsnumeroescritura VARCHAR(32),
	fechaoperacion TIMESTAMP,
	tipooperacion VARCHAR(5),
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(iddevolucionfolio)
)ENGINE=InnoDB ;

-- DEVOLUCION RECHAZADA
DROP TABLE IF EXISTS tbbsnm70;
CREATE TABLE tbbsnm70(
	iddevolucionfolio VARCHAR(32) NOT NULL,
	idusuariorechaza VARCHAR(32) NOT NULL,
	dsmotivo TEXT,
	isresuelta INT(1),
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(iddevolucionfolio)
)ENGINE=InnoDB;

-- CONTROL FOLIOS
/*OLD
DROP TABLE IF EXISTS tbbsnm71;
CREATE TABLE tbbsnm71(
	libroactual BIGINT,
	foliosdisponibles BIGINT,
	folioactual BIGINT
)ENGINE=InnoDB;
*/
DROP TABLE IF EXISTS tbbsnm71;
CREATE TABLE tbbsnm71(
	idcontrolfolios varchar(32) NOT NULL,
	foliosdisponibles BIGINT,
	folioactual BIGINT,
  	actualizacion date DEFAULT NULL,
	idsesion varchar(32) NOT NULL,
  	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idcontrolfolios)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS tbbsnm26a;
CREATE TABLE tbbsnm26a(
	idavisodecena varchar(32) NOT NULL,
	escrituraApertura varchar (32) NOT NULL,
	escrituraCierre varchar(32),
	fecha date DEFAULT NULL,
	libroapertura BIGINT,
	notario varchar(32) NOT NULL,
	tipoaviso varchar (5) NOT NULL,
	urldocumento varchar(500) NOT NULL,
	idsesion varchar(32) NOT NULL,
  	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idavisodecena)
)ENGINE=InnoDB;


-- ESCRITURA EXTERNA
DROP TABLE IF EXISTS tbbsnm72;
CREATE TABLE tbbsnm72(
  idescrituraexterna varchar(32) NOT NULL,
  dsnumescritura varchar(32) DEFAULT NULL,
  idexpediente varchar(32) NOT NULL,
  fechacreacion date NOT NULL,
  fechafirma date DEFAULT NULL,
  fechaimpresion date DEFAULT NULL,
  fechaencuadernado date DEFAULT NULL,
  folioini bigint DEFAULT NULL,
  foliofin bigint DEFAULT NULL,
  costo double DEFAULT NULL,
  idlibro varchar(32) DEFAULT NULL,
  idnotario varchar(32) NOT NULL,
  isfirmaditto int(1) DEFAULT 0,
  fechafirmaditto date DEFAULT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp TIMESTAMP NOT NULL,
  PRIMARY KEY (idescrituraexterna)
)ENGINE=InnoDB;


-- TACHADO DE FIRMAS
DROP TABLE IF EXISTS tbbsnm73;
CREATE TABLE tbbsnm73 (
	idfirma VARCHAR(32) NOT NULL,
	idescritura VARCHAR(32) NOT NULL,
	idcompareciente VARCHAR(32) NOT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idfirma)
)ENGINE=INNODB;


-- MESA DE CONTROL
DROP TABLE IF EXISTS tbbsnm74;
CREATE TABLE tbbsnm74 (
	idmesacontrol VARCHAR(32) NOT NULL,
	idescritura VARCHAR(32) NOT NULL,
	idactodoc VARCHAR(32) NOT NULL,
	idalerta VARCHAR(32)DEFAULT NULL,
	idpago VARCHAR(32) DEFAULT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idmesacontrol)
)ENGINE=INNODB;

-- PAGO
DROP TABLE IF EXISTS tbbsnm75;
CREATE TABLE tbbsnm75 (
  idpago varchar(32) NOT NULL,
  idescritura varchar(32) NOT NULL,
  idexpediente varchar(32) NOT NULL,
  idactodocumento varchar(32) DEFAULT NULL,
  statuspago varchar(255) DEFAULT NULL,
  isliquidado int(1) DEFAULT NULL,
  docacusepago varchar(500) DEFAULT NULL,
  dochojaamarilla varchar(500) DEFAULT NULL,
  docdatosfactura varchar(500) DEFAULT NULL,
  montoporpagar double DEFAULT NULL,
  montopagado double DEFAULT NULL,
  idsesion varchar(32) NOT NULL,
  tmstmp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (idpago)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

-- ALERTAOBJETO
DROP TABLE IF EXISTS tbbsnm76;
CREATE TABLE tbbsnm76 (
	idalertaobjeto VARCHAR(32) NOT NULL,
	idalerta VARCHAR(32) NOT NULL,
	idobjeto VARCHAR(32) NOT NULL,
	tipoobjeto VARCHAR(20)DEFAULT NULL,
	isfinalizado INT(1) DEFAULT 0,
	statusalerta VARCHAR(20)DEFAULT NULL,
	updated timestamp NULL DEFAULT NULL,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idalertaobjeto)
)ENGINE=INNODB;

-- BITACORA USUARIO EXPEDIENTES
DROP TABLE IF EXISTS tbbsnm77;
CREATE TABLE tbbsnm77 (
	idbitusu VARCHAR(32) NOT NULL,
	idacto VARCHAR(32) DEFAULT NULL,
	idexpediente VARCHAR(32) DEFAULT NULL,
	idtarea VARCHAR(32) DEFAULT NULL,
numexp VARCHAR(32) NOT NULL,
	idgrupotrabajo VARCHAR(32) DEFAULT NULL,
	idobjeto VARCHAR(32) NOT NULL,
	tipo VARCHAR(32) NOT NULL,
	texto VARCHAR(250) NOT NULL,
	status VARCHAR(32) DEFAULT NULL,
active INT(1) DEFAULT 0,	
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idbitusu)
)ENGINE=INNODB;



-- PIZARRON ELEMENTO
DROP TABLE IF EXISTS tbbsnm78;
CREATE TABLE tbbsnm78 (
	idpizarronelemento VARCHAR(32) NOT NULL,
	idabogado VARCHAR(32) DEFAULT NULL,
	idgrupotrabajo VARCHAR(32) DEFAULT NULL,
	fecha timestamp NULL DEFAULT NULL,
	idlibro VARCHAR(32) NOT NULL,
	folioinicial BIGINT UNSIGNED NOT NULL,
	foliofinal BIGINT UNSIGNED NOT NULL,
	numeroescritura VARCHAR(32) NOT NULL,
	status VARCHAR(32) DEFAULT NULL,
	iscierrelibro INT(1) DEFAULT 0,
	idsesion VARCHAR(32) NOT NULL,
	tmstmp TIMESTAMP NOT NULL,
	PRIMARY KEY(idpizarronelemento)
)ENGINE=INNODB;


-- ACTO PROCESO
DROP TABLE IF EXISTS tbbsnm81;
CREATE TABLE tbbsnm81(
	idacto VARCHAR(32) NOT NULL,
	idproceso BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY (idacto,idproceso)
)ENGINE=INNODB DEFAULT CHARSET = utf8;

-- TAREA - ACTO PROCESO
DROP TABLE IF EXISTS tbbsnm82;
CREATE TABLE tbbsnm82(
	idtarea BIGINT UNSIGNED NOT NULL,
	nombretarea VARCHAR(32) DEFAULT NULL,
	idacto VARCHAR(32) NOT NULL,
	idproceso BIGINT UNSIGNED NOT NULL,
	isactive INT(1) DEFAULT 0,
	PRIMARY KEY (idtarea)
)ENGINE=INNODB DEFAULT CHARSET = utf8;

ALTER TABLE tbbsnm81 ADD FOREIGN KEY(idacto) REFERENCES tbbsnm18(idacto);
ALTER TABLE tbbsnm82 ADD FOREIGN KEY(idacto,idproceso) REFERENCES tbbsnm81(idacto,idproceso);
ALTER TABLE tbbsnm73 ADD FOREIGN KEY(idescritura) REFERENCES tbbsnm24(idescritura);
ALTER TABLE tbbsnm73 ADD FOREIGN KEY(idcompareciente) REFERENCES tbbsnm21(idcompareciente);

ALTER TABLE tbcfgm21 ADD FOREIGN KEY(idsuboperacion) REFERENCES tbbsnm17(idsuboperacion);
ALTER TABLE tbcfgm32 ADD FOREIGN KEY(idvariable) REFERENCES tbcfgm08(idvariable),
		ADD FOREIGN KEY (idcomponente) REFERENCES tbbsnm50(idcomponente);
ALTER TABLE tbbsnm01 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32(idexpediente),
					 ADD FOREIGN KEY (idusuario) REFERENCES tbcfgm03(idusuario);
ALTER TABLE tbbsnm02 ADD FOREIGN KEY (idtramite) REFERENCES tbbsnm40(idtramite),
		ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32(idexpediente),
		ADD FOREIGN KEY (idusuarioasigna) REFERENCES tbcfgm03(idusuario),
		ADD FOREIGN KEY (idusuariorecibe) REFERENCES tbcfgm03(idusuario);
ALTER TABLE tbbsnm03 ADD FOREIGN KEY (idescritura) REFERENCES tbbsnm24(idescritura),
		ADD FOREIGN KEY (idusuariofirma) REFERENCES tbcfgm03(idusuario),
        ADD FOREIGN KEY (idcompareciente) REFERENCES tbbsnm21(idcompareciente);
ALTER TABLE tbbsnm04 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32(idexpediente);
		
ALTER TABLE tbbsnm05 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32(idexpediente);
		
ALTER TABLE tbbsnm06 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32(idexpediente),
		ADD FOREIGN KEY (idusuarioasigna) REFERENCES tbcfgm03(idusuario),
		ADD FOREIGN KEY (idusuarioatiende) REFERENCES tbcfgm03(idusuario);
ALTER TABLE tbbsnm07 ADD FOREIGN KEY (idescritura) REFERENCES tbbsnm24(idescritura);
ALTER TABLE tbbsnm08 ADD FOREIGN KEY (idsuboperacion) REFERENCES tbbsnm17(idsuboperacion);
ALTER TABLE tbbsnm08 ADD FOREIGN KEY (idlocacion) REFERENCES tbcfgm91 (idelemento);
ALTER TABLE tbbsnm09 ADD FOREIGN KEY (iddomicilio) REFERENCES tbbsnm12(iddomicilio),
                ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18(idacto),
				ADD FOREIGN KEY (idVocTerreno) REFERENCES tbcfgm91(idelemento),
				ADD FOREIGN KEY (idVocHabitacional) REFERENCES tbcfgm91(idelemento),
				ADD FOREIGN KEY (idVocComercial) REFERENCES tbcfgm91(idelemento);
				
ALTER TABLE tbbsnm10 ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18(idacto),
		ADD FOREIGN KEY (idusuarioelab) REFERENCES tbcfgm03(idusuario),
		ADD FOREIGN KEY (idpersona) REFERENCES tbbsnm28(idpersona);
ALTER TABLE tbbsnm16 ADD FOREIGN KEY (iddomicilio) REFERENCES tbbsnm12(iddomicilio),
		ADD FOREIGN KEY (idusuario) REFERENCES tbcfgm03(idusuario);
-- --------------------------------------------------------------		
ALTER TABLE tbbsnm17 ADD FOREIGN KEY (idoperacion) REFERENCES tbbsnm27 (idoperacion);
--                     ADD FOREIGN KEY (iddocnot) REFERENCES tbbsnm08 (iddocnot);
ALTER TABLE tbbsnm18 ADD FOREIGN KEY (idsuboperacion) REFERENCES tbbsnm17 (idsuboperacion),
 		     ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente);

ALTER TABLE tbbsnm18b ADD FOREIGN KEY (idtipocompareciente) REFERENCES tbbsnm31(idtipocompareciente),ADD FOREIGN KEY(idsuboperacion) REFERENCES tbbsnm17(idsuboperacion);

ALTER TABLE tbbsnm19 ADD FOREIGN KEY (iddocumentoexp) REFERENCES tbbsnm23 (iddocumentoexpediente),
		ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18 (idacto);
ALTER TABLE tbbsnm20 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente),
		ADD FOREIGN KEY (idusuarioavanza) REFERENCES tbcfgm03 (idusuario);
ALTER TABLE tbbsnm21 ADD FOREIGN KEY (idpersona) REFERENCES tbbsnm28 (idpersona),
		ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18 (idacto),
		ADD FOREIGN KEY (idregri) REFERENCES tbbsnm44 (idregri),
		ADD FOREIGN KEY (idtipocompareciente) REFERENCES tbbsnm31 (idtipocompareciente),
		ADD FOREIGN KEY(idtratamiento) REFERENCES tbcfgm91(idelemento),
		ADD FOREIGN KEY (idregimen) REFERENCES tbcfgm91(idelemento),
		ADD FOREIGN KEY (idestadocivil) REFERENCES tbcfgm91 (idelemento),
		ADD FOREIGN KEY (iddomicilio) REFERENCES tbbsnm12 (iddomicilio),
		ADD FOREIGN KEY (idcontacto) REFERENCES tbbsnm67 (idcontacto);
ALTER TABLE tbbsnm22 
		ADD FOREIGN KEY (idtipodoc) REFERENCES tbcfgm91 (idelemento);
ALTER TABLE tbbsnm22b ADD FOREIGN KEY (idtipodoc) REFERENCES tbcfgm91 (idelemento);		
ALTER TABLE tbbsnm23 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente),
		ADD FOREIGN KEY (iddocumento) REFERENCES tbbsnm22 (iddocumento),
                ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18 (idacto);
ALTER TABLE tbbsnm24 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente),
		ADD FOREIGN KEY (idlibro) REFERENCES tbbsnm26 (idlibro),
                ADD FOREIGN KEY (idnotario) REFERENCES tbcfgm03 (idusuario);
ALTER TABLE tbbsnm24 ADD CONSTRAINT dsnumescritura UNIQUE (dsnumescritura);
ALTER TABLE tbbsnm25 ADD FOREIGN KEY (idabogado) REFERENCES tbcfgm03 (idusuario);
ALTER TABLE tbbsnm28 ADD FOREIGN KEY (idtipopersona) REFERENCES tbcfgm91 (idelemento),
		ADD FOREIGN KEY (idnacionalidad) REFERENCES tbcfgm91 (idelemento),
		ADD FOREIGN KEY (idregimenfiscal) REFERENCES tbcfgm91 (idelemento);
ALTER TABLE tbbsnm29 ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18 (idacto), 
					 ADD FOREIGN KEY (conceptoPago) REFERENCES tbcfgm91 (idelemento);
ALTER TABLE tbbsnm30 ADD FOREIGN KEY (idescritura) REFERENCES tbbsnm24 (idescritura),
                ADD FOREIGN KEY (idnotario) REFERENCES tbcfgm03 (idusuario),
				ADD FOREIGN KEY (idusuarioelaboro) REFERENCES tbcfgm03 (idusuario); 
ALTER TABLE tbbsnm32 ADD FOREIGN KEY (idabogado) REFERENCES tbcfgm03 (idusuario),
               ADD FOREIGN KEY (idtramite) REFERENCES tbbsnm40 (idtramite);
-- ALTER TABLE tbbsnm32 ADD CONSTRAINT numexpeidente UNIQUE (numexpediente);
CREATE UNIQUE INDEX IDX_NUMERO_EXPEDIENTE ON tbbsnm32(numexpediente);
-- ALTER TABLE tbbsnm32b ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente);
ALTER TABLE tbbsnm33 ADD FOREIGN KEY (idescritura) REFERENCES tbbsnm24 (idescritura);
ALTER TABLE tbbsnm35 ADD FOREIGN KEY (idescritura) REFERENCES tbbsnm24(idescritura);
ALTER TABLE tbbsnm36 ADD FOREIGN KEY (idescritura) REFERENCES tbbsnm24(idescritura),
                ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18 (idacto);
ALTER TABLE tbbsnm37 ADD FOREIGN KEY (iddocumento) REFERENCES tbbsnm22 (iddocumento),
                ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18 (idacto),
                ADD FOREIGN KEY (idgestor) REFERENCES tbbsnm63 (idgestor),
                ADD FOREIGN KEY(idvaluador) REFERENCES tbbsnm65(idvaluador),
                
-- ALTER TABLE tbbsnm37 ADD FOREIGN KEY(idgestor) REFERENCES tbbsnm63(idgestor);     
-- ALTER TABLE tbbsnm37 ADD FOREIGN KEY(idvaluador) REFERENCES tbbsnm65(idvaluador);     
                

                ADD FOREIGN KEY (idnotario) REFERENCES tbcfgm03 (idusuario),
				ADD FOREIGN KEY (idformatopdf) REFERENCES tbcfgm20(identificador);
ALTER TABLE tbbsnm39 ADD FOREIGN KEY (idrepresentante) REFERENCES tbbsnm21 (idcompareciente),
                ADD FOREIGN KEY (idrepresentado) REFERENCES tbbsnm21 (idcompareciente);
				
ALTER TABLE tbbsnm40 ADD FOREIGN KEY (idcliente) REFERENCES tbbsnm28 (idpersona),
                ADD FOREIGN KEY (idabogado) REFERENCES tbcfgm03 (idusuario),
                ADD FOREIGN KEY (idlocacion) REFERENCES tbcfgm91 (idelemento),
                ADD FOREIGN KEY (idstatus) REFERENCES tbbsnm41 (idetatra);
				
ALTER TABLE tbbsnm42 ADD FOREIGN KEY (idusuario) REFERENCES tbcfgm03 (idusuario),
                ADD FOREIGN KEY (idtramite) REFERENCES tbbsnm40 (idtramite),
                ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente);
				
ALTER TABLE tbbsnm44 ADD FOREIGN KEY (idexpedidopor) REFERENCES tbcfgm91 (idelemento),
                ADD FOREIGN KEY (idtipo) REFERENCES tbcfgm91 (idelemento);
	
ALTER TABLE tbbsnm45 ADD FOREIGN KEY (idtestimonio) REFERENCES tbbsnm30 (idtestimonio),
                ADD FOREIGN KEY (idetapatestimonio) REFERENCES tbbsnm38 (idetates);

-- ALTER TABLE tbbsnm46 DROP INDEX dsnombrecorto;    
ALTER TABLE tbbsnm46 ADD CONSTRAINT dsnombrecorto UNIQUE (idconFormulario, version, dsnombrecorto);
ALTER TABLE tbbsnm46 ADD FOREIGN KEY (idlocacion) REFERENCES tbcfgm91 (idelemento);
				
ALTER TABLE tbbsnm47 ADD FOREIGN KEY (idsuboperacion) REFERENCES tbbsnm17 (idsuboperacion),
                ADD FOREIGN KEY (idconformulario, versionform) REFERENCES tbbsnm46 (idconFormulario, version);
				
ALTER TABLE tbbsnm48 ADD FOREIGN KEY (idrol) REFERENCES tbcfgm07 (idrol),
                ADD FOREIGN KEY (idconformulario, versionform) REFERENCES tbbsnm46 (idconFormulario, version);
				
ALTER TABLE tbbsnm49 ADD FOREIGN KEY (idconformulario, versionform) REFERENCES tbbsnm46 (idconFormulario, version),
                ADD CONSTRAINT dsnombrecorto UNIQUE (idconFormulario, versionform, dsnombrecorto);
                
-- ALTER TABLE tbbsnm49 DROP INDEX dsnombrecorto;
-- ALTER TABLE tbbsnm49 ADD CONSTRAINT dsnombrecorto UNIQUE (idconFormulario, versionform, dsnombrecorto);

ALTER TABLE tbbsnm50 ADD FOREIGN KEY (idconformulario, versionform) REFERENCES tbbsnm46 (idconFormulario, version),
                ADD FOREIGN KEY (idsubformulario) REFERENCES tbbsnm49 (idconsubform),
				ADD FOREIGN KEY (idtipocomponente) REFERENCES tbcfgm91 (idelemento);
				
ALTER TABLE tbbsnm51 ADD FOREIGN KEY (idacto) REFERENCES tbbsnm18 (idacto),
                ADD FOREIGN KEY (idconformulario, versionform) REFERENCES tbbsnm46 (idconFormulario, version);
				
ALTER TABLE tbbsnm52 ADD FOREIGN KEY (idformulario) REFERENCES tbbsnm51 (idformulario),
                ADD FOREIGN KEY (idcomponente) REFERENCES tbbsnm50 (idcomponente);
				
ALTER TABLE tbbsnm53 
                ADD FOREIGN KEY (idformulario) REFERENCES tbbsnm51 (idformulario),
				ADD FOREIGN KEY (idcomponente) REFERENCES tbbsnm50 (idcomponente);
				
ALTER TABLE tbbsnm54 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente);

ALTER TABLE tbbsnm55 ADD FOREIGN KEY (idsolicitudpago) REFERENCES tbbsnm54 (idsolpago),
					 ADD FOREIGN KEY (status) REFERENCES tbcfgm91 (idelemento),
                     ADD FOREIGN KEY (idcompareciente) REFERENCES tbbsnm21 (idcompareciente);

ALTER TABLE tbbsnm56 ADD FOREIGN KEY (iddatofiscapago) REFERENCES tbbsnm55 (iddatofiscapago),
					 ADD FOREIGN KEY (idmediopago) REFERENCES tbcfgm91 (idelemento),
					 ADD FOREIGN KEY (idusuarioelaboro) REFERENCES tbcfgm03 (idusuario);

ALTER TABLE tbbsnm57 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente),
                     ADD FOREIGN KEY (idusragendo) REFERENCES tbcfgm03 (idusuario);

ALTER TABLE tbbsnm58 ADD FOREIGN KEY (idcita,version) REFERENCES tbbsnm57 (idcita,version),
                     ADD FOREIGN KEY (idusuario) REFERENCES tbcfgm03 (idusuario),
                     ADD FOREIGN KEY (idcompareciente) REFERENCES tbbsnm21 (idcompareciente);

ALTER TABLE tbbsnm59 ADD FOREIGN KEY (iddocnotmas) REFERENCES tbbsnm35 (iddocnotmas),
                     ADD FOREIGN KEY (idcita,version) REFERENCES tbbsnm57 (idcita,version);

ALTER TABLE tbbsnm60 ADD FOREIGN KEY (idescritura) REFERENCES tbbsnm24(idescritura);
ALTER TABLE tbbsnm60b ADD FOREIGN KEY (idacuse) REFERENCES tbbsnm60 (idacuse);
ALTER TABLE tbbsnm61 ADD FOREIGN KEY (idsujeto) REFERENCES tbbsnm21 (idcompareciente),
					ADD FOREIGN KEY (idconyugecompra) REFERENCES tbbsnm21 (idcompareciente),
					ADD FOREIGN KEY (idconyugeactual) REFERENCES tbbsnm21 (idcompareciente);
ALTER TABLE tbbsnm62 ADD FOREIGN KEY (idoperacion) REFERENCES tbbsnm27(idoperacion);					
ALTER TABLE tbbsnm63 ADD FOREIGN KEY (idlocacion) REFERENCES tbcfgm91 (idelemento);

ALTER TABLE tbbsnm64 ADD FOREIGN KEY (iddocumento) REFERENCES tbbsnm22 (iddocumento),
					ADD FOREIGN KEY (idsuboperacion) REFERENCES tbbsnm17 (idsuboperacion),
					ADD FOREIGN KEY (idlocalidad) REFERENCES tbcfgm91 (idelemento),
					ADD FOREIGN KEY (idformatopdf) REFERENCES tbcfgm20 (identificador);
ALTER TABLE tbbsnm66 ADD FOREIGN KEY (idcompareciente) REFERENCES tbbsnm21(idcompareciente),
					ADD FOREIGN KEY(idautorizante) REFERENCES tbbsnm21(idcompareciente);
ALTER TABLE tbbsnm67 ADD FOREIGN KEY (idpersona) REFERENCES tbbsnm28 (idpersona);		
ALTER TABLE tbbsnm68 ADD FOREIGN KEY (idusuariorecibe) REFERENCES tbcfgm03(idusuario),
					ADD FOREIGN KEY (idusuariopresta) REFERENCES tbcfgm03(idusuario);
ALTER TABLE tbbsnm68 ADD CONSTRAINT dsnumescritura UNIQUE (dsnumescritura);	
ALTER TABLE tbbsnm68 ADD FOREIGN KEY(dsnumescritura) REFERENCES tbbsnm24(dsnumescritura);


ALTER TABLE tbbsnm69 ADD FOREIGN KEY (idusuariorecibe) REFERENCES tbcfgm03(idusuario),
					ADD FOREIGN KEY (idusuarioentrega) REFERENCES tbcfgm03(idusuario);
--ALTER TABLE tbbsnm70 ADD FOREIGN KEY (idusuariorechaza) REFERENCES tbcfgm03(idusuario), ADD FOREIGN KEY (iddevolucionfolio) REFERENCES tbbsnm69(iddevolucionfolio);
					
--ALTER TABLE tbbsnm70 ADD FOREIGN KEY (iddevolucionfolio) REFERENCES tbbsnm69(iddevolucionfolio);	
ALTER TABLE tbbsnm72 ADD FOREIGN KEY (idexpediente) REFERENCES tbbsnm32 (idexpediente),
		ADD FOREIGN KEY (idlibro) REFERENCES tbbsnm26 (idlibro),
                ADD FOREIGN KEY (idnotario) REFERENCES tbcfgm03 (idusuario);
ALTER TABLE tbbsnm72 ADD CONSTRAINT dsnumescritura UNIQUE (dsnumescritura);		
-- ALTER TABLE tbbsnm16 ADD COLUMN isasociada INT(5) NULL DEFAULT 0 AFTER iddomicilio;


--LLAVES FORANEAS DE USUARIO TRAMITE

ALTER TABLE tbbsnm40a ADD FOREIGN KEY(idusuario) REFERENCES tbcfgm03(idusuario) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE tbbsnm40a ADD FOREIGN KEY(idtramite) REFERENCES tbbsnm40(idtramite) ON DELETE CASCADE ON UPDATE CASCADE;
