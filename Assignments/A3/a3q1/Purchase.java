/**
 * COMP 2150 Summer 2022: Assignment 2 Question 1 "Point of Sale"
 *
 * A purchase transaction.
 */

public class Purchase extends Transaction {

   public Purchase(String id, long time) {
      super(id, time);
   }

   @Override
   public A2POSServer.TransactionType getType() {
      return A2POSServer.TransactionType.PURCHASE;
   }

   @Override
   public String toString() {
      return "Purchase " + super.toString();
   }
}
