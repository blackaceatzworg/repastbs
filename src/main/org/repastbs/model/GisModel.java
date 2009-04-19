/**
 * File: GisModel.java
 * Program: Repast BS
 * Author:  Ľudovít Hajzer, Zdenko Osina
 * Master's Thesis:	System Repast
 * Supervisor: Ing. Ladislav Samuelis, CSc.
 * Consultant: László Gulyás, Ph.D.
 */
package org.repastbs.model;

import org.repastbs.dynamic.DynamicException;
import org.repastbs.dynamic.JavassistGenerator;
import org.repastbs.generated.GisModelProp;
import org.repastbs.generated.ModelProp;

@SuppressWarnings("serial")
public class GisModel extends AbstractModel {
	
	private GisModelProp gisModelProp;

	/**
	 * @throws DynamicException
	 */
	public GisModel() throws DynamicException {
		this("Model name");
	}

	
	/**
	 * @param modelName
	 * @throws DynamicException
	 */
	public GisModel(String modelName) throws DynamicException {
		super("Gis model");
		setDescription("Gis is geographic information system type model");
		setGenerator(new JavassistGenerator(new DefaultModel()));
	}

	/**
	 * @see org.repastbs.component.Component#createNew()
	 */
	@Override
	public void createNew() throws Exception {
		super.createNew("GisModel","Gis model");
		gisModelProp = new GisModelProp();
		gisModelProp.setModelClass(this.getClass().getName());
	}

	/**
	 * @see org.repastbs.model.Model#getModelProp()
	 */
	@Override
	public ModelProp getModelProp() {
		return gisModelProp;
	}

	/**
	 * @see org.repastbs.model.Model#setModelProp(org.repastbs.generated.ModelProp)
	 */
	@Override
	public void setModelProp(ModelProp modelProp) throws Exception {
	}	
}
