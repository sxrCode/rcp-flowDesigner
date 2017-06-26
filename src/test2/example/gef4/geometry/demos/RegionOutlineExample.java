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
import org.eclipse.gef4.geometry.planar.Line;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Rectangle;
import org.eclipse.gef4.geometry.planar.Region;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import test2.example.gef4.geometry.AbstractExample;
import test2.example.gef4.geometry.ControllableShape;

public class RegionOutlineExample extends AbstractExample {
	public static void main(String[] args) {
		new RegionOutlineExample("Region Outline Example");
	}

	public RegionOutlineExample(String title) {
		super(title);
	}

	@Override
	protected ControllableShape[] getControllableShapes() {
		return new ControllableShape[] { new ControllableShape() {
			{
				addControlPoints(new Point(100, 50), new Point(300, 100));
				addControlPoints(new Point(250, 200), new Point(350, 330));
				addControlPoints(new Point(100, 200), new Point(190, 325));
				addControlPoints(new Point(150, 300), new Point(280, 380));
			}

			@Override
			public Region getShape() {
				Point[] cp = getPoints();

				Rectangle[] rectangles = new Rectangle[cp.length / 2];
				for (int i = 0; i < rectangles.length; i++) {
					rectangles[i] = new Rectangle(cp[2 * i], cp[2 * i + 1]);
				}

				return new Region(rectangles);
			}

			@Override
			public void onDraw(GC gc) {
				Region region = getShape();

				gc.setAlpha(128);
				gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
				for (Rectangle r : region.getShapes()) {
					gc.fillRectangle(Geometry2SWT.toSWTRectangle(r));
				}

				gc.setAlpha(255);
				// gc.setForeground(Display.getCurrent().getSystemColor(
				// SWT.COLOR_RED));
				// for (Rectangle r : region.getShapes()) {
				// gc.drawRectangle(r.toSWTRectangle());
				// }
				gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
				for (Line l : region.getOutlineSegments()) {
					gc.drawLine((int) (l.getX1()), (int) (l.getY1()), (int) (l.getX2()), (int) (l.getY2()));
				}
			}
		} };
	}

}
