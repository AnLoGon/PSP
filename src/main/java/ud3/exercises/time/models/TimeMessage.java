package ud3.exercises.time.models;

public class TimeMessage {
    public static String[] parse(String input) {
        input = input.trim();
        String[] parts = input.split("\\s+", 2);
        String command = parts[0].toUpperCase();
        String timezone = parts.length > 1 ? parts[1] : "UTC";
        return new String[]{command, timezone};
    }
}