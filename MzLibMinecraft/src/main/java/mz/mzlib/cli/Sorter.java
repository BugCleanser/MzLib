package mz.mzlib.cli;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Sorter<K,LV extends Set<V>,V> {
    protected Map<K,LV> map = new HashMap<>();

    public void addMap(K k,V v){
        LV lv = map.get(k);
        if(lv==null){
            lv = newSet();
        }
        lv.add(v);
        map.put(k,lv);
    }

    public abstract LV newSet();
}
