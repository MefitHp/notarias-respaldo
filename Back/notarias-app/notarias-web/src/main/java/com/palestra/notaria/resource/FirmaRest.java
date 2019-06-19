package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.FirmaBo;
import com.palestra.notaria.bo.FormatoPDFBO;
import com.palestra.notaria.bo.GrupoBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.FirmaBoImp;
import com.palestra.notaria.bo.impl.FormatoPDFBOImpl;
import com.palestra.notaria.bo.impl.GrupoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.envio.FirmaEnvio;
import com.palestra.notaria.envio.GrupoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Firma;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/firma")
public class FirmaRest {
	
	static Logger logger = Logger.getLogger(Firma.class);
	FirmaBo firmaBo;	
	
	public FirmaBo getFirmaBo() {
		return firmaBo;
	}

	public void setFirmaBo(FirmaBo firmaBo) {
		this.firmaBo = firmaBo;
	}

	public FirmaRest(){
		firmaBo = new FirmaBoImp();
	}
	
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public FirmaEnvio tachaFirma(FirmaEnvio fe) {
		FirmaEnvio respuesta = new FirmaEnvio();
		if (fe == null || fe.getUsuario() == null
				|| fe.getUsuario().getIdusuario() == null || fe.getUsuario().getIdusuario().isEmpty()
				|| fe.getUsuario().getIdsesionactual() == null || fe.getUsuario().getIdsesionactual().isEmpty()
				|| fe.getCompareciente() == null || fe.getCompareciente().getActo() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(),fe.getUsuario().getIdusuario())){
			
			Compareciente compareciente = fe.getCompareciente();
			EscrituraActoBo escActBo = new EscrituraActoBoImpl();			
			
			try {
				
				String idescritura= escActBo.obtenIdEscrituraPorActoId(compareciente.getActo().getIdacto());
				
				if(idescritura==null){
					respuesta.setEstatus("No se puede guardar la firma, por que aún no existe una Escritura asociada a este Acto");
					respuesta.setExito(false);
					return respuesta;
				}
				if(fe.getSolicitaPreventivo()){
						EscrituraBo escbo = new EscrituraBoImpl();
						Escritura esc = new Escritura();
						
						esc.setIdescritura(idescritura);
						esc = escbo.buscarPorIdCompleto(esc);
						if(esc.getDsnumescritura()==null){
							respuesta.setEstatus("No se puede solicitar el documento preventivo, por que la Escritura aún no tiene número");
							respuesta.setExito(false);
							return respuesta;
						}
						
						ActoDocumentoBo actoDocBo = new ActoDocumentoBoImpl();
						ArrayList<DatoActoDocumento> actosdocs= actoDocBo.obtenerDocXnombre("Preventivo", fe.getExpediente().getIdexpediente(), compareciente.getActo().getIdacto());
						
						/*
						 *20170523 Victor: este metodo con la actualizacion de bonita se fue.
						 if(actosdocs.size()>0){
							actoDocBo.saveDocProcess(esc, actosdocs.get(0), fe.getUsuario());
						}
						*/
				}
				
				Firma firma = new Firma();
				firma.setCompareciente(compareciente);
				firma.setIdsesion(fe.getUsuario().getIdsesionactual());
				firma.setIdfirma(GeneradorId.generaId(firma));
				FirmaBo fbo = new FirmaBoImp();
				fbo.save(firma);
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/borrar")
	@Produces({ "application/json", "application/xml" })
	public FirmaEnvio borraFirma(FirmaEnvio fe) {
		FirmaEnvio respuesta = new FirmaEnvio();
		if (fe == null || fe.getUsuario() == null
				|| fe.getUsuario().getIdusuario() == null || fe.getUsuario().getIdusuario().isEmpty()
				|| fe.getUsuario().getIdsesionactual() == null || fe.getUsuario().getIdsesionactual().isEmpty()
				|| fe.getFirma()==null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(),fe.getUsuario().getIdusuario())){
			
			try {
				FirmaBo fbo = new FirmaBoImp();
				fbo.borrar(fe.getFirma());
				
			} catch (Exception e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	
	@POST
	@Path("/checkfirmas")
	@Produces({ "application/json", "application/xml" })
	public FirmaEnvio checkfirmas(FirmaEnvio fe) {
		FirmaEnvio respuesta = new FirmaEnvio();
		if (fe == null || fe.getUsuario() == null
				|| fe.getUsuario().getIdusuario() == null || fe.getUsuario().getIdusuario().isEmpty()
				|| fe.getUsuario().getIdsesionactual() == null || fe.getUsuario().getIdsesionactual().isEmpty()
				|| fe.getEscritura() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(),fe.getUsuario().getIdusuario())){
			
			try {
				
				Boolean completas = firmaBo.checkcompletas(fe.getEscritura().getIdescritura());
				
				
				respuesta.setCompletas(completas);
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
				
			} catch (Exception e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	
	
	
	
}
