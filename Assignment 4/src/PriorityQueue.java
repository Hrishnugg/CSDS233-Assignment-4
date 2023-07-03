import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class PriorityQueue<K extends Comparable<? super K>, V> {
    private KeyValuePair<K, V>[] heap;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    // Default constructor.
    public PriorityQueue(){
        heap = new KeyValuePair[DEFAULT_CAPACITY];
        size = 0;
        capacity = 10;
    }

    // Constructor with keys and corresponding values as arrays.
    // The arrays keys and values must be of equal length.
    public PriorityQueue(K[] keys, V[] values) throws IllegalArgumentException{
        if (keys.length != values.length){
            throw new IllegalArgumentException("Keys and values must be the same length");
        }
        heap = new KeyValuePair[keys.length+1];
        size = keys.length;
        capacity = keys.length+1;
        for (int i = 1; i < capacity; i++) {
            heap[i] = new KeyValuePair<>(keys[i-1], values[i-1]);
        }
        buildHeap();
    }

    // Adds the given key - value pair to the priority queue.
    public void add(K key, V value){
        // Resize the heap if necessary.
        size++;
        if (size >= capacity){
            resize();
        }
        heap[size] = new KeyValuePair<>(key, value);
        int index = size;
        // Compare with parent and propagate up
        propagateUp(index);
    }

    public void update(K key, V value) throws NoSuchElementException{
        int index = findElementByValue(value);
        heap[index] = new KeyValuePair<>(key, value);
        // Compare with parent and propagate up
        index = propagateUp(index);
        // Compare with children and propagate down
        heapifyMax(index);
    }

    public V peek() throws NoSuchElementException {
        if (size == 0){
            throw new NoSuchElementException("Heap is empty");
        }
        return heap[1].getValue();
    }
    public V[] peek(int k) throws IllegalArgumentException{
        if (k > size){
            throw new IllegalArgumentException("k is greater than the size of the heap");
        }
        if (size == 0 || k <= 0) {
            return null;
        }
        KeyValuePair<K, V>[] greatestKeyValuePairs = new KeyValuePair[size+1];

        @SuppressWarnings("unchecked")
        V[] values = (V[]) Array.newInstance(heap[1].getValue().getClass(), k);
        for (int i = 0; i < k; i++) {
            greatestKeyValuePairs[i] = pollKV();
        }
        for (int i = 0; i < k; i++) {
            values[i] = greatestKeyValuePairs[i].getValue();
            add(greatestKeyValuePairs[i].getKey(), greatestKeyValuePairs[i].getValue());
        }
        return values;
    }

    public V poll() throws NoSuchElementException{
        return pollKV().getValue();
    }
    public KeyValuePair<K, V> pollKV() throws NoSuchElementException{
        if (size == 0){
            throw new NoSuchElementException("Heap is empty");
        }
        KeyValuePair<K, V> KeyValuePair = heap[1];
        heap[1] = heap[size];
        size--;
        heapifyMax(1);
        return KeyValuePair;
    }
    public K poll(V value) throws NoSuchElementException{
        int index = findElementByValue(value);
        KeyValuePair<K, V> KeyValuePair = heap[index];
        heap[index] = heap[size];
        size--;
        heapifyMax(index);
        return KeyValuePair.getKey();
    }

    // Returns number of key-value pairs in the priority queue.
    public int size(){
        return this.size;
    }
    private void heapifyMax(int index){
        int left = 2*index;
        int right = 2*index+1;
        int largest = index;
        if (left <= size && heap[left].getKey().compareTo(heap[index].getKey()) > 0){
            largest = left;
        }
        if (right <= size && heap[right].getKey().compareTo(heap[largest].getKey()) > 0){
            largest = right;
        }
        // If the largest is not the index, swap and heapify
        if (largest != index){
            KeyValuePair<K, V> temp = heap[index];
            heap[index] = heap[largest];
            heap[largest] = temp;
            heapifyMax(largest);
        }
    }
    private void buildHeap(){
        for (int i = size/2; i > 0; i--) {
            heapifyMax(i);
        }
    }
    private void resize(){
        int newCapacity = capacity*2;
        KeyValuePair<K, V>[] tempHeap = new KeyValuePair[newCapacity];
        for (int i = 0; i < capacity; i++) {
            tempHeap[i] = heap[i];
        }
        heap = tempHeap;
        capacity = newCapacity;
    }

    private int propagateUp(int index) {
        while (index > 1 && heap[index].getKey().compareTo(heap[index/2].getKey()) > 0){
            KeyValuePair<K, V> temp = heap[index];
            heap[index] = heap[index/2];
            heap[index/2] = temp;
            index = index/2;
        }
        return index;
    }

    private int findElementByValue(V value) {
        int index = -1;
        // Find the index of the element with the given value
        for (int i = 1; i <= size; i++) {
            if (heap[i].getValue().equals(value)){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new NoSuchElementException("Value not found");
        }
        return index;
    }


}