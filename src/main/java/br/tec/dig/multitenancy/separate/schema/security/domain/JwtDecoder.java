package br.tec.dig.multitenancy.separate.schema.security.domain;

import br.tec.dig.multitenancy.separate.schema.security.model.JwtClaim;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


public class JwtDecoder {
	private static final String AUTHORIZATION = "Authorization";
	private static final String JWT_TOKEN_LABEL = "token";
	private static final String publicKey = "-----BEGIN CERTIFICATE-----MIICnzCCAYcCBgGGHlUaWTANBgkqhkiG9w0BAQsFADATMREwDwYDVQQDDAhmYWNpbGl0ZTAeFw0yMzAyMDQyMTI3MTlaFw0zMzAyMDQyMTI4NTlaMBMxETAPBgNVBAMMCGZhY2lsaXRlMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhz1Vi3WfWfSHx3mKkrTxLvuYk93UuphckPT8cAsbX0Nf52y87aT6ZUr+i/q8muEvb4JtTgyFU7aE05Mgxik/Yvu/BHrHe/Dt+M4/4IElm5D3IGiPltjr0CVPMvoiznI9p01inT3ESI+NHJudw+7aDJqYPCi3leSHwpqoa7uQQERwHm6bKwkkH5d2mnwpBiaQJbDH0Q+k81JEpzjZQSCDpAdyip+lD+LLyYMHATnqQfyoNIjZ2DJuvJ+/KgbNm1wNEHyszoYipAB2JYRBgyMdBuw4UVpVej1xkxZLhqQsdSnyT/iuE1bs0ahnYZveZIW4PAnIQERFQQZhEmO+bGcH9wIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQA7ei8F6BhA6Y6dPywv9WxeszTt/sMlnLXjwB5BO5jSw6DoTt0C09VHInRLOEivmFQk2fifY83M/0BLhoSrmoQmMlIRnzxhmJzW3Wr/QMqkpRNhy1aq+uUcxNVz4Mjcd2RrHYCi6aUYX2rli1e+90WPqDTrjd6qk/dsRO/ReARh5timaUciEyqcb0Wnwov1YmKZhr9C+F27xTtmpzisg86g83XOJax0UnSM/+CLOTPA6aPOpCeUsndHk4Mh+1Hp5U15fCkR0WYTJo57wqEKy2rVb67xL0n2nO/6CANCzoLymyTS+aLD8F1ozqhl2kpIq0rAiHrY39srnt1ysT5/2i7M-----END CERTIFICATE-----";
	
	private final String jwtToken;
	
	public JwtDecoder(HttpServletRequest request) {
		this.jwtToken = Optional.ofNullable(request)
					.map(req -> req.getHeader(AUTHORIZATION))
//					.filter(headerWithToken -> headerWithToken.contains(JWT_TOKEN_LABEL))
//	    			.map(headerWithToken -> headerWithToken.substring(headerWithToken.indexOf(JWT_TOKEN_LABEL)+JWT_TOKEN_LABEL.length()+1))
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
				.orElse(null);
	}


	
	private SignedJWT getSignedJWT() {
		 try {
			 String token = jwtToken.substring(7);
			return SignedJWT.parse(token);
		} catch (ParseException e) {
			throw new CredentialsException("Cannot parse jwt token");
		}
	}

	private JWTClaimsSet validateExpiration(JWTClaimsSet jwtClaimsSet){
		Date expiration = jwtClaimsSet.getExpirationTime();
		if(expiration.before(new Date())){
			throw new CredentialsException("Expired token");
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
