package utiles.procesos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.bpm.actor.ActorCriterion;
import org.bonitasoft.engine.bpm.actor.ActorInstance;
import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.data.DataInstance;
import org.bonitasoft.engine.bpm.data.DataNotFoundException;
import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.bpm.flownode.ActivityInstanceCriterion;
import org.bonitasoft.engine.bpm.flownode.ActivityInstanceNotFoundException;
import org.bonitasoft.engine.bpm.flownode.EventCriterion;
import org.bonitasoft.engine.bpm.flownode.EventInstance;
import org.bonitasoft.engine.bpm.flownode.FlowElementContainerDefinition;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeInstance;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance;
import org.bonitasoft.engine.bpm.flownode.TransitionDefinition;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.bpm.process.ArchivedProcessInstance;
import org.bonitasoft.engine.bpm.process.ArchivedProcessInstanceNotFoundException;
import org.bonitasoft.engine.bpm.process.DesignProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.bpm.process.ProcessInstanceCriterion;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.DeletionException;
import org.bonitasoft.engine.exception.NotFoundException;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.identity.UserNotFoundException;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.search.SearchOptions;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.bonitasoft.engine.search.impl.SearchOptionsImpl;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.util.APITypeManager;

import pojos.pojos.Escritura;
import pojos.pojos.Impuesto;
import pojos.pojos.ProcesoComun;




public class BonitaUtilidades {
	
	
	
	private String username = "walter.bates";
	private String password = "bpm";
	static final String server = "http://localhost:8080";
	//static final String server = "http://localhost:9090";
	static final String app = "bonita";
	private APISession apiSession;
	public APISession getApiSession() {
		return apiSession;
	}


	public void setApiSession(APISession apiSession) {
		this.apiSession = apiSession;
	}

	private ProcessAPI processAPI;
	private IdentityAPI identityAPI;
	private String PROCESS = "General";
	private String PROCESSIMPUESTO = "Impuesto";
	private String VERSION = "1.0";	
	
	
	public BonitaUtilidades(String username,String password) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, LoginException{
		this.username = username;
		this.password = password;
		bonitaGetSession();
		
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	// OBTENGO EL PROCESO ACTIVO
	private ProcessAPI bonitaGetProcess() throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		if(processAPI==null){
			processAPI = TenantAPIAccessor.getProcessAPI(this.apiSession);	
		}
		return this.processAPI;
		 
	}
	
