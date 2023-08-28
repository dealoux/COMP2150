/**
 * COMP 2150 Summer 2022: Assignment 2 Question 1 "Point of Sale"
 *
 * A class representing an item in the inventory. Includes the item
 * code, its description, and its cost.
 */

public class Item {

   private String code;
   private String description;
   private int cost;

   public Item(String code, String description, int cost) {
      this.code = code;
      this.description = description;
      this.cost = cost;
   }

   /**
    * Determine if the given code matches this item.
    * 
    * @param code the code to check
    * @return true if the code matches this item; false otherwise
    */
   public boolean matchCode(String code) {
      return code.equals(this.code);
   }

   public String getCode() {
      return code;
   }

   public int getCost() {
      return cost;
   }

   @Override
   public String toString() {
      return "Code: " + code + " description: " + description + " cost: $" + (cost / 100) + "." + (cost % 100 < 10 ? "0" : "") + (cost % 100);
   }
}
