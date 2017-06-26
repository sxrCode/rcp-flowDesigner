/*******************************************************************************
 * Copyright (c) 2015, 2016 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API & implementation
 *
 * Note: Parts of this class have been transferred from org.eclipse.gef4.zest.examples.layout.FilterGraphSnippet
 *
 *******************************************************************************/
package test2.example.gef4.layout;

import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.graph.Node;
import org.eclipse.gef4.layout.algorithms.TreeLayoutAlgorithm;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.zest.fx.ZestProperties;

import javafx.application.Application;
import javafx.scene.Scene;
import test2.example.gef4.zest.basic.AbstractZestExample;

public class FilterLayoutExample extends AbstractZestExample {

	public static void main(String[] args) {
		Application.launch(args);
	}

	public FilterLayoutExample() {
		super("GEF4 Layouts - Filter Layout Example");
	}

	@Override
	protected Graph createGraph() {
		Graph graph = new Graph();

		Node a = n(graph, LABEL, "Root");
		Node b = n(graph, LABEL, "B");
		Node c = n(graph, LABEL, "C");
		Node d = n(graph, LABEL, "D");
		Node e = n(graph, LABEL, "E");
		Node f = n(graph, LABEL, "F");
		Node g = n(graph, LABEL, "G");
		Node h = n(graph, LABEL, "H");

		e(graph, a, b, LABEL, "", LAYOUT_IRRELEVANT, Boolean.TRUE, CSS_CLASS, "red");
		e(graph, a, c, LABEL, "", LAYOUT_IRRELEVANT, Boolean.TRUE, CSS_CLASS, "red");
		e(graph, a, d, LABEL, "", LAYOUT_IRRELEVANT, Boolean.TRUE, CSS_CLASS, "red");

		e(graph, b, e, LABEL, "");
		e(graph, b, f, LABEL, "");
		e(graph, c, g, LABEL, "");
		e(graph, d, h, LABEL, "");

		e(graph, b, c, LABEL, "", LAYOUT_IRRELEVANT, Boolean.TRUE, CSS_CLASS, "red");
		e(graph, c, d, LABEL, "", LAYOUT_IRRELEVANT, Boolean.TRUE, CSS_CLASS, "red");
		e(graph, e, f, LABEL, "", LAYOUT_IRRELEVANT, Boolean.TRUE, CSS_CLASS, "red");
		e(graph, f, g, LABEL, "", LAYOUT_IRRELEVANT, Boolean.TRUE, CSS_CLASS, "red");
		e(graph, h, e, LABEL, "", LAYOUT_IRRELEVANT, Boolean.TRUE, CSS_CLASS, "red");

		graph.attributesProperty().put(ZestProperties.LAYOUT_ALGORITHM__G,
				new TreeLayoutAlgorithm(TreeLayoutAlgorithm.TOP_DOWN));

		return graph;
	}

	@Override
	protected Scene createScene(FXViewer viewer) {
		Scene scene = super.createScene(viewer);
		scene.getStylesheets().add(getClass().getResource("FilterGraphExample.css").toExternalForm());
		return scene;
	}

	@Override
	protected int getStageHeight() {
		return 400;
	}

	@Override
	protected int getStageWidth() {
		return 400;
	}

}
