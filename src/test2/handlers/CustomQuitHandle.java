package test2.handlers;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.swt.widgets.Shell;

public class CustomQuitHandle {

	@Execute
	public void executeCus(IWorkbench workbench, Shell shell) {
		System.out.println("CustomQuitHandle");
	}

	@PostConstruct
	public void createContent() {
		System.out.println("CustomQuitHandle createContent!");
	}
}
