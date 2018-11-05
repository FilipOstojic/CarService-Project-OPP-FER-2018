--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- Name: all_users; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE all_users (
    email character varying(40) NOT NULL,
    password character varying(100) NOT NULL,
    role integer NOT NULL
);


ALTER TABLE all_users OWNER TO admin;

--
-- Name: TABLE all_users; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE all_users IS 'svi korisnici';


--
-- Name: mechanic; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE mechanic (
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    email character varying(40) NOT NULL,
    mobile character varying(15) NOT NULL,
    oib character varying(11) NOT NULL
);


ALTER TABLE mechanic OWNER TO admin;

--
-- Name: TABLE mechanic; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE mechanic IS 'serviser';


--
-- Name: models; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE models (
    id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE models OWNER TO admin;

--
-- Name: TABLE models; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE models IS 'modeli automobila';


--
-- Name: roles; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE roles (
    id integer NOT NULL,
    name character varying(15) NOT NULL
);


ALTER TABLE roles OWNER TO admin;

--
-- Name: TABLE roles; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE roles IS 'uloge korisnika';


--
-- Name: services; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE services (
    id integer NOT NULL,
    service character varying(100) NOT NULL
);


ALTER TABLE services OWNER TO admin;

--
-- Name: TABLE services; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE services IS 'usluge servisa';


--
-- Name: timetable; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE timetable (
    appointment timestamp without time zone NOT NULL,
    mechanic character varying(11) NOT NULL,
    licance_plate character varying(15) NOT NULL,
    service integer NOT NULL,
    description character varying(200),
    rep_vehicle boolean NOT NULL
);


ALTER TABLE timetable OWNER TO admin;

--
-- Name: TABLE timetable; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE timetable IS 'raspored termina za prijavu automobila';


--
-- Name: user; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE "user" (
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    email character varying(40) NOT NULL,
    mobile character varying(20) NOT NULL,
    oib character varying(11) NOT NULL
);


ALTER TABLE "user" OWNER TO admin;

--
-- Name: TABLE "user"; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE "user" IS 'registrirani korisnik';


--
-- Name: vehicle_service; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE vehicle_service (
    model integer NOT NULL,
    licance_plate character varying(15) NOT NULL,
    rented_to character varying(11)
);


ALTER TABLE vehicle_service OWNER TO admin;

--
-- Name: TABLE vehicle_service; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE vehicle_service IS 'automobili servisa';


--
-- Name: vehicle_user; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE vehicle_user (
    model integer NOT NULL,
    licence_plate character varying(15) NOT NULL,
    owner character varying(40) NOT NULL,
    year character varying(4) NOT NULL
);


ALTER TABLE vehicle_user OWNER TO admin;

--
-- Name: TABLE vehicle_user; Type: COMMENT; Schema: public; Owner: admin
--

COMMENT ON TABLE vehicle_user IS 'vozila registriranih korisnika';


--
-- Data for Name: all_users; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY all_users (email, password, role) FROM stdin;
\.


--
-- Data for Name: mechanic; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY mechanic (name, surname, email, mobile, oib) FROM stdin;
\.


--
-- Data for Name: models; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY models (id, name) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY roles (id, name) FROM stdin;
\.


--
-- Data for Name: services; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY services (id, service) FROM stdin;
\.


--
-- Data for Name: timetable; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY timetable (appointment, mechanic, licance_plate, service, description, rep_vehicle) FROM stdin;
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY "user" (name, surname, email, mobile, oib) FROM stdin;
\.


--
-- Data for Name: vehicle_service; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY vehicle_service (model, licance_plate, rented_to) FROM stdin;
\.


--
-- Data for Name: vehicle_user; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY vehicle_user (model, licence_plate, owner, year) FROM stdin;
\.


--
-- Name: all_users all_users_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY all_users
    ADD CONSTRAINT all_users_pkey PRIMARY KEY (email);


--
-- Name: mechanic mechanic_oib_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY mechanic
    ADD CONSTRAINT mechanic_oib_key UNIQUE (oib);


--
-- Name: mechanic mechanic_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY mechanic
    ADD CONSTRAINT mechanic_pkey PRIMARY KEY (email);


--
-- Name: models models_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY models
    ADD CONSTRAINT models_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: services services_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY services
    ADD CONSTRAINT services_pkey PRIMARY KEY (id);


--
-- Name: timetable timetable_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY timetable
    ADD CONSTRAINT timetable_pkey PRIMARY KEY (appointment, mechanic);


--
-- Name: user user_oib_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_oib_key UNIQUE (oib);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (email);


--
-- Name: vehicle_service vehicle_service_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY vehicle_service
    ADD CONSTRAINT vehicle_service_pkey PRIMARY KEY (licance_plate);


--
-- Name: vehicle_user vehicle_user_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY vehicle_user
    ADD CONSTRAINT vehicle_user_pkey PRIMARY KEY (licence_plate);


--
-- Name: fki_timetable_licence_plate_fkey; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fki_timetable_licence_plate_fkey ON timetable USING btree (licance_plate);


--
-- Name: fki_timetable_mechanic_fkey; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fki_timetable_mechanic_fkey ON timetable USING btree (mechanic);


--
-- Name: fki_timetable_service_fkey; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fki_timetable_service_fkey ON timetable USING btree (service);


--
-- Name: fki_vehicle_user_models_fkey; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fki_vehicle_user_models_fkey ON vehicle_user USING btree (model);


--
-- Name: all_users all_users_role_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY all_users
    ADD CONSTRAINT all_users_role_fkey FOREIGN KEY (role) REFERENCES roles(id);


--
-- Name: timetable timetable_licence_plate_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY timetable
    ADD CONSTRAINT timetable_licence_plate_fkey FOREIGN KEY (licance_plate) REFERENCES vehicle_user(licence_plate);


--
-- Name: timetable timetable_mechanic_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY timetable
    ADD CONSTRAINT timetable_mechanic_fkey FOREIGN KEY (mechanic) REFERENCES mechanic(oib);


--
-- Name: timetable timetable_service_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY timetable
    ADD CONSTRAINT timetable_service_fkey FOREIGN KEY (service) REFERENCES services(id);


--
-- Name: vehicle_service vehicle_service_model_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY vehicle_service
    ADD CONSTRAINT vehicle_service_model_fkey FOREIGN KEY (model) REFERENCES models(id);


--
-- Name: vehicle_service vehicle_service_rented_to_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY vehicle_service
    ADD CONSTRAINT vehicle_service_rented_to_fkey FOREIGN KEY (rented_to) REFERENCES "user"(oib);


--
-- Name: vehicle_user vehicle_user_models_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY vehicle_user
    ADD CONSTRAINT vehicle_user_models_fkey FOREIGN KEY (model) REFERENCES models(id);


--
-- Name: vehicle_user vehicle_user_owner_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY vehicle_user
    ADD CONSTRAINT vehicle_user_owner_fkey FOREIGN KEY (owner) REFERENCES "user"(oib);


--
-- PostgreSQL database dump complete
--

