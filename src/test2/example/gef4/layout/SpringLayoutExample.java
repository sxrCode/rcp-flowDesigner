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
 * Note: Parts of this class have been transferred from org.eclipse.gef4.zest.examples.layout.RadialLayoutExample
 *
 *******************************************************************************/
package test2.example.gef4.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef4.graph.Edge;
import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.graph.Node;
import org.eclipse.gef4.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef4.zest.fx.ZestProperties;

import javafx.application.Application;
import test2.example.gef4.zest.AbstractZestExample;

public class SpringLayoutExample extends AbstractZestExample {

	public static void main(String[] args) {
		Application.launch(args);
	}

	public SpringLayoutExample() {
		super("GEF4 Layouts - Spring Layout Example");
	}

	@Override
	protected Graph createGraph() {
		// create nodes
		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		Node root = makeNode(LABEL, "Root");
		nodes.add(root);
		for (int i = 0; i < 3; i++) {
			Node n = makeNode(LABEL, "1 - " + i);
			nodes.add(n);
			for (int j = 0; j < 3; j++) {
				// make these nodes differ via their ids (as the labels are
				// identical)
				Node n2 = makeNode(ID, i + "-" + j, LABEL, "2 - " + j);
				nodes.add(n2);
				Edge e = makeEdge(n, n2);
				edges.add(e);
			}
			edges.add(makeEdge(root, n));
		}
		return new Graph.Builder().nodes(nodes.toArray(new Node[] {})).edges(edges.toArray(new Edge[] {}))
				.attr(ZestProperties.LAYOUT_ALGORITHM__G, new SpringLayoutAlgorithm()).build();

	}

}
