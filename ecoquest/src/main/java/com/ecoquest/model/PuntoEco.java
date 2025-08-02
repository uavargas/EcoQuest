package com.ecoquest.model;

/**
 * Representa un punto ecológico con su información geográfica y tipo de
 * ecosistema.
 */
public class PuntoEco {
    private final String id;
    private final String nombre;
    private final TipoEcosistema tipoEcosistema;
    private final double latitud;
    private final double longitud;

    /**
     * Constructor para crear un punto ecológico.
     *
     * @param id             Identificador único del punto ecológico.
     * @param nombre         Nombre del punto ecológico.
     * @param tipoEcosistema Tipo de ecosistema (ej. bosque, laguna, desierto).
     * @param latitud        Latitud del punto.
     * @param longitud       Longitud del punto.
     */
    public PuntoEco(String id, String nombre, TipoEcosistema tipoEcosistema, double latitud, double longitud) {
        // Validación de latitud y longitud dentro de los rangos legitimos
        if (latitud < -90 || latitud > 90 || longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("Latitud y longitud deben estar dentro de los rangos válidos");
        }
        this.id = id;
        this.nombre = nombre;
        this.tipoEcosistema = tipoEcosistema;
        this.latitud = latitud;
        this.longitud = longitud;

    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoEcosistema getTipoEcosistema() {
        return tipoEcosistema;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    @Override
    public String toString() {
        return String.format(
                "PuntoEco{id='%s', nombre='%s', tipoEcosistema='%s', latitud=%.6f, longitud=%.6f}",
                id, nombre, tipoEcosistema, latitud, longitud);

    }
}