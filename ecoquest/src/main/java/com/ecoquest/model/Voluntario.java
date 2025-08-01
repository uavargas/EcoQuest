package com.ecoquest.model;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Voluntario {

    private String id;
    private String nombre;
    private List<String> habilidades;// Lista de habilidades del voluntario
    /**
     * Conjunto de identificadores de misiones que el voluntario ha completado.
     * Se utiliza un Set para evitar duplicados y garantizar que cada misión solo se
     * registre una vez.
     */
    private Set<String> misionesCompletadas;

    public Voluntario(String id, String nombre, List<String> habilidades) {
        this.id = id;
        this.nombre = nombre;
        this.habilidades = habilidades;
        /**
         * al crear un nuevo objeto Voluntario, el atributo misionesCompletadas se
         * inicializa como un nuevo conjunto vacío (HashSet).
         * Así, el voluntario comienza sin misiones completadas y se evita que haya
         * elementos duplicados en ese conjunto.
         * 
         * 
         * 
         */

        this.misionesCompletadas = new HashSet<>(); // Initialize with an empty set
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public Set<String> getMisionesCompletadas() {
        return misionesCompletadas;
    }

    // @Override para que se sobreescriba el método toString y mostre la
    // información del voluntario
    @Override
    public String toString() {
        return "Voluntario [id=" + id + ", nombre=" + nombre + ", habilidades=" + habilidades + ", misionesCompletadas="
                + misionesCompletadas + "]";
    }

}
