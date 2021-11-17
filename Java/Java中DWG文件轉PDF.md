### Java中DWG文件轉PDF

***

> 參考網址：https://github.com/aspose-cad/Aspose.CAD-for-Java

DWG文件轉PDF文件工具類：

```java
package com.foxconn.bidding.util;

import com.aspose.cad.Color;
import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.PdfOptions;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * DWG文件轉PDF文件工具類
 */
public class DWG_to_PDF_Util {
    // DWG文件轉換為PDF
    public static OutputStream convert_DWG_to_PDF(InputStream is, OutputStream os) {
        com.aspose.cad.Image objImage = com.aspose.cad.Image.load(is);

        CadRasterizationOptions rasterizationOptions = new CadRasterizationOptions();
        rasterizationOptions.setBackgroundColor(Color.getWhite());
        rasterizationOptions.setPageWidth(1600);
        rasterizationOptions.setPageHeight(1600);

        // Create an instance of PdfOptions
        PdfOptions pdfOptions = new PdfOptions();
        // Set the VectorRasterizationOptions property
        pdfOptions.setVectorRasterizationOptions(rasterizationOptions);

        // Export the DWG to PDF
        objImage.save(os, pdfOptions);
        //ExEnd:ConvertDWGFileToPDF

        return os;
    }
}

```

文件上傳FTP服務器時將DWG文件轉換成PDF

```java
package com.foxconn.bidding.util;

import com.foxconn.bidding.model.FTP_File_Param;
import com.foxconn.bidding.model.ResultParam;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FTP_File_Util {
    @Autowired
    private FTP_Properties_Util util;

    /**
     * 文件上傳
     * @param type_base_path 類型根路徑（例如： user_pic為用戶頭像圖片類型的文件的根路徑）
     * @param file 文件
     * @return 響應結果
     */
    public ResultParam fileUpload(String type_base_path, MultipartFile file) {
        String file_origin_name = file.getOriginalFilename();
        InputStream input = null;
        try {
            input = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultParam("0", "上傳文件失敗", null);
        }

        // 將DWG文件轉換成PDF文件
        OutputStream output = null;
        int lastIndexOfDot = file_origin_name.lastIndexOf(".");
        if(lastIndexOfDot != -1) {
            String suffix = file_origin_name.substring(lastIndexOfDot);
            if(".dwg".equals(suffix)) {
                output = new ByteArrayOutputStream();
                try {
                    input = Out_to_In_stream_Util.convert_out_to_in(DWG_to_PDF_Util.convert_DWG_to_PDF(input, output));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(file_origin_name + "上傳失敗，此DWG文件無法轉換成PDF文件，請檢查此DWG文件是否損壞");
                }
                String prefix = file_origin_name.substring(0, lastIndexOfDot);
                file_origin_name = prefix + ".pdf";
            }
        }

        String FTP_Address = util.getAddress();
        Integer FTP_Port = util.getPort();
        String FTP_Username = util.getUsername();
        String FTP_Password = util.getPassword();

        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");

        Date date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        String month = new SimpleDateFormat("MM").format(date);
        String day = new SimpleDateFormat("dd").format(date);
        String file_save_path = type_base_path + File.separator + year + File.separator + month + File.separator + day;// 取得配置文件路徑
        String file_save_name = "(" + new SimpleDateFormat("HHmmssSSS").format(new Date()).toString() + ")"
                + file_origin_name;

        try {
            int replay;
            ftp.connect(FTP_Address, FTP_Port);
            ftp.login(FTP_Username, FTP_Password);
            replay = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replay)) {
                ftp.disconnect();
                return new ResultParam("0", "上傳文件失敗", null);
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            String workingDirectory = ftp.printWorkingDirectory();
            String[] paths = file_save_path.split("\\\\");
            for(int i = 0; i < paths.length; i++) {
                ftp.makeDirectory(paths[i]);
                boolean changeWorkingDirectory_flag = ftp.changeWorkingDirectory(paths[i]);
                if(!changeWorkingDirectory_flag) {
                    return new ResultParam("0", "上傳文件失敗", workingDirectory);
                }
            }

            ftp.storeFile(file_save_name, input);
            input.close();
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultParam("0", "上傳文件失敗", null);
        } finally {
            try {
                if(ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new ResultParam("0", "上傳文件失敗", null);
            }
        }
        FTP_File_Param ftp_file_param = new FTP_File_Param();
        ftp_file_param.setFile_save_path(file_save_path);
        ftp_file_param.setFile_save_name(file_save_name);
        ftp_file_param.setFile_origin_name(file_origin_name);

        return new ResultParam("1", "上傳文件成功", ftp_file_param);
    }

}
```

pom.xml

```
        <!-- https://github.com/aspose-cad/Aspose.CAD-for-Java -->
        <dependency>
            <groupId>com.aspose</groupId>
            <artifactId>aspose-cad</artifactId>
            <version>19.9</version>
        </dependency>
```

Maven在打包中遇到的問題：

>因為jar包依賴時從https://repository.aspose.com/repo/這個私有公司的倉庫下載的，在線下載時會出現問題，因為該私有公司倉庫中的maven-metadata.xml中沒有將最新版的19.9加到該文件中。導致maven打包時會報無法從https://repository.aspose.com/repo/找到19.9這個包。
>
>解決辦法：將19.9這個包下載到本地中，然後通過本地的mvn-install命令添加到本地倉庫中。
>
>詳細操作類似於下面的操作：
>
>1.到oracle官網下載相應版本的Ojdbc jar
>2.將下載好的ojdbc.jar文件放在指定位置
>3.在剛才的指定位置下打開cmd，并運行以下指令
>  $ mvn install:install-file -Dfile=ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
>4.到你的maven本地倉庫去尋找相應的ojdbc6.jar文件，現在pom.xml中可以使用ojdbc6的依賴了。