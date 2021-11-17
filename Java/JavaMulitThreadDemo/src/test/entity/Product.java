package test.entity;

/**
 * 商品
 */
public class Product {
    private Integer num = 0;// 當前產品數量
    private static final Integer MAX_NUM = 1000;// 最大數量
    private String status = "produce";// 产品狀態，初始值為："produce", 代表生產狀態，"consume",代表消費狀態

    public static Integer getMaxNum() {
        return MAX_NUM;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
