CREATE SCHEMA IF NOT EXISTS owner_schema;

SET SEARCH_PATH TO 'owner_schema';

CREATE TABLE owners (
    id   UUID PRIMARY KEY,
    name TEXT NOT NULL
);

INSERT INTO owners (id, name)
    VALUES ('ee3829e4-fe2b-4d03-b2a1-70f1425d8c1c', 'Anna Schmidt'),
           ('85483586-044c-9778-73b1-6327133cf030', 'Clara Jung'),
           ('259ca287-08d0-50b0-3999-183b93e2e5bc', 'Tore Larson');
