package lexical.OtherState;

import lexical.MapList.Token;

import static lexical.Lin.Lexical_analysis.Code;
import static lexical.Lin.Lexical_analysis.StartState;
import static lexical.MapList.maplist.error_list;
import static lexical.MapList.maplist.tokens_list;
import static lexical.tool.Tool1.CodetoString;

/**
 * @author lzh
 * @date 2022/5/14 0014
 */

//引号
public class Single_QuotationState {
    // '
    public static void single_quotationState(int line,int index){
        int i=1;
        while (i+index< Code.length &&Code[i+index]!='\''){
            i++;
        }
        if(i+index==Code.length){
            error_list.add("error in line " +line+":"+"字符类型只遇到一个单引号");
            StartState(line,index+1);
        }else{
            tokens_list.add(new Token("char",CodetoString(Code,index+1,index+i-1)));
            StartState(line,index+i+1);
        }

    }
}
