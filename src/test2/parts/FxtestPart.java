package test2.parts;

import javax.annotation.PostConstruct;

import org.eclipse.gef4.fx.swt.canvas.FXCanvasEx;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import javafx.scene.Scene;
import test2.example.gef4.mvc.logo.MvcLogoExample;

public class FxtestPart {

	@PostConstruct
	public void createContent(Composite parent) {
		System.out.println("FxtestPart createContent!");
		FXCanvasEx fxCanvasEx = new FXCanvasEx(parent, SWT.NONE);
		fxCanvasEx.setScene(createScene());
	}

	private static Scene createScene() {

		return new MvcLogoExample().provideScene();
	}
}
