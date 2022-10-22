package tictactoe.game.settings;

public enum Settings {
    USER("user"),
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private final String inputType;

    Settings(String inputType) {
        this.inputType = inputType;
    }

    public static Settings fromType(String inputType) {
        return Settings.valueOf(inputType.toUpperCase());
    }
}
