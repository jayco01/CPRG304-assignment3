package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import appDomain.WordRecord;

public class WordRecordTest {

    private WordRecord record;

    @Before
    public void setUp() {
        record = new WordRecord("TestWord");
    }

    @Test
    public void testConstructor() {
        assertEquals("Word should be set correctly", "TestWord", record.getWord());
    }

    @Test
    public void testAddReferenceAndTotalFrequency() {
        record.addReference("file1.txt", 10);
        record.addReference("file1.txt", 20);
        record.addReference("file2.txt", 5);
        String output = record.toString("-po");
        
        assertTrue("Output should contain total frequency 3", output.contains("Frequency: 3"));
        assertTrue("Output should contain file1.txt", output.contains("file1.txt"));
        assertTrue("Output should contain file2.txt", output.contains("file2.txt"));
    }



    @Test
    public void testCompareTo() {
        WordRecord apple = new WordRecord("apple");
        WordRecord banana = new WordRecord("banana");
        WordRecord Apple = new WordRecord("Apple");

  
        assertTrue("apple should be smaller than banana", apple.compareTo(banana) < 0);
        assertTrue("banana should be bigger than apple", banana.compareTo(apple) > 0);
        assertEquals("apple and Apple should be equal", 0, apple.compareTo(Apple));
    }
}