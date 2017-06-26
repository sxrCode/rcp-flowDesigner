/*******************************************************************************
 * Copyright (c) 2012 itemis AG and others.
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

import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Rectangle;

public class ControlPoint {

	private Point p;
	private Rectangle bounds; // remember bounds

	public ControlPoint() {
		p = new Point();
	}

	public ControlPoint(double x, double y) {
		p = new Point(x, y);
	}

	public ControlPoint(Point p) {
		this.p = p.getCopy();
	}

	private void boundsCheck() {
		if (bounds != null) {
			if (p.x < 0) {
				p.x = 0;
			} else if (p.x > bounds.getWidth()) {
				p.x = bounds.getWidth();
			}
			if (p.y < 0) {
				p.y = 0;
			} else if (p.y > bounds.getHeight()) {
				p.y = bounds.getHeight();
			}
		}
	}

	public double getX() {
		return p.x;
	}

	public double getY() {
		return p.y;
	}

	public void onResize(Rectangle bounds) {
		this.bounds = bounds;
		boundsCheck();
	}

	public void setX(double x) {
		p.x = x;
		boundsCheck();
	}

	public void setY(double y) {
		p.y = y;
		boundsCheck();
	}

	public Point toPoint() {
		return p.getCopy();
	}

}
