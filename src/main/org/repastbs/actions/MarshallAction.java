/**
 * File: MarshallAction.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.repastbs.RepastBS;
import org.repastbs.Utils;
import org.repastbs.generated.NetworkModelProp;
import org.repastbs.gui.XMLFileFilter;

public class MarshallAction extends AbstractAction {
	
	private RepastBS repastBS;

	public MarshallAction(RepastBS repastBS) {
		super("Marshaller");
		this.repastBS = repastBS;
	}

	public Object execute(Object parameters) {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new XMLFileFilter());
		boolean saveAs = false;
		if (parameters == null)
			saveAs = false;
		else
			saveAs = ((Boolean) parameters).booleanValue();
		File modelFile = (File)repastBS.getProperty("modelFile");
		if (!saveAs && modelFile == null)
			saveAs = true;
		int returnVal = JFileChooser.APPROVE_OPTION;
		if (saveAs) {
			returnVal = fc.showSaveDialog(repastBS.getMainFrame());
			modelFile = fc.getSelectedFile();
		}
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// Component data = repastBS.getModel();
			if(Utils.getExtension(modelFile)==null || !Utils.getExtension(modelFile).equals(Utils.xml))
				modelFile = new File(modelFile.getAbsolutePath()+".xml");
			JAXBContext context;
			FileWriter fw = null;
			try {
				context = JAXBContext.newInstance(NetworkModelProp.class);

				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				/*
				 * NetworkModelProp toMarshall = new NetworkModelProp();
				 * toMarshall.setDescription(abcopr);
				 * variable.setDefaultValue("sdifklsdjfkl");
				 */
				/*
				 * toMarshall.setVariable(variable);
				 * toMarshall.setScheduledAction(schedule);
				 */
				fw = new FileWriter(modelFile);
				marshaller.marshal(repastBS.getModel().getModelProp(), fw);
			} catch (Exception e) {
				System.out.println("marshalling model exception");
				e.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						// IGNORE
					}
				}
			}
		}
		return null;
	}
}
