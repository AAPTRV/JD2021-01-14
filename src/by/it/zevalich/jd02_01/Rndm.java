package by.it.zevalich.jd02_01;

import java.util.Random;

public class Rndm {
    static final long init = System.currentTimeMillis();
    static final Random random = new Random(init);
    static int fromTo(int from, int to){
        int delta = to - from + 1;
        int msWait = from + random.nextInt(delta);
        return  msWait;
    }
}
