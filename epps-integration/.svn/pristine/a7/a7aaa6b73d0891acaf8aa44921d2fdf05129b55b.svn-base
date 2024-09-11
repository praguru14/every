-- Table: inayu.appointment_dtl_alog

-- DROP TABLE inayu.appointment_dtl_alog;

CREATE TABLE if not exists inayu.appointment_dtl_alog
(
    sr_no serial NOT NULL ,
    ref_sr_no integer,
    inv_stage smallint,
    audit_flag text COLLATE pg_catalog."default",
    log_by text COLLATE pg_catalog."default",
    log_dt timestamp without time zone,
	log_by_role_cd smallint,
    machine_id text COLLATE pg_catalog."default",
    amd_no smallint,
    CONSTRAINT appointment_dtl_alog_pkey PRIMARY KEY (sr_no)
        USING INDEX TABLESPACE tbs_epps_database
)

TABLESPACE tbs_epps_database;

ALTER TABLE inayu.appointment_dtl_alog
    OWNER to epps_programmer;

GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE inayu.appointment_dtl_alog TO epps_developer;

GRANT ALL ON TABLE inayu.appointment_dtl_alog TO epps_programmer;

GRANT SELECT ON TABLE inayu.appointment_dtl_alog TO epps_readonly;