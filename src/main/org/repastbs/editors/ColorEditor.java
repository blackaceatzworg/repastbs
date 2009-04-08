/**
 * File: ColorEditor.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.editors;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.repastbs.component.ColorComponent;
import org.repastbs.component.Component;
import org.repastbs.gui.SwingUtils;

/**
 * Editor used for editing Color components
 * @author  Ludovit Hajzer
 */
public class ColorEditor extends AbstractEditor implements ActionListener, ChangeListener {
	
	/** */
	private static final long serialVersionUID = 2593612224649361192L;
	
	private ColorComponent colorComponent;
	private JColorChooser chooser;
	private JLabel previewLabel;
	
	/** */
	public ColorEditor() {
		setLayout(gridbag);
		chooser = new JColorChooser();
		chooser.setBorder(BorderFactory.createTitledBorder("Color editor"));
		chooser.getSelectionModel().addChangeListener(this);
		gbc.fill = GridBagConstraints.BOTH;
		add(0,0,2,1,1, 100, chooser ,this);
		previewLabel = new JLabel();
		previewLabel.setOpaque(true);
		previewLabel.setBackground(Color.black);
		chooser.setPreviewPanel(new JPanel());
		add(0,1,1,1,1, 1, new JLabel("Selected color: ") ,this);
		add(1,1,1,1,100, 1, previewLabel ,this);
		
		add(0,2,2,1,1, 1, SwingUtils.createOkCancelPanel("Ok", "Cancel", this) ,this);
		
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Ok") {
			colorComponent.setColor(chooser.getColor());
			fireValueChanged(new EditorEvent(this,getEditedValue()));
		}
		if (e.getActionCommand()== "Cancel"  && colorComponent != null)
			setEditedValue(colorComponent);
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditedValue()
	 */
	public ColorComponent getEditedValue() {
		colorComponent.setColor(chooser.getColor());
		return colorComponent;
	}
	
	/**
	 * @see org.repastbs.editors.Editor#setEditedValue(org.repastbs.component.Component)
	 */
	public void setEditedValue(Component component) {
		colorComponent = (ColorComponent)component;
		chooser.setColor(colorComponent.getColor());
		previewLabel.setBackground(colorComponent.getColor());
	}

	/**
	 * @see org.repastbs.editors.Editor#getSupportedClass()
	 */
	@SuppressWarnings("unchecked")
	public Class getSupportedClass() {
		return ColorComponent.class;
	}

	/**
	 * @see org.repastbs.editors.Editor#getEditorPanel()
	 */
	public Container getEditorPanel() {
		return this;
	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		previewLabel.setBackground(chooser.getColor());
	}
}