package server.commands;

public enum Command {
    SET("set"),
    GET("get"),
    DELETE("delete");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command getCommand(String cmd) {
        return switch (cmd.toLowerCase()) {
            case "set" -> Command.SET;
            case "get" -> Command.GET;
            case "delete" -> Command.DELETE;
            default -> null;
        };
    }
}
