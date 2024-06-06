package lexical.Lin;

import lexical.LetterState.LetterState;
import lexical.NumState.DigitState;
import lexical.OpState.*;
import lexical.OtherState.*;

import static lexical.MapList.maplist.error_list;

/**
 * @author lzh
 * @date 2022/5/7 0007
 */
public class Lexical_analysis {
   public static char[] Code;
   public static void StartLexicalAnalysis(String s,int line){
      if (IsCode(s)) {//判断
         System.out.println("当前输入符号为：" + s);
         System.out.println("词法分析结果如下：");
         StartState(line, 0);
      }
   }
   private static boolean IsCode(String s){
      if (s.isEmpty()||s.toCharArray()[0]=='\n') {
         System.out.println("当前原代码为空");
         return false;
      }
      else {
         s=s.trim();
         Code = s.toCharArray();
         return true;
      }
   }
   public static void StartState(int line, int index){
      if(index>=Code.length){
         System.out.println("词法分析结束");
      }else if(Code[index]=='=') {
         EuqalState.euqalState(line,index);
      }else if(Code[index]=='+'){
         PlusState.plusState(line,index);
      }else if(Code[index]=='-'){
         ReduceState.reduceState(line,index);
      }else if(Code[index]=='*'){
         MulState.mulState(line,index);
      }else if(Code[index]=='/'){
         DivideState.divideState(line,index);
      }else if(Code[index]=='>') {
         Greater_thanState.greater_thanState(line,index);
      }else if(Code[index]=='<') {
         Less_thanState.less_thanState(line,index);
      }else if(Code[index]=='('){
         LparenthesisState.lparenthesisState(line,index);
      }else if(Code[index]==')') {
         RparenthesisState.rparenthesisState(line,index);
      }else if(Code[index]=='{'){
         LeftbraceState.leftbraceState(line,index);
      }else if(Code[index]=='}') {
         RightbraceState.rightbraceState(line,index);
      }else if(Code[index]=='['){
         LsquarebracketState.lsquarebracketState(line,index);
      }else if(Code[index]==']') {
         RsquarebracketState.rsquarebracketState(line,index);
      }else if(Code[index]==';'){
         SemicolonState.semicolonState(line,index);
      }else if(Code[index]==',') {
         CommaState.commaState(line,index);
      }else if(Code[index]=='.') {
         SpotState.spotState(line,index);
      }else if(Code[index]==':') {
         ColonState.colonState(line,index);
      }else if(Character.isLetter(Code[index])){
         LetterState.letterState(line,index);
      }else if(Character.isDigit(Code[index])){
         DigitState.digitState(line,index);
      }else if(Code[index]=='"'){
         QuotationState.quotationState(line,index);
      }else if(Code[index]=='\''){
         Single_QuotationState.single_quotationState(line,index);
      }else if(Code[index]==' '){
         StartState(line,index+1);
      }else{
         error_list.add("error in line " +line+":"+Code[index]+"为非法字符");
         StartState(line,index+1);
      }
   }
}
