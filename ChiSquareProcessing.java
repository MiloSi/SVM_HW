import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;



/*
 *	This Class is used to getting chi-square.
 */

public class ChiSquareProcessing {

	//private Feature[] features;
	

	List<ChiSquare> chiSquares = new ArrayList();
	HashMap<String, Integer> searchKeys = new HashMap<>();
	
	
	
	public ChiSquareProcessing() {}
	
	public void Processing(Feature[] features, HashSet<String> allFeatures) throws IOException {
		double A, B, C, D;
		
		double chiSquare; 
		double chiSquareMax;
		
		int N = 0;
		for(int i = 0; i< features.length; i++)
			N += features[i].number;
		

		
		Iterator<String> iter[] = new Iterator[8];
		//re init features 
		for(int i = 0; i < 8; i++)
			iter[i] = features[i].wordsOfDocuments.keySet().iterator();
		Iterator<String> setIter = allFeatures.iterator();
		

		while(setIter.hasNext()){
			String key = setIter.next();
			chiSquareMax = 0.0;
			
			for(int i = 0; i < 8; i++) {
				
				A = 0;
				B = 0;
				C = 0;
				D = 0;
				if(features[i].wordsOfDocuments.containsKey(key)) {
					A = features[i].wordsOfDocuments.get(key);
					B = features[i].number - A;
				}
				else {
					A = 0;
					B = features[i].number;
				}
				
				for(int  j = 0; j < 8; j++) {
					if(i == j)
						continue;
				
					if(features[j].wordsOfDocuments.containsKey(key)) {
						C += features[j].wordsOfDocuments.get(key);
					    D += features[j].number - features[j].wordsOfDocuments.get(key);
					}
					else {
						D +=  features[j].number;
					}	
				}
				
				chiSquare = N * (A*D - C*B) * (A*D - C*B) / ( (A+C) * (B+D) * (A+B) * (C+D) );
				if(chiSquareMax < chiSquare)
					chiSquareMax = chiSquare;
			}
			ChiSquare cs = new ChiSquare(key, chiSquareMax);
			chiSquares.add(cs);
		}
		
		Collections.sort(chiSquares, new Comparator<ChiSquare>() {
			
			@Override
			public int compare(ChiSquare s1, ChiSquare s2) {
				if (s1.chiSquare > s2.chiSquare)
					return 1;
				else if(s1.chiSquare < s2.chiSquare)
					return -1;
				else 
					return 0;
			}
		});
		Collections.reverse(chiSquares);	
		
		for(int i = 0; i < chiSquares.size(); i++)
		{
			searchKeys.put(chiSquares.get(i).feature, i);
		}
		
		
		//Find Feature Number and Name, and value.
		
		FileWriter fw= new FileWriter("features.txt");		 
		
		for (int i = 0; i < searchKeys.size(); i++)
		{
			fw.write(i + " " + chiSquares.get(i).feature + " " + chiSquares.get(i).chiSquare + "\n");
		}
		fw.close();
		
		
	}
	public List<ChiSquare> getChiSquares() {
		return chiSquares;
	}
	public HashMap<String, Integer> getSearchKeys() {
		return searchKeys;
	}
}
