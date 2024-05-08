package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<String> {

    @Option(names = {"-f", "--format"}, paramLabel = "format",
            description = "output format [default: ${DEFAULT-VALUE}]", defaultValue = "stylish")
    private String format;

    @Parameters(index = "0", paramLabel = "filepath1", description = "Path to first file")
    private String filepath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "Path to second file")
    private String filepath2;

    @Override
    public String call() throws IOException {
        String difference = Differ.generate(filepath1, filepath2, format);
        System.out.println(difference);
        return difference;
    }

    public static void main(String[] args) {
        try {
            System.exit(new CommandLine(new App()).execute(args));
            throw new IOException("File not found");
        } catch (IOException exception) {
            exception.getMessage();
        }
    }
}
