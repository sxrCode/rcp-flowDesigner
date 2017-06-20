package test2.handlers;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

public class Cus1Handler {

	@CanExecute
	public boolean canExecute() {
		System.out.println("Cus1Handler canExecute?");
		return true;
	}

	@Execute
	public void execute() {
		System.out.println("Cus1Handler execute.");
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("Cus1Handler postConstruct.");
	}
}
