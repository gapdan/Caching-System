package dataStructures.classes;

/**
 * The Pair class is a convenient way of storing key-value pairs.
 *
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Pair) {
            Pair<K, V> aux = (Pair<K, V>) obj;
            if (aux.key.equals(key) && aux.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return key.toString();
    }
}
