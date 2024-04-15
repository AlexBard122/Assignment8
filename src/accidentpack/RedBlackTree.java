package accidentpack;

import java.time.LocalDate;

/**
 * @author Devin C 
 * This class implements a Red-Black search tree for report objects.
 */
public class RedBlackTree {
    // Define colors for Red-Black tree
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // NODE structure
    class Node {
        report data;
        boolean color;
        Node left;
        Node right;
        Node parent;

        // Constructor for Node
        public Node(report data) {
            this.data = data;
            this.color = RED; // New nodes are always red
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    Node root;

    // Constructor
    public RedBlackTree() {
        root = null;
    }

    // Inserts a node with given value into the tree
    public void add(report key) {
        if (root == null) {
            root = new Node(key);
            root.color = BLACK; // Root is always black
        } else {
            root = insert(root, key);
            root.color = BLACK; // Ensure root remains black
        }
    }

    // BST insertion and balancing for Red-Black tree
    private Node insert(Node root, report key) {
        if (root == null)
            return new Node(key);

        if (key.compareTo(root.data) < 0) {
            root.left = insert(root.left, key);
            root.left.parent = root;
        } else if (key.compareTo(root.data) >= 0) {
            root.right = insert(root.right, key);
            root.right.parent = root;
        }

        // Balance the tree after insertion
        return balance(root);
    }

    // Balance the tree after insertion
    private Node balance(Node root) {
        if (isRed(root.right))
            root = rotateLeft(root);
        if (isRed(root.left) && isRed(root.left.left))
            root = rotateRight(root);
        if (isRed(root.left) && isRed(root.right))
            flipColors(root);

        return root;
    }

    // Checks if node is red (or null is considered black)
    private boolean isRed(Node node) {
        if (node == null)
            return false;
        return node.color == RED;
    }

    // Performs left rotation
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;
        y.left = x;
        y.color = x.color;
        x.color = RED;
        return y;
    }

    // Performs right rotation
    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;
        x.right = y;
        x.color = y.color;
        y.color = RED;
        return x;
    }

    // Flip colors of a node and its children
    private void flipColors(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    // Searches for a node with given value in the tree
    public int search(report key) {
        if (findNode(root, key) == null) {
            return 0;
        } else {
            return 1;
        }
    }

    // Finds the node with given value
    private Node findNode(Node root, report key) {
        if (root == null || key.compareTo(root.data) == 0)
            return root;
        if (key.compareTo(root.data) < 0)
            return findNode(root.left, key);
        else
            return findNode(root.right, key);
    }

    // Deletes a node with given value from the tree
    public void delete(report key) {
        if (findNode(root, key) != null) {
            root = remove(root, key);
            System.out.println("Deletion successful ");
        } else
            System.out.println("No node with entered value found in tree");
    }

    // BST deletion and balancing for Red-Black tree
    private Node remove(Node root, report key) {
        if (key.compareTo(root.data) < 0) {
            if (!isRed(root.left) && !isRed(root.left.left))
                root = moveRedLeft(root);
            root.left = remove(root.left, key);
        } else {
            if (isRed(root.left))
                root = rotateRight(root);
            if (key.compareTo(root.data) == 0 && root.right == null)
                return null;
            if (!isRed(root.right) && !isRed(root.right.left))
                root = moveRedRight(root);
            if (key.compareTo(root.data) == 0) {
                Node x = min(root.right);
                root.data = x.data;
                root.right = deleteMin(root.right);
            } else
                root.right = remove(root.right, key);
        }
        return balance(root);
    }

    // Moves a red node to the left
    private Node moveRedLeft(Node node) {
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    // Moves a red node to the right
    private Node moveRedRight(Node node) {
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    // Deletes the minimum node
    private Node deleteMin(Node node) {
        if (node.left == null)
            return null;
        if (!isRed(node.left) && !isRed(node.left.left))
            node = moveRedLeft(node);
        node.left = deleteMin(node.left);
        return balance(node);
    }

    // Finds the minimum node
    private Node min(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // Performs in-order traversal of the tree
    void inOrder(Node root) {
        if (root == null) {
            System.out.println("No nodes in the tree");
            return;
        }
        if (root.left != null)
            inOrder(root.left);
        System.out.print(root.data.getID() + " ");
        if (root.right != null)
            inOrder(root.right);
    }

    // Performs pre-order traversal of the tree
    void preOrder(Node root) {
        if (root == null) {
            System.out.println("No nodes in the tree");
            return;
        }
        System.out.println(root.data.getID() + " ");
        if (root.left != null)
            preOrder(root.left);
        if (root.right != null)
            preOrder(root.right);
    }
    
    
    // Helper method to perform pre-order traversal of the tree and count nodes
    static int preOrderCount(Node root) {
        if (root == null) {
            return 0;
        }

        // Initialize count for this subtree
        int count = 1; // Count the current node

//        // Print the ID of the current node
//        System.out.print(root.data.getID() + " ");

        // Recursively traverse the left subtree and accumulate count
        count += preOrderCount(root.left);

        // Recursively traverse the right subtree and accumulate count
        count += preOrderCount(root.right);

        return count; // Return the total count for this subtree
    }

    // Performs post-order traversal of the tree
    void postOrder(Node key) {
        if (key == null) {
            System.out.println("No nodes in the tree");
            return;
        }
        if (key.left != null)
            postOrder(key.left);
        if (key.right != null)
            postOrder(key.right);
        System.out.print(key.data.getID() + " ");
    }
    
    // Method to find the first occurrence of a node with the specified date
    public Node findFirstNodeWithDate(Node node, LocalDate date) {
        if (node == null) {
            return null;
        }
        if (date.isBefore(node.data.getStartTime())) {
            return findFirstNodeWithDate(node.left, date);
        } else if (date.isAfter(node.data.getStartTime())) {
            return findFirstNodeWithDate(node.right, date);
        } else {
            // Found the first node with the specified date
            return node;
        }
    }

    // Method to count all following nodes from a given node
    public int countFollowingNodes(Node node) {
        if (node == null) {
            return 0;
        }
        int count = 1; // Count the current node
        count += countFollowingNodes(node.left); // Count nodes in the left subtree
        count += countFollowingNodes(node.right); // Count nodes in the right subtree
        return count;
    }

    // Method to find the first occurrence of a node with the specified date
    // and count all following nodes
    public int countNodesFromDate(LocalDate date) {
        Node firstNodeWithDate = findFirstNodeWithDate(root, date);
        if (firstNodeWithDate == null) {
            return 0; // No node found with the specified date
        }
        return countFollowingNodes(firstNodeWithDate);
    }
    
    /**
     * @author abard
     * returns the number of nodes which come after a given node
     * & have dates >= the given node
     * @param root
     * @return int
     */
    int countAfter(Node root, LocalDate date) {
        if(root == null)
            return 0;
        else
            if(root.data.getStartTime().isBefore(date))
                return countAfter(root.right, date);
            //if root left date is before given date, count root and right root nodes
            if(root.left != null)
                if(root.left.data.getStartTime().isBefore(date))
                    return 1 + countAfter(root.right, date);
                else
                    return 1 + countAfter(root.right, date) + countAfter(root.left, date);
            return 1 + countAfter(root.right, date);        
    }
}
