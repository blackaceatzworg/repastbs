/**
 * File: MasterSchedule.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;

/**
 * Master schedule is responsible for generation of all schedules in model
 * @author Ľudovít Hajzer
 *
 */
public class MasterSchedule extends AbstractComponent implements DynamicChanger {


	/** */
	private static final long serialVersionUID = 7171703519151877211L;

	/** */
	public static final String ID = "MASTER_SCHEDULE";

	private List<Map<Integer,Map<Boolean,List<ScheduledAction>>>> scheduledActions 
	= new ArrayList<Map<Integer,Map<Boolean,List<ScheduledAction>>>>();

	/**
	 * default constructor
	 */
	public MasterSchedule() {
		super("Master Schedule");
		for(int i=0;i<5;i++)
			scheduledActions.add(i, new HashMap<Integer, Map<Boolean,List<ScheduledAction>>>());
		setId(ID);
	}

	/**
	 * removes all scheduled actions
	 */
	public void clear() {
		for (int i = 0; i < 5; i++)
			scheduledActions.get(i).clear();
	}

	/**
	 * @param name 
	 * @param action 
	 * @param execution 
	 * @param tick 
	 * @param executeLast 
	 * @param index 
	 * @return newly created scheduled action
	 */
	public ScheduledAction createScheduleAction(String name, 
			Action action, int execution, 
			int tick, boolean executeLast, int index) {
		ScheduledAction s = new ScheduledAction(name,action,execution,tick,executeLast, index);
		addScheduledAction(s);
		return s;
	}


	/**
	 * @param s
	 */
	public void addScheduledAction(ScheduledAction s) {
		if(s==null)
			return;
		super.add(s);
	}

