# Spring-Boot-Security

Securing a Spring Boot REST API

## Getting Started

These instructions will get you a copy of the projects up and running on your local machine for development and testing purposes

### Projects

- **jwt**: Spring Boot application that makes use of JWT authentication for securing an exposed REST API
- **oauth2-jwt**: Spring Boot application that builds an OAuth 2.0 Authorization Server to authenticate your identity and a Resource Server which handles authenticated requests

### Running jwt

The application can be tested locally using:

```
jwt.postman_collection.json into Postman
```

### Running oauth2-jwt

The application can be tested locally using:

```
oauth.postman_collection.json into Postman
```

> Get Token with ROLE_USER
<img src="https://github.com/rbailen/Spring-Boot-Security/raw/master/user.png" width="50%" height="50%">

> Get Token with ROLE_ADMIN
<img src="https://github.com/rbailen/Spring-Boot-Security/raw/master/admin.png" width="50%" height="50%">