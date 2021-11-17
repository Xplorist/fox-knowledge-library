package test.main;

import test.entity.Consumer;
import test.entity.Producer;
import test.entity.Product;

/**
 * 啟動器
 */
public class Starter {
    public static void main(String[] args) {
        Product pd = new Product();
        Producer producer = new Producer(pd);
        Consumer consumer = new Consumer(pd);

        Thread p_thread = new Thread(producer);
        Thread c_thread = new Thread(consumer);

        p_thread.start();
        c_thread.start();
    }
}

/*
* 本題的重點在於【兩個線程交替運行】，為了達成這個目的，就要使用同步鎖synchronized，
* 生產者線程在產品對象的狀態為消費狀態時，pd.wait()使當前線程進入等待狀態，
* 消費者線程在產品對象的狀態為消費狀態時，執行完相應的產品數目變更後，pd.notifyAll()喚醒正處於等待狀態的生產者線程
* */