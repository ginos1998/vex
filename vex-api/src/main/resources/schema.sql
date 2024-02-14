CREATE TABLE IF NOT EXISTS __vex
(
    id_vex SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS document_type
(
    doc_type_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

INSERT INTO document_type (name) VALUES ('DNI');

CREATE TABLE IF NOT EXISTS personal (
    personal_id SERIAL PRIMARY KEY,
    username varchar(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    address varchar(100),
    phone varchar(20),
    doc_type_id integer CONSTRAINT fk_doc_type_id REFERENCES document_type(doc_type_id),
    doc_number varchar(20)
);