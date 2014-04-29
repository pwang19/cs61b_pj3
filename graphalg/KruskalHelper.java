/* KruskalHelper.java */

package graphalg;

import graph.*;
import set.*;

public class KruskalHelper extends Comparable{
	private Object vertex1;
	private Object vertex2;
	private int weight;

	public KruskalHelper();

	//same fields as addEdge from WUGraph
	public KruskalHelper(Object u, Object v, int weight){
		vertex1 = u;
		vertex2 = v;
		this.weight = weight;
	}

	public Object getVertex1(){
		return vertex1;
	}

	public Object getVertex2(){
		return vertex2;
	}

	public int getWeight(){
		return weight;
	}

	public int compareTo(Object a){
		return (weight - ((KruskalHelper) a).weight);
	}
}