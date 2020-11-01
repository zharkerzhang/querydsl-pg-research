# run below extension with administrator
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS pgcrypto;

create TABLE public."user"
(
    t_id uuid NOT NULL,
    created_at    timestamp without time zone,
    modified_at   timestamp without time zone,
    id            character varying(255),
    country       character varying(255),
    customized_attributes jsonb,
    date_of_birth timestamp with time zone,
    date_of_death timestamp without time zone,
    editable      boolean,
    emails        character varying(255),
    external_ref  character varying(255),
    gender        character varying(255),
    job_title     character varying(255),
    nationality   character varying(255),
    password      character varying(255),
    status        character varying(255),
    user_types    text[],
    age           int,
    age_range     character varying(255)
);

alter table ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (t_id);



create table resource
(
    t_id       uuid constraint resource_pk primary key,
    created_at timestamp,
    modified_at timestamp,
    id         varchar not null,
    asset      varchar,
    uri        varchar(1000),
    attributes jsonb,
    name       varchar
);

create unique index resource_uindex on resource (id);

create table verb
(
    t_id uuid constraint verb_pk primary key,
    created_at timestamp,
    modified_at timestamp,
    id varchar not null,
    name varchar,
    attributes jsonb
);
create unique index verb_uindex on verb (id);

create table resource_verb_relation
(
    t_id uuid constraint resource_verb_relation_pk primary key,
    created_at timestamp,
    modified_at timestamp,
    resource_id uuid,
    verb_id uuid
);
create unique index resource_verb_relation_uindex on resource_verb_relation (resource_id, verb_id);