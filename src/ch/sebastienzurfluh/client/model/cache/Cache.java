package ch.sebastienzurfluh.client.model.cache;

public interface Cache<Key, Value> {  
	Value get(final Key key);  
	void put(final Key key, final Value value);
}