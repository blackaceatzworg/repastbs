/**
 * File: RepastBS.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	Development of a Modular Modeling Environment for Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs;

import bsh.EvalError;
import bsh.Interpreter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.repastbs.actions.Action;
import org.repastbs.actions.ActionManager;
import org.repastbs.actions.MarshallAction;
import org.repastbs.actions.UnMarshallAction;
import org.repastbs.component.Component;
import org.repastbs.component.ComponentManager;
import org.repastbs.editors.Editor;
import org.repastbs.editors.EditorManager;
import org.repastbs.gui.MainFrame;
import org.repastbs.model.Model;

/**
 * Main Repast BS class, contains managers
 * @author  Ľudovít Hajzer
 */
public class RepastBS implements ActionListener, WindowListener {

	private static ActionManager actionManager = new ActionManager();
	private static EditorManager editorManager = new EditorManager();
	private static ComponentManager componentManager = new ComponentManager();

	private static Hashtable<String, Icon> icons = new Hashtable<String, Icon>();
	private List<Model> modelTemplates;
	
	private Hashtable<String,Object> properties = new Hashtable<String, Object>();

	private Model model;

	private MainFrame mainFrame;

	private Interpreter interpreter;

	private Component actualComponent;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * possible return types
	 */
	public static List<Class> returnTypes = new LinkedList<Class>();

	/**
	 * possible variable types
	 */
	public static List<Class> variableTypes = new LinkedList<Class>();


	/**
	 * Default empty constructor, creates instance of Repast BS
	 */
	public RepastBS() {
		interpreter = new Interpreter();
		try {
			interpreter.set("repast", this);
		} catch (EvalError e) {
			showErrorDialog("Failed to set Repast global variable for Beanshell interpreter");
		}
		
		System.out.println(System.getProperty ("java.class.path"));
		registerActions();
		registerModels();
		registerIcons(new File("./icons"));
		
		registerComponents(new File("./components"));
		actionManager.registerAction(new MarshallAction(this));
	}

