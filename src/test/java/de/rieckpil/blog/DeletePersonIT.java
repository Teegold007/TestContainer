package de.rieckpil.blog;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

// JUnit 5 example with Spring Boot < 2.2.6
@Testcontainers
public class DeletePersonIT extends AbstractContainerBaseTest {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  public TestRestTemplate testRestTemplate;

  @Test
  @Sql("/testdata/FILL_FOUR_PERSONS.sql")
  public void testDeletePerson() {
    testRestTemplate.delete("/api/persons/1");
    assertEquals(3, personRepository.findAll().size());
    assertFalse(personRepository.findAll().contains("Phil"));

  }
}
