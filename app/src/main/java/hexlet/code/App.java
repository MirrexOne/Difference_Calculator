package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App {

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    private String format = "stylish";

    @Parameters(index = "0", paramLabel = "filepath1", description = "Path to first file")
    private File file1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "Path to second file")
    private File file2;

    public static void main(String[] args) {
        System.exit(new CommandLine(new App()).execute(args));

    }

}

