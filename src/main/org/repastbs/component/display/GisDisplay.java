/**
 * File: GridDisplay.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.display;

import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Action;
import org.repastbs.component.ActionsComponent;
import org.repastbs.component.Schedulable;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.ScheduledAction;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.GisDisplayProp;

public class GisDisplay extends AbstractComponent implements DynamicChanger, Schedulable {

	/** */
	private static final long serialVersionUID = -5278496280395421088L;
	
	private GisDisplayProp gisDisplayProp = new GisDisplayProp();
	/** */
	public static final String ID = "GIS_DISPLAY";

	/** */
	public GisDisplay() {
		super("Gis Display");
		setId(ID);
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	@Override
	public void createNew() throws Exception {
		ActionsComponent actions = new ActionsComponent();
		add(actions);
		actions.createNew();
		gisDisplayProp.setActions(actions.getActionsProp());

		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.createNew();
		gisDisplayProp.setSchedule(schedule.getScheduleProp());

		Action a = actions.createAction("updateDisplay","","surface.updateDisplay();",null);
		a.setRemovable(false);
		a.setEditable(false);
		actions.setRemovable(false);
		actions.setEditable(false);

		schedule.setRemovable(false);
		schedule.setEditable(false);

		ScheduledAction scheduledAction=schedule.createScheduleAction("updateDisplay",a,ScheduledAction.EVERY_TICK,1,false);
		scheduledAction.setRemovable(false);
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateFields(org.repastbs.dynamic.DynamicGenerator)
	 */
	@Override
	public void generateFields(DynamicGenerator generator)
			throws DynamicException {
		generator.addField("surface", "uchicago.src.sim.gui.DisplaySurface", null);

		generator.addImport("java.util");
		generator.addImport("uchicago.src.sim.network");
		generator.addImport("uchicago.src.sim.gui");
	}

	/**
	 * @see org.repastbs.dynamic.DynamicChanger#generateMethods(org.repastbs.dynamic.DynamicGenerator)
	 */
	@Override
	public void generateMethods(DynamicGenerator generator)
			throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see org.repastbs.component.Schedulable#getIterableClass()
	 */
	@Override
	public String getIterableClass() {
		return null;
	}

	/**
	 * @see org.repastbs.component.Schedulable#getSchedulableObjectName()
	 */
	@Override
	public String getSchedulableObjectName() {
		return "surface";
	}

	/**
	 * @see org.repastbs.component.Schedulable#isIterable()
	 */
	@Override
	public boolean isIterable() {
		return false;
	}

	/**
	 * @return the gisDisplayProp
	 */
	public GisDisplayProp getGisDisplayProp() {
		return gisDisplayProp;
	}

	/**
	 * @param gisDisplayProp the gisDisplayProp to set
	 */
	public void setGisDisplayProp(GisDisplayProp gisDisplayProp) {
		this.gisDisplayProp = gisDisplayProp;
		removeAllChildren();
		
		ActionsComponent ac = new ActionsComponent();
		ac.createNew();
		add(ac);
		ac.setActionsProp(gisDisplayProp.getActions());
		ac.setRemovable(false);
		ac.setEditable(false);

		ScheduleComponent sc = new ScheduleComponent();
		sc.createNew();
		add(sc);
		sc.setScheduleProp(gisDisplayProp.getSchedule());
		sc.setRemovable(false);
		sc.setEditable(false);
	}
}
