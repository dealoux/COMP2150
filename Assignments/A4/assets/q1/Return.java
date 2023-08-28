/**
 * COMP 2150 Summer 2019: Assignment 3 Question 1 "Point of Sale"
 *
 * A return transaction.
 */

public class Return extends Transaction {

   public Return(String id, long time) {
      super(id, time);
   }

   @Override
   public A3POSServer.TransactionType getType() {
      return A3POSServer.TransactionType.RETURN;
   }

   @Override
   public boolean canCompleteTransaction(TransactionUnit unit) {
      // Can always complete a return
      return true;
   }

   @Override
   public void completeTransaction(TransactionUnit unit, boolean canCompleteAll) {
      // has no effect on inventory
   }

   @Override
   public String toString() {
      return "Return " + super.toString();
   }
}
