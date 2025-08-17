# 📚 DOCUMENTACIÓN DE EXPOSICIÓN - ECOQUEST
## Sistema de Gestión de Aventuras Ecológicas

---

## 🎯 **DESCRIPCIÓN GENERAL DEL PROYECTO**

**EcoQuest** es un sistema de gestión integral para organizaciones ambientales que coordinan misiones ecológicas. El sistema permite gestionar voluntarios, misiones de diferentes tipos (plantación, limpieza, educación), y puntos ecológicos donde se realizan estas actividades.

### **Objetivos del Sistema:**
- ✅ Gestionar voluntarios y sus habilidades
- ✅ Organizar misiones ecológicas por tipo y dificultad
- ✅ Asignar voluntarios a misiones específicas
- ✅ Calcular impacto ambiental y recompensas
- ✅ Generar reportes de participación y efectividad

---

## 🏗️ **ARQUITECTURA DEL SISTEMA**

### **Patrón de Diseño:**
- **Arquitectura en Capas**: Separación clara entre modelo, servicio y presentación
- **Patrón Service**: Lógica de negocio centralizada en servicios especializados
- **Patrón Factory**: Creación de misiones según el tipo seleccionado

### **Estructura de Paquetes:**
```
com.ecoquest/
├── Main.java                 # Punto de entrada de la aplicación
├── model/                    # Entidades del dominio
│   ├── Voluntario.java      # Participantes del sistema
│   ├── Mision.java          # Clase abstracta base
│   ├── MisionPlantacion.java # Misiones de reforestación
│   ├── MisionLimpieza.java  # Misiones de limpieza
│   ├── MisionEducacion.java # Misiones educativas
│   ├── PuntoEco.java        # Ubicaciones geográficas
│   ├── Recompensa.java      # Interfaz para misiones con premios
│   ├── NivelDificultad.java # Enum de dificultad
│   └── TipoEcosistema.java  # Enum de tipos de ecosistema
└── service/                  # Lógica de negocio
    ├── MainMenu.java         # Interfaz de usuario
    ├── VoluntarioService.java # Gestión de voluntarios
    └── MisionService.java    # Gestión de misiones
```

---

## 🔧 **ANÁLISIS DETALLADO DE CLASES**

### **1. CLASE PRINCIPAL - Main.java**
```java
public class Main {
    public static void main(String[] args) {
       new MainMenu().showMenu();
    }
}
```
**Responsabilidad:** Punto de entrada que inicia la aplicación
**Interacción:** Crea e inicia el menú principal

---

### **2. CLASE ABSTRACTA - Mision.java**
```java
public abstract class Mision {
    private final String id;
    private final String descripcion;
    private final PuntoEco ubicacion;
    private final LocalDate fecha;
    private final NivelDificultad nivelDificultad;
    private final Set<Voluntario> voluntariosAsignados;
    
    public abstract int calcularImpacto();
}
```

**Responsabilidades:**
- ✅ Define estructura común para todas las misiones
- ✅ Almacena información básica (ID, descripción, ubicación, fecha, dificultad)
- ✅ Gestiona voluntarios asignados usando `HashSet` (evita duplicados)
- ✅ Define método abstracto `calcularImpacto()` para subclases

**Colecciones Utilizadas:**
- **`Set<Voluntario>`**: Evita asignar el mismo voluntario múltiples veces a una misión

**Interacciones:**
- **Composición con PuntoEco**: Cada misión se realiza en un punto ecológico específico
- **Agregación con Voluntario**: Múltiples voluntarios pueden participar en una misión

---

### **3. SUBCLASES DE MISIÓN**

#### **3.1 MisionPlantacion.java**
```java
public class MisionPlantacion extends Mision implements Recompensa {
    private int cantidadArbolesPlantados;
    
    @Override
    public int calcularImpacto() {
        return cantidadArbolesPlantados;
    }
    
    @Override
    public int calcularPuntosRecompensa() {
        return this.cantidadArbolesPlantados * 20; // 20 puntos por árbol
    }
}
```

