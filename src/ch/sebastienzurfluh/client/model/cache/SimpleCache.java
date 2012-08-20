package ch.sebastienzurfluh.client.model.cache;

/**
 * This is the simplest implementation possible with only one cached value, therefore no eviction policy.
 *
 *
 * @author Sebastien Zurfluh
 *
 * @param <Key>
 * @param <Value>
 */
public class SimpleCache<Key, Value> implements Cache<Key, Value> {
	Key key;
	Value value;

	@Override
	public Value get(Key key) {
		return this.key == key ? value : null;
	}

	@Override
	public void put(Key key, Value value) {
		this.key = key;
		this.value = value;
	}
}
