package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Database {
    private final LinkedHashMap<String, String> database = new LinkedHashMap<>(1000);

    private final File databaseFile = new File("C:\\Users\\Deser\\IdeaProjects\\Academy\\Hard\\JSON Database\\JSON " +
            "Database\\task\\src\\server\\data\\db.json");

    public void setData(String index, String data) {
        this.database.put(index, data);
        writeData();
    }

    private void writeData() {
        StringBuilder builder = new StringBuilder();
        for (Entry<String, String> e : database.entrySet()) {
            builder.append(e.getKey())
                    .append(": ")
                    .append(e.getValue())
                    .append("\n");
        }
        try {
            Files.writeString(databaseFile.toPath(), builder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData(String index) {
        this.database.remove(index);
        writeData();
    }

    public String getData(String index) {
        String data = database.get(index);
        return data == null ? "ERROR" : data;
    }
}
