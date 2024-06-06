package lexical.NumState;

import lexical.MapList.Token;
import lexical.tool.Tool1;

import static lexical.Lin.Lexical_analysis.Code;
import static lexical.Lin.Lexical_analysis.StartState;
import static lexical.MapList.maplist.tokens_list;

/**
 * @author lzh
 * @date 2022/5/7 0007
 */
//数字开头
public class DigitState {
     public static void digitState(int line,int index){
         int i=1;
         boolean flag=false;//判断是不是小数
         for(i=1;i+index<Code.length;i++){
             //遇到数字继续
             if(Character.isDigit(Code[i+index])){
                 continue;
             }else if(Code[i+index]=='.'&&!flag&&i+1+index<Code.length&&Character.isDigit(Code[i+1+index])){
                 flag=true;
                 //出现.  ,且. 后面还是数字
                 continue;
             }else{
                 //不是数字或小数点已经出现过
                 if(flag)
                     tokens_list.add(new Token("real",Tool1.CodetoString(Code,index,index+i-1)));
                 else
                     tokens_list.add(new Token("num",Tool1.CodetoString(Code,index,index+i-1)));
                 StartState(line,index+i);
                 return;
             }
         }
         //到最后一个字符
         if(flag)
             tokens_list.add(new Token("real",Tool1.CodetoString(Code,index,Code.length-1)));
         else
             tokens_list.add(new Token("num",Tool1.CodetoString(Code,index,Code.length-1)));
    }
}
