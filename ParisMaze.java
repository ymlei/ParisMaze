import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SquareMazePackage.Cell;
import SquareMazePackage.DiGraph;

public class ParisMaze {

	private static Long connectCellsBothWays(Long v1, Long v2, Long currentEdgeId, DiGraph<Long, Long> parisMaze) {
		parisMaze.addEdge(currentEdgeId++, v1, v2);
		parisMaze.addEdge(currentEdgeId++, v1, v2);
		return currentEdgeId;
	}
	
	private static Long connectCellsBothWays(Long v1, Long v2, Long currentEdgeId, float weight, WeightedDiGraph<Long, Long> parisMaze) {
		parisMaze.addEdge(currentEdgeId++, weight, v1, v2);
		parisMaze.addEdge(currentEdgeId++, weight, v1, v2);
		return currentEdgeId;
	}
	
	public static DiGraph<Long, Long> readDiGraph() throws IOException {
		DiGraph<Long, Long> parisMaze = new AdjacencyDiGraph<Long, Long>();
		//TODO
		Map <Long, ArrayList<String>> nodes = ReadFile.nodesImport();
		for(Long v : nodes.keySet()) {
			parisMaze.addVertex(v);
			parisMaze.nameVertex(nodes.get(v).get(0), v);
			//System.out.println("name"+v+nodes.get(v).get(0));
		}
		
		Map<ArrayList<Long>, Double> edges = ReadFile.edgesImport(nodes);
		Long currentId = 0L;
		for(ArrayList<Long> valList : edges.keySet()) {
			currentId = connectCellsBothWays(valList.get(0), valList.get(1), currentId, parisMaze);
		}
		return parisMaze;
	}
	

	public static WeightedDiGraph<Long, Long> readWeightedDiGraph() throws IOException {
		WeightedDiGraph<Long, Long> parisMaze = new AdjacencyWeightedDiGraph<Long, Long>();
		//TODO
		Map <Long, ArrayList<String>> nodes = ReadFile.nodesImport();
		for(Long v : nodes.keySet()) {
			parisMaze.addVertex(v);
			parisMaze.nameVertex(nodes.get(v).get(0), v);
			//System.out.println("name"+v+nodes.get(v).get(0));
		}
		
		Map<ArrayList<Long>, Double> edges = ReadFile.edgesImport(nodes);
		Long currentId = 0L;
		for(ArrayList<Long> valList : edges.keySet()) {
			currentId = connectCellsBothWays(valList.get(0), valList.get(1), currentId, edges.get(valList).floatValue(), parisMaze);
		}
		return parisMaze;
	}
	public static WeightedDiGraph<Integer, Integer> readWeightedDiGraph(InputStream is) throws IOException {
		WeightedDiGraph<Integer, Integer> ParisMaze = new AdjacencyWeightedDiGraph<Integer, Integer>();
		//TODO
		return ParisMaze;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Hello Paris");
		
		System.out.println("Unweighted Graph:");
		DiGraph<Long, Long> parismaze = readDiGraph();
		System.out.println(parismaze.diameterToString());

		System.out.println("Weighted Graph:");
		WeightedDiGraph<Long, Long> parismaze1 = readWeightedDiGraph();
		System.out.println(parismaze1.diameterToString());
		System.out.println(parismaze1.clustersToString(parismaze1.Graph_Clustering(5)));
		
	}

}
