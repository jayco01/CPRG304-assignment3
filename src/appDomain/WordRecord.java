package appDomain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class WordRecord implements Comparable<WordRecord>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String word;
    private HashMap<String, ArrayList<Integer>> occurrences; // Key: Filename, Value: List of Line Numbers where word appears
    private int totalFrequency;

    public WordRecord(String word) {
        this.word = word;
        this.occurrences = new HashMap<>();
        this.totalFrequency = 0;
    }


    public void addReference(String filename, int lineNumber) {
        occurrences.computeIfAbsent(filename, k -> new ArrayList<>()).add(lineNumber);
        totalFrequency++;
    }

    public String getWord() {
        return word;
    }


    public String toString(String option) {
        StringBuilder sb = new StringBuilder();
        sb.append("Word: ").append(word);
        
        if ("-po".equals(option)) {
            sb.append(" (Total Frequency: ").append(totalFrequency).append(")");
        }
        sb.append("\n");

        for (String filename : occurrences.keySet()) {
            sb.append("    File: ").append(filename);
            
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

    @Override
    public int compareTo(WordRecord other) {
        return this.word.compareToIgnoreCase(other.word);
    }
}