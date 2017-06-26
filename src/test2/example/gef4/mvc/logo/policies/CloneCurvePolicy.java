/*******************************************************************************
 * Copyright (c) 2015 itemis AG and others.
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

import org.eclipse.gef4.geometry.planar.ICurve;
import org.eclipse.gef4.geometry.planar.Point;

import test2.example.gef4.mvc.logo.model.AbstractFXGeometricElement;
import test2.example.gef4.mvc.logo.model.FXGeometricCurve;
import test2.example.gef4.mvc.logo.parts.FXGeometricCurvePart;

public class CloneCurvePolicy extends AbstractCloneContentPolicy {

	@Override
	public Object cloneContent() {
		FXGeometricCurve original = getHost().getContent();
		FXGeometricCurve clone = new FXGeometricCurve(original.getWayPointsCopy().toArray(new Point[] {}),
				original.getStroke(), original.getStrokeWidth(), original.getDashes(), original.getEffect());
		clone.setGeometry((ICurve) original.getGeometry().getCopy());
		clone.setSourceDecoration(original.getSourceDecoration());
		clone.setTargetDecoration(original.getTargetDecoration());
		clone.setTransform(original.getTransform());

		// anchorages
		for (AbstractFXGeometricElement<?> srcAnchorage : original.getSourceAnchorages()) {
			clone.addSourceAnchorage(srcAnchorage);
		}
		for (AbstractFXGeometricElement<?> dstAnchorage : original.getTargetAnchorages()) {
			clone.addTargetAnchorage(dstAnchorage);
		}

		return clone;
	}

	@Override
	public FXGeometricCurvePart getHost() {
		return (FXGeometricCurvePart) super.getHost();
	}

}
