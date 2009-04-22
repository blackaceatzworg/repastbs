package org.repastbs.component.network;

import org.repastbs.component.AbstractComponent;
import org.repastbs.component.IntegerComponent;
import org.repastbs.component.Schedulable;
import org.repastbs.component.ScheduleComponent;
import org.repastbs.component.StringComponent;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;

public class NetworkPlot extends AbstractComponent implements DynamicChanger, Schedulable {

	/** */
	private static final long serialVersionUID = -8424801631943608440L;

	private StringComponent plotName;
	private StringComponent plotTitle;
	private IntegerComponent xaxis;
	private IntegerComponent yaxis;

	/** */
	public static final String ID = "NETWORK_PLOT";

	public NetworkPlot() {
		super("Network Plot");
		setId(ID);
	}

	@Override
	public void createNew() throws Exception {
		plotName = new StringComponent("Plot name","NetworkPlot");
		add(plotName);
		plotTitle = new StringComponent("Plot title","Network Stats");
		add(plotTitle);
		xaxis = new IntegerComponent("X-Axis size",100);
		add(xaxis);
		yaxis = new IntegerComponent("Y-Axis size",4);
		add(yaxis);

		ScheduleComponent schedule = new ScheduleComponent();
		add(schedule);
		schedule.createNew();
	}

	@Override
	public void generateFields(DynamicGenerator generator)
			throws DynamicException {
		generator.addField("surface", "uchicago.src.sim.gui.DisplaySurface", null);

		generator.addImport("java.util");
		generator.addImport("uchicago.src.sim.network");
		generator.addImport("uchicago.src.sim.gui");
		generator.addImport("uchicago.src.sim.analysis ");
	}

	@Override
	public void generateMethods(DynamicGenerator generator)
			throws DynamicException {
		//TODO write code 
	}

	@Override
	public String getIterableClass() {
		return null;
	}

	@Override
	public String getSchedulableObjectName() {
		return "surface";
	}

	@Override
	public boolean isIterable() {
		return false;
	}

}
