package br.tec.dig.app.exception;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class EnumException extends InvalidFormatException{
    public EnumException(JsonParser p, String msg, Object value, Class<?> targetType) {
        super(p, msg, value, targetType);
    }
}
