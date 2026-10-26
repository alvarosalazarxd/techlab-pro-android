# TechLab Pro

Aplicación móvil nativa en Android para la gestión y registro de equipos tecnológicos en laboratorios académicos.

## Integrantes
- Alvaro Salazar
- Martin Ortiz

## Funcionalidades e Implementación
- **Registro de Equipos:** Validación de código en tiempo real con expresiones regulares (`XX00XX`).
- **Recursos Nativos:** Selección y renderizado de fotografías usando la galería nativa de Android (`GetContent`).
- **Persistencia Local:** Base de datos relacional mediante Room DB.
- **UI & Arquitectura:** Interfaz reactiva desarrollada en Jetpack Compose (Material 3) bajo el patrón MVVM.

## Pasos para Ejecutar
1. Clonar el repositorio:
   `git clone https://github.com/alvarosalazarxd/techlab-pro-android.git`
2. Abrir el proyecto en **Android Studio** (versión Iguana o superior recomendada).
3. Sincronizar las dependencias de Gradle.
4. Ejecutar en un emulador o dispositivo físico con **Android 8.0 (API 26)** o superior.
