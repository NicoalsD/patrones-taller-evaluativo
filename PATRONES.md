# PATRONES.md

## Abstract Factory
Ubicación: `src/line/LineFactory.java` y las clases concretas `GrowOutTilapiaFactory`, `GrowOutTroutFactory` y `FarmedShrimpFactory`.

Se usó para crear, por cada línea, el perfil nutricional, el proceso de extrusión y el empaque. Así el validador y el programador trabajan solo con interfaces y no requieren cambiar lógica basada en `switch` o condiciones por línea.

## Prototype
Ubicación: `src/formula/Formula.java`.

La fórmula maestra se clona con una copia profunda para generar variantes. Esto evita que un ajuste temporal se guarde en la maestra ni que una variante altere a otra.

## Builder
Ubicación: `src/order/ProductionOrder.java`.

La orden de producción tiene muchos atributos obligatorios y opcionales, por lo que Builder permite construirla de forma fluida y segura. La validación se ejecuta en `build()`, por ejemplo para toneladas fuera de rango o aditivos sin responsable de calidad.
