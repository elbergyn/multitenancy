package br.tec.dig.multitenancy.separate.schema.security.domain;

@SuppressWarnings("serial")
public class CredentialsException extends RuntimeException{
	public CredentialsException(String message) {
		super(message);
	}
}
