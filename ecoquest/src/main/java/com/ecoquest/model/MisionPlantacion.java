package com.ecoquest.model;

import java.time.LocalDate;

public class MisionPlantacion  extends Mision implements Recompensa {
    private int cantidadArbolesPlantados;

    /**
     * Constructor para crear una misión de plantación de árboles.
     *
     * @param id                     Identificador único de la misión.
     * @param descripcion            Descripción de la misión.
     * @param ubicacion              Ubicación geográfica de la misión.
     * @param fecha                  Fecha en que se debe completar la misión.
     * @param nivelDificultad        Nivel de dificultad de la misión.
     * @param cantidadArbolesPlantados Cantidad de árboles plantados en la misión.
     */
    public MisionPlantacion(String id, String descripcion, PuntoEco ubicacion, LocalDate fecha,
            NivelDificultad nivelDificultad,
            int cantidadArbolesPlantados) {
        super(id, descripcion, ubicacion, fecha, nivelDificultad);
        this.cantidadArbolesPlantados = cantidadArbolesPlantados;
    }

    /**
     * Calcula el impacto positivo de la misión de plantación de árboles.
     * 
     * @return Cantidad de árboles plantados por la misión.
     */
    @Override
    public int calcularImpacto() {
        return cantidadArbolesPlantados;
    }

    /**
     * Calcula la recompensa por completar la misión de plantación.
     *
     * @return Recompensa en puntos por completar la misión.
     */
    @Override
    public String otorgarRecompensa() {
        int puntos = this.cantidadArbolesPlantados * 20; // Asumiendo 20 puntos por árbol
        return "Has obtenido " + puntos + " puntos por tu esfuerzo en la plantación de árboles en la comunidad de EcoQuest.";
    }
    // Getters
    public int getCantidadArbolesPlantados() {
        return cantidadArbolesPlantados;
    }

}
