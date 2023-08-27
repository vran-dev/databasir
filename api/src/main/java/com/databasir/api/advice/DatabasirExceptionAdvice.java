package com.databasir.api.advice;

import com.databasir.common.DatabasirException;
import com.databasir.common.JsonData;
import com.databasir.common.SystemException;
import com.databasir.common.exception.Forbidden;
import com.databasir.common.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class DatabasirExceptionAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(
        ConstraintViolationException constraintViolationException, WebRequest request) {

        String errorMsg = "";
        String path = getPath(request);
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        for (ConstraintViolation<?> item : violations) {
            errorMsg = item.getMessage();
            log.warn("ConstraintViolationException, request: {}, exception: {}, invalid value: {}",
                path, errorMsg, item.getInvalidValue());
            break;
        }
        return handleNon200Response(errorMsg, HttpStatus.BAD_REQUEST, path);
    }

    @ExceptionHandler({InvalidTokenException.class})
    protected ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex,
                                                                 WebRequest request,
                                                                 Locale locale) {
        String path = getPath(request);
        log.warn("handle InvalidTokenException " + path + ", " + ex);
        String msg = messageSource.getMessage(ex.getErrCode(), ex.getArgs(), locale);
        JsonData<Object> data = JsonData.error(ex.getErrCode(), msg);
        return handleNon200Response(ex.getMessage(), HttpStatus.UNAUTHORIZED, path, data);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        String path = getPath(request);
        log.warn("handle illegalArgument " + path, ex);
        return handleNon200Response(ex.getMessage(), HttpStatus.BAD_REQUEST, path);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String path = getPath(request);
        log.warn("AccessDeniedException, request: {}, exception: {}", path, ex.getMessage());
        return handleNon200Response(ex.getMessage(), HttpStatus.FORBIDDEN, path);
    }

    @ExceptionHandler({Forbidden.class})
    protected ResponseEntity<Object> handleForbiddenException(Forbidden ex, WebRequest request) {
        String path = getPath(request);
        log.warn("Forbidden, request: {}, exception: {}", path, ex.getMessage());
        return handleNon200Response(ex.getMessage(), HttpStatus.FORBIDDEN, path);
    }

    @ExceptionHandler(value = DatabasirException.class)
    public ResponseEntity<Object> handleBusinessException(DatabasirException databasirException,
                                                          WebRequest request,
                                                          Locale locale) {

        String path = getPath(request);
        String msg = messageSource.getMessage(databasirException.getErrCode(), databasirException.getArgs(), locale);
        JsonData<Void> body = JsonData.error(databasirException.getErrCode(), msg);
        if (databasirException.getCause() == null) {
            log.warn("BusinessException, request: {}, exception: {}", path, msg);
        } else {
            log.warn("BusinessException, request: " + path, databasirException);
        }
        return ResponseEntity.ok()
            .header("X-Error-Code", databasirException.getErrCode())
            .body(body);
    }

    @ExceptionHandler({SystemException.class})
    public ResponseEntity<Object> handleSystemException(SystemException systemException, WebRequest request) {

        String path = getPath(request);
        if (systemException.getCause() != null) {
            log.error("SystemException, request: " + path
                + ", exception: " + systemException.getMessage() + ", caused by:", systemException.getCause());
        } else {
            log.error("SystemException, request: " + path + ", exception: " + systemException.getMessage());
        }
        return handleNon200Response(systemException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, path);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUnspecificException(Exception ex, WebRequest request) {

        String path = getPath(request);
        String errorMsg = ex.getMessage();
        log.error("Unspecific exception, request: " + path + ", exception: " + errorMsg + ":", ex);
        return handleNon200Response(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR, path);
    }

    @Override
    public ResponseEntity<Object> handleBindException(
        BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String errorMsg = buildMessages(ex.getBindingResult());
        log.warn("BindException, request: {}, exception: {}", getPath(request), errorMsg);
        return handleOverriddenException(ex, headers, status, request, errorMsg);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String errorMsg = buildMessages(ex.getBindingResult());
        log.warn("MethodArgumentNotValidException, request: {}, exception: {}", getPath(request), errorMsg);
        return handleOverriddenException(ex, headers, status, request, errorMsg);
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(
        TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.warn("TypeMismatchException, request: {}, exception: {}", getPath(request), ex.getMessage());
        return handleOverriddenException(ex, headers, status, request, ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.warn("MissingServletRequestParameterException, request: {}, exception: {}",
            getPath(request), ex.getMessage());
        return handleOverriddenException(ex, headers, status, request, ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestPart(
        MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.warn("MissingServletRequestPartException, request: {}, exception: {}", getPath(request), ex.getMessage());
        return handleOverriddenException(ex, headers, status, request, ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String errorMsg = ex.getMostSpecificCause().getMessage();

        log.warn("HttpMessageNotReadableException, request: {}, exception: {}", getPath(request), errorMsg);
        return handleOverriddenException(ex, headers, status, request, errorMsg);
    }

    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(
        ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.warn("ServletRequestBindingException, request: {}, exception: {}", getPath(request), ex.getMessage());
        return handleOverriddenException(ex, headers, status, request, ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String errorMsg = ex.getMessage();
        log.warn("HttpRequestMethodNotSupportedException, request: {}, exception: {}", getPath(request), errorMsg);
        return handleOverriddenException(ex, headers, status, request, ex.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class})
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        String path = getPath(request);
        JsonData<Void> body = JsonData.error("-1", "用户名或密码错误");
        log.warn("BadCredentialsException, request: {}, exception: {}", path, ex.getMessage());
        return ResponseEntity.ok().body(body);
    }

    private String buildMessages(BindingResult result) {

        StringBuilder resultBuilder = new StringBuilder();
        List<ObjectError> errors = result.getAllErrors();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String fieldName = fieldError.getField();
                String fieldErrMsg = fieldError.getDefaultMessage();
                resultBuilder.append(fieldName).append(" ").append(fieldErrMsg);
            }
        }
        return resultBuilder.toString();
    }

    private ResponseEntity<Object> handleNon200Response(String errorMsg, HttpStatus httpStatus, String path) {
        return ResponseEntity.status(httpStatus).body(null);
    }

    private ResponseEntity<Object> handleNon200Response(String errorMsg,
                                                        HttpStatus httpStatus,
                                                        String path,
                                                        Object body) {
        return ResponseEntity.status(httpStatus).body(body);
    }

    private ResponseEntity<Object> handleOverriddenException(
        Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, String errorMsg) {
        return handleExceptionInternal(ex, null, headers, status, request);
    }

    private String getPath(WebRequest request) {
        String description = request.getDescription(false);
        return description.startsWith("uri=") ? description.substring(4) : description;
    }
}
