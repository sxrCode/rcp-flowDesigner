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
package test2.example.gef4.geometry;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef4.geometry.planar.IGeometry;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;

abstract public class ControllableShape {

	private boolean active;
	public int shapeColor;
	public int controlColor;
	public double controlRadius;
	public List<ControlPoint> controlPoints;

	public ControllableShape() {
		controlPoints = new ArrayList<>();
		shapeColor = SWT.COLOR_BLACK;
		controlColor = SWT.COLOR_BLUE;
		controlRadius = 5;
		active = true;
	}

	public void activate() {
		active = true;
	}

	public void addControlPoints(Point... points) {
		for (Point p : points) {
			controlPoints.add(new ControlPoint(p));
		}
	}

	public void deactivate() {
		active = false;
	}

	public Point[] getPoints() {
		Point[] points = new Point[controlPoints.size()];
		for (int i = 0; i < controlPoints.size(); i++) {
			points[i] = controlPoints.get(i).toPoint();
		}
		return points;
	}

	abstract public IGeometry getShape();

	public boolean isActive() {
		return active;
	}

	abstract public void onDraw(GC gc);

	public void onMove(int dragPointIndex, double oldX, double oldY) {
	}

	public void onResize(Rectangle bounds) {
		for (ControlPoint cp : controlPoints) {
			cp.onResize(bounds);
		}
	}

}
