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
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.QuadraticCurve;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class QuadraticCurvesIntersection extends AbstractIntersectionExample {
	public static void main(String[] args) {
		new QuadraticCurvesIntersection("Quadratic Bezier Curve/Curve Intersection");
	}

	public QuadraticCurvesIntersection(String title) {
		super(title);
	}

	@Override
	protected Point[] computeIntersections(IGeometry g1, IGeometry g2) {
		return ((QuadraticCurve) g1).getIntersections((QuadraticCurve) g2);
	}

	private AbstractControllableShape createControllableQuadraticBezierCurveShape(Canvas canvas, Point... points) {
		final Point start = points[0];
		final Point ctrl = points[1];
		final Point end = points[2];

		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				addControlPoint(start);
				addControlPoint(ctrl);
				addControlPoint(end);
			}

			@Override
			public IGeometry createGeometry() {
				return new QuadraticCurve(getControlPoints());
			}

			@Override
			public void drawShape(GC gc) {
				QuadraticCurve curve = (QuadraticCurve) createGeometry();

				gc.drawPath(new org.eclipse.swt.graphics.Path(Display.getCurrent(),
						Geometry2SWT.toSWTPathData(curve.toPath())));
			}
		};
	}

	@Override
	protected AbstractControllableShape createControllableShape1(Canvas canvas) {
		return createControllableQuadraticBezierCurveShape(canvas, new Point(100, 100), new Point(300, 150),
				new Point(400, 400));
	}

	@Override
	protected AbstractControllableShape createControllableShape2(Canvas canvas) {
		return createControllableQuadraticBezierCurveShape(canvas, new Point(400, 100), new Point(310, 290),
				new Point(100, 400));
	}
}
