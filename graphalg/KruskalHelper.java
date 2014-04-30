/* KruskalHelper.java */

package graphalg;

import graph.*;
import set.*;

public class KruskalHelper{
	private Object vertex1;
	private Object vertex2;
	private int weight;

	/**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
	public KruskalHelper() {
	}

	/**
	* kruskalHelper() constructs a KruskalHelper that contains two vertices and its connecting edges.
	*
	* @param u , first vertex 
	* @param v , second vertex
	* @param weight, the weight of the edge connecting u and v
	*/
	public KruskalHelper(Object u, Object v, int weight){
		vertex1 = u;
		vertex2 = v;
		this.weight = weight;
	}

	/**
	*getVertex1() returns the first vertex
	*/
	public Object getVertex1(){
		return vertex1;
	}

	/**
	* getVertex2() returns the second vertex
	*/
	public Object getVertex2(){
		return vertex2;
	}

	/**
	* getWeight() returns the weight of the connecting edge
	*/
	public int getWeight(){
		return weight;
	}

}