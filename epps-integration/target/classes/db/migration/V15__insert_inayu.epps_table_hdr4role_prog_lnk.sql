do
$$
declare
v_tb_sr_no integer;
begin

if not exists(select 1 from inayu.epps_table_hdr where prog_id = 'listSysAdminRoleProgramLink') then
insert into inayu.epps_table_hdr
(prog_id ,    module_id ,    prog_display_name ,    show_rep_yn ,    mtqr_flag)
values('listSysAdminRoleProgramLink','ADMIN','Role to Screen Access Link',0,'M')
returning tb_sr_no into v_tb_sr_no;

insert into inayu.epps_table_dtl
(
    tb_sr_no  , tb_disp_name ,    db_col_name ,    column_disp_name ,
    dto_name ,  display_yn ,  display_column_seq ,    db_data_type ,
    java_data_type ,   allignment ,    max_len ,    fld_name ,    dec_prec ,
    req_yn ,    dropdown_yn  , screen_type ,    active_yn ,    api_url )
values(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','comp_cd','Company','companyCode',0,20,'smallint','Integer',NULL,NULL,NULL,NULL,0,null,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','div_cd','Division','divisionCode',1,1,'smallint','Integer',NULL,NULL,NULL,NULL,0,null,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','role_cd','Role','roleCode',1,1,'smallint','Integer','L',NULL,null,0,1,1,'Form',1,'{apiUrl}'),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','prog_cd','Program','programCode',1,1,'integer','Integer','L',NULL,null,0,1,1,'Form',1,'/roleProgramLinkMaster/v1/roleProgram/{roleCode}'),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','created_by','Created By','createdBy',1,1,'character varying','String','L',null,null,0,1,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','created_dt','Created Date','createdDate',1,1,'timestamp without time zone','Date','C',NULL,NULL,0,1,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','updated_by','Updated By','updatedBy',1,1,'character varying','String','L',null,null,0,1,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','updated_dt','Updated Date','updatedDate',1,1,'timestamp without time zone','Date','L',NULL,null,0,1,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','terminal_id','Ip Address','ipAddress',1,1,'character varying','String','L',null,null,0,100,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','active_yn','Active Y/N','activeYn',1,1,'integer','Integer','L',NULL,null,0,106,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','insert_flag','Insert Allowed Y/N','insertFlagYn',1,1,'integer','Integer','L',NULL,null,0,106,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','update_flag','Update Allowed Y/N','updateFlagYn',1,1,'integer','Integer','L',NULL,null,0,105,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','delete_flag','Delete Allowed Y/N','deleteFlagYn',1,1,'integer','Integer','L',NULL,null,0,104,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','query_flag','Query Allowed Y/N','queryFlagYn',1,1,'integer','Integer','L',NULL,null,0,103,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','creator_role_cd','Creator Role','creatorRole',1,1,'smallint','Integer','L',NULL,null,0,102,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','updator_role_cd','Updator Role','updatorRole',1,1,'smallint','Integer','L',NULL,null,0,102,0,'Form',1,NULL),
(v_tb_sr_no,'epps_admin.epps_role_prog_lnk','default_yn','Default Yes/No','defaultYn',1,1,'smallint','Integer','L',NULL,null,0,101,0,'Form',1,NULL);

end if;
end;
$$;