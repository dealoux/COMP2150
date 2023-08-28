/**
 * COMP 2150 Summer 2019: Assignment 2 Question 1 "Point of Sale"
 *
 * JUnit 4 tests for server implementations.
 */

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestA2POSServer {
   private A2POSServer server;
   
   @Before
   public void setUp() throws Exception {
//      server = new A2ReplaceThisWithTheNameOfYourPOSServerImplementation("a2inventory.txt");
      server = new A2ModelPOSServer("a2inventory.txt");
   }

   @After
   public void tearDown() throws Exception {
      server = null;
   }

   @Test
   public void testEmptyServer() {
      assertNotNull(server);
      
      assertEquals("63", server.queryServer(A2POSServer.ServerQuery.INVENTORY_COUNT));
      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

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

      id = server.createTransaction(A2POSServer.TransactionType.PURCHASE, 100);
      
      assertNull(server.addItemToTransaction(id, "1C3P", 1)); // 33119
      assertNull(server.addItemToTransaction(id, "1CR-SSWVISTA-B", 10)); // 15859
      assertNull(server.addItemToTransaction(id, "1CRGENC", 5)); // 7100

      assertEquals("PURCHASE", server.queryTransaction(id, A2POSServer.TransactionQuery.TYPE));
      assertEquals("3", server.queryTransaction(id, A2POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("16", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("227209", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_COST));
      assertEquals("false", server.queryTransaction(id, A2POSServer.TransactionQuery.IS_COMPLETE));

      // the exact format of the toString() is up to you, but it needs to have all the items
      assertTrue(server.toString(id).length() > 0);
      assertTrue(server.toString(id).contains("1C3P"));
      assertTrue(server.toString(id).contains("1CRGENC"));

      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A2POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
      
      id = server.createTransaction(A2POSServer.TransactionType.RETURN, 200);
      
      assertNull(server.addItemToTransaction(id, "1CRGENC", 3));
      assertNull(server.addItemToTransaction(id, "1CR-SSWVISTA-B", 5));

      assertEquals("RETURN", server.queryTransaction(id, A2POSServer.TransactionQuery.TYPE));
      assertEquals("2", server.queryTransaction(id, A2POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("8", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("100595", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_COST));
      assertEquals("false", server.queryTransaction(id, A2POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A2POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("2", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
   }

   @Test
   public void testSimultaneousTransactions() {
      String id1, id2;

      id1 = server.createTransaction(A2POSServer.TransactionType.PURCHASE, 100);
      assertNull(server.addItemToTransaction(id1, "1CGFLAG", 100)); // 1425 

      id2 = server.createTransaction(A2POSServer.TransactionType.PURCHASE, 200);
      assertNull(server.addItemToTransaction(id2, "1CRFH1824P", 5)); // 2928 

      assertNull(server.addItemToTransaction(id1, "1CCURB", 17)); // 6880 
      assertEquals("259460", server.queryTransaction(id1, A2POSServer.TransactionQuery.TOTAL_COST));
      assertEquals("14640", server.queryTransaction(id2, A2POSServer.TransactionQuery.TOTAL_COST));

      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("2", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
      
      assertNull(server.completeTransaction(id2));

      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertNull(server.addItemToTransaction(id1, "1CR-SSGAD", 1)); // 15859 

      id2 = server.createTransaction(A2POSServer.TransactionType.RETURN, 300);
      assertNull(server.addItemToTransaction(id2, "1CEXIT1120", 2)); // 19812 
      
      assertNull(server.addItemToTransaction(id1, "1CCWST2764", 1)); // 36734 
      
      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("2", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertEquals("119", server.queryTransaction(id1, A2POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("2", server.queryTransaction(id2, A2POSServer.TransactionQuery.TOTAL_QUANTITY));

      assertNull(server.completeTransaction(id1));
      assertEquals("true", server.queryTransaction(id1, A2POSServer.TransactionQuery.IS_COMPLETE));
      assertEquals("false", server.queryTransaction(id2, A2POSServer.TransactionQuery.IS_COMPLETE));

      assertNull(server.addItemToTransaction(id2, "1CWTFW", 3)); 
      assertEquals("2", server.queryTransaction(id2, A2POSServer.TransactionQuery.ITEM_COUNT));

      assertNull(server.completeTransaction(id2));

      assertEquals("3", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
   }

   @Test
   public void testItemQuantityChanges() {
      String id = server.createTransaction(A2POSServer.TransactionType.PURCHASE, 10000);
      
      assertNull(server.addItemToTransaction(id, "1C3P-DBR", 4)); 
      assertNull(server.addItemToTransaction(id, "1C6P-DBR", 5));
      assertEquals("2", server.queryTransaction(id, A2POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("9", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_QUANTITY));
      
      assertNull(server.addItemToTransaction(id, "1C3P-DBR", 3)); 
      assertNull(server.addItemToTransaction(id, "1C6P-DBR", 7));
      assertEquals("19", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_QUANTITY));
      
      assertNull(server.addItemToTransaction(id, "1C3P-DBR", -2)); 
      assertNull(server.addItemToTransaction(id, "1C6P-DBR", -4));
      assertEquals("13", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_QUANTITY));

      assertNull(server.addItemToTransaction(id, "1C3P-DBR", -5)); 
      assertEquals("1", server.queryTransaction(id, A2POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("8", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_QUANTITY));

      assertNull(server.addItemToTransaction(id, "1C6P-DBR", -8));
      assertEquals("0", server.queryTransaction(id, A2POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("0", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_QUANTITY));
   }

   @Test
   public void testEmptyTransaction() {
      String id = server.createTransaction(A2POSServer.TransactionType.PURCHASE, 100);
      
      assertEquals("PURCHASE", server.queryTransaction(id, A2POSServer.TransactionQuery.TYPE));
      assertEquals("0", server.queryTransaction(id, A2POSServer.TransactionQuery.ITEM_COUNT));
      assertEquals("0", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_QUANTITY));
      assertEquals("0", server.queryTransaction(id, A2POSServer.TransactionQuery.TOTAL_COST));
      assertEquals("false", server.queryTransaction(id, A2POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));

      assertNull(server.completeTransaction(id));
      assertEquals("true", server.queryTransaction(id, A2POSServer.TransactionQuery.IS_COMPLETE));

      assertEquals("1", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_COMPLETED_COUNT));
      assertEquals("0", server.queryServer(A2POSServer.ServerQuery.TRANSACTION_IN_PROGRESS_COUNT));
   }

   @Test
   public void testNonExistentTransactions() {
      assertNotNull(server.addItemToTransaction(null, "1CRGADWIDE", 1));
      assertNotNull(server.addItemToTransaction("1", "1CRGADWIDE", 1));
      assertNotNull(server.completeTransaction(null));
      assertNotNull(server.completeTransaction("1"));

      assertNull(server.queryTransaction(null, A2POSServer.TransactionQuery.TYPE));
      assertNull(server.queryTransaction("1", A2POSServer.TransactionQuery.TYPE));
      assertNull(server.toString(null));
      assertNull(server.toString("1"));
   }

   @Test
   public void testInvalidTransactionSequences() {
      String id1 = server.createTransaction(A2POSServer.TransactionType.PURCHASE, 0);
      String id2 = server.createTransaction(A2POSServer.TransactionType.PURCHASE, 1);

      assertNotNull(server.completeTransaction(""));

      assertNull(server.addItemToTransaction(id1, "1CRGADWIDE", 1)); // 7100
      assertEquals("PURCHASE", server.queryTransaction(id1, A2POSServer.TransactionQuery.TYPE));
      assertNull(server.completeTransaction(id1));

      assertNull(server.addItemToTransaction(id2, "1C3P-DBR", 5)); // 4900

      assertNotNull(server.addItemToTransaction(id1, "1CRGADWIDE", 1));
      assertNotNull(server.completeTransaction(id1));

      assertNull(server.addItemToTransaction(id2, "1CRGADWIDE", 3));
      assertEquals("45800", server.queryTransaction(id2, A2POSServer.TransactionQuery.TOTAL_COST));
      assertNull(server.completeTransaction(id2));

      assertNotNull(server.completeTransaction(id2));
      assertNotNull(server.addItemToTransaction(id2, "1CRGADWIDE", 1));
   }

   @Test
   public void testInvalidItems() {
      String id = server.createTransaction(A2POSServer.TransactionType.PURCHASE, 0);
      
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
