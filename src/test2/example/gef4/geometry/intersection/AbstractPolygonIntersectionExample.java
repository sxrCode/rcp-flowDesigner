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

import org.eclipse.gef4.geometry.planar.Line;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

public abstract class AbstractPolygonIntersectionExample extends AbstractIntersectionExample {

	public AbstractPolygonIntersectionExample(String title) {
		super(title);
	}

	@Override
	protected AbstractControllableShape createControllableShape1(Canvas canvas) {
		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				// no control points => user cannot change it
			}

			@Override
			public Polygon createGeometry() {
				Rectangle ca = getCanvas().getClientArea();
				double w = ca.width;
				double wg = w / 6;
				double h = ca.height;
				double hg = h / 6;

				return new Polygon(new Point[] { new Point(wg, hg), new Point(w - wg, h - hg), new Point(wg, h - hg),
						new Point(w - wg, hg) });
			}

			@Override
			public void drawShape(GC gc) {
				Polygon polygon = createGeometry();
				for (Line segment : polygon.getOutlineSegments()) {
					gc.drawLine((int) segment.getX1(), (int) segment.getY1(), (int) segment.getX2(),
							(int) segment.getY2());
				}
			}
		};
	}
}