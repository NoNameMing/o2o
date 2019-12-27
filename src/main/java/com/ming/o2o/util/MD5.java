package com.ming.o2o.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
    public static final String getMd5(String s){
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 计算messageDigest5函数
            messageDigest.update(s.getBytes());
            // digest()最后确定返回messageDigest5 hash值，返回值为8为字符串。因为messageDigest5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String args[]) {
        System.out.println(getMd5("admin"));
    }
}
