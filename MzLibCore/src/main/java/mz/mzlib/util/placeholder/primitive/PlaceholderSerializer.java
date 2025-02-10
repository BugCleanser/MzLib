package mz.mzlib.util.placeholder.primitive;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderSerializer<T> {
    protected String origin;

    public PlaceholderSerializer(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public List<String> searchPlaceholders(){
        Pattern pattern = Pattern.compile("%.*?%");
        Matcher matcher = pattern.matcher(origin);
        List<String> result = new ArrayList<>();
        for(int var1=0;matcher.find();var1++){
            result.add(matcher.group());
        }
        return result;
    }

    public String replace(PlaceholderParser<T> parser,T t) throws InvocationTargetException, IllegalAccessException {
        String ss = origin+"";
        for(String s: searchPlaceholders()){
            ss = ss.replace(s,placeholderred(s,parser,t));
        }
        return ss;
    }

    public static <T>String placeholderred(String origin,PlaceholderParser<T> parser,T t) throws InvocationTargetException, IllegalAccessException {
        String ss = origin.substring(0,origin.length()-1).replace("%","");
        String[] sss = ss.split(":");
        return parser.parse(sss,t);
    }

    public static String deleteCharacters(String origin){
        String ss = origin.substring(0,origin.length()-1).replace("%","");
        return ss;
    }
}
