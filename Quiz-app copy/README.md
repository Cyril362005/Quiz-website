# Quiz Application

This project contains a Java Swing desktop quiz application. It requires the FlatLaf look-and-feel library and the MySQL Connector/J driver.

## Build

Download `flatlaf.jar` and `mysql-connector-java.jar` and place them in the project directory. Compile the sources with:

```bash
javac -cp .:flatlaf.jar:mysql-connector-java.jar src/*.java
```

Run the application with:

```bash
java -cp .:src:flatlaf.jar:mysql-connector-java.jar QuizApplication
```

Note: The UI requires a graphical environment.
