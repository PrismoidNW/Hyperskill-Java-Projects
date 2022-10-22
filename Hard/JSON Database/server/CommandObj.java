package server;

public class CommandObj {

    private String type;
    private String key;
    private String value;


    public CommandObj(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public CommandObj(String type, String key) {
        this.type = type;
        this.key = key;
        this.value = null;
    }

    public CommandObj() {
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
