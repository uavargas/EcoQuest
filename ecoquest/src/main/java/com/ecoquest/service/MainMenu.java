package com.ecoquest.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.ecoquest.model.Mision;
import com.ecoquest.model.MisionPlantacion;
import com.ecoquest.model.MisionLimpieza;
import com.ecoquest.model.MisionEducacion;
import com.ecoquest.model.NivelDificultad;
import com.ecoquest.model.PuntoEco;
import com.ecoquest.model.Voluntario;
import com.ecoquest.model.TipoEcosistema;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final VoluntarioService voluntarioService = new VoluntarioService();
    private final MisionService misionService = new MisionService();
    private final Map<String, PuntoEco> puntosEco = new java.util.HashMap<>();

    public void showMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n\033[0;32m╔══════════════════════════════════════╗");
            System.out.println("║      \033[1;33m   E C O Q U E S T              \033[0;36m║");
            System.out.println("║    \033[0;33mM E N Ú   P R I N C I P A L       \033[0;32m║");
            System.out.println("╚══════════════════════════════════════╝\033[0m");

            System.out.println("\n\033[0;36m1. \033[0mRegistrar Voluntario");
            System.out.println("\033[0;36m2. \033[0mRegistrar Punto Ecológico");
            System.out.println("\033[0;36m3. \033[0mRegistrar Misión");
            System.out.println("\033[0;36m4. \033[0mAsignar Voluntario a Misión");
            System.out.println("\033[0;36m5. \033[0mCompletar Misión");
            System.out.println("\033[0;36m6. \033[0mBuscar Voluntarios por Habilidad");
            System.out.println("\033[0;36m7. \033[0mMostrar Reportes");
            System.out.println("\033[0;31m8. \033[0mSalir\033[0m");
            System.out.print("\n\033[1;35m» \033[0;33mIngrese opción (1-8): \033[0m");

            int opcion = -1;
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(" Error: Por favor, ingrese un número válido.");
            } finally {
                scanner.nextLine(); // Limpia el buffer del scanner
            }

            switch (opcion) {
                case 1 -> registrarVoluntario();
                case 2 -> registrarPuntoEco();
                case 3 -> registrarMision();
                case 4 -> asignarVoluntarioAMision();
                case 5 -> completarMision();
                case 6 -> buscarPorHabilidad();
                case 7 -> mostrarReportes();
                case 8 -> running = false;
                default -> System.out.println("Opción inválida.");
            }

        }
        scanner.close();
    }

    /* 1. Registrar voluntario */
    private void registrarVoluntario() {
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Habilidades (separadas por coma): ");
        List<String> habilidades = Arrays.asList(scanner.nextLine().trim().split("\\s*,\\s*"));

        Voluntario v = new Voluntario(id, nombre, habilidades);
        voluntarioService.registrarVoluntario(id, v);
        System.out.println(" Voluntario registrado.");
    }

    /* 2. Registrar misión */
    private void registrarMision() {
        System.out.print("ID de misión: ");
        String id = scanner.nextLine().trim();
        System.out.print("Descripción: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Fecha (yyyy-mm-dd): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Nivel [FACIL/MEDIO/DIFICIL]: ");
        NivelDificultad nivel = NivelDificultad.valueOf(scanner.nextLine().trim().toUpperCase());

        // Asignar un Punto Ecológico existente
        System.out.println("Puntos Ecológicos disponibles:");
        if (puntosEco.isEmpty()) {
            System.out.println("No hay Puntos Ecológicos registrados. Registre uno primero.");
            return;
        }
        puntosEco.values().forEach(p -> System.out.println(" - ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Latitud: " + p.getLatitud() + ", Longitud: " + p.getLongitud()));
        System.out.print("ID del Punto Ecológico para la misión: ");
        String puntoEcoId = scanner.nextLine().trim();
        PuntoEco puntoAsignado = puntosEco.get(puntoEcoId);
        if (puntoAsignado == null) {
            System.out.println(" Punto Ecológico no encontrado.");
            return;
        }
        System.out.println("Tipo de misión:");
        System.out.println("1. Plantación");
        System.out.println("2. Limpieza");
        System.out.println("3. Educación");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        Mision mision = switch (tipo) {
            case 1 -> {
                System.out.print("Árboles a plantar: ");
                int arboles = scanner.nextInt();
                scanner.nextLine();
                yield new MisionPlantacion(id, desc, puntoAsignado, fecha, nivel, arboles);
            }
            case 2 -> {
                System.out.print("Kg de basura a recoger: ");
                int kg = scanner.nextInt();
                scanner.nextLine();
                yield new MisionLimpieza(id, desc, puntoAsignado, fecha, nivel, kg);
            }
            case 3 -> {
                System.out.print("Nº de asistentes: ");
                int asistentes = scanner.nextInt();
                scanner.nextLine();
                yield new MisionEducacion(id, desc, puntoAsignado, fecha, nivel, asistentes);
            }
            default -> throw new IllegalArgumentException("Tipo no válido.");
        };

        misionService.agregarMision(id, mision);
        System.out.println("Misión registrada.");
    }

    /* 3. Registrar punto ecológico */
    private void registrarPuntoEco() {
        System.out.print("ID del punto: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.println("Tipos de ecosistema disponibles: " + Arrays.toString(TipoEcosistema.values()));
        System.out.print("Tipo de ecosistema: ");
        TipoEcosistema tipo = TipoEcosistema.valueOf(scanner.nextLine().trim().toUpperCase());
        
        // Solicitar coordenadas GPS
        System.out.println("\n--- COORDENADAS GPS ---");
        System.out.println("Rangos válidos: Latitud (-90 a 90), Longitud (-180 a 180)");
        
        double latitud = 0.0;
        double longitud = 0.0;
        
        try {
            System.out.print("Latitud (ej: 4.5709 para Bogotá): ");
            latitud = Double.parseDouble(scanner.nextLine().trim());
            
            System.out.print("Longitud (ej: -74.2973 para Bogotá): ");
            longitud = Double.parseDouble(scanner.nextLine().trim());
            
            // Validar rangos
            if (latitud < -90 || latitud > 90) {
                System.out.println("⚠️  Advertencia: Latitud fuera de rango válido (-90 a 90). Se usará 0.0");
                latitud = 0.0;
            }
            if (longitud < -180 || longitud > 180) {
                System.out.println("⚠️  Advertencia: Longitud fuera de rango válido (-180 a 180). Se usará 0.0");
                longitud = 0.0;
            }
            
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Error: Coordenadas inválidas. Se usarán valores por defecto (0.0, 0.0)");
            latitud = 0.0;
            longitud = 0.0;
        }
        
        puntosEco.put(id, new PuntoEco(id, nombre, tipo, latitud, longitud));
        System.out.println("✅ Punto ecológico registrado con coordenadas: (" + latitud + ", " + longitud + ")");
    }

    /* 4. Asignar voluntario a misión */
    private void asignarVoluntarioAMision() {
        System.out.print("ID del voluntario a asignar: ");
        String volId = scanner.nextLine().trim();
        Voluntario voluntario = voluntarioService.getVoluntario(volId);
        if (voluntario == null) {
            System.out.println(" Voluntario no encontrado.");
            return;
        }

        System.out.println("\nMisiones disponibles para asignar:");
        misionService.listarMisiones().forEach(System.out::println);
        System.out.print("\nID de la misión: ");
        String misId = scanner.nextLine().trim();
        Mision mision = misionService.getMision(misId);
        if (mision == null) {
            System.out.println(" Misión no encontrada.");
            return;
        }

        // Lógica de asignación
        mision.asignarVoluntario(voluntario);

        System.out.println(" Voluntario '" + voluntario.getNombre() + "' asignado a la misión '"
                + mision.getDescripcion() + "'.");
    }

    /* 5. Completar misión */
    private void completarMision() {
        System.out.print("ID del voluntario: ");
        String volId = scanner.nextLine().trim();
        System.out.print("ID de la misión a completar: ");
        String misId = scanner.nextLine().trim();

        Mision m = misionService.getMision(misId);
        if (m == null) {
            System.out.println(" Misión no encontrada.");
            return;
        }
        voluntarioService.completarMision(volId, m);
        System.out.println(" Misión marcada como completada.");
    }

    /* 6. Buscar por habilidad */
    private void buscarPorHabilidad() {
        System.out.print("Habilidad a buscar: ");
        String hab = scanner.nextLine().trim();
        List<Voluntario> encontrados = voluntarioService.buscarPorHabilidad(hab);
        if (encontrados.isEmpty()) {
            System.out.println("No hay voluntarios con esa habilidad.");
        } else {
            encontrados.forEach(System.out::println);
        }
    }

    /* 7. Reportes */
    private void mostrarReportes() {
        System.out.println("--- REPORTES ---");
        System.out.println("\n Top 3 Voluntarios por Puntos ");
        voluntarioService.topVoluntarios().forEach(System.out::println);

        System.out.println("\n Misiones Registradas ");
        misionService.listarMisiones().forEach(System.out::println);

        System.out.println("\n Puntos Ecológicos Registrados ");
        if (puntosEco.isEmpty()) {
            System.out.println("No hay puntos ecológicos registrados.");
        } else {
            puntosEco.values().forEach(p -> {
                System.out.println("📍 " + p.getNombre() + " (ID: " + p.getId() + ")");
                System.out.println("   Tipo: " + p.getTipoEcosistema());
                System.out.println("   Coordenadas: (" + p.getLatitud() + ", " + p.getLongitud() + ")");
                System.out.println();
            });
        }
    }
}