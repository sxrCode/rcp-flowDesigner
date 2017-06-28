package test2.parts;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import test2.view.LoginView;

public class SamplePart {

	private Text txtInput;
	private TableViewer tableViewer;

	@Inject
	private ESelectionService selectionService;

	@Inject
	private MDirtyable dirty;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		txtInput = new Text(parent, SWT.BORDER);
		txtInput.setMessage("Enter text to mark part as dirty");
		txtInput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dirty.setDirty(true);
			}
		});
		txtInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		tableViewer = new TableViewer(parent);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		;
		tableViewer.setInput(createInitialDataModel());
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		tableViewer.addSelectionChangedListener(event -> {
			selectionService.setSelection(event.getSelection());
		});
	}

	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	@Inject
	public void test5(Shell sysShell, Display display) {
		System.out.println("loginView open!");
		Shell shell = new Shell(display, SWT.BORDER | SWT.APPLICATION_MODAL);
		LoginView loginView = new LoginView(shell);
		loginView.open();
		sysShell.setMaximized(true);
	}

	@Persist
	public void save() {
		dirty.setDirty(false);
	}

	private List<String> createInitialDataModel() {
		return Arrays.asList("Sample item 1", "Sample item 2", "Sample item 3", "Sample item 4", "Sample item 5");
	}
}