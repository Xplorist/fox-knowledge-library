package test.entity;

import java.util.Random;

/**
 * 消費者
 */
public class Consumer implements Runnable{
    private Product pd;

    public Consumer(Product pd) {
        this.pd = pd;
    }

    @Override
    public void run() {
        for(int i = 0; i < 20; i++) {
            synchronized (pd) {
                if(!"consume".equals(pd.getStatus())) {
                    try {
                        pd.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Integer consumeNum = new Random().nextInt(pd.getNum());
                Integer lastTotalNum = pd.getNum();
                pd.setNum(pd.getNum() - consumeNum);
                pd.setStatus("produce");
                System.out.println("消費者本次所能消費的總數量為" + lastTotalNum + ", 而本次消費者隨機消費" + consumeNum + "件，則剩餘" + pd.getNum() + ";");
                pd.notifyAll();
            }
        }
    }
}
