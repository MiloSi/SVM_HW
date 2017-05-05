import java.util.HashSet;
import java.util.Iterator;


/*
 * This Class is used to get category features  and all features.
 */

public class Processor implements MyKeyword {

	private boolean isRead = false;
	private boolean endRead = false;
	public HashSet<String> wordsOfDocument = new HashSet<String>();	
	
	int categoryNumber = 0;
	private String sentences ="";
	
	public Feature[] features = new Feature[8];

	public Processor() {
		for(int i = 0; i < 8; i++) {
			features[i] = new Feature(i, categoryNames[i]);
		}
	
	}
	public void textProcessing(String line) {
		if(line.length() == 0)
			return;
		
		if(line.contains(DOCUMENT)) {
			if(isRead) {
				isRead = false;
				endRead = true;
			}
		}
	
		else if((!isRead) && endRead) {
			for(Iterator<String> itr = wordsOfDocument.iterator();  itr.hasNext();)
			{
				String s = itr.next();
				
				if(features[categoryNumber].wordsOfDocuments.containsKey(s)) {
					features[categoryNumber].wordsOfDocuments.put(s, features[categoryNumber].wordsOfDocuments.get(s) + 1);
				}
				else
					features[categoryNumber].wordsOfDocuments.put(s, 1);
				
			}
			wordsOfDocument.clear();
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
					return;
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
		
	public void doLast() {
		for(Iterator<String> itr = wordsOfDocument.iterator();  itr.hasNext();)
		{
			String s = itr.next();
			
			if(features[categoryNumber].wordsOfDocuments.containsKey(s)) {
				int i = features[categoryNumber].wordsOfDocuments.get(s) + 1;
				features[categoryNumber].wordsOfDocuments.put(s, i);
			}
			else
				features[categoryNumber].wordsOfDocuments.put(s, 1);
			
		}
		wordsOfDocument.clear();
	}
	
}
