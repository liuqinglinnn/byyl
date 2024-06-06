import grammar.LR.AnalysisTable;  // 导入 LR 分析表模块
import grammar.LR.LR;  // 导入 LR 解析模块
import grammar.LR.Regular;  // 导入 LR 规则模块
import grammar.Runing.Grammar_Analysis;  // 导入语法分析模块
import lexical.MapList.maplist;  // 导入词法分析的映射列表模块

import java.io.BufferedReader;  // 导入缓冲读取器类
import java.io.FileInputStream;  // 导入文件输入流类
import java.io.FileNotFoundException;  // 导入文件未找到异常类
import java.io.InputStreamReader;  // 导入输入流读取器类
import java.util.ArrayList;  // 导入 ArrayList 类
import java.util.Map;  // 导入 Map 接口

import static grammar.LR.Create_Itemsets.lr1_list;  // 静态导入 lr1 项目集列表
import static grammar.LR.Create_Itemsets.move;  // 静态导入 move 方法
import static grammar.LR.LR.*;  // 静态导入 LR 类的所有静态成员
import static lexical.Lin.Lexical_analysis.StartLexicalAnalysis;  // 静态导入开始词法分析的方法
import static lexical.MapList.maplist.tokens_list;  // 静态导入令牌列表

public class main {
    public static void main(String[] args) throws Exception {

        // 打开语法文件，并准备读取内容
        FileInputStream fis = new FileInputStream("Semantics_analysis/grammar2.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        // 用于存储读取的文件内容
        String input = "";
        String str = null;

        // 逐行读取文件内容并拼接到 input 字符串中
        while((str = br.readLine()) != null){
            input += str + "\n";
        }
        br.close();  // 关闭 BufferedReader

        // 处理读取到的语法内容
        new LR().first_deal(input);

        // 打印非终结符集合和终结符集合
        System.out.println(non_end);
        System.out.println(end);

        int num = 0;

        // 遍历规则集合，将每条规则添加到 int_to_regular 映射中
        for (Map.Entry<String, Regular> entry : regular.entrySet()) {
            for (Map.Entry<Integer,ArrayList<String>> entry2 : entry.getValue().list.entrySet()) {
                int_to_regular.put(num, new Regular(entry.getKey(), entry2.getValue()));
                num++;
            }
        }

        // 打印 int_to_regular 映射中的规则
        for(Map.Entry<Integer, Regular> entry : int_to_regular.entrySet()){
            if(entry.getKey() == -1)
                continue;
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        // 执行项目集的状态迁移
        move(0);

        // 打印 LR(1) 项目集列表及其大小
        System.out.println(lr1_list);
        System.out.println(lr1_list.size());

        // 创建分析表
        new AnalysisTable();

        // 打开分析图形用户界面
        new Analysis_GUI();
    }
}
