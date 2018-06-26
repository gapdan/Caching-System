package observerPattern.classes;

import java.util.*;

import dataStructures.classes.Pair;
import observerPattern.interfaces.CacheListener;

/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 * @param <V>
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {
    private HashMap<K, Integer> myHits = new HashMap<>();
    private HashMap<K, Integer> myMisses = new HashMap<>();
    private HashMap<K, Integer> myPuts = new HashMap<>();
    /**
     * Get the number of hits for a key.
     *
     * @param key the key
     * @return number of hits
     */
    public int getKeyHits(K key) {
        return myHits.get(key);
    }

    /**
     * Get the number of misses for a key.
     *
     * @param key the key
     * @return number of misses
     */
    public int getKeyMisses(K key) {
        return myMisses.get(key);
    }

    /**
     * Get the number of updates for a key.
     *
     * @param key the key
     * @return number of updates
     */
    public int getKeyUpdates(K key) {
        return myPuts.get(key);
    }

    /**
     * Get the @top most hit keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    private Comparator<Pair<K, Integer>> comp = new Comparator<Pair<K, Integer>>() {
        @Override
        public int compare(Pair<K, Integer> o1, Pair<K, Integer> o2) {
            Integer a = o1.getValue();
            Integer b = o2.getValue();
            return a.compareTo(b);
        }
    };

    private List<K> getTopFromHash(int top, HashMap<K, Integer> myHash) {
        PriorityQueue<Pair<K, Integer>> myHeap = new PriorityQueue<>(comp);
        Iterator it = myHash.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry aux = (Map.Entry) it.next();
            K key = (K) aux.getKey();
            Integer val = (Integer) aux.getValue();
            Pair pair = new Pair(key, val);
            if (myHeap.size() < top) {
                myHeap.add(pair);
            }
            else {
                Pair<K, Integer> min = myHeap.peek();
                if (comp.compare(min, pair) < 0) {
                    myHeap.poll();
                    myHeap.add(pair);
                }
            }
        }
        ArrayList<K> topHits = new ArrayList<>();
        while (myHeap.size() > 0) {
            Pair<K, Integer> aux = myHeap.poll();
            topHits.add(aux.getKey());
        }
        Collections.reverse(topHits);
        return topHits;
    }

    public List<K> getTopHitKeys(int top) {
        return getTopFromHash(top, myHits);
    }

    /**
     * Get the @top most missed keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopMissedKeys(int top) {
        return getTopFromHash(top, myMisses);
    }

    /**
     * Get the @top most updated keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopUpdatedKeys(int top) {
        return getTopFromHash(top, myPuts);
    }

    @Override
    public void onHit(K key) {
        Integer nrOfHits = myHits.get(key);
        if (nrOfHits == null) {
            myHits.put(key, 1);
        }
        else {
            myHits.put(key, nrOfHits + 1);
        }
    }

    @Override
    public void onMiss(K key) {
        Integer nrOfMisses = myMisses.get(key);
        if (nrOfMisses == null) {
            myMisses.put(key, 1);
        }
        else {
            myMisses.put(key, nrOfMisses + 1);
        }
    }

    @Override
    public void onPut(K key, V value) {
        Integer nrOfPuts = myPuts.get(key);
        if (nrOfPuts == null) {
            myPuts.put(key, 1);
        }
        else {
            myPuts.put(key, nrOfPuts + 1);
        }
    }
}
