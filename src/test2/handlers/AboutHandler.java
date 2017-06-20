package test2.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class AboutHandler {
	@Execute
	public void execute(Shell shell, EModelService modelService, MApplication application) {
		if (modelService != null) {
			MWindow existingWindow = (MWindow) modelService.find("test2.window.main", application);
			MessageDialog.openInformation(shell, "About", "existing " + existingWindow.getLabel());
		} else {
			MessageDialog.openInformation(shell, "About", "Eclipse 4 RCP Application");
		}
	}

	@CanExecute
	public boolean canExecute() {
		System.out.println("AboutHandler canExecute?");
		return true;
	}
}
