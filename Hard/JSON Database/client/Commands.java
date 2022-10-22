package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Commands {
    @Parameter(names = "-t")
    private String type;
    @Parameter(names = "-k")
    private String key;
    @Parameter(names = "-v")
    private String value;
    @Parameter(names = "-in")
    private String file;

    public Commands parseCommands(String[] args) {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);
        return this;
    }

    public String getFileCommand() {
        File dataFile = new File("C:\\Users\\Deser\\IdeaProjects\\Academy\\Hard\\JSON Database\\JSON " +
                "Database\\task\\src\\client\\data\\" + this.getFile());
        try {
            return Files.readString(dataFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFile() {
        return file;
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
