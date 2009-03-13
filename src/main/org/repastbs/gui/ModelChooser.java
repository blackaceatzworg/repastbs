/**
 * File: ModelChooser.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.repastbs.RepastBS;
import org.repastbs.gui.models.IconLabelCellRenderer;
import org.repastbs.model.Model;


/**
 * Frame for selecting from model templates
 * @author  Ľudovít Hajzer
 */
public class ModelChooser extends RepastBSDialog implements ActionListener, ListSelectionListener {
	
	/** */
	private static final long serialVersionUID = 1368391189591282167L;
	
	private JList modelsList;
	private DefaultListModel listModel;
	private JTextPane description = new JTextPane();
	private List<Model> models;
	private Model selectedModel;
	
	/**
	 * @param parent - main frame
	 * @param models 
	 */
	public ModelChooser(MainFrame parent, List<Model> models) {
		super(parent, "New model", true);
		this.models = models;
		Container cp = getContentPane();
		modelsList = new JList(listModel = new DefaultListModel());
		modelsList.addListSelectionListener(this);
		
		modelsList.setCellRenderer(new IconLabelCellRenderer());
		modelsList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
	            if (evt.getClickCount() == 2) {
	                dispose();
	            }
	        }
	    });
		for(int i = 0; i < models.size(); i++) {
			Model curr = models.get(i);
			JLabel label = new JLabel(curr.getName());
			label.setIcon(RepastBS.getIcon(curr.getClass().getSimpleName()));
			label.setOpaque(true);
			listModel.addElement(label);
		}
		description.setEditable(false);

		
		cp.setLayout(gridbag);
		gbc.fill = GridBagConstraints.BOTH;
		add(0,0,1,1,100,100,SwingUtils.createTitledScrollComponent(
				"Select model template", modelsList),cp);
		add(1,0,1,1,100,100,SwingUtils.createTitledScrollComponent(
				"Description", description),cp);
		add(0,1,2,1,100,1,SwingUtils.createOkCancelPanel("Ok", "Cancel", this),cp);
		setSize(400,200);
		setLocationRelativeTo(parent);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().compareTo("Ok")==0)
			dispose();
		if(e.getActionCommand().compareTo("Cancel")==0) {
			selectedModel = null;
			dispose();
		}
	}

	/**
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		selectedModel = models.get(modelsList.getSelectedIndex());
		description.setText(selectedModel.getDescription());
	}
	
	/**
	 * @return  selected model, or null if nothing selected
	 * @uml.property  name="selectedModel"
	 */
	public Model getSelectedModel() {
		return selectedModel;
		
	}

}
