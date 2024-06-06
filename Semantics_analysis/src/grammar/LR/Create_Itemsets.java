package grammar.LR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static grammar.LR.LR.*;

/**
 * @author lzh
 * @date 2022/5/21 0021
 */
public class Create_Itemsets {

    static int endstate;
    public static HashMap<String,String> lr1_list=new HashMap<>();
    static int priority = -1;//优先级
    ArrayList<String> left = new ArrayList<String>();//产生式左部规则
    HashMap<String,String> already = new HashMap<String,String>();//已经出现过的产生式左部(用于Add)
    HashMap<String,Regular> repeat = new HashMap<String,Regular>();//项集当前已有规则
    public static HashMap<Create_Itemsets,String> repetition = new HashMap<Create_Itemsets,String>();//判断重复
    public static ArrayList<Itemset> itemsets= new ArrayList<>();

    //得到项集可以到下一个状态的,字符
    public static HashSet<String> get_transfers(int state)
    {
//        System.out.println(state+"Hashset");
        HashSet<String> transfers = new HashSet();//存放输入的字符
        HashSet<Item> itemset_temp=itemsets.get(state).itemset;
        for (Item temp:itemset_temp) {
            if(temp.index==temp.the_right.size()){
                continue;
            }else {
                transfers.add(temp.the_right.get(temp.index));
            }
        }
       return transfers;
    }

    //小圆点在非终结符左边,扩充项集族
    public static void state_add_point(String end_temp,int state,Item temp){
        HashSet<String> temp_HashSet=new HashSet<>();
        if(temp!=null){
            ArrayList<String> temp_Arraylist=new ArrayList<>();
            for (int i = temp.index+1; i <temp.the_right.size() ; i++) {
                temp_Arraylist.add(temp.the_right.get(i));
            }
            temp_HashSet=first(temp_Arraylist);
        }
        for(int i=1;i<=regular.get(end_temp).list.size();i++){
//            itemsets.get(state).itemset.add(new Item(end_temp,regular.get(end_temp).list.get(i),0));
            Item temp_item=new Item(end_temp,regular.get(end_temp).list.get(i),0);
            if(temp!=null){
                if(temp_HashSet.size()!=0){
                    for(String s:temp_HashSet){//加入展望符
                        temp_item.Prospect.add(s);
                    }
                }else{
                    for(String s:temp.Prospect){
                        temp_item.Prospect.add(s);
                    }
                }

            }
            itemsets.get(state).itemset.add(temp_item);
        }

    }

