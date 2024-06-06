package grammar.LR;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author lzh
 * @date 2022/5/21 0021
 */

//项
public class Item {
    public String the_left; //产生式左边

    public ArrayList<String> the_right; //产生式右边

    public int index;//  小圆点所在处

    public HashSet<String> Prospect=new HashSet<>(); //期望符

    public Item(String the_right, ArrayList<String> the_left, int index) {
        this.the_left = the_right;
        this.the_right = the_left;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Item{" +
                "the_left='" + the_left + '\'' +
                ", the_right=" + the_right +
                ", index=" + index +
                ", Prospect=" + Prospect +
                '}';
    }

    public boolean iseuqal(Item temp){
        boolean flag=false;
        if(temp.index==this.index&&temp.the_left.equals(this.the_left)&&temp.the_right_iseuqal(this.the_right)&&temp.prospect_iseuqal(this.Prospect)){
            flag=true;
        }
        return flag;
    }

    public boolean the_right_iseuqal(ArrayList<String> temp){
        if(this.the_right.size()!=temp.size()){
            return false;
        }
        for (int i = 0; i < this.the_right.size(); i++) {
            if(!this.the_right.get(i).equals(temp.get(i))){
                return false;
            }
        }
        return true;
    }
    public boolean prospect_iseuqal(HashSet<String> temp){
        if(this.Prospect.size()!=temp.size()){
            return false;
        }
        for(String s:this.Prospect){
            boolean flag=false;
            for(String s2:temp){
                if(s.equals(s2)){
                    flag=true;
                }
            }
            if(!flag){
                return false;
            }
        }
        return true;
    }

}
