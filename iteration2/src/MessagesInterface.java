import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Scanner;


public class MessagesInterface {
    Object messageObject;
    JSONArray messageJson;
    Session session;

    ArrayList<Message> receivedMessages = new ArrayList<>();
    ArrayList<Message> sentMessages = new ArrayList<>();
    Scanner scanner;

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
        while(true){
            
            System.out.println("Messages Menu");
            System.out.println("1. Send Message");
            System.out.println("2. Received Messages");
            System.out.println("3. Sent Messages");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            if(choice == 1) {
                System.out.print("Enter receiver ID: ");
                String receiver = scanner.next();
                System.out.print("Enter subject: ");
                String subject = scanner.next();
                System.out.print("Enter description: ");
                String description = scanner.next();
                newMessage(description, subject, receiver);
            }
            else if(choice == 2) {
                showReceivedMessages();
            }
            else if(choice == 3) {
                showSentMessages();
            }
            else if(choice == 0) {
                return;
            }
            else {
                System.out.println("Invalid choice");
            }

        }

    }
    
    
    public void reveiveMessagesMenu() {
        // karagül
    }

    public void sentMessagesMenu() {
        //karagül
    }

    public void calculateReceivedMessages() {
        

        for (Object messageObj : messageJson) {
            JSONObject message = (JSONObject) messageObj;
            String receiver = (String) message.get("Receiver");
            String sender = (String) message.get("Sender");

            if(receiver.equals(session.getUser().getID())) {
                String description = (String) message.get("Description");
                String subject = (String) message.get("Subject");

                Message receivedmessage = new Message(sender, receiver, description, subject);
                
                receivedMessages.add(receivedmessage);
            }
        }
    }

    public void showReceivedMessages() {
        calculateReceivedMessages();
        for(Message message : receivedMessages) {
            System.out.println("Sender: " + message.getSenderID());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Description: " + message.getDescription());
        }
    }

    public void calculateSentMessages() {

        for (Object messageObj : messageJson) {
            JSONObject message = (JSONObject) messageObj;
            String receiver = (String) message.get("Receiver");
            String sender = (String) message.get("Sender");
            if(sender.equals(session.getUser().getID())) {
                String description = (String) message.get("Description");
                String subject = (String) message.get("Subject");

                Message sentMessage = new Message(sender, receiver, description, subject);

                sentMessages.add(sentMessage);                
            }
        }

    }

    public void showSentMessages() {
        calculateSentMessages();
        for(Message message : sentMessages) {
            System.out.println("Receiver: " + message.getReceiverID());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Description: " + message.getDescription());
        }
    }

    //send message specific person
    public void newMessage(String description , String subject, String receiver) {
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
            System.out.println("Error: " + e);
        }
    }
}
