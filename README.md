# Nearby Beer

Uma api em graphql para encotrar os ponto de vendas (de cervejas) mais próximo dado uma latitude e longitude. 

## Pré-requisitos
* Java 8
* Maven
* Docker
* Docker Compose
* Posgresql com Postgis para dados espaciais 

**(Todos os comandos citados abaixo devem ser executados na raíz do projeto)**

Caso não tenha um postgres instalado e devidamente configurado. Podemos rodar-lo através do 
__docker-compose__ com o seguinte comando: `docker-compose up`.  O arquivo __docker-compose.yml__ criar duas bases de 
dados (uma para a aplicação e uma para rodar os testes) com postgis habilitado



### Rodando localmente 


Para rodar localmente precisamos de uma instância do postgres com a extensão postgis habilitada.

As informações de conexão com o banco de dados são lidas a partir de váriaveis de ambiente: 

(A aplicação já tem um valor padrão para cada váriavel de ambiente caso a váriavel não seja definida.) 
 
* POSTGRES_HOST (default localhost)
* POSTGRES_PORT (default 5432)
* POSTGRES_DATABASE (default nearby_beer)
* POSTGRES_USER (default ze_delivery)
* POSTGRES_PASSWORD (default s3cr3t)

Com o banco de dados rodando podemos executar o seguinte comando: `mvn spring:run`

A aplicação vem com o __graphiql__ habilitado para acessa-lo navegue para `http://localhost:8080/graphiql` 



### Deploy
Para o deploy precisamos empacotar a aplicação. 

Antes de empacotar precisamos configuarar as informações de conexão com banco de dados de teste. 

Pois durante o empacotamento da aplicação (mais especificamente na fase de testes), 
precisamos de uma instância do banco de dados rodando
para que os testes de integração/aceitação possam ser executados.

Assim como fizemos para rodar localmente, as informações de conexão com o banco de dados são lidas a partir de váriaveis de ambiente: 

(A aplicação já tem um valor padrão para cada váriavel de ambiente caso a váriavel não seja definida.) 
 
* POSTGRES_TEST_HOST (default localhost)
* POSTGRES_TEST_PORT (default 5432)
* POSTGRES_TEST_DATABASE (default nearby_beer_test)
* POSTGRES_TEST_USER (default ze_delivery)
* POSTGRES_TEST_PASSWORD (default s3cr3t)

Com o banco de dados de teste rodando podemos executar o seguinte comando: `mvn clean package`

Ao empacotar nossa aplicação teremos uma imagem docker com o seguinte nome (repositório) **wilinando/nearby-beer**.
Essa imagem contém duas tags: **latest** e **0.0.1-SNAPSHOT**.



Com posse dessa imagem podemos utilizar qualquer provedor de cloud para deployar nosso container.


#### Extras
Também estou disponibilizando um arquivo _docker-compose-production.yml_, caso já tenha a imagem  **wilinando/nearby-beer:latest**, 
basta rodar o comando `docker-compose -f docker-compose-production.yml up`. Esse já executa o banco de dados e a aplicação.
