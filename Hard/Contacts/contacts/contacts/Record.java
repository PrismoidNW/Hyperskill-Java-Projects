package contacts.contacts;

import contacts.Main;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Record implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String phoneNumber;
    private String timeCreated;
    private String timeLastEdited;

    public Record(String name, String phoneNumber) {
        this.name = name;
        this.setPhoneNumber(phoneNumber);
        LocalDateTime time = LocalDateTime.now(Clock.systemDefaultZone());
        timeCreated = time.toString();
        timeLastEdited = time.toString();
    }

    public Record() {
        LocalDateTime time = LocalDateTime.now(Clock.systemDefaultZone());
        timeCreated = time.toString();
        timeLastEdited = time.toString();
    }

    public static void writeObject(Record record) {
        Main.records.add(record);
    }

    public static ArrayList<Record> readObjects() {
        return Main.records;
    }

    public Record setupContact() {
        return null;
    }

    public void setTimeCreated() {
        LocalDateTime time = LocalDateTime.now(Clock.systemDefaultZone());
        timeCreated = time.toString();
        timeLastEdited = time.toString();
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\+?([\\da-zA-Z]+[\\s-]?)?" +
                "(\\([\\da-zA-Z]{2,}(\\)[\\s-]|\\)$))?" +
                "([\\da-zA-Z]{2,}[\\s-]?)*([\\da-zA-Z]{2,})?$");
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        setTimeLastEdited();
        return this;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public String getTimeLastEdited() {
        return timeLastEdited;
    }

    public Record setTimeLastEdited() {
        timeLastEdited = LocalDateTime.now().toString();
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Record setPhoneNumber(String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = null;
            System.out.println("Wrong number format!");
        }
        setTimeLastEdited();
        return this;
    }

    public Record build() {
        return this;
    }
}
