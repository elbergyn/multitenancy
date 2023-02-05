package br.tec.dig.multitenancy.separate.schema.tenant;

import br.tec.dig.multitenancy.separate.schema.database.model.tenant.Tenant;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tec.dig.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tenant")
@AllArgsConstructor
public class TenantController {

    private TenantDomain tenantDomain;

    @PutMapping
    public Tenant createTenant(@RequestBody TenantDTO dto) {
        return tenantDomain.createNewTenant(dto);
    }
}
