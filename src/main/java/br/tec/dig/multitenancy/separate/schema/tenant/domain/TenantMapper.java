package br.tec.dig.multitenancy.separate.schema.tenant.domain;

import java.util.Optional;

import br.tec.dig.multitenancy.separate.schema.database.model.tenant.Tenant;
import br.tec.dig.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

interface TenantMapper {

	default TenantDTO mapToDto(Tenant tenant) {
		return TenantDTO.builder()
				.tenantId(tenant.getTenantId())
				.schemaName(tenant.getSchemaName())
				.dataBaseUrl(tenant.getDataBaseUrl())
				.user(tenant.getUserDb())
				.password(tenant.getPasswordDb())
				.build();
	}
	
	default Tenant mapToEntity(TenantDTO dto) {
		return Tenant.builder()
				.tenantId(dto.getTenantId())
				.schemaName(dto.getSchemaName())
				.dataBaseUrl(dto.getDataBaseUrl())
				.userDb(dto.getUser())
				.passwordDb(dto.getPassword())
				.build();
	}

}
