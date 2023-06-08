package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger COUNTER1 = new AtomicInteger(0);
    public static AtomicInteger COUNTER2 = new AtomicInteger(0);
    public static AtomicInteger COUNTER3 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    textLength(text);
                }
            }
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                textLength(text);
            }
        });
        thread2.start();
        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isAlpha(text)) {
                    textLength(text);
                }
            }
        });
        thread3.start();

        System.out.println("Красивых слов с длиной 3: " + COUNTER1);
        System.out.println("Красивых слов с длиной 4: " + COUNTER2);
        System.out.println("Красивых слов с длиной 5: " + COUNTER3);


    }

    public static void textLength(String text) {
        if (text.length() == 3) {
            COUNTER1.addAndGet(1);
        } else if (text.length() == 4) {
            COUNTER2.addAndGet(1);
        } else if (text.length() == 5) {
            COUNTER3.addAndGet(1);
        }
    }

    public static boolean isAlpha(String s) {
        char[] c = s.toCharArray();
        Arrays.sort(c);
        return s.equals(new String(c));
    }

    public static Boolean isPalindrome(String s) {
        return s.equals((new StringBuilder(s)).reverse().toString());
    }

    public static boolean isSameCharacters(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        char c = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}