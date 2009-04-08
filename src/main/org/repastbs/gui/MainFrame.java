/**
 * File: MainFrame.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.repastbs.RepastBS;
import org.repastbs.component.Component;
import org.repastbs.editors.Editor;
import org.repastbs.editors.EditorEvent;
import org.repastbs.editors.EditorListener;
import org.repastbs.editors.NoEditor;
import org.repastbs.gui.models.ModelComponentsCellRenderer;

/**
 * Main frame window
 * @author  Ludovit Hajzer
 */
public class MainFrame extends JFrame implements TreeSelectionListener, TreeModelListener, EditorListener {

	/** */
	private static final long serialVersionUID = 5382554624792970269L;

	RepastBS repast;

	JTree modelComponents;
	DefaultTreeModel componentsModel;
	//JTable properties;
	//PropertyTableModel propertyTableModel = new PropertyTableModel(properties);
	JScrollPane scrollPane;
	JSplitPane split;
	JPanel editorPanel= new JPanel();

	DefaultTreeModel treeModel;
	JButton cancel = new JButton("Quit");

	JToolBar toolBar = new JToolBar();

	/**
	 * Creates main application window
	 * @param repast 
	 */
	public MainFrame (RepastBS repast) {
		this.repast = repast;
		createMenu();		

		modelComponents = new JTree();
		componentsModel = new DefaultTreeModel(repast.getModel());
		//repast.getModel().setTreeModel(componentsModel);
		modelComponents.setModel(componentsModel);
		modelComponents.getModel().addTreeModelListener(this);
		modelComponents.addTreeSelectionListener(this);
		modelComponents.setCellRenderer(new ModelComponentsCellRenderer());

		split = new JSplitPane();
		java.awt.Component scroll = SwingUtils.createScrollComponent(modelComponents);
		scroll.setMinimumSize(new Dimension(250,100));
		split.setLeftComponent(scroll);
		split.setRightComponent(editorPanel);
		split.setDividerLocation(150);
		split.setContinuousLayout(true);
		modelComponents.setMinimumSize(new Dimension(250,100));
		//scrollPane.setMinimumSize(new Dimension(100,100));
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(toolBar, BorderLayout.PAGE_START);

		addToolbarButton("New", "New model", "NewModel");
		addToolbarButton("Open", "Open model", "OpenModel");
		addToolbarButton("Save", "Save model", "SaveModel");
		addToolbarButton("SaveAs", "Save model as", "SaveAsModel");
		toolBar.addSeparator(new Dimension(10,32));
		addToolbarButton("Delete", "Delete selected component", "Delete");
		toolBar.addSeparator(new Dimension(10,32));
		addToolbarButton("Compile", "Compile model", "Compile");
		addToolbarButton("Run","Run simulation", "Run");
		toolBar.addSeparator(new Dimension(10,32));
		addToolbarButton("Marshaller","Marshalling model","Marshalling");
		addToolbarButton("Unmarshaller","Unmarshalling model","UnMarshalling");

		toolBar.setFloatable(false);
		toolBar.setRollover(true);

		JPanel mainContent = new JPanel();
		cp.add(mainContent,BorderLayout.CENTER);

		mainContent.setLayout(new BorderLayout());
		mainContent.add(split,BorderLayout.CENTER);
		mainContent.add(cancel,BorderLayout.SOUTH);
		split.setDividerSize(4);

		cancel.addActionListener(repast);
		setSize(800,450);
		setLocationRelativeTo(null);
		setTitle("Repast BS");
	}

	private void addToolbarButton(String command, String label, String icon) {
		JButton b = SwingUtils.createToolbarButton(label, RepastBS.getIcon(icon));
		b.setActionCommand(command);
		b.addActionListener(repast);
		toolBar.add(b);
		setVisible(true);
	}

	/*private void addComponent(DefaultMutableTreeNode node, DefaultMutableTreeNode root) {
		Enumeration<Component> e = root.get;
		DefaultMutableTreeNode actualNode = new DefaultMutableTreeNode(c.getName());
		System.out.println("Processing: "+c.getName());
		treeModel.insertNodeInto(actualNode,root, 0);
		while(e.hasMoreElements())
			addComponent(e.nextElement(),actualNode);
	}*/

	private void createMenu() {
		ResourceBundle strings = ResourceBundle.getBundle("properties/menu",Locale.getDefault());
		int maxDepth=5;
		try {
			maxDepth=Integer.parseInt(strings.getString("maxDepth"));
			if(maxDepth<=0) {
				System.out.println("Error: maxDepth menu property must be positive number, using default depth of 5");
				maxDepth=5;
			}
		} catch (NumberFormatException e) {
			System.out.println("Error: not parsable maxDepth menu property, using default depth of 5");
			maxDepth=5;
		}
		JMenuBar menu=new JMenuBar();
		String menus[]=tokenize(strings.getString("menus")," ");
		int counts[]=new int[menus.length];
		for(int i=0;i<counts.length;i++) {
			String tokens[]=tokenize(menus[i],":");
			int count=Integer.parseInt(tokens[1]);
			//counts[i]=Integer.parseInt(tokens[1]);
			String name=strings.getString(tokens[0]);
			JMenu m=new JMenu(name);
			int currLevel=0;
			JMenu parents[]=new JMenu[maxDepth];
			parents[0]=m;
			for(int j=1;j<count+1;j++) {
				try {
					String line=strings.getString(tokens[0]+j);
					int index=line.indexOf(':');
					String cname;
					String action;
					if(index==-1) {
						cname=line;
						action=null;
					}
					else {	
						cname=line.substring(0,index);
						action=line.substring(index+1);
					}
					int isParent=isParentMenu(strings,tokens[0],j);
					while(cname.startsWith("."))
						cname=cname.substring(1);
					if(isParent>currLevel) {
						currLevel++;
						parents[currLevel]=new JMenu(cname);
						parents[currLevel-1].add(parents[currLevel]);
					}
					else {
						if(cname.compareTo("-")==0)
							parents[currLevel].addSeparator();
						else {
							JMenuItem cm=new JMenuItem(cname);
							parents[currLevel].add(cm);
							if(!repast.getActionManager().isActionRegistered(action))
								System.out.println("Warning: action "+action+" has not been registered");
							cm.setActionCommand(action);
							cm.addActionListener(repast);
						}
					}
					currLevel=isParent;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Error processing menu "+tokens[0]+j+", probably reached maxDepth limit");
				} catch (NullPointerException e) {
					System.out.println("Error processing menu "+tokens[0]+j+", parent menu not found");
				}catch (RuntimeException e) {
					e.printStackTrace();
					System.out.println("Error processing menu "+tokens[0]+j);
				}
			}
			menu.add(m);
		}
		setJMenuBar(menu);
	}

