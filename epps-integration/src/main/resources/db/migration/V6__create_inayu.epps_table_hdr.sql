do
$$
begin

if not exists (select 1 from information_schema.tables where table_name ='epps_table_hdr' and table_schema ='inayu' ) then

CREATE TABLE inayu.epps_table_hdr
(
    tb_sr_no serial NOT NULL primary key,
    prog_id text,
    module_id text NOT NULL,
    prog_display_name text,
    show_rep_yn smallint DEFAULT (1)::smallint,
    mtqr_flag text
)

TABLESPACE tbs_epps_database;

ALTER TABLE inayu.epps_table_hdr
    OWNER to epps_programmer;

GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE inayu.epps_table_hdr TO epps_developer;

GRANT ALL ON TABLE inayu.epps_table_hdr TO epps_programmer;

GRANT SELECT ON TABLE inayu.epps_table_hdr TO epps_readonly;


-- Table: inayu.epps_table_dtl

-- DROP TABLE inayu.epps_table_dtl;

CREATE TABLE inayu.epps_table_dtl
(
    tb_sr_no integer NOT NULL,
    tb_dtl_sr_no serial NOT NULL primary key,
	tb_disp_name text,
    db_col_name text,
    column_disp_name text,
    dto_name text,
    display_yn integer,
    display_column_seq integer,
    db_data_type text,
    java_data_type text,
    allignment text,
    max_len text,
    fld_name text,
    dec_prec text,
    req_yn integer,
    dropdown_yn integer DEFAULT 0,
	screen_type text,
    active_yn integer,
    api_url text,
    CONSTRAINT fkey_tb_dtlhdr_srno FOREIGN KEY (tb_sr_no)
        REFERENCES epps_admin.epps_table_hdr (tb_sr_no) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE tbs_epps_database;

ALTER TABLE inayu.epps_table_dtl
    OWNER to epps_programmer;

GRANT INSERT, SELECT, UPDATE, DELETE ON TABLE inayu.epps_table_dtl TO epps_developer;

GRANT ALL ON TABLE inayu.epps_table_dtl TO epps_programmer;

GRANT SELECT ON TABLE inayu.epps_table_dtl TO epps_readonly;

end if;
end;
$$;