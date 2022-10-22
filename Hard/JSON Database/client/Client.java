package client;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;

public class Client {
    private Socket socket;

    public void run(Commands commands) {
        System.out.println("Client started!");
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 23456);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            Gson gson = new Gson();
            String messageOut;
            if (commands.getFile() != null) {
                messageOut = commands.getFileCommand();
            } else {
                messageOut = gson.toJson(commands);
            }
            outputStream.writeUTF(messageOut);
            System.out.println("Sent: " + messageOut);

            String messageIn = inputStream.readUTF();
            System.out.println("Received: " + messageIn);

            Commands cmd = new Gson().fromJson(messageOut, Commands.class);

            if (cmd.getType().equalsIgnoreCase("EXIT")) {
                restartClient();
            }
        } catch (IOException ignore) {
        }
    }

    private void restartClient() throws IOException {
        socket = new Socket(InetAddress.getByName("127.0.0.1"), 23456);
        socket.close();
    }
}
