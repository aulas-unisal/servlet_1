CREATE DATABASE unisal
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'pt_BR.UTF-8'
       LC_CTYPE = 'pt_BR.UTF-8'
       CONNECTION LIMIT = -1;
       
    
       
CREATE SEQUENCE serialcliente
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 7
  CACHE 1;
ALTER TABLE serialcliente
  OWNER TO postgres;
  
  CREATE SEQUENCE serialclientefone
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 7
  CACHE 1;
ALTER TABLE serialclientefone
  OWNER TO postgres;
  

  CREATE TABLE cliente_pessoa_fisica
(
  id bigint NOT NULL DEFAULT nextval('serialcliente'::regclass),
  nome character varying(500),
  cpf character varying(20),
  endereco character varying(500),
  datanacimento date,
  numerologradouro integer,
  CONSTRAINT cliente_pessoa_fisica_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cliente_pessoa_fisica
  OWNER TO postgres;
  

  
  CREATE TABLE telefone_cliente
(
  id bigint NOT NULL DEFAULT nextval('serialclientefone'::regclass),
  tipotelefone character varying NOT NULL,
  numero character varying,
  clientepessoafisica bigint NOT NULL,
  CONSTRAINT telefone_cliente_pkey PRIMARY KEY (id),
  CONSTRAINT telefone_cliente_clientepessoafisica_fkey FOREIGN KEY (clientepessoafisica)
      REFERENCES cliente_pessoa_fisica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE telefone_cliente
  OWNER TO postgres;


ALTER TABLE cliente_pessoa_fisica ADD COLUMN foto text;

ALTER TABLE cliente_pessoa_fisica ADD COLUMN sexo character varying(10);
ALTER TABLE cliente_pessoa_fisica ADD COLUMN ativo boolean;

