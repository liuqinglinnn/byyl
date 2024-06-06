package grammar.LR;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author lzh
 * @date 2022/5/21 0021
 */



//项集
public class Itemset {
    HashSet<Item> itemset=new HashSet();
    boolean ismove=false;  //已经算完项集族
    boolean isnext=false;  //已经转到下一个状态

    HashMap<String,String> action=new HashMap<>();//项集族拥有的移入操作
    HashMap<String,Regular> action_R=new HashMap<>();//项集族拥有的归约操作

    @Override
    public String toString() {
        return "Itemset{" +
                "itemset=" + itemset +
                '}';
    }
    public boolean iseuqal(Itemset temp){
        if(this.itemset.size()!=temp.itemset.size()){
            return false;
        }
        for(Item item_temp1:this.itemset){
            boolean flag=false;
            for(Item item_temp2:temp.itemset){
                if(item_temp1.iseuqal(item_temp2)){
                    flag= true;
                    break;
                }
            }
            if(!flag){
                return false;
            }
        }
        return true;
    }

}
