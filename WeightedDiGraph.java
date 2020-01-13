import java.util.List;

public interface WeightedDiGraph<Vertex, Edge, WeightType> {
	//use addVertex();
	public void addVertex(Vertex v);
	public List<Vertex> getVertices();
	//use addEdge()
	public void addEdge(Edge e, WeightType weight, Vertex src, Vertex dest);
	public List<Edge> getEdges();
	public List<Vertex> getAdjacentVertices(Vertex v);
	
	//use nameVertex()
	public void nameVertex(String name, Vertex v);
	public Vertex getVertexByName(String name);
	public List<String> getNames();
	public List<String> VerticesToStrings(List<Vertex> path);
	public boolean areConnected(Vertex src, Vertex dest);
	public boolean areConnected(String src, String dest);
	public List<Vertex> shortestPath(Vertex src, Vertex dest);
	
	public List<List<Vertex>> shortestPathFrom(Vertex src);
	public List<List<Vertex>> collectAllSP();
	public List<Vertex> diameterUnweighted();

//	public List<List<Vertex> > Graph_Clustering(int cluster_num);
}