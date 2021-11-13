package com.tinyblog.springboot.jdbctemplate.common.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @see ;
 */
public class BeanUtils {

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object propertyValue = beanWrapper.getPropertyValue(pd.getName());
            if (propertyValue == null) 
                emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[0]);
    }

    public static void copyNonNullProperties(Object src, Object target){
        org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static void copyProperties(Object src, Object target){
        org.springframework.beans.BeanUtils.copyProperties(src, target);
    }
}
