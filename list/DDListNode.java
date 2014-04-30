package list;


/**
 * A DDListNode is a DListNode that stores 2 items instead of 1.
 * @author peter
 *
 */

public class DDListNode extends DListNode {
	protected Object item2;

	DDListNode(Object i, Object i2, DList l, DListNode p, DListNode n) {
		super(i, l, p, n);
		item2 = i2;
	}

	/**
	 * item2() returns this node's item2. If this node is invalid, throws an
	 * exception.
	 *
	 * @return the item stored in this node.
	 *
	 *         Performance: runs in O(1) time.
	 */
	public Object item2() throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException();
		}
		return item2;
	}

	/**
	 * setItem2() sets this node's item2 to "item". If this node is invalid,
	 * throws an exception.
	 *
	 * Performance: runs in O(1) time.
	 */
	public void setItem2(Object item) throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException();
		}
		this.item2 = item;
	}

}
