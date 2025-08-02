package com.ecoquest.model;

import java.time.LocalDate;

/**
 * Clase abstracta que representa una misión ecológica dentro del sistema
 * EcoQuest.
 * 
 * Define los atributos y métodos comunes a todas las misiones, como el
 * identificador,
 * descripción, ubicación, fecha y nivel de dificultad.
 * 
 * No puede ser instanciada directamente; debe ser extendida por subclases
 * concretas
 * (por ejemplo, MisionPlantacion, MisionLimpieza, MisionEducacion),
 * las cuales implementan el método abstracto calcularImpacto() para definir
 * el impacto positivo específico de cada tipo de misión.
 * 
 * Esta abstracción garantiza que cada misión tenga su propia lógica de impacto
 * y datos propios,
 * promoviendo la extensibilidad y el correcto modelado del dominio.
 */
public abstract class Mision {

    private final String id;
    private final String descripcion;
    private final PuntoEco ubicacion;
    private final LocalDate fecha;
    private final NivelDificultad nivelDificultad;

    public Mision(String id, String descripcion, PuntoEco ubicacion, LocalDate fecha, NivelDificultad nivelDificultad) {
        this.id = id;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.nivelDificultad = nivelDificultad;
    }

    /**
     * Obliga a cada subclase a indicar cuánto impacto positivo genera esta misión.
     * 
     * @return número que representa la cantidad de árboles plantados,
     *         kilos de basura recogidos, personas educadas, etc.,
     *         según el tipo concreto de misión.
     */
    public abstract int calcularImpacto();

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public PuntoEco getUbicacion() {
        return ubicacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public NivelDificultad getNivelDificultad() {
        return nivelDificultad;
    }

    @Override
    public String toString() {
        return String.format(
                "Mision[id=%s, descripcion=%s, ubicacion=%s, fecha=%s, nivelDificultad=%s]",
                id, descripcion, ubicacion, fecha, nivelDificultad);
    }

}
