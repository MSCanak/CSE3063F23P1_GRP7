import json
import os
from datetime import datetime


class Notification:
    def __init__(
        self,
        receiver_id: str,
        description: str,
        sender_id: str,
        is_read: bool = False,
        notification_id: int = None,
    ) -> None:
        self.__receiver_id = receiver_id
        self.__description = description
        self.__sender_id = sender_id
        self.__time_sent = datetime.now()
        self.__is_read = is_read
        self.__notification_id = (
            self.__notification_identifier()
            if notification_id is None
            else notification_id
        )

    def __notification_identifier(self) -> int:
        try:
            with open("./jsons/notifications.json", "r") as file:
                notifications = json.load(file)

            return len(notifications) + 1

        except Exception as e:
            print("Error:", e)
            return 0

    def send_notification(self, sender_id: str) -> bool:
        try:
            with open("./jsons/notifications.json", "r") as file:
                notifications: list = json.load(file)

            new_notification: dict = {
                "receiverID": self.__receiver_id,
                "isRead": self.__is_read,
                "description": self.__description,
                "timeSent": str(self.__time_sent),
                "senderID": sender_id,
                "notificationID": self.__notification_id,
            }

            notifications.append(new_notification)

            with open("./jsons/notifications.json", "w") as file:
                json.dump(notifications, file, indent=2)

            return True

        except Exception as e:
            print("Error:", e)
            return False

    def get_receiver_id(self) -> str:
        return self.__receiver_id

    def get_is_read(self) -> bool:
        return self.__is_read

    def get_description(self) -> str:
        return self.__description

    def get_time_sent(self) -> datetime:
        return self.__time_sent

    def get_sender_id(self) -> str:
        return self.__sender_id

    def get_notification_id(self) -> int:
        return self.__notification_id

    def set_sender_id(self, sender_id: str) -> None:
        self.__sender_id = sender_id

    def set_is_read(self, is_read: bool) -> None:
        try:
            with open("./jsons/notifications.json", "r") as file:
                notifications: list = json.load(file)

            for notification in notifications:
                if notification["notification_id"] == self.__notification_id:
                    notification["is_read"] = is_read

            with open("./jsons/notifications.json", "w") as file:
                json.dump(notifications, file, indent=2)

        except Exception as e:
            print("Error:", e)

    def set_description(self, description: str) -> None:
        self.__description = description

    def set_notification_id(self, notification_id: int) -> None:
        self.__notification_id = notification_id
