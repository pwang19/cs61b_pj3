/* HashTableChained.java */

package dict;

import list.*;

/**
 * HashTableChained implements a Dictionary as a hash table with chaining. All
 * objects used as keys must have a valid hashCode() method, which is used to
 * determine which bucket of the hash table an entry is stored in. Each object's
 * hashCode() is presumed to return an int between Integer.MIN_VALUE and
 * Integer.MAX_VALUE. The HashTableChained class implements only the compression
 * function, which maps the hash code to a bucket in the table's range.
 *
 * DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

	/**
	 * Place any data fields here.
	 **/
	private SList[] table;
	private int size;

	/**
	 * Construct a new empty hash table intended to hold roughly sizeEstimate
	 * entries. (The precise number of buckets is up to you, but we recommend
	 * you use a prime number, and shoot for a load factor between 0.5 and 1.)
	 **/

	public HashTableChained(int sizeEstimate) {
		table = new SList[sizeEstimate];
	}

	/**
	 * Construct a new empty hash table with a default size. Say, a prime in the
	 * neighborhood of 100.
	 **/

	public HashTableChained() {
		table = new SList[101];
	}

	/**
	 * Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
	 * to a value in the range 0...(size of hash table) - 1.
	 *
	 * This function should have package protection (so we can test it), and
	 * should be used by insert, find, and remove.
	 **/

	protected int compFunction(int code) {
		return Math.abs((127 * code + 73) % 16908799 % table.length);
	}

	/**
	 * Returns the number of entries stored in the dictionary. Entries with the
	 * same key (or even the same key and value) each still count as a separate
	 * entry.
	 * 
	 * @return number of entries in the dictionary.
	 **/

	public int size() {
		return size;
	}

	/**
	 * Tests if the dictionary is empty.
	 *
	 * @return true if the dictionary has no entries; false otherwise.
	 **/

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Create a new Entry object referencing the input key and associated value,
	 * and insert the entry into the dictionary. Return a reference to the new
	 * entry. Multiple entries with the same key (or even the same key and
	 * value) can coexist in the dictionary.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key
	 *            the key by which the entry can be retrieved.
	 * @param value
	 *            an arbitrary object.
	 * @return an entry containing the key and value.
	 **/

	public Entry insert(Object key, Object value) {
		Entry ins = new Entry();
		ins.key = key;
		ins.value = value;
		int index = compFunction(ins.key.hashCode());

		if (table[index] == null) {
			table[index] = new SList();
		}
		table[index].insertBack(ins);
		size++;
		return ins;
	}

	/**
	 * Search for an entry with the specified key. If such an entry is found,
	 * return it; otherwise return null. If several entries have the specified
	 * key, choose one arbitrarily and return it.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key
	 *            the search key.
	 * @return an entry containing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 * @throws InvalidKeyException
	 * @throws InvalidNodeException
	 **/

	public Entry find(Object key) throws InvalidKeyException,
			InvalidNodeException {
		int index = compFunction(key.hashCode());
		if (table[index] == null) {
			throw new InvalidKeyException();
		}
		return (Entry) table[index].front().item();
	}

	/**
	 * Remove an entry with the specified key. If such an entry is found, remove
	 * it from the table and return it; otherwise return null. If several
	 * entries have the specified key, choose one arbitrarily, then remove and
	 * return it.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key
	 *            the search key.
	 * @return an entry containing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 */

	public Entry remove(Object key) throws InvalidKeyException,
			InvalidNodeException {
		int index = compFunction(key.hashCode());
		if (table[index] == null) {
			throw new InvalidKeyException(key);
		}
		SListNode node = (SListNode) table[index].front();
		Entry entry = (Entry) node.item();
		node.remove();
		size--;
		return entry;
	}

	/**
	 * Remove all entries from the dictionary.
	 */
	public void makeEmpty() {
		for (int index = 0; index < table.length; index++) {
			table[index] = null;
		}
		size = 0;
	}

	public static void main(String[] args) {
		HashTableChained t = new HashTableChained();
		try {
			t.insert(0, 50);
			System.out.println(t.find(0).value);
			System.out.println(t.size);
			t.insert(0, 70);
			System.out.println(t.find(0).value);
			System.out.println(t.size);
			t.remove(0);
			System.out.println(t.find(0).value);
			System.out.println(t.size);
			t.makeEmpty();
			System.out.println(t.size);
			t.insert(0, 35);
			t.insert(1, 45);
			t.insert("ugly", "duckling");
			t.insert("ugly", "bugs");
			System.out.println(t.size);
			System.out.println(t.find(0).value);
			System.out.println(t.find("ugly").value);
			System.out.println(t.find(1).value);
			t.remove("ugly");
			System.out.println(t.find("ugly").value);
			System.out.println("removing. " + t.remove("ugly").value);
			t.makeEmpty();
			System.out.println(t.isEmpty());
//			System.out.println(t.remove("ugly")); // should and will throw error
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
