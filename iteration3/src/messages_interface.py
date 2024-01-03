import json
from typing import List

from colors import Colors
from session import Session
from message import Message

class MessagesInterface:
    def __init__(self, session: Session):
        self.__colors = Colors()
        self.__message_object = None
        self.__message_json = None
        self.__session = session
        self.__received_messages: List[Message] = []
        self.__sent_messages: List[Message] = []

        try:
            with open("./jsons/messages.json") as file:
                self.__message_json = json.load(file)
        except Exception as e:
            print(f"Error: {e}")

    def messages_menu(self):
        choice = 0
        while True:
            print(self.__colors.get_red() + self.__colors.get_bold() + "\n>> Messages Menu\n" + self.__colors.get_reset())
            print(self.__colors.get_yellow() + "1" + self.__colors.get_reset() + ".   " + self.__colors.get_green() +
                  "New Message" + self.__colors.get_reset())
            print(self.__colors.get_yellow() + "2" + self.__colors.get_reset() + ".   Received Messages")
            print(self.__colors.get_yellow() + "3" + self.__colors.get_reset() + ".   Sent Messages")
            print(self.__colors.get_yellow() + "0" + self.__colors.get_reset() +
                  ".   Go back to the Course Registration System Menu")

            print("\n" + self.__colors.get_blue() + "--> " + self.__colors.get_reset() +
                  "What do you want to do?   ", end="")
            choice = int(input().strip())

            if choice == 1:
                print(self.__colors.get_yellow() + "Enter receiver ID: " + self.__colors.get_reset(), end="")
                receiver = input().strip()
                print(self.__colors.get_yellow() + "\nEnter subject " + self.__colors.get_reset() +
                      "(Press Enter twice to go to the next line): ")
                subject = self.__read_multi_line_input()
                print(self.__colors.get_yellow() + "\nEnter description " + self.__colors.get_reset() +
                      "(Press Enter twice to go to the next line): ")
                description = self.__read_multi_line_input()
                self.__new_message(description, subject, receiver)
            elif choice == 2:
                self.__show_received_messages()
            elif choice == 3:
                self.__show_sent_messages()
            elif choice == 0:
                return
            else:
                print(self.__colors.get_yellow() + "Invalid choice! Please try again." + self.__colors.get_reset())

    def __calculate_received_messages(self):
        for message_obj in self.__message_json:
            message = json.loads(message_obj)
            receiver = message.get("Receiver")
            sender = message.get("Sender")

            if receiver == self.__session.get_user().get_id():
                description = message.get("Description")
                subject = message.get("Subject")

                received_message = Message(sender, receiver, description, subject)
                self.__received_messages.append(received_message)

    def __show_received_messages(self):
        self.__calculate_received_messages()
        print(self.__colors.get_red() + self.__colors.get_bold() +
              "\n>>> Received Messages\n" + self.__colors.get_reset())
        i = 1
        if not self.__received_messages:
            print(self.__colors.get_yellow() + "You have no received messages.\n" + self.__colors.get_reset())
            return

        for message in self.__received_messages:
            print(self.__colors.get_blue() + f"Received Message {i}:" + self.__colors.get_reset())
            print(self.__colors.get_yellow() + f"\tSender: {message.get_sender_id()}" + self.__colors.get_reset())
            print(self.__colors.get_yellow() + f"\tSubject: {message.get_subject()}" + self.__colors.get_reset())
            print(self.__colors.get_yellow() + f"\tDescription: {message.get_description()}" + self.__colors.get_reset())
            i += 1
        print("")

    def __calculate_sent_messages(self):
        for message_obj in self.__message_json:
            message = json.loads(message_obj)
            receiver = message.get("Receiver")
            sender = message.get("Sender")
            if sender == self.__session.get_user().get_id():
                description = message.get("Description")
                subject = message.get("Subject")

                sent_message = Message(sender, receiver, description, subject)
                self.__sent_messages.append(sent_message)

    def __show_sent_messages(self):
        self.__calculate_sent_messages()
        i = 1
        print(self.__colors.get_red() + self.__colors.get_bold() +
              "\n>> Sent Messages\n" + self.__colors.get_reset())
        if not self.__sent_messages:
            print(self.__colors.get_yellow() + "You have no sent messages.\n" + self.__colors.get_reset())
            return

        for message in self.__sent_messages:
            print(self.__colors.get_blue() + f"Sent Message {i}:" + self.__colors.get_reset())
            print(self.__colors.get_yellow() + f"\tReceiver: {message.get_receiver_id()}" + self.__colors.get_reset())
            print(self.__colors.get_yellow() + f"\tSubject: {message.get_subject()}" + self.__colors.get_reset())
            print(self.__colors.get_yellow() + f"\tDescription: {message.get_description()}" + self.__colors.get_reset())
            i += 1
        print("")

    # this method creates a new message and adds it to the messages.json file
    def __new_message(self, description, subject, receiver):
        new_message = {
            "Sender": self.__session.get_user().get_id(),
            "Receiver": receiver,
            "Subject": subject,
            "Description": description,
        }

        self.__message_json.append(json.dumps(new_message))

        try:
            with open("./jsons/messages.json", "w") as file:
                json.dump(self.__message_json, file)
        except IOError as e:
            print(f"Error: {e}")

    def __read_multi_line_input(self):
        lines = []
        while True:
            line = input()
            if not line.strip():
                break
            lines.append(line)
        return "\n".join(lines)
