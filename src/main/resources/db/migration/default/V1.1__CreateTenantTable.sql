CREATE TABLE tenant
(
    tenant_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    schema_name character varying(16) COLLATE pg_catalog."default" NOT NULL,
    data_base_url character varying(256) COLLATE pg_catalog."default",
    user_db character varying(18) COLLATE pg_catalog."default",
    password_db character varying(256) COLLATE pg_catalog."default",
    CONSTRAINT tenant_pkey PRIMARY KEY (tenant_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE tenant
    OWNER to postgres;

GRANT INSERT, SELECT, UPDATE, DELETE, REFERENCES ON TABLE tenant TO multi_tenant;

GRANT ALL ON TABLE tenant TO postgres;