package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {
    public static ServerSocket serverSocket;
    public static State state = State.RUNNING;

    public static void main(String[] args) {
        System.out.println("\nServer started!");
        try {
            serverSocket = new ServerSocket(23456, 50, InetAddress.getByName("127.0.0.1"));
            while (state.equals(State.RUNNING)) {
                Session session = new Session(serverSocket.accept());
                if (!state.equals(State.EXIT)) {
                    session.start();
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() throws IOException {
        serverSocket.close();
        System.exit(0);
    }
}
