# wishlist
Desafío de la codificación que consiste en la implementación de una aplicación web que expone una API REST- ful para crear listas de deseos de libros para diferentes usuarios

## Estructura del proyecto
```
wishlist-spring-boot-jwt/
 │
 ├── src/main/java/books
 │   └── wishlist
 │       ├── configuration
 │       │   └── SwaggerConfig.java
 │       │
 │       ├── controller
 │       │   └── UserController.java
 │       │
 │       ├── dto
 │       │   ├── UserDataDTO.java
 │       │   └── UserResponseDTO.java
 │       │
 │       ├── exception
 │       │   ├── CustomException.java
 │       │   └── GlobalExceptionController.java
 │       │
 │       ├── model
 │       │   ├── Role.java
 │       │   └── User.java
 │       │
 │       ├── repository
 │       │   └── UserRepository.java
 │       │
 │       ├── security
 │       │   ├── JwtTokenFilter.java
 │       │   ├── JwtTokenFilterConfigurer.java
 │       │   ├── JwtTokenProvider.java
 │       │   ├── MyUserDetails.java
 │       │   └── WebSecurityConfig.java
 │       │
 │       ├── service
 │       │   └── UserService.java
 │       │
 │       └── JwtAuthServiceApp.java
 │
 ├── src/main/resources/
 │   ├── sql
 |   |    └── roles.sql
 │   └── application.yml
 │
 ├── .gitignore
 ├── LICENSE
 ├── mvnw/mvnw.cmd
 ├── README.md
 └── pom.xml
```

## Comandos para ejecucion
Compilacion de artefacto, el cual queda ubicado en la carpeta target
- mvn package

genera el jar .. nombre ..

- docker build -t wishlist-docker .

con esto genera la imagen basada en jdk 8

- docker run -p 8080:8091 wishlist-docker

corre la imagen en el puerto 


## Funcionamiento de la API

### Autenticacion
``` Peticion get ..  ``` 