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
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import test2.example.gef4.geometry.AbstractExample;
import test2.example.gef4.geometry.ControllableShape;

public class TriangulationExample extends AbstractExample {
	public static void main(String[] args) {
		new TriangulationExample("Triangulation Example");
	}

	public TriangulationExample(String title) {
		super(title);
	}

	@Override
	protected ControllableShape[] getControllableShapes() {
		return new ControllableShape[] { new ControllableShape() {
			{
				addControlPoints(new Point(300 / 2, 100 / 2), new Point(100 / 2, 200 / 2), new Point(200 / 2, 300 / 2),
						new Point(100 / 2, 500 / 2), new Point(300 / 2, 400 / 2), new Point(500 / 2, 600 / 2),
						new Point(600 / 2, 300 / 2), new Point(500 / 2, 400 / 2), new Point(500 / 2, 200 / 2),
						new Point(300 / 2, 200 / 2));
			}

			@Override
			public Polygon getShape() {
				Polygon p = new Polygon(getPoints());
				return p;
			}

			@Override
			public void onDraw(GC gc) {
				Polygon p = getShape();

				// System.out.println("p = " + p);

				gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));

				Polygon[] triangulation;
				try {
					triangulation = p.getTriangulation();
				} catch (IllegalStateException x) {
					triangulation = new Polygon[] { p };
				}
				for (Polygon triangle : triangulation) {
					gc.drawPolygon(Geometry2SWT.toSWTPointArray(triangle));
				}

				int lineWidth = gc.getLineWidth();
				gc.setLineWidth(lineWidth + 2);
				gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));

				gc.drawPolygon(Geometry2SWT.toSWTPointArray(p));

				gc.setLineWidth(lineWidth);
			}
		} };
	}

}
