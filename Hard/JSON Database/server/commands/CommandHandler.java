package server.commands;

import com.google.gson.JsonObject;
import server.CommandObj;
import server.Session;

import java.io.IOException;

import static server.Main.*;
import static server.Session.writeMessage;

public class CommandHandler {

    public void handleCommands(CommandObj cmdObj) {
        String message = cmdObj.getType();

        if (cmdObj.getKey() != null) {
            message += " " + cmdObj.getKey();
        }

        if (cmdObj.getValue() != null) {
            message += " " + cmdObj.getValue();
        }

        String[] splitLine = message.split("\\s+");
        if (splitLine.length >= 2) {
            String command = splitLine[0];

            String index = splitLine[1];
            switch (Command.getCommand(command)) {
                case GET -> getCommand(index);
                case DELETE -> deleteCommand(index);
                case SET -> setCommand(index, message.substring(command.length() + 2 + index.length()));
                default -> writeMessage(true, "No such key");
            }
        } else {
            writeMessage(true, "No such key");
        }
    }

    public void getCommand(String index) {
        String data = DATABASE.getData(index);

        JsonObject object = new JsonObject();

        try {
            if (data.equalsIgnoreCase("ERROR")) {
                object.addProperty("response", "ERROR");
                object.addProperty("reason", "No such key");
            } else {
                object.addProperty("response", "OK");
                object.addProperty("value", data);
            }
            Session.getOutputStream().writeUTF(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCommand(String index, String data) {
        DATABASE.setData(index, data);
        writeMessage(false, "");
    }

    private void deleteCommand(String index) {
        String data = DATABASE.getData(index);

        if (data.equals("ERROR")) {
            writeMessage(true, "No such key");
        } else {
            writeMessage(false, "");
            DATABASE.deleteData(index);
        }
    }
}
