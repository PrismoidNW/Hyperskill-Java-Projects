package contacts.contacts;

import contacts.Main;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;

public class PersonContact extends Record implements Serializable {
    private transient final Scanner scanner = Main.scanner;
    private String surName;
    private String birthDate;
    private String gender;

    public PersonContact(String name, String surName, String phoneNumber, String birthDate, String gender) {
        super(name, phoneNumber);
        this.surName = surName;
        setBirthDate(birthDate);
        setGender(gender);
        super.setTimeLastEdited();
    }

    public PersonContact() {
        super.setTimeCreated();
        super.setTimeLastEdited();
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        try {
            LocalDate.parse(birthDate);
        } catch (Exception e) {
            System.out.println("Bad birth date!");
            birthDate = "[no data]";
        }

        this.birthDate = birthDate;
        setTimeLastEdited();
    }

    public void setSurName(String surName) {
        this.surName = surName;
        setTimeLastEdited();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F")) {
            System.out.println("Bad gender!");
            gender = "[no data]";
        }
        this.gender = gender;
        setTimeLastEdited();
    }

    public String getSurname() {
        return surName;
    }

    @Override
    public Record setupContact() {
        PersonContact personContact = new PersonContact();
        System.out.print("Enter the name: ");
        String name = scanner.next() + scanner.nextLine();
        personContact.setName(name);

        System.out.print("Enter the surname: ");
        String surname = scanner.next() + scanner.nextLine();
        personContact.setSurName(surname);

        System.out.print("Enter the birth date: ");
        String birthDate = scanner.nextLine();
        setBirthDate(birthDate);

        System.out.print("Enter the gender (M, F): ");
        String gender = scanner.nextLine().replace("\\s", "");
        setGender(gender);

        System.out.print("Enter the number: ");
        String phoneNumber = scanner.next() + scanner.nextLine();

        personContact.setPhoneNumber(phoneNumber);
        return personContact;
    }

    public Record setSurname(String surName) {
        this.surName = surName;
        return this;
    }

    @Override
    public Record build() {
        return this;
    }
}
