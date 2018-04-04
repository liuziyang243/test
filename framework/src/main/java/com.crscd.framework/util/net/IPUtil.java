package com.crscd.framework.util.net;

import com.crscd.framework.util.number.NumberUtil;
import com.google.common.net.InetAddresses;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * InetAddress工具类，基于Guava的InetAddresses.
 * <p>
 * 主要包含int, String/IPV4String, InetAdress/Inet4Address之间的互相转换
 * <p>
 * 先将字符串传换为byte[]再用InetAddress.getByAddress(byte[])，避免了InetAddress.getByName(ip)可能引起的DNS访问.
 * <p>
 * InetAddress与String的转换其实消耗不小，如果是有限的地址，建议进行缓存.
 *
 * @author calvin
 */
public class IPUtil {

    /**
     * 从InetAddress转化到int, 传输和存储时, 用int代表InetAddress是最小的开销.
     * <p>
     * InetAddress可以是IPV4或IPV6，都会转成IPV4.
     *
     * @see com.google.common.net.InetAddresses#coerceToInteger(InetAddress)
     */
    public static int toInt(InetAddress address) {
        return InetAddresses.coerceToInteger(address);
    }

    /**
     * InetAddress转换为String.
     * <p>
     * InetAddress可以是IPV4或IPV6. 其中IPV4直接调用getHostAddress()
     *
     * @see com.google.common.net.InetAddresses#toAddrString(InetAddress)
     */
    public static String toString(InetAddress address) {
        return InetAddresses.toAddrString(address);
    }

    /**
     * 从int转换为Inet4Address(仅支持IPV4)
     */
    public static Inet4Address fromInt(int address) {
        return InetAddresses.fromInteger(address);
    }

    /**
     * 从String转换为InetAddress.
     * <p>
     * IpString可以是ipv4 或 ipv6 string, 但不可以是域名.
     * <p>
     * 先字符串传换为byte[]再调getByAddress(byte[])，避免了调用getByName(ip)可能引起的DNS访问.
     */
    public static InetAddress fromIpString(String address) {
        return InetAddresses.forString(address);
    }

    /**
     * 从IPv4String转换为InetAddress.
     * <p>
     * IpString如果确定ipv4, 使用本方法减少字符分析消耗 .
     * <p>
     * 先字符串传换为byte[]再调getByAddress(byte[])，避免了调用getByName(ip)可能引起的DNS访问.
     */
    public static Inet4Address fromIpv4String(String address) {
        byte[] bytes = ip4StringToBytes(address);
        if (bytes == null) {
            return null;
        } else {
            try {
                return (Inet4Address) Inet4Address.getByAddress(bytes);
            } catch (UnknownHostException e) {
                throw new AssertionError(e);
            }
        }
    }

    /**
     * int转换到IPV4 String, from Netty NetUtil
     */
    public static String intToIpv4String(int i) {
        return new StringBuilder(15).append(i >> 24 & 0xff).append('.').append(i >> 16 & 0xff).append('.')
                .append(i >> 8 & 0xff).append('.').append(i & 0xff).toString();
    }

    /**
     * Ipv4 String 转换到int
     */
    public static int ipv4StringToInt(String ipv4Str) {
        byte[] byteAddress = ip4StringToBytes(ipv4Str);
        if (byteAddress == null) {
            return 0;
        } else {
            return NumberUtil.toInt(byteAddress);
        }
    }

    /**
     * Ipv4 String 转换到byte[]
     */
    private static byte[] ip4StringToBytes(String ipv4Str) {
        if (ipv4Str == null) {
            return null;
        }
        String[] test = ipv4Str.split("\\.");

        List<String> it = Arrays.asList(test);
        if (it.size() != 4) {
            return null;
        }

        byte[] byteAddress = new byte[4];
        for (int i = 0; i < 4; i++) {
            int tempInt = Integer.parseInt(it.get(i));
            if (tempInt > 255) {
                return null;
            }
            byteAddress[i] = (byte) tempInt;
        }
        return byteAddress;
    }

    /**
     * 验证ip地址是否在范围内
     * ipRange的格式为192.167.100.152~192.168.50.30;192.168.100.1~192.168.101.2;192.168.60.3
     */
    public static boolean verifyIpAddr(String ipAddr, String ipRange) {
        int idx = ipRange.indexOf(';');
        if (idx > 0) {
            String ipRangePart1 = ipRange.substring(0, idx);
            if (verifyIpAddrRange(ipAddr, ipRangePart1)) {
                return true;
            } else {
                String ipRangePart2 = ipRange.substring(idx + 1);
                return verifyIpAddr(ipAddr, ipRangePart2);
            }
        } else {
            return verifyIpAddrRange(ipAddr, ipRange);
        }
    }

    private static boolean verifyIpAddrRange(String ipAddr, String ipRange) {
        int idx = ipRange.indexOf('~');
        if (idx > 0) {//ip地址段
            String startIp = ipRange.substring(0, idx);
            String endIp = ipRange.substring(idx + 1);
            int currentIp = ipv4StringToInt(ipAddr);
            int startIpInt = ipv4StringToInt(startIp);
            int endIpInt = ipv4StringToInt(endIp);
            return (currentIp >= startIpInt && currentIp <= endIpInt);
        } else//具体的ip地址
        {
            return ipAddr.equals(ipRange);
        }
    }
}

