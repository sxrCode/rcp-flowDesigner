
/*******************************************************************************
 * Copyright (c) 2015 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 *
 *******************************************************************************/

package test2.example.gef4.mvc.logo.policies;

import org.eclipse.gef4.mvc.parts.IContentPart;
import org.eclipse.gef4.mvc.parts.IRootPart;

import javafx.scene.Node;

public interface IFXCreationMenuItem {

	public Object createContent();

	public Node createVisual();

	public IContentPart<Node, ? extends Node> findContentParent(IRootPart<Node, ? extends Node> rootPart);

}
