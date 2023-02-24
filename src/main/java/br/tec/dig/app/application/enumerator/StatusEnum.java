package br.tec.dig.app.application.enumerator;

import lombok.Getter;

@Getter
public enum StatusEnum {

   ATIVO("ATIVO"),
   INATIVO("INATIVO");

   private final String descricao;

   StatusEnum(String descricao) {
      this.descricao = descricao;
   }
}
