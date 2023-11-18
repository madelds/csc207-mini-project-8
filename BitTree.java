import java.io.*;

/**
 * An implementation of a bit tree, which 
 * stores mappings from bits to values.
 * 
 * @author Madel Sibal
 * 
 */
class BitTreeNode {

    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The value associated with the node
     */
    String val;  
    
    /**
     * The left child of the node
     */
    BitTreeNode left; 
       
    /**
     * The right child of the node
     */
    BitTreeNode right;     
           
    /**
     * The length of the bit string up to this node
     */
    int bitLength;
    
    // +--------------+------------------------------------------------
    // | Constructors |
    // +--------------+
            
    /**
     * Constructor for internal nodes
     */
    BitTreeNode(int bitLength) {
        this.bitLength = bitLength;
        this.val = "";
        this.left = null;
        this.right = null;
    }
}

/**
 * Represents a leaf node in the binary tree
 */
class BitTreeLeaf extends BitTreeNode {
    BitTreeLeaf(int bitLength) {
        super(bitLength);
    }
}

/**
 * Main class representing the binary tree
 */
public class BitTree {

    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The root of the binary tree
     */
    private BitTreeNode root;   

    /**
     * The length of the bit strings
     */
    private int n;              

    // +--------------+------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Constructor initializes the binary tree with given bit length
     */
    public BitTree(int n) {
        this.n = n;
        this.root = new BitTreeNode(0);
    }

    // +---------+-----------------------------------------------------
    // | Methods |
    // +---------+

    /**
     * Sets a value in the binary tree for a given bit string
     */
    public void set(String bits, String value) throws IllegalArgumentException {
        if (bits.length() != n || !isValidBitString(bits)) {
            throw new IllegalArgumentException("Invalid bit string");
        }

        BitTreeNode current = root;
        for (int i = 0; i < n; i++) {
            char bit = bits.charAt(i);
            if (bit == '0') {
                if (current.left == null) {
                    current.left = new BitTreeNode(i + 1);
                }
                current = current.left;
            } else if (bit == '1') {
                if (current.right == null) {
                    current.right = new BitTreeNode(i + 1);
                }
                current = current.right;
            }
        }

        current.val = value;
    }
      
    /**
     * Gets the value associated with a given bit string in the binary tree
     */
    public String get(String bits) throws IllegalArgumentException {
        if (bits.length() != n || !isValidBitString(bits)) {
            throw new IllegalArgumentException("Invalid bit string");
        }

        BitTreeNode current = root;
        for (int i = 0; i < n; i++) {
            char bit = bits.charAt(i);
            if (bit == '0') {
                if (current.left == null) {
                    throw new IllegalArgumentException("Path not found in the tree");
                }
                current = current.left;
            } else if (bit == '1') {
                if (current.right == null) {
                    throw new IllegalArgumentException("Path not found in the tree");
                }
                current = current.right;
            }
        }

        return current.val;
    }
          
    /**
     * Dumps the contents of the binary tree to a PrintWriter
     */
    public void dump(PrintWriter pen) {
        dumpHelper(root, "", pen);
    }

    /**
     * Recursive helper method for dump()
     */
    private void dumpHelper(BitTreeNode node, String bits, PrintWriter pen) {
        if (node != null) {
            if (node instanceof BitTreeLeaf) {
                pen.println(bits + "," + node.val);
            } else {
                dumpHelper(node.left, bits + "0", pen);
                dumpHelper(node.right, bits + "1", pen);
            }
        }
    }

    /**
     * Loads the binary tree from an InputStream
     */
    public void load(InputStream source) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    set(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a given bit string consists only of '0' and '1'
     */
    private boolean isValidBitString(String bits) {
        for (char bit : bits.toCharArray()) {
            if (bit != '0' && bit != '1') {
                return false;
            }
        }
        return true;
    }
}