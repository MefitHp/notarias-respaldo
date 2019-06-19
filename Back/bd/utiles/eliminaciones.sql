

=== elimina expedientes ===
set @expdel="0597d0f803273d2481aef84cb7ca0253";
#elimino comparecientes
delete from tbbsnm61 where idsujeto in (select idcompareciente from tbbsnm21 where idacto in (select idacto from tbbsnm18 where idexpediente =@expdel));

delete from tbbsnm37 where idacto in (select idacto from tbbsnm18 where idexpediente = @expdel);

delete from tbbsnm21 where idacto in (select idacto from tbbsnm18 where idexpediente = @expdel);

#elimino formularios

delete from tbbsnm52 where idformulario in (select idformulario from tbbsnm51 where idacto in (select idacto from tbbsnm18 where idexpediente = @expdel));

delete from tbbsnm53 where idformulario in (select idformulario from tbbsnm51 where idacto in (select idacto from tbbsnm18 where idexpediente = @expdel));

delete from tbbsnm51 where idacto in (select idacto from tbbsnm18 where idexpediente = @expdel);

#elimino acto
delete from tbbsnm18 where idexpediente= @expdel;
#elimino bitacora
delete from tbbsnm42 where idexpediente= @expdel;
#elimino expediente
delete from tbbsnm32 where idexpediente= @expdel;


# fin eliminado de expediente

DELIMITER //
CREATE PROCEDURE eliminaTramitesTruncos()
COMMENT 'PROCESO PARA ELIMNAR LOS TRÁMITES QUE NO TIENEN EXPEDIENTE AL NO TERMINAR EL PROCESO DE REGISTRO'
BEGIN
   DECLARE EXIT HANDLER FOR SQLEXCEPTION
     BEGIN
	ROLLBACK;
     END;
  START TRANSACTION;
	DELETE  FROM tbbsnm02 where idtramite IN (SELECT idtramite FROM tbbsnm40 WHERE idtramite NOT IN(SELECT idtramite FROM tbbsnm32));
	DELETE FROM tbbsnm42 WHERE idtramite IN (SELECT idtramite FROM tbbsnm40 WHERE idtramite NOT IN (SELECT idtramite FROM tbbsnm32));
	DELETE FROM tbbsnm40 WHERE idtramite NOT IN(SELECT idtramite FROM tbbsnm32);
  COMMIT;
END//
DELIMITER ;		



DELIMITER //
CREATE PROCEDURE eliminaTramites()
BEGIN
  DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN	
        ROLLBACK;        
    END;
  START TRANSACTION;
  DELETE FROM tbbsnm61;
  DELETE FROM tbbsnm60b;
  DELETE FROM tbbsnm60;
  DELETE FROM tbbsnm59;
  DELETE FROM tbbsnm58;
  DELETE FROM tbbsnm57;
  DELETE FROM tbbsnm56;
  DELETE FROM tbbsnm55;
  DELETE FROM tbbsnm54;
  DELETE FROM tbbsnm53;
  DELETE FROM tbbsnm52;
  DELETE FROM tbbsnm51;
  DELETE FROM tbbsnm45;
  DELETE FROM tbbsnm42;
  DELETE FROM tbbsnm39;
  DELETE FROM tbbsnm37;
  DELETE FROM tbbsnm36;
  DELETE FROM tbbsnm35;
  DELETE FROM tbbsnm33;
  DELETE FROM tbbsnm30;
  DELETE FROM tbbsnm29;
  DELETE FROM tbbsnm23;
  DELETE FROM tbbsnm21;
  DELETE FROM tbbsnm19;
  DELETE FROM tbbsnm10;
  DELETE FROM tbbsnm09;
  DELETE FROM tbbsnm07;
  DELETE FROM tbbsnm03;
  DELETE FROM tbbsnm20;
  DELETE FROM tbbsnm06;
  DELETE FROM tbbsnm05;
  DELETE FROM tbbsnm04;
  DELETE FROM tbbsnm02;
  DELETE FROM tbbsnm01;
  DELETE FROM tbbsnm18;
  DELETE FROM tbbsnm24;
  DELETE FROM tbbsnm32;
  DELETE FROM tbbsnm42;
  DELETE FROM tbbsnm40;
  COMMIT;
END//
DELIMITER ;


/*BUSCAR PLANTILLAS EN ULTIMAS VERSIONES*/

//VER TODAS LAS PLANTILLAS
select iddocnot,dstitulo,inversion from tbbsnm08;
DELETE FROM tbbsnm08 where iddocnot = '8ea99b215724fc52485be50ae1c92739' AND inversion < 1; 



/*una no sirve este procedimiento*/
/*DELIMITER //
 
CREATE PROCEDURE `eliminaVersionPlantilla` (IN idPlantilla VARCHAR(32))
BEGIN
    DECLARE maximoTable INT;
    
    SET maximoTable = (select MAX(inversion) from tbbsnm08 where iddocnot = idPlantilla);
	DELETE FROM tbbsnm08 where iddocnot = idPlantilla AND inversion <  @maximoTable; 
END //

DELIMITER ;

CALL eliminaVersionPlantilla('cc6def24a53d4dad914356aedee6f0f0');
*/
