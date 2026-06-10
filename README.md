# Spring Modulith Example Application

This repository contains the code of my talk "Vom Monolithen zum Modulithen: Spring Modulith in der Praxis"

![Roses](./roses.png)

## Branches

This repository contains three branches with different states of the application:

- `main`: initial code base used for live coding
- [`schritte`](https://github.com/nilshartmann-workshops/jug-ostfalen-spring-modulith/tree/schritte): this contains all code that I intended to show. In the [commit list of that branch](https://github.com/nilshartmann-workshops/jug-ostfalen-spring-modulith/commits/schritte/) you find single commits each with one (sub)topic we discussed. **Probably the best way to "get started" with this application and the talk,** just look at the Git diff for each commit.
- [`live_coding`](https://github.com/nilshartmann-workshops/jug-ostfalen-spring-modulith/tree/live_coding): that is the code that I wrote during the talk

# Examples

In this repository you'll find examples for:
- application modules
- named interfaces (`care.suggestions`)
- explicitly allowed dependencies (`rose`)
- ignoring a known violation during a migration (`PlantifyModuleTest`)
- decoupling modules with events, from `@EventListener` to `@ApplicationModuleListener` (`PlantService`, `CareService`, `UsageTracker`)
- event publication registry: failed events, re-delivery on restart and at runtime (`admin`)
- event chains (`care` -> `owner`)
- observability: the event chain as a span tree, plus `/actuator/modulith`
- module tests (`PlantifyModuleTest`, `BillingModuleTest`)

# Getting started

As this example has no frontend (only some HTTP endpoints), best is to run the test cases in the `test` folder on the `schritte` branch.

Otherwise, you can run the backend, by starting the `PlantifyApplication` class. Make sure, the required postgres DB and Kafka are running (see `compose.yaml`).

You can trigger the processing of new plants (including async event handling) by running an HTTP call for example with curl:

```bash
curl -X POST --location "http://127.0.0.1:8080/api/plants" \
    -H "Content-Type: application/json" \
    -d '{
          "location": "Balkon",
          "name": "Cannabis (natürlich nur für medizinische Zwecke)",
          "ownerId": "ee3829e4-fe2b-4d03-b2a1-70f1425d8c1c",
          "plantType": "SUMMER_FLOWERS"
        }'
```

# Observability

Spring Modulith records tracing to Micrometer. The example application is configured to use Zipkin as frontend. Zipkin is part of `compose.yaml` and starts together with Postgres and Kafka.

On `main` the export is switched off; it gets enabled in the `10b` commit on the `schritte` branch (`management.tracing.export.enabled=true`). When running the application and registering a new plant, you will then see the complete event chain (`plant` -> `care` -> `owner`, `plant` -> `billing`) as a single span tree at http://localhost:9411.

# Contact

If you have questions, comments or feedback, do not hesitate to contact me. You can find my [contact data here](https://nilshartmann.net/contact).
