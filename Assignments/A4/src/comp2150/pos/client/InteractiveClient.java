//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package comp2150.pos.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InteractiveClient {
    public static final int PURCHASE = 1;
    public static final int BACKORDER = 2;
    public static final int RETURN = 3;
    public static final int CANCEL = 4;
    public static final int SEARCH = 5;
    public static final int SUMMARY = 6;
    private static final int CREATE = 7;
    private static final int SWITCH = 8;
    private static final int QUIT = 9;
    private static BufferedReader input;
    private static SimpleDateFormat sdf;
    private static ArrayList<InteractiveClient> clients;
    private static int currentID;
    private int clientID;
    private int currentTransaction;
    private String line;

    public static String nextCommand() {
        if (clients == null) {
            initialize();
        }

        InteractiveClient var0 = null;
        if (currentID > 0 && clients.get(currentID - 1) != null) {
            var0 = (InteractiveClient)clients.get(currentID - 1);
        } else {
            var0 = nextClient();
        }

        return var0 == null ? null : var0.getNextCommand();
    }

    private static void initialize() {
        input = new BufferedReader(new InputStreamReader(System.in));
        sdf = new SimpleDateFormat("yyyymmddHHmmss");
        clients = new ArrayList();
        currentID = createClient().clientID;
    }

    private static InteractiveClient createClient() {
        InteractiveClient var0 = new InteractiveClient(clients.size() + 1);
        clients.add(var0);
        currentID = clients.size();
        return var0;
    }

    private static InteractiveClient nextClient() {
        int var0;
        for(var0 = currentID; var0 < clients.size(); ++var0) {
            if (clients.get(var0) != null) {
                currentID = var0 + 1;
                return (InteractiveClient)clients.get(var0);
            }
        }

        for(var0 = 0; var0 < currentID; ++var0) {
            if (clients.get(var0) != null) {
                currentID = var0 + 1;
                return (InteractiveClient)clients.get(var0);
            }
        }

        return null;
    }

    private static InteractiveClient quitClient(InteractiveClient var0) {
        clients.set(var0.clientID - 1, null);
        return nextClient();
    }

    private InteractiveClient(int var1) {
        this.clientID = var1;
        this.currentTransaction = 0;
    }

    private String getNextCommand() {
        this.line = null;
        return this.currentTransaction == 0 ? this.getNextMainCommand() : this.getNextTransactionCommand();
    }

    private String getNextMainCommand() {
        String var1 = "";

        while(this.line == null) {
            System.out.println("\nPOS Client #" + this.clientID + ": enter a command");
            System.out.println("   1 - Purchase some items");
            System.out.println("   2 - Backorder some items");
            System.out.println("   3 - Return some items");
            System.out.println("   4 - Cancel a transaction");
            System.out.println("   5 - Search for items");
            System.out.println("   6 - List all of this client's transactions");
            System.out.println("   7 - Create a new client");
            System.out.println("   8 - Switch to the next client");
            System.out.println("   9 - Exit this client");
            System.out.print("Enter your choice: ");

            try {
                this.line = input.readLine();
            } catch (IOException var4) {
                System.out.println("Error reading from input " + var4.getMessage());
                this.line = "9";
            }

            var1 = "";
            if (this.line.equals("1")) {
                System.out.println("\nYou selected purchase. Starting a purchase transaction.");
                this.currentTransaction = 1;
                var1 = this.toCommand("PURCHASE");
            } else if (this.line.equals("2")) {
                System.out.println("\nYou selected backorder. Starting a backorder transaction.");
                this.currentTransaction = 2;
                var1 = this.toCommand("BACKORDER");
            } else if (this.line.equals("3")) {
                System.out.println("\nYou selected return. Starting a return transaction.");
                this.currentTransaction = 3;
                var1 = this.toCommand("RETURN");
            } else if (this.line.equals("4")) {
                System.out.print("\nWhat transaction ID would you like to cancel? ");

                try {
                    this.line = input.readLine();
                    var1 = this.toCommand("CANCEL", this.line);
                } catch (IOException var3) {
                    System.out.println("Error reading from input " + var3.getMessage());
                    this.line = null;
                }
            } else {
                var1 = this.commonCommands();
            }
        }

        return var1;
    }

    private String getNextTransactionCommand() {
        String var1 = "";
        String var2 = this.currentTransaction == 1 ? "purchase" : (this.currentTransaction == 2 ? "backorder" : "return");

        while(this.line == null) {
            System.out.println("\nPOS Client #" + currentID + ": enter a command");
            System.out.println("   1 - Add item to current " + var2);
            System.out.println("   3 - Complete the current " + var2);
            System.out.println("   4 - Cancel the current " + var2);
            System.out.println("   5 - Search for items");
            System.out.println("   6 - Get a summary of the current " + var2);
            System.out.println("   7 - Create a new client");
            System.out.println("   8 - Switch to the next client");
            System.out.println("   9 - Exit this client");
            System.out.print("Enter your choice: ");

            try {
                this.line = input.readLine();
            } catch (IOException var5) {
                System.out.println("Error reading from input " + var5.getMessage());
                this.line = "9";
            }

            var1 = "";
            if (this.line.equals("1")) {
                System.out.print("\nEnter an item ID to add to the " + var2 + ": ");

                try {
                    this.line = input.readLine();
                    var1 = this.toCommand("ADD", this.line);
                } catch (IOException var4) {
                    System.out.println("Error reading from input " + var4.getMessage());
                    this.line = null;
                }
            } else if (this.line.equals("3")) {
                System.out.println("\nCompleting the current " + var2 + ".");
                var1 = this.toCommand("COMPLETE");
                this.currentTransaction = 0;
            } else if (this.line.equals("4")) {
                System.out.println("\nCancelling the current " + var2 + ".");
                var1 = this.toCommand("CANCEL");
                this.currentTransaction = 0;
            } else {
                var1 = this.commonCommands();
            }
        }

        return var1;
    }

    private String commonCommands() {
        String var1 = null;
        if (this.line.equals("5")) {
            System.out.print("\nYou selected search. Enter a string to search for:");

            try {
                this.line = input.readLine();
                var1 = this.toCommand("SEARCH", this.line);
            } catch (IOException var3) {
                System.out.println("Error reading from input " + var3.getMessage());
                this.line = null;
            }
        } else if (this.line.equals("6")) {
            var1 = this.toCommand("SUMMARY");
        } else if (this.line.equals("7")) {
            System.out.println("\nCreating new client...");
            var1 = createClient().getNextCommand();
        } else if (this.line.equals("8")) {
            System.out.println("\nSwitching to the next client...");
            var1 = nextClient().getNextCommand();
        } else if (this.line.equals("9")) {
            System.out.println("\nQuitting the current client...");
            InteractiveClient var2 = quitClient(this);
            if (var2 == null) {
                System.out.println("\nNo more clients. Exiting.");
                var1 = null;
            } else {
                var1 = var2.getNextCommand();
            }
        } else {
            System.out.println("Unknown command " + this.line);
            this.line = null;
        }

        return var1;
    }

    private String toCommand(String var1) {
        return this.toCommand(var1, (String)null);
    }

    private String toCommand(String var1, String var2) {
        String var3 = "" + this.clientID + "," + var1 + "," + sdf.format(Calendar.getInstance().getTime());
        if (var2 != null) {
            var3 = var3 + "," + var2;
        }

        return var3;
    }

    private static String getListOfItems() {
        String var0 = null;
        String var1 = "";

        while(var0 == null) {
            try {
                var0 = input.readLine();
            } catch (IOException var6) {
                System.out.println("Error reading from input " + var6.getMessage());
                var0 = "";
            }

            if (var0.length() > 0) {
                String var2 = var0;
                System.out.print("Enter quantity of item " + var0 + ": ");

                try {
                    var0 = input.readLine();
                    Integer.parseInt(var0);
                    var1 = var1 + "," + var2 + "," + var0;
                    var0 = null;
                } catch (IOException var4) {
                    System.out.println("Error reading from input " + var4.getMessage());
                    var0 = "";
                } catch (NumberFormatException var5) {
                    System.out.println("Invalid quantity " + var0);
                    var0 = null;
                }

                if (var0 == null) {
                    System.out.print("Enter next item (blank input to quit)");
                }
            }
        }

        return var1;
    }

    public static void main(String[] var0) {
        for(String var1 = nextCommand(); var1 != null; var1 = nextCommand()) {
            System.out.println("received --> " + var1);
        }

    }
}