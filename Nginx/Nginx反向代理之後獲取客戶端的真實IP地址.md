### Nginx反向代理之後獲取客戶端的真實IP地址

***

> 參考網址：http://www.pinhuba.com/other/101331.htm
>
> https://blog.csdn.net/Nedved_L/article/details/79120869

***

nginx.conf

```
    server {
        listen       80;
		#listen        8086;
        server_name  localhost;
		root         E:\static;
		#root          html;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            try_files $uri $uri/ /index.html;
        }
		
		#項目配置
		location ^~ /bid {
			try_files $uri $uri/ /bid/index.html;
		}
		#location ^~ /send {
		#	try_files $uri $uri/ /send/index.html;
		#}
		
		#項目對應api代理配置
		location /bid/api/ {
			#proxy_pass http://10.244.231.103:8081/api/;
			proxy_pass http://10.244.186.86:8081/api/;
			proxy_set_header    Host                        $host;
            proxy_set_header    X-Real-IP                    $remote_addr;
            proxy_set_header    X-Forwarded-For              $proxy_add_x_forwarded_for;
            proxy_set_header    HTTP_X_FORWARDED_FOR      $remote_addr;
		}
	}
```

核心在於在反向代理配置中加入請求頭設置

```
			proxy_set_header    Host                        $host;
            proxy_set_header    X-Real-IP                    $remote_addr;
            proxy_set_header    X-Forwarded-For              $proxy_add_x_forwarded_for;
            proxy_set_header    HTTP_X_FORWARDED_FOR      $remote_addr;
```

> 如果是多級反向代理，每一級反向代理都要添加這樣的請求頭設置

Java後台後去客戶端真實IP的示例：

```
    // 【test】測試IP
    @RequestMapping("/testIP")
    public ResultParam testIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        ResultParam result = ResultParam.of("1", "IP:" + ip);

        return result;
    }
```



一個比較全面的獲取真實IP的工具類：

```java
import javax.servlet.http.HttpServletRequest;

/**
 * IP地址工具类
 * @author xudongdong
 *
 */
public class IpUtil {
    
    /**
     * 私有化构造器
     */
    private IpUtil() {
    }

    /**
     * 获取真实IP地址
     * <p>使用getRealIP代替该方法</p>
     * @param request req
     * @return ip
     */
    @Deprecated
    public static String getClinetIpByReq(HttpServletRequest request) {
        // 获取客户端ip地址
        String clientIp = request.getHeader("x-forwarded-for");

        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        /*
         * 对于获取到多ip的情况下，找到公网ip.
         */
        String sIP = null;
        if (clientIp != null && !clientIp.contains("unknown") && clientIp.indexOf(",") > 0) {
            String[] ipsz = clientIp.split(",");
            for (String anIpsz : ipsz) {
                if (!isInnerIP(anIpsz.trim())) {
                    sIP = anIpsz.trim();
                    break;
                }
            }
            /*
             * 如果多ip都是内网ip，则取第一个ip.
             */
            if (null == sIP) {
                sIP = ipsz[0].trim();
            }
            clientIp = sIP;
        }
        if (clientIp != null && clientIp.contains("unknown")){
            clientIp =clientIp.replaceAll("unknown,", "");
            clientIp = clientIp.trim();
        }
        if ("".equals(clientIp) || null == clientIp){
            clientIp = "127.0.0.1";
        }
        return clientIp;
    }
    
    /**
     * 判断IP是否是内网地址
     * @param ipAddress ip地址
     * @return 是否是内网地址
     */
    public static boolean isInnerIP(String ipAddress) {
        boolean isInnerIp;
        long ipNum = getIpNum(ipAddress);
        /**   
        私有IP：A类  10.0.0.0-10.255.255.255   
               B类  172.16.0.0-172.31.255.255   
               C类  192.168.0.0-192.168.255.255   
        当然，还有127这个网段是环回地址   
        **/
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd)
                || ipAddress.equals("127.0.0.1");
        return isInnerIp;
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        return a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
    }
    
    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

    public static String getRealIP(HttpServletRequest request){
        // 获取客户端ip地址
        String clientIp = request.getHeader("x-forwarded-for");

        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        String[] clientIps = clientIp.split(",");
        if(clientIps.length <= 1) return clientIp.trim();

        // 判断是否来自CDN
        if(isComefromCDN(request)){
            if(clientIps.length>=2) return clientIps[clientIps.length-2].trim();
        }

        return clientIps[clientIps.length-1].trim();
    }

    private static boolean isComefromCDN(HttpServletRequest request) {
        String host = request.getHeader("host");
        return host.contains("www.189.cn") ||host.contains("shouji.189.cn") || host.contains(
                "image2.chinatelecom-ec.com") || host.contains(
                "image1.chinatelecom-ec.com");
    }
}
```