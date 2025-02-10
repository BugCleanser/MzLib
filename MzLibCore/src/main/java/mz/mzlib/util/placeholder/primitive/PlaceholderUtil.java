package mz.mzlib.util.placeholder.primitive;

import java.util.Arrays;

public class PlaceholderUtil {
    public static String[] deleteFirstStrings(String[] strings) {
        return Arrays.stream(strings).skip(1).toArray(String[]::new);
    }

    public static String[] deleteStrings(String[] strings,int amount) {
        return Arrays.stream(strings).skip(amount).toArray(String[]::new);
    }
}
