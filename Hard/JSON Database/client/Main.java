package client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();

        Commands commands = new Commands().parseCommands(args);

        client.run(commands);
    }
}
