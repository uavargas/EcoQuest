package com.ecoquest.model;

import java.time.LocalDate;

public class MisionEducacion extends Mision {
    private int asistentes;

    /**
     * Constructor para crear una misión de educación ambiental.
     *
     * @param id              Identificador único de la misión.
     * @param descripcion     Descripción de la misión.
     * @param ubicacion       Ubicación geográfica de la misión.
     * @param fecha           Fecha en que se debe completar la misión.
     * @param nivelDificultad Nivel de dificultad de la misión.
     */

    public MisionEducacion(String id, String descripcion, PuntoEco ubicacion, LocalDate fecha, NivelDificultad nivelDificultad,
            int asistentes) {
        super(id, descripcion, ubicacion, fecha, nivelDificultad);
        this.asistentes = asistentes;

    }
    /**
     * Calcula el impacto positivo de la misión de educación ambiental.
     * 
     * @return Número de personas educadas por la misión.
     */
    @Override
    public int calcularImpacto() {
        return asistentes;
    }

    //getters
    public int getAsistentes() {
        return asistentes;
    }
    

}
