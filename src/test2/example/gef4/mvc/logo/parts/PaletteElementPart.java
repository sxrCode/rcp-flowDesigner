/*******************************************************************************
 * Copyright (c) 2016 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package test2.example.gef4.mvc.logo.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef4.common.collections.CollectionUtils;
import org.eclipse.gef4.fx.nodes.GeometryNode;
import org.eclipse.gef4.geometry.planar.IShape;
import org.eclipse.gef4.mvc.fx.parts.AbstractFXContentPart;

import com.google.common.collect.SetMultimap;

import test2.example.gef4.mvc.logo.model.FXGeometricShape;

public class PaletteElementPart extends AbstractFXContentPart<GeometryNode<IShape>> {

	@Override
	protected GeometryNode<IShape> createVisual() {
		return new GeometryNode<>();
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return CollectionUtils.emptySetMultimap();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	protected void doRefreshVisual(GeometryNode<IShape> visual) {
		visual.setGeometry(getContent().getGeometry());
		visual.setStroke(getContent().getStroke());
		visual.setStrokeWidth(getContent().getStrokeWidth());
		visual.setFill(getContent().getFill());
		visual.setEffect(getContent().getEffect());
	}

	@Override
	public FXGeometricShape getContent() {
		return (FXGeometricShape) super.getContent();
	}

}
