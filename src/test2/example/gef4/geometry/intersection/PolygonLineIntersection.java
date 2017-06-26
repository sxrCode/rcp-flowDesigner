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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

/**
 * Simple example demonstrating the intersection of an {@link Ellipse} and a
 * {@link Line}.
 * 
 * @author Matthias Wienand (matthias.wienand@itemis.de)
 * 
 */
public class PolygonLineIntersection extends AbstractPolygonIntersectionExample {

	public static void main(String[] args) {
		new PolygonLineIntersection();
	}

	public PolygonLineIntersection() {
		super("Polygon/Line Intersection");
	}

	@Override
	protected Point[] computeIntersections(IGeometry g1, IGeometry g2) {
		return ((Polygon) g1).getOutline().getIntersections((Line) g2);
	}

	@Override
	protected AbstractControllableShape createControllableShape2(Canvas canvas) {
		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				addControlPoint(new Point(100, 100));
				addControlPoint(new Point(300, 300));
			}

			@Override
			public Line createGeometry() {
				Point[] points = getControlPoints();
				return new Line(points[0], points[1]);
			}

			@Override
			public void drawShape(GC gc) {
				Line line = createGeometry();
				gc.drawPolyline(Geometry2SWT.toSWTPointArray(line));
			}
		};
	}
}
