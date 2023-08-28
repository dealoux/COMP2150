Name: Tom Le
Student ID: 7871324

How to build with JUnit 4 on the command line
=============================================

The current directory must contain:
 - all of the source code (java files)
 - the junit4.jar file
 - the a2inventory.txt input file

To compile the program:

javac -cp .:junit4.jar TestA2POSServer.java

To run the tests:

java -cp .:junit4.jar org.junit.runner.JUnitCore TestA2POSServer

Description:
/**
 * Server.java
 *
 * COMP 2150 SECTION A01
 * INSTRUCTOR Riley Wall
 * ASSIGNMENT Assignment 2, question 1
 * @author Tom Le, 7871324
 * @version July 3rd 2022
 *
 * REMARKS: implementation of the server of the client-server point of sale system
 */

Platform: Ubuntu 20.4 WSL on Windows 11, VS Code, compiled with Java 8