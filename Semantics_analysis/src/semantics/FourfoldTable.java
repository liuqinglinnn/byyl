// 引入相关包
package semantics;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// 类的声明
public class FourfoldTable {

    // 四元组的 Hash 表，存储四元式指令
    private HashMap<Integer,four> three_address=new HashMap<>();

    // 主窗体
    JFrame mainframe=new JFrame();

    // 表格组件
    JTable jTable;

    // 表格的列数
    int column=0;

    // 构造方法，初始化四元式表格
    public FourfoldTable()
    {
        // 四元式数据从语义分析器中获取
        three_address=Semantics_analysis.three_address;
        column=three_address.size(); // 获取四元式的数量作为列数

        // 控制台输出四元式数量（调试用）
        System.out.println(Semantics_analysis.three_address.size());

        // 创建 GUI 窗体
        CreateGUI();

        // 绘制四元式表格
        DrawFourfoldTable();

        // 使主窗体可见
        mainframe.setVisible(true);
    }

    // 创建四元式表的示例数据，此处为手动添加的示例
    public void CreateFourfoldTable()
    {
        three_address.put(0,new four("um","c","-","T1"));
        // ... 其他四元式的添加
        // 省略了具体添加过程，具体添加了哪些四元式数据
        // 这里的 "um", "*", "+" 等为操作符，"T1", "T2" 等为临时变量名或操作数
    }

    // GUI 窗体创建方法
    public void CreateGUI()
    {
        // 设置窗体标题
        mainframe.setTitle("四元表");

        // 设置窗体尺寸
        mainframe.setSize(1000,1000);

        // 设置默认关闭操作：关闭窗口时退出程序
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗体布局
        mainframe.setLayout(new BorderLayout());

        // 设置窗体位置为屏幕中央
        mainframe.setLocationRelativeTo(null);

        // 创建二维数组，存储表格数据，行数为列数+1，列数为5（序号，操作符，操作数1，操作数2，结果）
        Object[][] tableDate=new Object[column+1][5];

        // 初始化序号列
        for(int i=0,j=0; i<column; i++,j++)
        {
            tableDate[i+1][0]=j; // 序号从0开始计数
        }

        // 设置表头名称
        tableDate[0][0]="序号";
        tableDate[0][1]="OP";
        tableDate[0][2]="arg1";
        tableDate[0][3]="arg2";
        tableDate[0][4]="result";

        // 表头数组
        String[] name=new String[]{"序号","OP","arg1","arg2","result"};

        // 使用表格数据和表头创建 JTable 对象
        jTable=new JTable(tableDate,name);

        // 设置表格行高
        for (int j=0; j <= jTable.getRowCount(); j++){
            jTable.setRowHeight(j,30);
        }

        // 设置表格字体
        jTable.setFont(new Font("楷体",0,25));

        // 把表格加入到主窗体中心区域
        mainframe.add(jTable, BorderLayout.CENTER);
    }

    // 绘制四元式表格的具体数据
    public void DrawFourfoldTable()
    {
        // 遍历 four 元素存储的 HashMap，设置表格各个单元格的数据
        for(Map.Entry<Integer, four> entry:three_address.entrySet())
        {
            jTable.setValueAt(entry.getValue().op,     entry.getKey()+1, 1); // 设置操作符
            jTable.setValueAt(entry.getValue().arg1,   entry.getKey()+1, 2); // 设置第一个操作数
            jTable.setValueAt(entry.getValue().arg2,   entry.getKey()+1, 3); // 设置第二个操作数
            jTable.setValueAt(entry.getValue().result, entry.getKey()+1, 4); // 设置结果
        }
    }
}

// 注意：此代码引用了三个未定义的类或对象：Semantics_analysis、four 和 Column
// 其中 Semantics_analysis 似乎是一个语义分析类，four 似乎是一个存储四元组元素的类
