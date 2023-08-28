/**
 * COMP 2150 Summer 2019: Assignment 3 Question 1 "Point of Sale"
 *
 * A purchase transaction.
 */

public class Purchase extends Transaction {

   public Purchase(String id, long time) {
      super(id, time);
   }

   @Override
   public A3POSServer.TransactionType getType() {
      return A3POSServer.TransactionType.PURCHASE;
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