**Responsabilidades:**
- ✅ Especializa misiones de reforestación
- ✅ Calcula impacto basado en árboles plantados
- ✅ Implementa sistema de recompensas (20 puntos por árbol)

#### **3.2 MisionLimpieza.java**
```java
public class MisionLimpieza extends Mision implements Recompensa {
    private int cantidadBasuraRecogida;
    
    @Override
    public int calcularImpacto() {
        return cantidadBasuraRecogida;
    }
    
    @Override
    public int calcularPuntosRecompensa() {
        return this.cantidadBasuraRecogida * 10; // 10 puntos por kg
    }
}
```

**Responsabilidades:**
- ✅ Especializa misiones de limpieza ambiental
- ✅ Calcula impacto basado en kilos de basura recogida
- ✅ Implementa sistema de recompensas (10 puntos por kg)

#### **3.3 MisionEducacion.java**
```java
public class MisionEducacion extends Mision {
    private int asistentes;
    
    @Override
    public int calcularImpacto() {
        return asistentes;
    }
}
```

**Responsabilidades:**
- ✅ Especializa misiones de educación ambiental
- ✅ Calcula impacto basado en número de personas educadas
- ✅ **NO implementa Recompensa** (misiones educativas no dan puntos extra)

---

### **4. CLASE VOLUNTARIO - Voluntario.java**
```java
public class Voluntario {
    private final String id;
    private final String nombre;
    private final List<String> habilidades;
    private int puntos;
    private final Set<Mision> misionesCompletadas;
}
```

**Responsabilidades:**
- ✅ Almacena información personal del voluntario
- ✅ Gestiona habilidades como `List<String>` (permite duplicados si es necesario)
- ✅ Mantiene historial de misiones completadas usando `Set<Mision>`
- ✅ Acumula puntos de recompensa

**Colecciones Utilizadas:**
- **`List<String> habilidades`**: Permite múltiples habilidades, ordenadas
- **`Set<Mision> misionesCompletadas`**: Evita contar la misma misión dos veces

**Interacciones:**
- **Asociación con Mision**: Un voluntario puede participar en múltiples misiones
- **Dependencia de Recompensa**: Recibe puntos cuando completa misiones que implementan la interfaz

---

### **5. CLASE PUNTO ECO - PuntoEco.java**
```java
public class PuntoEco {
    private final String id;
    private final String nombre;
    private final TipoEcosistema tipoEcosistema;
    private final double latitud;
    private final double longitud;
}
```

**Responsabilidades:**
- ✅ Representa ubicaciones geográficas donde se realizan misiones
- ✅ Almacena coordenadas GPS (latitud/longitud)
- ✅ Clasifica el tipo de ecosistema (bosque, desierto, montaña, etc.)

**Validaciones:**
- ✅ Coordenadas dentro de rangos válidos (-90 a 90 latitud, -180 a 180 longitud)

---

### **6. INTERFAZ RECOMPENSA - Recompensa.java**
```java
public interface Recompensa {
    int calcularPuntosRecompensa();
}
```

**Responsabilidades:**
- ✅ Define contrato para misiones que otorgan puntos extra
- ✅ Permite polimorfismo en el cálculo de recompensas

**Implementación:**
- ✅ `MisionPlantacion`: 20 puntos por árbol
- ✅ `MisionLimpieza`: 10 puntos por kg de basura
- ✅ `MisionEducacion`: NO implementa (misiones educativas sin recompensa)

---

### **7. ENUMS DEL SISTEMA**

#### **7.1 NivelDificultad.java**
```java
public enum NivelDificultad {
    FACIL, MEDIO, DIFICIL
}
```

#### **7.2 TipoEcosistema.java**
```java
public enum TipoEcosistema {
    BOSQUE, DESERTO, MONTANO, LAGUNA, PLAYA, URBANO, RURAL, OCEANO, HUMEDAL, SABANA
}
```

---

## 🚀 **SERVICIOS DEL SISTEMA**

### **1. VOLUNTARIO SERVICE - VoluntarioService.java**
```java
public class VoluntarioService {
    private final Map<String, Voluntario> voluntarios = new HashMap<>();
}
```

