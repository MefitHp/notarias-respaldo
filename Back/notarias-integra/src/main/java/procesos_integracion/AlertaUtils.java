package procesos_integracion;


import com.fasterxml.jackson.databind.JsonNode;
import com.palestra.notaria.envio.BitacoraUsuarioEnvio;
import com.palestra.notaria.modelo.BitacoraUsuario;
import com.palestra.notaria.modelo.Usuario;
import com.victor.utils.restconexion.PostGet;

import pojos.pojos.ProcesoComun;


public class AlertaUtils {

	
	public AlertaUtils() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void save(ProcesoComun procomun,String statusalert, String texto){
		
		procomun.setIdusuario("fc2d98aadbfbca1d304e1d56ae894fbb");
		procomun.setReferencia("1234");
		BitacoraUsuario bitacora = new BitacoraUsuario();
		bitacora.setIdobjeto("123456");
		bitacora.setTipo("nada");

		bitacora.setIdbitusu(procomun.getIdusuario());
		bitacora.setActive(true);
		bitacora.setIdexpediente(procomun.getIdexpediente());
		bitacora.setNumexp(procomun.getExpediente());
		bitacora.setStatus(statusalert);
		bitacora.setTexto(texto);
		bitacora.setActive(true);
		
		Usuario usu = new Usuario();
		usu.setIdusuario(procomun.getIdusuario());
		usu.setIdsesionactual("12345");
		
		BitacoraUsuarioEnvio biusuen = new BitacoraUsuarioEnvio();
		
		biusuen.setUsuario(usu);
		biusuen.setBitacorausuario(bitacora);
		
		PostGet pg = new PostGet("http://localhost:8080/notarias-web/notaria/bitacorausuario/save");
		JsonNode respuesta = pg.getPost(biusuen);
		biusuen = (BitacoraUsuarioEnvio) PostGet.setMapperJsonToPojo(respuesta,BitacoraUsuarioEnvio.class);
		
		System.out.println(biusuen);
		
		/*procomun.setEscritura("1234");
		procomun.setExpediente("1234");
		procomun.setIdexpediente("1234");
		
		procomun.setIdusuario("fc2d98aadbfbca1d304e1d56ae894fbb");
		procomun.setReferencia("1234");
		BitacoraUsuario bitacora = new BitacoraUsuario();
		//BitacoraUsuarioBo bitbo = new BitacoraUsuarioBoImpl();
		bitacora.setIdbitusu(procomun.getIdusuario());
		bitacora.setActive(true);
		bitacora.setIdobjeto("------");
		bitacora.setTipo("-------");
		bitacora.setIdexpediente(procomun.getIdexpediente());
		bitacora.setNumexp(procomun.getExpediente());
		bitacora.setStatus(statusalert);
		bitacora.setTexto(texto);
		bitacora.setActive(true);
		
		Usuario usu = new Usuario();
		usu.setIdusuario(procomun.getIdusuario());
		usu.setIdsesionactual("------------");
		BitacoraUsuarioEnvio biusuen = new BitacoraUsuarioEnvio();
		
		biusuen.setUsuario(usu);
		biusuen.setBitacorausuario(bitacora);
		PostGet pg = new PostGet("http://localhost:8080/notarias-web/notaria/bitacorausuario/save");
		JsonNode respuesta = pg.getPost(biusuen);
		biusuen = (BitacoraUsuarioEnvio) PostGet.setMapperJsonToPojo(respuesta,BitacoraUsuarioEnvio.class);
		System.out.println(biusuen);*/
	}
	
		public void save(String statusalert, String texto){
		
		ProcesoComun procomun = new ProcesoComun();
		procomun.setEscritura("1234");
		procomun.setExpediente("1234");
		procomun.setIdexpediente("1234");
		
		procomun.setIdusuario("fc2d98aadbfbca1d304e1d56ae894fbb");
		procomun.setReferencia("1234");
		BitacoraUsuario bitacora = new BitacoraUsuario();
		bitacora.setIdobjeto("123456");
		bitacora.setTipo("nada");

		bitacora.setIdbitusu(procomun.getIdusuario());
		bitacora.setActive(true);
		bitacora.setIdexpediente(procomun.getIdexpediente());
		bitacora.setNumexp(procomun.getExpediente());
		bitacora.setStatus(statusalert);
		bitacora.setTexto(texto);
		bitacora.setActive(true);
		
		Usuario usu = new Usuario();
		usu.setIdusuario(procomun.getIdusuario());
		usu.setIdsesionactual("12345");
		
		BitacoraUsuarioEnvio biusuen = new BitacoraUsuarioEnvio();
		
		biusuen.setUsuario(usu);
		biusuen.setBitacorausuario(bitacora);
		
		PostGet pg = new PostGet("http://localhost:8080/notarias-web/notaria/bitacorausuario/save");
		JsonNode respuesta = pg.getPost(biusuen);
		biusuen = (BitacoraUsuarioEnvio) PostGet.setMapperJsonToPojo(respuesta,BitacoraUsuarioEnvio.class);
		
		System.out.println(biusuen);
		
	}
		
		public void save(String idusuario,String idexpediente,String expediente, String statusalert, String texto,Long idtarea,String tipoalerta){
			
			//revisar que no vengan en null los datos
			BitacoraUsuario bitacora = new BitacoraUsuario();
			bitacora.setIdobjeto("--------");
			bitacora.setTipo(tipoalerta);
			if(idtarea!=null){
				bitacora.setIdtarea(idtarea.toString());
			}else{
				bitacora.setIdtarea(null);
			}
			bitacora.setIdbitusu(idusuario);
			bitacora.setActive(true);
			bitacora.setIdexpediente(idexpediente);
			bitacora.setNumexp(expediente);
			bitacora.setStatus(statusalert);
			bitacora.setTexto(texto);
			bitacora.setActive(true);
			
			Usuario usu = new Usuario();
			usu.setIdusuario(idusuario);
			usu.setIdsesionactual("-----------");
			
			BitacoraUsuarioEnvio biusuen = new BitacoraUsuarioEnvio();
			
			biusuen.setUsuario(usu);
			biusuen.setBitacorausuario(bitacora);
			
			PostGet pg = new PostGet("http://localhost:9090/notarias-web/notaria/bitacorausuario/save");
			JsonNode respuesta = pg.getPost(biusuen);
			biusuen = (BitacoraUsuarioEnvio) PostGet.setMapperJsonToPojo(respuesta,BitacoraUsuarioEnvio.class);
			
			System.out.println(biusuen);
			
		}
		
		
		
		public static void main(String[] args) {
			AlertaUtils au = new AlertaUtils();
			ProcesoComun procomun = new ProcesoComun();
			procomun.setEscritura("1234");
			procomun.setExpediente("1234");
			procomun.setIdexpediente("1234");
			procomun.setIdusuario("fc2d98aadbfbca1d304e1d56ae894fbb");
			procomun.setReferencia("1234");
			
			
			au.save(procomun,"prueba", "prueba");
		}
	

}
