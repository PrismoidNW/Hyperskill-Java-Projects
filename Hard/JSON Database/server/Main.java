package server;

import server.commands.CommandHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {

    public static final CommandHandler HANDLER = new CommandHandler();
    public static final Database DATABASE = new Database();
    public static ServerSocket serverSocket;
    public static State state = State.RUNNING;

    public static void main(String[] args) {
        System.out.println("Server Started!");
        try {
            serverSocket = new ServerSocket(23456, 50, InetAddress.getByName("127.0.0.1"));
            while (state.equals(State.RUNNING)) {
                Session session = new Session(serverSocket.accept());
                session.start();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static void closeConnection() throws IOException {
        serverSocket.close();

    }
}
