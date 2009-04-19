package org.repastbs.component.gis;

import org.dom4j.Node;
import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Agent;
import org.repastbs.component.ComponentEvent;
import org.repastbs.component.ComponentListener;
import org.repastbs.dynamic.DynamicChanger;
import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.DynamicGenerator;
import org.repastbs.generated.AgentProp;
import org.repastbs.xml.XMLSerializationException;
import org.xml.sax.ContentHandler;

public class GisAgent extends AbstractComponent implements 
Agent, ComponentListener, DynamicChanger {

	/** */
	private static final long serialVersionUID = -1251324159052478587L;

	public GisAgent(String name) {
		super("GisAgent");
		setName("GisAgent");
		//gisAgentProp.setAgentClass(getClass().getName());
		setId(ID);
	}

	@Override
	public AgentProp getAgentProp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAgentProp(AgentProp agentProp) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getIterableClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSchedulableObjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIterable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createNew() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createFromXML(Node node) throws XMLSerializationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeToXML(ContentHandler handler)
			throws XMLSerializationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFields() throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateMethods() throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DynamicGenerator getGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void componentChanged(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateFields(DynamicGenerator generator)
			throws DynamicException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateMethods(DynamicGenerator generator)
			throws DynamicException {
		// TODO Auto-generated method stub
		
	}

}
