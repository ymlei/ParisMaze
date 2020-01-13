import java.util.Set;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class AdjacencyWeightedDiGraph<Vertex, Edge, WeightType> 
	implements WeightedDiGraph<Vertex, Edge, WeightType> {
	
	protected Set<Vertex> vertices= new HashSet<Vertex>();
	protected Set<Edge> edges= new HashSet<Edge>();
	protected Map<Edge, WeightType> edgeToWeight = new HashMap<Edge, WeightType>();
	protected Map<Vertex, List<Edge> > vertexToEdges= new HashMap<Vertex, List<Edge> >();
	private Map<Edge, Vertex> edgeToSrc= new HashMap<Edge, Vertex>();
	private Map<Edge, Vertex> edgeToDest= new HashMap<Edge, Vertex>();
//	private Map<String, Vertex> nameToVertex= new HashMap<String, Vertex>();
	private Map<Vertex, String> vertexToName= new HashMap<Vertex, String>();
	
	
	private class Node implements Comparable<Node>{
		public final Vertex v;
		public Vertex pathFrom;
		public Edge e;
		public WeightType dis;
		
		public Node(Vertex vertex, Vertex from, Edge edge, WeightType distance) {
			v = vertex;
			e = edge;
			pathFrom = from;
			dis = distance;
		}
		
		public int compareTo(Node other) {
			if((float)this.dis < (float)other.dis) return 1;
			return -1;
		}
		
	}
	
	public AdjacencyWeightedDiGraph(){
	}
	
	public void addVertex(Vertex v){
		if(!vertices.contains(v)){
			vertices.add(v);
			vertexToEdges.put(v, new ArrayList<Edge>());
		}
	}
	
	public List<Vertex> getVertices(){
		return new ArrayList<Vertex>(vertices);
	}
	
	public void addEdge(Edge e, WeightType weight, Vertex src, Vertex dest){
		//System.out.println("Add edge " + e + "\n");
		addVertex(src);
		addVertex(dest);
		edges.add(e);
		edgeToWeight.put(e, weight);
		edgeToSrc.put(e, src);
		edgeToDest.put(e, dest);
		vertexToEdges.get(src).add(e);
	}
	
	public List<Edge> getEdges(){
		return new ArrayList<Edge>(edges);
	}
	
	public List<Vertex> getAdjacentVertices(Vertex src){
		List<Vertex> res= new ArrayList<Vertex>();
		for(Edge e : vertexToEdges.get(src)){
			res.add(edgeToDest.get(e));
		}
		return res;
	}
	
	public void nameVertex(String name, Vertex v) {
		vertexToName.put(v, name);
	}
	
	public String getNameByVertex(Vertex v){
		return vertexToName.get(v);
	}
	
	// should use java.util.Optional
	public Vertex getVertexByName(String name) {
		for(Map.Entry<Vertex, String> e: vertexToName.entrySet()){
			if(e.getValue().equals(name)){
				return e.getKey();
			}
		}
		return null;
	}
	
	public List<String> getNames(){
		return new ArrayList<String>(vertexToName.values());
	}
	
	public boolean areConnected(Vertex src, Vertex dest){
		// TODO !
		Map<Vertex, Boolean> visit = new HashMap<Vertex, Boolean>();
		List<Vertex> temp = getVertices();
		for(Vertex v : temp){
			visit.put(v, null);
		}
		
		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(src);
		visit.put(src, true);
		
		Vertex cur_vertex = src;
		while(!queue.isEmpty()) {
			cur_vertex = queue.poll();
			//System.out.println("poll " + cur_vertex + "\n");
			if(cur_vertex.equals(dest)) return true;
			for(Vertex v : getAdjacentVertices(cur_vertex)) {
				if(visit.get(v) == null) {
					//System.out.println("visit "+ v +"\n");
					visit.put(v, true);
					queue.add(v);
				}
			}
		}
		return false;
	}
	
	public boolean areConnected(String src, String dest){
		return areConnected(getVertexByName(src), getVertexByName(dest));
	}
/*	
	public List<Vertex> shortestPath(Vertex src, Vertex dest){
		// Dijkstra with priority queue
		Map<Vertex, Edge> visit = new HashMap<Vertex, Edge>();
		
		Queue<Node> pq = new PriorityQueue<Node>();
		Node cur = new Node(src, src, null, 0);
		
		pq.add(cur);
		
		while(!pq.isEmpty()) {
			cur = pq.poll();
			//System.out.println("poll " + cur_vertex + "\n");
			
			if(visit.get(cur.v) == null) {
				visit.put(cur.v, cur.e);
				if(cur.v.equals(dest)) break;
				for(Edge e : vertexToEdges.get(cur.v)) { //update distance
					pq.add(new Node(edgeToDest.get(e), cur.v, cur.dis+edgeToWeight.get(e)));
				}
			}
			
		}
		
		if(!cur.v.equals(dest)) return null;
		
		List<Vertex> res = new ArrayList<Vertex>();
		Vertex v = cur.v;
		while(!v.equals(src)) {
			//System.out.println("track "+ cur_vertex +"\n");
			res.add(0, v);
			v = visit.get(v);
		}
		res.add(0, v);
		return res;
	}
	*/
	public List<List<Vertex>> shortestPathFrom(Vertex src){
		// Dijkstra with priority queue
		Map<Vertex, WeightType> distance = new HashMap<Vertex, WeightType>();
		Map<Vertex, Edge> adjacent = new HashMap<Vertex, Edge>();
		Map<Vertex, Boolean> visit = new HashMap<Vertex, Boolean>();
		
		Queue<Node> pq = new PriorityQueue<Node>();
		Node cur = new Node(src, src, null, edgeToWeight.get(0));
		//the Last para should be 0. But the WeightType doesn't allow 0;
		pq.add(cur);
		
		while(!pq.isEmpty()) {

			cur = pq.poll();
			
			if(visit.get(cur.v) == true) continue;
			
			visit.put(cur.v, true);
			adjacent.put(cur.v, cur.e);
			distance.put(cur.v, cur.dis);
			//System.out.println("poll " + cur_vertex + "\n");
			
			for(Edge e : vertexToEdges.get(cur.v)) { //update distance
				
				//Has been visited, next edge
				if(visit.get(edgeToDest.get(e)) == true) continue;
				
				pq.add(new Node(edgeToDest.get(e), cur.v, e, cur.dis+edgeToWeight.get(e)));
			}
		}
			
		
		List<Vertex> res = new ArrayList<Vertex>();
		Vertex v = cur.v;
		while(!v.equals(src)) {
			//System.out.println("track "+ cur_vertex +"\n");
			res.add(0, v);
			v = visit.get(v);
		}
		res.add(0, v);
		return res;
	}
	
}
