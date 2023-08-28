/**
 * COMP 2150 Summer 2019: Assignment 3 Question 1 "Point of Sale"
 *
 * A return transaction.
 */

package comp2150.pos.server;
class Return extends Transaction {

   public Return(String id, int clientID, long time) {
      super(id, clientID, time);
   }

   @Override
   public A4POSServer.TransactionType getType() {
      return A4POSServer.TransactionType.RETURN;
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