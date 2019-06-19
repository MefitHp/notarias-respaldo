package BoTest;

import com.palestra.notaria.bo.FirmaBo;
import com.palestra.notaria.bo.impl.FirmaBoImp;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Firma;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;

public class FirmaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FirmaBo fbo = new FirmaBoImp();
		
		Firma fir = new Firma();
		Compareciente com = new Compareciente();
		com.setIdcompareciente("732d487e284f070951471776568c875d");
		Escritura esc = new Escritura();
		esc.setIdescritura("1bba2df973c31e0adbb0101e1dd41661");
		
		fir.setIdsesion("22BCA57BA529890AA76D120B6E931601");
		fir.setCompareciente(com);
		fir.setIdfirma(GeneradorId.generaId(fir));
		try {
			fbo.save(fir);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
