package test;

import com.palestra.notaria.uif.core.dao.PersonaDao;
import com.palestra.notaria.uif.core.dao.PersonaDaoImp;
import com.palestra.notaria.uif.core.models.Persona;
import com.palestra.notaria.uif.exceptions.NotariaException;

public class TestDao {
	
	private static final String PersonaDao = null;

	public static void main(String[] args) {
		Persona persona = new Persona("juan","peres","lopez");
		persona.setIdpersona("ok ok");
		
		PersonaDao personadao = new PersonaDaoImp();
		
		try {
			personadao.save(persona);
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
