package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

import java.util.ArrayList;

/**
 * The BroadcastListener broadcasts cache events to other listeners that have been added to it.
 */
public class BroadcastListener<K, V> implements CacheListener<K, V> {

    /**
     * Add a listener to the broadcast list.
     *
     * @param listener the listener
     */
    private ArrayList<CacheListener<K, V>> listeners = new ArrayList<>();
    public void addListener(CacheListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void onHit(K key) {
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onHit(key);
        }
    }

    @Override
    public void onMiss(K key) {
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onMiss(key);
        }
    }

    @Override
    public void onPut(K key, V value) {
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onPut(key, value);
        }
    }


}
