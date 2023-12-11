public interface MessagesMenu {
    // shows the messages menu and let user take action in message box
    public void messagesMenu();
    // shows the receieved messages menu and let user take action in received messages box
    public void reveiveMessagesMenu();
    // shows the sent messages menu and let user take action in sent messages box
    public void sentMessagesMenu();
    // calcultes received messages that is sent to user by reading json file that 
    public void calculateReceivedMessages();
    // shows received messages that is calculated by calculateReceivedMessages method
    public void showReceivedMessages();
    // calcultes sent messages that is sent by user by reading json file that
    public void calculateSentMessages();
    // shows sent messages that is calculated by calculateSentMessages method
    public void showSentMessages();
    // let user to send new message to another user
    public void newMessage();
}
