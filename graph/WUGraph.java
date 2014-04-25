/* WUGraph.java */

package graph;

import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph. Self-edges are
 * permitted.
 */

public class WUGraph {
	private DList vertices;
	private HashTableChained edgeHash;
	private HashTableChained vertexHash;

	/**
	 * WUGraph() constructs a graph having no vertices or edges.
	 *
	 * Running time: O(1).
	 */
	public WUGraph() {
		vertices = new DList();
	}

	/**
	 * vertexCount() returns the number of vertices in the graph.
	 *
	 * Running time: O(1).
	 */
	public int vertexCount() {
		return vertices.length();
	}

	/**
	 * edgeCount() returns the total number of edges in the graph.
	 *
	 * Running time: O(1).
	 */
	public int edgeCount() {
		return edgeHash.size();
	}

	/**
	 * getVertices() returns an array containing all the objects that serve as
	 * vertices of the graph. The array's length is exactly equal to the number
	 * of vertices. If the graph has no vertices, the array has length zero.
	 *
	 * (NOTE: Do not return any internal data structure you use to represent
	 * vertices! Return only the same objects that were provided by the calling
	 * application in calls to addVertex().)
	 *
	 * Running time: O(|V|).
	 */
	public Object[] getVertices() {
		Object[] allVertices = new Object[vertexCount()];
		DListNode vertex = (DListNode) vertices.front();
		try {
			for (int index = 0; index < allVertices.length; index++) {
				allVertices[index] = vertex.item();
				vertex = (DListNode) vertex.next();
			}
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		}
		return allVertices;
	}

	/**
	 * addVertex() adds a vertex (with no incident edges) to the graph. The
	 * vertex's "name" is the object provided as the parameter "vertex". If this
	 * object is already a vertex of the graph, the graph is unchanged.
	 *
	 * Running time: O(1).
	 */
	public void addVertex(Object vertex) {
		if (!isVertex(vertex)) {
			vertices.insertBack(new DList());
			vertexHash.insert(vertex, vertexCount());
		}
	}

	/**
	 * removeVertex() removes a vertex from the graph. All edges incident on the
	 * deleted vertex are removed as well. If the parameter "vertex" does not
	 * represent a vertex of the graph, the graph is unchanged.
	 *
	 * Running time: O(d), where d is the degree of "vertex".
	 */
	public void removeVertex(Object vertex) {
		if (isVertex(vertex)) {
			try {
				DListNode node = findVertexNode(vertex);
				DList list = (DList) node.item();
				DListNode lNode = (DListNode) list.front();
				
				// remove partner references on all nodes
				// in the adjacency list
				while (lNode.isValidNode()){
					DListNode partner = (DListNode) lNode.item();
					partner.setItem(null);
					lNode = (DListNode) lNode.next();
				}
				
				// remove this vertex
				vertexHash.remove(vertex);
				node.remove();
				
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidNodeException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * isVertex() returns true if the parameter "vertex" represents a vertex of
	 * the graph.
	 *
	 * Running time: O(1).
	 */
	public boolean isVertex(Object vertex) {
		try {
			return vertexHash.find(vertex) != null;
		} catch (InvalidKeyException e) {
			System.out.println("shoot me now.");
		} catch (InvalidNodeException e) {
			System.out.println("bang.");
		}
		return false;
	}

	/**
	 * degree() returns the degree of a vertex. Self-edges add only one to the
	 * degree of a vertex. If the parameter "vertex" doesn't represent a vertex
	 * of the graph, zero is returned.
	 *
	 * Running time: O(1).
	 */
	public int degree(Object vertex) {
		// currently runs in O(n) time

		try {
			// find the adjacency list associated with this vertex.
			DList list = (DList) findVertexNode(vertex).item();

			// returns the length of the DList at the node,
			// which is the degree of the node.
			return list.length();

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * findVertexNode() finds the input vertex's corresponding node in vertices
	 * 
	 * @param vertex
	 * @return the adjacency list associated with this vertex
	 * @throws InvalidKeyException
	 * @throws InvalidNodeException
	 */
	private DListNode findVertexNode(Object vertex) throws InvalidKeyException,
			InvalidNodeException {
		// find the index of the vertex in the DList.
		int index = (Integer) vertexHash.find(vertex).value();

		// find the node that corresponds to the vertex.
		DListNode node = (DListNode) vertices.front();
		while (index > 0) {
			node = (DListNode) node.next();
			index--;
		}

		return node;
	}

	/**
	 * getNeighbors() returns a new Neighbors object referencing two arrays. The
	 * Neighbors.neighborList array contains each object that is connected to
	 * the input object by an edge. The Neighbors.weightList array contains the
	 * weights of the corresponding edges. The length of both arrays is equal to
	 * the number of edges incident on the input vertex. If the vertex has
	 * degree zero, or if the parameter "vertex" does not represent a vertex of
	 * the graph, null is returned (instead of a Neighbors object).
	 *
	 * The returned Neighbors object, and the two arrays, are both newly
	 * created. No previously existing Neighbors object or array is changed.
	 *
	 * (NOTE: In the neighborList array, do not return any internal data
	 * structure you use to represent vertices! Return only the same objects
	 * that were provided by the calling application in calls to addVertex().)
	 *
	 * Running time: O(d), where d is the degree of "vertex".
	 */
	public Neighbors getNeighbors(Object vertex) {
		Neighbors n = new Neighbors();
		// n.neighborList = new Object[];
		return n;
	}

	/**
	 * addEdge() adds an edge (u, v) to the graph. If either of the parameters u
	 * and v does not represent a vertex of the graph, the graph is unchanged.
	 * The edge is assigned a weight of "weight". If the graph already contains
	 * edge (u, v), the weight is updated to reflect the new value. Self-edges
	 * (where u == v) are allowed.
	 *
	 * Running time: O(1).
	 */
	public void addEdge(Object u, Object v, int weight) {
		VertexPair updated = new VertexPair(u, v);
		findVertexNode(u).item.insertBack(updated.hashCode());
		edgeHash.insert(updated.hashCode(), findVertexNode(u).item.back);
		findVertexNode(v).item.insertBack(updated.hashCode());
		findVertexNode(u).item.back.item = findVertexNode(v).item.back;
		findVertexNode(v).item.back.item = findVertexNode(u).item.back;
	}

	/**
	 * removeEdge() removes an edge (u, v) from the graph. If either of the
	 * parameters u and v does not represent a vertex of the graph, the graph is
	 * unchanged. If (u, v) is not an edge of the graph, the graph is unchanged.
	 *
	 * Running time: O(1).
	 */
	public void removeEdge(Object u, Object v) {

	}

	/**
	 * isEdge() returns true if (u, v) is an edge of the graph. Returns false if
	 * (u, v) is not an edge (including the case where either of the parameters
	 * u and v does not represent a vertex of the graph).
	 *
	 * Running time: O(1).
	 */
	public boolean isEdge(Object u, Object v) {
		return false;
	}

	/**
	 * weight() returns the weight of (u, v). Returns zero if (u, v) is not an
	 * edge (including the case where either of the parameters u and v does not
	 * represent a vertex of the graph).
	 *
	 * (NOTE: A well-behaved application should try to avoid calling this method
	 * for an edge that is not in the graph, and should certainly not treat the
	 * result as if it actually represents an edge with weight zero. However,
	 * some sort of default response is necessary for missing edges, so we
	 * return zero. An exception would be more appropriate, but also more
	 * annoying.)
	 *
	 * Running time: O(1).
	 */
	public int weight(Object u, Object v) {
		return 0;
	}

	public static void main(String[] args) {

	}

}
