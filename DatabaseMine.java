/*
DatabaseMine stores all the passwords (value) and 
their respective hash code (key) in my own implementation of a hash map 
using an inner class (Entry) stored in an array
*/
public class DatabaseMine implements DatabaseInterface {

    /*
    Entry stores the key and value
    */
    private class Entry {
        private String key;
        private String value;

        /*
        Constructor to initialize the key and value 
        */
        public Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /*
        Returns key
        */
        public String getKey() {
            return key;
        }

        /*
        Returns value
        */
        public String getValue() {
            return value;
        }

        /*
        Sets value
        */
        public void setValue(String value) {
            this.value = value;
        }
    }

    // Defining class variables
    private int N; // this is a prime number that gives the number of addresses
    private Entry[] hashTable;
    int totalNumOfProbes;
    int numOfDisplacements;
    int size;

    // these constructors must create your hash tables with enough positions N
    // to hold the entries you will insert; you may experiment with primes N
    public DatabaseMine() {
        N = 174613; // Chosen prime number to obtain reasonable load factor
        hashTable = new Entry[N];
        size = 0;
        numOfDisplacements = 0;
        totalNumOfProbes = 0;
    }

    public DatabaseMine(int N) {
        this.N = N;
        hashTable = new Entry[N];
        size = 0;
        numOfDisplacements = 0;
        totalNumOfProbes = 0;
    }

    public int hashFunction(String key) {
        int address = key.hashCode() % N;
        return (address >= 0) ? address : (address + N);

    }

    /*
    Stores values in array using algorithms seen in class and returns previousValue
    */
    public String save(String plainPassword, String encryptedPassword) {
        Entry newEntry = new Entry(encryptedPassword, plainPassword);
        int address = hashFunction(encryptedPassword);
        String previousValue = null;

        // Checks 3 cases 
        if (hashTable[address] == null) {

            hashTable[address] = newEntry;
            totalNumOfProbes++;
            size++;

        } else if (hashTable[address].getValue().equals(plainPassword)) {

            previousValue = hashTable[address].getValue();
            hashTable[address].setValue(plainPassword);
            totalNumOfProbes++;

        } else {

            while (hashTable[address] != null && !hashTable[address].getValue().equals(plainPassword)) {

                if (address == hashTable.length - 1) {
                    address = 0;
                } else {
                    address++;
                }

                numOfDisplacements++;
                totalNumOfProbes++;

            }

            if (hashTable[address] == null) {

                hashTable[address] = newEntry;
                totalNumOfProbes++;
                size++;

            } else {

                previousValue = hashTable[address].getValue();
                hashTable[address].setValue(plainPassword);
                totalNumOfProbes++;

            }

        }

        return previousValue;

    }

    /* 
    Returns password if found, otherwise "NO_SUCH_KEY"
    Uses algorithm seen in class
    */
    public String decrypt(String encryptedPassword) {
        int address = hashFunction(encryptedPassword);  
        String notFound = "NO_SUCH_KEY";
        int numOfProbes = 0;

        while (numOfProbes != N) {

            Entry currentEntry = hashTable[address];
            
            if (hashTable[address] == null) {

                return notFound;

            } else if (currentEntry.getKey().equals(encryptedPassword)) {

                return currentEntry.getValue();

            } else {

                address++;
                numOfProbes = 0;
                
            }

        }
      
        return notFound;
    }

    public int size() {
        return size;
    }

    public double loadFactor() {
        return (double) size / (double) N;
    }

    public double averageNumOfProbes() {
        return (double) totalNumOfProbes / (double) size;
    }

    public void printStatistics() {
        System.out.println("*** DatabaseMine Statistics ***");
        System.out.println("Size is " + size() + " passwords");
        System.out.println("Number of Indexes is " + N);
        System.out.println("Load Factor is " + loadFactor());
        System.out.println("Average Number of Probes is " + averageNumOfProbes());
        System.out.println("Number of Displacements (due to collisions) " + numOfDisplacements);
        System.out.println("*** End DatabaseMine Statistics ***");
    }

}
