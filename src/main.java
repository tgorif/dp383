import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class main {

	public static void main(String[] args) {
		System.out.println("comparing 2 items");
		System.out.println(same_necklace("nicole", "icolen"));
		System.out.println(same_necklace("nicole", "lenico"));
		System.out.println(same_necklace("nicole", "coneli"));
		System.out.println(same_necklace("aabaaaaabaab", "aabaabaabaaa"));
		System.out.println(same_necklace("abc", "cba"));
		System.out.println(same_necklace("xxyyy", "xxxyy"));
		System.out.println(same_necklace("xyxxz", "xxyxz"));
		System.out.println(same_necklace("x", "x"));
		System.out.println(same_necklace("x", "xx"));
		System.out.println(same_necklace("x", ""));
		System.out.println(same_necklace("", ""));
		System.out.println();
		
		System.out.println("repeating subsequence");
		System.out.println(repeats("abc"));
		System.out.println(repeats("abcabcabc"));
		System.out.println(repeats("abcabcabcx"));
		System.out.println(repeats("aaaaaa"));
		System.out.println(repeats("a"));
		System.out.println(repeats(""));
		System.out.println();
		
		printIdenticalNecklaces(3);

	}
	private static void printIdenticalNecklaces(int minduplicates) {
		System.out.println("Searching for String describing at least " +  minduplicates + " identical Necklaces");
		NecklaceLetterCount.map= new HashMap<>();
		ArrayList<Necklace> necklaces = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File("Files/testcase.txt"));
			while(scanner.hasNextLine()) {
				Necklace n = new Necklace(scanner.nextLine());
				necklaces.add(n);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Map.Entry<String, NecklaceLetterCount> e : NecklaceLetterCount.map.entrySet()) {
			e.getValue().compareTo();
		}
		Set<Necklace> set = new HashSet<>();
		for(Necklace n : necklaces) {
			if(n.identicalNecklace.size()>=minduplicates && !set.contains(n)) {
				System.out.print(n.name);
				set.add(n);
				for(Necklace n2 :n.identicalNecklace) {
					System.out.print(" " + n2.name);
					set.add(n2);
				}
				System.out.println(" " + (n.identicalNecklace.size()+1));
			}
		}
	}
	public static boolean same_necklace(String base,String target) {
		if(base==null || target==null || base.length()!=target.length())return false;
		if(base.length()==0)return true;
	ArrayList<Integer> startingIndexes = getStartingIndexes(base.charAt(0),target);
	for(int i: startingIndexes) {
		if(isShiftedSubstring(base,target,i))return true;
	}
	return false;
	}
	private static ArrayList<Integer> getStartingIndexes(char c,String s){
		ArrayList<Integer> result = new ArrayList<>();
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)==c) {
				result.add(i);
			}
		}
		return result;
	}
	private static boolean isShiftedSubstring(String base,String target,int target_index) {
		String target1=target.substring(target_index);
		String target2=target.substring(0, target_index);
		String base1=base.substring(0, target1.length());
		String base2=base.substring(base1.length());
		if(target1.equals(base1) &&target2.equals(base2))return true;
		return false;
	}
	private static int repeats(String target) {
		if(target==null)return 0;
		if(target.length()==0)return 1;
		int result=0;
		ArrayList<String> list = new ArrayList<>();
		list.add(target);
		for(int i=0;i<target.length();i++) {
			String tmp = list.get(list.size()-1).substring(1)+list.get(list.size()-1).charAt(0);
			list.add(tmp);
		}
		for(String s : list) {
			if(s.equals(target))result++;
		}
		return result-1;
	}
}
class Necklace{
	String name;
	int[] charCount;
	String ID;
	ArrayList<Necklace> identicalNecklace;
	public Necklace(String s) {
		name=s;
		charCount= new int['z'+1];
		for(int i=0;i<s.length();i++) {
			charCount[s.charAt(i)]++;
		}
		ID= new String();
		for(int i : charCount) {
			ID=ID+i;
		}
		if(NecklaceLetterCount.map.containsKey(ID)) {
			NecklaceLetterCount.map.get(ID).items.add(this);
		}
		else {
			NecklaceLetterCount tmp = new NecklaceLetterCount(this);
		}
		identicalNecklace = new ArrayList<>();
	}
}
class NecklaceLetterCount{
	static Map<String,NecklaceLetterCount> map;
	String ID;
	ArrayList<Necklace> items;
	public NecklaceLetterCount(Necklace n) {
		ID=n.ID;
		items= new ArrayList<>();
		items.add(n);
		map.put(ID, this);
	}
	public void compareTo() {
		for(Necklace n1 : items) {
			for(Necklace n2 : items) {
				if(n1!=n2 && !n1.identicalNecklace.contains(n2) && !n2.identicalNecklace.contains(n1)) {
					boolean isSame =main.same_necklace(n1.name,n2.name);
					if(isSame) {
						n1.identicalNecklace.add(n2);
						n2.identicalNecklace.add(n1);
					}
				}
			}
		}
	}
}
