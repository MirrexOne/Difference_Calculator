package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable {

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    private String format = "stylish";

    @Parameters(index = "0", paramLabel = "filepath1", description = "Path to first file")
    private String filepath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "Path to second file")
    private String filepath2;
    @Override
    public String call() throws IOException {
        String difference = Differ.generate(filepath1, filepath2);
        System.out.println(difference);
        return difference;
    }

    public static void main(String[] args) {
        try {
            System.exit(new CommandLine(new App()).execute(args));
            throw new IOException("File does not found");
        } catch (IOException exception) {
            exception.getMessage();
        }
    }

}
