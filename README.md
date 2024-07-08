
# Mercado Bmx Br - Backend

Uma API feita em Java/Spring Boot com a utilização de Docker para conteinêrização, Azure Storage para armazenamento das imagens em nuvem, Spring Mail para envio de e-mails para os usuários e uso do MongoDB como banco de dados principal.

JWT como forma de gerenciamento de sessão com Token, criptografia das senhas no banco de dados usando Bcrypt para criptografar e descriptografar os dados.

Utilização de ORM do MongoDB (JPA)


## Funcionalidades

- Registro, Atulização e Deleção de dados de usuário
- Login usando token JWT e criptografia de senhas
- Variedade de filtros para os anúncios
- Sistema de favoritos
- Integração com WhatsApp
- Utilização de banco de dados NoSQL para economia de espaço de colunas e tabelas


## Stack utilizada

**Front-end:** Typescript, React Native, Expo, Axios, TailwindCSS, React Native Paper.

**Back-end:** Java, Spring Boot, Azure, Docker, MongoDB




## Variáveis de Ambiente

Para rodar esse projeto, você vai precisar adicionar as seguintes variáveis de ambiente no seu .env

`CONNECTION_STRING_AZURE_STORAGE_ACCOUNT`
`SPRING_MAIL_HOST`
`SPRING_MAIL_PORT`
`SPRING_MAIL_USERNAME`
`SPRING_MAIL_PASSWORD`
`SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH`
`SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE`
`SPRING_DATA_MONGODB_URI`
`SPRING_DATA_MONGODB_DATABASE`
`SPRING_APPLICATION_NAME`
`SPRING_MVC_FORMAT_DATE`
`SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE`
`SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE`
`SPRING_SERVLET_MULTIPART_ENABLED`
`SERVER_TOMCAT_MAX_SWALLOW_SIZE`


## Rodando localmente

**Pré-Requisito: Ter o Docker/Docker Desktop Instalado**

Clone o projeto

```bash
  git clone https://github.com/giovaniflp/MercadoBmxBr-Backend-SpringBoot
```

Entre no diretório do projeto

```bash
  cd MercadoBmxBr-Backend-SpringBoot
```

Instale as dependências

```bash
  mvn install
```

Inicie o servidor

```bash
  mvn spring-boot:run
```


## Aprendizados

Ao fazer esse projeto, aprendi do zero ao básico como fazer uma API em Spring Boot. A utilização do ORM/JPA junto com a utilização do MongoDB.

Aprendi também como conteinêrizar a aplicação e fazer o seu Deploy em algum serviço de nuvem.

Conheci como hospedar um banco de dados em um serviço gerenciado de nuvem também.

Aprendi sobre escalonamento horizontal utilizando Redis para cacheamento do servidor, para diminuir a carga sobre o servidor.

Como mandar e-mails utilizando Spring Mail e suas configurações.

Aquisição e armazenamento de imagens por meio das requisições e o form-data.

Entendi como organizar e como funciona uma estrutura MVC no Java.

## Relacionados

Frontend Mobile da aplicação

[MercadoBmxBr-Mobile-ReactNative](https://github.com/giovaniflp/MercadoBmxBr-Mobile-ReactNative)


## Autores

- [@giovaniflp](https://www.github.com/giovaniflp)

