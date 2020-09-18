package vn.com.mta.science.util;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class CollectionUtils {

    private static final String SPLIT_PATTERN = ",";

    public static Set<String> toSet(String str) {
        if (str == null) return null;

        String[] values = str.split(SPLIT_PATTERN);
        Set<String> result = new TreeSet<>();
        for (String v : values) if (!v.isEmpty()) result.add(v);
        return result.isEmpty() ? null : result;
    }

    public static String toString(Collection<String> set) {
        if (set == null || set.isEmpty()) return null;

        StringBuilder builder = new StringBuilder();
        for (String str : set) if (!str.isEmpty()) builder.append(SPLIT_PATTERN).append(str);
        return builder.length() > 0 ? builder.toString().substring(1) : null;
    }

}

