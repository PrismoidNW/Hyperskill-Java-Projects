package client;

import server.Session;
import server.State;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    private final Socket socket;
    private final Path dataDir = Path.of("C:\\Users\\Deser\\IdeaProjects\\Academy\\Hard\\File Server\\File " +
            "Server\\task\\src\\client\\data");

    public Client(Socket socket) {
        this.socket = socket;
        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restartClient() throws IOException {
        Client client = new Client(new Socket(InetAddress.getByName("127.0.0.1"), 23456));
        client.socket.close();
    }

    public void start() throws IOException {

        System.out.print("Enter action (1 - get a file, 2 - save a file, 3 - delete a file): ");
        String input = scanner.next();

        if (input.equalsIgnoreCase("EXIT")) {
            outputStream.writeUTF("EXIT");
            server.Main.state = State.EXIT;
            System.out.println("The request was sent.");
            client.Main.closeConnection();
            inputStream.close();
            outputStream.close();
            scanner.close();
            restartClient();
            return;
        }

        int action = Integer.parseInt(input);

        switch (action) {
            case 1 -> {
                this.get();
            }
            case 2 -> {
                this.put();
            }
            case 3 -> {
                this.delete();
            }
        }
        this.socket.close();
        inputStream.close();
        outputStream.close();
    }

    private synchronized void delete() throws IOException {
        System.out.print("Do you want to delete the file by name or by id (1 - name, 2 - id): ");
        int input = scanner.nextInt();

        if (input == 1) {
            System.out.print("Enter name: ");
            String name = scanner.next();
            outputStream.writeUTF(String.format("DELETE BY_NAME %s", name));
            System.out.print("The request was sent.");
        } else {
            System.out.print("Enter id: ");
            int id = scanner.nextInt();
            outputStream.writeUTF(String.format("DELETE BY_ID %d", id));
            System.out.print("The request was sent.");
        }

        int statusCode = inputStream.readInt();

        if (statusCode == 200) {
            System.out.println("\nThe response says that this file was deleted successfully!");
        } else {
            System.out.println("\nThe response says that this file is not found!");
        }
    }

    private synchronized void get() throws IOException {
        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");
        int input = scanner.nextInt();

        if (input == 1) {
            System.out.print("Enter name: ");
            String name = scanner.next();
            outputStream.writeUTF(String.format("GET BY_NAME %s", name));
            System.out.print("\nThe request was sent.");
        } else if (input == 2) {
            System.out.print("\nEnter id: ");
            int id = scanner.nextInt();
            outputStream.writeUTF(String.format("GET BY_ID %d", id));
            System.out.print("\nThe request was sent.");
        }

        int statusCode = inputStream.readInt();

        if (statusCode == 200) {
            int length = inputStream.readInt();
            byte[] message = new byte[length];
            inputStream.readFully(message, 0, message.length);
            System.out.print("\nThe file was downloaded! Specify a name for it: ");
            scanner.nextLine();
            String name = scanner.nextLine();

            File file = new File(dataDir.toFile(), name);
            if (file.createNewFile()) {
                Files.write(Path.of(dataDir + "\\" + name), message);
                System.out.println("\nFile saved on the hard drive!");
            }
        } else {
            System.out.println("\nThe response says that this file is not found!");
        }
    }

    private void put() throws IOException {
        System.out.print("Enter name of the file: ");
        String nameSearch = scanner.next();

        File fileToSend = new File(dataDir + "\\" + nameSearch);

        if (!fileToSend.exists()) return;

        System.out.print("Enter name of the file to be saved on server: ");
        scanner.nextLine();
        String serverName = scanner.nextLine();
        String name = serverName.isBlank() ? nameSearch : serverName;

        outputStream.writeUTF("PUT " + name);

        byte[] message = Files.readAllBytes(fileToSend.toPath());
        outputStream.writeInt(message.length);
        outputStream.write(message);


        String response = inputStream.readUTF();
        int code = Integer.parseInt(response.replaceFirst("\\s.+", ""));

        if (code == 200) {
            int id = Integer.parseInt(response.replaceFirst("\\d{3}\\s", ""));
            System.out.println("Response says that file is saved! ID = " + id);
        }
    }/*

    private String generateName() {
        for (int i = 1; ; i++) {
            if (!Session.fileId.containsKey("Save(" + i + ")")) {
                return "Save(" + i + ")";
            }
        }
    }*/

    private synchronized void putKey(String key) {
        Session.fileId.put(Session.fileId.size() + 1, key);
    }
}
