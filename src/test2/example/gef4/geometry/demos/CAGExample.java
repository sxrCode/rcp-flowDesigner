/*******************************************************************************
 * Copyright (c) 2012, 2015 itemis AG and others.
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
package test2.example.gef4.geometry.demos;

import org.eclipse.gef4.geometry.convert.swt.Geometry2SWT;
import org.eclipse.gef4.geometry.planar.Ellipse;
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.geometry.planar.Path;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import test2.example.gef4.geometry.AbstractExample;
import test2.example.gef4.geometry.ControlPoint;
import test2.example.gef4.geometry.ControllableShape;

public class CAGExample extends AbstractExample {

	public static void main(String[] args) {
		new CAGExample("CAG Example", new String[] {});
	}

	private ControllableShape csTriangle, csEllipse;

	public CAGExample(String title, String[] infos) {
		super(title, infos);
	}

	@Override
	protected ControllableShape[] getControllableShapes() {
		return new ControllableShape[] { csTriangle = new ControllableShape() {
			{
				addControlPoints(new Point(100, 150), new Point(350, 120), new Point(200, 300));
			}

			@Override
			public Polygon getShape() {
				return new Polygon(getPoints());
			}

			@Override
			public void onDraw(GC gc) {
				// we draw them later
			}
		}, csEllipse = new ControllableShape() {
			{
				addControlPoints(new Point(280, 230), new Point(380, 280));
			}

			@Override
			public Ellipse getShape() {
				Point[] points = getPoints();
				double a = Math.abs(points[0].x - points[1].x);
				double b = Math.abs(points[0].y - points[1].y);
				return new Ellipse(points[0].x - a, points[0].y - b, 2 * a, 2 * b);
			}

			@Override
			public void onDraw(GC gc) {
				// we draw them later
			}

			@Override
			public void onMove(int dragPointIndex, double oldX, double oldY) {
				if (dragPointIndex == 0) {
					double dx = controlPoints.get(0).getX() - oldX;
					double dy = controlPoints.get(0).getY() - oldY;
					ControlPoint cp = controlPoints.get(1);
					cp.setX(cp.getX() + dx);
					cp.setY(cp.getY() + dy);
				}
			}
		}, new ControllableShape() {
			@Override
			public IGeometry getShape() {
				return null; // does not control a geometry
			}

			private org.eclipse.swt.graphics.Path makeSWTPath(Path p) {
				return new org.eclipse.swt.graphics.Path(Display.getCurrent(), Geometry2SWT.toSWTPathData(p));
			}

			@Override
			public void onDraw(GC gc) {
				Path trianglePath = csTriangle.getShape().toPath();
				Path ellipsePath = csEllipse.getShape().toPath();
				Path intersection = Path.intersect(trianglePath, ellipsePath);

				gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
				gc.fillPath(makeSWTPath(trianglePath));

				gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
				gc.fillPath(makeSWTPath(ellipsePath));

				gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
				gc.fillPath(makeSWTPath(intersection));
			}
		} };
	}
}
