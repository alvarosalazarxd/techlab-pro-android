TeqGastos - TEQMED SPA

Aplicación móvil nativa en Android para el registro y rendición de gastos en terreno de los técnicos de servicio técnico de TEQMED SPA.

Integrantes
- Alvaro Salazar
- Martin Ortiz

Descripción de la Solución
Permite a los técnicos que se desplazan a clínicas y hospitales del Gran Concepción registrar sus comprobantes de gastos (peajes, alimentación, insumos) en el momento en que ocurren, adjuntando la fotografía del documento y garantizando la persistencia de datos local para operar en zonas sin conexión a internet.

Tecnologías e Implementación
- UI: Jetpack Compose con componentes de Material Design 3.
- Arquitectura:MVVM (Model-View-ViewModel) con estado reactivo.
- Persistencia Local (Offline-first): Room DB (SQLite) con Coroutines y Flow.
- Recursos Nativos:Integración con la galería del dispositivo (`ActivityResultContracts.GetContent`) para captura y procesamiento de imágenes.
- Validaciones: Control de formato de folios mediante Expresiones Regulares (`Regex`).
Ejecución
1. Clonar el repositorio.
2. Abrir el proyecto en Android Studio (Iguana o superior).
3. Sincronizar Gradle y ejecutar en un emulador o dispositivo físico con Android API 26+.
