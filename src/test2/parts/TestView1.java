package test2.parts;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TestView1 {

	private Job job = null;

	@Inject
	public TestView1(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText("TestView1");
		System.out.println("TestView1");
	}

	@PostConstruct
	@Optional
	public void createContent(Composite parent, MWindow window, final UISynchronize ui) {
		Label label = new Label(parent, SWT.NONE);
		label.setText("lable!");
		if (window != null) {
			label.setText(window.getLabel());
		}
		System.out.println("currenttime: " + getTime());
		job = new Job("time") {

			private boolean isCancel = true;

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// String time = getTime();
				// while (isCancel) {
				// if (!getTime().equals(time)) {
				// time = getTime();
				// ui.asyncExec(() -> {
				// label.setText("currenttime: " + getTime());
				// });
				//
				// } else {
				// try {
				// Thread.sleep(500);
				// } catch (InterruptedException e) {
				// isCancel = false;
				// }
				// }
				// }

				monitor.beginTask("progress", 5);
				try {
					for (int i = 0; i < 5; i++) {
						Thread.sleep(1000);
						monitor.worked(1);
					}
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				} finally {
					monitor.done();
				}
				System.out.println("job done!");
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	private String getTime() {
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("HH:mm:ss");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate;
	}

	@Inject
	@Optional
	public void test6(@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
		if (selection != null) {
			System.out.println("test6 " + selection.toString());
		}
		System.out.println("test6");
	}

	@Focus
	public void setFocus() {
		System.out.println("TestView1 setFocus");
		new Exception().printStackTrace();
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("TestView1 preDestroy!");
		if (job != null) {
			System.out.println("job.cancel");
			job.cancel();
		}
	}

}
