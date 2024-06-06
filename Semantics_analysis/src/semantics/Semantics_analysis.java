package semantics;

import lexical.MapList.Token;

import java.util.HashMap;
import java.util.Stack;


public class Semantics_analysis {
    // 定义各种静态属性，包括栈和哈希表，用于存储中间结果和代码
    public static Stack<Token> token_temp = new Stack<>(); // 符号临时栈，存放 Token
    public static Stack<String> op_temp = new Stack<>(); // 操作符临时栈
    public static Stack<String> logicop = new Stack<>(); // 逻辑操作符栈
    public static Stack<logic> logic_string = new Stack<>(); // 逻辑字符串栈，存放逻辑表达式
    public static Stack<Integer> if_true = new Stack<>(); // if 为真时跳转的地址栈
    public static Stack<Integer> if_false = new Stack<>(); // if 为假时跳转的地址栈
    public static Stack<Integer> while_1 = new Stack<>(); // while 循环的地址栈1
    public static Stack<Integer> while_do = new Stack<>(); // while 循环的地址栈2
    public static Stack<String> op = new Stack<>(); // 存放操作符的栈
    public static HashMap<Integer,String> address_three = new HashMap<>(); // 存放三地址码的HashMap
    public static HashMap<Integer,four> three_address = new HashMap<>(); // 存放四元式的HashMap
    public static HashMap<String,String> types = new HashMap<>(); // 存放类型信息的HashMap
    public static int address = 0; // 生成的代码地址
    public static int t_number = 0; // 中间变量标号
    public static String three = ""; // 三地址码字符串
    public static String opdouble = ""; // 双目操作符
    public static boolean flag_array = false; // 数组标志
    public static boolean flag_type_array = false; // 类型数组标志
    public static String index_array; // 数组索引
    public static String type_String = ""; // 类型字符串
    public static Token type_id; // 变量的类型 Token
    public static String Error_string = ""; // 错误字符串


    public static String new_label() {//新产生中间变量t
        t_number ++;//中间变量标号+1
        return "t" + t_number;//返回中间变量
    }

    public static void get_three(){//获取三地址码
        three = "";// 清空原有的三地址码文本
        for(int i=0;i<address;i++){
//            three+=i+":"+address_three.get(i)+"\n";
            // 循环拼接当前已有的三地址码
            three+=i+":"+three_address.get(i).toString();
        }
    }
    public static void regular01(){
        System.out.println("01");
        // 如果该声明是一个数组类型的声明（可能先前设置了这个标志）
        if(flag_type_array){
            // 将数组类型声明标志重置为 false 并提前返回，不执行后面的操作
            flag_type_array=false;
            return;
        }
        // 创建一个新的中间变量用于当前的声明
        String left=new_label();

        // 遍历 token_temp 栈，打印其中的 Token 信息，主要用于调试
        for (int i = 0; i < token_temp.size() ; i++) {
            System.out.print("token_temp栈内容：");
            System.out.println(token_temp.get(i).species_code + " " + token_temp.get(i).attribute_code);
        }

        // 检查当前类型标识符（比如变量名）是否已在 types 映射中定义（已声明）
        if(types.get(type_id.attribute_code) != null){
            // 如果已经存在，说明变量重复声明，记录错误信息
            Error_string += "错误！变量 " + type_id.attribute_code + " 已被声明！！！\n";
            System.out.println("nb"); // 打印调试信息，"nb" 没有明确的含义，可能是开发者用于标记的
        }else{
            // 如果不存在，将其添加到 types 映射中，表示声明成功，记录该变量的类型信息
            types.put(type_id.attribute_code, type_String);
        }

        // 打印 types 哈希映射的内容，主要用于调试，查看变量名和对应类型
        System.out.println(types);
    }

