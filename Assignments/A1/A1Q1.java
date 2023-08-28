/**
 * COMP 2150 Summer 2022 Assignment 1 Question 1
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class A1Q1 {
 
 public static void printReservationList(AllDepots parkades, Month month) {
  ArrayList<Renter> parkers = parkades.getRentersByMonth(month);

  System.out.println("\nReservation List:");
  parkers.sort(Renter.getNameComparator());
  for (Renter p: parkers) {
   System.out.println(" " + p.toReservationList());
  }
 }

 public static void main(String[] args) {
  AllDepots parkades;
  ArrayList<Event> eventDays = new ArrayList<>();
  String line;
  boolean done;

  try {
   Scanner in = new Scanner(new FileInputStream(args.length == 0 ? "a1q1.txt" : args[0]));
   //Insert your code here
   
   in.close();
  } catch (FileNotFoundException fnfe) {
   System.out.println("File not found: " + fnfe.getMessage());
  }
 }

}
