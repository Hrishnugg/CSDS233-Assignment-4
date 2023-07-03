import java.util.NoSuchElementException;

public class HashTable<T>{
    private static final int DEFAULT_CAPACITY = 17;
    private static final int PRIMES[] = {17, 37, 79, 163, 331, 673, 1361, 2729, 5471, 10949, 21911, 43853, 87719};
    private Node[] table;
    //Number of buckets in the HashTable
    private int numBuckets;
    //Number of elements in the HashTable
    private int size;

    private class Node<T>{
        private String key;
        private T value;
        private Node next;

        public String getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
        public Node(String key, T value){
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    public HashTable(){
        table = new Node[DEFAULT_CAPACITY];
        numBuckets = DEFAULT_CAPACITY;
        size = 0;
    }
    public HashTable(int capacity){
        table = new Node[capacity];
        numBuckets = capacity;
        size = 0;
    }
    public T get(String key) throws NoSuchElementException {
        int bucket = getBucket(key);
        Node<T> node = table[bucket];
        // Check if key already exists
        while (node != null){
            if(node.getKey().equals(key)){
                return node.getValue();
            }
            node = node.getNext();
        }
        throw new NoSuchElementException("Key was not found");
    }
    private int nextPrime(int n){
        for (int i = 0; i < PRIMES.length; i++) {
            if(PRIMES[i] > n){
                return PRIMES[i];
            }
        }
        return PRIMES[PRIMES.length - 1];
    }
    private void rehash(){
        Node[] oldTable = table;
        int oldNumBuckets = numBuckets;
        numBuckets = nextPrime(2 * oldNumBuckets);
        table = new Node[numBuckets];
        size = 0;
        // Insert all elements from the old table to the new table
        for (int i = 0; i < oldNumBuckets; i++) {
            Node<T> node = oldTable[i];
            while (node != null){
                put(node.getKey(), node.getValue());
                node = node.getNext();
            }
        }
    }
    public void put(String key, T value){
        int bucket = getBucket(key);
        Node<T> node = table[bucket];
        // Check if key already exists
        while (node != null){
            if(node.getKey().equals(key)){
                node.setValue(value);
                return;
            }
            node = node.getNext();
        }
        // Insert new node at the beginning of the collision list
        Node<T> newNode = new Node<>(key, value);
        newNode.setNext(table[bucket]);
        table[bucket] = newNode;
        size++;
        if(size > numBuckets){
            rehash();
        }
    }

    public T remove(String key) throws NoSuchElementException {
        int bucket = getBucket(key);
        Node<T> node = table[bucket];
        Node<T> prev = null;
        while (node != null){
            // If found, delete from the collision list
            if(node.getKey().equals(key)){
                if(prev == null){
                    table[bucket] = node.getNext();
                } else {
                    prev.setNext(node.getNext());
                }
                size--;
                return node.getValue();
            }
            prev = node;
            node = node.getNext();
        }
        throw new NoSuchElementException("Key was not found");
    }
    private int getBucket(String key){
        return Math.abs(key.hashCode()) % numBuckets;
    }
    public int size(){
        return size;
    }

}