    //    public static void regular01(){
//        System.out.println("01");
//        String left=new_label();
//        for (int i = 0; i <token_temp.size() ; i++) {
//            System.out.println("token_temp表");
//            System.out.println(token_temp.get(i).species_code+" "+token_temp.get(i).attribute_code);
//        }
//        String right=token_temp.peek().attribute_code;
//        token_temp.pop();
//        token_temp.push(new Token(left,left));
//
//        address_three.put(address,left+"="+right);
//        three_address.put(address,new four("=",right,"",left));
//        address++;
//    }
    // regular02方法，处理赋值运算的中间代码
    public static void regular02() {
        // flag_array标识符用来检查是否处理的是一个数组赋值
        if (flag_array) {
            // 如果是数组赋值操作，取出左侧标识符
            String left = token_temp.peek().attribute_code;
            token_temp.pop();

            // 检查左侧标识符是否已定义
            if (types.get(left) == null) {
                // 若未定义，则添加错误信息到Error_string
                Error_string += "Error!" + left + "变量未定义\n";
            }

            // 将上一步中间代码的结果赋值给左侧标识符，在three_address结构中存储中间代码
            three_address.put(address, new four("=", three_address.get(address - 1).result, "", left));
            address++;

            // 设置flag_array标志为false，表示数组元素处理完毕
            flag_array = false;
            // 结束函数执行
            return;
        }

        // 不是数组元素赋值，打印标志和token_temp栈的内容
        System.out.println("02");
        System.out.println("token_temp表");
        for (int i = 0; i < token_temp.size(); i++) {
            System.out.println(token_temp.get(i).species_code + " " + token_temp.get(i).attribute_code);
        }

        // 取出左操作数，并检查是否已定义
        String left = token_temp.peek().attribute_code;
        token_temp.pop();
        if (types.get(left) == null) {
            Error_string += "Error!" + left + "变量未定义\n";
        }

        // 取出右操作数
        String right = token_temp.peek().attribute_code;
        token_temp.pop();

        // 检查左操作数是否为常量，常量不能被赋值
        if (left.matches("-?[0-9]+\\.?[0-9]*")) {
            Error_string += " Error!不能为常量赋值!!\n";
        }

        // 将右操作数的值赋给左操作数，在address_three结构中存储简化的中间代码
        address_three.put(address, left + "=" + right);
        // 在three_address结构中存储完整的中间代码
        three_address.put(address, new four("=", right, "", left));
        // 地址增加，用于下一次中间代码的存储
        address++;
    }

    // regular03方法，处理可能包括数组访问和二元运算符的表达式的中间代码生成
    public static void regular03() {
        // 打印标志和token_temp栈的内容
        System.out.println("03");
        System.out.println("token_temp表");
        for (int i = 0; i < token_temp.size(); i++) {
            System.out.println(token_temp.get(i).species_code + " " + token_temp.get(i).attribute_code);
        }

        // 检查flag_array标识符，确定是否处理数组访问
        if (flag_array) {
            // 为数组元素生成一个新的标签（或临时变量）
            String left = new_label();
            // 获取栈顶元素的值，代表数组的索引或者数组名
            String right = token_temp.peek().attribute_code;
            // 如果栈顶元素是id标识符但尚未定义，则添加错误信息
            if (token_temp.peek().species_code.equals("id") && types.get(token_temp.peek().attribute_code) == null) {
                Error_string += "Error!" + token_temp.peek().attribute_code + "变量未定义\n";
            }
            token_temp.pop(); // 弹出栈顶元素
            // 将数组访问操作的中间代码存入three_address
            three_address.put(address, new four("=[]", right, index_array, left));
            address++; // 地址增加
            return; // 结束函数执行
        }

        // 循环处理所有的操作符
        while (!op_temp.empty()) {
            // 为结果生成一个新标签（或临时变量）
            String left = new_label();
            // 从token_temp取出右操作数
            String right1 = token_temp.peek().attribute_code;
            // 如果右操作数是一个未定义的id标识符，则添加错误信息
            if (token_temp.peek().species_code.equals("id") && types.get(token_temp.peek().attribute_code) == null) {
                Error_string += "Error!" + token_temp.peek().attribute_code + "变量未定义\n";
            }
            token_temp.pop(); // 弹出栈顶元素
            // 从op_temp取出操作符
            String right2 = op_temp.peek();
            op_temp.pop();
            // 把新生成的标签（临时变量）放回token_temp用于后续运算
            token_temp.push(new Token(left, left));

            // 如果操作是除法，并且除数是0，则添加错误信息
            if (right2.substring(0, 1).equals("/") && right2.substring(1).equals("0")) {
                Error_string += " Error! 除数不能为0!\n";
            }
            // 把此次运算的简化形式放入address_three
            address_three.put(address, left + "=" + right1 + right2);
            // 按照四元式的形式把此次运算的信息放入three_address
            three_address.put(address, new four(right2.substring(0, 1), right1, right2.substring(1), left));
            address++; // 地址增加
        }
    }

