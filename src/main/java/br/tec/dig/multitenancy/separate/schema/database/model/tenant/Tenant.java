package br.tec.dig.multitenancy.separate.schema.database.model.tenant;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema="public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
	@Id
	private String tenantId;
	private String schemaName;
	private String dataBaseUrl;
	private String userDb;
	private String passwordDb;
}
