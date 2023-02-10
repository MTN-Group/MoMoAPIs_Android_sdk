package com.momo.sdk.model;

import java.io.Serializable;

/**
 * Model for response errors
 * <br>
 * If {@link #getThrowable()} returns null, then {@link #getErrorCode()} and {@link #getErrorBody()} will give you an explanation about error.
 * <br>
 * And vice versa, if {@link #getErrorCode()} or {@link #getErrorBody()} return {@link #ERROR_CODE_UNAVAILABLE} and null accordingly, then use {@link #getThrowable()} to obtain error details.
 */
@SuppressWarnings("ALL")
public class MtnError implements Serializable {
    /**
     * The constant ERROR_CODE_UNAVAILABLE.
     */
    public static final int ERROR_CODE_UNAVAILABLE = -1;

    private int errorCode;
    private ErrorResponse errorBody;
    private Throwable throwable;


    /**
     * Instantiates a new GSMA error.
     *
     * @param errorCode the error code
     * @param errorBody the error body
     * @param throwable the throwable
     */
    public MtnError(int errorCode, ErrorResponse errorBody, Throwable throwable) {
        this.errorCode = errorCode;
        this.errorBody = errorBody;
        this.throwable = throwable;
    }

    /**
     * Gets error code.
     *
     * @return HTTP error response code (4x, 5x)
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Gets error body.
     *
     * @return Json string returned by server, containing error explanation
     */
    public ErrorResponse getErrorBody() {
        return errorBody;
    }

    /**
     * Gets throwable.
     *
     * @return {@link Throwable}, returned by connection (timeout, failed to connect etc.)
     */
    public Throwable getThrowable() {
        return throwable;
    }

    @SuppressWarnings("unused")
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @SuppressWarnings("unused")
    public void setErrorBody(ErrorResponse errorBody) {
        this.errorBody = errorBody;
    }

    @SuppressWarnings("unused")
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

}
