package utiles.procesos;

import java.util.List;

import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.bpm.flownode.EventInstance;
import org.bonitasoft.engine.bpm.flownode.FlowNodeInstance;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance;
import org.bonitasoft.engine.bpm.process.ArchivedProcessInstance;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.NotFoundException;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.platform.LoginException;

public class TestBonita {

	public TestBonita() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String args[]) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, LoginException, ProcessActivationException, ProcessExecutionException, SearchException, NotFoundException{
		
		BonitaUtilidades bonitautils = new BonitaUtilidades("golguin", "golguin");
		Long idprocess = 48004L;
		//bonitautils.testbonitaNewProcess();
		List<HumanTaskInstance> tareas =  bonitautils.bonitaGetPendingTask();
		List<HumanTaskInstance> tareasasignadas =  bonitautils.bonitaGetAssignedTask();
		List<HumanTaskInstance> tareasXroot =  bonitautils.bonitaGetTaskByRootProcess(6486543626599994096L);
		List<HumanTaskInstance> tareasProcess =  bonitautils.getHumanTaskInstanceByProcess(idprocess,"Mesa de control: DIM");
		List<ActivityInstance> activies = bonitautils.bonitaActivies(idprocess);
		//HumanTaskInstance human = bonitautils.getTaskHumanName(48002L);
		//ArchivedProcessInstance historyProcess = bonitautils.bonitaGetHistoryActivity(idprocess);
/*
		System.out.println("*******************************************");
		System.out.println("**************  HISTORY   *****************");
		System.out.println("*******************************************");

		System.out.println("************** STATE: "+historyProcess.getState()+ "   *****************");
		System.out.println("************** NAME: "+historyProcess.getName()+ "   *****************");
		System.out.println("************** description: "+historyProcess.getDescription()+ "   *****************");
		System.out.println("*******************************************");
		*/
		
		System.out.println("*******************************************");
		System.out.println("**************  PENDING   *****************");
		System.out.println("*******************************************");
		for(HumanTaskInstance in : tareas){
			System.out.println("'''''''''''''''''''''''''''''''''''''''");
			System.out.println("DEFID TASK:"+in.getFlownodeDefinitionId());
			System.out.println("ID TASK:"+in.getId());
			System.out.println("NAME:"+in.getName());
			System.out.println("ROOT:"+in.getRootContainerId());
			System.out.println("PARENT CONTAINER:"+in.getParentContainerId());
			System.out.println("PARENT PROCESS INSTANCE:"+in.getParentProcessInstanceId());
			
			System.out.println("'''''''''''''''''''''''''''''''''''''''");
		}
		System.out.println("*******************************************");
		System.out.println("*******************************************");
		System.out.println("**************  ASIGNADAS   *****************");
		System.out.println("*******************************************");
		for(HumanTaskInstance in : tareasasignadas){
			System.out.println("'''''''''''''''''''''''''''''''''''''''");
			System.out.println("DEFID TASK:"+in.getFlownodeDefinitionId());
			System.out.println("ID TASK:"+in.getId());
			System.out.println("NAME:"+in.getName());
			System.out.println("ROOT:"+in.getRootContainerId());
			System.out.println("PARENT CONTAINER:"+in.getParentContainerId());
			System.out.println("PARENT PROCESS INSTANCE:"+in.getParentProcessInstanceId());
		}
		
		System.out.println("*******************************************");
		
		System.out.println("*******************************************");
		System.out.println("*******************************************");
		System.out.println("**************  POR ROOT CONTAINER   *****************");
		System.out.println("*******************************************");
		/*for(HumanTaskInstance in : tareasXroot){
			System.out.println("'''''''''''''''''''''''''''''''''''''''");
			System.out.println("DEFID TASK:"+in.getFlownodeDefinitionId());
			System.out.println("ID TASK:"+in.getId());
			System.out.println("NAME:"+in.getName());
			System.out.println("ROOT:"+in.getRootContainerId());
			System.out.println("PARENT CONTAINER:"+in.getParentContainerId());
			System.out.println("PARENT PROCESS INSTANCE:"+in.getParentProcessInstanceId());
		}*/
		
		System.out.println("*******************************************");

		System.out.println("*******************************************");
		System.out.println("*******************************************");
		System.out.println("**************  activies INSTANCES   *****************");
		System.out.println("*******************************************");
		for(ActivityInstance in : activies){
			System.out.println("'''''''''''''''''''''''''''''''''''''''");
			System.out.println("ID TASK:"+in.getId());
			System.out.println("NAME:"+in.getName());
			System.out.println("ROOT:"+in.getRootContainerId());
			System.out.println("PARENT CONTAINER:"+in.getParentContainerId());
			System.out.println("PARENT PROCESS INSTANCE:"+in.getParentProcessInstanceId());
		}
		
		System.out.println("*******************************************");

		System.out.println("*******************************************");

		System.out.println("*******************************************");
		System.out.println("*******************************************");
		System.out.println("**************  HUMAN PROCESS   *****************");
		System.out.println("*******************************************");
		for(ActivityInstance in : tareasProcess){
			System.out.println("'''''''''''''''''''''''''''''''''''''''");
			System.out.println("ID TASK:"+in.getId());
			System.out.println("NAME:"+in.getName());
			System.out.println("ROOT:"+in.getRootContainerId());
			System.out.println("PARENT CONTAINER:"+in.getParentContainerId());
			System.out.println("PARENT PROCESS INSTANCE:"+in.getParentProcessInstanceId());
		}
		
		System.out.println("*******************************************");
		
		
		
	}

}
