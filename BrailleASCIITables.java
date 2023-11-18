import java.io.*;

/**
 * Provides Braille to ASCII, Braille to Unicode, 
 * and ASCII to Braille conversions using binary 
 * tree mappings for Braille characters.
 * 
 * @author Madel Sibal
 */

public class BrailleASCIITables {
    
    // +------------------+------------------------------------------------
    // | Fields           |
    // +------------------+

    /**
     * Binary tree for converting ASCII characters to Braille.
     */
    private static BitTree asciiToBrailleTree;

    /**
     * Binary tree for converting Braille to ASCII characters.
     */
    private static BitTree brailleToAsciiTree;

    /**
     * Binary tree for converting Braille to Unicode characters.
     */
    private static BitTree brailleToUnicodeTree;

    // +-------------+------------------------------------------------
    // | Initializer |
    // +-------------+

    static {
        try {
            brailleToAsciiTree = buildBitTree("brailleToASCII.txt", 6);
            brailleToUnicodeTree = buildBitTree("brailleToUnicode.txt", 6);
            asciiToBrailleTree = buildBitTree("ASCIIToBraille.txt", 8);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing trees: " + e.getMessage());
        }
    }

    // +------------------+------------------------------------------------
    // | Private Methods  |
    // +------------------+

    /**
     * Builds a BitTree from a given file containing Braille character mappings.
     */
    private static BitTree buildBitTree(String filename, int n) throws IOException {
        BitTree tree = new BitTree(n);

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    tree.set(parts[0], parts[1]);
                }
            }
        }

        return tree;
    }

    // +------------------+------------------------------------------------
    // | Public Methods   |
    // +------------------+

    /**
     * Converts an ASCII character to its Braille representation.
     */
    public static String toBraille(char letter) {
        String asciiBits = String.format("%8s", Integer.toBinaryString(letter)).replace(' ', '0');
        return asciiToBrailleTree.get(asciiBits);
    }

    /**
     * Converts a Braille representation to its corresponding ASCII character.
     */
    public static String toASCII(String bits) {
        return brailleToAsciiTree.get(bits);
    }

    /**
     * Converts a Braille representation to its corresponding Unicode character.
     */
    public static String toUnicode(String bits) {
        return brailleToUnicodeTree.get(bits);
    }
}
