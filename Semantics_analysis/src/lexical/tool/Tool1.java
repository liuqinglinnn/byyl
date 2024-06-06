package lexical.tool;

import grammar.LR.Regular;

import java.util.Map;

import static grammar.LR.Create_Itemsets.itemsets;
import static grammar.Runing.Grammar_Analysis.*;
import static lexical.MapList.maplist.*;
import static grammar.LR.LR.*;
import static semantics.Semantics_analysis.*;

/**
 * @author lzh
 * @date 2022/5/14 0014
 */
public class Tool1 {
    public static String CodetoString(char[] Code,int begin,int end){
        String s="";
        for(int i=begin;i<=end;i++){
            s+=Character.toString(Code[i]);
        }
        return s;
    }
    public static String string_result01(){
        String temp="静态表\n";
        for (int i = 0; i < tokens_list.size(); i++) {
            temp+="<" + tokens_list.get(i).species_code + "," + tokens_list.get(i).attribute_code + ">\n";
        }
        if(!error_list.isEmpty()){
            temp+="\n\n\n\n错误信息\n";
            for (int i = 0;i <error_list.size() ; i++) {
                temp+=error_list.get(i)+"\n";
            }
        }
        if(!id_list.isEmpty()){
            temp+="\n\n\n\n动态表\n";
            for (Map.Entry<String, String> entry : id_list.entrySet()) {
                temp+=entry.getValue() + " " + entry.getKey()+"\n";
            }
        }
        return temp;
    }
    public static String string_result02(){
        String temp="";
        temp+="终结符\n";
        for(Map.Entry<String,String> entry:end.entrySet()){
            temp+=entry.getKey()+"   ";
        }
        temp+="\n非终结符\n";
        for(Map.Entry<String,String> entry:non_end.entrySet()){
            temp+=entry.getKey()+"   ";
        }
        temp+="\n产生式\n";
        for(Map.Entry<Integer, Regular> entry:int_to_regular.entrySet()){
            temp+="规则"+entry.getKey()+"\n";
            temp+=entry.getValue()+"\n";
        }
        temp+="项集族\n";
        for(int i=0;i<itemsets.size();i++){
            temp+="项集"+i +"\n";
            temp+=itemsets.get(i).toString()+"\n";
        }
        if(!error_list.isEmpty()){
            temp+="\n\n\n\n错误信息\n";
            for (int i = 0;i <error_list.size() ; i++) {
                temp+=error_list.get(i)+"\n";
            }
        }
        for (int i = 0; i <result_save.size() ; i++) {
            temp+=result_save.get(i)+"\n";
        }
        temp+=three;
        return temp;
    }
    public static void clear_all(){
        tokens_list.clear();
        id_list.clear();
        error_list.clear();
        idnum=300;

        //语法分析
        result_save.clear();
        state.clear();
        input.clear();
        token.clear();
        action="";
        wrong_id=0;
        max_wrong=10;
        step=1;

        //语义分析
        token_temp.clear();
        op_temp.clear();
        logicop.clear();
        logic_string.clear();
        if_true.clear();
        if_false.clear();
        while_1.clear();
        while_do.clear();
        op.clear();;
        address_three.clear();
        three_address.clear();
        types.clear();
        address=0;
        t_number=0;
        three="";
        opdouble="";
        flag_array=false;
        index_array="";
        type_String="";
        Error_string="";
        flag_type_array=false;
    }
}