	/**
	 * @see org.repastbs.component.Component#toString()
	 */
	public String toString() {
		return getName();
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	public void createNew() {

	}

	/**
	 * @param component
	 */
	public void findSchedules(Component component) {
		Enumeration<?> children = component.children();
		while(children.hasMoreElements()) {
			Component comp = (Component)children.nextElement();
			if(comp.getId().equals(ScheduleComponent.ID)){
				processScheduledComponent((ScheduleComponent)comp);
				continue;
			}
			findSchedules(comp);
		}
	}

	/**
	 * @param component
	 */
	@SuppressWarnings("unchecked")
	private void processScheduledComponent(ScheduleComponent component) {
		Enumeration scheduledActions = 
			((ScheduleComponent)component).children();
		while(scheduledActions.hasMoreElements()) {
			ScheduledAction action = (ScheduledAction)scheduledActions.nextElement();
			List<ScheduledAction> scheduled = getActionsScheduledForTick(action);
			scheduled.add(action);
		}
	}

	/**
	 * @param a
	 * @return actions scheduled for tick at which action a is scheduled
	 */
	private List<ScheduledAction> getActionsScheduledForTick(ScheduledAction a) {
		List<ScheduledAction> scheduledActions;
		try {
			scheduledActions = this.scheduledActions
			.get(a.getScheduledActionProp().getExecution())
			.get(new Integer(a.getScheduledActionProp().getTick()))
			.get(new Boolean(a.getScheduledActionProp().isExecuteLast()));
		} catch(NullPointerException e) {
			scheduledActions = null;
		}
		//no actions at tick, create new list
		if(scheduledActions == null) {
			Map<Boolean,List<ScheduledAction>> map = new HashMap<Boolean,List<ScheduledAction>>();

			scheduledActions = new ArrayList<ScheduledAction>();
			List<ScheduledAction> otherActions = new ArrayList<ScheduledAction>();
			if(a.getScheduledActionProp().isExecuteLast()) {
				map.put(new Boolean(true), scheduledActions);
				map.put(new Boolean(false), otherActions);
			}
			else {
				map.put(new Boolean(false), scheduledActions);
				map.put(new Boolean(true), otherActions);
			}
			this.scheduledActions.get(a.getScheduledActionProp().getExecution()).put(
					new Integer(a.getScheduledActionProp().getTick()),
					map);
		}
		return scheduledActions;
	}

	/**
	 * @param executionType 
	 * @param tick 
	 * @param executeLast 
	 * @return actions scheduled for tick
	 */
	public List<ScheduledAction> getActionsScheduledForTick(int executionType, int tick, boolean executeLast) {
		List<ScheduledAction> toSort = scheduledActions.get(executionType).get(new Integer(tick)).get(new Boolean(executeLast));
		Collections.sort(toSort);
		return toSort;
	}

	/**
	 * @param executionType
	 * @return ticks for selected execution type
	 */
	public List<Integer> getTicks(int executionType) {
		return new ArrayList<Integer>(
				scheduledActions.get(executionType).keySet());
	}

	/**
	 * @param executionType
	 * @param tick
	 * @param executeLast 
	 * @param currentActionPosition
	 */
	public void moveActionUp(int executionType, int tick, boolean executeLast, int currentActionPosition) {
		List<ScheduledAction> scheduled = 
			getActionsScheduledForTick(executionType, tick, executeLast);
		if(currentActionPosition==0)
			return;
		Collections.swap(scheduled, currentActionPosition, currentActionPosition-1);
		scheduled.get(currentActionPosition).getScheduledActionProp().setIndex(currentActionPosition);
		scheduled.get(currentActionPosition-1).getScheduledActionProp().setIndex(currentActionPosition-1);
	}

	/**
	 * @param executionType
	 * @param tick
	 * @param executeLast 
	 * @param currentActionPosition
	 */
	public void moveActionDown(int executionType, int tick, boolean executeLast, int currentActionPosition) {
		List<ScheduledAction> scheduled = 
			getActionsScheduledForTick(executionType, tick, executeLast);
		if(currentActionPosition==scheduled.size()-1)
			return;
		Collections.swap(scheduled, currentActionPosition, currentActionPosition+1);
		scheduled.get(currentActionPosition).getScheduledActionProp().setIndex(currentActionPosition);
		scheduled.get(currentActionPosition+1).getScheduledActionProp().setIndex(currentActionPosition+1);
	}
	
	private StringBuffer generateMethods(DynamicGenerator generator, int execution, 
			int tick, int generated, List<ScheduledAction> scheduledActions, boolean last)
		throws DynamicException {
		StringBuffer scheduleMethod = new StringBuffer();
		StringBuffer methodBody = new StringBuffer();
		for (int j=0;j<scheduledActions.size();j++) {
			ScheduledAction action = scheduledActions.get(j);
			Schedulable s = action.getSchedulable();
			//if not iterable, just call object method
			if(!s.isIterable()) {
				methodBody.append(s.getSchedulableObjectName()+".");
				methodBody.append(action.getActionName()+"();\n");
			} else {
				//generate code using iterator
				generator.addImport("java.util");
				methodBody.append("Iterator iterator = "+s.getSchedulableObjectName()+".iterator();\n");
				methodBody.append("while (iterator.hasNext()) {\n");
				methodBody.append("    "+s.getIterableClass()+" i = ("+s.getIterableClass()+") iterator.next();\n");
				methodBody.append("		i."+action.getActionName()+"();\n");
				methodBody.append("}\n");
			}
		}
		
		//insert created method to dynamic class
		generator.addMethod("scheduledActions"+generated,
				null, null, null, methodBody.toString());
		
		scheduleMethod.append("schedule.scheduleAction");
		switch(execution) {
			case ScheduledAction.EVERY_TICK:
				scheduleMethod.append("Beginning((double) "+tick+",");
				break;
			case ScheduledAction.AT_A_SINGLE_TICK:   
				scheduleMethod.append("At((double) "+tick+",");
				break;
			case ScheduledAction.AT_INTERVAL:
				scheduleMethod.append("AtInterval((double) "+tick+",");
				break;
			case ScheduledAction.AT_END:
				scheduleMethod.append("AtEnd(");
				break;
			case ScheduledAction.AT_PAUSE:
				scheduleMethod.append("AtPause(");
				break;
		}
		scheduleMethod.append("this, \"scheduledActions"+generated+"\"");
		if(last)
			scheduleMethod.append(", Schedule.LAST");
		scheduleMethod.append(");\n");
		return scheduleMethod;
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateFields(DynamicGenerator generator) throws DynamicException {
		clear();
		findSchedules(getParent());
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	public void generateMethods(DynamicGenerator generator) throws DynamicException {
		int generatedMethods = 1;
		StringBuffer scheduleMethod = new StringBuffer();
		for (int i=0;i<scheduledActions.size();i++) {
			Map<Integer,Map<Boolean,List<ScheduledAction>>> ticksMap = scheduledActions.get(i);
			Set<Integer> ticks = ticksMap.keySet();
			for (Iterator<Integer> iterator = ticks.iterator(); iterator.hasNext();) {
				Integer tick = iterator.next();
				Map<Boolean,List<ScheduledAction>> scheduled = ticksMap.get(tick);
				List<ScheduledAction> scheduledActions = scheduled.get(new Boolean(false));
				if(scheduledActions.size()>0) {
					scheduleMethod.append(generateMethods(generator,i,tick,
							generatedMethods,scheduledActions,false));
					generatedMethods++;
				}
				scheduledActions = scheduled.get(new Boolean(true));
				if(scheduledActions.size()>0) {
					scheduleMethod.append(generateMethods(generator,i,tick,
							generatedMethods,scheduledActions,true));
					generatedMethods++;
				}
			}
		}
		generator.addMethod("buildSchedule",
				null, null, null, scheduleMethod.toString());
		generator.addImport("uchicago.src.sim.engine");
		String beginMethod = "schedule = new Schedule();\nbuildSchedule();";
		try {
			generator.insertAfter("begin", null, beginMethod);
		} catch (DynamicException x) {
			generator.addMethod("begin", null, null, null, beginMethod);
		}
	}
}
