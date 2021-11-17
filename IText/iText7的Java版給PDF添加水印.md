### iText7的Java版給PDF添加水印

***

> 參考網址：https://itextpdf.com/en/resources/examples/itext-7/watermark-examples
>
> https://itextpdf.com/en/resources/faq/technical-support/itext-7/how-create-pdf-font-information-and-embed-actual-font-while

***

pom.xml中的jar包依賴

```txt
<!-- https://mvnrepository.com/artifact/com.itextpdf/kernel -->
<dependency>
	<groupId>com.itextpdf</groupId>
	<artifactId>kernel</artifactId>
	<version>7.1.8</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.itextpdf/layout -->
<dependency>
	<groupId>com.itextpdf</groupId>
	<artifactId>layout</artifactId>
	<version>7.1.8</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.itextpdf/font-asian -->
<dependency>
	<groupId>com.itextpdf</groupId>
	<artifactId>font-asian</artifactId>
	<version>7.1.8</version>
</dependency>
```

添加水印工具類:PDF_WaterMark_Util.java

```java
package com.foxconn.bidding.util;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * PDF水印工具類
 */
public class PDF_WaterMark_Util {
    // 添加水印
    public static OutputStream addWaterMark(InputStream is, OutputStream os, String waterMarkText) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(is), new PdfWriter(os));
        Document doc = new Document(pdfDoc);
        int n = pdfDoc.getNumberOfPages();
        PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        Paragraph p = new Paragraph(waterMarkText).setFont(font).setFontSize(30);

        // transparency
        PdfExtGState gs1 = new PdfExtGState();
        gs1.setFillOpacity(0.5f);
        // properties
        PdfCanvas over;
        Rectangle pagesize;
        float x, y;

        for (int i = 1; i <= n; i++) {
            pagesize = pdfDoc.getPage(i).getPageSize();
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = new PdfCanvas(pdfDoc.getPage(i));
            over.saveState();
            over.setExtGState(gs1);

            doc.showTextAligned(p, x, y, i, TextAlignment.CENTER, VerticalAlignment.TOP, 0);
        }
        doc.close();

        return os;
    }
}
```

SpringBoot項目中的下載controller:Pic_Show_Controller.java

```java
package com.foxconn.bidding.controller;

import com.foxconn.bidding.mapper.UserMapper;
import com.foxconn.bidding.model.ResultParam;
import com.foxconn.bidding.model.USER_INFO_bean;
import com.foxconn.bidding.util.FTP_File_Util;
import com.foxconn.bidding.util.PDF_WaterMark_Util;
import com.foxconn.bidding.util.VerifyToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@Controller
@RequestMapping("api/pic_show")
public class Pic_Show_Controller {
    @Autowired
    private FTP_File_Util util;
    @Autowired
    private UserMapper userMapper;

    // 加載圖片(param中必須傳遞 file_save_path, file_save_name, file_origin_name)
    // html中用<a href="/api/pic_show/load?file_save_path=xxx&file_save_name=yyy&file_origin_name=zzz"></a>來實現下載
    // html中用<img src="/api/pic_show/load?file_save_path=xxx&file_save_name=yyy&file_origin_name=zzz"></img>來實現顯示
    @RequestMapping("/load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        String file_save_path = request.getParameter("file_save_path");
        String file_save_name = request.getParameter("file_save_name");
        String file_origin_name = request.getParameter("file_origin_name");

        int lastIndexOfDot = file_save_name.lastIndexOf(".");
        if(lastIndexOfDot == -1) {
            throw new RuntimeException("文件無格式後綴");
        } else {
            String suffix = file_save_name.substring(lastIndexOfDot);
            if(!".bmp".equals(suffix) && !".jpg".equals(suffix) && !".jpeg".equals(suffix) && !".png".equals(suffix) && !".gif".equals(suffix)) {
                throw new RuntimeException("圖片格式有誤，只能加載.bmp .jpg .jpeg .png .gif 格式的圖片");
            }
        }

        OutputStream os = null;
        InputStream is = null;
        try {
            ResultParam resultParam = util.fileDownload(file_save_path, file_save_name);
            if(resultParam.getT() != null) {
                response.setContentType("application/x-download");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file_origin_name, "UTF-8"));

                is = (InputStream) resultParam.getT();
                BufferedInputStream bis = new BufferedInputStream(is);
                os = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int length;

                while((length = bis.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }
                is.close();
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 【04】文件下載
    @VerifyToken
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String file_save_path = request.getParameter("file_save_path");
        String file_save_name = request.getParameter("file_save_name");
        String file_origin_name = request.getParameter("file_origin_name");

        String user_pkid = (String) request.getAttribute("user_pkid");
        USER_INFO_bean user = userMapper.findUserById(user_pkid);
        String username = user.getUsername();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String waterMarkText = "下載用戶:" + username + "\n下載時間:" + dateStr + "\n公司內部文件，注意保密！";

        OutputStream os = null;
        InputStream is = null;
        try {
            ResultParam resultParam = util.fileDownload(file_save_path, file_save_name);
            if(resultParam.getT() != null) {
                response.setContentType("application/x-download");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file_origin_name, "UTF-8"));

                is = (InputStream) resultParam.getT();
                BufferedInputStream bis = new BufferedInputStream(is);
                os = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int length;

                if(file_save_name.length() > 4) {
                    String suffix = file_save_name.substring(file_save_name.length() - 4);
                    if(".pdf".equals(suffix)) {
                        try {
                            os = PDF_WaterMark_Util.addWaterMark(is, os, waterMarkText);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("此pdf文件未添加水印,文件下載失敗");
                        }
                    }
                }

                while((length = bis.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }
                is.close();
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```