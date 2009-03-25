/**
 * File: MarshallAction.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.actions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.repastbs.RepastBS;
import org.repastbs.generated.StringComponentProp;
import org.repastbs.generated.NetworkModelProp;
import org.repastbs.generated.ScheduledActionProp;
import org.repastbs.generated.VariableProp;

public class MarshallAction extends AbstractAction {
	
	private StringComponentProp abcopr = new StringComponentProp();
	private VariableProp variable = new VariableProp();
	private ScheduledActionProp schedule = new ScheduledActionProp();
//	private RepastBS repastBS;

	public MarshallAction(RepastBS repastBS) {
		super("Marshaller");
//		this.repastBS = repastBS;
	}

	public Object execute(Object component) {
		System.out.println("Marshalling model");
		//Component data = repastBS.getModel();
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(NetworkModelProp.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            NetworkModelProp toMarshall = new NetworkModelProp();    
			toMarshall.setStringComponent(abcopr);
            toMarshall.setVariable(variable);
            toMarshall.setScheduledAction(schedule);
            marshaller.marshal(toMarshall, System.out);
        } catch (JAXBException e) {
        	System.out.println("marshalling model exception");
        	e.printStackTrace();
		}
		return null;
	}
}
