package br.tec.dig.multitenancy.separate.schema._configuration.spring.multitenancy;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.tec.dig.multitenancy.separate.schema.tenant.TenantContext;
import br.tec.dig.multitenancy.separate.schema.tenant.TenantDomain;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import br.tec.dig.multitenancy.separate.schema.security.domain.SecurityDomain;

@Component
public class TenantRequestInterceptor implements AsyncHandlerInterceptor{
	
	private SecurityDomain securityDomain;
	private TenantDomain tenantDomain;
	
	public TenantRequestInterceptor(SecurityDomain securityDomain, TenantDomain tenantDomain) {
		this.securityDomain = securityDomain;
		this.tenantDomain = tenantDomain;
	}

	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		 return Optional.ofNullable(request)
				 .map(securityDomain::getTenantIdFromJwt)
				 .map(tenantDomain::setTenantInContext)
				 .orElse(false);
	    }

	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	        TenantContext.clear();
	    }
	    
}

