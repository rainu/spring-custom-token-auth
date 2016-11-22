package de.rainu.example.store;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStore<K, V> {
	 protected final Map<K, V> store = new HashMap<>();

	 protected AbstractStore() {
		  initilizeStore();
	 }

	 protected abstract void initilizeStore();

	 protected V get(K key){
		  return store.get(key);
	 }

	 protected boolean contains(K key){
		  return store.containsKey(key);
	 }

	 protected void put(K key, V value){
		  store.put(key, value);
	 }

	 protected V remove(K key){
		  return store.remove(key);
	 }
}
