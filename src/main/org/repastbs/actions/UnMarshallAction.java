/**
 * File: UnMarshallAction.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.actions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.repastbs.component.AbstractComponent;
import org.repastbs.component.Component;

public class UnMarshallAction extends AbstractAction {

	public UnMarshallAction() {
		super("Unmarshaller");
	}

	public Object execute(Object component) {

		@SuppressWarnings("unused")
		Component data = (Component)component;
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(AbstractComponent.class);
            @SuppressWarnings("unused")
			Unmarshaller unmarshaller = context.createUnmarshaller();
//            unmarshaller.setProperty(Unmarshaller.JAXB_FORMATTED_OUTPUT, true);
//            unmarshaller.unmarshal(data, System.out);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
        	System.out.println("unmarshalling model exception");
        	e.printStackTrace();
        }
        System.out.println("UnMarshalling model");
		return null;
	}
}
