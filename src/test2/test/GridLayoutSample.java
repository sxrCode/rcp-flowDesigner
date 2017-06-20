package test2.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class GridLayoutSample {

	private Shell shell;

	public void create() {
		GridLayout gridLayout = new GridLayout(1, true);
		shell.setLayout(gridLayout);
		shell.setBounds(new Rectangle(200, 200, 500, 500));

		Button button1 = new Button(shell, SWT.PUSH);
		button1.setText("button1");
		button1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		List list = new List(shell, SWT.BORDER);
		list.add("item 1");
		list.add("item 2");
		list.add("item 3");
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Button button2 = new Button(shell, SWT.PUSH);
		button2.setText("button #2");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalIndent = 5;
		gridData.minimumHeight = 200;
		button2.setLayoutData(gridData);

		Button button3 = new Button(shell, SWT.PUSH);
		button3.setText("3");
		button3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}

	public GridLayoutSample(Shell shell) {
		this.shell = shell;
	}
}
