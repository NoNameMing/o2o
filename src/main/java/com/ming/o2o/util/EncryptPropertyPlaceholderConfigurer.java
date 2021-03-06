package com.ming.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    // 需要加密的字段数组
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password"};

    /**
     * 对关键的属性进行转换
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if(isEncryptProp(propertyName)) {
            // 对已加密字段进行解密工作
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

    /**
     * 该属性是否已加密
     *
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName) {
        // 若等于已加密的 field，则进行解密
        for (String encryptPropertyName : encryptPropNames) {
            if(encryptPropertyName.equals(propertyName))
                return true;
        }
        return false;
    }
}
