/**
 * COMP 2150 Summer 2022: Assignment 2 Question 1 "Point of Sale"
 *
 * This interface defines the communication between the point of sale client
 * and the server. Messages to the server are sent as method calls, and
 * information is returned as return values.
 *
 * Each transaction in the system is made with a sequence of method calls:
 *  createTransaction() - create a new transaction
 *  addItemToTransaction() - add an item to a transaction (may be called repeatedly)
 *  completeTransaction() - finish the transaction
 *
 * There are also some calls that can be used to retrieve information about
 * a specific transaction (either in progress, or once it is completed):
 *  queryTransaction() - get information about a transaction
 *  toString() - return a string containing the details of a transaction
 *
 * There are also some calls that can be used to retrieve information about
 * the server:
 *  queryServer() - get information about the server
 *  toString() - return a string containing the details of the server
 */

public interface A2POSServer {
   enum TransactionType {
      PURCHASE, RETURN
   }

   enum TransactionQuery {
      TYPE, ITEM_COUNT, TOTAL_QUANTITY, TOTAL_COST, IS_COMPLETE 
   }

   enum ServerQuery {
      INVENTORY_COUNT, TRANSACTION_COMPLETED_COUNT, TRANSACTION_IN_PROGRESS_COUNT 
   }

   /**
    * Construct a new transaction of the given type.
    *
    * @param  type the type of transaction
    * @param  time the date and time of the transaction
    * @return the transaction ID, or null on error
    */
   String createTransaction(TransactionType type, long time);

   /**
    * Add a new item to a transaction, or change its quantity. If the item code is
    * already found in the transaction, add the given quantity to the item's
    * transaction quantity. The item's transaction quantity may be reduced (with a
    * negative quantity parameter) but it cannot be reduced below zero. If the
    * quantity reaches zero exactly, remove the item from the transaction.
    *
    * @param  id   the transaction ID
    * @param  item the item code
    * @param  quantity the number of that item to add to the transaction 
    * @return an error message, or null on success
    */
   String addItemToTransaction(String id, String item, int quantity);

   /**
    * Complete a transaction.
    *
    * @param  id the transaction ID
    * @return an error message, or null on success
    */
   String completeTransaction(String id);

   /**
    * Query some feature of a transaction.
    *
    * @param  id    the transaction ID
    * @param  query identifies the value requested
    * @return determined by the requested value; or null on error
    */
   String queryTransaction(String id, TransactionQuery query);

   /**
    * Show the details of a transaction.
    *
    * @param  id the transaction ID
    * @return a string describing a transaction or null on error
    *         (ID, type, time, all the items, and whether or not it is complete)
    */
   String toString(String id);

   /**
    * Query some feature of the server.
    *
    * @param  query identifies the value requested
    * @return determined by the requested value; or null on error
    */
   String queryServer(ServerQuery query);

   /**
    * Describe the inventory of items for sale. Order the list alphabetically
    * by item code.
    *
    * @return a string describing the inventory on the server
    *         (one item per line), ordered by item code
    */
   String toString();
}