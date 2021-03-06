package by.it.petrov.jd02_01;

public class Timer extends Thread implements Runnable {

    private Integer setTimer;
    private static Integer secondsPassed;
    private static Integer speedCoefficient;

    public Timer(Integer setTimer, Integer speedCoefficientInput) {
        this.setTimer = setTimer;
        speedCoefficient = speedCoefficientInput;
        secondsPassed = 0;
        start();
    }

    public static Integer getSpeedCoefficient() {
        return speedCoefficient;
    }

    public static Integer getSecondsPassed() {
        int countedTime = secondsPassed;
        if(secondsPassed % 60 == 0){
            return secondsPassed;
        } else {
            return secondsPassed % 60;
        }
    }

    @Override
    public void run() {
        System.out.println("The timer has started!");
        while (this.setTimer != 0){
            try {
                Thread.sleep(1000 / Timer.getSpeedCoefficient());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.setTimer--;
            secondsPassed++;
            if(this.setTimer % 5 == 0 && this.setTimer != 0){
                System.out.println(this.setTimer + "seconds left!");
            }
        }
        System.out.println("The time is up!");
    }
}