package chapter3.part4;

import java.util.LinkedHashMap;

public class FirstNonRepeatedChar {
    public static void main(String[] args) {
        String input="aa";
        LinkedHashMap<Character, Integer> freqMap = new LinkedHashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (freqMap.containsKey(c)) {
                freqMap.put(c, freqMap.get(c) + 1);
            } else {
                freqMap.put(c, 1);
            }
        }
        Character nonRepeated = null;
        for (char c : freqMap.keySet()) {
            if (freqMap.get(c) == 1) {
                nonRepeated = c;
                break;
            }
        }
        System.out.println(nonRepeated);
    }
}
