import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Scanner;

public class MessagesInterface {

    private Object messageObject;
    private JSONArray messageJson;
    private Session session;

    private ArrayList<Message> receivedMessages = new ArrayList<>();
    private ArrayList<Message> sentMessages = new ArrayList<>();
    private Scanner scanner;

    public MessagesInterface(Session session) {
        this.session = session;
        try {
            messageObject = new JSONParser().parse(new FileReader("./jsons/messages.json"));
            messageJson = (JSONArray) messageObject;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        scanner = new Scanner(System.in);

    }

    public void messagesMenu() {
        int choice = 0;
        while (true) {
            System.out.println(Colors.RED + "\n--------------------Messages Menu--------------------\n" + Colors.RESET);
            System.out
                    .println(Colors.YELLOW + "1" + Colors.RESET + ".   " + Colors.GREEN + "New Message" + Colors.RESET);
            System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   Received Messages");
            System.out.println(Colors.YELLOW + "3" + Colors.RESET + ".   Sent Messages");
            System.out
                    .println(Colors.YELLOW + "0" + Colors.RESET + ".   Go back to the Course Registration System Menu");
            System.out.print("\n" + Colors.BLUE + "--> " + Colors.RESET + "What do you want to do?   ");
            System.out.print(Colors.BLUE);
            choice = scanner.nextInt();
            System.out.print(Colors.RESET);
            if (choice == 1) {
                System.out.print(Colors.YELLOW + "Enter receiver ID: " + Colors.RESET);
                String receiver = scanner.next();
                System.out.println(Colors.YELLOW + "\nEnter subject " + Colors.GREEN + "(Press Enter twice to go to next line) : "
                        + Colors.RESET);
                String subject = readMultiLineInput();
                System.out.println(Colors.YELLOW + "\nEnter description " + Colors.GREEN + "(Press Enter twice to go to next line) : "
                        + Colors.RESET);
                String description = readMultiLineInput();
                newMessage(description, subject, receiver);
            } else if (choice == 2) {
                showReceivedMessages();
            } else if (choice == 3) {
                showSentMessages();
            } else if (choice == 0) {
                return;
            } else {
                System.out.println(Colors.YELLOW + "Invalid choice! Please try again." + Colors.RESET);
            }

        }

    }

    private void calculateReceivedMessages() {

        for (Object messageObj : messageJson) {
            JSONObject message = (JSONObject) messageObj;
            String receiver = (String) message.get("Receiver");
            String sender = (String) message.get("Sender");

            if (receiver.equals(session.getUser().getID())) {
                String description = (String) message.get("Description");
                String subject = (String) message.get("Subject");

                Message receivedmessage = new Message(sender, receiver, description, subject);

                receivedMessages.add(receivedmessage);
            }
        }
    }

    private void showReceivedMessages() {
        calculateReceivedMessages();
        System.out.println(Colors.RED + "\n--------------------Received Messages--------------------\n" + Colors.RESET);
        for (Message message : receivedMessages) {
            System.out.println("----------------------------------------");
            System.out.println("Sender: " + message.getSenderID());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Description: " + message.getDescription());
        }
        System.out.println("----------------------------------------");
    }

    private void calculateSentMessages() {

        for (Object messageObj : messageJson) {
            JSONObject message = (JSONObject) messageObj;
            String receiver = (String) message.get("Receiver");
            String sender = (String) message.get("Sender");
            if (sender.equals(session.getUser().getID())) {
                String description = (String) message.get("Description");
                String subject = (String) message.get("Subject");

                Message sentMessage = new Message(sender, receiver, description, subject);

                sentMessages.add(sentMessage);
            }
        }

    }

    private void showSentMessages() {
        calculateSentMessages();
        System.out.println(Colors.RED + "\n--------------------Sent Messages--------------------\n" + Colors.RESET);
        for (Message message : sentMessages) {
            System.out.println("----------------------------------------");
            System.out.println("Receiver: " + message.getReceiverID());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Description: " + message.getDescription());
        }
        System.out.println("----------------------------------------");
    }

    // send message specific person
    private void newMessage(String description, String subject, String receiver) {
        JSONObject newMessage = new JSONObject();
        newMessage.put("Sender", session.getUser().getID());
        newMessage.put("Receiver", receiver);
        newMessage.put("Subject", subject);
        newMessage.put("Description", description);

        messageJson.add(newMessage);

        try {
            PrintWriter pw = new PrintWriter("./jsons/messages.json");
            pw.write(messageJson.toJSONString());

            pw.flush();
            pw.close();

        } catch (Exception e) {
            System.out.println(Colors.YELLOW + "Error: " + e + Colors.RESET);
        }
    }

    private static String readMultiLineInput() {
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString().trim();

    }
}
