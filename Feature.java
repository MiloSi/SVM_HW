import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/*
 * 
 * This Class has category number, category name, and features.
 */

public class Feature  {
	
	private int categoryNumber;
	private String category;
	
	public int number = 0; 
	public HashMap<String, Integer> wordsOfDocuments = new HashMap<String, Integer>();
	
	
	public Feature(int num, String name){
		this.categoryNumber = num;
		this.category = name;
	}
	int getNumber() {
		return categoryNumber;
	}
	String getCategory() {
		return category;
	}
	
}
