package br.tec.dig.app.application.database.model.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity{

	private String name;
	private String description;
	private BigDecimal price;

}
