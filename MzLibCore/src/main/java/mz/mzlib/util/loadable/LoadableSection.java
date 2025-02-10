package mz.mzlib.util.loadable;

import java.util.Set;

public interface LoadableSection {
    String getName();
    Object getValue(String path);
    LoadableSection getSection(String path);
    String getStringValue(String path);
    Set<String> getKeys(boolean keys);
}
