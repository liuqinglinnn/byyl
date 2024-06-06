import grammar.Runing.Grammar_Analysis;
import lexical.MapList.Token;
import lexical.MapList.maplist;
import semantics.FourfoldTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static lexical.Lin.Lexical_analysis.StartLexicalAnalysis;
import static lexical.MapList.maplist.tokens_list;
import static lexical.tool.Tool1.*;


public class Analysis_GUI extends JFrame implements ActionListener {
	JPanel panel1;            //承载窗格
	JPanel panel2;            //承载按钮
	JTextArea input_text;      //定义输入框
	JTextArea complier_text;  //定义输出框
	JScrollPane jspane1;      //定义滚动窗格
	JScrollPane jspane2;      //定义滚动窗格
	JSplitPane jsplitpane;      //定义拆分窗格
	JButton send;              //定义按钮

	public static void main(String[] args) {
		Analysis_GUI gui = new Analysis_GUI();    //显示界面
	}

	public Analysis_GUI() {
		panel1 = new JPanel(); // 创建面板 1
		panel2 = new JPanel(); // 创建面板 2
		OutputScreen(); // 设置输出区域
		InputScreen(); // 设置输入区域
		SplitScreen(); // 设置分割面板
		Button(); // 设置按钮
		this.add(panel1, BorderLayout.CENTER); // 将面板 1 添加到窗口中央
		this.add(panel2, BorderLayout.SOUTH); // 将面板 2 添加到窗口底部
		this.setTitle("词法分析"); // 设置窗口标题
		this.setSize(1000, 800); // 设置窗口大小
		this.setLocationRelativeTo(null); // 窗口居中显示
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作
		this.setVisible(true); // 显示窗口
		send.addActionListener(this); // 为按钮添加动作监听器
	}

	public void OutputScreen() {
		complier_text = new JTextArea(""); // 创建输出文本区域
		complier_text.setLineWrap(true); // 启用自动换行
		Font f0 = new Font("MonoLisa", Font.PLAIN, 20); // 设置字体
		complier_text.setFont(f0);
		complier_text.setEditable(false); // 使文本区域只读
		jspane1 = new JScrollPane(complier_text); // 创建输出区域的滚动面板
	}

	public void InputScreen() {
		input_text = new JTextArea(""); // 创建输入文本区域
		input_text.setLineWrap(true); // 启用自动换行
		Font f0 = new Font("MonoLisa", Font.PLAIN, 20); // 设置字体
		input_text.setFont(f0);
		jspane2 = new JScrollPane(input_text); // 创建输入区域的滚动面板
	}

	public void SplitScreen() {
		JLabel inputLabel = new JLabel("请输入代码："); // 创建输入区域的标签
		JLabel outputLabel = new JLabel("词法分析结果如下："); // 创建输出区域的标签
		Font labelFont = new Font("楷体", Font.PLAIN, 20); // 创建标签的字体
		inputLabel.setFont(labelFont); // 设置输入标签的字体
		outputLabel.setFont(labelFont); // 设置输出标签的字体
		JPanel inputPanel = new JPanel(new BorderLayout()); // 使用 BorderLayout 创建输入面板
		JPanel outputPanel = new JPanel(new BorderLayout()); // 使用 BorderLayout 创建输出面板
		inputPanel.add(inputLabel, BorderLayout.NORTH); // 将输入标签添加到输入面板顶部
		inputPanel.add(jspane2, BorderLayout.CENTER); // 将输入文本区域添加到输入面板中央
		outputPanel.add(outputLabel, BorderLayout.NORTH); // 将输出标签添加到输出面板顶部
		outputPanel.add(jspane1, BorderLayout.CENTER); // 将输出文本区域添加到输出面板中央
		jsplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, outputPanel); // 创建水平分割面板
		jsplitpane.setDividerLocation(500); // 设置初始分割位置
		jsplitpane.setDividerSize(1); // 设置分割大小
		panel1.setLayout(new BorderLayout()); // 设置面板布局
		panel1.add(jsplitpane); // 将分割面板添加到面板中
	}

	public void Button() {
		Font f_button = new Font("微软雅黑", Font.PLAIN, 18); // 按钮字体
		send = new JButton("编译运行"); // 创建编译和运行按钮
		send.setFont(f_button);
		send.setBackground(new Color(70, 130, 180)); // 设置按钮背景颜色
		send.setForeground(Color.WHITE); // 设置按钮文本颜色
		send.setBorderPainted(false); // 移除按钮边框
		panel2.setBackground(new Color(240, 240, 240)); // 设置面板背景颜色
		panel2.setLayout(new FlowLayout(FlowLayout.RIGHT)); // 设置按钮面板布局
		panel2.add(send); // 将按钮添加到面板中
	}

	public void actionPerformed(ActionEvent e) // 定义事件监听方法
	{
		if (e.getSource() == send) // 判断事件源是否为“send”按钮
		{
			maplist.init(); // 初始化 maplist，具体功能依赖于 `maplist` 类定义的 `init` 方法

			// 从输入文本域中获取文本，分割成单独的行
			String[] input = input_text.getText().split("\n");

			// 逐行进行词法分析
			for (int i = 0; i < input.length; i++) {
				StartLexicalAnalysis(input[i], i + 1);
			}

			// 初始化语法分析所需的输入堆栈，并压入结束符
			Grammar_Analysis.input.push(new Token("$", "$"));

			// 将所有的词法分析结果（token）反向压入语法分析的输入堆栈中
			for (int i = tokens_list.size() - 1; i >= 0; i--) {
				Grammar_Analysis.input.push(tokens_list.get(i));
			}

			// 控制台输出，调试用：输出当前的语法分析输入堆栈
			System.out.println(Grammar_Analysis.input);

			// 运行语法分析过程，可能会抛出异常
			try {
				Grammar_Analysis.runing();
			} catch (Exception exception) {
				exception.printStackTrace(); // 打印异常堆栈信息到控制台
			}

			// 获取处理后的结果，并设置到编译器的文本域中
			String result_after_deal = string_result02();
			complier_text.setText(result_after_deal);

			// 创建一个新的四元式表格实例，具体细节取决于该类的构造器和方法定义
			new FourfoldTable();

			// 清除所有分析过程中使用的全局变量或列表
			clear_all();
		}
	}
}
