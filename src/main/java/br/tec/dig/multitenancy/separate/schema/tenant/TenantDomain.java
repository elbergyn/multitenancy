package br.tec.dig.multitenancy.separate.schema.tenant;

import java.util.List;

import br.tec.dig.multitenancy.separate.schema.database.model.tenant.Tenant;
import br.tec.dig.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

public interface TenantDomain {
	boolean setTenantInContext(String tenantId);
	Tenant createNewTenant(TenantDTO dto);
	List<TenantDTO> getAllTenants();
}
