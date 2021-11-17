# Java中ASCII码与字符的转换

> * 参考网址：[java 字符与ASCII码互转](https://www.cnblogs.com/ooo0/p/8465237.html)

## 核心ASCII码

``` txt

     0-9对应Ascii 48-57
	 A-Z 65-90
	 a-z 97-122
	 第33～126号(共94个)是字符，其中第48～57号为0～9十个阿拉伯数字

```

## ASCII码转化为字符char，并进一步转化为字符串String

* 示例代码：

``` java
		// ASCII码A-Z转字符，然后添加到list中
		List<String> alphabet = new ArrayList<String>();
		for (int i = 65; i <= 90; i++ ) {
			char c = (char)i;
			String a = String.valueOf(c);
			alphabet.add(a);
		}
```

## 字符char转ASCII码

* 示例代码：

``` java

	char ch = 'A';
	int ascii = (int) ch;

```