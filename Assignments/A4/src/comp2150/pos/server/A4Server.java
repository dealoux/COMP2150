/**
 * COMP 2150 Summer 2019: Assignment 4 Question 1 "Point of Sale"
 *
 * This interface defines the communication between the point of sale client
 * and the server. Messages to the server are sent as method calls, and
 * information is returned as return values.
 *
 * New for assignment 4: order cancellation
 */
/**
 * A4Server.java
 *
 * COMP 2150 SECTION A01
 * INSTRUCTOR Riley Wall
 * ASSIGNMENT Assignment 4
 * @author Tom Le, 7871324
 * @version August 13 2022
 *
 * REMARKS: Singleton instance of the server
 */

package comp2150.pos.server;

public class A4Server {
   private static A4Server instance = null;
   private A4POSServer server;

   // constructor
   private A4Server(){
      server = new A4ModelPOSServer("inventory.txt");
   }

   public static A4Server getInstance(){
      if(instance == null){
         instance = new A4Server();
      }

      return instance;
   }

   public A4POSServer getServer() {
      return server;
   }
}