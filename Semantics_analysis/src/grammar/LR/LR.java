package grammar.LR;
/**
 * @author lzh
 * @date 2022/5/21 0026
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class LR
{
	public static HashMap<Integer,Regular> int_to_regular=new HashMap<>();
	public static HashMap<Regular,Integer> regular_to_int=new HashMap<>();

	public static HashMap<String,String> non_end = new HashMap<String,String>();//非终结符
	public static HashMap<String,String> end = new HashMap<String,String>();//终结符
	public static HashMap<String, Regular> regular = new HashMap<String,Regular>();//左部对应的右部规则
	public static int state_id = 0;//当前状态数
	public static String start = "";//开始符
	private String L = "";//临时终结符记录
	private ArrayList<String> list = new ArrayList<String>();//临时表
	public void first_deal(String input)//求文法的终结符和非终结符
	{
		final boolean left = false;//产生式左部
		final boolean right = true;//产生式右部
		boolean flag = left;
		input = input + "\n";//加入最后的分隔符
		for(int i=0 ; i<input.length() ; i++)
		{
			if(input.charAt(i) == '-' &&input.charAt(i+1) == '>')
			{
				flag = right;//遇到箭头，表示将要遍历产生式右部
				i = i + 1;//改变索引位置
			}
			else if(input.charAt(i) == '\n')
			{
				flag = left;//遇到换行符表示将要遍历产生式左部
				if(regular.get(L) == null)
				{
					Regular temp = new Regular();
					temp.insert(list);
					regular.put(L, temp);
				}
				else
				{
					regular.get(L).insert(list);
				}
				L = "";
				list.clear();
			}
			else if(input.charAt(i) == ' ')
			{
				continue;//忽略空格
			}
			else if(Character.isLetter(input.charAt(i)))
			{
				StringBuffer deal = new StringBuffer();//定义临时处理的字符串
				for(int j=i ; j<input.length() ; j++)
				{
					if(!Character.isLetter(input.charAt(j)))
					{
						i = j - 1;//改变索引位置
						break;
					}
					else
					{
						deal.append(input.charAt(j));//记录已经遍历的字符串内容
					}
				}
				String temp = deal.toString();//格式转换
				if(flag == left)
				{
					L = temp;
				}
				else if(flag == right)
				{
					list.add(temp);
				}
				if(flag == left && non_end.get(temp) == null)
				{
					if(start.equals(""))
					{
						start = temp;//第一个非终结符是开始符
					}
					non_end.put(temp, "非终结符");//如果是产生式左部的字符，加入非终结符集
				}
				else if(flag == right && end.get(temp) == null)
				{
					end.put(temp, "终结符");//产生式右部暂时处理成终结符集
				}
			}
			else if(!Character.isLetter(input.charAt(i)))
			{
				if(i+1<input.length()){
					if(input.charAt(i)=='+'&&input.charAt(i+1)=='+'){
						list.add(input.substring(i,i+2));
						end.put(input.substring(i,i+2), "终结符");//非字母的字符直接加入终结符集
						i++;
						continue;
					}
					if(input.charAt(i)=='-'&&input.charAt(i+1)=='-'){
						list.add(input.substring(i,i+2));
						end.put(input.substring(i,i+2), "终结符");//非字母的字符直接加入终结符集
						i++;
						continue;
					}
					if(input.charAt(i)=='='&&input.charAt(i+1)=='='){
						list.add(input.substring(i,i+2));
						end.put(input.substring(i,i+2), "终结符");//非字母的字符直接加入终结符集
						i++;
						continue;
					}
					if(input.charAt(i)=='>'&&input.charAt(i+1)=='='){
						list.add(input.substring(i,i+2));
						end.put(input.substring(i,i+2), "终结符");//非字母的字符直接加入终结符集
						i++;
						continue;
					}
					if(input.charAt(i)=='<'&&input.charAt(i+1)=='='){
						list.add(input.substring(i,i+2));
						end.put(input.substring(i,i+2), "终结符");//非字母的字符直接加入终结符集
						i++;
						continue;
					}
				}
				list.add(String.valueOf(input.charAt(i)));
				end.put(String.valueOf(input.charAt(i)), "终结符");//非字母的字符直接加入终结符集
			}
		}
		for(String x : non_end.keySet())
		{
			if(end.get(x) != null)
			{
				end.keySet().remove(x);//删去终结符集合中出现过的非终结符集
			}
		}
	}

	public static ArrayList<String> first_each(String input)//求非终结符的FIRST集(包含空串）
	{
		ArrayList<String> ans = new ArrayList<String>();//返回的最终结果
		Regular temp = new Regular();//定义临时对象
		temp = regular.get(input);//取出右部规则
		for(Integer i : temp.list.keySet())
		{
			String s = temp.list.get(i).get(0);//取出右部规则首字符
			if(s.equals(input)) continue;//和原串相等则忽略（相当于左递归的部分）
			else if(end.get(s) != null)
			{
				ans.add(s);//终结符加入结果
			}
			else if(non_end.get(s) != null)
			{
				ArrayList<String> ans_copy = new ArrayList<String>();//临时存储
				ans_copy = first_each(s);//如果是非终结符则递归计算，加入非终结符的first集
				ans.addAll(ans_copy);//加入结果
			}
		}
		remove_repetition(ans);//去除重复元素，虽然这一步放在最外面用一次更好
		return ans;//返回计算结果
	}
	public static boolean  blankable_each(String input)//判断某非终结符是否可空
	{
		if(first_each(input).contains("ε")) return true;//如果first集包含空串则可空
		else return false;//否则不可空
	}
	public static void remove_repetition(ArrayList<String> input)//去除重复元素
	{
		HashSet<String> set = new HashSet();//利用set集合去除线性表的重复元素
		for(String i : input)
		{
			set.add(i);
		}
		input.clear();
		input.addAll(set);//此去除重复元素用于顺序不影响结果的线性表
	}
	public static HashSet<String> first(ArrayList<String> input)//求某串的FIRST集
	{
		HashSet<String> ans = new HashSet<String>();//返回计算结果的线性表
		for(int i=0 ; i<input.size() ; i++)//保证顺序遍历
		{
			if(end.get(input.get(i)) != null)
			{
				ans.add(input.get(i));
				return ans;
			}
			ans.addAll(first_each(input.get(i)));//先计算答案
			if(!blankable_each(input.get(i)))//如果遇到不可空串，则停止
			{
				break;
			}
		}
		return ans;//返回计算结果
	}

	public ArrayList<String> follow(String input)//求某非终结符的follow集
	{
		ArrayList<String> ans = new ArrayList<String>();//返回计算结果的线性表
		if(input.equals(start))
		{
			ans.add("$");//开始符的follow集加入$符号
		}
		for(String i : non_end.keySet())
		{
			for(Integer j=1 ; j<=regular.get(i).id ; j++)
			{
				ArrayList<String> temp = new ArrayList<String>();
				temp = (ArrayList<String>)regular.get(i).get_regular(j).clone();
				while(temp.size() != 0)
				{
					if(temp.get(0).equals(input))
					{
						temp.remove(0);
						if(temp.size() == 0 && !i.equals(input))
						{
							ArrayList<String> ans_copy = new ArrayList<String>();
							ans_copy = follow(i);
							ans.addAll(ans_copy);
						}
						ans.addAll(first(temp));
						if(ans.contains("ε") && !i.equals(input))
						{
							ans.remove("ε");
							ArrayList<String> ans_copy = new ArrayList<String>();
							ans_copy = follow(i);
							ans.addAll(ans_copy);
						}
					}
					if(temp.size() != 0)
					{
						temp.remove(0);
					}
				}
			}
		}
		remove_repetition(ans);
		return ans;
	}


	public void print()
	{
//		ArrayList<String> test = new ArrayList<String>();
//		test.add("stmt");
//		test.add("expr");
//		System.out.println(first_each("expr"));
//		System.out.println(first_each("stmt"));
//		System.out.println(follow("B"));
//		System.out.println(non_end);//输出非终结符
//		System.out.println(end);//输出终结符
//		System.out.println(start);//输出开始符
	}
}
