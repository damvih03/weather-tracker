package com.damvih.utils;

import org.modelmapper.ModelMapper;


public class MapperUtil {

    private static ModelMapper INSTANCE;

    public static ModelMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModelMapper();
        }
        return INSTANCE;
    }

    private MapperUtil() {

    }

}