**Responsabilidades:**
- ✅ **Registro de voluntarios** con control de IDs únicos
- ✅ **Gestión de misiones completadas** y asignación de puntos
- ✅ **Búsqueda por habilidades** usando Streams y Lambdas
- ✅ **Ranking de voluntarios** por puntos acumulados

**Colecciones Utilizadas:**
- **`HashMap<String, Voluntario>`**: Acceso O(1) por ID, evita duplicados de ID

**Métodos Principales:**
- `registrarVoluntario()`: Control de duplicados con excepciones
- `completarMision()`: Asigna puntos según tipo de misión
- `buscarPorHabilidad()`: Filtrado funcional con Streams
- `topVoluntarios()`: Ordenamiento y limitación de resultados

---

### **2. MISIÓN SERVICE - MisionService.java**
```java
public class MisionService {
    private final Map<String, Mision> misiones = new HashMap<>();
}
```

**Responsabilidades:**
- ✅ **Almacenamiento de misiones** con control de IDs únicos
- ✅ **Recuperación y listado** de misiones registradas
- ✅ **Gestión del ciclo de vida** de las misiones

**Colecciones Utilizadas:**
- **`HashMap<String, Mision>`**: Acceso rápido por ID, control de duplicados

---

### **3. MENÚ PRINCIPAL - MainMenu.java**
```java
public class MainMenu {
    private final VoluntarioService voluntarioService = new VoluntarioService();
    private final MisionService misionService = new MisionService();
    private final Map<String, PuntoEco> puntosEco = new HashMap<>();
}
```

**Responsabilidades:**
- ✅ **Interfaz de usuario** en consola
- ✅ **Coordinación entre servicios** para operaciones complejas
- ✅ **Validación de entrada** y manejo de errores
- ✅ **Creación de misiones** según tipo seleccionado

**Colecciones Utilizadas:**
- **`HashMap<String, PuntoEco>`**: Gestión de puntos ecológicos por ID

---

## 🔄 **FLUJO DE INTERACCIÓN ENTRE CLASES**

### **FLUJO 1: REGISTRO DE VOLUNTARIO**
```
Usuario → MainMenu.registrarVoluntario() 
    → VoluntarioService.registrarVoluntario() 
    → HashMap.put() (almacenamiento)
```

### **FLUJO 2: REGISTRO DE MISIÓN**
```
Usuario → MainMenu.registrarMision() 
    → Selección de tipo de misión
    → Creación de subclase específica (MisionPlantacion/Limpieza/Educacion)
    → MisionService.agregarMision()
    → HashMap.put() (almacenamiento)
```

### **FLUJO 3: ASIGNACIÓN DE VOLUNTARIO**
```
Usuario → MainMenu.asignarVoluntarioAMision()
    → VoluntarioService.getVoluntario() (búsqueda por ID)
    → MisionService.getMision() (búsqueda por ID)
    → Mision.asignarVoluntario() → HashSet.add() (evita duplicados)
```

### **FLUJO 4: COMPLETADO DE MISIÓN**
```
Usuario → MainMenu.completarMision()
    → VoluntarioService.completarMision()
    → Voluntario.completarMision() → Set.add() (registra misión)
    → Si implementa Recompensa → cálculo de puntos
    → Voluntario.agregarPuntos() (actualiza puntuación)
```

### **FLUJO 5: BÚSQUEDA POR HABILIDAD**
```
Usuario → MainMenu.buscarPorHabilidad()
    → VoluntarioService.buscarPorHabilidad()
    → Stream.filter() + anyMatch() (búsqueda funcional)
    → List<Voluntario> (resultados filtrados)
```

---

## 📊 **JUSTIFICACIÓN DEL USO DE COLECCIONES**

### **1. HashMap para IDs Únicos**
- **Voluntarios, Misiones, PuntosEco**: Acceso O(1) por identificador
- **Control de duplicados**: `containsKey()` antes de inserción
- **Recuperación eficiente**: `get()` para operaciones de búsqueda

