# Observability: Event-Kette als Span-Baum (Schritt 10b)

    Warum: Nach der Entkopplung sagt statisches Codelesen nicht mehr, was zur Laufzeit
    wirklich passiert. Tracing macht den realen Pfad sichtbar.

    Setup: Zipkin kommt über `compose.yaml` mit hoch (UI: http://localhost:9411).
    `spring-modulith-starter-insight` ist seit Schritt 01 drin.

    ## Demo

    1. `create-plant.http` absetzen.
    2. Zipkin öffnen -> **Run Query** -> jüngsten Trace öffnen:

       ```
       POST /api/plants              (plant)
         |- onPlantRegistered        (care)   <- async @ApplicationModuleListener
         |    |- onCareTasksScheduled (owner) <- async @ApplicationModuleListener
         |- onPlantCreated           (billing) <- async @ApplicationModuleListener
       ```

       Der Clou: `care`, `billing` und `owner` laufen **asynchron** (eigener Thread,
       eigene TX) – ihre Spans hängen trotzdem im **selben** Trace. Der
       Trace-Kontext wird über die `@Async`-Grenze propagiert.

    3. http://localhost:8080/actuator/modulith -> Modulstruktur als JSON aus der
       **laufenden** App. Komplement zu `verify()`: der Test ist ein Build-Artefakt,
       der Endpoint liefert dieselbe Struktur zur Laufzeit über HTTP.
    