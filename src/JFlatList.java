import java.util.ArrayList;
import java.util.HashMap;

public class JFlatList {
	private static HashMap<Integer, ArrayList<Integer>> lists = new HashMap<>();
	private static int currentKey = 0;

	public static int createNewList() {
		ArrayList<Integer> i = new ArrayList<>();
		int key = currentKey;
		currentKey++;
		lists.put(key, i);
		return key;
	}

	public static int listSize(int i) {
		return lists.get(i).size();
	}

	public static int getFromList(int i, int j) {
		return lists.get(i).get(j);
	}

	public static void addToList(int i, int v) {
		lists.get(i).add(v);
	}

	public static void setToList(int i, int j, int v) {
		lists.get(i).set(j, v);
	}
}
