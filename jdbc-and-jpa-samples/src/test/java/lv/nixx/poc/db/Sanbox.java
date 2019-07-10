package lv.nixx.poc.db;

import org.junit.Test;

public class Sanbox {

    @Test
    public void test() {


        int[][] sa = new int[][] {
            new int[] {11, 12, 13 },
            new int[] {21, 22, 23 },
            new int[] {31, 32, 33 },
            new int[] {41, 42, 43 }
        };

        for (int i = 0; i<4; i++) {
            System.out.println( sa[i][1] );
        }

    }

}
