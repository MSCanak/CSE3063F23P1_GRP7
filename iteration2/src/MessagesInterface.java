import java.io.FileReader;
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
            System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   Send Message");
            System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   Received Messages");
            System.out.println(Colors.YELLOW + "3" + Colors.RESET + ".   Sent Messages");
            System.out
                    .println(Colors.YELLOW + "*" + Colors.RESET + ".   Go back to the Course Registration System Menu");
            System.out.println("\nWhat do you want to do?\n");

            choice = scanner.nextInt();
            if (choice == 1) {
                System.out.print("Enter receiver ID: ");
                String receiver = scanner.next();
                System.out.print("\nEnter subject: ");
                String subject = scanner.next();
                System.out.print("\nEnter description: ");
                String description = scanner.next();
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

    private void reveiveMessagesMenu() {
        // karagül
    }

    private void sentMessagesMenu() {
        // karagül
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
        for (Message message : receivedMessages) {
            System.out.println("Sender: " + message.getSenderID());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Description: " + message.getDescription());
        }
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
        for (Message message : sentMessages) {
            System.out.println("Receiver: " + message.getReceiverID());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Description: " + message.getDescription());
        }
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
            System.out.println(Colors.YELLOW+"Error: " + e+Colors.RESET);
        }
    }
}
