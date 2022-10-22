package contacts.menus;

import contacts.Main;
import contacts.State;
import contacts.contacts.OrganizationContact;
import contacts.contacts.PersonContact;
import contacts.contacts.Record;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = Main.scanner;

    public void execute() {
        System.out.print("[menu] Enter action (add, list, search, count, exit): ");
        String input = scanner.next().toLowerCase();

        switch (input) {
            case "add": {
                add();
                break;
            }
            case "list": {
                Main.state = State.LIST;
                list();
                break;
            }
            case "search": {
                search();
                break;
            }
            case "count": {
                ArrayList<Record> rec = Record.readObjects();
                System.out.printf("The Phone Book has %d records.\n", rec.size());
                break;
            }
            case "exit": {
                Main.state = State.EXIT;
                break;
            }
        }
        System.out.println();
    }

    public void search() {
        ArrayList<Record> recordList = Record.readObjects();
        if (recordList.size() > 0) {
            System.out.print("Enter search query: ");
            String query = scanner.next() + scanner.nextLine();
            printSearchQueries(query, true);
        }

        searchOptions();
    }

    public void printSearchQueries(String query, boolean printCount) {
        ArrayList<Record> recordList = Record.readObjects();
        int count = 0;

        if (printCount) {
            for (Record r : recordList) {
                if (r.getName().toLowerCase().contains(query)) {
                    count++;
                }
            }
        }

        count = 1;

        for (Record r : recordList) {
            if (r instanceof PersonContact) {
                if (r.isValidPhoneNumber(query) && r.getPhoneNumber().contains(query)) {
                    System.out.printf("%d. %s\n", count, r.getName() + " " + ((PersonContact) r).getSurname());
                    count++;
                } else if ((r.getName() + " " + ((PersonContact) r).getSurname()).toLowerCase().contains(query.toLowerCase())) {
                    System.out.printf("%d. %s\n", count, r.getName() + " " + ((PersonContact) r).getSurname());
                    count++;
                }
            } else {
                if (r.isValidPhoneNumber(query) && r.getPhoneNumber().contains(query)) {
                    System.out.printf("%d. %s\n", count, r.getName());
                    count++;
                } else if (r.getName().toLowerCase().contains(query.toLowerCase())) {
                    System.out.printf("%d. %s\n", count, r.getName());
                    count++;
                }
            }
        }
    }

    public void add() {
        Record record = null;
        System.out.print("Enter the type (person, organization): ");
        switch (scanner.next().toLowerCase()) {
            case "organization": {
                record = new OrganizationContact();
                break;
            }
            case "person": {
                record = new PersonContact();
                break;
            }
            case "exit": {
                System.exit(0);
                return;
            }
        }
        if (record == null) {
            return;
        }
        Record.writeObject(record.setupContact());
        System.out.println("The record added.");
    }

    public void list() {
        new ListMenu().execute();
    }

    public void searchOptions() {
        System.out.print("\n[search] Enter action ([number], back, again): ");
        String query = scanner.next();
        switch (query.toLowerCase()) {
            case "back": {
                return;
            }
            case "again": {
                search();
            }
            default: {
                int input = -1;
                try {
                    input = Integer.parseInt(query);
                } catch (Exception ignore) {
                }
                if (input > 0) {
                    Main.printRecord(input);
                }
            }
        }
    }
}
