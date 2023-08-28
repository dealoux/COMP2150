/**
 * A2ModelPOSServer.java
 *
 * COMP 2150 SECTION A01
 * INSTRUCTOR Riley Wall
 * ASSIGNMENT Assignment 2, question 1
 * @author Tom Le, 7871324
 * @version July 3rd 2022
 *
 * REMARKS: implementation of the server of the client-server point of sale system
 */

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Item implements Comparable <Item>{
    private String code;
    private String name;
    private int quantity;

    public Item(String code, String name, int quantity){
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int q){
        quantity += q;
    }

    // for ordering
    @Override
    public int compareTo(Item o) {
        return this.getCode().compareTo(o.getCode());
    }

    public String toString(){
        return "code: " + code + ", name: " + name + ", quantity: " + quantity;
    }
} // end of Item class

class Transaction{
    private String id;
    private A2POSServer.TransactionType type;
    private long time;
    private Map<String, Item> items; // map by code
    private A2POSServer.TransactionQuery query;

    public Transaction(String id, A2POSServer.TransactionType type, long time){
        this.id = id;
        this.type = type;
        this.time = time;

        items = new HashMap<>();
        query = A2POSServer.TransactionQuery.TYPE;
    }

    public String getId() {
        return id;
    }

    public A2POSServer.TransactionType getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public Map getItems() {
        return items;
    }

    public A2POSServer.TransactionQuery getQuery() {
        return query;
    }

    public void setQuery(A2POSServer.TransactionQuery query) {
        this.query = query;
    }

    public void addItem(String code, String name, int quantity){
        Item item = items.get(code);

        // checks if the item doesnt exists in the transaction
        if(item == null){
            item = new Item(code, name, quantity);
            items.put(code , item);
        }
        // else add to the quantity
        else{
            item.addQuantity(quantity);
        }
    }

    public String toString(){
        return "Id: " + id + ", transaction type: " + type + ", time: " + time + ", query: " + query;
    }
} // end of Transaction class

public class A2ModelPOSServer implements A2POSServer {
    private String inventoryName;
    private Map<String, Item> inventory;
    private Map<String, Transaction> transactionList;

    public A2ModelPOSServer(String name) {
        inventoryName = name;
        transactionList = new HashMap<>();
        inventory = new TreeMap<>(); // sorted

        // read in the inventory
        try {
            Scanner in = new Scanner(new FileInputStream(inventoryName));

            while(in.hasNextLine()){
                String[] lineTokens = in.nextLine().split(",");
                inventory.put(lineTokens[0], new Item(lineTokens[0], lineTokens[1],  Integer.parseInt(lineTokens[2])));
            }

            in.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe.getMessage());
        }
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public Map<String, Item> getInventory() {
        return inventory;
    }

    public Map<String, Transaction> getTransactionList() {
        return transactionList;
    }

    /**
     * Construct a new transaction of the given type.
     *
     * @param type the type of transaction
     * @param time the date and time of the transaction
     * @return the transaction ID, or null on error
     */
    public String createTransaction(TransactionType type, long time) {
        try{
            String id = (type == TransactionType.PURCHASE ? "P" : "R") + transactionList.size()+1;
            transactionList.put(id, new Transaction(id, type, time));
            return id;
        }
        catch (Exception e){
            System.out.println("Failed to create a new transaction\n" + e);
            return null;
        }
    }

    /**
     * Add a new item to a transaction, or change its quantity. If the item code is
     * already found in the transaction, add the given quantity to the item's
     * transaction quantity. The item's transaction quantity may be reduced (with a
     * negative quantity parameter) but it cannot be reduced below zero. If the
     * quantity reaches zero exactly, remove the item from the transaction.
     *
     * @param id       the transaction ID
     * @param item     the item code
     * @param quantity the number of that item to add to the transaction
     * @return an error message, or null on success
     */
    public String addItemToTransaction(String id, String item, int quantity) {
        String msg = null;
        Transaction transaction = transactionList.get(id);

        // check if the transaction id is valid
        if(transaction != null){
            Item inventoryItem = inventory.get(item);

            // check if the item id is valid
            if(inventoryItem != null){
                // limit the purchasing quantity
                quantity = (transaction.getType() == TransactionType.PURCHASE && quantity > inventoryItem.getQuantity()) ? inventoryItem.getQuantity() : quantity;

                if(quantity != 0)
                    transaction.addItem(inventoryItem.getCode(), inventoryItem.getName(), quantity);
                else
                    msg = "Quantity is 0";
            }
            else{
                msg = "No item is found in inventory with id: " + item;
            }
        }
        else{
            msg = "No transaction is found with id: " + id;
        }

        return msg;
    }

    /**
     * Complete a transaction.
     *
     * @param id the transaction ID
     * @return an error message, or null on success
     */
    public String completeTransaction(String id) {
        String msg = null;

        Transaction transaction = transactionList.get(id);

        if(transaction != null){
            transaction.setQuery(TransactionQuery.IS_COMPLETE);
        }
        else{
            msg = "No transaction is found with id: " + id;
        }

        return msg;
    }

    /**
     * Query some feature of a transaction.
     *
     * @param id    the transaction ID
     * @param query identifies the value requested
     * @return determined by the requested value; or null on error
     */
    public String queryTransaction(String id, TransactionQuery query) {
        String msg;

        Transaction transaction = transactionList.get(id);

        if(transaction != null){
            transaction.setQuery(TransactionQuery.IS_COMPLETE);
            msg = "" + query;
        }
        else{
            msg = "No transaction is found with id: " + id;
        }

        return msg;
    }

    /**
     * Show the details of a transaction.
     *
     * @param id the transaction ID
     * @return a string describing a transaction or null on error
     * (ID, type, time, all the items, and whether or not it is complete)
     */
    public String toString(String id) {
        String msg;

        Transaction transaction = transactionList.get(id);

        if(transaction != null){
            msg = transaction.toString();
        }
        else{
            msg = "No transaction is found with id: " + id;
        }

        return msg;
    }

    /**
     * Query some feature of the server.
     *
     * @param query identifies the value requested
     * @return determined by the requested value; or null on error
     */
    public String queryServer(ServerQuery query) {
        return "" + query;
    }

    /**
     * Describe the inventory of items for sale. Order the list alphabetically
     * by item code.
     *
     * @return a string describing the inventory on the server
     * (one item per line), ordered by item code
     */
    public String toString() {
        return inventory.values().toString();
    }
} // end of A2ModelPOSServer class