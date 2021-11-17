### Java泛型

***

>參考網址：https://juejin.im/post/5d5789d26fb9a06ad0056bd9

***

1. 核心模型：類型參數化

   類型參數化，提高了類的抽象程度，例如：類的屬性可以不用是一個具體的類型，類的屬性變得動態了，根據實例化時構造方法中傳入的具體類型來確定對象該屬性的具體類型。

   參數化：類聲明中的通配符就是一個形參，而實例化的時候傳入類的就是一個實參，類對象實際的屬性根據實例化的時候傳入的具體類型來確定。

   核心理解：在類實例化時確定類的屬性的具體類型。

   記憶：泛，不具體，抽象的意思，泛型就是不具體類型，抽象的類型，根據主類實例化時構造方法具體傳入的類型來確定實例化對象該屬性具體的類型，提高了主類的抽象程度，可以適應更多的情況，而不用去設置多個具體類型的屬性。

   具體形象記憶可以參考下面的代碼示例。

   示例：

   ```Java
   public class Result<T> {
       private T t;
       
       public Result(T t) {
           this.t = t;
       }
       
       public T getT() {
           return t;
       }
       
       public void setT(T t) {
           this.t = t;
       } 
       
       public static void main(String[] args) {
           Dog dog = new Dog();
           Result result = new Result(dog);
       } 
   }
   ```

2. 通配符區分

* ？：不確定的java類型

* T：type 類型

* K：key value 中的key

* V：key value 中的value

* E：element 元素