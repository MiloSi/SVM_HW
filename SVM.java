import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;


/*
 * This is Main class.
 * To get train case and test case.
 */

public class SVM implements MyKeyword {
	public static void main (String[] args) throws IOException{	
		//CLASS 
		Processor p = new Processor();
		ChiSquareProcessing chiController = new ChiSquareProcessing();
		CaseProcessing caseProcessing;
		
		
		Feature[] features;
		HashSet<String> allFeatures = new HashSet<>();
		List<ChiSquare> chiSquares = new ArrayList();
		HashMap<String, Integer> searchKeys = new HashMap<>();
		
		long start;
		long end;
		long totalTime;
		
		start =System.currentTimeMillis();
		totalTime = System.currentTimeMillis();
	
		BufferedReader files = new BufferedReader(new FileReader("document.txt"));
	
	
		while(true)
		{
			String line = files.readLine();
			if(line == null) break;
			p.textProcessing(line);
		}
		p.doLast();
		
		end = System.currentTimeMillis();
		
		System.out.println("File Featuring Time : " +(end - start)/1000.0);
		start = System.currentTimeMillis();
	
		features = p.features;
		Iterator<String> iter[] = new Iterator[8];

		for(int i = 0; i < 8; i++)
		{
			iter[i] = features[i].wordsOfDocuments.keySet().iterator();
			while(iter[i].hasNext())
			{
				allFeatures.add(iter[i].next());
			}
		}

		
		//Check What Features are made.
		PrintWriter printw = new PrintWriter("hello.txt");
		//All Features
		Iterator<String> itsa = allFeatures.iterator();
		while(itsa.hasNext())
		{
			printw.write(itsa.next() + "\n");
		}
		printw.close();
		files.close();
		
		chiController.Processing(features, allFeatures);
		end = System.currentTimeMillis();
		System.out.println("Chi-Square Time : " +(end - start)/1000.0);
		start = System.currentTimeMillis();
		
		
		chiSquares = chiController.getChiSquares();
		searchKeys = chiController.getSearchKeys();

		
		caseProcessing = new CaseProcessing(chiSquares, searchKeys, features);
		
		caseProcessing.Porcessing("document.txt", "train.txt");
		end = System.currentTimeMillis();
		System.out.println("Training File Processing: " +(end - start)/1000.0);
		
		
		start = System.currentTimeMillis();
		
		caseProcessing.Porcessing("test.txt", "test_feature.txt");
		end = System.currentTimeMillis();
		System.out.println("Predict File Processing : " +(end - start)/1000.0);
		System.out.println("Total Time : " +(end - totalTime)/1000.0);
		System.out.println("Feature Number :: " + allFeatures.size());
	}

}