    // regular04方法，处理诸如将操作符与操作数合并这样的步骤
    public static void regular04() {
        // 打印标志和token_temp栈的内容
        System.out.println("04");
        System.out.println("token_temp表");
        for (int i = 0; i < token_temp.size(); i++) {
            System.out.println(token_temp.get(i).species_code + " " + token_temp.get(i).attribute_code);
        }

        // 将操作栈op的顶部元素与操作数栈token_temp的顶部元素的属性码结合
        // 然后将结合后的结果压入操作符临时栈op_temp
        op_temp.push(op.peek() + token_temp.peek().attribute_code);

        // 将操作栈op的顶部元素弹出
        op.pop();
        // 将操作数栈token_temp的顶部元素弹出
        token_temp.pop();
    }

    // regular05方法，处理在表达式中的递增或递减操作
    public static void regular05() {
        // 打印“05”标志和token_temp栈的内容
        System.out.println("05");
        System.out.println("token_temp表");
        for (int i = 0; i < token_temp.size(); i++) {
            System.out.println(token_temp.get(i).species_code + " " + token_temp.get(i).attribute_code);
        }

        // 创建一个新的标签（即临时变量）来存储操作结果
        String left = new_label();
        // 查看token_temp栈顶的元素属性码，即变量名
        String right = token_temp.peek().attribute_code;
        // 弹出栈顶元素（因为该元素已经被处理）
        token_temp.pop();
        // 如果types映射表中不存在right作为键的条目，则认为right指的变量未定义，并记录错误信息
        if (types.get(right) == null) {
            Error_string += "Error!" + right + "变量未定义\n";
        }
        // 将left标识为与right相等的关系存储到address_three中
        address_three.put(address, left + "=" + right);
        // 将此关系以四元式的形式存入three_address
        three_address.put(address, new four("=", right, "", left));
        // 地址加1，为后续的递增或递减操作做准备
        address++;

        // 根据opdouble的值决定是执行递增还是递减操作
        if (opdouble.equals("++")) {
            // 如果是递增，则将right的值加1
            address_three.put(address, right + "=" + left + "+1");
            three_address.put(address, new four("+", left, "1", right));
            address++;
        } else {
            // 如果是递减，则将right的值减1
            address_three.put(address, right + "=" + left + "-1");
            three_address.put(address, new four("-", left, "1", right));
            address++;
        }
    }

    // regular06方法，处理自增或自减的中间代码生成逻辑
    public static void regular06() {
        // 打印出当前方法的标识
        System.out.println("06");
        System.out.println("token_temp表");

        // 遍历token_temp列表，打印出每个元素的种类码和属性码
        for (int i = 0; i < token_temp.size(); i++) {
            System.out.println(token_temp.get(i).species_code + " " + token_temp.get(i).attribute_code);
        }

        // 生成一个新的标签（可能是一个临时变量名）
        String left = new_label();
        // 获取token_temp栈顶的属性码作为右操作数，并将该元素从栈中移除
        String right = token_temp.peek().attribute_code;
        token_temp.pop();

        // 如果right变量未定义，则在错误字符串中记录错误信息
        if (types.get(right) == null) {
            Error_string += "Error!" + right + "变量未定义\n";
        }

        // 生成递增（++）或递减（--）的中间代码
        // 如果操作是自增，则生成自增的中间代码地址映射
        if (opdouble.equals("++")) {
            address_three.put(address, left + "=" + right + "+1");
            three_address.put(address, new four("+", right, "1", left));
        } else {
            // 否则，生成自减的中间代码地址映射
            address_three.put(address, left + "=" + right + "-1");
            three_address.put(address, new four("-", right, "1", left));
        }
        // 地址自增，为下一条中间代码做准备
        address++;

        // 生成right = left的中间代码，完成自增或自减的值赋值回原变量
        address_three.put(address, right + "=" + left);
        three_address.put(address, new four("=", left, "", right));
        // 地址再次自增
        address++;
    }

