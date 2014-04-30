/* NewListNode */

package list;

/*
* This class was made to fix the collision in front() method. The return values for the method in LinkedQueue and SListNode was different.
* So created a new node class to get rid of collision.
*/


class NewListNode {
	protected Object o;
	protected NewListNode next;


	NewListNode(Object o){
		this.o = o;
		next = null;
	}

	NewListNode(Object o, NewListNode next){
		this.o = o;
		this.next = next;
	}
}