package br.tec.dig.app.exception;

import br.tec.dig.app.exception.EnumException;
import br.tec.dig.app.exception.error.ErrorObject;
import br.tec.dig.app.exception.error.ErrorResponse;
import br.tec.dig.multitenancy.separate.schema.security.domain.CredentialsException;
import lombok.AllArgsConstructor;
import org.hibernate.PersistentObjectException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@AllArgsConstructor
public class ExcetionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;

	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (ex.getCause() instanceof EnumException) {
			return enumException((EnumException) ex.getCause(), headers, status, request);
		}

		String mensagem = messageSource.getMessage("mensagem.parametro-invalido", null, LocaleContextHolder.getLocale());

		ErrorResponse error = new ErrorResponse(mensagem, status.value(),status.name(),"", null);
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	//NoHandlerFoundException


	/**
	 * Tratamento para parâmentro obrigatório
	 */
	@Override
	protected  ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,WebRequest request) {
		String mensagem = "O parâmetro '" + ex.getParameterName().toUpperCase() + "' é obrigatório.";

		ErrorResponse error = new ErrorResponse(mensagem, status.value(),status.name(),"", null);
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@Override
	protected  ResponseEntity<Object> handleTypeMismatch( TypeMismatchException ex,  HttpHeaders headers,
														  HttpStatus status,  WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
	}

	/**
	 * Tratamento para parâmentro de URL incompatíveis
	 * 1. MethodArgumentTypeMismatchException é um subtipo de TypeMismatchException
	 * 2. ResponseEntityExceptionHandler já trata TypeMismatchException de forma mais abrangente
	 * 3. Então, especializamos o método handleTypeMismatch e verificamos se a exception
	 * é uma instância de MethodArgumentTypeMismatchException
	 */
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
																	HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagem = String.format(
				"O parâmetro de URL '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

		ErrorResponse error = new ErrorResponse(mensagem, status.value(),status.name(),"", null);
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	/**
	 * Tratamento para urls não mapeadas
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		String message = "Método de solicitação '" + ex.getMethod() + "' não suportado";
		ErrorResponse error = new ErrorResponse(message, status.value(), status.name(), "", null);
		return handleExceptionInternal(ex,error,headers,status,request);
	}

	private ResponseEntity<Object> enumException(EnumException ex, HttpHeaders headers, HttpStatus status,
												 WebRequest request) {
		ErrorObject error = new ErrorObject(ex.getValue().toString(), ex.getOriginalMessage(), null);
		List<ErrorObject> errors = List.of(error);
		String objectName = Arrays.stream(ex.getTargetType().getName().split("\\.")).reduce((first, second) -> second).orElse("");
		ErrorResponse errorResponse = new ErrorResponse("Requisição possui campos inválidos", status.value(),
				status.getReasonPhrase(), objectName, errors);
		return handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	@ExceptionHandler({CredentialsException.class})
	public ResponseEntity<Object> handleInvalidTokenException(CredentialsException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErrorResponse error = new ErrorResponse("Token de acesso inválido", status.value(), status.name(), "", null);
		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String mensagem = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());

		HttpStatus status = HttpStatus.BAD_REQUEST;

		ErrorResponse errorResponse = new ErrorResponse(mensagem, status.value(), status.getReasonPhrase(), "", null);
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

	@ExceptionHandler({PersistentObjectException.class})
	public ResponseEntity<Object> persistentObjectException(PersistentObjectException ex, WebRequest request) {
		String mensagem = messageSource.getMessage("recurso.detached", null, LocaleContextHolder.getLocale());

		HttpStatus status = HttpStatus.BAD_REQUEST;

		ErrorResponse errorResponse = new ErrorResponse(mensagem, status.value(), status.getReasonPhrase(), "", null);
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

	/**
	 * Metodo que valida campos
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ErrorObject> errors = getErrors(ex);
		ErrorResponse errorResponse = getErrorResponse(ex, status, errors);
		return new ResponseEntity<>(errorResponse, status);
	}

	private ErrorResponse getErrorResponse(MethodArgumentNotValidException ex, HttpStatus status, List<ErrorObject> errors) {
		return new ErrorResponse("Requisição possui campos inválidos", status.value(),
				status.getReasonPhrase(), ex.getBindingResult().getObjectName(), errors);
	}

	private List<ErrorObject> getErrors(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream()
				.map(error -> new ErrorObject(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
				.collect(Collectors.toList());
	}
}