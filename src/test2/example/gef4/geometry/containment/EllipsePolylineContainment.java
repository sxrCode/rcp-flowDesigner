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

import org.eclipse.gef4.geometry.convert.swt.Geometry2SWT;
import org.eclipse.gef4.geometry.planar.Ellipse;
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polyline;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class EllipsePolylineContainment extends AbstractEllipseContainmentExample {

	public static void main(String[] args) {
		new EllipsePolylineContainment();
	}

	public EllipsePolylineContainment() {
		super("Ellipse/Polyline Containment");
	}

	@Override
	protected boolean computeContains(IGeometry g1, IGeometry g2) {
		return ((Ellipse) g1).contains(g2);
	}

	@Override
	protected boolean computeIntersects(IGeometry g1, IGeometry g2) {
		return ((Ellipse) g1).touches(g2);
	}

	@Override
	protected AbstractControllableShape createControllableShape2(Canvas canvas) {
		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				addControlPoint(new Point(100, 100));
				addControlPoint(new Point(500, 400));
				addControlPoint(new Point(100, 400));
				addControlPoint(new Point(500, 100));
			}

			@Override
			public Polyline createGeometry() {
				Point[] points = getControlPoints();
				Polyline polyline = new Polyline(points);
				return polyline;
			}

			@Override
			public void drawShape(GC gc) {
				Polyline polyline = createGeometry();
				gc.drawPolyline(Geometry2SWT.toSWTPointArray(polyline));
			}

			@Override
			public void fillShape(GC gc) {
				int lineWidth = gc.getLineWidth();
				Color fg = gc.getForeground();

				gc.setLineWidth(3);
				gc.setForeground(gc.getBackground());
				drawShape(gc);

				gc.setLineWidth(lineWidth);
				gc.setForeground(fg);
			}
		};
	}
}
