/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    LinkedQueue queofque = new LinkedQueue();
    try{
      while(!q.isEmpty()){
        KruskalHelper item = (KruskalHelper) q.dequeue();
        LinkedQueue que = new LinkedQueue();
        que.enqueue(item);
        queofque.enqueue(que);
      }
    }catch(QueEmptyException e){
      System.out.println("Wrong queue. makeQueueOfQueues");
    }
    return queofque;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    LinkedQueue q3 = new LinkedQueue();
    try{
      while(!q1.isEmpty() || !q2.isEmpty()){
        int comparison = ((KruskalHelper)q1.front()).getWeight() - ((KruskalHelper)q2.front()).getWeight()
        if(comparison>0){
          q3.enqueue(itemq2);
          q2.dequeue();
        }else{
          q3.enqueue(itemq1);
          q1.dequeue();
        }
      }
      if(q1.isEmpty()){
        q3.append(q2);
      }else{
        q3.append(q1);
      }
    }catch(QueEmptyException e){
      System.out.println("Wrong queue. mergeSortedQueues");
    }
    return q3;
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
    LinkedQueue mergeQueofQue = q.makeQueueOfQueues();
    try{
      while(mergeQueofQue.size() > 1){
        LinkedQueue q1 = (LinkedQueue) mergeQueofQue.dequeue();
        LinkedQueue q2 = (LinkedQueue) mergeQueofQue.dequeue();x
        LinkedQueue q3 = mergeSortedQueues(q1, q2);
        mergeQueofQue.enqueue(q3);
      }
      q.append((LinkedQueue) mergeQueofQue.dequeue())
    }catch(QueEmptyException e){
      System.out.println("Wrong queue. merge");
    }
  }


  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

  }

}