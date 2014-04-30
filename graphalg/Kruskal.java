/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import dict.*;
import list.*;


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
    // new graph to store minSpanTree
  	WUGraph t = new WUGraph();
  	Object[] vertices = g.getVertices();
  	int size = vertices.length;

    // store vertexes from g to t
  	for(int i = 0; i < size; i++){   
  		t.addVertex(vertices[i]);
  	}

    // don't know size needed so use LinkedQueue
  	LinkedQueue list = new LinkedQueue();

    // Store all edges from g into a LinkedQueue using getNeighbors()
  	for(int i = 0; i < size; i++){
  		Neighbors vert = getNeighbors(vertices[i]);
  		for(int k = 0; k < vert.length; k++){
  			KruskalHelper temp = new KruskalHelper(vertices[i], vert.neighborList[k], vert.weightList[k]);
  			list.enqueue(temp);
  		}
  	}
  	
    // Sort the list using mergesort to achieve  O(|E|log(|E|)) 
  	mergeSort(list);

    // Use HashTableChained to map objects that serve as Vertices to unique integers
    // 
  	HashTableChained encodedVertices = new HashTableChained();
  	for(int i = 0; i < size; i++){
  		encodedVertices.insert(vertices[i],i);
  	}

    // Use DisjointSets to make sure no cycle 
  	DisjointSets cycleCheck = new DisjointSets(size);

    // get vertex values in hash and find its root in disjointset to check if two vertices have same root. If not the same root, add edge and union the two vertices.
    try{
    	while(!list.isEmpty()){
    		KruskalHelper temp = (KruskalHelper) list.dequeue();
    		int vertex1 = (int) encodedVertices.find(temp.getVertex1()).value();
    		int vertex2 = (int) encodedVertices.find(temp.getVertex2()).value();

        int vertex1Root = (int) cycleCheck.find(vertex1);
        int vertex2Root = (int) cycleCheck.find(vertex2);
    		if(vertex1Root != vertex2Root){
          cycleCheck.union(vertex1, vertex2);
          t.addEdge(vertex1, vertex2, temp.getWeight());
        } 
    	}
    }catch(QueEmptyException e){
      System.out.println("Wrong queue. minSpanTree");
    }
    return t;
  }


}
