package test2.test;

public class CusExecuter {

	CusCommand cusCommand;
	private static String cusMessage;

	public CusExecuter(CusCommand command) {
		this.cusCommand = command;
	}

	public void start() {
		this.cusCommand.run(cusMessage + " ");
	}

	public static void consume(String message) {
		cusMessage = message;
	}

	public static String setName() {
		return "dog";
	}
}
