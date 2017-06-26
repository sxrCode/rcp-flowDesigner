/*******************************************************************************
 * Copyright (c) 2011, 2015 itemis AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 *     
 *******************************************************************************/
package test2.example.gef4.geometry.intersection;

import org.eclipse.gef4.geometry.convert.swt.Geometry2SWT;
import org.eclipse.gef4.geometry.planar.Ellipse;
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.geometry.planar.Line;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.gef4.geometry.planar.QuadraticCurve;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

/**
 * Simple example demonstrating the intersection of an {@link Ellipse} and a
 * {@link Line}.
 * 
 * @author Matthias Wienand (matthias.wienand@itemis.de)
 * 
 */
public class PolygonQuadraticCurveIntersection extends AbstractPolygonIntersectionExample {

	public static void main(String[] args) {
		new PolygonQuadraticCurveIntersection();
	}

	public PolygonQuadraticCurveIntersection() {
		super("Polygon/QuadraticCurve Intersection");
	}

	@Override
	protected Point[] computeIntersections(IGeometry g1, IGeometry g2) {
		return ((Polygon) g1).getOutline().getIntersections((QuadraticCurve) g2);
	}

	@Override
	protected AbstractControllableShape createControllableShape2(Canvas canvas) {
		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				addControlPoint(new Point(100, 100));
				addControlPoint(new Point(300, 100));
				addControlPoint(new Point(300, 300));
			}

			@Override
			public QuadraticCurve createGeometry() {
				return new QuadraticCurve(getControlPoints());
			}

			@Override
			public void drawShape(GC gc) {
				QuadraticCurve c = createGeometry();
				gc.drawPath(new org.eclipse.swt.graphics.Path(Display.getCurrent(),
						Geometry2SWT.toSWTPathData(c.toPath())));
			}
		};
	}
}
