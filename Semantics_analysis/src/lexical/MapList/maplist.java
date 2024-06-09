package lexical.MapList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author lzh
 * @date 2022/5/7 0007
 */
public class maplist {
    public static int idnum=300;
    //运算符,关键字,分隔符
    public static HashMap<String,String> lexical_list=new HashMap<>();
    //动态表
    public static HashMap<String,String> id_list=new HashMap<>();
    //token表
    public static ArrayList<Token> tokens_list=new ArrayList<>();

    public static ArrayList<String> error_list=new ArrayList<>();
    public static void init(){

        lexical_list.put("integer","integer");
        lexical_list.put("elsepart","elsepart");
        lexical_list.put("function","function");
        lexical_list.put("id","id");
        lexical_list.put("ɛ","ɛ");
        lexical_list.put("[","[");
        lexical_list.put("]","]");
        lexical_list.put("IF","IF");
        lexical_list.put("(","(");
        lexical_list.put("ENDIF","ENDIF");
        lexical_list.put(")",")");
        lexical_list.put("basestmt","basestmt");
        lexical_list.put("+","+");
        lexical_list.put(",",",");
        lexical_list.put("0","0");
        lexical_list.put("1","1");
        lexical_list.put("2","2");
        lexical_list.put("INTLITERAL","INTLITERAL");
        lexical_list.put(";",";");
        lexical_list.put("=","=");
//        lexical_list.put("basestmt","basestmt");
//        lexical_list.put("basestmt","basestmt");
//
//
//
//        lexical_list.put("break","break");
//        lexical_list.put("continue","continue");
//        lexical_list.put("int","int");
//        lexical_list.put("double","double");
//        lexical_list.put("float","float");
//        lexical_list.put("unsigned","unsigned");
//        lexical_list.put("return","return");
//        lexical_list.put("goto","goto");
//        lexical_list.put("then","then");
//        lexical_list.put("static","static");
//        lexical_list.put("new","new");
//        lexical_list.put("case","case");
//        lexical_list.put("switch","switch");
//        lexical_list.put("default","default");
//        lexical_list.put("true","true");
//        lexical_list.put("false","false");
//        lexical_list.put("bool","bool");
//        lexical_list.put("public","public");
//        lexical_list.put("private","private");
//        lexical_list.put("protected","protected");
//        lexical_list.put("Main","Main");
//        lexical_list.put("main","main");
//        lexical_list.put("import","import");
//        lexical_list.put("class","class");
//        lexical_list.put("String","String");
//
//        lexical_list.put("Double","Double");
//        lexical_list.put("void","void");
//        lexical_list.put("for","for");
//        lexical_list.put("char","0");
//        lexical_list.put("include","include");
//        lexical_list.put("do","do");
//        lexical_list.put(".","33");
//        lexical_list.put(",","34");
//        lexical_list.put(";",";");
//        lexical_list.put("(","(");
//        lexical_list.put(")",")");
//        lexical_list.put("{","{");
//        lexical_list.put("}","}");
//        lexical_list.put("[","[");
//        lexical_list.put("]","]");
//        lexical_list.put("'","'");
//        lexical_list.put("\"","43");
//        lexical_list.put("=","=");
//        lexical_list.put("==","==");
//        lexical_list.put("+","+");
//        lexical_list.put("++","++");
//        lexical_list.put("+=","48");
//        lexical_list.put("-","-");
//        lexical_list.put("-=","50");
//        lexical_list.put("--","--");
//        lexical_list.put("*","*");
//        lexical_list.put("*=","53");
//        lexical_list.put("/","/");
//        lexical_list.put("/=","55");
//        lexical_list.put(">",">");
//        lexical_list.put(">=",">=");
//        lexical_list.put("<=","<=");
//        lexical_list.put("<","<");
//        lexical_list.put(":",":");
    }
}
