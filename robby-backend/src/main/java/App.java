import java.io.IOException;

public class App {
	public static void main(String[] args) {
		Configuration configuration;
		try {
			 configuration = Configuration.load("config.json");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.out.println("Loading default configuration.");
			configuration = Configuration.getDefault();
		}

		System.out.println(configuration);
		Runner runner = new Runner(configuration);
		try {
			runner.runCivilization();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			runner.shutdown();
		}
	}
}
