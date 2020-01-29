package Fundamentals;

import libraries.*;

class LinkedList<Type> {
    Node<Type> first;
    private int N; // number of items

    int size() {
        return N;
    }

    boolean isEmpty() {
        return N == 0;
    }

    void insertHead(Type item) {
        Node<Type> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        N++;
    }

    void delete(int k) {
        if (k <= N) {
            if (k == 1) {
                first = first.next;
                N--;
            } else if (k > 1) {
                int i = 1;
                for (Node node = first; node != null; node = node.next) {
                    if (i == k - 1) {
                        node.next = node.next.next;
                        N--;
                        break;
                    }
                    i++;
                }
            }
        }
    }

    // k ranges from 0 to N-1
    Node<Type> getNode(int k) {
        if (0 <= k && k < N) {
            int i = 0;
            for (Node<Type> node = first; node != null; node = node.next) {
                if (i == k) {
                    return node;
                }
                i++;
            }
        }
        return null;
    }

    void removeNode(Node node) {
        if (node != null) {
            if (node == first) {
                first = first.next;
                N--;
            } else {
                for (Node currentNode = first; currentNode != null; currentNode = currentNode.next) {
                    if (currentNode.next == node) {
                        currentNode.next = currentNode.next.next;
                        N--;
                        break;
                    }
                }
            }
        }
    }

    void removeAfter(Node node) {
        if (node != null && node.next != null) {
            node.next = node.next.next;
        }
    }

    Node createNode(Type item) {
        Node node = new Node();
        node.item = item;
        return node;
    }

    void insertAfter(Node targetNode, Node newNode) {
        if (targetNode != null && newNode != null) {
            newNode.next = targetNode.next;
            targetNode.next = newNode;
            N++;
        }
    }

    void exchange(int i, int j) {
        if (i < 0 || j < 0 || i >= size() || j >= size() || i == j || size() < 2) return;
        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
        }

        if (i == 0) {
            Node<Type> oldFirst = first;
            Node<Type> iNext = oldFirst.next;
            Node<Type> newFirst = getNode(j);
            Node<Type> jLast = getNode(j - 1);
            oldFirst.next = newFirst.next;
            if (iNext == newFirst) {
                newFirst.next = oldFirst;
            } else {
                newFirst.next = iNext;
                jLast.next = oldFirst;
            }
            first = newFirst;
        } else {
            Node<Type> a = getNode(i);
            Node<Type> aNext = a.next;
            Node<Type> aLast = getNode(i - 1);
            Node<Type> b = getNode(j);
            Node<Type> bNext = b.next;
            Node<Type> bLast = getNode(j - 1);
            if (aNext == b) {
                a.next = bNext;
                b.next = a;
                aLast.next = b;
            } else {
                a.next = bNext;
                b.next = aNext;
                bLast.next = a;
                aLast.next = b;
            }
        }
    }

    private Node<String> merge(Node<String> leftPart, Node<String> rightPart) {
        Node<String> newHead, newTail;
        if (leftPart == null) {
            return rightPart;
        } else if (rightPart == null) {
            return leftPart;
        } else if (leftPart.item.compareTo(rightPart.item) <= 0) {
            newHead = newTail = leftPart;
            leftPart = leftPart.next;
        } else {
            newHead = newTail = rightPart;
            rightPart = rightPart.next;
        }

        while (leftPart != null && rightPart != null) {
            if (leftPart.item.compareTo(rightPart.item) <= 0) {
                newTail.next = leftPart;
                newTail = newTail.next;
                leftPart = leftPart.next;
            }
            else {
                newTail.next = rightPart;
                newTail = newTail.next;
                rightPart = rightPart.next;
            }
        }

        if (leftPart != null) {
            newTail.next = leftPart;
        }
        if (rightPart != null) {
            newTail.next = rightPart;
        }
        return newHead;
    }

    private Node<String> mergeSort(Node<String> head) {
        if (head.next == null) return head;
        Node<String> slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        Node<String> rHead = slow.next;
        slow.next = null;
        Node<String> l = mergeSort(head);
        Node<String> r = mergeSort(rHead);
        return merge(l, r);
    }

    Node<String> sort(Node<String> head) {
        if (head == null || head.next == null) return head;
        return mergeSort(head);
    }

    void printNode(Node node) {
        while (node != null) {
            StdOut.print(node.item + " -> ");
            node = node.next;
        }
        StdOut.println("NULL");
    }

    void print() {
        for (Node node = first; node != null; node = node.next) {
            StdOut.print(node.item + " -> ");
        }
        StdOut.println("NULL");
    }

    static Node<Integer> reverseIterativeSolution(Node<Integer> first) {
        if (first != null && first.next != null) {
            Node<Integer> reverse = null;
            while (first != null) {
                Node<Integer> second = first.next;
                first.next = reverse;
                reverse = first;
                first = second;
            }
            return reverse;
        }
        return first;
    }

    static Node<Integer> reverseRecursiveSolution(Node<Integer> first) {
        if (first == null || first.next == null) {
            return first;
        }
        Node<Integer> second = first.next;
        Node<Integer> reverse = reverseRecursiveSolution(second);
        second.next = first;
        first.next = null;
        return reverse;
    }

    public static void main(String[] args) {
        String line = "a b c d e f g h i j k l m n o p";
        String[] arr = line.split("\\s+");
        LinkedList<String> list = new LinkedList<>();
        for (String str : arr) {
            list.insertHead(str);
        }
        list.print();
        list.exchange(0, 1);
        list.exchange(0, 8);
        list.exchange(5, 6);
        list.exchange(1, 3);
        list.exchange(2, 15);
        list.print();
        list.first = list.sort(list.first);
        list.print();
    }
}