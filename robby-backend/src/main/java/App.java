import org.apache.commons.cli.*;

import java.io.IOException;

public class App {
	public static void main(String[] args) {

		final Options options = new Options();
		options.addOption(new Option("d", "debug", false, "Turn on debug."));
		options.addOption(new Option("h", "help", false, "Print this message."));
		options.addOption(new Option("c", "config", true, "Configuration JSON file."));
		options.addOption(new Option("o", "output", true, "Output directory."));

		CommandLineParser parser = new DefaultParser();
		CommandLine line;
		try {
			line = parser.parse(options, args);
		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
			System.exit(1);
			return;
		}

		Configuration configuration;
		try {
			if (line.hasOption("c")) {
				String configFileName = line.getOptionValue("c");
				configuration = Configuration.load(configFileName);
			} else {
				configuration = Configuration.getDefault();
				System.out.println("Option -c was not specified. Loading default configuration.");
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(2);
			return;
		}

		if (line.hasOption("d")) configuration.setDebug(true);

		String outDir;
		if (!line.hasOption("o")) {
			System.out.println("Option -o was not specified. Output directory must be specified.");
			System.exit(3);
			return;
		} else {
			outDir = line.getOptionValue("o");
		}

		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("ant", options);
		}

		//Actual main call
		main(configuration, outDir);
	}

	private static void main(Configuration configuration, String outDir) {
		System.out.println(configuration);
		try {
			configuration.save(outDir + "/config.json");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.exit(10);
		}

		Runner runner = new Runner(configuration);
		try (ResultWriter writer = new ResultWriter(outDir)) {

			runner.runCivilization(writer);

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.exit(11);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.exit(12);
		} finally {
			runner.shutdown();
		}
	}
}
