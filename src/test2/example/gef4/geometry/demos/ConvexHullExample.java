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
package test2.example.gef4.geometry.demos;

import org.eclipse.gef4.geometry.convert.swt.Geometry2SWT;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import test2.example.gef4.geometry.AbstractExample;
import test2.example.gef4.geometry.ControllableShape;

public class ConvexHullExample extends AbstractExample {

	public static void main(String[] args) {
		new ConvexHullExample("Convex Hull Example");
	}

	public ConvexHullExample(String title) {
		super(title); // creates the UI for us
	}

	@Override
	protected ControllableShape[] getControllableShapes() {
		return new ControllableShape[] { new ControllableShape() {
			{
				// These are the points which are displayed on the screen.
				// We will compute their convex hull later.
				addControlPoints(new Point(100, 100), new Point(150, 400), new Point(200, 300), new Point(250, 150),
						new Point(300, 250), new Point(350, 200), new Point(400, 350));
			}

			@Override
			public Polygon getShape() {
				// Compute the convex hull of the defined point list.
				// We return the convex hull as a Polygon.
				return new Polygon(Point.getConvexHull(getPoints()));
			}

			@Override
			public void onDraw(GC gc) {
				// This is the code to display the computed convex hull.

				// Compute the convex hull.
				Polygon convexHull = getShape();

				// Display the convex hull as an SWT Path.
				gc.drawPath(new org.eclipse.swt.graphics.Path(Display.getCurrent(),
						Geometry2SWT.toSWTPathData(convexHull.toPath())));
			}
		} };
	}
}