	private int isParentMenu(ResourceBundle b,String key,int pos) {
		try {
			String text=b.getString(key+(pos+1));
			int level=0;
			while(text.startsWith(".")) {
				level++;
				text=text.substring(1);
			}
			return level;
		}
		catch(Exception e) {
			return 0;
		}
	}


	/**
	 * @param s - string to tokenize
	 * @param delimeter
	 * @return tokenized array
	 */
	static public String[] tokenize(String s,String delimeter) {
		StringTokenizer st=new StringTokenizer(s,delimeter);
		String [] ret=new String[st.countTokens()];
		for(int i=0;i<ret.length;i++)
			ret[i]=st.nextToken();
		return ret;
	}

	/**
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent e) {
		Component component = (Component)modelComponents.getLastSelectedPathComponent();
		if(component==null)
			return;
		repast.setActualComponent(component);
		Editor editor = component.getEditor();
		if(editor==null)
			component.setEditor(editor=RepastBS.getEditorManager().getEditor(component.getClass()));
		if(!component.isEditable()) {
			NoEditor noEditor = new NoEditor();
			noEditor.setLabelText("Component is not editable");
			editor = noEditor;
		}
		split.setRightComponent(editor.getEditorPanel());
		editor.setEditedValue(component);
		((DefaultTreeModel)modelComponents.getModel()).nodeChanged(component);
	}

	/**
	 * @see org.repastbs.editors.EditorListener#valueChanged(org.repastbs.editors.EditorEvent)
	 */
	public void valueChanged(EditorEvent e) {
		DefaultTreeModel model = (DefaultTreeModel)modelComponents.getModel();
		if(e.getType()==EditorEvent.VALUE_CREATED) {
			TreePath parentPath = modelComponents.getSelectionPath();
			Component parentNode = (Component)(parentPath.getLastPathComponent());

			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)e.getNewValue();
			((DefaultTreeModel)modelComponents.getModel()).insertNodeInto(childNode, parentNode,parentNode.getChildCount());
			modelComponents.scrollPathToVisible(new TreePath(childNode.getPath()));
			model.nodeStructureChanged(e.getNewValue()); 
			repast.setActualComponent(e.getNewValue());
		}
		else if(e.getType() == EditorEvent.VALUE_EDITED)
			repast.setActualComponent(e.getNewValue());
		repast.setProperty("modelSaved",new Boolean(false));
		repast.getActionManager().executeAction("UpdateFrameTitle");
		model.nodeChanged(e.getNewValue());
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesChanged(javax.swing.event.TreeModelEvent)
	 */
	public void treeNodesChanged(TreeModelEvent e) {
		System.out.println("chaaanged");
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesInserted(javax.swing.event.TreeModelEvent)
	 */
	public void treeNodesInserted(TreeModelEvent e) {
		((DefaultTreeModel)modelComponents.getModel()).nodeChanged((Component)modelComponents.getLastSelectedPathComponent());
		System.out.println("inserted");
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesRemoved(javax.swing.event.TreeModelEvent)
	 */
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub
		System.out.println("removed");
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeStructureChanged(javax.swing.event.TreeModelEvent)
	 */
	public void treeStructureChanged(TreeModelEvent e) {
		((DefaultTreeModel)modelComponents.getModel()).nodeChanged((Component)modelComponents.getLastSelectedPathComponent());
		System.out.println("chnaged");
	}

	/**
	 * refreshes model components tree
	 */
	public void refreshModel() {
		modelComponents.setModel(componentsModel = new DefaultTreeModel(repast.getModel()));
		if(repast.getModel()!=null)
			repast.getModel().setTreeModel(componentsModel);
	}

	/**
	 * refreshes model components tree
	 * @param c 
	 */
	public void setActual(Component c) {
		modelComponents.scrollPathToVisible(new TreePath(((DefaultMutableTreeNode)c).getPath()));
	}

	/**
	 * @param c component to remove
	 */
	public void removeComponent(Component c) {
		c.getParent().removeChildProp(c);
		((DefaultTreeModel)modelComponents.getModel()).removeNodeFromParent(c);
		//c.getParent().remove(c);
		//c.removeFromParent();
		modelComponents.setSelectionRow(-1);
		repast.setActualComponent(null);
		NoEditor noEditor = new NoEditor();
		noEditor.setLabelText("Select component");
		split.setRightComponent(noEditor);
	}
}