# start-trek
# OrangeRoute-Oracle

## Tecnologias Usadas

<div style="display: inline_block"><br> 
  <img aling="center" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
</div>

## INTEGRANTES
### Jhonantan Quispe Torrez
[![Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white
)](https://www.linkedin.com/in/jhonatan-quispe-torrez-360b60198/)[![Github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/JhowQT)
### Julia Damasceno Busso
[![Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white
)](https://www.linkedin.com/in/jhonatan-quispe-torrez-360b60198/)[![Github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/JhowQT)
### Gabriel Gomes
[![Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white
)](https://www.linkedin.com/in/jhonatan-quispe-torrez-360b60198/)[![Github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/JhowQT)

**Jhonatan Quispe Torrez — Java & Banco de Dados**

`Responsável pelo backend em Java/Spring Boot e pela modelagem relacional no Oracle. Implementou Entities com JPA/Hibernate, Services/Repositories e endpoints REST, além da configuração de persistência, testes de API (Postman) e documentação Swagger.`

**Gabriel Gomes — Advanced Business Development with .NET & DevOps/Cloud**

`Responsável pelo desenho de arquitetura .NET (camadas, DTOs, Repositórios) e boas práticas de Clean Architecture. No pilar DevOps & Cloud, cuidou de Docker/Docker Compose, organização de CI/CD, e provisionamento/ajustes de infraestrutura em nuvem para deploy e observabilidade.`

**Julia Bussinos — Mobile App Development & Compliance & QA**

`Responsável pelo app mobile (React Native/Expo), navegação entre telas, integração com a API e protótipo funcional. No pilar Compliance & Quality Assurance, estruturou a documentação de escopo/visão, critérios de qualidade, e evidências para validação das entregas.`
_____________________________________________________________________________________________________

Como rodar
primeiro garanta que você tem o git e o docker instalados na sua maquina, assim como ter o docker desktop aberto
```bash
# 1. Clonar o repositório
git https://github.com/JhowQT/start-trek.git .

# 2. Construir a imagem Docker
docker build -t="start-trek" .

# 3. Executar o container
docker run -p 8080:8080 -it --rm start-trek
```
_____________________________________________________________________________________________________
🧩 Visão Geral

Start Trek é um sistema desenvolvido para explorar e apresentar diferentes categorias de profissões — antigas, atuais e futuras — destacando como a tecnologia impacta cada uma delas. O projeto tem como objetivo servir como uma plataforma educativa e informativa, ajudando usuários a compreender tendências do mercado, transformações tecnológicas e novas carreiras emergentes.

O sistema foi construído com Java Spring Boot no back-end, integrado diretamente ao banco de dados Oracle Developer, garantindo robustez, segurança e alta consistência dos dados. O projeto conta com operações CRUD completas para usuários, trilhas de carreira, trabalhos e comentários, além de controle de permissões e perfis de acesso.

______________________________________________________________________________________________________

<details>
  <summary>📘 MER - Modelo Entidade Relacionamento</summary>

  ![MER](https://github.com/JhowQT/start-trek/issues/1#issue-3653129261)

  _Figura: MER do sistema._
</details>

<details>
  <summary>📗 DER - Diagrama Entidade Relacionamento</summary>

  ![DER](https://github.com/JhowQT/start-trek/issues/2#issue-3653132589)

  _Figura: DER do sistema._
</details>



## A Start-Trek API fornece endpoints para gerenciamento de:

``TIPO_USUARIO``
``USUARIO``
``ESP32``
``TRABALHO``
``COMENTARIO``
``CATEGORIA``

-- *Arquitetura em camadas Controller → Service → Repository → Entity, com JPA/Hibernate.*

### COMO USAR OS ENDPOINTS

- **USUARIO** -
> -
> - Busca todos os usuarios **GET**`localhost:8080:usuario` 
> - Busca usuarios por id **GET**`localhost:8080:usuario/{id}` 
> - Cria um usuario **POST**`localhost:8080:usuario`
> - Atualiza por ID **PUT**`localhost:8080:usuario/{id}`
> - Atualiza foto **PUT**`localhost:8080:usuario/{id}/foto`
> - Deleta por ID **DELETE**`localhost:8080:usuario/{id}`
> - `IdTipoUsuario: 1 para admin e 2 para user, apenas esses dois dados são aceitos para tipo_usuario`
> 
#### 📥 Corpo da Requisição POST (JSON esperado)
```json
{
  "nomeUsuario": "Thaisa Mendes",
  "email": "thaisa.mendes@example.com",
  "senha": "123456",
  "idTipoUsuario": 2
}
```
_______________________________________________________________________________________________________

- **TIPO - USUARIOS** -
> -
> - Busca todos os tipo-usuarios **GET**`localhost:8080:tipos-usuario` 
> - Busca tipo-usuarios por id **GET**`localhost:8080:tipo-usuario/{id}`
> - Busca tipo-usuarios por id **GET**`localhost:8080:tipo-usuario/todos-tipos`  
> -

_______________________________________________________________________________________________________

- **TRABALHOS** -
> -
> - Busca todos os trabalho **GET**`localhost:8080:trabalhos`  
> - Busca trabalho ID de usuario **GET**`localhost:8080:trabalhos/usuario/{idUsuario}`
> - Busca trabalho por categoria **GET**`localhost:8080:trabalho/categoria/{idCategoria}`
> - Modifica trabalho por ID **PUT**`localhost:8080/trabalhos/{id}`
> - Cria favorito **POST**`localhost:8080:trabalhos`
> - Deleta por ID **DELETE**`localhost:8080:favotiros/{idFavorito}`
> -

#### 📥 Corpo da Requisição POST (JSON esperado)
```json
{
  "idUsuario": 2,
  "idTrilhaCarreira": 5
}
```
_______________________________________________________________________________________________________

- **COMENTARIOS** -
> -
> - Busca todos os comentarios **GET**`localhost:8080:comentarios` 
> - Busca ID de Trilha **GET**`localhost:8080:comentarios/trabalho/{idTrilha}`
> - Cria Comentario **POST**`localhost:8080:comentarios`
> -

#### 📥 Corpo da Requisição POST (JSON esperado)
```json
{
  {
  "conteudoComentario": "string",
  "idTrabalho": 0
}

}
```
_______________________________________________________________________________________________________


- **CATEGORIA** -
> -
> - Busca todos os tags **GET**`localhost:8080:categorias` 
> - Busca por ID **GET**`localhost:8080:categorias/{id}`
> - Cria categoria **POST**`localhost:8080:categorias`
> - Modifica categoria por ID **PUT**`localhost:8080:categoria/{id}`
> - Busca Id por Trilha **DELETE**`localhost:8080:categoria/{id}`
> -

________________________________________________________________________________________________________

- **LOGIN** -
> -
> - Post Login **POST**`localhost:8080:auth/login` 
> -
#### 📥 Corpo da Requisição POST (JSON esperado)
```json
{
  "email": "thaisa.mendes@example.com",
  "senha": "123456"
}
```
________________________________________________________________________________________________________

📚 Documentação dos Endpoints (Swagger/OpenAPI)

Observação: Swagger ainda será analisado e integrado. Assim que habilitar:

UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

________________________________________________________________________________________________________

🧪 Como Executar Localmente
Pré-requisitos

JDK 17+

Maven 3.9+

Oracle Database (acesso e credenciais)

Configuração de Banco

Crie/edite src/main/resources/application.properties:

spring.datasource.url=jdbc:oracle:thin:@//<host>:<port>/<service_name>
spring.datasource.username= `rm560601`
spring.datasource.password=`040301`
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.Oracle12cDialect
server.port=8080

Executando
# Clonar o repositório
- git clone https://github.com/JhowQT/start-trek.git
- cd start-trek
- code .


# Rodar a aplicação
mvn spring-boot:run
# ou
mvn clean package && java -jar target/starttrek=-*.jar

## ACESSO AO BD
- Para iniciar o projeto insira o acesso
- campo de login **rm560601**
- campo de senha **040301**

Aplicação disponível em:
- http://localhost:8080/ `aqui colocar as rotas detalhas no topo para acessar os dados do end-points`
- http://localhost:8080//swagger-ui/index.html `vizualização de todas as rotas no swagger`

__________________________________________________________________________________________________________
## LINK YT
- https:


