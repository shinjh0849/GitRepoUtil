package GitRepoUtil;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
	CLIOptions arguments = null;
	boolean is_help = false;
	public static boolean is_time = false;

	public static void main(String[] args) throws Exception {
		Main cvc = new Main();
		cvc.run(args);
	}

	public void run(String[] args) throws Exception {
		Options options = createOptions();

		if (parseOptions(options, args)) {
			if(is_time) {
				DevTime dt = new DevTime(arguments);
				System.out.println(dt.getDevTime());
				
				return;
			}
		}
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		String in;
		String out;
		String url;

		try {
			CommandLine cmd = parser.parse(options, args);
			try {
				if (cmd.hasOption("t"))
					is_time = true;

				in = cmd.getOptionValue("i");
				out = cmd.getOptionValue("o");
				url = cmd.getOptionValue("u");

			} catch (Exception e) {
				System.out.println(e.getMessage());
				printHelp(options);
				return false;
			}

			arguments = new CLIOptions(url, in, out);
		} catch (Exception e) {
			e.printStackTrace();
			printHelp(options);
			return false;
		}
		return true;
	}

	private Options createOptions() {
		Options options = new Options();

		options.addOption(Option.builder("t").longOpt("dev time").desc("get the time of development of a repo").build());

		options.addOption(
				Option.builder("u").longOpt("url").desc("url of the git repo").hasArg().argName("git_url").build());

		options.addOption(Option.builder("i").longOpt("input").desc("directory of the input file to parse").hasArg()
				.argName("input_path").required().build());

		options.addOption(Option.builder("o").longOpt("output").desc("directory will have result file").hasArg()
				.argName("output_path").required().build());

		options.addOption(Option.builder("h").longOpt("help").desc("Help").build());

		return options;
	}

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "Various Utils for git repo";
		String footer = "\nPlease report issues Jiho";
		formatter.printHelp("Git Repo Utils", header, options, footer, true);
	}
}
