package grammar.LR;
/**
 * @author lzh
 * @date 2022/5/21 0027
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Regular
{
	public String left;
	public ArrayList<String> right=new ArrayList<>();

	public Regular(String left, ArrayList<String> right) {
		this.left = left;
		this.right = right;
	}
	public Regular(){

	}

	public HashMap<Integer,ArrayList<String>> list = new HashMap<Integer,ArrayList<String>>();
	Integer id = 0;
	public void insert(ArrayList<String> L)
	{
		id = id + 1;
		ArrayList<String> copy = new ArrayList<String>();
		copy = (ArrayList<String>)L.clone();
		list.put(id, copy);
	}
	public ArrayList<String> get_regular(Integer index)
	{
		return list.get(index);
	}
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(obj instanceof Regular)
		{
			Regular regular = (Regular) obj;
			if(this.list.equals(regular.list))
			{
				return true;
			}
		}
		return false;
	}
	public int hashCode()
	{
		int result = 17;
		result = 31 * result + (list == null ? 0 : list.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Regular{" +
				"left='" + left + '\'' +
				", right=" + right +
				'}';
	}
	public boolean isequal(Regular temp){
		if(!temp.left.equals(this.left)){
			return false;
		}
		if(temp.right.size()!=this.right.size()){
			return false;
		}
		for (int i = 0; i <temp.right.size() ; i++) {
			if(!temp.right.get(i).equals(this.right.get(i)))
				return false;
		}
		return true;

	}
}
