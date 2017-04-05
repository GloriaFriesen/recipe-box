--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: ingredients; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE ingredients (
    id integer NOT NULL,
    recipe_id integer,
    measure character varying,
    ingredient_text character varying
);


ALTER TABLE ingredients OWNER TO "Guest";

--
-- Name: ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ingredients_id_seq OWNER TO "Guest";

--
-- Name: ingredients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE ingredients_id_seq OWNED BY ingredients.id;


--
-- Name: instructions; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE instructions (
    id integer NOT NULL,
    recipe_id integer,
    step integer,
    instruction_text character varying
);


ALTER TABLE instructions OWNER TO "Guest";

--
-- Name: instructions_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE instructions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE instructions_id_seq OWNER TO "Guest";

--
-- Name: instructions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE instructions_id_seq OWNED BY instructions.id;


--
-- Name: recipes; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE recipes (
    id integer NOT NULL,
    name character varying,
    rating integer
);


ALTER TABLE recipes OWNER TO "Guest";

--
-- Name: recipes_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipes_id_seq OWNER TO "Guest";

--
-- Name: recipes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipes_id_seq OWNED BY recipes.id;


--
-- Name: recipes_tags; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE recipes_tags (
    id integer NOT NULL,
    recipe_id integer,
    tag_id integer
);


ALTER TABLE recipes_tags OWNER TO "Guest";

--
-- Name: recipes_tags_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipes_tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipes_tags_id_seq OWNER TO "Guest";

--
-- Name: recipes_tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipes_tags_id_seq OWNED BY recipes_tags.id;


--
-- Name: tags; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE tags (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE tags OWNER TO "Guest";

--
-- Name: tags_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE tags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tags_id_seq OWNER TO "Guest";

--
-- Name: tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE tags_id_seq OWNED BY tags.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ingredients ALTER COLUMN id SET DEFAULT nextval('ingredients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY instructions ALTER COLUMN id SET DEFAULT nextval('instructions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes ALTER COLUMN id SET DEFAULT nextval('recipes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes_tags ALTER COLUMN id SET DEFAULT nextval('recipes_tags_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tags ALTER COLUMN id SET DEFAULT nextval('tags_id_seq'::regclass);


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY ingredients (id, recipe_id, measure, ingredient_text) FROM stdin;
\.


--
-- Name: ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('ingredients_id_seq', 1, false);


--
-- Data for Name: instructions; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY instructions (id, recipe_id, step, instruction_text) FROM stdin;
\.


--
-- Name: instructions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('instructions_id_seq', 1, false);


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes (id, name, rating) FROM stdin;
\.


--
-- Name: recipes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipes_id_seq', 1, false);


--
-- Data for Name: recipes_tags; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes_tags (id, recipe_id, tag_id) FROM stdin;
\.


--
-- Name: recipes_tags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipes_tags_id_seq', 1, false);


--
-- Data for Name: tags; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY tags (id, name) FROM stdin;
\.


--
-- Name: tags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('tags_id_seq', 1, false);


--
-- Name: ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (id);


--
-- Name: instructions_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY instructions
    ADD CONSTRAINT instructions_pkey PRIMARY KEY (id);


--
-- Name: recipes_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes
    ADD CONSTRAINT recipes_pkey PRIMARY KEY (id);


--
-- Name: recipes_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes_tags
    ADD CONSTRAINT recipes_tags_pkey PRIMARY KEY (id);


--
-- Name: tags_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (id);


--
-- Name: recipes_tags_recipe_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes_tags
    ADD CONSTRAINT recipes_tags_recipe_id_fkey FOREIGN KEY (recipe_id) REFERENCES recipes(id);


--
-- Name: recipes_tags_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes_tags
    ADD CONSTRAINT recipes_tags_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES tags(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

