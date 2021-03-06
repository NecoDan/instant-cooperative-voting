 # API RESTFul WebService - Sessão Votação De Pautas
 ###### Copyright © 2021 NTCONSULT.
  Projeto em Spring Boot WebFlux de uma construção API RESTFul voltado a atender o desafio da [NTCONSULT](https://www.ntconsultcorp.com/).
   
  Uma solução criada, utilizando a stack Java em formato de API REST. Voltada para atender as demandas as sessões de votação em cooperativas, desde o processo de criação de pautas e votação destas. Onde todos os serviços devem trabalhar com JSON em suas chamadas e retornos.

 #### Stack do projeto
  - Escrito em Java 8;
  - Utilizando as facilidades e recursos framework Spring Boot/ Spring WebFlux;
  - Lombok e MapStruct na classes para evitar o boilerplate do Java;
  - Framework WebFlux integrado com MongoDB  para garantir a persistência dos dados (collections). Facilitando as operações CRUD (aumentando o nivel de desempenho e escalabilidade);
  - Boas práticas de programação, utilizando Design Patterns (Builder);
  - Testes unitários (junit, mockito, webclient test);
  - Banco de dados MongoDB;
  - Docker utilizando o compose;
  
  #### Visão Geral
  
  A aplicaçao tem como objetivo disponibilizar endpoints para consulta de informações e operações à respeito de:
  - Votos de associados em cooperativas, onde ```cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Gerenciar as sessões de votação``` de acordo com os respectivos requisitos: 
    
    - ```Cadastrar uma nova pauta;```
    - ```Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);```
    - ```Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta);``` 
    - ```Contabilizar os votos e dar o resultado da votação na pauta.``` 
  
  #### Instruções Inicialização - Projeto
    
      1. Clone o repositório https://github.com/NecoDan/instant-cooperative-voting
      
      2. Ou faça o download do arquivo ZIP do projeto em https://github.com/NecoDan/instant-cooperative-voting
          
      3. Importar o projeto em sua IDE de preferência (lembre-se, projeto baseado em Spring & Maven)
      
      4. Buildar o projeto e executá-lo.
    
 
  #### Endpoints: 
  
  Utilizando a ferramenta de documentação de endpoints ```Swagger```, pode-se visualizar todos os endpoints disponíveis. Basta acessar a documentação da API via [Swagger](http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config). 
  <br><br> Logo após inicialização da aplicação. De sorte que, segue a lista de alguns endpoints para conhecimento: 
  
  - Retornar uma lista completa de pautas existentes:
    - `http://localhost:8080/v1/pautas`
    
  - Retornar uma única pauta, a partir de um identificado único:
    - `http://localhost:8080/v1/pautas/{id}`
      
 Entre outros, aos quais podem ser identificados no endereço fornecido pelo [Swagger](http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config).


  
 #### Autor e mantenedor do projeto
   Daniel Santos Gonçalves - Bachelor in Information Systems, Federal Institute of Maranhão - IFMA / Software Developer Fullstack.
 - [GitHub](https://github.com/NecoDan)
 
 - [Linkedin](https://www.linkedin.com/in/daniel-santos-bb072321) 
 
 - [Twiter](https://twitter.com/necodaniel)
