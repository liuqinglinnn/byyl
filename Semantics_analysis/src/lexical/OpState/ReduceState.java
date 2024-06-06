package lexical.OpState;

import lexical.MapList.Token;

import static lexical.Lin.Lexical_analysis.Code;
import static lexical.Lin.Lexical_analysis.StartState;
import static lexical.MapList.maplist.lexical_list;
import static lexical.MapList.maplist.tokens_list;

/**
 * @author lzh
 * @date 2022/5/7 0007
 */

//减号处理
public class ReduceState {
    public static void reduceState(int line,int index){
        if(index+1<Code.length){
            //  --
            if(Code[index+1]=='-'){
                tokens_list.add(new Token(lexical_list.get("--"),"--"));
                StartState(line,index+1+1);
                return;
                //  -=
            }else if(Code[index+1]=='='){
                tokens_list.add(new Token(lexical_list.get("-="),"-="));
                StartState(line,index+1+1);
                return;
            }
        }
        // -
        tokens_list.add(new Token(lexical_list.get("-"),"-"));
        StartState(line,index+1);
    }
}
