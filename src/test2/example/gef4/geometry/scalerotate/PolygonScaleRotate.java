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
package test2.example.gef4.geometry.scalerotate;

import org.eclipse.gef4.geometry.convert.swt.Geometry2SWT;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class PolygonScaleRotate extends AbstractScaleRotateExample {

	public static void main(String[] args) {
		new PolygonScaleRotate();
	}

	public PolygonScaleRotate() {
		super("Scale/Rotate - Polygon");
	}

	@Override
	protected AbstractScaleRotateShape createShape(Canvas canvas) {
		return new AbstractScaleRotateShape(canvas) {
			@Override
			public boolean contains(Point p) {
				return createGeometry().contains(p);
			}

			@Override
			public Polygon createGeometry() {
				double w = getCanvas().getClientArea().width;
				double h = getCanvas().getClientArea().height;
				double w2 = w / 2;
				double h2 = h / 2;
				double w5 = w / 5;
				double h5 = h / 5;

				Polygon me = new Polygon(new Point(w2 - w5, h2 - h5), new Point(w2 + w5, h2 - h5),
						new Point(w2 + w5, h2 + h5), new Point(w2 - w5, h2 + h5));

				me.rotateCW(getRotationAngle(), getCenter());
				me.scale(getZoomFactor(), getCenter());

				return me;
			}

			@Override
			public void draw(GC gc) {
				Polygon me = createGeometry();
				gc.setLineWidth(2);
				gc.drawPolygon(Geometry2SWT.toSWTPointArray(me));
				gc.fillPolygon(Geometry2SWT.toSWTPointArray(me));
			}
		};
	}

}
