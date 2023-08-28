/**
 * COMP 2150 Summer 2022: Assignment 2 Question 1 "Point of Sale"
 *
 * A return transaction.
 */

public class Return extends Transaction {

   public Return(String id, long time) {
      super(id, time);
   }

   @Override
   public A2POSServer.TransactionType getType() {
      return A2POSServer.TransactionType.RETURN;
   }

   @Override
   public String toString() {
      return "Return " + super.toString();
   }
}
