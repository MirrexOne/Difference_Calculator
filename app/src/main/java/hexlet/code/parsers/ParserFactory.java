package hexlet.code.parsers;

public interface ParserFactory {
    static Parser getParser(String fileExtension) {
        return "json".equals(fileExtension) ? new Json() : new Yaml();
    }
}
