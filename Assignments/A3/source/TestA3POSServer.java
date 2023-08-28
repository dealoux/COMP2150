/**
 * COMP 2150 Summer 2022: Assignment 3 Question 1 "Point of Sale"
 *
 * JUnit 4 tests for server implementations.
 */

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestA3POSServer {
   private A3POSServer server;
   
   @Before
   public void setUp() throws Exception {
//      server = new A3ReplaceThisWithTheNameOfYourPOSServerImplementation("a3inventory.txt");
      server = new A3ModelPOSServer("a3inventory.txt");
   }

   @After
   public void tearDown() throws Exception {
      server = null;
   }

   @Test
   public void testInStockPurchases() {
      String id;

      id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 100);
      
      assertNull(server.addItemToTransaction(id, "14FT2", 5)); // 11110 with 20 in stock
      assertNull(server.addItemToTransaction(id, "1CPOLE", 1)); // 3253 with 1 in stock
      assertNull(server.addItemToTransaction(id, "1CWTFW", 1)); // 12914 with 20 in stock
      assertNull(server.addItemToTransaction(id, "14FT2", -4));

      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("27277", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("27277", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 101);
      
      assertNull(server.addItemToTransaction(id, "14FT2", 19)); // 11110 with 19 in stock
      assertNull(server.addItemToTransaction(id, "1CWTFW", 9)); // 12914 with 19 in stock
      
      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("28", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("327316", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("2", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("28", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("327316", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));
      
      // verify the correct stock of all these items

      id = server.search("14FT2", A3POSServer.ItemField.QUANTITY);
      assertTrue(server.next(id));
      assertEquals("0", server.queryMatch(id, A3POSServer.ItemField.QUANTITY));

      id = server.search("1CPOLE", A3POSServer.ItemField.QUANTITY);
      assertTrue(server.next(id));
      assertEquals("0", server.queryMatch(id, A3POSServer.ItemField.QUANTITY));

      id = server.search("1CWTFW", A3POSServer.ItemField.QUANTITY);
      assertTrue(server.next(id));
      assertEquals("10", server.queryMatch(id, A3POSServer.ItemField.QUANTITY));
   }

   @Test
   public void testOutOfStockPurchases() {
      String id;

      id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 100);
      
      assertNull(server.addItemToTransaction(id, "1CPOLE", 2)); // 3253 but only 1 in stock
      assertNull(server.addItemToTransaction(id, "1CRGENC", 1)); // 7100 but none in stock

      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("13606", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.addItemToTransaction(id, "1CRGENC", -1)); // remove it from the transaction

      assertEquals("1", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("6506", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("1", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 101);
      
      assertNull(server.addItemToTransaction(id, "1C3P", 1)); // 33119
      assertNull(server.addItemToTransaction(id, "1CR-SSWVISTA-B", 10)); // 15859
      assertNull(server.addItemToTransaction(id, "1CRGENC", 5)); // 7100 but out of stock

      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("16", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("227209", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("2", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));
      
      // make sure no items were removed from inventory
      
      id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 102);
      
      assertNull(server.addItemToTransaction(id, "1CPOLE", 1)); // 3253
      assertNull(server.addItemToTransaction(id, "1C3P", 10)); // 33119
      assertNull(server.addItemToTransaction(id, "1CR-SSWVISTA-B", 20)); // 15859

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("3", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("31", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("651623", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));
   }

   @Test
   public void testReturns() {
      String id;

      id = server.createTransaction(A3POSServer.TransactionType.RETURN, 100);
      
      assertNull(server.addItemToTransaction(id, "1CPOLE", 2)); // 3253
      assertNull(server.addItemToTransaction(id, "1CRGENC", 1)); // 7100

      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("13606", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("13606", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      // verify the stock hasn't changed

      id = server.search("1CPOLE", A3POSServer.ItemField.QUANTITY);
      assertTrue(server.next(id));
      assertEquals("1", server.queryMatch(id, A3POSServer.ItemField.QUANTITY));

      id = server.search("1CRGENC", A3POSServer.ItemField.QUANTITY);
      assertTrue(server.next(id));
      assertEquals("0", server.queryMatch(id, A3POSServer.ItemField.QUANTITY));
   }

   @Test
   public void testInStockBackorders() {
      String id;

      // a "backorder" of in-stock items is exactly the same as a purchase
      
      id = server.createTransaction(A3POSServer.TransactionType.BACKORDER, 100);

      assertNull(server.addItemToTransaction(id, "14FT2", 5)); // 11110 with 20 in stock
      assertNull(server.addItemToTransaction(id, "1CPOLE", 1)); // 3253 with 1 in stock
      assertNull(server.addItemToTransaction(id, "1CWTFW", 1)); // 12914 with 20 in stock
      assertNull(server.addItemToTransaction(id, "14FT2", -4));

      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("27277", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("27277", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      id = server.createTransaction(A3POSServer.TransactionType.BACKORDER, 101);
      
      assertNull(server.addItemToTransaction(id, "14FT2", 19)); // 11110 with 19 in stock
      assertNull(server.addItemToTransaction(id, "1CWTFW", 19)); // 12914 with 19 in stock
      
      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("38", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("456456", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("2", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("38", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("456456", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));
 
      // verify that there are zero in stock of all these items

      id = server.search("14FT2", A3POSServer.ItemField.QUANTITY);
      assertTrue(server.next(id));
      assertEquals("0", server.queryMatch(id, A3POSServer.ItemField.QUANTITY));

      id = server.search("1CPOLE", A3POSServer.ItemField.QUANTITY);
      assertTrue(server.next(id));
      assertEquals("0", server.queryMatch(id, A3POSServer.ItemField.QUANTITY));

      id = server.search("1CWTFW", A3POSServer.ItemField.QUANTITY);
      assertTrue(server.next(id));
      assertEquals("0", server.queryMatch(id, A3POSServer.ItemField.QUANTITY));
   }

   @Test
   public void testOutOfStockBackorders() {
      String id;

      id = server.createTransaction(A3POSServer.TransactionType.BACKORDER, 100);
      
      assertNull(server.addItemToTransaction(id, "14FT2", 25)); // 11110 with 20 in stock

      assertEquals("1", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("25", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("277750", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("1", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("25", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("277750", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      // check the backorder quantities
      
      id = server.search("14FT2", A3POSServer.ItemField.BACKORDER_QUANTITY);
      assertTrue(server.next(id));
      assertEquals("5", server.queryMatch(id, A3POSServer.ItemField.BACKORDER_QUANTITY));

      // another backorder
      
      id = server.createTransaction(A3POSServer.TransactionType.BACKORDER, 101);
      
      assertNull(server.addItemToTransaction(id, "14FT2", 5)); // 11110 with 0 in stock
      assertNull(server.addItemToTransaction(id, "1CPOLE", 2)); // 3253 with 1 in stock
      assertNull(server.addItemToTransaction(id, "1CWTFW", 10)); // 12914 with 20 in stock
      assertNull(server.addItemToTransaction(id, "1CRGENC", 1)); // 7100 with 0 in stock

      assertEquals("4", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("18", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("198296", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("2", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("4", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("18", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("198296", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));

      // check the backorder quantities
      
      id = server.search("14FT2", A3POSServer.ItemField.BACKORDER_QUANTITY);
      assertTrue(server.next(id));
      assertEquals("10", server.queryMatch(id, A3POSServer.ItemField.BACKORDER_QUANTITY));
      
      id = server.search("1CPOLE", A3POSServer.ItemField.BACKORDER_QUANTITY);
      assertTrue(server.next(id));
      assertEquals("1", server.queryMatch(id, A3POSServer.ItemField.BACKORDER_QUANTITY));
      
      id = server.search("1CWTFW", A3POSServer.ItemField.BACKORDER_QUANTITY);
      assertTrue(server.next(id));
      assertEquals("0", server.queryMatch(id, A3POSServer.ItemField.BACKORDER_QUANTITY));
      
      id = server.search("1CRGENC", A3POSServer.ItemField.BACKORDER_QUANTITY);
      assertTrue(server.next(id));
      assertEquals("1", server.queryMatch(id, A3POSServer.ItemField.BACKORDER_QUANTITY));
   }

   @Test
   public void testSearchOneMatch() {
      String iid;
      
      // note that search order doesn't matter for any of these

      iid = server.search("1CRWV2W2TC", A3POSServer.ItemField.CODE);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("1CRWV2W2TC", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertEquals("Wayne Vista 2-hose. Full-height. Wide Nozzle Config w/ 2\" Inset. Totalizer Slots left & right of center. (42\"w x 37-1/4\"h)", server.queryMatch(iid, A3POSServer.ItemField.DESCRIPTION));
      assertEquals("7825", server.queryMatch(iid, A3POSServer.ItemField.COST));
      assertEquals("20", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY));
      assertEquals("0", server.queryMatch(iid, A3POSServer.ItemField.BACKORDER_QUANTITY));
      assertFalse(server.next(iid));
      
      iid = server.search("EXIT11", A3POSServer.ItemField.DESCRIPTION);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("30", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY));
      assertFalse(server.next(iid));
      
      iid = server.search("Water", A3POSServer.ItemField.QUANTITY);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("16246", server.queryMatch(iid, A3POSServer.ItemField.COST));
      assertFalse(server.next(iid));
   }

   @Test
   public void testSearchNoMatches() {
      String iid;
      
      // note that search order doesn't matter for any of these

      iid = server.search("does not exist", A3POSServer.ItemField.CODE);
      assertNotNull(iid);
      assertFalse(server.next(iid));

      // incorrect case
      iid = server.search("pole", A3POSServer.ItemField.DESCRIPTION);
      assertNotNull(iid);
      assertFalse(server.next(iid));

      // extra space at end
      iid = server.search("ADA Parking Only ", A3POSServer.ItemField.QUANTITY);
      assertNotNull(iid);
      assertFalse(server.next(iid));
   }
      
   @Test
   public void testSearchMultipleMatches() {
      String iid;
      int count;

      iid = server.search("1C6", A3POSServer.ItemField.DESCRIPTION);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("1C6MP", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("41115", server.queryMatch(iid, A3POSServer.ItemField.COST));
      assertTrue(server.next(iid));
      assertEquals("Diesel Bottomer for #1C6MP; 6-product Mini Sign", server.queryMatch(iid, A3POSServer.ItemField.DESCRIPTION));
      assertTrue(server.next(iid));
      assertEquals("1C6P-DBR", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertFalse(server.next(iid));

      iid = server.search("Gilbarco", A3POSServer.ItemField.CODE);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("1CR-SSGAD", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("Gilbarco Encore SideSkins. 2 inner & 2 outer panels. IR Silver finish.", server.queryMatch(iid, A3POSServer.ItemField.DESCRIPTION));
      assertTrue(server.next(iid));
      assertEquals("1CR-SSGMPD", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("7100", server.queryMatch(iid, A3POSServer.ItemField.COST)); // 1CRGADNAR
      assertTrue(server.next(iid));
      assertEquals("1CRGADWIDE", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("0", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY)); // 1CRGENC
      assertTrue(server.next(iid));
      assertEquals("1CRGMPDHI", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("6", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY)); // 1CRGMPDLO
      assertFalse(server.next(iid));

      iid = server.search("24", A3POSServer.ItemField.QUANTITY);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("1CRGENC", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("6", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY)); // 1CRGMPDLO
      // the next four have the same quantity (unpredictable order)
      assertTrue(server.next(iid));
      assertTrue(server.next(iid));
      assertTrue(server.next(iid));
      assertTrue(server.next(iid));
      assertTrue(server.next(iid));
      assertEquals("Open 24 Hours/Enter Sign", server.queryMatch(iid, A3POSServer.ItemField.DESCRIPTION)); // 1CO24HR2020
      assertFalse(server.next(iid));

      iid = server.search("Wayne", A3POSServer.ItemField.BACKORDER_QUANTITY);
      count = 0;
      while (server.next(iid)) {
         count++;
      }
      assertEquals(19, count);

      // every item description has a space in it
      iid = server.search(" ", A3POSServer.ItemField.COST);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("1CGFLAG", server.queryMatch(iid, A3POSServer.ItemField.CODE)); // lowest cost of all items is 1425
      count = 1;
      int lastCost = Integer.parseInt(server.queryMatch(iid, A3POSServer.ItemField.COST));
      while (server.next(iid)) {
         int cost = Integer.parseInt(server.queryMatch(iid, A3POSServer.ItemField.COST));
         assertTrue(cost >= lastCost);
         count++;
      }
      assertEquals(63, count);
   }

   @Test
   public void testSearchWithTransaction() {
      String id, iid;

      id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 100);

      iid = server.search("TC", A3POSServer.ItemField.QUANTITY);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("3", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY));
      assertTrue(server.next(iid));
      assertEquals("20", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY));
      assertTrue(server.next(iid));
      assertEquals("25", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY));
      assertFalse(server.next(iid));

      iid = server.search("TC", A3POSServer.ItemField.BACKORDER_QUANTITY);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("0", server.queryMatch(iid, A3POSServer.ItemField.BACKORDER_QUANTITY));
      assertTrue(server.next(iid));

      assertNull(server.addItemToTransaction(id, "1CRWV1TC", 15)); // 25 in stock
      assertNull(server.addItemToTransaction(id, "1CRWV2W2TC", 19)); // 20 in stock

      assertEquals("0", server.queryMatch(iid, A3POSServer.ItemField.BACKORDER_QUANTITY));
      assertTrue(server.next(iid));
      assertEquals("0", server.queryMatch(iid, A3POSServer.ItemField.BACKORDER_QUANTITY));
      assertFalse(server.next(iid));

      assertNull(server.completeTransaction(id));

      iid = server.search("TC", A3POSServer.ItemField.QUANTITY);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("1", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY));
      assertEquals("1CRWV2W2TC", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("3", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY));
      assertEquals("1CRWV2TC", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("10", server.queryMatch(iid, A3POSServer.ItemField.QUANTITY));
      assertEquals("1CRWV1TC", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertFalse(server.next(iid));

      id = server.createTransaction(A3POSServer.TransactionType.BACKORDER, 101);
      assertNull(server.addItemToTransaction(id, "1CRWV1TC", 15)); // 10 in stock
      assertNull(server.addItemToTransaction(id, "1CRWV2W2TC", 2)); // 1 in stock
      assertNull(server.completeTransaction(id));

      iid = server.search("TC", A3POSServer.ItemField.BACKORDER_QUANTITY);
      assertNotNull(iid);
      assertTrue(server.next(iid));
      assertEquals("0", server.queryMatch(iid, A3POSServer.ItemField.BACKORDER_QUANTITY));
      assertEquals("1CRWV2TC", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("1", server.queryMatch(iid, A3POSServer.ItemField.BACKORDER_QUANTITY));
      assertEquals("1CRWV2W2TC", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid));
      assertEquals("5", server.queryMatch(iid, A3POSServer.ItemField.BACKORDER_QUANTITY));
      assertEquals("1CRWV1TC", server.queryMatch(iid, A3POSServer.ItemField.CODE));
      assertFalse(server.next(iid));
   }

   @Test
   public void testSearchSimultaneous() {
      String iid1, iid2, iid3;
      
      iid1 = server.search("1C6", A3POSServer.ItemField.DESCRIPTION);
      assertNotNull(iid1);

      iid2 = server.search("Gilbarco", A3POSServer.ItemField.CODE);
      assertNotNull(iid2);

      assertTrue(server.next(iid1));
      assertEquals("1C6MP", server.queryMatch(iid1, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid1));
      assertEquals("41115", server.queryMatch(iid1, A3POSServer.ItemField.COST));

      assertTrue(server.next(iid2));
      assertEquals("1CR-SSGAD", server.queryMatch(iid2, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid2));
      assertEquals("Gilbarco Encore SideSkins. 2 inner & 2 outer panels. IR Silver finish.", server.queryMatch(iid2, A3POSServer.ItemField.DESCRIPTION));
      assertTrue(server.next(iid2));
      assertEquals("1CR-SSGMPD", server.queryMatch(iid2, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid2));
      assertEquals("7100", server.queryMatch(iid2, A3POSServer.ItemField.COST)); // 1CRGADNAR
      assertTrue(server.next(iid2));

      assertTrue(server.next(iid1));
      assertEquals("Diesel Bottomer for #1C6MP; 6-product Mini Sign", server.queryMatch(iid1, A3POSServer.ItemField.DESCRIPTION));
      assertTrue(server.next(iid1));

      iid3 = server.search("24", A3POSServer.ItemField.QUANTITY);
      assertNotNull(iid3);

      assertTrue(server.next(iid3));
      assertEquals("1CRGENC", server.queryMatch(iid3, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid3));
      assertEquals("6", server.queryMatch(iid3, A3POSServer.ItemField.QUANTITY)); // 1CRGMPDLO

      assertEquals("1CRGADWIDE", server.queryMatch(iid2, A3POSServer.ItemField.CODE));
      assertTrue(server.next(iid2));
      assertEquals("0", server.queryMatch(iid2, A3POSServer.ItemField.QUANTITY)); // 1CRGENC
      assertTrue(server.next(iid2));
      assertEquals("1CRGMPDHI", server.queryMatch(iid2, A3POSServer.ItemField.CODE));

      assertTrue(server.next(iid3));
      assertEquals("11110", server.queryMatch(iid3, A3POSServer.ItemField.COST)); // 14FT2

      assertTrue(server.next(iid2));
      assertEquals("6", server.queryMatch(iid2, A3POSServer.ItemField.QUANTITY)); // 1CRGMPDLO
      assertFalse(server.next(iid2));

      // the next three have the same quantity (unpredictable order)
      assertTrue(server.next(iid3));
      assertTrue(server.next(iid3));
      assertTrue(server.next(iid3));
      assertTrue(server.next(iid3));
      assertEquals("Open 24 Hours/Enter Sign", server.queryMatch(iid3, A3POSServer.ItemField.DESCRIPTION)); // 1CO24HR2020
      assertFalse(server.next(iid3));

      assertEquals("1C6P-DBR", server.queryMatch(iid1, A3POSServer.ItemField.CODE));
      assertFalse(server.next(iid1));
   }

   @Test
   public void testSearchInvalid() {
      String iid;

      iid = server.search(null, A3POSServer.ItemField.DESCRIPTION);
      assertNull(iid);

      // must call next() before first result
      iid = server.search("1CEXIT1120", A3POSServer.ItemField.DESCRIPTION);
      assertNull(server.queryMatch(iid, A3POSServer.ItemField.CODE));

      iid = server.search("1CEXIT1120", A3POSServer.ItemField.DESCRIPTION);
      assertFalse(server.next(null));

      iid = server.search("1CEXIT1120", A3POSServer.ItemField.DESCRIPTION);
      assertTrue(server.next(iid));
      assertNull(server.queryMatch(null, A3POSServer.ItemField.CODE));

      // goes past end of matches
      iid = server.search("1CEXIT1120", A3POSServer.ItemField.DESCRIPTION);
      assertTrue(server.next(iid));
      assertFalse(server.next(iid));
      assertNull(server.queryMatch(iid, A3POSServer.ItemField.CODE));
   }

   @Test
   public void testEmptyInventory() {
      // this could be any invalid, empty, or not-found file
      server = new A3ModelPOSServer("README.txt");
      
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.INVENTORY_COUNT));
   }

   /*** Old tests follow (these too shall pass) ***/
   
   @Test
   public void testEmptyServer() {
      assertNotNull(server);
      
      assertEquals("63", server.queryServer(A3POSServer.ServerQuery.INVENTORY_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      // there should be one line of string for each item in the inventory
      // check the first and last are first and last alphabetically
      // otherwise the format is up to you
      String[] serverToStringSplit = server.toString().split("\n");
      assertEquals(63, serverToStringSplit.length);
      assertTrue(serverToStringSplit[0].contains("14FT2"));
      assertTrue(serverToStringSplit[62].contains("1CWTFW"));
   }
   
   @Test
   public void testTypicalTransactions() {
      String id;

      id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 100);
      
      assertNull(server.addItemToTransaction(id, "1C3P", 1)); // 33119
      assertNull(server.addItemToTransaction(id, "1CR-SSWVISTA-B", 10)); // 15859
      assertNull(server.addItemToTransaction(id, "1CRGENC", 5)); // 7100

      assertEquals("PURCHASE", server.queryTransaction(id, A3POSServer.TransactionQuery.TYPE));
      assertEquals("3", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("16", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("227209", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));
      assertEquals("false", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      // the exact format of the toString() is up to you, but it needs to have all the items
      assertTrue(server.toString(id).length() > 0);
      assertTrue(server.toString(id).contains("1C3P"));
      assertTrue(server.toString(id).contains("1CRGENC"));

      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
      
      id = server.createTransaction(A3POSServer.TransactionType.RETURN, 200);
      
      assertNull(server.addItemToTransaction(id, "1CRGENC", 3));
      assertNull(server.addItemToTransaction(id, "1CR-SSWVISTA-B", 5));

      assertEquals("RETURN", server.queryTransaction(id, A3POSServer.TransactionQuery.TYPE));
      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("8", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("100595", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));
      assertEquals("false", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("2", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
   }

   @Test
   public void testSimultaneousTransactions() {
      String id1, id2;

      id1 = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 100);
      assertNull(server.addItemToTransaction(id1, "1CGFLAG", 100)); // 1425 

      id2 = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 200);
      assertNull(server.addItemToTransaction(id2, "1CRFH1824P", 5)); // 2928 

      assertNull(server.addItemToTransaction(id1, "1CCURB", 17)); // 6880 
      assertEquals("259460", server.queryTransaction(id1, A3POSServer.TransactionQuery.TOTAL_COST));
      assertEquals("14640", server.queryTransaction(id2, A3POSServer.TransactionQuery.TOTAL_COST));

      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("2", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
      
      assertNull(server.completeTransaction(id2));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertNull(server.addItemToTransaction(id1, "1CR-SSGAD", 1)); // 15859 

      id2 = server.createTransaction(A3POSServer.TransactionType.RETURN, 300);
      assertNull(server.addItemToTransaction(id2, "1CEXIT1120", 2)); // 19812 
      
      assertNull(server.addItemToTransaction(id1, "1CCWST2764", 1)); // 36734 
      
      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("2", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("119", server.queryTransaction(id1, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("2", server.queryTransaction(id2, A3POSServer.TransactionQuery.TOTAL_QUANTITY));

      assertNull(server.completeTransaction(id1));
      assertEquals("true", server.queryTransaction(id1, A3POSServer.TransactionQuery.IS_COMPLETE));
      assertEquals("false", server.queryTransaction(id2, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertNull(server.addItemToTransaction(id2, "1CWTFW", 3)); 
      assertEquals("2", server.queryTransaction(id2, A3POSServer.TransactionQuery.ITEM_COUNT));

      assertNull(server.completeTransaction(id2));

      assertEquals("3", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
   }

   @Test
   public void testItemQuantityChanges() {
      String id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 10000);
      
      assertNull(server.addItemToTransaction(id, "1C3P-DBR", 4)); 
      assertNull(server.addItemToTransaction(id, "1C6P-DBR", 5));
      assertEquals("2", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("9", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      
      assertNull(server.addItemToTransaction(id, "1C3P-DBR", 3)); 
      assertNull(server.addItemToTransaction(id, "1C6P-DBR", 7));
      assertEquals("19", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      
      assertNull(server.addItemToTransaction(id, "1C3P-DBR", -2)); 
      assertNull(server.addItemToTransaction(id, "1C6P-DBR", -4));
      assertEquals("13", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));

      assertNull(server.addItemToTransaction(id, "1C3P-DBR", -5)); 
      assertEquals("1", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("8", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));

      assertNull(server.addItemToTransaction(id, "1C6P-DBR", -8));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
   }

   @Test
   public void testEmptyTransaction() {
      String id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 100);
      
      assertEquals("PURCHASE", server.queryTransaction(id, A3POSServer.TransactionQuery.TYPE));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("0", server.queryTransaction(id, A3POSServer.TransactionQuery.TOTAL_COST));
      assertEquals("false", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A3POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A3POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
   }

   @Test
   public void testNonExistentTransactions() {
      assertNotNull(server.addItemToTransaction(null, "1CRGADWIDE", 1));
      assertNotNull(server.addItemToTransaction("1", "1CRGADWIDE", 1));
      assertNotNull(server.completeTransaction(null));
      assertNotNull(server.completeTransaction("1"));

      assertNull(server.queryTransaction(null, A3POSServer.TransactionQuery.TYPE));
      assertNull(server.queryTransaction("1", A3POSServer.TransactionQuery.TYPE));
      assertNull(server.toString(null));
      assertNull(server.toString("1"));
   }

   @Test
   public void testInvalidTransactionSequences() {
      String id1 = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 0);
      String id2 = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 1);

      assertNotNull(server.completeTransaction(""));

      assertNull(server.addItemToTransaction(id1, "1CRGADWIDE", 1)); // 7100
      assertEquals("PURCHASE", server.queryTransaction(id1, A3POSServer.TransactionQuery.TYPE));
      assertNull(server.completeTransaction(id1));

      assertNull(server.addItemToTransaction(id2, "1C3P-DBR", 5)); // 4900

      assertNotNull(server.addItemToTransaction(id1, "1CRGADWIDE", 1));
      assertNotNull(server.completeTransaction(id1));

      assertNull(server.addItemToTransaction(id2, "1CRGADWIDE", 3));
      assertEquals("45800", server.queryTransaction(id2, A3POSServer.TransactionQuery.TOTAL_COST));
      assertNull(server.completeTransaction(id2));

      assertNotNull(server.completeTransaction(id2));
      assertNotNull(server.addItemToTransaction(id2, "1CRGADWIDE", 1));
   }

   @Test
   public void testInvalidItems() {
      String id = server.createTransaction(A3POSServer.TransactionType.PURCHASE, 0);
      
      assertNotNull(server.addItemToTransaction(id, null, 1));
      assertNotNull(server.addItemToTransaction(id, "UNKNOWN", 1));
      assertNotNull(server.addItemToTransaction(id, "1CDFLAGS", 1));
      assertNotNull(server.addItemToTransaction(id, "1CDFLA", 1));

      assertNotNull(server.addItemToTransaction(id, "1CDFLAG", -1));

      assertNull(server.addItemToTransaction(id, "1CDFLAG", 5));
      assertNotNull(server.addItemToTransaction(id, "1CDFLAG", -6));

      assertNull(server.addItemToTransaction(id, "1CDFLAG", -5));
      assertNotNull(server.addItemToTransaction(id, "1CDFLAG", -1));
   }
}
