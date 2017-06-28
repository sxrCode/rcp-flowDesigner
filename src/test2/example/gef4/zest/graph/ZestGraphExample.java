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
 *******************************************************************************/
package test2.example.gef4.zest.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.gef4.graph.Edge;
import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef4.zest.fx.ZestProperties;

import com.google.inject.Module;

import javafx.application.Application;
import test2.example.gef4.zest.AbstractZestExample;

public class ZestGraphExample extends AbstractZestExample {

	private static Graph buildAC(String id) {
		// create nodes "A" to "C"
		List<org.eclipse.gef4.graph.Node> nodes = new ArrayList<>();
		nodes.addAll(Arrays.asList(
				makeNode(ZestProperties.LABEL__NE, "A", ZestProperties.TOOLTIP__N, "Alpha", ZestProperties.CSS_ID__NE,
						id + "A"),
				makeNode(ZestProperties.LABEL__NE, "B", ZestProperties.TOOLTIP__N, "Beta", ZestProperties.CSS_ID__NE,
						id + "B"),
				makeNode(ZestProperties.LABEL__NE, "C", ZestProperties.TOOLTIP__N, "Gamma", ZestProperties.CSS_ID__NE,
						id + "C")));

		// create some edges between those nodes
		List<Edge> edges = new ArrayList<>();
		edges.addAll(Arrays.asList(makeEdge(nodes.get(0), nodes.get(1)), makeEdge(nodes.get(1), nodes.get(2)),
				makeEdge(nodes.get(2), nodes.get(0))));

		// directed connections
		HashMap<String, Object> attrs = new HashMap<>();
		attrs.put(ZestProperties.LAYOUT_ALGORITHM__G, new SpringLayoutAlgorithm());
		return new Graph(attrs, nodes, edges);
	}

	private static Graph buildAE(String id) {
		// create nodes "A" to "C"
		List<org.eclipse.gef4.graph.Node> nodes = new ArrayList<>();
		nodes.addAll(Arrays.asList(
				makeNode(ZestProperties.LABEL__NE, "A", ZestProperties.TOOLTIP__N, "Alpha", ZestProperties.CSS_ID__NE,
						id + "A"),
				makeNode(ZestProperties.LABEL__NE, "B", ZestProperties.TOOLTIP__N, "Beta", ZestProperties.CSS_ID__NE,
						id + "B"),
				makeNode(ZestProperties.LABEL__NE, "C", ZestProperties.TOOLTIP__N, "Gamma", ZestProperties.CSS_ID__NE,
						id + "C"),
				makeNode(ZestProperties.LABEL__NE, "D", ZestProperties.TOOLTIP__N, "Delta", ZestProperties.CSS_ID__NE,
						id + "D"),
				makeNode(ZestProperties.LABEL__NE, "E", ZestProperties.TOOLTIP__N, "Epsilon", ZestProperties.CSS_ID__NE,
						id + "E")));

		// add nested graphs
		nodes.get(4).setNestedGraph(buildAC("c"));

		// create some edges between those nodes
		List<Edge> edges = new ArrayList<>();
		edges.addAll(Arrays.asList(makeEdge(nodes.get(0), nodes.get(1)), makeEdge(nodes.get(1), nodes.get(2)),
				makeEdge(nodes.get(2), nodes.get(3)), makeEdge(nodes.get(3), nodes.get(4)), makeEdge(nodes.get(4), nodes.get(0))));

		// directed connections
		HashMap<String, Object> attrs = new HashMap<>();
		attrs.put(ZestProperties.LAYOUT_ALGORITHM__G, new SpringLayoutAlgorithm());
		return new Graph(attrs, nodes, edges);
	}

	public static Graph createDefaultGraph() {
		// create nodes "0" to "9"
		List<org.eclipse.gef4.graph.Node> nodes = new ArrayList<>();
		nodes.addAll(Arrays.asList(makeNode(ZestProperties.LABEL__NE, "0", ZestProperties.TOOLTIP__N, "zero"),
				makeNode(ZestProperties.LABEL__NE, "1", ZestProperties.TOOLTIP__N, "one"),
				makeNode(ZestProperties.LABEL__NE, "2", ZestProperties.TOOLTIP__N, "two"),
				makeNode(ZestProperties.LABEL__NE, "3", ZestProperties.TOOLTIP__N, "three"),
				makeNode(ZestProperties.LABEL__NE, "4", ZestProperties.TOOLTIP__N, "four"),
				makeNode(ZestProperties.LABEL__NE, "5", ZestProperties.TOOLTIP__N, "five"),
				makeNode(ZestProperties.LABEL__NE, "6", ZestProperties.TOOLTIP__N, "six"),
				makeNode(ZestProperties.LABEL__NE, "7", ZestProperties.TOOLTIP__N, "seven"),
				makeNode(ZestProperties.LABEL__NE, "8", ZestProperties.TOOLTIP__N, "eight"),
				makeNode(ZestProperties.LABEL__NE, "9", ZestProperties.TOOLTIP__N, "nine")));

		// set nested graphs
		nodes.get(0).setNestedGraph(buildAC("a"));
		nodes.get(5).setNestedGraph(buildAE("b"));

		// create some edges between those nodes
		List<Edge> edges = new ArrayList<>();
		edges.addAll(Arrays.asList(makeEdge(nodes.get(0), nodes.get(9)), makeEdge(nodes.get(1), nodes.get(8)),
				makeEdge(nodes.get(2), nodes.get(7)), makeEdge(nodes.get(3), nodes.get(6)), makeEdge(nodes.get(4), nodes.get(5)),
				makeEdge(nodes.get(0), nodes.get(4)), makeEdge(nodes.get(1), nodes.get(6)), makeEdge(nodes.get(2), nodes.get(8)),
				makeEdge(nodes.get(3), nodes.get(5)), makeEdge(nodes.get(4), nodes.get(7)), makeEdge(nodes.get(5), nodes.get(1))));

		// directed connections
		HashMap<String, Object> attrs = new HashMap<>();
		attrs.put(ZestProperties.LAYOUT_ALGORITHM__G, new SpringLayoutAlgorithm());
		return new Graph(attrs, nodes, edges);

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public ZestGraphExample() {
		super("GEF4 Zest Graph Example");
	}

	@Override
	protected Graph createGraph() {
		return createDefaultGraph();
	}

	@Override
	protected Module createModule() {
		return new ZestGraphExampleModule();
	}

}
