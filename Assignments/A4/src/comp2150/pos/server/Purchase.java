/**
 * COMP 2150 Summer 2019: Assignment 3 Question 1 "Point of Sale"
 *
 * A purchase transaction.
 */

package comp2150.pos.server;
class Purchase extends Transaction {

   public Purchase(String id, int clientID, long time) {
      super(id, clientID, time);
   }

   @Override
   public A4POSServer.TransactionType getType() {
      return A4POSServer.TransactionType.PURCHASE;
   }

   @Override
   public boolean canCompleteTransaction(TransactionUnit unit) {
      return unit.getQuantity() <= unit.getItem().getInStock();
   }

   @Override
   public void completeTransaction(TransactionUnit unit, boolean canCompleteAll) {
      if (canCompleteAll) {
         unit.getItem().reduceInStock(unit.getQuantity());
      } else {
         // clear the transaction
         unit.changeQuantity(0);
      }
   }

   @Override
   public String toString() {
      return "Purchase " + super.toString();
   }
}