package semantics;

/**
 * 逻辑类 logic 表示含有逻辑运算符的逻辑表达式。
 */
public class logic {
    String l;   // 表达式的左侧部分
    String op;  // 逻辑操作符，如 "&&"（与）、"||"（或）、"!"（非）
    String r;   // 表达式的右侧部分

    /**
     * 构造函数，用于创建逻辑表达式的实例。
     *
     * @param l   表达式左侧部分的字符串表示
     * @param op  表达式中的逻辑运算符
     * @param r   表达式右侧部分的字符串表示
     */
    public logic(String l, String op, String r) {
        this.l = l;
        this.op = op;
        this.r = r;
    }

    /**
     * toString 方法返回这个逻辑表达式的字符串表示形式。
     *
     * @return 返回逻辑表达式的标准格式：左侧表达式 + 逻辑运算符 + 右侧表达式。
     */
    @Override
    public String toString() {
        return l + op + r; // 生成并返回表达式的字符串形式
    }
}
