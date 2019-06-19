-- MySQL dump 10.13  Distrib 5.6.20, for Linux (x86_64)
--
-- Host: localhost    Database: notaria
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbbsnm01`
--

DROP TABLE IF EXISTS `tbbsnm01`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm01` (
  `idcomentario` varchar(32) NOT NULL,
  `dstexto` varchar(120) DEFAULT NULL,
  `idusuario` varchar(32) DEFAULT NULL,
  `idexpediente` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcomentario`),
  KEY `idexpediente` (`idexpediente`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `tbbsnm01_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`),
  CONSTRAINT `tbbsnm01_ibfk_2` FOREIGN KEY (`idusuario`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm01`
--

LOCK TABLES `tbbsnm01` WRITE;
/*!40000 ALTER TABLE `tbbsnm01` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm01` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm02`
--

DROP TABLE IF EXISTS `tbbsnm02`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm02` (
  `idtareapend` varchar(32) NOT NULL,
  `idtramite` varchar(32) DEFAULT NULL,
  `idexpediente` varchar(32) DEFAULT NULL,
  `ismanual` int(1) DEFAULT NULL,
  `dsdescripcion` varchar(60) DEFAULT NULL,
  `idprioridad` varchar(32) DEFAULT NULL,
  `idusuarioasigna` varchar(32) NOT NULL,
  `idusuariorecibe` varchar(32) NOT NULL,
  `inprioritaria` int(1) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtareapend`),
  KEY `idtramite` (`idtramite`),
  KEY `idexpediente` (`idexpediente`),
  KEY `idusuarioasigna` (`idusuarioasigna`),
  KEY `idusuariorecibe` (`idusuariorecibe`),
  CONSTRAINT `tbbsnm02_ibfk_1` FOREIGN KEY (`idtramite`) REFERENCES `tbbsnm40` (`idtramite`),
  CONSTRAINT `tbbsnm02_ibfk_2` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`),
  CONSTRAINT `tbbsnm02_ibfk_3` FOREIGN KEY (`idusuarioasigna`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm02_ibfk_4` FOREIGN KEY (`idusuariorecibe`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm02`
--

LOCK TABLES `tbbsnm02` WRITE;
/*!40000 ALTER TABLE `tbbsnm02` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm02` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm03`
--

DROP TABLE IF EXISTS `tbbsnm03`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm03` (
  `idbitfirma` varchar(32) NOT NULL,
  `idescritura` varchar(32) NOT NULL,
  `idusuariofirma` varchar(32) NOT NULL,
  `idcompareciente` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idbitfirma`),
  KEY `idescritura` (`idescritura`),
  KEY `idusuariofirma` (`idusuariofirma`),
  KEY `idcompareciente` (`idcompareciente`),
  CONSTRAINT `tbbsnm03_ibfk_1` FOREIGN KEY (`idescritura`) REFERENCES `tbbsnm24` (`idescritura`),
  CONSTRAINT `tbbsnm03_ibfk_2` FOREIGN KEY (`idusuariofirma`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm03_ibfk_3` FOREIGN KEY (`idcompareciente`) REFERENCES `tbbsnm21` (`idcompareciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm03`
--

LOCK TABLES `tbbsnm03` WRITE;
/*!40000 ALTER TABLE `tbbsnm03` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm03` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm04`
--

DROP TABLE IF EXISTS `tbbsnm04`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm04` (
  `iddocor` varchar(32) NOT NULL,
  `txdescripcion` longtext,
  `idexpediente` varchar(32) NOT NULL,
  `dscomentario` varchar(250) DEFAULT NULL,
  `iscliente` int(1) DEFAULT NULL,
  `dsrutaentrega` varchar(600) DEFAULT NULL,
  `dsrutarecibo` varchar(600) DEFAULT NULL,
  `dsentrega` varchar(250) DEFAULT NULL,
  `fechaRecivo` date DEFAULT NULL,
  `fechaEntrega` date DEFAULT NULL,
  `dsnombrecliente` varchar(250) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddocor`),
  KEY `idexpediente` (`idexpediente`),
  CONSTRAINT `tbbsnm04_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm04`
--

LOCK TABLES `tbbsnm04` WRITE;
/*!40000 ALTER TABLE `tbbsnm04` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm04` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm05`
--

DROP TABLE IF EXISTS `tbbsnm05`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm05` (
  `iddocescaneado` varchar(32) NOT NULL,
  `dsruta` varchar(600) DEFAULT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `idexpediente` varchar(32) NOT NULL,
  `fecha` date DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddocescaneado`),
  KEY `idexpediente` (`idexpediente`),
  CONSTRAINT `tbbsnm05_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm05`
--

LOCK TABLES `tbbsnm05` WRITE;
/*!40000 ALTER TABLE `tbbsnm05` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm05` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm06`
--

DROP TABLE IF EXISTS `tbbsnm06`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm06` (
  `idtareaaten` varchar(32) NOT NULL,
  `dsnombre` varchar(30) NOT NULL,
  `dsdescripcion` varchar(60) DEFAULT NULL,
  `dscomentario` varchar(120) DEFAULT NULL,
  `idexpediente` varchar(32) NOT NULL,
  `idusuarioasigna` varchar(32) NOT NULL,
  `idusuarioatiende` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtareaaten`),
  KEY `idexpediente` (`idexpediente`),
  KEY `idusuarioasigna` (`idusuarioasigna`),
  KEY `idusuarioatiende` (`idusuarioatiende`),
  CONSTRAINT `tbbsnm06_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`),
  CONSTRAINT `tbbsnm06_ibfk_2` FOREIGN KEY (`idusuarioasigna`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm06_ibfk_3` FOREIGN KEY (`idusuarioatiende`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm06`
--

LOCK TABLES `tbbsnm06` WRITE;
/*!40000 ALTER TABLE `tbbsnm06` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm06` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm07`
--

DROP TABLE IF EXISTS `tbbsnm07`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm07` (
  `idbitversesc` varchar(32) NOT NULL,
  `dsruta` varchar(600) NOT NULL,
  `dsnombre` varchar(120) NOT NULL,
  `idescritura` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `dsversion` varchar(10) NOT NULL,
  `idnodoalfresco` varchar(120) DEFAULT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idbitversesc`),
  KEY `idescritura` (`idescritura`),
  CONSTRAINT `tbbsnm07_ibfk_1` FOREIGN KEY (`idescritura`) REFERENCES `tbbsnm24` (`idescritura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm07`
--

LOCK TABLES `tbbsnm07` WRITE;
/*!40000 ALTER TABLE `tbbsnm07` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm07` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm08`
--

DROP TABLE IF EXISTS `tbbsnm08`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm08` (
  `iddocnot` varchar(32) NOT NULL,
  `idsuboperacion` varchar(32) NOT NULL,
  `dstitulo` varchar(30) NOT NULL,
  `inversion` int(5) NOT NULL DEFAULT '0',
  `dssesion` varchar(32) DEFAULT NULL,
  `ispublicado` int(1) NOT NULL,
  `txplantilla` longtext,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddocnot`,`inversion`),
  KEY `idsuboperacion` (`idsuboperacion`),
  CONSTRAINT `tbbsnm08_ibfk_1` FOREIGN KEY (`idsuboperacion`) REFERENCES `tbbsnm17` (`idsuboperacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm08`
--

LOCK TABLES `tbbsnm08` WRITE;
/*!40000 ALTER TABLE `tbbsnm08` DISABLE KEYS */;
INSERT INTO `tbbsnm08` VALUES ('a9b7ba70783b617e9998dc4dd82eb3c5','c4ca4238a0b923820dcc509a6f75849b','plantilla_cancelacion_hipoteca',1,'sesion',1,'<p>LIBRO ${var:numero_libro}.--------------------------RDG/NCG/AMR.</p>\n\n<p>${var:numero_libro(funcion=texto)}.-</p>\n\n<p>- - - -MEXICO, DISTRITO FEDERAL, a ${var:fecha_hoy(funcion=texto)}.</p>\n\n<p>${var:notaria_notario}, titular de la notaría número ${var:notaria_numero(funcion=texto)} del ${var:notaria_estado}, actuando como asociado en el protocolo de la notaría número ${var:notaria_asociada_numero(funcion=texto)}, de la que es titular el licenciado ${var:notaria_asociada_notario}, después de haberme identificado plenamente como notario, hago constar LA CANCELACIÓN DE HIPOTECA que otorga el &ldquo;${var:compareciente_nombre(tipo=PRESTADOR)}&rdquo;, quien fungió como intermediaria de la segunda en apoyo al &ndash;-------------------------------------, representada en este acto por el licenciado ${var:compareciente_nombre(tipo=PRESTADOR)}, a favor de ${var:compareciente_nombre(tipo=BENEFICIARIO)}, quien en lo sucesivo se le llamará &ldquo;${var:compareciente_nombre(tipo=COMPRADOR)}&rdquo;, al tenor de los siguientes antecedentes y cláusulas:</p>\n\n<p>--------------------A N T E C E D E N T E S</p>\n\n<p>I.- DEL CONTRATO DE APERTURA DE CREDITO CON GARANTIA HIPOTECARIA.- Por escritura número ${var:numero_escritura(funcion=texto)}, de fecha ${var:fecha_escritura(funcion=texto)}, ante el licenciado ${var:notaria_notario}, titular de la notaría número ${var:notaria_numero(funcion=texto)} del Distrito Federal, cuyo primer testimonio quedó inscrito en el Registro Público de la Propiedad de esta capital, en el folio real número ${var:inmueble_foja}, &ldquo;${var:compareciente_nombre(tipo=ACREDITADO)}, &ndash;-----------------------------------, quienes fueron los titulares últimos de los derechos de crédito, otorgó un crédito con garantía hipotecaria a &ldquo;EL DEUDOR&rdquo;, por la suma de ${var:inmueble_precio(funcion=texto)}, Moneda Nacional, equivalente a &ndash;---------------------------- el salario mínimo mensual vigente en el Distrito Federal y para garantizar el pago de la cantidad adeudada, así como sus intereses y demás accesorios legales &ldquo;EL DEUDOR&rdquo;, constituyó hipoteca en primer lugar y grado a favor del intermediario sobre la unidad privativa marcada con el número ${var:inmueble_domicilio}, y elementos comunes que le corresponden, con la superficie, medidas, colindancias y porcentaje de indiviso descritos en el instrumento referido y que se tienen aquí por íntegramente reproducidos como si a la letra se insertasen.</p>\n\n<p>Como consecuencia de dicho otorgamiento, &ldquo;EL DEUDOR&rdquo; autorizó en forma expresa a la entidad o dependencia en la que prestará sus servicios realizar los descuentos atendiendo la orden de descuento emitida por &ndash;-----------.</p>\n\n<p>II.- CESIÓN DE CRÉDITO. Por acta de cesión de créditos hipotecarios de fecha &ndash;---------------------------------, se hizo constar la cesión de créditos hipotecarios, por virtud de la cual ${var:compareciente_nombre(tipo=GARANTE HIPOTECARIO)}, transmitió al ${var:compareciente_nombre(tipo=ACREDITADO)} a través del Fondo de la Vivienda, dentro de los que se encuentra comprendido el crédito otorgado a &ldquo;EL DEUDOR&rdquo;, documento que en copia fotostática cotejada con su original agrego al apéndice de esta escritura con la letra <strong>&ldquo;A&rdquo;</strong>.</p>\n\n<p>III.- CONSTANCIA DE FINIQUITO. A solicitud del otorgante, agrego al apéndice de esta escritura bajo la letra <strong>&ldquo;B&rdquo;</strong>, en copia fotostática cotejada con su original, la Constancia de Finiquito Oficio número DRO guión SP guión DV guión cero trescientos ochenta diagonal doce, de fecha veintiocho de febrero del dos mil once, expedida por el Fondo de la Vivienda del ${var:compareciente_nombre(tipo=ACREDITADO)}, a través del &ndash;-------------------------------------.</p>\n\n<p>IV.- CARTA DE INSTRUCCIONES. El día ${var:fecha_hoy(funcion=texto)}, el ${var:compareciente_nombre(tipo=ACREDITADO)}, giró instrucciones al suscrito notario, a fin de otorgar en el protocolo a mi cargo la cancelación de hipoteca del presente instrumento, documento que agrego al apéndice de esta escritura con la letra <strong>&ldquo;C&rdquo;</strong>.</p>\n\n<p>V.- IDENTIFICACION.- En virtud de que así lo solicita el &ldquo;INSTITUTO DE SEGURIDAD Y SERVICIOS SOCIALES DE LOS TRABAJADORES DEL ESTADO&rdquo;, a través del Fondo de la Vivienda, agrego al apéndice de esta escritura con la letra <strong>&ldquo;D&rdquo;</strong>, copia fotostática de la identificación oficial de &ldquo;EL DEUDOR&rdquo;.</p>\n\n<p>&nbsp;</p>','sesion','2014-09-29 17:37:45');
/*!40000 ALTER TABLE `tbbsnm08` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm09`
--

DROP TABLE IF EXISTS `tbbsnm09`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm09` (
  `idinmueble` varchar(32) NOT NULL,
  `iddomicilio` varchar(32) NOT NULL,
  `idacto` varchar(32) NOT NULL,
  `idvocterreno` varchar(32) DEFAULT NULL,
  `idvochabitacional` varchar(32) DEFAULT NULL,
  `idvoccomercial` varchar(32) DEFAULT NULL,
  `fchinscripcion` date NOT NULL,
  `dsdescripcion` varchar(60) DEFAULT NULL,
  `cdcatastral` varchar(60) DEFAULT NULL,
  `valcatastral` double DEFAULT NULL,
  `dsctaagua` varchar(10) DEFAULT NULL,
  `dspredio` varchar(60) DEFAULT NULL,
  `dssuperficie` varchar(30) DEFAULT NULL,
  `dscolindancias` longtext,
  `ininscripcion` int(3) DEFAULT NULL,
  `dstomo` varchar(10) DEFAULT NULL,
  `dsseccion` varchar(10) DEFAULT NULL,
  `dsfoja` varchar(10) DEFAULT NULL,
  `dsvolumen` varchar(10) DEFAULT NULL,
  `dslibro` varchar(10) DEFAULT NULL,
  `dsdomcompleto` longtext,
  `isasistido` int(1) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `gravamen` double DEFAULT NULL,
  `avaluos` double DEFAULT NULL,
  `otrosavaluos` text,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idinmueble`),
  KEY `iddomicilio` (`iddomicilio`),
  KEY `idacto` (`idacto`),
  KEY `idvocterreno` (`idvocterreno`),
  KEY `idvochabitacional` (`idvochabitacional`),
  KEY `idvoccomercial` (`idvoccomercial`),
  CONSTRAINT `tbbsnm09_ibfk_1` FOREIGN KEY (`iddomicilio`) REFERENCES `tbbsnm12` (`iddomicilio`),
  CONSTRAINT `tbbsnm09_ibfk_2` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`),
  CONSTRAINT `tbbsnm09_ibfk_3` FOREIGN KEY (`idvocterreno`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm09_ibfk_4` FOREIGN KEY (`idvochabitacional`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm09_ibfk_5` FOREIGN KEY (`idvoccomercial`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm09`
--

LOCK TABLES `tbbsnm09` WRITE;
/*!40000 ALTER TABLE `tbbsnm09` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm09` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm10`
--

DROP TABLE IF EXISTS `tbbsnm10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm10` (
  `idtrjamarilla` varchar(32) NOT NULL,
  `idusuarioelab` varchar(32) NOT NULL,
  `idacto` varchar(32) NOT NULL,
  `idpersona` varchar(32) DEFAULT NULL,
  `isr` double DEFAULT NULL,
  `numesc` varchar(32) DEFAULT NULL,
  `dsnomcliente` varchar(120) DEFAULT NULL,
  `dsrfc` varchar(13) DEFAULT NULL,
  `iva` double DEFAULT NULL,
  `trasdominio` double DEFAULT NULL,
  `adquisicionlocal` double DEFAULT NULL,
  `rpp` double DEFAULT NULL,
  `erogacion` double DEFAULT NULL,
  `ideerog` double DEFAULT NULL,
  `honorario` double DEFAULT NULL,
  `porcentajeIVA` double DEFAULT NULL,
  `subhiva` double DEFAULT NULL,
  `ide` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtrjamarilla`),
  KEY `idacto` (`idacto`),
  KEY `idusuarioelab` (`idusuarioelab`),
  KEY `idpersona` (`idpersona`),
  CONSTRAINT `tbbsnm10_ibfk_1` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`),
  CONSTRAINT `tbbsnm10_ibfk_2` FOREIGN KEY (`idusuarioelab`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm10_ibfk_3` FOREIGN KEY (`idpersona`) REFERENCES `tbbsnm28` (`idpersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm10`
--

LOCK TABLES `tbbsnm10` WRITE;
/*!40000 ALTER TABLE `tbbsnm10` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm10` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm11`
--

DROP TABLE IF EXISTS `tbbsnm11`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm11` (
  `idlocalidad` varchar(32) NOT NULL,
  `dsnombre` varchar(60) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idlocalidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm11`
--

LOCK TABLES `tbbsnm11` WRITE;
/*!40000 ALTER TABLE `tbbsnm11` DISABLE KEYS */;
INSERT INTO `tbbsnm11` VALUES ('00411460f7c92d2124a67ea0f4cb5f85','Guadalupe','sesion','2014-09-29 17:39:56'),('006f52e9102a8d3be2fe5614f42ba989','Zumpango del Río','sesion','2014-09-29 17:39:49'),('00ac8ed3b4327bdd4ebbebcb2ba10a00','Cuichapa','sesion','2014-09-29 17:40:06'),('00ec53c4682d36f5c4359f4ae7bd7ba1','San Juan Bautista Valle Nacional','sesion','2014-09-29 17:39:57'),('01161aaa0b6d1345dd8fe4e481144d84','Jalostotitlán','sesion','2014-09-29 17:39:51'),('01386bd6d8e091c2ab4c7c7de644d37b','Xalisco','sesion','2014-09-29 17:39:56'),('013d407166ec4fa56eb1e1f8cbe183b9','San José Iturbide','sesion','2014-09-29 17:39:48'),('019d385eb67632a7e958e23f24bd07d7','San Andrés Cholula','sesion','2014-09-29 17:39:59'),('01f78be6f7cad02658508fe4616098a9','Villa Vicente Guerrero','sesion','2014-09-29 17:40:04'),('02522a2b2726fb0a03bb19f2d8d9524d','Dolores Hidalgo Cuna de la Independencia Nacional','sesion','2014-09-29 17:39:47'),('0266e33d3f546cb5436a10798e657d97','Lagos de Moreno','sesion','2014-09-29 17:39:52'),('02e74f10e0327ad868d138f2b4fdd6f0','Guerrero Negro','sesion','2014-09-29 17:39:43'),('0336dcbab05b9d5ad24f4333c7658a0e','Azoyú','sesion','2014-09-29 17:39:50'),('0353ab4cbed5beae847a7ff6e220b5cf','Charcas','sesion','2014-09-29 17:40:00'),('03afdbd66e7929b125f8597834fa83a4','Tecoman','sesion','2014-09-29 17:39:45'),('03c6b06952c750899bb03d998e631860','Jamay','sesion','2014-09-29 17:39:52'),('04025959b191f8f9de3f924f0940515f','Tecuala','sesion','2014-09-29 17:39:55'),('045117b0e0a11a242b9765e79cbf113f','Zihuatanejo','sesion','2014-09-29 17:39:49'),('04ecb1fa28506ccb6f72b12c0245ddbc','Lerdo de Tejada','sesion','2014-09-29 17:40:06'),('05049e90fa4f5039a8cadc6acbb4b2cc','Cadereyta Jiménez','sesion','2014-09-29 17:39:56'),('051e4e127b92f5d98d3c79b195f2b291','Sombrerete','sesion','2014-09-29 17:40:07'),('0584ce565c824b7b7f50282d9a19945b','Natividad','sesion','2014-09-29 17:39:57'),('05f971b5ec196b8c65b75d2ef8267331','La Cruz','sesion','2014-09-29 17:40:02'),('060ad92489947d410d897474079c1477','Acatlán de Juárez','sesion','2014-09-29 17:39:51'),('06138bc5af6023646ede0e1f7c1eac75','Tejupilco de Hidalgo','sesion','2014-09-29 17:39:53'),('06409663226af2f3114485aa4e0a23b4','Chilpancingo de los Bravo','sesion','2014-09-29 17:39:48'),('069059b7ef840f0c74a814ec9237b6ec','Irapuato','sesion','2014-09-29 17:39:47'),('069d3bb002acd8d7dd095917f9efe4cb','Orizaba','sesion','2014-09-29 17:40:05'),('06eb61b839a0cefee4967c67ccb099dc','Jiquilpan de Juárez','sesion','2014-09-29 17:39:54'),('072b030ba126b2f4b2374f342be9ed44','Francisco I. Madero (Chávez)','sesion','2014-09-29 17:39:45'),('07563a3fe3bbe7e3ba84431ad9d055af','Teapa','sesion','2014-09-29 17:40:03'),('076a0c97d09cf1a0ec3e19c7f2529f2b','Alvarado','sesion','2014-09-29 17:40:06'),('0777d5c17d4066b82ab86dff8a46af6f','Tierra Colorada','sesion','2014-09-29 17:39:48'),('077e29b11be80ab57e1a2ecabb7da330','Tototlán','sesion','2014-09-29 17:39:52'),('07cdfd23373b17c6b337251c22b7ea57','Naucalpan de Juárez','sesion','2014-09-29 17:39:53'),('07e1cd7dca89a1678042477183b7ac3f','Guanajuato','sesion','2014-09-29 17:39:47'),('084b6fbb10729ed4da8c3d3f5a3ae7c9','Marquelia','sesion','2014-09-29 17:39:50'),('087408522c31eeb1f982bc0eaf81d35f','Empalme','sesion','2014-09-29 17:40:02'),('08b255a5d42b89b0585260b6f2360bdd','General Miguel Alemán (Potrero Nuevo)','sesion','2014-09-29 17:40:05'),('08c5433a60135c32e34f46a71175850c','Cosamaloapan','sesion','2014-09-29 17:40:06'),('091d584fced301b442654dd8c23b3fc9','Santiago Tulantepec','sesion','2014-09-29 17:39:50'),('093f65e080a295f8076b1c5722a46aa2','San Pedro','sesion','2014-09-29 17:39:45'),('0a09c8844ba8f0936c20bd791130d6b6','Santa Cruz Juventino Rosas','sesion','2014-09-29 17:39:48'),('0aa1883c6411f7873cb83dacb17b0afc','Tlalixtaquilla','sesion','2014-09-29 17:39:49'),('0bb4aec1710521c12ee76289d9440817','San pedro Lagunillas','sesion','2014-09-29 17:39:56'),('0c74b7f78409a4022a2c4c5a5ca3ee19','Linares','sesion','2014-09-29 17:39:57'),('0d0fd7c6e093f7b804fa0150b875b868','San Pedro Pochutla','sesion','2014-09-29 17:39:58'),('0deb1c54814305ca9ad266f53bc82511','Santa Cruz Itundujia','sesion','2014-09-29 17:39:58'),('0e01938fc48a2cfb5f2217fbfb00722d','Tultitlán de Mariano Escobedo','sesion','2014-09-29 17:39:53'),('0e65972dce68dad4d52d063967f0a705','Pachuca de Soto','sesion','2014-09-29 17:39:50'),('0f28b5d49b3020afeecd95b4009adf4c','Apaseo el Grande','sesion','2014-09-29 17:39:48'),('0f49c89d1e7298bb9930789c8ed59d48','Cuautitlán','sesion','2014-09-29 17:39:53'),('0fcbc61acd0479dc77e3cccc0f5ffca7','Nuevo Laredo','sesion','2014-09-29 17:40:03'),('1068c6e4c8051cfd4e9ea8072e3189e2','Bahias de Huatulco','sesion','2014-09-29 17:39:58'),('10a7cdd970fe135cf4f7bb55c0e3b59f','Navojoa','sesion','2014-09-29 17:40:02'),('115f89503138416a242f40fb7d7f338e','Zacoalco de Torres','sesion','2014-09-29 17:39:51'),('11b921ef080f7736089c757404650e40','Veracruz','sesion','2014-09-29 17:40:04'),('11b9842e0a271ff252c1903e7132cd68','Puruándiro','sesion','2014-09-29 17:39:54'),('1385974ed5904a438616ff7bdb3f7439','Rincón de Tamayo','sesion','2014-09-29 17:39:48'),('138bb0696595b338afbab333c555292a','Monterrey','sesion','2014-09-29 17:39:56'),('13f320e7b5ead1024ac95c3b208610db','San Fernando','sesion','2014-09-29 17:40:03'),('13f3cf8c531952d72e5847c4183e6910','Tepeaca','sesion','2014-09-29 17:39:59'),('13f9896df61279c928f19721878fac41','Bucerías','sesion','2014-09-29 17:39:56'),('13fe9d84310e77f13a6d184dbf1232f3','Jocotepec','sesion','2014-09-29 17:39:51'),('140f6969d5213fd0ece03148e62e461e','Ocotito','sesion','2014-09-29 17:39:48'),('142949df56ea8ae0be8b5306971900a4','San Juan Bautista Tuxtepec','sesion','2014-09-29 17:39:57'),('149e9677a5989fd342ae44213df68868','Buenavista de Cuellar','sesion','2014-09-29 17:39:49'),('14bfa6bb14875e45bba028a21ed38046','Chiapa de Corzo','sesion','2014-09-29 17:39:45'),('1534b76d325a8f591b52d302e7181331','Ciudad de Fray Bernardino de Sahagún','sesion','2014-09-29 17:39:50'),('1543843a4723ed2ab08e18053ae6dc5b','San Francisco Ixhuatán','sesion','2014-09-29 17:39:58'),('158f3069a435b314a80bdcb024f8e422','Jacona de Plancarte','sesion','2014-09-29 17:39:54'),('15d4e891d784977cacbfcbb00c48f133','Izúcar de Matamoros','sesion','2014-09-29 17:39:59'),('15de21c670ae7c3f6f3f1f37029303c9','José Cardel','sesion','2014-09-29 17:40:04'),('1651cf0d2f737d7adeab84d339dbabd3','Teziutlan','sesion','2014-09-29 17:39:59'),('1679091c5a880faf6fb5e6087eb1b2dc','Pabellón de Arteaga','sesion','2014-09-29 17:39:43'),('16a5cdae362b8d27a1d8f8c7b78b4330','Santa Maria Tultepec','sesion','2014-09-29 17:39:53'),('16c222aa19898e5058938167c8ab6c57','González','sesion','2014-09-29 17:40:04'),('1700002963a49da13542e0726b7bb758','Texcoco de Mora','sesion','2014-09-29 17:39:53'),('1728efbda81692282ba642aafd57be3a','Huayacocotla','sesion','2014-09-29 17:40:05'),('17d63b1625c816c22647a73e1482372b','Putla Villa de Guerrero','sesion','2014-09-29 17:39:58'),('17e62166fc8586dfa4d1bc0e1742c08b','Castaños','sesion','2014-09-29 17:39:44'),('182be0c5cdcd5072bb1864cdee4d3d6e','Champotón','sesion','2014-09-29 17:39:44'),('185c29dc24325934ee377cfda20e414c','Luis Moya','sesion','2014-09-29 17:40:07'),('18997733ec258a9fcaf239cc55d53363','Río Grande o Piedra Parada','sesion','2014-09-29 17:39:59'),('18d8042386b79e2c279fd162df0205c8','Tlacolula de Matamoros','sesion','2014-09-29 17:39:58'),('19ca14e7ea6328a42e0eb13d585e4c22','Pomuch','sesion','2014-09-29 17:39:44'),('19f3cd308f1455b3fa09a282e0d496f4','Ocotlán','sesion','2014-09-29 17:39:52'),('1a5b1e4daae265b790965a275b53ae50','Tehuacan','sesion','2014-09-29 17:40:00'),('1afa34a7f984eeabdbb0a7d494132ee5','Huanímaro','sesion','2014-09-29 17:39:47'),('1bb91f73e9d31ea2830a5e73ce3ed328','Tempoal de Sánchez','sesion','2014-09-29 17:40:04'),('1be3bc32e6564055d5ca3e5a354acbef','Estación Naranjo','sesion','2014-09-29 17:40:02'),('1c383cd30b7c298ab50293adfecb7b18','Hecelchakán','sesion','2014-09-29 17:39:44'),('1c9ac0159c94d8d0cbedc973445af2da','Santiago Maravatío','sesion','2014-09-29 17:39:48'),('1d7f7abc18fcb43975065399b0d1e48e','Salvatierra','sesion','2014-09-29 17:39:48'),('1f0e3dad99908345f7439f8ffabdffc4','San José del Cabo','sesion','2014-09-29 17:39:43'),('1ff1de774005f8da13f42943881c655f','Heroica Mulegé','sesion','2014-09-29 17:39:43'),('1ff8a7b5dc7a7d1f0ed65aaa29c04b1e','Arcelia','sesion','2014-09-29 17:39:49'),('202cb962ac59075b964b07152d234b70','San Francisco del Rincón','sesion','2014-09-29 17:39:47'),('2050e03ca119580f74cca14cc6e97462','Tierra Nueva','sesion','2014-09-29 17:40:01'),('20f07591c6fcb220ffe637cda29bb3f6','Ciudad Adolfo López Mateos','sesion','2014-09-29 17:39:53'),('218a0aefd1d1a4be65601cc6ddc1520e','Guasave','sesion','2014-09-29 17:40:01'),('233509073ed3432027d48b1a83f5fbd2','Ciudad Cuauhtémoc','sesion','2014-09-29 17:40:07'),('2421fcb1263b9530df88f7f002e78ea5','Huauchinango','sesion','2014-09-29 17:39:59'),('248e844336797ec98478f85e7626de4a','San Pedro Cholula','sesion','2014-09-29 17:39:59'),('24b16fede9a67c9251d3e7c7161c83ac','Oaxaca de Juárez','sesion','2014-09-29 17:39:57'),('250cf8b51c773f3f8dc8b4be867a9a02','Playa del Carmen','sesion','2014-09-29 17:40:00'),('258be18e31c8188555c2ff05b4d542c3','Boca del Río','sesion','2014-09-29 17:40:05'),('25b2822c2f5a3230abfadd476e8b04c9','San Juan Bautista lo de Soto','sesion','2014-09-29 17:39:59'),('25ddc0f8c9d3e22e03d3076f98d83cb2','Rioverde','sesion','2014-09-29 17:40:01'),('26337353b7962f533d78c762373b3318','Cerritos','sesion','2014-09-29 17:40:01'),('26657d5ff9020d2abefe558796b99584','Nuevo Casas Grandes','sesion','2014-09-29 17:39:46'),('26dd0dbc6e3f4c8043749885523d6a25','Jalpa','sesion','2014-09-29 17:40:07'),('26e359e83860db1d11b6acca57d8ea88','Chalco de Díaz Covarrubias','sesion','2014-09-29 17:39:54'),('2723d092b63885e0d7c260cc007e8b9d','San Juan del Río del Centauro del Norte','sesion','2014-09-29 17:39:46'),('274ad4786c3abca69fa097b85867d9a4','Tepeji del Rio','sesion','2014-09-29 17:39:50'),('2838023a778dfaecdc212708f721b788','Allende','sesion','2014-09-29 17:39:44'),('285e19f20beded7d215102b49d5c09a0','Sonoita','sesion','2014-09-29 17:40:02'),('289dff07669d7a23de0ef88d2f7129e7','Ahualulco de Mercado','sesion','2014-09-29 17:39:51'),('28dd2c7955ce926456240b2ff0100bde','Las Margaritas','sesion','2014-09-29 17:39:45'),('28f0b864598a1291557bed248a998d4e','Unión Hidalgo','sesion','2014-09-29 17:39:58'),('298f95e1bf9136124592c8d4825a06fc','Ciudad Miguel Alemán','sesion','2014-09-29 17:40:03'),('2a38a4a9316c49e5a833517c45d31070','Tapachula de Córdova y Ordóñez','sesion','2014-09-29 17:39:46'),('2a79ea27c279e471f4d180b08d62b00a','Yuriria','sesion','2014-09-29 17:39:48'),('2b24d495052a8ce66358eb576b8912c8','Villagrán','sesion','2014-09-29 17:39:48'),('2b44928ae11fb9384c4cf38708677c48','El Salto','sesion','2014-09-29 17:39:47'),('2b8a61594b1f4c4db0902a8a395ced93','Jalpa de Méndez','sesion','2014-09-29 17:40:02'),('2bb232c0b13c774965ef8558f0fbd615','Emiliano Zapata','sesion','2014-09-29 17:40:03'),('2d6cc4b2d139a53512fb8cbb3086ae2e','Agua Prieta','sesion','2014-09-29 17:40:02'),('2f2b265625d76a6704b08093c652fd79','Jojutla','sesion','2014-09-29 17:39:55'),('2f55707d4193dc27118a0f19a1985716','Sinaloa de Leyva','sesion','2014-09-29 17:40:02'),('30bb3825e8f631cc6075c0f87bb4978c','Atoyac','sesion','2014-09-29 17:40:05'),('310dcbbf4cce62f762a2aaa148d556bd','Galeana','sesion','2014-09-29 17:39:55'),('31fefc0e570cb3860f2a6d4b38c6490d','Olinalá','sesion','2014-09-29 17:39:49'),('320722549d1751cf3f247855f937b982','Las Guacamayas','sesion','2014-09-29 17:39:54'),('3295c76acbf4caaed33c36b1b5fc2cb1','Ciudad de Villa de Álvarez','sesion','2014-09-29 17:39:45'),('32bb90e8976aab5298d5da10fe66f21d','Reforma','sesion','2014-09-29 17:39:45'),('335f5352088d7d9bf74191e006d8e24c','San Ignacio Cerro Gordo','sesion','2014-09-29 17:39:51'),('33e75ff09dd601bbe69f351039152189','San Francisco de Campeche','sesion','2014-09-29 17:39:44'),('33e8075e9970de0cfea955afd4644bb2','Ciudad Camargo','sesion','2014-09-29 17:40:03'),('3416a75f4cea9109507cacd8e2f2aefc','Frontera','sesion','2014-09-29 17:39:44'),('34173cb38f07f89ddbebc2ac9128303f','Sabancuy','sesion','2014-09-29 17:39:44'),('3435c378bb76d4357324dd7e69f3cd18','Isla','sesion','2014-09-29 17:40:06'),('3493894fa4ea036cfc6433c3e2ee63b0','Paso del Macho','sesion','2014-09-29 17:40:05'),('34ed066df378efacc9b924ec161e7639','Ciudad Nezahualcoyotl','sesion','2014-09-29 17:39:54'),('35051070e572e47d2c26c241ab88307f','Mazatlán','sesion','2014-09-29 17:40:02'),('352fe25daf686bdb4edca223c921acea','Lagunas','sesion','2014-09-29 17:39:58'),('357a6fdf7642bf815a88822c447d9dc4','Santiago Ixcuintla','sesion','2014-09-29 17:39:55'),('35f4a8d465e6e1edc05f3d8ab658c551','Venustiano Carranza','sesion','2014-09-29 17:39:45'),('3636638817772e42b59d74cff571fbb3','Taxco de Alarcón','sesion','2014-09-29 17:39:49'),('3644a684f98ea8fe223c713b77189a77','Zimapan','sesion','2014-09-29 17:39:50'),('36660e59856b4de58a219bcf4e27eba3','Sayula','sesion','2014-09-29 17:39:52'),('371bce7dc83817b7893bcdeed13799b5','Ixtlán del Río','sesion','2014-09-29 17:39:56'),('37693cfc748049e45d87b8c7d8b9aacd','Loreto','sesion','2014-09-29 17:39:43'),('37a749d808e46495a8da1e5352d03cae','Tarandacuao','sesion','2014-09-29 17:39:48'),('37bc2f75bf1bcfe8450a1a41c200364c','Zacapu','sesion','2014-09-29 17:39:54'),('37f0e884fbad9667e38940169d0a3c95','Ciudad Tula','sesion','2014-09-29 17:40:03'),('3871bd64012152bfb53fdf04b401193f','Río Grande','sesion','2014-09-29 17:40:07'),('38913e1d6a7b94cb0f55994f679f5956','Huimanguillo','sesion','2014-09-29 17:40:02'),('389bc7bb1e1c2a5e7e147703232a88f6','Heroica Ciudad de Cananea','sesion','2014-09-29 17:40:02'),('38af86134b65d0f10fe33d30dd76442e','Ciudad Altamirano','sesion','2014-09-29 17:39:49'),('38b3eff8baf56627478ec76a704e9b52','Delicias','sesion','2014-09-29 17:39:46'),('38db3aed920cf82ab059bfccbd02be6a','Unión de San Antonio','sesion','2014-09-29 17:39:52'),('39059724f73a9969845dfe4146c5660e','Amatepec','sesion','2014-09-29 17:39:53'),('39461a19e9eddfb385ea76b26521ea48','Mariscala de Juárez','sesion','2014-09-29 17:39:57'),('3988c7f88ebcb58c6ce932b957b6f332','Doctor Mora','sesion','2014-09-29 17:39:48'),('3a0772443a0739141292a5429b952fe6','Tantoyuca','sesion','2014-09-29 17:40:04'),('3ad7c2ebb96fcba7cda0cf54a2e802f5','Compostela','sesion','2014-09-29 17:39:56'),('3b8a614226a953a8cd9526fca6fe9ba5','Tala','sesion','2014-09-29 17:39:50'),('3c59dc048e8850243be8079a5c74d079','Ciudad Constitución','sesion','2014-09-29 17:39:43'),('3c7781a36bcd6cf08c11a970fbe0e2a6','San Juan Cacahuatepec','sesion','2014-09-29 17:39:59'),('3cec07e9ba5f5bb252d13f5f431e4bbb','San Diego de Alejandría','sesion','2014-09-29 17:39:52'),('3cf166c6b73f030b4f67eeaeba301103','El rosario','sesion','2014-09-29 17:40:02'),('3dc4876f3f08201c7c76cb71fa1da439','Huatabampo','sesion','2014-09-29 17:40:02'),('3dd48ab31d016ffcbf3314df2b3cb9ce','Villa Hidalgo (El Nuevo)','sesion','2014-09-29 17:39:55'),('3def184ad8f4755ff269862ea77393dd','Ciudad Manuel Doblado','sesion','2014-09-29 17:39:47'),('3ef815416f775098fe977004015c6193','Pijijiapan','sesion','2014-09-29 17:39:46'),('3fe94a002317b5f9259f82690aeea4cd','Paracho de Verduzco','sesion','2014-09-29 17:39:54'),('40008b9a5380fcacce3976bf7c08af5b','Francisco I. Madero (Puga)','sesion','2014-09-29 17:39:55'),('41ae36ecb9b3eee609d05b90c14222fb','Villa de Zaachila','sesion','2014-09-29 17:39:59'),('41f1f19176d383480afa65d325c06ed0','Doctor Arroyo','sesion','2014-09-29 17:39:57'),('428fca9bc1921c25c5121f9da7815cde','Matehuala','sesion','2014-09-29 17:40:00'),('42998cf32d552343bc8e460416382dca','Bacalar','sesion','2014-09-29 17:40:00'),('42a0e188f5033bc65bf8d78622277c4e','San Luis de la Paz','sesion','2014-09-29 17:39:48'),('42e77b63637ab381e8be5f8318cc28a2','Fresnillo','sesion','2014-09-29 17:40:07'),('42e7aaa88b48137a16a1acd04ed91125','Santa Lucia del Camino','sesion','2014-09-29 17:39:59'),('432aca3a1e345e339f35a30c8f65edce','Apatzingán de la Constitución','sesion','2014-09-29 17:39:54'),('43ec517d68b6edd3015b3edc9a11367b','Jiquipilas','sesion','2014-09-29 17:39:45'),('44c4c17332cace2124a1a836d9fc4b6f','Catemaco','sesion','2014-09-29 17:40:06'),('44f683a84163b3523afe57c2e008bc8c','Colima','sesion','2014-09-29 17:39:45'),('45645a27c4f1adc8a7a835976064a86d','Ojocaliente','sesion','2014-09-29 17:40:07'),('45c48cce2e2d7fbdea1afc51c7c6ad26','Jesús María','sesion','2014-09-29 17:39:43'),('45fbc6d3e05ebd93369ce542e8f2322d','Tlaquepaque','sesion','2014-09-29 17:39:51'),('46922a0880a8f11f8f69cbb52b1396be','Fortín de las Flores','sesion','2014-09-29 17:40:05'),('46ba9f2a6976570b0353203ec4474217','Melchor Ocampo','sesion','2014-09-29 17:39:53'),('4734ba6f3de83d861c3176a6273cac6d','Metepec','sesion','2014-09-29 17:39:53'),('47d1e990583c9c67424d369f3414728e','Jaral del Progreso','sesion','2014-09-29 17:39:48'),('48ab2f9b45957ab574cf005eb8a76760','Víctor Rosales','sesion','2014-09-29 17:40:07'),('48aedb8880cab8c45637abc7493ecddd','Zumpango','sesion','2014-09-29 17:39:53'),('49182f81e6a13cf5eaa496d51fea6406','San Vicente Chicoloapan de Juárez','sesion','2014-09-29 17:39:54'),('496e05e1aea0a9c4655800e8a7b9ea28','Cuitzeo del Porvenir','sesion','2014-09-29 17:39:54'),('49ae49a23f67c759bf4fc791ba842aa2','Isla Mujeres','sesion','2014-09-29 17:40:00'),('4c56ff4ce4aaf9573aa5dff913df997a','Romita','sesion','2014-09-29 17:39:47'),('4c5bde74a8f110656874902f07378009','San Luis de la Loma','sesion','2014-09-29 17:39:49'),('4e4b5fbbbb602b6d35bea8460aa8f8e5','El Higo','sesion','2014-09-29 17:40:04'),('4e732ced3463d06de0ca9a15b6153677','Villa Alberto Andrés Alvarado Arámburo','sesion','2014-09-29 17:39:43'),('4f4adcbf8c6f66dcfc8a3282ac2bf10a','Salina Cruz','sesion','2014-09-29 17:39:58'),('4f6ffe13a5d75b2d6a3923922b3922e5','Teotitlán de Flores Magón','sesion','2014-09-29 17:39:57'),('502e4a16930e414107ee22b6198c578f','Autlán de Navarro','sesion','2014-09-29 17:39:52'),('51d92be1c60d1db1d2e5e7a07da55b26','Salinas de Hidalgo','sesion','2014-09-29 17:40:00'),('52720e003547c70561bf5e03b95aa99f','San Nicolás de los Garza','sesion','2014-09-29 17:39:56'),('539fd53b59e3bb12d203f45a912eeaf2','San Miguel el Alto','sesion','2014-09-29 17:39:51'),('53c3bce66e43be4f209556518c2fcb54','Chiconcuac','sesion','2014-09-29 17:39:54'),('53fde96fcc4b4ce72d7739202324cd49','Tenosique de Pino Suárez','sesion','2014-09-29 17:40:03'),('54229abfcfa5649e7003b83dd4755294','Motozintla de Mendoza','sesion','2014-09-29 17:39:46'),('550a141f12de6341fba65b0ad0433500','Tecamachalco','sesion','2014-09-29 17:40:00'),('555d6702c950ecb729a966504af0a635','Arandas','sesion','2014-09-29 17:39:51'),('559cb990c9dffd8675f6bc2186971dc2','Angostura','sesion','2014-09-29 17:40:02'),('55a7cf9c71f1c9c495413f934dd1a158','Choix','sesion','2014-09-29 17:40:02'),('5737034557ef5b8c02c0e46513b98f90','Ciudad Madero','sesion','2014-09-29 17:40:03'),('5737c6ec2e0716f3d8a7a5c4e0de0d9a','Maravatío de Ocampo','sesion','2014-09-29 17:39:55'),('577bcc914f9e55d5e4e4f82f9f00e7d4','Morelia','sesion','2014-09-29 17:39:54'),('577ef1154f3240ad5b9b413aa7346a1e','San Juan de los Lagos','sesion','2014-09-29 17:39:51'),('57aeee35c98205091e18d1140e9f38cf','Colotlán','sesion','2014-09-29 17:39:51'),('58238e9ae2dd305d79c2ebc8c1883422','Ruiz','sesion','2014-09-29 17:39:56'),('5878a7ab84fb43402106c575658472fa','Tepecoacuilco de Trujano','sesion','2014-09-29 17:39:49'),('58a2fc6ed39fd083f55d4182bf88826d','San Luis Acatlán','sesion','2014-09-29 17:39:49'),('58ae749f25eded36f486bc85feb3f0ab','Naranjos','sesion','2014-09-29 17:40:04'),('58d4d1e7b1e97b258c9ed0b37e02d087','Mérida','sesion','2014-09-29 17:40:06'),('598b3e71ec378bd83e0a727608b5db01','Tamasopo','sesion','2014-09-29 17:40:01'),('59b90e1005a220e2ebc542eb9d950b1e','Villahermosa','sesion','2014-09-29 17:40:02'),('5a4b25aaed25c2ee1b74de72dc03c14e','Heroica Ciudad de Tlaxiaco','sesion','2014-09-29 17:39:58'),('5b69b9cb83065d403869739ae7f0995e','San Luis Río Colorado','sesion','2014-09-29 17:40:02'),('5b8add2a5d98b1a652ea7fd72d942dac','Los Reyes de Salgado','sesion','2014-09-29 17:39:54'),('5d44ee6f2c3f71b73125876103c8f6c4','Motul de Carrillo Puerto','sesion','2014-09-29 17:40:06'),('5e388103a391daabe3de1d76a6739ccd','Paso de Ovejas','sesion','2014-09-29 17:40:04'),('5ea1649a31336092c05438df996a3e59','Ciudad Río Bravo','sesion','2014-09-29 17:40:03'),('5ef059938ba799aaa845e1c2e8a762bd','Santa María del Oro','sesion','2014-09-29 17:39:47'),('5ef0b4eba35ab2d6180b0bca7e46b6f9','Fracción el Refugio','sesion','2014-09-29 17:40:01'),('5ef698cd9fe650923ea331c15af3b160','El cercado','sesion','2014-09-29 17:39:56'),('5f93f983524def3dca464469d2cf9f3e','Santiago Papasquiaro','sesion','2014-09-29 17:39:46'),('5fd0b37cd7dbbb00f97ba6ce92bf5add','Vicente Guerrero','sesion','2014-09-29 17:39:47'),('605ff764c617d3cd28dbbdd72be8f9a2','Huiloapan de Cuauhtémoc','sesion','2014-09-29 17:40:05'),('621bf66ddb7c962aa0d22ac97d69b793','Tepatitlán de Morelos','sesion','2014-09-29 17:39:52'),('63538fe6ef330c13a05a3ed7e599d5f7','Cunduacán','sesion','2014-09-29 17:40:03'),('6364d3f0f495b6ab9dcf8d3b5c6e0b01','Escárcega','sesion','2014-09-29 17:39:44'),('63923f49e5241343aa7acb6a06a751e7','Ocoyoacac','sesion','2014-09-29 17:39:53'),('63dc7ed1010d3c3b8269faf0ba7491d4','Tlajomulco de Zúñiga','sesion','2014-09-29 17:39:51'),('642e92efb79421734881b53e1e1b18b6','Ciudad Melchor Múzquiz','sesion','2014-09-29 17:39:44'),('647bba344396e7c8170902bcf2e15551','Tlaxcala (Tlaxcala de Xicotencatl)','sesion','2014-09-29 17:40:04'),('6512bd43d9caa6e02c990b0a82652dca','Tecate','sesion','2014-09-29 17:39:43'),('65658fde58ab3c2b6e5132a39fae7cb9','Reynosa','sesion','2014-09-29 17:40:03'),('65b9eea6e1cc6bb9f0cd2a47751a186f','José Mariano Jiménez','sesion','2014-09-29 17:39:46'),('65ded5353c5ee48d0b7d48c591b8f430','León de los Aldama','sesion','2014-09-29 17:39:47'),('66368270ffd51418ec58bd793f2d9b1b','Cuautlancingo','sesion','2014-09-29 17:39:59'),('66808e327dc79d135ba18e051673d906','Zimatlán de Álvarez','sesion','2014-09-29 17:39:59'),('66f041e16a60928b05a7e228a89c3799','Cuatro Ciénegas de Carranza','sesion','2014-09-29 17:39:45'),('6766aa2750c19aad2fa1b32f36ed4aee','Nochistlán de Mejía','sesion','2014-09-29 17:40:07'),('67c6a1e7ce56d3d6fa748ab6d9af3fd7','Ciudad Acuña','sesion','2014-09-29 17:39:44'),('67f7fb873eaf29526a11a9b7ac33bfac','Ciudad Serdán','sesion','2014-09-29 17:40:00'),('6855456e2fe46a9d49d3d3af4f57443d','Tepic','sesion','2014-09-29 17:39:55'),('6883966fd8f918a4aa29be29d2c386fb','Chimalhuacán','sesion','2014-09-29 17:39:54'),('68ce199ec2c5517597ce0a4d89620f55','Ébano','sesion','2014-09-29 17:40:00'),('68d30a9594728bc39aa24be94b319d21','Tonalá','sesion','2014-09-29 17:39:45'),('69421f032498c97020180038fddb8e24','Heroica Matamoros','sesion','2014-09-29 17:40:03'),('6974ce5ac660610b44d9b9fed0ff9548','Santa Rosalía de Camargo','sesion','2014-09-29 17:39:46'),('698d51a19d8a121ce581499d7b701668','Peñón Blanco','sesion','2014-09-29 17:39:46'),('69adc1e107f7f7d035d7baf04342e1ca','Tulancingo','sesion','2014-09-29 17:39:50'),('69cb3ea317a32c4e6143e665fdb20b14','San Pedro Totolapa','sesion','2014-09-29 17:39:58'),('6a10bbd480e4c5573d8f3af73ae0454b','Moyahua de Estrada','sesion','2014-09-29 17:40:07'),('6a9aeddfc689c1d0e3b9ccc3ab651bc5','Cuautitlán Izcalli','sesion','2014-09-29 17:39:53'),('6aca97005c68f1206823815f66102863','Playa Vicente','sesion','2014-09-29 17:40:06'),('6c4b761a28b734fe93831e3fb400ce87','Uriangato','sesion','2014-09-29 17:39:48'),('6c524f9d5d7027454a783c841250ba71','Ciénega de Flores','sesion','2014-09-29 17:39:56'),('6c8349cc7260ae62e3b1396831a8398f','Piedras Negras','sesion','2014-09-29 17:39:44'),('6c9882bbac1c7093bd25041881277658','Atotonilco el Alto','sesion','2014-09-29 17:39:52'),('6cdd60ea0045eb7a6ec44c54d29ed402','Atoyac de Álvarez','sesion','2014-09-29 17:39:49'),('6da37dd3139aa4d9aa55b8d237ec5d4a','Santa Rosa Treinta','sesion','2014-09-29 17:39:55'),('6da9003b743b65f4c0ccd295cc484e57','Tequila','sesion','2014-09-29 17:39:51'),('6e2713a6efee97bacb63e52c54f0ada0','Tampico Alto','sesion','2014-09-29 17:40:04'),('6ea2ef7311b482724a9b7b0bc0dd85c6','Navolato','sesion','2014-09-29 17:40:01'),('6ea9ab1baa0efb9e19094440c317e21b','Ciudad del Carmen','sesion','2014-09-29 17:39:44'),('6ecbdd6ec859d284dc13885a37ce8d81','Santiago Jamiltepec','sesion','2014-09-29 17:39:59'),('6f3ef77ac0e3619e98159e9b6febf557','Apan','sesion','2014-09-29 17:39:50'),('6f4922f45568161a8cdf4ad2299f6d23','Todos Santos','sesion','2014-09-29 17:39:43'),('6faa8040da20ef399b63a72d0e4ab575','Puente de Ixtla','sesion','2014-09-29 17:39:55'),('705f2172834666788607efbfca35afb3','Poncitlán','sesion','2014-09-29 17:39:51'),('70c639df5e30bdee440e4cdf599fec2b','Ciudad Ixtepec','sesion','2014-09-29 17:39:58'),('70efdf2ec9b086079795c442636b55fb','La Paz','sesion','2014-09-29 17:39:43'),('72b32a1f754ba1c09b3695e0cb6cde7f','Nadadores','sesion','2014-09-29 17:39:45'),('73278a4a86960eeb576a8fd4c9ec6997','Nombre de Dios','sesion','2014-09-29 17:39:47'),('735b90b4568125ed6c3f678819b6e058','Tuxtla Gutiérrez','sesion','2014-09-29 17:39:45'),('7380ad8a673226ae47fce7bff88e9c33','Escuinapa de Hidalgo','sesion','2014-09-29 17:40:02'),('74071a673307ca7459bcf75fbd024e09','Tamazunchale','sesion','2014-09-29 17:40:01'),('74db120f0a8e5646ef5a30154e9f6deb','Huejuquilla el Alto','sesion','2014-09-29 17:39:51'),('757b505cfd34c64c85ca5b5690ee5293','Actopan','sesion','2014-09-29 17:39:50'),('758874998f5bd0c393da094e1967a72b','Cotija de la Paz','sesion','2014-09-29 17:39:54'),('75fc093c0ee742f6dddaa13fff98f104','San Pedro Mixtepec -Dto. 22-','sesion','2014-09-29 17:39:59'),('7647966b7343c29048673252e490f736','Puerto Madero (San Benito)','sesion','2014-09-29 17:39:46'),('766ebcd59621e305170616ba3d3dac32','Cuitláhuac','sesion','2014-09-29 17:40:05'),('76dc611d6ebaafc66cc0879c71b5db5c','Pénjamo','sesion','2014-09-29 17:39:47'),('7750ca3559e5b8e1f44210283368fc16','Kanasín','sesion','2014-09-29 17:40:06'),('7a614fd06c325499f1680b9896beedeb','San Mateo Atenco','sesion','2014-09-29 17:39:53'),('7bcdf75ad237b8e02e301f4091fb6bc8','Tampico','sesion','2014-09-29 17:40:03'),('7cbbc409ec990f19c78c75bd1e06f215','San Cristóbal de las Casas','sesion','2014-09-29 17:39:45'),('7d04bbbe5494ae9d2f5a76aa1c00fa2f','Los Mochis','sesion','2014-09-29 17:40:01'),('7dcd340d84f762eba80aa538b0c527f7','Estación Manuel (Úrsulo Galván)','sesion','2014-09-29 17:40:04'),('7e7757b1e12abcb736ab9a754ffb617a','Huitzuco','sesion','2014-09-29 17:39:49'),('7eabe3a1649ffa2b3ff8c02ebfd5659f','Huejutla de Reyes','sesion','2014-09-29 17:39:50'),('7eacb532570ff6858afd2723755ff790','Cuilápam de Guerrero','sesion','2014-09-29 17:39:59'),('7ef605fc8dba5425d6965fbd4c8fbe1f','Jerécuaro','sesion','2014-09-29 17:39:48'),('7f100b7b36092fb9b06dfb4fac360931','Xonacatlán','sesion','2014-09-29 17:39:53'),('7f1de29e6da19d22b51c68001e7e0e54','San Diego de la Unión','sesion','2014-09-29 17:39:47'),('7f24d240521d99071c93af3917215ef7','Xalapa-Enríquez','sesion','2014-09-29 17:40:04'),('7f39f8317fbdb1988ef4c628eba02591','Parras de la Fuente','sesion','2014-09-29 17:39:45'),('7f6ffaa6bb0b408017b62254211691b5','Francisco I. Madero','sesion','2014-09-29 17:39:47'),('7fe1f8abaad094e0b5cb1b01d712f708','Soledad de Graciano Sánchez','sesion','2014-09-29 17:40:00'),('812b4ba287f5ee0bc9d43bbf5bbe87fb','Bachíniva','sesion','2014-09-29 17:39:46'),('81448138f5f163ccdba4acc69819f280','Xicoténcatl','sesion','2014-09-29 17:40:04'),('816b112c6105b3ebd537828a39af4818','San Pablo Villa de Mitla','sesion','2014-09-29 17:39:58'),('819f46e52c25763a55cc642422644317','Acaponeta','sesion','2014-09-29 17:39:55'),('82161242827b703e6acf9c726942a1e4','Cutzamala de Pinzón','sesion','2014-09-29 17:39:49'),('821fa74b50ba3f7cba1e6c53e8fa6845','Cozumel','sesion','2014-09-29 17:40:00'),('82aa4b0af34c2313a562076992e50aa3','Acapulco de Juárez','sesion','2014-09-29 17:39:48'),('82cec96096d4281b7c95cd7e74623496','Ciudad Sabinas Hidalgo','sesion','2014-09-29 17:39:56'),('839ab46820b524afda05122893c2fe8e','Tequixquiac','sesion','2014-09-29 17:39:53'),('84d9ee44e457ddef7f2c4f25dc8fa865','Ixmiquilpan','sesion','2014-09-29 17:39:50'),('851ddf5058cf22df63d3344ad89919cf','Las Choapas','sesion','2014-09-29 17:40:06'),('85422afb467e9456013a2a51d4dff702','Valle Hermoso','sesion','2014-09-29 17:40:03'),('854d6fae5ee42911677c739ee1734486','Tula de Allende','sesion','2014-09-29 17:39:50'),('854d9fca60b4bd07f9bb215d59ef5561','Topolobampo','sesion','2014-09-29 17:40:01'),('85d8ce590ad8981ca2c8286f79f59954','Cuajinicuilapa','sesion','2014-09-29 17:39:50'),('85fc37b18c57097425b52fc7afbb6969','Zacatecas','sesion','2014-09-29 17:40:06'),('8613985ec49eb8f757ae6439e879bb2a','Cacahoatán','sesion','2014-09-29 17:39:46'),('877a9ba7a98f75b90a9d49f53f15a858','Ciudad del Maíz','sesion','2014-09-29 17:40:01'),('89f0fd5c927d466d6ec9a21b9ac34ffa','Tacámbaro de Codallos','sesion','2014-09-29 17:39:55'),('8b16ebc056e613024c057be590b542eb','Poza Rica de Hidalgo','sesion','2014-09-29 17:40:05'),('8bf1211fd4b7b94528899de0a43b9fb3','Cosolapa','sesion','2014-09-29 17:39:57'),('8c19f571e251e61cb8dd3612f26d5ecf','Zapotiltic','sesion','2014-09-29 17:39:52'),('8cb22bdd0b7ba1ab13d742e22eed8da2','San Blas Atempa','sesion','2014-09-29 17:39:58'),('8d34201a5b85900908db6cae92723617','Huamantla','sesion','2014-09-29 17:40:04'),('8d3bba7425e7c98c50f52ca1b52d3735','Ciudad Lázaro Cárdenas','sesion','2014-09-29 17:39:54'),('8d5e957f297893487bd98fa830fa6413','Valle de Santiago','sesion','2014-09-29 17:39:48'),('8d7d8ee069cb0cbbf816bbb65d56947e','Puerto Escondido','sesion','2014-09-29 17:39:59'),('8dd48d6a2e2cad213179a3992c0be53c','Anáhuac','sesion','2014-09-29 17:39:56'),('8e296a067a37563370ded05f5a3bf3ec','San Ignacio','sesion','2014-09-29 17:39:43'),('8e6b42f1644ecb1327dc03ab345e618b','Villa de Reyes','sesion','2014-09-29 17:40:01'),('8e98d81f8217304975ccb23337bb5761','Sahuayo de Morelos','sesion','2014-09-29 17:39:54'),('8ebda540cbcc4d7336496819a46a1b68','Coatzacoalcos','sesion','2014-09-29 17:40:06'),('8eefcfdf5990e441f0fb6f3fad709e21','Cerro Azul','sesion','2014-09-29 17:40:05'),('8efb100a295c0c690931222ff4467bb8','Villa de Tamazulápam del Progreso','sesion','2014-09-29 17:39:57'),('8f121ce07d74717e0b1f21d122e04521','Almoloya de Juárez','sesion','2014-09-29 17:39:53'),('8f14e45fceea167a5a36dedd4bea2543','Asientos','sesion','2014-09-29 17:39:43'),('8f53295a73878494e9bc8dd6c3c7104f','Petatlán','sesion','2014-09-29 17:39:49'),('8f85517967795eeef66c225f7883bdcb','La Unión','sesion','2014-09-29 17:39:49'),('8fe0093bb30d6f8c31474bd0764e6ac0','San Antonio de la Cal','sesion','2014-09-29 17:39:59'),('903ce9225fca3e988c2af215d4e544d3','Empalme Escobedo (Escobedo)','sesion','2014-09-29 17:39:48'),('918317b57931b6b7a7d29490fe5ec9f9','Ecatepec de Morelos','sesion','2014-09-29 17:39:53'),('9188905e74c28e489b44e954ec0b9bca','Valle de Guadalupe','sesion','2014-09-29 17:39:51'),('92c8c96e4c37100777c7190b76d28233','Villa Nicolás Romero','sesion','2014-09-29 17:39:53'),('92cc227532d17e56e07902b254dfad10','Chihuahua','sesion','2014-09-29 17:39:46'),('93db85ed909c13838ff95ccfa94cebd9','Mapastepec','sesion','2014-09-29 17:39:46'),('941e1aaaba585b952b62c14a3a175a61','Felipe Carrillo Puerto','sesion','2014-09-29 17:40:00'),('9431c87f273e507e6040fcb07dcb4509','Kantunilkín','sesion','2014-09-29 17:40:00'),('9461cce28ebe3e76fb4b931c35a169b0','Lic. Benito Juárez (Campo Gobierno)','sesion','2014-09-29 17:40:01'),('94c7bb58efc3b337800875b5d382a072','Banderilla','sesion','2014-09-29 17:40:04'),('94f6d7e04a4d452035300f18b984988c','Juchitepec de Mariano Riva Palacio','sesion','2014-09-29 17:39:54'),('950a4152c2b4aa3ad78bdd6b366cc179','Tangancícuaro de Arista','sesion','2014-09-29 17:39:54'),('96da2f590cd7246bbde0051047b0d6f7','Coyuca de Catalán','sesion','2014-09-29 17:39:49'),('9766527f2b5d3e95d4a733fcfb77bd7e','Iguala de la Independencia','sesion','2014-09-29 17:39:49'),('9778d5d219c5080b9a6a17bef029331c','Arriaga','sesion','2014-09-29 17:39:45'),('979d472a84804b9f647bc185a877a8b5','Guadalajara','sesion','2014-09-29 17:39:50'),('97e8527feaf77a97fc38f34216141515','Ciudad Mante','sesion','2014-09-29 17:40:04'),('9872ed9fc22fc182d371c3e9ed316094','Coyuca de Benítez','sesion','2014-09-29 17:39:49'),('98b297950041a42470269d56260243a1','Cedral','sesion','2014-09-29 17:40:00'),('98dce83da57b0395e163467c9dae521b','Cuauhtémoc','sesion','2014-09-29 17:39:46'),('98f13708210194c475687be6106a3b84','Cabo San Lucas','sesion','2014-09-29 17:39:43'),('996a7fa078cc36c46d02f9af3bef918b','Jáltipan de Morelos','sesion','2014-09-29 17:40:06'),('99c5e07b4d5de9d18c350cdf64c5aa3d','Cazones de Herrera','sesion','2014-09-29 17:40:05'),('9a1158154dfa42caddbd0694a4e9bdc8','Sabinas','sesion','2014-09-29 17:39:44'),('9a96876e2f8f3dc4f3cf45f02c61c0c1','Querétaro','sesion','2014-09-29 17:40:00'),('9ad6aaed513b73148b7d49f70afcfb32','Córdoba','sesion','2014-09-29 17:40:05'),('9b04d152845ec0a378394003c96da594','Magdalena','sesion','2014-09-29 17:39:51'),('9b70e8fe62e40c570a322f1b0b659098','San Juan del Rio','sesion','2014-09-29 17:40:00'),('9b72e31dac81715466cd580a448cf823','Altamira','sesion','2014-09-29 17:40:03'),('9b8619251a19057cff70779273e95aa6','Abasolo','sesion','2014-09-29 17:39:47'),('9be40cee5b0eee1462c82c6964087ff9','Santiago','sesion','2014-09-29 17:39:56'),('9bf31c7ff062936a96d3c8bd1f8f2ff3','Rodolfo Sánchez T. (Maneadero)','sesion','2014-09-29 17:39:43'),('9c838d2e45b2ad1094d42f4ef36764f6','Tecamac de Felipe Villanueva','sesion','2014-09-29 17:39:53'),('9cc138f8dc04cbf16240daa92d8d50e2','Valparaíso','sesion','2014-09-29 17:40:07'),('9cf81d8026a9018052c429cc4e56739b','Ángel R. Cabada','sesion','2014-09-29 17:40:06'),('9cfdf10e8fc047a44b08ed031e1f0ed1','Ajijic','sesion','2014-09-29 17:39:51'),('9dcb88e0137649590b755372b040afad','Chilapa de Álvarez','sesion','2014-09-29 17:39:49'),('9de6d14fff9806d4bcd1ef555be766cd','Jala','sesion','2014-09-29 17:39:56'),('9dfcd5e558dfa04aaf37f137a1d9d3e5','Zamora de Hidalgo','sesion','2014-09-29 17:39:54'),('9f61408e3afb633e50cdf1b20de6f466','Viesca','sesion','2014-09-29 17:39:45'),('9fc3d7152ba9336a670e36d0ed79bc43','San miguel de Allende','sesion','2014-09-29 17:39:47'),('9fd81843ad7f202f26c1a174c7357585','Ixtapaluca','sesion','2014-09-29 17:39:54'),('a01a0380ca3c61428c26a231f0e49a09','San Sebastián Tecomaxtlahuaca','sesion','2014-09-29 17:39:57'),('a02ffd91ece5e7efeb46db8f10a74059','Vicente Camalote','sesion','2014-09-29 17:39:57'),('a0a080f42e6f13b3a2df133f073095dd','Marfil','sesion','2014-09-29 17:39:47'),('a1d0c6e83f027327d8461063f4ac58a6','Monclova','sesion','2014-09-29 17:39:44'),('a2557a7b2e94197ff767970b67041697','Huamuxtitlan','sesion','2014-09-29 17:39:49'),('a3c65c2974270fd093ee8a9bf8ae7d0b','Canatlán','sesion','2014-09-29 17:39:46'),('a3f390d88e4c41f2747bfa2f1b5f87db','Ocozocoautla de Espinosa','sesion','2014-09-29 17:39:45'),('a49e9411d64ff53eccfdd09ad10a15b3','Amozoc','sesion','2014-09-29 17:39:59'),('a4a042cf4fd6bfb47701cbc8a1653ada','Teloloapan','sesion','2014-09-29 17:39:49'),('a4f23670e1833f3fdb077ca70bbd5d66','Cihuatlán','sesion','2014-09-29 17:39:52'),('a516a87cfcaef229b342c437fe2b95f7','Ahome','sesion','2014-09-29 17:40:01'),('a5771bce93e200c36f7cd9dfd0e5deaa','Saltillo','sesion','2014-09-29 17:39:44'),('a597e50502f5ff68e3e25b9114205d4a','Copala','sesion','2014-09-29 17:39:50'),('a5bfc9e07964f8dddeb95fc584cd965d','Calkini','sesion','2014-09-29 17:39:44'),('a5e00132373a7031000fd987a3c9f87b','Cortazar','sesion','2014-09-29 17:39:48'),('a666587afda6e89aec274a3657558a27','Nueva Italia de Ruiz','sesion','2014-09-29 17:39:55'),('a684eceee76fc522773286a895bc8436','Torreón','sesion','2014-09-29 17:39:44'),('a733fa9b25f33689e2adbe72199f0e62','Villa de Cos','sesion','2014-09-29 17:40:07'),('a760880003e7ddedfef56acb3b09697f','Heroica Guaymas','sesion','2014-09-29 17:40:02'),('a7aeed74714116f3b292a982238f83d2','Pánuco','sesion','2014-09-29 17:40:05'),('a86c450b76fb8c371afead6410d55534','Papantla de Olarte','sesion','2014-09-29 17:40:05'),('a87ff679a2f3e71d9181a67b7542122c','Cosío','sesion','2014-09-29 17:39:43'),('a8849b052492b5106526b2331e526138','Soledad de Doblado','sesion','2014-09-29 17:40:05'),('a8abb4bb284b5b27aa7cb790dc20f80b','Atlixco','sesion','2014-09-29 17:39:59'),('a8baa56554f96369ab93e4f3bb068c22','Comonfort','sesion','2014-09-29 17:39:48'),('a8c88a0055f636e4a163a5e3d16adab7','Yurécuaro','sesion','2014-09-29 17:39:54'),('a8f15eda80c50adb0e71943adc8015cf','Acámbaro','sesion','2014-09-29 17:39:48'),('a96b65a721e561e1e3de768ac819ffbb','Santa María Huatulco','sesion','2014-09-29 17:39:58'),('a97da629b098b75c294dffdc3e463904','Victoria de Durango','sesion','2014-09-29 17:39:46'),('a9a1d5317a33ae8cef33961c34144f84','Río Blanco','sesion','2014-09-29 17:40:05'),('a9a6653e48976138166de32772b1bf40','Platón Sánchez','sesion','2014-09-29 17:40:04'),('aa942ab2bfa6ebda4840e7360ce6e7ef','Ciudad General Escobedo','sesion','2014-09-29 17:39:56'),('aab3238922bcc25a6f606eb525ffdc56','Playas de Rosarito','sesion','2014-09-29 17:39:43'),('ab817c9349cf9c4f6877e1894a1faa00','El Naranjo','sesion','2014-09-29 17:40:01'),('abd815286ba1007abfbb8415b83ae2cf','Villanueva','sesion','2014-09-29 17:40:07'),('ac1dd209cbcc5e5d1c6e28598e8cbbe8','San Julián','sesion','2014-09-29 17:39:51'),('ac627ab1ccbdb62ec96e702f07f6425b','Manuel Ojinaga','sesion','2014-09-29 17:39:46'),('acc3e0404646c57502b480dc052c4fe1','Tierra Blanca','sesion','2014-09-29 17:40:06'),('ad13a2a07ca4b7642959dc0c4c740ab6','Uruapan','sesion','2014-09-29 17:39:54'),('ad61ab143223efbc24c7d2583be69251','Ocosingo','sesion','2014-09-29 17:39:45'),('ad972f10e0800b49d76fed33a21f6698','San Pablo Huitzo','sesion','2014-09-29 17:39:57'),('b1a59b315fc9a3002ce38bbe070ec3f5','Ciudad Guzmán','sesion','2014-09-29 17:39:52'),('b1d10e7bafa4421218a51b1e1f1b0ba2','Tizayuca','sesion','2014-09-29 17:39:50'),('b2eb7349035754953b57a32e2841bda5','Zinapécuaro de Figueroa','sesion','2014-09-29 17:39:54'),('b2eeb7362ef83deff5c7813a67e14f0a','Carlos A. Carrillo','sesion','2014-09-29 17:40:06'),('b2f627fff19fda463cb386442eac2b3d','San Andrés Tuxtla','sesion','2014-09-29 17:40:06'),('b337e84de8752b27eda3a12363109e80','Heroica Caborca','sesion','2014-09-29 17:40:02'),('b3967a0e938dc2a6340e258630febd5a','Las Varas','sesion','2014-09-29 17:39:56'),('b3e3e393c77e35a4a3f3cbd1e429b5dc','Moroleón','sesion','2014-09-29 17:39:48'),('b534ba68236ba543ae44b22bd110a1d6','Villa Unión','sesion','2014-09-29 17:40:02'),('b53b3a3d6ab90ce0268229151c9bde11','Matamoros','sesion','2014-09-29 17:39:45'),('b5b41fac0361d157d9673ecb926af5ae','Puerto Peñasco','sesion','2014-09-29 17:40:02'),('b6d767d2f8ed5d21a44b0e5886680cb9','Puerto Adolfo López Mateos','sesion','2014-09-29 17:39:43'),('b6f0479ae87d244975439c6124592772','Villa Sola de Vega','sesion','2014-09-29 17:39:59'),('b73ce398c39f506af761d2277d853a92','Tixtla de Guerrero','sesion','2014-09-29 17:39:48'),('b73dfe25b4b8714c029b37a6ad3006fa','Ticul','sesion','2014-09-29 17:40:06'),('b7b16ecf8ca53723593894116071700c','Matías Romero Avendaño','sesion','2014-09-29 17:39:58'),('b7bb35b9c6ca2aee2df08cf09d7016c2','Jerez de García Salinas','sesion','2014-09-29 17:40:07'),('b83aac23b9528732c23cc7352950e880','Huetamo de Núñez','sesion','2014-09-29 17:39:55'),('b9228e0962a78b84f3d5d92f4faa000b','San Miguel el Grande','sesion','2014-09-29 17:39:58'),('ba2fd310dcaa8781a9a652a31baf3c68','Ciudad Victoria','sesion','2014-09-29 17:40:03'),('bac9162b47c56fc8a4d2a519803d51b3','Ciudad Benito Juárez','sesion','2014-09-29 17:39:56'),('bbcbff5c1f1ded46c25d28119a85c6c2','Santo Domingo Tehuantepec','sesion','2014-09-29 17:39:58'),('bbf94b34eb32268ada57a3be5062fe7d','El Camarón','sesion','2014-09-29 17:39:58'),('bc6dc48b743dc5d013b1abaebd2faed2','Heroica Zitácuaro','sesion','2014-09-29 17:39:55'),('bca82e41ee7b0833588399b1fcd177c7','San Felipe Jalapa de Díaz','sesion','2014-09-29 17:39:57'),('bcbe3365e6ac95ea2c0343a2395834dd','Villa Corona','sesion','2014-09-29 17:39:51'),('bd4c9ab730f5513206b999ec0d90d1fb','Ayutla de los Libres','sesion','2014-09-29 17:39:48'),('bd686fd640be98efaae0091fa301e613','Cruz Grande','sesion','2014-09-29 17:39:49'),('be83ab3ecd0db773eb2dc1b0a17836a1','Etzatlán','sesion','2014-09-29 17:39:51'),('beed13602b9b0e6ecb5b568ff5058f07','San Juan Bautista Cuicatlán','sesion','2014-09-29 17:39:57'),('bf8229696f7a3bb4700cfddef19fa23f','Tlapehuala','sesion','2014-09-29 17:39:49'),('c042f4db68f23406c6cecf84a7ebb0fe','Zacatepec de Hidalgo','sesion','2014-09-29 17:39:55'),('c058f544c737782deacefa532d9add4c','Ciudad Santa Catarina','sesion','2014-09-29 17:39:56'),('c0c7c76d30bd3dcaefc96f40275bdc0a','Morelos','sesion','2014-09-29 17:39:44'),('c0e190d8267e36708f955d7ab048990d','San José el Verde (El Verde)','sesion','2014-09-29 17:39:51'),('c16a5320fa475530d9583c34fd356ef5','Candelaria','sesion','2014-09-29 17:39:44'),('c203d8a151612acf12457e4d67635a95','Acatlán de Osorio','sesion','2014-09-29 17:39:59'),('c20ad4d76fe97759aa27a0c99bff6710','San Felipe','sesion','2014-09-29 17:39:43'),('c24cd76e1ce41366a4bbe8a49b02a028','La Barca','sesion','2014-09-29 17:39:52'),('c361bc7b2c033a83d663b8d9fb4be56e','Juan Díaz Covarrubias','sesion','2014-09-29 17:40:06'),('c3992e9a68c5ae12bd18488bc579b30d','Sihuapan','sesion','2014-09-29 17:40:06'),('c399862d3b9d6b76c8436e924a68c45b','Ciudad Gustavo Díaz Ordaz','sesion','2014-09-29 17:40:03'),('c3c59e5f8b3e9753913f4d435b53c308','Higuera de Zaragoza','sesion','2014-09-29 17:40:01'),('c3e878e27f52e2a57ace4d9a76fd9acf','Ciudad Apodaca','sesion','2014-09-29 17:39:56'),('c410003ef13d451727aeff9082c29a5c','Guamúchil','sesion','2014-09-29 17:40:02'),('c45147dee729311ef5b5c3003946c48f','Gómez Palacio','sesion','2014-09-29 17:39:47'),('c4ca4238a0b923820dcc509a6f75849b','Aguascalientes','sesion','2014-09-29 17:39:43'),('c51ce410c124a10e0db5e4b97fc2af39','Tijuana','sesion','2014-09-29 17:39:43'),('c52f1bd66cc19d05628bd8bf27af3ad6','Talpa de Allende','sesion','2014-09-29 17:39:52'),('c5ff2543b53f4cc0ad3819a36752467b','San Blas','sesion','2014-09-29 17:39:56'),('c6e19e830859f2cb9f7c8f8cacb8d2a6','Ixtaczoquitlán','sesion','2014-09-29 17:40:05'),('c74d97b01eae257e44aa9d5bade97baf','Ensenada','sesion','2014-09-29 17:39:43'),('c75b6f114c23a4d7ea11331e7c00e73c','Apizaco','sesion','2014-09-29 17:40:04'),('c7e1249ffc03eb9ded908c236bd1996d','Huixtla','sesion','2014-09-29 17:39:46'),('c81e728d9d4c2f636f067f89cc14862c','San Francisco de los Romo','sesion','2014-09-29 17:39:43'),('c86a7ee3d8ef0b551ed58e354a836f2b','Santiago Juxtlahuaca','sesion','2014-09-29 17:39:57'),('c8ffe9a587b126f152ed3d89a146b445','Purísima de Bustos','sesion','2014-09-29 17:39:47'),('c9892a989183de32e976c6f04e700201','Gutiérrez Zamora','sesion','2014-09-29 17:40:05'),('c9e1074f5b3f9fc8ea15d152add07294','Hidalgo del Parral','sesion','2014-09-29 17:39:46'),('c9f0f895fb98ab9159f51fd0297e236d','Calvillo','sesion','2014-09-29 17:39:43'),('ca46c1b9512a7a8315fa3c5a946e8265','Zapopan','sesion','2014-09-29 17:39:50'),('caf1a3dfb505ffed0d024130f58c5cfa','Ciudad Hidalgo','sesion','2014-09-29 17:39:55'),('cb70ab375662576bd1ac5aaf16b3fca4','Yahualica de González Gallo','sesion','2014-09-29 17:39:51'),('cbcb58ac2e496207586df2854b17995f','Túxpam de Rodríguez Cano','sesion','2014-09-29 17:40:05'),('ccb1d45fb76f7c5a0bf619f979c6cf36','Chiautempan','sesion','2014-09-29 17:40:04'),('cd00692c3bfe59267d5ecfac5310286c','Cuernavaca','sesion','2014-09-29 17:39:55'),('cdc0d6e63aa8e41c89689f54970bb35f','Valladolid','sesion','2014-09-29 17:40:06'),('cedebb6e872f539bef8c3f919874e9d7','San Luis San Pedro','sesion','2014-09-29 17:39:49'),('cee631121c2ec9232f3a2f028ad5c89b','Hermosillo','sesion','2014-09-29 17:40:02'),('cf004fdc76fa1a4f25f62e0eb5261ca3','Montemorelos','sesion','2014-09-29 17:39:56'),('cf67355a3333e6e143439161adc2d82e','Macuspana','sesion','2014-09-29 17:40:03'),('cfa0860e83a4c3a763a7e62d825349f7','La Resolana','sesion','2014-09-29 17:39:52'),('cfecdb276f634854f3ef915e2e980c31','Tlapa de Comonfort','sesion','2014-09-29 17:39:49'),('cfee398643cbc3dc5eefc89334cacdc1','Culiacán Rosales','sesion','2014-09-29 17:40:01'),('d07e70efcfab08731a97e7b91be644de','San Luis Potosí','sesion','2014-09-29 17:40:00'),('d09bf41544a3365a46c9077ebb5e35c3','Palenque','sesion','2014-09-29 17:39:45'),('d18f655c3fce66ca401d5f38b48c89af','Aguaruto','sesion','2014-09-29 17:40:01'),('d1c38a09acc34845c6be3a127a5aacaf','Chapala','sesion','2014-09-29 17:39:51'),('d1f255a373a3cef72e03aa9d980c7eca','El Rosario','sesion','2014-09-29 17:39:59'),('d1f491a404d6854880943e5c3cd9ca25','Cuerámaro','sesion','2014-09-29 17:39:47'),('d1fe173d08e959397adf34b1d77e88d7','Las Rosas','sesion','2014-09-29 17:39:45'),('d296c101daa88a51f6ca8cfc1ac79b50','Los Reyes Acaquilpan (La Paz)','sesion','2014-09-29 17:39:54'),('d2ddea18f00665ce8623e36bd4e3c7c5','Pichucalco','sesion','2014-09-29 17:39:45'),('d34ab169b70c9dcd35e62896010cd9ff','Loma Bonita','sesion','2014-09-29 17:39:57'),('d395771085aab05244a4fb8fd91bf4ee','Tlalnepantla de Baz','sesion','2014-09-29 17:39:53'),('d3d9446802a44259755d38e6d163e820','Mexicali','sesion','2014-09-29 17:39:43'),('d490d7b4576290fa60eb31b5fc917ad1','Juan Rodríguez Clara','sesion','2014-09-29 17:40:06'),('d61e4bbd6393c9111e6526ea173a7c8b','El Pueblito','sesion','2014-09-29 17:40:00'),('d645920e395fedad7bbbed0eca3fe2e0','San Buenaventura','sesion','2014-09-29 17:39:44'),('d64a340bcb633f536d56e51874281454','Jaumave','sesion','2014-09-29 17:40:03'),('d67d8ab4f4c10bf22aa353e27879133c','Arteaga','sesion','2014-09-29 17:39:44'),('d6baf65e0b240ce177cf70da146c8dc8','Tamazula de Gordiano','sesion','2014-09-29 17:39:52'),('d709f38ef758b5066ef31b18039b8ce5','Hualahuises','sesion','2014-09-29 17:39:57'),('d7a728a67d909e714c0774e22cb806f2','Cosoleacaque','sesion','2014-09-29 17:40:06'),('d81f9c1be2e08964bf9f24b15f0e4900','La peñita de Jaltemba','sesion','2014-09-29 17:39:56'),('d82c8d1619ad8176d665453cfb2e55f0','Nueva Rosita','sesion','2014-09-29 17:39:44'),('d86ea612dec96096c5e0fcc8dd42ab6d','Santiago Tuxtla','sesion','2014-09-29 17:40:06'),('d947bf06a885db0d477d707121934ff8','Capulhuac','sesion','2014-09-29 17:39:53'),('d96409bf894217686ba124d7356686c9','El Grullo','sesion','2014-09-29 17:39:52'),('d9d4f495e875a2e075a1a4a6e1b9770f','Nava','sesion','2014-09-29 17:39:44'),('d9fc5b73a8d78fad3d6dffe419384e70','Asunción Nochixtlán','sesion','2014-09-29 17:39:57'),('da4fb5c6e93e74d3df8527599fa62642','Silao','sesion','2014-09-29 17:39:47'),('daca41214b39c5dc66674d09081940f0','Paraje Nuevo','sesion','2014-09-29 17:40:05'),('db85e2590b6109813dafa101ceb2faeb','Tihuatlán','sesion','2014-09-29 17:40:05'),('db8e1af0cb3aca1ae2d0018624204529','Huixquilucan de Degollado','sesion','2014-09-29 17:39:53'),('dbe272bab69f8e13f14b405e038deb64','Tezonapa','sesion','2014-09-29 17:40:05'),('dc6a6489640ca02b0d42dabeb8e46bb7','Cárdenas','sesion','2014-09-29 17:40:01'),('dc82d632c9fcecb0778afbc7924494a6','Acayucan','sesion','2014-09-29 17:40:06'),('dc912a253d1e9ba40e2c597ed2376640','Heroica Ciudad de Huajuapan de León','sesion','2014-09-29 17:39:57'),('dd458505749b2941217ddd59394240e8','Coatzintla','sesion','2014-09-29 17:40:05'),('ddb30680a691d157187ee1cf9e896d03','Xicotepec','sesion','2014-09-29 17:39:59'),('df877f3865752637daa540ea9cbc474f','Nueva Ciudad Guerrero','sesion','2014-09-29 17:40:03'),('e00da03b685a0dd18fb6a08af0923de0','Celaya','sesion','2014-09-29 17:39:48'),('e0c641195b27425bb056ac56f8953d24','Heroica Ciudad de Ejutla de Crespo','sesion','2014-09-29 17:39:59'),('e165421110ba03099a1c0393373c5b43','Ameca','sesion','2014-09-29 17:39:51'),('e1e32e235eee1f970470a3a6658dfdd5','Cosalá','sesion','2014-09-29 17:40:01'),('e2230b853516e7b05d79744fbd4c9c13','Ciudad Obregón','sesion','2014-09-29 17:40:02'),('e2c0be24560d78c5e599c2a9c9d0bbd2','Cruz Azul','sesion','2014-09-29 17:39:50'),('e2c420d928d4bf8ce0ff2ec19b371514','Acala','sesion','2014-09-29 17:39:45'),('e2ef524fbf3d9fe611d5a8e90fefdc9c','Madera','sesion','2014-09-29 17:39:46'),('e369853df766fa44e1ed0ff613f563bd','Hopelchén','sesion','2014-09-29 17:39:44'),('e3796ae838835da0b6f6ea37bcf8bcb7','Tepotzotlán','sesion','2014-09-29 17:39:53'),('e44fea3bec53bcea3b7513ccef5857ac','Cancún','sesion','2014-09-29 17:40:00'),('e46de7e1bcaaced9a54f1e9d0d2f800d','Chahuites','sesion','2014-09-29 17:39:58'),('e4a6222cdb5b34375400904f03d8e6a5','Villa Hidalgo','sesion','2014-09-29 17:39:51'),('e4da3b7fbbce2345d7772b0674a318d5','Tepezalá','sesion','2014-09-29 17:39:43'),('e56954b4f6347e897f954495eab16a88','Tuxpan','sesion','2014-09-29 17:39:52'),('e5f6ad6ce374177eef023bf5d0c018b6','Tlapacoyan','sesion','2014-09-29 17:40:05'),('e6b4b2a746ed40e1af829d1fa82daa10','San Rafael','sesion','2014-09-29 17:40:05'),('e7b24b112a44fdd9ee93bdf998c6ca0e','San Pedro Garza García','sesion','2014-09-29 17:39:56'),('e836d813fd184325132fca8edcdfb40e','Tamuin','sesion','2014-09-29 17:40:01'),('e8c0653fea13f91bf3c48159f7c24f78','Heroica Nogales','sesion','2014-09-29 17:40:02'),('e96ed478dab8595a7dbda4cbcbee168f','Las Pintitas','sesion','2014-09-29 17:39:51'),('ea5d2f1c4608232e07d3aa3d998e5135','Manzanillo','sesion','2014-09-29 17:39:45'),('eae27d77ca20db309e056e3d2dcd7d69','Tlaxcoapan','sesion','2014-09-29 17:39:50'),('eb160de1de89d9058fcb0b968dbbbd68','Ciudad Lerdo','sesion','2014-09-29 17:39:47'),('eb163727917cbba1eea208541a643e74','Tepeapulco','sesion','2014-09-29 17:39:50'),('eb6fdc36b281b7d5eabf33396c2683a2','Tizimín','sesion','2014-09-29 17:40:06'),('eba0dc302bcd9a273f8bbb72be3a687b','Mocorito','sesion','2014-09-29 17:40:01'),('ebd9629fc3ae5e9f6611e2ee05a31cef','Paraíso','sesion','2014-09-29 17:40:03'),('ec5decca5ed3d6b8079e2e7e7bacc9f2','Salamanca','sesion','2014-09-29 17:39:47'),('ec8956637a99787bd197eacd77acce5e','Saucillo','sesion','2014-09-29 17:39:46'),('ec8ce6abb3e952a85b8551ba726a1227','El Quince (San José el Quince)','sesion','2014-09-29 17:39:51'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Rincón de Romos','sesion','2014-09-29 17:39:43'),('ed265bc903a5a097f61d3ec064d96d2e','Calpulalpan','sesion','2014-09-29 17:40:04'),('ed3d2c21991e3bef5e069713af9fa6ca','Juárez','sesion','2014-09-29 17:39:46'),('eda80a3d5b344bc40f3bc04f65b7a357','Toluca de Lerdo','sesion','2014-09-29 17:39:53'),('eddea82ad2755b24c4e168c5fc2ebd40','La piedad de Cabadas','sesion','2014-09-29 17:39:54'),('eecca5b6365d9607ee5a9d336962c534','San Jerónimo de Juárez','sesion','2014-09-29 17:39:49'),('eed5af6add95a9a6f1252739b1ad8c24','San Martín Texmelucan de Labastida','sesion','2014-09-29 17:39:59'),('ef0d3930a7b6c95bd2b32ed45989c61f','Valle de Chalco Solidaridad','sesion','2014-09-29 17:39:54'),('ef575e8837d065a1683c022d2077d342','Santa María del Río','sesion','2014-09-29 17:40:01'),('efe937780e95574250dabe07151bdc23','Ahuacatlán','sesion','2014-09-29 17:39:56'),('f033ab37c30201f73f142449d037028d','Cintalapa de Figueroa','sesion','2014-09-29 17:39:45'),('f0935e4cd5920aa6c7c996a5ee53a70f','Ciudad de México','sesion','2014-09-29 17:39:46'),('f0e52b27a7a5d6a1a87373dffa53dbe5','Altotonga','sesion','2014-09-29 17:40:05'),('f1b6f2857fb6d44dd73c7041e0aa0f19','Ciudad Valles','sesion','2014-09-29 17:40:00'),('f2217062e9a397a1dca429e7d70bc6ca','Apaseo el Alto','sesion','2014-09-29 17:39:48'),('f29c21d4897f78948b91f03172341b7b','Minatitlán','sesion','2014-09-29 17:40:06'),('f2fc990265c712c49d51a18a32b39f0c','Pátzcuaro','sesion','2014-09-29 17:39:55'),('f340f1b1f65b6df5b5e3f94d95b11daf','Teocaltiche','sesion','2014-09-29 17:39:51'),('f387624df552cea2f369918c5e1e12bc','Coatepec','sesion','2014-09-29 17:40:04'),('f3f27a324736617f20abbf2ffd806f6d','Comalcalco','sesion','2014-09-29 17:40:02'),('f457c545a9ded88f18ecee47145a72c0','Zaragoza','sesion','2014-09-29 17:39:44'),('f4b9ec30ad9f68f89b29639786cb62ef','Colonia Anáhuac','sesion','2014-09-29 17:39:46'),('f4be00279ee2e0a53eafdaa94a151e2c','Soto la Marina','sesion','2014-09-29 17:40:03'),('f4f6dce2f3a0f9dada0c2b5b66452017','Miahuatlán de Porfirio Díaz','sesion','2014-09-29 17:39:58'),('f5deaeeae1538fb6c45901d524ee2f98','Nogales','sesion','2014-09-29 17:40:05'),('f5f8590cd58a54e94377e6ae2eded4d9','Chetumal','sesion','2014-09-29 17:40:00'),('f61d6947467ccd3aa5af24db320235dd','Santiago Suchilquitongo','sesion','2014-09-29 17:39:57'),('f7177163c833dff4b38fc8d2872f1ec6','Ramos Arizpe','sesion','2014-09-29 17:39:44'),('f718499c1c8cef6730f9fd03c8125cab','Cocula','sesion','2014-09-29 17:39:52'),('f73b76ce8949fe29bf2a537cfa420e8f','Juchitán (Juchitán de Zaragoza)','sesion','2014-09-29 17:39:58'),('f74909ace68e51891440e4da0b65a70c','Puebla (Heroica Puebla)','sesion','2014-09-29 17:39:59'),('f7664060cc52bc6f3d620bcedc94a4b6','Tecalitlán','sesion','2014-09-29 17:39:52'),('f76a89f0cb91bc419542ce9fa43902dc','Agua dulce','sesion','2014-09-29 17:40:06'),('f770b62bc8f42a0b66751fe636fc6eb0','Quila','sesion','2014-09-29 17:40:01'),('f7e6c85504ce6e82442c770f7c8606f0','Ciudad Apaxtla de Castrejón','sesion','2014-09-29 17:39:49'),('f85454e8279be180185cac7d243c5eb3','Ocotlán de Morelos','sesion','2014-09-29 17:39:59'),('f899139df5e1059396431415e770c6dd','Juan Aldama','sesion','2014-09-29 17:39:46'),('f8c1f23d6a8d8d7904fc0ea8e066b3bb','San Pedro Tapanatepec','sesion','2014-09-29 17:39:58'),('f90f2aca5c640289d0a29417bcb63a37','Coacalco de Berriozabal','sesion','2014-09-29 17:39:53'),('f9b902fc3289af4dd08de5d1de54f68f','Tlaquiltenango','sesion','2014-09-29 17:39:55'),('fa7cdfad1a5aaf8370ebeda47a1ff1c3','San Marcos','sesion','2014-09-29 17:39:48'),('faa9afea49ef2ff029a833cccc778fd0','Santiago Pinotepa Nacional','sesion','2014-09-29 17:39:59'),('fb7b9ffa5462084c5f4e7e85a093e6d7','García','sesion','2014-09-29 17:39:56'),('fbd7939d674997cdb4692d34de8633c4','Comitán de Domínguez','sesion','2014-09-29 17:39:45'),('fc221309746013ac554571fbd180e1c8','Técpan de Galeana','sesion','2014-09-29 17:39:49'),('fc490ca45c00b1249bbe3554a4fdf6fb','Ciudad de Armería','sesion','2014-09-29 17:39:45'),('fccb60fb512d13df5083790d64c4d5dd','Zacatlán','sesion','2014-09-29 17:39:59'),('fde9264cf376fffe2ee4ddf4a988880d','Huatusco de Chicuellar','sesion','2014-09-29 17:40:05'),('fe131d7f5a6b38b23cc967316c13dae2','Puerto Vallarta','sesion','2014-09-29 17:39:52'),('fe73f687e5bc5280214e0486b273a5f9','Cuautla (Cuautla de Morelos)','sesion','2014-09-29 17:39:55'),('fe9fc289c3ff0af142b6d3bead98a923','Villaflores','sesion','2014-09-29 17:39:45'),('ff4d5fbbafdf976cfdc032e3bde78de5','Magdalena de Kino','sesion','2014-09-29 17:40:02'),('ffd52f3c7e12435a724a8f30fddadd9c','San Francisco Telixtlahuaca','sesion','2014-09-29 17:39:57'),('ffeabd223de0d4eacb9a3e6e53e5448d','Yecuatla','sesion','2014-09-29 17:40:05');
/*!40000 ALTER TABLE `tbbsnm11` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm12`
--

DROP TABLE IF EXISTS `tbbsnm12`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm12` (
  `iddomicilio` varchar(32) NOT NULL,
  `dsestado` varchar(60) DEFAULT NULL,
  `dsmunicipio` varchar(60) DEFAULT NULL,
  `dsciudad` varchar(60) DEFAULT NULL,
  `isasistido` int(1) DEFAULT NULL,
  `dscolonia` varchar(60) DEFAULT NULL,
  `dslocalidad` varchar(60) DEFAULT NULL,
  `dscalle` varchar(60) DEFAULT NULL,
  `dscodpos` varchar(5) DEFAULT NULL,
  `dsnumint` varchar(30) DEFAULT NULL,
  `dsnumext` varchar(6) DEFAULT NULL,
  `dsdircompleta` text,
  `dslote` varchar(255) DEFAULT NULL,
  `dsmanzana` varchar(255) DEFAULT NULL,
  `dsreferencia` text,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddomicilio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm12`
--

LOCK TABLES `tbbsnm12` WRITE;
/*!40000 ALTER TABLE `tbbsnm12` DISABLE KEYS */;
INSERT INTO `tbbsnm12` VALUES ('c4ca4238a0b923820dcc509a6f75849b','D.F','Alvaro Obregon','D.F',0,'Col. San Angel','San Angel','Altavista','01049','','83','','','','Avenida Revolución','sesion','2014-09-29 17:37:44');
/*!40000 ALTER TABLE `tbbsnm12` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm13`
--

DROP TABLE IF EXISTS `tbbsnm13`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm13` (
  `idestado` varchar(32) NOT NULL,
  `dsnombre` varchar(60) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idestado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm13`
--

LOCK TABLES `tbbsnm13` WRITE;
/*!40000 ALTER TABLE `tbbsnm13` DISABLE KEYS */;
INSERT INTO `tbbsnm13` VALUES ('02e74f10e0327ad868d138f2b4fdd6f0','Tabasco','sesion','2014-09-29 17:38:16'),('1679091c5a880faf6fb5e6087eb1b2dc','Colima','sesion','2014-09-29 17:38:15'),('1f0e3dad99908345f7439f8ffabdffc4','Nuevo León','sesion','2014-09-29 17:38:15'),('1ff1de774005f8da13f42943881c655f','San Luis Potosí','sesion','2014-09-29 17:38:16'),('33e75ff09dd601bbe69f351039152189','Tamaulipas','sesion','2014-09-29 17:38:16'),('34173cb38f07f89ddbebc2ac9128303f','Veracruz de Ignacio de la Llave','sesion','2014-09-29 17:38:16'),('37693cfc748049e45d87b8c7d8b9aacd','Quintana Roo','sesion','2014-09-29 17:38:16'),('3c59dc048e8850243be8079a5c74d079','Puebla','sesion','2014-09-29 17:38:15'),('45c48cce2e2d7fbdea1afc51c7c6ad26','Distrito Federal','sesion','2014-09-29 17:38:15'),('4e732ced3463d06de0ca9a15b6153677','Sonora','sesion','2014-09-29 17:38:16'),('6364d3f0f495b6ab9dcf8d3b5c6e0b01','Zacatecas','sesion','2014-09-29 17:38:16'),('6512bd43d9caa6e02c990b0a82652dca','Guanajuato','sesion','2014-09-29 17:38:15'),('6ea9ab1baa0efb9e19094440c317e21b','Tlaxcala','sesion','2014-09-29 17:38:16'),('6f4922f45568161a8cdf4ad2299f6d23','Nayarit','sesion','2014-09-29 17:38:15'),('70efdf2ec9b086079795c442636b55fb','Morelos','sesion','2014-09-29 17:38:15'),('8e296a067a37563370ded05f5a3bf3ec','Sinaloa','sesion','2014-09-29 17:38:16'),('8f14e45fceea167a5a36dedd4bea2543','Chiapas','sesion','2014-09-29 17:38:15'),('98f13708210194c475687be6106a3b84','Oaxaca','sesion','2014-09-29 17:38:15'),('9bf31c7ff062936a96d3c8bd1f8f2ff3','México','sesion','2014-09-29 17:38:15'),('a87ff679a2f3e71d9181a67b7542122c','Campeche','sesion','2014-09-29 17:38:15'),('aab3238922bcc25a6f606eb525ffdc56','Jalisco','sesion','2014-09-29 17:38:15'),('b6d767d2f8ed5d21a44b0e5886680cb9','Querétaro','sesion','2014-09-29 17:38:16'),('c16a5320fa475530d9583c34fd356ef5','Yucatán','sesion','2014-09-29 17:38:16'),('c20ad4d76fe97759aa27a0c99bff6710','Guerrero','sesion','2014-09-29 17:38:15'),('c4ca4238a0b923820dcc509a6f75849b','Aguascalientes','sesion','2014-09-29 17:38:15'),('c51ce410c124a10e0db5e4b97fc2af39','Hidalgo','sesion','2014-09-29 17:38:15'),('c74d97b01eae257e44aa9d5bade97baf','Michoacán de Ocampo','sesion','2014-09-29 17:38:15'),('c81e728d9d4c2f636f067f89cc14862c','Baja California','sesion','2014-09-29 17:38:15'),('c9f0f895fb98ab9159f51fd0297e236d','Chihuahua','sesion','2014-09-29 17:38:15'),('d3d9446802a44259755d38e6d163e820','Durango','sesion','2014-09-29 17:38:15'),('e4da3b7fbbce2345d7772b0674a318d5','Coahuila de Zaragoza','sesion','2014-09-29 17:38:15'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Baja California Sur','sesion','2014-09-29 17:38:15');
/*!40000 ALTER TABLE `tbbsnm13` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm14`
--

DROP TABLE IF EXISTS `tbbsnm14`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm14` (
  `idmunicipio` varchar(32) NOT NULL,
  `dsnombre` varchar(120) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idmunicipio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm14`
--

LOCK TABLES `tbbsnm14` WRITE;
/*!40000 ALTER TABLE `tbbsnm14` DISABLE KEYS */;
INSERT INTO `tbbsnm14` VALUES ('0004d0b59e19461ff126e3a08a814c33','Tepecoacuilco de Trujano','sesion','2014-09-29 17:39:29'),('00411460f7c92d2124a67ea0f4cb5f85','Churumuco','sesion','2014-09-29 17:38:29'),('006f52e9102a8d3be2fe5614f42ba989','Atzalan','sesion','2014-09-29 17:38:22'),('0070d23b06b1486a538c0eaa45dd167a','Tlapacoyan','sesion','2014-09-29 17:39:33'),('008bd5ad93b754d500338c253d9c1770','Tequisquiapan','sesion','2014-09-29 17:39:30'),('00a03ec6533ca7f5c644d198d815329c','Villa Aldama','sesion','2014-09-29 17:39:37'),('00ac8ed3b4327bdd4ebbebcb2ba10a00','Guevea de Humboldt','sesion','2014-09-29 17:38:38'),('00e26af6ac3b1c1c49d7c3d79c60d000','Rafael Lara Grajales','sesion','2014-09-29 17:38:59'),('00ec53c4682d36f5c4359f4ae7bd7ba1','Coatecas Altas','sesion','2014-09-29 17:38:30'),('01161aaa0b6d1345dd8fe4e481144d84','Calotmul','sesion','2014-09-29 17:38:24'),('01386bd6d8e091c2ab4c7c7de644d37b','Chinameca','sesion','2014-09-29 17:38:28'),('013a006f03dbc5392effeb8f18fda755','La Yesca','sesion','2014-09-29 17:38:47'),('013d407166ec4fa56eb1e1f8cbe183b9','Atemajac de Brizuela','sesion','2014-09-29 17:38:21'),('01882513d5fa7c329e940dda99b12147','Marín','sesion','2014-09-29 17:38:49'),('0188e8b8b014829e2fa0f430f0a95961','Salamanca','sesion','2014-09-29 17:39:00'),('01894d6f048493d2cacde3c579c315a3','Villa Talea de Castro','sesion','2014-09-29 17:39:38'),('0189caa552598b845b29b17a427692d1','Tlacoachistlahuaca','sesion','2014-09-29 17:39:32'),('018b59ce1fd616d874afad0f44ba338d','San Felipe de Jesús','sesion','2014-09-29 17:39:03'),('01931a6925d3de09e5f87419d9d55055','Totolapan','sesion','2014-09-29 17:39:34'),('019d385eb67632a7e958e23f24bd07d7','Coscomatepec','sesion','2014-09-29 17:38:32'),('01d8bae291b1e4724443375634ccfa0e','San Mateo Piñas','sesion','2014-09-29 17:39:09'),('01e00f2f4bfcbb7505cb641066f2859b','Santa María Yalina','sesion','2014-09-29 17:39:18'),('01e9565cecc4e989123f9620c1d09c09','San Jerónimo Sosola','sesion','2014-09-29 17:39:05'),('01eee509ee2f68dc6014898c309e86bf','Xalpatláhuac','sesion','2014-09-29 17:39:39'),('01f78be6f7cad02658508fe4616098a9','Españita','sesion','2014-09-29 17:38:36'),('020c8bfac8de160d4c5543b96d1fdede','San Pedro y San Pablo Teposcolula','sesion','2014-09-29 17:39:13'),('021bbc7ee20b71134d53e20206bd6feb','Nogales','sesion','2014-09-29 17:38:54'),('0233f3bb964cf325a30f8b1c2ed2da93','Yecuatla','sesion','2014-09-29 17:39:40'),('0234c510bc6d908b28c70ff313743079','Vega de Alatorre','sesion','2014-09-29 17:39:37'),('0245952ecff55018e2a459517fdb40e3','San Miguel Aloápam','sesion','2014-09-29 17:39:10'),('024d7f84fff11dd7e8d9c510137a2381','Manuel Benavides','sesion','2014-09-29 17:38:49'),('02522a2b2726fb0a03bb19f2d8d9524d','Asunción Nochixtlán','sesion','2014-09-29 17:38:21'),('0266e33d3f546cb5436a10798e657d97','Campeche','sesion','2014-09-29 17:38:24'),('02a32ad2669e6fe298e607fe7cc0e1a0','Larráinzar','sesion','2014-09-29 17:38:47'),('02b1be0d48924c327124732726097157','Trinidad García de la Cadena','sesion','2014-09-29 17:39:34'),('02e656adee09f8394b402d9958389b7d','Villa Unión','sesion','2014-09-29 17:39:38'),('02e74f10e0327ad868d138f2b4fdd6f0','Acuitzio','sesion','2014-09-29 17:38:17'),('02f039058bd48307e6f653a2005c9dd2','Santa María Zoquitlán','sesion','2014-09-29 17:39:18'),('0336dcbab05b9d5ad24f4333c7658a0e','Badiraguato','sesion','2014-09-29 17:38:23'),('0353ab4cbed5beae847a7ff6e220b5cf','Cuautinchán','sesion','2014-09-29 17:38:33'),('03afdbd66e7929b125f8597834fa83a4','Almoloya del Río','sesion','2014-09-29 17:38:18'),('03c6b06952c750899bb03d998e631860','Candelaria Loxicha','sesion','2014-09-29 17:38:25'),('03e0704b5690a2dee1861dc3ad3316c9','Nicolás Flores','sesion','2014-09-29 17:38:53'),('03e7d2ebec1e820ac34d054df7e68f48','Teoloyucan','sesion','2014-09-29 17:39:28'),('03f544613917945245041ea1581df0c2','Panabá','sesion','2014-09-29 17:38:56'),('04025959b191f8f9de3f924f0940515f','Chilapa de Álvarez','sesion','2014-09-29 17:38:28'),('04048aeca2c0f5d84639358008ed2ae7','Xalapa','sesion','2014-09-29 17:39:39'),('043c3d7e489c69b48737cc0c92d0f3a2','Metlatónoc','sesion','2014-09-29 17:38:50'),('045117b0e0a11a242b9765e79cbf113f','Ayotlán','sesion','2014-09-29 17:38:22'),('046ddf96c233a273fd390c3d0b1a9aa4','Zongolica','sesion','2014-09-29 17:39:42'),('04ad5632029cbfbed8e136e5f6f7ddfa','Yaxkukul','sesion','2014-09-29 17:39:40'),('04df4d434d481c5bb723be1b6df1ee65','Tehuacán','sesion','2014-09-29 17:39:27'),('04ecb1fa28506ccb6f72b12c0245ddbc','Guachochi','sesion','2014-09-29 17:38:38'),('05049e90fa4f5039a8cadc6acbb4b2cc','Cintalapa','sesion','2014-09-29 17:38:29'),('051928341be67dcba03f0e04104d9047','Tlacolula de Matamoros','sesion','2014-09-29 17:39:32'),('051e4e127b92f5d98d3c79b195f2b291','Hoctún','sesion','2014-09-29 17:38:39'),('052335232b11864986bb2fa20fa38748','Santo Domingo Yodohino','sesion','2014-09-29 17:39:22'),('05311655a15b75fab86956663e1819cd','San José Ayuquila','sesion','2014-09-29 17:39:05'),('0533a888904bd4867929dffd884d60b8','Santo Tomás Tamazulapan','sesion','2014-09-29 17:39:22'),('0537fb40a68c18da59a35c2bfe1ca554','Jalapa','sesion','2014-09-29 17:38:43'),('05546b0e38ab9175cd905eebcc6ebb76','Torreón','sesion','2014-09-29 17:39:34'),('0584ce565c824b7b7f50282d9a19945b','Coatlán del Río','sesion','2014-09-29 17:38:30'),('059fdcd96baeb75112f09fa1dcc740cc','Tecali de Herrera','sesion','2014-09-29 17:39:26'),('05a5cf06982ba7892ed2a6d38fe832d6','Tianguistengo','sesion','2014-09-29 17:39:31'),('05a70454516ecd9194c293b0e415777f','Villa Sola de Vega','sesion','2014-09-29 17:39:38'),('05f971b5ec196b8c65b75d2ef8267331','Domingo Arenas','sesion','2014-09-29 17:38:34'),('0609154fa35b3194026346c9cac2a248','Tecpatán','sesion','2014-09-29 17:39:27'),('060ad92489947d410d897474079c1477','Bustamante','sesion','2014-09-29 17:38:24'),('06138bc5af6023646ede0e1f7c1eac75','Casimiro Castillo','sesion','2014-09-29 17:38:26'),('061412e4a03c02f9902576ec55ebbe77','Maravilla Tenejapa','sesion','2014-09-29 17:38:49'),('062ddb6c727310e76b6200b7c71f63b5','Ozumba','sesion','2014-09-29 17:38:56'),('06409663226af2f3114485aa4e0a23b4','Atlixtac','sesion','2014-09-29 17:38:21'),('0663a4ddceacb40b095eda264a85f15c','San Luis Acatlán','sesion','2014-09-29 17:39:08'),('069059b7ef840f0c74a814ec9237b6ec','Arroyo Seco','sesion','2014-09-29 17:38:20'),('06964dce9addb1c5cb5d6e3d9838f733','Tetecala','sesion','2014-09-29 17:39:30'),('069654d5ce089c13f642d19f09a3d1c0','Soltepec','sesion','2014-09-29 17:39:23'),('06997f04a7db92466a2baa6ebc8b872d','Jerez','sesion','2014-09-29 17:38:44'),('069d3bb002acd8d7dd095917f9efe4cb','General Francisco R. Murguía','sesion','2014-09-29 17:38:37'),('06a15eb1c3836723b53e4abca8d9b879','Sultepec','sesion','2014-09-29 17:39:24'),('06a81a4fb98d149f2d31c68828fa6eb2','Santa Ana del Valle','sesion','2014-09-29 17:39:14'),('06b1338ba02add2b5d2da67663b19ebe','Tlacolulan','sesion','2014-09-29 17:39:32'),('06d5ae105ea1bea4d800bc96491876e9','Villa García','sesion','2014-09-29 17:39:38'),('06eb61b839a0cefee4967c67ccb099dc','Charo','sesion','2014-09-29 17:38:27'),('07042ac7d03d3b9911a00da43ce0079a','San Mateo Tlapiltepec','sesion','2014-09-29 17:39:10'),('072b030ba126b2f4b2374f342be9ed44','Almoloya','sesion','2014-09-29 17:38:18'),('0731460a8a5ce1626210cbf4385ae0ef','Santo Tomás Hueyotlipan','sesion','2014-09-29 17:39:22'),('074177d3eb6371e32c16c55a3b8f706b','Zacatelco','sesion','2014-09-29 17:39:41'),('07563a3fe3bbe7e3ba84431ad9d055af','El Fuerte','sesion','2014-09-29 17:38:35'),('076023edc9187cf1ac1f1163470e479a','San Simón Zahuatlán','sesion','2014-09-29 17:39:14'),('0768281a05da9f27df178b5c39a51263','Nicolás Ruíz','sesion','2014-09-29 17:38:53'),('076a0c97d09cf1a0ec3e19c7f2529f2b','Guachinango','sesion','2014-09-29 17:38:38'),('0771fc6f0f4b1d7d1bb73bbbe14e0e31','Santa Cruz Tayata','sesion','2014-09-29 17:39:16'),('0777d5c17d4066b82ab86dff8a46af6f','Atoyac','sesion','2014-09-29 17:38:22'),('077e29b11be80ab57e1a2ecabb7da330','Canatlán','sesion','2014-09-29 17:38:25'),('07811dc6c422334ce36a09ff5cd6fe71','Tierra Nueva','sesion','2014-09-29 17:39:31'),('07871915a8107172b3b5dc15a6574ad3','Manlio Fabio Altamirano','sesion','2014-09-29 17:38:49'),('07a4e20a7bbeeb7a736682b26b16ebe8','Tejupilco','sesion','2014-09-29 17:39:27'),('07a96b1f61097ccb54be14d6a47439b0','Ignacio de la Llave','sesion','2014-09-29 17:38:41'),('07c5807d0d927dcd0980f86024e5208b','Ixhuatlancillo','sesion','2014-09-29 17:38:42'),('07cb5f86508f146774a2fac4373a8e50','Tlaltenango de Sánchez Román','sesion','2014-09-29 17:39:33'),('07cdfd23373b17c6b337251c22b7ea57','Celaya','sesion','2014-09-29 17:38:26'),('07e1cd7dca89a1678042477183b7ac3f','Arcelia','sesion','2014-09-29 17:38:20'),('08040837089cdf46631a10aca5258e16','San Pedro Ixtlahuaca','sesion','2014-09-29 17:39:12'),('0829424ffa0d3a2547b6c9622c77de03','San Felipe Tepatlán','sesion','2014-09-29 17:39:03'),('08419be897405321542838d77f855226','Ixtapaluca','sesion','2014-09-29 17:38:43'),('084b6fbb10729ed4da8c3d3f5a3ae7c9','Bahía de Banderas','sesion','2014-09-29 17:38:23'),('087408522c31eeb1f982bc0eaf81d35f','Ecatepec de Morelos','sesion','2014-09-29 17:38:35'),('08b255a5d42b89b0585260b6f2360bdd','Gómez Palacio','sesion','2014-09-29 17:38:37'),('08c5433a60135c32e34f46a71175850c','Guadalcázar','sesion','2014-09-29 17:38:38'),('08d98638c6fcd194a4b1e6992063e944','Huitzilan de Serdán','sesion','2014-09-29 17:38:41'),('08e6bea8e90ba87af3c9554d94db6579','Santa María la Asunción','sesion','2014-09-29 17:39:17'),('08f90c1a417155361a5c4b8d297e0d78','Teteles de Avila Castillo','sesion','2014-09-29 17:39:30'),('08fe2621d8e716b02ec0da35256a998d','Nextlalpan','sesion','2014-09-29 17:38:53'),('091d584fced301b442654dd8c23b3fc9','Benjamín Hill','sesion','2014-09-29 17:38:23'),('093b60fd0557804c8ba0cbf1453da22f','Timilpan','sesion','2014-09-29 17:39:31'),('093f65e080a295f8076b1c5722a46aa2','Allende','sesion','2014-09-29 17:38:18'),('0950ca92a4dcf426067cfd2246bb5ff3','Temascalcingo','sesion','2014-09-29 17:39:27'),('0966289037ad9846c5e994be2a91bafa','San Juan Mixtepec -Dto. 08 -','sesion','2014-09-29 17:39:07'),('09b15d48a1514d8209b192a8b8f34e48','Sierra Mojada','sesion','2014-09-29 17:39:22'),('09c6c3783b4a70054da74f2538ed47c6','Pungarabato','sesion','2014-09-29 17:38:58'),('09fb05dd477d4ae6479985ca56c5a12d','Tampacán','sesion','2014-09-29 17:39:25'),('0a09c8844ba8f0936c20bd791130d6b6','Atexcal','sesion','2014-09-29 17:38:21'),('0a0a0c8aaa00ade50f74a3f0ca981ed7','Rincón de Romos','sesion','2014-09-29 17:38:59'),('0a113ef6b61820daa5611c870ed8d5ee','Maní','sesion','2014-09-29 17:38:49'),('0a1bf96b7165e962e90cb14648c9462d','San Mateo Nejápam','sesion','2014-09-29 17:39:09'),('0a87257e5308197df43230edf4ad1dae','Villa de Arista','sesion','2014-09-29 17:39:37'),('0aa1883c6411f7873cb83dacb17b0afc','Bacerac','sesion','2014-09-29 17:38:23'),('0ae3f79a30234b6c45a6f7d298ba1310','Tlalmanalco','sesion','2014-09-29 17:39:32'),('0b1ec366924b26fc98fa7b71a9c249cf','Tlayacapan','sesion','2014-09-29 17:39:33'),('0b8aff0438617c055eb55f0ba5d226fa','Nahuatzen','sesion','2014-09-29 17:38:52'),('0bb4aec1710521c12ee76289d9440817','Chinampa de Gorostiza','sesion','2014-09-29 17:38:28'),('0c048b3a434e49e655c1247efb389cec','Quimixtlán','sesion','2014-09-29 17:38:58'),('0c0a7566915f4f24853fc4192689aa7e','San Juan Ihualtepec','sesion','2014-09-29 17:39:07'),('0c74b7f78409a4022a2c4c5a5ca3ee19','Ciudad del Maíz','sesion','2014-09-29 17:38:29'),('0c8ce55163055c4da50a81e0a273468c','San Nicolás de los Ranchos','sesion','2014-09-29 17:39:11'),('0c9ebb2ded806d7ffda75cd0b95eb70c','San Nicolás Tolentino','sesion','2014-09-29 17:39:11'),('0cb929eae7a499e50248a3a78f7acfc7','Indaparapeo','sesion','2014-09-29 17:38:42'),('0ce2ffd21fc958d9ef0ee9ba5336e357','San Carlos Yautepec','sesion','2014-09-29 17:39:03'),('0d0871f0806eae32d30983b62252da50','Mérida','sesion','2014-09-29 17:38:50'),('0d0fd7c6e093f7b804fa0150b875b868','Concepción Buenavista','sesion','2014-09-29 17:38:31'),('0d3180d672e08b4c5312dcdafdf6ef36','Ixtapangajoya','sesion','2014-09-29 17:38:43'),('0d4f4805c36dc6853edfa4c7e1638b48','San Sebastián Ixcapa','sesion','2014-09-29 17:39:14'),('0d73a25092e5c1c9769a9f3255caa65a','Tzitzio','sesion','2014-09-29 17:39:36'),('0d7de1aca9299fe63f3e0041f02638a3','Huhí','sesion','2014-09-29 17:38:41'),('0deb1c54814305ca9ad266f53bc82511','Coneto de Comonfort','sesion','2014-09-29 17:38:31'),('0e01938fc48a2cfb5f2217fbfb00722d','Cerro Azul','sesion','2014-09-29 17:38:26'),('0e087ec55dcbe7b2d7992d6b69b519fb','Socoltenango','sesion','2014-09-29 17:39:23'),('0e095e054ee94774d6a496099eb1cf6a','Rosales','sesion','2014-09-29 17:38:59'),('0e3a37aa85a14e359df74fa77eded3f6','San Juan Chicomezúchil','sesion','2014-09-29 17:39:06'),('0e4e946668cf2afc4299b462b812caca','San Juan Yucuita','sesion','2014-09-29 17:39:08'),('0e55666a4ad822e0e34299df3591d979','San José Iturbide','sesion','2014-09-29 17:39:05'),('0e65972dce68dad4d52d063967f0a705','Balleza','sesion','2014-09-29 17:38:23'),('0e9fa1f3e9e66792401a6972d477dcc3','San Felipe Teotlalcingo','sesion','2014-09-29 17:39:03'),('0ebcc77dc72360d0eb8e9504c78d38bd','Santa Ana Maya','sesion','2014-09-29 17:39:14'),('0ed9422357395a0d4879191c66f4faa2','San Lucas','sesion','2014-09-29 17:39:08'),('0eec27c419d0fe24e53c90338cdc8bc6','Petlalcingo','sesion','2014-09-29 17:38:57'),('0efbe98067c6c73dba1250d2beaa81f9','Santiago Cacaloxtepec','sesion','2014-09-29 17:39:19'),('0efe32849d230d7f53049ddc4a4b0c60','Malinaltepec','sesion','2014-09-29 17:38:48'),('0f28b5d49b3020afeecd95b4009adf4c','Atenco','sesion','2014-09-29 17:38:21'),('0f2c9a93eea6f38fabb3acb1c31488c6','Ramos Arizpe','sesion','2014-09-29 17:38:59'),('0f3d014eead934bbdbacb62a01dc4831','San Pablo Huixtepec','sesion','2014-09-29 17:39:11'),('0f46c64b74a6c964c674853a89796c8e','Zapopan','sesion','2014-09-29 17:39:41'),('0f49c89d1e7298bb9930789c8ed59d48','Cerralvo','sesion','2014-09-29 17:38:26'),('0f840be9b8db4d3fbd5ba2ce59211f55','Mixistlán de la Reforma','sesion','2014-09-29 17:38:51'),('0f96613235062963ccde717b18f97592','Huandacareo','sesion','2014-09-29 17:38:39'),('0fcbc61acd0479dc77e3cccc0f5ffca7','El Porvenir','sesion','2014-09-29 17:38:35'),('0ff39bbbf981ac0151d340c9aa40e63e','Huatabampo','sesion','2014-09-29 17:38:40'),('0ff8033cf9437c213ee13937b1c4c455','Huehuetlán el Grande','sesion','2014-09-29 17:38:40'),('100d9f30ca54b18d14821dc88fea0631','Vicente Guerrero','sesion','2014-09-29 17:39:37'),('1019c8091693ef5c5f55970346633f92','Ocotlán de Morelos','sesion','2014-09-29 17:38:54'),('1068c6e4c8051cfd4e9ea8072e3189e2','Concepción del Oro','sesion','2014-09-29 17:38:31'),('10907813b97e249163587e6246612e21','Xochitepec','sesion','2014-09-29 17:39:40'),('109a0ca3bc27f3e96597370d5c8cf03d','Huixtán','sesion','2014-09-29 17:38:41'),('109d2dd3608f669ca17920c511c2a41e','San Pedro Garza García','sesion','2014-09-29 17:39:12'),('10a5ab2db37feedfdeaab192ead4ac0e','Iguala de la Independencia','sesion','2014-09-29 17:38:41'),('10a7cdd970fe135cf4f7bb55c0e3b59f','Ecuandureo','sesion','2014-09-29 17:38:35'),('10c272d06794d3e5785d5e7c5356e9ff','Santa Inés del Monte','sesion','2014-09-29 17:39:16'),('11108a3dbfe4636cb40b84b803b2fff6','Tenampa','sesion','2014-09-29 17:39:28'),('1113d7a76ffceca1bb350bfe145467c6','Teopisca','sesion','2014-09-29 17:39:28'),('1141938ba2c2b13f5505d7c424ebae5f','Julimes','sesion','2014-09-29 17:38:45'),('1145a30ff80745b56fb0cecf65305017','Villa de Tamazulápam del Progreso','sesion','2014-09-29 17:39:38'),('115f89503138416a242f40fb7d7f338e','Caborca','sesion','2014-09-29 17:38:24'),('1177967c7957072da3dc1db4ceb30e7a','Zapotlán del Rey','sesion','2014-09-29 17:39:41'),('11b921ef080f7736089c757404650e40','Ezequiel Montes','sesion','2014-09-29 17:38:36'),('11b9842e0a271ff252c1903e7132cd68','Chapantongo','sesion','2014-09-29 17:38:27'),('11c484ea9305ea4c7bb6b2e6d570d466','Santiago Tapextla','sesion','2014-09-29 17:39:20'),('11d0e6287202fced83f79975ec59a3a6','Santa María Nativitas','sesion','2014-09-29 17:39:17'),('11f524c3fbfeeca4aa916edcb6b6392e','Tecamachalco','sesion','2014-09-29 17:39:26'),('12b1e42dc0746f22cf361267de07073f','Veracruz','sesion','2014-09-29 17:39:37'),('130f1a8e9e102707f3f91b010f151b0b','Zentla','sesion','2014-09-29 17:39:42'),('13111c20aee51aeb480ecbd988cd8cc9','Villaldama','sesion','2014-09-29 17:39:39'),('13168e6a2e6c84b4b7de9390c0ef5ec5','Tatatila','sesion','2014-09-29 17:39:26'),('1359aa933b48b754a2f54adb688bfa77','Tlahuelilpan','sesion','2014-09-29 17:39:32'),('1368ba1ab6ed38bb1f26f36673739d54','Villa Pesqueira','sesion','2014-09-29 17:39:38'),('136f951362dab62e64eb8e841183c2a9','Tenampulco','sesion','2014-09-29 17:39:28'),('1373b284bc381890049e92d324f56de0','San Pedro Mixtepec -Dto. 22 -','sesion','2014-09-29 17:39:12'),('1385974ed5904a438616ff7bdb3f7439','Atenango del Río','sesion','2014-09-29 17:38:21'),('138bb0696595b338afbab333c555292a','Chiquihuitlán de Benito Juárez','sesion','2014-09-29 17:38:29'),('139f0874f2ded2e41b0393c4ac5644f7','Temoac','sesion','2014-09-29 17:39:27'),('13f320e7b5ead1024ac95c3b208610db','El Marqués','sesion','2014-09-29 17:38:35'),('13f3cf8c531952d72e5847c4183e6910','Coyoacán','sesion','2014-09-29 17:38:32'),('13f9896df61279c928f19721878fac41','Chimaltitán','sesion','2014-09-29 17:38:28'),('13fe9d84310e77f13a6d184dbf1232f3','Cacahoatán','sesion','2014-09-29 17:38:24'),('140f6969d5213fd0ece03148e62e461e','Atolinga','sesion','2014-09-29 17:38:21'),('1415db70fe9ddb119e23e9b2808cde38','San Miguel Totolapan','sesion','2014-09-29 17:39:11'),('142949df56ea8ae0be8b5306971900a4','Coahuayana','sesion','2014-09-29 17:38:30'),('144a3f71a03ab7c4f46f9656608efdb2','San Agustín Amatengo','sesion','2014-09-29 17:39:00'),('147702db07145348245dc5a2f2fe5683','Salvador Alvarado','sesion','2014-09-29 17:39:00'),('148510031349642de5ca0c544f31b2ef','Santa María Huatulco','sesion','2014-09-29 17:39:17'),('149e9677a5989fd342ae44213df68868','Atzitzintla','sesion','2014-09-29 17:38:22'),('14bfa6bb14875e45bba028a21ed38046','Altepexi','sesion','2014-09-29 17:38:18'),('14cfdb59b5bda1fc245aadae15b1984a','Soconusco','sesion','2014-09-29 17:39:23'),('14d9e8007c9b41f57891c48e07c23f57','San Juan Mazatlán','sesion','2014-09-29 17:39:07'),('14ea0d5b0cf49525d1866cb1e95ada5d','Villa de Chilapa de Díaz','sesion','2014-09-29 17:39:37'),('15231a7ce4ba789d13b722cc5c955834','Santa María Yavesía','sesion','2014-09-29 17:39:18'),('1534b76d325a8f591b52d302e7181331','Bocoyna','sesion','2014-09-29 17:38:23'),('1543843a4723ed2ab08e18053ae6dc5b','Coicoyán de las Flores','sesion','2014-09-29 17:38:30'),('155fa09596c7e18e50b58eb7e0c6ccb4','Siltepec','sesion','2014-09-29 17:39:22'),('1579779b98ce9edb98dd85606f2c119d','Ocoyucan','sesion','2014-09-29 17:38:55'),('1587965fb4d4b5afe8428a4a024feb0d','Nauzontla','sesion','2014-09-29 17:38:53'),('158f3069a435b314a80bdcb024f8e422','Chenalhó','sesion','2014-09-29 17:38:27'),('158fc2ddd52ec2cf54d3c161f2dd6517','Santa María Ecatepec','sesion','2014-09-29 17:39:17'),('1595af6435015c77a7149e92a551338e','Huitzilac','sesion','2014-09-29 17:38:41'),('15d185eaa7c954e77f5343d941e25fbd','Temósachic','sesion','2014-09-29 17:39:28'),('15d4e891d784977cacbfcbb00c48f133','Coxquihui','sesion','2014-09-29 17:38:32'),('15de21c670ae7c3f6f3f1f37029303c9','Etzatlán','sesion','2014-09-29 17:38:36'),('16026d60ff9b54410b3435b403afd226','Zempoala','sesion','2014-09-29 17:39:42'),('160c88652d47d0be60bfbfed25111412','Sacalum','sesion','2014-09-29 17:39:00'),('1651cf0d2f737d7adeab84d339dbabd3','Cotaxtla','sesion','2014-09-29 17:38:32'),('1679091c5a880faf6fb5e6087eb1b2dc','Acala','sesion','2014-09-29 17:38:16'),('168908dd3227b8358eababa07fcaf091','Miquihuana','sesion','2014-09-29 17:38:51'),('169779d3852b32ce8b1a1724dbf5217d','Venustiano Carranza','sesion','2014-09-29 17:39:37'),('16a5cdae362b8d27a1d8f8c7b78b4330','Cerro de San Pedro','sesion','2014-09-29 17:38:26'),('16ba72172e6a4f1de54d11ab6967e371','Tlalixtac de Cabrera','sesion','2014-09-29 17:39:32'),('16c222aa19898e5058938167c8ab6c57','Ensenada','sesion','2014-09-29 17:38:36'),('16e6a3326dd7d868cbc926602a61e4d0','Quintana Roo','sesion','2014-09-29 17:38:58'),('1700002963a49da13542e0726b7bb758','Chalchihuitán','sesion','2014-09-29 17:38:26'),('170c944978496731ba71f34c25826a34','Malinalco','sesion','2014-09-29 17:38:48'),('1714726c817af50457d810aae9d27a2e','San Miguel Tilquiápam','sesion','2014-09-29 17:39:11'),('1728efbda81692282ba642aafd57be3a','Fresnillo','sesion','2014-09-29 17:38:37'),('17326d10d511828f6b34fa6d751739e2','San Blas','sesion','2014-09-29 17:39:02'),('17c276c8e723eb46aef576537e9d56d0','Hueyotlipan','sesion','2014-09-29 17:38:41'),('17c3433fecc21b57000debdf7ad5c930','Sitalá','sesion','2014-09-29 17:39:23'),('17d63b1625c816c22647a73e1482372b','Concepción Pápalo','sesion','2014-09-29 17:38:31'),('17e23e50bedc63b4095e3d8204ce063b','San Pedro Mártir Quiechapa','sesion','2014-09-29 17:39:12'),('17e62166fc8586dfa4d1bc0e1742c08b','Ahuatlán','sesion','2014-09-29 17:38:17'),('17ed8abedc255908be746d245e50263a','Santiago Tuxtla','sesion','2014-09-29 17:39:20'),('17fafe5f6ce2f1904eb09d2e80a4cbf6','San Jerónimo Coatlán','sesion','2014-09-29 17:39:05'),('1819932ff5cf474f4f19e7c7024640c2','Ursulo Galván','sesion','2014-09-29 17:39:36'),('182be0c5cdcd5072bb1864cdee4d3d6e','Agua Dulce','sesion','2014-09-29 17:38:17'),('184260348236f9554fe9375772ff966e','Progreso','sesion','2014-09-29 17:38:58'),('185c29dc24325934ee377cfda20e414c','Higueras','sesion','2014-09-29 17:38:39'),('185e65bc40581880c4f2c82958de8cfe','Jalcomulco','sesion','2014-09-29 17:38:43'),('186a157b2992e7daed3677ce8e9fe40f','San Juan Tabaá','sesion','2014-09-29 17:39:08'),('1896a3bf730516dd643ba67b4c447d36','Río Bravo','sesion','2014-09-29 17:38:59'),('18997733ec258a9fcaf239cc55d53363','Coroneo','sesion','2014-09-29 17:38:31'),('18d10dc6e666eab6de9215ae5b3d54df','San Salvador','sesion','2014-09-29 17:39:13'),('18d8042386b79e2c279fd162df0205c8','Colotlán','sesion','2014-09-29 17:38:30'),('18ead4c77c3f40dabf9735432ac9d97a','San Pedro Teutila','sesion','2014-09-29 17:39:13'),('1905aedab9bf2477edc068a355bba31a','Juchitlán','sesion','2014-09-29 17:38:45'),('192fc044e74dffea144f9ac5dc9f3395','Hueyapan','sesion','2014-09-29 17:38:41'),('193002e668758ea9762904da1a22337c','San Cristóbal Amoltepec','sesion','2014-09-29 17:39:03'),('1943102704f8f8f3302c2b730728e023','Tuxtilla','sesion','2014-09-29 17:39:35'),('194cf6c2de8e00c05fcf16c498adc7bf','Tochtepec','sesion','2014-09-29 17:39:33'),('19b650660b253761af189682e03501dd','Los Ramones','sesion','2014-09-29 17:38:48'),('19bc916108fc6938f52cb96f7e087941','Ixhuatlán del Sureste','sesion','2014-09-29 17:38:42'),('19ca14e7ea6328a42e0eb13d585e4c22','Aguascalientes','sesion','2014-09-29 17:38:17'),('19de10adbaa1b2ee13f77f679fa1483a','Tampico Alto','sesion','2014-09-29 17:39:25'),('19f3cd308f1455b3fa09a282e0d496f4','Candelaria','sesion','2014-09-29 17:38:25'),('1a0a283bfe7c549dee6c638a05200e32','Totolac','sesion','2014-09-29 17:39:34'),('1a3f91fead97497b1a96d6104ad339f6','Xochiltepec','sesion','2014-09-29 17:39:40'),('1a5b1e4daae265b790965a275b53ae50','Coyuca de Benítez','sesion','2014-09-29 17:38:32'),('1aa48fc4880bb0c9b8a3bf979d3b917e','Jungapeo','sesion','2014-09-29 17:38:45'),('1abb1e1ea5f481b589da52303b091cbb','San Mateo Sindihui','sesion','2014-09-29 17:39:09'),('1afa34a7f984eeabdbb0a7d494132ee5','Asunción Cacalotepec','sesion','2014-09-29 17:38:21'),('1b0114c51cc532ed34e1954b5b9e4b58','Otumba','sesion','2014-09-29 17:38:55'),('1b36ea1c9b7a1c3ad668b8bb5df7963f','Tepexco','sesion','2014-09-29 17:39:29'),('1b5230e3ea6d7123847ad55a1e06fffd','Tres Valles','sesion','2014-09-29 17:39:34'),('1baff70e2669e8376347efd3a874a341','San Miguel Suchixtepec','sesion','2014-09-29 17:39:11'),('1bb91f73e9d31ea2830a5e73ce3ed328','Filomeno Mata','sesion','2014-09-29 17:38:36'),('1bc0249a6412ef49b07fe6f62e6dc8de','Santa Catalina Quierí','sesion','2014-09-29 17:39:15'),('1be3bc32e6564055d5ca3e5a354acbef','Doctor Coss','sesion','2014-09-29 17:38:34'),('1c1d4df596d01da60385f0bb17a4a9e0','Mina','sesion','2014-09-29 17:38:51'),('1c383cd30b7c298ab50293adfecb7b18','Agualeguas','sesion','2014-09-29 17:38:17'),('1c54985e4f95b7819ca0357c0cb9a09f','Santa Cruz Zenzontepec','sesion','2014-09-29 17:39:16'),('1c65cef3dfd1e00c0b03923a1c591db4','San Antonio de la Cal','sesion','2014-09-29 17:39:02'),('1c6a0198177bfcc9bd93f6aab94aad3c','Villa de Álvarez','sesion','2014-09-29 17:39:37'),('1c9ac0159c94d8d0cbedc973445af2da','Atlequizayan','sesion','2014-09-29 17:38:21'),('1cc3633c579a90cfdd895e64021e2163','Mesones Hidalgo','sesion','2014-09-29 17:38:50'),('1cd3882394520876dc88d1472aa2a93f','San Pablo del Monte','sesion','2014-09-29 17:39:11'),('1ce927f875864094e3906a4a0b5ece68','Mixtlán','sesion','2014-09-29 17:38:51'),('1cecc7a77928ca8133fa24680a88d2f9','San Felipe','sesion','2014-09-29 17:39:03'),('1d72310edc006dadf2190caad5802983','San Andrés Huayápam','sesion','2014-09-29 17:39:01'),('1d7f7abc18fcb43975065399b0d1e48e','Atlatlahucan','sesion','2014-09-29 17:38:21'),('1e056d2b0ebd5c878c550da6ac5d3724','Mazatán','sesion','2014-09-29 17:38:50'),('1e1d184167ca7676cf665225e236a3d2','Reforma','sesion','2014-09-29 17:38:59'),('1e48c4420b7073bc11916c6c1de226bb','Navojoa','sesion','2014-09-29 17:38:53'),('1e4d36177d71bbb3558e43af9577d70e','Tepetzintla','sesion','2014-09-29 17:39:29'),('1e6e0a04d20f50967c64dac2d639a577','Parácuaro','sesion','2014-09-29 17:38:56'),('1e8c391abfde9abea82d75a2d60278d4','Unión de Tula','sesion','2014-09-29 17:39:36'),('1e913e1b06ead0b66e30b6867bf63549','Tempoal','sesion','2014-09-29 17:39:28'),('1ecfb463472ec9115b10c292ef8bc986','Ixhuatán','sesion','2014-09-29 17:38:42'),('1ee3dfcd8a0645a25a35977997223d22','San Ignacio Cerro Gordo','sesion','2014-09-29 17:39:04'),('1ef91c212e30e14bf125e9374262401f','Santa María Peñoles','sesion','2014-09-29 17:39:18'),('1efa39bcaec6f3900149160693694536','Lerdo','sesion','2014-09-29 17:38:47'),('1f0e3dad99908345f7439f8ffabdffc4','Acatzingo','sesion','2014-09-29 17:38:17'),('1f1baa5b8edac74eb4eaa329f14a0361','Sayula','sesion','2014-09-29 17:39:22'),('1f3202d820180a39f736f20fce790de8','San Marcos','sesion','2014-09-29 17:39:09'),('1f36c15d6a3d18d52e8d493bc8187cb9','Tepexi de Rodríguez','sesion','2014-09-29 17:39:29'),('1f4477bad7af3616c1f933a02bfabe4e','Méndez','sesion','2014-09-29 17:38:50'),('1f4fe6a4411edc2ff625888b4093e917','Tlaltizapán de Zapata','sesion','2014-09-29 17:39:33'),('1f50893f80d6830d62765ffad7721742','Magdalena Yodocono de Porfirio Díaz','sesion','2014-09-29 17:38:48'),('1f71e393b3809197ed66df836fe833e5','Teocelo','sesion','2014-09-29 17:39:28'),('1fc214004c9481e4c8073e85323bfd4b','Los Reyes','sesion','2014-09-29 17:38:48'),('1ff1de774005f8da13f42943881c655f','Acteopan','sesion','2014-09-29 17:38:17'),('1ff8a7b5dc7a7d1f0ed65aaa29c04b1e','Axapusco','sesion','2014-09-29 17:38:22'),('201d7288b4c18a679e48b31c72c30ded','Suchiate','sesion','2014-09-29 17:39:23'),('202cb962ac59075b964b07152d234b70','Armadillo de los Infante','sesion','2014-09-29 17:38:20'),('204da255aea2cd4a75ace6018fad6b4d','San Pedro Pochutla','sesion','2014-09-29 17:39:13'),('2050e03ca119580f74cca14cc6e97462','Cuetzala del Progreso','sesion','2014-09-29 17:38:33'),('2056d8c1dec3d12cbce646b348d189d1','Viesca','sesion','2014-09-29 17:39:37'),('207f88018f72237565570f8a9e5ca240','Santa María Apazco','sesion','2014-09-29 17:39:16'),('208e43f0e45c4c78cafadb83d2888cb6','Pueblo Nuevo Solistahuacán','sesion','2014-09-29 17:38:58'),('20aee3a5f4643755a79ee5f6a73050ac','Maravatío','sesion','2014-09-29 17:38:49'),('20b5e1cf8694af7a3c1ba4a87f073021','Ocozocoautla de Espinosa','sesion','2014-09-29 17:38:55'),('20d135f0f28185b84a4cf7aa51f29500','Peñón Blanco','sesion','2014-09-29 17:38:57'),('20f07591c6fcb220ffe637cda29bb3f6','Cedral','sesion','2014-09-29 17:38:26'),('210f760a89db30aa72ca258a3483cc7f','Mainero','sesion','2014-09-29 17:38:48'),('211c1e0b83b9c69fa9c4bdede203c1e3','Vanegas','sesion','2014-09-29 17:39:37'),('215a71a12769b056c3c32e7299f1c5ed','San Juan Mixtepec -Dto. 26 -','sesion','2014-09-29 17:39:07'),('217eedd1ba8c592db97d0dbe54c7adfc','Iztapalapa','sesion','2014-09-29 17:38:43'),('218a0aefd1d1a4be65601cc6ddc1520e','Cutzamala de Pinzón','sesion','2014-09-29 17:38:33'),('21be9a4bd4f81549a9d1d241981cec3c','Santiago Zoochila','sesion','2014-09-29 17:39:21'),('21fe5b8ba755eeaece7a450849876228','Santa Cruz Tlaxcala','sesion','2014-09-29 17:39:16'),('226d1f15ecd35f784d2a20c3ecf56d7f','San Miguel Tenango','sesion','2014-09-29 17:39:11'),('228499b55310264a8ea0e27b6e7c6ab6','San Vicente Lachixío','sesion','2014-09-29 17:39:14'),('2290a7385ed77cc5592dc2153229f082','Orizaba','sesion','2014-09-29 17:38:55'),('2291d2ec3b3048d1a6f86c2c4591b7e0','Huejuquilla el Alto','sesion','2014-09-29 17:38:40'),('229754d7799160502a143a72f6789927','Zautla','sesion','2014-09-29 17:39:42'),('22ac3c5a5bf0b520d281c122d1490650','Lerma','sesion','2014-09-29 17:38:47'),('22fb0cee7e1f3bde58293de743871417','Madero','sesion','2014-09-29 17:38:48'),('231141b34c82aa95e48810a9d1b33a79','Santiago Tepetlapa','sesion','2014-09-29 17:39:20'),('233509073ed3432027d48b1a83f5fbd2','Hidalgo del Parral','sesion','2014-09-29 17:38:39'),('2387337ba1e0b0249ba90f55b2ba2521','Naucalpan de Juárez','sesion','2014-09-29 17:38:53'),('23ad3e314e2a2b43b4c720507cec0723','San Lorenzo Albarradas','sesion','2014-09-29 17:39:08'),('23c97e9cb93576e45d2feaf00d0e8502','Tuxtla Gutiérrez','sesion','2014-09-29 17:39:35'),('23ce1851341ec1fa9e0c259de10bf87c','Mazapil','sesion','2014-09-29 17:38:50'),('23d2e1578544b172cca332ff74bddf5f','Tixmehuac','sesion','2014-09-29 17:39:31'),('24146db4eb48c718b84cae0a0799dcfc','Nonoava','sesion','2014-09-29 17:38:54'),('2421fcb1263b9530df88f7f002e78ea5','Cosoleacaque','sesion','2014-09-29 17:38:32'),('242c100dc94f871b6d7215b868a875f8','San Felipe del Progreso','sesion','2014-09-29 17:39:03'),('243facb29564e7b448834a7c9d901201','Xoxocotla','sesion','2014-09-29 17:39:40'),('2451041557a22145b3701b0184109cab','Santa María Ipalapa','sesion','2014-09-29 17:39:17'),('24681928425f5a9133504de568f5f6df','Huitziltepec','sesion','2014-09-29 17:38:41'),('24896ee4c6526356cc127852413ea3b4','Mazapa de Madero','sesion','2014-09-29 17:38:49'),('248e844336797ec98478f85e7626de4a','Cosautlán de Carvajal','sesion','2014-09-29 17:38:32'),('24917db15c4e37e421866448c9ab23d8','Uayma','sesion','2014-09-29 17:39:36'),('24b16fede9a67c9251d3e7c7161c83ac','Ciudad Madero','sesion','2014-09-29 17:38:29'),('24f0d2c90473b2bc949ad962e61d9bcb','Tonaya','sesion','2014-09-29 17:39:34'),('250cf8b51c773f3f8dc8b4be867a9a02','Cuatro Ciénegas','sesion','2014-09-29 17:38:32'),('253614bbac999b38b5b60cae531c4969','Texhuacán','sesion','2014-09-29 17:39:30'),('253f7b5d921338af34da817c00f42753','San Pedro Cajonos','sesion','2014-09-29 17:39:12'),('2557911c1bf75c2b643afb4ecbfc8ec2','Tixpéhual','sesion','2014-09-29 17:39:31'),('258be18e31c8188555c2ff05b4d542c3','General Felipe Ángeles','sesion','2014-09-29 17:38:37'),('2596a54cdbb555cfd09cd5d991da0f55','Valle de Chalco Solidaridad','sesion','2014-09-29 17:39:36'),('25b2822c2f5a3230abfadd476e8b04c9','Coronado','sesion','2014-09-29 17:38:31'),('25db67c5657914454081c6a18e93d6dd','Villa de la Paz','sesion','2014-09-29 17:39:38'),('25ddc0f8c9d3e22e03d3076f98d83cb2','Cuetzalan del Progreso','sesion','2014-09-29 17:38:33'),('25df35de87aa441b88f22a6c2a830a17','San Andrés Paxtlán','sesion','2014-09-29 17:39:01'),('25e2a30f44898b9f3e978b1786dcd85c','Santiago Jamiltepec','sesion','2014-09-29 17:39:19'),('2612aa892d962d6f8056b195ca6e550d','Santiago Tilantongo','sesion','2014-09-29 17:39:20'),('26337353b7962f533d78c762373b3318','Cuencamé','sesion','2014-09-29 17:38:33'),('26408ffa703a72e8ac0117e74ad46f33','Huehuetla','sesion','2014-09-29 17:38:40'),('2647c1dba23bc0e0f9cdf75339e120d2','Soledad Atzompa','sesion','2014-09-29 17:39:23'),('26505e0494662534f633586941b77d0c','Yehualtepec','sesion','2014-09-29 17:39:40'),('26588e932c7ccfa1df309280702fe1b5','San Bartolomé Zoogocho','sesion','2014-09-29 17:39:02'),('26657d5ff9020d2abefe558796b99584','Angel R. Cabada','sesion','2014-09-29 17:38:19'),('26751be1181460baf78db8d5eb7aad39','Santa María Texcatitlán','sesion','2014-09-29 17:39:18'),('26dd0dbc6e3f4c8043749885523d6a25','Hostotipaquillo','sesion','2014-09-29 17:38:39'),('26e359e83860db1d11b6acca57d8ea88','Chanal','sesion','2014-09-29 17:38:27'),('26f5bd4aa64fdadf96152ca6e6408068','Tlaxcoapan','sesion','2014-09-29 17:39:33'),('270edd69788dce200a3b395a6da6fdb7','Santiago Lachiguiri','sesion','2014-09-29 17:39:19'),('2715518c875999308842e3455eda2fe3','San Andrés Duraznal','sesion','2014-09-29 17:39:01'),('2723d092b63885e0d7c260cc007e8b9d','Apodaca','sesion','2014-09-29 17:38:20'),('274ad4786c3abca69fa097b85867d9a4','Bejucal de Ocampo','sesion','2014-09-29 17:38:23'),('277281aada22045c03945dcb2ca6f2ec','San Vicente Coatlán','sesion','2014-09-29 17:39:14'),('277a78fc05c8864a170e9a56ceeabc4c','Teotongo','sesion','2014-09-29 17:39:28'),('27ed0fb950b856b06e1273989422e7d3','Oaxaca de Juárez','sesion','2014-09-29 17:38:54'),('2812e5cf6d8f21d69c91dddeefb792a7','San Francisco de Borja','sesion','2014-09-29 17:39:04'),('2823f4797102ce1a1aec05359cc16dd9','Jamay','sesion','2014-09-29 17:38:44'),('28267ab848bcf807b2ed53c3a8f8fc8a','Juchitán','sesion','2014-09-29 17:38:45'),('2838023a778dfaecdc212708f721b788','Álamo Temapache','sesion','2014-09-29 17:38:18'),('285ab9448d2751ee57ece7f762c39095','San Agustín Loxicha','sesion','2014-09-29 17:39:01'),('285e19f20beded7d215102b49d5c09a0','Dzemul','sesion','2014-09-29 17:38:34'),('285f89b802bcb2651801455c86d78f2a','Quechultenango','sesion','2014-09-29 17:38:58'),('286674e3082feb7e5afb92777e48821f','San Francisco de Conchos','sesion','2014-09-29 17:39:04'),('287e03db1d99e0ec2edb90d079e142f3','Moroleón','sesion','2014-09-29 17:38:52'),('288cc0ff022877bd3df94bc9360b9c5d','Jiménez del Teul','sesion','2014-09-29 17:38:44'),('289dff07669d7a23de0ef88d2f7129e7','Calkiní','sesion','2014-09-29 17:38:24'),('28dc6b0e1b33769b4b94685e4f4d1e5c','Santiago del Río','sesion','2014-09-29 17:39:19'),('28dd2c7955ce926456240b2ff0100bde','Amanalco','sesion','2014-09-29 17:38:19'),('28e209b61a52482a0ae1cb9f5959c792','San Jerónimo Tecuanipan','sesion','2014-09-29 17:39:05'),('28f0b864598a1291557bed248a998d4e','Cohuecan','sesion','2014-09-29 17:38:30'),('28fc2782ea7ef51c1104ccf7b9bea13d','San Juan Quiotepec','sesion','2014-09-29 17:39:07'),('291597a100aadd814d197af4f4bab3a7','Huanusco','sesion','2014-09-29 17:38:39'),('2952351097998ac1240cb2ab7333a3d2','Xochiapulco','sesion','2014-09-29 17:39:39'),('29530de21430b7540ec3f65135f7323c','Temoaya','sesion','2014-09-29 17:39:28'),('296472c9542ad4d4788d543508116cbc','Tlalpujahua','sesion','2014-09-29 17:39:33'),('297fa7777981f402dbba17e9f29e292d','Santa Catarina Yosonotú','sesion','2014-09-29 17:39:15'),('298923c8190045e91288b430794814c4','Nejapa de Madero','sesion','2014-09-29 17:38:53'),('298f95e1bf9136124592c8d4825a06fc','El Salto','sesion','2014-09-29 17:38:35'),('29921001f2f04bd3baee84a12e98098f','San Pedro y San Pablo Ayutla','sesion','2014-09-29 17:39:13'),('299570476c6f0309545110c592b6a63b','Santo Domingo','sesion','2014-09-29 17:39:21'),('299a23a2291e2126b91d54f3601ec162','Opichén','sesion','2014-09-29 17:38:55'),('299fb2142d7de959380f91c01c3a293c','San Lucas Tecopilco','sesion','2014-09-29 17:39:08'),('2a084e55c87b1ebcdaad1f62fdbbac8e','Loreto','sesion','2014-09-29 17:38:47'),('2a27b8144ac02f67687f76782a3b5d8f','Santa María Zacatepec','sesion','2014-09-29 17:39:18'),('2a34abd6ebbd7fcf5a4421229c946c0a','Tuzantán','sesion','2014-09-29 17:39:35'),('2a38a4a9316c49e5a833517c45d31070','Ameca','sesion','2014-09-29 17:38:19'),('2a50e9c2d6b89b95bcb416d6857f8b45','San Ignacio Río Muerto','sesion','2014-09-29 17:39:04'),('2a79ea27c279e471f4d180b08d62b00a','Atlautla','sesion','2014-09-29 17:38:21'),('2a8a812400df8963b2e2ac0ed01b07b8','Xicohtzinco','sesion','2014-09-29 17:39:39'),('2a9d121cd9c3a1832bb6d2cc6bd7a8a7','Maxcanú','sesion','2014-09-29 17:38:49'),('2ab56412b1163ee131e1246da0955bd1','Morelia','sesion','2014-09-29 17:38:52'),('2ac2406e835bd49c70469acae337d292','Rojas de Cuauhtémoc','sesion','2014-09-29 17:38:59'),('2afe4567e1bf64d32a5527244d104cea','Ixtacomitán','sesion','2014-09-29 17:38:42'),('2b0f658cbffd284984fb11d90254081f','Tixcacalcupul','sesion','2014-09-29 17:39:31'),('2b24d495052a8ce66358eb576b8912c8','Atil','sesion','2014-09-29 17:38:21'),('2b38c2df6a49b97f706ec9148ce48d86','Purépero','sesion','2014-09-29 17:38:58'),('2b3bf3eee2475e03885a110e9acaab61','San Pablo Macuiltianguis','sesion','2014-09-29 17:39:11'),('2b44928ae11fb9384c4cf38708677c48','Aquismón','sesion','2014-09-29 17:38:20'),('2b45c629e577731c4df84fc34f936a89','Zimatlán de Álvarez','sesion','2014-09-29 17:39:42'),('2b6d65b9a9445c4271ab9076ead5605a','Ruíz','sesion','2014-09-29 17:39:00'),('2b8a61594b1f4c4db0902a8a395ced93','El Arenal','sesion','2014-09-29 17:38:35'),('2b8eba3cb0d0f1d761cb74d94a5ace36','Yutanduchi de Guerrero','sesion','2014-09-29 17:39:41'),('2ba596643cbbbc20318224181fa46b28','Misantla','sesion','2014-09-29 17:38:51'),('2ba8698b79439589fdd2b0f7218d8b07','San Baltazar Yatzachi el Bajo','sesion','2014-09-29 17:39:02'),('2bb232c0b13c774965ef8558f0fbd615','El Higo','sesion','2014-09-29 17:38:35'),('2bcab9d935d219641434683dd9d18a03','Nadadores','sesion','2014-09-29 17:38:52'),('2bd7f907b7f5b6bbd91822c0c7b835f6','San Juan Juquila Mixes','sesion','2014-09-29 17:39:07'),('2c89109d42178de8a367c0228f169bf8','San Antonio Nanahuatípam','sesion','2014-09-29 17:39:02'),('2ca65f58e35d9ad45bf7f3ae5cfd08f1','Jaumave','sesion','2014-09-29 17:38:44'),('2cad8fa47bbef282badbb8de5374b894','Toluca','sesion','2014-09-29 17:39:34'),('2cb6b10338a7fc4117a80da24b582060','Santa María Lachixío','sesion','2014-09-29 17:39:17'),('2cbca44843a864533ec05b321ae1f9d1','Penjamillo','sesion','2014-09-29 17:38:57'),('2cd4e8a2ce081c3d7c32c3cde4312ef7','Tochimilco','sesion','2014-09-29 17:39:33'),('2cfd4560539f887a5e420412b370b361','Pijijiapan','sesion','2014-09-29 17:38:57'),('2d00f43f07911355d4151f13925ff292','Teocaltiche','sesion','2014-09-29 17:39:28'),('2d1b2a5ff364606ff041650887723470','Tasquillo','sesion','2014-09-29 17:39:26'),('2d2ca7eedf739ef4c3800713ec482e1a','Zináparo','sesion','2014-09-29 17:39:42'),('2d3acd3e240c61820625fff66a19938f','Yaxe','sesion','2014-09-29 17:39:40'),('2d405b367158e3f12d7c1e31a96b3af3','Tlalixcoyan','sesion','2014-09-29 17:39:32'),('2d579dc29360d8bbfbb4aa541de5afa9','Timucuy','sesion','2014-09-29 17:39:31'),('2d6cc4b2d139a53512fb8cbb3086ae2e','Dzitás','sesion','2014-09-29 17:38:34'),('2d969e2cee8cfa07ce7ca0bb13c7a36d','Villa del Carbón','sesion','2014-09-29 17:39:38'),('2dace78f80bc92e6d7493423d729448e','Juan Galindo','sesion','2014-09-29 17:38:45'),('2de5d16682c3c35007e4e92982f1a2ba','San Antonio Huitepec','sesion','2014-09-29 17:39:02'),('2dea61eed4bceec564a00115c4d21334','Hueytamalco','sesion','2014-09-29 17:38:41'),('2df45244f09369e16ea3f9117ca45157','San Francisco del Rincón','sesion','2014-09-29 17:39:04'),('2dffbc474aa176b6dc957938c15d0c8b','San Martín Toxpalan','sesion','2014-09-29 17:39:09'),('2e65f2f2fdaf6c699b223c61b1b5ab89','Jacona','sesion','2014-09-29 17:38:43'),('2eace51d8f796d04991c831a07059758','Tlahuiltepa','sesion','2014-09-29 17:39:32'),('2f25f6e326adb93c5787175dda209ab6','Tangamandapio','sesion','2014-09-29 17:39:25'),('2f29b6e3abc6ebdefb55456ea6ca5dc8','Rayones','sesion','2014-09-29 17:38:59'),('2f2b265625d76a6704b08093c652fd79','Chignautla','sesion','2014-09-29 17:38:28'),('2f37d10131f2a483a8dd005b3d14b0d9','Huehuetlán el Chico','sesion','2014-09-29 17:38:40'),('2f55707d4193dc27118a0f19a1985716','Doctor Arroyo','sesion','2014-09-29 17:38:34'),('2f885d0fbe2e131bfc9d98363e55d1d4','Miahuatlán','sesion','2014-09-29 17:38:51'),('301ad0e3bd5cb1627a2044908a42fdc2','Lafragua','sesion','2014-09-29 17:38:47'),('303ed4c69846ab36c2904d3ba8573050','Huatlatlauca','sesion','2014-09-29 17:38:40'),('309928d4b100a5d75adff48a9bfc1ddb','San Lorenzo Cacaotepec','sesion','2014-09-29 17:39:08'),('309fee4e541e51de2e41f21bebb342aa','Santa Ana Tavela','sesion','2014-09-29 17:39:14'),('30bb3825e8f631cc6075c0f87bb4978c','Gómez Farías','sesion','2014-09-29 17:38:37'),('30c8e1ca872524fbf7ea5c519ca397ee','San Lorenzo Cuaunecuiltitla','sesion','2014-09-29 17:39:08'),('30ef30b64204a3088a26bc2e6ecf7602','Huautla de Jiménez','sesion','2014-09-29 17:38:40'),('310ce61c90f3a46e340ee8257bc70e93','San Andrés Teotilálpam','sesion','2014-09-29 17:39:01'),('310dcbbf4cce62f762a2aaa148d556bd','Chignahuapan','sesion','2014-09-29 17:38:28'),('312351bff07989769097660a56395065','Tihuatlán','sesion','2014-09-29 17:39:31'),('3147da8ab4a0437c15ef51a5cc7f2dc4','Xochistlahuaca','sesion','2014-09-29 17:39:40'),('31839b036f63806cba3f47b93af8ccb5','La Huerta','sesion','2014-09-29 17:38:46'),('31857b449c407203749ae32dd0e7d64a','Otáez','sesion','2014-09-29 17:38:55'),('31b3b31a1c2f8a370206f111127c0dbd','Nava','sesion','2014-09-29 17:38:53'),('31fefc0e570cb3860f2a6d4b38c6490d','Baca','sesion','2014-09-29 17:38:22'),('320722549d1751cf3f247855f937b982','Chichimilá','sesion','2014-09-29 17:38:27'),('3210ddbeaa16948a702b6049b8d9a202','San Andrés Sinaxtla','sesion','2014-09-29 17:39:01'),('3214a6d842cc69597f9edf26df552e43','Tapachula','sesion','2014-09-29 17:39:26'),('325995af77a0e8b06d1204a171010b3a','Tlalpan','sesion','2014-09-29 17:39:32'),('3295c76acbf4caaed33c36b1b5fc2cb1','Altamira','sesion','2014-09-29 17:38:18'),('329e6581efbc90bd92a1f22c4ba2103d','Xico','sesion','2014-09-29 17:39:39'),('32b30a250abd6331e03a2a1f16466346','La Antigua','sesion','2014-09-29 17:38:46'),('32bb90e8976aab5298d5da10fe66f21d','Altzayanca','sesion','2014-09-29 17:38:18'),('32e05616c8ed659463f9af00b142dd6f','Valle de Bravo','sesion','2014-09-29 17:39:36'),('3323fe11e9595c09af38fe67567a9394','Villa Purificación','sesion','2014-09-29 17:39:38'),('3328bdf9a4b9504b9398284244fe97c2','Huixquilucan','sesion','2014-09-29 17:38:41'),('333222170ab9edca4785c39f55221fe7','Teloloapan','sesion','2014-09-29 17:39:27'),('333ac5d90817d69113471fbb6e531bee','Uruachi','sesion','2014-09-29 17:39:36'),('335f5352088d7d9bf74191e006d8e24c','Calvillo','sesion','2014-09-29 17:38:24'),('33ceb07bf4eeb3da587e268d663aba1a','San Agustín Tlacotepec','sesion','2014-09-29 17:39:01'),('33e75ff09dd601bbe69f351039152189','Acula','sesion','2014-09-29 17:38:17'),('33e8075e9970de0cfea955afd4644bb2','Elota','sesion','2014-09-29 17:38:35'),('33ebd5b07dc7e407752fe773eed20635','San José de Gracia','sesion','2014-09-29 17:39:05'),('3416a75f4cea9109507cacd8e2f2aefc','Ahualulco','sesion','2014-09-29 17:38:17'),('34173cb38f07f89ddbebc2ac9128303f','Acultzingo','sesion','2014-09-29 17:38:17'),('3435c378bb76d4357324dd7e69f3cd18','Guadalupe de Ramírez','sesion','2014-09-29 17:38:38'),('3473decccb0509fb264818a7512a8b9b','Río Blanco','sesion','2014-09-29 17:38:59'),('3493894fa4ea036cfc6433c3e2ee63b0','González','sesion','2014-09-29 17:38:38'),('34ed066df378efacc9b924ec161e7639','Chapab','sesion','2014-09-29 17:38:27'),('35051070e572e47d2c26c241ab88307f','Doctor González','sesion','2014-09-29 17:38:34'),('350db081a661525235354dd3e19b8c05','Ucú','sesion','2014-09-29 17:39:36'),('351b33587c5fdd93bd42ef7ac9995a28','San Pedro Teozacoalco','sesion','2014-09-29 17:39:13'),('352407221afb776e3143e8a1a0577885','Magdalena Teitipac','sesion','2014-09-29 17:38:48'),('352fe25daf686bdb4edca223c921acea','Colón','sesion','2014-09-29 17:38:30'),('35309226eb45ec366ca86a4329a2b7c3','San Pedro Jaltepetongo','sesion','2014-09-29 17:39:12'),('35464c848f410e55a13bb9d78e7fddd0','San Pedro Quiatoni','sesion','2014-09-29 17:39:13'),('3546ab441e56fa333f8b44b610d95691','San Jacinto Tlacotepec','sesion','2014-09-29 17:39:05'),('357a6fdf7642bf815a88822c447d9dc4','Chila','sesion','2014-09-29 17:38:28'),('359f38463d487e9e29bd20e24f0c050a','San Juan Juquila Vijanos','sesion','2014-09-29 17:39:07'),('35cf8659cfcb13224cbd47863a34fc58','Juárez Hidalgo','sesion','2014-09-29 17:38:45'),('35f4a8d465e6e1edc05f3d8ab658c551','Amatán','sesion','2014-09-29 17:38:19'),('3621f1454cacf995530ea53652ddf8fb','José Sixto Verduzco','sesion','2014-09-29 17:38:45'),('362e80d4df43b03ae6d3f8540cd63626','León','sesion','2014-09-29 17:38:47'),('3636638817772e42b59d74cff571fbb3','Atzitzihuacán','sesion','2014-09-29 17:38:22'),('363763e5c3dc3a68b399058c34aecf2c','San Juan Guichicovi','sesion','2014-09-29 17:39:07'),('3644a684f98ea8fe223c713b77189a77','Banderilla','sesion','2014-09-29 17:38:23'),('365d17770080c807a0e47ae9118d8641','Santo Domingo Teojomulco','sesion','2014-09-29 17:39:21'),('36660e59856b4de58a219bcf4e27eba3','Cardonal','sesion','2014-09-29 17:38:25'),('3683af9d6f6c06acee72992f2977f67e','Tepatlaxco de Hidalgo','sesion','2014-09-29 17:39:29'),('36a1694bce9815b7e38a9dad05ad42e0','Reynosa','sesion','2014-09-29 17:38:59'),('36a16a2505369e0c922b6ea7a23a56d2','Padilla','sesion','2014-09-29 17:38:56'),('36ac8e558ac7690b6f44e2cb5ef93322','Teolocholco','sesion','2014-09-29 17:39:28'),('36d7534290610d9b7e9abed244dd2f28','Santo Domingo Chihuitán','sesion','2014-09-29 17:39:21'),('371bce7dc83817b7893bcdeed13799b5','Chínipas','sesion','2014-09-29 17:38:29'),('372d3f309fef061977fb2f7ba36d74d2','San Miguel Achiutla','sesion','2014-09-29 17:39:10'),('375c71349b295fbe2dcdca9206f20a06','Santa María Yosoyúa','sesion','2014-09-29 17:39:18'),('37693cfc748049e45d87b8c7d8b9aacd','Aconchi','sesion','2014-09-29 17:38:17'),('378a063b8fdb1db941e34f4bde584c7d','Teotitlán del Valle','sesion','2014-09-29 17:39:28'),('37a749d808e46495a8da1e5352d03cae','Atlangatepec','sesion','2014-09-29 17:38:21'),('37bc2f75bf1bcfe8450a1a41c200364c','Chapulco','sesion','2014-09-29 17:38:27'),('37d097caf1299d9aa79c2c2b843d2d78','Tuxtla Chico','sesion','2014-09-29 17:39:35'),('37f0e884fbad9667e38940169d0a3c95','El Oro','sesion','2014-09-29 17:38:35'),('3806734b256c27e41ec2c6bffa26d9e7','Nopaltepec','sesion','2014-09-29 17:38:54'),('38651c4450f87348fcbe1f992746a954','Yaonáhuac','sesion','2014-09-29 17:39:40'),('3871bd64012152bfb53fdf04b401193f','Heroica Ciudad de Tlaxiaco','sesion','2014-09-29 17:38:39'),('3875115bacc48cca24ac51ee4b0e7975','Tlacoapa','sesion','2014-09-29 17:39:32'),('38913e1d6a7b94cb0f55994f679f5956','El Bosque','sesion','2014-09-29 17:38:35'),('389bc7bb1e1c2a5e7e147703232a88f6','Dzoncauich','sesion','2014-09-29 17:38:34'),('38af86134b65d0f10fe33d30dd76442e','Ayahualulco','sesion','2014-09-29 17:38:22'),('38b3eff8baf56627478ec76a704e9b52','Apaseo el Alto','sesion','2014-09-29 17:38:20'),('38ca89564b2259401518960f7a06f94b','Quiroga','sesion','2014-09-29 17:38:59'),('38db3aed920cf82ab059bfccbd02be6a','Cañada Morelos','sesion','2014-09-29 17:38:25'),('39027dfad5138c9ca0c474d71db915c3','Sayula de Alemán','sesion','2014-09-29 17:39:22'),('39059724f73a9969845dfe4146c5660e','Castaños','sesion','2014-09-29 17:38:26'),('390e982518a50e280d8e2b535462ec1f','Otzolotepec','sesion','2014-09-29 17:38:55'),('3937230de3c8041e4da6ac3246a888e8','Totutla','sesion','2014-09-29 17:39:34'),('393c55aea738548df743a186d15f3bef','Santo Domingo Tepuxtepec','sesion','2014-09-29 17:39:21'),('39461a19e9eddfb385ea76b26521ea48','Coatzingo','sesion','2014-09-29 17:38:30'),('3948ead63a9f2944218de038d8934305','San Andrés Cabecera Nueva','sesion','2014-09-29 17:39:01'),('3988c7f88ebcb58c6ce932b957b6f332','Atarjea','sesion','2014-09-29 17:38:21'),('39dcaf7a053dc372fbc391d4e6b5d693','Tepatitlán de Morelos','sesion','2014-09-29 17:39:29'),('39e4973ba3321b80f37d9b55f63ed8b8','San Baltazar Loxicha','sesion','2014-09-29 17:39:02'),('3a029f04d76d32e79367c4b3255dda4d','San Andrés Ixtlahuaca','sesion','2014-09-29 17:39:01'),('3a066bda8c96b9478bb0512f0a43028c','Huejotitán','sesion','2014-09-29 17:38:40'),('3a0772443a0739141292a5429b952fe6','Florencio Villarreal','sesion','2014-09-29 17:38:36'),('3a15c7d0bbe60300a39f76f8a5ba6896','Piedras Negras','sesion','2014-09-29 17:38:57'),('3a1dd98341fafc1dfe9bcf36360e6b84','Vetagrande','sesion','2014-09-29 17:39:37'),('3a20f62a0af1aa152670bab3c602feed','San Pedro y San Pablo Tequixtepec','sesion','2014-09-29 17:39:13'),('3a824154b16ed7dab899bf000b80eeee','Ticul','sesion','2014-09-29 17:39:31'),('3a835d3215755c435ef4fe9965a3f2a0','Jiménez','sesion','2014-09-29 17:38:44'),('3ad7c2ebb96fcba7cda0cf54a2e802f5','Chilón','sesion','2014-09-29 17:38:28'),('3b3dbaf68507998acd6a5a5254ab2d76','Los Reyes de Juárez','sesion','2014-09-29 17:38:48'),('3b5dca501ee1e6d8cd7b905f4e1bf723','La Libertad','sesion','2014-09-29 17:38:46'),('3b712de48137572f3849aabd5666a4e3','Pihuamo','sesion','2014-09-29 17:38:57'),('3b8a614226a953a8cd9526fca6fe9ba5','Briseñas','sesion','2014-09-29 17:38:23'),('3b92d18aa7a6176dd37d372bc2f1eb71','Tulancingo de Bravo','sesion','2014-09-29 17:39:35'),('3bbfdde8842a5c44a0323518eec97cbe','San Mateo Etlatongo','sesion','2014-09-29 17:39:09'),('3bf55bbad370a8fcad1d09b005e278c2','San Bartolomé Loxicha','sesion','2014-09-29 17:39:02'),('3c1e4bd67169b8153e0047536c9f541e','Santa Inés Ahuatempan','sesion','2014-09-29 17:39:16'),('3c59dc048e8850243be8079a5c74d079','Acayucan','sesion','2014-09-29 17:38:17'),('3c7781a36bcd6cf08c11a970fbe0e2a6','Córdoba','sesion','2014-09-29 17:38:31'),('3c947bc2f7ff007b86a9428b74654de5','Tamalín','sesion','2014-09-29 17:39:24'),('3cec07e9ba5f5bb252d13f5f431e4bbb','Cañadas de Obregón','sesion','2014-09-29 17:38:25'),('3cef96dcc9b8035d23f69e30bb19218a','Miguel Hidalgo','sesion','2014-09-29 17:38:51'),('3cf166c6b73f030b4f67eeaeba301103','Donato Guerra','sesion','2014-09-29 17:38:34'),('3d2d8ccb37df977cb6d9da15b76c3f3a','Huehuetán','sesion','2014-09-29 17:38:40'),('3d779cae2d46cf6a8a99a35ba4167977','San Joaquín','sesion','2014-09-29 17:39:05'),('3d863b367aa379f71c7afc0c9cdca41d','Tepelmeme Villa de Morelos','sesion','2014-09-29 17:39:29'),('3d8e28caf901313a554cebc7d32e67e5','Las Margaritas','sesion','2014-09-29 17:38:47'),('3dc4876f3f08201c7c76cb71fa1da439','Eduardo Neri','sesion','2014-09-29 17:38:35'),('3dd48ab31d016ffcbf3314df2b3cb9ce','Chilchotla','sesion','2014-09-29 17:38:28'),('3dd9424294b0292b6e89ea2bba2e1144','Xiutetelco','sesion','2014-09-29 17:39:39'),('3de2334a314a7a72721f1f74a6cb4cee','San Juan Tamazola','sesion','2014-09-29 17:39:08'),('3de568f8597b94bda53149c7d7f5958c','Zihuateutla','sesion','2014-09-29 17:39:42'),('3def184ad8f4755ff269862ea77393dd','Arriaga','sesion','2014-09-29 17:38:20'),('3df1d4b96d8976ff5986393e8767f5b2','Mezquital','sesion','2014-09-29 17:38:50'),('3e15cc11f979ed25912dff5b0669f2cd','Santiago Papasquiaro','sesion','2014-09-29 17:39:20'),('3e313b9badf12632cdae5452d20e1af6','San José Tenango','sesion','2014-09-29 17:39:06'),('3e6260b81898beacda3d16db379ed329','Xicoténcatl','sesion','2014-09-29 17:39:39'),('3e7e0224018ab3cf51abb96464d518cd','Santiago Ihuitlán Plumas','sesion','2014-09-29 17:39:19'),('3e89ebdb49f712c7d90d1b39e348bbbf','Mazatecochco de José María Morelos','sesion','2014-09-29 17:38:50'),('3e9e39fed3b8369ed940f52cf300cf88','Tlapehuala','sesion','2014-09-29 17:39:33'),('3eb71f6293a2a31f3569e10af6552658','Reforma de Pineda','sesion','2014-09-29 17:38:59'),('3ef815416f775098fe977004015c6193','Amatlán de los Reyes','sesion','2014-09-29 17:38:19'),('3f088ebeda03513be71d34d214291986','Tequixquiac','sesion','2014-09-29 17:39:30'),('3f67fd97162d20e6fe27748b5b372509','San Andrés Dinicuiti','sesion','2014-09-29 17:39:01'),('3fab5890d8113d0b5a4178201dc842ad','Santos Reyes Yucuná','sesion','2014-09-29 17:39:22'),('3fb451ca2e89b3a13095b059d8705b15','San Pedro Cholula','sesion','2014-09-29 17:39:12'),('3fe78a8acf5fda99de95303940a2420c','Pisaflores','sesion','2014-09-29 17:38:57'),('3fe94a002317b5f9259f82690aeea4cd','Chiapilla','sesion','2014-09-29 17:38:27'),('40008b9a5380fcacce3976bf7c08af5b','Chilchota','sesion','2014-09-29 17:38:28'),('404dcc91b2aeaa7caa47487d1483e48a','Santiago el Pinar','sesion','2014-09-29 17:39:19'),('405e28906322882c5be9b4b27f4c35fd','Tepetitlán','sesion','2014-09-29 17:39:29'),('4079016d940210b4ae9ae7d41c4a2065','San Felipe Tejalápam','sesion','2014-09-29 17:39:03'),('40b5f25a228570053bc64a043c3f1833','Ziltlaltépec de Trinidad Sánchez Santos','sesion','2014-09-29 17:39:42'),('411ae1bf081d1674ca6091f8c59a266f','San Luis de la Paz','sesion','2014-09-29 17:39:08'),('4122cb13c7a474c1976c9706ae36521d','San Andrés Tepetlapa','sesion','2014-09-29 17:39:01'),('414e773d5b7e5c06d564f594bf6384d0','Temamatla','sesion','2014-09-29 17:39:27'),('418ef6127e44214882c61e372e866691','Santiago Niltepec','sesion','2014-09-29 17:39:20'),('41a60377ba920919939d83326ebee5a1','San Pablo Etla','sesion','2014-09-29 17:39:11'),('41ae36ecb9b3eee609d05b90c14222fb','Contla de Juan Cuamatzi','sesion','2014-09-29 17:38:31'),('41bfd20a38bb1b0bec75acf0845530a7','Pánuco','sesion','2014-09-29 17:38:56'),('41e7637e7b6a9f27a98b84d3a185c7c0','Valle de Guadalupe','sesion','2014-09-29 17:39:36'),('41f1f19176d383480afa65d325c06ed0','Ciudad Ixtepec','sesion','2014-09-29 17:38:29'),('426f990b332ef8193a61cc90516c1245','Zumpango','sesion','2014-09-29 17:39:42'),('428fca9bc1921c25c5121f9da7815cde','Cuautitlán de García Barragán','sesion','2014-09-29 17:38:33'),('42998cf32d552343bc8e460416382dca','Cuauhtémoc','sesion','2014-09-29 17:38:32'),('42a0e188f5033bc65bf8d78622277c4e','Asunción Tlacolulita','sesion','2014-09-29 17:38:21'),('42a3964579017f3cb42b26605b9ae8ef','Santa Cruz Xoxocotlán','sesion','2014-09-29 17:39:16'),('42d6c7d61481d1c21bd1635f59edae05','San Pedro Amuzgos','sesion','2014-09-29 17:39:12'),('42e77b63637ab381e8be5f8318cc28a2','Hocabá','sesion','2014-09-29 17:38:39'),('42e7aaa88b48137a16a1acd04ed91125','Constancia del Rosario','sesion','2014-09-29 17:38:31'),('42ffcf057e133f94c1b7b5cf543ef3bd','San Miguel Chicahua','sesion','2014-09-29 17:39:10'),('430c3626b879b4005d41b8a46172e0c0','Mazatlán','sesion','2014-09-29 17:38:50'),('4311359ed4969e8401880e3c1836fbe1','Monte Escobedo','sesion','2014-09-29 17:38:52'),('432aca3a1e345e339f35a30c8f65edce','Chiautla','sesion','2014-09-29 17:38:27'),('43351f7bf9a215be70c2c2caa7555002','Zapotlán de Juárez','sesion','2014-09-29 17:39:41'),('437d7d1d97917cd627a34a6a0fb41136','Mixquiahuala de Juárez','sesion','2014-09-29 17:38:51'),('43baa6762fa81bb43b39c62553b2970d','Pachuca de Soto','sesion','2014-09-29 17:38:56'),('43cca4b3de2097b9558efefd0ecc3588','San Andrés Lagunas','sesion','2014-09-29 17:39:01'),('43dd49b4fdb9bede653e94468ff8df1e','Osumacinta','sesion','2014-09-29 17:38:55'),('43ec517d68b6edd3015b3edc9a11367b','Amatepec','sesion','2014-09-29 17:38:19'),('43fa7f58b7eac7ac872209342e62e8f1','La Huacana','sesion','2014-09-29 17:38:46'),('43feaeeecd7b2fe2ae2e26d917b6477d','Magdalena Apasco','sesion','2014-09-29 17:38:48'),('442cde81694ca09a626eeddefd1b74ca','Santiago Nuyoó','sesion','2014-09-29 17:39:20'),('443cb001c138b2561a0d90720d6ce111','Huatusco','sesion','2014-09-29 17:38:40'),('443dec3062d0286986e21dc0631734c9','Xaloztoc','sesion','2014-09-29 17:39:39'),('4462bf0ddbe0d0da40e1e828ebebeb11','Santa Cruz Acatepec','sesion','2014-09-29 17:39:15'),('4476b929e30dd0c4e8bdbcc82c6ba23a','San Francisco Sola','sesion','2014-09-29 17:39:04'),('44968aece94f667e4095002d140b5896','Telchac Pueblo','sesion','2014-09-29 17:39:27'),('4496bf24afe7fab6f046bf4923da8de6','Solidaridad','sesion','2014-09-29 17:39:23'),('44a2e0804995faf8d2e3b084a1e2db1d','Tantoyuca','sesion','2014-09-29 17:39:26'),('44c4c17332cace2124a1a836d9fc4b6f','Guaymas','sesion','2014-09-29 17:38:38'),('44cd7a8f7f9f85129b9953950665064d','Trincheras','sesion','2014-09-29 17:39:34'),('44f683a84163b3523afe57c2e008bc8c','Almoloya de Juárez','sesion','2014-09-29 17:38:18'),('452bf208bf901322968557227b8f6efe','San Sebastián Coatlán','sesion','2014-09-29 17:39:14'),('4558dbb6f6f8bb2e16d03b85bde76e2c','La Magdalena Tlaltelulco','sesion','2014-09-29 17:38:46'),('45645a27c4f1adc8a7a835976064a86d','Hidalgotitlán','sesion','2014-09-29 17:38:39'),('456ac9b0d15a8b7f1e71073221059886','Ojocaliente','sesion','2014-09-29 17:38:55'),('4588e674d3f0faf985047d4c3f13ed0d','Pueblo Viejo','sesion','2014-09-29 17:38:58'),('459a4ddcb586f24efd9395aa7662bc7c','San Francisco Cahuacuá','sesion','2014-09-29 17:39:03'),('45c48cce2e2d7fbdea1afc51c7c6ad26','Acanceh','sesion','2014-09-29 17:38:16'),('45f31d16b1058d586fc3be7207b58053','Puruándiro','sesion','2014-09-29 17:38:58'),('45fbc6d3e05ebd93369ce542e8f2322d','Buctzotz','sesion','2014-09-29 17:38:23'),('46031b3d04dc90994ca317a7c55c4289','Santa Cruz Tacache de Mina','sesion','2014-09-29 17:39:16'),('46072631582fc240dd2674a7d063b040','Oxkutzcab','sesion','2014-09-29 17:38:56'),('464d828b85b0bed98e80ade0a5c43b0f','Santiago Atitlán','sesion','2014-09-29 17:39:19'),('4671aeaf49c792689533b00664a5c3ef','San Ildefonso Villa Alta','sesion','2014-09-29 17:39:05'),('46771d1f432b42343f56f791422a4991','San Juan Teitipac','sesion','2014-09-29 17:39:08'),('46922a0880a8f11f8f69cbb52b1396be','General Pánfilo Natera','sesion','2014-09-29 17:38:37'),('46a558d97954d0692411c861cf78ef79','Zimapán','sesion','2014-09-29 17:39:42'),('46ba9f2a6976570b0353203ec4474217','Cerritos','sesion','2014-09-29 17:38:26'),('470e7a4f017a5476afb7eeb3f8b96f9b','Janos','sesion','2014-09-29 17:38:44'),('471c75ee6643a10934502bdafee198fb','San Pedro Coxcaltepec Cántaros','sesion','2014-09-29 17:39:12'),('4734ba6f3de83d861c3176a6273cac6d','Catemaco','sesion','2014-09-29 17:38:26'),('47810f956e3d8fb8a32fb276448b464d','Zapotlanejo','sesion','2014-09-29 17:39:41'),('47a658229eb2368a99f1d032c8848542','Poncitlán','sesion','2014-09-29 17:38:58'),('47d1e990583c9c67424d369f3414728e','Atizapán de Zaragoza','sesion','2014-09-29 17:38:21'),('487d4c6a324446b3fa45b30cfcee5337','Xochiatipan','sesion','2014-09-29 17:39:39'),('4888241374e8c62ddd9b4c3cfd091f96','Sudzal','sesion','2014-09-29 17:39:24'),('489d0396e6826eb0c1e611d82ca8b215','San Lucas Ojitlán','sesion','2014-09-29 17:39:08'),('48ab2f9b45957ab574cf005eb8a76760','Hidalgo','sesion','2014-09-29 17:38:39'),('48aedb8880cab8c45637abc7493ecddd','Chacsinkín','sesion','2014-09-29 17:38:26'),('490640b43519c77281cb2f8471e61a71','San Pablo Yaganiza','sesion','2014-09-29 17:39:12'),('491442df5f88c6aa018e86dac21d3606','Salvador Escalante','sesion','2014-09-29 17:39:00'),('49182f81e6a13cf5eaa496d51fea6406','Chalma','sesion','2014-09-29 17:38:27'),('4921f95baf824205e1b13f22d60357a1','Santa María Teopoxco','sesion','2014-09-29 17:39:18'),('495dabfd0ca768a3c3abd672079f48b6','Santa Catarina Ixtepeji','sesion','2014-09-29 17:39:15'),('496e05e1aea0a9c4655800e8a7b9ea28','Chapulhuacán','sesion','2014-09-29 17:38:27'),('49ad23d1ec9fa4bd8d77d02681df5cfa','Tala','sesion','2014-09-29 17:39:24'),('49ae49a23f67c759bf4fc791ba842aa2','Cualác','sesion','2014-09-29 17:38:32'),('49af6c4e558a7569d80eee2e035e2bd7','San Vicente Tancuayalab','sesion','2014-09-29 17:39:14'),('49b8b4f95f02e055801da3b4f58e28b7','San Nicolás Hidalgo','sesion','2014-09-29 17:39:11'),('49c0b9d84c2a16fcaf9d25694fda75e1','Zitlala','sesion','2014-09-29 17:39:42'),('49c9adb18e44be0711a94e827042f630','Madera','sesion','2014-09-29 17:38:48'),('4a08142c38dbe374195d41c04562d9f8','Onavas','sesion','2014-09-29 17:38:55'),('4a213d37242bdcad8e7300e202e7caa4','Platón Sánchez','sesion','2014-09-29 17:38:57'),('4a2ddf148c5a9c42151a529e8cbdcc06','Santo Domingo Roayaga','sesion','2014-09-29 17:39:21'),('4a3e00961a08879c34f91ca0070ea2f5','Tepezalá','sesion','2014-09-29 17:39:30'),('4a47d2983c8bd392b120b627e0e1cab4','Ixcaquixtla','sesion','2014-09-29 17:38:42'),('4abe17a1c80cbdd2aa241b70840879de','Santo Domingo Albarradas','sesion','2014-09-29 17:39:21'),('4ae67a7dd7e491f8fb6f9ea0cf25dfdb','Xayacatlán de Bravo','sesion','2014-09-29 17:39:39'),('4aecfbe5d21e3f7912bf8eb29124423a','Villa Guerrero','sesion','2014-09-29 17:39:38'),('4afd521d77158e02aed37e2274b90c9c','Tepetitla de Lardizábal','sesion','2014-09-29 17:39:29'),('4b0250793549726d5c1ea3906726ebfe','Mapimí','sesion','2014-09-29 17:38:49'),('4b04a686b0ad13dce35fa99fa4161c65','José Joaquín de Herrera','sesion','2014-09-29 17:38:45'),('4b0a59ddf11c58e7446c9df0da541a84','Magdalena Ocotlán','sesion','2014-09-29 17:38:48'),('4b4edc2630fe75800ddc29a7b4070add','Villa Díaz Ordaz','sesion','2014-09-29 17:39:38'),('4b6538a44a1dfdc2b83477cd76dee98e','Mier','sesion','2014-09-29 17:38:51'),('4b86abe48d358ecf194c56c69108433e','Tlaltetela','sesion','2014-09-29 17:39:33'),('4ba29b9f9e5732ed33761840f4ba6c53','Tetipac','sesion','2014-09-29 17:39:30'),('4ba3c163cd1efd4c14e3a415fa0a3010','Tototlán','sesion','2014-09-29 17:39:34'),('4c144c47ecba6f8318128703ca9e2601','Tizapán el Alto','sesion','2014-09-29 17:39:31'),('4c22bd444899d3b6047a10b20a2f26db','San Jerónimo Silacayoapilla','sesion','2014-09-29 17:39:05'),('4c27cea8526af8cfee3be5e183ac9605','Huamuxtitlán','sesion','2014-09-29 17:38:39'),('4c56ff4ce4aaf9573aa5dff913df997a','Arivechi','sesion','2014-09-29 17:38:20'),('4c5bde74a8f110656874902f07378009','Ayotzintepec','sesion','2014-09-29 17:38:22'),('4ca82782c5372a547c104929f03fe7a9','Omealca','sesion','2014-09-29 17:38:55'),('4cb811134b9d39fc3104bd06ce75abad','Yecapixtla','sesion','2014-09-29 17:39:40'),('4d2e7bd33c475784381a64e43e50922f','Saltabarranca','sesion','2014-09-29 17:39:00'),('4d5b995358e7798bc7e9d9db83c612a5','Lagos de Moreno','sesion','2014-09-29 17:38:47'),('4d6b3e38b952600251ee92fe603170ff','Tlatlaya','sesion','2014-09-29 17:39:33'),('4d6e4749289c4ec58c0063a90deb3964','San Pedro Yólox','sesion','2014-09-29 17:39:13'),('4d8556695c262ab91ff51a943fdd6058','Tepechitlán','sesion','2014-09-29 17:39:29'),('4da04049a062f5adfe81b67dd755cecc','Paso de Ovejas','sesion','2014-09-29 17:38:57'),('4daa3db355ef2b0e64b472968cb70f0d','Metztitlán','sesion','2014-09-29 17:38:50'),('4dcae38ee11d3a6606cc6cd636a3628b','San Mateo Río Hondo','sesion','2014-09-29 17:39:09'),('4dcf435435894a4d0972046fc566af76','San Pablo Coatlán','sesion','2014-09-29 17:39:11'),('4e0928de075538c593fbdabb0c5ef2c3','Jiutepec','sesion','2014-09-29 17:38:44'),('4e0cb6fb5fb446d1c92ede2ed8780188','Mocorito','sesion','2014-09-29 17:38:51'),('4e0d67e54ad6626e957d15b08ae128a6','San Juan Diuxi','sesion','2014-09-29 17:39:07'),('4e2545f819e67f0615003dd7e04a6087','Papalotla','sesion','2014-09-29 17:38:56'),('4e4b5fbbbb602b6d35bea8460aa8f8e5','Francisco León','sesion','2014-09-29 17:38:36'),('4e4e53aa080247bc31d0eb4e7aeb07a0','San Agustín Atenango','sesion','2014-09-29 17:39:00'),('4e62e752ae53fb6a6eebd0f6146aa702','Tuxcueca','sesion','2014-09-29 17:39:35'),('4e6cd95227cb0c280e99a195be5f6615','San Pedro Taviche','sesion','2014-09-29 17:39:13'),('4e732ced3463d06de0ca9a15b6153677','Acuamanala de Miguel Hidalgo','sesion','2014-09-29 17:38:17'),('4e8412ad48562e3c9934f45c3e144d48','San Miguel Ixitlán','sesion','2014-09-29 17:39:10'),('4e87337f366f72daa424dae11df0538c','Tamiahua','sesion','2014-09-29 17:39:25'),('4e9cec1f583056459111d63e24f3b8ef','San Pedro Topiltepec','sesion','2014-09-29 17:39:13'),('4ea06fbc83cdd0a06020c35d50e1e89a','Jiquilpan','sesion','2014-09-29 17:38:44'),('4edaa105d5f53590338791951e38c3ad','San Juan Sayultepec','sesion','2014-09-29 17:39:08'),('4f16c818875d9fcb6867c7bdc89be7eb','Palenque','sesion','2014-09-29 17:38:56'),('4f284803bd0966cc24fa8683a34afc6e','Sabanilla','sesion','2014-09-29 17:39:00'),('4f398cb9d6bc79ae567298335b51ba8a','Santa María del Río','sesion','2014-09-29 17:39:17'),('4f4adcbf8c6f66dcfc8a3282ac2bf10a','Comitán de Domínguez','sesion','2014-09-29 17:38:31'),('4f6ffe13a5d75b2d6a3923922b3922e5','Coatepec','sesion','2014-09-29 17:38:30'),('4f87658ef0de194413056248a00ce009','San José Chiltepec','sesion','2014-09-29 17:39:05'),('4fa53be91b4933d536748a60458b9797','Santiago Lalopa','sesion','2014-09-29 17:39:19'),('4fa7c62536118cc404dec4a0ca88d4f6','San Juan Huactzinco','sesion','2014-09-29 17:39:07'),('4fac9ba115140ac4f1c22da82aa0bc7f','Múzquiz','sesion','2014-09-29 17:38:52'),('4ffce04d92a4d6cb21c1494cdfcd6dc1','Huanímaro','sesion','2014-09-29 17:38:39'),('500e75a036dc2d7d2fec5da1b71d36cc','Ixcatepec','sesion','2014-09-29 17:38:42'),('502e4a16930e414107ee22b6198c578f','Capulhuac','sesion','2014-09-29 17:38:25'),('5055cbf43fac3f7e2336b27310f0b9ef','Ojinaga','sesion','2014-09-29 17:38:55'),('50905d7b2216bfeccb5b41016357176b','San Gabriel','sesion','2014-09-29 17:39:04'),('50c3d7614917b24303ee6a220679dab3','Ixtacamaxtitlán','sesion','2014-09-29 17:38:42'),('5103c3584b063c431bd1268e9b5e76fb','Temascaltepec','sesion','2014-09-29 17:39:27'),('51174add1c52758f33d414ceaf3fe6ba','Totoltepec de Guerrero','sesion','2014-09-29 17:39:34'),('5129a5ddcd0dcd755232baa04c231698','Santa Ana Ateixtlahuaca','sesion','2014-09-29 17:39:14'),('512c5cad6c37edb98ae91c8a76c3a291','San Juan Yaeé','sesion','2014-09-29 17:39:08'),('515ab26c135e92ed8bf3a594d67e4ade','Santiago Ixcuintla','sesion','2014-09-29 17:39:19'),('519c84155964659375821f7ca576f095','Tenejapa','sesion','2014-09-29 17:39:28'),('51d92be1c60d1db1d2e5e7a07da55b26','Cuautitlán','sesion','2014-09-29 17:38:33'),('51de85ddd068f0bc787691d356176df9','Zacapala','sesion','2014-09-29 17:39:41'),('51ef186e18dc00c2d31982567235c559','Magdalena Mixtepec','sesion','2014-09-29 17:38:48'),('5227b6aaf294f5f027273aebf16015f2','Tulcingo','sesion','2014-09-29 17:39:35'),('52292e0c763fd027c6eba6b8f494d2eb','Sahuaripa','sesion','2014-09-29 17:39:00'),('522a9ae9a99880d39e5daec35375e999','Pabellón de Arteaga','sesion','2014-09-29 17:38:56'),('5249ee8e0cff02ad6b4cc0ee0e50b7d1','Villa de Tezontepec','sesion','2014-09-29 17:39:38'),('52720e003547c70561bf5e03b95aa99f','Chumayel','sesion','2014-09-29 17:38:29'),('52947e0ade57a09e4a1386d08f17b656','Santa Catarina Quiané','sesion','2014-09-29 17:39:15'),('52c5189391854c93e8a0e1326e56c14f','Santa Elena','sesion','2014-09-29 17:39:16'),('52d080a3e172c33fd6886a37e7288491','Santiago Amoltepec','sesion','2014-09-29 17:39:18'),('52d2752b150f9c35ccb6869cbf074e48','Temozón','sesion','2014-09-29 17:39:28'),('52dbb0686f8bd0c0c757acf716e28ec0','Tlalchapa','sesion','2014-09-29 17:39:32'),('5352696a9ca3397beb79f116f3a33991','Tinguindín','sesion','2014-09-29 17:39:31'),('535ab76633d94208236a2e829ea6d888','San Francisco Telixtlahuaca','sesion','2014-09-29 17:39:04'),('536a76f94cf7535158f66cfbd4b113b6','San Mateo del Mar','sesion','2014-09-29 17:39:09'),('537d9b6c927223c796cac288cced29df','Ocampo','sesion','2014-09-29 17:38:54'),('537de305e941fccdbba5627e3eefbb24','Tamazula de Gordiano','sesion','2014-09-29 17:39:24'),('539fd53b59e3bb12d203f45a912eeaf2','Calpan','sesion','2014-09-29 17:38:24'),('53adaf494dc89ef7196d73636eb2451b','Otatitlán','sesion','2014-09-29 17:38:55'),('53c04118df112c13a8c34b38343b9c10','Oluta','sesion','2014-09-29 17:38:55'),('53c3bce66e43be4f209556518c2fcb54','Chalchihuites','sesion','2014-09-29 17:38:27'),('53e3a7161e428b65688f14b84d61c610','Ilamatlán','sesion','2014-09-29 17:38:41'),('53ed35c74a2ec275b837374f04396c03','Yuriria','sesion','2014-09-29 17:39:41'),('53fde96fcc4b4ce72d7739202324cd49','El Grullo','sesion','2014-09-29 17:38:35'),('54072f485cdb7897ebbcaf7525139561','San Juan Yatzona','sesion','2014-09-29 17:39:08'),('540ae6b0f6ac6e155062f3dd4f0b2b01','San Martín de las Pirámides','sesion','2014-09-29 17:39:09'),('54229abfcfa5649e7003b83dd4755294','Amozoc','sesion','2014-09-29 17:38:19'),('5487315b1286f907165907aa8fc96619','Iliatenco','sesion','2014-09-29 17:38:42'),('54a367d629152b720749e187b3eaa11b','Múgica','sesion','2014-09-29 17:38:52'),('54e36c5ff5f6a1802925ca009f3ebb68','Santiago Pinotepa Nacional','sesion','2014-09-29 17:39:20'),('54f5f4071faca32ad5285fef87b78646','Santiago Miltepec','sesion','2014-09-29 17:39:20'),('54ff9e9e3a2ec0300d4ce11261f5169f','Tlapacoya','sesion','2014-09-29 17:39:33'),('550a141f12de6341fba65b0ad0433500','Coyomeapan','sesion','2014-09-29 17:38:32'),('5531a5834816222280f20d1ef9e95f69','Tierra Blanca','sesion','2014-09-29 17:39:31'),('555d6702c950ecb729a966504af0a635','Caltepec','sesion','2014-09-29 17:38:24'),('556f391937dfd4398cbac35e050a2177','Huitzuco de los Figueroa','sesion','2014-09-29 17:38:41'),('55743cc0393b1cb4b8b37d09ae48d097','Huautla','sesion','2014-09-29 17:38:40'),('559cb990c9dffd8675f6bc2186971dc2','Delicias','sesion','2014-09-29 17:38:34'),('55a7cf9c71f1c9c495413f934dd1a158','Divisaderos','sesion','2014-09-29 17:38:34'),('55b1927fdafef39c48e5b73b5d61ea60','Putla Villa de Guerrero','sesion','2014-09-29 17:38:58'),('55b37c5c270e5d84c793e486d798c01d','Huauchinango','sesion','2014-09-29 17:38:40'),('55c567fd4395ecef6d936cf77b8d5b2b','San Miguel Tulancingo','sesion','2014-09-29 17:39:11'),('5607fe8879e4fd269e88387e8cb30b7e','San Pedro Apóstol','sesion','2014-09-29 17:39:12'),('56352739f59643540a3a6e16985f62c7','San Luis Amatlán','sesion','2014-09-29 17:39:08'),('56468d5607a5aaf1604ff5e15593b003','San Luis Potosí','sesion','2014-09-29 17:39:09'),('5680522b8e2bb01943234bce7bf84534','Rosario','sesion','2014-09-29 17:39:00'),('56f9f88906aebf4ad985aaec7fa01313','Teabo','sesion','2014-09-29 17:39:26'),('5705e1164a8394aace6018e27d20d237','Marcos Castellanos','sesion','2014-09-29 17:38:49'),('571d3a9420bfd9219f65b643d0003bf4','San Miguel del Puerto','sesion','2014-09-29 17:39:10'),('571e0f7e2d992e738adff8b1bd43a521','Playas de Rosarito','sesion','2014-09-29 17:38:58'),('5737034557ef5b8c02c0e46513b98f90','Empalme','sesion','2014-09-29 17:38:36'),('5737c6ec2e0716f3d8a7a5c4e0de0d9a','Chicoasén','sesion','2014-09-29 17:38:28'),('5751ec3e9a4feab575962e78e006250d','Ixtlahuaca','sesion','2014-09-29 17:38:43'),('577bcc914f9e55d5e4e4f82f9f00e7d4','Chapala','sesion','2014-09-29 17:38:27'),('577ef1154f3240ad5b9b413aa7346a1e','Calnali','sesion','2014-09-29 17:38:24'),('57aeee35c98205091e18d1140e9f38cf','Calakmul','sesion','2014-09-29 17:38:24'),('57c0531e13f40b91b3b0f1a30b529a1d','Sucilá','sesion','2014-09-29 17:39:24'),('5807a685d1a9ab3b599035bc566ce2b9','Jesús María','sesion','2014-09-29 17:38:44'),('58238e9ae2dd305d79c2ebc8c1883422','Chilcuautla','sesion','2014-09-29 17:38:28'),('584b98aac2dddf59ee2cf19ca4ccb75e','Tlacotepec de Mejía','sesion','2014-09-29 17:39:32'),('5878a7ab84fb43402106c575658472fa','Atzala','sesion','2014-09-29 17:38:22'),('58a2fc6ed39fd083f55d4182bf88826d','Bachíniva','sesion','2014-09-29 17:38:23'),('58ae749f25eded36f486bc85feb3f0ab','Francisco I. Madero','sesion','2014-09-29 17:38:36'),('58c54802a9fb9526cd0923353a34a7ae','Ocuituco','sesion','2014-09-29 17:38:55'),('58d4d1e7b1e97b258c9ed0b37e02d087','Halachó','sesion','2014-09-29 17:38:38'),('58e4d44e550d0f7ee0a23d6b02d9b0db','Miguel Auza','sesion','2014-09-29 17:38:51'),('5938b4d054136e5d59ada6ec9c295d7a','Villa Victoria','sesion','2014-09-29 17:39:38'),('596dedf4498e258e4bdc9fd70df9a859','Sochiapa','sesion','2014-09-29 17:39:23'),('596f713f9a7376fe90a62abaaedecc2d','San Pedro Totolápam','sesion','2014-09-29 17:39:13'),('598920e11d1eb2a49501d59fce5ecbb7','Villanueva','sesion','2014-09-29 17:39:39'),('598b3e71ec378bd83e0a727608b5db01','Cuilápam de Guerrero','sesion','2014-09-29 17:38:33'),('59b90e1005a220e2ebc542eb9d950b1e','Ejutla','sesion','2014-09-29 17:38:35'),('59bcda7c438bad7d2afffe9e2fed00be','Suma','sesion','2014-09-29 17:39:24'),('59c33016884a62116be975a9bb8257e3','Ixtlahuacán de los Membrillos','sesion','2014-09-29 17:38:43'),('59e0b2658e9f2e77f8d4d83f8d07ca84','Santo Tomás','sesion','2014-09-29 17:39:22'),('59f51fd6937412b7e56ded1ea2470c25','San Juanito de Escobedo','sesion','2014-09-29 17:39:08'),('5a142a55461d5fef016acfb927fee0bd','Santiago Apoala','sesion','2014-09-29 17:39:18'),('5a1e3a5aede16d438c38862cac1a78db','Tubutama','sesion','2014-09-29 17:39:35'),('5a4b25aaed25c2ee1b74de72dc03c14e','Coeneo','sesion','2014-09-29 17:38:30'),('5a7f963e5e0504740c3a6b10bb6d4fa5','Teotitlán de Flores Magón','sesion','2014-09-29 17:39:28'),('5b69b9cb83065d403869739ae7f0995e','Durango','sesion','2014-09-29 17:38:34'),('5b6ba13f79129a74a3e819b78e36b922','Silacayoápam','sesion','2014-09-29 17:39:22'),('5b8add2a5d98b1a652ea7fd72d942dac','Chiautempan','sesion','2014-09-29 17:38:27'),('5b8e4fd39d9786228649a8a8bec4e008','Zapotitlán Palmas','sesion','2014-09-29 17:39:41'),('5bce843dd76db8c939d5323dd3e54ec9','Temixco','sesion','2014-09-29 17:39:27'),('5c04925674920eb58467fb52ce4ef728','Hueyapan de Ocampo','sesion','2014-09-29 17:38:41'),('5c50b4df4b176845cd235b6a510c6903','Santa Cruz Quilehtla','sesion','2014-09-29 17:39:16'),('5c572eca050594c7bc3c36e7e8ab9550','Jalacingo','sesion','2014-09-29 17:38:43'),('5c936263f3428a40227908d5a3847c0b','Mixtla de Altamirano','sesion','2014-09-29 17:38:51'),('5ca3e9b122f61f8f06494c97b1afccf3','San Juan Evangelista Analco','sesion','2014-09-29 17:39:07'),('5caf41d62364d5b41a893adc1a9dd5d4','San Francisco Jaltepetongo','sesion','2014-09-29 17:39:04'),('5cbdfd0dfa22a3fca7266376887f549b','San Nicolás Buenos Aires','sesion','2014-09-29 17:39:11'),('5cce8dede893813f879b873962fb669f','San Pedro el Alto','sesion','2014-09-29 17:39:12'),('5cf21ce30208cfffaa832c6e44bb567d','Santa María Tlalixtac','sesion','2014-09-29 17:39:18'),('5d44ee6f2c3f71b73125876103c8f6c4','Hermenegildo Galeana','sesion','2014-09-29 17:38:38'),('5d616dd38211ebb5d6ec52986674b6e4','Nezahualcóyotl','sesion','2014-09-29 17:38:53'),('5d6646aad9bcc0be55b2c82f69750387','Tuzamapan de Galeana','sesion','2014-09-29 17:39:35'),('5d79099fcdf499f12b79770834c0164a','Santa María Petapa','sesion','2014-09-29 17:39:18'),('5dd9db5e033da9c6fb5ba83c7a7ebea9','Hueypoxtla','sesion','2014-09-29 17:38:41'),('5e1b18c4c6a6d31695acbae3fd70ecc6','San José del Peñasco','sesion','2014-09-29 17:39:05'),('5e388103a391daabe3de1d76a6739ccd','Etchojoa','sesion','2014-09-29 17:38:36'),('5e76bef6e019b2541ff53db39f407a98','San Mateo Peñasco','sesion','2014-09-29 17:39:09'),('5e9f92a01c986bafcabbafd145520b13','Huasca de Ocampo','sesion','2014-09-29 17:38:40'),('5ea1649a31336092c05438df996a3e59','Eloxochitlán de Flores Magón','sesion','2014-09-29 17:38:35'),('5eac43aceba42c8757b54003a58277b5','San Antonio Sinicahua','sesion','2014-09-29 17:39:02'),('5ec829debe54b19a5f78d9a65b900a39','Tetela del Volcán','sesion','2014-09-29 17:39:30'),('5ec91aac30eae62f4140325d09b9afd0','Los Herreras','sesion','2014-09-29 17:38:48'),('5ef059938ba799aaa845e1c2e8a762bd','Arandas','sesion','2014-09-29 17:38:20'),('5ef0b4eba35ab2d6180b0bca7e46b6f9','Cuichapa','sesion','2014-09-29 17:38:33'),('5ef698cd9fe650923ea331c15af3b160','Cihuatlán','sesion','2014-09-29 17:38:29'),('5f0f5e5f33945135b874349cfbed4fb9','Jalatlaco','sesion','2014-09-29 17:38:43'),('5f2c22cb4a5380af7ca75622a6426917','Ixtacuixtla de Mariano Matamoros','sesion','2014-09-29 17:38:42'),('5f6371c9126149517d9ba475def53139','Zaragoza','sesion','2014-09-29 17:39:42'),('5f93f983524def3dca464469d2cf9f3e','Aporo','sesion','2014-09-29 17:38:20'),('5fa9e41bfec0725742cc9d15ef594120','Teapa','sesion','2014-09-29 17:39:26'),('5fd0b37cd7dbbb00f97ba6ce92bf5add','Aquiles Serdán','sesion','2014-09-29 17:38:20'),('602d1305678a8d5fdb372271e980da6a','San Juan Ozolotepec','sesion','2014-09-29 17:39:07'),('605ff764c617d3cd28dbbdd72be8f9a2','General Treviño','sesion','2014-09-29 17:38:37'),('6081594975a764c8e3a691fa2b3a321d','Ixhuatlán del Café','sesion','2014-09-29 17:38:42'),('6150ccc6069bea6b5716254057a194ef','Zacazonapan','sesion','2014-09-29 17:39:41'),('61b1fb3f59e28c67f3925f3c79be81a1','Soteapan','sesion','2014-09-29 17:39:23'),('61b4a64be663682e8cb037d9719ad8cd','José María Morelos','sesion','2014-09-29 17:38:45'),('6211080fa89981f66b1a0c9d55c61d0f','Ziracuaretiro','sesion','2014-09-29 17:39:42'),('621461af90cadfdaf0e8d4cc25129f91','Matachí','sesion','2014-09-29 17:38:49'),('621bf66ddb7c962aa0d22ac97d69b793','Cananea','sesion','2014-09-29 17:38:25'),('62889e73828c756c961c5a6d6c01a463','Santiago Huauclilla','sesion','2014-09-29 17:39:19'),('632cee946db83e7a52ce5e8d6f0fed35','La Manzanilla de la Paz','sesion','2014-09-29 17:38:46'),('6351bf9dce654515bf1ddbd6426dfa97','Terrenate','sesion','2014-09-29 17:39:30'),('63538fe6ef330c13a05a3ed7e599d5f7','El Carmen Tequexquitla','sesion','2014-09-29 17:38:35'),('635440afdfc39fe37995fed127d7df4f','Yajalón','sesion','2014-09-29 17:39:40'),('6364d3f0f495b6ab9dcf8d3b5c6e0b01','Agua Blanca de Iturbide','sesion','2014-09-29 17:38:17'),('63923f49e5241343aa7acb6a06a751e7','Caxhuacan','sesion','2014-09-29 17:38:26'),('6395ebd0f4b478145ecfbaf939454fa4','Mineral del Chico','sesion','2014-09-29 17:38:51'),('63dc7ed1010d3c3b8269faf0ba7491d4','Buenaventura','sesion','2014-09-29 17:38:23'),('64223ccf70bbb65a3a4aceac37e21016','Miahuatlán de Porfirio Díaz','sesion','2014-09-29 17:38:51'),('642e92efb79421734881b53e1e1b18b6','Ajalpan','sesion','2014-09-29 17:38:18'),('6449f44a102fde848669bdd9eb6b76fa','Santo Domingo Tlatayápam','sesion','2014-09-29 17:39:21'),('645098b086d2f9e1e0e939c27f9f2d6f','Senguio','sesion','2014-09-29 17:39:22'),('647bba344396e7c8170902bcf2e15551','Erongarícuaro','sesion','2014-09-29 17:38:36'),('647c722bf90a49140184672e0d3723e3','Santa María Huazolotitlán','sesion','2014-09-29 17:39:17'),('6490791e7abf6b29a381288cc23a8223','San Miguel Yotao','sesion','2014-09-29 17:39:11'),('6492d38d732122c58b44e3fdc3e9e9f3','Tlanchinol','sesion','2014-09-29 17:39:33'),('64f1f27bf1b4ec22924fd0acb550c235','Santa María Jalapa del Marqués','sesion','2014-09-29 17:39:17'),('6512bd43d9caa6e02c990b0a82652dca','Acaponeta','sesion','2014-09-29 17:38:16'),('653ac11ca60b3e021a8c609c7198acfc','Xicotlán','sesion','2014-09-29 17:39:39'),('654ad60ebd1ae29cedc37da04b6b0672','Tlapa de Comonfort','sesion','2014-09-29 17:39:33'),('655ea4bd3b5736d88afc30c9212ccddf','San Salvador el Verde','sesion','2014-09-29 17:39:13'),('65658fde58ab3c2b6e5132a39fae7cb9','Eloxochitlán','sesion','2014-09-29 17:38:35'),('65699726a3c601b9f31bf04019c8593c','Técpan de Galeana','sesion','2014-09-29 17:39:27'),('65b9eea6e1cc6bb9f0cd2a47751a186f','Apaxtla','sesion','2014-09-29 17:38:20'),('65cc2c8205a05d7379fa3a6386f710e1','Nicolás Romero','sesion','2014-09-29 17:38:53'),('65d2ea03425887a717c435081cfc5dbb','Teziutlán','sesion','2014-09-29 17:39:31'),('65ded5353c5ee48d0b7d48c591b8f430','Asunción Cuyotepeji','sesion','2014-09-29 17:38:21'),('65fc52ed8f88c81323a418ca94cec2ed','Tekantó','sesion','2014-09-29 17:39:27'),('6602294be910b1e3c4571bd98c4d5484','Momax','sesion','2014-09-29 17:38:52'),('66368270ffd51418ec58bd793f2d9b1b','Cosamaloapan de Carpio','sesion','2014-09-29 17:38:32'),('66808e327dc79d135ba18e051673d906','Conkal','sesion','2014-09-29 17:38:31'),('66be31e4c40d676991f2405aaecc6934','Santiago Camotlán','sesion','2014-09-29 17:39:19'),('66e8ba8216a1e152d72653d99a4f03ab','Yogana','sesion','2014-09-29 17:39:40'),('66f041e16a60928b05a7e228a89c3799','Aljojuca','sesion','2014-09-29 17:38:18'),('6709e8d64a5f47269ed5cea9f625f7ab','Santo Domingo Ixcatlán','sesion','2014-09-29 17:39:21'),('670e8a43b246801ca1eaca97b3e19189','La Colorada','sesion','2014-09-29 17:38:46'),('673271cc47c1a4e77f57e239ed4d28a7','Santa María Tonameca','sesion','2014-09-29 17:39:18'),('674bfc5f6b72706fb769f5e93667bd23','San José del Progreso','sesion','2014-09-29 17:39:05'),('6766aa2750c19aad2fa1b32f36ed4aee','Huachinera','sesion','2014-09-29 17:38:39'),('677e09724f0e2df9b6c000b75b5da10d','La Misión','sesion','2014-09-29 17:38:46'),('6786f3c62fbf9021694f6e51cc07fe3c','San Mateo Cajonos','sesion','2014-09-29 17:39:09'),('678a1491514b7f1006d605e9161946b1','Pinotepa de Don Luis','sesion','2014-09-29 17:38:57'),('67c6a1e7ce56d3d6fa748ab6d9af3fd7','Ajacuba','sesion','2014-09-29 17:38:18'),('67d16d00201083a2b118dd5128dd6f59','Magdalena Peñasco','sesion','2014-09-29 17:38:48'),('67d96d458abdef21792e6d8e590244e7','Jonacatepec','sesion','2014-09-29 17:38:45'),('67e103b0761e60683e83c559be18d40c','Huaniqueo','sesion','2014-09-29 17:38:39'),('67f7fb873eaf29526a11a9b7ac33bfac','Coyotepec','sesion','2014-09-29 17:38:32'),('68053af2923e00204c3ca7c6a3150cf7','Juan C. Bonilla','sesion','2014-09-29 17:38:45'),('68148596109e38cf9367d27875e185be','Tatahuicapan de Juárez','sesion','2014-09-29 17:39:26'),('68264bdb65b97eeae6788aa3348e553c','Huehuetoca','sesion','2014-09-29 17:38:40'),('6832a7b24bc06775d02b7406880b93fc','Villa de Reyes','sesion','2014-09-29 17:39:38'),('6855456e2fe46a9d49d3d3af4f57443d','Chikindzonot','sesion','2014-09-29 17:38:28'),('6883966fd8f918a4aa29be29d2c386fb','Chalco','sesion','2014-09-29 17:38:27'),('68a83eeb494a308fe5295da69428a507','San Bernardo','sesion','2014-09-29 17:39:02'),('68b1fbe7f16e4ae3024973f12f3cb313','Pantepec','sesion','2014-09-29 17:38:56'),('68c694de94e6c110f42e587e8e48d852','Tancítaro','sesion','2014-09-29 17:39:25'),('68ce199ec2c5517597ce0a4d89620f55','Cuautla','sesion','2014-09-29 17:38:33'),('68d13cf26c4b4f4f932e3eff990093ba','San Andrés Nuxiño','sesion','2014-09-29 17:39:01'),('68d309812548887400e375eaa036d2f1','Zumpahuacán','sesion','2014-09-29 17:39:42'),('68d30a9594728bc39aa24be94b319d21','Amatlán de Cañas','sesion','2014-09-29 17:38:19'),('692f93be8c7a41525c0baf2076aecfb4','Nacajuca','sesion','2014-09-29 17:38:52'),('69421f032498c97020180038fddb8e24','El Llano','sesion','2014-09-29 17:38:35'),('6974ce5ac660610b44d9b9fed0ff9548','Apatzingán','sesion','2014-09-29 17:38:20'),('697e382cfd25b07a3e62275d3ee132b3','Uriangato','sesion','2014-09-29 17:39:36'),('698d51a19d8a121ce581499d7b701668','Apozol','sesion','2014-09-29 17:38:20'),('69a5b5995110b36a9a347898d97a610e','Pitiquito','sesion','2014-09-29 17:38:57'),('69adc1e107f7f7d035d7baf04342e1ca','Benito Juárez','sesion','2014-09-29 17:38:23'),('69cb3ea317a32c4e6143e665fdb20b14','Comalcalco','sesion','2014-09-29 17:38:30'),('69d1fc78dbda242c43ad6590368912d4','Tampico','sesion','2014-09-29 17:39:25'),('69d658d0b2859e32cd4dc3b970c8496c','Santa Catarina Quioquitani','sesion','2014-09-29 17:39:15'),('69dafe8b58066478aea48f3d0f384820','Zongozotla','sesion','2014-09-29 17:39:42'),('6a10bbd480e4c5573d8f3af73ae0454b','Huajicori','sesion','2014-09-29 17:38:39'),('6a2feef8ed6a9fe76d6b3f30f02150b4','Pantelhó','sesion','2014-09-29 17:38:56'),('6a508a60aa3bf9510ea6acb021c94b48','Teopantlán','sesion','2014-09-29 17:39:28'),('6a5889bb0190d0211a991f47bb19a777','San Juan Bautista Valle Nacional','sesion','2014-09-29 17:39:06'),('6a5dfac4be1502501489fc0f5a24b667','Santiago Suchilquitongo','sesion','2014-09-29 17:39:20'),('6a61d423d02a1c56250dc23ae7ff12f3','San Andrés Cholula','sesion','2014-09-29 17:39:01'),('6a81681a7af700c6385d36577ebec359','Santa María Ozolotepec','sesion','2014-09-29 17:39:17'),('6a9aeddfc689c1d0e3b9ccc3ab651bc5','Centro','sesion','2014-09-29 17:38:26'),('6aab1270668d8cac7cef2566a1c5f569','Motul','sesion','2014-09-29 17:38:52'),('6aca97005c68f1206823815f66102863','Guadalupe','sesion','2014-09-29 17:38:38'),('6ae07dcb33ec3b7c814df797cbda0f87','Talpa de Allende','sesion','2014-09-29 17:39:24'),('6b180037abbebea991d8b1232f8a8ca9','Nazas','sesion','2014-09-29 17:38:53'),('6b8eba43551742214453411664a0dcc8','San Pedro Tapanatepec','sesion','2014-09-29 17:39:13'),('6ba3af5d7b2790e73f0de32e5c8c1798','Santa Cruz Tacahua','sesion','2014-09-29 17:39:16'),('6bc24fc1ab650b25b4114e93a98f1eba','Izamal','sesion','2014-09-29 17:38:43'),('6be5336db2c119736cf48f475e051bfe','Tecate','sesion','2014-09-29 17:39:26'),('6c14da109e294d1e8155be8aa4b1ce8e','San Andrés Tuxtla','sesion','2014-09-29 17:39:01'),('6c1da886822c67822bcf3679d04369fa','San Juan Cancuc','sesion','2014-09-29 17:39:06'),('6c29793a140a811d0c45ce03c1c93a28','Ixtlán','sesion','2014-09-29 17:38:43'),('6c340f25839e6acdc73414517203f5f0','Oriental','sesion','2014-09-29 17:38:55'),('6c3cf77d52820cd0fe646d38bc2145ca','Maguarichi','sesion','2014-09-29 17:38:48'),('6c4b761a28b734fe93831e3fb400ce87','Atlixco','sesion','2014-09-29 17:38:21'),('6c524f9d5d7027454a783c841250ba71','Chocholá','sesion','2014-09-29 17:38:29'),('6c8349cc7260ae62e3b1396831a8398f','Ahuehuetitla','sesion','2014-09-29 17:38:17'),('6c8dba7d0df1c4a79dd07646be9a26c8','San Carlos','sesion','2014-09-29 17:39:02'),('6c9882bbac1c7093bd25041881277658','Candela','sesion','2014-09-29 17:38:25'),('6cd67d9b6f0150c77bda2eda01ae484c','Ixtlán del Río','sesion','2014-09-29 17:38:43'),('6cd9313ed34ef58bad3fdd504355e72c','Xichú','sesion','2014-09-29 17:39:39'),('6cdd60ea0045eb7a6ec44c54d29ed402','Ayutla de los Libres','sesion','2014-09-29 17:38:22'),('6cfe0e6127fa25df2a0ef2ae1067d915','Moctezuma','sesion','2014-09-29 17:38:51'),('6d0f846348a856321729a2f36734d1a7','Mazatepec','sesion','2014-09-29 17:38:50'),('6d3a1e06d6a06349436bc054313b648c','San Miguel Santa Flor','sesion','2014-09-29 17:39:10'),('6d70cb65d15211726dcce4c0e971e21c','Numarán','sesion','2014-09-29 17:38:54'),('6d9c547cf146054a5a720606a7694467','San Juan Tepeuxila','sesion','2014-09-29 17:39:08'),('6d9cb7de5e8ac30bd5e8734bc96a35c1','San Miguel Ahuehuetitlán','sesion','2014-09-29 17:39:10'),('6da37dd3139aa4d9aa55b8d237ec5d4a','Chietla','sesion','2014-09-29 17:38:28'),('6da9003b743b65f4c0ccd295cc484e57','Calcahualco','sesion','2014-09-29 17:38:24'),('6dd4e10e3296fa63738371ec0d5df818','Santa Cruz Xitla','sesion','2014-09-29 17:39:16'),('6e0721b2c6977135b916ef286bcb49ec','Joquicingo','sesion','2014-09-29 17:38:45'),('6e2713a6efee97bacb63e52c54f0ada0','Felipe Carrillo Puerto','sesion','2014-09-29 17:38:36'),('6e5025ccc7d638ae4e724da8938450a6','Uxpanapa','sesion','2014-09-29 17:39:36'),('6e62a992c676f611616097dbea8ea030','Santiago Nacaltepec','sesion','2014-09-29 17:39:20'),('6e79ed05baec2754e25b4eac73a332d2','Seyé','sesion','2014-09-29 17:39:22'),('6e7b33fdea3adc80ebd648fffb665bb8','Kinchil','sesion','2014-09-29 17:38:46'),('6e7d2da6d3953058db75714ac400b584','Rafael Delgado','sesion','2014-09-29 17:38:59'),('6ea2ef7311b482724a9b7b0bc0dd85c6','Cumpas','sesion','2014-09-29 17:38:33'),('6ea9ab1baa0efb9e19094440c317e21b','Aculco','sesion','2014-09-29 17:38:17'),('6eb6e75fddec0218351dc5c0c8464104','Rodeo','sesion','2014-09-29 17:38:59'),('6ecbdd6ec859d284dc13885a37ce8d81','Coronango','sesion','2014-09-29 17:38:31'),('6f2268bd1d3d3ebaabb04d6b5d099425','Jáltipan','sesion','2014-09-29 17:38:44'),('6f2688a5fce7d48c8d19762b88c32c3b','Tenosique','sesion','2014-09-29 17:39:28'),('6f3e29a35278d71c7f65495871231324','San Francisco del Oro','sesion','2014-09-29 17:39:04'),('6f3ef77ac0e3619e98159e9b6febf557','Boca del Río','sesion','2014-09-29 17:38:23'),('6f4920ea25403ec77bee9efce43ea25e','Tlalnelhuayocan','sesion','2014-09-29 17:39:32'),('6f4922f45568161a8cdf4ad2299f6d23','Acatlán de Pérez Figueroa','sesion','2014-09-29 17:38:17'),('6faa8040da20ef399b63a72d0e4ab575','Chicontepec','sesion','2014-09-29 17:38:28'),('70222949cc0db89ab32c9969754d4758','San Felipe Usila','sesion','2014-09-29 17:39:03'),('704afe073992cbe4813cae2f7715336f','Maltrata','sesion','2014-09-29 17:38:48'),('705f2172834666788607efbfca35afb3','Cadereyta Jiménez','sesion','2014-09-29 17:38:24'),('708f3cf8100d5e71834b1db77dfa15d6','Oxchuc','sesion','2014-09-29 17:38:56'),('70c639df5e30bdee440e4cdf599fec2b','Cohetzala','sesion','2014-09-29 17:38:30'),('70ece1e1e0931919438fcfc6bd5f199c','Santiago Maravatío','sesion','2014-09-29 17:39:20'),('70efba66d3d8d53194fb1a8446ae07fa','San Sebastián Nicananduta','sesion','2014-09-29 17:39:14'),('70efdf2ec9b086079795c442636b55fb','Acatlán de Juárez','sesion','2014-09-29 17:38:17'),('70feb62b69f16e0238f741fab228fec2','San Juan Cotzocón','sesion','2014-09-29 17:39:06'),('712a3c9878efeae8ff06d57432016ceb','Santa María Chilchotla','sesion','2014-09-29 17:39:17'),('7137debd45ae4d0ab9aa953017286b20','Santiago Ixtayutla','sesion','2014-09-29 17:39:19'),('7143d7fbadfa4693b9eec507d9d37443','Jonuta','sesion','2014-09-29 17:38:45'),('71560ce98c8250ce57a6a970c9991a5f','Villa de Arriaga','sesion','2014-09-29 17:39:37'),('716e1b8c6cd17b771da77391355749f3','Zapotitlán','sesion','2014-09-29 17:39:41'),('71a3cb155f8dc89bf3d0365288219936','Huimanguillo','sesion','2014-09-29 17:38:41'),('71a58e8cb75904f24cde464161c3e766','Santa Catarina Ticuá','sesion','2014-09-29 17:39:15'),('71ad16ad2c4d81f348082ff6c4b20768','La Grandeza','sesion','2014-09-29 17:38:46'),('71e09b16e21f7b6919bbfc43f6a5b2f0','Tizimín','sesion','2014-09-29 17:39:31'),('71f6278d140af599e06ad9bf1ba03cb0','San Fernando','sesion','2014-09-29 17:39:03'),('7250eb93b3c18cc9daa29cf58af7a004','La Unión de Isidoro Montes de Oca','sesion','2014-09-29 17:38:47'),('7283518d47a05a09d33779a17adf1707','Santiago Tulantepec de Lugo Guerrero','sesion','2014-09-29 17:39:20'),('728f206c2a01bf572b5940d7d9a8fa4c','Suchiapa','sesion','2014-09-29 17:39:23'),('729c68884bd359ade15d5f163166738a','San Miguel Peras','sesion','2014-09-29 17:39:10'),('72b32a1f754ba1c09b3695e0cb6cde7f','Alfajayucan','sesion','2014-09-29 17:38:18'),('72da7fd6d1302c0a159f6436d01e9eb0','Jopala','sesion','2014-09-29 17:38:45'),('7302e3f5e7c072aea8801faf8a492be0','Tarimoro','sesion','2014-09-29 17:39:26'),('731c83db8d2ff01bdc000083fd3c3740','Pacula','sesion','2014-09-29 17:38:56'),('73278a4a86960eeb576a8fd4c9ec6997','Aquila','sesion','2014-09-29 17:38:20'),('735b90b4568125ed6c3f678819b6e058','Altamirano','sesion','2014-09-29 17:38:18'),('7380ad8a673226ae47fce7bff88e9c33','Dolores Hidalgo','sesion','2014-09-29 17:38:34'),('7385db9a3f11415bc0e9e2625fae3734','Susupuato','sesion','2014-09-29 17:39:24'),('73e0f7487b8e5297182c5a711d20bf26','Tecomán','sesion','2014-09-29 17:39:27'),('74071a673307ca7459bcf75fbd024e09','Cuitláhuac','sesion','2014-09-29 17:38:33'),('743394beff4b1282ba735e5e3723ed74','Zacatecas','sesion','2014-09-29 17:39:41'),('7437d136770f5b35194cb46c1653efaa','Santa Lucía Monteverde','sesion','2014-09-29 17:39:16'),('74563ba21a90da13dacf2a73e3ddefa7','Santiago Ixcuintepec','sesion','2014-09-29 17:39:19'),('748ba69d3e8d1af87f84fee909eef339','San Pedro Jocotipac','sesion','2014-09-29 17:39:12'),('74bba22728b6185eec06286af6bec36d','Mexticacán','sesion','2014-09-29 17:38:50'),('74db120f0a8e5646ef5a30154e9f6deb','Cajeme','sesion','2014-09-29 17:38:24'),('7501e5d4da87ac39d782741cd794002d','Salto de Agua','sesion','2014-09-29 17:39:00'),('7503cfacd12053d309b6bed5c89de212','Tahmek','sesion','2014-09-29 17:39:24'),('7504adad8bb96320eb3afdd4df6e1f60','Magdalena Zahuatlán','sesion','2014-09-29 17:38:48'),('752d25a1f8dbfb2d656bac3094bfb81c','Santiago Miahuatlán','sesion','2014-09-29 17:39:20'),('754dda4b1ba34c6fa89716b85d68532b','Panotla','sesion','2014-09-29 17:38:56'),('757b505cfd34c64c85ca5b5690ee5293','Batopilas','sesion','2014-09-29 17:38:23'),('757f843a169cc678064d9530d12a1881','Santa María Jacatepec','sesion','2014-09-29 17:39:17'),('758874998f5bd0c393da094e1967a72b','Cherán','sesion','2014-09-29 17:38:27'),('758a06618c69880a6cee5314ee42d52f','Xochitlán de Vicente Suárez','sesion','2014-09-29 17:39:40'),('75fc093c0ee742f6dddaa13fff98f104','Cortazar','sesion','2014-09-29 17:38:31'),('7634ea65a4e6d9041cfd3f7de18e334a','Mazamitla','sesion','2014-09-29 17:38:49'),('7647966b7343c29048673252e490f736','Amecameca','sesion','2014-09-29 17:38:19'),('766d856ef1a6b02f93d894415e6bfa0e','Nealtican','sesion','2014-09-29 17:38:53'),('766ebcd59621e305170616ba3d3dac32','General Zaragoza','sesion','2014-09-29 17:38:37'),('76cf99d3614e23eabab16fb27e944bf9','San Ignacio','sesion','2014-09-29 17:39:04'),('76dc611d6ebaafc66cc0879c71b5db5c','Ascensión','sesion','2014-09-29 17:38:20'),('77369e37b2aa1404f416275183ab055f','Tecozautla','sesion','2014-09-29 17:39:27'),('7750ca3559e5b8e1f44210283368fc16','Hecelchakán','sesion','2014-09-29 17:38:38'),('778609db5dc7e1a8315717a9cdd8fd6f','Zozocolco de Hidalgo','sesion','2014-09-29 17:39:42'),('77f959f119f4fb2321e9ce801e2f5163','San Nicolás de los Garza','sesion','2014-09-29 17:39:11'),('7810ccd41bf26faaa2c4e1f20db70a71','San Cristóbal Suchixtlahuaca','sesion','2014-09-29 17:39:03'),('788d986905533aba051261497ecffcbb','Ixpantepec Nieves','sesion','2014-09-29 17:38:42'),('7895fc13088ee37f511913bac71fa66f','San Marcial Ozolotepec','sesion','2014-09-29 17:39:09'),('78b9cab19959e4af8ff46156ee460c74','San Miguel Ejutla','sesion','2014-09-29 17:39:10'),('792c7b5aae4a79e78aaeda80516ae2ac','San Juan Comaltepec','sesion','2014-09-29 17:39:06'),('7940ab47468396569a906f75ff3f20ef','San Francisco Logueche','sesion','2014-09-29 17:39:04'),('7949e456002b28988d38185bd30e77fd','San Salvador Huixcolotla','sesion','2014-09-29 17:39:13'),('795c7a7a5ec6b460ec00c5841019b9e9','La Pe','sesion','2014-09-29 17:38:46'),('798cebccb32617ad94123450fd137104','Tepetlán','sesion','2014-09-29 17:39:29'),('798ed7d4ee7138d49b8828958048130a','Santiago Ayuquililla','sesion','2014-09-29 17:39:19'),('79a49b3e3762632813f9e35f4ba53d6c','San Juan de Guadalupe','sesion','2014-09-29 17:39:06'),('7a53928fa4dd31e82c6ef826f341daec','Juchitepec','sesion','2014-09-29 17:38:45'),('7a614fd06c325499f1680b9896beedeb','Catazajá','sesion','2014-09-29 17:38:26'),('7a674153c63cff1ad7f0e261c369ab2c','San Julián','sesion','2014-09-29 17:39:08'),('7a6a74cbe87bc60030a4bd041dd47b78','Santa Catarina Minas','sesion','2014-09-29 17:39:15'),('7af6266cc52234b5aa339b16695f7fc4','Tenango del Valle','sesion','2014-09-29 17:39:28'),('7b13b2203029ed80337f27127a9f1d28','Nácori Chico','sesion','2014-09-29 17:38:52'),('7b1ce3d73b70f1a7246e7b76a35fb552','Tonanitla','sesion','2014-09-29 17:39:34'),('7b5b23f4aadf9513306bcd59afb6e4c9','San Juan Teposcolula','sesion','2014-09-29 17:39:08'),('7b7a53e239400a13bd6be6c91c4f6c4e','Tianguistenco','sesion','2014-09-29 17:39:31'),('7bb060764a818184ebb1cc0d43d382aa','San Francisco Teopan','sesion','2014-09-29 17:39:04'),('7bc1ec1d9c3426357e69acd5bf320061','Vallecillo','sesion','2014-09-29 17:39:36'),('7bccfde7714a1ebadf06c5f4cea752c1','San Antonino el Alto','sesion','2014-09-29 17:39:01'),('7bcdf75ad237b8e02e301f4091fb6bc8','Emiliano Zapata','sesion','2014-09-29 17:38:36'),('7bd28f15a49d5e5848d6ec70e584e625','San Sebastián Abasolo','sesion','2014-09-29 17:39:13'),('7c4ede33a62160a19586f6e26eaefacf','Santa María Tecomavaca','sesion','2014-09-29 17:39:18'),('7c590f01490190db0ed02a5070e20f01','Juárez','sesion','2014-09-29 17:38:45'),('7c82fab8c8f89124e2ce92984e04fb40','San Francisco Cajonos','sesion','2014-09-29 17:39:03'),('7c9d0b1f96aebd7b5eca8c3edaa19ebb','Poanas','sesion','2014-09-29 17:38:58'),('7ca57a9f85a19a6e4b9a248c1daca185','Teocuitatlán de Corona','sesion','2014-09-29 17:39:28'),('7cbbc409ec990f19c78c75bd1e06f215','Alto Lucero de Gutiérrez Barrios','sesion','2014-09-29 17:38:18'),('7cce53cf90577442771720a370c3c723','Ocuilan','sesion','2014-09-29 17:38:55'),('7ce3284b743aefde80ffd9aec500e085','Mama','sesion','2014-09-29 17:38:48'),('7d04bbbe5494ae9d2f5a76aa1c00fa2f','Cuyamecalco Villa de Zaragoza','sesion','2014-09-29 17:38:33'),('7d12b66d3df6af8d429c1a357d8b9e1a','Santa María de la Paz','sesion','2014-09-29 17:39:17'),('7d2b92b6726c241134dae6cd3fb8c182','Tepemaxalco','sesion','2014-09-29 17:39:29'),('7d6044e95a16761171b130dcb476a43e','San Miguel de Horcasitas','sesion','2014-09-29 17:39:10'),('7d771e0e8f3633ab54856925ecdefc5d','Rioverde','sesion','2014-09-29 17:38:59'),('7dc1c7653ac42a05642a667959c12239','Tzicatlacoyan','sesion','2014-09-29 17:39:35'),('7dcd340d84f762eba80aa538b0c527f7','Epatlán','sesion','2014-09-29 17:38:36'),('7dd0240cd412efde8bc165e864d3644f','Santo Domingo de Morelos','sesion','2014-09-29 17:39:21'),('7e1d842d0f7ee600116ffc6b2d87d83f','San Sebastián Tecomaxtlahuaca','sesion','2014-09-29 17:39:14'),('7e230522657ecdc50e4249581b861f8e','Santa Gertrudis','sesion','2014-09-29 17:39:16'),('7e7757b1e12abcb736ab9a754ffb617a','Atzacan','sesion','2014-09-29 17:38:22'),('7e7e69ea3384874304911625ac34321c','San Agustín de las Juntas','sesion','2014-09-29 17:39:00'),('7e9e346dc5fd268b49bf418523af8679','Taniche','sesion','2014-09-29 17:39:25'),('7eabe3a1649ffa2b3ff8c02ebfd5659f','Benemérito de las Américas','sesion','2014-09-29 17:38:23'),('7eacb532570ff6858afd2723755ff790','Copala','sesion','2014-09-29 17:38:31'),('7eb3c8be3d411e8ebfab08eba5f49632','San Buenaventura','sesion','2014-09-29 17:39:02'),('7eb7eabbe9bd03c2fc99881d04da9cbd','Santiago Yucuyachi','sesion','2014-09-29 17:39:21'),('7ef605fc8dba5425d6965fbd4c8fbe1f','Atlahuilco','sesion','2014-09-29 17:38:21'),('7f100b7b36092fb9b06dfb4fac360931','Castillo de Teayo','sesion','2014-09-29 17:38:26'),('7f1171a78ce0780a2142a6eb7bc4f3c8','Ixtapan de la Sal','sesion','2014-09-29 17:38:43'),('7f16109f1619fd7a733daf5a84c708c1','Tepalcatepec','sesion','2014-09-29 17:39:29'),('7f1de29e6da19d22b51c68001e7e0e54','Asunción Ocotlán','sesion','2014-09-29 17:38:21'),('7f24d240521d99071c93af3917215ef7','Esperanza','sesion','2014-09-29 17:38:36'),('7f39f8317fbdb1988ef4c628eba02591','Almoloya de Alquisiras','sesion','2014-09-29 17:38:18'),('7f53f8c6c730af6aeb52e66eb74d8507','San Juan Lachao','sesion','2014-09-29 17:39:07'),('7f5d04d189dfb634e6a85bb9d9adf21e','Huixtla','sesion','2014-09-29 17:38:41'),('7f6ffaa6bb0b408017b62254211691b5','Apulco','sesion','2014-09-29 17:38:20'),('7f975a56c761db6506eca0b37ce6ec87','Navolato','sesion','2014-09-29 17:38:53'),('7fa732b517cbed14a48843d74526c11a','Medellín','sesion','2014-09-29 17:38:50'),('7fb8ceb3bd59c7956b1df66729296a4c','San Miguel Piedras','sesion','2014-09-29 17:39:10'),('7fe1f8abaad094e0b5cb1b01d712f708','Cuautepec','sesion','2014-09-29 17:38:33'),('7fea637fd6d02b8f0adf6f7dc36aed93','Santa María Sola','sesion','2014-09-29 17:39:18'),('7fec306d1e665bc9c748b5d2b99a6e97','Olinalá','sesion','2014-09-29 17:38:55'),('7ffd85d93a3e4de5c490d304ccd9f864','Satevó','sesion','2014-09-29 17:39:22'),('801272ee79cfde7fa5960571fee36b9b','Tlazazalca','sesion','2014-09-29 17:39:33'),('801c14f07f9724229175b8ef8b4585a8','Poza Rica de Hidalgo','sesion','2014-09-29 17:38:58'),('8038da89e49ac5eabb489cfc6cea9fc1','Texistepec','sesion','2014-09-29 17:39:30'),('80537a945c7aaa788ccfcdf1b99b5d8f','Zapotitlán de Méndez','sesion','2014-09-29 17:39:41'),('8065d07da4a77621450aa84fee5656d9','Mochitlán','sesion','2014-09-29 17:38:51'),('806beafe154032a5b818e97b4420ad98','Santa Cruz','sesion','2014-09-29 17:39:15'),('80a8155eb153025ea1d513d0b2c4b675','San Mateo Atenco','sesion','2014-09-29 17:39:09'),('812b4ba287f5ee0bc9d43bbf5bbe87fb','Angel Albino Corzo','sesion','2014-09-29 17:38:19'),('81448138f5f163ccdba4acc69819f280','Epazoyucan','sesion','2014-09-29 17:38:36'),('814a9c18f5abff398787c9cfcbf3d80c','Tlalnepantla de Baz','sesion','2014-09-29 17:39:32'),('816b112c6105b3ebd537828a39af4818','Comala','sesion','2014-09-29 17:38:30'),('818f4654ed39a1c147d1e51a00ffb4cb','Pahuatlán','sesion','2014-09-29 17:38:56'),('819c9fbfb075d62a16393b9fe4fcbaa5','San Pedro Molinos','sesion','2014-09-29 17:39:13'),('819e3d6c1381eac87c17617e5165f38c','Tlanepantla','sesion','2014-09-29 17:39:33'),('819f46e52c25763a55cc642422644317','Chila de la Sal','sesion','2014-09-29 17:38:28'),('81b3833e2504647f9d794f7d7b9bf341','Villa Corona','sesion','2014-09-29 17:39:37'),('81c650caac28cdefce4de5ddc18befa0','Santa Inés Yatzeche','sesion','2014-09-29 17:39:16'),('81c8727c62e800be708dbf37c4695dff','San Pedro','sesion','2014-09-29 17:39:12'),('81ca0262c82e712e50c580c032d99b60','San Juan Cieneguilla','sesion','2014-09-29 17:39:06'),('81dc9bdb52d04dc20036dbd8313ed055','San Andrés Zautla','sesion','2014-09-29 17:39:01'),('81e5f81db77c596492e6f1a5a792ed53','San Bartolo Coyotepec','sesion','2014-09-29 17:39:02'),('81e74d678581a3bb7a720b019f4f1a93','La Compañía','sesion','2014-09-29 17:38:46'),('82161242827b703e6acf9c726942a1e4','Axutla','sesion','2014-09-29 17:38:22'),('821fa74b50ba3f7cba1e6c53e8fa6845','Cuapiaxtla de Madero','sesion','2014-09-29 17:38:32'),('8232e119d8f59aa83050a741631803a6','Tonalá','sesion','2014-09-29 17:39:34'),('82489c9737cc245530c7a6ebef3753ec','Juanacatlán','sesion','2014-09-29 17:38:45'),('8248a99e81e752cb9b41da3fc43fbe7f','Progreso de Obregón','sesion','2014-09-29 17:38:58'),('82965d4ed8150294d4330ace00821d77','San José Independencia','sesion','2014-09-29 17:39:05'),('82aa4b0af34c2313a562076992e50aa3','Atotonilco el Grande','sesion','2014-09-29 17:38:22'),('82b8a3434904411a9fdc43ca87cee70c','Nombre de Dios','sesion','2014-09-29 17:38:54'),('82c2559140b95ccda9c6ca4a8b981f1e','Sabinas Hidalgo','sesion','2014-09-29 17:39:00'),('82cadb0649a3af4968404c9f6031b233','Susticacán','sesion','2014-09-29 17:39:24'),('82cec96096d4281b7c95cd7e74623496','Chocamán','sesion','2014-09-29 17:38:29'),('82f2b308c3b01637c607ce05f52a2fed','Ixtapan del Oro','sesion','2014-09-29 17:38:43'),('8303a79b1e19a194f1875981be5bdb6f','Santa María Temaxcaltepec','sesion','2014-09-29 17:39:18'),('831c2f88a604a07ca94314b56a4921b8','Santa María Jaltianguis','sesion','2014-09-29 17:39:17'),('838e8afb1ca34354ac209f53d90c3a43','San Bartolo Soyaltepec','sesion','2014-09-29 17:39:02'),('839ab46820b524afda05122893c2fe8e','Chahuites','sesion','2014-09-29 17:38:26'),('83adc9225e4deb67d7ce42d58fe5157c','San Juan Bautista Cuicatlán','sesion','2014-09-29 17:39:06'),('83cdcec08fbf90370fcf53bdd56604ff','San Miguel el Alto','sesion','2014-09-29 17:39:10'),('83e8ef518174e1eb6be4a0778d050c9d','Santa Ana Zegache','sesion','2014-09-29 17:39:15'),('83f2550373f2f19492aa30fbd5b57512','San Pablo Tijaltepec','sesion','2014-09-29 17:39:11'),('83f97f4825290be4cb794ec6a234595f','Río Lagartos','sesion','2014-09-29 17:38:59'),('83fa5a432ae55c253d0e60dbfa716723','Nuevo Urecho','sesion','2014-09-29 17:38:54'),('84117275be999ff55a987b9381e01f96','Huejutla de Reyes','sesion','2014-09-29 17:38:40'),('84438b7aae55a0638073ef798e50b4ef','San Francisco Chapulapa','sesion','2014-09-29 17:39:04'),('846c260d715e5b854ffad5f70a516c88','Kantunil','sesion','2014-09-29 17:38:46'),('847cc55b7032108eee6dd897f3bca8a5','Loma Bonita','sesion','2014-09-29 17:38:47'),('84c6494d30851c63a55cdb8cb047fadd','Santiago Matatlán','sesion','2014-09-29 17:39:20'),('84d2004bf28a2095230e8e14993d398d','Nopalucan','sesion','2014-09-29 17:38:54'),('84d9ee44e457ddef7f2c4f25dc8fa865','Banámichi','sesion','2014-09-29 17:38:23'),('84ddfb34126fc3a48ee38d7044e87276','Tezoyuca','sesion','2014-09-29 17:39:31'),('84f0f20482cde7e5eacaf7364a643d33','Santa Inés de Zaragoza','sesion','2014-09-29 17:39:16'),('84f7e69969dea92a925508f7c1f9579a','Las Vigas de Ramírez','sesion','2014-09-29 17:38:47'),('850af92f8d9903e7a4e0559a98ecc857','Tlapanalá','sesion','2014-09-29 17:39:33'),('851300ee84c2b80ed40f51ed26d866fc','Santiago Jocotepec','sesion','2014-09-29 17:39:19'),('851ddf5058cf22df63d3344ad89919cf','Güémez','sesion','2014-09-29 17:38:38'),('85422afb467e9456013a2a51d4dff702','El Mante','sesion','2014-09-29 17:38:35'),('854d6fae5ee42911677c739ee1734486','Baviácora','sesion','2014-09-29 17:38:23'),('854d9fca60b4bd07f9bb215d59ef5561','Degollado','sesion','2014-09-29 17:38:34'),('8562ae5e286544710b2e7ebe9858833b','Tenabo','sesion','2014-09-29 17:39:28'),('856fc81623da2150ba2210ba1b51d241','San Lucas Camotlán','sesion','2014-09-29 17:39:08'),('8597a6cfa74defcbde3047c891d78f90','Piaxtla','sesion','2014-09-29 17:38:57'),('85d8ce590ad8981ca2c8286f79f59954','Balancán','sesion','2014-09-29 17:38:23'),('85f007f8c50dd25f5a45fca73cad64bd','Soyopa','sesion','2014-09-29 17:39:23'),('85fc37b18c57097425b52fc7afbb6969','Heroica Ciudad de Juchitán de Zaragoza','sesion','2014-09-29 17:38:39'),('860320be12a1c050cd7731794e231bd3','Naco','sesion','2014-09-29 17:38:52'),('86109d400f0ed29e840b47ed72777c84','San José Estancia Grande','sesion','2014-09-29 17:39:05'),('8613985ec49eb8f757ae6439e879bb2a','Amixtlán','sesion','2014-09-29 17:38:19'),('861dc9bd7f4e7dd3cccd534d0ae2a2e9','San José Teacalco','sesion','2014-09-29 17:39:05'),('865dfbde8a344b44095495f3591f7407','Tataltepec de Valdés','sesion','2014-09-29 17:39:26'),('8698ff92115213ab187d31d4ee5da8ea','Santiago Yaitepec','sesion','2014-09-29 17:39:21'),('86b122d4358357d834a87ce618a55de0','Jitotol','sesion','2014-09-29 17:38:44'),('86d7c8a08b4aaa1bc7c599473f5dddda','Santa María del Tule','sesion','2014-09-29 17:39:17'),('86df7dcfd896fcaf2674f757a2463eba','San Miguel Quetzaltepec','sesion','2014-09-29 17:39:10'),('86e78499eeb33fb9cac16b7555b50767','Uruapan','sesion','2014-09-29 17:39:36'),('86e8f7ab32cfd12577bc2619bc635690','San Juan Guelavía','sesion','2014-09-29 17:39:07'),('872488f88d1b2db54d55bc8bba2fad1b','Jaltocán','sesion','2014-09-29 17:38:44'),('8725fb777f25776ffa9076e44fcfd776','Santiago Huajolotitlán','sesion','2014-09-29 17:39:19'),('8757150decbd89b0f5442ca3db4d0e0e','Huejúcar','sesion','2014-09-29 17:38:40'),('877a9ba7a98f75b90a9d49f53f15a858','Cuayuca de Andrade','sesion','2014-09-29 17:38:33'),('87ec2f451208df97228105657edb717f','Santa Ana Tlapacoyan','sesion','2014-09-29 17:39:14'),('883e881bb4d22a7add958f2d6b052c9f','Nopala de Villagrán','sesion','2014-09-29 17:38:54'),('884ce4bb65d328ecb03c598409e2b168','San Bartolomé Ayautla','sesion','2014-09-29 17:39:02'),('884d247c6f65a96a7da4d1105d584ddd','Huautepec','sesion','2014-09-29 17:38:40'),('884d79963bd8bc0ae9b13a1aa71add73','Quiriego','sesion','2014-09-29 17:38:59'),('88a199611ac2b85bd3f76e8ee7e55650','San Simón Almolongas','sesion','2014-09-29 17:39:14'),('88a839f2f6f1427879fc33ee4acf4f66','Valle de Santiago','sesion','2014-09-29 17:39:36'),('88ae6372cfdc5df69a976e893f4d554b','Jerécuaro','sesion','2014-09-29 17:38:44'),('88bfcf02e7f554f9e9ea350b699bc6a7','Zoquitlán','sesion','2014-09-29 17:39:42'),('88ef51f0bf911e452e8dbb1d807a81ab','Tingambato','sesion','2014-09-29 17:39:31'),('8929c70f8d710e412d38da624b21c3c8','Valladolid','sesion','2014-09-29 17:39:36'),('892c91e0a653ba19df81a90f89d99bcd','Marquelia','sesion','2014-09-29 17:38:49'),('894b77f805bd94d292574c38c5d628d5','Sanahcat','sesion','2014-09-29 17:39:14'),('89885ff2c83a10305ee08bd507c1049c','Tizayuca','sesion','2014-09-29 17:39:31'),('89ae0fe22c47d374bc9350ef99e01685','San Pedro Ocopetatillo','sesion','2014-09-29 17:39:13'),('89f03f7d02720160f1b04cf5b27f5ccb','Santa Isabel Cholula','sesion','2014-09-29 17:39:16'),('89f0fd5c927d466d6ec9a21b9ac34ffa','Chiconamel','sesion','2014-09-29 17:38:28'),('89fcd07f20b6785b92134bd6c1d0fa42','Moloacán','sesion','2014-09-29 17:38:52'),('8a0e1141fd37fa5b98d5bb769ba1a7cc','Indé','sesion','2014-09-29 17:38:42'),('8a146f1a3da4700cbf03cdc55e2daae6','San Miguel Soyaltepec','sesion','2014-09-29 17:39:10'),('8a1e808b55fde9455cb3d8857ed88389','Ozuluama de Mascareñas','sesion','2014-09-29 17:38:56'),('8a3363abe792db2d8761d6403605aeb7','Puente Nacional','sesion','2014-09-29 17:38:58'),('8b0d268963dd0cfb808aac48a549829f','San Miguel Chimalapa','sesion','2014-09-29 17:39:10'),('8b0dc65f996f98fd178a9defd0efa077','San Juan Achiutla','sesion','2014-09-29 17:39:06'),('8b16ebc056e613024c057be590b542eb','Fronteras','sesion','2014-09-29 17:38:37'),('8b4066554730ddfaa0266346bdc1b202','Panindícuaro','sesion','2014-09-29 17:38:56'),('8b5040a8a5baf3e0e67386c2e3a9b903','Mayapán','sesion','2014-09-29 17:38:49'),('8b5700012be65c9da25f49408d959ca0','Santiago Chazumba','sesion','2014-09-29 17:39:19'),('8b6a80c3cf2cbd5f967063618dc54f39','Santo Domingo Petapa','sesion','2014-09-29 17:39:21'),('8b6dd7db9af49e67306feb59a8bdc52c','Matamoros','sesion','2014-09-29 17:38:49'),('8bb88f80d334b1869781beb89f7b73be','Santa María Zaniza','sesion','2014-09-29 17:39:18'),('8bf1211fd4b7b94528899de0a43b9fb3','Coahuitlán','sesion','2014-09-29 17:38:30'),('8c00dee24c9878fea090ed070b44f1ab','San Pedro de la Cueva','sesion','2014-09-29 17:39:12'),('8c01a75941549a705cf7275e41b21f0d','Santo Domingo Nuxaá','sesion','2014-09-29 17:39:21'),('8c19f571e251e61cb8dd3612f26d5ecf','Carichí','sesion','2014-09-29 17:38:25'),('8c235f89a8143a28a1d6067e959dd858','Lolotla','sesion','2014-09-29 17:38:47'),('8c249675aea6c3cbd91661bbae767ff1','Tepeyahualco','sesion','2014-09-29 17:39:29'),('8c3039bd5842dca3d944faab91447818','Tacámbaro','sesion','2014-09-29 17:39:24'),('8c5f6ecd29a0eb234459190ca51c16dd','Villa de Guadalupe','sesion','2014-09-29 17:39:38'),('8c6744c9d42ec2cb9e8885b54ff744d0','Jocotitlán','sesion','2014-09-29 17:38:44'),('8c7bbbba95c1025975e548cee86dfadc','Huásabas','sesion','2014-09-29 17:38:40'),('8c8a58fa97c205ff222de3685497742c','Tekom','sesion','2014-09-29 17:39:27'),('8ca8da41fe1ebc8d3ca31dc14f5fc56c','Xalisco','sesion','2014-09-29 17:39:39'),('8cb22bdd0b7ba1ab13d742e22eed8da2','Comonfort','sesion','2014-09-29 17:38:31'),('8ce6790cc6a94e65f17f908f462fae85','Pueblo Nuevo','sesion','2014-09-29 17:38:58'),('8d317bdcf4aafcfc22149d77babee96d','Jalostotitlán','sesion','2014-09-29 17:38:43'),('8d3369c4c086f236fabf61d614a32818','San Juan Bautista Guelache','sesion','2014-09-29 17:39:06'),('8d34201a5b85900908db6cae92723617','Escuinapa','sesion','2014-09-29 17:38:36'),('8d3bba7425e7c98c50f52ca1b52d3735','Chiautzingo','sesion','2014-09-29 17:38:27'),('8d420fa35754d1f1c19969c88780314d','Santa Lucía Miahuatlán','sesion','2014-09-29 17:39:16'),('8d55a249e6baa5c06772297520da2051','Tenamaxtlán','sesion','2014-09-29 17:39:28'),('8d5e957f297893487bd98fa830fa6413','Atizapán','sesion','2014-09-29 17:38:21'),('8d6dc35e506fc23349dd10ee68dabb64','Mezquital del Oro','sesion','2014-09-29 17:38:51'),('8d7d8ee069cb0cbbf816bbb65d56947e','Corregidora','sesion','2014-09-29 17:38:31'),('8d8818c8e140c64c743113f563cf750f','Tezontepec de Aldama','sesion','2014-09-29 17:39:31'),('8d9a0adb7c204239c9635426f35c9522','San Juan de los Lagos','sesion','2014-09-29 17:39:06'),('8d9fc2308c8f28d2a7d2f6f48801c705','Santo Tomás Mazaltepec','sesion','2014-09-29 17:39:22'),('8dd48d6a2e2cad213179a3992c0be53c','Chiquilistlán','sesion','2014-09-29 17:38:29'),('8df707a948fac1b4a0f97aa554886ec8','Ixmatlahuacan','sesion','2014-09-29 17:38:42'),('8e296a067a37563370ded05f5a3bf3ec','Actopan','sesion','2014-09-29 17:38:17'),('8e2cfdc275761edc592f73a076197c33','San Ciro de Acosta','sesion','2014-09-29 17:39:03'),('8e6b42f1644ecb1327dc03ab345e618b','Cuerámaro','sesion','2014-09-29 17:38:33'),('8e82ab7243b7c66d768f1b8ce1c967eb','La Trinidad Vista Hermosa','sesion','2014-09-29 17:38:46'),('8e98d81f8217304975ccb23337bb5761','Chapultepec','sesion','2014-09-29 17:38:27'),('8ebda540cbcc4d7336496819a46a1b68','Gustavo A. Madero','sesion','2014-09-29 17:38:38'),('8edd72158ccd2a879f79cb2538568fdc','San Jerónimo Xayacatlán','sesion','2014-09-29 17:39:05'),('8eefcfdf5990e441f0fb6f3fad709e21','Francisco Z. Mena','sesion','2014-09-29 17:38:37'),('8efb100a295c0c690931222ff4467bb8','Coatzintla','sesion','2014-09-29 17:38:30'),('8f121ce07d74717e0b1f21d122e04521','Casas Grandes','sesion','2014-09-29 17:38:26'),('8f14e45fceea167a5a36dedd4bea2543','Acámbaro','sesion','2014-09-29 17:38:16'),('8f19793b2671094e63a15ab883d50137','San Vicente Nuñú','sesion','2014-09-29 17:39:14'),('8f1d43620bc6bb580df6e80b0dc05c48','Peñamiller','sesion','2014-09-29 17:38:57'),('8f468c873a32bb0619eaeb2050ba45d1','Molango de Escamilla','sesion','2014-09-29 17:38:51'),('8f53295a73878494e9bc8dd6c3c7104f','Ayoquezco de Aldama','sesion','2014-09-29 17:38:22'),('8f7d807e1f53eff5f9efbe5cb81090fb','Landa de Matamoros','sesion','2014-09-29 17:38:47'),('8f85517967795eeef66c225f7883bdcb','Ayapango','sesion','2014-09-29 17:38:22'),('8fb21ee7a2207526da55a679f0332de2','San Martín Texmelucan','sesion','2014-09-29 17:39:09'),('8fb5f8be2aa9d6c64a04e3ab9f63feee','San Martín Itunyoso','sesion','2014-09-29 17:39:09'),('8fe0093bb30d6f8c31474bd0764e6ac0','Contepec','sesion','2014-09-29 17:38:31'),('8fecb20817b3847419bb3de39a609afe','Huichapan','sesion','2014-09-29 17:38:41'),('903ce9225fca3e988c2af215d4e544d3','Atenguillo','sesion','2014-09-29 17:38:21'),('905056c1ac1dad141560467e0a99e1cf','San Antonio Tepetlapa','sesion','2014-09-29 17:39:02'),('90599c8fdd2f6e7a03ad173e2f535751','Santo Tomás Jalieza','sesion','2014-09-29 17:39:22'),('90794e3b050f815354e3e29e977a88ab','Mexicaltzingo','sesion','2014-09-29 17:38:50'),('90aef91f0d9e7c3be322bd7bae41617d','Xochimilco','sesion','2014-09-29 17:39:40'),('90db9da4fc5414ab55a9fe495d555c06','San Lorenzo Texmelúcan','sesion','2014-09-29 17:39:08'),('90e1357833654983612fb05e3ec9148c','Sáric','sesion','2014-09-29 17:39:22'),('912d2b1c7b2826caf99687388d2e8f7c','Jalpan de Serra','sesion','2014-09-29 17:38:44'),('918317b57931b6b7a7d29490fe5ec9f9','Chacaltianguis','sesion','2014-09-29 17:38:26'),('9185f3ec501c674c7c788464a36e7fb3','Tonila','sesion','2014-09-29 17:39:34'),('9188905e74c28e489b44e954ec0b9bca','Camocuautla','sesion','2014-09-29 17:38:24'),('92262bf907af914b95a0fc33c3f33bf6','Mezquitic','sesion','2014-09-29 17:38:51'),('9232fe81225bcaef853ae32870a2b0fe','Mineral de la Reforma','sesion','2014-09-29 17:38:51'),('9246444d94f081e3549803b928260f56','Naupan','sesion','2014-09-29 17:38:53'),('92977ae4d2ba21425a59afb269c2a14e','Montecristo de Guerrero','sesion','2014-09-29 17:38:52'),('92af93f73faf3cefc129b6bc55a748a9','San Pedro Yeloixtlahuaca','sesion','2014-09-29 17:39:13'),('92bbd31f8e0e43a7da8a6295b251725f','Trancoso','sesion','2014-09-29 17:39:34'),('92c8c96e4c37100777c7190b76d28233','Cenotillo','sesion','2014-09-29 17:38:26'),('92cc227532d17e56e07902b254dfad10','Anáhuac','sesion','2014-09-29 17:38:19'),('92fb0c6d1758261f10d052e6e2c1123c','Lerdo de Tejada','sesion','2014-09-29 17:38:47'),('931af583573227f0220bc568c65ce104','Topia','sesion','2014-09-29 17:39:34'),('9327969053c0068dd9e07c529866b94d','Santa Cruz de Juventino Rosas','sesion','2014-09-29 17:39:15'),('934815ad542a4a7c5e8a2dfa04fea9f5','Nacozari de García','sesion','2014-09-29 17:38:52'),('934b535800b1cba8f96a5d72f72f1611','Vista Hermosa','sesion','2014-09-29 17:39:39'),('936a40b7e8eea0dc537e5f2edee1387a','Tzintzuntzan','sesion','2014-09-29 17:39:35'),('93d65641ff3f1586614cf2c1ad240b6c','Nochistlán de Mejía','sesion','2014-09-29 17:38:54'),('93db85ed909c13838ff95ccfa94cebd9','Amaxac de Guerrero','sesion','2014-09-29 17:38:19'),('93fb9d4b16aa750c7475b6d601c35c2c','San Gregorio Atzompa','sesion','2014-09-29 17:39:04'),('941e1aaaba585b952b62c14a3a175a61','Cuajimalpa de Morelos','sesion','2014-09-29 17:38:32'),('9431c87f273e507e6040fcb07dcb4509','Cuajinicuilapa','sesion','2014-09-29 17:38:32'),('944626adf9e3b76a3919b50dc0b080a4','Tecolotlán','sesion','2014-09-29 17:39:27'),('944bdd9636749a0801c39b6e449dbedc','San Felipe Jalapa de Díaz','sesion','2014-09-29 17:39:03'),('9461cce28ebe3e76fb4b931c35a169b0','Cuncunul','sesion','2014-09-29 17:38:33'),('94c7bb58efc3b337800875b5d382a072','Espinal','sesion','2014-09-29 17:38:36'),('94e4451ad23909020c28b26ca3a13cb8','Santiago Xiacuí','sesion','2014-09-29 17:39:21'),('94f6d7e04a4d452035300f18b984988c','Chapa de Mota','sesion','2014-09-29 17:38:27'),('950a4152c2b4aa3ad78bdd6b366cc179','Chemax','sesion','2014-09-29 17:38:27'),('95151403b0db4f75bfd8da0b393af853','San Mateo Yoloxochitlán','sesion','2014-09-29 17:39:10'),('95192c98732387165bf8e396c0f2dad2','Tezonapa','sesion','2014-09-29 17:39:31'),('955a1584af63a546588caae4d23840b3','Santiago Nejapilla','sesion','2014-09-29 17:39:20'),('955cb567b6e38f4c6b3f28cc857fc38c','Tlacuilotepec','sesion','2014-09-29 17:39:32'),('958adb57686c2fdec5796398de5f317a','Tanhuato','sesion','2014-09-29 17:39:25'),('9597353e41e6957b5e7aa79214fcb256','Santa Catarina Juquila','sesion','2014-09-29 17:39:15'),('959a557f5f6beb411fd954f3f34b21c3','Jilotlán de los Dolores','sesion','2014-09-29 17:38:44'),('95d309f0b035d97f69902e7972c2b2e6','San Sebastián Teitipac','sesion','2014-09-29 17:39:14'),('95e6834d0a3d99e9ea8811855ae9229d','Tenango del Aire','sesion','2014-09-29 17:39:28'),('95f6870ff3dcd442254e334a9033d349','Tepanco de López','sesion','2014-09-29 17:39:29'),('95f8d9901ca8878e291552f001f67692','Zacatepec','sesion','2014-09-29 17:39:41'),('96055f5b06bf9381ac43879351642cf5','Tepotzotlán','sesion','2014-09-29 17:39:30'),('962e56a8a0b0420d87272a682bfd1e53','Santa Catarina Tlaltempan','sesion','2014-09-29 17:39:15'),('96671501524948bc3937b4b30d0e57b9','Zacualtipán de Ángeles','sesion','2014-09-29 17:39:41'),('9683cc5f89562ea48e72bb321d9f03fb','San Juan Bautista Tlachichilco','sesion','2014-09-29 17:39:06'),('96a93ba89a5b5c6c226e49b88973f46e','Santiago Yaveo','sesion','2014-09-29 17:39:21'),('96b9bff013acedfb1d140579e2fbeb63','La Cruz','sesion','2014-09-29 17:38:46'),('96c5c28becf18e71190460a9955aa4d8','Tamazulápam del Espíritu Santo','sesion','2014-09-29 17:39:24'),('96da2f590cd7246bbde0051047b0d6f7','Ayala','sesion','2014-09-29 17:38:22'),('96de2547f44254c97f5f4f1f402711c1','Santa Lucía Ocotlán','sesion','2014-09-29 17:39:16'),('96ea64f3a1aa2fd00c72faacf0cb8ac9','Juan R. Escudero','sesion','2014-09-29 17:38:45'),('9701a1c165dd9420816bfec5edd6c2b1','San Juan Quiahije','sesion','2014-09-29 17:39:07'),('970af30e481057c48f87e101b61e6994','San Lucas Zoquiápam','sesion','2014-09-29 17:39:08'),('97275a23ca44226c9964043c8462be96','San Miguel del Río','sesion','2014-09-29 17:39:10'),('973a5f0ccbc4ee3524ccf035d35b284b','Santa Magdalena Jicotlán','sesion','2014-09-29 17:39:16'),('9766527f2b5d3e95d4a733fcfb77bd7e','Atoyatempan','sesion','2014-09-29 17:38:22'),('9778d5d219c5080b9a6a17bef029331c','Amatitán','sesion','2014-09-29 17:38:19'),('978d76676f5e7918f81d28e7d092ca0d','Tolimán','sesion','2014-09-29 17:39:33'),('979d472a84804b9f647bc185a877a8b5','Bokobá','sesion','2014-09-29 17:38:23'),('97af4fb322bb5c8973ade16764156bed','Rosamorada','sesion','2014-09-29 17:38:59'),('97d0145823aeb8ed80617be62e08bdcc','Santa Cruz Nundaco','sesion','2014-09-29 17:39:16'),('97d98119037c5b8a9663cb21fb8ebf47','Santiago Nundiche','sesion','2014-09-29 17:39:20'),('97e8527feaf77a97fc38f34216141515','Epitacio Huerta','sesion','2014-09-29 17:38:36'),('980ecd059122ce2e50136bda65c25e07','San Juan Bautista Atatlahuca','sesion','2014-09-29 17:39:06'),('9872ed9fc22fc182d371c3e9ed316094','Azoyú','sesion','2014-09-29 17:38:22'),('98986c005e5def2da341b4e0627d4712','San Pedro Tidaá','sesion','2014-09-29 17:39:13'),('98b297950041a42470269d56260243a1','Cuautepec de Hinojosa','sesion','2014-09-29 17:38:33'),('98c7242894844ecd6ec94af67ac8247d','Tepeapulco','sesion','2014-09-29 17:39:29'),('98d6f58ab0dafbb86b083a001561bb34','Magdalena Jaltepec','sesion','2014-09-29 17:38:48'),('98dce83da57b0395e163467c9dae521b','Angamacutiro','sesion','2014-09-29 17:38:19'),('98e6f17209029f4ae6dc9d88ec8eac2c','Zapotlán el Grande','sesion','2014-09-29 17:39:41'),('98f13708210194c475687be6106a3b84','Acaxochitlán','sesion','2014-09-29 17:38:17'),('9908279ebbf1f9b250ba689db6a0222b','Muñoz de Domingo Arenas','sesion','2014-09-29 17:38:52'),('991de292e76f74f3c285b3f6d57958d5','San Miguel Amatlán','sesion','2014-09-29 17:39:10'),('995665640dc319973d3173a74a03860c','San Dionisio Ocotepec','sesion','2014-09-29 17:39:03'),('995e1fda4a2b5f55ef0df50868bf2a8f','Nuevo Ideal','sesion','2014-09-29 17:38:54'),('996009f2374006606f4c0b0fda878af1','San Francisco Huehuetlán','sesion','2014-09-29 17:39:04'),('996a7fa078cc36c46d02f9af3bef918b','Guelatao de Juárez','sesion','2014-09-29 17:38:38'),('9978b7063e297d84bb2ac8e46c1c845f','Valle Hermoso','sesion','2014-09-29 17:39:36'),('9996535e07258a7bbfd8b132435c5962','San Antonino Castillo Velasco','sesion','2014-09-29 17:39:01'),('99adff456950dd9629a5260c4de21858','San Pedro Ixcatlán','sesion','2014-09-29 17:39:12'),('99bcfcd754a98ce89cb86f73acc04645','Irapuato','sesion','2014-09-29 17:38:42'),('99c5e07b4d5de9d18c350cdf64c5aa3d','Frontera Comalapa','sesion','2014-09-29 17:38:37'),('9a1158154dfa42caddbd0694a4e9bdc8','Alamos','sesion','2014-09-29 17:38:18'),('9a1756fd0c741126d7bbd4b692ccbd91','San Lorenzo Axocomanitla','sesion','2014-09-29 17:39:08'),('9a1de01f893e0d2551ecbb7ce4dc963e','Soto la Marina','sesion','2014-09-29 17:39:23'),('9a3d458322d70046f63dfd8b0153ece4','Sahuayo','sesion','2014-09-29 17:39:00'),('9a4400501febb2a95e79248486a5f6d3','Santa María Coyotepec','sesion','2014-09-29 17:39:17'),('9a49a25d845a483fae4be7e341368e36','Tuxcacuesco','sesion','2014-09-29 17:39:35'),('9a96876e2f8f3dc4f3cf45f02c61c0c1','Coyuca de Catalán','sesion','2014-09-29 17:38:32'),('9aa42b31882ec039965f3c4923ce901b','San Lorenzo Victoria','sesion','2014-09-29 17:39:08'),('9ab0d88431732957a618d4a469a0d4c3','Nanacamilpa de Mariano Arista','sesion','2014-09-29 17:38:53'),('9ac403da7947a183884c18a67d3aa8de','Ocotepec','sesion','2014-09-29 17:38:54'),('9ad6aaed513b73148b7d49f70afcfb32','General Plutarco Elías Calles','sesion','2014-09-29 17:38:37'),('9adeb82fffb5444e81fa0ce8ad8afe7a','Salina Cruz','sesion','2014-09-29 17:39:00'),('9afefc52942cb83c7c1f14b2139b09ba','Tláhuac','sesion','2014-09-29 17:39:32'),('9b04d152845ec0a378394003c96da594','Calera','sesion','2014-09-29 17:38:24'),('9b698eb3105bd82528f23d0c92dedfc0','Huaquechula','sesion','2014-09-29 17:38:40'),('9b70e8fe62e40c570a322f1b0b659098','Coyutla','sesion','2014-09-29 17:38:32'),('9b72e31dac81715466cd580a448cf823','Encarnación de Díaz','sesion','2014-09-29 17:38:36'),('9b8619251a19057cff70779273e95aa6','Astacinga','sesion','2014-09-29 17:38:21'),('9bb6dee73b8b0ca97466ccb24fff3139','Tuxpan','sesion','2014-09-29 17:39:35'),('9be40cee5b0eee1462c82c6964087ff9','Ciénega de Zimatlán','sesion','2014-09-29 17:38:29'),('9bf31c7ff062936a96d3c8bd1f8f2ff3','Acatic','sesion','2014-09-29 17:38:16'),('9c01802ddb981e6bcfbec0f0516b8e35','Montemorelos','sesion','2014-09-29 17:38:52'),('9c19a2aa1d84e04b0bd4bc888792bd1e','Santa Ana Yareni','sesion','2014-09-29 17:39:15'),('9c3b1830513cc3b8fc4b76635d32e692','Peribán','sesion','2014-09-29 17:38:57'),('9c82c7143c102b71c593d98d96093fde','Ixcateopan de Cuauhtémoc','sesion','2014-09-29 17:38:42'),('9c838d2e45b2ad1094d42f4ef36764f6','Chalchicomula de Sesma','sesion','2014-09-29 17:38:26'),('9cb67ffb59554ab1dabb65bcb370ddd9','San Jerónimo Zacualpan','sesion','2014-09-29 17:39:05'),('9cc138f8dc04cbf16240daa92d8d50e2','Homún','sesion','2014-09-29 17:38:39'),('9cf81d8026a9018052c429cc4e56739b','Guanajuato','sesion','2014-09-29 17:38:38'),('9cfdf10e8fc047a44b08ed031e1f0ed1','Cadereyta de Montes','sesion','2014-09-29 17:38:24'),('9d2682367c3935defcb1f9e247a97c0d','Santa María Camotlán','sesion','2014-09-29 17:39:16'),('9d7311ba459f9e45ed746755a32dcd11','Tepeyanco','sesion','2014-09-29 17:39:30'),('9da187a7a191431db943a9a5a6fec6f4','Rayón','sesion','2014-09-29 17:38:59'),('9dcb88e0137649590b755372b040afad','Bacadéhuachi','sesion','2014-09-29 17:38:22'),('9de6d14fff9806d4bcd1ef555be766cd','Chinantla','sesion','2014-09-29 17:38:28'),('9dfcd5e558dfa04aaf37f137a1d9d3e5','Chavinda','sesion','2014-09-29 17:38:27'),('9e3cfc48eccf81a0d57663e129aef3cb','Juchique de Ferrer','sesion','2014-09-29 17:38:45'),('9e984c108157cea74c894b5cf34efc44','Santa Apolonia Teacalco','sesion','2014-09-29 17:39:15'),('9f36407ead0629fc166f14dde7970f68','Pajapan','sesion','2014-09-29 17:38:56'),('9f396fe44e7c05c16873b05ec425cbad','Hueytlalpan','sesion','2014-09-29 17:38:41'),('9f44e956e3a2b7b5598c625fcc802c36','Tuzantla','sesion','2014-09-29 17:39:35'),('9f53d83ec0691550f7d2507d57f4f5a2','Metapa','sesion','2014-09-29 17:38:50'),('9f61408e3afb633e50cdf1b20de6f466','Aldama','sesion','2014-09-29 17:38:18'),('9f62b8625f914a002496335037e9ad97','Tila','sesion','2014-09-29 17:39:31'),('9f6992966d4c363ea0162a056cb45fe5','Telchac Puerto','sesion','2014-09-29 17:39:27'),('9fc3d7152ba9336a670e36d0ed79bc43','Asunción Ixtaltepec','sesion','2014-09-29 17:38:21'),('9fd81843ad7f202f26c1a174c7357585','Chamula','sesion','2014-09-29 17:38:27'),('9fdb62f932adf55af2c0e09e55861964','Techaluta de Montenegro','sesion','2014-09-29 17:39:26'),('9fe8593a8a330607d76796b35c64c600','Huimilpan','sesion','2014-09-29 17:38:41'),('9fe97fff97f089661135d0487843108e','Santa María Totolapilla','sesion','2014-09-29 17:39:18'),('a00e5eb0973d24649a4a920fc53d9564','Teúl de González Ortega','sesion','2014-09-29 17:39:30'),('a012869311d64a44b5a0d567cd20de04','Tula de Allende','sesion','2014-09-29 17:39:35'),('a0160709701140704575d499c997b6ca','Paracho','sesion','2014-09-29 17:38:56'),('a01610228fe998f515a72dd730294d87','San Agustín Metzquititlán','sesion','2014-09-29 17:39:01'),('a01a0380ca3c61428c26a231f0e49a09','Cocula','sesion','2014-09-29 17:38:30'),('a02ffd91ece5e7efeb46db8f10a74059','Coalcomán de Vázquez Pallares','sesion','2014-09-29 17:38:30'),('a03fa30821986dff10fc66647c84c9c3','Tocumbo','sesion','2014-09-29 17:39:33'),('a07c2f3b3b907aaf8436a26c6d77f0a2','Yahualica','sesion','2014-09-29 17:39:40'),('a0833c8a1817526ac555f8d67727caf6','San Francisco del Mar','sesion','2014-09-29 17:39:04'),('a0872cc5b5ca4cc25076f3d868e1bdf8','San Francisco Ozolotepec','sesion','2014-09-29 17:39:04'),('a088ea2078cd92b0b8a0e78a32c5c082','Tlaquilpa','sesion','2014-09-29 17:39:33'),('a0a080f42e6f13b3a2df133f073095dd','Arizpe','sesion','2014-09-29 17:38:20'),('a0e2a2c563d57df27213ede1ac4ac780','Ocoyoacac','sesion','2014-09-29 17:38:54'),('a0f3601dc682036423013a5d965db9aa','Santa María Ixcatlán','sesion','2014-09-29 17:39:17'),('a113c1ecd3cace2237256f4c712f61b5','Reyes Etla','sesion','2014-09-29 17:38:59'),('a1140a3d0df1c81e24ae954d935e8926','Muxupip','sesion','2014-09-29 17:38:52'),('a14ac55a4f27472c5d894ec1c3c743d2','Santa Catarina Mechoacán','sesion','2014-09-29 17:39:15'),('a1519de5b5d44b31a01de013b9b51a80','Otzoloapan','sesion','2014-09-29 17:38:55'),('a19acd7d2689207f9047f8cb01357370','Tamuín','sesion','2014-09-29 17:39:25'),('a1afc58c6ca9540d057299ec3016d726','San Sebastián del Oeste','sesion','2014-09-29 17:39:14'),('a1d0c6e83f027327d8461063f4ac58a6','Ahualulco de Mercado','sesion','2014-09-29 17:38:17'),('a1d33d0dfec820b41b54430b50e96b5c','Jaltenco','sesion','2014-09-29 17:38:44'),('a1d50185e7426cbb0acad1e6ca74b9aa','Puerto Peñasco','sesion','2014-09-29 17:38:58'),('a1d7311f2a312426d710e1c617fcbc8c','Tolcayuca','sesion','2014-09-29 17:39:33'),('a2137a2ae8e39b5002a3f8909ecb88fe','Ostuacán','sesion','2014-09-29 17:38:55'),('a223c6b3710f85df22e9377d6c4f7553','San Juan Cacahuatepec','sesion','2014-09-29 17:39:06'),('a2557a7b2e94197ff767970b67041697','Bacalar','sesion','2014-09-29 17:38:22'),('a26398dca6f47b49876cbaffbc9954f9','Palmar de Bravo','sesion','2014-09-29 17:38:56'),('a284df1155ec3e67286080500df36a9a','San Bartolo Tutotepec','sesion','2014-09-29 17:39:02'),('a29d1598024f9e87beab4b98411d48ce','Totolapa','sesion','2014-09-29 17:39:34'),('a2cc63e065705fe938a4dda49092966f','Santa Catarina Loxicha','sesion','2014-09-29 17:39:15'),('a34bacf839b923770b2c360eefa26748','Nuevo Parangaricutiro','sesion','2014-09-29 17:38:54'),('a3545bd79d31f9a72d3a78690adf73fc','Tamasopo','sesion','2014-09-29 17:39:24'),('a368b0de8b91cfb3f91892fbf1ebd4b2','Santa Catarina Ayometla','sesion','2014-09-29 17:39:15'),('a376033f78e144f494bfc743c0be3330','Zacapu','sesion','2014-09-29 17:39:41'),('a381c2c35c9157f6b67fd07d5a200ae1','Unión Hidalgo','sesion','2014-09-29 17:39:36'),('a38b16173474ba8b1a95bcbc30d3b8a5','Teotihuacán','sesion','2014-09-29 17:39:28'),('a3c65c2974270fd093ee8a9bf8ae7d0b','Apizaco','sesion','2014-09-29 17:38:20'),('a3d68b461bd9d3533ee1dd3ce4628ed4','Milpa Alta','sesion','2014-09-29 17:38:51'),('a3f390d88e4c41f2747bfa2f1b5f87db','Altar','sesion','2014-09-29 17:38:18'),('a3fb4fbf9a6f9cf09166aa9c20cbc1ad','Rafael Lucio','sesion','2014-09-29 17:38:59'),('a40511cad8383e5ae8ddd8b855d135da','Santa María Chimalapa','sesion','2014-09-29 17:39:17'),('a424ed4bd3a7d6aea720b86d4a360f75','San Lucas Quiaviní','sesion','2014-09-29 17:39:08'),('a42a596fc71e17828440030074d15e74','Sain Alto','sesion','2014-09-29 17:39:00'),('a4300b002bcfb71f291dac175d52df94','Matehuala','sesion','2014-09-29 17:38:49'),('a48564053b3c7b54800246348c7fa4a0','Tixtla de Guerrero','sesion','2014-09-29 17:39:31'),('a486cd07e4ac3d270571622f4f316ec5','Umán','sesion','2014-09-29 17:39:36'),('a49e9411d64ff53eccfdd09ad10a15b3','Cosío','sesion','2014-09-29 17:38:32'),('a4a042cf4fd6bfb47701cbc8a1653ada','Autlán de Navarro','sesion','2014-09-29 17:38:22'),('a4d2f0d23dcc84ce983ff9157f8b7f88','San Agustín Yatareni','sesion','2014-09-29 17:39:01'),('a4f23670e1833f3fdb077ca70bbd5d66','Carbó','sesion','2014-09-29 17:38:25'),('a501bebf79d570651ff601788ea9d16d','Tlacojalpan','sesion','2014-09-29 17:39:32'),('a50abba8132a77191791390c3eb19fe7','San José del Rincón','sesion','2014-09-29 17:39:05'),('a516a87cfcaef229b342c437fe2b95f7','Cuyoaco','sesion','2014-09-29 17:38:34'),('a51c896c9cb81ecb5a199d51ac9fc3c5','Santiago Astata','sesion','2014-09-29 17:39:19'),('a51fb975227d6640e4fe47854476d133','San Francisco de los Romo','sesion','2014-09-29 17:39:04'),('a532400ed62e772b9dc0b86f46e583ff','Huamantla','sesion','2014-09-29 17:38:39'),('a5771bce93e200c36f7cd9dfd0e5deaa','Ahome','sesion','2014-09-29 17:38:17'),('a58149d355f02887dfbe55ebb2b64ba3','San Agustín Chayuco','sesion','2014-09-29 17:39:00'),('a588a6199feff5ba48402883d9b72700','Santa María Yucuhiti','sesion','2014-09-29 17:39:18'),('a591024321c5e2bdbd23ed35f0574dde','Tetiz','sesion','2014-09-29 17:39:30'),('a597e50502f5ff68e3e25b9114205d4a','Bácum','sesion','2014-09-29 17:38:23'),('a5a61717dddc3501cfdf7a4e22d7dbaa','Tinum','sesion','2014-09-29 17:39:31'),('a5bfc9e07964f8dddeb95fc584cd965d','Aguililla','sesion','2014-09-29 17:38:17'),('a5cdd4aa0048b187f7182f1b9ce7a6a7','Iztacalco','sesion','2014-09-29 17:38:43'),('a5e00132373a7031000fd987a3c9f87b','Atitalaquia','sesion','2014-09-29 17:38:21'),('a5e0ff62be0b08456fc7f1e88812af3d','Papantla','sesion','2014-09-29 17:38:56'),('a60937eba57758ed45b6d3e91e8659f3','San Lorenzo','sesion','2014-09-29 17:39:08'),('a63fc8c5d915e1f1a40f40e6c7499863','Santiago Tenango','sesion','2014-09-29 17:39:20'),('a64c94baaf368e1840a1324e839230de','Mariano Escobedo','sesion','2014-09-29 17:38:49'),('a666587afda6e89aec274a3657558a27','Chiconcuac','sesion','2014-09-29 17:38:28'),('a67f096809415ca1c9f112d96d27689b','López','sesion','2014-09-29 17:38:47'),('a684eceee76fc522773286a895bc8436','Albino Zertuche','sesion','2014-09-29 17:38:18'),('a733fa9b25f33689e2adbe72199f0e62','Heroica Villa Tezoatlán de Segura y Luna','sesion','2014-09-29 17:38:39'),('a760880003e7ddedfef56acb3b09697f','Ecatzingo','sesion','2014-09-29 17:38:35'),('a7aeed74714116f3b292a982238f83d2','General Cepeda','sesion','2014-09-29 17:38:37'),('a7d8ae4569120b5bec12e7b6e9648b86','Río Grande','sesion','2014-09-29 17:38:59'),('a8240cb8235e9c493a0c30607586166c','Querétaro','sesion','2014-09-29 17:38:58'),('a82d922b133be19c1171534e6594f754','Tepeaca','sesion','2014-09-29 17:39:29'),('a869ccbcbd9568808b8497e28275c7c8','Tumbalá','sesion','2014-09-29 17:39:35'),('a86c450b76fb8c371afead6410d55534','Gabriel Zamora','sesion','2014-09-29 17:38:37'),('a87ff679a2f3e71d9181a67b7542122c','Acacoyagua','sesion','2014-09-29 17:38:16'),('a8849b052492b5106526b2331e526138','General Escobedo','sesion','2014-09-29 17:38:37'),('a89cf525e1d9f04d16ce31165e139a4b','Opodepe','sesion','2014-09-29 17:38:55'),('a8abb4bb284b5b27aa7cb790dc20f80b','Coxcatlán','sesion','2014-09-29 17:38:32'),('a8baa56554f96369ab93e4f3bb068c22','Atengo','sesion','2014-09-29 17:38:21'),('a8c88a0055f636e4a163a5e3d16adab7','Charapan','sesion','2014-09-29 17:38:27'),('a8e864d04c95572d1aece099af852d0a','Jonotla','sesion','2014-09-29 17:38:45'),('a8ecbabae151abacba7dbde04f761c37','Kopomá','sesion','2014-09-29 17:38:46'),('a8f15eda80c50adb0e71943adc8015cf','Atlamajalcingo del Monte','sesion','2014-09-29 17:38:21'),('a8f8f60264024dca151f164729b76c0b','San Martín Chalchicuautla','sesion','2014-09-29 17:39:09'),('a9078e8653368c9c291ae2f8b74012e7','San Antonio Cañada','sesion','2014-09-29 17:39:02'),('a941493eeea57ede8214fd77d41806bc','Santiago Apóstol','sesion','2014-09-29 17:39:18'),('a96b65a721e561e1e3de768ac819ffbb','Concepción de Buenos Aires','sesion','2014-09-29 17:38:31'),('a96d3afec184766bfeca7a9f989fc7e7','Tlahualilo','sesion','2014-09-29 17:39:32'),('a97da629b098b75c294dffdc3e463904','Apetatitlán de Antonio Carvajal','sesion','2014-09-29 17:38:20'),('a9813e9550fee3110373c21fa012eee7','Tlacotepec de Benito Juárez','sesion','2014-09-29 17:39:32'),('a981f2b708044d6fb4a71a1463242520','Santa Cruz Itundujia','sesion','2014-09-29 17:39:15'),('a9a1d5317a33ae8cef33961c34144f84','General Terán','sesion','2014-09-29 17:38:37'),('a9a6653e48976138166de32772b1bf40','Fortín','sesion','2014-09-29 17:38:36'),('a9b7ba70783b617e9998dc4dd82eb3c5','Naolinco','sesion','2014-09-29 17:38:53'),('a9be4c2a4041cadbf9d61ae16dd1389e','San Juan Bautista Suchitepec','sesion','2014-09-29 17:39:06'),('a9eb812238f753132652ae09963a05e9','San Antonino Monte Verde','sesion','2014-09-29 17:39:01'),('aa169b49b583a2b5af89203c2b78c67c','Martínez de la Torre','sesion','2014-09-29 17:38:49'),('aa2a77371374094fe9e0bc1de3f94ed9','Solosuchiapa','sesion','2014-09-29 17:39:23'),('aa486f25175cbdc3854151288a645c19','San Pedro Huamelula','sesion','2014-09-29 17:39:12'),('aa68c75c4a77c87f97fb686b2f068676','Natividad','sesion','2014-09-29 17:38:53'),('aa942ab2bfa6ebda4840e7360ce6e7ef','Chontla','sesion','2014-09-29 17:38:29'),('aab3238922bcc25a6f606eb525ffdc56','Acatepec','sesion','2014-09-29 17:38:16'),('aace49c7d80767cffec0e513ae886df0','Parras','sesion','2014-09-29 17:38:57'),('ab1a4d0dd4d48a2ba1077c4494791306','San Dionisio del Mar','sesion','2014-09-29 17:39:03'),('ab233b682ec355648e7891e66c54191b','Huazalingo','sesion','2014-09-29 17:38:40'),('ab2b41c63853f0a651ba9fbf502b0cd8','Santiago Laollaga','sesion','2014-09-29 17:39:19'),('ab541d874c7bc19ab77642849e02b89f','San Miguel Mixtepec','sesion','2014-09-29 17:39:10'),('ab7314887865c4265e896c6e209d1cd6','Tecuala','sesion','2014-09-29 17:39:27'),('ab817c9349cf9c4f6877e1894a1faa00','Cuaxomulco','sesion','2014-09-29 17:38:33'),('ab88b15733f543179858600245108dd8','Lagunillas','sesion','2014-09-29 17:38:47'),('aba3b6fd5d186d28e06ff97135cade7f','Ixtaczoquitlán','sesion','2014-09-29 17:38:43'),('abd815286ba1007abfbb8415b83ae2cf','Hopelchén','sesion','2014-09-29 17:38:39'),('abdbeb4d8dbe30df8430a8394b7218ef','Trinidad Zaachila','sesion','2014-09-29 17:39:34'),('abea47ba24142ed16b7d8fbf2c740e0d','Quitupan','sesion','2014-09-29 17:38:59'),('ac1dd209cbcc5e5d1c6e28598e8cbbe8','Calpulalpan','sesion','2014-09-29 17:38:24'),('ac5dab2e99eee9cf9ec672e383691302','Santo Tomás Ocotepec','sesion','2014-09-29 17:39:22'),('ac627ab1ccbdb62ec96e702f07f6425b','Antiguo Morelos','sesion','2014-09-29 17:38:19'),('ac796a52db3f16bbdb6557d3d89d1c5a','San Miguel de Allende','sesion','2014-09-29 17:39:10'),('acab0116c354964a558e65bdd07ff047','Santiago de Anaya','sesion','2014-09-29 17:39:19'),('acc3e0404646c57502b480dc052c4fe1','Granados','sesion','2014-09-29 17:38:38'),('acf4b89d3d503d8252c9c4ba75ddbf6d','Mariscala de Juárez','sesion','2014-09-29 17:38:49'),('ad13a2a07ca4b7642959dc0c4c740ab6','Chiapa de Corzo','sesion','2014-09-29 17:38:27'),('ad3019b856147c17e82a5bead782d2a8','San Juan Lajarcia','sesion','2014-09-29 17:39:07'),('ad4cc1fb9b068faecfb70914acc63395','Tenancingo','sesion','2014-09-29 17:39:28'),('ad61ab143223efbc24c7d2583be69251','Álvaro Obregón','sesion','2014-09-29 17:38:18'),('ad71c82b22f4f65b9398f76d8be4c615','San Mateo Yucutindó','sesion','2014-09-29 17:39:10'),('ad972f10e0800b49d76fed33a21f6698','Coacalco de Berriozábal','sesion','2014-09-29 17:38:30'),('addfa9b7e234254d26e9c7f2af1005cb','Llera','sesion','2014-09-29 17:38:47'),('ae0eb3eed39d2bcef4622b2499a05fe6','Ixhuacán de los Reyes','sesion','2014-09-29 17:38:42'),('ae1eaa32d10b6c886981755d579fb4d8','Zapotitlán de Vadillo','sesion','2014-09-29 17:39:41'),('ae5e3ce40e0404a45ecacaaf05e5f735','Salinas','sesion','2014-09-29 17:39:00'),('ae614c557843b1df326cb29c57225459','Santa María Tataltepec','sesion','2014-09-29 17:39:18'),('aeb3135b436aa55373822c010763dd54','Magdalena','sesion','2014-09-29 17:38:48'),('aebf7782a3d445f43cf30ee2c0d84dee','Tlacotalpan','sesion','2014-09-29 17:39:32'),('af21d0c97db2e27e13572cbf59eb343d','Paso del Macho','sesion','2014-09-29 17:38:57'),('af3303f852abeccd793068486a391626','Tecámac','sesion','2014-09-29 17:39:26'),('af4732711661056eadbf798ba191272a','San Juan Atepec','sesion','2014-09-29 17:39:06'),('af5afd7f7c807171981d443ad4f4f648','San Sebastián Río Hondo','sesion','2014-09-29 17:39:14'),('afd4836712c5e77550897e25711e1d96','Irimbo','sesion','2014-09-29 17:38:42'),('afda332245e2af431fb7b672a68b659d','La Magdalena Tlatlauquitepec','sesion','2014-09-29 17:38:46'),('afdec7005cc9f14302cd0474fd0f3c96','Nuevo Casas Grandes','sesion','2014-09-29 17:38:54'),('afe434653a898da20044041262b3ac74','San Martín Lachilá','sesion','2014-09-29 17:39:09'),('aff0a6a4521232970b2c1cf539ad0a19','San Pedro Ocotepec','sesion','2014-09-29 17:39:13'),('aff1621254f7c1be92f64550478c56e6','Libres','sesion','2014-09-29 17:38:47'),('b056eb1587586b71e2da9acfe4fbd19e','Jalpa de Méndez','sesion','2014-09-29 17:38:43'),('b069b3415151fa7217e870017374de7c','San Luis Río Colorado','sesion','2014-09-29 17:39:09'),('b0b183c207f46f0cca7dc63b2604f5cc','Lamadrid','sesion','2014-09-29 17:38:47'),('b0b79da57b95837f14be95aaa4d54cf8','Xilitla','sesion','2014-09-29 17:39:39'),('b0df2270be9cb16c14537e5bc2f2d37b','Villagrán','sesion','2014-09-29 17:39:39'),('b0f2ad44d26e1a6f244201fe0fd864d1','Santa María Guelacé','sesion','2014-09-29 17:39:17'),('b132ecc1609bfcf302615847c1caa69a','San Pedro Yaneri','sesion','2014-09-29 17:39:13'),('b137fdd1f79d56c7edf3365fea7520f2','Jalpan','sesion','2014-09-29 17:38:43'),('b139e104214a08ae3f2ebcce149cdf6e','Temascalapa','sesion','2014-09-29 17:39:27'),('b147a61c1d07c1c999560f62add6dbc7','Santa María Atzompa','sesion','2014-09-29 17:39:16'),('b1563a78ec59337587f6ab6397699afc','Palmillas','sesion','2014-09-29 17:38:56'),('b166b57d195370cd41f80dd29ed523d9','Xocchel','sesion','2014-09-29 17:39:39'),('b197ffdef2ddc3308584dce7afa3661b','San Martín Hidalgo','sesion','2014-09-29 17:39:09'),('b1a59b315fc9a3002ce38bbe070ec3f5','Cárdenas','sesion','2014-09-29 17:38:25'),('b1d10e7bafa4421218a51b1e1f1b0ba2','Berriozábal','sesion','2014-09-29 17:38:23'),('b1eec33c726a60554bc78518d5f9b32c','Isla Mujeres','sesion','2014-09-29 17:38:42'),('b20bb95ab626d93fd976af958fbc61ba','Sacramento','sesion','2014-09-29 17:39:00'),('b24d516bb65a5a58079f0f3526c87c57','San Andrés Huaxpaltepec','sesion','2014-09-29 17:39:01'),('b2531e7bb29bf22e1daae486fae3417a','Santa María de los Ángeles','sesion','2014-09-29 17:39:17'),('b265ce60fe4c5384e622b09eb829b8df','San Martín Peras','sesion','2014-09-29 17:39:09'),('b29eed44276144e4e8103a661f9a78b7','Santiago Juxtlahuaca','sesion','2014-09-29 17:39:19'),('b2ab001909a8a6f04b51920306046ce5','Tlilapan','sesion','2014-09-29 17:39:33'),('b2dd140336c9df867c087a29b2e66034','San Pedro Tlaquepaque','sesion','2014-09-29 17:39:13'),('b2eb7349035754953b57a32e2841bda5','Chapultenango','sesion','2014-09-29 17:38:27'),('b2eeb7362ef83deff5c7813a67e14f0a','Guadalajara','sesion','2014-09-29 17:38:38'),('b2f627fff19fda463cb386442eac2b3d','Guadalupe Victoria','sesion','2014-09-29 17:38:38'),('b337e84de8752b27eda3a12363109e80','Dzidzantún','sesion','2014-09-29 17:38:34'),('b3967a0e938dc2a6340e258630febd5a','Chilpancingo de los Bravo','sesion','2014-09-29 17:38:28'),('b3b43aeeacb258365cc69cdaf42a68af','Santiago Minas','sesion','2014-09-29 17:39:20'),('b3b4d2dbedc99fe843fd3dedb02f086f','Tepetlixpa','sesion','2014-09-29 17:39:29'),('b3ba8f1bee1238a2f37603d90b58898d','San Antonio Acutla','sesion','2014-09-29 17:39:02'),('b3bbccd6c008e727785cb81b1aa08ac5','Santiago Tamazola','sesion','2014-09-29 17:39:20'),('b3e3e393c77e35a4a3f3cbd1e429b5dc','Atlapexco','sesion','2014-09-29 17:38:21'),('b4288d9c0ec0a1841b3b3728321e7088','Huehuetlán','sesion','2014-09-29 17:38:40'),('b432f34c5a997c8e7c806a895ecc5e25','Tekal de Venegas','sesion','2014-09-29 17:39:27'),('b440509a0106086a67bc2ea9df0a1dab','Villa de Allende','sesion','2014-09-29 17:39:37'),('b4568df26077653eeadf29596708c94b','Tapalapa','sesion','2014-09-29 17:39:26'),('b495ce63ede0f4efc9eec62cb947c162','San Bartolo Yautepec','sesion','2014-09-29 17:39:02'),('b4a528955b84f584974e92d025a75d1f','Isla','sesion','2014-09-29 17:38:42'),('b4d168b48157c623fbd095b4a565b5bb','Omitlán de Juárez','sesion','2014-09-29 17:38:55'),('b51a15f382ac914391a58850ab343b00','San Cristóbal Amatlán','sesion','2014-09-29 17:39:03'),('b534ba68236ba543ae44b22bd110a1d6','Doctor Mora','sesion','2014-09-29 17:38:34'),('b53b3a3d6ab90ce0268229151c9bde11','Alcozauca de Guerrero','sesion','2014-09-29 17:38:18'),('b5488aeff42889188d03c9895255cecc','San Pablo Anicano','sesion','2014-09-29 17:39:11'),('b55ec28c52d5f6205684a473a2193564','Miacatlán','sesion','2014-09-29 17:38:51'),('b56a18e0eacdf51aa2a5306b0f533204','Matlapa','sesion','2014-09-29 17:38:49'),('b571ecea16a9824023ee1af16897a582','Samahil','sesion','2014-09-29 17:39:00'),('b59a51a3c0bf9c5228fde841714f523a','Santa María Tepantlali','sesion','2014-09-29 17:39:18'),('b59c67bf196a4758191e42f76670ceba','Pénjamo','sesion','2014-09-29 17:38:57'),('b5a1fc2085986034e448d2ccc5bb9703','San Pedro Juchatengo','sesion','2014-09-29 17:39:12'),('b5b41fac0361d157d9673ecb926af5ae','Dzán','sesion','2014-09-29 17:38:34'),('b5c01503041b70d41d80e3dbe31bbd8c','Villaflores','sesion','2014-09-29 17:39:38'),('b5dc4e5d9b495d0196f61d45b26ef33e','Huetamo','sesion','2014-09-29 17:38:40'),('b5f1e8fb36cd7fbeb7988e8639ac79e9','Tlalnepantla','sesion','2014-09-29 17:39:32'),('b60c5ab647a27045b462934977ccad9a','San Pedro Mártir','sesion','2014-09-29 17:39:12'),('b618c3210e934362ac261db280128c22','Santa María Quiegolani','sesion','2014-09-29 17:39:18'),('b6a1085a27ab7bff7550f8a3bd017df8','Marqués de Comillas','sesion','2014-09-29 17:38:49'),('b6d767d2f8ed5d21a44b0e5886680cb9','Acolman','sesion','2014-09-29 17:38:17'),('b6e32320fa6bc5a588b90183b95dc028','Xochihuehuetlán','sesion','2014-09-29 17:39:40'),('b6edc1cd1f36e45daf6d7824d7bb2283','La Barca','sesion','2014-09-29 17:38:46'),('b6f0479ae87d244975439c6124592772','Copalillo','sesion','2014-09-29 17:38:31'),('b706835de79a2b4e80506f582af3676a','Nanchital de Lázaro Cárdenas del Río','sesion','2014-09-29 17:38:53'),('b7087c1f4f89e63af8d46f3b20271153','San Juan Teita','sesion','2014-09-29 17:39:08'),('b710915795b9e9c02cf10d6d2bdb688c','San Simón de Guerrero','sesion','2014-09-29 17:39:14'),('b73ce398c39f506af761d2277d853a92','Atotonilco de Tula','sesion','2014-09-29 17:38:22'),('b73dfe25b4b8714c029b37a6ad3006fa','Heroica Ciudad de Huajuapan de León','sesion','2014-09-29 17:38:39'),('b7892fb3c2f009c65f686f6355c895b5','Mexquitic de Carmona','sesion','2014-09-29 17:38:50'),('b7b16ecf8ca53723593894116071700c','Colipa','sesion','2014-09-29 17:38:30'),('b7bb35b9c6ca2aee2df08cf09d7016c2','Honey','sesion','2014-09-29 17:38:39'),('b7ee6f5f9aa5cd17ca1aea43ce848496','Jiquipilas','sesion','2014-09-29 17:38:44'),('b83aac23b9528732c23cc7352950e880','Chiconcuautla','sesion','2014-09-29 17:38:28'),('b865367fc4c0845c0682bd466e6ebf4c','Santo Domingo Tehuantepec','sesion','2014-09-29 17:39:21'),('b86e8d03fe992d1b0e19656875ee557c','Las Rosas','sesion','2014-09-29 17:38:47'),('b8b4b727d6f5d1b61fff7be687f7970f','Tetla de la Solidaridad','sesion','2014-09-29 17:39:30'),('b8c27b7a1c450ffdacb31483454e0b54','San Juan Atzompa','sesion','2014-09-29 17:39:06'),('b8c37e33defde51cf91e1e03e51657da','Naranjal','sesion','2014-09-29 17:38:53'),('b9141aff1412dc76340b3822d9ea6c72','Ocotlán','sesion','2014-09-29 17:38:54'),('b9228e0962a78b84f3d5d92f4faa000b','Concordia','sesion','2014-09-29 17:38:31'),('b9d487a30398d42ecff55c228ed5652b','Pedro Escobedo','sesion','2014-09-29 17:38:57'),('b9f94c77652c9a76fc8a442748cd54bd','Santa María Yolotepec','sesion','2014-09-29 17:39:18'),('ba1b3eba322eab5d895aa3023fe78b9c','Santiago Tillo','sesion','2014-09-29 17:39:20'),('ba2fd310dcaa8781a9a652a31baf3c68','El Limón','sesion','2014-09-29 17:38:35'),('ba3866600c3540f67c1e9575e213be0a','Ixtlahuacán del Río','sesion','2014-09-29 17:38:43'),('ba9a56ce0a9bfa26e8ed9e10b2cc8f46','Tanlajás','sesion','2014-09-29 17:39:26'),('bac9162b47c56fc8a4d2a519803d51b3','Ciénega de Flores','sesion','2014-09-29 17:38:29'),('bad5f33780c42f2588878a9d07405083','San Juan de Sabinas','sesion','2014-09-29 17:39:07'),('bb04af0f7ecaee4aae62035497da1387','San Diego de la Unión','sesion','2014-09-29 17:39:03'),('bb1662b7c5f22a0f905fd59e718ca05e','Venado','sesion','2014-09-29 17:39:37'),('bb7946e7d85c81a9e69fee1cea4a087c','San Jacinto Amilpas','sesion','2014-09-29 17:39:05'),('bbaa9d6a1445eac881750bea6053f564','Yécora','sesion','2014-09-29 17:39:40'),('bbcbff5c1f1ded46c25d28119a85c6c2','Comondú','sesion','2014-09-29 17:38:31'),('bbf94b34eb32268ada57a3be5062fe7d','Comapa','sesion','2014-09-29 17:38:31'),('bc573864331a9e42e4511de6f678aa83','Santa Cruz Mixtepec','sesion','2014-09-29 17:39:15'),('bc6dc48b743dc5d013b1abaebd2faed2','Chicoloapan','sesion','2014-09-29 17:38:28'),('bc7316929fe1545bf0b98d114ee3ecb8','San Pablo Villa de Mitla','sesion','2014-09-29 17:39:12'),('bca82e41ee7b0833588399b1fcd177c7','Coapilla','sesion','2014-09-29 17:38:30'),('bcb41ccdc4363c6848a1d760f26c28a0','Santa Ana','sesion','2014-09-29 17:39:14'),('bcbe3365e6ac95ea2c0343a2395834dd','Cabo Corrientes','sesion','2014-09-29 17:38:24'),('bcc0d400288793e8bdcd7c19a8ac0c2b','San José Miahuatlán','sesion','2014-09-29 17:39:05'),('bd0cc810b580b35884bd9df37c0e8b0f','Valle de Juárez','sesion','2014-09-29 17:39:36'),('bd4c9ab730f5513206b999ec0d90d1fb','Atotonilco el Alto','sesion','2014-09-29 17:38:22'),('bd5af7cd922fd2603be4ee3dc43b0b77','Tultepec','sesion','2014-09-29 17:39:35'),('bd686fd640be98efaae0091fa301e613','Bacoachi','sesion','2014-09-29 17:38:23'),('bdb106a0560c4e46ccc488ef010af787','Nuevo Morelos','sesion','2014-09-29 17:38:54'),('bdc4626aa1d1df8e14d80d345b2a442d','Totontepec Villa de Morelos','sesion','2014-09-29 17:39:34'),('be3159ad04564bfb90db9e32851ebf9c','Huejotzingo','sesion','2014-09-29 17:38:40'),('be3e9d3f7d70537357c67bb3f4086846','Santa María Alotepec','sesion','2014-09-29 17:39:16'),('be53ee61104935234b174e62a07e53cf','San Raymundo Jalpan','sesion','2014-09-29 17:39:13'),('be6c7b094f88532b6c6b35bbcd525ee8','Xonacatlán','sesion','2014-09-29 17:39:40'),('be83ab3ecd0db773eb2dc1b0a17836a1','Calihualá','sesion','2014-09-29 17:38:24'),('bea5955b308361a1b07bc55042e25e54','Mecayapan','sesion','2014-09-29 17:38:50'),('beb22fb694d513edcf5533cf006dfeae','Juchipila','sesion','2014-09-29 17:38:45'),('beed13602b9b0e6ecb5b568ff5058f07','Coatepec Harinas','sesion','2014-09-29 17:38:30'),('bf201d5407a6509fa536afc4b380577e','Santo Domingo Yanhuitlán','sesion','2014-09-29 17:39:21'),('bf424cb7b0dea050a42b9739eb261a3a','Tlaxco','sesion','2014-09-29 17:39:33'),('bf62768ca46b6c3b5bea9515d1a1fc45','Jantetelco','sesion','2014-09-29 17:38:44'),('bf8229696f7a3bb4700cfddef19fa23f','Axtla de Terrazas','sesion','2014-09-29 17:38:22'),('bffc98347ee35b3ead06728d6f073c68','San Miguel Panixtlahuaca','sesion','2014-09-29 17:39:10'),('c042f4db68f23406c6cecf84a7ebb0fe','Chigmecatitlán','sesion','2014-09-29 17:38:28'),('c0560792e4a3c79e62f76cbf9fb277dd','Santa María Colotepec','sesion','2014-09-29 17:39:17'),('c058f544c737782deacefa532d9add4c','Chucándiro','sesion','2014-09-29 17:38:29'),('c06d06da9666a219db15cf575aff2824','Ignacio Zaragoza','sesion','2014-09-29 17:38:41'),('c0826819636026dd1f3674774f06c51d','San Juan Lachigalla','sesion','2014-09-29 17:39:07'),('c0a271bc0ecb776a094786474322cb82','San Felipe Orizatlán','sesion','2014-09-29 17:39:03'),('c0c7c76d30bd3dcaefc96f40275bdc0a','Akil','sesion','2014-09-29 17:38:18'),('c0d0e461de8d0024aebcb0a7c68836df','Santiago Llano Grande','sesion','2014-09-29 17:39:19'),('c0e190d8267e36708f955d7ab048990d','Buenavista de Cuéllar','sesion','2014-09-29 17:38:24'),('c0f168ce8900fa56e57789e2a2f2c9d0','Jocotepec','sesion','2014-09-29 17:38:44'),('c15da1f2b5e5ed6e6837a3802f0d1593','Juan Aldama','sesion','2014-09-29 17:38:45'),('c164bbc9d6c72a52c599bbb43d8db8e1','Tancanhuitz','sesion','2014-09-29 17:39:25'),('c16a5320fa475530d9583c34fd356ef5','Acuña','sesion','2014-09-29 17:38:17'),('c182f930a06317057d31c73bb2fedd4f','Tzimol','sesion','2014-09-29 17:39:35'),('c1e39d912d21c91dce811d6da9929ae8','San Cristóbal Lachirioag','sesion','2014-09-29 17:39:03'),('c1fea270c48e8079d8ddf7d06d26ab52','Santa Isabel Xiloxoxtla','sesion','2014-09-29 17:39:16'),('c203d8a151612acf12457e4d67635a95','Coyame del Sotol','sesion','2014-09-29 17:38:32'),('c20ad4d76fe97759aa27a0c99bff6710','Acapulco de Juárez','sesion','2014-09-29 17:38:16'),('c21002f464c5fc5bee3b98ced83963b8','Pinal de Amoles','sesion','2014-09-29 17:38:57'),('c215b446bcdf956d848a8419c1b5a920','Tepango de Rodríguez','sesion','2014-09-29 17:39:29'),('c22abfa379f38b5b0411bc11fa9bf92f','Monclova','sesion','2014-09-29 17:38:52'),('c24cd76e1ce41366a4bbe8a49b02a028','Canelas','sesion','2014-09-29 17:38:25'),('c2626d850c80ea07e7511bbae4c76f4b','La Piedad','sesion','2014-09-29 17:38:46'),('c26820b8a4c1b3c2aa868d6d57e14a79','San Juan Bautista Coixtlahuaca','sesion','2014-09-29 17:39:06'),('c2aee86157b4a40b78132f1e71a9e6f1','Manzanillo','sesion','2014-09-29 17:38:49'),('c2ba1bc54b239208cb37b901c0d3b363','Yauhquemehcan','sesion','2014-09-29 17:39:40'),('c32d9bf27a3da7ec8163957080c8628e','Morelos','sesion','2014-09-29 17:38:52'),('c3395dd46c34fa7fd8d729d8cf88b7a8','Tenochtitlán','sesion','2014-09-29 17:39:28'),('c3535febaff29fcb7c0d20cbe94391c7','Totatiche','sesion','2014-09-29 17:39:34'),('c3614206a443012045cfd75d2600af2d','Zapotitlán Tablas','sesion','2014-09-29 17:39:41'),('c361bc7b2c033a83d663b8d9fb4be56e','Guasave','sesion','2014-09-29 17:38:38'),('c366c2c97d47b02b24c3ecade4c40a01','Tarímbaro','sesion','2014-09-29 17:39:26'),('c3992e9a68c5ae12bd18488bc579b30d','Guadalupe y Calvo','sesion','2014-09-29 17:38:38'),('c399862d3b9d6b76c8436e924a68c45b','El Tule','sesion','2014-09-29 17:38:35'),('c3c59e5f8b3e9753913f4d435b53c308','Cuzamá','sesion','2014-09-29 17:38:34'),('c3e0c62ee91db8dc7382bde7419bb573','Praxedis G. Guerrero','sesion','2014-09-29 17:38:58'),('c3e4035af2a1cde9f21e1ae1951ac80b','Valerio Trujano','sesion','2014-09-29 17:39:36'),('c3e878e27f52e2a57ace4d9a76fd9acf','Churintzio','sesion','2014-09-29 17:38:29'),('c4015b7f368e6b4871809f49debe0579','Mecatlán','sesion','2014-09-29 17:38:50'),('c410003ef13d451727aeff9082c29a5c','Del Nayar','sesion','2014-09-29 17:38:34'),('c44799b04a1c72e3c8593a53e8000c78','Tehuitzingo','sesion','2014-09-29 17:39:27'),('c4492cbe90fbdbf88a5aec486aa81ed5','Santiago Laxopa','sesion','2014-09-29 17:39:19'),('c44e503833b64e9f27197a484f4257c0','Sabinas','sesion','2014-09-29 17:39:00'),('c45008212f7bdf6eab6050c2a564435a','Tapilula','sesion','2014-09-29 17:39:26'),('c45147dee729311ef5b5c3003946c48f','Aquixtla','sesion','2014-09-29 17:38:20'),('c4851e8e264415c4094e4e85b0baa7cc','San Andrés Solaga','sesion','2014-09-29 17:39:01'),('c4b31ce7d95c75ca70d50c19aef08bf1','Miguel Alemán','sesion','2014-09-29 17:38:51'),('c4ca4238a0b923820dcc509a6f75849b','Abalá','sesion','2014-09-29 17:38:16'),('c4de8ced6214345614d33fb0b16a8acd','Tepehuanes','sesion','2014-09-29 17:39:29'),('c51ce410c124a10e0db5e4b97fc2af39','Acateno','sesion','2014-09-29 17:38:16'),('c52f1bd66cc19d05628bd8bf27af3ad6','Cañitas de Felipe Pescador','sesion','2014-09-29 17:38:25'),('c54e7837e0cd0ced286cb5995327d1ab','Salinas Victoria','sesion','2014-09-29 17:39:00'),('c559da2ba967eb820766939a658022c8','Santa Bárbara','sesion','2014-09-29 17:39:15'),('c5866e93cab1776890fe343c9e7063fb','Tomatlán','sesion','2014-09-29 17:39:34'),('c59b469d724f7919b7d35514184fdc0f','Santiago','sesion','2014-09-29 17:39:18'),('c5a4e7e6882845ea7bb4d9462868219b','Tequila','sesion','2014-09-29 17:39:30'),('c5ab0bc60ac7929182aadd08703f1ec6','Hualahuises','sesion','2014-09-29 17:38:39'),('c5b2cebf15b205503560c4e8e6d1ea78','Tetela de Ocampo','sesion','2014-09-29 17:39:30'),('c5cc17e395d3049b03e0f1ccebb02b4d','San Martín Zacatepec','sesion','2014-09-29 17:39:09'),('c5ff2543b53f4cc0ad3819a36752467b','China','sesion','2014-09-29 17:38:28'),('c6036a69be21cb660499b75718a3ef24','Pichucalco','sesion','2014-09-29 17:38:57'),('c60d060b946d6dd6145dcbad5c4ccf6f','Peto','sesion','2014-09-29 17:38:57'),('c6335734dbc0b1ded766421cfc611750','Tonayán','sesion','2014-09-29 17:39:34'),('c667d53acd899a97a85de0c201ba99be','Parás','sesion','2014-09-29 17:38:57'),('c6bff625bdb0393992c9d4db0c6bbe45','Paraíso','sesion','2014-09-29 17:38:56'),('c6e19e830859f2cb9f7c8f8cacb8d2a6','General Heliodoro Castillo','sesion','2014-09-29 17:38:37'),('c70daf247944fe3add32218f914c75a6','San Javier','sesion','2014-09-29 17:39:05'),('c73dfe6c630edb4c1692db67c510f65c','San José Lachiguiri','sesion','2014-09-29 17:39:05'),('c74d97b01eae257e44aa9d5bade97baf','Acatlán','sesion','2014-09-29 17:38:16'),('c75b6f114c23a4d7ea11331e7c00e73c','Escobedo','sesion','2014-09-29 17:38:36'),('c7635bfd99248a2cdef8249ef7bfbef4','Pilcaya','sesion','2014-09-29 17:38:57'),('c7af0926b294e47e52e46cfebe173f20','Santa Cruz Amilpas','sesion','2014-09-29 17:39:15'),('c7e1249ffc03eb9ded908c236bd1996d','Amealco de Bonfil','sesion','2014-09-29 17:38:19'),('c81e728d9d4c2f636f067f89cc14862c','Abasolo','sesion','2014-09-29 17:38:16'),('c850371fda6892fbfd1c5a5b457e5777','San Cristóbal de las Casas','sesion','2014-09-29 17:39:03'),('c86a7ee3d8ef0b551ed58e354a836f2b','Cocotitlán','sesion','2014-09-29 17:38:30'),('c8758b517083196f05ac29810b924aca','Texcoco','sesion','2014-09-29 17:39:30'),('c88d8d0a6097754525e02c2246d8d27f','San Pedro Sochiápam','sesion','2014-09-29 17:39:13'),('c8ba76c279269b1c6bc8a07e38e78fa4','San Agustín Tlaxiaca','sesion','2014-09-29 17:39:01'),('c8c41c4a18675a74e01c8a20e8a0f662','Mier y Noriega','sesion','2014-09-29 17:38:51'),('c8cbd669cfb2f016574e9d147092b5bb','Santo Domingo Zanatepec','sesion','2014-09-29 17:39:22'),('c8ed21db4f678f3b13b9d5ee16489088','Ixtapa','sesion','2014-09-29 17:38:43'),('c8fbbc86abe8bd6a5eb6a3b4d0411301','Mascota','sesion','2014-09-29 17:38:49'),('c8ffe9a587b126f152ed3d89a146b445','Armería','sesion','2014-09-29 17:38:20'),('c902b497eb972281fb5b4e206db38ee6','Tunkás','sesion','2014-09-29 17:39:35'),('c913303f392ffc643f7240b180602652','San Melchor Betaza','sesion','2014-09-29 17:39:10'),('c91591a8d461c2869b9f535ded3e213e','Tecolutla','sesion','2014-09-29 17:39:27'),('c92a10324374fac681719d63979d00fe','Tijuana','sesion','2014-09-29 17:39:31'),('c930eecd01935feef55942cc445f708f','San Martín Tilcajete','sesion','2014-09-29 17:39:09'),('c9892a989183de32e976c6f04e700201','Galeana','sesion','2014-09-29 17:38:37'),('c9e1074f5b3f9fc8ea15d152add07294','Apaxco','sesion','2014-09-29 17:38:20'),('c9f0f895fb98ab9159f51fd0297e236d','Acambay de Ruíz Castañeda','sesion','2014-09-29 17:38:16'),('c9f95a0a5af052bffce5c89917335f67','Pátzcuaro','sesion','2014-09-29 17:38:57'),('ca0daec69b5adc880fb464895726dbdf','Villa de Cos','sesion','2014-09-29 17:39:37'),('ca460332316d6da84b08b9bcf39b687b','Taxco de Alarcón','sesion','2014-09-29 17:39:26'),('ca46c1b9512a7a8315fa3c5a946e8265','Bolaños','sesion','2014-09-29 17:38:23'),('ca75910166da03ff9d4655a0338e6b09','Monjas','sesion','2014-09-29 17:38:52'),('ca8155f4d27f205953f9d3d7974bdd70','Luis Moya','sesion','2014-09-29 17:38:48'),('ca9c267dad0305d1a6308d2a0cf1c39c','Huiramba','sesion','2014-09-29 17:38:41'),('caf1a3dfb505ffed0d024130f58c5cfa','Chichiquila','sesion','2014-09-29 17:38:28'),('cb70ab375662576bd1ac5aaf16b3fca4','Camerino Z. Mendoza','sesion','2014-09-29 17:38:24'),('cb79f8fa58b91d3af6c9c991f63962d3','Tekax','sesion','2014-09-29 17:39:27'),('cb8acb1dc9821bf74e6ca9068032d623','San Pedro Huilotepec','sesion','2014-09-29 17:39:12'),('cb8da6767461f2812ae4290eac7cbc42','Unión de San Antonio','sesion','2014-09-29 17:39:36'),('cbb6a3b884f4f88b3a8e3d44c636cbd8','Melchor Ocampo','sesion','2014-09-29 17:38:50'),('cbcb58ac2e496207586df2854b17995f','Fresnillo de Trujano','sesion','2014-09-29 17:38:37'),('cbef46321026d8404bc3216d4774c8a9','Tlanalapa','sesion','2014-09-29 17:39:33'),('cc1aa436277138f61cda703991069eaf','Monterrey','sesion','2014-09-29 17:38:52'),('cc42acc8ce334185e0193753adb6cb77','Santa María Chachoápam','sesion','2014-09-29 17:39:16'),('cc7e2b878868cbae992d1fb743995d8f','Zacualpan','sesion','2014-09-29 17:39:41'),('ccb0989662211f61edae2e26d58ea92f','Jamapa','sesion','2014-09-29 17:38:44'),('ccb1d45fb76f7c5a0bf619f979c6cf36','Escuintla','sesion','2014-09-29 17:38:36'),('ccbd8ca962b80445df1f7f38c57759f0','Yobaín','sesion','2014-09-29 17:39:40'),('ccc0aa1b81bf81e16c676ddb977c5881','Mazatlán Villa de Flores','sesion','2014-09-29 17:38:50'),('cd00692c3bfe59267d5ecfac5310286c','Chiconquiaco','sesion','2014-09-29 17:38:28'),('cd0cbcc668fe4bc58e0af3cc7e0a653d','Zacatlán','sesion','2014-09-29 17:39:41'),('cd0dce8fca267bf1fb86cf43e18d5598','San Juan Evangelista','sesion','2014-09-29 17:39:07'),('cd0f74b5955dc87fd0605745c4b49ee8','Zacapoaxtla','sesion','2014-09-29 17:39:41'),('cd14821dab219ea06e2fd1a2df2e3582','Santos Reyes Pápalo','sesion','2014-09-29 17:39:22'),('cd3afef9b8b89558cd56638c3631868a','Villa Hidalgo','sesion','2014-09-29 17:39:38'),('cd4bb35c75ba84b4f39e547b1416fd35','Tultitlán','sesion','2014-09-29 17:39:35'),('cd63a3eec3319fd9c84c942a08316e00','Tabasco','sesion','2014-09-29 17:39:24'),('cd758e8f59dfdf06a852adad277986ca','San Bartolomé Yucuañe','sesion','2014-09-29 17:39:02'),('cd89fef7ffdd490db800357f47722b20','Oquitoa','sesion','2014-09-29 17:38:55'),('cda72177eba360ff16b7f836e2754370','San Pedro Mártir Yucuxaco','sesion','2014-09-29 17:39:12'),('cdc0d6e63aa8e41c89689f54970bb35f','Heroica Ciudad de Ejutla de Crespo','sesion','2014-09-29 17:38:38'),('cdf1035c34ec380218a8cc9a43d438f9','Santa María Guienagati','sesion','2014-09-29 17:39:17'),('cdf1e288ca02272e717c9d5e4cb180bd','Villa de Etla','sesion','2014-09-29 17:39:37'),('ce5140df15d046a66883807d18d0264b','Nocupétaro','sesion','2014-09-29 17:38:54'),('ce6c92303f38d297e263c7180f03d402','Yanga','sesion','2014-09-29 17:39:40'),('ce78d1da254c0843eb23951ae077ff5f','La Reforma','sesion','2014-09-29 17:38:46'),('cec6f62cfb44b1be110b7bf70c8362d8','San Miguel Amatitlán','sesion','2014-09-29 17:39:10'),('ced556cd9f9c0c8315cfbe0744a3baf0','San Miguel Xoxtla','sesion','2014-09-29 17:39:11'),('cedebb6e872f539bef8c3f919874e9d7','Ayutla','sesion','2014-09-29 17:38:22'),('cee631121c2ec9232f3a2f028ad5c89b','Dr. Belisario Domínguez','sesion','2014-09-29 17:38:34'),('cee8d6b7ce52554fd70354e37bbf44a2','Teya','sesion','2014-09-29 17:39:30'),('cefab442b1728a7c1b49c63f1a55781c','Santiago Texcalcingo','sesion','2014-09-29 17:39:20'),('cf004fdc76fa1a4f25f62e0eb5261ca3','Citlaltépetl','sesion','2014-09-29 17:38:29'),('cf1f78fe923afe05f7597da2be7a3da8','San Juan Bautista Tuxtepec','sesion','2014-09-29 17:39:06'),('cf2226ddd41b1a2d0ae51dab54d32c36','Tocatlán','sesion','2014-09-29 17:39:33'),('cf67355a3333e6e143439161adc2d82e','El Espinal','sesion','2014-09-29 17:38:35'),('cf9a242b70f45317ffd281241fa66502','San Juan Bautista Lo de Soto','sesion','2014-09-29 17:39:06'),('cfa0860e83a4c3a763a7e62d825349f7','Carácuaro','sesion','2014-09-29 17:38:25'),('cfa5301358b9fcbe7aa45b1ceea088c6','San Nicolás','sesion','2014-09-29 17:39:11'),('cfbce4c1d7c425baf21d6b6f2babe6be','Manuel Doblado','sesion','2014-09-29 17:38:49'),('cfecdb276f634854f3ef915e2e980c31','Bacanora','sesion','2014-09-29 17:38:23'),('cfee398643cbc3dc5eefc89334cacdc1','Cuitzeo','sesion','2014-09-29 17:38:33'),('d010396ca8abf6ead8cacc2c2f2f26c7','San Pedro Mixtepec -Dto. 26 -','sesion','2014-09-29 17:39:12'),('d045c59a90d7587d8d671b5f5aec4e7c','Meoqui','sesion','2014-09-29 17:38:50'),('d072677d210ac4c03ba046120f0802ec','Tampamolón Corona','sesion','2014-09-29 17:39:25'),('d07e70efcfab08731a97e7b91be644de','Cuautempan','sesion','2014-09-29 17:38:33'),('d09bf41544a3365a46c9077ebb5e35c3','Amacueca','sesion','2014-09-29 17:38:19'),('d0fb963ff976f9c37fc81fe03c21ea7b','Tetepango','sesion','2014-09-29 17:39:30'),('d10ec7c16cbe9de8fbb1c42787c3ec26','San José Chiapa','sesion','2014-09-29 17:39:05'),('d14220ee66aeec73c49038385428ec4c','Ixil','sesion','2014-09-29 17:38:42'),('d18f655c3fce66ca401d5f38b48c89af','Culiacán','sesion','2014-09-29 17:38:33'),('d198bd736a97e7cecfdf8f4f2027ef80','Tiquicheo de Nicolás Romero','sesion','2014-09-29 17:39:31'),('d1a21da7bca4abff8b0b61b87597de73','Valparaíso','sesion','2014-09-29 17:39:37'),('d1a69640d53a32a9fb13e93d1c8f3104','San Pedro Lagunillas','sesion','2014-09-29 17:39:12'),('d1c38a09acc34845c6be3a127a5aacaf','Cacalchén','sesion','2014-09-29 17:38:24'),('d1dc3a8270a6f9394f88847d7f0050cf','Santa Clara','sesion','2014-09-29 17:39:15'),('d1e946f4e67db4b362ad23818a6fb78a','Saucillo','sesion','2014-09-29 17:39:22'),('d1ee59e20ad01cedc15f5118a7626099','San Juan Chilateca','sesion','2014-09-29 17:39:06'),('d1f255a373a3cef72e03aa9d980c7eca','Copainalá','sesion','2014-09-29 17:38:31'),('d1f491a404d6854880943e5c3cd9ca25','Asientos','sesion','2014-09-29 17:38:21'),('d1fe173d08e959397adf34b1d77e88d7','Amatenango de la Frontera','sesion','2014-09-29 17:38:19'),('d240e3d38a8882ecad8633c8f9c78c9b','Mitontic','sesion','2014-09-29 17:38:51'),('d25414405eb37dae1c14b18d6a2cac34','Santo Domingo Xagacía','sesion','2014-09-29 17:39:21'),('d282ef263719ab842e05382dc235f69e','San Francisco Ixhuatán','sesion','2014-09-29 17:39:04'),('d296c101daa88a51f6ca8cfc1ac79b50','Champotón','sesion','2014-09-29 17:38:27'),('d2a27e83d429f0dcae6b937cf440aeb1','Villamar','sesion','2014-09-29 17:39:39'),('d2cdf047a6674cef251d56544a3cf029','Santiago Textitlán','sesion','2014-09-29 17:39:20'),('d2ddea18f00665ce8623e36bd4e3c7c5','Alvarado','sesion','2014-09-29 17:38:18'),('d2ed45a52bc0edfa11c2064e9edee8bf','Metepec','sesion','2014-09-29 17:38:50'),('d305281faf947ca7acade9ad5c8c818c','Tancoco','sesion','2014-09-29 17:39:25'),('d30960ce77e83d896503d43ba249caf7','Tzucacab','sesion','2014-09-29 17:39:36'),('d34ab169b70c9dcd35e62896010cd9ff','Coahuayutla de José María Izazaga','sesion','2014-09-29 17:38:30'),('d38901788c533e8286cb6400b40b386d','San Antonio','sesion','2014-09-29 17:39:02'),('d395771085aab05244a4fb8fd91bf4ee','Celestún','sesion','2014-09-29 17:38:26'),('d3a7f48c12e697d50c8a7ae7684644ef','Tlaxcala','sesion','2014-09-29 17:39:33'),('d3d9446802a44259755d38e6d163e820','Acapetahua','sesion','2014-09-29 17:38:16'),('d43ab110ab2489d6b9b2caa394bf920f','Santa María Temaxcalapa','sesion','2014-09-29 17:39:18'),('d46e1fcf4c07ce4a69ee07e4134bcef1','Tepatlaxco','sesion','2014-09-29 17:39:29'),('d47268e9db2e9aa3827bba3afb7ff94a','Tetlatlahuca','sesion','2014-09-29 17:39:30'),('d490d7b4576290fa60eb31b5fc917ad1','Guadalupe Etla','sesion','2014-09-29 17:38:38'),('d4b2aeb2453bdadaa45cbe9882ffefcf','Tangancícuaro','sesion','2014-09-29 17:39:25'),('d4c2e4a3297fe25a71d030b67eb83bfc','Ixtepec','sesion','2014-09-29 17:38:43'),('d4dd111a4fd973394238aca5c05bebe3','Xochicoatlán','sesion','2014-09-29 17:39:40'),('d516b13671a4179d9b7b458a6ebdeb92','Magdalena Tlacotepec','sesion','2014-09-29 17:38:48'),('d51b416788b6ee70eb0c381c06efc9f1','Victoria','sesion','2014-09-29 17:39:37'),('d523773c6b194f37b938d340d5d02232','Taretan','sesion','2014-09-29 17:39:26'),('d542599794c1cf067d90638b5d3911f3','Ures','sesion','2014-09-29 17:39:36'),('d54e99a6c03704e95e6965532dec148b','Tecoanapa','sesion','2014-09-29 17:39:26'),('d554f7bb7be44a7267068a7df88ddd20','La Paz','sesion','2014-09-29 17:38:46'),('d56b9fc4b0f1be8871f5e1c40c0067e7','Mapastepec','sesion','2014-09-29 17:38:49'),('d58072be2820e8682c0a27c0518e805e','Kaua','sesion','2014-09-29 17:38:46'),('d5c186983b52c4551ee00f72316c6eaa','Tepoztlán','sesion','2014-09-29 17:39:30'),('d5cfead94f5350c12c322b5b664544c1','Ixtlahuacán','sesion','2014-09-29 17:38:43'),('d5e2c0adad503c91f91df240d0cd4e49','Yahualica de González Gallo','sesion','2014-09-29 17:39:40'),('d61e4bbd6393c9111e6526ea173a7c8b','Cozumel','sesion','2014-09-29 17:38:32'),('d6288499d0083cc34e60a077b7c4b3e1','Yaxcabá','sesion','2014-09-29 17:39:40'),('d63fbf8c3173730f82b150c5ef38b8ff','San Pedro Jicayán','sesion','2014-09-29 17:39:12'),('d645920e395fedad7bbbed0eca3fe2e0','Ahuacuotzingo','sesion','2014-09-29 17:38:17'),('d64a340bcb633f536d56e51874281454','El Plateado de Joaquín Amaro','sesion','2014-09-29 17:38:35'),('d6723e7cd6735df68d1ce4c704c29a04','Pánuco de Coronado','sesion','2014-09-29 17:38:56'),('d67d8ab4f4c10bf22aa353e27879133c','Ahuacatlán','sesion','2014-09-29 17:38:17'),('d68a18275455ae3eaa2c291eebb46e6d','Tepeyahualco de Cuauhtémoc','sesion','2014-09-29 17:39:29'),('d6baf65e0b240ce177cf70da146c8dc8','Carlos A. Carrillo','sesion','2014-09-29 17:38:25'),('d6bcb486f72ae7b5dc68b5b7df7ec887','Zapotiltic','sesion','2014-09-29 17:39:41'),('d6c651ddcd97183b2e40bc464231c962','Iturbide','sesion','2014-09-29 17:38:42'),('d6ef5f7fa914c19931a55bb262ec879c','Perote','sesion','2014-09-29 17:38:57'),('d707329bece455a462b58ce00d1194c9','Mixtla','sesion','2014-09-29 17:38:51'),('d709f38ef758b5066ef31b18039b8ce5','Ciudad Fernández','sesion','2014-09-29 17:38:29'),('d72fbbccd9fe64c3a14f85d225a046f4','Santa Ana Cuauhtémoc','sesion','2014-09-29 17:39:14'),('d7322ed717dedf1eb4e6e52a37ea7bcd','Nautla','sesion','2014-09-29 17:38:53'),('d736bb10d83a904aefc1d6ce93dc54b8','Ocosingo','sesion','2014-09-29 17:38:54'),('d757719ed7c2b66dd17dcee2a3cb29f4','Sotuta','sesion','2014-09-29 17:39:23'),('d759175de8ea5b1d9a2660e45554894f','San Dimas','sesion','2014-09-29 17:39:03'),('d7657583058394c828ee150fada65345','Suaqui Grande','sesion','2014-09-29 17:39:23'),('d77f00766fd3be3f2189c843a6af3fb2','Tepache','sesion','2014-09-29 17:39:29'),('d79aac075930c83c2f1e369a511148fe','Moris','sesion','2014-09-29 17:38:52'),('d79c6256b9bdac53a55801a066b70da3','Tlachichilco','sesion','2014-09-29 17:39:31'),('d7a728a67d909e714c0774e22cb806f2','Guerrero','sesion','2014-09-29 17:38:38'),('d7a84628c025d30f7b2c52c958767e76','Texcatepec','sesion','2014-09-29 17:39:30'),('d7fd118e6f226a71b5f1ffe10efd0a78','Tamazula','sesion','2014-09-29 17:39:24'),('d81f9c1be2e08964bf9f24b15f0e4900','Chimalhuacán','sesion','2014-09-29 17:38:28'),('d82118376df344b0010f53909b961db3','San Juan del Río','sesion','2014-09-29 17:39:07'),('d827f12e35eae370ba9c65b7f6026695','Zinacantepec','sesion','2014-09-29 17:39:42'),('d82c8d1619ad8176d665453cfb2e55f0','Alaquines','sesion','2014-09-29 17:38:18'),('d8330f857a17c53d217014ee776bfd50','Tlachichuca','sesion','2014-09-29 17:39:31'),('d840cc5d906c3e9c84374c8919d2074e','Jilotepec','sesion','2014-09-29 17:38:44'),('d860bd12ce9c026814bbdfc1c573f0f5','Santo Domingo Tomaltepec','sesion','2014-09-29 17:39:21'),('d860edd1dd83b36f02ce52bde626c653','Tilapa','sesion','2014-09-29 17:39:31'),('d86ea612dec96096c5e0fcc8dd42ab6d','Guanaceví','sesion','2014-09-29 17:38:38'),('d8700cbd38cc9f30cecb34f0c195b137','Ometepec','sesion','2014-09-29 17:38:55'),('d88518acbcc3d08d1f18da62f9bb26ec','Santa Ana Nopalucan','sesion','2014-09-29 17:39:14'),('d8d31bd778da8bdd536187c36e48892b','Santiago Tetepec','sesion','2014-09-29 17:39:20'),('d91d1b4d82419de8a614abce9cc0e6d4','Palizada','sesion','2014-09-29 17:38:56'),('d93ed5b6db83be78efb0d05ae420158e','Moyahua de Estrada','sesion','2014-09-29 17:38:52'),('d947bf06a885db0d477d707121934ff8','Catorce','sesion','2014-09-29 17:38:26'),('d94e18a8adb4cc0f623f7a83b1ac75b4','San Esteban Atatlahuca','sesion','2014-09-29 17:39:03'),('d961e9f236177d65d21100592edb0769','San Rafael','sesion','2014-09-29 17:39:13'),('d96409bf894217686ba124d7356686c9','Capulálpam de Méndez','sesion','2014-09-29 17:38:25'),('d9731321ef4e063ebbee79298fa36f56','San Juan Ñumí','sesion','2014-09-29 17:39:07'),('d9d4f495e875a2e075a1a4a6e1b9770f','Ahumada','sesion','2014-09-29 17:38:18'),('d9fc5b73a8d78fad3d6dffe419384e70','Cochoapa el Grande','sesion','2014-09-29 17:38:30'),('da0d1111d2dc5d489242e60ebcbaf988','Mazapiltepec de Juárez','sesion','2014-09-29 17:38:50'),('da11e8cd1811acb79ccf0fd62cd58f86','San Dionisio Ocotlán','sesion','2014-09-29 17:39:03'),('da4fb5c6e93e74d3df8527599fa62642','Ario','sesion','2014-09-29 17:38:20'),('da8ce53cf0240070ce6c69c48cd588ee','Juan Rodríguez Clara','sesion','2014-09-29 17:38:45'),('daa96d9681a21445772454cbddf0cac1','San Miguel Coatlán','sesion','2014-09-29 17:39:10'),('dabd8d2ce74e782c65a973ef76fd540b','Riva Palacio','sesion','2014-09-29 17:38:59'),('daca41214b39c5dc66674d09081940f0','General Zuazua','sesion','2014-09-29 17:38:37'),('db116b39f7a3ac5366079b1d9fe249a5','Villa Juárez','sesion','2014-09-29 17:39:38'),('db1915052d15f7815c8b88e879465a1e','San Pedro Atoyac','sesion','2014-09-29 17:39:12'),('db29450c3f5e97f97846693611f98c15','Santiago Yosondúa','sesion','2014-09-29 17:39:21'),('db2b4182156b2f1f817860ac9f409ad7','Papalotla de Xicohténcatl','sesion','2014-09-29 17:38:56'),('db576a7d2453575f29eab4bac787b919','Olintla','sesion','2014-09-29 17:38:55'),('db6ebd0566994d14a1767f14eb6fba81','San Martín de Bolaños','sesion','2014-09-29 17:39:09'),('db85e2590b6109813dafa101ceb2faeb','Frontera','sesion','2014-09-29 17:38:37'),('db8e1af0cb3aca1ae2d0018624204529','Cazones de Herrera','sesion','2014-09-29 17:38:26'),('db957c626a8cd7a27231adfbf51e20eb','Santo Domingo Tonalá','sesion','2014-09-29 17:39:21'),('db9eeb7e678863649bce209842e0d164','Zoquiapan','sesion','2014-09-29 17:39:42'),('dbe272bab69f8e13f14b405e038deb64','Gran Morelos','sesion','2014-09-29 17:38:38'),('dc09c97fd73d7a324bdbfe7c79525f64','San Miguel el Grande','sesion','2014-09-29 17:39:10'),('dc16622ddc767e6bc1200fe5df2fbdfb','Zamora','sesion','2014-09-29 17:39:41'),('dc40b7120e77741d191c0d2b82cea7be','Santos Reyes Tepejillo','sesion','2014-09-29 17:39:22'),('dc4c44f624d600aa568390f1f1104aa0','San Blas Atempa','sesion','2014-09-29 17:39:02'),('dc513ea4fbdaa7a14786ffdebc4ef64e','Tepic','sesion','2014-09-29 17:39:30'),('dc5689792e08eb2e219dce49e64c885b','Kanasín','sesion','2014-09-29 17:38:45'),('dc58e3a306451c9d670adcd37004f48f','Othón P. Blanco','sesion','2014-09-29 17:38:55'),('dc5c768b5dc76a084531934b34601977','San Sebastián Tutla','sesion','2014-09-29 17:39:14'),('dc5d637ed5e62c36ecb73b654b05ba2a','Tantima','sesion','2014-09-29 17:39:26'),('dc6a6489640ca02b0d42dabeb8e46bb7','Cucurpe','sesion','2014-09-29 17:38:33'),('dc6a70712a252123c40d2adba6a11d84','Huiloapan de Cuauhtémoc','sesion','2014-09-29 17:38:41'),('dc82d632c9fcecb0778afbc7924494a6','Guazapares','sesion','2014-09-29 17:38:38'),('dc87c13749315c7217cdc4ac692e704c','San Francisco Chindúa','sesion','2014-09-29 17:39:04'),('dc912a253d1e9ba40e2c597ed2376640','Coatzacoalcos','sesion','2014-09-29 17:38:30'),('dc960c46c38bd16e953d97cdeefdbc68','San Ildefonso Sola','sesion','2014-09-29 17:39:04'),('dca5672ff3444c7e997aa9a2c4eb2094','Sanctórum de Lázaro Cárdenas','sesion','2014-09-29 17:39:14'),('dd055f53a45702fe05e449c30ac80df9','Tepeojuma','sesion','2014-09-29 17:39:29'),('dd28e50635038e9cf3a648c2dd17ad0a','Villa de Zaachila','sesion','2014-09-29 17:39:38'),('dd45045f8c68db9f54e70c67048d32e8','Macuspana','sesion','2014-09-29 17:38:48'),('dd458505749b2941217ddd59394240e8','Frontera Hidalgo','sesion','2014-09-29 17:38:37'),('dd77279f7d325eec933f05b1672f6a1f','Petatlán','sesion','2014-09-29 17:38:57'),('dd8eb9f23fbd362da0e3f4e70b878c16','Magdalena Tequisistlán','sesion','2014-09-29 17:38:48'),('ddb30680a691d157187ee1cf9e896d03','Cosolapa','sesion','2014-09-29 17:38:32'),('ddd9dda6bfaf0bb1525a8a27c3ee6131','Urique','sesion','2014-09-29 17:39:36'),('de03beffeed9da5f3639a621bcab5dd4','Teococuilco de Marcos Pérez','sesion','2014-09-29 17:39:28'),('de73998802680548b916f1947ffbad76','Tepehuacán de Guerrero','sesion','2014-09-29 17:39:29'),('dea9ddb25cbf2352cf4dec30222a02a5','Tecomatlán','sesion','2014-09-29 17:39:27'),('deb54ffb41e085fd7f69a75b6359c989','Tepeji del Río de Ocampo','sesion','2014-09-29 17:39:29'),('df0aab058ce179e4f7ab135ed4e641a9','Puerto Vallarta','sesion','2014-09-29 17:38:58'),('df12ecd077efc8c23881028604dbb8cc','San Pedro Comitancillo','sesion','2014-09-29 17:39:12'),('df1f1d20ee86704251795841e6a9405a','Xochitlán Todos Santos','sesion','2014-09-29 17:39:40'),('df263d996281d984952c07998dc54358','Izúcar de Matamoros','sesion','2014-09-29 17:38:43'),('df4fe8a8bcd5c95cdb640aa9793bb32b','Tlahuapan','sesion','2014-09-29 17:39:32'),('df6d2338b2b8fce1ec2f6dda0a630eb0','Muna','sesion','2014-09-29 17:38:52'),('df7f28ac89ca37bf1abd2f6c184fe1cf','Juan N. Méndez','sesion','2014-09-29 17:38:45'),('df877f3865752637daa540ea9cbc474f','El Salvador','sesion','2014-09-29 17:38:35'),('df9028fcb6b065e000ffe8a4f03eeb38','Santiago Xanica','sesion','2014-09-29 17:39:21'),('dfa92d8f817e5b08fcaafb50d03763cf','San Salvador el Seco','sesion','2014-09-29 17:39:13'),('dfce06801e1a85d6d06f1fdd4475dacd','Soyaló','sesion','2014-09-29 17:39:23'),('dfd7468ac613286cdbb40872c8ef3b06','San Gabriel Chilac','sesion','2014-09-29 17:39:04'),('e00406144c1e7e35240afed70f34166a','San Cristóbal de la Barranca','sesion','2014-09-29 17:39:03'),('e00da03b685a0dd18fb6a08af0923de0','Atempan','sesion','2014-09-29 17:38:21'),('e034fb6b66aacc1d48f445ddfb08da98','San Andrés Zabache','sesion','2014-09-29 17:39:01'),('e069ea4c9c233d36ff9c7f329bc08ff1','Zihuatanejo de Azueta','sesion','2014-09-29 17:39:42'),('e06f967fb0d355592be4e7674fa31d26','Tecalitlán','sesion','2014-09-29 17:39:26'),('e07413354875be01a996dc560274708e','Jolalpan','sesion','2014-09-29 17:38:45'),('e077e1a544eec4f0307cf5c3c721d944','San Jerónimo Tecóatl','sesion','2014-09-29 17:39:05'),('e0a209539d1e74ab9fe46b9e01a19a97','Tlaquiltenango','sesion','2014-09-29 17:39:33'),('e0ab531ec312161511493b002f9be2ee','Sombrerete','sesion','2014-09-29 17:39:23'),('e0c641195b27425bb056ac56f8953d24','Copanatoyac','sesion','2014-09-29 17:38:31'),('e0cf1f47118daebc5b16269099ad7347','La Concordia','sesion','2014-09-29 17:38:46'),('e0ec453e28e061cc58ac43f91dc2f3f0','La Trinitaria','sesion','2014-09-29 17:38:46'),('e0f7a4d0ef9b84b83b693bbf3feb8e6e','Xicotepec','sesion','2014-09-29 17:39:39'),('e11943a6031a0e6114ae69c257617980','San Luis del Cordero','sesion','2014-09-29 17:39:08'),('e1314fc026da60d837353d20aefaf054','Tapalpa','sesion','2014-09-29 17:39:26'),('e165421110ba03099a1c0393373c5b43','Calimaya','sesion','2014-09-29 17:38:24'),('e1696007be4eefb81b1a1d39ce48681b','Santa María Cortijo','sesion','2014-09-29 17:39:17'),('e17184bcb70dcf3942c54e0b537ffc6d','Nuevo Laredo','sesion','2014-09-29 17:38:54'),('e19347e1c3ca0c0b97de5fb3b690855a','Pesquería','sesion','2014-09-29 17:38:57'),('e1d5be1c7f2f456670de3d53c7b54f4a','San Antonio la Isla','sesion','2014-09-29 17:39:02'),('e1e32e235eee1f970470a3a6658dfdd5','Cuquío','sesion','2014-09-29 17:38:33'),('e205ee2a5de471a70c1fd1b46033a75f','Matías Romero Avendaño','sesion','2014-09-29 17:38:49'),('e21e4e58ad9ab56e8a4634046da90113','Tzompantepec','sesion','2014-09-29 17:39:36'),('e2230b853516e7b05d79744fbd4c9c13','Ebano','sesion','2014-09-29 17:38:34'),('e22312179bf43e61576081a2f250f845','Queréndaro','sesion','2014-09-29 17:38:58'),('e22dd5dabde45eda5a1a67772c8e25dd','Santiago Tlazoyaltepec','sesion','2014-09-29 17:39:20'),('e2a2dcc36a08a345332c751b2f2e476c','La Magdalena Contreras','sesion','2014-09-29 17:38:46'),('e2ad76f2326fbc6b56a45a56c59fafdb','Villa Corzo','sesion','2014-09-29 17:39:37'),('e2c0be24560d78c5e599c2a9c9d0bbd2','Bavispe','sesion','2014-09-29 17:38:23'),('e2c420d928d4bf8ce0ff2ec19b371514','Altotonga','sesion','2014-09-29 17:38:18'),('e2ef524fbf3d9fe611d5a8e90fefdc9c','Angostura','sesion','2014-09-29 17:38:19'),('e3251075554389fe91d17a794861d47b','San Bartolomé Quialana','sesion','2014-09-29 17:39:02'),('e3408432c1a48a52fb6c74d926b38886','Teotlalco','sesion','2014-09-29 17:39:28'),('e369853df766fa44e1ed0ff613f563bd','Agua Prieta','sesion','2014-09-29 17:38:17'),('e3796ae838835da0b6f6ea37bcf8bcb7','Centla','sesion','2014-09-29 17:38:26'),('e449b9317dad920c0dd5ad0a2a2d5e49','Santa María del Oro','sesion','2014-09-29 17:39:17'),('e44fea3bec53bcea3b7513ccef5857ac','Cuapiaxtla','sesion','2014-09-29 17:38:32'),('e45823afe1e5120cec11fc4c379a0c67','Villa de Tututepec de Melchor Ocampo','sesion','2014-09-29 17:39:38'),('e46de7e1bcaaced9a54f1e9d0d2f800d','Colima','sesion','2014-09-29 17:38:30'),('e4873aa9a05cc5ed839561d121516766','Santa Lucía del Camino','sesion','2014-09-29 17:39:16'),('e48e13207341b6bffb7fb1622282247b','San Jorge Nuchita','sesion','2014-09-29 17:39:05'),('e49b8b4053df9505e1f48c3a701c0682','Jalpa','sesion','2014-09-29 17:38:43'),('e4a6222cdb5b34375400904f03d8e6a5','Camarón de Tejeda','sesion','2014-09-29 17:38:24'),('e4bb4c5173c2ce17fd8fcd40041c068f','Imuris','sesion','2014-09-29 17:38:42'),('e4da3b7fbbce2345d7772b0674a318d5','Acajete','sesion','2014-09-29 17:38:16'),('e4dd5528f7596dcdf871aa55cfccc53c','Tepakán','sesion','2014-09-29 17:39:29'),('e515df0d202ae52fcebb14295743063b','Noria de Ángeles','sesion','2014-09-29 17:38:54'),('e53a0a2978c28872a4505bdb51db06dc','San Andrés Yaá','sesion','2014-09-29 17:39:01'),('e555ebe0ce426f7f9b2bef0706315e0c','Igualapa','sesion','2014-09-29 17:38:41'),('e56954b4f6347e897f954495eab16a88','Carmen','sesion','2014-09-29 17:38:25'),('e56b06c51e1049195d7b26d043c478a0','San Juan Colorado','sesion','2014-09-29 17:39:06'),('e57c6b956a6521b28495f2886ca0977a','Jiquipilco','sesion','2014-09-29 17:38:44'),('e5841df2166dd424a57127423d276bbe','Isidro Fabela','sesion','2014-09-29 17:38:42'),('e58aea67b01fa747687f038dfde066f6','Zirándaro','sesion','2014-09-29 17:39:42'),('e58cc5ca94270acaceed13bc82dfedf7','Pedro Ascencio Alquisiras','sesion','2014-09-29 17:38:57'),('e5a4d6bf330f23a8707bb0d6001dfbe8','Santiago Zacatepec','sesion','2014-09-29 17:39:21'),('e5b294b70c9647dcf804d7baa1903918','Temax','sesion','2014-09-29 17:39:27'),('e5e63da79fcd2bebbd7cb8bf1c1d0274','Pajacuarán','sesion','2014-09-29 17:38:56'),('e5f6ad6ce374177eef023bf5d0c018b6','Genaro Codina','sesion','2014-09-29 17:38:37'),('e60e81c4cbe5171cd654662d9887aec2','San Pedro del Gallo','sesion','2014-09-29 17:39:12'),('e6384711491713d29bc63fc5eeb5ba4f','Santa María Mixtequilla','sesion','2014-09-29 17:39:17'),('e655c7716a4b3ea67f48c6322fc42ed6','San Miguel Tequixtepec','sesion','2014-09-29 17:39:11'),('e6b4b2a746ed40e1af829d1fa82daa10','García','sesion','2014-09-29 17:38:37'),('e6c2dc3dee4a51dcec3a876aa2339a78','Zinacatepec','sesion','2014-09-29 17:39:42'),('e6cb2a3c14431b55aa50c06529eaa21b','Minatitlán','sesion','2014-09-29 17:38:51'),('e6d8545daa42d5ced125a4bf747b3688','San Baltazar Chichicápam','sesion','2014-09-29 17:39:02'),('e702e51da2c0f5be4dd354bb3e295d37','San Diego de Alejandría','sesion','2014-09-29 17:39:03'),('e70611883d2760c8bbafb4acb29e3446','Ixhuatlán de Madero','sesion','2014-09-29 17:38:42'),('e721a54a8cf18c8543d44782d9ef681f','Santa María Tlahuitoltepec','sesion','2014-09-29 17:39:18'),('e744f91c29ec99f0e662c9177946c627','Molcaxac','sesion','2014-09-29 17:38:52'),('e74c0d42b4433905293aab661fcf8ddb','Tekit','sesion','2014-09-29 17:39:27'),('e7b24b112a44fdd9ee93bdf998c6ca0e','Chumatlán','sesion','2014-09-29 17:38:29'),('e7e23670481ac78b3c4122a99ba60573','Tonatico','sesion','2014-09-29 17:39:34'),('e7f8a7fb0b77bcb3b283af5be021448f','Ixmiquilpan','sesion','2014-09-29 17:38:42'),('e816c635cad85a60fabd6b97b03cbcc9','San Miguel Huautla','sesion','2014-09-29 17:39:10'),('e820a45f1dfc7b95282d10b6087e11c0','Mexicali','sesion','2014-09-29 17:38:50'),('e836d813fd184325132fca8edcdfb40e','Cuautlancingo','sesion','2014-09-29 17:38:33'),('e8b1cbd05f6e6a358a81dee52493dd06','Quecholac','sesion','2014-09-29 17:38:58'),('e8c0653fea13f91bf3c48159f7c24f78','Dzilam de Bravo','sesion','2014-09-29 17:38:34'),('e8d92f99edd25e2cef48eca48320a1a5','Santa María del Rosario','sesion','2014-09-29 17:39:17'),('e8dfff4676a47048d6f0c4ef899593dd','Súchil','sesion','2014-09-29 17:39:23'),('e8fd4a8a5bab2b3785d794ab51fef55c','Santiago Yolomécatl','sesion','2014-09-29 17:39:21'),('e94550c93cd70fe748e6982b3439ad3b','Jala','sesion','2014-09-29 17:38:43'),('e94f63f579e05cb49c05c2d050ead9c0','San Martín Totoltepec','sesion','2014-09-29 17:39:09'),('e96ed478dab8595a7dbda4cbcbee168f','Buenavista','sesion','2014-09-29 17:38:24'),('e97ee2054defb209c35fe4dc94599061','Las Minas','sesion','2014-09-29 17:38:47'),('e995f98d56967d946471af29d7bf99f1','Ixtlán de Juárez','sesion','2014-09-29 17:38:43'),('e9b73bccd1762555582b513ff9d02492','Santo Domingo Ingenio','sesion','2014-09-29 17:39:21'),('e9fd7c2c6623306db59b6aef5c0d5cac','Santa Catarina Tayata','sesion','2014-09-29 17:39:15'),('ea119a40c1592979f51819b0bd38d39d','Villa de Ramos','sesion','2014-09-29 17:39:38'),('ea5a486c712a91e48443cd802642223d','Teuchitlán','sesion','2014-09-29 17:39:30'),('ea5d2f1c4608232e07d3aa3d998e5135','Alpatláhuac','sesion','2014-09-29 17:38:18'),('ea6b2efbdd4255a9f1b3bbc6399b58f4','Tianguismanalco','sesion','2014-09-29 17:39:31'),('ea8fcd92d59581717e06eb187f10666d','San Juan del Estado','sesion','2014-09-29 17:39:07'),('eaa32c96f620053cf442ad32258076b9','San Marcos Arteaga','sesion','2014-09-29 17:39:09'),('eaae339c4d89fc102edd9dbdb6a28915','Huayacocotla','sesion','2014-09-29 17:38:40'),('eae27d77ca20db309e056e3d2dcd7d69','Bella Vista','sesion','2014-09-29 17:38:23'),('eb160de1de89d9058fcb0b968dbbbd68','Aramberri','sesion','2014-09-29 17:38:20'),('eb163727917cbba1eea208541a643e74','Bochil','sesion','2014-09-29 17:38:23'),('eb1e78328c46506b46a4ac4a1e378b91','Tahdziú','sesion','2014-09-29 17:39:24'),('eb6fdc36b281b7d5eabf33396c2683a2','Hermosillo','sesion','2014-09-29 17:38:38'),('eb76c035d5d0a2bd2a0d0834b93c9c26','Yurécuaro','sesion','2014-09-29 17:39:40'),('eb86d510361fc23b59f18c1bc9802cc6','San Andrés Tenejapan','sesion','2014-09-29 17:39:01'),('eba0dc302bcd9a273f8bbb72be3a687b','Cusihuiriachi','sesion','2014-09-29 17:38:33'),('ebb71045453f38676c40deb9864f811d','San Pablo Huitzo','sesion','2014-09-29 17:39:11'),('ebd6d2f5d60ff9afaeda1a81fc53e2d0','San Pablo Cuatro Venados','sesion','2014-09-29 17:39:11'),('ebd9629fc3ae5e9f6611e2ee05a31cef','El Carmen','sesion','2014-09-29 17:38:35'),('ec5aa0b7846082a2415f0902f0da88f2','Namiquipa','sesion','2014-09-29 17:38:53'),('ec5decca5ed3d6b8079e2e7e7bacc9f2','Arteaga','sesion','2014-09-29 17:38:20'),('ec8956637a99787bd197eacd77acce5e','Apaseo el Grande','sesion','2014-09-29 17:38:20'),('ec8ce6abb3e952a85b8551ba726a1227','Burgos','sesion','2014-09-29 17:38:24'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Abejones','sesion','2014-09-29 17:38:16'),('ecd62de20ea67e1c2d933d311b08178a','Santo Domingo Tonaltepec','sesion','2014-09-29 17:39:21'),('ed265bc903a5a097f61d3ec064d96d2e','Escárcega','sesion','2014-09-29 17:38:36'),('ed3d2c21991e3bef5e069713af9fa6ca','Ánimas Trujano','sesion','2014-09-29 17:38:19'),('ed4227734ed75d343320b6a5fd16ce57','San Sebastián Tlacotepec','sesion','2014-09-29 17:39:14'),('ed519dacc89b2bead3f453b0b05a4a8b','Soledad de Doblado','sesion','2014-09-29 17:39:23'),('eda80a3d5b344bc40f3bc04f65b7a357','Casas','sesion','2014-09-29 17:38:26'),('eddb904a6db773755d2857aacadb1cb0','Nuevo Zoquiápam','sesion','2014-09-29 17:38:54'),('eddea82ad2755b24c4e168c5fc2ebd40','Charcas','sesion','2014-09-29 17:38:27'),('ede7e2b6d13a41ddf9f4bdef84fdc737','Luvianos','sesion','2014-09-29 17:38:48'),('edfbe1afcf9246bb0d40eb4d8027d90f','Jacala de Ledezma','sesion','2014-09-29 17:38:43'),('ee26fc66b1369c7625333bedafbfcaf6','Unión Juárez','sesion','2014-09-29 17:39:36'),('ee8374ec4e4ad797d42350c904d73077','San Juan Atenco','sesion','2014-09-29 17:39:06'),('eeb69a3cb92300456b6a5f4162093851','Mocochá','sesion','2014-09-29 17:38:51'),('eecca5b6365d9607ee5a9d336962c534','Azcapotzalco','sesion','2014-09-29 17:38:22'),('eed5af6add95a9a6f1252739b1ad8c24','Cotija','sesion','2014-09-29 17:38:32'),('eefc9e10ebdc4a2333b42b2dbb8f27b6','Jesús Carranza','sesion','2014-09-29 17:38:44'),('ef0d3930a7b6c95bd2b32ed45989c61f','Chankom','sesion','2014-09-29 17:38:27'),('ef2a4be5473ab0b3cc286e67b1f59f44','Santa María Xadani','sesion','2014-09-29 17:39:18'),('ef41d488755367316f04fc0e0e9dc9fc','Tula','sesion','2014-09-29 17:39:35'),('ef4e3b775c934dada217712d76f3d51f','Mineral del Monte','sesion','2014-09-29 17:38:51'),('ef50c335cca9f340bde656363ebd02fd','Nicolás Bravo','sesion','2014-09-29 17:38:53'),('ef575e8837d065a1683c022d2077d342','Cuernavaca','sesion','2014-09-29 17:38:33'),('ef8446f35513a8d6aa2308357a268a7e','Texcaltitlán','sesion','2014-09-29 17:39:30'),('efb76cff97aaf057654ef2f38cd77d73','Santa Cruz Papalutla','sesion','2014-09-29 17:39:16'),('efdf562ce2fb0ad460fd8e9d33e57f57','Xaltocan','sesion','2014-09-29 17:39:39'),('efe937780e95574250dabe07151bdc23','Chinicuila','sesion','2014-09-29 17:38:29'),('effc299a1addb07e7089f9b269c31f2f','San Juan Bautista Tlacoatzintepec','sesion','2014-09-29 17:39:06'),('f016e59c7ad8b1d72903bb1aa5720d53','San Juan Lalana','sesion','2014-09-29 17:39:07'),('f033ab37c30201f73f142449d037028d','Amatenango del Valle','sesion','2014-09-29 17:38:19'),('f0935e4cd5920aa6c7c996a5ee53a70f','Apazapan','sesion','2014-09-29 17:38:20'),('f09696910bdd874a99cd74c8f05b5c44','San Francisco Tlapancingo','sesion','2014-09-29 17:39:04'),('f0adc8838f4bdedde4ec2cfad0515589','La Independencia','sesion','2014-09-29 17:38:46'),('f0bbac6fa079f1e00b2c14c1d3c6ccf0','Simojovel','sesion','2014-09-29 17:39:22'),('f0bda020d2470f2e74990a07a607ebd9','Santo Domingo Armenta','sesion','2014-09-29 17:39:21'),('f0dd4a99fba6075a9494772b58f95280','San Juan Petlapa','sesion','2014-09-29 17:39:07'),('f0e52b27a7a5d6a1a87373dffa53dbe5','General Bravo','sesion','2014-09-29 17:38:37'),('f0fcf351df4eb6786e9bb6fc4e2dee02','Tarandacuao','sesion','2014-09-29 17:39:26'),('f106b7f99d2cb30c3db1c3cc0fde9ccb','Tepalcingo','sesion','2014-09-29 17:39:29'),('f12ee9734e1edf70ed02d9829018b3d9','Villa Comaltitlán','sesion','2014-09-29 17:39:37'),('f15d337c70078947cfe1b5d6f0ed3f13','Tulum','sesion','2014-09-29 17:39:35'),('f187a23c3ee681ef6913f31fd6d6446b','San Miguel Tecomatlán','sesion','2014-09-29 17:39:11'),('f18a6d1cde4b205199de8729a6637b42','Santa Catarina Cuixtla','sesion','2014-09-29 17:39:15'),('f197002b9a0853eca5e046d9ca4663d5','Purísima del Rincón','sesion','2014-09-29 17:38:58'),('f1981e4bd8a0d6d8462016d2fc6276b3','Texcalyacac','sesion','2014-09-29 17:39:30'),('f1b6f2857fb6d44dd73c7041e0aa0f19','Cuautitlán Izcalli','sesion','2014-09-29 17:38:33'),('f1c1592588411002af340cbaedd6fc33','Jojutla','sesion','2014-09-29 17:38:44'),('f2201f5191c4e92cc5af043eebfd0946','Jilotzingo','sesion','2014-09-29 17:38:44'),('f2217062e9a397a1dca429e7d70bc6ca','Atlacomulco','sesion','2014-09-29 17:38:21'),('f22e4747da1aa27e363d86d40ff442fe','Tenango de Doria','sesion','2014-09-29 17:39:28'),('f26dab9bf6a137c3b6782e562794c2f2','Soyaniquilpan de Juárez','sesion','2014-09-29 17:39:23'),('f29b38f160f87ae86df31cee1982066f','San Francisco Tetlanohcan','sesion','2014-09-29 17:39:04'),('f29c21d4897f78948b91f03172341b7b','Gutiérrez Zamora','sesion','2014-09-29 17:38:38'),('f2d887e01a80e813d9080038decbbabb','Sitio de Xitlapehua','sesion','2014-09-29 17:39:23'),('f2fc990265c712c49d51a18a32b39f0c','Chicomuselo','sesion','2014-09-29 17:38:28'),('f3144cefe89a60d6a1afaf7859c5076b','Santiago Comaltepec','sesion','2014-09-29 17:39:19'),('f3173935ed8ac4bf073c1bcd63171f8a','Santa Catarina','sesion','2014-09-29 17:39:15'),('f31b20466ae89669f9741e047487eb37','Tanquián de Escobedo','sesion','2014-09-29 17:39:26'),('f337d999d9ad116a7b4f3d409fcc6480','San Pedro Nopala','sesion','2014-09-29 17:39:13'),('f33ba15effa5c10e873bf3842afb46a6','Nazareno Etla','sesion','2014-09-29 17:38:53'),('f340f1b1f65b6df5b5e3f94d95b11daf','Camargo','sesion','2014-09-29 17:38:24'),('f387624df552cea2f369918c5e1e12bc','Espita','sesion','2014-09-29 17:38:36'),('f39ae9ff3a81f499230c4126e01f421b','Santos Reyes Nopala','sesion','2014-09-29 17:39:22'),('f3ac63c91272f19ce97c7397825cc15f','Zontecomatlán de López y Fuentes','sesion','2014-09-29 17:39:42'),('f3bd5ad57c8389a8a1a541a76be463bf','San Juan Coatzóspam','sesion','2014-09-29 17:39:06'),('f3d9de86462c28781cbe5c47ef22c3e5','Villa Tejúpam de la Unión','sesion','2014-09-29 17:39:38'),('f3e52c300b822a8123e7ace55fe15c08','Tlacotepec Plumas','sesion','2014-09-29 17:39:32'),('f3f1b7fc5a8779a9e618e1f23a7b7860','San Gabriel Mixtepec','sesion','2014-09-29 17:39:04'),('f3f27a324736617f20abbf2ffd806f6d','El Barrio de la Soledad','sesion','2014-09-29 17:38:35'),('f410588e48dc83f2822a880a68f78923','Tlaola','sesion','2014-09-29 17:39:33'),('f442d33fa06832082290ad8544a8da27','Soledad de Graciano Sánchez','sesion','2014-09-29 17:39:23'),('f4552671f8909587cf485ea990207f3b','Lázaro Cárdenas','sesion','2014-09-29 17:38:47'),('f4573fc71c731d5c362f0d7860945b88','San Bernardo Mixtepec','sesion','2014-09-29 17:39:02'),('f457c545a9ded88f18ecee47145a72c0','Ajuchitlán del Progreso','sesion','2014-09-29 17:38:18'),('f45a1078feb35de77d26b3f7a52ef502','Sunuapa','sesion','2014-09-29 17:39:24'),('f47330643ae134ca204bf6b2481fec47','Romita','sesion','2014-09-29 17:38:59'),('f47d0ad31c4c49061b9e505593e3db98','Mártir de Cuilapan','sesion','2014-09-29 17:38:49'),('f48c04ffab49ff0e5d1176244fdfb65c','Tixkokob','sesion','2014-09-29 17:39:31'),('f4a331b7a22d1b237565d8813a34d8ac','San Martín de los Cansecos','sesion','2014-09-29 17:39:09'),('f4a4da9aa7eadfd23c7bdb7cf57b3112','Sinanché','sesion','2014-09-29 17:39:23'),('f4b9ec30ad9f68f89b29639786cb62ef','Angangueo','sesion','2014-09-29 17:38:19'),('f4be00279ee2e0a53eafdaa94a151e2c','El Naranjo','sesion','2014-09-29 17:38:35'),('f4dd765c12f2ef67f98f3558c282a9cd','Ojuelos de Jalisco','sesion','2014-09-29 17:38:55'),('f4f6dce2f3a0f9dada0c2b5b66452017','Compostela','sesion','2014-09-29 17:38:31'),('f50a6c02a3fc5a3a5d4d9391f05f3efc','Santa María Nduayaco','sesion','2014-09-29 17:39:17'),('f516dfb84b9051ed85b89cdc3a8ab7f5','Zitácuaro','sesion','2014-09-29 17:39:42'),('f52378e14237225a6f6c7d802dc6abbd','San Juan de los Cués','sesion','2014-09-29 17:39:06'),('f542eae1949358e25d8bfeefe5b199f1','Silao','sesion','2014-09-29 17:39:22'),('f57a2f557b098c43f11ab969efe1504b','Mártires de Tacubaya','sesion','2014-09-29 17:38:49'),('f5c3dd7514bf620a1b85450d2ae374b1','Singuilucan','sesion','2014-09-29 17:39:23'),('f5deaeeae1538fb6c45901d524ee2f98','General Simón Bolívar','sesion','2014-09-29 17:38:37'),('f5f8590cd58a54e94377e6ae2eded4d9','Cruillas','sesion','2014-09-29 17:38:32'),('f60bb6bb4c96d4df93c51bd69dcc15a0','Santa Catarina Zapoquila','sesion','2014-09-29 17:39:15'),('f61d6947467ccd3aa5af24db320235dd','Coacoatzintla','sesion','2014-09-29 17:38:30'),('f63f65b503e22cb970527f23c9ad7db1','Santo Domingo Ozolotepec','sesion','2014-09-29 17:39:21'),('f64eac11f2cd8f0efa196f8ad173178e','Ixcamilpa de Guerrero','sesion','2014-09-29 17:38:42'),('f670ef5d2d6bdf8f29450a970494dd64','Santa Isabel','sesion','2014-09-29 17:39:16'),('f69e505b08403ad2298b9f262659929a','San Martín Huamelúlpam','sesion','2014-09-29 17:39:09'),('f6c79f4af478638c39b206ec30ab166b','Yautepec','sesion','2014-09-29 17:39:40'),('f6e794a75c5d51de081dbefa224304f9','Tumbiscatío','sesion','2014-09-29 17:39:35'),('f7177163c833dff4b38fc8d2872f1ec6','Ahuazotepec','sesion','2014-09-29 17:38:17'),('f718499c1c8cef6730f9fd03c8125cab','Cantamayec','sesion','2014-09-29 17:38:25'),('f73b76ce8949fe29bf2a537cfa420e8f','Coetzala','sesion','2014-09-29 17:38:30'),('f74909ace68e51891440e4da0b65a70c','Cosalá','sesion','2014-09-29 17:38:32'),('f7664060cc52bc6f3d620bcedc94a4b6','Carrillo Puerto','sesion','2014-09-29 17:38:26'),('f76a89f0cb91bc419542ce9fa43902dc','Gustavo Díaz Ordaz','sesion','2014-09-29 17:38:38'),('f770b62bc8f42a0b66751fe636fc6eb0','Cunduacán','sesion','2014-09-29 17:38:33'),('f79921bbae40a577928b76d2fc3edc2a','Hunucmá','sesion','2014-09-29 17:38:41'),('f7cade80b7cc92b991cf4d2806d6bd78','San Agustín Etla','sesion','2014-09-29 17:39:00'),('f7e6c85504ce6e82442c770f7c8606f0','Axochiapan','sesion','2014-09-29 17:38:22'),('f7e9050c92a851b0016442ab604b0488','Linares','sesion','2014-09-29 17:38:47'),('f7f580e11d00a75814d2ded41fe8e8fe','Puebla','sesion','2014-09-29 17:38:58'),('f80bf05527157a8c2a7bb63b22f49aaa','Tepetlaoxtoc','sesion','2014-09-29 17:39:29'),('f80ff32e08a25270b5f252ce39522f72','Tlatlauquitepec','sesion','2014-09-29 17:39:33'),('f85454e8279be180185cac7d243c5eb3','Copándaro','sesion','2014-09-29 17:38:31'),('f899139df5e1059396431415e770c6dd','Apan','sesion','2014-09-29 17:38:20'),('f8bf09f5fceaea80e1f864a1b48938bf','Tlalixtaquilla de Maldonado','sesion','2014-09-29 17:39:32'),('f8c0c968632845cd133308b1a494967f','Tlajomulco de Zúñiga','sesion','2014-09-29 17:39:32'),('f8c1f23d6a8d8d7904fc0ea8e066b3bb','Cojumatlán de Régules','sesion','2014-09-29 17:38:30'),('f8e59f4b2fe7c5705bf878bbd494ccdf','Valle de Zaragoza','sesion','2014-09-29 17:39:36'),('f9028faec74be6ec9b852b0a542e2f39','Lampazos de Naranjo','sesion','2014-09-29 17:38:47'),('f90f2aca5c640289d0a29417bcb63a37','Chalcatongo de Hidalgo','sesion','2014-09-29 17:38:26'),('f91e24dfe80012e2a7984afa4480a6d6','San Damián Texóloc','sesion','2014-09-29 17:39:03'),('f93882cbd8fc7fb794c1011d63be6fb6','San Francisco Nuxaño','sesion','2014-09-29 17:39:04'),('f976b57bb9dd27aa2e7e7df2825893a6','Zinapécuaro','sesion','2014-09-29 17:39:42'),('f9a40a4780f5e1306c46f1c8daecee3b','Los Cabos','sesion','2014-09-29 17:38:48'),('f9b902fc3289af4dd08de5d1de54f68f','Chihuahua','sesion','2014-09-29 17:38:28'),('f9be311e65d81a9ad8150a60844bb94c','San Ildefonso Amatlán','sesion','2014-09-29 17:39:04'),('f9d1152547c0bde01830b7e8bd60024c','Tamazunchale','sesion','2014-09-29 17:39:25'),('fa14d4fe2f19414de3ebd9f63d5c0169','Jaral del Progreso','sesion','2014-09-29 17:38:44'),('fa1e9c965314ccd7810fb5ea838303e5','San Miguel Tlacotepec','sesion','2014-09-29 17:39:11'),('fa2431bf9d65058fe34e9713e32d60e6','Zapotitlán Lagunas','sesion','2014-09-29 17:39:41'),('fa3a3c407f82377f55c19c5d403335c7','La Perla','sesion','2014-09-29 17:38:46'),('fa7cdfad1a5aaf8370ebeda47a1ff1c3','Atoyac de Álvarez','sesion','2014-09-29 17:38:22'),('fa83a11a198d5a7f0bf77a1987bcd006','Landero y Coss','sesion','2014-09-29 17:38:47'),('faa9afea49ef2ff029a833cccc778fd0','Coquimatlán','sesion','2014-09-29 17:38:31'),('faacbcd5bf1d018912c116bf2783e9a1','Sinaloa','sesion','2014-09-29 17:39:22'),('faafda66202d234463057972460c04f5','Santa Catarina Lachatao','sesion','2014-09-29 17:39:15'),('facf9f743b083008a894eee7baa16469','San Pedro Yucunama','sesion','2014-09-29 17:39:13'),('fae0b27c451c728867a567e8c1bb4e53','Huépac','sesion','2014-09-29 17:38:40'),('fb2e203234df6dee15934e448ee88971','Tlaltenango','sesion','2014-09-29 17:39:33'),('fb2fcd534b0ff3bbed73cc51df620323','Salvatierra','sesion','2014-09-29 17:39:00'),('fb508ef074ee78a0e58c68be06d8a2eb','San Juan Bautista Jayacatlán','sesion','2014-09-29 17:39:06'),('fb60d411a5c5b72b2e7d3527cfc84fd0','San Diego la Mesa Tochimiltzingo','sesion','2014-09-29 17:39:03'),('fb7b9ffa5462084c5f4e7e85a093e6d7','Choix','sesion','2014-09-29 17:38:29'),('fb87582825f9d28a8d42c5e5e5e8b23d','Tepetongo','sesion','2014-09-29 17:39:29'),('fb89705ae6d743bf1e848c206e16a1d7','Ixtenco','sesion','2014-09-29 17:38:43'),('fb8feff253bb6c834deb61ec76baa893','Tanetze de Zaragoza','sesion','2014-09-29 17:39:25'),('fba9d88164f3e2d9109ee770223212a0','Naranjos Amatlán','sesion','2014-09-29 17:38:53'),('fbd7939d674997cdb4692d34de8633c4','Amacuzac','sesion','2014-09-29 17:38:19'),('fc221309746013ac554571fbd180e1c8','Ayotoxco de Guerrero','sesion','2014-09-29 17:38:22'),('fc2c7c47b918d0c2d792a719dfb602ef','Oteapan','sesion','2014-09-29 17:38:55'),('fc3cf452d3da8402bebb765225ce8c0e','Las Choapas','sesion','2014-09-29 17:38:47'),('fc490ca45c00b1249bbe3554a4fdf6fb','Alpoyeca','sesion','2014-09-29 17:38:18'),('fc49306d97602c8ed1be1dfbf0835ead','Los Aldamas','sesion','2014-09-29 17:38:48'),('fc4ddc15f9f4b4b06ef7844d6bb53abf','Tecoh','sesion','2014-09-29 17:39:26'),('fc528592c3858f90196fbfacc814f235','Santa Cruz de Bravo','sesion','2014-09-29 17:39:15'),('fc6709bfdf0572f183c1a84ce5276e96','Santiago Choápam','sesion','2014-09-29 17:39:19'),('fc8001f834f6a5f0561080d134d53d29','José Azueta','sesion','2014-09-29 17:38:45'),('fc9b003bb003a298c2ad0d05e4342bdc','Santiago Sochiapan','sesion','2014-09-29 17:39:20'),('fca0789e7891cbc0583298a238316122','Turicato','sesion','2014-09-29 17:39:35'),('fccb3cdc9acc14a6e70a12f74560c026','Huitiupán','sesion','2014-09-29 17:38:41'),('fccb60fb512d13df5083790d64c4d5dd','Cosoltepec','sesion','2014-09-29 17:38:32'),('fcdf25d6e191893e705819b177cddea0','San Miguel Tlacamama','sesion','2014-09-29 17:39:11'),('fd06b8ea02fe5b1c2496fe1700e9d16c','Pluma Hidalgo','sesion','2014-09-29 17:38:58'),('fd2c5e4680d9a01dba3aada5ece22270','Polotitlán','sesion','2014-09-29 17:38:58'),('fd5c905bcd8c3348ad1b35d7231ee2b1','San Francisco Lachigoló','sesion','2014-09-29 17:39:04'),('fddd7938a71db5f81fcc621673ab67b7','Santa María Pápalo','sesion','2014-09-29 17:39:17'),('fde9264cf376fffe2ee4ddf4a988880d','General Enrique Estrada','sesion','2014-09-29 17:38:37'),('fe131d7f5a6b38b23cc967316c13dae2','Cansahcab','sesion','2014-09-29 17:38:25'),('fe2d010308a6b3799a3d9c728ee74244','Saltillo','sesion','2014-09-29 17:39:00'),('fe40fb944ee700392ed51bfe84dd4e3d','Zacoalco de Torres','sesion','2014-09-29 17:39:41'),('fe51510c80bfd6e5d78a164cd5b1f688','San Jerónimo Taviche','sesion','2014-09-29 17:39:05'),('fe709c654eac84d5239d1a12a4f71877','Playa Vicente','sesion','2014-09-29 17:38:58'),('fe70c36866add1572a8e2b96bfede7bf','San Matías Tlalancaleca','sesion','2014-09-29 17:39:10'),('fe73f687e5bc5280214e0486b273a5f9','Chicxulub Pueblo','sesion','2014-09-29 17:38:28'),('fe7ee8fc1959cc7214fa21c4840dff0a','Mulegé','sesion','2014-09-29 17:38:52'),('fe8c15fed5f808006ce95eddb7366e35','Leonardo Bravo','sesion','2014-09-29 17:38:47'),('fe9fc289c3ff0af142b6d3bead98a923','Amatitlán','sesion','2014-09-29 17:38:19'),('fea9c11c4ad9a395a636ed944a28b51a','Villa González Ortega','sesion','2014-09-29 17:39:38'),('feab05aa91085b7a8012516bc3533958','Puente de Ixtla','sesion','2014-09-29 17:38:58'),('fec8d47d412bcbeece3d9128ae855a7a','Motozintla','sesion','2014-09-29 17:38:52'),('fed33392d3a48aa149a87a38b875ba4a','Natívitas','sesion','2014-09-29 17:38:53'),('ff1418e8cc993fe8abcfe3ce2003e5c5','Tacotalpa','sesion','2014-09-29 17:39:24'),('ff49cc40a8890e6a60f40ff3026d2730','San Jerónimo Tlacochahuaya','sesion','2014-09-29 17:39:05'),('ff4d5fbbafdf976cfdc032e3bde78de5','Dzilam González','sesion','2014-09-29 17:38:34'),('ff7d0f525b3be596a51fb919492c099c','Tehuipango','sesion','2014-09-29 17:39:27'),('fface8385abbf94b4593a0ed53a0c70f','Soledad Etla','sesion','2014-09-29 17:39:23'),('ffd52f3c7e12435a724a8f30fddadd9c','Ciudad Valles','sesion','2014-09-29 17:38:30'),('ffeabd223de0d4eacb9a3e6e53e5448d','General Canuto A. Neri','sesion','2014-09-29 17:38:37'),('ffedf5be3a86e2ee281d54cdc97bc1cf','Zinacantán','sesion','2014-09-29 17:39:42'),('ffeed84c7cb1ae7bf4ec4bd78275bb98','Pinos','sesion','2014-09-29 17:38:57');
/*!40000 ALTER TABLE `tbbsnm14` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm15`
--

DROP TABLE IF EXISTS `tbbsnm15`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm15` (
  `idcolonia` varchar(32) NOT NULL,
  `dsnombre` varchar(30) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcolonia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm15`
--

LOCK TABLES `tbbsnm15` WRITE;
/*!40000 ALTER TABLE `tbbsnm15` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm15` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm16`
--

DROP TABLE IF EXISTS `tbbsnm16`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm16` (
  `idnotaria` varchar(32) NOT NULL,
  `innumnot` varchar(3) NOT NULL,
  `dslogo` varchar(250) DEFAULT NULL,
  `iddomicilio` varchar(32) NOT NULL,
  `isasociada` int(5) NOT NULL DEFAULT '0',
  `idusuario` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idsesion` varchar(32) NOT NULL,
  PRIMARY KEY (`idnotaria`),
  KEY `iddomicilio` (`iddomicilio`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `tbbsnm16_ibfk_1` FOREIGN KEY (`iddomicilio`) REFERENCES `tbbsnm12` (`iddomicilio`),
  CONSTRAINT `tbbsnm16_ibfk_2` FOREIGN KEY (`idusuario`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm16`
--

LOCK TABLES `tbbsnm16` WRITE;
/*!40000 ALTER TABLE `tbbsnm16` DISABLE KEYS */;
INSERT INTO `tbbsnm16` VALUES ('1','98','','c4ca4238a0b923820dcc509a6f75849b',0,'1679091c5a880faf6fb5e6087eb1b2dc','2014-09-29 17:37:44','sesion1'),('2','24','','c4ca4238a0b923820dcc509a6f75849b',1,'8f14e45fceea167a5a36dedd4bea2543','2014-09-29 17:37:44','sesion1');
/*!40000 ALTER TABLE `tbbsnm16` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm17`
--

DROP TABLE IF EXISTS `tbbsnm17`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm17` (
  `idsuboperacion` varchar(32) NOT NULL,
  `dsnombre` varchar(250) NOT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `idoperacion` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idsuboperacion`),
  KEY `idoperacion` (`idoperacion`),
  CONSTRAINT `tbbsnm17_ibfk_1` FOREIGN KEY (`idoperacion`) REFERENCES `tbbsnm27` (`idoperacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm17`
--

LOCK TABLES `tbbsnm17` WRITE;
/*!40000 ALTER TABLE `tbbsnm17` DISABLE KEYS */;
INSERT INTO `tbbsnm17` VALUES ('c4ca4238a0b923820dcc509a6f75849b','Inmueble','descripcion','c4ca4238a0b923820dcc509a6f75849b','idsesion','2014-09-29 17:37:40');
/*!40000 ALTER TABLE `tbbsnm17` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm18`
--

DROP TABLE IF EXISTS `tbbsnm18`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm18` (
  `idacto` varchar(32) NOT NULL,
  `dsnombre` varchar(120) NOT NULL,
  `numacto` int(11) DEFAULT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `idsuboperacion` varchar(32) NOT NULL,
  `idexpediente` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idacto`),
  KEY `idsuboperacion` (`idsuboperacion`),
  KEY `idexpediente` (`idexpediente`),
  CONSTRAINT `tbbsnm18_ibfk_1` FOREIGN KEY (`idsuboperacion`) REFERENCES `tbbsnm17` (`idsuboperacion`),
  CONSTRAINT `tbbsnm18_ibfk_2` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm18`
--

LOCK TABLES `tbbsnm18` WRITE;
/*!40000 ALTER TABLE `tbbsnm18` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm18` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm19`
--

DROP TABLE IF EXISTS `tbbsnm19`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm19` (
  `idbitacoradocumento` varchar(32) NOT NULL,
  `iddocumentoexp` varchar(32) NOT NULL,
  `idacto` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idbitacoradocumento`),
  KEY `iddocumentoexp` (`iddocumentoexp`),
  KEY `idacto` (`idacto`),
  CONSTRAINT `tbbsnm19_ibfk_1` FOREIGN KEY (`iddocumentoexp`) REFERENCES `tbbsnm23` (`iddocumentoexpediente`),
  CONSTRAINT `tbbsnm19_ibfk_2` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm19`
--

LOCK TABLES `tbbsnm19` WRITE;
/*!40000 ALTER TABLE `tbbsnm19` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm19` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm20`
--

DROP TABLE IF EXISTS `tbbsnm20`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm20` (
  `idbitacoraexpediente` varchar(32) NOT NULL,
  `idexpediente` varchar(32) NOT NULL,
  `cdacto` varchar(32) NOT NULL,
  `fechainicial` date NOT NULL,
  `fechafinal` date NOT NULL,
  `fechaesperada` date DEFAULT NULL,
  `idusuarioavanza` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idbitacoraexpediente`),
  KEY `idexpediente` (`idexpediente`),
  KEY `idusuarioavanza` (`idusuarioavanza`),
  CONSTRAINT `tbbsnm20_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`),
  CONSTRAINT `tbbsnm20_ibfk_2` FOREIGN KEY (`idusuarioavanza`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm20`
--

LOCK TABLES `tbbsnm20` WRITE;
/*!40000 ALTER TABLE `tbbsnm20` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm20` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm21`
--

DROP TABLE IF EXISTS `tbbsnm21`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm21` (
  `idcompareciente` varchar(32) NOT NULL,
  `idpersona` varchar(32) NOT NULL,
  `idtipocompareciente` varchar(32) NOT NULL,
  `alias` varchar(120) DEFAULT NULL,
  `isrepresentante` int(1) DEFAULT NULL,
  `idacto` varchar(32) NOT NULL,
  `idregri` varchar(32) DEFAULT NULL,
  `idtratamiento` varchar(32) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `amboscompran` int(1) DEFAULT NULL,
  `idregimen` varchar(32) DEFAULT NULL,
  `idocupacion` varchar(32) DEFAULT NULL,
  `idestadocivil` varchar(32) DEFAULT NULL,
  `iddomicilio` varchar(32) DEFAULT NULL,
  `idcontacto` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`idcompareciente`),
  KEY `idpersona` (`idpersona`),
  KEY `idacto` (`idacto`),
  KEY `idregri` (`idregri`),
  KEY `idtipocompareciente` (`idtipocompareciente`),
  KEY `idtratamiento` (`idtratamiento`),
  KEY `idregimen` (`idregimen`),
  KEY `idocupacion` (`idocupacion`),
  KEY `idestadocivil` (`idestadocivil`),
  KEY `iddomicilio` (`iddomicilio`),
  KEY `idcontacto` (`idcontacto`),
  CONSTRAINT `tbbsnm21_ibfk_1` FOREIGN KEY (`idpersona`) REFERENCES `tbbsnm28` (`idpersona`),
  CONSTRAINT `tbbsnm21_ibfk_10` FOREIGN KEY (`idcontacto`) REFERENCES `tbbsnm67` (`idcontacto`),
  CONSTRAINT `tbbsnm21_ibfk_2` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`),
  CONSTRAINT `tbbsnm21_ibfk_3` FOREIGN KEY (`idregri`) REFERENCES `tbbsnm44` (`idregri`),
  CONSTRAINT `tbbsnm21_ibfk_4` FOREIGN KEY (`idtipocompareciente`) REFERENCES `tbbsnm31` (`idtipocompareciente`),
  CONSTRAINT `tbbsnm21_ibfk_5` FOREIGN KEY (`idtratamiento`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm21_ibfk_6` FOREIGN KEY (`idregimen`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm21_ibfk_7` FOREIGN KEY (`idocupacion`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm21_ibfk_8` FOREIGN KEY (`idestadocivil`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm21_ibfk_9` FOREIGN KEY (`iddomicilio`) REFERENCES `tbbsnm12` (`iddomicilio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm21`
--

LOCK TABLES `tbbsnm21` WRITE;
/*!40000 ALTER TABLE `tbbsnm21` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm21` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm22`
--

DROP TABLE IF EXISTS `tbbsnm22`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm22` (
  `iddocumento` varchar(32) NOT NULL,
  `dstitulo` varchar(90) NOT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `version` int(5) NOT NULL,
  `ispublicado` int(1) NOT NULL,
  `fecha` date NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `isrequerido` int(1) NOT NULL,
  `idtipodoc` varchar(32) NOT NULL,
  `txplantilla` longtext,
  `isactivo` int(1) DEFAULT '1',
  `isgestionado` varchar(1) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `numdiasgestion` int(5) DEFAULT NULL,
  PRIMARY KEY (`iddocumento`),
  KEY `idtipodoc` (`idtipodoc`),
  CONSTRAINT `tbbsnm22_ibfk_1` FOREIGN KEY (`idtipodoc`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm22`
--

LOCK TABLES `tbbsnm22` WRITE;
/*!40000 ALTER TABLE `tbbsnm22` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm22` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm22b`
--

DROP TABLE IF EXISTS `tbbsnm22b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm22b` (
  `identificador` int(10) NOT NULL AUTO_INCREMENT,
  `iddocumento` varchar(32) NOT NULL,
  `version` int(5) NOT NULL,
  `versionbase` int(5) NOT NULL,
  `dstitulo` varchar(30) NOT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `txplantilla` longtext,
  `idtipodoc` varchar(32) NOT NULL,
  `fecha` date NOT NULL,
  `ispublicado` int(1) NOT NULL,
  `isrequerido` int(1) NOT NULL,
  `numdiasgestion` int(5) DEFAULT NULL,
  `isactivo` int(1) DEFAULT '1',
  `isgestionado` varchar(1) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`identificador`),
  KEY `iddocumento_fk` (`iddocumento`),
  KEY `idtipodoc` (`idtipodoc`),
  CONSTRAINT `tbbsnm22b_ibfk_1` FOREIGN KEY (`idtipodoc`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm22b`
--

LOCK TABLES `tbbsnm22b` WRITE;
/*!40000 ALTER TABLE `tbbsnm22b` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm22b` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm23`
--

DROP TABLE IF EXISTS `tbbsnm23`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm23` (
  `iddocumentoexpediente` varchar(32) NOT NULL,
  `idexpediente` varchar(32) NOT NULL,
  `iddocumento` varchar(32) NOT NULL,
  `idacto` varchar(32) NOT NULL,
  `dsnombreVar` varchar(60) NOT NULL,
  `dsvalorVar` varchar(120) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddocumentoexpediente`),
  KEY `idexpediente` (`idexpediente`),
  KEY `iddocumento` (`iddocumento`),
  KEY `idacto` (`idacto`),
  CONSTRAINT `tbbsnm23_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`),
  CONSTRAINT `tbbsnm23_ibfk_2` FOREIGN KEY (`iddocumento`) REFERENCES `tbbsnm22` (`iddocumento`),
  CONSTRAINT `tbbsnm23_ibfk_3` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm23`
--

LOCK TABLES `tbbsnm23` WRITE;
/*!40000 ALTER TABLE `tbbsnm23` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm23` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm24`
--

DROP TABLE IF EXISTS `tbbsnm24`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm24` (
  `idescritura` varchar(32) NOT NULL,
  `dsnumescritura` varchar(32) DEFAULT NULL,
  `idexpediente` varchar(32) NOT NULL,
  `fechacreacion` date NOT NULL,
  `fechafirma` date DEFAULT NULL,
  `fechaimpresion` date DEFAULT NULL,
  `fechaencuadernado` date DEFAULT NULL,
  `folioini` bigint(20) DEFAULT NULL,
  `foliofin` bigint(20) DEFAULT NULL,
  `costo` double DEFAULT NULL,
  `idlibro` varchar(32) DEFAULT NULL,
  `idnotario` varchar(32) NOT NULL,
  `isfirmaditto` int(1) DEFAULT '0',
  `fechafirmaditto` date DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idescritura`),
  UNIQUE KEY `dsnumescritura` (`dsnumescritura`),
  KEY `idexpediente` (`idexpediente`),
  KEY `idlibro` (`idlibro`),
  KEY `idnotario` (`idnotario`),
  CONSTRAINT `tbbsnm24_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`),
  CONSTRAINT `tbbsnm24_ibfk_2` FOREIGN KEY (`idlibro`) REFERENCES `tbbsnm26` (`idlibro`),
  CONSTRAINT `tbbsnm24_ibfk_3` FOREIGN KEY (`idnotario`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm24`
--

LOCK TABLES `tbbsnm24` WRITE;
/*!40000 ALTER TABLE `tbbsnm24` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm24` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm25`
--

DROP TABLE IF EXISTS `tbbsnm25`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm25` (
  `idguardia` varchar(32) NOT NULL,
  `idabogado` varchar(32) NOT NULL,
  `fechainicial` date NOT NULL,
  `fechafinal` date NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idguardia`),
  KEY `idabogado` (`idabogado`),
  CONSTRAINT `tbbsnm25_ibfk_1` FOREIGN KEY (`idabogado`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm25`
--

LOCK TABLES `tbbsnm25` WRITE;
/*!40000 ALTER TABLE `tbbsnm25` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm25` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm26`
--

DROP TABLE IF EXISTS `tbbsnm26`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm26` (
  `idlibro` varchar(32) NOT NULL,
  `dsdescripcion` varchar(250) NOT NULL,
  `fecha` date NOT NULL,
  `infolioinicial` int(11) NOT NULL,
  `infoliofinal` int(11) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `innumlibro` int(5) DEFAULT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idlibro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm26`
--

LOCK TABLES `tbbsnm26` WRITE;
/*!40000 ALTER TABLE `tbbsnm26` DISABLE KEYS */;
INSERT INTO `tbbsnm26` VALUES ('86b86014d1f8ca4f6054db174be70841','actualizacion de libro','2014-09-29',7001,12000,'sesion',1100,'2014-09-29 17:37:35');
/*!40000 ALTER TABLE `tbbsnm26` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm27`
--

DROP TABLE IF EXISTS `tbbsnm27`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm27` (
  `idoperacion` varchar(32) NOT NULL,
  `dsnombre` varchar(120) NOT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idoperacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm27`
--

LOCK TABLES `tbbsnm27` WRITE;
/*!40000 ALTER TABLE `tbbsnm27` DISABLE KEYS */;
INSERT INTO `tbbsnm27` VALUES ('c4ca4238a0b923820dcc509a6f75849b','Compraventa','codigo','sesion','2014-09-29 17:37:40');
/*!40000 ALTER TABLE `tbbsnm27` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm28`
--

DROP TABLE IF EXISTS `tbbsnm28`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm28` (
  `idpersona` varchar(32) NOT NULL,
  `idtipopersona` varchar(32) DEFAULT NULL,
  `dsnombre` varchar(120) NOT NULL,
  `dsapellidopat` varchar(30) DEFAULT NULL,
  `dsapellidomat` varchar(30) DEFAULT NULL,
  `dsnombrecompleto` varchar(120) DEFAULT NULL,
  `dsrfc` varchar(13) DEFAULT NULL,
  `dscurp` varchar(18) DEFAULT NULL,
  `idnacionalidad` varchar(32) DEFAULT NULL,
  `dsestadonacimiento` varchar(60) DEFAULT NULL,
  `dsmunicipionacimiento` varchar(60) DEFAULT NULL,
  `fechanacimiento` date DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpersona`),
  KEY `idtipopersona` (`idtipopersona`),
  KEY `idnacionalidad` (`idnacionalidad`),
  CONSTRAINT `tbbsnm28_ibfk_1` FOREIGN KEY (`idtipopersona`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm28_ibfk_2` FOREIGN KEY (`idnacionalidad`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm28`
--

LOCK TABLES `tbbsnm28` WRITE;
/*!40000 ALTER TABLE `tbbsnm28` DISABLE KEYS */;
INSERT INTO `tbbsnm28` VALUES ('c4ca4238a0b923820dcc509a6f75849b','3c59dc048e8850243be8079a5c74d079','Gonzalo','Blanco','Ortíz','Gonzalo Blanco Ortiz','','','c9f0f895fb98ab9159f51fd0297e236d','','','2014-11-23','sesion','2014-09-29 17:37:44');
/*!40000 ALTER TABLE `tbbsnm28` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm29`
--

DROP TABLE IF EXISTS `tbbsnm29`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm29` (
  `idpresupuesto` varchar(32) NOT NULL,
  `idacto` varchar(32) NOT NULL,
  `conceptoPago` varchar(32) NOT NULL,
  `isaplicaiva` int(1) DEFAULT NULL,
  `ispagado` int(1) DEFAULT NULL,
  `fechaPago` date DEFAULT NULL,
  `fechaCreacion` date DEFAULT NULL,
  `importe` double DEFAULT NULL,
  `iva` double DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `comentario` longtext,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpresupuesto`),
  KEY `idacto` (`idacto`),
  KEY `conceptoPago` (`conceptoPago`),
  CONSTRAINT `tbbsnm29_ibfk_1` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`),
  CONSTRAINT `tbbsnm29_ibfk_2` FOREIGN KEY (`conceptoPago`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm29`
--

LOCK TABLES `tbbsnm29` WRITE;
/*!40000 ALTER TABLE `tbbsnm29` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm29` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm30`
--

DROP TABLE IF EXISTS `tbbsnm30`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm30` (
  `idtestimonio` varchar(32) NOT NULL,
  `idusuarioelaboro` varchar(32) NOT NULL,
  `idescritura` varchar(32) NOT NULL,
  `idnotario` varchar(32) NOT NULL,
  `isgenerado` int(1) DEFAULT NULL,
  `dsrutaescritura` varchar(600) DEFAULT NULL,
  `dsrutacaratula` varchar(600) DEFAULT NULL,
  `dscodigobarras` varchar(600) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtestimonio`),
  KEY `idescritura` (`idescritura`),
  KEY `idnotario` (`idnotario`),
  KEY `idusuarioelaboro` (`idusuarioelaboro`),
  CONSTRAINT `tbbsnm30_ibfk_1` FOREIGN KEY (`idescritura`) REFERENCES `tbbsnm24` (`idescritura`),
  CONSTRAINT `tbbsnm30_ibfk_2` FOREIGN KEY (`idnotario`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm30_ibfk_3` FOREIGN KEY (`idusuarioelaboro`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm30`
--

LOCK TABLES `tbbsnm30` WRITE;
/*!40000 ALTER TABLE `tbbsnm30` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm30` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm31`
--

DROP TABLE IF EXISTS `tbbsnm31`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm31` (
  `idtipocompareciente` varchar(32) NOT NULL,
  `dsnombre` varchar(60) NOT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtipocompareciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm31`
--

LOCK TABLES `tbbsnm31` WRITE;
/*!40000 ALTER TABLE `tbbsnm31` DISABLE KEYS */;
INSERT INTO `tbbsnm31` VALUES ('a87ff679a2f3e71d9181a67b7542122c','Representante','desc','sesion','2014-09-29 17:37:40'),('c4ca4238a0b923820dcc509a6f75849b','Comprador','desc','sesion','2014-09-29 17:37:40'),('c81e728d9d4c2f636f067f89cc14862c','Vendedor','desc','sesion','2014-09-29 17:37:40'),('e4da3b7fbbce2345d7772b0674a318d5','Autorizante','desc','sesion','2014-09-29 17:37:40'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Cónyuge','desc','sesion','2014-09-29 17:37:40');
/*!40000 ALTER TABLE `tbbsnm31` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm32`
--

DROP TABLE IF EXISTS `tbbsnm32`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm32` (
  `idexpediente` varchar(32) NOT NULL,
  `dsdescripcion` varchar(120) DEFAULT NULL,
  `fechainicial` date NOT NULL,
  `fechafinal` date DEFAULT NULL,
  `credito` double DEFAULT NULL,
  `dsreferencia` varchar(120) DEFAULT NULL,
  `iscotejo` int(1) DEFAULT '0',
  `dsstatus` varchar(120) DEFAULT NULL,
  `idabogado` varchar(32) DEFAULT NULL,
  `idtramite` varchar(32) DEFAULT NULL,
  `enumestatus` enum('CERRADO','CANCELADO','ABIERTO') NOT NULL,
  `dsmotivocierre` varchar(250) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `numexpediente` varchar(13) DEFAULT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idexpediente`),
  UNIQUE KEY `IDX_NUMERO_EXPEDIENTE` (`numexpediente`),
  KEY `idabogado` (`idabogado`),
  KEY `idtramite` (`idtramite`),
  CONSTRAINT `tbbsnm32_ibfk_1` FOREIGN KEY (`idabogado`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm32_ibfk_2` FOREIGN KEY (`idtramite`) REFERENCES `tbbsnm40` (`idtramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm32`
--

LOCK TABLES `tbbsnm32` WRITE;
/*!40000 ALTER TABLE `tbbsnm32` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm32` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm33`
--

DROP TABLE IF EXISTS `tbbsnm33`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm33` (
  `iddocnotpar` varchar(32) NOT NULL,
  `idescritura` varchar(32) NOT NULL,
  `fecha` date NOT NULL,
  `version` int(4) NOT NULL,
  `iscerrado` int(1) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `txtdoc` longtext,
  PRIMARY KEY (`iddocnotpar`),
  KEY `idescritura` (`idescritura`),
  CONSTRAINT `tbbsnm33_ibfk_1` FOREIGN KEY (`idescritura`) REFERENCES `tbbsnm24` (`idescritura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm33`
--

LOCK TABLES `tbbsnm33` WRITE;
/*!40000 ALTER TABLE `tbbsnm33` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm33` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm33b`
--

DROP TABLE IF EXISTS `tbbsnm33b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm33b` (
  `iddocnotpar` varchar(32) NOT NULL,
  `idescritura` varchar(32) NOT NULL,
  `fecha` date NOT NULL,
  `version` int(4) NOT NULL,
  `iscerrado` int(1) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `txtdoc` longtext,
  PRIMARY KEY (`iddocnotpar`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm33b`
--

LOCK TABLES `tbbsnm33b` WRITE;
/*!40000 ALTER TABLE `tbbsnm33b` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm33b` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm34`
--

DROP TABLE IF EXISTS `tbbsnm34`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm34` (
  `idciudad` varchar(32) NOT NULL,
  `dsnombre` varchar(30) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idciudad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm34`
--

LOCK TABLES `tbbsnm34` WRITE;
/*!40000 ALTER TABLE `tbbsnm34` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm34` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm35`
--

DROP TABLE IF EXISTS `tbbsnm35`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm35` (
  `iddocnotmas` varchar(32) NOT NULL,
  `idescritura` varchar(32) NOT NULL,
  `fecha` date NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `txtdoc` longtext,
  PRIMARY KEY (`iddocnotmas`),
  KEY `idescritura` (`idescritura`),
  CONSTRAINT `tbbsnm35_ibfk_1` FOREIGN KEY (`idescritura`) REFERENCES `tbbsnm24` (`idescritura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm35`
--

LOCK TABLES `tbbsnm35` WRITE;
/*!40000 ALTER TABLE `tbbsnm35` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm35` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm36`
--

DROP TABLE IF EXISTS `tbbsnm36`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm36` (
  `idescacto` varchar(32) NOT NULL,
  `idescritura` varchar(32) NOT NULL,
  `idacto` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idescacto`),
  KEY `idescritura` (`idescritura`),
  KEY `idacto` (`idacto`),
  CONSTRAINT `tbbsnm36_ibfk_1` FOREIGN KEY (`idescritura`) REFERENCES `tbbsnm24` (`idescritura`),
  CONSTRAINT `tbbsnm36_ibfk_2` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm36`
--

LOCK TABLES `tbbsnm36` WRITE;
/*!40000 ALTER TABLE `tbbsnm36` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm36` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm37`
--

DROP TABLE IF EXISTS `tbbsnm37`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm37` (
  `idactodoc` varchar(32) NOT NULL,
  `iddocumento` varchar(32) DEFAULT NULL,
  `idacto` varchar(32) NOT NULL,
  `idformatopdf` varchar(32) DEFAULT NULL,
  `idnotario` varchar(32) DEFAULT NULL,
  `idgestor` varchar(32) DEFAULT '',
  `idvaluador` varchar(32) DEFAULT '',
  `dsruta` varchar(600) DEFAULT NULL,
  `dsrutaformato` varchar(600) DEFAULT NULL,
  `fechasolicitud` date DEFAULT NULL,
  `fechaentrega` date DEFAULT NULL,
  `fechaaprobacion` date DEFAULT NULL,
  `issolicitado` int(1) DEFAULT NULL,
  `isentregado` int(1) DEFAULT NULL,
  `isaprovado` int(1) DEFAULT NULL,
  `txtFormato` longtext,
  `fechaArchivo` date DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idactodoc`),
  KEY `iddocumento` (`iddocumento`),
  KEY `idacto` (`idacto`),
  KEY `idgestor` (`idgestor`),
  KEY `idvaluador` (`idvaluador`),
  KEY `idnotario` (`idnotario`),
  KEY `idformatopdf` (`idformatopdf`),
  CONSTRAINT `tbbsnm37_ibfk_1` FOREIGN KEY (`iddocumento`) REFERENCES `tbbsnm22` (`iddocumento`),
  CONSTRAINT `tbbsnm37_ibfk_2` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`),
  CONSTRAINT `tbbsnm37_ibfk_3` FOREIGN KEY (`idgestor`) REFERENCES `tbbsnm63` (`idgestor`),
  CONSTRAINT `tbbsnm37_ibfk_4` FOREIGN KEY (`idvaluador`) REFERENCES `tbbsnm65` (`idvaluador`),
  CONSTRAINT `tbbsnm37_ibfk_5` FOREIGN KEY (`idnotario`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm37_ibfk_6` FOREIGN KEY (`idformatopdf`) REFERENCES `tbcfgm20` (`identificador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm37`
--

LOCK TABLES `tbbsnm37` WRITE;
/*!40000 ALTER TABLE `tbbsnm37` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm37` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm38`
--

DROP TABLE IF EXISTS `tbbsnm38`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm38` (
  `idetates` varchar(32) NOT NULL,
  `dsnombre` varchar(120) NOT NULL,
  `dsdescripcion` varchar(120) DEFAULT NULL,
  `inorden` int(3) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idetates`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm38`
--

LOCK TABLES `tbbsnm38` WRITE;
/*!40000 ALTER TABLE `tbbsnm38` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm38` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm39`
--

DROP TABLE IF EXISTS `tbbsnm39`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm39` (
  `idcomrep` varchar(32) NOT NULL,
  `idrepresentante` varchar(32) NOT NULL,
  `idrepresentado` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcomrep`),
  KEY `idrepresentante` (`idrepresentante`),
  KEY `idrepresentado` (`idrepresentado`),
  CONSTRAINT `tbbsnm39_ibfk_1` FOREIGN KEY (`idrepresentante`) REFERENCES `tbbsnm21` (`idcompareciente`),
  CONSTRAINT `tbbsnm39_ibfk_2` FOREIGN KEY (`idrepresentado`) REFERENCES `tbbsnm21` (`idcompareciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm39`
--

LOCK TABLES `tbbsnm39` WRITE;
/*!40000 ALTER TABLE `tbbsnm39` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm39` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm40`
--

DROP TABLE IF EXISTS `tbbsnm40`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm40` (
  `idtramite` varchar(32) NOT NULL,
  `idcliente` varchar(32) NOT NULL,
  `idabogado` varchar(32) NOT NULL,
  `idlocacion` varchar(32) DEFAULT NULL,
  `txtproposito` longtext,
  `idstatus` varchar(32) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idtramite`),
  KEY `idcliente` (`idcliente`),
  KEY `idabogado` (`idabogado`),
  KEY `idlocacion` (`idlocacion`),
  KEY `idstatus` (`idstatus`),
  CONSTRAINT `tbbsnm40_ibfk_1` FOREIGN KEY (`idcliente`) REFERENCES `tbbsnm28` (`idpersona`),
  CONSTRAINT `tbbsnm40_ibfk_2` FOREIGN KEY (`idabogado`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm40_ibfk_3` FOREIGN KEY (`idlocacion`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm40_ibfk_4` FOREIGN KEY (`idstatus`) REFERENCES `tbbsnm41` (`idetatra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm40`
--

LOCK TABLES `tbbsnm40` WRITE;
/*!40000 ALTER TABLE `tbbsnm40` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm40` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm41`
--

DROP TABLE IF EXISTS `tbbsnm41`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm41` (
  `idetatra` varchar(32) NOT NULL,
  `dsnombre` varchar(120) NOT NULL,
  `dsdescripcion` varchar(120) NOT NULL,
  `inorden` int(3) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idetatra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm41`
--

LOCK TABLES `tbbsnm41` WRITE;
/*!40000 ALTER TABLE `tbbsnm41` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm41` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm42`
--

DROP TABLE IF EXISTS `tbbsnm42`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm42` (
  `idbitgeneral` varchar(32) NOT NULL,
  `idusuario` varchar(32) NOT NULL,
  `idtramite` varchar(32) DEFAULT NULL,
  `idexpediente` varchar(32) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dsoperacion` varchar(60) DEFAULT NULL,
  `dsentidad` varchar(60) DEFAULT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`idbitgeneral`),
  KEY `idusuario` (`idusuario`),
  KEY `idtramite` (`idtramite`),
  KEY `idexpediente` (`idexpediente`),
  CONSTRAINT `tbbsnm42_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm42_ibfk_2` FOREIGN KEY (`idtramite`) REFERENCES `tbbsnm40` (`idtramite`),
  CONSTRAINT `tbbsnm42_ibfk_3` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm42`
--

LOCK TABLES `tbbsnm42` WRITE;
/*!40000 ALTER TABLE `tbbsnm42` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm42` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm43`
--

DROP TABLE IF EXISTS `tbbsnm43`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm43` (
  `idimpuesto` varchar(32) NOT NULL,
  `dsnombre` varchar(250) DEFAULT NULL,
  `dssiglas` varchar(10) DEFAULT NULL,
  `tasa` double DEFAULT NULL,
  `porcentaje` double DEFAULT NULL,
  PRIMARY KEY (`idimpuesto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm43`
--

LOCK TABLES `tbbsnm43` WRITE;
/*!40000 ALTER TABLE `tbbsnm43` DISABLE KEYS */;
INSERT INTO `tbbsnm43` VALUES ('c4ca4238a0b923820dcc509a6f75849b','IVA','IVA',0.16,0.16);
/*!40000 ALTER TABLE `tbbsnm43` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm44`
--

DROP TABLE IF EXISTS `tbbsnm44`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm44` (
  `idregri` varchar(32) NOT NULL,
  `dsnombre` varchar(250) DEFAULT NULL,
  `idexpedidopor` varchar(32) DEFAULT NULL,
  `idtipo` varchar(32) DEFAULT NULL,
  `numclave` varchar(60) DEFAULT NULL,
  `fechaadjuntado` date DEFAULT NULL,
  `dsruta` varchar(512) DEFAULT NULL,
  `isvalidadonotario` int(1) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idregri`),
  KEY `idexpedidopor` (`idexpedidopor`),
  KEY `idtipo` (`idtipo`),
  CONSTRAINT `tbbsnm44_ibfk_1` FOREIGN KEY (`idexpedidopor`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm44_ibfk_2` FOREIGN KEY (`idtipo`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm44`
--

LOCK TABLES `tbbsnm44` WRITE;
/*!40000 ALTER TABLE `tbbsnm44` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm44` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm45`
--

DROP TABLE IF EXISTS `tbbsnm45`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm45` (
  `idreletapatesti` varchar(32) NOT NULL,
  `idtestimonio` varchar(32) DEFAULT NULL,
  `idetapatestimonio` varchar(32) DEFAULT NULL,
  `fechaaprobada` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isaprobada` int(1) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`idreletapatesti`),
  KEY `idtestimonio` (`idtestimonio`),
  KEY `idetapatestimonio` (`idetapatestimonio`),
  CONSTRAINT `tbbsnm45_ibfk_1` FOREIGN KEY (`idtestimonio`) REFERENCES `tbbsnm30` (`idtestimonio`),
  CONSTRAINT `tbbsnm45_ibfk_2` FOREIGN KEY (`idetapatestimonio`) REFERENCES `tbbsnm38` (`idetates`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm45`
--

LOCK TABLES `tbbsnm45` WRITE;
/*!40000 ALTER TABLE `tbbsnm45` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm45` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm46`
--

DROP TABLE IF EXISTS `tbbsnm46`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm46` (
  `idconFormulario` varchar(32) NOT NULL,
  `version` int(5) NOT NULL DEFAULT '0',
  `dstitulo` varchar(120) DEFAULT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `dsnombrecorto` varchar(250) DEFAULT NULL,
  `ispublicado` int(1) DEFAULT NULL,
  `fechapublicacion` date DEFAULT NULL,
  `intipoform` enum('D','E','O','') DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idconFormulario`,`version`),
  UNIQUE KEY `dsnombrecorto` (`idconFormulario`,`version`,`dsnombrecorto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm46`
--

LOCK TABLES `tbbsnm46` WRITE;
/*!40000 ALTER TABLE `tbbsnm46` DISABLE KEYS */;
INSERT INTO `tbbsnm46` VALUES ('03b8df59c5d0ae36bbbc46192113b867',20,'Situación registral','gravamen descripción','gravamen',1,'2014-08-28','D','34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:11'),('2934e7e4228b917d5b3af5a352b3726d',1,'Exención ISR','Formulario de exención de ISR','subfrm',1,'2014-08-29','O','0A9C2D49F06407FEEF03BD57A8705E10','2014-09-24 03:36:05'),('63549fff6f5f3d27e1b6a636163b8f11',8,'Antecedentes de propiedad','Antecedentes de la propiedad','ante_propiedad',1,'2014-08-26','D','997548F10C95746B4D24187A8235344B','2014-08-26 15:02:03'),('7541cc4fcd5abb618c7dee111434bdd6',3,'Avaluo','Ingrese el valor del avaluo del inmueble','ant_avaluo',1,'2014-09-25','D','22F549FD500A57740F51DE36BE8EB558','2014-09-25 15:28:23'),('a7e3c62935fb0b5a6e576d52e5d2b7d5',13,'Operación','Datos para llevar acabo la operación de la escritura','operacion',1,'2014-08-29','O','B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('aee3dab120f23c8c78131fcb0e48273f',3,'Impuesto sobre la renta','Impuesto sobre la renta del inmueble','isr',0,'2014-08-29','O','139EA1AF0DB23AED8425AF1FAAF1BFE6','2014-08-29 19:15:06'),('f2ee49249350d40549e6e5597283e421',5,'Descripción del inmueble','Datos generales del inmueble','desc_inmueble',1,'2014-08-27','E','7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('f4e9f1f2c92225b5b26a9eaadfcfd200',1,'Situación catastral','situación catastral del inmueble','ant_sit_catastral',1,'2014-06-24','D','4BEA99FF3C3943E8344A2E340E21048B','2014-06-24 22:11:01');
/*!40000 ALTER TABLE `tbbsnm46` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm47`
--

DROP TABLE IF EXISTS `tbbsnm47`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm47` (
  `idactoformulario` varchar(32) NOT NULL,
  `idsuboperacion` varchar(32) DEFAULT NULL,
  `idconformulario` varchar(32) DEFAULT NULL,
  `versionform` int(5) DEFAULT NULL,
  `inposicion` int(5) DEFAULT '0' COMMENT 'Indica la posicion del formulario dentro del acto',
  `inestatus` enum('F','E','') DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idactoformulario`),
  KEY `idsuboperacion` (`idsuboperacion`),
  KEY `idconformulario` (`idconformulario`,`versionform`),
  CONSTRAINT `tbbsnm47_ibfk_1` FOREIGN KEY (`idsuboperacion`) REFERENCES `tbbsnm17` (`idsuboperacion`),
  CONSTRAINT `tbbsnm47_ibfk_2` FOREIGN KEY (`idconformulario`, `versionform`) REFERENCES `tbbsnm46` (`idconFormulario`, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm47`
--

LOCK TABLES `tbbsnm47` WRITE;
/*!40000 ALTER TABLE `tbbsnm47` DISABLE KEYS */;
INSERT INTO `tbbsnm47` VALUES ('100163fcd3593b84e6f3f259623fdcb4','c4ca4238a0b923820dcc509a6f75849b','7541cc4fcd5abb618c7dee111434bdd6',3,3,'','22F549FD500A57740F51DE36BE8EB558','2014-09-25 15:28:23'),('115f1dcd898e628300bddd96117dfbf7','c4ca4238a0b923820dcc509a6f75849b','a7e3c62935fb0b5a6e576d52e5d2b7d5',13,0,'','B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('177e410431ff6c75479da577b446a30b','c4ca4238a0b923820dcc509a6f75849b','f2ee49249350d40549e6e5597283e421',5,0,'','7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('3938469b14d04ccf6c5ac5d673491474','c4ca4238a0b923820dcc509a6f75849b','f4e9f1f2c92225b5b26a9eaadfcfd200',1,2,NULL,'sesion','2014-06-27 13:36:50'),('4faf9bb3d011ccac395592a66791a443','c4ca4238a0b923820dcc509a6f75849b','03b8df59c5d0ae36bbbc46192113b867',20,1,'','34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('768e5fc0f5a2a90d80b754bcaa2bfabe','c4ca4238a0b923820dcc509a6f75849b','2934e7e4228b917d5b3af5a352b3726d',1,0,'','0A9C2D49F06407FEEF03BD57A8705E10','2014-08-29 20:32:08'),('b20ed126481d391dbfdc1b90ff63119e','c4ca4238a0b923820dcc509a6f75849b','aee3dab120f23c8c78131fcb0e48273f',3,0,'','139EA1AF0DB23AED8425AF1FAAF1BFE6','2014-08-29 19:14:11'),('c91a8a9c4ffb441995d3ec78eebe940b','c4ca4238a0b923820dcc509a6f75849b','63549fff6f5f3d27e1b6a636163b8f11',8,0,'','997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04');
/*!40000 ALTER TABLE `tbbsnm47` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm48`
--

DROP TABLE IF EXISTS `tbbsnm48`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm48` (
  `idpermisorol` varchar(32) NOT NULL,
  `idrol` varchar(32) DEFAULT NULL,
  `idconformulario` varchar(32) DEFAULT NULL,
  `versionform` int(5) DEFAULT NULL,
  `inpermiso` int(5) DEFAULT '0',
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idpermisorol`),
  KEY `idrol` (`idrol`),
  KEY `idconformulario` (`idconformulario`,`versionform`),
  CONSTRAINT `tbbsnm48_ibfk_1` FOREIGN KEY (`idrol`) REFERENCES `tbcfgm07` (`idrol`),
  CONSTRAINT `tbbsnm48_ibfk_2` FOREIGN KEY (`idconformulario`, `versionform`) REFERENCES `tbbsnm46` (`idconFormulario`, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm48`
--

LOCK TABLES `tbbsnm48` WRITE;
/*!40000 ALTER TABLE `tbbsnm48` DISABLE KEYS */;
INSERT INTO `tbbsnm48` VALUES ('317bb4c45fabeaa9432e998010483709','eccbc87e4b5ce2fe28308fd9f2a7baf3','7541cc4fcd5abb618c7dee111434bdd6',3,0,'22F549FD500A57740F51DE36BE8EB558','2014-09-25 15:28:23'),('363d19cfe4677c80979a5c0351a09c12','eccbc87e4b5ce2fe28308fd9f2a7baf3','f4e9f1f2c92225b5b26a9eaadfcfd200',1,7,'4BEA99FF3C3943E8344A2E340E21048B','2014-06-24 22:11:01'),('6557130a51faa53acf7122e1c68ea40d','eccbc87e4b5ce2fe28308fd9f2a7baf3','63549fff6f5f3d27e1b6a636163b8f11',8,7,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('799bf09ad5be7647a61667a5f7ae46d8','c20ad4d76fe97759aa27a0c99bff6710','aee3dab120f23c8c78131fcb0e48273f',3,7,'139EA1AF0DB23AED8425AF1FAAF1BFE6','2014-08-29 19:14:11'),('841ecdced55d1ab83692f858e1f6386e','eccbc87e4b5ce2fe28308fd9f2a7baf3','f2ee49249350d40549e6e5597283e421',5,7,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('8ab52822e6f60a51950c27a8f6d0e88e','eccbc87e4b5ce2fe28308fd9f2a7baf3','03b8df59c5d0ae36bbbc46192113b867',20,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('cbdb8accd2842dad594bc6501b51a5a1','eccbc87e4b5ce2fe28308fd9f2a7baf3','aee3dab120f23c8c78131fcb0e48273f',3,7,'139EA1AF0DB23AED8425AF1FAAF1BFE6','2014-08-29 19:14:11'),('d9afc941478f36d13cde58c9a0be32af','eccbc87e4b5ce2fe28308fd9f2a7baf3','2934e7e4228b917d5b3af5a352b3726d',1,7,'0A9C2D49F06407FEEF03BD57A8705E10','2014-08-29 20:32:08'),('f82cd5afb3b3d0304b53aa7f1f1d21a9','eccbc87e4b5ce2fe28308fd9f2a7baf3','a7e3c62935fb0b5a6e576d52e5d2b7d5',13,7,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57');
/*!40000 ALTER TABLE `tbbsnm48` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm49`
--

DROP TABLE IF EXISTS `tbbsnm49`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm49` (
  `idconsubform` varchar(32) NOT NULL,
  `nombre` varchar(120) DEFAULT NULL,
  `dsnombrecorto` varchar(250) DEFAULT NULL,
  `inposicion` int(11) DEFAULT '0',
  `idconformulario` varchar(32) DEFAULT NULL,
  `versionform` int(5) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idconsubform`),
  UNIQUE KEY `dsnombrecorto` (`idconformulario`,`versionform`,`dsnombrecorto`),
  CONSTRAINT `tbbsnm49_ibfk_1` FOREIGN KEY (`idconformulario`, `versionform`) REFERENCES `tbbsnm46` (`idconFormulario`, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm49`
--

LOCK TABLES `tbbsnm49` WRITE;
/*!40000 ALTER TABLE `tbbsnm49` DISABLE KEYS */;
INSERT INTO `tbbsnm49` VALUES ('67e03b09a15048a9c5a7a3875f48d436',NULL,'subfrm_subform_0',0,'2934e7e4228b917d5b3af5a352b3726d',1,'0A9C2D49F06407FEEF03BD57A8705E10','2014-08-29 20:32:08');
/*!40000 ALTER TABLE `tbbsnm49` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm50`
--

DROP TABLE IF EXISTS `tbbsnm50`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm50` (
  `idcomponente` varchar(32) NOT NULL,
  `idtipocomponente` varchar(32) DEFAULT NULL,
  `dsetiqueta` varchar(120) DEFAULT NULL,
  `longitudminima` int(5) DEFAULT NULL,
  `longitudmaxima` int(5) DEFAULT NULL,
  `dsexpresionvalidacion` varchar(250) DEFAULT NULL,
  `dslistavalores` longtext,
  `dscatalogo` varchar(250) DEFAULT NULL,
  `dstablabusqueda` varchar(250) DEFAULT NULL,
  `dsnombrevariable` varchar(120) DEFAULT NULL,
  `dsayuda` varchar(600) DEFAULT NULL,
  `isrequerido` int(1) DEFAULT NULL,
  `idconformulario` varchar(32) DEFAULT NULL,
  `versionform` int(5) DEFAULT NULL,
  `inposicion` int(5) DEFAULT '0',
  `inorden` int(5) DEFAULT '0',
  `idsubformulario` varchar(32) DEFAULT NULL,
  `isparasubform` int(1) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcomponente`),
  KEY `idconformulario` (`idconformulario`,`versionform`),
  KEY `idsubformulario` (`idsubformulario`),
  KEY `idtipocomponente` (`idtipocomponente`),
  CONSTRAINT `tbbsnm50_ibfk_1` FOREIGN KEY (`idconformulario`, `versionform`) REFERENCES `tbbsnm46` (`idconFormulario`, `version`),
  CONSTRAINT `tbbsnm50_ibfk_2` FOREIGN KEY (`idsubformulario`) REFERENCES `tbbsnm49` (`idconsubform`),
  CONSTRAINT `tbbsnm50_ibfk_3` FOREIGN KEY (`idtipocomponente`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm50`
--

LOCK TABLES `tbbsnm50` WRITE;
/*!40000 ALTER TABLE `tbbsnm50` DISABLE KEYS */;
INSERT INTO `tbbsnm50` VALUES ('0514d66f677dfeaf6b585a995647d567','6c8349cc7260ae62e3b1396831a8398f','Nombre del notario',NULL,254,NULL,NULL,NULL,NULL,'ant_prop_nom_not','Ingresa el nombre del notario',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,3,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('0e7b43c47489a786f7da844d62b99fdb','6c8349cc7260ae62e3b1396831a8398f','Superficie',NULL,254,NULL,NULL,NULL,NULL,'inm_superficie','Ingresa la superficie del inmueble',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,13,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('12035270d4da81e3871b4e03c0e9947c','f457c545a9ded88f18ecee47145a72c0','Impuesto sobre la renta',NULL,NULL,NULL,'exento::Exento,pago::Pago,perdida::Pérdida,exc_pago::Exento y pago por excedencia,exc_per::Exento y pérdida por exedencia,pm::Persona moral,pfcae::Persona física con actividad empresarial',NULL,NULL,'isr_tipo','Ingrese el tipo de impuesto sobre la renta',NULL,NULL,NULL,0,1,'67e03b09a15048a9c5a7a3875f48d436',1,'0A9C2D49F06407FEEF03BD57A8705E10','2014-08-29 20:32:08'),('135657b1074c2e3bb6a279dea6ea67b4','6c8349cc7260ae62e3b1396831a8398f','Lugar',NULL,254,NULL,NULL,NULL,NULL,'ant_prop_lugar','Ingresa el lugar dónde se dió de alta el inmueble',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,7,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('15aa3cdcc598707df568ea816b78ecc0','6c8349cc7260ae62e3b1396831a8398f','Cantidad a pagar',NULL,30,NULL,NULL,NULL,NULL,'isr_pago','Ingresa la cantidad a pagar',NULL,NULL,NULL,0,2,'67e03b09a15048a9c5a7a3875f48d436',1,'0A9C2D49F06407FEEF03BD57A8705E10','2014-08-29 20:32:08'),('15e86e2c49c32804d2f0f08bb08b972e','6c8349cc7260ae62e3b1396831a8398f','Número de escritura',NULL,50,NULL,NULL,NULL,NULL,'grav_cancelacion_num_esc','Ingrese el número de escritura con el cual fue cancelado el gravamen',NULL,'03b8df59c5d0ae36bbbc46192113b867',20,NULL,3,NULL,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('18b509c9735048bccac39f31127f6ffc','6c8349cc7260ae62e3b1396831a8398f','Indiviso',NULL,NULL,NULL,NULL,NULL,NULL,'inm_indivisible','El inmueble es indivisible',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,16,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('1ca15854cc489148629ac65b2ab115b7','6c8349cc7260ae62e3b1396831a8398f','Número exterior',NULL,5,NULL,NULL,NULL,NULL,'inm_num_ext','Ingresa el número exterior del inmueble',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,3,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('24406724c37172eb4acf04e1b5d6a981','d9d4f495e875a2e075a1a4a6e1b9770f','Fecha de la escritura',NULL,NULL,NULL,NULL,NULL,NULL,'escritura_fecha_elaboracion',NULL,NULL,'03b8df59c5d0ae36bbbc46192113b867',20,NULL,4,NULL,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('2664efa749fd13ed73705a3b979b7b8a','6c8349cc7260ae62e3b1396831a8398f','Número de la notaría',NULL,3,NULL,NULL,NULL,NULL,'ant_prop_num_not','Ingrese el número de la notaría',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,4,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('2ea7d33a3670db80b6c7a2fb76b3a9e4','f457c545a9ded88f18ecee47145a72c0','Comparecientes',NULL,NULL,NULL,NULL,NULL,'comparecientes','isr_compareciente','Ingrese el nombre del compareciente a tratar',NULL,NULL,NULL,0,0,'67e03b09a15048a9c5a7a3875f48d436',1,'0A9C2D49F06407FEEF03BD57A8705E10','2014-09-08 23:52:01'),('3154c413c481b05e60c4bc4bec2f00de','6c8349cc7260ae62e3b1396831a8398f','Número de la notaría',NULL,10,NULL,NULL,NULL,NULL,'grav_cancelacion_num_not','Ingresa el número de la notaria con la cual se realizó la cancelación del gravamen',NULL,'03b8df59c5d0ae36bbbc46192113b867',20,NULL,6,NULL,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('32ebbad0fa281a8a1028196a1880088a','6c8349cc7260ae62e3b1396831a8398f','Fólio real',NULL,20,NULL,NULL,NULL,NULL,'ant_prop_folio_real','Ingrese el folio real de la escritura',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,5,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('35ca058a2856431feda7546ddd47151b','6c8349cc7260ae62e3b1396831a8398f','Colonia',NULL,254,NULL,NULL,NULL,NULL,'inm_colonia','Ingresa la colonia donde se encuentra ubicado el inmueble',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,7,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('39f72490dcba4e765f9056d9c2213cb0','6c8349cc7260ae62e3b1396831a8398f','Observaciones certificado libertad de gravamen',NULL,500,NULL,NULL,NULL,NULL,'op_observa_doc_grav','Ingresa las observaciones referentes al documento de certificado de libertad de gravamen',NULL,'a7e3c62935fb0b5a6e576d52e5d2b7d5',13,NULL,6,NULL,0,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('3a51f536e77cf647d6692ef10df41781','6c8349cc7260ae62e3b1396831a8398f','Valuador del inmueble',NULL,254,NULL,NULL,NULL,NULL,'ant_avaluo_valuador','Ingresa el nombre del valuador del inmueble',NULL,'7541cc4fcd5abb618c7dee111434bdd6',3,NULL,2,NULL,0,'22F549FD500A57740F51DE36BE8EB558','2014-09-25 15:28:23'),('3bdf78cb4067c2ee20447498084a5591','f457c545a9ded88f18ecee47145a72c0','Tipo de cancelación de gravámenes',NULL,NULL,NULL,'grav_lib::Libre de gravámenes,grav_mis::Cancelado mismo instrumento,grav_pre::Cancelado por separado previo,grav_pos::Cancelado por separado posterior',NULL,NULL,'grav_cancelado_tipo','Ingresa el método por el cual se cancelará el gravamen',NULL,'03b8df59c5d0ae36bbbc46192113b867',20,NULL,0,NULL,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('3da52ba09dcc11a17c566f77279d07d9','d9d4f495e875a2e075a1a4a6e1b9770f','Fecha de certificado de gravamen',NULL,NULL,NULL,NULL,NULL,NULL,'grav_cancelacion_fecha','Ingrese la fecha del certificado de cancelación de gravamen',NULL,'03b8df59c5d0ae36bbbc46192113b867',20,NULL,1,NULL,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('3f98bad1c063b2f7a88def76aea2dc00','67c6a1e7ce56d3d6fa748ab6d9af3fd7','Lista de observaciones',NULL,NULL,NULL,NULL,NULL,NULL,'2014-08-29T17:57:36.750Z',NULL,NULL,'a7e3c62935fb0b5a6e576d52e5d2b7d5',13,NULL,4,NULL,0,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('440815e95a1bee2326d1b388d4f11427','d82c8d1619ad8176d665453cfb2e55f0','Jornada notarial',NULL,NULL,NULL,NULL,NULL,NULL,'op_jorn_not','Jornada notarial',NULL,'a7e3c62935fb0b5a6e576d52e5d2b7d5',13,NULL,3,NULL,0,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('4969c9c7aa95a67cb93d48b2aa21ceac','6c8349cc7260ae62e3b1396831a8398f','Linderos',NULL,254,NULL,NULL,NULL,NULL,'inm_linderos','Ingresa los linderos del domicilio',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,15,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('4b8886d7fd53c1e395d4de1611a584ed','6c8349cc7260ae62e3b1396831a8398f','Calle',NULL,254,NULL,NULL,NULL,NULL,'inm_calle','Ingresa la calle donde se encuentra ubicado el inmueble',0,'f2ee49249350d40549e6e5597283e421',5,NULL,2,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('509ed70298eb44974858210c7a75e66c','d82c8d1619ad8176d665453cfb2e55f0','Se tiene el antecedente original a la vista',NULL,NULL,NULL,NULL,NULL,NULL,'antecedente_ala_vista','Ingrese si se tiene la información del antecedente a la vista',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,0,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('512cdf2670465158c100b0f23f0809b6','6c8349cc7260ae62e3b1396831a8398f','Referencia para documento de avalúo',NULL,254,NULL,NULL,NULL,NULL,'inm_referencia','Ingresa la referencia del inmueble',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,14,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-28 00:21:52'),('52ff1518445dc3a3238d99aa7edb879e','6c8349cc7260ae62e3b1396831a8398f','Estado',NULL,254,NULL,NULL,NULL,NULL,'inm_estado','Ingresa el Estado dónde se encuentra ubicado el domicilio',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,9,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('5338e3aedd7ac2b925e9569937751193','d9d4f495e875a2e075a1a4a6e1b9770f','Fecha del avaluo',NULL,NULL,NULL,NULL,NULL,NULL,'ant_avaluo_fecha','Ingresa la fecha del avaluo',NULL,'7541cc4fcd5abb618c7dee111434bdd6',3,NULL,1,NULL,0,'22F549FD500A57740F51DE36BE8EB558','2014-09-25 15:28:23'),('58dc4ad51fbde1744b1e3fbbd1df9916','6c8349cc7260ae62e3b1396831a8398f','Entidad',NULL,50,NULL,NULL,NULL,NULL,'grav_cancelacion_entidad','Ingrese la entidad a la que pertecene la notaría en la que fué dado de alta la cancelación',NULL,'03b8df59c5d0ae36bbbc46192113b867',20,NULL,7,NULL,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('5d2a079a979529ff8cb305f6b8c97718','f457c545a9ded88f18ecee47145a72c0','Uso de suelo del inmueble',NULL,NULL,NULL,'Casa_habitacion::Casa_habitación,Comercial::Comercial,Terreno::Terreno,Mixto::Mixto',NULL,NULL,'inm_uso_suelo','Ingresa el uso de suelo del inmueble',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,17,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('5de9a9aed0cb5a37e051cb32f8111b9c','d82c8d1619ad8176d665453cfb2e55f0','Pertenece a una escritura de la notaría',NULL,NULL,NULL,NULL,NULL,NULL,'grav_tiene_exp','Ingrese si la cancelación pertenece a algún expediente',NULL,'03b8df59c5d0ae36bbbc46192113b867',20,NULL,2,NULL,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('62a26103d7295a33095aa11a79d66e3a','6c8349cc7260ae62e3b1396831a8398f','Valor catastral',NULL,100,NULL,NULL,NULL,NULL,'ant_valor_catastral','Ingresa el valor catastral',NULL,'f4e9f1f2c92225b5b26a9eaadfcfd200',1,NULL,2,NULL,0,'4BEA99FF3C3943E8344A2E340E21048B','2014-06-24 22:11:01'),('639c5616f45ee7f70e376ad83ca2f83c','6c8349cc7260ae62e3b1396831a8398f','Lote',NULL,10,NULL,NULL,NULL,NULL,'inm_lote','Ingresa el lote donde se encuentra ubicado el domicilio',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,5,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('65edac5de9ae98cb8d20b9757a1d8234','6c8349cc7260ae62e3b1396831a8398f','Municipio',NULL,254,NULL,NULL,NULL,NULL,'inm_municipio','Ingresa el municipio donde se encuentra el domicilio',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,10,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('695882ef203d4ac1ffd1cdbc4cc0f93b','6c8349cc7260ae62e3b1396831a8398f','Costo de adquisición',NULL,254,NULL,NULL,NULL,NULL,'ant_prop_cost_adq','Ingresa los datos de adquisición del inmueble',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,6,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('71e13279e0bacdedd1a29f6c64594d8f','67c6a1e7ce56d3d6fa748ab6d9af3fd7','Detalle del inmueble',NULL,NULL,NULL,NULL,NULL,NULL,'2014-08-27T23:25:26.304Z',NULL,NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,12,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('812974b3632afcf6c3104f596bcf306d','6c8349cc7260ae62e3b1396831a8398f','Número de escritura',NULL,100,NULL,NULL,NULL,NULL,'ant_prop_num_escritura','Ingresa el número de escritura',0,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,2,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('8d4de196dbea832c7cc157073b09b903','6c8349cc7260ae62e3b1396831a8398f','Número interior',NULL,10,NULL,NULL,NULL,NULL,'inm_num_interior','Ingresa el número interior del domicilio',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,4,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('910e09a1dd86361d814c56520b8b684d','6c8349cc7260ae62e3b1396831a8398f','Manzana',NULL,50,NULL,NULL,NULL,NULL,'inm_manzana','Ingresa la manzana donde se encuentra ubicado el domicilio',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,6,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('94e75f9eea86e5b6d022b27c761024e0','6c8349cc7260ae62e3b1396831a8398f','Observaciones en escritura',NULL,500,NULL,NULL,NULL,NULL,'op_observa_esc','Ingresa las observaciones referentes a la escritura',NULL,'a7e3c62935fb0b5a6e576d52e5d2b7d5',13,NULL,5,NULL,0,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('97379910a24d2de5eb583b1eaa92ca5f','d9d4f495e875a2e075a1a4a6e1b9770f','fechados',NULL,NULL,NULL,NULL,NULL,NULL,'fechados',NULL,NULL,'7541cc4fcd5abb618c7dee111434bdd6',3,NULL,4,NULL,0,'22F549FD500A57740F51DE36BE8EB558','2014-09-25 15:28:23'),('9ecb6d485d966f282abd001b4f423f98','d9d4f495e875a2e075a1a4a6e1b9770f','Fecha prueba',NULL,NULL,NULL,NULL,NULL,NULL,'fecha_prueba',NULL,NULL,'7541cc4fcd5abb618c7dee111434bdd6',3,NULL,3,NULL,0,'22F549FD500A57740F51DE36BE8EB558','2014-09-25 15:28:23'),('a148f885bdbed9c83bd823d1b6293dbd','6c8349cc7260ae62e3b1396831a8398f','Nombre del notario',NULL,255,NULL,NULL,NULL,NULL,'grav_cancelacion_nom_not','Ingrese el nombre del notario el cual realizó la cancelación del gravamen',NULL,'03b8df59c5d0ae36bbbc46192113b867',20,NULL,5,NULL,0,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:41:12'),('a2d8f03c3e96366869be247a27848454','6c8349cc7260ae62e3b1396831a8398f','Descripción del inmueble',NULL,1000,NULL,NULL,NULL,NULL,'inm_desc_inmueble','Ingresa la descripción del inmueble',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,0,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('a64d85a3c972fa385ae989c3268a9f4a','642e92efb79421734881b53e1e1b18b6','Exención de ISR',NULL,NULL,NULL,NULL,NULL,NULL,'subform_0',NULL,0,'2934e7e4228b917d5b3af5a352b3726d',1,0,0,NULL,1,'0A9C2D49F06407FEEF03BD57A8705E10','2014-08-29 20:32:08'),('adcd44b1e840673955578e6fad1ad055','6c8349cc7260ae62e3b1396831a8398f','Observaciones constancia de folios',NULL,500,NULL,NULL,NULL,NULL,'op_observa_doc_folios','Ingresa las observaciones referentes al documento de constancia de folios',NULL,'a7e3c62935fb0b5a6e576d52e5d2b7d5',13,NULL,7,NULL,0,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('c0ac668bbf36003b9bf0d8583359f949','6c8349cc7260ae62e3b1396831a8398f','Cantidad no exenta',NULL,50,NULL,NULL,NULL,NULL,'isr_noexenta','Ingrese la cantidad no exenta',NULL,NULL,NULL,0,3,'67e03b09a15048a9c5a7a3875f48d436',1,'0A9C2D49F06407FEEF03BD57A8705E10','2014-08-29 20:32:08'),('c555c2060b93ed1cb315a00db58be2cc','f457c545a9ded88f18ecee47145a72c0','Impuesto sobre la renta',NULL,NULL,NULL,'exento::Exento,pago::Pago,perdida::Pérdida,exc_pago::Exento y pago por excedencia,exc_per::Exento y pérdida por exedencia,pm::Persona moral,pfcae::Persona física con actividad empresarial',NULL,NULL,'isr_tipo','Ingrese el tipo de impuesto sobre la renta',0,'aee3dab120f23c8c78131fcb0e48273f',3,NULL,0,NULL,0,'139EA1AF0DB23AED8425AF1FAAF1BFE6','2014-08-29 19:14:11'),('cdda17d95bf9a0f02e2a8b2c6e123e4a','6c8349cc7260ae62e3b1396831a8398f','Valor del avaluo',NULL,100,NULL,NULL,NULL,NULL,'ant_avaluo_valor','Ingresa el valor total del avaluo',0,'7541cc4fcd5abb618c7dee111434bdd6',3,NULL,0,NULL,0,'22F549FD500A57740F51DE36BE8EB558','2014-09-25 15:28:23'),('d63106d49339ed2fd85af015d514481f','6c8349cc7260ae62e3b1396831a8398f','Número de cuenta de agua',NULL,100,NULL,NULL,NULL,NULL,'ant_num_cta_agua','Ingresa el número de cuenta del agua',0,'f4e9f1f2c92225b5b26a9eaadfcfd200',1,NULL,0,NULL,0,'4BEA99FF3C3943E8344A2E340E21048B','2014-06-24 22:11:01'),('d6d0c7dd8345fb4d3425b4dbbeedc472','f457c545a9ded88f18ecee47145a72c0','Título',NULL,NULL,NULL,'Compraventa::Compraventa, Donación::Donación, Adjudicación::Adjudicación,Transmisión de dominio en ejecución de fideicomiso::Transmisión de dominio en ejecución de fideicomiso,Permuta::Permuta,Transmisión de dominio::Transmisión de dominio,Aplicación de bienes::Aplicación de bienes,Dación en pago::Dación en pago',NULL,NULL,'ant_prop_titulo','Ingrese en titulo de la propiedad',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,9,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('dab2023edcb77af8222fa31fb0abbd1f','67c6a1e7ce56d3d6fa748ab6d9af3fd7','Jornada notarial',NULL,NULL,NULL,NULL,NULL,NULL,'2014-08-29T17:57:36.729Z',NULL,NULL,'a7e3c62935fb0b5a6e576d52e5d2b7d5',13,NULL,2,NULL,0,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('dca3f7d227aa5febc761dfec5fb7dbae','6c8349cc7260ae62e3b1396831a8398f','Código Postal',NULL,10,NULL,NULL,NULL,NULL,'inm_codigo_postal','Ingresa el codigo postal donde se encuentra ubicado el domicilio',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,11,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('e2e9a552551b4d172b85ae7ac558558e','6c8349cc7260ae62e3b1396831a8398f','IVA',NULL,254,NULL,NULL,NULL,NULL,'op_iva','IVA solo cuando es mixto o empresarial, no cuando es casa habitación',NULL,'a7e3c62935fb0b5a6e576d52e5d2b7d5',13,NULL,1,NULL,0,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('e690107dc7165e5795e59a5aa746d580','d9d4f495e875a2e075a1a4a6e1b9770f','Fecha de la escritura',NULL,NULL,NULL,NULL,NULL,NULL,'ant_prop_fecha_esc','Ingresa la fecha de la escritura',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,8,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-26 15:02:04'),('e6b173b6931e1d24754e8669ebc9a520','6c8349cc7260ae62e3b1396831a8398f','Número de cuenta de predial',NULL,100,NULL,NULL,NULL,NULL,'ant_num_cta_predial','Ingresa el número de cuenta del predial',NULL,'f4e9f1f2c92225b5b26a9eaadfcfd200',1,NULL,1,NULL,0,'4BEA99FF3C3943E8344A2E340E21048B','2014-06-24 22:11:01'),('eadec35ff9297ee676fec2095b1a4364','6c8349cc7260ae62e3b1396831a8398f','Precio',NULL,200,NULL,NULL,NULL,NULL,'op_precio','Ingrese el precio del inmueble',0,'a7e3c62935fb0b5a6e576d52e5d2b7d5',13,NULL,0,NULL,0,'B477B2767B29DEB1EC9293B4C72F9144','2014-08-29 17:59:57'),('eb3ce7773d76d7dc4886d49fb191f744','d82c8d1619ad8176d665453cfb2e55f0','Arrendamiento',NULL,NULL,NULL,NULL,NULL,NULL,'inm_arrendamiento','Ingresa si el inmueble es arrendado',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,18,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('ee46c58663ce9ec06b25daa0cae6746a','6c8349cc7260ae62e3b1396831a8398f','Lugar de registro',NULL,254,NULL,NULL,NULL,NULL,'ant_prop_datos_ins','ingresa los datos de inscripción',NULL,'63549fff6f5f3d27e1b6a636163b8f11',8,NULL,1,NULL,0,'997548F10C95746B4D24187A8235344B','2014-08-28 00:20:00'),('ee6f784bd44f59b1daa7bc51bf61570f','6c8349cc7260ae62e3b1396831a8398f','Delegación',NULL,254,NULL,NULL,NULL,NULL,'inm_delegacion','Ingresa la delegación donde se encuentra ubicado el domicilio',NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,8,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-08-27 23:25:54'),('ef037c256b682ca552838b72a2dea839','67c6a1e7ce56d3d6fa748ab6d9af3fd7','Datos del inmueble (Información para documentos)',NULL,NULL,NULL,NULL,NULL,NULL,'2014-08-27T23:25:26.275Z',NULL,NULL,'f2ee49249350d40549e6e5597283e421',5,NULL,1,NULL,0,'7DDD52DF53629FC5DD65D8DCC3318020','2014-09-24 01:13:10');
/*!40000 ALTER TABLE `tbbsnm50` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm51`
--

DROP TABLE IF EXISTS `tbbsnm51`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm51` (
  `idformulario` varchar(32) NOT NULL,
  `idacto` varchar(32) DEFAULT NULL,
  `idconformulario` varchar(32) DEFAULT NULL,
  `versionform` int(5) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idformulario`),
  KEY `idacto` (`idacto`),
  KEY `idconformulario` (`idconformulario`,`versionform`),
  CONSTRAINT `tbbsnm51_ibfk_1` FOREIGN KEY (`idacto`) REFERENCES `tbbsnm18` (`idacto`),
  CONSTRAINT `tbbsnm51_ibfk_2` FOREIGN KEY (`idconformulario`, `versionform`) REFERENCES `tbbsnm46` (`idconFormulario`, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm51`
--

LOCK TABLES `tbbsnm51` WRITE;
/*!40000 ALTER TABLE `tbbsnm51` DISABLE KEYS */;
INSERT INTO `tbbsnm51` VALUES ('5a71b1a4671cec33ae340f0781287d8c','0c5339605de68339bb861df2d78247cc','a7e3c62935fb0b5a6e576d52e5d2b7d5',13,'C0440FF4550C921BDFFF176FF598B36C','2014-09-24 03:28:33'),('92f2dd76f2e97a7a51a0573a4734888d','27a24b5317a3ddfec8b2ae1ad4667a9e','a7e3c62935fb0b5a6e576d52e5d2b7d5',13,'C0440FF4550C921BDFFF176FF598B36C','2014-09-24 04:26:09'),('98b1dae1f6776b8d4554d0a8cb745bbc','0c5339605de68339bb861df2d78247cc','f4e9f1f2c92225b5b26a9eaadfcfd200',1,'C0440FF4550C921BDFFF176FF598B36C','2014-09-24 03:29:32'),('9fadf44e6da16a0b04c53696b14c8064','0c5339605de68339bb861df2d78247cc','2934e7e4228b917d5b3af5a352b3726d',1,'482FBDD7A768B5791AE783BB88CD708D','2014-09-24 18:32:19'),('a722f437223096d3beecbef4dc073b64','0c5339605de68339bb861df2d78247cc','f2ee49249350d40549e6e5597283e421',5,'C0440FF4550C921BDFFF176FF598B36C','2014-09-24 04:05:43'),('e9919bc3984b56632b34bd26336275f4','0c5339605de68339bb861df2d78247cc','03b8df59c5d0ae36bbbc46192113b867',20,'E813B28CC5204083A421634553E62012','2014-09-24 01:21:03'),('f173188aded805df186c5ba706e7333f','0c5339605de68339bb861df2d78247cc','63549fff6f5f3d27e1b6a636163b8f11',8,'E813B28CC5204083A421634553E62012','2014-09-24 01:20:35');
/*!40000 ALTER TABLE `tbbsnm51` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm52`
--

DROP TABLE IF EXISTS `tbbsnm52`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm52` (
  `idvalorform` varchar(32) NOT NULL,
  `idformulario` varchar(32) DEFAULT NULL,
  `idcomponente` varchar(32) DEFAULT NULL,
  `valorcadena` varchar(600) DEFAULT NULL,
  `valorentero` int(11) DEFAULT NULL,
  `valordoble` double DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idvalorform`),
  KEY `idformulario` (`idformulario`),
  KEY `idcomponente` (`idcomponente`),
  CONSTRAINT `tbbsnm52_ibfk_1` FOREIGN KEY (`idformulario`) REFERENCES `tbbsnm51` (`idformulario`),
  CONSTRAINT `tbbsnm52_ibfk_2` FOREIGN KEY (`idcomponente`) REFERENCES `tbbsnm50` (`idcomponente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm52`
--

LOCK TABLES `tbbsnm52` WRITE;
/*!40000 ALTER TABLE `tbbsnm52` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm52` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm53`
--

DROP TABLE IF EXISTS `tbbsnm53`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm53` (
  `idvalorsubform` varchar(32) NOT NULL,
  `idformulario` varchar(32) DEFAULT NULL,
  `idcomponente` varchar(32) DEFAULT NULL,
  `registro` int(11) DEFAULT NULL,
  `valorcadena` varchar(600) DEFAULT NULL,
  `valorentero` int(11) DEFAULT NULL,
  `valordoble` double DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idvalorsubform`),
  KEY `idformulario` (`idformulario`),
  KEY `idcomponente` (`idcomponente`),
  CONSTRAINT `tbbsnm53_ibfk_1` FOREIGN KEY (`idformulario`) REFERENCES `tbbsnm51` (`idformulario`),
  CONSTRAINT `tbbsnm53_ibfk_2` FOREIGN KEY (`idcomponente`) REFERENCES `tbbsnm50` (`idcomponente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm53`
--

LOCK TABLES `tbbsnm53` WRITE;
/*!40000 ALTER TABLE `tbbsnm53` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm53` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm54`
--

DROP TABLE IF EXISTS `tbbsnm54`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm54` (
  `idsolpago` varchar(32) NOT NULL,
  `idexpediente` varchar(32) DEFAULT NULL,
  `concepto` varchar(600) DEFAULT NULL,
  `valor` double DEFAULT NULL,
  `isanticipo` int(1) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idsolpago`),
  KEY `idexpediente` (`idexpediente`),
  CONSTRAINT `tbbsnm54_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm54`
--

LOCK TABLES `tbbsnm54` WRITE;
/*!40000 ALTER TABLE `tbbsnm54` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm54` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm55`
--

DROP TABLE IF EXISTS `tbbsnm55`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm55` (
  `iddatofiscapago` varchar(32) NOT NULL,
  `idsolicitudpago` varchar(32) DEFAULT NULL,
  `idcompareciente` varchar(32) DEFAULT NULL,
  `innumpago` int(11) DEFAULT NULL,
  `dsnombrepagador` varchar(600) DEFAULT NULL,
  `dsrfc` varchar(13) DEFAULT NULL,
  `dscurp` varchar(18) DEFAULT NULL,
  `dscorreoelectronico` varchar(60) DEFAULT NULL,
  `dsdircompleta` longtext,
  `status` varchar(32) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddatofiscapago`),
  KEY `idsolicitudpago` (`idsolicitudpago`),
  KEY `status` (`status`),
  KEY `idcompareciente` (`idcompareciente`),
  CONSTRAINT `tbbsnm55_ibfk_1` FOREIGN KEY (`idsolicitudpago`) REFERENCES `tbbsnm54` (`idsolpago`),
  CONSTRAINT `tbbsnm55_ibfk_2` FOREIGN KEY (`status`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm55_ibfk_3` FOREIGN KEY (`idcompareciente`) REFERENCES `tbbsnm21` (`idcompareciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm55`
--

LOCK TABLES `tbbsnm55` WRITE;
/*!40000 ALTER TABLE `tbbsnm55` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm55` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm56`
--

DROP TABLE IF EXISTS `tbbsnm56`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm56` (
  `iddatopago` varchar(32) NOT NULL,
  `iddatofiscapago` varchar(32) DEFAULT NULL,
  `fechapago` date DEFAULT NULL,
  `idusuarioelaboro` varchar(32) DEFAULT NULL,
  `facturaserie` varchar(120) DEFAULT NULL,
  `facturafolio` varchar(120) DEFAULT NULL,
  `isenviacorreo` int(1) DEFAULT NULL,
  `txtnota` longtext,
  `rutaarchivoxml` varchar(600) DEFAULT NULL,
  `idmediopago` varchar(32) DEFAULT NULL,
  `importepago` double DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`iddatopago`),
  KEY `iddatofiscapago` (`iddatofiscapago`),
  KEY `idmediopago` (`idmediopago`),
  KEY `idusuarioelaboro` (`idusuarioelaboro`),
  CONSTRAINT `tbbsnm56_ibfk_1` FOREIGN KEY (`iddatofiscapago`) REFERENCES `tbbsnm55` (`iddatofiscapago`),
  CONSTRAINT `tbbsnm56_ibfk_2` FOREIGN KEY (`idmediopago`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm56_ibfk_3` FOREIGN KEY (`idusuarioelaboro`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm56`
--

LOCK TABLES `tbbsnm56` WRITE;
/*!40000 ALTER TABLE `tbbsnm56` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm56` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm57`
--

DROP TABLE IF EXISTS `tbbsnm57`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm57` (
  `idcita` varchar(32) NOT NULL,
  `version` int(5) NOT NULL,
  `dsestatus` varchar(60) DEFAULT NULL,
  `fechainicio` timestamp NULL DEFAULT NULL,
  `fechatermino` timestamp NULL DEFAULT NULL,
  `dsactividad` varchar(250) DEFAULT NULL,
  `isreprogramdo` int(1) DEFAULT NULL,
  `dsdescripcion` text,
  `notificarcorreo` int(1) DEFAULT NULL,
  `idexpediente` varchar(32) DEFAULT NULL,
  `idusragendo` varchar(32) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`idcita`,`version`),
  KEY `idexpediente` (`idexpediente`),
  KEY `idusragendo` (`idusragendo`),
  CONSTRAINT `tbbsnm57_ibfk_1` FOREIGN KEY (`idexpediente`) REFERENCES `tbbsnm32` (`idexpediente`),
  CONSTRAINT `tbbsnm57_ibfk_2` FOREIGN KEY (`idusragendo`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm57`
--

LOCK TABLES `tbbsnm57` WRITE;
/*!40000 ALTER TABLE `tbbsnm57` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm57` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm58`
--

DROP TABLE IF EXISTS `tbbsnm58`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm58` (
  `idinvitado` varchar(32) NOT NULL,
  `idusuario` varchar(32) DEFAULT NULL,
  `idcompareciente` varchar(32) DEFAULT NULL,
  `idcita` varchar(32) NOT NULL,
  `version` int(5) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idinvitado`),
  KEY `idcita` (`idcita`,`version`),
  KEY `idusuario` (`idusuario`),
  KEY `idcompareciente` (`idcompareciente`),
  CONSTRAINT `tbbsnm58_ibfk_1` FOREIGN KEY (`idcita`, `version`) REFERENCES `tbbsnm57` (`idcita`, `version`),
  CONSTRAINT `tbbsnm58_ibfk_2` FOREIGN KEY (`idusuario`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbbsnm58_ibfk_3` FOREIGN KEY (`idcompareciente`) REFERENCES `tbbsnm21` (`idcompareciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm58`
--

LOCK TABLES `tbbsnm58` WRITE;
/*!40000 ALTER TABLE `tbbsnm58` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm58` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm59`
--

DROP TABLE IF EXISTS `tbbsnm59`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm59` (
  `idanexo` varchar(32) NOT NULL,
  `iddocnotmas` varchar(32) DEFAULT NULL,
  `idcita` varchar(32) NOT NULL,
  `version` int(5) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idanexo`),
  KEY `iddocnotmas` (`iddocnotmas`),
  KEY `idcita` (`idcita`,`version`),
  CONSTRAINT `tbbsnm59_ibfk_1` FOREIGN KEY (`iddocnotmas`) REFERENCES `tbbsnm35` (`iddocnotmas`),
  CONSTRAINT `tbbsnm59_ibfk_2` FOREIGN KEY (`idcita`, `version`) REFERENCES `tbbsnm57` (`idcita`, `version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm59`
--

LOCK TABLES `tbbsnm59` WRITE;
/*!40000 ALTER TABLE `tbbsnm59` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm59` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm60`
--

DROP TABLE IF EXISTS `tbbsnm60`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm60` (
  `idacuse` varchar(32) NOT NULL,
  `idescritura` varchar(32) NOT NULL,
  `enumentrega` enum('Mensajeria','Notaria') DEFAULT NULL,
  `fchentrega` date NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idacuse`),
  KEY `idescritura` (`idescritura`),
  CONSTRAINT `tbbsnm60_ibfk_1` FOREIGN KEY (`idescritura`) REFERENCES `tbbsnm24` (`idescritura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm60`
--

LOCK TABLES `tbbsnm60` WRITE;
/*!40000 ALTER TABLE `tbbsnm60` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm60` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm60b`
--

DROP TABLE IF EXISTS `tbbsnm60b`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm60b` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dsnombrecompleto` varchar(250) NOT NULL,
  `idacuse` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idacuse` (`idacuse`),
  CONSTRAINT `tbbsnm60b_ibfk_1` FOREIGN KEY (`idacuse`) REFERENCES `tbbsnm60` (`idacuse`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm60b`
--

LOCK TABLES `tbbsnm60b` WRITE;
/*!40000 ALTER TABLE `tbbsnm60b` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm60b` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm61`
--

DROP TABLE IF EXISTS `tbbsnm61`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm61` (
  `idsujeto` varchar(32) NOT NULL,
  `idconyugecompra` varchar(32) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idconyugeactual` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`idsujeto`),
  KEY `idconyugecompra` (`idconyugecompra`),
  KEY `idconyugeactual` (`idconyugeactual`),
  CONSTRAINT `tbbsnm61_ibfk_1` FOREIGN KEY (`idsujeto`) REFERENCES `tbbsnm21` (`idcompareciente`),
  CONSTRAINT `tbbsnm61_ibfk_2` FOREIGN KEY (`idconyugecompra`) REFERENCES `tbbsnm21` (`idcompareciente`),
  CONSTRAINT `tbbsnm61_ibfk_3` FOREIGN KEY (`idconyugeactual`) REFERENCES `tbbsnm21` (`idcompareciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm61`
--

LOCK TABLES `tbbsnm61` WRITE;
/*!40000 ALTER TABLE `tbbsnm61` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm61` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm62`
--

DROP TABLE IF EXISTS `tbbsnm62`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm62` (
  `identificador` int(11) NOT NULL AUTO_INCREMENT,
  `idoperacion` varchar(32) NOT NULL,
  `dscampo` varchar(90) NOT NULL,
  `dscodigo` varchar(10) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`identificador`),
  KEY `idoperacion` (`idoperacion`),
  CONSTRAINT `tbbsnm62_ibfk_1` FOREIGN KEY (`idoperacion`) REFERENCES `tbbsnm27` (`idoperacion`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm62`
--

LOCK TABLES `tbbsnm62` WRITE;
/*!40000 ALTER TABLE `tbbsnm62` DISABLE KEYS */;
INSERT INTO `tbbsnm62` VALUES (1,'c4ca4238a0b923820dcc509a6f75849b','Derechos de RPP','rpp','sesion','2014-09-29 17:37:45'),(10,'c4ca4238a0b923820dcc509a6f75849b','Erogaciones','ero','sesion','2014-09-29 17:37:45'),(21,'c4ca4238a0b923820dcc509a6f75849b','Honorarios','hon','sesion','2014-09-29 17:37:45'),(38,'c4ca4238a0b923820dcc509a6f75849b','IVA','iva','sesion','2014-09-29 17:37:45'),(56,'c4ca4238a0b923820dcc509a6f75849b','ISAI, TD, Impuestos','iti','sesion','2014-09-29 17:37:45');
/*!40000 ALTER TABLE `tbbsnm62` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm63`
--

DROP TABLE IF EXISTS `tbbsnm63`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm63` (
  `idgestor` varchar(32) NOT NULL,
  `dsnombre` varchar(60) NOT NULL,
  `dspaterno` varchar(60) NOT NULL,
  `dsmaterno` varchar(60) NOT NULL,
  `dstelefono` varchar(10) DEFAULT NULL,
  `dscorreo` varchar(60) DEFAULT NULL,
  `dsrfc` varchar(13) DEFAULT NULL,
  `dscurp` varchar(18) DEFAULT NULL,
  `dsempresa` varchar(90) NOT NULL,
  `inprecio` double DEFAULT NULL,
  `idlocacion` varchar(32) NOT NULL,
  `inestatus` int(1) NOT NULL DEFAULT '1',
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idgestor`),
  KEY `idlocacion` (`idlocacion`),
  CONSTRAINT `tbbsnm63_ibfk_1` FOREIGN KEY (`idlocacion`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm63`
--

LOCK TABLES `tbbsnm63` WRITE;
/*!40000 ALTER TABLE `tbbsnm63` DISABLE KEYS */;
INSERT INTO `tbbsnm63` VALUES ('a87ff679a2f3e71d9181a67b7542122c','Ernesto','Galicia','Martínez','','','','','',0,'1c383cd30b7c298ab50293adfecb7b18',1,'sesion','2014-09-29 17:37:45'),('c4ca4238a0b923820dcc509a6f75849b','Remy Omar','Alaniz','Mendoza','','','','','',0,'1c383cd30b7c298ab50293adfecb7b18',1,'sesion','2014-09-29 17:37:45'),('c81e728d9d4c2f636f067f89cc14862c','Christopher','Gonzalez','Avila','','','','','',0,'1c383cd30b7c298ab50293adfecb7b18',1,'sesion','2014-09-29 17:37:45'),('e4da3b7fbbce2345d7772b0674a318d5','Juan Carlos','Flores','Díaz','','','','','',0,'1c383cd30b7c298ab50293adfecb7b18',1,'sesion','2014-09-29 17:37:45'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Reynaldo','Zarco','Ayala','','','','','',0,'1c383cd30b7c298ab50293adfecb7b18',1,'sesion','2014-09-29 17:37:45');
/*!40000 ALTER TABLE `tbbsnm63` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm64`
--

DROP TABLE IF EXISTS `tbbsnm64`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm64` (
  `identificador` int(11) NOT NULL AUTO_INCREMENT,
  `iddocumento` varchar(32) DEFAULT NULL,
  `idsuboperacion` varchar(32) DEFAULT NULL,
  `idlocalidad` varchar(32) DEFAULT NULL,
  `idformatopdf` varchar(32) DEFAULT NULL,
  `inorden` int(11) DEFAULT NULL,
  `isgestionado` varchar(1) DEFAULT '',
  PRIMARY KEY (`identificador`),
  KEY `iddocumento` (`iddocumento`),
  KEY `idsuboperacion` (`idsuboperacion`),
  KEY `idlocalidad` (`idlocalidad`),
  KEY `idformatopdf` (`idformatopdf`),
  CONSTRAINT `tbbsnm64_ibfk_1` FOREIGN KEY (`iddocumento`) REFERENCES `tbbsnm22` (`iddocumento`),
  CONSTRAINT `tbbsnm64_ibfk_2` FOREIGN KEY (`idsuboperacion`) REFERENCES `tbbsnm17` (`idsuboperacion`),
  CONSTRAINT `tbbsnm64_ibfk_3` FOREIGN KEY (`idlocalidad`) REFERENCES `tbcfgm91` (`idelemento`),
  CONSTRAINT `tbbsnm64_ibfk_4` FOREIGN KEY (`idformatopdf`) REFERENCES `tbcfgm20` (`identificador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm64`
--

LOCK TABLES `tbbsnm64` WRITE;
/*!40000 ALTER TABLE `tbbsnm64` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm64` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm65`
--

DROP TABLE IF EXISTS `tbbsnm65`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm65` (
  `idvaluador` varchar(255) NOT NULL,
  `dsnombre` varchar(60) NOT NULL,
  `dspaterno` varchar(60) NOT NULL,
  `dsmaterno` varchar(60) NOT NULL,
  `dsempresa` varchar(90) NOT NULL,
  `dsrfc` varchar(13) DEFAULT NULL,
  `dscurp` varchar(18) DEFAULT NULL,
  `dscorreo` varchar(60) DEFAULT NULL,
  `dstelefono` varchar(10) DEFAULT NULL,
  `dsmovil` varchar(10) DEFAULT NULL,
  `inprecio` double DEFAULT NULL,
  `inestatus` int(1) NOT NULL DEFAULT '1',
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idvaluador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm65`
--

LOCK TABLES `tbbsnm65` WRITE;
/*!40000 ALTER TABLE `tbbsnm65` DISABLE KEYS */;
INSERT INTO `tbbsnm65` VALUES ('c4ca4238a0b923820dcc509a6f75849b','Juan Pablo','Lopez','Cuevas','AVE, Asesoría, Valuación y Estudio Unidad de Valuación S.A de C.V','','','','','',0,1,'sesion','2014-09-29 17:37:45'),('c81e728d9d4c2f636f067f89cc14862c','Alberto','Lopez','Cuevas','AVE, Asesoría, Valuación y Estudio Unidad de Valuación S.A de C.V','','','','','',0,1,'sesion','2014-09-29 17:37:45'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Santiago','Morales','Broc','Broc Avalúos S.C','','','','','',0,1,'sesion','2014-09-29 17:37:45');
/*!40000 ALTER TABLE `tbbsnm65` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm66`
--

DROP TABLE IF EXISTS `tbbsnm66`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm66` (
  `idcompareciente` varchar(32) NOT NULL,
  `idautorizante` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcompareciente`,`idautorizante`),
  KEY `idautorizante` (`idautorizante`),
  CONSTRAINT `tbbsnm66_ibfk_1` FOREIGN KEY (`idcompareciente`) REFERENCES `tbbsnm21` (`idcompareciente`),
  CONSTRAINT `tbbsnm66_ibfk_2` FOREIGN KEY (`idautorizante`) REFERENCES `tbbsnm21` (`idcompareciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm66`
--

LOCK TABLES `tbbsnm66` WRITE;
/*!40000 ALTER TABLE `tbbsnm66` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm66` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbbsnm67`
--

DROP TABLE IF EXISTS `tbbsnm67`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbsnm67` (
  `idcontacto` varchar(32) NOT NULL,
  `idpersona` varchar(32) NOT NULL,
  `dstelefono` varchar(30) DEFAULT NULL,
  `dscorreoelectronico` varchar(60) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcontacto`),
  KEY `idpersona` (`idpersona`),
  CONSTRAINT `tbbsnm67_ibfk_1` FOREIGN KEY (`idpersona`) REFERENCES `tbbsnm28` (`idpersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbsnm67`
--

LOCK TABLES `tbbsnm67` WRITE;
/*!40000 ALTER TABLE `tbbsnm67` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbbsnm67` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm03`
--

DROP TABLE IF EXISTS `tbcfgm03`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm03` (
  `idusuario` varchar(32) NOT NULL,
  `dsnombre` varchar(30) NOT NULL,
  `dspaterno` varchar(30) NOT NULL,
  `dsmaterno` varchar(30) DEFAULT NULL,
  `idrol` varchar(32) NOT NULL,
  `cdusuario` varchar(16) NOT NULL,
  `dsvalenc` varchar(41) NOT NULL,
  `dscorreo` varchar(120) DEFAULT NULL,
  `dsrfc` varchar(13) DEFAULT NULL,
  `dscurp` varchar(18) DEFAULT NULL,
  `dsiniciales` varchar(5) NOT NULL,
  `dsreferencia` varchar(250) DEFAULT NULL,
  `isactualizapwd` int(1) NOT NULL DEFAULT '0',
  `inestatus` int(1) NOT NULL DEFAULT '1',
  `isactivo` int(1) NOT NULL DEFAULT '1',
  `dsultimoacceso` varchar(15) DEFAULT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fchinicio` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fchfin` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `idsesion` varchar(32) NOT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `cdusuario_UNIQUE` (`cdusuario`),
  KEY `idrol` (`idrol`),
  CONSTRAINT `tbcfgm03_ibfk_1` FOREIGN KEY (`idrol`) REFERENCES `tbcfgm07` (`idrol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm03`
--

LOCK TABLES `tbcfgm03` WRITE;
/*!40000 ALTER TABLE `tbcfgm03` DISABLE KEYS */;
INSERT INTO `tbcfgm03` VALUES ('1679091c5a880faf6fb5e6087eb1b2dc','Gonzalo M.','Ortíz','Blanco','45c48cce2e2d7fbdea1afc51c7c6ad26','gortiz','c2d5f363d0ff4d943bd1287f8da344d835417ad1','gortiz@notarias98y24.com.mx','GOBL7904057K8',NULL,'GOB','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:44','2014-09-29 17:37:44','2014-09-29 17:37:44','sesion 1'),('26657d5ff9020d2abefe558796b99584','Marlen','Guerrero','Orozco','eccbc87e4b5ce2fe28308fd9f2a7baf3','mguerrero','8c09bd2e211e6613bdbd407c815343430c3a2071','','',NULL,'MGO','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion 1'),('3295c76acbf4caaed33c36b1b5fc2cb1','Carlos','Ceron','Nacif','eccbc87e4b5ce2fe28308fd9f2a7baf3','cceron','dfd10a600b3e128d2bcaeccb55b289e797a65100','','',NULL,'CCN','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:44','2014-09-29 17:37:44','2014-09-29 17:37:44','sesion 1'),('640f36aa97d0ae6ba8d17a3b5c34186e','Marisela','Ramirez','Martinez','e4da3b7fbbce2345d7772b0674a318d5','cajera','281059ade658c3952197f1a498a050582a9ac915','caja@caja.com','MPDE7908078J9','MPDE790807HDFLRM65','MRM','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion'),('879d1dc82fedd85c55fa77ef26a11dcf','Octavio','Ugalde','Paz','e4da3b7fbbce2345d7772b0674a318d5','cajero1','b4dea840eb0bd3bb0e00038ced4cb3e9132c1a33','caja@caja.com','OEUP7903047J8','OEUP790402HDFLRM78','OUP','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion'),('8f14e45fceea167a5a36dedd4bea2543','Luis Ricardo','Duarte','Guerra','45c48cce2e2d7fbdea1afc51c7c6ad26','rduarte','7dac827fbd88f910ad6cc23e39026e7ec719646e','rduarte@notarias98y24.com.mx','LDUG7403057J3',NULL,'LDG','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:44','2014-09-29 17:37:44','2014-09-29 17:37:44','sesion 1'),('93db85ed909c13838ff95ccfa94cebd9','Aranzazu','Huacuja','De La Torre','eccbc87e4b5ce2fe28308fd9f2a7baf3','ahuacuja','9b6bf368a10c2ff8551eee95e444279c812e0d12','','',NULL,'AHDLT','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion 1'),('9f61408e3afb633e50cdf1b20de6f466','Selene','Porcayo','','eccbc87e4b5ce2fe28308fd9f2a7baf3','sporcayo','bbd9e2f999019dc01b15ca0d6d11cede7383e69c','','',NULL,'SP','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion 1'),('c45147dee729311ef5b5c3003946c48f','Carlo Fabián','Pizano','Salinas','eccbc87e4b5ce2fe28308fd9f2a7baf3','cpizano','ffe61adb6a6386185b483ea95021328ff10461f7','','',NULL,'CFPS','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion 1'),('c4ca4238a0b923820dcc509a6f75849b','Diego','Godinez','Perez','c4ca4238a0b923820dcc509a6f75849b','usuario2','8be52126a6fde450a7162a3651d589bb51e9579d','asdf@asdf.com','DEGP8708028K9',NULL,'DGP','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:44','2014-09-29 17:37:44','2014-09-29 17:37:44','sesion 2'),('c81e728d9d4c2f636f067f89cc14862c','Alan','Castillo','Perez','cfcd208495d565ef66e7dff9f98764da','usuario','9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684','user@user.com','ACEP9805049L8',NULL,'ACP','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:44','2014-09-29 17:37:44','2014-09-29 17:37:44','sesion 1'),('f0935e4cd5920aa6c7c996a5ee53a70f','Rodrigo','López','Peters','eccbc87e4b5ce2fe28308fd9f2a7baf3','rlopez','b2d2d805b091289c36f5e30add0ac01a7eeb379d','','',NULL,'RLP','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion 1'),('fae0b27c451c728867a567e8c1bb4e53','Víctor Manuel','Munguía','','cfcd208495d565ef66e7dff9f98764da','sysadmin','a159b7ae81ba3552af61e9731b20870515944538','','',NULL,'VMM','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion 1'),('fbd7939d674997cdb4692d34de8633c4','Guillermo','Olguin','Ortega','eccbc87e4b5ce2fe28308fd9f2a7baf3','golguin','51f4de65207d67df154f96db8ec2ba9aea7b90a3','','',NULL,'GOO','ref',0,1,1,'23.23.23.23','2014-09-29 17:37:45','2014-09-29 17:37:45','2014-09-29 17:37:45','sesion 1');
/*!40000 ALTER TABLE `tbcfgm03` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm04`
--

DROP TABLE IF EXISTS `tbcfgm04`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm04` (
  `idpeticion` varchar(32) NOT NULL,
  `idusuario` varchar(32) NOT NULL,
  `dtfecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `inestatus` varchar(1) NOT NULL DEFAULT 'P',
  `dtvencimiento` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `dsnewpasswd` varchar(32) DEFAULT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`idpeticion`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `tbcfgm04_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `tbcfgm03` (`idusuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm04`
--

LOCK TABLES `tbcfgm04` WRITE;
/*!40000 ALTER TABLE `tbcfgm04` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbcfgm04` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm07`
--

DROP TABLE IF EXISTS `tbcfgm07`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm07` (
  `idrol` varchar(32) NOT NULL,
  `dsnombre` varchar(30) NOT NULL,
  `dsdescripcion` varchar(60) DEFAULT NULL,
  `dsprefijo` varchar(5) DEFAULT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idsesion` varchar(32) NOT NULL,
  PRIMARY KEY (`idrol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm07`
--

LOCK TABLES `tbcfgm07` WRITE;
/*!40000 ALTER TABLE `tbcfgm07` DISABLE KEYS */;
INSERT INTO `tbcfgm07` VALUES ('1679091c5a880faf6fb5e6087eb1b2dc','Archivo','descripcion','arch','2014-09-29 17:37:40','sesion'),('1f0e3dad99908345f7439f8ffabdffc4','Reg. Externo','descripcion','regex','2014-09-29 17:37:41','sesion'),('1ff1de774005f8da13f42943881c655f','Encuadernador','descripcion','enc','2014-09-29 17:37:41','sesion'),('37693cfc748049e45d87b8c7d8b9aacd','Cotejo','descripcion','cote','2014-09-29 17:37:41','sesion'),('3c59dc048e8850243be8079a5c74d079','Mensajería','descripcion','msj','2014-09-29 17:37:41','sesion'),('45c48cce2e2d7fbdea1afc51c7c6ad26','Notario','descripcion','not','2014-09-29 17:37:41','sesion'),('6512bd43d9caa6e02c990b0a82652dca','Jefe de Pool','descripcion','jpool','2014-09-29 17:37:41','sesion'),('6f4922f45568161a8cdf4ad2299f6d23','Notas','descripcion','notas','2014-09-29 17:37:41','sesion'),('70efdf2ec9b086079795c442636b55fb','Tesorería','descripcion','tesor','2014-09-29 17:37:41','sesion'),('8f14e45fceea167a5a36dedd4bea2543','Ditto','descripcion','ditto','2014-09-29 17:37:41','sesion'),('98f13708210194c475687be6106a3b84','Funcion','descripcion','func','2014-09-29 17:37:41','sesion'),('9bf31c7ff062936a96d3c8bd1f8f2ff3','Pasante','descripcion','pas','2014-09-29 17:37:41','sesion'),('a87ff679a2f3e71d9181a67b7542122c','Sc. Atencion Publico','descripcion','secap','2014-09-29 17:37:40','sesion'),('aab3238922bcc25a6f606eb525ffdc56','Jefe Pasantes','descripcion','jpas','2014-09-29 17:37:41','sesion'),('b6d767d2f8ed5d21a44b0e5886680cb9','J. Cotejo','descripcion','jcote','2014-09-29 17:37:41','sesion'),('c20ad4d76fe97759aa27a0c99bff6710','Protocolista','descripcion','proto','2014-09-29 17:37:41','sesion'),('c4ca4238a0b923820dcc509a6f75849b','Cliente','descripcion','clte','2014-09-29 17:37:40','sesion'),('c51ce410c124a10e0db5e4b97fc2af39','Revisor','descripcion','rev','2014-09-29 17:37:41','sesion'),('c74d97b01eae257e44aa9d5bade97baf','R. Mesa Control','descripcion','rmesc','2014-09-29 17:37:41','sesion'),('c81e728d9d4c2f636f067f89cc14862c','Recepción','descripcion','recep','2014-09-29 17:37:40','sesion'),('c9f0f895fb98ab9159f51fd0297e236d','J. Mesa Control','descripcion','mesco','2014-09-29 17:37:41','sesion'),('cfcd208495d565ef66e7dff9f98764da','Administrador','descripcion','admin','2014-09-29 17:37:40','sesion'),('d3d9446802a44259755d38e6d163e820','Gestor','descripcion','gest','2014-09-29 17:37:41','sesion'),('e4da3b7fbbce2345d7772b0674a318d5','Caja','descripcion','caja','2014-09-29 17:37:40','sesion'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Abogado','descripcion','abog','2014-09-29 17:37:40','sesion');
/*!40000 ALTER TABLE `tbcfgm07` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm08`
--

DROP TABLE IF EXISTS `tbcfgm08`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm08` (
  `idvariable` varchar(32) NOT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `dsnombre` varchar(250) DEFAULT NULL,
  `isnulo` int(1) DEFAULT NULL,
  `intipodato` varchar(32) NOT NULL,
  `isselmultiple` int(1) DEFAULT NULL,
  `ispermitevalor` int(1) DEFAULT NULL,
  `cdlongitud` int(3) DEFAULT NULL,
  `dsfiltrado` varchar(25) DEFAULT NULL,
  `isactivo` int(1) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idsesion` varchar(32) NOT NULL,
  PRIMARY KEY (`idvariable`),
  KEY `intipodato` (`intipodato`),
  CONSTRAINT `tbcfgm08_ibfk_1` FOREIGN KEY (`intipodato`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm08`
--

LOCK TABLES `tbcfgm08` WRITE;
/*!40000 ALTER TABLE `tbcfgm08` DISABLE KEYS */;
INSERT INTO `tbcfgm08` VALUES ('02e74f10e0327ad868d138f2b4fdd6f0','tramite_operacion','tramite_operacion',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('089aa07b6872a39f04b703b18be3b886',NULL,'colonia',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-15 21:06:35','465A44DE35FF7137250E3D54C99EA6EB'),('0a01ff120ec2e5e4533fc74d4fae8c83',NULL,'tramite_suboperacion',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,50,NULL,1,'2014-07-03 20:31:32','9A7167420573981F62447519FFD6856B'),('0a6a797eb05e657defeb80b3c6693b29',NULL,'codigo_postal',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-15 21:06:53','465A44DE35FF7137250E3D54C99EA6EB'),('1679091c5a880faf6fb5e6087eb1b2dc','notaria_asociada_numero','notaria_asociada_numero',0,'e4da3b7fbbce2345d7772b0674a318d5',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('1778fb3812d98835666508324fcecdf6',NULL,'gestor_nombre_completo',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 01:02:27','8D05A55984B22FD9C1EF24170BDC4D23'),('1c6eb6125849971f752d4d31a2328e63',NULL,'notaria_notario_documento',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-17 17:32:47','F57AFE3217F63E897ADBB2D0BAF0C40C'),('1f0e3dad99908345f7439f8ffabdffc4','compareciente_fecha_nacimiento','compareciente_fecha_nacimiento',0,'1679091c5a880faf6fb5e6087eb1b2dc',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('1ff1de774005f8da13f42943881c655f','compareciente_curp','compareciente_curp',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('33e75ff09dd601bbe69f351039152189','escritura_fecha_elaboracion','escritura_fecha_elaboracion',0,'1679091c5a880faf6fb5e6087eb1b2dc',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('34173cb38f07f89ddbebc2ac9128303f','compareciente_ri_valordocumento','compareciente_ri_valordocumento',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('37693cfc748049e45d87b8c7d8b9aacd','compareciente_rfc','compareciente_rfc',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('3815f95d100a57d66556d0566e960850',NULL,'compareciente_telefono',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 14:41:22','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('38d542343e82983c570f29b3e8183d0e',NULL,'documento_valuador',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,154,NULL,1,'2014-07-04 17:47:07','74EEF7329D03491E25B10EC75AE720DA'),('3c59dc048e8850243be8079a5c74d079','compareciente_domicilio','compareciente_domicilio',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('412bceb43b3c73b8c455dfe3d551d62e',NULL,'compareciente_nombre(tipo=VENDEDOR)',NULL,'1679091c5a880faf6fb5e6087eb1b2dc',NULL,NULL,254,NULL,1,'2014-06-16 23:16:27','8C7FF7FE32882C8ACF24202A3E1D7C28'),('45c48cce2e2d7fbdea1afc51c7c6ad26','numero_escritura','numero_escritura',0,'e4da3b7fbbce2345d7772b0674a318d5',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('47130b6562b35a5c5ee066537896c015',NULL,'gestor_nombre_completo',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 14:42:03','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('4b5bd00d1ac758690803a181ea5bcb8a',NULL,'compareciente_nombre_materno(tipo=COMPRADOR)',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 01:30:54','8D05A55984B22FD9C1EF24170BDC4D23'),('4e732ced3463d06de0ca9a15b6153677','compareciente_compraciente_cantidad','compareciente_compraciente_cantidad',0,'e4da3b7fbbce2345d7772b0674a318d5',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('53d1182bb7aa07d1545456133c0a714c',NULL,'compareciente_estado',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-06-26 21:38:17','8D05A55984B22FD9C1EF24170BDC4D23'),('5bf50c811618994403bc4f9da79fbe4c',NULL,'notaria_notario_iniciales',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-06-27 14:43:18','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('63ab7ee49cf6edb9621fe3f2ef432fc2',NULL,'gestor_nombre_completo',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 14:42:38','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('6512bd43d9caa6e02c990b0a82652dca','escritura_folio','escritura_folio',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('69811b293c4886b5d652705a8207ce77',NULL,'abogado_iniciales',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-06-27 14:43:36','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('6b36fb6ce9f824b2961d8ea68c7a9698',NULL,'notaria_notario_rfc',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,50,NULL,1,'2014-06-27 00:57:24','8D05A55984B22FD9C1EF24170BDC4D23'),('6ea9ab1baa0efb9e19094440c317e21b','compareciente_ri_tipodocumento','compareciente_ri_tipodocumento',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('6f4922f45568161a8cdf4ad2299f6d23','compareciente_originario','compareciente_originario',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('70efdf2ec9b086079795c442636b55fb','compareciente_nacionalidad','compareciente_nacionalidad',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('72de79e4b71df5b5c725b48389ac84a4',NULL,'compareciente_nombre_paterno(tipo=COMPRADOR)',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 01:30:45','8D05A55984B22FD9C1EF24170BDC4D23'),('73191c65923abdf899595fb0dd369451',NULL,'notaria_esasociada',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,250,NULL,1,'2014-07-14 23:26:02','8BDFC7FF9D618E201F54E14AFACD3BCF'),('7921d5d03523214067570507a39e8e17',NULL,'cuenta_agua',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-15 21:07:14','465A44DE35FF7137250E3D54C99EA6EB'),('7bb8b980b3bae5977fe5e7fa7f9fb289',NULL,'compareciente_compro_proindiviso',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-07-14 21:17:06','8BDFC7FF9D618E201F54E14AFACD3BCF'),('8b12a056c8b04f56e9bf42638ca6a54e',NULL,'calle_no',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-15 21:06:25','465A44DE35FF7137250E3D54C99EA6EB'),('8db21596e7bbf4fb6d5b3d7dba188976',NULL,'compareciente_ambos_compraron',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-07-03 01:05:55','764D23655EFDF995E92086DCFA44DFA7'),('8e296a067a37563370ded05f5a3bf3ec','compareciente_estado_civil_texto','compareciente_estado_civil_texto',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('8f14e45fceea167a5a36dedd4bea2543','notaria_asociada_notario','notaria_asociada_notario',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('965cd083cfd3e01e4b891ef1a97ec3f8',NULL,'compareciente_nombre_materno(tipo=VENDEDOR)',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-07-07 17:49:36','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('98f13708210194c475687be6106a3b84','compareciente_estado_civil','compareciente_estado_civil',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('9bf31c7ff062936a96d3c8bd1f8f2ff3','compareciente_nombre_representante','compareciente_nombre_representante',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('9cf656ebfbef18cb111cdf9f6cfe8bfa',NULL,'abogado_iniciales',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-06-26 21:37:56','8D05A55984B22FD9C1EF24170BDC4D23'),('9e9257456b7ae6d41a5a8e2e18e7edcc',NULL,'compareciente_nombre_nombre(tipo=COMPRADOR)',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 01:30:33','8D05A55984B22FD9C1EF24170BDC4D23'),('a097e6bf991cf21c69225314aac134d0',NULL,'compareciente_nombre(tipo=COMPRADOR)',NULL,'1679091c5a880faf6fb5e6087eb1b2dc',NULL,NULL,254,NULL,1,'2014-06-16 23:15:54','8C7FF7FE32882C8ACF24202A3E1D7C28'),('a731aff1b579b0fda739b758b7bb76f7',NULL,'telefono',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,50,NULL,1,'2014-06-15 21:07:05','465A44DE35FF7137250E3D54C99EA6EB'),('a87ff679a2f3e71d9181a67b7542122c','notaria_numero','notaria_numero',0,'e4da3b7fbbce2345d7772b0674a318d5',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('aab3238922bcc25a6f606eb525ffdc56','inmueble_domicilio','inmueble_domicilio',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('b6d767d2f8ed5d21a44b0e5886680cb9','compareciente_ocupacion','compareciente_ocupacion',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('bb5074cd090b97cf550771f2ffbd1e7e',NULL,'documento_gestor',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-16 23:01:43','8C7FF7FE32882C8ACF24202A3E1D7C28'),('bd9961d2a7953e85bc237ae93130df59',NULL,'compareciente_nombre_nombre(tipo=VENDEDOR)',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-07-07 17:49:03','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('be74e8be7c365dd8825721ca15e4f2d3',NULL,'notaria_notario_iniciales',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-06-26 21:37:21','8D05A55984B22FD9C1EF24170BDC4D23'),('c16a5320fa475530d9583c34fd356ef5','compareciente_conyugue','compareciente_conyugue',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:10','sesion'),('c20ad4d76fe97759aa27a0c99bff6710','inmueble_foja','inmueble_foja',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('c2fb318358d3ca311eb1d571039c41a2',NULL,'compareciente_compro_estadocivil',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-07-03 01:05:44','764D23655EFDF995E92086DCFA44DFA7'),('c4ca4238a0b923820dcc509a6f75849b','numero_libro','numero_libro',0,'e4da3b7fbbce2345d7772b0674a318d5',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('c51ce410c124a10e0db5e4b97fc2af39','inmueble_precio','inmueble_precio',0,'8f14e45fceea167a5a36dedd4bea2543',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('c74d97b01eae257e44aa9d5bade97baf','fecha_firma_escritura','fecha_firma_escritura',0,'1679091c5a880faf6fb5e6087eb1b2dc',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('c81e728d9d4c2f636f067f89cc14862c','fecha_hoy','fecha_hoy',0,'1679091c5a880faf6fb5e6087eb1b2dc',0,0,20,NULL,1,'2014-06-13 03:22:09','sesion'),('c99a4cadcff9d60f09711816aeaebd42',NULL,'fecha',NULL,'1679091c5a880faf6fb5e6087eb1b2dc',NULL,NULL,10,NULL,1,'2014-06-15 21:07:38','465A44DE35FF7137250E3D54C99EA6EB'),('c9f0f895fb98ab9159f51fd0297e236d','compareciente_nombre','compareciente_nombre',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idacto',1,'2014-06-13 03:22:09','sesion'),('d3d9446802a44259755d38e6d163e820','fecha_escritura','fecha_escritura',0,'1679091c5a880faf6fb5e6087eb1b2dc',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('d518fecb17f316582890c6876557cbaa',NULL,'protocolista_iniciales',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-06-26 21:38:10','8D05A55984B22FD9C1EF24170BDC4D23'),('d6c4b1eaa01c560ca2300ab56bb83111',NULL,'variable_pruebita',NULL,'1679091c5a880faf6fb5e6087eb1b2dc',NULL,NULL,250,NULL,1,'2014-07-08 17:33:57','344B32BE40672B90F99A41A36768339D'),('ddb8b0422753e68ecd0b522b9302effe',NULL,'compareciente_nombre_paterno(tipo=VENDEDOR)',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-07-07 17:50:10','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('deecad296b400ecd946ff794e4b6d4c2',NULL,'protocolista_iniciales',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-06-27 14:44:04','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('e4da3b7fbbce2345d7772b0674a318d5','notaria_estado','notaria_estado',0,'a87ff679a2f3e71d9181a67b7542122c',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('e8e3e8089d8d20d7b94b00f2fe4ceec3',NULL,'notaria_notario_rfc',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 14:42:59','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('ebf14e61f6efc6f8670c93565738094c',NULL,'compareciente_nacimiento_estado',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,10,NULL,1,'2014-06-27 14:44:21','DA0F9DB9D4AA4C92472DE02B38C4D01B'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','notaria_notario','notaria_notario',0,'e4da3b7fbbce2345d7772b0674a318d5',0,0,20,'idescritura',1,'2014-06-13 03:22:09','sesion'),('ee95bf6fe76a6171bc6f4ba9ed399899',NULL,'compareciente_mail',NULL,'a87ff679a2f3e71d9181a67b7542122c',NULL,NULL,254,NULL,1,'2014-06-27 14:41:49','DA0F9DB9D4AA4C92472DE02B38C4D01B');
/*!40000 ALTER TABLE `tbcfgm08` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm09`
--

DROP TABLE IF EXISTS `tbcfgm09`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm09` (
  `idrestriccion` varchar(32) NOT NULL,
  `idoperador` varchar(32) DEFAULT NULL,
  `dsexpresion` varchar(60) DEFAULT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idsesion` varchar(32) NOT NULL,
  `idvariable` varchar(32) NOT NULL,
  PRIMARY KEY (`idrestriccion`),
  KEY `idvariable` (`idvariable`),
  KEY `idoperador` (`idoperador`),
  CONSTRAINT `tbcfgm09_ibfk_1` FOREIGN KEY (`idvariable`) REFERENCES `tbcfgm08` (`idvariable`),
  CONSTRAINT `tbcfgm09_ibfk_2` FOREIGN KEY (`idoperador`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm09`
--

LOCK TABLES `tbcfgm09` WRITE;
/*!40000 ALTER TABLE `tbcfgm09` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbcfgm09` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm10`
--

DROP TABLE IF EXISTS `tbcfgm10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm10` (
  `idgrupo` varchar(32) NOT NULL,
  `dsgrupo` varchar(30) NOT NULL,
  `idsdescripcion` varchar(250) DEFAULT NULL,
  `isactivo` int(1) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idsesion` varchar(32) NOT NULL,
  PRIMARY KEY (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm10`
--

LOCK TABLES `tbcfgm10` WRITE;
/*!40000 ALTER TABLE `tbcfgm10` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbcfgm10` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm11`
--

DROP TABLE IF EXISTS `tbcfgm11`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm11` (
  `idgpovars` varchar(32) NOT NULL,
  `idgrupo` varchar(32) NOT NULL,
  `idvariable` varchar(32) NOT NULL,
  `dsorden` int(2) DEFAULT NULL,
  PRIMARY KEY (`idgpovars`),
  KEY `idvariable` (`idvariable`),
  KEY `idgrupo` (`idgrupo`),
  CONSTRAINT `tbcfgm11_ibfk_1` FOREIGN KEY (`idvariable`) REFERENCES `tbcfgm08` (`idvariable`),
  CONSTRAINT `tbcfgm11_ibfk_2` FOREIGN KEY (`idgrupo`) REFERENCES `tbcfgm10` (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm11`
--

LOCK TABLES `tbcfgm11` WRITE;
/*!40000 ALTER TABLE `tbcfgm11` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbcfgm11` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm16`
--

DROP TABLE IF EXISTS `tbcfgm16`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm16` (
  `idvarest` varchar(32) NOT NULL,
  `dsentidad` varchar(120) DEFAULT NULL,
  `dspropiedad` varchar(250) DEFAULT NULL,
  `idvariable` varchar(32) NOT NULL,
  `dsfiltro` varchar(250) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idvarest`),
  KEY `idvariable` (`idvariable`),
  CONSTRAINT `tbcfgm16_ibfk_1` FOREIGN KEY (`idvariable`) REFERENCES `tbcfgm08` (`idvariable`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm16`
--

LOCK TABLES `tbcfgm16` WRITE;
/*!40000 ALTER TABLE `tbcfgm16` DISABLE KEYS */;
INSERT INTO `tbcfgm16` VALUES ('1679091c5a880faf6fb5e6087eb1b2dc','Inmueble','precio','c51ce410c124a10e0db5e4b97fc2af39','acto.idacto','session','2014-06-13 03:22:10'),('45c48cce2e2d7fbdea1afc51c7c6ad26','Acto','suboperacion.operacion.dsnombre','02e74f10e0327ad868d138f2b4fdd6f0','idacto','session','2014-06-13 03:22:10'),('496dbe0a62c985524b0edfffce04531a','Escritura','fechacreacion','69811b293c4886b5d652705a8207ce77','Escritura','CDFE9788CFC13A1F1D871376C8C3BD9F','2014-07-10 17:44:07'),('747e869f994defaa6dfcbf5b78244f11','Acto','dsnombre','0a01ff120ec2e5e4533fc74d4fae8c83','idacto','DA0F9DB9D4AA4C92472DE02B38C4D01B','2014-07-03 20:33:45'),('8f14e45fceea167a5a36dedd4bea2543','Inmueble','dsdomcompleto','aab3238922bcc25a6f606eb525ffdc56','acto.idacto','session','2014-06-13 03:22:10'),('8f86c98411b860989471188cc13f2a9b','Escritura','dsnumescritura','9cf656ebfbef18cb111cdf9f6cfe8bfa','Acto','CDFE9788CFC13A1F1D871376C8C3BD9F','2014-07-10 18:06:18'),('a87ff679a2f3e71d9181a67b7542122c','Escritura','folioini','6512bd43d9caa6e02c990b0a82652dca','idescritura','session','2014-06-13 03:22:10'),('c4ca4238a0b923820dcc509a6f75849b','Escritura','libro.innumlibro','c4ca4238a0b923820dcc509a6f75849b','idescritura','session','2014-06-13 03:22:10'),('c81e728d9d4c2f636f067f89cc14862c','Escritura','dsnumescritura','45c48cce2e2d7fbdea1afc51c7c6ad26','idescritura','session','2014-06-13 03:22:10'),('c9f0f895fb98ab9159f51fd0297e236d','Escritura','fechafirma','c74d97b01eae257e44aa9d5bade97baf','idescritura','session','2014-06-13 03:22:10'),('e4da3b7fbbce2345d7772b0674a318d5','Inmueble','dsfoja','c20ad4d76fe97759aa27a0c99bff6710','acto.idacto','session','2014-06-13 03:22:10'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Escritura','fechacreacion','d3d9446802a44259755d38e6d163e820','idescritura','session','2014-06-13 03:22:10');
/*!40000 ALTER TABLE `tbcfgm16` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm17`
--

DROP TABLE IF EXISTS `tbcfgm17`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm17` (
  `identificador` int(5) NOT NULL,
  `idrol` varchar(32) NOT NULL,
  `idproceso` varchar(32) NOT NULL,
  PRIMARY KEY (`idrol`,`idproceso`),
  KEY `idproceso` (`idproceso`),
  CONSTRAINT `tbcfgm17_ibfk_1` FOREIGN KEY (`idrol`) REFERENCES `tbcfgm07` (`idrol`),
  CONSTRAINT `tbcfgm17_ibfk_2` FOREIGN KEY (`idproceso`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm17`
--

LOCK TABLES `tbcfgm17` WRITE;
/*!40000 ALTER TABLE `tbcfgm17` DISABLE KEYS */;
INSERT INTO `tbcfgm17` VALUES (33,'1679091c5a880faf6fb5e6087eb1b2dc','02e74f10e0327ad868d138f2b4fdd6f0'),(30,'1679091c5a880faf6fb5e6087eb1b2dc','1ff1de774005f8da13f42943881c655f'),(34,'1679091c5a880faf6fb5e6087eb1b2dc','33e75ff09dd601bbe69f351039152189'),(36,'1679091c5a880faf6fb5e6087eb1b2dc','34173cb38f07f89ddbebc2ac9128303f'),(29,'1679091c5a880faf6fb5e6087eb1b2dc','37693cfc748049e45d87b8c7d8b9aacd'),(32,'1679091c5a880faf6fb5e6087eb1b2dc','4e732ced3463d06de0ca9a15b6153677'),(35,'1679091c5a880faf6fb5e6087eb1b2dc','6ea9ab1baa0efb9e19094440c317e21b'),(31,'1679091c5a880faf6fb5e6087eb1b2dc','8e296a067a37563370ded05f5a3bf3ec'),(38,'1679091c5a880faf6fb5e6087eb1b2dc','908c9a564a86426585b29f5335b619bc'),(37,'1679091c5a880faf6fb5e6087eb1b2dc','c16a5320fa475530d9583c34fd356ef5'),(80,'1f0e3dad99908345f7439f8ffabdffc4','6ea9ab1baa0efb9e19094440c317e21b'),(85,'1ff1de774005f8da13f42943881c655f','908c9a564a86426585b29f5335b619bc'),(84,'37693cfc748049e45d87b8c7d8b9aacd','c16a5320fa475530d9583c34fd356ef5'),(82,'3c59dc048e8850243be8079a5c74d079','34173cb38f07f89ddbebc2ac9128303f'),(53,'45c48cce2e2d7fbdea1afc51c7c6ad26','02e74f10e0327ad868d138f2b4fdd6f0'),(50,'45c48cce2e2d7fbdea1afc51c7c6ad26','1ff1de774005f8da13f42943881c655f'),(54,'45c48cce2e2d7fbdea1afc51c7c6ad26','33e75ff09dd601bbe69f351039152189'),(52,'45c48cce2e2d7fbdea1afc51c7c6ad26','4e732ced3463d06de0ca9a15b6153677'),(55,'45c48cce2e2d7fbdea1afc51c7c6ad26','6ea9ab1baa0efb9e19094440c317e21b'),(51,'45c48cce2e2d7fbdea1afc51c7c6ad26','8e296a067a37563370ded05f5a3bf3ec'),(57,'45c48cce2e2d7fbdea1afc51c7c6ad26','908c9a564a86426585b29f5335b619bc'),(56,'45c48cce2e2d7fbdea1afc51c7c6ad26','c16a5320fa475530d9583c34fd356ef5'),(63,'6512bd43d9caa6e02c990b0a82652dca','33e75ff09dd601bbe69f351039152189'),(62,'6512bd43d9caa6e02c990b0a82652dca','4e732ced3463d06de0ca9a15b6153677'),(61,'6512bd43d9caa6e02c990b0a82652dca','8e296a067a37563370ded05f5a3bf3ec'),(79,'6f4922f45568161a8cdf4ad2299f6d23','6ea9ab1baa0efb9e19094440c317e21b'),(78,'70efdf2ec9b086079795c442636b55fb','02e74f10e0327ad868d138f2b4fdd6f0'),(39,'8f14e45fceea167a5a36dedd4bea2543','1ff1de774005f8da13f42943881c655f'),(42,'8f14e45fceea167a5a36dedd4bea2543','33e75ff09dd601bbe69f351039152189'),(41,'8f14e45fceea167a5a36dedd4bea2543','4e732ced3463d06de0ca9a15b6153677'),(43,'8f14e45fceea167a5a36dedd4bea2543','6ea9ab1baa0efb9e19094440c317e21b'),(40,'8f14e45fceea167a5a36dedd4bea2543','8e296a067a37563370ded05f5a3bf3ec'),(45,'8f14e45fceea167a5a36dedd4bea2543','908c9a564a86426585b29f5335b619bc'),(44,'8f14e45fceea167a5a36dedd4bea2543','c16a5320fa475530d9583c34fd356ef5'),(81,'98f13708210194c475687be6106a3b84','6ea9ab1baa0efb9e19094440c317e21b'),(72,'9bf31c7ff062936a96d3c8bd1f8f2ff3','33e75ff09dd601bbe69f351039152189'),(71,'9bf31c7ff062936a96d3c8bd1f8f2ff3','4e732ced3463d06de0ca9a15b6153677'),(73,'9bf31c7ff062936a96d3c8bd1f8f2ff3','908c9a564a86426585b29f5335b619bc'),(20,'a87ff679a2f3e71d9181a67b7542122c','1ff1de774005f8da13f42943881c655f'),(23,'a87ff679a2f3e71d9181a67b7542122c','34173cb38f07f89ddbebc2ac9128303f'),(19,'a87ff679a2f3e71d9181a67b7542122c','37693cfc748049e45d87b8c7d8b9aacd'),(22,'a87ff679a2f3e71d9181a67b7542122c','4e732ced3463d06de0ca9a15b6153677'),(21,'a87ff679a2f3e71d9181a67b7542122c','8e296a067a37563370ded05f5a3bf3ec'),(69,'aab3238922bcc25a6f606eb525ffdc56','4e732ced3463d06de0ca9a15b6153677'),(70,'aab3238922bcc25a6f606eb525ffdc56','908c9a564a86426585b29f5335b619bc'),(83,'b6d767d2f8ed5d21a44b0e5886680cb9','c16a5320fa475530d9583c34fd356ef5'),(65,'c20ad4d76fe97759aa27a0c99bff6710','4e732ced3463d06de0ca9a15b6153677'),(64,'c20ad4d76fe97759aa27a0c99bff6710','8e296a067a37563370ded05f5a3bf3ec'),(4,'c4ca4238a0b923820dcc509a6f75849b','34173cb38f07f89ddbebc2ac9128303f'),(1,'c4ca4238a0b923820dcc509a6f75849b','37693cfc748049e45d87b8c7d8b9aacd'),(3,'c4ca4238a0b923820dcc509a6f75849b','4e732ced3463d06de0ca9a15b6153677'),(2,'c4ca4238a0b923820dcc509a6f75849b','8e296a067a37563370ded05f5a3bf3ec'),(5,'c4ca4238a0b923820dcc509a6f75849b','c16a5320fa475530d9583c34fd356ef5'),(67,'c51ce410c124a10e0db5e4b97fc2af39','33e75ff09dd601bbe69f351039152189'),(74,'c51ce410c124a10e0db5e4b97fc2af39','4e732ced3463d06de0ca9a15b6153677'),(66,'c51ce410c124a10e0db5e4b97fc2af39','8e296a067a37563370ded05f5a3bf3ec'),(68,'c51ce410c124a10e0db5e4b97fc2af39','c16a5320fa475530d9583c34fd356ef5'),(76,'c74d97b01eae257e44aa9d5bade97baf','02e74f10e0327ad868d138f2b4fdd6f0'),(75,'c74d97b01eae257e44aa9d5bade97baf','4e732ced3463d06de0ca9a15b6153677'),(77,'c74d97b01eae257e44aa9d5bade97baf','6ea9ab1baa0efb9e19094440c317e21b'),(8,'c81e728d9d4c2f636f067f89cc14862c','34173cb38f07f89ddbebc2ac9128303f'),(6,'c81e728d9d4c2f636f067f89cc14862c','37693cfc748049e45d87b8c7d8b9aacd'),(7,'c81e728d9d4c2f636f067f89cc14862c','4e732ced3463d06de0ca9a15b6153677'),(9,'c81e728d9d4c2f636f067f89cc14862c','c16a5320fa475530d9583c34fd356ef5'),(48,'c9f0f895fb98ab9159f51fd0297e236d','02e74f10e0327ad868d138f2b4fdd6f0'),(46,'c9f0f895fb98ab9159f51fd0297e236d','1ff1de774005f8da13f42943881c655f'),(47,'c9f0f895fb98ab9159f51fd0297e236d','4e732ced3463d06de0ca9a15b6153677'),(49,'c9f0f895fb98ab9159f51fd0297e236d','6ea9ab1baa0efb9e19094440c317e21b'),(59,'d3d9446802a44259755d38e6d163e820','02e74f10e0327ad868d138f2b4fdd6f0'),(58,'d3d9446802a44259755d38e6d163e820','1ff1de774005f8da13f42943881c655f'),(60,'d3d9446802a44259755d38e6d163e820','6ea9ab1baa0efb9e19094440c317e21b'),(26,'e4da3b7fbbce2345d7772b0674a318d5','02e74f10e0327ad868d138f2b4fdd6f0'),(24,'e4da3b7fbbce2345d7772b0674a318d5','37693cfc748049e45d87b8c7d8b9aacd'),(25,'e4da3b7fbbce2345d7772b0674a318d5','4e732ced3463d06de0ca9a15b6153677'),(27,'e4da3b7fbbce2345d7772b0674a318d5','6ea9ab1baa0efb9e19094440c317e21b'),(28,'e4da3b7fbbce2345d7772b0674a318d5','c16a5320fa475530d9583c34fd356ef5'),(14,'eccbc87e4b5ce2fe28308fd9f2a7baf3','02e74f10e0327ad868d138f2b4fdd6f0'),(11,'eccbc87e4b5ce2fe28308fd9f2a7baf3','1ff1de774005f8da13f42943881c655f'),(15,'eccbc87e4b5ce2fe28308fd9f2a7baf3','33e75ff09dd601bbe69f351039152189'),(17,'eccbc87e4b5ce2fe28308fd9f2a7baf3','34173cb38f07f89ddbebc2ac9128303f'),(10,'eccbc87e4b5ce2fe28308fd9f2a7baf3','37693cfc748049e45d87b8c7d8b9aacd'),(13,'eccbc87e4b5ce2fe28308fd9f2a7baf3','4e732ced3463d06de0ca9a15b6153677'),(16,'eccbc87e4b5ce2fe28308fd9f2a7baf3','6ea9ab1baa0efb9e19094440c317e21b'),(12,'eccbc87e4b5ce2fe28308fd9f2a7baf3','8e296a067a37563370ded05f5a3bf3ec'),(18,'eccbc87e4b5ce2fe28308fd9f2a7baf3','c16a5320fa475530d9583c34fd356ef5');
/*!40000 ALTER TABLE `tbcfgm17` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm18`
--

DROP TABLE IF EXISTS `tbcfgm18`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm18` (
  `iddocobjeto` varchar(32) NOT NULL,
  `dsnombre` varchar(30) NOT NULL,
  `dsdescripcion` varchar(90) DEFAULT NULL,
  `version` int(5) NOT NULL DEFAULT '0',
  `ispublicada` int(1) DEFAULT NULL,
  `fchpublicacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dscontenido` longtext,
  `isactivo` int(1) NOT NULL,
  `idusuariopublico` varchar(32) DEFAULT NULL,
  `idusuariocreomodifico` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`iddocobjeto`,`version`),
  KEY `version_key` (`version`),
  KEY `iddocobjeto_key` (`iddocobjeto`),
  KEY `idusuariopublico` (`idusuariopublico`),
  KEY `idusuariocreomodifico` (`idusuariocreomodifico`),
  CONSTRAINT `tbcfgm18_ibfk_1` FOREIGN KEY (`idusuariopublico`) REFERENCES `tbcfgm03` (`idusuario`),
  CONSTRAINT `tbcfgm18_ibfk_2` FOREIGN KEY (`idusuariocreomodifico`) REFERENCES `tbcfgm03` (`idusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm18`
--

LOCK TABLES `tbcfgm18` WRITE;
/*!40000 ALTER TABLE `tbcfgm18` DISABLE KEYS */;
INSERT INTO `tbcfgm18` VALUES ('a87ff679a2f3e71d9181a67b7542122c','texto SC','clausulas de SC',0,1,'2014-09-29 17:37:46','\n\n\nLa combinación de los conocimientos, experiencias, esfuerzos\ny recursos de los miembros de la sociedad para prestar, toda\nclase  de  servicios de consultoría, asesoría y capacitación\nen   toda   clase   de  materias,  tales  como  legales,  de\ncontabilidad,   administrativos,   financieros,  fiscales  e\ninmobiliarios.\nPor  lo  que enunciativa pero no limitativamente la sociedad\npodrá:\nI.  Adquirir  por  cualquier  título de toda clase de bienes\nmuebles  e  inmuebles y derechos que sean necesarios para la\nrealización   de  su  objeto  social,  así  como  establecer\noficinas para prestar los servicios de referencia.\nII.  Obtener  por  cualquier  título, concesiones, permisos,\nautorizaciones  o  licencias,  así  como  celebrar cualquier\nclase de contratos con la administración pública sea federal\no local, relacionados con su objeto.\nIII.  Obtener,  adquirir,  registrar, utilizar o disponer de\ntoda  clase  de patentes, marcas industriales y de servicio,\ncertificados  de  invención o nombres comerciales, diseños y\ndibujos  industriales,  derechos  de  autor y cualquier otro\ntipo  de  derechos  de  propiedad  industrial,  literaria  o\nartística,  y  derechos sobre ellos ya sea en México o en el\nextranjero.\nIV.  Otorgar  avales,  así  como  obligarse solidariamente y\nconstituir garantías a favor de terceros.\nV. Emitir, girar, endosar, aceptar y suscribir toda clase de\ntítulos  de  crédito  sin  que  constituyan una especulación\ncomercial.\nVI. Contratar al personal necesario.\nVII.  Celebrar  toda  clase  de  contratos  y  convenios,  y\nejecutar  actos  u  operaciones de cualquier naturaleza, así\ncomo  otorgar  y suscribir cualquier clase de documentos que\nsean  necesarios  o  convenientes  para la realización de su\nobjeto,   a   excepción  de  aquellos  que  constituyan  una\nespeculación comercial.\n#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,NULL,'c4ca4238a0b923820dcc509a6f75849b','sesion','2014-09-29 17:37:46'),('c4ca4238a0b923820dcc509a6f75849b','editorial','clausulas editorial',0,1,'2014-09-29 17:37:46','\n\nEl  diseño, edición, producción y comercialización de medios\nimpresos  y  audiovisuales,  tales  como  libros, boletines,\ngacetas,   periódicos,   revistas,   videos,   películas,  \ndocumentales,  y  en  general  cualquier  tipo  o  medio  de\nrepresentación.   Por   lo   que   enunciativa   pero  no  \nlimitativamente la sociedad podrá:\nI.  Ejecutar  toda  clase  de  actos  de  comercio  pudiendo\ncomprar,  vender,  adquirir, distribuir, importar, exportar,\nproducir,  fabricar,  manufacturar,  transformar,  maquilar,\ncomercializar  y  en  general,  negociar  con  toda clase de\nartículos  y  mercancías  por  cuenta  propia o ajena, en la\nRepública Mexicana o en el Extranjero.\nII.  Contratar  con  empresas  industriales, comerciales, de\nservicios,  sociedades  y asociaciones, privadas o públicas,\nentidades   gubernamentales,  asociaciones  profesionales  y\norganismos  sociales en general, respecto de: el diseño y la\nproducción  de  medios de comunicación, escritos o gráficos,\npara  informar  y  difundir  la  índole  de  sus actividades\npropias,  tanto  a nivel interno como externo; la prestación\nde servicios de información a través de los diversos géneros\nperiodísticos   (nota,   reportaje,  crónica,  artículos,  \nentrevistas,  etcétera),  publicando  los  mismos  tanto  en\nórganos  de  comunicación propios como en ajenos, impresos o\naudiovisuales,  de  acuerdo con la índole de sus actividades\npropias; la prestación de servicios de asesoría editorial en\nórganos  de  comunicación  y  la  prestación de servicios de\ndiseño   gráfico,   publicitario   y   de  representación  \naudiovisual.\nIII.  Prestar  y  recibir  toda  clase de servicios legales,\nfiscales,  de contabilidad, administrativos y de asesoría en\ngeneral,  a  empresas  en  la  República  Mexicana  o  en el\nExtranjero.\nIV.  Llevar a cabo por cuenta propia o de terceros programas\nde  capacitación  y  desarrollo,  así  como  investigaciones\ncientíficas  para  desarrollo  tecnológico o investigaciones\nprofesionales,  en  las  materias que requieran las personas\nfísicas  o morales a las que la sociedad preste servicios, o\na  las  que la propia sociedad considere conveniente, ya sea\ndirectamente  o  por  medio  de  Institutos  Tecnológicos  y\nUniversitarios  o empresas o instituciones especializadas en\nel  País o en el Extranjero o mediante asociación con dichos\ninstitutos,   universidades,   empresas  o  instituciones  \nespecializadas  y proporcionar a sus clientes los resultados\nde dicha investigación.\nV. Obtener, adquirir, registrar, utilizar o disponer de toda\nclase  de  patentes,  marcas  industriales  y  de  servicio,\ncertificados  de  invención o nombres comerciales, diseños y\ndibujos  industriales,  derechos  de  autor y cualquier otro\ntipo  de  derechos  de  propiedad  industrial,  literaria  o\nartística,  y  derechos sobre ellos ya sea en México o en el\nextranjero.\nVI.  Obtener  por  cualquier  título, concesiones, permisos,\nautorizaciones  o  licencias,  así  como  celebrar cualquier\nclase  de  contratos,  con  la  administración  pública  sea\nfederal o local o con cualquier particular.\nVII.  Dar  o  tomar  dinero  en préstamo con o sin garantía,\nemitir  bonos,  obligaciones,  valores  y  otros  títulos de\ncrédito,  así como adquirir legalmente y negociar con bonos,\nobligaciones,  acciones,  valores y otros títulos de crédito\nemitidos por terceros.\nVIII.  Otorgar  avales  y  obligarse solidariamente así como\nconstituir garantías a favor de terceros.\nIX.   Formar   parte   directa  o  indirectamente  de  otras\nsociedades  o asociaciones e intervenir en todos los asuntos\ny derechos relacionados con ellas.\nX. Establecer sucursales, oficinas, subsidiarias y agencias,\nasí  como representar o ser agente de empresas e intermediar\nen la venta de toda clase de bienes y servicios.\nXI.  Adquirir  o  poseer  por  cualquier título, usar, dar o\ntomar  en  arrendamiento,  administrar, vender o disponer en\ncualquier  forma,  de  todos los bienes muebles o inmuebles,\nasí  como  derechos  reales  o  personales  sobre ellos, que\nfueren  necesarios  o  convenientes  para la realización del\nobjeto de la sociedad.\nXI. Contratar al personal necesario.\nXII.  En  general  celebrar  toda clase de contratos ya sean\nciviles, mercantiles o de cualquier naturaleza.\n#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,NULL,'c4ca4238a0b923820dcc509a6f75849b','sesion','2014-09-29 17:37:46'),('c81e728d9d4c2f636f067f89cc14862c','constructora','clausulas de constructora',0,1,'2014-09-29 17:37:46','\n\n\nLa  realización, supervisión, dirección, diseño, proyección,\npresupuesto,  administración y mantenimiento de todo tipo de\nobras   de  ingeniería,  urbanización  y  arquitectura  sean\npúblicas  o  privadas,  relacionadas  con  la  construcción,\nremodelación,  conservación, reparación o demolición de toda\nclase   de   inmuebles  y  obras  en  general;  Por  lo  que\nenunciativa pero no limitativamente, la sociedad podrá:\nI.  Ejecutar  toda  clase  de  actos  de  comercio  pudiendo\ncomprar,  vender,  adquirir, distribuir, importar, exportar,\nproducir,  fabricar,  manufacturar,  transformar,  maquilar,\ncomercializar  y  en  general,  negociar  con  toda clase de\nartículos  y  mercancías  por  cuenta  propia o ajena, en la\nRepública Mexicana o en el Extranjero.\nII. Comprar, vender, importar y exportar por cuenta propia o\nde  terceros  toda  clase  de  materiales  y equipos para la\nconstrucción, así como sus refacciones.\nIII.  Prestar  y  recibir  toda  clase de servicios legales,\nfiscales,  de contabilidad, administrativos y de asesoría en\ngeneral,  a  empresas  en  la  República  Mexicana  o  en el\nExtranjero.\nIV.  Llevar a cabo por cuenta propia o de terceros programas\nde  capacitación  y  desarrollo,  así  como  investigaciones\ncientíficas  para  desarrollo  tecnológico o investigaciones\nprofesionales,  en  las  materias que requieran las personas\nfísicas  o morales a las que la sociedad preste servicios, o\na  las  que la propia sociedad considere conveniente, ya sea\ndirectamente  o  por  medio  de  Institutos  Tecnológicos  y\nUniversitarios  o empresas o instituciones especializadas en\nel  País o en el Extranjero o mediante asociación con dichos\ninstitutos,   universidades,   empresas  o  instituciones  \nespecializadas  y proporcionar a sus clientes los resultados\nde dicha investigación.\nV. Obtener, adquirir, registrar, utilizar o disponer de toda\nclase  de  patentes,  marcas  industriales  y  de  servicio,\ncertificados  de  invención o nombres comerciales, diseños y\ndibujos  industriales,  derechos  de  autor y cualquier otro\ntipo  de  derechos  de  propiedad  industrial,  literaria  o\nartística,  y  derechos sobre ellos ya sea en México o en el\nextranjero.\nVI.  Obtener  por  cualquier  título, concesiones, permisos,\nautorizaciones  o  licencias,  así  como  celebrar cualquier\nclase  de  contratos,  con  la  administración  pública  sea\nfederal o local o con cualquier particular.\nVII.  Dar  o  tomar  dinero  en préstamo con o sin garantía,\nemitir  bonos,  obligaciones,  valores  y  otros  títulos de\ncrédito,  así como adquirir legalmente y negociar con bonos,\nobligaciones,  acciones,  valores y otros títulos de crédito\nemitidos por terceros.\nVIII.  Otorgar  avales  y  obligarse solidariamente así como\nconstituir garantías a favor de terceros.\nIX.   Formar   parte   directa  o  indirectamente  de  otras\nsociedades  o asociaciones e intervenir en todos los asuntos\ny derechos relacionados con ellas.\nX. Establecer sucursales, oficinas, subsidiarias y agencias,\nasí  como representar o ser agente de empresas e intermediar\nen la venta de toda clase de bienes y servicios.\nXI.  Adquirir  o  poseer  por  cualquier título, usar, dar o\ntomar  en  arrendamiento,  administrar, vender o disponer en\ncualquier  forma,  de  todos los bienes muebles o inmuebles,\nasí  como  derechos  reales  o  personales  sobre ellos, que\nfueren  necesarios  o  convenientes  para la realización del\nobjeto de la sociedad.\nXII. Contratar al personal necesario.\nXIII.  En  general  celebrar toda clase de contratos ya sean\nciviles, mercantiles o de cualquier naturaleza.\n#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,NULL,'c4ca4238a0b923820dcc509a6f75849b','sesion','2014-09-29 17:37:46'),('e4da3b7fbbce2345d7772b0674a318d5','transportes','clausulas transportes',0,1,'2014-09-29 17:37:46','\n\n\nexplotación  del Servicio Público de Autotransporte de Carga\nGeneral  y  especializada  en  materiales  peligrosos  y sus\nresiduos  en  todas  sus  modalidades,  en  los  caminos  de\njurisdicción  federal y/o de jurisdicción local, autorizados\nmediante  las  concesiones  o permisos que para el efecto le\notorgue  a  la  Sociedad,  la Secretaría de Comunicaciones y\nTransportes   y/o   el  Gobierno  Local  correspondiente,  o\nmediante   las   concesiones   que  la  sociedad  reciba  en\ntransferencia en virtud de las concesiones o permisos que en\ngoce  le  aporten  sus  propios  socios  y que autoricen las\nautoridades competentes.\nb).-  La  explotación  del  Servicio Público Federal de Auto\nTransporte  de  Carga  General,  Especializada  y Residuos y\nmateriales   peligrosos,   materiales   para   construcción,\nmateriales  a  granel,  desechos  industriales  y materiales\ntóxicos  en  todos  los  caminos  de  Jurisdicción  Federal,\nmediante los permisos que expida la Autoridad competente.\nc).-  La explotación y operación de los servicios auxiliares\ny conexos.\nd).-  La  coordinación, enlace, combinaciones intercambio de\nequipo  con  otras  personas  físicas o morales dedicadas al\nmismo  objeto,  así  como  el  enrolamiento  interno  de los\nvehículos  con  que  la empresa realiza la prestación de los\nservicios tantos Federales como Locales.\ne).-  La  construcción,  reparación  y  conservación  de los\nequipos  y bienes necesarios para la mejor realización de su\nobjeto social.\nf).-   La   compra   y   venta   importación,   exportación,\nconsignación,   comisión   y  representación  de  equipo  de\ntransporte  en  general,  llantas, accesorios, lubricantes y\ntodos  aquellos  elementos que sean necesarios para el mejor\ndesarrollo de sus actividades.\ng).-   La   compra,   venta,   exportación,  importación,  \nconsignación  y comisión, etcétera, de todo tipo de bienes y\nproductos,   siendo   entre   otros  mobiliario,  enseres  \nelectrónicos  como  computadoras, copiadoras, reproductoras,\nmáquinas  sumadoras  y de escribir y toda clase de artículos\npara  la  oficina,  el  comercio y la industria; así como la\nreparación y mantenimiento de las mismas.\nh).-  Obrar  como agente representante, ya sea de personas o\nindustrias sean mexicanas o extranjeras.\ni).-  Tomar  en arrendamiento o subarrendamiento o comprar y\nbajo  cualquier título, adquirir los locales necesarios para\nel  establecimiento  de  sus  oficinas,  patios  operativos,\nfábricas,  almacenes  o  laboratorios  siempre que esos sean\ninmuebles  que  se  dediquen  exclusivamente  a  los objetos\nsociales;\nj).-  Dar  y  tomar  dinero  a título de préstamo y adquirir\nacciones  o partes de interés en otras sociedades nacionales\no extranjeras de objeto similar al de esta sociedad, siempre\nque  sea  permitido por las leyes, obteniendo en su caso los\npermisos previos que se requieran;\nK).-  Solicitar, obtener, comprar o adquirir cualesquiera de\ntodas   las  patentes,  derechos  de  patentes,  propiedades\nliterarias,  licencias y privilegios, invenciones, mejoras y\nprocedimientos,  marcas  de  fábrica,  nombres  comerciales,\nmembretes,  diseños  y  marcas  que  se  refieran y que sean\nútiles en conexiones con cualquier negocio de la sociedad.\nDesarrollar  y  conceder  licencias respecto a los mismos, o\nbien   venderlos   o  cambiarlos  para  comprar  o  adquirir\ncualquier tipo de bienes o servicios.\nl).-   Comprar,  vender,  traspasar,  hipotecar,  empeñar  y\ngarantizar   hasta  donde  sea  posible  cualquiera  de  las\nacciones   del   capital  social  y/o  cualesquiera  abonos,\nobligaciones,   pagarés,   valores   o   cualquier  otros  \ncomprobantes de adeudos;\nm).-  Pedir  préstamos  por  medio  de la venta o emisión de\nacciones  o  bonos pagarés u otras obligaciones de cualquier\nnaturaleza y asegurar los mismos por medio de hipoteca.\nn).-  En  general  la  celebración  de  toda clase de actos,\nconvenios,  comisiones,  mediaciones  y  contratos  civiles,\nmercantiles  y  administrativos  que  en la forma más amplia\npuedan  realizarse  para  sus  objetos  sociales,  directos,\nBconexos y afines.\nñ).-  Obtener  y  conceder préstamos, otorgando y recibiendo\ngarantías  específicas, emitir obligaciones, aceptar, girar,\nendosar, o avalar toda clase de Títulos de Crédito y otorgar\nFianzas,  Avales  o Garantías de cualquier clase respecto de\nlas  obligaciones  contraídas  o  de  los Títulos emitidos o\naceptados por terceros.\n#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,NULL,'c4ca4238a0b923820dcc509a6f75849b','sesion','2014-09-29 17:37:46'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','escuela','clausulas de escuela',0,1,'2014-09-29 17:37:46','\n\n\nLa  promoción,  coordinación e impartición de la educación y\nenseñanza  en  general en los grados, niveles y materias que\nlibremente  escoja,  tales como preescolar, primaria, media,\nsuperior  y  universitaria,  entre otras, pudiendo evaluar y\ncalificar   los   resultados   alcanzados  por  los  sujetos\nreceptores  de la educación y enseñanza referida, expidiendo\nal  efecto, en su caso, las constancias que acrediten dichos\nresultados.\nPor  lo  que enunciativa pero no limitativamente la sociedad\npodrá:\nI.   Promover,   constituir,   organizar,   operar  y  tomar\nparticipación  en el patrimonio, de toda clase de sociedades\nciviles,  organismos  o  asociaciones  tanto nacionales como\nextranjeras;   que   tengan   como   objeto   preponderante,\nactividades  culturales,  educativas  o  cualquier  otra que\nresulte similar o afín con su propio objeto social, pudiendo\nincluso,   participar  en  su  administración,  operación  y\nliquidación;\nII.  Adquirir  por  cualquier título de toda clase de bienes\nmuebles  e  inmuebles y derechos que sean necesarios para la\nrealización de su objeto social, así como establecer locales\npara prestar los servicios de referencia.\nIII.  Obtener  por  cualquier título, concesiones, permisos,\nautorizaciones,   licencias,   reconocimientos   o   -  -  \nincorporaciones,   así  como  celebrar  cualquier  clase  de\ncontratos con la administración pública sea federal o local,\nrelacionados con su objeto.\nIV.  Obtener,  adquirir,  registrar,  utilizar o disponer de\ntoda  clase  de patentes, marcas industriales y de servicio,\ncertificados  de  invención o nombres comerciales, diseños y\ndibujos  industriales,  derechos  de  autor y cualquier otro\ntipo  de  derechos  de  propiedad  industrial,  literaria  o\nartística,  y  derechos sobre ellos ya sea en México o en el\nextranjero.\nV.  Otorgar  avales,  así  como  obligarse  solidariamente y\nconstituir garantías a favor de terceros.\nVI.  Emitir,  girar, endosar, aceptar y suscribir toda clase\nde  títulos  de crédito sin que constituyan una especulación\ncomercial.\nVII. Contratar al personal necesario.\nVIII.  Celebrar  toda  clase  de  contratos  y  convenios, y\nejecutar  actos  u  operaciones de cualquier naturaleza, así\ncomo  otorgar  y suscribir cualquier clase de documentos que\nsean  necesarios  o  convenientes  para la realización de su\nobjeto,   a   excepción  de  aquellos  que  constituyan  una\nespeculación comercial.\n#[1;1H#[J#[0m##6RICO ALVAREZ Y ASOCIADOS S.C.#[2;1H##6EDICIONES#[21;1H#[J#[21;10HDOCUMENTO COPIADO...',1,NULL,'c4ca4238a0b923820dcc509a6f75849b','sesion','2014-09-29 17:37:46');
/*!40000 ALTER TABLE `tbcfgm18` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm19`
--

DROP TABLE IF EXISTS `tbcfgm19`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm19` (
  `ideditor` varchar(32) NOT NULL,
  `dstitulo` varchar(30) NOT NULL,
  `txtexto` longtext,
  `isactivo` int(1) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ideditor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm19`
--

LOCK TABLES `tbcfgm19` WRITE;
/*!40000 ALTER TABLE `tbcfgm19` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbcfgm19` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm20`
--

DROP TABLE IF EXISTS `tbcfgm20`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm20` (
  `identificador` varchar(32) NOT NULL,
  `dstitulo` varchar(90) NOT NULL,
  `dsdescripcion` varchar(250) NOT NULL,
  `dsruta` varchar(255) DEFAULT NULL,
  `idtipodoc` varchar(32) NOT NULL,
  `isgestionado` varchar(1) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`identificador`),
  KEY `idtipodoc` (`idtipodoc`),
  CONSTRAINT `tbcfgm20_ibfk_1` FOREIGN KEY (`idtipodoc`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm20`
--

LOCK TABLES `tbcfgm20` WRITE;
/*!40000 ALTER TABLE `tbcfgm20` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbcfgm20` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm21`
--

DROP TABLE IF EXISTS `tbcfgm21`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm21` (
  `identificador` int(11) NOT NULL AUTO_INCREMENT,
  `idftopdf` varchar(32) NOT NULL,
  `dscampo` varchar(90) NOT NULL,
  `dsvariable` varchar(90) NOT NULL,
  PRIMARY KEY (`identificador`),
  KEY `idftopdf` (`idftopdf`),
  CONSTRAINT `tbcfgm21_ibfk_1` FOREIGN KEY (`idftopdf`) REFERENCES `tbcfgm20` (`identificador`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm21`
--

LOCK TABLES `tbcfgm21` WRITE;
/*!40000 ALTER TABLE `tbcfgm21` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbcfgm21` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm22`
--

DROP TABLE IF EXISTS `tbcfgm22`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm22` (
  `idexpresion` varchar(32) NOT NULL,
  `idvariable` varchar(32) NOT NULL,
  `dsexpresion` text,
  `ifnulo` varchar(90) DEFAULT NULL,
  `isvalido` int(1) DEFAULT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idexpresion`),
  KEY `idvariable` (`idvariable`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm22`
--

LOCK TABLES `tbcfgm22` WRITE;
/*!40000 ALTER TABLE `tbcfgm22` DISABLE KEYS */;
INSERT INTO `tbcfgm22` VALUES ('74a21b71c1e02fbd47791721d3089deb','e6d043416c1257944b779a9ae26a6bdb','f:si(${cmp:gravamen[grav_cancelado_tipo]}!=\'grav_pre\'\n,\nf:visible(${cmp:gravamen[grav_cancelacion_entidad]},false)\nf:visible(${cmp:gravamen[grav_canc_num_exp]},false)\nf:visible(${cmp:gravamen[grav_cancelacion_nom_not]},false)\nf:visible(${cmp:gravamen[grav_cancelacion_num_not]},false)\nf:visible(${cmp:gravamen[grav_cancelacion_num_esc]},false)\nf:visible(${cmp:gravamen[escritura_fecha_elaboracion]},false)\n\nf:visible(${cmp:gravamen[grav_tiene_exp]},false)\nf:asignar(${cmp:gravamen[grav_tiene_exp]},null)\nf:asignar(${cmp:gravamen[grav_canc_num_exp]},\' \')\nf:asignar(${cmp:gravamen[escritura_fecha_elaboracion]},\' \')\nf:asignar(${cmp:gravamen[grav_cancelacion_nom_not]},\' \')\nf:asignar(${cmp:gravamen[grav_cancelacion_num_not]},\' \')\nf:asignar(${cmp:gravamen[grav_cancelacion_num_esc]},\' \')\nf:asignar(${cmp:gravamen[grav_cancelacion_entidad]},\' \')\n,f:visible(${cmp:gravamen[grav_tiene_exp]},true)\n)\n;',NULL,1,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:45:17'),('8a2938d08eca677700a274955bb8947b','28ffbcb91dcc77b4da901ca3f3effd2d','f:si(${cmp:subfrm_subform_0[isr_tipo]}==\'exento\',\nf:visible(${cmp:subfrm_subform_0[isr_pago]} ,false)\nf:visible(${cmp:subfrm_subform_0[isr_noexenta]} ,false),);\n\nf:si(${cmp:subfrm_subform_0[isr_tipo]}==\'perdida\',\nf:visible(${cmp:subfrm_subform_0[isr_pago]} ,false)\nf:visible(${cmp:subfrm_subform_0[isr_noexenta]} ,false),);\n\nf:si(${cmp:subfrm_subform_0[isr_tipo]}==\'pm\',\nf:visible(${cmp:subfrm_subform_0[isr_pago]} ,false)\nf:visible(${cmp:subfrm_subform_0[isr_noexenta]} ,false),);\n\nf:si(${cmp:subfrm_subform_0[isr_tipo]}==\'pfcae\',\nf:visible(${cmp:subfrm_subform_0[isr_pago]} ,false)\nf:visible(${cmp:subfrm_subform_0[isr_noexenta]} ,false),);\n\nf:si(${cmp:subfrm_subform_0[isr_tipo]}==\'exc_pago\',\nf:visible(${cmp:subfrm_subform_0[isr_pago]} ,true)\nf:visible(${cmp:subfrm_subform_0[isr_noexenta]} ,true),);\n\nf:si(${cmp:subfrm_subform_0[isr_tipo]}==\'exc_per\',\nf:visible(${cmp:subfrm_subform_0[isr_pago]} ,false)\nf:visible(${cmp:subfrm_subform_0[isr_noexenta]} ,true),);\n\nf:si(${cmp:subfrm_subform_0[isr_tipo]}==\'pago\',\nf:visible(${cmp:subfrm_subform_0[isr_pago]} ,true)\nf:visible(${cmp:subfrm_subform_0[isr_noexenta]} ,false),);',NULL,1,'81646793614D3028A0564697508B9AE6','2014-09-04 21:13:43'),('977db3bccd4b6547fb52f17959b12842','714789153d41838e18b5f7d4dca21cf7','f:si(${cmp:gravamen[grav_tiene_exp]}==\'true\'\n,\nf:visible(${cmp:gravamen[grav_cancelacion_num_esc]},true)\nf:visible(${cmp:gravamen[grav_cancelacion_nom_not]},false)\nf:visible(${cmp:gravamen[grav_cancelacion_num_not]},false)\nf:visible(${cmp:gravamen[grav_cancelacion_entidad]},false)\nf:visible(${cmp:gravamen[escritura_fecha_elaboracion]},false)\nf:asignar(${cmp:gravamen[escritura_fecha_elaboracion]},\'\')\nf:asignar(${cmp:gravamen[grav_cancelacion_nom_not]},\'\')\nf:asignar(${cmp:gravamen[grav_cancelacion_num_not]},\'\')\nf:asignar(${cmp:gravamen[grav_cancelacion_entidad]},\'\')\nf:asignar(${cmp:gravamen[escritura_fecha_elaboracion]},\'\')\n,\nf:visible(${cmp:gravamen[grav_cancelacion_num_esc]},true)\nf:visible(${cmp:gravamen[grav_cancelacion_nom_not]},true)\nf:visible(${cmp:gravamen[grav_cancelacion_num_not]},true)\nf:visible(${cmp:gravamen[grav_cancelacion_entidad]},true)\nf:visible(${cmp:gravamen[escritura_fecha_elaboracion]},true)\n)',NULL,1,'34D00405F6DBD143837D49444443FE0E','2014-08-28 16:44:51');
/*!40000 ALTER TABLE `tbcfgm22` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm30`
--

DROP TABLE IF EXISTS `tbcfgm30`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm30` (
  `identificador` varchar(32) NOT NULL,
  `dsdescripcion` varchar(250) DEFAULT NULL,
  `dsnombre` varchar(60) NOT NULL,
  `idretorno` varchar(32) NOT NULL,
  `dsforma` varchar(32) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`identificador`),
  KEY `idretorno` (`idretorno`),
  CONSTRAINT `tbcfgm30_ibfk_1` FOREIGN KEY (`idretorno`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm30`
--

LOCK TABLES `tbcfgm30` WRITE;
/*!40000 ALTER TABLE `tbcfgm30` DISABLE KEYS */;
INSERT INTO `tbcfgm30` VALUES ('8f14e45fceea167a5a36dedd4bea2543','Asignar valor','asignar','a87ff679a2f3e71d9181a67b7542122c','asignar(componente,valor)','7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),('c81e728d9d4c2f636f067f89cc14862c','Condicional si','si','a87ff679a2f3e71d9181a67b7542122c','si(condicion,cierto,falso)','7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),('e4da3b7fbbce2345d7772b0674a318d5','Visible','visible','a87ff679a2f3e71d9181a67b7542122c','visible(componente,ciertofalso)','7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','Activa','activa','a87ff679a2f3e71d9181a67b7542122c','activa(componente,ciertofalso)','7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52');
/*!40000 ALTER TABLE `tbcfgm30` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm31`
--

DROP TABLE IF EXISTS `tbcfgm31`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm31` (
  `cdfuncion` int(11) NOT NULL AUTO_INCREMENT,
  `idfuncion` varchar(32) NOT NULL,
  `dsparam` varchar(30) DEFAULT NULL,
  `idtipo` varchar(32) NOT NULL,
  `isrequerido` int(11) NOT NULL,
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cdfuncion`),
  KEY `idfuncion` (`idfuncion`),
  KEY `idtipo` (`idtipo`),
  CONSTRAINT `tbcfgm31_ibfk_1` FOREIGN KEY (`idfuncion`) REFERENCES `tbcfgm30` (`identificador`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tbcfgm31_ibfk_2` FOREIGN KEY (`idtipo`) REFERENCES `tbcfgm91` (`idelemento`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm31`
--

LOCK TABLES `tbcfgm31` WRITE;
/*!40000 ALTER TABLE `tbcfgm31` DISABLE KEYS */;
INSERT INTO `tbcfgm31` VALUES (1,'c81e728d9d4c2f636f067f89cc14862c','condicion','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),(2,'c81e728d9d4c2f636f067f89cc14862c','cierto','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),(3,'c81e728d9d4c2f636f067f89cc14862c','falso','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),(4,'eccbc87e4b5ce2fe28308fd9f2a7baf3','componente','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),(5,'eccbc87e4b5ce2fe28308fd9f2a7baf3','ciertofalso','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),(6,'e4da3b7fbbce2345d7772b0674a318d5','componente','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),(7,'e4da3b7fbbce2345d7772b0674a318d5','ciertofalso','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),(8,'8f14e45fceea167a5a36dedd4bea2543','componente','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52'),(9,'8f14e45fceea167a5a36dedd4bea2543','valor','a87ff679a2f3e71d9181a67b7542122c',1,'7ABBE1DAEADF9E2CA02A10CBABC6D9AD','2014-07-10 19:08:52');
/*!40000 ALTER TABLE `tbcfgm31` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm32`
--

DROP TABLE IF EXISTS `tbcfgm32`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm32` (
  `idvarstipo` varchar(32) NOT NULL,
  `idvariable` varchar(32) DEFAULT NULL,
  `idcomponente` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`idvarstipo`),
  KEY `idvariable` (`idvariable`),
  KEY `idcomponente` (`idcomponente`),
  CONSTRAINT `tbcfgm32_ibfk_1` FOREIGN KEY (`idvariable`) REFERENCES `tbcfgm08` (`idvariable`),
  CONSTRAINT `tbcfgm32_ibfk_2` FOREIGN KEY (`idcomponente`) REFERENCES `tbbsnm50` (`idcomponente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm32`
--

LOCK TABLES `tbcfgm32` WRITE;
/*!40000 ALTER TABLE `tbcfgm32` DISABLE KEYS */;
INSERT INTO `tbcfgm32` VALUES ('28ffbcb91dcc77b4da901ca3f3effd2d',NULL,'12035270d4da81e3871b4e03c0e9947c'),('714789153d41838e18b5f7d4dca21cf7',NULL,'5de9a9aed0cb5a37e051cb32f8111b9c'),('e6d043416c1257944b779a9ae26a6bdb',NULL,'3bdf78cb4067c2ee20447498084a5591');
/*!40000 ALTER TABLE `tbcfgm32` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm89`
--

DROP TABLE IF EXISTS `tbcfgm89`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm89` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idsesion` varchar(32) NOT NULL,
  `idusuario` varchar(32) NOT NULL,
  `fchinicio` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fchfinprogr` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fchtermino` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `inip` varchar(15) DEFAULT NULL,
  `islogout` int(1) NOT NULL,
  `agente` longtext,
  `tmstmp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm89`
--

LOCK TABLES `tbcfgm89` WRITE;
/*!40000 ALTER TABLE `tbcfgm89` DISABLE KEYS */;
INSERT INTO `tbcfgm89` VALUES (1,'1','c81e728d9d4c2f636f067f89cc14862c','2014-09-29 17:37:35','2014-09-29 17:37:35','2014-09-29 17:37:35','',0,'','2014-09-29 17:37:35'),(2,'DD7DED1135D1562C8C96F98F35005FFA','fae0b27c451c728867a567e8c1bb4e53','2014-09-29 18:01:46','2014-09-29 18:01:46','2014-09-29 18:01:46',NULL,0,NULL,'2014-09-29 18:01:46'),(3,'A3A7923666CCC74ADFA48738B700EC10','fae0b27c451c728867a567e8c1bb4e53','2014-09-29 18:08:14','2014-09-29 18:08:14','2014-09-29 18:08:14',NULL,0,NULL,'2014-09-29 18:08:14'),(4,'3A6F6A7060C6601A99B43459140D6E81','fae0b27c451c728867a567e8c1bb4e53','2014-09-29 19:39:34','2014-09-29 19:39:34','2014-09-29 19:39:34',NULL,0,NULL,'2014-09-29 19:39:34'),(5,'D78E664FEA66D90692FDE38B0E3EFB47','1679091c5a880faf6fb5e6087eb1b2dc','2014-09-29 19:57:40','2014-09-29 19:57:40','2014-09-29 19:57:40',NULL,0,NULL,'2014-09-29 19:57:40');
/*!40000 ALTER TABLE `tbcfgm89` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm90`
--

DROP TABLE IF EXISTS `tbcfgm90`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm90` (
  `idcatalogo` varchar(32) NOT NULL,
  `dsnombre` varchar(60) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idsesion` varchar(32) NOT NULL,
  PRIMARY KEY (`idcatalogo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm90`
--

LOCK TABLES `tbcfgm90` WRITE;
/*!40000 ALTER TABLE `tbcfgm90` DISABLE KEYS */;
INSERT INTO `tbcfgm90` VALUES ('1679091c5a880faf6fb5e6087eb1b2dc','suboperacion','2014-09-29 17:37:36','sesion'),('1f0e3dad99908345f7439f8ffabdffc4','docs_oficiales','2014-09-29 17:37:36','sesion'),('38b3eff8baf56627478ec76a704e9b52','Variables Filtro','2014-09-29 17:37:36','sesion'),('3c59dc048e8850243be8079a5c74d079','tratamiento','2014-09-29 17:37:36','sesion'),('45c48cce2e2d7fbdea1afc51c7c6ad26','tipo_dato','2014-09-29 17:37:36','sesion'),('6512bd43d9caa6e02c990b0a82652dca','vocacion','2014-09-29 17:37:36','sesion'),('6f4922f45568161a8cdf4ad2299f6d23','concepto_pago','2014-09-29 17:37:36','sesion'),('70efdf2ec9b086079795c442636b55fb','medio_pago','2014-09-29 17:37:36','sesion'),('8f14e45fceea167a5a36dedd4bea2543','tipo_persona','2014-09-29 17:37:36','sesion'),('98f13708210194c475687be6106a3b84','instituciones_gobierno','2014-09-29 17:37:36','sesion'),('a87ff679a2f3e71d9181a67b7542122c','tipo_compareciente','2014-09-29 17:37:36','sesion'),('aab3238922bcc25a6f606eb525ffdc56','tipo_componente_formulario','2014-09-29 17:37:36','sesion'),('b6d767d2f8ed5d21a44b0e5886680cb9','regimen','2014-09-29 17:37:36','sesion'),('c20ad4d76fe97759aa27a0c99bff6710','operador','2014-09-29 17:37:36','sesion'),('c4ca4238a0b923820dcc509a6f75849b','nacionalidad','2014-09-29 17:37:35','sesion'),('c51ce410c124a10e0db5e4b97fc2af39','locacion','2014-09-29 17:37:36','sesion'),('c74d97b01eae257e44aa9d5bade97baf','status_anticipo','2014-09-29 17:37:36','sesion'),('c81e728d9d4c2f636f067f89cc14862c','estado_civil','2014-09-29 17:37:35','sesion'),('c9f0f895fb98ab9159f51fd0297e236d','procesos','2014-09-29 17:37:36','sesion'),('d3d9446802a44259755d38e6d163e820','tipo_documento','2014-09-29 17:37:36','sesion'),('e2ef524fbf3d9fe611d5a8e90fefdc9c','Entidad','2014-09-29 17:37:36','sesion'),('e4da3b7fbbce2345d7772b0674a318d5','operacion','2014-09-29 17:37:36','sesion'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','ocupacion','2014-09-29 17:37:35','sesion');
/*!40000 ALTER TABLE `tbcfgm90` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbcfgm91`
--

DROP TABLE IF EXISTS `tbcfgm91`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbcfgm91` (
  `idelemento` varchar(32) NOT NULL,
  `idcatalogo` varchar(32) NOT NULL,
  `dselemento` varchar(60) NOT NULL,
  `dscodigo` varchar(10) DEFAULT NULL,
  `iseliminado` int(1) DEFAULT '0',
  `idsesion` varchar(32) NOT NULL,
  `tmstmp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idelemento`),
  KEY `idcatalogo` (`idcatalogo`),
  CONSTRAINT `tbcfgm91_ibfk_1` FOREIGN KEY (`idcatalogo`) REFERENCES `tbcfgm90` (`idcatalogo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbcfgm91`
--

LOCK TABLES `tbcfgm91` WRITE;
/*!40000 ALTER TABLE `tbcfgm91` DISABLE KEYS */;
INSERT INTO `tbcfgm91` VALUES ('02e74f10e0327ad868d138f2b4fdd6f0','c9f0f895fb98ab9159f51fd0297e236d','Posteriores','codigo',0,'sesion','2014-09-29 17:37:38'),('03afdbd66e7929b125f8597834fa83a4','70efdf2ec9b086079795c442636b55fb','N/A','codigo',0,'sesion','2014-09-29 17:37:39'),('072b030ba126b2f4b2374f342be9ed44','c74d97b01eae257e44aa9d5bade97baf','Pagado','codigo',0,'sesion','2014-09-29 17:37:38'),('14bfa6bb14875e45bba028a21ed38046','6f4922f45568161a8cdf4ad2299f6d23','Tramite asociado','codigo',0,'sesion','2014-09-29 17:37:39'),('1679091c5a880faf6fb5e6087eb1b2dc','45c48cce2e2d7fbdea1afc51c7c6ad26','Fecha','codigo',0,'sesion','2014-09-29 17:37:37'),('17e62166fc8586dfa4d1bc0e1742c08b','c20ad4d76fe97759aa27a0c99bff6710','!=','codigo',0,'sesion','2014-09-29 17:37:38'),('182be0c5cdcd5072bb1864cdee4d3d6e','6512bd43d9caa6e02c990b0a82652dca','Habitacional','codigo',0,'sesion','2014-09-29 17:37:38'),('19ca14e7ea6328a42e0eb13d585e4c22','c51ce410c124a10e0db5e4b97fc2af39','Estado de Mexico','codigo',0,'sesion','2014-09-29 17:37:38'),('1c383cd30b7c298ab50293adfecb7b18','c51ce410c124a10e0db5e4b97fc2af39','Distrito Federal','codigo',0,'sesion','2014-09-29 17:37:38'),('1f0e3dad99908345f7439f8ffabdffc4','eccbc87e4b5ce2fe28308fd9f2a7baf3','Empresario','codigo',0,'sesion','2014-09-29 17:37:37'),('1ff1de774005f8da13f42943881c655f','c9f0f895fb98ab9159f51fd0297e236d','Previos','codigo',0,'sesion','2014-09-29 17:37:37'),('2723d092b63885e0d7c260cc007e8b9d','38b3eff8baf56627478ec76a704e9b52','Objeto Acto','---',0,'sesion','2014-09-29 17:37:36'),('2838023a778dfaecdc212708f721b788','aab3238922bcc25a6f606eb525ffdc56','Casilla de verificaciÃ³n','check',0,'sesion','2014-09-29 17:37:38'),('28dd2c7955ce926456240b2ff0100bde','98f13708210194c475687be6106a3b84','Secretaría De Relaciones Exteriores','codigo',0,'sesion','2014-09-29 17:37:39'),('2a38a4a9316c49e5a833517c45d31070','98f13708210194c475687be6106a3b84','Autoridad Competente','codigo',0,'sesion','2014-09-29 17:37:40'),('3295c76acbf4caaed33c36b1b5fc2cb1','70efdf2ec9b086079795c442636b55fb','Cheque','codigo',0,'sesion','2014-09-29 17:37:39'),('32bb90e8976aab5298d5da10fe66f21d','6f4922f45568161a8cdf4ad2299f6d23','Hoja amarilla','codigo',0,'sesion','2014-09-29 17:37:39'),('33e75ff09dd601bbe69f351039152189','c9f0f895fb98ab9159f51fd0297e236d','Testimonios','codigo',0,'sesion','2014-09-29 17:37:38'),('3416a75f4cea9109507cacd8e2f2aefc','c20ad4d76fe97759aa27a0c99bff6710','<=','codigo',0,'sesion','2014-09-29 17:37:38'),('34173cb38f07f89ddbebc2ac9128303f','c9f0f895fb98ab9159f51fd0297e236d','Entrega','codigo',0,'sesion','2014-09-29 17:37:38'),('35f4a8d465e6e1edc05f3d8ab658c551','98f13708210194c475687be6106a3b84','Secretaría De Defensa Nacional','codigo',0,'sesion','2014-09-29 17:37:39'),('37693cfc748049e45d87b8c7d8b9aacd','c9f0f895fb98ab9159f51fd0297e236d','Atención de clientes','codigo',0,'sesion','2014-09-29 17:37:37'),('38b3eff8baf56627478ec76a704e9b52','e2ef524fbf3d9fe611d5a8e90fefdc9c','Acto','---',0,'sesion','2014-09-29 17:37:36'),('3c59dc048e8850243be8079a5c74d079','8f14e45fceea167a5a36dedd4bea2543','Física','codigo',0,'sesion','2014-09-29 17:37:37'),('3ef815416f775098fe977004015c6193','98f13708210194c475687be6106a3b84','Gobierno Estatal','codigo',0,'sesion','2014-09-29 17:37:39'),('43ec517d68b6edd3015b3edc9a11367b','1f0e3dad99908345f7439f8ffabdffc4','Documento Migratorio','codigo',0,'sesion','2014-09-29 17:37:39'),('44f683a84163b3523afe57c2e008bc8c','c74d97b01eae257e44aa9d5bade97baf','Pendiente de Pago','codigo',0,'sesion','2014-09-29 17:37:39'),('45c48cce2e2d7fbdea1afc51c7c6ad26','c4ca4238a0b923820dcc509a6f75849b','Extranjera','codigo',0,'sesion','2014-09-29 17:37:37'),('4ba29b9f9e5732ed33761840f4ba6c53','eccbc87e4b5ce2fe28308fd9f2a7baf3','Desempleado','codigo',0,'sesion','2014-09-29 17:37:37'),('4e732ced3463d06de0ca9a15b6153677','c9f0f895fb98ab9159f51fd0297e236d','Atencion firmas','codigo',0,'sesion','2014-09-29 17:37:37'),('54229abfcfa5649e7003b83dd4755294','3c59dc048e8850243be8079a5c74d079','Señorita','codigo',0,'sesion','2014-09-29 17:37:40'),('6364d3f0f495b6ab9dcf8d3b5c6e0b01','6512bd43d9caa6e02c990b0a82652dca','Terreno','codigo',0,'sesion','2014-09-29 17:37:38'),('642e92efb79421734881b53e1e1b18b6','aab3238922bcc25a6f606eb525ffdc56','SubFormulario','subform',0,'sesion','2014-09-29 17:37:38'),('6512bd43d9caa6e02c990b0a82652dca','c81e728d9d4c2f636f067f89cc14862c','Casado','codigo',0,'sesion','2014-09-29 17:37:37'),('67c6a1e7ce56d3d6fa748ab6d9af3fd7','aab3238922bcc25a6f606eb525ffdc56','Título','header',0,'sesion','2014-09-29 17:37:38'),('68d30a9594728bc39aa24be94b319d21','98f13708210194c475687be6106a3b84','Gobierno Federal','codigo',0,'sesion','2014-09-29 17:37:39'),('6974ce5ac660610b44d9b9fed0ff9548','e2ef524fbf3d9fe611d5a8e90fefdc9c','Escritura','---',0,'sesion','2014-09-29 17:37:36'),('698d51a19d8a121ce581499d7b701668','38b3eff8baf56627478ec76a704e9b52','Escritura','---',0,'sesion','2014-09-29 17:37:36'),('6c8349cc7260ae62e3b1396831a8398f','aab3238922bcc25a6f606eb525ffdc56','Campo de texto','text',0,'sesion','2014-09-29 17:37:38'),('6ea9ab1baa0efb9e19094440c317e21b','c9f0f895fb98ab9159f51fd0297e236d','Registro','codigo',0,'sesion','2014-09-29 17:37:38'),('6f4922f45568161a8cdf4ad2299f6d23','eccbc87e4b5ce2fe28308fd9f2a7baf3','Servidor Público','codigo',0,'sesion','2014-09-29 17:37:37'),('70efdf2ec9b086079795c442636b55fb','eccbc87e4b5ce2fe28308fd9f2a7baf3','Ama de casa','codigo',0,'sesion','2014-09-29 17:37:37'),('735b90b4568125ed6c3f678819b6e058','70efdf2ec9b086079795c442636b55fb','Crédito','codigo',0,'sesion','2014-09-29 17:37:39'),('7647966b7343c29048673252e490f736','3c59dc048e8850243be8079a5c74d079','Señor','codigo',0,'sesion','2014-09-29 17:37:40'),('7cbbc409ec990f19c78c75bd1e06f215','6f4922f45568161a8cdf4ad2299f6d23','Liquidación de la operación','codigo',0,'sesion','2014-09-29 17:37:39'),('7f39f8317fbdb1988ef4c628eba02591','c74d97b01eae257e44aa9d5bade97baf','No Pagado','codigo',0,'sesion','2014-09-29 17:37:38'),('8613985ec49eb8f757ae6439e879bb2a','3c59dc048e8850243be8079a5c74d079','Señora','codigo',0,'sesion','2014-09-29 17:37:40'),('8e296a067a37563370ded05f5a3bf3ec','c9f0f895fb98ab9159f51fd0297e236d','Elaboracion','codigo',0,'sesion','2014-09-29 17:37:37'),('8f14e45fceea167a5a36dedd4bea2543','45c48cce2e2d7fbdea1afc51c7c6ad26','Moneda','codigo',0,'sesion','2014-09-29 17:37:37'),('908c9a564a86426585b29f5335b619bc','c9f0f895fb98ab9159f51fd0297e236d','Gestion de libros','codigo',0,'sesion','2014-09-29 17:37:38'),('92cc227532d17e56e07902b254dfad10','b6d767d2f8ed5d21a44b0e5886680cb9','Bienes Separados','codigo',0,'sesion','2014-09-29 17:37:40'),('93db85ed909c13838ff95ccfa94cebd9','98f13708210194c475687be6106a3b84','Gobierno Municipal','codigo',0,'sesion','2014-09-29 17:37:40'),('9778d5d219c5080b9a6a17bef029331c','1f0e3dad99908345f7439f8ffabdffc4','Certificado De Matrícula Consular','codigo',0,'sesion','2014-09-29 17:37:39'),('98dce83da57b0395e163467c9dae521b','b6d767d2f8ed5d21a44b0e5886680cb9','Sociedad Conyugal','codigo',0,'sesion','2014-09-29 17:37:40'),('98f13708210194c475687be6106a3b84','eccbc87e4b5ce2fe28308fd9f2a7baf3','Empleado','codigo',0,'sesion','2014-09-29 17:37:37'),('9a1158154dfa42caddbd0694a4e9bdc8','aab3238922bcc25a6f606eb525ffdc56','Lista desplegable opciÃ³n multiple','sel-check',0,'sesion','2014-09-29 17:37:38'),('9bf31c7ff062936a96d3c8bd1f8f2ff3','eccbc87e4b5ce2fe28308fd9f2a7baf3','Profesional','codigo',0,'sesion','2014-09-29 17:37:37'),('a1d0c6e83f027327d8461063f4ac58a6','c20ad4d76fe97759aa27a0c99bff6710','==','codigo',0,'sesion','2014-09-29 17:37:38'),('a3f390d88e4c41f2747bfa2f1b5f87db','6f4922f45568161a8cdf4ad2299f6d23','Impuesto','codigo',0,'sesion','2014-09-29 17:37:39'),('a5771bce93e200c36f7cd9dfd0e5deaa','c20ad4d76fe97759aa27a0c99bff6710','>','codigo',0,'sesion','2014-09-29 17:37:38'),('a5bfc9e07964f8dddeb95fc584cd965d','c51ce410c124a10e0db5e4b97fc2af39','Foráneo','codigo',0,'sesion','2014-09-29 17:37:38'),('a87ff679a2f3e71d9181a67b7542122c','45c48cce2e2d7fbdea1afc51c7c6ad26','Texto','codigo',0,'sesion','2014-09-29 17:37:37'),('a97da629b098b75c294dffdc3e463904','38b3eff8baf56627478ec76a704e9b52','Acto','---',0,'sesion','2014-09-29 17:37:36'),('aab3238922bcc25a6f606eb525ffdc56','eccbc87e4b5ce2fe28308fd9f2a7baf3','Estudiante','codigo',0,'sesion','2014-09-29 17:37:37'),('ad61ab143223efbc24c7d2583be69251','1f0e3dad99908345f7439f8ffabdffc4','Pasaporte','codigo',0,'sesion','2014-09-29 17:37:39'),('b6d767d2f8ed5d21a44b0e5886680cb9','8f14e45fceea167a5a36dedd4bea2543','Moral','codigo',0,'sesion','2014-09-29 17:37:37'),('c0c7c76d30bd3dcaefc96f40275bdc0a','aab3238922bcc25a6f606eb525ffdc56','Lista tipo test','radio',0,'sesion','2014-09-29 17:37:38'),('c16a5320fa475530d9583c34fd356ef5','c9f0f895fb98ab9159f51fd0297e236d','Cotejo','codigo',0,'sesion','2014-09-29 17:37:38'),('c74d97b01eae257e44aa9d5bade97baf','eccbc87e4b5ce2fe28308fd9f2a7baf3','Jubilado','codigo',0,'sesion','2014-09-29 17:37:37'),('c7e1249ffc03eb9ded908c236bd1996d','98f13708210194c475687be6106a3b84','Gobierno Del Distrito Federal','codigo',0,'sesion','2014-09-29 17:37:40'),('c81e728d9d4c2f636f067f89cc14862c','d3d9446802a44259755d38e6d163e820','Posterior','codigo',0,'sesion','2014-09-29 17:37:37'),('c9f0f895fb98ab9159f51fd0297e236d','c4ca4238a0b923820dcc509a6f75849b','Mexicana','codigo',0,'sesion','2014-09-29 17:37:37'),('d09bf41544a3365a46c9077ebb5e35c3','1f0e3dad99908345f7439f8ffabdffc4','Cartilla Militar','codigo',0,'sesion','2014-09-29 17:37:39'),('d0fb963ff976f9c37fc81fe03c21ea7b','eccbc87e4b5ce2fe28308fd9f2a7baf3','Empleador','codigo',0,'sesion','2014-09-29 17:37:37'),('d1fe173d08e959397adf34b1d77e88d7','1f0e3dad99908345f7439f8ffabdffc4','Cedula Profesional','codigo',0,'sesion','2014-09-29 17:37:39'),('d2ddea18f00665ce8623e36bd4e3c7c5','1f0e3dad99908345f7439f8ffabdffc4','Credencial INE','codigo',0,'sesion','2014-09-29 17:37:39'),('d3d9446802a44259755d38e6d163e820','c81e728d9d4c2f636f067f89cc14862c','Soltero','codigo',0,'sesion','2014-09-29 17:37:37'),('d645920e395fedad7bbbed0eca3fe2e0','c20ad4d76fe97759aa27a0c99bff6710','>=','codigo',0,'sesion','2014-09-29 17:37:38'),('d67d8ab4f4c10bf22aa353e27879133c','c20ad4d76fe97759aa27a0c99bff6710','<','codigo',0,'sesion','2014-09-29 17:37:38'),('d82c8d1619ad8176d665453cfb2e55f0','aab3238922bcc25a6f606eb525ffdc56','SiNo','singlechk',0,'sesion','2014-09-29 17:37:38'),('d9d4f495e875a2e075a1a4a6e1b9770f','aab3238922bcc25a6f606eb525ffdc56','Campo de  fecha','datetime',0,'sesion','2014-09-29 17:37:38'),('e2c420d928d4bf8ce0ff2ec19b371514','6f4922f45568161a8cdf4ad2299f6d23','Anticipo de la operación','codigo',0,'sesion','2014-09-29 17:37:39'),('e369853df766fa44e1ed0ff613f563bd','6512bd43d9caa6e02c990b0a82652dca','Comercial','codigo',0,'sesion','2014-09-29 17:37:38'),('e4da3b7fbbce2345d7772b0674a318d5','45c48cce2e2d7fbdea1afc51c7c6ad26','Entero','codigo',0,'sesion','2014-09-29 17:37:37'),('ea5d2f1c4608232e07d3aa3d998e5135','70efdf2ec9b086079795c442636b55fb','Efectivo','codigo',0,'sesion','2014-09-29 17:37:39'),('eccbc87e4b5ce2fe28308fd9f2a7baf3','d3d9446802a44259755d38e6d163e820','Previo','codigo',0,'sesion','2014-09-29 17:37:37'),('f033ab37c30201f73f142449d037028d','1f0e3dad99908345f7439f8ffabdffc4','Identificación Oficial','codigo',0,'sesion','2014-09-29 17:37:39'),('f457c545a9ded88f18ecee47145a72c0','aab3238922bcc25a6f606eb525ffdc56','Lista desplegable','select',0,'sesion','2014-09-29 17:37:38'),('f7177163c833dff4b38fc8d2872f1ec6','c20ad4d76fe97759aa27a0c99bff6710','Exp. Reg.','codigo',0,'sesion','2014-09-29 17:37:38'),('fbd7939d674997cdb4692d34de8633c4','98f13708210194c475687be6106a3b84','Instituto Nacional Electoral','codigo',0,'sesion','2014-09-29 17:37:39'),('fc490ca45c00b1249bbe3554a4fdf6fb','70efdf2ec9b086079795c442636b55fb','Tarjeta','codigo',0,'sesion','2014-09-29 17:37:39'),('fe9fc289c3ff0af142b6d3bead98a923','98f13708210194c475687be6106a3b84','Secretaría De Educación Pública','codigo',0,'sesion','2014-09-29 17:37:39');
/*!40000 ALTER TABLE `tbcfgm91` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-29 16:18:26
