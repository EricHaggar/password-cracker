public class DatabaseMine implements DatabaseInterface {

    private class Entry {
        private String key;
        private String value;

        public Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

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
        N = 201193; // Chosen prime number to obtain reasonable load factor
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
        return (address >= 0)?address:(address + N);

    }

    public String save(String plainPassword, String encryptedPassword) {
        return null; //CHANGE
    }

    public String decrypt(String encryptedPassword) {
        return null;  //CHANGE
    }

    public int size() {
        return size;
    }

        
    public double loadFactor() {
		return (double)N/(double)size;
    } 
    
    public void printStatistics() {
        System.out.println("*** DatabaseMine Statistics ***");
        System.out.println("Size is " + size() + " passwords");
        System.out.println("Number of Indexes is " + N);
        System.out.println("Load Factor is " + loadFactor());
        System.out.println("Average Number of Probes is " + totalNumOfProbes);
        System.out.println("*** End DatabaseMine Statistics ***");
    }

}