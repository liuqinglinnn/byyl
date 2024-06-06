package grammar.Runing;

import grammar.LR.Regular;
import lexical.MapList.Token;

import java.io.*;
import java.util.*;

import static grammar.LR.Create_Itemsets.lr1_list;
import static grammar.LR.LR.int_to_regular;
import static grammar.LR.LR.start;
import static semantics.Semantics_analysis.*;

/**
 * @author lzh
 * @date 2022/5/21 0021
 */
public class Grammar_Analysis
{
	public static ArrayList<String> result_save=new ArrayList<>();

	public static Stack<String> state = new Stack<String>();//状态栈
	public static Stack<Token> token = new Stack<>();//符号栈
	public static Stack<Token> input = new Stack<>();//输入栈，不使用字符串是便于操作
	public static String action = "";//动作
	public static int wrong_id = 0;//错误个数
	public static int max_wrong = 10;//错误阈值
	public static int step = 1;//分析过程步数
	public static void runing() throws Exception {
		final FileOutputStream fos = new FileOutputStream("error.txt");
		final OutputStreamWriter osw = new OutputStreamWriter(fos);
		final BufferedWriter bw = new BufferedWriter(osw);
		boolean flag_error=false;//是否报错过
		int count=0;
		System.out.println(lr1_list);
		while (action!="acc"){
			if (state.empty()) {
				state.push("0");//状态栈初始化
				token.push(new Token("$","$"));//符号栈初始化
			}

			result_save.add("----------------------------------------------------------------------");

			System.out.println("第"+(++count)+"步");
			result_save.add("第"+count+"步");
			result_save.add("输入栈:  "+input.toString());
//			System.out.println(state);
			result_save.add("状态栈:  "+state.toString());
//			System.out.println(token);
			result_save.add("符号栈:  "+token.toString());

			String now = state.peek();
			if(input.empty()){
				bw.flush();
				bw.close();
				System.out.println("输入栈已空，程序终止");
				return;
			}
			else now += input.peek().species_code;
			if(lr1_list.get(now)==null){//出错
				now=state.peek()+"ε";
				if(lr1_list.get(now)==null){
					flag_error=true;
					bw.write("第"+(count)+"步出错：状态I" + state.peek() + "出错字符："
							+ input.peek().species_code + "错误no：" + (10-max_wrong) );
					bw.newLine();
					if(max_wrong-- > 0 && !input.empty()){
						System.out.println("分析" + input.peek().species_code + "时出错，正在进行错误处理。剩余阈值：" + max_wrong);
						input.pop();
					}else{
						bw.write("错误数量超过阈值，程序终止");
						result_save.add("错误数量超过阈值，程序终止");
						bw.flush();
						bw.close();
						System.out.println("错误数量超过阈值，程序终止");
						return;
					}

				}else{
//					System.out.println(now);

					result_save.add("当前输入: "+now);

					now=lr1_list.get(now);
					System.out.println(now);

					result_save.add("对应操作: "+now);

					now = now.substring(1);
					Regular temp=int_to_regular.get(Integer.parseInt(now));
//					System.out.println("规约:"+temp.left + " - > " +temp.right);


					result_save.add("规约:"+temp.left + " - > " +temp.right);


					for (int i = 0; i <temp.right.size() ; i++) {
						if(!token.peek().attribute_code.equals(token.peek().species_code)){
							token_temp.push(token.peek());
						}
						token.pop();
						state.pop();
					}
					three_address_code(Integer.parseInt(now));
					token.push(new Token(temp.left,temp.left));
					now=state.peek()+temp.left;
					now=lr1_list.get(now);
					state.push(now);//入状态栈
					input.push(new Token("ε","ε"));
				}
			}else{
//				System.out.println(now);

				result_save.add("当前输入: "+now);

				now=lr1_list.get(now);
//				System.out.println(now);

				result_save.add("对应操作: "+now);

				if (now.charAt(0)=='S'){
//					System.out.println("移入");

					result_save.add("移入");

					now=now.substring(1);
					state.push(now);//入状态栈
					String temp = input.peek().species_code;//输入栈弹出
					String temp2 = input.peek().attribute_code;//输入栈弹出

					if(temp.equals("if")){
						if_true.push(address);
					}else if(temp.equals("else")){
						if_false.push(address);
					}else if(temp.equals("while")){
						while_1.push(address);
					}else if(temp.equals("do")){
						while_do.push(address);
					}


					input.pop();
					token.push(new Token(temp,temp2));//符号栈入栈
				}else if (now.charAt(0)=='R'){
					now = now.substring(1);
					Regular temp=int_to_regular.get(Integer.parseInt(now));
//					System.out.println("规约:"+temp.left + " - > " +temp.right);
					result_save.add("规约:"+temp.left + " - > " +temp.right);

					for (int i = 0; i <temp.right.size() ; i++) {
						if(!token.peek().attribute_code.equals(token.peek().species_code)){
							token_temp.push(token.peek());
						}
						token.pop();
						state.pop();
					}
					three_address_code(Integer.parseInt(now));
					token.push(new Token(temp.left,temp.left));
					now=state.peek()+temp.left;
					now=lr1_list.get(now);
					state.push(now);//入状态栈
				}else{
					action="acc";
					now=now.substring(4);
					Regular temp=int_to_regular.get(Integer.parseInt(now));
					for (int i = 0; i <temp.right.size() ; i++) {
						if(!token.peek().attribute_code.equals(token.peek().species_code)){
							token_temp.push(token.peek());
						}
						token.pop();
						state.pop();
					}
					token.push(new Token(temp.left,temp.left));
//					System.out.println("接受");
					result_save.add("----------------------------------------------------------------------");
					result_save.add("状态栈:  "+state.toString());
					result_save.add("符号栈:  "+token.toString());
					if(!flag_error)
						result_save.add("接受");
					else
						result_save.add("语法分析,去除错误项后,接受");

					if(!Error_string.equals("")){
						result_save.add("----------------------");
						result_save.add("----------------------");
						result_save.add("----------------------");
						result_save.add("语义分析,有错误项,忽略");
						bw.write("语义分析错误\n");
						bw.write(Error_string);
					}
					bw.flush();
					bw.close();

					three_address_code(Integer.parseInt(now));
				}
			}
		}
	}
}
