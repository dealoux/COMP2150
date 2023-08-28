/**
 * LeTomA4.java
 *
 * COMP 2150 SECTION A01
 * INSTRUCTOR Riley Wall
 * ASSIGNMENT Assignment 4
 * @author Tom Le, 7871324
 * @version August 13 2022
 *
 * REMARKS: Additional features to the Point of Sale server including, an interactive client, packages, singleton, and order cancellation
 */

package comp2150.pos.main;
import comp2150.pos.server.A4POSServer;
import comp2150.pos.server.A4Server;
import comp2150.pos.client.InteractiveClient;

public class LeTomA4 {
    private static A4Server server = A4Server.getInstance();
    private static String currTransactionID;

    public static void main(String[] args) {
        String command = InteractiveClient.nextCommand();

        while(command != null){
            // process current command
            System.out.println(command);
            String tokens[] = command.split(",");

            switch(tokens[1]){
                case "PURCHASE":
                    currTransactionID = server.getServer().createTransaction(A4POSServer.TransactionType.PURCHASE, Long.parseLong(tokens[2]), Integer.parseInt(tokens[0]));
                    break;

                case "BACKORDER":
                    currTransactionID = server.getServer().createTransaction(A4POSServer.TransactionType.BACKORDER, Long.parseLong(tokens[2]), Integer.parseInt(tokens[0]));
                    break;

                case "RETURN":
                    currTransactionID = server.getServer().createTransaction(A4POSServer.TransactionType.RETURN, Long.parseLong(tokens[2]), Integer.parseInt(tokens[0]));
                    break;

                case "ADD":
                    System.out.println(server.getServer().addItemToTransaction(currTransactionID, tokens[3], 1));
                    break;

                case "COMPLETE":
                    System.out.println(server.getServer().completeTransaction(currTransactionID));
                    currTransactionID = null;
                    break;

                case "CANCEL":
                    if(currTransactionID != null){
                        System.out.println(server.getServer().cancelTransaction(currTransactionID, Long.parseLong(tokens[2]), Integer.parseInt(tokens[0])));
                        currTransactionID = null;
                    }
                    else{
                        System.out.println(server.getServer().cancelTransaction(tokens[3], Long.parseLong(tokens[2]), Integer.parseInt(tokens[0])));
                    }
                    break;

                case "SEARCH":
                    System.out.println(server.getServer().search(tokens[3], A4POSServer.ItemField.CODE));
                    break;

                case "SUMMARY":
                    if(currTransactionID != null)
                        System.out.println(server.getServer().toString(currTransactionID));
                    else{
                        System.out.println(server.getServer().toString(Integer.parseInt(tokens[0])));
                    }
                    break;
            }

            // next command
            command = InteractiveClient.nextCommand();
        }
    }
}
