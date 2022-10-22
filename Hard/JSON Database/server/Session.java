package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static server.Main.HANDLER;

public class Session extends Thread {
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    private final Socket socket;

    public Session(Socket socket) {
        this.socket = socket;

        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject buildMessage(boolean error) {
        JsonObject object = new JsonObject();
        object.addProperty("response", error ? "ERROR" : "OK");
        return object;
    }

    public static void writeMessage(boolean error, String message) {
        JsonObject object = buildMessage(error);
        try {
            if (error) {
                object.addProperty("reason", message);
            }
            outputStream.writeUTF(object.toString());
            System.out.println("Sent: " + object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataOutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void run() {
        try {
            String incomingMessage = inputStream.readUTF();
            System.out.println("Received: " + incomingMessage);
            Gson gson = new Gson();
            CommandObj cmdObj = gson.fromJson(incomingMessage, CommandObj.class);
            if (cmdObj.getType().equalsIgnoreCase("EXIT")) {
                writeMessage(false, "");
                Main.state = server.State.EXIT;
                Main.closeConnection();
                return;
            }
            HANDLER.handleCommands(cmdObj);
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }
}
