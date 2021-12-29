package com.cddx.common.core.utils.thread;

import java.util.*;

/**
 *
 *
 * @author liliang
 * @date 2020/12/16 15:544
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new HashMap<>(4));

    public static Map<String, Object> getThreadLocal(){
        return THREAD_LOCAL.get();
    }

    public static <V> V get(String key) {
        Map<String, Object> map = getThreadLocal();
        return (V) map.get(key);
    }

    public static <V> V get(String key,V defaultValue) {
        Map map = getThreadLocal();
        return (V)map.get(key) == null ? defaultValue : (V)map.get(key);
    }

    public static <K,V> void set(K key, V value) {
        Map map = getThreadLocal();
        map.put(key, value);
    }

    public static <K,V> void set(Map<K, V> keyValueMap) {
        Map map = getThreadLocal();
        map.putAll(keyValueMap);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static <T> Map<String,T> fetchVarsByPrefix(String prefix) {
        Map<String,T> vars = new HashMap<>();
        if( prefix == null ){
            return vars;
        }
        Map map = getThreadLocal();
        Set<Map.Entry> set = map.entrySet();

        for( Map.Entry entry : set ){
            Object key = entry.getKey();
            if( key instanceof String ){
                if( ((String) key).startsWith(prefix) ){
                    vars.put((String)key,(T)entry.getValue());
                }
            }
        }
        return vars;
    }

    public static <K,V> V remove(K key) {
        Map map = getThreadLocal();
        return (V)map.remove(key);
    }

    public static void clear(String prefix) {
        if( prefix == null ){
            return;
        }
        Map map = getThreadLocal();
        Set<Map.Entry> set = map.entrySet();
        List<String> removeKeys = new ArrayList<>();

        for( Map.Entry entry : set ){
            Object key = entry.getKey();
            if( key instanceof String ){
                if( ((String) key).startsWith(prefix) ){
                    removeKeys.add((String)key);
                }
            }
        }
        for( String key : removeKeys ){
            map.remove(key);
        }
    }
}


