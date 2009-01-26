/**
 * File: DefaultGridAgent.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.component.grid;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import uchicago.src.sim.engine.CustomProbeable;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;

/**
 * This class is copy of  uchicago.src.simbuilder.base.DefaultGridAgent is used as parent class for generated grid agents
 */
public class DefaultGridAgent implements CustomProbeable, Drawable {

	protected static final int RECT = 0;
	protected static final int CIRCLE = 1;

	protected int x;
	protected int y;
	protected Color color = Color.red;
	protected int shape = RECT;
	
	private List<String> propNames = new ArrayList<String>();
	
	/** */
	public DefaultGridAgent() {
		propNames.add("x");
		propNames.add("y");
	}

	/**
	 * 
	 */
	public void setDrawAsRect() {
		shape = RECT;
	}

	/**
	 * 
	 */
	public void setDrawAsCircle() {
		shape = CIRCLE;
	}

	/**
	 * @param  c
	 * @uml.property  name="color"
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * @return  shape
	 * @uml.property  name="shape"
	 */
	public int getShape() {
		return shape;
	}

	/**
	 * @param  shape
	 * @uml.property  name="shape"
	 */
	public void setShape(int shape) {
		this.shape = shape;
	}


	/**
	 * Invoked when the object should draw itself
	 * @param g sim graphics
	 */
	public void draw(SimGraphics g) {
		if (shape == RECT)
			g.drawFastRect(color);
		else
			g.drawFastRoundRect(color);
	}


	/**
	 * Gets the x coordinate of this drawable. Note that this should return the x coordinate in some space, not necessarily a screen coordinate.
	 * @return  x
	 * @uml.property  name="x"
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param  val
	 * @uml.property  name="x"
	 */
	public void setX(int val) {
		x = val;
	}

	/**
	 * Gets the y coordinate of the this drawable. Note that this should return the y coordinate in some space, not necessarily a screen coordinate.
	 * @return  y
	 * @uml.property  name="y"
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param val  - y value
	 * @uml.property  name="y"
	 */
	public void setY(int val) {
		y = val;
	}

	/**
	 * @see uchicago.src.sim.engine.CustomProbeable#getProbedProperties()
	 */
	public String[] getProbedProperties() {
		String[] result = new String[propNames.size()];
		return propNames.toArray(result);
	}
}