	/**
	 * Repast BS entry point, creates instance of Repast BS, creates main frame
	 * @param args
	 */
	public static void main(String[] args) {
		returnTypes.clear();
		returnTypes.add(void.class);
		returnTypes.add(int.class);
		returnTypes.add(double.class);
		returnTypes.add(boolean.class);
		returnTypes.add(String.class);
		variableTypes.clear();
		variableTypes.add(int.class);
		variableTypes.add(double.class);
		variableTypes.add(float.class);
		variableTypes.add(boolean.class);
		variableTypes.add(String.class);

		RepastBS main = new RepastBS();
		main.getActionManager();
		MainFrame mainFrame = new MainFrame(main);
		main.setMainFrame(mainFrame);
		registerEditors(main);
		
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(main);
		mainFrame.refreshModel();
		actionManager.executeAction("Init");
		actionManager.registerAction(new UnMarshallAction());
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			actionManager.executeAction(e.getActionCommand());
		}
		catch(Exception x) {
			showErrorDialog(x.getMessage());
			x.printStackTrace();
		}
	}

	/**
	 * Registers actions found in ./actions folder
	 */
	public void registerActions() {
		File file = new File("./actions");
		File actions[] = file.listFiles();
		try {
			for (int i = 0; i < actions.length; i++) {

				try {
					Action action = (Action)interpreter.source(actions[i].getAbsolutePath());
					actionManager.registerAction(action);
				} catch (EvalError x) {
					showErrorDialog("Error registering action "
							+actions[i].getName()+": "+x.getMessage());
					x.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			showErrorDialog("Error registering actions: "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * registers editors found in ./editors folder
	 * @param repast repastBS context
	 */
	public static void registerEditors(RepastBS repast) {
		File file = new File("./editors");
		File editors[] = file.listFiles();
		try {
			for (int i = 0; i < editors.length; i++) {
				try {
					Editor editor = (Editor)repast.getInterpreter().source(editors[i].getAbsolutePath());
					editorManager.registerEditor(editor.getSupportedClass(), editor);
					editor.addEditorListener(repast.getMainFrame());
				} catch (EvalError x) {
					repast.showErrorDialog("Error registering editor "
							+editors[i].getName()+": "+x.getMessage());
					x.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			repast.showErrorDialog("Error registering editors: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Register all components found in ./components directory
	 * @param directory 
	 */
	public void registerComponents(File directory) {
		File components[] = directory.listFiles();
		try {
			for (int i = 0; i < components.length; i++) {
				if(components[i].isDirectory()) {
					registerComponents(components[i]);
					continue;
				}
				try {
					Component component = (Component)interpreter.source(components[i].getAbsolutePath());
					componentManager.registerComponent(component);
				} catch (EvalError x) {
					showErrorDialog("Error registering component "
							+components[i].getName()+": "+x.getMessage());
					x.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			showErrorDialog("Error registering components: "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Register all model templates found in ./components directory
	 */
	public void registerModels() {
		modelTemplates = new ArrayList<Model>();
		File file = new File("./models");
		File models[] = file.listFiles();
		try {
			interpreter.set("repast", this);

			for (int i = 0; i < models.length; i++) {

				try {
					Model model = (Model)interpreter.source(models[i].getAbsolutePath());
					modelTemplates.add(model);
				} catch (EvalError x) {
					showErrorDialog("Error registering model "
							+models[i].getName()+": "+x.getMessage());
					x.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			showErrorDialog("Error registering models: "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return  the Repast BS's action manager
	 * @uml.property  name="actionManager"
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}

	/**
	 * Sets Repast BS's action manager
	 * @param actionManager  the actionManager to set
	 * @uml.property  name="actionManager"
	 */
	public void setActionManager(ActionManager actionManager) {
		RepastBS.actionManager = actionManager;
	}

	/**
	 * @return  the actual loaded model
	 * @uml.property  name="model"
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Sets actual model
	 * @param model  the model to set
	 * @uml.property  name="model"
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * @return  the Repast BS's main frame
	 * @uml.property  name="mainFrame"
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Repast BS's allows to set custom main frame to display GUI
	 * @param mainFrame  the mainFrame to set
	 * @uml.property  name="mainFrame"
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * @return  the Repast BS's bean shell interpreter
	 * @uml.property  name="interpreter"
	 */
	public Interpreter getInterpreter() {
		return interpreter;
	}

	/**
	 * Sets custom interpreter for Repast BS
	 * @param interpreter  the interpreter to set
	 * @uml.property  name="interpreter"
	 */
	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	/**
	 * @return  the Repast BS's editor manager
	 * @uml.property  name="editorManager"
	 */
	public static EditorManager getEditorManager() {
		return editorManager;
	}

	/**
	 * Sets editor manager
	 * @param editorManager  the editorManager to set
	 * @uml.property  name="editorManager"
	 */
	public void setEditorManager(EditorManager editorManager) {
		RepastBS.editorManager = editorManager;
	}

	/**
	 * @return  the actual edited component
	 * @uml.property  name="actualComponent"
	 */
	public Component getActualComponent() {
		return actualComponent;
	}

	/**
	 * Sets actual edited component
	 * @param actualComponent  the actualComponent to set
	 * @uml.property  name="actualComponent"
	 */
	public void setActualComponent(Component actualComponent) {
		setProperty("modelSaved",new Boolean(false));
		this.actualComponent = actualComponent;
	}

	/**
	 * Static method to show simple dialog
	 * @param text
	 * @param title
	 * @param type
	 */
	public void showDialog(String text, String title, int type) {
		JOptionPane.showMessageDialog(getMainFrame(),text,title, type);
	}

	/**
	 * Load and register icons from ./icons folder
	 * @param directory
	 */
	public void registerIcons(File directory) {
		File icons[]=directory.listFiles();
		if(icons==null)
			return;
		for (int i = 0; i < icons.length; i++) {
			if(icons[i].isDirectory()) {
				registerIcons(icons[i]);
				continue;
			}
			ImageIcon icon = new ImageIcon(icons[i].getAbsolutePath());
			String name = icons[i].getName().substring(0,icons[i].getName().indexOf('.'));
			RepastBS.icons.put(name,icon);
		}
	}

	/**
	 * @param name
	 * @return icon, if has been registered, or null if not
	 */
	public static Icon getIcon(String name) {
		return icons.get(name);
	}

	/**
	 * Runs simulation using Repast, first generate actual model, and then run it
	 * @param model - model to run
	 */
	public void runSimulation(Model model) {
		try {
			model.generateFields();
			model.generateMethods();
			model.getGenerator().saveToByteCode("output");
			model.getGenerator().saveSourceCode("output");
			Runtime.getRuntime().exec(new StringBuffer().append("java -classpath \"")
					.append("."+File.separator+"output"+File.separator)
					.append(File.pathSeparator)
					.append("."+File.separator+".."+File.separator+"output"+File.separator)
					.append(File.pathSeparator)
					.append(System.getProperty("java.class.path")+"\" "+model.getGenerator().getClassName())
					.toString());
		} catch(Exception e)	{
			showErrorDialog(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Shows simple error dialog
	 * @param message
	 */
	public void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(mainFrame,
				message,
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Shows dialog with Yes, No, Cancel options
	 * @param message - message of dialog
	 * @param title - title of dialog
	 * @return user selected action
	 */
	public int showChooseDialog(String title,String message) {
		return JOptionPane.showConfirmDialog(mainFrame,
				message, title,
	            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Shows simple message dialog, with Ok option
	 * @param message
	 */
	public void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(mainFrame,message);
	}

	/**
	 * @return  the registered model templates
	 * @uml.property  name="modelTemplates"
	 */
	public List<Model> getModelTemplates() {
		return modelTemplates;
	}

	/**
	 * Sets registered model templates
	 * @param modelTemplates  the modelTemplates to set
	 * @uml.property  name="modelTemplates"
	 */
	public void setModelTemplates(List<Model> modelTemplates) {
		this.modelTemplates = modelTemplates;
	}

	/**
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0) {
		//DO NOTHING
	}

	/**
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0) {
		//DO NOTHING
	}

	/**
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0) {
		actionManager.executeAction("Quit");
	}

	/**
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0) {
		//DO NOTHING
	}

	/**
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0) {
		//DO NOTHING
	}

	/**
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0) {
		//DO NOTHING
	}

	/**
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0) {
		//DO NOTHING
	}

	/**
	 * @return  Repast BS's component manager
	 * @uml.property  name="componentManager"
	 */
	public static ComponentManager getComponentManager() {
		return componentManager;
	}
	
	/**
	 * Sets property to Repast BS
	 * @param name
	 * @param value
	 */
	public void setProperty(String name, Object value) {
		if(value==null) {
			Object currValue = properties.get(name);
			if(currValue == null)
				return;
			properties.remove(currValue);
		}
		else
			properties.put(name,value);
	}
	
	/**
	 * Returns registered Repast BS property, or null, if nothing found
	 * @param name of the property to get
	 * @return property - found property
	 */
	public Object getProperty(String name) {
		return properties.get(name);
	}
}
