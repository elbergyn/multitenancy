package br.tec.dig.multitenancy.separate.schema.tenant.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantDTO {

	private String tenantId;
	private String schemaName;
	private String dataBaseUrl;
	private String user;
	private String password;
}
