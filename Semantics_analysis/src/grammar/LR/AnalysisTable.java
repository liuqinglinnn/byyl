package grammar.LR;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static grammar.LR.Create_Itemsets.endstate;
import static grammar.LR.Create_Itemsets.lr1_list;

/**
 * 分析表类，用于构建 LR 分析表和显示 GUI
 * 作者: lzh
 * 日期: 2022/5/21
 */

public class AnalysisTable {
    // 存储分析表，每行是一个 HashMap，键是符号，值是动作或状态
    public static ArrayList<HashMap<String, String>> AnalysisTable = new ArrayList<>();
    public static ArrayList<String> mark = new ArrayList<>(); // 终结符集合
    public static ArrayList<String> nonmark = new ArrayList<>(); // 非终结符集合
    int rowsum = endstate; // 行数，对应状态数
    int columnsum = 0; // 列数，对应符号数
    JFrame mainframe = new JFrame(); // 主框架
    JTable jTable; // 表格组件

    // 构造函数，初始化分析表和 GUI
    public AnalysisTable() {
        CreateAnalysisTable(); // 创建分析表
        CreateGUI(); // 创建 GUI 界面
        DrawAnalysisTable(); // 绘制分析表
        mainframe.setVisible(true); // 显示主框架
        System.out.println(mark);
        System.out.println(nonmark);
    }

    // 创建分析表的方法
    public void CreateAnalysisTable() {
        // 初始化分析表的每一行
        for (int i = 0; i <= endstate; i++) {
            AnalysisTable.add(new HashMap<String, String>());
        }

        // 遍历 LR(1) 项目集列表
        for (Map.Entry<String, String> entry : lr1_list.entrySet()) {
            String key = entry.getKey(); // 获取键
            int i = getStartDigits(key); // 获取键中数字部分的结束位置
            String index = key.substring(0, i); // 提取索引部分
            key = key.substring(i); // 提取符号部分
            String Value = entry.getValue(); // 获取值

            // 根据值判断是 ACTION 还是 GOTO 并分类记录
            if (Value.charAt(0) >= '0' && Value.charAt(0) <= '9') {
                if (!ifexists(nonmark, key)) nonmark.add(key);
            } else {
                if (!ifexists(mark, key)) mark.add(key);
            }

            move(mark); // 将 $ 符号移到末尾
            AnalysisTable.get(Integer.parseInt(index)).put(key, Value); // 填入分析表
        }
    }

    // 绘制分析表的方法
    public void DrawAnalysisTable() {
        for (int i = 0; i <= endstate; i++) {
            AnalysisTable.add(new HashMap<String, String>());
        }
        for (Map.Entry<String, String> entry : lr1_list.entrySet()) {
            String key = entry.getKey();
            int i = getStartDigits(key);
            String index = key.substring(0, i);
            key = key.substring(i);
            String Value = entry.getValue();
            System.out.println(index + " " + key + " " + Value);

            // 根据符号是终结符还是非终结符确定表格位置
            if (ReturnIndex(mark, key) != -1) {
                jTable.setValueAt(Value, Integer.parseInt(index) + 2, ReturnIndex(mark, key) + 1);
            } else {
                jTable.setValueAt(Value, Integer.parseInt(index) + 2, ReturnIndex(nonmark, key) + 1 + mark.size());
            }
        }
    }

    // 创建 GUI 界面的方法
    public void CreateGUI() {
        mainframe.setTitle("分析表"); // 设置主框架标题
        mainframe.setSize(600, 600); // 设置主框架大小
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作
        mainframe.setLocationRelativeTo(null); // 设置居中显示
        columnsum = mark.size() + nonmark.size(); // 计算列数

        // 创建表格数据和列名
        Object[][] tableDate = new Object[rowsum + 3][columnsum + 3];
        for (int i = 0; i < rowsum + 1; i++) {
            tableDate[i + 2][0] = i; // 状态列
        }
        for (int i = 0; i < mark.size(); i++) {
            tableDate[0][i + 1] = "ACTION";
            String s = mark.get(i);
            tableDate[1][i + 1] = s; // 终结符列
        }
        for (int i = 0; i < nonmark.size(); i++) {
            tableDate[0][mark.size() + i + 1] = "GOTO";
            String s = nonmark.get(i);
            tableDate[1][mark.size() + i + 1] = s; // 非终结符列
        }
        String[] name = new String[columnsum + 1];
        for (int i = 0; i < columnsum + 1; i++) {
            name[i] = String.valueOf(i);
        }
        tableDate[1][0] = "状态";
        jTable = new JTable(tableDate, name); // 创建表格

        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 设置表格自动调整模式
        JScrollPane pane = new JScrollPane(jTable); // 创建滚动面板
        mainframe.add(pane); // 将滚动面板添加到主框架
    }

    // 检查数组中是否存在相同元素
    public static boolean ifexists(ArrayList arrayList, String str) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (str.equals(arrayList.get(i))) return true;
        }
        return false;
    }

    // 获取以数字开头的字符串的数字部分的结束位置
    public int getStartDigits(String str) {
        int len = str.length();
        int stopPos = 0;
        for (int i = 0; i < len; i++) { // 遍历 str 的字符
            char ch = str.charAt(i);
            if (!(ch >= '0' && ch <= '9')) { // 如果当前字符不是数字
                stopPos = i;
                break;
            }
        }
        return stopPos;
    }

    // 将 $ 符号移到数组末尾
    public void move(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals("$")) {
                arrayList.set(i, arrayList.get(arrayList.size() - 1));
                arrayList.set(arrayList.size() - 1, "$");
            }
        }
    }

    // 返回数组中指定元素的索引
    public int ReturnIndex(ArrayList arrayList, String s) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (s.equals(arrayList.get(i))) return i;
        }
        return -1;
    }
}
