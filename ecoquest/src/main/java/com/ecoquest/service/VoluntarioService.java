package com.ecoquest.service;

import java.util.Comparator;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;
import com.ecoquest.model.Mision;
import com.ecoquest.model.Recompensa;
import com.ecoquest.model.Voluntario;

/**
 * Servicio que centraliza la gestión de voluntarios y sus misiones completadas.
 * Utiliza colecciones seguras para evitar duplicados y facilitar consultas.
 */
public class VoluntarioService {

    /** Voluntarios indexados por ID único. */
    private final Map<String, Voluntario> voluntarios = new HashMap<>();

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
        
        // El voluntario registra la misión internamente
        v.completarMision(mision);

        // Si la misión tiene recompensa, se le asignan los puntos
        if (mision instanceof Recompensa) {
            int puntos = ((Recompensa) mision).calcularPuntosRecompensa();
            v.agregarPuntos(puntos);
            System.out.println("✨ ¡" + v.getNombre() + " ha ganado " + puntos + " puntos de recompensa! ✨");
        }
    }

    /**
     * Devuelve los tres voluntarios con más puntos (orden descendente).
     *
     * @return lista inmutable con los TOP 3 voluntarios.
     */
    public List<Voluntario> topVoluntarios() {
        return voluntarios.values().stream().sorted(Comparator.comparingInt(Voluntario::getPuntos).reversed())
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

    /**
     * Obtiene un voluntario por su ID.
     *
     * @param id identificador único del voluntario.
     * @return el voluntario correspondiente o null si no existe.
     */
    public Voluntario getVoluntario(String id) {
        return voluntarios.get(id);
    }
}