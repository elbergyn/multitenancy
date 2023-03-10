package br.tec.dig.multitenancy.separate.schema._configuration.hibernate.multitenancy;

import java.util.Optional;

import br.tec.dig.multitenancy.separate.schema.tenant.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import br.tec.dig.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		return getTenantIdentifier()
				.orElse(TenantContext.DEFAULT_TENANT_ID);
	}

	private Optional<String> getTenantIdentifier() {
		return Optional.ofNullable(TenantContext.getCurrentTenant())
				.map(TenantDTO::getTenantId);
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
