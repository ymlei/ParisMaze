import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ParisMaze {

	public static DiGraph<Integer, Integer> read(InputStream is) throws IOException {
		DiGraph<Integer, Integer> ParisMaze = new AdjacencyDiGraph<Integer, Integer>();
		return ParisMaze;
	}
	
	public static String diameterToString (DiGraph<Integer, Integer> parisMaze) {
		StringBuilder longestPathStr = new StringBuilder();
		StringBuilder lengthSubPath = new StringBuilder();
		longestPathStr.append("Longest path: ");
		lengthSubPath.append("Lengths of sub-paths: ");

		int length = 0;
		for(String v : parisMaze.VerticesToStrings(parisMaze.diameterUnweighted())) {
			longestPathStr.append(v+"=>");
			lengthSubPath.append(v+":1 step;"+v+"-");
			length++;
		}
		
		StringBuilder res = new StringBuilder();
		res.append(longestPathStr + "\n");
		res.append(lengthSubPath + "\n");
		res.append("Total lengthof the path: "+length+"m.\n");
		return res.toString();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Hello Paris");
		
		String fileName = "input_file.txt";
		try(InputStream is = new FileInputStream(fileName)){
			DiGraph<Integer, Integer> parismaze = read(is);
			System.out.println(diameterToString(parismaze));
		}
	}

}
