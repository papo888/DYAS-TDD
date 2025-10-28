package edu.unisabana.tyvs.tdd.domain.service;

import edu.unisabana.tyvs.tdd.domain.model.Gender;
import edu.unisabana.tyvs.tdd.domain.model.Person;
import edu.unisabana.tyvs.tdd.domain.model.RegisterResult;
import org.junit.Assert;
import org.junit.Test;

/**
 * Todos los tests siguen AAA (Arrange-Act-Assert)
 * y cada uno mapea a un escenario BDD (Given-When-Then) en comentarios.
 */
public class RegistryTest {

    @Test
    public void shouldRegisterValidPerson() {
        // BDD: Given persona viva, 30 años, id único
        // When intento registrarla
        // Then el resultado es VALID
        // Arrange
        Registry registry = new Registry();
        Person person = new Person("Ana", 1, 30, Gender.FEMALE, true);
        // Act
        RegisterResult result = registry.registerVoter(person);
        // Assert
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectDeadPerson() {
        // Given persona muerta
        // When intento registrarla
        // Then DEAD
        Registry registry = new Registry();
        Person dead = new Person("Carlos", 2, 40, Gender.MALE, false);
        RegisterResult result = registry.registerVoter(dead);
        Assert.assertEquals(RegisterResult.DEAD, result);
    }

    @Test
    public void shouldReturnInvalidWhenPersonIsNull() {
        // Given persona null
        // When intento registrarla
        // Then INVALID
        Registry registry = new Registry();
        RegisterResult result = registry.registerVoter(null);
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    @Test
    public void shouldRejectWhenIdIsZeroOrNegative() {
        // Given id = 0 (y también podría probar -5) con edad válida y vivo
        // When intento registrarla
        // Then INVALID
        Registry registry = new Registry();
        Person p = new Person("Lu", 0, 25, Gender.UNIDENTIFIED, true);
        RegisterResult result = registry.registerVoter(p);
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    @Test
    public void shouldRejectUnderageAt17() {
        // Given 17 años, vivo, id válido
        // When intento registrarla
        // Then UNDERAGE
        Registry registry = new Registry();
        Person p = new Person("Sofi", 10, 17, Gender.FEMALE, true);
        RegisterResult result = registry.registerVoter(p);
        Assert.assertEquals(RegisterResult.UNDERAGE, result);
    }

    @Test
    public void shouldAcceptAdultAt18() {
        // Given 18 años, vivo, id válido
        // When intento registrarla
        // Then VALID
        Registry registry = new Registry();
        Person p = new Person("Leo", 11, 18, Gender.MALE, true);
        RegisterResult result = registry.registerVoter(p);
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldAcceptMaxAge120() {
        // Given 120 años, vivo, id válido
        // When registro
        // Then VALID
        Registry registry = new Registry();
        Person p = new Person("Abue", 12, 120, Gender.UNIDENTIFIED, true);
        RegisterResult result = registry.registerVoter(p);
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectInvalidAgeOver120() {
        // Given 121 años, vivo, id válido
        // When registro
        // Then INVALID_AGE
        Registry registry = new Registry();
        Person p = new Person("Extremo", 13, 121, Gender.MALE, true);
        RegisterResult result = registry.registerVoter(p);
        Assert.assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectInvalidAgeNegative() {
        // Given edad -1, vivo, id válido
        // When registro
        // Then INVALID_AGE
        Registry registry = new Registry();
        Person p = new Person("Raro", 14, -1, Gender.FEMALE, true);
        RegisterResult result = registry.registerVoter(p);
        Assert.assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectDuplicatedId() {
        // Given mismo id ya registrado
        // When registro por segunda vez
        // Then DUPLICATED
        Registry registry = new Registry();
        Person p1 = new Person("A", 777, 30, Gender.MALE, true);
        Person p2 = new Person("B", 777, 25, Gender.FEMALE, true);

        Assert.assertEquals(RegisterResult.VALID, registry.registerVoter(p1));
        Assert.assertEquals(RegisterResult.DUPLICATED, registry.registerVoter(p2));
    }
}
