package test2.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LoginView {

	private Shell loginShell;
	private Composite loginPanel;
	private Text fTextUsername;
	private Text fTextPassword;
	private Button fButtonOK;
	private Button fButtonCancel;

	private boolean fAuthenticated = false;

	private int screenHeight;
	private int screenWeith;

	private Rectangle loginPanelBounds;

	public LoginView(final Shell shell) {
		this.loginShell = shell;
		screenHeight = shell.getDisplay().getBounds().height;
		screenWeith = shell.getDisplay().getBounds().width;
	}

	public void open() {
		createUI();
		addButtonEvent();
		loginShell.layout(true);
		System.out.println("open login view!");
		doEventLoop();
	}

	private void doEventLoop() {
		// loginShell.pack();
		loginShell.open();
		System.out.println("display size: " + loginShell.getDisplay().getBounds().toString());
		System.out.println("panel bounds: " + loginPanelBounds.toString());
		while (fAuthenticated == false) {
			if (loginShell.getDisplay().readAndDispatch() == false) {
				loginShell.getDisplay().sleep();
			}
		}
		loginShell.dispose();
	}

	private void addButtonEvent() {
		fButtonOK.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("say yes!");
				fAuthenticated = true;
			}
		});

		fButtonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("say no!");
				loginShell.getDisplay().close();
				System.exit(0);
			}
		});

		loginShell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!fAuthenticated) {
					loginShell.getDisplay().close();
					System.exit(0);
				}

			}
		});
	}

	private void createUI() {
		setLoginShell();
		// Create the login panel
		createUILoginPanel();
	}

	private void setLoginShell() {
		FillLayout layout = new FillLayout();
		loginShell.setLayout(layout);
		loginShell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		loginShell.setBounds((int) (screenWeith * 0.4), (int) (screenHeight * 0.3), (int) (screenWeith * 0.2),
				(int) (screenHeight * 0.4));
	}

	private void createUILoginPanel() {
		loginPanel = new Composite(loginShell, SWT.NONE);
		GridLayout loginPanelLayout = new GridLayout(1, false);
		loginPanel.setLayout(loginPanelLayout);
		loginPanelLayout.horizontalSpacing = 0;
		loginPanelLayout.verticalSpacing = 0;
		loginPanelLayout.marginLeft = 0;
		loginPanelLayout.marginRight = 0;
		loginPanelLayout.marginWidth = 0;
		loginPanel.setBounds(loginShell.getBounds());
		loginPanelBounds = loginPanel.getBounds();
		loginPanel.setBackground(new Color(loginShell.getDisplay(), 0, 0, 0));

		createUICompositeBanner();
		createUILoginContent();
		createUIButton();
	}

	private void createUICompositeBanner() {
		Composite banner = new Composite(loginPanel, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		banner.setSize(loginPanelBounds.width, (int) (loginPanelBounds.height * 0.5));
		System.out.println("loginPanelBounds: " + loginPanelBounds.toString());
		data.minimumHeight = (int) (loginPanelBounds.height * 0.5);
		banner.setLayoutData(data);
		System.out.println("banner: " + banner.getBounds().toString());

		GridLayout gridLayout = new GridLayout(2, true);
		banner.setLayout(gridLayout);

		Label label = new Label(banner, SWT.CENTER);
		GridData data2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		data2.horizontalSpan = 2;
		label.setLayoutData(data2);
		label.setText("登陆");
	}

	private void createUILoginContent() {
		Composite content = new Composite(loginPanel, SWT.NONE);
		content.setBackground(new Color(loginShell.getDisplay(), 10, 200, 30));
		content.setSize(loginPanelBounds.width, (int) (loginPanelBounds.height * 0.3));
		GridData contentLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		// contentLayoutData.minimumHeight = (int) (loginPanelBounds.height *
		// 0.5);
		contentLayoutData.verticalIndent = 0;
		contentLayoutData.horizontalIndent = 0;
		content.setLayoutData(contentLayoutData);

		GridLayout gridLayout = new GridLayout(4, false);
		content.setLayout(gridLayout);
		System.out.println("content: " + content.getBounds().toString());

		createUIBlank(content, 0.1);
		createUILabelUserName(content, 0.4);
		createUITextUserName(content, 0.3);
		createUIBlank(content, 0.1);

		createUIBlank(content, 0.1);
		createUILabelPassword(content, 0.4);
		createUITextPassword(content, 0.4);
		createUIBlank(content, 0.1);

	}

	private void createUIBlank(Composite parent, double widthPre) {
		Composite blank = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		// data.minimumWidth = (int) (parent.getBounds().width * widthPre);
		blank.setLayoutData(data);
		blank.setBackground(new Color(loginShell.getDisplay(), 10, 100, 130));
	}

	private void createUILabelUserName(Composite parent, double widthPre) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = (int) (parent.getBounds().width * widthPre);

		Label label = new Label(composite, SWT.CENTER);
		label.setAlignment(SWT.CENTER);
		label.setText("User name:"); //$NON-NLS-1$
		RowData rowData = new RowData((int) (data.minimumWidth * 0.8), 30);
		label.setLayoutData(rowData);
		label.setBackground(new Color(loginShell.getDisplay(), 220, 220, 220));

		// Configure layout data
		composite.setLayoutData(data);
		composite.setBackground(new Color(loginShell.getDisplay(), 106, 90, 205));
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.center = true;
		layout.wrap = false;
		composite.setLayout(layout);
	}

	private void createUITextUserName(Composite parent, double widthPre) {
		// Create the text widget
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = (int) (parent.getBounds().width * widthPre);
		composite.setLayoutData(data);
		fTextUsername = new Text(composite, SWT.BORDER | SWT.LEAD);
		RowData rowData = new RowData();
		rowData.width = data.minimumWidth;
		fTextUsername.setLayoutData(rowData);
		composite.setLayout(new RowLayout());
		composite.setBackground(new Color(loginShell.getDisplay(), 255, 255, 240));
	}

	private void createUILabelPassword(Composite parent, double widthPre) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = (int) (parent.getBounds().width * widthPre);
		Label label = new Label(composite, SWT.CENTER);
		label.setText("&Password:"); //$NON-NLS-1$
		// Configure layout data
		composite.setLayoutData(data);
		composite.setBackground(new Color(loginShell.getDisplay(), 0, 229, 238));
	}

	private void createUITextPassword(Composite parent, double widthPre) {
		// Create the text widget
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = (int) (parent.getBounds().width * widthPre);
		composite.setLayoutData(data);
		fTextPassword = new Text(composite, SWT.BORDER | SWT.LEAD);
		composite.setBackground(new Color(loginShell.getDisplay(), 255, 218, 185));
	}

	private void createUIButton() {
		Composite buttonContent = new Composite(loginPanel, SWT.NONE);
		buttonContent.setBackground(new Color(loginShell.getDisplay(), 150, 20, 40));
		// buttonContent.setSize(loginPanelBounds.width, (int)
		// (loginPanelBounds.height * 0.2));
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.minimumHeight = (int) (loginPanelBounds.height * 0.2);
		buttonContent.setLayoutData(gridData);

		GridLayout gridLayout = new GridLayout(2, true);
		buttonContent.setLayout(gridLayout);
		createUIButtonOK(buttonContent);
		createUIButtonCancel(buttonContent);
		System.out.println("buttonContent: " + buttonContent.getBounds().toString());
	}

	private void createUIButtonOK(Composite parent) {
		// Create the button
		fButtonOK = new Button(parent, SWT.PUSH | SWT.CENTER);
		fButtonOK.setText("登陆"); //$NON-NLS-1$
		// Configure layout data
		GridData data = new GridData(SWT.NONE, SWT.NONE, true, false);
		// data.widthHint = F_BUTTON_WIDTH_HINT;
		// data.verticalIndent = 10;
		fButtonOK.setLayoutData(data);
	}

	private void createUIButtonCancel(Composite parent) {
		// Create the button
		fButtonCancel = new Button(parent, SWT.PUSH | SWT.CENTER);
		fButtonCancel.setText("取消"); //$NON-NLS-1$
		// Configure layout data
		GridData data = new GridData(SWT.NONE, SWT.NONE, true, false);
		// data.widthHint = F_BUTTON_WIDTH_HINT;
		// data.verticalIndent = 10;
		fButtonCancel.setLayoutData(data);
	}
}
