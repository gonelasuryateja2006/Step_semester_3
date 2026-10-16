// Custom Node class as requested
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// Singly Linked List class
class SinglyLinkedList {
    private Node head;

    public SinglyLinkedList() {
        this.head = null;
    }

    // 1. Add a token at the front (priority)
    public void addFirst(int token) {
        Node newNode = new Node(token);
        newNode.next = head;
        head = newNode;
    }

    // 2. Add a token at the end (normal)
    public void addLast(int token) {
        Node newNode = new Node(token);
        if (head == null) {
            head = newNode;
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    // 3. Insert a token at a given 0-based position (rescheduled)
    public void insertAt(int index, int token) {
        // If inserting at the front
        if (index == 0) {
            addFirst(token);
            return;
        }

        Node newNode = new Node(token);
        Node current = head;

        // Walk to the node just before the insertion index
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        // Set the new node's next first, then link the previous node to it
        newNode.next = current.next;
        current.next = newNode;
    }

    // Utility method to print the list in the specified format
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // Main method matching the example scenario
    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();

        // Creating the starting list: 101 -> 102 -> 103
        list.addLast(101);
        list.addLast(102);
        list.addLast(103);

        // Sequence of operations from the example
        list.addFirst(100);
        list.addLast(104);
        list.insertAt(2, 150);

        // Print final output
        list.printList();
    }
}