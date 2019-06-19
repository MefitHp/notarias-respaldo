package utiles.procesos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance;

public class TestIntegracionBonita {

	public static void main(String[] args) {
		
		try {
			
			BonitaUtilidades bu = new BonitaUtilidades("golguin", "golguin");
			HumanTaskInstance ht = bu.bonitaLastHumanTask(2001L,"prueba2");
			System.out.println(ht.getDisplayName());
			bu.bonitaExcecuteTask(ht.getActorId());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(1);
		
		
	}
	
	
}
