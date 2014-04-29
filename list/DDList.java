package list;

public class DDList extends DList {

	/**
	 * newNode() calls the DDListNode constructor. Use this method to allocate
	 * new DDListNodes rather than calling the DDListNode constructor directly.
	 * That way, only this method need be overridden if a subclass of DList
	 * wants to use a different kind of node.
	 *
	 * @param item
	 *            the item to store in the node.
	 * @param list
	 *            the list that owns this node. (null for sentinels.)
	 * @param prev
	 *            the node previous to this node.
	 * @param next
	 *            the node following this node.
	 **/
	protected DDListNode newNode(Object item, Object item2, DList list, DListNode prev,
			DListNode next) {
		return new DDListNode(item, item2, list, prev, next);
	}

	/**
	 * DList() constructs for an empty DList.
	 **/
	public DDList() {
		head = newNode(null, null, null, null, null);
		head.prev = head;
		head.next = head;
		size = 0;
	}

	/**
	 * insertFront() inserts an item at the front of this DList.
	 *
	 * @param item
	 *            is the item to be inserted.
	 *
	 *            Performance: runs in O(1) time.
	 **/
	public void insertFront(Object item, Object item2) {
		DListNode temp = head.next;
		head.next = newNode(item, item2, this, head, temp);
		temp.prev = head.next;
		size++;
	}

	/**
	 * insertBack() inserts an item at the back of this DList.
	 *
	 * @param item
	 *            is the item to be inserted.
	 *
	 *            Performance: runs in O(1) time.
	 **/
	public void insertBack(Object item, Object item2) {
		DListNode temp = head.prev;
		head.prev = newNode(item, item2, this, temp, head);
		temp.next = head.prev;
		size++;
	}
}
