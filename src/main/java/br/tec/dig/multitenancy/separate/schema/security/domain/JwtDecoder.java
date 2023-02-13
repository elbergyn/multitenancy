package br.tec.dig.multitenancy.separate.schema.security.domain;

import br.tec.dig.multitenancy.separate.schema.security.model.JwtClaim;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;


public class JwtDecoder {
	private static final String AUTHORIZATION = "Authorization";

	private final String jwtToken;
	
	public JwtDecoder(HttpServletRequest request) {
		this.jwtToken = Optional.ofNullable(request)
				.map(req -> req.getHeader(AUTHORIZATION))
				.map(token -> token.trim())
	                .orElseThrow(() -> {
	                    return new CredentialsException("Missing Authentication Token");
	                });
	}
	
	
	public String getJwtParameter(JwtClaim jwtClaim) {
		return Optional.ofNullable(getSignedJWT())
				.map(this::getJWTClaimsSet)
				.map(this::validateExpiration)
				.map(JWTClaimsSet::getClaims)
				.map(stringObjectMap -> stringObjectMap.get(jwtClaim.getValue()))
				.map(Object::toString)
				.orElseThrow(() -> new CredentialsException("Tenant not defined"));
	}

	private SignedJWT getSignedJWT() {
		 try {
			 String token = jwtToken.replace("Bearer ","");
			 return SignedJWT.parse(token);
		} catch (ParseException e) {
			throw new CredentialsException("Cannot parse jwt token");
		}
	}

	private JWTClaimsSet validateExpiration(JWTClaimsSet jwtClaimsSet){
		Date expiration = jwtClaimsSet.getExpirationTime();
		if(expiration.before(new Date())){
			throw new CredentialsException("Expired token, reauthenticate");
		}
		return jwtClaimsSet;
	}
	
	private JWTClaimsSet getJWTClaimsSet(SignedJWT signedJWT) {
		try {
			return signedJWT.getJWTClaimsSet();
		} catch (ParseException e) {
			throw new CredentialsException("Canot exctract Claim Set from jwt token");
		}
	}
	
}
