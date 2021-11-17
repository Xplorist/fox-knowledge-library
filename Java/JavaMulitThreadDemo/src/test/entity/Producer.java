package test.entity;

import java.util.Random;

/**
 * 生產者
 */
public class Producer implements Runnable{
    private Product pd;

    public Producer(Product pd) {
        this.pd = pd;
    }

    @Override
    public void run() {
        for(int i = 0; i < 20; i++) {
            synchronized (pd) {
                if(!"produce".equals(pd.getStatus())) {
                    try {
                        pd.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Integer produceNum = new Random().nextInt(Product.getMaxNum() - pd.getNum()) + 1;
                Integer lastLeftNum = pd.getNum();
                pd.setNum(pd.getNum() + produceNum);
                pd.setStatus("consume");
                System.out.println("生產者本次隨機生產了" + produceNum + "件商品，上次剩餘商品數量為" + lastLeftNum + ", 則本次提供的總數量為" + pd.getNum() + ";");
                pd.notifyAll();
            }

        }
    }
}
