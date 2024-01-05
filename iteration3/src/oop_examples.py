"""Encapsulation"""
# Encapsulation is the process of restricting access to methods and variables in a class to prevent direct data modification, thus preventing accidental modification of data.
# Encapsulation is a way to achieve data hiding.


class Computer:
    def __init__(self):
        self.__maxprice = 900

    def sell(self):
        print("Selling Price: {}".format(self.__maxprice))

    def setMaxPrice(self, price):
        self.__maxprice = price


# Creating an instance of the Computer class
c = Computer()

# Accessing the private variable using the getter method
c.sell()

# Trying to directly change the private variable (won't affect the actual attribute)
c.__maxprice = 1500
c.sell()

# Using the setter function to modify the private variable
c.setMaxPrice(1000)
c.sell()

# Attempting to show the private variable using the getter function (not recommended)
# This won't work as intended; the private variable is not directly accessible from outside the class
# Instead, the instance has a new attribute "__maxprice"
print(c.__maxprice)

"""Encapsulation Using Modern Python"""
# In Python, private attributes are denoted using an underscore as a prefix, i.e., single "_" or double "__".
# Example:


class Computer:
    def __init__(self):
        self.__maxprice = 900

    def sell(self):
        print("Selling Price: {}".format(self.__maxprice))

    def setMaxPrice(self, price):
        self.__maxprice = price

    def getMaxPrice(self):
        return self.__maxprice


# Creating an instance of the Computer class
c = Computer()
c.sell()

# Accessing the private variable using the getter method
print("Current Max Price:", c.getMaxPrice())

"""Abstraction"""
# Abstraction is the concept of hiding internal details and showing only functionalities.
# It is often achieved through abstract classes and interfaces.
# Example:

from abc import ABC, abstractmethod


class Polygon(ABC):
    @abstractmethod
    def noofsides(self):
        pass


# Concrete classes implementing the abstract class
class Triangle(Polygon):
    def noofsides(self):
        print("I have 3 sides")


class Quadrilateral(Polygon):
    def noofsides(self):
        print("I have 4 sides")


# Creating instances of concrete classes
R = Triangle()
R.noofsides()

K = Quadrilateral()
K.noofsides()

"""Polymorphism"""
# Polymorphism is the ability to use a common interface for multiple data types.
# In Python, polymorphism can be achieved through method overriding.
# Example:


class Bird:
    def intro(self):
        print("There are many types of birds.")

    def flight(self):
        print("Most of the birds can fly but some cannot.")


class Sparrow(Bird):
    def flight(self):
        print("Sparrows can fly.")


class Ostrich(Bird):
    def flight(self):
        print("Ostriches cannot fly.")


# Creating instances of different bird types
obj_bird = Bird()
obj_spr = Sparrow()
obj_ost = Ostrich()

# Demonstrating polymorphism
obj_bird.intro()
obj_bird.flight()

obj_spr.intro()
obj_spr.flight()

obj_ost.intro()
obj_ost.flight()

"""Inheritance"""
# Inheritance is the capability of one class to derive or inherit properties from another class.
# Example:


class Bird:
    def __init__(self):
        print("Bird is ready")

    def whoisThis(self):
        print("Bird")

    def swim(self):
        print("Swim faster")


# Child class inheriting from the Bird class
class Penguin(Bird):
    def __init__(self):
        # Call the constructor of the parent class using super()
        super().__init__()
        print("Penguin is ready")

    def whoisThis(self):
        print("Penguin")

    def run(self):
        print("Run faster")


# Creating an instance of the Penguin class
peggy = Penguin()

# Demonstrating inheritance and method overriding
peggy.whoisThis()
peggy.swim()
peggy.run()
