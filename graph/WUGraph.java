/* WUGraph.java */

package graph;

import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph. Self-edges are
 * permitted.
 */

public class WUGraph {
	private DDList vertices; // vertices are objects of any kind
	private HashTableChained edgeHash;

	/**
	 * key is the vertex name, value is the DListNode of 'vertices'
	 */
	private HashTableChained vertexHash;

	/**
	 * WUGraph() constructs a graph having no vertices or edges.
	 *
	 * Running time: O(1).
	 */
	public WUGraph() {
		vertices = new DDList();
		edgeHash = new HashTableChained();
		vertexHash = new HashTableChained();

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
		int index = 0;
		try {
			DDListNode vertex = (DDListNode) vertices.front();
			while (vertex.isValidNode()) {
				allVertices[index] = vertex.item();
				vertex = (DDListNode) vertex.next();
				index++;
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
			DDList newList = new DDList();
			vertices.insertBack(vertex, newList);
			vertexHash.insert(vertex, vertices.back());
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
				DDListNode node = findVertexNode(vertex);
				if (!((DDList) node.item2()).isEmpty()) {
					DDList list = (DDList) node.item2();
					DDListNode lNode = (DDListNode) list.front();

					// remove partner references on all nodes
					// in the adjacency list
					while (lNode.isValidNode()) {
						Object vertex2 = ((Object[]) lNode.item())[1];
						if(vertex2 != null) {
							lNode = (DDListNode) lNode.next();
							removeEdge(vertex, vertex2);
						} else {
							lNode = (DDListNode) lNode.next();
						}
					}
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
			return false;
		} catch (InvalidNodeException e) {
			return false;
		}
	}

	/**
	 * degree() returns the degree of a vertex. Self-edges add only one to the
	 * degree of a vertex. If the parameter "vertex" doesn't represent a vertex
	 * of the graph, zero is returned.
	 *
	 * Running time: O(1).
	 */
	public int degree(Object vertex) {
		try {
			// find the adjacency list associated with this vertex.
			DDList list = (DDList) findVertexNode(vertex).item2();

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
	private DDListNode findVertexNode(Object vertex)
			throws InvalidKeyException, InvalidNodeException {
		return (DDListNode) vertexHash.find(vertex).value();
	}

	/**
	 * findEdgeNode() finds the edge associated with the vertices indicated, if
	 * it exists. Checks the edgeHash for the edge; will throw exception if it
	 * is not found.
	 *
	 * @param u
	 *            first vertex
	 * @param v
	 *            second vertex
	 * @return
	 * @throws InvalidNodeException
	 * @throws InvalidKeyException
	 */
	private DDListNode findEdgeNode(Object u, Object v)
			throws InvalidKeyException, InvalidNodeException {
		VertexPair vp = new VertexPair(u, v);
		return (DDListNode) edgeHash.find(vp).value();
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
		try {
			if (degree(vertex) == 0 || !isVertex(vertex)) {
				return null;
			} else {
				DDList list = (DDList) findVertexNode(vertex).item2();
				DDListNode node = (DDListNode) list.front();
				n.neighborList = new Object[list.length()];
				n.weightList = new int[list.length()];
				int index = 0;
				while (node.isValidNode()) {
					n.neighborList[index] = ((Object[]) node.item())[1];
					n.weightList[index] = (Integer) node.item2();
					index++;
					node = (DDListNode) node.next();
				}
			}
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
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
		if (isVertex(u) && isVertex(v)) {
			try {
				// check if the edge exists
				if (isEdge(u, v)) {
					DDListNode updated = findEdgeNode(u, v);
					if (u != v) {
						((DDListNode) ((Object[]) updated.item())[0])
								.setItem2(weight);
					}
					updated.setItem2(weight);

				} else {
					VertexPair newEdge = new VertexPair(u, v);
					DDList vertex = (DDList) findVertexNode(u).item2();

					// if the vertices are referencing the same thing,
					// assign partner reference as itself.
					if (u == v) {

						// the first parameter is partner reference
						vertex.insertBack(null, weight);

						Object[] partner = new Object[2];
						partner[0] = vertex.back();
						partner[1] = v;

						vertex.back().setItem(partner);

					} else { // insert the edge in the other vertex.
						DDList vertex2 = (DDList) findVertexNode(v).item2();

						vertex.insertBack(null, weight);
						vertex2.insertBack(null, weight);

						Object[] partner = new Object[2];
						partner[0] = vertex.back();
						partner[1] = u;

						Object[] partner2 = new Object[2];
						partner[0] = vertex2.back();
						partner[1] = v;

						vertex.back().setItem(partner);
						vertex2.back().setItem(partner2);
					}

					edgeHash.insert(newEdge, vertex.back());
				}
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidNodeException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * removeEdge() removes an edge (u, v) from the graph. If either of the
	 * parameters u and v does not represent a vertex of the graph, the graph is
	 * unchanged. If (u, v) is not an edge of the graph, the graph is unchanged.
	 *
	 * Running time: O(1).
	 */
	public void removeEdge(Object u, Object v) {
		try {
			if (isVertex(u) && isVertex(v) && isEdge(u, v)) {
				DDListNode node = findEdgeNode(u, v);
				DDListNode partner = ((DDListNode) ((Object[]) node.item())[0]);
				partner.remove();
				
				if (node.isValidNode()) {
					node.remove();
				}

				VertexPair vp = new VertexPair(u, v);
				edgeHash.remove(vp);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		}

	}

	/**
	 * isEdge() returns true if (u, v) is an edge of the graph. Returns false if
	 * (u, v) is not an edge (including the case where either of the parameters
	 * u and v does not represent a vertex of the graph).
	 *
	 * Running time: O(1).
	 */
	public boolean isEdge(Object u, Object v) {

		try {
			findEdgeNode(u, v);
			return true;
		} catch (InvalidKeyException e) {
			return false;
		} catch (InvalidNodeException e) {
			return false;
		}
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
		if (isEdge(u, v)) {
			try {
				DDListNode edge = findEdgeNode(u, v);
				return (Integer) edge.item2();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidNodeException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}