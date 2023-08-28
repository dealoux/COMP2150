public class List extends ListItem {
    private Node head;
    public List() {
        head = null;
    }

    public void print() {
        System.out.print("[");
        if (head != null) {
            head.print();
        }
        System.out.print("]");
    }

    public void add ( ListItem data ) {
        head = new Node(data, head);
    }
}

