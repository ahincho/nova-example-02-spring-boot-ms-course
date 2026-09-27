# ms-course

Microservicio de ejemplo en Spring Boot construido sobre Nova Platform. Existe para mostrar, con
el mínimo código posible, cómo se ve un servicio que hereda `nova-spring-boot-parent` y usa el
starter de observabilidad: los endpoints se instrumentan con una anotación, y las trazas salen
por OTLP sin más configuración que la dirección del collector.

Es el gemelo Spring Boot de `nova-example-05-quarkus-ms-course`: el mismo dominio en el otro stack,
para comparar qué escribe el servicio y qué pone la plataforma en cada caso.

## Qué muestra

- `@NovaSpringBootApplication` y `NovaApplication.run(...)` en lugar de la anotación y el
  arranque de Spring Boot.
- `@Traced` y `@Metered` sobre los endpoints de `CourseController`. La latencia está simulada a
  propósito, para que las métricas tengan algo que mostrar.
- Toda la configuración propia son dos valores: el puerto (`8081`) y
  `nova.observability.otlp.endpoint`.

## Cómo correrlo

```bash
mvn spring-boot:run
```

Los artefactos `pe.edu.nova.java` se leen de GitHub Packages, así que Maven necesita
credenciales para `maven.pkg.github.com`. Para ver las trazas, `nova-shared-03-infrastructure`
levanta el collector en `localhost:4318`.

Los cursos y los alumnos son datos de muestra escritos en el controlador.
