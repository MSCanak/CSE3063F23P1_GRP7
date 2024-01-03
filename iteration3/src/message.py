from datetime import datetime


class Message:
    def __init__(
        self, sender_id: str, receiver_id: str, description: str, subject: str
    ) -> None:
        self.__sender_id = sender_id
        self.__receiver_id = receiver_id
        self.__description = description
        self.__subject = subject
        self.__time_sent = datetime.now()

    def get_sender_id(self) -> str:
        return self.__sender_id

    def get_receiver_id(self) -> str:
        return self.__receiver_id

    def get_description(self) -> str:
        return self.__description

    def get_subject(self) -> str:
        return self.__subject

    def get_time_sent(self) -> datetime:
        return self.__time_sent

    def set_sender_id(self, sender_id: str) -> None:
        self.__sender_id = sender_id

    def set_receiver_id(self, receiver_id: str) -> None:
        self.__receiver_id = receiver_id

    def set_description(self, description: str) -> None:
        self.__description = description

    def set_subject(self, subject: str) -> None:
        self.__subject = subject

    def set_time_sent(self, time_sent: str) -> None:
        self.__time_sent = time_sent
