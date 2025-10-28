package edu.unisabana.tyvs.tdd.domain.service;

import java.util.HashSet;
import java.util.Set;

import edu.unisabana.tyvs.tdd.domain.model.Person;
import edu.unisabana.tyvs.tdd.domain.model.RegisterResult;

public class Registry {

    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 120;

    // Estado mínimo para simular unicidad en el dominio (sin BD, sin frameworks)
    private final Set<Integer> registeredIds = new HashSet<>();

    public RegisterResult registerVoter(Person p) {
        if (p == null) {
            return RegisterResult.INVALID; // validación defensiva
        }
        if (!p.isAlive()) {
            return RegisterResult.DEAD; // regla de negocio: no registrar fallecidos
        }
        // Validación de edad
        int age = p.getAge();
        if (age < 0) {
            return RegisterResult.INVALID_AGE;
        }
        if (age < MIN_AGE) {
            return RegisterResult.UNDERAGE;
        }
        if (age > MAX_AGE) {
            return RegisterResult.INVALID_AGE;
        }
        // Validación de ID (formato simple)
        if (p.getId() <= 0) {
            return RegisterResult.INVALID;
        }
        // Unicidad
        if (registeredIds.contains(p.getId())) {
            return RegisterResult.DUPLICATED;
        }
        registeredIds.add(p.getId());
        return RegisterResult.VALID;
    }
}
