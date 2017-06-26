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
package test2.example.gef4.mvc.logo.policies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.mvc.fx.domain.FXDomain;
import org.eclipse.gef4.mvc.fx.policies.AbstractFXInteractionPolicy;
import org.eclipse.gef4.mvc.fx.policies.IFXOnDragPolicy;
import org.eclipse.gef4.mvc.fx.tools.FXClickDragTool;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.SelectionModel;
import org.eclipse.gef4.mvc.operations.DeselectOperation;
import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IRootPart;
import org.eclipse.gef4.mvc.parts.IVisualPart;
import org.eclipse.gef4.mvc.policies.CreationPolicy;
import org.eclipse.gef4.mvc.viewer.IViewer;

import com.google.common.collect.HashMultimap;
import com.google.common.reflect.TypeToken;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import test2.example.gef4.mvc.logo.model.FXGeometricShape;
import test2.example.gef4.mvc.logo.parts.FXGeometricModelPart;
import test2.example.gef4.mvc.logo.parts.FXGeometricShapePart;
import test2.example.gef4.mvc.logo.parts.PaletteElementPart;

public class CreateAndTranslateOnDragPolicy extends AbstractFXInteractionPolicy implements IFXOnDragPolicy {

	private FXGeometricShapePart createdShapePart;
	private Map<AdapterKey<? extends IFXOnDragPolicy>, IFXOnDragPolicy> dragPolicies;

	@Override
	public void drag(MouseEvent event, Dimension delta) {
		if (createdShapePart == null) {
			return;
		}

		// forward drag events to bend target part
		if (dragPolicies != null) {
			for (IFXOnDragPolicy dragPolicy : dragPolicies.values()) {
				dragPolicy.drag(event, delta);
			}
		}
	}

	@Override
	public void dragAborted() {
		if (createdShapePart == null) {
			return;
		}

		// forward event to bend target part
		if (dragPolicies != null) {
			for (IFXOnDragPolicy dragPolicy : dragPolicies.values()) {
				dragPolicy.dragAborted();
			}
		}

		createdShapePart = null;
		dragPolicies = null;
	}

	protected FXViewer getContentViewer() {
		Map<AdapterKey<? extends IViewer<Node>>, IViewer<Node>> viewers = getHost().getRoot().getViewer().getDomain()
				.getViewers();
		for (Entry<AdapterKey<? extends IViewer<Node>>, IViewer<Node>> e : viewers.entrySet()) {
			if (FXDomain.CONTENT_VIEWER_ROLE.equals(e.getKey().getRole())) {
				return (FXViewer) e.getValue();
			}
		}
		throw new IllegalStateException("Cannot find content viewer!");
	}

	@Override
	public PaletteElementPart getHost() {
		return (PaletteElementPart) super.getHost();
	}

	protected Point getLocation(MouseEvent e) {
		Point2D location = ((FXViewer) getHost().getRoot().getViewer()).getCanvas().getContentGroup()
				.sceneToLocal(e.getSceneX(), e.getSceneY());
		return new Point(location.getX(), location.getY());
	}

	@Override
	public void hideIndicationCursor() {
	}

	@SuppressWarnings("serial")
	@Override
	public void press(MouseEvent event) {
		// find model part
		IRootPart<Node, ? extends Node> contentRoot = getContentViewer().getRootPart();
		IVisualPart<Node, ? extends Node> modelPart = contentRoot.getChildrenUnmodifiable().get(0);
		if (!(modelPart instanceof FXGeometricModelPart)) {
			throw new IllegalStateException("Cannot find FXGeometricModelPart.");
		}

		// copy the prototype
		FXGeometricShape copy = getHost().getContent().getCopy();
		// determine coordinates of prototype's origin in model coordinates
		Point2D localToScene = getHost().getVisual().localToScene(0, 0);
		Point2D originInModel = modelPart.getVisual().sceneToLocal(localToScene.getX(), localToScene.getY());
		// initially move to the originInModel
		double[] matrix = copy.getTransform().getMatrix();
		copy.getTransform().setTransform(matrix[0], matrix[1], matrix[2], matrix[3], originInModel.getX(),
				originInModel.getY());

		// create copy of host's geometry using CreationPolicy from root part
		CreationPolicy<Node> creationPolicy = contentRoot.getAdapter(new TypeToken<CreationPolicy<Node>>() {
		});
		init(creationPolicy);
		createdShapePart = (FXGeometricShapePart) creationPolicy.create(copy, (FXGeometricModelPart) modelPart,
				HashMultimap.<IContentPart<Node, ? extends Node>, String>create());
		commit(creationPolicy);

		// disable refresh visuals for the created shape part
		storeAndDisableRefreshVisuals(createdShapePart);

		// build operation to deselect all but the new part
		List<IContentPart<Node, ? extends Node>> toBeDeselected = new ArrayList<>(
				getHost().getRoot().getViewer().getAdapter(new TypeToken<SelectionModel<Node>>() {
				}).getSelectionUnmodifiable());
		toBeDeselected.remove(createdShapePart);
		DeselectOperation<Node> deselectOperation = new DeselectOperation<>(getHost().getRoot().getViewer(),
				toBeDeselected);

		// execute on stack
		try {
			getHost().getRoot().getViewer().getDomain().execute(deselectOperation, new NullProgressMonitor());
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}

		// find drag target part
		dragPolicies = createdShapePart.getAdapters(FXClickDragTool.ON_DRAG_POLICY_KEY);
		if (dragPolicies != null) {
			for (IFXOnDragPolicy dragPolicy : dragPolicies.values()) {
				dragPolicy.press(event);
			}
		}
	}

	@Override
	public void release(MouseEvent e, Dimension delta) {
		if (createdShapePart == null) {
			return;
		}

		// forward event to bend target part
		if (dragPolicies != null) {
			for (IFXOnDragPolicy dragPolicy : dragPolicies.values()) {
				dragPolicy.release(e, delta);
			}
		}

		restoreRefreshVisuals(createdShapePart);
		createdShapePart = null;
		dragPolicies = null;
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		return false;
	}

}
