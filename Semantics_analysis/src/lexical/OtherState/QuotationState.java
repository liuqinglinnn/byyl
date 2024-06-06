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
public class QuotationState {
    public static void quotationState(int line,int index){
        // "
        int i=1;
        while (i+index< Code.length &&Code[i+index]!='"'){
            i++;
        }
        // 没有遇到下一个"
        if(i+index==Code.length){
            error_list.add("error in line " +line+":"+"字符串类型只遇到一个引号");
            StartState(line,index+1);
        }else{ //  是string
            tokens_list.add(new Token("string",CodetoString(Code,index+1,index+i-1)));
            StartState(line,index+i+1);
        }

    }
}
