// Custom Node class definition as required
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class Solution {
    public static Node removeAll(Node head, int code) {
        // Step 1: Remove matching nodes from the front by moving the head
        while (head != null && head.data == code) {
            head = head.next;
        }

        // If the list is empty after removing leading target nodes
        if (head == null) {
            return null;
        }

        // Step 2: Walk through the remaining list using a pointer and skip matches
        Node current = head;
        while (current.next != null) {
            if (current.next.data == code) {
                current.next = current.next.next; // Skip the matching node
            } else {
                current = current.next; // Move forward only if no node was skipped
            }
        }

        return head;
    }
}