import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


/*
 * This Class is used to making text file(train.txt, test_feature.txt)  
 */
public class CaseProcessing implements MyKeyword {
	private List<ChiSquare> chiSquares;
	private HashMap<String, Integer> searchKeys;
	private Feature[] features;
	
	
	private HashSet<String> wordsOfDocument = new HashSet<String>();
	private int categoryNumber = 0;
	private int sequence[];
	private BufferedReader files;
	private FileWriter fw;
	
	public CaseProcessing(List<ChiSquare> chiSquares, HashMap<String, Integer> searchKeys, Feature[] features) {
		this.chiSquares =chiSquares;
		this.searchKeys =searchKeys;
		this.features = features;
	}

	public void Porcessing(String readFileName, String writeFileName) throws IOException {
		files = new BufferedReader(new FileReader(readFileName));
		fw= new FileWriter(writeFileName);		 
		
		boolean isRead = false;
		boolean endRead = false;

		while(true) 
		{
			String line = files.readLine();
			if(line == null) break;
			
			if(line.length() == 0)
				continue;
				
				if(line.contains(DOCUMENT)) {
					if(isRead) {
						isRead = false;
						endRead = true;
					}
				}
			
				else if((!isRead) && endRead) {
					writeResult();
					endRead = false;
				}
				else if(isRead) {
					if(line.length() < 1)
						return;
					
					for(int i = 0; i < replaceWords.length; i++)
						line = line.replace(replaceWords[i], ' ');
					String[] words = line.split(" ");
					
					for(String word : words) {
						if(word.length() < 1)
							continue;
						if(word.length() == 1) {
							if(alphabets.contains(word.toLowerCase()))
								continue;
						}
						boolean isNumber = true;
						for(int i= 0; i< word.length(); i++)
						{
							
							if(!numbers.contains(word.charAt(i) +"")) {
								isNumber = false;
							}
							break;
						}
						if(isNumber)
							continue;
						
						wordsOfDocument.add(word);
					}
				}
				else if(line.contains(CATEGORY)) {
					for(int i = 0; i < 8; i++) {
						if(line.contains(categoryNames[i])) {
							categoryNumber = i;
							features[i].number++;
							continue;
						}	
					}
				}
				else if(line.contains(TEXT)){
					isRead = true;
				}
				else if(line.contains(KEYWORD)){
					isRead = false;
					endRead= true;
				}		
			
		}
		writeResult();
		endRead = false;
		fw.close();
	}
	
	
	void writeResult() throws IOException {
		int n = 0;
		for(Iterator<String> itr = wordsOfDocument.iterator();  itr.hasNext();)
		{
			String s = itr.next();
			
			//seachKeys
			if(searchKeys.containsKey(s))
				n++;
		}
		sequence = new int [n];
		
		n = 0;
		
		for(Iterator<String> itr = wordsOfDocument.iterator();  itr.hasNext();)
		{
			String s = itr.next();
			if(searchKeys.containsKey(s))
				sequence[n++] = searchKeys.get(s); 
		}
		Arrays.sort(sequence);
		fw.write(categoryNumber + " ");
		for(int i = 0; i < n; i++)
		{
		   	fw.write(sequence[i] + ":"  + chiSquares.get(sequence[i]).chiSquare + " ");
		}
		fw.write("\n");
		wordsOfDocument.clear();
		
	}
}
