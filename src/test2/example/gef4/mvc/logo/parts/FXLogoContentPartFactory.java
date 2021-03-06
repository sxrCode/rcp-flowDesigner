/*******************************************************************************
 * Copyright (c) 2014, 2015 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package test2.example.gef4.mvc.logo.parts;

import java.util.Map;

import org.eclipse.gef4.mvc.behaviors.IBehavior;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IContentPartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

import javafx.scene.Node;
import test2.example.gef4.mvc.logo.model.FXGeometricCurve;
import test2.example.gef4.mvc.logo.model.FXGeometricModel;
import test2.example.gef4.mvc.logo.model.FXGeometricShape;

public class FXLogoContentPartFactory implements IContentPartFactory<Node> {

	@Inject
	private Injector injector;

	@Override
	public IContentPart<Node, ? extends Node> createContentPart(Object content, IBehavior<Node> contextBehavior,
			Map<Object, Object> contextMap) {

		if (content instanceof FXGeometricModel) {
			return injector.getInstance(FXGeometricModelPart.class);
		} else if (content instanceof FXGeometricShape) {
			return injector.getInstance(FXGeometricShapePart.class);
		} else if (content instanceof FXGeometricCurve) {
			return injector.getInstance(FXGeometricCurvePart.class);
		} else {
			throw new IllegalArgumentException(content.getClass().toString());
		}
	};

}
