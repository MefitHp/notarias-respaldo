package BoTest;

import com.palestra.notaria.bo.BitacoraDocumentoBo;
import com.palestra.notaria.bo.BitacoraUsuarioBo;
import com.palestra.notaria.bo.impl.BitacoraUsuarioBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraUsuario;
import com.palestra.notaria.util.GeneradorId;

public class BitacoraUsuarioTest {

	public BitacoraUsuarioTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws NotariaException {
		BitacoraUsuarioBo bitusuBo = new BitacoraUsuarioBoImpl();
		BitacoraUsuario bitusu = new BitacoraUsuario();
		bitusu.setIdbitusu(GeneradorId.generaId(bitusu));
		bitusu.setIdacto("1234556");
		bitusu.setIdgrupotrabajo("1234556");
		bitusu.setIdobjeto("1234556");
		bitusu.setStatus("1234556");
		bitusu.setTexto("1234556");
		bitusu.setTipo("1234556");		
		bitusuBo.save(bitusu);
	}
	

}
