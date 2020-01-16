import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.reflect.TypeToken;


public class ReadFile {
    
	public static HashMap<Long, ArrayList<String>> nodesImport() throws IOException{

		JsonReader nodeReader;
		nodeReader = new JsonReader(new FileReader("nodes.json"));
		nodeReader.beginArray();

		HashMap<Long, ArrayList<String>> nodes = new HashMap<Long, ArrayList<String>>();

		while(nodeReader.hasNext()){
			nodeReader.beginObject();
			//JsonToken nextToken = nodeReader.peek();

			nodeReader.nextName();
			long id = nodeReader.nextLong();

			nodeReader.nextName();
			String name = nodeReader.nextString();

			nodeReader.nextName();
			nodeReader.nextDouble();

			nodeReader.nextName();
			nodeReader.nextDouble();

			nodeReader.nextName();
			double longitude = nodeReader.nextDouble();

			nodeReader.nextName();
			double latitude = nodeReader.nextDouble();

			nodeReader.nextName();
			nodeReader.nextBoolean();
			
			ArrayList<String> details = new ArrayList<String>();
			details.add(name);
			details.add(Double.toString(longitude));
			details.add(Double.toString(latitude));

			nodes.put(id, details);

			nodeReader.endObject();
		}
		nodeReader.endArray();
		nodeReader.close();
		
		return nodes;	
	}
	
	public static HashMap<ArrayList<Long>, Double> edgesImport(Map<Long, ArrayList<String>> nodes) throws IOException{

		JsonReader edgeReader = new JsonReader(new FileReader("edges.json"));
		edgeReader.beginArray();
		Gson gson = new Gson();

		HashMap<ArrayList<Long>, Double> edges = new HashMap<ArrayList<Long>, Double>();

		while(edgeReader.hasNext()){
			edgeReader.beginObject();
			//JsonToken nextToken = edgeReader.peek();

			edgeReader.nextName();
			long source = edgeReader.nextLong();

			edgeReader.nextName();
			long target = edgeReader.nextLong();

			edgeReader.nextName();
			edgeReader.nextString();

			edgeReader.nextName();
			edgeReader.nextString();

			edgeReader.nextName();
			edgeReader.nextString();

			edgeReader.nextName();
			edgeReader.nextString();

			double lon1 = Double.valueOf(nodes.get(source).get(1));
			double lat1 = Double.valueOf(nodes.get(source).get(2));
			
			double lon2 = Double.valueOf(nodes.get(target).get(1));
			double lat2 = Double.valueOf(nodes.get(target).get(2));
			
			double weight = weightCalc(lon1, lat1, lon2, lat2);
			
			ArrayList<Long> src_tgt = new ArrayList<Long>();
			src_tgt.add(source);
			src_tgt.add(target);
			
			ArrayList<Long> tgt_src = new ArrayList<Long>();
			tgt_src.add(target);
			tgt_src.add(source);
			
			edges.put(src_tgt, weight);
			edges.put(tgt_src, weight);

			edgeReader.endObject();
			}
			edgeReader.endArray();
			edgeReader.close();
			
			return edges;
	}
	
	public static double weightCalc(double lon1, double lat1, double lon2, double lat2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1609.344; //convert to m
        return (dist);
      }

      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts decimal degrees to radians             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }

      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts radians to decimal degrees             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }
	
	public static void main(String[] args) throws IOException {
		HashMap<Long, ArrayList<String>> nodes = nodesImport();
		System.out.println(nodes);
		long val = 4083286998L;
		System.out.println("The Value is: " + nodes.get(val));
		
		HashMap<ArrayList<Long>, Double> edges = edgesImport(nodes);
		System.out.println(edges);
		ArrayList<Long> valList = new ArrayList<Long>();
		long l1 = 4058783101L;
		long l2 = 260765390L;
		valList.add(l1);
		valList.add(l2);
		System.out.println("The Value is: " + edges.get(valList));

				
	}
}
