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
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class EllipseRectangleIntersection extends AbstractEllipseIntersectionExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new EllipseRectangleIntersection();
	}

	public EllipseRectangleIntersection() {
		super("Ellipse/Rectangle Intersection");
	}

	@Override
	protected Point[] computeIntersections(IGeometry g1, IGeometry g2) {
		return ((Ellipse) g1).getOutline().getIntersections(((Rectangle) g2).getOutline());
	}

	@Override
	protected AbstractControllableShape createControllableShape2(Canvas canvas) {
		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				addControlPoint(new Point(100, 150));
				addControlPoint(new Point(550, 300));
			}

			@Override
			public Rectangle createGeometry() {
				Point[] points = getControlPoints();
				return new Rectangle(points[0], points[1]);
			}

			@Override
			public void drawShape(GC gc) {
				Rectangle rect = createGeometry();
				gc.drawRectangle(Geometry2SWT.toSWTRectangle(rect));
			}
		};
	}
}