    // regular07方法，处理逻辑表达式，对应于语法结构：逻辑表达式 = '(' + F + 逻辑运算符 + F + ')'
    public static void regular07() {
        // 打印出当前方法标识
        System.out.println("07");
        System.out.println("token_temp表");

        // 遍历token_temp列表，打印出每个元素的种类码和属性码
        for (int i = 0; i < token_temp.size(); i++) {
            System.out.println(token_temp.get(i).species_code + " " + token_temp.get(i).attribute_code);
        }

        // 取出右操作数，并从token_temp栈中移除，并检查该操作数是否已定义
        String right = token_temp.peek().attribute_code;
        if (token_temp.peek().species_code.equals("id") && types.get(token_temp.peek().attribute_code) == null) {
            // 如果操作数未定义，则在错误字符串中记录错误信息
            Error_string += "Error!" + token_temp.peek().attribute_code + "变量未定义\n";
        }
        token_temp.pop();

        // 取出左操作数，并从token_temp栈中移除，并检查是否已定义
        String left = token_temp.peek().attribute_code;
        if (token_temp.peek().species_code.equals("id") && types.get(token_temp.peek().attribute_code) == null) {
            // 如果操作数未定义，则在错误字符串中记录错误信息
            Error_string += "Error!" + token_temp.peek().attribute_code + "变量未定义\n";
        }
        token_temp.pop();

        // 取出操作符，并从logicop栈中移除
        String logicop_temp = logicop.peek();
        logicop.pop();

        // 将新的逻辑表达式对象压入logic_string栈中
        logic_string.push(new logic(left, logicop_temp, right));
    }

    // regular08方法，处理if语句的中间代码生成
    public static void regular08() {
        // 打印出当前方法标识和token临时表的内容
        System.out.println("08");
        System.out.println("token_temp表");
        // 遍历token_temp列表，打印出每个元素的种类码和属性码
        for (int i = 0; i < token_temp.size(); i++) {
            System.out.println(token_temp.get(i).species_code + " " + token_temp.get(i).attribute_code);
        }

        // 打印出当前逻辑字符串
        System.out.println(logic_string);

        // 获取逻辑表达式字符串的顶部元素并转换为字符串，然后从栈中移除它
        logic logic_string_temp1 = logic_string.peek();
        String logic_string_temp = logic_string_temp1.toString();
        logic_string.pop();

        // 获取if_true栈的顶部整型值，并将其从栈中移除
        int if_true_temp = if_true.peek();
        if_true.pop();

        // 在address_three映射中添加新的代码行和跳转地址（条件为真时的跳转）
        address_three.put(address, "if " + logic_string_temp + " goto " + if_true_temp);
        // 在three_address映射中添加条件跳转的四元式
        three_address.put(address, new four(logic_string_temp1.op, logic_string_temp1.l, logic_string_temp1.r, Integer.toString(if_true_temp)));
        address++;

        // 添加一个无条件跳转到下一个地址的代码行
        address_three.put(address, "goto " + (address + 1));
        // 在three_address映射中添加无条件跳转的四元式
        three_address.put(address, new four("go", "", "", Integer.toString(address + 1)));
        address++;

        // 重新整理地址，如果地址为整数则递增地址
        // 并且为跳过已添加的if-true和goto语句更新中间代码的地址
        for (int i = address - 1; i > if_true_temp + 1; i--) {
            // 检查是不是整数地址，如果是就递增2
            if (three_address.get(i - 2).isInteger()) {
                three_address.get(i - 2).result = Integer.toString(Integer.parseInt(three_address.get(i - 2).result) + 2);
            }
            // 更新map里的地址映射和代码行
            address_three.replace(i, address_three.get(i - 2));
            three_address.replace(i, three_address.get(i - 2));
        }

        // 更新if_true_temp所对应的跳转地址
        address_three.replace(if_true_temp, "if " + logic_string_temp + " goto " + (if_true_temp + 2));
        three_address.replace(if_true_temp, new four(logic_string_temp1.op, logic_string_temp1.l, logic_string_temp1.r, Integer.toString(if_true_temp + 2)));
        // 更新下一个无条件跳转地址
        address_three.replace(if_true_temp + 1, "goto " + address);
        three_address.replace(if_true_temp + 1, new four("go", "", "", Integer.toString(address)));
    }

