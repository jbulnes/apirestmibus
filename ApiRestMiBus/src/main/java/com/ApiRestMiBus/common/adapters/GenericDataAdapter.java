package com.ApiRestMiBus.common.adapters;

import com.ApiRestMiBus.common.domain.GenericResponse;
import com.ApiRestMiBus.util.Utils;

public class GenericDataAdapter {
    public GenericResponse createData(Object obj) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(true);
        response.setData(obj);
        response.setLastUpdate(Utils.generateDate());
        return response;
    }

    public GenericResponse createError(String str1,String str2) {
        GenericResponse response = new GenericResponse();
        response.addError(str1, str2);
        response.setSuccess(false);
        response.setLastUpdate(Utils.generateDate());
        return response;
    }
}
