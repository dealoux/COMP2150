public abstract class ListItem {
    public abstract void print();
}


class IntItem extends ListItem {
    private int data;
    public IntItem(int data) {
        this.data = data;
    }

    public void print() {
        System.out.print(data + " ");
    }
}

class CharItem extends ListItem {
    private char data;
    public CharItem(char data) {
        this.data = data;
    }

    public void print() {
        System.out.print(data + " ");
    }
}

