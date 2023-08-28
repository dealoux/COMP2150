/**
 * COMP 2150 Summer 2022: Assignment 2 Question 1 "Point of Sale"
 *
 * An abstract transaction (currently, a purchase or return). Includes the transaction
 * ID, the time the transaction started, and a list of the items and their quantities.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Transaction {
   private String id;
   private long time;
   private Map<String, TransactionUnit> units;
   private boolean complete;

   public Transaction(String id, long time) {
      this.id = id;
      this.time = time;
      this.units = new HashMap<>();
      this.complete = false;
   }

   /**
    * Add an item to the transaction.
    * 
    * @param  item the inventory item to add
    * @param  quantity the quantity of the item
    * @return true if the add was successful or false if not (invalid quantity)
    */
   public boolean addItem(Item item, int quantity) {
      boolean result = true;

      TransactionUnit unit = units.get(item.getCode());
      if (unit == null) {
         if (quantity < 0) {
            result = false;
         } else {
            units.put(item.getCode(), new TransactionUnit(item, quantity));
         }
      } else {
         if (unit.getQuantity() + quantity < 0) {
            result = false;
         } else if (unit.getQuantity() + quantity == 0) {
            units.remove(item.getCode());
         } else {
            unit.changeQuantity(unit.getQuantity() + quantity);
         }
      }

      return result;
   }

   /**
    * Complete the transaction.
    */
   public void complete() {
      assert complete != true;
      complete = true;
   }

   /**
    * Determine if the given ID matches the transaction ID.
    * 
    * @param id the id to check
    * @return true if it is the same as the transaction ID; false otherwise
    */
   public boolean matchID(String id) {
      return id.equals(this.id);
   }

   public String getID() {
      return id;
   }

   public boolean isComplete() {
      return complete;
   }

   public abstract A2POSServer.TransactionType getType();

   public int itemCount() {
      return units.size();
   }

   /**
    * Get the total quantity of the transaction.
    * 
    * @return the sum of the quantities of the items in the transaction
    */
   public int totalQuantity() {
      int count = 0;
      for (TransactionUnit unit: units.values()) {
         count += unit.getQuantity();
      }
      return count;
   }

   /**
    * Get the total cost of the transaction.
    * 
    * @return the sum of the cost of the items times quantities in the transaction
    */
   public int totalCost() {
      int total = 0;

      for (TransactionUnit item : units.values())
         total += item.getTotalCost();

      return total;
   }

   @Override
   public String toString() {
      int cost = totalCost();
      return "ID: " + id + " time: " + time + "\n items: " + units + "\n value: $" + (cost / 100) + "." + (cost % 100 < 10 ? "0" : "") + (cost % 100);
   }
}
