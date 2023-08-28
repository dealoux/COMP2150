/**
 * COMP 2150 Summer 2019: Assignment 3 Question 1 "Point of Sale"
 *
 * An order transaction. In addition to the regular transaction features,
 * an order is like a purchase but it allows some items to be on backorder.
 */

package comp2150.pos.server;
import java.util.ArrayList;

class Backorder extends Transaction {
   private ArrayList<TransactionUnit> backOrder;

   public Backorder(String id, int clientID, long time) {
      super(id, clientID, time);
      backOrder = new ArrayList<TransactionUnit>();
   }

   @Override
   public A4POSServer.TransactionType getType() {
      return A4POSServer.TransactionType.BACKORDER;
   }

   @Override
   public boolean canCompleteTransaction(TransactionUnit unit) {
      // Can always complete a backorder
      return true;
   }

   @Override
   public void completeTransaction(TransactionUnit unit, boolean canCompleteAll) {
      int transactionQuantity = unit.getQuantity();
      Item item = unit.getItem();
      int itemQuantity = item.getInStock();
      
      if (transactionQuantity <= itemQuantity) {
         item.reduceInStock(transactionQuantity); 
      } else {
         // backorder
         int backorderQuantity = transactionQuantity - itemQuantity;
         item.reduceInStock(itemQuantity);
         item.increaseBackorder(backorderQuantity);
      }
   }
   
   /**
    * Make the leftovers from an unit in a transaction into a back order.
    * 
    * @param unit the unit that is back ordered
    * @param quantity the quantity of the back order
    * @return true if there is nothing left in the original unit; false otherwise
    */
   public boolean makeBackOrder(TransactionUnit unit, int quantity) {
      backOrder.add(new TransactionUnit(unit.getItem(), quantity));
      return unit.getQuantity() == 0;
   }

   @Override
   public String toString() {
      return "Order " + super.toString() + (backOrder.size() > 0 ? "\n back order: " + backOrder : "");
   }
}