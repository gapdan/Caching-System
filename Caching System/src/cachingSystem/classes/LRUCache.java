package cachingSystem.classes;

import dataStructures.classes.DoublyLinkedList;
import dataStructures.classes.DoublyLinkedListNode;
import dataStructures.classes.Pair;

import java.util.HashMap;

/**
 * This cache is very similar to the FIFOCache, but guarantees O(1) complexity for the get, put and
 * remove operations.
 */
public class LRUCache<K, V> extends ObservableCache<K, V> {

    protected DoublyLinkedList<Pair<K, V>> nodes = new DoublyLinkedList<>();
    protected HashMap<K, DoublyLinkedListNode<Pair<K, V>>> cache = new HashMap<>();

    @Override
    public V get(K key) {
        if (cache.get(key) != null) {
            DoublyLinkedListNode<Pair<K, V>> node = cache.get(key);
            V res = node.getVal().getValue();
            nodes.erase(node);
            nodes.pushFront(node);
            cacheListener.onHit(key);
            return res;
        }
        else {
            cacheListener.onMiss(key);
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if (cache.get(key) != null) {
            DoublyLinkedListNode<Pair<K, V>> node = cache.get(key);
            nodes.erase(node);
            Pair<K, V> pair = node.getVal();
            pair.setValue(value);
            node.setVal(pair);
            nodes.pushFront(node);
            cache.put(key, node);
        }
        else {
            DoublyLinkedListNode<Pair<K, V>> node;
            node = new DoublyLinkedListNode<>(new Pair(key, value));
            nodes.pushFront(node);
            cache.put(key, node);
        }
        clearStaleEntries();
        cacheListener.onPut(key, value);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public V remove(K key) {
        if (cache.get(key) != null) {
            DoublyLinkedListNode<Pair<K, V>> node = cache.get(key);
            nodes.erase(node);
            cache.remove(key);
            return node.getVal().getValue();
        }
        return null;
    }

    @Override
    public void clearAll() {
        cache.clear();
        nodes.clear();
    }

    @Override
    public Pair<K, V> getEldestEntry() {
        DoublyLinkedListNode<Pair<K, V>> node = nodes.getLast();
        if (node != null) {
            return node.getVal();
        }
        else {
            return null;
        }
    }
}
