public class Node {
    private ListItem data;
    private Node next;

    public Node(ListItem data, Node next) {
        this.data = data;
        this.next = next;
    }

    public void print() {
        data.print(); // print out current node's data.
        // print out everything else.
        if (next != null)
            next.print();

    }
}