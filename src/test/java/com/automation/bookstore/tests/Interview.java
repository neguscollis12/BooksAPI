package com.automation.bookstore.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interview {
    public static void main(String[] args) {
        char[] input = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        Map<Character, Integer> map = new HashMap<>();
        for (char c : input) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        System.out.println(map);
    }
}
