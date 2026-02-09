#! /bin/bash
#
# Verschiebt die Flyway-Migrationen in einen Ordner je Modul, so wie es Spring
# Modulith für modulbewusste Migrationen erwartet:
# https://docs.spring.io/spring-modulith/reference/runtime.html#module-aware-flyway-migrations
#
# Jedes Modul bekommt seine eigene Historien-Tabelle
# (flyway_schema_history_<modul>). Die Versionsnummern sind danach modul-lokal,
# deshalb fängt jedes Modul wieder bei V1 an.
#
# Aufruf: ./move-db-migration.sh

set -euo pipefail

cd "$(dirname "$0")"

MIGRATION_DIR="src/main/resources/db/migration"

# Modul|alter Dateiname|neuer Dateiname
MIGRATIONS="
owner|V1__create_owner.sql|V1__create_owner.sql
billing|V11__create_billing_schema.sql|V1__create_billing_schema.sql
billing|V12__add_usage_records_table.sql|V2__add_usage_records_table.sql
plant|V21__create_plant_schema.sql|V1__create_plant_schema.sql
plant|V22__add_plants_table.sql|V2__add_plants_table.sql
care|V31__create_care_schema.sql|V1__create_care_schema.sql
care|V32__add_care_tasks_table.sql|V2__add_care_tasks_table.sql
"

if [ ! -d "$MIGRATION_DIR" ]; then
	echo "Verzeichnis $MIGRATION_DIR nicht gefunden. Das Script gehört ins Wurzelverzeichnis des Projekts."
	exit 1
fi

# Migrationen, die zu keinem Modul gehören, laufen aus __root und behalten die
# voreingestellte Flyway-Historien-Tabelle. Hier bleibt der Ordner leer.
mkdir -p "$MIGRATION_DIR/__root"

verschoben=0
for eintrag in $MIGRATIONS; do
	modul="${eintrag%%|*}"
	rest="${eintrag#*|}"
	alt="${rest%%|*}"
	neu="${rest#*|}"

	if [ ! -f "$MIGRATION_DIR/$alt" ]; then
		echo "übersprungen: $alt ist nicht (mehr) da"
		continue
	fi

	mkdir -p "$MIGRATION_DIR/$modul"
	mv "$MIGRATION_DIR/$alt" "$MIGRATION_DIR/$modul/$neu"
	echo "$alt -> $modul/$neu"
	verschoben=$((verschoben + 1))
done

echo
echo "$verschoben Migration(en) verschoben."
echo "Jetzt noch spring.modulith.runtime.flyway-enabled=true in der application.properties setzen."
