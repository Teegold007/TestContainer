package de.rieckpil.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

// JUnit 5 example with Spring Boot >= 2.2.6
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public  abstract class AbstractContainerBaseTest implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Container
  public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer();

  @DynamicPropertySource
  static void postgresqlProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
  }

  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    TestPropertyValues values = TestPropertyValues.of(
            "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
            "spring.datasource.password=" + postgreSQLContainer.getPassword(),
            "spring.datasource.username=" + postgreSQLContainer.getUsername()
    );
    values.applyTo(configurableApplicationContext);
  }




}
