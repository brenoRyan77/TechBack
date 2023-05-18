package br.com.fujideia.iesp.tecback.exception;

import java.util.List;

import br.com.fujideia.iesp.tecback.util.message.MessageBundle;
import br.com.fujideia.iesp.tecback.util.message.MessageServiceError;

public class ApplicationServiceException extends Exception{

	private static final long serialVersionUID = -4369358821782813062L;	

	private static final String MESSAGE_DEFAUT = "message.default";
	
	private Throwable rootCause = null;
	
	private Integer statusCode = 500;
	
	private List<MessageServiceError> errorList;

	
	public ApplicationServiceException(String messageKeyLoc) {
		super(MessageBundle.getMessage(messageKeyLoc));
	}
	
	
	public ApplicationServiceException(String messageKeyLoc, Integer statusCode) {
		super(MessageBundle.getMessage(messageKeyLoc));		
		this.statusCode = statusCode;
	}
	
	
	public ApplicationServiceException(String messageKeyLoc, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(messageKeyLoc));
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
		
	
	public ApplicationServiceException(String messageKeyLoc, String[] parameters) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
	}
	

	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Integer statusCode) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		this.statusCode = statusCode;
	}
	

	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
	

	public ApplicationServiceException(Throwable causa) {
		super(MessageBundle.getMessage(MESSAGE_DEFAUT));
		setRootCause(causa);
	}
	

	public ApplicationServiceException(Throwable causa, Integer statusCode) {
		super(MessageBundle.getMessage(MESSAGE_DEFAUT));
		setRootCause(causa);
		this.statusCode = statusCode;
	}
	

	public ApplicationServiceException(Throwable causa, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(MESSAGE_DEFAUT));
		setRootCause(causa);
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
		

	public ApplicationServiceException(String messageKeyLoc, Throwable causa) {
		super(MessageBundle.getMessage(messageKeyLoc));
		setRootCause(causa);
	}
	

	public ApplicationServiceException(String messageKeyLoc, Throwable causa, Integer statusCode) {
		super(MessageBundle.getMessage(messageKeyLoc));
		setRootCause(causa);
		this.statusCode = statusCode;
	}
	

	public ApplicationServiceException(String messageKeyLoc, Throwable causa, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(messageKeyLoc));
		setRootCause(causa);
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
			

	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		setRootCause(rootCause);
	}
	

	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause, Integer statusCode) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		setRootCause(rootCause);
		this.statusCode = statusCode;
	}
	

	public ApplicationServiceException(String messageKeyLoc, String[] parameters, Throwable rootCause, Integer statusCode, List<MessageServiceError> errorList) {
		super(MessageBundle.getMessage(messageKeyLoc, parameters));
		setRootCause(rootCause);
		this.statusCode = statusCode;
		this.errorList = errorList;
	}
		

	public Throwable getRootCause() {
		return rootCause;
	}
		
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public List<MessageServiceError> getErrorList() {
		return errorList;
	}
}
