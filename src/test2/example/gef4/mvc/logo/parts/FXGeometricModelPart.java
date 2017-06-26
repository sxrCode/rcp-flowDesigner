/*******************************************************************************
 * Copyright (c) 2014, 2016 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *     Matthias Wienand (itemis AG) - contributions for Bugzillas #450285 & #487070
 *
 *******************************************************************************/
package test2.example.gef4.mvc.logo.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef4.mvc.fx.parts.AbstractFXContentPart;
import org.eclipse.gef4.mvc.models.GridModel;
import org.eclipse.gef4.mvc.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import test2.example.gef4.mvc.logo.model.AbstractFXGeometricElement;
import test2.example.gef4.mvc.logo.model.FXGeometricModel;

public class FXGeometricModelPart extends AbstractFXContentPart<Group> {

	private final ChangeListener<? super Boolean> snapToGridObserver = new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			applySnapToGrid(newValue);
		}
	};

	@Override
	protected void addChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		getVisual().getChildren().add(index, child.getVisual());
	}

	protected void applySnapToGrid(boolean snapToGrid) {
		getViewer().getAdapter(GridModel.class).setSnapToGrid(snapToGrid);
	}

	@Override
	protected Group createVisual() {
		Group visual = new Group();
		visual.setAutoSizeChildren(false);
		return visual;
	}

	@Override
	protected void doActivate() {
		super.doActivate();
		// register snap-to-grid property listener
		getContent().snapToGridProperty().addListener(snapToGridObserver);
	}

	@Override
	protected void doAddContentChild(Object contentChild, int index) {
		if (!(contentChild instanceof AbstractFXGeometricElement)) {
			throw new IllegalArgumentException("Cannot add content child: wrong type!");
		}
		getContent().getShapeVisuals().add(index, (AbstractFXGeometricElement<?>) contentChild);
	}

	@Override
	protected void doDeactivate() {
		// unregister snap-to-grid property listener
		getContent().snapToGridProperty().removeListener(snapToGridObserver);
		super.doDeactivate();
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		List<Object> objs = new ArrayList<>();
		objs.addAll(getContent().getShapeVisuals());
		return objs;
	}

	@Override
	protected void doRefreshVisual(Group visual) {
		// apply snap-to-grid from model
		applySnapToGrid(getContent().isSnapToGrid());
	}

	@Override
	protected void doRemoveContentChild(Object contentChild) {
		getContent().getShapeVisuals().remove(contentChild);
	}

	@Override
	public FXGeometricModel getContent() {
		return (FXGeometricModel) super.getContent();
	}

	@Override
	public boolean isFocusable() {
		return false;
	}

	@Override
	protected void removeChildVisual(IVisualPart<Node, ? extends Node> child, int index) {
		getVisual().getChildren().remove(child.getVisual());
	}

}
