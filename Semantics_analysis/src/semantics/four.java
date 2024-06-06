package semantics;

import java.util.regex.Pattern;

/**
 * 类 four 用于表示一个四元组对象。
 */
public class four {
    String op;     // 操作符
    String arg1;   // 第一个操作数
    String arg2;   // 第二个操作数
    String result; // 结果或跳转目标

    /**
     * 四元组的构造器(Constructor)。
     * @param op 对应于操作符
     * @param arg1 对应于第一个操作数
     * @param arg2 对应于第二个操作数
     * @param result 对应于结果或跳转目标
     */
    public four(String op, String arg1, String arg2, String result) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }

    /**
     * 方法 isInteger 检测结果字段是否为整数。
     * @return 返回一个布尔值，表示结果字段是否仅包含数字。
     */
    public  boolean isInteger() {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); // 定义一个正则表达式，匹配整数
        return pattern.matcher(this.result).matches(); // 检查 result 字段是否匹配正则表达式
    }

    /**
     * toString 方法用于返回四元组的字符串表示形式。
     * @return 返回四元组的字符串形式。
     */
    @Override
    public String toString() {
        // 如果操作符是算术操作符
        if(op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")){
            return result+" = "+arg1+" "+op +" "+arg2+"\n";
        }
        // 如果操作符是关系操作符
        else if(op.equals(">")||op.equals(">=")||op.equals("<")||op.equals("<=")||op.equals("==")){
            return " if "+arg1+op+arg2+" goto "+result+"\n";
        }
        // 如果操作符是跳转(go)
        else if(op.equals("go")){
            return  " goto "+result+"\n";
        }
        // 如果操作符是数组访问
        else if(op.equals("=[]")){
            return result+" = "+arg1+" [ "+arg2+" ]"+"\n";
        }
        // 对于所有其他操作符
        return result+" = "+arg1+"\n";
    }
}
