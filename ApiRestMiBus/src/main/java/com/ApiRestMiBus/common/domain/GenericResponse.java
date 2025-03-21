package com.ApiRestMiBus.common.domain;


import lombok.Data;

@Data
public class GenericResponse {
    private boolean success = true;
    private Object data;
    private ErrorInfo error;
    private String lastUpdate;

    public void addError(String str1, String str2) {
        if(this.error == null) {
            this.error = new ErrorInfo();
        }
        error.setCode(str1);
        error.setDescription(str2);
    }
}
