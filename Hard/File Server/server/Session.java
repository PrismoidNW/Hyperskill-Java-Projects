package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Session extends Thread {
    public static HashMap<Integer, String> fileId = new HashMap<>();
    private final Path dataDir = Path.of("C:\\Users\\Deser\\IdeaProjects\\Academy\\Hard\\File Server\\File " +
            "Server\\task\\src\\server\\data");
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public Session(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.addFileValues();
        try {
            String incomingMessage = inputStream.readUTF();

            if (incomingMessage.equals("EXIT")) {
                Main.state = server.State.EXIT;
                this.outputStream.close();
                this.inputStream.close();
                return;
            }
            this.decideAction(incomingMessage);
            this.socket.close();
            this.outputStream.close();
            this.inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decideAction(String inputMessage) throws IOException {
        addFileValues();
        String request = inputMessage.replaceFirst("\\s.+", "");
        switch (request) {
            case "GET" -> this.get(inputMessage);
            case "PUT" -> this.put(inputMessage);
            case "DELETE" -> this.delete(inputMessage);
        }
    }

    private void get(String message) throws IOException {
        addFileValues();
        String type = message.replaceFirst(".{3}\\s", "").replaceFirst("\\s.+", "");
        boolean isId = type.equals("BY_ID");

        File fileToGet;
        if (isId) {
            int id = Integer.parseInt(message.replaceFirst(".{3}\\s.{5}\\s", ""));
            if (fileId.containsKey(id)) {
                outputStream.writeInt(200);
                fileToGet = Path.of(dataDir + "\\" + getKeyFromId(id)).toFile();
            } else {
                outputStream.writeInt(404);
                return;
            }
        } else {
            String name = message.replaceFirst(".{3}\\s.{7}\\s", "");

            if (fileId.containsValue(name)) {
                outputStream.writeInt(200);
                fileToGet = Path.of(dataDir + "\\" + name).toFile();
            } else {
                outputStream.writeInt(404);
                return;
            }
        }
        addFileValues();
        byte[] byteMessage = Files.readAllBytes(fileToGet.toPath());
        outputStream.writeInt(byteMessage.length);
        outputStream.write(byteMessage);
    }

    public void addFileValues() {
        String[] files = Path.of(dataDir + "\\").toFile().list();
        for (String file : files) {
            if (fileId.containsValue(file)) continue;
            putKey(file);
        }
    }

    private void putKey(String key) {
        Session.fileId.put(Session.fileId.size() + 1, key);
    }

    private void put(String request) throws IOException {
        String fileSaveName = request.replaceFirst("PUT\\s", "");
        File fileToSave = new File(dataDir + "\\" + fileSaveName);

        putKey(fileSaveName);
        int length = inputStream.readInt();
        byte[] message = new byte[length];
        inputStream.readFully(message, 0, message.length);

        this.addFileValues();

        if (fileToSave.createNewFile()) {
            Files.write(fileToSave.toPath(), message);
            outputStream.writeUTF("200 " + getIdFromKey(fileSaveName));
        } else {
            outputStream.writeUTF("403");
        }
        this.addFileValues();
    }

    private void delete(String request) throws IOException {

        String editedRequest = request.replaceFirst("DELETE\\s", "").replaceFirst("\\s.+", "");
        boolean isId = editedRequest.equals("BY_ID");
        File file;
        if (isId) {
            int id = Integer.parseInt(request.replaceFirst("DELETE\\sBY_ID\\s", ""));
            file = Path.of(dataDir + "\\" + getKeyFromId(id)).toFile();
        } else {
            String name = request.replaceFirst("DELETE\\sBY_NAME\\s", "");
            file = Path.of(dataDir + "\\" + name).toFile();
        }

        if (file.delete()) {
            outputStream.writeInt(200);
        } else {
            outputStream.writeInt(404);
        }
        this.removeEntry();
    }

    public void removeEntry() {
        fileId = new HashMap<>();
        addFileValues();
    }

    private String getKeyFromId(int search) {
        final String[] filename = new String[1];
        fileId.forEach((key, value) -> {
            if (key == search) {
                filename[0] = value;
            }
        });
        return filename[0];
    }

    private int getIdFromKey(String search) {
        int[] id = new int[1];
        fileId.forEach((key, value) -> {
            if (value.equals(search)) {
                id[0] = key;
            }
        });
        return id[0];
    }
}
