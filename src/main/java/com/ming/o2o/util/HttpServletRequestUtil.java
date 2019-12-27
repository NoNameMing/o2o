package com.ming.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    /**
     * 从 request 中获取一个 key 做返回，转化为 int 类型
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key) {
        try{
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 从 request 中获取一个 key 做返回，转化为 long 类型
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request, String key) {
        try{
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 从 request 中获取一个 key 做返回，转化为 Double 类型
     * @param request
     * @param key
     * @return
     */
    public static Double getDouble(HttpServletRequest request, String key) {
        try{
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }
    }

    /**
     * 从 request 中获取一个 key 做返回，转化为 boolean 类型
     * @param request
     * @param key
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request, String key) {
        try{
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 request 中获取一个 key 做返回，转化为 String 类型
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        try{
            String result = request.getParameter(key);
            if(result != null){
                result = result.trim();
                return result;
            }
            if("".equals(result)){
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