    public static void regular18() {
        // 从栈中取得当前的逻辑表达式并从栈中移除
        logic logic_string_temp = logic_string.peek();
        logic_string.pop();

        // 从栈中取得while循环的起始位置并从栈中移除
        int while_1_temp = while_1.peek();
        while_1.pop();

        // 添加一个无条件跳转到下一条指令的三地址码
        three_address.put(address, new four("go", "", "", Integer.toString(address + 1)));
        address++;

        // 添加当前逻辑表达式对应的三地址码，这可能是检查循环条件的指令
        three_address.put(address, new four(logic_string_temp.op, logic_string_temp.l, logic_string_temp.r, Integer.toString(while_1_temp)));
        address++;

        // 再次添加一个无条件跳转到下一条指令的三地址码
        three_address.put(address, new four("go", "", "", Integer.toString(address + 1)));
        address++;

        // 重新整理地址，这可能是调整跳转目标地址的操作
        for(int i = address - 1; i > while_1_temp + 1; i--) {
            if (three_address.get(i - 2).isInteger()) {
                three_address.get(i - 2).result = Integer.toString(Integer.parseInt(three_address.get(i - 2).result) + 2);
            }
            three_address.replace(i, three_address.get(i - 2));
        }

        // 更新之前添加的无条件跳转，使其能跳转回循环开始的位置
        three_address.replace(address - 1, new four("go", "", "", Integer.toString(while_1_temp)));

        // 更新循环开始处的三地址码，设置新的跳转目标地址
        three_address.replace(while_1_temp, new four(logic_string_temp.op, logic_string_temp.l, logic_string_temp.r, Integer.toString(while_1_temp + 2)));

        // 添加一个新的无条件跳转指令，跳转目标为循环结束后的代码
        three_address.replace(while_1_temp + 1, new four("go", "", "", Integer.toString(address)));
    }

    public static void regular19() {
        // 从栈顶取出逻辑表达式元素并立即移除
        logic logic_string_temp = logic_string.peek();
        logic_string.pop();

        // 从栈顶取出关联的while循环中的标记或位置，并立即移除
        int while_do_temp = while_do.peek();
        while_do.pop();

        // 将此逻辑表达式转换为四元组形式的三地址代码，并分配地址
        // 这里三地址代码的形式是为了在循环条件为真时跳转到循环体的开始位置
        three_address.put(address, new four(logic_string_temp.op, logic_string_temp.l, logic_string_temp.r, Integer.toString(while_do_temp)));
        address++;

        // 添加一个无条件跳转代码，可能指向循环末尾之后的第一个地址（通常跳出循环的地址）
        three_address.put(address, new four("go", "", "", Integer.toString(address + 1)));
        address++;
    }

    public static void regular20(){//Regular{left='ifelsesentence', right=[if, logic, {, complex, }, else, {, complex, }]}
        System.out.println("20");
        System.out.println("token_temp表");
        for (int i = 0; i <token_temp.size() ; i++) {
            System.out.println(token_temp.get(i).species_code+" "+token_temp.get(i).attribute_code);
        }
        System.out.println(logic_string);
        logic logic_string_temp1=logic_string.peek();
        String logic_string_temp=logic_string_temp1.toString();
        logic_string.pop();
        int if_true_temp=if_true.peek();
        if_true.pop();
        int if_false_temp=if_false.peek();
        if_false.pop();
        address_three.put(address,"if "+logic_string_temp+" goto "+if_true_temp);
        three_address.put(address,new four(logic_string_temp1.op,logic_string_temp1.l,logic_string_temp1.r,Integer.toString(if_true_temp)));
        address++;
        address_three.put(address,"goto "+if_false_temp);
        three_address.put(address,new four("go","","",Integer.toString(if_false_temp)));
        address++;

        //重新整理地址
        for(int i=address-1;i>if_true_temp+1;i--){
            if(three_address.get(i-2).isInteger()){
                three_address.get(i-2).result=Integer.toString(Integer.parseInt(three_address.get(i-2).result)+2);
            }
            address_three.replace(i,address_three.get(i-2));
            three_address.replace(i,three_address.get(i-2));
        }
        address_three.replace(if_true_temp,"if "+logic_string_temp+" goto "+(if_true_temp+2));
        three_address.replace(if_true_temp,new four(logic_string_temp1.op,logic_string_temp1.l,logic_string_temp1.r,Integer.toString(if_true_temp+2)));
        address_three.replace(if_true_temp+1,"goto "+(if_false_temp+2));
        three_address.replace(if_true_temp+1,new four("go","","",Integer.toString(if_false_temp+2)));

        //else里面的goto
        address_three.put(address,"goto "+(address+1));
        three_address.put(address,new four("go","","",Integer.toString(address+1)));
        address++;
        //再次调整地址位置
        for(int i=address-1;i>if_false_temp+2;i--){
            if(three_address.get(i-1).isInteger()){
                three_address.get(i-1).result=Integer.toString(Integer.parseInt(three_address.get(i-1).result)+1);
            }
            address_three.replace(i,address_three.get(i-1));
            three_address.replace(i,three_address.get(i-1));
        }
        address_three.replace(if_false_temp+2,"goto "+address);
        three_address.replace(if_false_temp+2,new four("go","","",Integer.toString(address)));
        address_three.replace(if_true_temp+1,"goto "+(if_false_temp+3));
        three_address.replace(if_true_temp+1,new four("go","","",Integer.toString(if_false_temp+3)));
    }
    public static void regular36(){
        flag_type_array = true; // 设置一个标志flag_type_array，指示当前正在处理的是一个数组类型
        String right = token_temp.peek().attribute_code; // 获取栈顶token的代码属性，这可能是数组名
        token_temp.pop(); // 弹出栈顶token
        String left = token_temp.peek().attribute_code; // 获取现在的栈顶token的代码属性，这可能是数组的类型
        token_temp.pop(); // 再次弹出栈顶token

        // 检查类型映射中是否已经存在right表示的变量
        if(types.get(right) != null){
            // 如果变量已经存在，添加错误信息到Error_string
            Error_string += "Error!" + right + " 变量已经定义!!!\n";
        } else {
            // 如果变量不存在，在类型映射中添加新条目，将right表示的变量名映射为其类型
            types.put(right, type_String);
        }

        // 打印当前所有类型信息，用于调试
        System.out.println(types);
    }

