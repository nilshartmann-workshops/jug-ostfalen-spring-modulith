SET SEARCH_PATH TO 'plant_schema';

CREATE TABLE plants (
    id         UUID PRIMARY KEY,
    owner_id   UUID NOT NULL,
    name       TEXT NOT NULL,
    plant_type TEXT NOT NULL,
    location   TEXT NOT NULL
);