package com.example.app4;

import java.util.Random;

public class GenerateNumUtil {

    public static int randomCompNum(int degree) {
        int min = (int)Math.pow(10, degree - 1);
        int max = (int)Math.pow(10, degree) - 1;
        int diff = max - min;
        Random random = new Random();
        return random.nextInt(diff + 1) + min;
    }
}
