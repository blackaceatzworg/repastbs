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
//import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Component;
import org.repastbs.component.MappedComponent;

public class MarshallAction extends AbstractAction {
	
	private RepastBS repastBS;
	
	public MarshallAction(RepastBS repastBS) {
		super("Marshaller");
		this.repastBS=repastBS;
	}

	public Object execute(Object component) {
		System.out.println("Marshalling model");
		Component data = repastBS.getModel();
        JAXBContext context;
        try {
            //context = JAXBContext.newInstance(AbstractComponent.class);
            context = JAXBContext.newInstance(MappedComponent.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            MappedComponent toMarshall = new MappedComponent();    
            toMarshall.setName(data.getName());
            marshaller.marshal(toMarshall, System.out);
        } catch (JAXBException e) {
        	System.out.println("marshalling model exception");
        	e.printStackTrace();
        }
		return null;
	}
}