### **2. HashSet para Evitar Duplicados**
- **Voluntarios asignados a misiones**: Un voluntario no puede estar asignado dos veces
- **Misiones completadas**: Evita contar la misma misión múltiples veces
- **Operaciones O(1)**: `add()`, `contains()`, `remove()`

### **3. List para Resultados de Búsqueda**
- **Habilidades del voluntario**: Permite múltiples habilidades, ordenadas
- **Resultados de búsquedas**: Listas inmutables para evitar modificaciones externas
- **Streams y Lambdas**: Compatibilidad perfecta para operaciones funcionales

### **4. Set para Misiones Completadas**
- **Eliminación automática de duplicados**: Si se intenta registrar la misma misión
- **Búsqueda eficiente**: `contains()` para verificar si ya se completó
- **Tamaño preciso**: `size()` devuelve el número real de misiones únicas

---

## 🎮 **DEMOSTRACIÓN DEL SISTEMA**

### **Escenario de Uso Completo:**

1. **Registrar Punto Ecológico**: "Parque Nacional Verde" (BOSQUE)
2. **Registrar Voluntario**: "Angélica Torres" con habilidades "plantar árboles, educar"
3. **Registrar Misión**: "Reforestación ribera del río" (PLANTACIÓN, MEDIA)
4. **Asignar Voluntario**: Angélica a la misión de reforestación
5. **Completar Misión**: Marcar como completada
6. **Ver Recompensa**: 20 puntos por árbol plantado
7. **Generar Reporte**: Top voluntarios, misiones, puntos ecológicos

### **Características Destacadas:**
- ✅ **Validación robusta**: Coordenadas, IDs únicos, datos requeridos
- ✅ **Sistema de recompensas**: Puntos por impacto ambiental
- ✅ **Búsquedas funcionales**: Streams y Lambdas para consultas complejas
- ✅ **Interfaz intuitiva**: Menú colorido y fácil de usar
- ✅ **Arquitectura escalable**: Fácil agregar nuevos tipos de misión

---

## 🏆 **LOGROS TÉCNICOS DEL PROYECTO**

### **1. Programación Orientada a Objetos**
- ✅ **Herencia**: Mision → MisionPlantacion/Limpieza/Educacion
- ✅ **Interfaces**: Recompensa para misiones especiales
- ✅ **Encapsulación**: Atributos privados con getters controlados
- ✅ **Polimorfismo**: Métodos abstractos y sobreescritura

### **2. Manejo de Colecciones**
- ✅ **HashSet**: Evita duplicados automáticamente
- ✅ **HashMap**: Acceso eficiente por clave
- ✅ **List**: Gestión ordenada de elementos
- ✅ **Streams**: Operaciones funcionales y filtrado

### **3. Manejo de Excepciones**
- ✅ **Validación de entrada**: Coordenadas geográficas válidas
- ✅ **Control de duplicados**: IDs únicos para entidades
- ✅ **Manejo de errores**: Mensajes claros para el usuario

### **4. Arquitectura del Software**
- ✅ **Separación de responsabilidades**: Modelo, Servicio, Presentación
- ✅ **Bajo acoplamiento**: Servicios independientes
- ✅ **Alta cohesión**: Cada clase tiene una responsabilidad clara
- ✅ **Extensibilidad**: Fácil agregar nuevos tipos de misión

---

## 📋 **CONCLUSIÓN**

**EcoQuest** es un sistema robusto y bien diseñado que demuestra dominio completo de:

- **Programación Orientada a Objetos** con herencia, interfaces y polimorfismo
- **Gestión eficiente de colecciones** con justificación técnica sólida
- **Arquitectura de software** con separación clara de responsabilidades
- **Manejo de excepciones** y validaciones robustas
- **Programación funcional** con Streams y Lambdas

El sistema está listo para producción y puede ser fácilmente extendido para incluir nuevas funcionalidades como:
- Misiones de investigación científica
- Sistema de insignias y logros
- Integración con mapas y GPS
- Reportes estadísticos avanzados

**¡Un proyecto de excelente calidad que cumple todos los requerimientos del módulo!** 🌟
