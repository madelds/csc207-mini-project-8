/**
 * Provides conversion between Braille, ASCII, and Unicode character sets.
 * Reads input arguments specifying the target character set and source 
 * text, then performs the conversion accordingly.
 * 
 * @author Madel Sibal
 */
public class BrailleASCII {

    /**
     * The main method that handles the conversion based on the provided command-line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java BrailleASCII <targetCharacterSet> <sourceText>");
            System.exit(1);
        }

        String targetCharacterSet = args[0].toLowerCase();
        String sourceText = args[1];

        switch (targetCharacterSet) {
            case "braille":
                String braille = convertToBraille(sourceText);
                System.out.println(braille);
                break;
            case "ascii":
                String ascii = convertToASCII(sourceText);
                System.out.println(ascii);
                break;
            case "unicode":
                String unicode = convertToUnicode(sourceText);
                System.out.println(unicode);
                break;
            default:
                System.out.println("Invalid target character set. Supported options: braille, ascii, unicode");
                System.exit(1);
        }
    }

    /**
     * Converts the source text to Braille.
     * 
     */
    private static String convertToBraille(String sourceText) {
        StringBuilder brailleResult = new StringBuilder();
        for (char c : sourceText.toCharArray()) {
            brailleResult.append(BrailleASCIITables.toBraille(c));
        }
        return brailleResult.toString();
    }

    /**
     * Converts the source text to ASCII.
     */
    private static String convertToASCII(String sourceText) {
        StringBuilder asciiResult = new StringBuilder();
        int length = sourceText.length();
        for (int i = 0; i < length; i += 6) {
            String brailleChunk = sourceText.substring(i, Math.min(i + 6, length));
            asciiResult.append(BrailleASCIITables.toASCII(brailleChunk));
        }
        return asciiResult.toString();
    }

    /**
     * Converts the source text to Unicode.
     */
    private static String convertToUnicode(String sourceText) {
        StringBuilder unicodeResult = new StringBuilder();
        int length = sourceText.length();
        for (int i = 0; i < length; i += 6) {
            String brailleChunk = sourceText.substring(i, Math.min(i + 6, length));
            
            while (brailleChunk.length() < 6) {
                brailleChunk += "0";
            }
            
            unicodeResult.append(BrailleASCIITables.toUnicode(brailleChunk));
        }
        return unicodeResult.toString();
    }
}
