/**
 * COMP 2150 Summer 2022: Assignment 2 Question 1 "Point of Sale"
 *
 * Server class implementing the POSServer interface. Stores inventory
 * and transactions.
 */

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;

public class A2ModelPOSServer implements A2POSServer {
   private TreeMap<String,Item> inventory;
   private HashMap<String,Transaction> transactions;
   private static int lastID = 1;

   /**
    * Get the initial inventory from the given file.
    * 
    * @param inventoryFile the name of the file to read inventory from
    */
   public A2ModelPOSServer(String inventoryFile) {
      inventory = new TreeMap<>();
      transactions = new HashMap<>();

      String msg = readInventory(inventoryFile);
      if (msg != null) {
         System.out.println(msg);
      }
   }

   /**
    * Read the contents of a file into the inventory.
    * 
    * @param inventoryFile the name of the file
    * @return null if successful; otherwise, a message describing the error(s)
    */
   private String readInventory(String inventoryFile) {
      String msg = "";
      BufferedReader in;
      String line;
      String[] tokens;
      int cost;

      try {
         in = new BufferedReader(new FileReader(inventoryFile));

         line = in.readLine();
         while (line != null) {
            tokens = line.split(",");
            if (tokens.length != 3) {
               msg += "Invalid line: " + line + "\n";
            } else {
               try {
                  cost = Integer.parseInt(tokens[2]);
               } catch (NumberFormatException nfe) {
                  cost = -1;
               }
               if (cost < 0)
                  msg += "Invalid cost: " + line + "\n";
               else
                  inventory.put(tokens[0], new Item(tokens[0], tokens[1], cost));
            }
            line = in.readLine();
         }
      } catch (IOException ioe) {
         msg += ioe.getMessage();
      }

      return msg.length() == 0 ? null : msg;
   }

   public String createTransaction(TransactionType type, long time) {
      Transaction t;
      String tID = "" + lastID;

      if (type == TransactionType.PURCHASE)
         t = new Purchase(tID, time);
      else
         t = new Return(tID, time);
      lastID++;

      transactions.put(tID, t);

      return t.getID();
   }

   public String addItemToTransaction(String id, String code, int quantity) {
      String result = null;
      Transaction trans = transactions.get(id);
      Item item;

      if (trans == null) {
         result = "Unable to find transaction " + id + " to add an item";
      } else if (code == null) {
         result = "Invalid item code";
      } else if (trans.isComplete()) {
         result = "Transaction " + id + " already completed";
      } else {
         item = inventory.get(code);
         if (item == null) {
            result = "Unable to find item " + code + " in inventory";
         } else {
            if (!trans.addItem(item, quantity)) {
               result = "Invalid quantity " + quantity + " of item " + code;
            }
         }
      }

      return result;
   }

   public String completeTransaction(String id) {
      String result = null;
      Transaction t = transactions.get(id);

      if (t == null)
         result = "Unable to find transaction " + id;
      else if (t.isComplete())
         result = "Transaction already completed " + id;
      else
         t.complete();

      return result;
   }

   public String queryTransaction(String id, TransactionQuery query) {
      String result = null;
      Transaction t = transactions.get(id);

      if (t != null) {
         switch (query) {
         case TYPE:
            result = t.getType().toString();
            break;
         case ITEM_COUNT:
            result = Integer.toString(t.itemCount());
            break;
         case TOTAL_QUANTITY:
            result = Integer.toString(t.totalQuantity());
            break;
         case TOTAL_COST:
            result = Integer.toString(t.totalCost());
            break;
         case IS_COMPLETE:
            result = Boolean.toString(t.isComplete());
            break;
         }
      }

      return result;
   }

   public String toString(String id) {
      String result = null;
      Transaction t = transactions.get(id);

      if (t != null)
         result = t.toString();

      return result;
   }

   public String queryServer(ServerQuery query) {
      String result = null;

      switch (query) {
      case INVENTORY_COUNT:
         result = Integer.toString(inventory.size());
         break;
      case TRANSACTION_COMPLETED_COUNT:
         result = Integer.toString(countCompleteTransactions());
         break;
      case TRANSACTION_IN_PROGRESS_COUNT:
         result = Integer.toString(transactions.size() - countCompleteTransactions());
         break;
      }

      return result;
   }

   private int countCompleteTransactions() {
      int count = 0;
      for (Map.Entry<String,Transaction> entry: transactions.entrySet()) {
         if (entry.getValue().isComplete()) {
            count++;
         }
      }
      return count;
   }

   @Override
   public String toString() {
      String result = "";

      for (Map.Entry<String,Item> entry: inventory.entrySet()) {
         result += entry.getValue().toString() + "\n";
      }

      return result;
   }
}
