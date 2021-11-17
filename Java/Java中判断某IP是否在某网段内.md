---
title: Java中判断某IP是否在某网段内
date: 2021-01-30 16:43:00
categories: Java
toc: true
---

# Java中判断某IP是否在某网段内

## 示例代码

* IPUtil.java
``` java
// IPUtil.java
public class IPUtil {
    public static void main(String[] args) {
		System.out.println(isInRange("192.168.1.127", "192.168.1.64/26"));
		System.out.println(isInRange("192.168.1.2", "192.168.0.0/23"));
        System.out.println(isInRange("192.168.0.1", "192.168.0.0/24"));
        System.out.println(isInRange("192.168.0.0", "192.168.0.0/32"));
        System.out.println(isInRange("10.244.186.86", "10.244.186.0/24"));
	}
    
    public static boolean isInRange(String ip, String cidr) {
    	String[] ips = ip.split("\\.");
    	int ipAddr = (Integer.parseInt(ips[0]) << 24) + (Integer.parseInt(ips[1]) << 16) + (Integer.parseInt(ips[2]) << 8) + Integer.parseInt(ips[3]);
    	
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
    	int mask = 0xFFFFFFFF << (32 - type);
    	
    	String cidrIp = cidr.replaceAll("/.*", "");
    	String[] cidrIps = cidrIp.split("\\.");
    	int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24) + (Integer.parseInt(cidrIps[1]) << 16) + (Integer.parseInt(cidrIps[2]) << 8) + Integer.parseInt(cidrIps[3]);
    	
    	return (ipAddr & mask) == (cidrIpAddr & mask);
    }
}
```