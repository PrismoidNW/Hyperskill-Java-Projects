package tracker.data;

public enum ValidCommands {
    EXIT("EXIT"),
    ADD_STUDENTS("ADD STUDENTS"),
    LIST("LIST"),
    ADD_POINTS("ADD POINTS"),
    FIND("FIND"),
    STATISTICS("STATISTICS"),
    NOTIFY("NOTIFY");

    public final String commandName;

    ValidCommands(String commandName) {
        this.commandName = commandName;
    }

    public static ValidCommands getCommand(String command) {
        for (ValidCommands cmd : ValidCommands.values()) {
            if (isValidCommand(command) && cmd.commandName.equals(command.toUpperCase())) {
                return cmd;
            }
        }
        return null;
    }

    public static boolean isValidCommand(String command) {
        for (ValidCommands cmd : ValidCommands.values()) {
            if (cmd.commandName.equals(command.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
