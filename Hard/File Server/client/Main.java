package client;

import server.State;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    private static Socket socket;

    public static void main(String[] args) {
        while (server.Main.state.equals(State.RUNNING)) {
            try {
                socket = new Socket(InetAddress.getByName("127.0.0.1"), 23456);
                Client client = new Client(socket);
                client.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeConnection() throws IOException {
        socket.close();
    }
}
