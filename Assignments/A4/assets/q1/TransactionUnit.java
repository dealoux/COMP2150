/**
 * COMP 2150 Summer 2019: Assignment 3 Question 1 "Point of Sale"
 *
 * An item in a transaction. Points to the item in the inventory and the
 * quantity.
 */

public class TransactionUnit {
   private Item item;
   private int quantity;
   
   public TransactionUnit(Item item, int quantity) {
      this.item = item;
      this.quantity = quantity;
   }
   
   public Item getItem() {
      return item;
   }

   public int getQuantity() {
      return quantity;
   }

   public boolean changeQuantity(int quantity) {
      if (quantity < 0) {
         return false;
      }
      
      this.quantity = quantity;
      return true;
   }
   
   /**
    * Get the total value of this transaction item.
    * 
    * @return the cost of the item times the quantity
    */
   public int getTotalCost() {
      return item.getCost() * quantity;
   }

   @Override
   public String toString() {
      return item.getCode() + " (" + quantity + ")";
   }
}
