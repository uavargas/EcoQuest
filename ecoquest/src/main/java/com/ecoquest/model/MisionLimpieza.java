package com.ecoquest.model;

import java.time.LocalDate;

public class MisionLimpieza extends Mision implements Recompensa {
    private int cantidadBasuraRecogida;

    /**
     * Constructor para crear una misión de limpieza ambiental.
     *
     * @param id                     Identificador único de la misión.
     * @param descripcion            Descripción de la misión.
     * @param ubicacion              Ubicación geográfica de la misión.
     * @param fecha                  Fecha en que se debe completar la misión.
     * @param nivelDificultad        Nivel de dificultad de la misión.
     * @param cantidadBasuraRecogida Cantidad de basura recogida en la misión.
     */
    public MisionLimpieza(String id, String descripcion, PuntoEco ubicacion, LocalDate fecha,
            NivelDificultad nivelDificultad,
            int cantidadBasuraRecogida) {
        super(id, descripcion, ubicacion, fecha, nivelDificultad);
        this.cantidadBasuraRecogida = cantidadBasuraRecogida;
    }

    /**
     * Calcula el impacto positivo de la misión de limpieza ambiental.
     * 
     * @return Cantidad de basura (en kg) recogida por la misión.
     */
    @Override
    public int calcularImpacto() {
        return cantidadBasuraRecogida;
    }

    /**
     * Calcula la recompensa por completar la misión de limpieza.
     *
     * @return Recompensa en puntos por completar la misión.
     */
    @Override
    public String otorgarRecompensa() {
        int puntos = this.cantidadBasuraRecogida * 10; // Asumiendo 10 puntos por kilo
        return "Has obtenido " + puntos + " puntos por tu esfuerzo en la limpieza ambiental en la comunidad de EcoQuest.";
    }

}
