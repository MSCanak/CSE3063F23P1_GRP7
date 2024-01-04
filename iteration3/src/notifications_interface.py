import json
from datetime import datetime
from colors import Colors
from notification import Notification

class NotificationsInterface:
    def __init__(self, session):
        self.colors = Colors()
        self.person = session.get_user()
        self.notifications = []
        self.input = input
        self._calculate_notifications()

    def _calculate_notifications(self):
        try:
            with open("data/notifications.json", "r") as notification_file:
                notification_object = json.load(notification_file)
            for notification_json in notification_object:
                if notification_json["receiverID"] == self.person.get_id():
                    receiver_id = notification_json["receiverID"]
                    description = notification_json["description"]
                    sender_id = notification_json["senderID"]
                    is_read = notification_json["isRead"]
                    notification_id = notification_json["notificationID"]

                    new_notification = Notification(
                        receiver_id, description, sender_id, is_read, notification_id
                    )
                    self.notifications.append(new_notification)

        except Exception as e:
            print(self.colors.get_yellow() + "Error: " + str(e) + self.colors.get_reset())

    def _show_notifications(self):
        if not self.notifications:
            print(
                self.colors.get_yellow()
                + "You have no notifications."
                + self.colors.get_reset()
            )
            print(
                "You will be "
                + self.colors.get_green()
                + "directed"
                + self.colors.get_reset()
                + " to the Main Menu!"
            )
            return
            for i, notification in enumerate(self.notifications, 1):
                is_read = "Read" if notification.is_read else "Unread"
                if is_read == "Read":
                    color = self.colors.get_blue()
                    is_new = ""
                else:
                    color = self.colors.get_green()
                    is_new = "New"

                print(
                    f"{i}  {color}{is_new} Notification {self.colors.get_reset()}\t{notification.time_sent}"
                )
                print("\tDescription: ")
                print(
                    f"{self.colors.get_blue()}\t  o  {self.colors.get_green()}{self.colors.get_italic()}{notification.description}{self.colors.get_reset()}"
                )
                print(self.colors.get_reset())

    def _notifications_menu(self):
        while True:
            print(
                f"{self.colors.get_red()}{self.colors.get_bold()}\n>> Notifications Menu{self.colors.get_reset()}"
            )

            # notifications
            self._show_notifications()
            if not self.notifications:
                return

            print(f"{self.colors.get_yellow()}1{self.colors.get_reset()}.   Mark as Read")
            print(
                f"{self.colors.get_yellow()}2{self.colors.get_reset()}.   Delete Notification"
            )
            print(
                f"{self.colors.get_yellow()}0{self.colors.get_reset()}.   Go back to the Main Menu"
            )
            user_input = input(
                f"\n{self.colors.get_blue()}--> {self.colors.get_reset()}What do you want to do?   "
            )
            choice = user_input[0]

            switch_choice = {
                "1": lambda: self.mark_as_read(),
                "2": lambda: self.delete_notification(),
                "0": lambda: exit(),
                "default": lambda: print(
                    f"{self.colors.get_yellow()}Invalid input! Please try again.{self.colors.get_reset()}"
                ),
            }

            switch_choice.get(choice, switch_choice["default"])()

    def _mark_as_read(self):
        while True:
            print(
                f"{self.colors.get_red()}{self.colors.get_bold()}\n>> Mark as Read{self.colors.get_reset()}"
            )
            self._show_notifications()

            print(
                f"Select a notification{self.colors.get_blue()} number {self.colors.get_reset()}to mark as read or select {self.colors.get_blue()}x{self.colors.get_reset()} to mark all as read."
            )
            print(
                f"(For example -> {self.colors.get_yellow()}1-2-3 {self.colors.get_reset()}or {self.colors.get_yellow()}x{self.colors.get_reset()})"
            )
            print(
                f"\n{self.colors.get_blue()}--> {self.colors.get_reset()}Which notification do you want to mark as read?   "
            )
            print(
                f"{self.colors.get_yellow()}0{self.colors.get_reset()}.   Go back to the Notifications Menu"
            )

            user_input = input(
                f"{self.colors.get_blue()}--> {self.colors.get_reset()}What do you want to do?   "
            )
            choice = user_input[0]

            switch_choice = {
                '0': lambda: exit(),
                'x': lambda: self._mark_all_as_read(),
                'default': lambda: self._mark_single_as_read(int(choice)),
            }

            switch_choice.get(choice, switch_choice['default'])()

    def _mark_all_as_read(self):
        for notification in self.notifications:
            notification.set_is_read(True)

    def _mark_single_as_read(self, index):
        self.notifications[index - 1].set_is_read(True)


    def _delete_notification(self):
        # notifications with numbers
        while True:
            print(
                self.colors.get_red()
                + self.colors.get_bold()
                + "\n>>> Delete Notification Menu"
                + self.colors.get_reset()
                + self.colors.get_reset()
            )
            self.show_notifications()
            if not self.notifications:
                return

            print(
                "Select a notification number to delete or select x to delete all."
            )
            print(
                f"(For example -> {self.colors.get_yellow()}1{self.colors.get_reset()} or{self.colors.get_yellow()} x{self.colors.get_reset()})"
            )
            print("Which notification do you want to delete? \n")
            print(
                f"{self.colors.get_yellow()}0{self.colors.get_reset()}.  Go back to the Notifications Menu.\n"
            )
            user_input = input(
                f"{self.colors.get_blue()}--> {self.colors.get_reset()}What do you want to do?   "
            )
            choice = user_input[0]

            # go back to notifications menu
            if choice == "0":
                return
            # delete all
            elif choice == "x":
                self.notifications.clear()
                break
            # delete
            else:
                # delete from json
                self._delete_in_json(self.notifications[int(choice) - 1].get_notification_id())
                del self.notifications[int(choice) - 1]
                break

    def _delete_in_json(self, notification_id):
        try:
            with open("./jsons/notifications.json", "r") as notification_file:
                notification_object = json.load(notification_file)

            for notification in notification_object:
                if notification["notificationID"] == notification_id:
                    notification_object.remove(notification)

                    with open("./jsons/notifications.json", "w") as notification_file:
                        json.dump(notification_object, notification_file, indent=2)

        except Exception as e:
            print(
                self.colors.get_yellow() + "Error: " + self.colors.get_reset() + str(e)
            )