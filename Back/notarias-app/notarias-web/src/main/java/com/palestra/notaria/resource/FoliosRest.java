package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.palestra.notaria.bo.BitacoraFoliosBo;
import com.palestra.notaria.bo.ControlFoliosBo;
import com.palestra.notaria.bo.DevolucionRechazadaFolioBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.EscrituraExternaBo;
import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.bo.SolicitudPrestamoFoliosBo;
import com.palestra.notaria.bo.impl.BitacoraFoliosBoImpl;
import com.palestra.notaria.bo.impl.ControlFoliosBoImpl;
import com.palestra.notaria.bo.impl.DevolucionRechazadaFolioBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.EscrituraExternaBoImpl;
import com.palestra.notaria.bo.impl.LibroBoImpl;
import com.palestra.notaria.bo.impl.SolicitudPrestamoFoliosBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.FolioEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraExterna;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/folios")
public class FoliosRest {

	@POST
	@Path("/listarSolicitudesNoAtendidas")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio listarSolicitudes(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				SolicitudPrestamoFoliosBo foliosBo = new SolicitudPrestamoFoliosBoImpl();
				respuesta.setSolicitudesNoAtendidas(foliosBo.listarSolicitudesNoAtendidas());
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}			
	}
	@POST
	@Path("/listarPrestados")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio listarPrestados(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				SolicitudPrestamoFoliosBo foliosBo = new SolicitudPrestamoFoliosBoImpl();
				respuesta.setFoliosPrestadosList(foliosBo.listarPrestados());
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/listarDisponibles")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio listarDisponibles(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				ControlFoliosBo controlFoliosBo = new ControlFoliosBoImpl();
				ControlFolios controlFolio = controlFoliosBo.getUltimo();
				LibroBo libroBo = new LibroBoImpl();
				Libro libro = libroBo.obtenUltimoLibro();
				respuesta.setLibro(libro);
				respuesta.setControlFolios(controlFolio);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/eliminarSolicitud")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio eliminarSolicitud(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				SolicitudPrestamoFoliosBo solpresBo = new SolicitudPrestamoFoliosBoImpl();
				solpresBo.borrar(fe.getSolicitudPrestamo());
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/buscarPrestadosPorAbogado")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio buscarPorAbogado(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				SolicitudPrestamoFoliosBo prestamoBo = new SolicitudPrestamoFoliosBoImpl();
				List<SolicitudPrestamoFolios> prestados = prestamoBo.buscarPrestamosPorAbogado(fe.getAbogado());
				respuesta.setPrestadosAbogado(prestados);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/buscarSolicitudesPorAbogado")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio buscarSolicitudesPorAbogado(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				SolicitudPrestamoFoliosBo prestamoBo = new SolicitudPrestamoFoliosBoImpl();
				List<SolicitudPrestamoFolios> solicitudes = prestamoBo.buscarSolicitudesPorAbogado(fe.getAbogado().getIdusuario());
				respuesta.setSolicitadosAbogado(solicitudes);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	
	@POST
	@Path("/listarDevueltos")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio listarDevueltos(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				BitacoraFoliosBo devolucionBo = new BitacoraFoliosBoImpl();
				respuesta.setFoliosDevueltos(devolucionBo.listarDevueltos());
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/hisorial_x_numero_escritura")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio hisorial_x_numero_escritura(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				BitacoraFoliosBo devolucionBo = new BitacoraFoliosBoImpl();
				respuesta.setFoliosDevueltos(devolucionBo.listar_x_numeroescritura(fe.getSolicitudPrestamo().getNumeroEscritura()));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	@POST
	@Path("/historial_x_usuario")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio historial_x_usuario(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				BitacoraFoliosBo devolucionBo = new BitacoraFoliosBoImpl();
				respuesta.setFoliosDevueltos(devolucionBo.listar_x_usuario(fe.getAbogado()));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/historial_x_fecha")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio historial_x_fecha(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				BitacoraFoliosBo devolucionBo = new BitacoraFoliosBoImpl();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date DateInicio = sdf.parse(fe.getFechaInicio());
				Date DateFin = sdf.parse(fe.getFechaFin());
				Timestamp tmpInicio = new Timestamp(DateInicio.getTime());
				Timestamp tmpFin = new Timestamp(DateFin.getTime());
				
				respuesta.setFoliosDevueltos(devolucionBo.listar_x_fechas(tmpInicio,tmpFin));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		return respuesta;
	}
	
	@POST
	@Path("/bitacoraDocumentos")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio bitacoraDocumentos(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				BitacoraFoliosBo bitFolBo = new BitacoraFoliosBoImpl();
				respuesta.setFoliosDevueltos(bitFolBo.listarDevueltos());
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	@POST
	@Path("/listarDevolucionesRechazadas")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio listarDevolucionesRechazadas(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				DevolucionRechazadaFolioBo rechazadaBo = new DevolucionRechazadaFolioBoImpl();
				respuesta.setFoliosRechazadosList(rechazadaBo.listarRechazados());
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/agregarFoliosDisponibles")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio actualizarFoliosDisponibles(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				
				if(fe.getControlFolios().getFoliosDisponibles()<=0){
					respuesta.setEstatus(fe.getControlFolios().getFoliosDisponibles() + " no es un número positivo");
					respuesta.setExito(false);
					return respuesta;
				}
				
				ControlFoliosBo controlFoliosBo = new ControlFoliosBoImpl();
				ControlFolios controlFolioBD = controlFoliosBo.getUltimo();
				
				if(controlFolioBD ==null){
					controlFolioBD = new ControlFolios();
					controlFolioBD.setFoliosDisponibles(0L);
					controlFolioBD.setFolioActual(0L);
					
				}
//				se guarda la suma de los folios existentes + los folios solicitados a guardar
				controlFolioBD.setFoliosDisponibles(controlFolioBD.getFoliosDisponibles()+fe.getControlFolios().getFoliosDisponibles());
				controlFolioBD.setActualizacion(new Date());
				controlFolioBD.setIdcontrolfolios(GeneradorId.generaId(controlFolioBD));
				controlFolioBD.setIdsesion(fe.getUsuario().getIdsesionactual());
				controlFolioBD.setTmstmp(null);
				controlFoliosBo.save(controlFolioBD);
				respuesta.setControlFolios(controlFoliosBo.update(controlFolioBD));
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardarSolicitudPrestamo")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio guardarSolicitudPrestamo(FolioEnvio fe){
		SolicitudPrestamoFolios spf = fe.getSolicitudPrestamo();
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			if (fe == null || 
				fe.getUsuario() == null ||
				spf ==null ||
				spf.getUsuarioRecibe() == null ||
				(spf.getNumeroEscritura() == null && 
				(spf.getInfolioinicio() == null || spf.getInfoliofin() ==null))
				){
				
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
			try{
				if(spf.getNumeroEscritura()!=null){
					String escrituraSolicitada = spf.getNumeroEscritura();
					EscrituraBo escrituraBo = new EscrituraBoImpl();
					Escritura escritura = escrituraBo.getByNumeroEscritura(escrituraSolicitada);;
					spf.setInfolioinicio(escritura.getFolioini());
					spf.setInfoliofin(escritura.getFoliofin());
					spf.setNumeroEscritura(escritura.getDsnumescritura());
				}else{
					if(spf.getInfolioinicio()>
					spf.getInfoliofin()){
						respuesta.setEstatus("El folio inicio debe ser menor al folio final solicitado");
						respuesta.setExito(false);
						return respuesta;
					}
				}
				spf.setIdsolicitudprestamo(GeneradorId.generaId(spf));
				spf.setIdsesion(fe.getUsuario().getIdsesionactual());
				spf.setEstanPrestados(false);
				spf.setIsPrestamoTerminado(Boolean.FALSE);
//				fe.getSolicitudPrestamo().setUsuarioRecibe(fe.getUsuario());
				SolicitudPrestamoFoliosBo prestamoBo = new SolicitudPrestamoFoliosBoImpl();
				
				respuesta.setSolicitudPrestamo(prestamoBo.save(spf));
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/autorizarPrestamo")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio autorizarPrestamo(FolioEnvio fe) throws ParseException{
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
				respuesta = validaAutorizacion(fe);
				if(!respuesta.isExito()){
					return respuesta;
				}
//				para autorizar un prestamo ya viene con id de solicitud para solo actualizar el estatus y poner quien prestó 
				fe.getSolicitudPrestamo().setUsuarioPrestador(fe.getUsuario());
				fe.getSolicitudPrestamo().setIdsesion(fe.getUsuario().getIdsesionactual());
				fe.getSolicitudPrestamo().setEstanPrestados(true);
				fe.getSolicitudPrestamo().setIsPrestamoTerminado(Boolean.FALSE);
				String fecha = "";
				Date hoy = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				fecha = sdf.format(hoy);
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(fecha));
				c.add(Calendar.DATE, 2);
				int dayOfWeekDevolucion = c.get(Calendar.DAY_OF_WEEK);
//				se valida que si la entrega cae en Sab o Dom se agregan 2 días
				if(dayOfWeekDevolucion == 1 || dayOfWeekDevolucion == 7){
					c.add(Calendar.DATE, 2);
				}
				fe.getSolicitudPrestamo().setFechaEntrega(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				fe.getSolicitudPrestamo().setFechaDevolucion(new java.sql.Date(c.getTimeInMillis()));

				SolicitudPrestamoFoliosBo prestamoBo = new SolicitudPrestamoFoliosBoImpl();
				//prestamoBo.update(fe.getSolicitudPrestamo());
				prestamoBo.aprobarSolicitud(fe.getSolicitudPrestamo());
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	public FolioEnvio validaAutorizacion(FolioEnvio fe){
		FolioEnvio respuesta = new FolioEnvio();
		SolicitudPrestamoFolios solicitud = fe.getSolicitudPrestamo();
		SolicitudPrestamoFoliosBo prestamoBo = new SolicitudPrestamoFoliosBoImpl();
		try {
			if(solicitud.getIdsolicitudprestamo()==null){
				fe = guardarSolicitudPrestamo(fe);
				if(!fe.isExito()){
					throw new NotariaException(fe.getEstatus());
				}
				solicitud = fe.getSolicitudPrestamo();
			}else{
				solicitud = prestamoBo.findById(solicitud.getIdsolicitudprestamo());	
			}
			if(solicitud.getEstanPrestados()){
				respuesta.setExito(false);
				respuesta.setEstatus("Esta solicitud ya fue atendida");
				return respuesta;
			}else{
				respuesta.setEstatus("ok");
				respuesta.setExito(true);
				return respuesta;
			}
			
		} catch (NotariaException e) {
			e.printStackTrace();
			respuesta.setEstatus("error: "+e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	public FolioEnvio procesaDevolucion(FolioEnvio fe,String tipo){
		FolioEnvio respuesta=new FolioEnvio();
		try{
	//		se obtienen los registros de prestamo asociados al abogado que va a devolver
			SolicitudPrestamoFoliosBo prestamoBo = new SolicitudPrestamoFoliosBoImpl();
			BitacoraFolios foliosaDevolver = null;//fe.getDevolucionFolios();
			List<SolicitudPrestamoFolios> prestamosAbogado = null;// prestamoBo.listarPrestadosPorAbogado(
					//fe.getDevolucionFolios().getUsuarioEntrega().getIdusuario());
			if(prestamosAbogado.size()==0){
				respuesta.setEstatus("No existen préstamos de folios al abogado indicado" +
						" para poder proceder a una devolución");
				respuesta.setExito(false);
				return respuesta;
			}
			for(SolicitudPrestamoFolios solPrest:prestamosAbogado){
	//			se valida que los folios que devuelve esten dentro de algun registro de préstamo
	//			si es así se guarda la devolucion de los folios solicitados
				
				//if(foliosaDevolver.getInfolioinicio() >= solPrest.getInfolioinicio()
				//		&& foliosaDevolver.getInfoliofin() <= solPrest.getInfoliofin()){
//					se indica que termino el prestamo y se actualiza el objeto de solicitud prestamo folios
					
				solPrest.setIsPrestamoTerminado(Boolean.TRUE);
					prestamoBo.update(solPrest);
					foliosaDevolver.setIddevolucionfolio(GeneradorId.generaId(foliosaDevolver));
					foliosaDevolver.setIdsesion(fe.getUsuario().getIdsesionactual());
					foliosaDevolver.setUsuarioRecibe(fe.getUsuario());
				//	foliosaDevolver.setFechaDevolucion(solPrest.getFechaDevolucion());
				//	foliosaDevolver.setFechaEntrega(solPrest.getFechaEntrega());
					if(tipo.equals("devolucion")){
				//		foliosaDevolver.setIspendiente(false);
					}else{
				//		foliosaDevolver.setIspendiente(true);
					}
				
					BitacoraFoliosBo devolucionBo = new BitacoraFoliosBoImpl();
					//devolucionBo.save(foliosaDevolver);
			//	}
				
			}
			if(tipo.equals("rechazo")){
				/*DevolucionRechazadaFolio devolucionRechazada = null;// fe.getDevolucionRechazada();
				DevolucionRechazadaFolioBo rechazadaBo = new DevolucionRechazadaFolioBoImpl();
				devolucionRechazada.setDevolucionFolio(foliosaDevolver);
				devolucionRechazada.setIdsesion(fe.getUsuario().getIdsesionactual());
				devolucionRechazada.setIsresuelta(false);
				devolucionRechazada.setUsuarioRechaza(fe.getUsuario());
				rechazadaBo.save(devolucionRechazada);*/
			}
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		}catch(NotariaException e){
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/devolucion")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio devolucion(FolioEnvio fe){
		
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			if(fe.getSolicitudPrestamo()!=null){
				SolicitudPrestamoFolios spf = fe.getSolicitudPrestamo();
				SolicitudPrestamoFoliosBo spfBo = new SolicitudPrestamoFoliosBoImpl();
				try {
					spfBo.aprobarDevolucion(spf,fe.getComentario());
				} catch (NotariaException e) {
					respuesta.setEstatus("No se logró guardar la devolucion contacte a sistemas:"+e.getMessage());	
					e.printStackTrace();
					respuesta.setExito(false);
					return respuesta;
				}
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			}else{
				respuesta.setEstatus("Hace falta un objeto de devolución de folios");
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/rechazarDevolucion")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio rechazarDevolucion(FolioEnvio fe){
		FolioEnvio respuesta=new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			if(fe.getDevolucionRechazada()!=null){
				return procesaDevolucion(fe, "rechazo");
			}else{
				respuesta.setEstatus("Hace falta mandar un objeto de rechazo de devolución");
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardarEscrituraExterna")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio guardarEscrituraExterna(FolioEnvio fe){
		FolioEnvio respuesta = new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			
			if (fe == null || 
				fe.getUsuario() == null ||
				fe.getEscrituraExt()==null ||
				fe.getEscrituraExt().getDsnumescritura() ==null||
				fe.getEscrituraExt().getFolioini()==null || 
				fe.getEscrituraExt().getFoliofin()==null
				){
					
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
					respuesta.setExito(false);
					return respuesta;
				}
			
			
			try{
				EscrituraExterna escritura = fe.getEscrituraExt();
				escritura.setFechacreacion(new Date());
				escritura.setIdescrituraExterna(GeneradorId.generaId(escritura));
				escritura.setIdsesion(fe.getUsuario().getIdsesionactual());
				escritura.setExpediente("");
				escritura.setNotario("");
				EscrituraExternaBo escrituraBo = new EscrituraExternaBoImpl();
//				la actualizacion de folio actual y folios disponibles se hace en este metodo
				
					escritura = escrituraBo.guardarEscrituraExterna(escritura);
					respuesta.setEscrituraExt(escritura);
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardarLibro")
	@Produces(MediaType.APPLICATION_JSON)
	public FolioEnvio guardarLibro(FolioEnvio fe){
		FolioEnvio respuesta = new FolioEnvio();
		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe.getUsuario().getIdusuario())) {
			try{
//				calcular folios del listado de escrituras que lleguen
				List<Escritura> escrituras = fe.getEscrituras();
				Long tmpFolioIni=0L;
				Long tmpFolioFin=0L;
				if(escrituras.size()>0){
					tmpFolioIni=escrituras.get(0).getFolioini();
					tmpFolioFin=escrituras.get(0).getFoliofin();
					if(escrituras.size()==1){
//					de aqui se sale la condicion
					}else{
						for(int i=0;i<escrituras.size();i++){
							if(i==escrituras.size()-1){
								if(tmpFolioIni>escrituras.get(i).getFolioini()){
									tmpFolioIni = escrituras.get(i).getFolioini();
								}
								if(tmpFolioFin<escrituras.get(i).getFoliofin()){
									tmpFolioFin = escrituras.get(i).getFoliofin();
								}
							}else{
								if(tmpFolioIni>escrituras.get(i+1).getFolioini()){
									tmpFolioIni = escrituras.get(i+1).getFolioini();
								}
								if(tmpFolioFin<escrituras.get(i+1).getFoliofin()){
									tmpFolioFin = escrituras.get(i+1).getFoliofin();
								}
							}
						}
					}
				}else if(fe.getFolioInicio()!=null && fe.getFolioFin()!=null){
					tmpFolioIni = fe.getFolioInicio();
					tmpFolioFin = fe.getFolioFin();
				}
				ControlFoliosBo controlFoliosBo = new ControlFoliosBoImpl();
				LibroBo libroBo = new LibroBoImpl();
				ControlFolios controlFolios = controlFoliosBo.findAll().get(0);
				Libro libro = fe.getLibro();
				libro.setIdlibro(GeneradorId.generaId(libro));
				libro.setIdsesion(fe.getUsuario().getIdsesionactual());
				//libro.setInfoliofinal(tmpFolioFin);
				libro.setInfolioinicial(tmpFolioIni);
				//libro.setInnumlibro(controlFolios.getLibroActual()+1);
				libro.setFecha(new Date());
				libroBo.save(libro);
				respuesta.setLibro(libro);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	
	
    private void verify(String activeReader) {
        System.out.printf("Performing fingerprint verification...\n");
        String username = "Enter the name of the person to be verified: ";

//        UserDatabase.User user = userDatabase.getUser(username);
//        TODO: get usuario por id
        Usuario usuario = new Usuario();
        if (usuario == null) {
            System.out.printf("\"%s\" was not found in the database.\n", username);
        } else {
//            if (usuario.getDshuelladigital() == null) {
        	if(usuario == null){
                System.out.printf("No fingers for \"%s\" have been enrolled.\n", username);
            } else {
                try {
                    DPFPSample sample = getSample(activeReader, "Scan your finger\n");
                    if (sample == null)
                        throw new Exception();

                    DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
                    DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

                    DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
                    matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
                    
                    for (DPFPFingerIndex finger : DPFPFingerIndex.values()) {
//                        DPFPTemplate template = usuario.getTemplate(finger);
//                        if (template != null) {
//                            DPFPVerificationResult result = matcher.verify(featureSet, template);
//                            if (result.isVerified()) {
////                                System.out.printf("Matching finger: %s, FAR achieved: %g.\n",
////                                		fingerName(finger), (double)result.getFalseAcceptRate()/DPFPVerification.PROBABILITY_ONE);
//                                return;
//                            }
//                        }
                    }
                } catch (Exception e) {
                    System.out.printf("Failed to perform verification.");
                }
                System.out.printf("No matching fingers for \"%s\" were found.\n", username);
            }
        }
    }
	
    private DPFPSample getSample(String activeReader, String prompt) throws InterruptedException{
            final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
            DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
            capture.setReaderSerialNumber(activeReader);
            capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
            capture.addDataListener(new DPFPDataListener()
            {
                public void dataAcquired(DPFPDataEvent e) {
                    if (e != null && e.getSample() != null) {
                        try {
                            samples.put(e.getSample());
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            capture.addReaderStatusListener(new DPFPReaderStatusAdapter()
            {
            	int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;
				public void readerConnected(DPFPReaderStatusEvent e) {
					if (lastStatus != e.getReaderStatus())
						System.out.println("Reader is connected");
					lastStatus = e.getReaderStatus();
				}
				public void readerDisconnected(DPFPReaderStatusEvent e) {
					if (lastStatus != e.getReaderStatus())
						System.out.println("Reader is disconnected");
					lastStatus = e.getReaderStatus();
				}
            	
            });
            try {
                capture.startCapture();
                System.out.print(prompt);
                return samples.take();
            } catch (RuntimeException e) {
                System.out.printf("Failed to start capture. Check that reader is not used by another application.\n");
                throw e;
            } finally {
                capture.stopCapture();
            }
        }
}
