do
$$
begin
	
	DROP VIEW inayu.per_mst_vw;

alter table  inmdb.per_dtls
alter column guv_email_id type text,
alter column nud_email_id type text,
alter column pers_email_id type text;

-- View: inayu.per_mst_vw

-- DROP VIEW inayu.per_mst_vw;

CREATE OR REPLACE VIEW inayu.per_mst_vw
 AS
 SELECT pm.sr_no,
    pm.pno,
    pm.first_name,
    pm.mid_name,
    pm.last_name,
    pm.emp_gender,
    pm.dob,
    pm.placeofbirth,
    pm.user_type_code,
    pm.full_name,
    pm.isactive,
    pm.created_by,
    pm.created_dt,
    pm.modified_by,
    pm.modified_dt,
    pm.machine_id,
    pm.source_app,
    pd.unit_code,
    u.name AS unit_name,
    pd.rank_code,
    r.name AS rank_name,
    pd.nud_email_id
   FROM inmdb.per_mst pm
     LEFT JOIN inmdb.per_dtls pd ON pm.pno::text = pd.pno::text
     LEFT JOIN inmdb.unit_mst u ON pd.unit_code::text = u.code::text
     LEFT JOIN inmdb.rank_mst r ON pgp_sym_decrypt(pd.rank_code::bytea, current_setting('encrypt.key'::text)) = r.code::text;

ALTER TABLE inayu.per_mst_vw
    OWNER TO epps_programmer;

GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE inayu.per_mst_vw TO epps_developer;
GRANT ALL ON TABLE inayu.per_mst_vw TO epps_programmer;
GRANT SELECT ON TABLE inayu.per_mst_vw TO epps_readonly;
GRANT ALL ON TABLE inayu.per_mst_vw TO dbaactivity;
end;
$$;