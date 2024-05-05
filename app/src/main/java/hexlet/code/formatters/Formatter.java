package hexlet.code.formatters;

public abstract class Formatter {
     public static Format getFormat(String format) {
        return switch (format) {
            default -> new Stylish();
        };

    }
}
