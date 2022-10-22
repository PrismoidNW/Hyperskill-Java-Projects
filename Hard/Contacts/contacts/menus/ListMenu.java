package contacts.menus;

import contacts.Main;
import contacts.State;

import java.util.Scanner;

public class ListMenu {

    private final Scanner scanner = Main.scanner;

    public void execute() {
        Main.printContacts();

        System.out.print("[list] Enter action ([number], back): ");
        String input = scanner.next();

        if (input.equalsIgnoreCase("back")) {
            Main.state = State.MENU;
        } else if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        } else {
            int inputInt;
            try {
                inputInt = Integer.parseInt(input);
                System.out.println();
                new RecordMenu().execute(inputInt);
            } catch (Exception ignore) {
            }
        }
    }
}