    //展开项集,即扩展CLOSURE(I)
    public static void move(int state){
        if(state==0){
            endstate=0;
            itemsets.add(new Itemset());
            state_add_point(start,state,null);
            for(Item temp:itemsets.get(state).itemset){
                temp.Prospect.add("$");
            }
        }
        int now_i=0;
        boolean flag=true;//是否加入新项
        HashMap<String,String> repeat=new HashMap<>();

        while(flag){
            flag=false;
            for(Item temp:itemsets.get(state).itemset){
                ArrayList<String> the_right_temp=temp.the_right;
                int index_temp=temp.index;
                if (the_right_temp.size()==index_temp){
                    //存入归约操作
                    for(String temp_prospect:temp.Prospect){
                        String state_input=Integer.toString(state)+temp_prospect;
                        Regular operate=new Regular(temp.the_left,the_right_temp);
                        itemsets.get(state).action_R.put(state_input,operate);
                    }
                    continue;
                }else{
                    //小圆点在非终结符左边,扩充项集族
                    if (non_end.get(the_right_temp.get(index_temp))!=null){
                        if(repeat.get(the_right_temp.get(index_temp))==null){
                            repeat.put(the_right_temp.get(index_temp),"yes");
                            state_add_point(the_right_temp.get(index_temp),state,temp);
                            flag=true;
                            break;
                        }else{
                            //再次遇到小圆点在已经扩展的非终结符左边,扩充展望符
                            ArrayList<String> temp_Arraylist=new ArrayList<>();
                            for (int i = temp.index+1; i <temp.the_right.size() ; i++) {
                                temp_Arraylist.add(temp.the_right.get(i));
                            }
                            HashSet<String> temp_HashSet=new HashSet<>();
                            temp_HashSet=first(temp_Arraylist);
                            for(Item temp_temp:itemsets.get(state).itemset){
                                if(temp.the_right.get(index_temp).equals(temp_temp.the_left)&&temp.index==0){
                                    for(String temp_prospect:temp_HashSet){
                                        temp_temp.Prospect.add(temp_prospect);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        itemsets.get(state).ismove=true;
        boolean flag2=true;
        int true_state=state;
        //判断项集是否出现过
        for(int j=0;j<state;j++){
            if(itemsets.get(j).iseuqal(itemsets.get(state))){
                true_state=j;
                flag2=false;
                break;
            }
        }
        //加入移入操作
        for(Map.Entry<String, String> entry :itemsets.get(state).action.entrySet()){
            lr1_list.put(entry.getKey(),entry.getValue()+Integer.toString(true_state));
        }
        if(flag2){
            //加入归约操作
            for(Map.Entry<String, Regular> entry :itemsets.get(state).action_R.entrySet()){
                Integer temp=-1;
                for(Map.Entry<Integer, Regular> entry2:int_to_regular.entrySet()){
                    if (entry2.getValue().isequal(entry.getValue())){
                        temp=entry2.getKey();
                    }
                }
                if(entry.getValue().left.equals(start))
                    lr1_list.put(entry.getKey(),"acc"+"R"+Integer.toString(temp));
                else
                    lr1_list.put(entry.getKey(),"R"+Integer.toString(temp));

            }
            System.out.println(state);
            System.out.println(itemsets.get(state));
        }else{
            //项集已经出现过,删除
            itemsets.remove(state);
            endstate--;
        }
        run_lr();
    }

    //项集到下一个项集
    public static void next_state(int state){
        HashSet<Item> itemset_temp=itemsets.get(state).itemset;
        HashSet<String> transfers=get_transfers(state);
//        System.out.println(transfers);
        endstate++;
        itemsets.add(new Itemset());
        int sum=0;
        for(String s:transfers){
            boolean flag=false;
            for (Item temp:itemset_temp) {
                if(temp.the_right.size()==temp.index){
                    continue;
                }else{
                    if(temp.the_right.get(temp.index).equals(s)){
                        //存入移入操作
                        String state_input=Integer.toString(state)+s;
                        String operate="";
                        if(end.get(s)!=null){
                            operate="S";
                        }
                        itemsets.get(endstate).action.put(state_input,operate);
//                        if(lr1_list.get(state_input)==null){
//                            lr1_list.put(state_input,operate);
//                        }
                        Item temp_item=new Item(temp.the_left,temp.the_right,temp.index+1);
//                        itemsets.get(endstate).itemset.add(new Item(temp.the_left,temp.the_right,temp.index+1));
                        for(String temp_prospect:temp.Prospect){
                            temp_item.Prospect.add(temp_prospect);
                        }
                        itemsets.get(endstate).itemset.add(temp_item);
                        flag=true;
                    }
                }
            }
            if(flag){
                sum++;
                endstate++;
                itemsets.add(new Itemset());
            }
        }
        itemsets.remove(endstate);
        endstate--;
        itemsets.get(state).isnext=true;
        run_lr();
    }

    public static int ismove_index=0; //已经扩展项集的下标
    public static int isnext_index=0; //已经移入到下一项集的下标


    public static void run_lr(){
        for(int i=ismove_index;i<=endstate;i++){
            if(itemsets.get(i).ismove){
                ismove_index++;
            }else{
                move(i);
                return;
            }
        }
        for(int i=isnext_index;i<=endstate;i++){
            if(itemsets.get(i).isnext){
                isnext_index++;
            }else{
                next_state(i);
                return;
            }
        }
    }

}
