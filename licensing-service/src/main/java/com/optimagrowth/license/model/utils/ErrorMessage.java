package com.optimagrowth.license.model.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Defines a single error message payload.
 * Used within the RestErrorList.
 */
@Getter @Setter @ToString
public class ErrorMessage {
    private String message;
    private String detail;
    private String code;

    public ErrorMessage(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public ErrorMessage(String message, String detail, String code) {
        this.message = message;
        this.detail = detail;
        this.code = code;
    }
}
