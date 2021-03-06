package by.it.abeseda.jd02_03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Market extends Thread {
    static final int COUNT_CASHIERS = 2;

    private final QueueBuyers queueBuyers;

    private final Dispatcher dispatcher;

    Market(Dispatcher dispatcher, QueueBuyers queueBuyers) {
        this.queueBuyers = queueBuyers;
        this.dispatcher = dispatcher;
    }

    public QueueBuyers getQueueBuyers() {
        return queueBuyers;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    @Override
    public void run() {
        System.out.println("Market is opened");
        int numberBuyer = 0;


        //создаем пул потоков кассиров, обозначаем их 2 штуки
        ExecutorService threadPool = Executors.newFixedThreadPool(COUNT_CASHIERS);


        //создаем и запускаем двух кассиров
        for (int numberCashier = 1; numberCashier <= COUNT_CASHIERS; numberCashier++) {
//           Cashier cashier = new Cashier(numberCashier, this);
//            а можно сделать еще так
            threadPool.submit(new Cashier(numberCashier, this));
        }

        threadPool.shutdown();

        //теперь создаем и запускаем потоки покупателей пока их не будет создано ровно 100
        while (dispatcher.marketIsOpened()) {
            int n = Utils.random(2);
            //тут важно проверить нужны ли новые покупатели, если их получилось 2 то можно впустить лишнего
            for (int i = 0; i < n && dispatcher.marketIsOpened(); i++) {
                Buyer buyer = new Buyer(++numberBuyer, this);
                buyer.start();
            }
            //ждем одну секунду
            Utils.timeout(1000);
        }

        //осталось дождаться завершения всех потоков
        try {
            while (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                Thread.onSpinWait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Market is closed");
    }

}
