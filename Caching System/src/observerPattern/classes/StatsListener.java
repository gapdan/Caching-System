package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

/**
 * The StatsListener collects hit / miss / update stats for a cache.
 *
 * @param <K>
 * @param <V>
 */
public class StatsListener<K, V> implements CacheListener<K, V> {

    /**
     * Get the number of hits for the cache.
     *
     * @return number of hits
     */
    private int nrOfHits = 0;
    private int nrOfMisses = 0;
    private int nrOfPuts = 0;

    public int getHits() {
        return nrOfHits;
    }

    /**
     * Get the number of misses for the cache.
     *
     * @return number of misses
     */
    public int getMisses() {
        return nrOfMisses;
    }

    /**
     * Get the number of updates (put operations) for the cache.
     *
     * @return number of updates
     */
    public int getUpdates() {
        return nrOfPuts;
    }

    @Override
    public void onHit(K key) {
        ++nrOfHits;
    }

    @Override
    public void onMiss(K key) {
        ++nrOfMisses;
    }

    @Override
    public void onPut(K key, V value) {
        ++nrOfPuts;
    }
}
