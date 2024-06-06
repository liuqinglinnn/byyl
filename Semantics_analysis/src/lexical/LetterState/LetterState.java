package lexical.LetterState;

import lexical.MapList.Token;

import static lexical.Lin.Lexical_analysis.Code;
import static lexical.Lin.Lexical_analysis.StartState;
import static lexical.MapList.maplist.*;
import static lexical.tool.Tool1.CodetoString;

/**
 * @author lzh
 * @date 2022/5/7 0007
 */

//字母的判断
public class LetterState {
    public static void letterState(int line,int index){
        int i=1;
        //是字母或数字或者_则继续
        while(i+index< Code.length&&(Character.isDigit(Code[index+i])||Character.isLetter(Code[index+i])||Code[index+i]=='_')){
            i++;
        }
        //不是字母或数字，退出

        //得到的由字母和数字组成的字符串
        String temp=CodetoString(Code,index,index+i-1);
        if(temp.length()>=32) {
            //长度大于32,标识符长度不能大于32,报错
            error_list.add("error in line "+line+":"+temp+"变量长度不能大于等于32");
        }else{
            //判断是否为关键字
            if(lexical_list.get(temp)!=null)
                tokens_list.add(new Token(lexical_list.get(temp),temp));
            else{
                tokens_list.add(new Token("id",temp));
                //判断是否在动态表已经存在
//                if(id_list.get(temp)!=null){
//                    tokens_list.add(new Token("id:"+id_list.get(temp),temp));
//                }else{
//                    //不存在,加入动态表
//                    id_list.put(temp,Integer.toString(idnum));
//                    tokens_list.add(new Token("id:"+Integer.toString(idnum),temp));
//                    idnum++;
//                }
            }
        }

        StartState(line,index+i);
    }
}
