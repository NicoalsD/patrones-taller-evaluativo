# Case 3 patterns project

This project implements a Java console solution for an aquaculture feed plant using the required patterns: Abstract Factory, Prototype, and Builder.

The implementation stays simple and focused on the requested scope: master formula, cloned variants, nutritional validation, cost calculation, and production scheduling.

## Structure

- `src/app`: application entry point.
- `src/line`: production line families.
- `src/formula`: ingredients and formulas.
- `src/order`: immutable production order with Builder.
- `src/service`: validation, costing, and scheduling services.

## How to run

```bash
javac -d out $(find src -name "*.java")
java -cp out app.Main
```

## Implemented software patterns

- **Abstract Factory**: each production line provides its nutritional profile, extrusion process, and packaging without `switch` logic in service code.
- **Prototype**: formula variants are created using deep cloning so the master formula and other variants remain isolated.
- **Builder**: the production order is built fluently and validated at `build()` time to prevent inconsistent states.

## Notes

- Code is fully in English (packages, classes, methods, fields, parameters, locals, and messages).
- `PATRONES.md` remains in Spanish, as required.
- No external libraries or automated tests are included.

