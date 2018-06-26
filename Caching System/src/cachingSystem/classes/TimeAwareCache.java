package cachingSystem.classes;

import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.DoublyLinkedListNode;
import dataStructures.classes.Pair;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;

/**
 * The TimeAwareCache offers the same functionality as the LRUCache, but also stores a timestamp for
 * each element. The timestamp is updated after each get / put operation for a key. This
 * functionality allows for time based cache stale policies (e.g. removing entries that are older
 * than 1 second).
 */
public class TimeAwareCache<K, V> extends LRUCache<K, V> {

    private HashMap<K, DoublyLinkedListNode<Pair<K, Timestamp>>> timestamps = new HashMap<>();
    /**
     * Get the timestamp associated with a key, or null if the key is not stored in the cache.
     *
     * @param key the key
     * @return the timestamp, or null
     */

    public Timestamp getTimestampOfKey(K key) {
        DoublyLinkedListNode<Pair<K, Timestamp>> node = timestamps.get(key);
        return node.getVal().getValue();
    }

    @Override
    public V get(K key) {
        clearStaleEntries();
        if (cache.get(key) != null) {
            DoublyLinkedListNode<Pair<K, V>> node = cache.get(key);
            DoublyLinkedListNode<Pair<K, Timestamp>> time = timestamps.get(key);
            V res = node.getVal().getValue();
            nodes.erase(node);
            nodes.pushFront(node);
            cacheListener.onHit(key);
            //update timestamp
            Pair<K, Timestamp> pair = time.getVal();
            pair.setValue(Timestamp.from(Instant.now()));
            time.setVal(pair);
            timestamps.put(key, time);
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
            //update timestamp
            DoublyLinkedListNode<Pair<K, Timestamp>> time = timestamps.get(key);
            timestamps.remove(key);
            Pair<K, Timestamp> timePair = time.getVal();
            timePair.setValue(Timestamp.from(Instant.now()));
            time.setVal(timePair);
            timestamps.put(key, time);
        }
        else {
            Timestamp tmpstmp = Timestamp.from(Instant.now());
            DoublyLinkedListNode<Pair<K, V>> node;
            DoublyLinkedListNode<Pair<K, Timestamp>> time;
            node = new DoublyLinkedListNode<>(new Pair(key, value));
            nodes.pushFront(node);
            cache.put(key, node);
            //insert time
            time = new DoublyLinkedListNode<>(new Pair(key, tmpstmp));
            timestamps.put(key, time);
        }
        clearStaleEntries();
        cacheListener.onPut(key, value);
    }

    public void clearAll() {
        super.clearAll();
        timestamps.clear();
    }

    @Override
    public V remove(K key) {
        timestamps.remove(key);
        return super.remove(key);
    }
    /**
     * Set a cache stale policy that should remove all elements older than @millisToExpire
     * milliseconds. This is a convenience method for setting a time based policy for the cache.
     *
     * @param millisToExpire the expiration time, in milliseconds
     */
    public void setExpirePolicy(long millisToExpire) {
            stalePolicy = new CacheStalePolicy() {
            @Override
            public boolean shouldRemoveEldestEntry(Pair entry) {
                if (entry != null) {
                    Timestamp time = timestamps.get(entry.getKey()).getVal().getValue();
                    Timestamp currentTime = Timestamp.from(Instant.now());
                    if (currentTime.getTime() - time.getTime() > millisToExpire) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
