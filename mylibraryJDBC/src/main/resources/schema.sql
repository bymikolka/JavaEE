CREATE ROLE myuser WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION
  ENCRYPTED PASSWORD 'md5dd9c52d41abcc8c5de5d717d9fd2efee'; --mypass

ALTER ROLE myuser SET search_path TO public;


CREATE DATABASE library
    WITH 
    OWNER = myuser
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
    
    
 -- Table: public.person

DROP TABLE public.person;

CREATE TABLE public.person
(
    id integer NOT NULL DEFAULT nextval('person_id_seq'::regclass),
    username text NOT NULL,
    lastname text NOT NULL,
    firstname text NOT NULL,
    description text NOT NULL,
    email text NOT NULL,
    role numeric NOT NULL,
	"DTYPE" text NOT NULL,
    CONSTRAINT "personPK" PRIMARY KEY (id)
);

ALTER TABLE public.person
    OWNER to myuser;
    
-- SEQUENCE: public.person_id_seq

-- DROP SEQUENCE public.person_id_seq;

CREATE SEQUENCE public.person_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.person_id_seq
    OWNER TO myuser;   
    
    
    
 -- Table: public.person

--DROP TABLE public.users;

CREATE TABLE public.users
(
    id serial NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    "group" numeric(2) NOT NULL,
    PRIMARY KEY (id, username)
);
ALTER TABLE public.person
    ADD COLUMN "DTYPE" text NOT NULL;

ALTER TABLE public.users
    OWNER to myuser;
   
    
INSERT INTO public.users(username, password, "group")
	VALUES ( 'admin', 'admin', 1);
	
	INSERT INTO public.users(
	username, password, "group")
	VALUES ('user', 'user', 0);