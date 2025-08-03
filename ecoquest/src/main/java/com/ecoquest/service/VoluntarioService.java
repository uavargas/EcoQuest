package com.ecoquest.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ecoquest.model.Mision;
import com.ecoquest.model.Voluntario;

/**
 * Servicio que centraliza la gestión de voluntarios y sus misiones completadas.
 * Utiliza colecciones seguras para evitar duplicados y facilitar consultas.
 */
public class VoluntarioService {

    /** Voluntarios indexados por ID único. */
    private final Map<String, Voluntario> voluntarios = new HashMap<>();

    /**
     * Misiones completadas por cada voluntario.
     * Clave: ID del voluntario.
     * Valor: conjunto de misiones terminadas.
     */
    private final Map<String, Set<Mision>> misionesCompletadas = new HashMap<>();

    /** Constructor vacío: inicializa colecciones vacías. */
    public VoluntarioService() {
    }

    /**
     * Registra un nuevo voluntario.
     *
     * @param id         identificador único.
     * @param voluntario objeto voluntario a guardar.
     * @throws IllegalArgumentException si el ID ya existe.
     */
    public void registrarVoluntario(String id, Voluntario voluntario) {
        if (voluntario == null || id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID y el voluntario no pueden ser null o vacíos.");
        }
        if (voluntarios.containsKey(id)) {
            throw new IllegalArgumentException("El voluntario con el ID " + id + " ya existe.");
        }
        voluntarios.put(id, voluntario);
        misionesCompletadas.put(id, new HashSet<>()); // Inicializa vacío el conjunto de misiones completadas
    }

    /**
     * Marca una misión como completada por un voluntario.
     *
     * @param voluntarioId ID del voluntario.
     * @param mision       misión terminada.
     */
    public void completarMision(String voluntarioId, Mision mision) {
        Voluntario v = voluntarios.get(voluntarioId);
        if (v == null) {
            throw new IllegalArgumentException("Voluntario no encontrado con ID: " + voluntarioId);
        }
        if (!misionesCompletadas.get(voluntarioId).add(mision)) {
            throw new IllegalStateException("No se pudo agregar la misión a las completadas.");
        }
        v.completarMision(mision);
    }

    /**
     * Devuelve los tres voluntarios con más misiones completadas (orden
     * descendente).
     *
     * @return lista inmutable con los TOP 3 voluntarios.
     */
    public List<Voluntario> topVoluntarios() {
        return voluntarios.values().stream()
                .sorted(Comparator.comparingInt((Voluntario v) -> v.getMisionesCompletadas().size())
                        .reversed())
                .limit(3)
                .toList();
    }

    /**
     * Busca voluntarios que posean la habilidad indicada (ignorando mayúsculas).
     *
     * @param hab habilidad a buscar.
     * @return lista de voluntarios que la poseen.
     */
    public List<Voluntario> buscarPorHabilidad(String hab) {
        return voluntarios.values()
                .stream()
                .filter(v -> v.getHabilidades()
                        .stream()
                        .anyMatch(h -> h.equalsIgnoreCase(hab)))
                .toList();
    }
}