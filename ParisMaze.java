import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ParisMaze {

	public static DiGraph<Integer, Integer> readDiGraph(InputStream is) throws IOException {
		DiGraph<Integer, Integer> ParisMaze = new AdjacencyDiGraph<Integer, Integer>();
		//TODO
		return ParisMaze;
	}
	
	public static WeightedDiGraph<Integer, Integer> readWeightedDiGraph(InputStream is) throws IOException {
		WeightedDiGraph<Integer, Integer> ParisMaze = new AdjacencyWeightedDiGraph<Integer, Integer>();
		//TODO
		return ParisMaze;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Hello Paris");
		
		String fileName = "input_file.txt";
		try(InputStream is = new FileInputStream(fileName)){
			DiGraph<Integer, Integer> parismaze = readDiGraph(is);
			System.out.println(parismaze.diameterToString());
		}
		
		try(InputStream is = new FileInputStream(fileName)){
			WeightedDiGraph<Integer, Integer> parismaze = readWeightedDiGraph(is);
			System.out.println(parismaze.diameterToString());
			parismaze.Graph_Clustering(5);
		}
	}

}
