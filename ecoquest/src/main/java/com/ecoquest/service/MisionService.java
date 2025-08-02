package com.ecoquest.service;

import java.util.*;

import com.ecoquest.model.Mision;

/**
 * Gestor central de misiones ecológicas.
 * Encapsula la lógica de almacenamiento y consulta para evitar
 * duplicados y centralizar operaciones.
 */
public class MisionService {

    /** Mapa interno que almacena misiones indexadas por ID único. */
    private final Map<String, Mision> misiones = new HashMap<>();

    /** Constructor vacío: el mapa se inicializa vacío. */
    public MisionService() {
    }

    /**
     * Registra una nueva misión.
     * @param id identificador único de la misión.
     * @param mision objeto misión a guardar.
     * @throws IllegalArgumentException si el ID ya existe.
     */
    public void agregarMision(String id, Mision mision) {
        if (misiones.containsKey(id)) {
            throw new IllegalArgumentException("La misión con el ID " + id + " ya existe.");
        }
        misiones.put(id, mision);
    }

    /**
     * Recupera una misión por su ID.
     * @param id identificador de la misión.
     * @return la misión encontrada o null si no existe.
     */
    public Mision getMision(String id) {
        return misiones.get(id);
    }

    /**
     * Elimina una misión por su ID.
     * @param id identificador de la misión a eliminar.
     */
    public void eliminarMision(String id) {
        misiones.remove(id);
    }

    /**
     * Devuelve una lista inmutable con todas las misiones registradas.
     * @return lista de misiones (copia segura para evitar modificaciones externas).
     */
    public List<Mision> listarMisiones() {
        return List.copyOf(misiones.values());
    }
}