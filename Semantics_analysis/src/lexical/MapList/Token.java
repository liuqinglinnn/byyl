package lexical.MapList;

/**
 * @author lzh
 * @date 2022/5/14 0014
 */
public class Token {
    public String species_code;//种别码
    public String attribute_code;//属性值
    public Token(String species_code, String attribute_code) {
        this.species_code = species_code;
        this.attribute_code = attribute_code;
    }

    @Override
    public String toString() {
        return species_code;
    }
}