	private IdentityAPI bonitaGetIdentity() throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		if(identityAPI==null){
			identityAPI = TenantAPIAccessor.getIdentityAPI(this.apiSession);
		}
		return this.identityAPI;
		 
	}
	
	public DataInstance bonitaGetDato(Long idprocess,String nombredato) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		ProcessAPI api = bonitaGetProcess();
		DataInstance di = null;
		try {
			di= api.getProcessDataInstance(nombredato, idprocess);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			System.out.println("Dato: "+nombredato +", no encontrado");
		}
		return di;
	}
	
	public DataInstance bonitaGetDatoTarea(Long idtarea,String nombredato) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		ProcessAPI api = bonitaGetProcess();		
		DataInstance di = null;
		try {
			di=api.getActivityDataInstance(nombredato, idtarea);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			System.out.println("Dato: "+nombredato +", no encontrado");
		}
		return di;
	}
	
	public void bonitaUpdateDato(String namedato,Long process,Serializable value) throws UpdateException{
		processAPI.updateProcessDataInstance(namedato, process, value);
	}
	
		
	// ME FIRMO EN LA APLICACION
	private void bonitaGetSession() throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, LoginException{
		Map<String, String> settings = new HashMap<String, String>();
		settings.put("server.url", server);
		settings.put("application.name", app);
		APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, settings);
		// get the LoginAPI using the TenantAPIAccessor
		LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();
		this.apiSession = loginAPI.login(this.username,this.password);
	}
	
	// LIBRERIA DE UTILIDADES
	
	//OBTIENE LAS TAREAS PENDIENTES
	public List<HumanTaskInstance> bonitaGetPendingTask() throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		bonitaGetProcess();
		return processAPI.getPendingHumanTaskInstances(apiSession.getUserId(),0, 200, ActivityInstanceCriterion.EXPECTED_END_DATE_DESC);
	}
	
	// OBTIENE LAS TAREAS ASIGNADAS
	public List<HumanTaskInstance> bonitaGetAssignedTask() throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		bonitaGetProcess();
		return processAPI.getAssignedHumanTaskInstances(apiSession.getUserId(), 0, 200, ActivityInstanceCriterion.PRIORITY_ASC);
	}
	
	
	
	// OBTIENE LAS TAREAS ASIGNADAS
		public List<ActivityInstance>  bonitaActivies(Long processInstanceId) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, NotFoundException{
			bonitaGetProcess();
			return processAPI.getActivities(processInstanceId, 0, 100);
		}
		
		public HumanTaskInstance bonitaLastHumanTask(Long processInstanceId,String name) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, NotFoundException{
			bonitaGetProcess();
			return processAPI.getLastStateHumanTaskInstance(processInstanceId, name);
		}
	
		
		
		public ArchivedProcessInstance bonitaGetHistoryActivity(Long processInstanceID) throws ArchivedProcessInstanceNotFoundException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
			bonitaGetProcess();
			ArchivedProcessInstance respuesta = processAPI.getArchivedProcessInstance(processInstanceID);
			return respuesta;
		}
		
		
		public List<HumanTaskInstance> getHumanTaskInstanceByProcess(Long processDefinitionId,String name) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
			bonitaGetProcess();
			return processAPI.getHumanTaskInstances(processDefinitionId,name, 0, 100);
		}

		
	
	public List<HumanTaskInstance> bonitaGetTaskByRootProcess(Long rootProcessDefinitionId) throws SearchException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		bonitaGetProcess();
		final SearchOptions searchOptions = new SearchOptionsImpl(0, 100);
		SearchResult<HumanTaskInstance> result =  processAPI.searchAssignedAndPendingHumanTasks(rootProcessDefinitionId, searchOptions);
		return result.getResult();
	}
	
	
	public void bonitaGetEventIntanceByRootContainer() throws SearchException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		bonitaGetProcess();

		try {
			SearchResult<FlowNodeInstance> result = processAPI.searchFlowNodeInstances(new SearchOptionsBuilder(0, 100).done());
			System.out.println("Count: "+ result.getCount());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
			
		
	}
	

	
	
	public void bonitaNewProcess(Escritura esc) throws ProcessDefinitionNotFoundException, ProcessActivationException, ProcessExecutionException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		bonitaGetProcess();
		long pd = processAPI.getProcessDefinitionId(PROCESS, VERSION);
		Map<String, Serializable> vars = new HashMap<String, Serializable>();
		vars.put("escrituradato", esc);
		processAPI.startProcess(pd,vars);
	}
	
	
	public void bonitaNewProcess(String nameprocess,String version,Map<String, Serializable> map) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, ProcessDefinitionNotFoundException, ProcessActivationException, ProcessExecutionException{
		bonitaGetProcess();
		long pd = processAPI.getProcessDefinitionId(nameprocess, version);
		processAPI.startProcess(pd,map);
		
	}
	
	
	public void bonitaNewImpuesto(ProcesoComun procesocomun, Impuesto imp) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, ProcessDefinitionNotFoundException, ProcessActivationException, ProcessExecutionException{
		bonitaGetProcess();
		long pd = processAPI.getProcessDefinitionId(PROCESSIMPUESTO, VERSION);
		Map<String, Serializable> vars = new HashMap<String, Serializable>();
		vars.put("escrituradato", procesocomun);
		vars.put("impuesto", imp);
		processAPI.startProcess(pd,vars);
		
	}
	
	public void testbonitaNewProcess() throws ProcessDefinitionNotFoundException, ProcessActivationException, ProcessExecutionException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		ArrayList<Impuesto> impuestos = new ArrayList<Impuesto>();
		Impuesto imp1 = new Impuesto();
		Impuesto imp2 = new Impuesto();
		Escritura esc = new Escritura();
		esc.setEscritura("98765");
		esc.setIdescritura("98765");
		esc.setExpediente("4321");
		esc.setIdexpediente("ab79a097867946fb55e08549fccf74bd");
		esc.setReferencia("Oso polar");
		esc.setIdusuario("fc2d98aadbfbca1d304e1d56ae894fbb");
		imp1.setActonombre("Compraventa");
		imp1.setIdacto("7de62a20007792fda114be42eb08d9a9");
		imp1.setDocumentotipo("ISR");
		imp1.setIdacto("18364923038947");
		imp1.setEscritura(esc.getEscritura());
		imp1.setIdexpediente(esc.getIdexpediente());
		imp1.setExpediente(esc.getExpediente());
		
				
		impuestos.add(imp1);
		//impuestos.add(imp2);
		esc.setImpuestos(impuestos);
		bonitaNewProcess(esc);
		
	}
	
	
	
	
	
	//ID DE LA TAREA
	public void bonitaAssignTaskActualSession(Long task) throws UpdateException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
	
		bonitaAssignTask(task,apiSession.getUserId());
	}
	public void bonitaAssignTask(Long task,Long idsesion) throws UpdateException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		bonitaGetProcess();
		processAPI.assignUserTask(task, idsesion);
	}
	
	
	public void bonitaExcecuteTask(Long idtask,Map<String, Serializable> inputs) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, UserTaskNotFoundException, FlowNodeExecutionException, ContractViolationException{
		bonitaGetProcess();
		processAPI.executeUserTask(idtask, inputs);
	}
	
	public void bonitaExcecuteTask(Long idtask) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, UserTaskNotFoundException, FlowNodeExecutionException, ContractViolationException{
		Map<String, Serializable> inputs = new HashMap<String, Serializable>();
		bonitaExcecuteTask(idtask,inputs);
	}

	public ActorInstance bonitaGetInstanceUserByName(String nombre) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, ProcessDefinitionNotFoundException{
		bonitaGetProcess();
		long pd = processAPI.getProcessDefinitionId(PROCESS, VERSION);
		List<ActorInstance> actors =processAPI.getActors(pd, 0, 10, ActorCriterion.NAME_ASC);
		for(ActorInstance actor:actors){
			if(actor.getName().equals(nombre)){
				System.out.println("Encontre instancia de :"+actor.toString());
				return actor;
			}
		}
		return null;
	}
	
	public User bonitaGetUserByName(String username) throws UserNotFoundException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		bonitaGetIdentity();
		return identityAPI.getUserByUserName(username);
	}
	
	
	
	public String getTaskName(long activityInstanceId) throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException{
		bonitaGetProcess();
		ActivityInstance activity=null;
		try {
			activity = processAPI.getActivityInstance(activityInstanceId);
		} catch (ActivityInstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activity.getDisplayName();
		
	}
	
	
	public String getNextTask(long activityInstanceId)throws BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, ProcessDefinitionNotFoundException{
		ProcessAPI proAPI = bonitaGetProcess();
		
		Long processDefId = proAPI.getProcessDefinitionIdFromActivityInstanceId(activityInstanceId);
//		proAPI.g
		DesignProcessDefinition designProDef = proAPI.getDesignProcessDefinition(processDefId);
		FlowElementContainerDefinition elmntContDef = designProDef.getFlowElementContainer();
		
		for(TransitionDefinition trDf : elmntContDef.getTransitions()){
			System.out.println(trDf.getSource());
			System.out.println(trDf.getName());
			System.out.println(trDf.getCondition().getName());
			
		}
		return "";
	}
	
	public void deleteCaseForTask(long activityId){
		ProcessAPI proAPI;
		try {
			proAPI = bonitaGetProcess();
			proAPI.deleteProcessInstance(102008);
		} catch (BonitaHomeNotSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownAPITypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DeletionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) throws UserNotFoundException, BonitaHomeNotSetException, ServerAPIException, UnknownAPITypeException, LoginException, ProcessDefinitionNotFoundException, ProcessActivationException, ProcessExecutionException, SearchException {
		BonitaUtilidades bu = new BonitaUtilidades("golguin", "golguin");
		bu.deleteCaseForTask(102008);
//		try {
//			User usr =bu.bonitaGetUserByName("caja");
//			System.out.println(usr.toString());
//			bu.testbonitaNewProcess();
//		} catch (BonitaHomeNotSetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ServerAPIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnknownAPITypeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		System.out.println(bu.getTaskName(2000002L));
	}
	
	
	
	
	
	
	
}
