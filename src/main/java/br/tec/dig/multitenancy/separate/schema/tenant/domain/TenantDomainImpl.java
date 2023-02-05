package br.tec.dig.multitenancy.separate.schema.tenant.domain;

import java.util.List;

import br.tec.dig.multitenancy.separate.schema.database.model.tenant.Tenant;
import org.springframework.stereotype.Component;

import br.tec.dig.multitenancy.separate.schema.tenant.TenantContext;
import br.tec.dig.multitenancy.separate.schema.tenant.TenantDomain;
import br.tec.dig.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class TenantDomainImpl implements TenantDomain {
	
	private final TenantService service;

	@Override
	public boolean setTenantInContext(String tenantId) {
		TenantDTO tenant = service.findByTenantId(tenantId);
		TenantContext.setCurrentTenant(tenant);
		return true;
	}

	@Override
	public Tenant createNewTenant(TenantDTO dto) {
		return service.createNewTenant(dto);
	}

	@Override
	public List<TenantDTO> getAllTenants() {
		return service.findAllTenants();
	}

}
