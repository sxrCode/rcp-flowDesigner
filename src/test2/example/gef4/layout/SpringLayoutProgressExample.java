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
 * Note: Parts of this class have been transferred from org.eclipse.gef4.zest.examples.layout.SpringLayoutProgress
 *
 *******************************************************************************/
package test2.example.gef4.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.graph.Edge;
import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.graph.Node;
import org.eclipse.gef4.layout.LayoutContext;
import org.eclipse.gef4.layout.LayoutProperties;
import org.eclipse.gef4.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.zest.fx.ZestProperties;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import test2.example.gef4.zest.AbstractZestExample;

public class SpringLayoutProgressExample extends AbstractZestExample {

	/**
	 * The ManualSpringLayoutAlgorithm does not perform full layout passes, so
	 * that we can use the {@link #performNIteration(int)} method to gradually
	 * apply the layout.
	 */
	public static class ManualSpringLayoutAlgorithm extends SpringLayoutAlgorithm {
		@Override
		public void applyLayout(boolean clean) {
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public SpringLayoutProgressExample() {
		super("GEF4 Layouts - Spring Layout Progress Example");
	}

	@Override
	protected Graph createGraph() {
		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		Node root = makeNode(LABEL, "Root");
		nodes.add(root);

		Node aa = makeNode(LABEL, "A");
		Node bb = makeNode(LABEL, "B");
		Node cc = makeNode(LABEL, "C");
		nodes.addAll(Arrays.asList(aa, bb, cc));

		Node dd = makeNode(LABEL, "D");
		Node ee = makeNode(LABEL, "E");
		Node ff = makeNode(LABEL, "F");
		nodes.addAll(Arrays.asList(dd, ee, ff));

		edges.addAll(Arrays.asList(makeEdge(root, aa, LABEL, ""), makeEdge(root, bb, LABEL, ""), makeEdge(root, cc, LABEL, "")));

		edges.addAll(Arrays.asList(makeEdge(aa, bb, LABEL, ""), makeEdge(bb, cc, LABEL, ""), makeEdge(cc, aa, LABEL, ""),
				makeEdge(aa, dd, LABEL, ""), makeEdge(bb, ee, LABEL, ""), makeEdge(cc, ff, LABEL, ""), makeEdge(cc, dd, LABEL, ""),
				makeEdge(dd, ee, LABEL, ""), makeEdge(ee, ff, LABEL, "")));

		Node[] mix = new Node[3];
		mix[0] = aa;
		mix[1] = bb;
		mix[2] = cc;

		for (int k = 0; k < 1; k++) {
			for (int i = 0; i < 8; i++) {
				Node n = makeNode(LABEL, "1 - " + i);
				nodes.add(n);
				for (int j = 0; j < 5; j++) {
					Node n2 = makeNode(LABEL, "2 - " + j);
					nodes.add(n2);
					Edge e = makeEdge(n, n2, LABEL, "", "weight", "-1");
					edges.addAll(Arrays.asList(e, makeEdge(mix[j % 3], n2, LABEL, "")));
				}
				edges.add(makeEdge(root, n, LABEL, ""));
			}
		}

		for (Node n : nodes) {
			n.attributesProperty().put(LayoutProperties.LOCATION_PROPERTY, new Point(200, 200));
		}

		return new Graph.Builder().nodes(nodes.toArray(new Node[] {})).edges(edges.toArray(new Edge[] {}))
				.attr(ZestProperties.LAYOUT_ALGORITHM__G, new SpringLayoutAlgorithm()).build();
	}

	@Override
	protected Scene createScene(final FXViewer viewer) {
		Scene scene = super.createScene(viewer);
		Group overlay = viewer.getCanvas().getOverlayGroup();
		final ToggleButton button = new ToggleButton("step");
		final ManualSpringLayoutAlgorithm[] layoutAlgorithm = new ManualSpringLayoutAlgorithm[1];
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (layoutAlgorithm[0] == null) {
					layoutAlgorithm[0] = new ManualSpringLayoutAlgorithm();
					layoutAlgorithm[0].setRandom(false);
					ZestProperties.setLayoutAlgorithm(graph, layoutAlgorithm[0]);
				} else {
					viewer.getContentPartMap().get(graph).getAdapter(LayoutContext.class).applyLayout(true);
				}
			}
		});
		overlay.getChildren().add(button);
		new AnimationTimer() {
			private long last = 0;
			private final long NANOS_PER_MILLI = 1000000;
			private final long NANOS_PER_ITERATION = 10 * NANOS_PER_MILLI;

			@Override
			public void handle(long now) {
				if (button.isSelected()) {
					long elapsed = now - last;
					if (elapsed > NANOS_PER_ITERATION) {
						int n = (int) (elapsed / NANOS_PER_ITERATION);
						layoutAlgorithm[0].performNIteration(n);
						last = now;
					}
				} else {
					last = now;
				}
			}
		}.start();
		return scene;
	}

}
