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
package test2.example.gef4.geometry.containment;

import org.eclipse.gef4.geometry.planar.Ellipse;
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class PolygonEllipseContainment extends AbstractPolygonContainmentExample {

	public static void main(String[] args) {
		new PolygonEllipseContainment();
	}

	public PolygonEllipseContainment() {
		super("Polygon/Ellipse Containment");
	}

	@Override
	protected boolean computeContains(IGeometry g1, IGeometry g2) {
		return ((Polygon) g1).contains(g2);
	}

	@Override
	protected boolean computeIntersects(IGeometry g1, IGeometry g2) {
		return ((Polygon) g1).touches(g2);
	}

	@Override
	protected AbstractControllableShape createControllableShape2(Canvas canvas) {
		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				ControlPoint center = addControlPoint(new Point(300, 300));
				ControlPoint a = addControlPoint(new Point(400, 300));
				ControlPoint b = addControlPoint(new Point(300, 200));
				a.setYLink(center);
				b.setXLink(center);
			}

			@Override
			public Ellipse createGeometry() {
				Point[] points = getControlPoints();
				double a = Math.abs(points[0].x - points[1].x);
				double b = Math.abs(points[0].y - points[2].y);
				return new Ellipse(points[0].x - a, points[0].y - b, 2 * a, 2 * b);
			}

			@Override
			public void drawShape(GC gc) {
				Ellipse ellipse = createGeometry();
				gc.drawOval((int) ellipse.getX(), (int) ellipse.getY(), (int) ellipse.getWidth(),
						(int) ellipse.getHeight());
			}

			@Override
			public void fillShape(GC gc) {
				Ellipse ellipse = createGeometry();
				gc.fillOval((int) ellipse.getX(), (int) ellipse.getY(), (int) ellipse.getWidth(),
						(int) ellipse.getHeight());
			}
		};
	}
}
