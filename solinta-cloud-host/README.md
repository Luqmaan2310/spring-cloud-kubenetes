## Spring Cloud Bootstrap

Articles about bootstrapping Spring Cloud applications:

### Relevant Articles:
- [Spring Cloud – Bootstrapping](http://www.baeldung.com/spring-cloud-bootstrapping)
- [Spring Cloud – Securing Services](http://www.baeldung.com/spring-cloud-securing-services)
- [Spring Cloud – Tracing Services with Zipkin](http://www.baeldung.com/tracing-services-with-zipkin)
- [Spring Cloud Series – The Gateway Pattern](http://www.baeldung.com/spring-cloud-gateway-pattern)
- [Spring Cloud – Adding Angular 4](http://www.baeldung.com/spring-cloud-angular)

### Running the Project

- To run the project:
  - copy the application-config folder to c:\Users\{username}\ on Windows or /home/{username}/ on *nix. Then open a git bash terminal in application-config and run:
    - git init
    - git add .
    - git commit -m "First commit"
  - install and start redis (in-memory db) (TODO switch from in-memory db to standard db)
  - start the config-server
  - start the discovery-server
  - start all the other servers in any order (gateway-server, organisation-service)
