package ch07.scratch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test {
    public static void main(String[] args) {
        Map<String, Integer> h = new HashMap<>();
        h.put("a", null);
        System.out.println(h.keySet());
        h.compute("a", (k, v) -> null);
        System.out.println(h.keySet());
    }
}
