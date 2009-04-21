/**
 * File: UnMarshallAction.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.actions;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.repastbs.RepastBS;
import org.repastbs.generated.GisModelProp;
import org.repastbs.generated.GridModelProp;
import org.repastbs.generated.ModelProp;
import org.repastbs.generated.NetworkModelProp;
import org.repastbs.gui.XMLFileFilter;
import org.repastbs.model.Model;

/**
 * @author Ludovit Hajzer
 *
 */
public class UnMarshallAction extends AbstractAction {
	
	private RepastBS repastBS;

	/**
	 * @param repastBS
	 */
	public UnMarshallAction(RepastBS repastBS) {
		super("Unmarshaller");
		this.repastBS = repastBS;
	}

	/**
	 * @see org.repastbs.actions.Action#execute(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Object execute(Object component) {		
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
		fc.setFileFilter(new XMLFileFilter());
		int returnVal = fc.showOpenDialog(repastBS.getMainFrame());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				JAXBContext context = JAXBContext.newInstance(NetworkModelProp.class,GridModelProp.class,GisModelProp.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				ModelProp modelProp = (ModelProp)unmarshaller.unmarshal(fc.getSelectedFile());
				Class clazz = Class.forName(modelProp.getModelClass());
				Model model = (Model)clazz.newInstance();

				model.setModelProp(modelProp);
				repastBS.setModel(model);
				repastBS.setProperty("modelFile",fc.getSelectedFile());
				repastBS.setProperty("modelSaved",true);
				repastBS.getActionManager().executeAction("UpdateFrameTitle");
				//using DOM
				/*SAXReader reader = new SAXReader();
				Document document = reader.read(fc.getSelectedFile());
				document.normalize();
				Node root = document.selectSingleNode("//model");
				Class modelClass = Class.forName(root.valueOf("@class"));
				model = (Model)modelClass.newInstance();
				model.createFromXML(root);
				global.repast.setModel(model);
				global.repast.setProperty("modelFile",fc.getSelectedFile());
				global.repast.setProperty("modelSaved",true);
				global.repast.getActionManager().executeAction("UpdateFrameTitle");*/
			} catch (Exception e) {
				repastBS.showErrorDialog("Error occured while loading model: "+e.getMessage());
				e.printStackTrace();
			}
		}		
		if(repastBS.getModel()!=null)
			repastBS.getMainFrame().refreshModel();
		return repastBS.getModel();
	}
}
