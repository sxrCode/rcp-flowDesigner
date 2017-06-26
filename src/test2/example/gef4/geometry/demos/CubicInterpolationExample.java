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
import org.eclipse.gef4.geometry.planar.PolyBezier;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Slider;

import test2.example.gef4.geometry.AbstractExample;
import test2.example.gef4.geometry.ControllableShape;

public class CubicInterpolationExample extends AbstractExample {

	public static void main(String[] args) {
		new CubicInterpolationExample("Cubic Interpolation Example");
	}

	private double curveWidthCoefficient;

	public CubicInterpolationExample(String title) {
		super(title); // Creates the UI for us.
	}

	@Override
	protected ControllableShape[] getControllableShapes() {
		return new ControllableShape[] { new ControllableShape() {
			{
				/*
				 * These are the anchor points for the cubic Bezier
				 * interpolation.
				 */
				addControlPoints(new Point(100, 200), new Point(150, 250), new Point(200, 150), new Point(250, 250),
						new Point(300, 150), new Point(350, 250), new Point(400, 200));
			}

			@Override
			public PolyBezier getShape() {
				/*
				 * Constructs the cubic Bezier interpolation through the defined
				 * anchor points as a PolyBezier.
				 */
				return PolyBezier.interpolateCubic(curveWidthCoefficient, getPoints());
			}

			@Override
			public void onDraw(GC gc) {
				/*
				 * Displays the cubic Bezier interpolation.
				 */

				// compute the interpolation
				PolyBezier curve = getShape();

				// display it as an SWT Path
				gc.drawPath(new org.eclipse.swt.graphics.Path(Display.getCurrent(),
						Geometry2SWT.toSWTPathData(curve.toPath())));
			}
		} };
	}

	@Override
	public void onInit() {
		/*
		 * Creates the slider to be able to change the curve width coefficient
		 * used to construct the cubic Bezier interpolation through the later-on
		 * defined anchor points. (Just SWT buzz.)
		 */
		curveWidthCoefficient = 1;

		// add slider
		final Slider slider = new Slider(shell, SWT.HORIZONTAL);
		slider.setBounds(0, 0, 200, 20);
		slider.setValues(25, 0, 101, 1, 1, 1);
		slider.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				curveWidthCoefficient = slider.getSelection() / 25d;
				shell.redraw();
			}
		});
	}

}
