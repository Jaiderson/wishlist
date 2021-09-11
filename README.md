# wishlist-api
Desafío de la codificación que consiste en la implementación de una aplicación web que expone una API REST- ful para crear listas de deseos de libros para diferentes usuarios.

## Estructura del proyecto
```
wishlist-spring-boot-jwt/
 │
 ├── src/main/java/books
 │   └── wishlist
 │       ├── configuration
 │       │   └── SwaggerConfiguration.java
 │       │
 │       ├── controller
 │       │   └── RolController.java
 │       │   └── UsuarioController.java
 │       │   └── LibroController.java
 │       │   └── ListaDeseoController.java
 │       │
 │       ├── dto
 │       │   └── ItemDto.java
 │       │   └── TokenDto.java
 │       │   └── LibroNuevoDto.java
 │       │   └── VolumeInfoDto.java
 │       │   └── LoginUsuarioDto.java
 │       │   └── NuevoUsuarioDto.java
 │       │   └── RespuestaLibroDto.java
 │       │   └── LibroExistenteDto.java
 │       │   └── CambioClaveUsuarioDto.java
 │       │   └── BusquedaParametricaDto.java
 │       │
 │       ├── entities
 │       │   └── Rol.java
 │       │   └── ERol.java
 │       │   └── Libro.java
 │       │   └── Usuario.java
 │       │   └── ListaDeseo.java
 │       │   └── ItemListaLibro.java
 │       │
 │       ├── repositories
 │       │   └── IRolRep.java
 │       │   └── ILibroRep.java
 │       │   └── IUsuarioRep.java
 │       │   └── IListaDeseoRep.java
 │       │   └── ItemListaLibroRep.java
 │       │
 │       ├── security
 │       │   └── SecurityConguration.java
 │       │   ├── jwtokens
 │       │       └── PuntoEntrada.java
 │       │       └── ValidarToken.java
 │       │       └── ProveedorToken.java
 │       │       └── UsuarioAutorizado.java 
 │       │
 │       ├── services
 │       │   └── IRolServie.java
 │       │   └── ILibroServie.java
 │       │   └── IUsuarioServie.java
 │       │   └── IListaDeseoServie.java
 │       │   └── ItemListaLibroServie.java
 │       │   ├── clients
 │       │       └── ClienteLibro.java
 │       │       └── ETipoBusqueda.java
 │       │   ├── implemtations
 │       │       └── RolServieImpl.java
 │       │       └── LibroServieImpl.java
 │       │       └── UsuarioServieImpl.java
 │       │       └── ListaDeseoServieImpl.java
 │       │       └── UserDetailsServiceImpl.java
 │       │       └── ItemListaLibroServieImpl.java
 │       │
 │       ├── utils
 │       │   ├── Dato.java
 │       │   └── Util.java
 │       │   └── Consola.java
 │       │   └── MensajeError.java
 │       │   └── MensajeRespuesta.java 
 │       │
 │       └── WishlistApplication.java
 │
 ├── src/main/resources/
 │   ├── sql
 │   │    └── roles.sql
 │
 ├── src/test/java/
 │   └── wishlist
 │       ├── services
 │       │   └── RolServiceTest.java
 │       │   └── LibroServiceTest.java
 │       │   └── ProveedorObjetos.java
 │       │   └── UsuarioServiceTest.java
 │       │   └── ListaDeseoServiceTest.java
 │       │   
 │       WishlistApplicationTests
 │       │   
 │       │   
 ├──
 ├── Dokerfile
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
Para acceder a la documentación del API wishlist por favor utilice el siguiente link: 
<a href="https://wishlist-api-meli.herokuapp.com/wishlist-api/v2/swagger-ui.html">Documentacion API wishlist</a>

### Autenticacion Bearer Token
``` Peticion get ..  ``` 

## License

<a href="https://github.com/Jaiderson/wishlist">Repositorio Git API wishlist</a>

**Free Software**
