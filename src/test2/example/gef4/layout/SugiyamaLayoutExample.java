/*******************************************************************************
 * Copyright (c) 2014, 2016 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API & implementation
 *
 *  Note: Parts of this class have been transferred from org.eclipse.gef4.zest.examples.layout.SugiyamaLayoutExample
 *
 *******************************************************************************/
package test2.example.gef4.layout;

import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.layout.algorithms.SugiyamaLayoutAlgorithm;
import org.eclipse.gef4.zest.fx.ZestProperties;

import javafx.application.Application;
import test2.example.gef4.zest.AbstractZestExample;

public class SugiyamaLayoutExample extends AbstractZestExample {

	public static void main(String[] args) {
		Application.launch(args);
	}

	public SugiyamaLayoutExample() {
		super("GEF4 Layouts - Sugiyama Layout Example");
	}

	@Override
	protected Graph createGraph() {
		// create nodes
		org.eclipse.gef4.graph.Node[] nodes = new org.eclipse.gef4.graph.Node[] { makeNode(LABEL, "Coal"), makeNode(LABEL, "Ore"),
				makeNode(LABEL, "Stone"), makeNode(LABEL, "Metal"), makeNode(LABEL, "Concrete"), makeNode(LABEL, "Machine"), makeNode(LABEL, "Building") };

		// create edges
		org.eclipse.gef4.graph.Edge[] edges = new org.eclipse.gef4.graph.Edge[] {
				makeEdge(nodes[0], nodes[3]) /* coal -> metal */,
				makeEdge(nodes[0], nodes[4]) /* coal -> concrete */,
				makeEdge(nodes[3], nodes[5]) /* metal -> machine */,
				makeEdge(nodes[3], nodes[6]) /* metal -> building */,
				makeEdge(nodes[4], nodes[6]) /* concrete -> building */,
				makeEdge(nodes[1], nodes[3]) /* ore -> metal */,
				makeEdge(nodes[2], nodes[4]) /* stone -> concrete */ };

		return new Graph.Builder().nodes(nodes).edges(edges)
				.attr(ZestProperties.LAYOUT_ALGORITHM__G, new SugiyamaLayoutAlgorithm()).build();
	}

}
