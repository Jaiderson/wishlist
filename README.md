# wishlist-api
Desafío de la codificación que consiste en la implementación de una aplicación web que expone una API REST- ful para crear listas de deseos de libros para diferentes usuarios.

En esta API los usuarios se prodra n registrar, posteriormente mediante autenticacion via tocken podran:
- Crear listas de libros de deseos.
- Modificar los datos de las listas creadas tales como su nombre o su posicion.
- Eliminrar una lista de deseos. Esto eliminará los libros asociados a ella.
- Agregar libros a listas de deseos creadas. (Hay 2 opciones agregar libros ya registrados o nuevos)
- Modificar la posicion de un libro en una lista de deseos.
- Eliminiar libro de una lista de de deseos.

- Crear, modificar y eliminar Libros.
- Crear, modificar y eliminar Usuarios. (Solo admin puede eliminar usuarios)
- Crear, modificar y eliminar Roles. (Solo admin)

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
- mvn package     [Genera el jar wishlist0.0.1-SNAPSHOT.jar ]
- docker build -t wishlist-docker .  [Genera la imagen basada en jdk 8]
- docker run -p 8080:8091 wishlist-docker [Corre la imagen en el puerto 8091]

## Funcionamiento de la API
Para acceder a la documentación del API wishlist por favor utilice el siguiente link: 
<a href="https://wishlist-api-meli.herokuapp.com/wishlist-api/v2/swagger-ui.html">Documentacion API wishlist</a>

Para acceder al repositorio del proyecto se debe solicitar permiso al rpositorio provado en Github link: 
<a href="https://github.com/Jaiderson/wishlist">Repositorio Git API wishlist</a>

### Autenticacion Bearer Token
``` Peticion post wishlist-api/v2/usuarios/login por favor revisa la documentacion en swagger ``` 

## License

**Free Software**
