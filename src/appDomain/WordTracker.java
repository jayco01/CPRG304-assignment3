package appDomain;

import implementations.BSTree;
import implementations.BSTreeNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;


public class WordTracker {

    private BSTree<WordRecord> wordTree;

    public WordTracker() {
        this.wordTree = new BSTree<>();
    }

    /**
     * Reads a text file and populates the BSTree with words, line numbers, and filenames.
     * @param filename The path to the text file to process.
     */
    public void processFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.err.println("Input file not found: " + filename);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 1;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                // skip empty lines when parsing
                if (!line.trim().isEmpty()) {
                    // Split the line by non-word characters (punctuation, whitespace)
                    String[] words = line.split("\\W+");// Regex "\\W+" means "one or more non-word characters"
                    
                    for (String w : words) {
                        if (w.isEmpty()) continue; // Skip empty strings
                        
                        // temporary record to search with
                        WordRecord searchKey = new WordRecord(w);
                        BSTreeNode<WordRecord> foundNode = wordTree.search(searchKey);

                        if (foundNode != null) {
                            // if word exists. Update the existing record.
                            foundNode.getElement().addReference(filename, lineNumber);
                        } else {
                            // if word is new. Add the reference to our key and add to tree.
                            searchKey.addReference(filename, lineNumber);
                            wordTree.add(searchKey);
                        }
                    }
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    // helper method to access tree
    public BSTree<WordRecord> getWordTree() {
        return wordTree;
    }

    /*
     * This is the App's entry point.
     * 
     * */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("use java -jar WordTracker.jar....");
            return;
        }

        String inputFileName = args[0];
        
        WordTracker tracker = new WordTracker();
        
        // TODO: Step 6 - Check if repository.ser exists and restore the tree
        
        // --- STEP 4 ---
        tracker.processFile(inputFileName);
        // -----------------------------
        
        // Step 5 - store tree in repository.ser
        tracker.saveRepository();
        
        System.out.println(
        		"Tree constructed from " + inputFileName 
        		+ "with size:" + tracker.getWordTree().size());
  
        // TODO: Step 7 - Handle command line options (-pf/-pl/-po) and generate report
        
    } 
    
    // step 5 - write tree
    private void saveRepository() {
    	try (ObjectOutputStream oos = 
    			new ObjectOutputStream(new FileOutputStream("repository.ser"))) {
    		
    		oos.writeObject(wordTree);
    		
    		System.out.println("repository.ser has been successfully updated!");
    		
    	} catch (IOException e) {
    		System.err.println("Error: failed to save repository.ser");
    		e.printStackTrace();
    	}
    }
      

}