class Colors:
    def __init__(self):
        self.RESET = "\u001b[0m"
        self.RED = "\u001b[31m"
        self.GREEN = "\u001b[32m"
        self.YELLOW = "\u001b[33m"
        self.BLUE = "\u001b[34m"
        self.MAGENTA = "\u001b[35m"
        self.CYAN = "\u001b[36m"
        self.WHITE = "\u001b[37m"
        self.BRIGHT_BLACK = "\u001b[90m"
        self.BRIGHT_RED = "\u001b[91m"
        self.BRIGHT_GREEN = "\u001b[92m"

        self.BOLD = "\u001b[1m"
        self.UNDERLINE = "\u001b[4m"
        self.REVERSED = "\u001b[7m"
        self.ITALIC = "\u001b[3m"

    def get_reset(self):
        return self.RESET
    def get_red(self):
        return self.RED
    def get_green(self):
        return self.GREEN
    def get_yellow(self):
        return self.YELLOW
    def get_blue(self):
        return self.BLUE
    def get_magenta(self):
        return self.MAGENTA
    def get_cyan(self):
        return self.CYAN
    def get_white(self):
        return self.WHITE
    def get_bright_black(self):
        return self.BRIGHT_BLACK
    def get_bright_red(self):
        return self.BRIGHT_RED
    def get_bright_green(self):
        return self.BRIGHT_GREEN
    def get_bold(self):
        return self.BOLD
    def get_underline(self):
        return self.UNDERLINE
    def get_reversed(self):
        return self.REVERSED
    def get_italic(self):
        return self.ITALIC
    