package contacts.menus;

import contacts.Main;
import contacts.State;
import contacts.contacts.OrganizationContact;
import contacts.contacts.PersonContact;
import contacts.contacts.Record;

import java.util.ArrayList;
import java.util.Scanner;

public class RecordMenu {

    private transient final Scanner scanner = Main.scanner;

    public void execute(int i) {
        if (i != -1) {
            Main.printRecord(i);
        }
        System.out.print("\n[record] Enter action (edit, delete, menu): ");
        String input = scanner.next().toLowerCase();
        switch (input) {
            case "edit": {
                if (i == -1) {
                    edit();
                } else {
                    edit(i);
                }
                break;
            }
            case "delete": {
                deleteRecord();
                break;
            }
            case "menu": {
                Main.state = State.MENU;
                return;
            }
            case "exit": {
                System.exit(0);
                break;
            }
        }
    }

    public void deleteRecord(int i) {
        Main.printRecord();
        int number = i;
        if (i == -1) {
            System.out.print("Select a record: ");
            try {
                number = Integer.parseInt(scanner.next());
            } catch (Exception ignore) {
                return;
            }
        }
        ArrayList<Record> recordList = Record.readObjects();
        if (recordList.size() == 0) {
            System.out.println("You have 0 records to delete.");
            return;
        }
        Main.records.remove(number - 1);

        scanner.nextLine();
    }

    public void deleteRecord() {
        deleteRecord(-1);
    }

    public void edit() {
        edit(-1);
        System.out.println("The record updated!\n");
    }

    public int getRecord() {
        System.out.print("Select a record: ");
        int recordIndex = -1;
        try {
            recordIndex = Integer.parseInt(scanner.next());
        } catch (Exception ignore) {
        }
        return recordIndex;
    }

    public void edit(int i) {
        Main.printRecord();
        int recordIndex = i;
        if (i == -1) {
            recordIndex = getRecord();
        }
        Record record = Record.readObjects().get(recordIndex - 1);
        if (record instanceof OrganizationContact) {
            OrganizationContact organizationContact = (OrganizationContact) record;
            System.out.print("Select a field (name, address, number): ");
            String input = scanner.next() + scanner.nextLine();
            System.out.println();
            switch (input.toLowerCase()) {
                case "name": {
                    System.out.print("Enter name: ");
                    String name = scanner.next() + scanner.nextLine();
                    organizationContact.setName(name);
                    break;
                }
                case "address": {
                    System.out.print("Enter address: ");
                    String address = scanner.next() + scanner.nextLine();
                    organizationContact.setAddress(address);
                    break;
                }
                case "number": {
                    System.out.print("Enter number: ");
                    String phone = scanner.next() + scanner.nextLine();
                    organizationContact.setPhoneNumber(phone);
                    break;
                }
            }
            Record.writeObject(Record.readObjects().set(recordIndex - 1, organizationContact));
        } else {
            PersonContact personContact = (PersonContact) record;
            System.out.print("Select a field (name, surname, birth, gender, number): ");
            String input = scanner.next() + scanner.nextLine();

            switch (input.toLowerCase()) {
                case "name": {
                    System.out.print("Enter name: ");
                    String name = scanner.next() + scanner.nextLine();
                    personContact.setName(name);
                    break;
                }
                case "surname": {
                    System.out.print("Enter surname: ");
                    String surname = scanner.next() + scanner.nextLine();
                    personContact.setSurName(surname);
                    break;
                }
                case "birth": {
                    System.out.print("Enter birth: ");
                    String birth = scanner.next() + scanner.nextLine();
                    personContact.setBirthDate(birth);
                    break;
                }
                case "gender": {
                    System.out.print("Enter gender: ");
                    String gender = scanner.next() + scanner.nextLine();
                    System.out.println(gender);
                    personContact.setGender(gender);
                    break;
                }
                case "number": {
                    System.out.print("Enter number: ");
                    String number = scanner.next() + scanner.nextLine();
                    System.out.println(number);
                    personContact.setPhoneNumber(number);
                    break;
                }
            }
            Record.writeObject(Record.readObjects().set(recordIndex - 1, personContact));
        }
        System.out.println("Saved");
        execute(i);
    }
}
