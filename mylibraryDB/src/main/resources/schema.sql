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
    id serial NOT NULL,
    username text NOT NULL,
    lastname text NOT NULL,
    firstname text NOT NULL,
    description text NOT NULL,
    email text NOT NULL,
    role numeric NOT NULL,
    CONSTRAINT "personPK" PRIMARY KEY (id)
);

ALTER TABLE public.person
    OWNER to myuser;
    
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
   
    
INSERT INTO public.users(
	id, username, password, "group")
	VALUES (1, 'admin', 'admin', 1);
	
	INSERT INTO public.users(
	id, username, password, "group")
	VALUES (2, 'user', 'user', 0);