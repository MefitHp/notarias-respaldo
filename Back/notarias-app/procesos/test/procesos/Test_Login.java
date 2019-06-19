package procesos;

import java.util.List;

import org.bonitasoft.engine.bpm.data.DataInstance;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance;

import pojos.Escritura;

public class Test_Login  {


	public static void main(String[] args) throws Exception {
		String username = "golguin";
		String password = "golguin";
		
		BonitaUtilidades bu = new BonitaUtilidades(username, password);
		List<HumanTaskInstance> pedientes = bu.bonitaGetPendingTask();
		List<HumanTaskInstance> asignados = bu.bonitaGetAssignedTask();
		
		//HumanTaskInstance ht = pedientes.get(0);
		//bu.bonitaAssignTask(ht.getId());
		//bu.bonitaExcecuteTask(ht.getId());
		System.out.println("***************************************************************");
		System.out.println("******************** PENDIENTES *******************************");
		System.out.println("***************************************************************");
		bu.testbonitaNewProcess();
		
		for (HumanTaskInstance hti: pedientes){
			System.out.println("PENDIENTES TAREA::::"+hti.getDisplayName());
		}
		
		System.out.println("***************************************************************");
		System.out.println("******************** ASIGNADOS ********************************");
		System.out.println("****************************************************************");
		for (HumanTaskInstance hti: asignados){
			System.out.println("ASIGNADA TAREA::::"+hti.getDisplayName());
			DataInstance di = bu.bonitaGetDato(hti.getParentProcessInstanceId(),"escrituraDato");
			if(di!=null){
				Escritura es = (Escritura) di.getValue();
				System.out.println(es);

			}
		}
		System.out.println("***************************************************************");
		System.out.println("***************************************************************");
		
		
	}

}
