1679091c5a880faf6fb5e6087eb1b2dc GON
8f14e45fceea167a5a36dedd4bea2543 DUA
dc2d18a26ddb94f655b89383c7c71cf4 ADM
fae0b27c451c728867a567e8c1bb4e53 ADM2


/// BORRAR USUARIOS
delete from tbcfgm03 where idusuario != "1679091c5a880faf6fb5e6087eb1b2dc" AND idusuario != "8f14e45fceea167a5a36dedd4bea25432" AND idusuario != "dc2d18a26ddb94f655b89383c7c71cf4" AND idusuario !="dc2d18a26ddb94f655b89383c7c71cf4";




// borrado de bitacora asignacion expediente
delete from tbbsnm02;


**//BORRAR EXPEDIENTES
/1/borrar acto
 delete from tbbsnm18;
/1.1/ compareciente
delete from  tbbsnm21;
/1.1.1/ representados
delete from tbbsnm39;
/1.1.2/ conyuge
delete from tbbsnm21;
/1.1.2.1/ conyuge actual
delete from tbbsnm61;
/1.2/ ESCRITURA ACTO
delete from tbbsnm36;
/1.2.1/ FORMULARIO ACTO
delete from tbbsnm51
1.2.1.1/SUBFORMULARIO ACTO
delete from tbbsnm52;
delete from tbbsnm53;
1.3.1/ documentos
delete from tbbsnm37;


2/borrar escrituras
delete from tbbsnm24;
2.1/ version escrituras
delete from tbbsnm33;

3/ borrar bitacora
delete from tbbsnm42;

-- TODAS LAS ESCRITURAS--
delete from tbbsnm32;


---
**//BORRAR EXPEDIENTES
--actos y comparecientes--
 delete from tbbsnm37;
 delete from tbbsnm53;
 delete from tbbsnm52;
 delete from tbbsnm51
 delete from tbbsnm36;
 delete from tbbsnm61;
 delete from tbbsnm21;
 delete from tbbsnm39;
 delete from  tbbsnm21;
 delete from tbbsnm18;
--bitacoras--
delete from tbbsnm02;
-- escrituras --
delete from tbbsnm24;
delete from tbbsnm33;
delete from tbbsnm42;

-- TODAS LAS ESCRITURAS--
delete from tbbsnm32;
--

** // BORRA USUARIOS
 // 1.0 BORRAR TRAMITES
DELETE FROM tbbsnm40;
//1.0.1 domicilios
DELETE FROM tbbsnm16;
// 1.0.2 GRUPOS DE TRABAJO
DELETE FROM tbcfgm12;
// 1.0.3 documento Objeto
DELETE FROM tbcfgm18;

// borrar usuarios menos notarios y admins
DELETE FROM tbcfgm03 where idrol != "45c48cce2e2d7fbdea1afc51c7c6ad26" AND idrol != "cfcd208495d565ef66e7dff9f98764da";

** // BORRA USUARIOS
-- 
DELETE FROM tbcfgm18;
DELETE FROM tbcfgm12;
DELETE FROM tbbsnm16;
DELETE FROM tbbsnm40;
-- USUARIOS
DELETE from tbcfgm03 where idusuario != "1679091c5a880faf6fb5e6087eb1b2dc" AND idusuario != "8f14e45fceea167a5a36dedd4bea25432" AND idusuario != "dc2d18a26ddb94f655b89383c7c71cf4" AND idusuario !="dc2d18a26ddb94f655b89383c7c71cf4";
---




////BORRADO DE PERSONA

//1.0 CONTACTO
DELETE FROM tbbsnm67;
//PERSONAS
DELETE FROM tbbsnm28;






