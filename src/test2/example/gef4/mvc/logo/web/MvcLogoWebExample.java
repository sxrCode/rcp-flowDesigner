/*******************************************************************************
 * Copyright (c) 2015, 2016 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package test2.example.gef4.mvc.logo.web;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.mvc.models.ContentModel;

import com.google.inject.Module;

import javafx.application.Application;
import javafx.scene.Scene;
import test2.example.gef4.mvc.AbstractMvcExample;
import test2.example.gef4.mvc.logo.MvcLogoExampleModule;
import test2.example.gef4.mvc.logo.MvcLogoExampleViewersComposite;
import test2.example.gef4.mvc.logo.model.FXGeometricModel;
import test2.example.gef4.mvc.logo.model.PaletteModel;

// TODO: Better extend MvcLogoExample (same code).
public class MvcLogoWebExample extends AbstractMvcExample {

	public static List<FXGeometricModel> createDefaultContents() {
		return Collections.singletonList(new FXGeometricModel());
	}

	public static List<PaletteModel> createPaletteContents() {
		return Collections.singletonList(new PaletteModel());
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public MvcLogoWebExample() {
		super("GEF4 MVC Logo Example (Web)");
	}

	@Override
	protected Module createModule() {
		return new MvcLogoExampleModule();
	}

	protected FXViewer getPaletteViewer() {
		return getDomain().getAdapter(AdapterKey.get(FXViewer.class, MvcLogoExampleModule.PALETTE_VIEWER_ROLE));
	}

	@Override
	protected void hookViewers() {
		getPrimaryStage().setScene(
				new Scene(new MvcLogoExampleViewersComposite(getContentViewer(), getPaletteViewer()).getComposite()));
	}

	@Override
	protected void populateViewerContents() {
		getContentViewer().getAdapter(ContentModel.class).getContents().setAll(createDefaultContents());
		getPaletteViewer().getAdapter(ContentModel.class).getContents().setAll(createPaletteContents());
	}

}
