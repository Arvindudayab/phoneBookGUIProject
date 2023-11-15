Navigation to code: phoneBookGUIProject/phoneBook/src/main/java/com/example/phonebook/

This Java project provides a simple graphical user interface (GUI) for managing a phonebook. Users can enter contact information, save it to a file, and search through existing entries.

## Project Structure:

Main.java
Contains the main class (`Main`) that launches the JavaFX application. It handles the setup of the main window, input validation, and 
saving data to the phonebook file.

SearchWindow.java
Defines the `SearchWindow` class responsible for displaying a search window. Users can search for specific entries in the phonebook file. 
It utilizes JavaFX TableView for displaying and searching phonebook data.

phoneBookData.java
A simple JavaBean class representing the structure of phonebook entries. It contains fields such as first name, last name, phone number, 
etc., along with getter and setter methods.
