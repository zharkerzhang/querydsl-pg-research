create TABLE public."user"
(
    t_id uuid NOT NULL,
    created_at    timestamp without time zone,
    modified_at   timestamp without time zone,
    id            character varying(255),
    country       character varying(255),
    customized_attributes jsonb,
    date_of_birth timestamp without time zone,
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


