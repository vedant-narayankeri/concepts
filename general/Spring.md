What does SpringBoot offer more than Spring?

- Spring provides the core IoC container and dependency injection.-
- Spring Boot is essentially an opinionated layer on top that provides auto-configuration based on classpath scanning, embedded servers, and starter dependencies.
- It reduces boilerplate—you don't have to manually wire up a DataSource or EntityManagerFactory if the right dependencies are present.
- Facilitates Rapid Application Development

Spring Boot Application Start Process
- Main calls SpringApplication.run(), this is entry point
- Then we loads environment, application.properties file, resolve profiles, system properties/env variables
- Creates the ApplicationContext
- Then scans the classpath for components
- Applies auto-configuration based on dependencies present
- Builds the bean dependency graph
- Instantiates beans in order, then runs postConstruct() method