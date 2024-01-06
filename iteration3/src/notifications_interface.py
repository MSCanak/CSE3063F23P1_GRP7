import json
from colors import Colors
from notification import Notification
from session import Session


class NotificationsInterface:
    def __init__(self, session: Session):
        self.__colors = Colors()
        self.__person = session.get_user()
        self.__notifications: list[Notification] = []
        self.__calculate_notifications()

    def __calculate_notifications(self):
        try:
            with open("jsons/notifications.json", "r") as notification_file:
                notification_object = json.load(notification_file)
            for notification_json in notification_object:
                if notification_json["receiverID"] == self.__person.get_id():
                    receiver_id = notification_json["receiverID"]
                    description = notification_json["description"]
                    sender_id = notification_json["senderID"]
                    is_read = notification_json["isRead"]
                    notification_id = notification_json["notificationID"]

                    new_notification = Notification(
                        receiver_id, description, sender_id, is_read, notification_id
                    )
                    self.__notifications.append(new_notification)

        except Exception as e:
            print(
                self.__colors.get_yellow()
                + "Error: "
                + str(e)
                + self.__colors.get_reset()
            )

    def __show_notifications(self):
        if not self.__notifications:
            print(
                self.__colors.get_yellow()
                + "You have no notifications."
                + self.__colors.get_reset()
            )
            print(
                "You will be "
                + self.__colors.get_green()
                + "directed"
                + self.__colors.get_reset()
                + " to the Main Menu!"
            )
            return
        for i, notification in enumerate(self.__notifications, 1):
            is_read = "Read" if notification.get_is_read() else "Unread"
            if is_read == "Read":
                color = self.__colors.get_blue()
                is_new = ""
            else:
                color = self.__colors.get_green()
                is_new = "New"

            print(
                f"{i}  {color}{is_new} Notification {self.__colors.get_reset()}\t{notification.get_time_sent()}"
            )
            print("\tDescription: ")
            print(
                f"{self.__colors.get_blue()}\t  o  {self.__colors.get_green()}{self.__colors.get_italic()}{notification.get_description()}{self.__colors.get_reset()}"
            )
            print(self.__colors.get_reset())

    def notifications_menu(self):
        while True:
            print(
                f"{self.__colors.get_red()}{self.__colors.get_bold()}\n>> Notifications Menu{self.__colors.get_reset()}"
            )

            # notifications
            self.__show_notifications()
            if not self.__notifications:
                return

            print(
                f"{self.__colors.get_yellow()}1{self.__colors.get_reset()}.   Mark as Read"
            )
            print(
                f"{self.__colors.get_yellow()}2{self.__colors.get_reset()}.   Delete Notification"
            )
            print(
                f"{self.__colors.get_yellow()}0{self.__colors.get_reset()}.   Go back to the Main Menu"
            )
            user_input = input(
                f"\n{self.__colors.get_blue()}--> {self.__colors.get_reset()}What do you want to do?   "
            )
            choice = user_input[0]

            switch_choice = {
                "1": lambda: self.__mark_as_read(),
                "2": lambda: self.__delete_notification(),
                "0": lambda: exit(),
                "default": lambda: print(
                    f"{self.__colors.get_yellow()}Invalid input! Please try again.{self.__colors.get_reset()}"
                ),
            }

            switch_choice.get(choice, switch_choice["default"])()

    def __mark_as_read(self):
        while True:
            print(
                f"{self.__colors.get_red()}{self.__colors.get_bold()}\n>> Mark as Read{self.__colors.get_reset()}"
            )
            self.__show_notifications()

            print(
                f"Select a notification{self.__colors.get_blue()} number {self.__colors.get_reset()}to mark as read or select {self.__colors.get_blue()}x{self.__colors.get_reset()} to mark all as read."
            )
            print(
                f"(For example -> {self.__colors.get_yellow()}1-2-3 {self.__colors.get_reset()}or {self.__colors.get_yellow()}x{self.__colors.get_reset()})"
            )
            print(
                f"\n{self.__colors.get_blue()}--> {self.__colors.get_reset()}Which notification do you want to mark as read?   "
            )
            print(
                f"{self.__colors.get_yellow()}0{self.__colors.get_reset()}.   Go back to the Notifications Menu"
            )

            user_input = input(
                f"{self.__colors.get_blue()}--> {self.__colors.get_reset()}What do you want to do?   "
            )
            choice = user_input[0]

            switch_choice = {
                "0": lambda: exit(),
                "x": lambda: self.__mark_all_as_read(),
                "default": lambda: self.__mark_single_as_read(int(choice)),
            }

            switch_choice.get(choice, switch_choice["default"])()

    def __mark_all_as_read(self):
        for notification in self.__notifications:
            notification.set_is_read(True)

    def __mark_single_as_read(self, index: int):
        self.__notifications[index - 1].set_is_read(True)

    def __delete_notification(self):
        # notifications with numbers
        while True:
            print(
                self.__colors.get_red()
                + self.__colors.get_bold()
                + "\n>>> Delete Notification Menu"
                + self.__colors.get_reset()
                + self.__colors.get_reset()
            )
            self.__show_notifications()
            if not self.__notifications:
                return

            print("Select a notification number to delete or select x to delete all.")
            print(
                f"(For example -> {self.__colors.get_yellow()}1{self.__colors.get_reset()} or{self.__colors.get_yellow()} x{self.__colors.get_reset()})"
            )
            print("Which notification do you want to delete? \n")
            print(
                f"{self.__colors.get_yellow()}0{self.__colors.get_reset()}.  Go back to the Notifications Menu.\n"
            )
            user_input = input(
                f"{self.__colors.get_blue()}--> {self.__colors.get_reset()}What do you want to do?   "
            )
            choice = user_input[0]

            # go back to notifications menu
            if choice == "0":
                return
            # delete all
            elif choice == "x":
                self.__notifications.clear()
                break
            # delete
            else:
                # delete from json
                self.__delete_in_json(
                    self.__notifications[int(choice) - 1].get_notification_id()
                )
                del self.__notifications[int(choice) - 1]
                break

    def __delete_in_json(self, notification_id):
        try:
            with open("./jsons/notifications.json", "r") as notification_file:
                notification_object: list = json.load(notification_file)

            for notification in notification_object:
                if notification["notificationID"] == notification_id:
                    notification_object.remove(notification)

                    with open("./jsons/notifications.json", "w") as notification_file:
                        json.dump(notification_object, notification_file, indent=2)

        except Exception as e:
            print(
                self.__colors.get_yellow()
                + "Error: "
                + self.__colors.get_reset()
                + str(e)
            )
