/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g){
    WUGraph t = new WUGraph();
    Object[] vertices = g.getVertices();
    int size = vertices.length;

    for(int i = 0; i < size; i++){   
      t.addVertex(vertices[i]);
    }
    LinkedQueue list = new LinkedQueue(); // don't know size needed
    for(int i = 0; i < size; i++){
      Neighbors vert = getNeighbors(vertices[i]);
      for(int k = 0; k < vert.length; k++){
        KruskalHelper temp = new KruskalHelper(vertices[i], vert.neighborList[k], vert.weightList[k]);
        list.enqueue(temp);
      }
    }
    
    quickSort(list);

    HashTableChained encodedVertices = new HashTableChained(13);
    for(int i = 0; i < size; i++){
      encodedVertices.insert(vertices[i],i);
    }

    DisjointSets cycleCheck = new DisjointSets(size);
    try{
      while(!list.isEmpty()){
        KruskalHelper temp = (KruskalHelper) list.dequeue();
        int vertex1 = (int) encodedVertices.find(temp.getVertex1()).getWeight();
        int vertex2 = (int) encodedVertices.find(temp.getVertex2()).getWeight();
        int vertex1Root = cycleCheck.find(vertex1);
        int vertex2Root = cycleCheck.find(vertex2);
        if(vertex1Root != vertex2Root){
          cycleCheck.union(vertex1, vertex2);
          t.addedge(temp.getVertex1(), temp.getVertex2(), temp.getWeight());
        } 
      }
    }catch(QueEmptyException e){
      System.out.println("Wrong queue. minSpanTree");
    }
    return t;
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  private static void quickSort(LinkedQueue q) {
    // Your solution here.
    if(q.size() == 0 || q.size() == 1){
      return;
    }
    
    int choose = (int) (Math.random() * q.size);
    Comparable item = (Comparable) q.nth(choose + 1);
    LinkedQueue qLarge = new LinkedQueue();
    LinkedQueue qEquals = new LinkedQueue();
    LinkedQueue qSmall = new LinkedQueue();
    partition(q, item, qSmall, qEquals, qLarge);
    quickSort(qSmall);
    quickSort(qLarge);
    q.append(qSmall);
    q.append(qEquals);
    q.append(qLarge);
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    try{
      while(!qIn.isEmpty()){
        Comparable nextQue = (Comparable) qIn.dequeue();
        int comparison = nextQue.compareTo(pivot);
        if(comparison>0){
          qLarge.enqueue(nextQue);
        }else if(comparison < 0){
          qSmall.enqueue(nextQue);
        }else{
          qEquals.enqueue(nextQue);
        }
      }
    }catch(QueEmptyException e){
      System.out.println("Wrong queue. partition");
    }
  }
  

//disjoint sets - use hash table to map (new code for hashing)
}
