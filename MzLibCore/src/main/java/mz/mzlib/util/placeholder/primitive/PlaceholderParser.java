package mz.mzlib.util.placeholder.primitive;

import java.lang.reflect.Method;
import java.util.Arrays;

public interface PlaceholderParser<T> {
    String getName();
    default String parse(String[] s, T tiered){
        String parsed = defaultParse(tiered);
        try {
            for (Method method : getClass().getDeclaredMethods()) {
                PlaceholderParseable placeholderParseable = method.getAnnotation(PlaceholderParseable.class);
                if (placeholderParseable == null) {
                    continue;
                }
                if (s == null || s.length == 0) {
                    return defaultParse(tiered);
                }
                if (placeholderParseable.params().length != 0) {
                    String placeholder = getName() + Arrays.stream(placeholderParseable.params()).reduce((a, b) -> a + ":" + b).orElse("");
                    String placeholdering = getName() + Arrays.stream(s).reduce((a, b) -> a + ":" + b).orElse("");
                    if (!placeholdering.startsWith(placeholder)) {
                        continue;
                    }
                }
                Class<?> product = method.getReturnType();
                if (SubPlaceholder.class.isAssignableFrom(product)) {
                    SubPlaceholder<T, ?> object = (SubPlaceholder<T, ?>) method.invoke(this, s, tiered);
                    return object.parse(PlaceholderUtil.deleteStrings(s,Math.max(1,placeholderParseable.params().length)), tiered);
                }
                parsed = (String) method.invoke(this, s, tiered);
            }
        }catch (Exception e){
            System.out.println("其某处变量不可解析罢:)");
            e.printStackTrace();
        }
        return parsed;
    }
    default String defaultParse(T tierred){
        return "";
    }
}
