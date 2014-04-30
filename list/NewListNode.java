/* NewListNode */

package list;

/*
* This class was made to fix the collision in front() method. The return values for the method in LinkedQueue and SListNode was different.
* So created a new node class to get rid of collision.
*/


class NewListNode extends ListNode{
	protected Object o;
	protected NewListNode next;


	NewListNode(Object o){
		this.o = o;
		next = null;
	}

	NewListNode(Object i, NewListNode next){
		this.o = obj;
		this.next = next;
	}

	NewListNode(Object i, SList l, NewListNode n) {
		item = i;
		myList = l;
		next = n;
	}
}