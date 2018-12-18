--
-- PostgreSQL database dump
--

-- Dumped from database version 10.3
-- Dumped by pg_dump version 10.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
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


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: appointment; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.appointment (
    id integer NOT NULL,
    date timestamp without time zone NOT NULL,
    description character varying(255),
    repvehicle boolean NOT NULL,
    mechanic_id character varying(255) NOT NULL,
    service_id integer NOT NULL,
    vehicle_id character varying(255) NOT NULL
);


ALTER TABLE public.appointment OWNER TO admin;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO admin;

--
-- Name: model; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.model (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.model OWNER TO admin;

--
-- Name: role; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.role (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.role OWNER TO admin;

--
-- Name: service; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.service (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.service OWNER TO admin;

--
-- Name: servicevehicle; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.servicevehicle (
    licenseplate character varying(255) NOT NULL,
    rentedto character varying(255)
);


ALTER TABLE public.servicevehicle OWNER TO admin;

--
-- Name: users; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.users (
    email character varying(50) NOT NULL,
    mobile character varying(15) NOT NULL,
    name character varying(20) NOT NULL,
    oib character varying(11),
    password character varying(255) NOT NULL,
    surname character varying(20) NOT NULL,
    roleid integer
);


ALTER TABLE public.users OWNER TO admin;

--
-- Name: uservehicle; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.uservehicle (
    licenseplate character varying(255) NOT NULL,
    user_email character varying(255)
);


ALTER TABLE public.uservehicle OWNER TO admin;

--
-- Name: vehicle; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.vehicle (
    licenseplate character varying(255) NOT NULL,
    year character varying(255) NOT NULL,
    modelid integer
);


ALTER TABLE public.vehicle OWNER TO admin;

--
-- Data for Name: appointment; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.appointment (id, date, description, repvehicle, mechanic_id, service_id, vehicle_id) FROM stdin;
\.


--
-- Data for Name: model; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.model (id, name) FROM stdin;
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.role (id, name) FROM stdin;
\.


--
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.service (id, name) FROM stdin;
\.


--
-- Data for Name: servicevehicle; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.servicevehicle (licenseplate, rentedto) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.users (email, mobile, name, oib, password, surname, roleid) FROM stdin;
\.


--
-- Data for Name: uservehicle; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.uservehicle (licenseplate, user_email) FROM stdin;
\.


--
-- Data for Name: vehicle; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.vehicle (licenseplate, year, modelid) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- Name: appointment appointment_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT appointment_pkey PRIMARY KEY (id);


--
-- Name: model model_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.model
    ADD CONSTRAINT model_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: service service_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pkey PRIMARY KEY (id);


--
-- Name: servicevehicle servicevehicle_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.servicevehicle
    ADD CONSTRAINT servicevehicle_pkey PRIMARY KEY (licenseplate);


--
-- Name: users uk_s8l2wh3rjdqc9r6j34c4ob3sw; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_s8l2wh3rjdqc9r6j34c4ob3sw UNIQUE (oib);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (email);


--
-- Name: uservehicle uservehicle_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.uservehicle
    ADD CONSTRAINT uservehicle_pkey PRIMARY KEY (licenseplate);


--
-- Name: vehicle vehicle_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vehicle_pkey PRIMARY KEY (licenseplate);


--
-- Name: servicevehicle fk6sry69n892kq5kmfnkkssh85n; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.servicevehicle
    ADD CONSTRAINT fk6sry69n892kq5kmfnkkssh85n FOREIGN KEY (licenseplate) REFERENCES public.vehicle(licenseplate);


--
-- Name: appointment fk_mechanic_email; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fk_mechanic_email FOREIGN KEY (mechanic_id) REFERENCES public.users(email);


--
-- Name: vehicle fk_model_id; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT fk_model_id FOREIGN KEY (modelid) REFERENCES public.model(id);


--
-- Name: servicevehicle fk_reguser_email; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.servicevehicle
    ADD CONSTRAINT fk_reguser_email FOREIGN KEY (rentedto) REFERENCES public.users(email);


--
-- Name: uservehicle fk_reguser_email; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.uservehicle
    ADD CONSTRAINT fk_reguser_email FOREIGN KEY (user_email) REFERENCES public.users(email);


--
-- Name: users fk_role_id; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_role_id FOREIGN KEY (roleid) REFERENCES public.role(id);


--
-- Name: appointment fk_service_name; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fk_service_name FOREIGN KEY (service_id) REFERENCES public.service(id);


--
-- Name: appointment fk_uservehicle_licencplate; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fk_uservehicle_licencplate FOREIGN KEY (vehicle_id) REFERENCES public.uservehicle(licenseplate);


--
-- Name: uservehicle fkgrtj7770312h5edvsaexwoglq; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.uservehicle
    ADD CONSTRAINT fkgrtj7770312h5edvsaexwoglq FOREIGN KEY (licenseplate) REFERENCES public.vehicle(licenseplate);


--
-- PostgreSQL database dump complete
--

