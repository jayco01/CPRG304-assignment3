package appDomain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a record of a unique word, tracking its total frequency and 
 * specific occurrences (file names and line numbers).
 * Implements Comparable for alphabetical sorting and Serializable for persistence.
 */
public class WordRecord implements Comparable<WordRecord>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String word;
    private HashMap<String, ArrayList<Integer>> occurrences; // Key: Filename, Value: List of Line Numbers
    private int totalFrequency;

    /**
     * Constructs a new WordRecord for the specified word.
     * @param word The word to be tracked.
     */
    public WordRecord(String word) {
        this.word = word;
        this.occurrences = new HashMap<>();
        this.totalFrequency = 0;
    }

    /**
     * Records an occurrence of the word in a specific file at a specific line number.
     * Updates the total frequency count.
     * @param filename The name of the file where the word was found.
     * @param lineNumber The line number in the file.
     */
    public void addReference(String filename, int lineNumber) {
        occurrences.computeIfAbsent(filename, k -> new ArrayList<>()).add(lineNumber);
        totalFrequency++;
    }

    /**
     * Gets the word associated with this record.
     * @return The word string.
     */
    public String getWord() {
        return word;
    }

    /**
     * Generates a formatted string report of the word record based on the specified command option.
     * @param option The output format flag: "-pf" (files), "-pl" (lines), or "-po" (occurrences).
     * @return A formatted string representation of the record.
     */
    public String toString(String option) {
        StringBuilder sb = new StringBuilder();
        sb.append("Word: ").append(word);
        
        // Option -po adds frequency count
        if ("-po".equals(option)) {
            sb.append(" (Total Frequency: ").append(totalFrequency).append(")");
        }
        sb.append("\n");

        for (String filename : occurrences.keySet()) {
            sb.append("    File: ").append(filename);
            
            // Options -pl and -po add specific line numbers
            if ("-pl".equals(option) || "-po".equals(option)) {
                sb.append(" Lines: ");
                for (int line : occurrences.get(filename)) {
                    sb.append(line).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Compares this record to another WordRecord alphabetically by word (case-insensitive).
     * Used for sorting within the Binary Search Tree.
     * @param other The other WordRecord object to compare.
     * @return A negative integer, zero, or a positive integer as this word is less than, equal to, or greater than the specified word.
     */
    @Override
    public int compareTo(WordRecord other) {
        return this.word.compareToIgnoreCase(other.word);
    }
}