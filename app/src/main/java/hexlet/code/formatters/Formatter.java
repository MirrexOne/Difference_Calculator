package hexlet.code.formatters;

public abstract class Formatter {
     public static Format getFormat(String format) {
        return switch (format) {
            case "plain" -> new Plain();
            case "json" -> new JsonFormat();
            default -> new Stylish();
        };

    }
}
