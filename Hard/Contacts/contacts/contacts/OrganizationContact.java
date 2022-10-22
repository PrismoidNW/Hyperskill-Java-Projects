package contacts.contacts;

import contacts.Main;

import java.io.Serializable;
import java.util.Scanner;

public class OrganizationContact extends Record implements Serializable {
    private String address;
    private transient Scanner scanner = Main.scanner;

    public OrganizationContact(String name, String address, String phoneNumber) {
        super(name, phoneNumber);
        this.address = address;
    }

    public OrganizationContact() {
        super.setTimeCreated();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        setTimeLastEdited();
    }

    @Override
    public Record setupContact() {
        System.out.print("Enter the name: ");
        String name = scanner.next() + scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.next() + scanner.nextLine();

        System.out.print("Enter the number: ");
        String phoneNumber = scanner.next() + scanner.nextLine();
        return new OrganizationContact(name, address, phoneNumber);
    }
}
