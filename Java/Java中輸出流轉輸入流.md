### Java中輸出流轉輸入流

***

輸出流轉輸入流工具類：

```java
package com.foxconn.bidding.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 輸出流轉輸入流工具類
 */
public class Out_to_In_stream_Util {
    // 輸入流轉輸入流
    public static InputStream convert_out_to_in(OutputStream os) {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) os;
        InputStream is = new ByteArrayInputStream(baos.toByteArray());

        return is;
    }
}

```

