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

    Colors Colors = new Colors();

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
            System.out.println(Colors.getRED() + Colors.getBOLD() + "\n>> Messages Menu\n" + Colors.getRESET());
            System.out
                    .println(Colors.getYELLOW() + "1" + Colors.getRESET() + ".   " + Colors.getGREEN() + "New Message"
                            + Colors.getRESET());
            System.out.println(Colors.getYELLOW() + "2" + Colors.getRESET() + ".   Received Messages");
            System.out.println(Colors.getYELLOW() + "3" + Colors.getRESET() + ".   Sent Messages");
            System.out
                    .println(Colors.getYELLOW() + "0" + Colors.getRESET()
                            + ".   Go back to the Course Registration System Menu");
            System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");
            System.out.print(Colors.getBLUE());
            choice = scanner.nextInt();
            System.out.print(Colors.getRESET());
            if (choice == 1) {
                System.out.print(Colors.getYELLOW() + "Enter receiver ID: " + Colors.getRESET());
                String receiver = scanner.next();
                System.out.println(Colors.getYELLOW() + "\nEnter subject " + Colors.getRESET()
                        + "(Press Enter twice to go to next line) : ");
                String subject = readMultiLineInput();
                System.out.println(Colors.getYELLOW() + "\nEnter description " + Colors.getRESET()
                        + "(Press Enter twice to go to next line) : ");
                String description = readMultiLineInput();
                newMessage(description, subject, receiver);
            } else if (choice == 2) {
                showReceivedMessages();
            } else if (choice == 3) {
                showSentMessages();
            } else if (choice == 0) {
                return;
            } else {
                System.out.println(Colors.getYELLOW() + "Invalid choice! Please try again." + Colors.getRESET());
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
        System.out.println(
                Colors.getRED()+Colors.getBOLD() + "\n>>> Received Messages\n" + Colors.getRESET()+Colors.getRESET());
        int i = 1;
        if (receivedMessages.size() == 0) {
            
            System.out.println(Colors.getYELLOW()+"You have no received messages.\n"+Colors.getRESET());
            return;
        }
        
        for (Message message : receivedMessages) { 
            System.out.println(Colors.getBLUE()+"Received Message " + i++ + ":" + Colors.getRESET());
            System.out.println(Colors.getYELLOW()+"\tSender: " +Colors.getRESET()+ message.getSenderID());
            System.out.println(Colors.getYELLOW()+"\tSubject: " +Colors.getRESET()+ message.getSubject());
            System.out.println(Colors.getYELLOW()+"\tDescription: " +Colors.getRESET()+ message.getDescription());
        }
        System.out.println("");
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
        int i=1;
        
        System.out
                .println(Colors.getRED()+ Colors.getBOLD()+ "\n>> Sent Messages\n" + Colors.getRESET()+Colors.getRESET());
        if (sentMessages.size() == 0) {
            System.out.println(Colors.getYELLOW()+"You have no sent messages.\n"+Colors.getRESET());
            return;
        }

        for (Message message : sentMessages) {
            System.out.println(Colors.getBLUE()+"Sent Message " + i++ + ":" + Colors.getRESET());
            System.out.println(Colors.getYELLOW()+"\tReceiver: " +Colors.getRESET()+ message.getReceiverID());
            System.out.println(Colors.getYELLOW()+"\tSubject: " +Colors.getRESET()+ message.getSubject());
            System.out.println(Colors.getYELLOW()+"\tDescription: " +Colors.getRESET()+ message.getDescription());
        }
        System.out.println("");
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
            System.out.println(Colors.getYELLOW() + "Error: " + e + Colors.getRESET());
        }
    }

    private String readMultiLineInput() {
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
