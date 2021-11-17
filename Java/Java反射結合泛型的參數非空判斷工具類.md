### Java反射結合泛型的參數非空判斷工具類

****

參數工具類：

```java
package uitl.param;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 參數工具類
 */
public class ParamUtil<T> {
	private T param;
	
	/*
	 * 1.創建對象，將泛型類的對象和其屬性名list傳遞到此對象中
	 * 2.根據泛型類的反射，判斷傳遞的屬性名list中的值是否是該泛型類中存在的屬性名
	 * 3.判斷屬性名list中的每個屬性名對應的屬性值是否為空
	 * */
	public void checkAttrListIsEmpty(List<String> attrList) throws Exception {
		if(this.param == null) {
			throw new RuntimeException("參數類對象不能為null");
		}
		if(attrList == null || attrList.isEmpty()) {
			throw new RuntimeException("參數list不能為空");
		}
		
		Class<? extends Object> clazz = param.getClass();
		String clazzName = clazz.getName();
		Field[] fields = clazz.getDeclaredFields();
		
		for(int i = 0; i < attrList.size(); i++) {
			String attrName = attrList.get(i);
			Field attr = null;
			Boolean flag = false;
			for(int j = 0; j < fields.length; j++) {
				Field field = fields[j];
				String fieldName = field.getName();
				if(attrName.equals(fieldName)) {
					flag = true;
					attr = field;
					break;
				}
			}
			
			if(!flag) {
				throw new RuntimeException("參數類" + clazzName + "中不存在" + attrName + "屬性");
			}
			
			attr.setAccessible(true);// 設置屬性為可訪問
			Object attrObj = attr.get(param);
			if(attrObj == null) {
				throw new RuntimeException("參數類" + clazzName + "中" + attrName + "屬性值為null");
			}
			Class<? extends Object> attrClass = attrObj.getClass();//關鍵語句， 可以獲取到泛型類型的真實類型
			String attrClassStr = attrClass.toString();
			if("class java.lang.String".equals(attrClassStr)) {
				if("".equals(attrObj.toString())) {
					throw new RuntimeException("參數類" + clazzName + "中String類型" + attrName + "屬性值為空");
				}
			} else if("class java.util.ArrayList".equals(attrClassStr)) {
				if("[]".equals(attrObj.toString())) {
					throw new RuntimeException("參數類" + clazzName + "中ArrayList類型" + attrName + "屬性值為[ ] ");
				}
			}
		}
	}
	
	// 檢查param的所有參數是否為空
	public void checkAllAttrIsEmpty() throws Exception {
		if(this.param == null) {
			throw new RuntimeException("參數類對象不能為null");
		}
		Class<? extends Object> paramClass = param.getClass();
		String paramClassName = paramClass.getName();
		Field[] declaredFields = paramClass.getDeclaredFields();
		for(int i = 0; i < declaredFields.length; i++) {
			Field field = declaredFields[i];
			String fieldName = field.getName();
			field.setAccessible(true);
			Object fieldObj = field.get(param);
			if(fieldObj == null) {
				throw new RuntimeException("參數類" + paramClassName + "中的" + fieldName + "屬性值為null");
			}
			Class<? extends Object> fieldObjClass = fieldObj.getClass();
			String fieldObjClassStr = fieldObjClass.toString();
			if("class java.lang.String".equals(fieldObjClassStr)) {
				if("".equals(fieldObj.toString())) {
					throw new RuntimeException("參數類" + paramClassName + "中的String類型" + fieldName + "屬性值為空");
				}
			} else if("class java.util.ArrayList".equals(fieldObjClassStr)) {
				if("[]".equals(fieldObj.toString())) {
					throw new RuntimeException("參數類" + paramClassName + "中的ArrayList類型" + fieldName + "屬性值為 [ ] ");
				}
			}
		}
	}
	
	public ParamUtil(T param) {
		super();
		if(param == null) {
			throw new RuntimeException("參數類對象不能為null");
		}
		this.param = param;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}
}
```

測試類

```java
package uitl.test;

import java.util.ArrayList;
import java.util.List;

import global.ResultParam;
import uitl.param.ParamUtil;

/**
 * 測試類
 */
public class Test {
    public static void main(String[] args) {
        testParamUtil();
    }

    public static void testParamUtil() {
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        ResultParam<List<Integer>> rp = new ResultParam<List<Integer>>("testXXX", "", intList);

        List<String> attrList = new ArrayList<String>();
        attrList.add("code");
        attrList.add("msg");
        attrList.add("t");
        try {
            new ParamUtil<ResultParam<List<Integer>>>(rp, attrList).checkAttrListIsEmpty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

****

參數類

```java
package global;

/**
 * 返回結果類
 */
public class ResultParam<T> {
    private String code;// 返回結果代碼
    private String msg;// 返回結果信息
    private T t;// 返回結果泛型

    public ResultParam() {
        super();
    }

    public ResultParam(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ResultParam(String code, String msg, T t) {
        super();
        this.code = code;
        this.msg = msg;
        this.t = t;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
```

****

>  反射和泛型相關參考資料：
> 
> * [https://www.jianshu.com/p/3e30c7814002](https://www.jianshu.com/p/3e30c7814002)
> 
> * [https://www.jianshu.com/p/283a3e2bf251](https://www.jianshu.com/p/283a3e2bf251)
> 
> * [https://blog.csdn.net/qq_30698633/article/details/76671547](https://blog.csdn.net/qq_30698633/article/details/76671547)

.
