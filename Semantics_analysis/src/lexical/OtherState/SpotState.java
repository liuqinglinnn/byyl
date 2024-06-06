package lexical.OtherState;

import lexical.MapList.Token;

import static lexical.Lin.Lexical_analysis.StartState;
import static lexical.MapList.maplist.lexical_list;
import static lexical.MapList.maplist.tokens_list;

/**
 * @author lzh
 * @date 2022/5/7 0007
 */

//小数点,点
public class SpotState {
    public static void spotState(int line,int index){
        tokens_list.add(new Token(lexical_list.get("."),"."));
        StartState(line,index+1);
    }
}
