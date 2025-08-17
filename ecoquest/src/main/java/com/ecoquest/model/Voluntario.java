package com.ecoquest.model;

/**
 * @author Alonso Vargas
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representa a una persona que participa en las misiones ecológicas.
 * Contiene la información mínima para identificar, evaluar y
 * premiar su labor dentro del sistema EcoQuest.
 */
public class Voluntario {

    private final String id;
    private final String nombre;
    private final List<String> habilidades;
    private int puntos;

    /**
     * Conjunto de misiones completadas.
     * Se usa Set<Mision> para:
     *   1. Evitar que la misma misión se cuente dos veces.
     *   2. Obtener búsqueda O(1) al verificar si ya la realizó.
     * HashSet es suficiente porque el orden no es prioritario.
     */
    private final Set<Mision> misionesCompletadas;

    /* ----------------------------------------------------------
       Constructores
       ---------------------------------------------------------- */
    /**
     * Constructor principal.
     * Recibe id y nombre; inicializa colecciones vacías.
     * Facilita la creación rápida de voluntarios sin habilidades
     * ni misiones previas.
     */
    public Voluntario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.habilidades = new ArrayList<>();
        this.misionesCompletadas = new HashSet<>();
        this.puntos = 0;
    }

    /**
     * Constructor alternativo que permite crear un voluntario
     * con habilidades iniciales (útil para carga masiva o pruebas).
     */
    public Voluntario(String id, String nombre, List<String> habilidades) {
        this(id, nombre);               // reutiliza el constructor principal
        this.habilidades.addAll(habilidades);
    }

    /* ----------------------------------------------------------
       Métodos de comportamiento
       ---------------------------------------------------------- */

    /**
     * Añade una habilidad al voluntario.
     */
    public void agregarHabilidad(String habilidad) {
        habilidades.add(habilidad);
    }

    /**
     * Registra que el voluntario completó una misión.
     * Si la misión ya estaba registrada, Set la ignora automáticamente.
     */
    public void completarMision(Mision mision) {
        misionesCompletadas.add(mision);
    }

    /**
     * Añade puntos al voluntario.
     */
    public void agregarPuntos(int puntos) {
        this.puntos += puntos;
    }

    
    public int obtenerTotalMisiones() {
        return misionesCompletadas.size();
    }


    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public Set<Mision> getMisionesCompletadas() {
        return misionesCompletadas;
    }

    public int getPuntos() {
        return puntos;
    }

    @Override
    public String toString() {
        return String.format(
            "Voluntario[id=%s, nombre=%s, puntos=%d, habilidades=%d, misionesCompletadas=%d]",
            id, nombre, puntos, habilidades.size(), misionesCompletadas.size()
        );
    }
}
