# Projeto Multi Tenancy

Projeto que poderá ser utilizado como base para desenvolvimento de aplicação multi tenancy utilizando flyway para controle de alteração de banco de dados utilizando schema no controle e distinção dos dados entre tenancy.

# Tecnologias empregadas

### * JAVA
### * SPRING BOOT 
### * SPRING SECURITY
### * FLYWAY
### * MAVEN
### * OAUTH2
### * Docker
### * Keycloak 15.0.0
<br>

# Configuração

> Deverá conter no token o parametro de usuário "tenant" que deverá ser o nome da base de dados.

> Para segurança deve ser adicionado roles "admin" e "user" para realizar testes nos endpoints

> Poderá realizar o import do arquivo realm-export.json para o keycloak assim como o start do keycloak a partir do docker compose que se encontra na pasta keycloak.
