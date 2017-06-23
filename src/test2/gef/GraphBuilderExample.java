package test2.gef;

import java.util.Arrays;
import java.util.List;

import test2.test.CusExecuter;

public class GraphBuilderExample {

	public static void main(String[] args) {

		List<String> strings = Arrays.asList("1", "2", "3", "4");
		strings.forEach(CusExecuter::consume);

		new CusExecuter((mess) -> {
			System.out.println(mess);
			return mess;
		}).start();

	}
}
