CREATE TABLE product
(
    id character varying(36) NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    price numeric(19,2),
    CONSTRAINT product_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE product
    OWNER to postgres;

GRANT INSERT, SELECT, UPDATE, DELETE, REFERENCES ON TABLE public.tenant TO multi_tenant;

GRANT ALL ON TABLE public.tenant TO postgres;