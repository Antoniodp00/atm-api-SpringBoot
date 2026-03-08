package com.dam.adp.atmapi.exceptions;

/**
 * Excepción personalizada para indicar que un registro no fue encontrado.
 * Contiene detalles sobre el error y el valor que causó la excepción.
 */
public class RecordNotFoundException extends RuntimeException {
    private Object exceptionDetail;
    private Object fieldValue;

    public RecordNotFoundException(String exceptionDetail, Object fieldValue) {

        super(exceptionDetail+": "+fieldValue);
        this.exceptionDetail = exceptionDetail;
        this.fieldValue = fieldValue;

    }

    public Object getExceptionDetail() {
        return exceptionDetail;
    }
    public Object getFieldValue() {
        return fieldValue;
    }

}
