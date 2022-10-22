package contacts;

import contacts.contacts.OrganizationContact;
import contacts.contacts.PersonContact;
import contacts.contacts.Record;
import contacts.menus.ListMenu;
import contacts.menus.MainMenu;
import contacts.menus.RecordMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static State state = State.MENU;
    public static ArrayList<Record> records = new ArrayList<>();

    public static void main(String[] args) {
        while (!state.equals(State.EXIT)) {
            switch (state) {
                case MENU: {
                    new MainMenu().execute();
                    break;
                }
                case LIST: {
                    new ListMenu().execute();
                    break;
                }
                case RECORD: {
                    new RecordMenu().execute(-1);
                    break;
                }
            }
        }
    }

    public static void printContacts() {
        int count = 1;
        for (Record r : Record.readObjects()) {
            System.out.printf("%d. %s\n", count, r.getName());
            count++;
        }
        System.out.println();
    }

    public static void printRecord() {
        printRecord(-1);
    }

    public static void printRecord(int index) {
        if (index == -1) {
            int count = 1;
            for (Record r : Record.readObjects()) {
                System.out.printf("%d. %s\n", count, r.getName());
                count++;
            }
        } else {
            Record r = Record.readObjects().get(index - 1);
            if (r instanceof OrganizationContact) {
                OrganizationContact contact = (OrganizationContact) r;
                System.out.printf("Organization name: %s\n", contact.getName());
                System.out.printf("Address: %s\n", contact.getAddress());
                System.out.printf("Number: %s\n", contact.getPhoneNumber());
                System.out.printf("Time created: %s\n", contact.getTimeCreated());
                System.out.printf("Time last edit: %s\n", contact.getTimeLastEdited());
            } else {
                PersonContact contact = (PersonContact) r;
                System.out.printf("Name: %s\n", contact.getName());
                System.out.printf("Surname: %s\n", contact.getSurname());

                String birthDate = contact.getBirthDate() == null ? "[no data]" : contact.getBirthDate();
                String gender = contact.getGender() == null ? "[no data]" : contact.getGender();

                System.out.printf("Birth date: %s\n", birthDate);
                System.out.printf("Gender: %s\n", gender);
                System.out.printf("Number: %s\n", contact.getPhoneNumber());
                System.out.printf("Time created: %s\n", contact.getTimeCreated());
                System.out.printf("Time last edit: %s\n", contact.getTimeLastEdited());
            }
        }
    }
}
