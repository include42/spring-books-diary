package com.booksdiary.global.error;

import com.booksdiary.global.error.exception.BusinessException;
import com.booksdiary.global.error.exception.EntityNotFoundException;
import com.booksdiary.global.error.exception.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.nio.file.AccessDeniedException;

/**
 * GlobalExceptionHandler는 다양한 예외에 대한 핸들링 작업을 수행한다.
 * Exception을 상속받은 모든 클래스에 대해 대응하며, ErrorResponse를 ResponseEntity에 담아 반환한다.
 * <p>
 * ref https://cheese10yun.github.io/spring-guide-exception/
 *
 * @Author Geonhee lee
 */
@Slf4j(topic = "ERROR_FILE_LOGGER")
@RestControllerAdvice
public class GlobalExceptionHandler {
    // TODO: 2021/03/05 예외 클래스 정보와 에러 메시지를 얼마정도 전달해줘야 할까? 고민해보기

    /**
     * handleMethodArgumentNotValidException메서드는 javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생한다.
     *
     * @param e 전달받은 MethodArgumentNotValidException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleBindException은 ModelAttribute 으로 binding error가 발생할 시 호출된다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     *
     * @param e 전달받은 BindException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleMethodArgumentTypeMismatchException 메서드는 enum type이 일치하지 않아 binding을 못할 경우 호출된다.
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생한다.
     *
     * @param e 전달받은 MethodArgumentTypeMismatchException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        final ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleHttpRequestMethodNotSupportedException 메서드는 지원하지 않은 HTTP method를 호출 할 경우 발생한다.
     *
     * @param e 전달받은 HttpRequestMethodNotSupportedException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * handleAccessDeniedException 메서드는 Authentication 객체가 필요한 권한을 보유하지 않은 경우 호출된다.
     *
     * @param e 전달받은 AccessDeniedException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
    }

    /**
     * handleWebClientRequestException은 WebClient 요청 도중 예외 발생시 호출된다.
     *
     * @param e 전달받은 WebClientRequestException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(WebClientRequestException.class)
    protected ResponseEntity<ErrorResponse> handleWebClientRequestException(WebClientRequestException e) {
        log.error("handleWebClientRequestException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.WEBCLIENT_REQUEST_ERROR);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleEntityNotFoundException은 특정 엔티티를 조회/검색하는 도중 관련 예외 발생시 호출된다.
     *
     * @param e 전달받은 EntityNotFoundException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException e) {
        log.error("handleEntityNotFoundException", e);
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    /**
     * handleInvalidValueException은 잘못된 값이 비즈니스 로직에서 도출되어 예외 발생시 호출된다.
     *
     * @param e 전달받은 InvalidValueException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(InvalidValueException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidValueException(final InvalidValueException e) {
        log.error("handleInvalidValueException", e);
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    /**
     * handleBusinessException 메서드는, 비즈니스 로직에서의 예외 발생 시 호출된다.
     * InvalidValueException과 EntityNotFoundException은 BusinessException을 상속받아 구현한 예외 객체이다.
     *
     * @param e 전달받은 BusinessException 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     * @see InvalidValueException
     * @see EntityNotFoundException
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    /**
     * handleException 메서드는, 예외가 발생했는데 해당 예외에 대한 핸들링이 이루어지지 않을 경우 호출된다.
     * 500 에러를 발생시키고, 해당 내용을 로깅 및 전달한다.
     *
     * @param e 전달받은 Exception 예외 객체이다.
     * @return ErrorResponse 객체를 ResponseEntity에 담아 반환한다.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