    public static void regular33(){
        flag_array = true; // 设置一个标记flag_array，表示接下来的处理涉及到数组
        String right = token_temp.peek().attribute_code; // 从栈顶取得最近的token，并获取其属性attribute_code
        token_temp.pop(); // 将这个token从栈中移除
        index_array = right; // 将获取到的属性值赋给变量index_array，这应该是数组的索引
    }

    public static void three_address_code(int r){//语句翻译
        // 根据不同的规则编号r选择不同的动作
        switch(r) {
            case 1:regular01();break; // 执行规则1的操作
            case 4:regular05();break; // 翻译类似{left='sentence', right=[opdouble, id, ;]}的结构
            case 5:regular06();break; // 翻译类似{left='sentence', right=[id, opdouble, ;]}的结构
            case 11:op.push("+");break; // 将加号操作符压栈（用于之后的表达式处理）
            case 12:op.push("-");break; // 将减号操作符压栈
            case 13:op.push("*");break; // 将乘号操作符压栈
            case 14:op.push("/");break; // 将除号操作符压栈
            case 15:opdouble="++";break; // 设置自增操作
            case 16:opdouble="--";break; // 设置自减操作
            case 18:regular18();break; // 翻译while循环结构{left='whilesentence', right=[while, logic, {, complex, }]}
            case 19:regular19();break; // 翻译do-while循环结构{left='whilesentence', right=[do, {, complex, }, while, logic, ;]}
            case 20:regular20();break; // 翻译if-else条件语句{left='ifelsesentence', right=[if, logic, {, complex, }, else, {, complex, }]}
            case 21:regular02();break; // 执行规则2的操作，处理赋值语句{left='assignment', right=[id, =, expression, ;]}
            case 25:regular08();break; // 翻译if条件语句{left='ifsentence', right=[if, logic, {, complex, }]}
            case 27:type_String="int";break; // 设置类型字符串为int
            case 28:type_String="double";break; // 设置类型字符串为double
            case 31:regular04();break; // 翻译表达式中的Flast部分{left='Flast', right=[op, F, Flast]}
            case 33:regular33();break; // 翻译数组的访问{left='Flast', right=[[, num, ]]}
            case 35:type_id=token_temp.peek();token_temp.pop();break; // 获取并移除栈顶的token，此处为句子复制右部分的id{left='sentencecopy', right=[id, ;]}
            case 36:regular36();// 翻译数组声明和初始化{left='sentencecopy', right=[[, ], id, =, new, type, [, num, ], ;]}
            case 38:regular03();break; // 翻译T部分{left='T', right=[F, Flast]}
            case 41:logicop.push("==");break; // 将等于操作符压栈（用于逻辑表达式）
            case 42:logicop.push(">=");break; // 将大于等于操作符压栈
            case 43:logicop.push("<=");break; // 将小于等于操作符压栈
            case 44:logicop.push(">");break; // 将大于操作符压栈
            case 45:logicop.push("<");break; // 将小于操作符压栈
            case 47:regular07();break; // 翻译逻辑表达式{left='logic', right=[(, F, logicop, F, )]}
        }
    }

}
