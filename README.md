# Desafio

## Descrição
Decidi realizar esse desafio em Java pensando como se fosse uma API back-end que serve a um front-end Angular (podendo, é claro, ser qualquer outra tecnologia que possua suporte).
 Simulando o banco de dados.

### Segurança
Essa API possui Spring Security e OAuth2 com JWT Token.
O refresh token possui a característica de vir por Cookie e ser utilizado diretamente pelo mesmo, excluindo a necessidade de envia-lo pelo Body. 

## Requisitos
Java 8 e Maven.

## Tecnologias utilizadas
- Java 8
- Spring Boot - Framework base para a API
- Spring Security, OAuth2 e JWT Token - Segurança da API
- Swagger - Documentação de API de forma dinâmica
- Junit4 e Mockito - Execução de testes

## Executando com o Maven
```sh
mvn install
```
```sh
mvn spring-boot:run
```
Ou simplesmente executar por uma IDE que tenha suporte para Spring Boot

## Instruções
- Essa API está segura e necessita de um JWT Token e uma autenticação básica para acessar seus endpoints.
- Faça uma requisição POST para /oauth/token com o Username: angular e Password: @ngul@r0, com o Body:
```sh
client:angular
username:(um dos usuários disponíveis)
password:(um dos usuários disponíveis)
grant_type:password
```
- Com o bearer access token retornado é possível acessar as endpoints de votos.
- As endpoints são detalhadas pelo Swagger em /swagger-ui.html

### Usuários
Todos os usuários possuem as mesmas permissões, sendo assim possível que todos votem e verifiquem qual foi o ganhador do dia.
```sh
admin@dbserver.com
admin
```
```sh
dev@dbserver.com
admin
```
```sh
tester@dbserver.com
admin
```

### Restaurantes
```sh
Coco bambu
```
```sh
Tudo pelo social
```
## O que fazer para melhorar a API?
- O Service não foi escrito para facilitar o desenvolvimento dos testes, acredito que caso eu utilizasse TDD seria possível escrever testes mais claros e mais objetivos.
- No início do desenvolvimento da API tive um problema em relação à API JAXB que nunca me havia acontecido antes, e para resolver tive que a incluir no pom.xml. Acredito que exista uma forma de executar minha API sem o JAXB no pom.xml.