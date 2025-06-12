package com.ApiRestMiBus.common.domain;

import lombok.Generated;

import java.util.Map;

public class ErrorInfo {
    private String code;
    private String description;
    private Map<String, String> errors;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Error [code=");
        builder.append(this.code);
        builder.append("description=");
        builder.append(this.description);
        builder.append("errors=");
        builder.append(this.errors);
        builder.append("]");
        return builder.toString();
    }

    @Generated
    public static ErrorInfoBuilder builder() {
        return new ErrorInfoBuilder();
    }

    @Generated
    public String getCode() {
        return this.code;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public Map<String, String> getErrors() {
        return this.errors;
    }

    @Generated
    public void setCode(final String code) {
        this.code = code;
    }

    @Generated
    public void setDescription(final String description) {
        this.description = description;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Generated
    public ErrorInfo(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorInfo(String code, String description, Map<String, String> errors) {
        this.code = code;
        this.description = description;
        this.errors = errors;
    }

    @Generated
    public ErrorInfo() {
    }

    @Generated
    public static class ErrorInfoBuilder {
        @Generated
        private String code;
        @Generated
        private String description;
        private Map<String, String> errors;

        @Generated
        ErrorInfoBuilder() {
        }

        @Generated
        public ErrorInfoBuilder code(final String code) {
            this.code = code;
            return this;
        }

        @Generated
        public ErrorInfoBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ErrorInfoBuilder errors(final Map<String, String> errors) {
            this.errors = errors;
            return this;
        }

        public ErrorInfo build() {
            return new ErrorInfo(this.code, this.description, this.errors);
        }

        @Generated
        public String toString() {
            return "ErrorInfo.ErrorInfoBuilder(code=" + this.code + ", description=" + this.description + ")";
        }
    }
}
