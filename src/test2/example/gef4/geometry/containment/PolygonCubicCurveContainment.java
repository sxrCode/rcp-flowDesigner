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
import org.eclipse.gef4.geometry.planar.CubicCurve;
import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class PolygonCubicCurveContainment extends AbstractPolygonContainmentExample {

	public static void main(String[] args) {
		new PolygonCubicCurveContainment("Polygon / Cubic Curve - Containment");
	}

	public PolygonCubicCurveContainment(String title) {
		super(title);
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
				addControlPoint(new Point(200, 100));
				addControlPoint(new Point(190, 310));
				addControlPoint(new Point(410, 90));
				addControlPoint(new Point(400, 300));
			}

			@Override
			public CubicCurve createGeometry() {
				return new CubicCurve(getControlPoints());
			}

			@Override
			public void drawShape(GC gc) {
				CubicCurve c = createGeometry();
				gc.drawPath(new org.eclipse.swt.graphics.Path(Display.getCurrent(),
						Geometry2SWT.toSWTPathData(c.toPath())));
			}

			@Override
			public void fillShape(GC gc) {
				int lineWidth = gc.getLineWidth();
				Color fg = gc.getForeground();

				gc.setLineWidth(3);
				gc.setForeground(gc.getBackground());
				drawShape(gc);

				gc.setForeground(fg);
				gc.setLineWidth(lineWidth);
			}
		};
	}

}
