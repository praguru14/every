DO
$$
declare 
v_prog_cd integer;
BEGIN
IF NOT EXISTS (select 1 from epps_admin.epps_prog_mst
		where prog_id = 'loadExaminationStatusReport'
		  and prog_disp_name = 'Examination Status Report') then

INSERT INTO epps_admin.epps_prog_mst
(
  comp_cd ,
  prog_cd ,
  prog_id ,
  prog_disp_name ,
  prog_long_name ,
  prog_type ,
  parent_id ,
  module_id ,
  tran_indicator ,
  prog_mtqr_flag ,
  rep_type ,
  menu_pass_parameter ,
  prog_report_name ,
  prog_menu_display_yn ,
  prog_disp_seq_no ,
  prog_app_flag ,
  default_access_flag ,
  epps_admin_flag ,
  MWA_FLAG ,
  mob_disp_name ,
  mob_sub_menu_flag,
  mob_menu_pass_para ,
  screen_role_flag ,
  prog_doc_id ,
  prog_developed_by ,
  created_by ,
  created_dt ,
  updated_by ,
  updated_dt ,
  terminal_id ,
  active_yn ,
  rep_max_days ,
  report_description ,
  session_req_yn ,
  approval_flag ,
  creator_role_cd ,
  updator_role_cd ,
  prog_tree_del_flag ,
  MOB_ECS_FLAG )
  VALUES (
  1::SMALLINT, ---  comp_cd smallint NOT NULL,
  (SELECT (MAX(PROG_CD)::integer)+1 FROM epps_admin.epps_prog_mst)::INTEGER, ---prog_cd integer NOT NULL,
  'loadExaminationStatusReport'::character varying, ---prog_id character varying(40),
  'Examination Status Report'::character varying,--prog_disp_name character varying(100) NOT NULL,
  'Examination Status Report'::character varying,--prog_long_name character varying(2000),
  'R'::character varying,--prog_type character varying(1) NOT NULL DEFAULT 'F'::character varying,
  NULL::integer,--parent_id integer,
  'INAYU'::character varying, ---module_id character varying(5),
  Null::character varying,---tran_indicator character varying(20),
  'Q'::character varying,---prog_mtqr_flag character varying(1) DEFAULT 'T'::character varying, -- Set it (M) for Master Screen, (T) for Transaction, (Q) for Query Screen and (R) for Report .
  null::character varying, ---rep_type character(1) DEFAULT NULL::bpchar, -- Report Is of Type (N) For Normal Reports, (M) For MIS Reports
  NULL::character varying, ---menu_pass_parameter character varying(50), -- If PROG_MTQR_FLAG is (T), Then Pop-Up one window, from which user select data & continues Transacion entry(eg. Issue).
  NULL::character varying, ---prog_report_name character varying(50),
  'Y'::character varying, ---prog_menu_display_yn character varying(1) DEFAULT 'Y'::character varying, -- If this flag is set to(N), Respectinve entry will not Display In Menu
  (SELECT (MAX(PROG_DISP_SEQ_NO)::smallint)+1 FROM epps_admin.epps_prog_mst)::SMALLINT, ---prog_disp_seq_no smallint,
  'S'::character varying, ---prog_app_flag character(1) DEFAULT 'S'::bpchar, -- Indicates that The screen having Screen Based Approval (S) or Role Based Approval(R)
  'N'::character varying, ---default_access_flag character(1) DEFAULT 'N'::bpchar,
  'N'::character varying, ---epps_admin_flag character varying(1) DEFAULT 'N'::character varying, -- Special Screen have access to Only EPPS ADMIN Role, that are identified by this flag (Y)
  'A'::character varying, ---MWA_FLAG ,
  NULL::character varying, ---mob_disp_name character varying(50),
  NULL::character varying, ---mob_sub_menu_flag character varying(1),
  NULL::character varying, ---mob_menu_pass_para character varying(30),
  NULL::character varying, ---screen_role_flag character varying(25),
  NULL::character varying, ---prog_doc_id character varying(25),
  'P0'::character varying, ---prog_developed_by character varying(10),
  'P0'::character varying, ---created_by character varying(10),
  NOW(),--created_dt timestamp without time zone,
  NULL::character varying, ---updated_by character varying(10),
  NULL,---updated_dt timestamp without time zone,
  'Uma_BE'::character varying, ---terminal_id character varying(100),
  1::smallint, ---active_yn character varying(1) DEFAULT 'Y'::character varying,
  NULL::INTEGER, ---rep_max_days integer,
  NULL::character varying, ---report_description character(250),
  'Y'::character varying, ---session_req_yn character(1), -- Used to set Session Parameter Active for the  Screen or not
  'N'::character varying, ---approval_flag character varying DEFAULT 'N'::character varying, -- Used to indicate Transaction Approval cycle is applied or not
  1::smallint, ---creator_role_cd smallint,
  NULL::SMALLINT, ---updator_role_cd smallint,
  'N'::character varying,---prog_tree_del_flag character varying(15),
  'E'::character varying---MOB_ECS_FLAG character varying(15),
  );    

select  prog_cd into v_prog_cd
from epps_admin.epps_prog_mst 
where prog_id = 'loadExaminationStatusReport';


insert into epps_admin.epps_role_prog_lnk
	(comp_cd,div_cd,role_cd,prog_cd,created_by,created_dt,terminal_id,active_yn,insert_flag,
    update_flag,delete_flag,query_flag,creator_role_cd,default_yn)
values (1,1,2,v_prog_cd,'PO',now(),'Uma_BE'::character varying,1,1,1,1,1,1,0);

insert into epps_admin.epps_role_prog_lnk
	(comp_cd,div_cd,role_cd,prog_cd,created_by,created_dt,terminal_id,active_yn,insert_flag,
    update_flag,delete_flag,query_flag,creator_role_cd,default_yn)
values (1,1,410,v_prog_cd,'PO',now(),'Uma_BE'::character varying,1,1,1,1,1,1,0);

END IF ;
END;
$$;

DO
$$
declare 
v_prog_cd integer;
BEGIN
IF NOT EXISTS (select 1 from epps_admin.epps_prog_mst
		where prog_id = 'loadAudittraireport'
		  and prog_disp_name = 'Audit Trail Report') then

INSERT INTO epps_admin.epps_prog_mst
(
  comp_cd ,
  prog_cd ,
  prog_id ,
  prog_disp_name ,
  prog_long_name ,
  prog_type ,
  parent_id ,
  module_id ,
  tran_indicator ,
  prog_mtqr_flag ,
  rep_type ,
  menu_pass_parameter ,
  prog_report_name ,
  prog_menu_display_yn ,
  prog_disp_seq_no ,
  prog_app_flag ,
  default_access_flag ,
  epps_admin_flag ,
  MWA_FLAG ,
  mob_disp_name ,
  mob_sub_menu_flag,
  mob_menu_pass_para ,
  screen_role_flag ,
  prog_doc_id ,
  prog_developed_by ,
  created_by ,
  created_dt ,
  updated_by ,
  updated_dt ,
  terminal_id ,
  active_yn ,
  rep_max_days ,
  report_description ,
  session_req_yn ,
  approval_flag ,
  creator_role_cd ,
  updator_role_cd ,
  prog_tree_del_flag ,
  MOB_ECS_FLAG )
  VALUES (
  1::SMALLINT, ---  comp_cd smallint NOT NULL,
  (SELECT (MAX(PROG_CD)::integer)+1 FROM epps_admin.epps_prog_mst)::INTEGER, ---prog_cd integer NOT NULL,
  'loadAudittraireport'::character varying, ---prog_id character varying(40),
  'Audit Trail Report'::character varying,--prog_disp_name character varying(100) NOT NULL,
  'Audit Trail Report'::character varying,--prog_long_name character varying(2000),
  'R'::character varying,--prog_type character varying(1) NOT NULL DEFAULT 'F'::character varying,
  NULL::integer,--parent_id integer,
  'INAYU'::character varying, ---module_id character varying(5),
  Null::character varying,---tran_indicator character varying(20),
  'Q'::character varying,---prog_mtqr_flag character varying(1) DEFAULT 'T'::character varying, -- Set it (M) for Master Screen, (T) for Transaction, (Q) for Query Screen and (R) for Report .
  null::character varying, ---rep_type character(1) DEFAULT NULL::bpchar, -- Report Is of Type (N) For Normal Reports, (M) For MIS Reports
  NULL::character varying, ---menu_pass_parameter character varying(50), -- If PROG_MTQR_FLAG is (T), Then Pop-Up one window, from which user select data & continues Transacion entry(eg. Issue).
  NULL::character varying, ---prog_report_name character varying(50),
  'Y'::character varying, ---prog_menu_display_yn character varying(1) DEFAULT 'Y'::character varying, -- If this flag is set to(N), Respectinve entry will not Display In Menu
  (SELECT (MAX(PROG_DISP_SEQ_NO)::smallint)+1 FROM epps_admin.epps_prog_mst)::SMALLINT, ---prog_disp_seq_no smallint,
  'S'::character varying, ---prog_app_flag character(1) DEFAULT 'S'::bpchar, -- Indicates that The screen having Screen Based Approval (S) or Role Based Approval(R)
  'N'::character varying, ---default_access_flag character(1) DEFAULT 'N'::bpchar,
  'N'::character varying, ---epps_admin_flag character varying(1) DEFAULT 'N'::character varying, -- Special Screen have access to Only EPPS ADMIN Role, that are identified by this flag (Y)
  'A'::character varying, ---MWA_FLAG ,
  NULL::character varying, ---mob_disp_name character varying(50),
  NULL::character varying, ---mob_sub_menu_flag character varying(1),
  NULL::character varying, ---mob_menu_pass_para character varying(30),
  NULL::character varying, ---screen_role_flag character varying(25),
  NULL::character varying, ---prog_doc_id character varying(25),
  'P0'::character varying, ---prog_developed_by character varying(10),
  'P0'::character varying, ---created_by character varying(10),
  NOW(),--created_dt timestamp without time zone,
  NULL::character varying, ---updated_by character varying(10),
  NULL,---updated_dt timestamp without time zone,
  'Sushant'::character varying, ---terminal_id character varying(100),
  1::smallint, ---active_yn character varying(1) DEFAULT 'Y'::character varying,
  NULL::INTEGER, ---rep_max_days integer,
  NULL::character varying, ---report_description character(250),
  'Y'::character varying, ---session_req_yn character(1), -- Used to set Session Parameter Active for the  Screen or not
  'N'::character varying, ---approval_flag character varying DEFAULT 'N'::character varying, -- Used to indicate Transaction Approval cycle is applied or not
  1::smallint, ---creator_role_cd smallint,
  NULL::SMALLINT, ---updator_role_cd smallint,
  'N'::character varying,---prog_tree_del_flag character varying(15),
  'E'::character varying---MOB_ECS_FLAG character varying(15),
  );    

select  prog_cd into v_prog_cd
from epps_admin.epps_prog_mst 
where prog_id = 'loadAudittraireport';


insert into epps_admin.epps_role_prog_lnk
	(comp_cd,div_cd,role_cd,prog_cd,created_by,created_dt,terminal_id,active_yn,insert_flag,
    update_flag,delete_flag,query_flag,creator_role_cd,default_yn)
values (1,1,2,v_prog_cd,'PO',now(),'Sushant'::character varying,1,1,1,1,1,1,0);

insert into epps_admin.epps_role_prog_lnk
	(comp_cd,div_cd,role_cd,prog_cd,created_by,created_dt,terminal_id,active_yn,insert_flag,
    update_flag,delete_flag,query_flag,creator_role_cd,default_yn)
values (1,1,410,v_prog_cd,'PO',now(),'Sushant'::character varying,1,1,1,1,1,1,0);

END IF ;
END;
$$;

--------sushant
DO
$$
declare 
v_prog_cd integer;
BEGIN
IF NOT EXISTS (select 1 from epps_admin.epps_prog_mst
		where prog_id = 'loadAdminLoggedInUserDetails'
		  and prog_disp_name = 'Logged In User Details') then

INSERT INTO epps_admin.epps_prog_mst
(
  comp_cd ,
  prog_cd ,
  prog_id ,
  prog_disp_name ,
  prog_long_name ,
  prog_type ,
  parent_id ,
  module_id ,
  tran_indicator ,
  prog_mtqr_flag ,
  rep_type ,
  menu_pass_parameter ,
  prog_report_name ,
  prog_menu_display_yn ,
  prog_disp_seq_no ,
  prog_app_flag ,
  default_access_flag ,
  epps_admin_flag ,
  MWA_FLAG ,
  mob_disp_name ,
  mob_sub_menu_flag,
  mob_menu_pass_para ,
  screen_role_flag ,
  prog_doc_id ,
  prog_developed_by ,
  created_by ,
  created_dt ,
  updated_by ,
  updated_dt ,
  terminal_id ,
  active_yn ,
  rep_max_days ,
  report_description ,
  session_req_yn ,
  approval_flag ,
  creator_role_cd ,
  updator_role_cd ,
  prog_tree_del_flag ,
  MOB_ECS_FLAG )
  VALUES (
  1::SMALLINT, ---  comp_cd smallint NOT NULL,
  (SELECT (MAX(PROG_CD)::integer)+1 FROM epps_admin.epps_prog_mst)::INTEGER, ---prog_cd integer NOT NULL,
  'loadAdminLoggedInUserDetails'::character varying, ---prog_id character varying(40),
  'Logged In User Details'::character varying,--prog_disp_name character varying(100) NOT NULL,
  'Logged In User Details'::character varying,--prog_long_name character varying(2000),
  'R'::character varying,--prog_type character varying(1) NOT NULL DEFAULT 'F'::character varying,
  NULL::integer,--parent_id integer,
  'INAYU'::character varying, ---module_id character varying(5),
  Null::character varying,---tran_indicator character varying(20),
  'Q'::character varying,---prog_mtqr_flag character varying(1) DEFAULT 'T'::character varying, -- Set it (M) for Master Screen, (T) for Transaction, (Q) for Query Screen and (R) for Report .
  null::character varying, ---rep_type character(1) DEFAULT NULL::bpchar, -- Report Is of Type (N) For Normal Reports, (M) For MIS Reports
  NULL::character varying, ---menu_pass_parameter character varying(50), -- If PROG_MTQR_FLAG is (T), Then Pop-Up one window, from which user select data & continues Transacion entry(eg. Issue).
  NULL::character varying, ---prog_report_name character varying(50),
  'Y'::character varying, ---prog_menu_display_yn character varying(1) DEFAULT 'Y'::character varying, -- If this flag is set to(N), Respectinve entry will not Display In Menu
  (SELECT (MAX(PROG_DISP_SEQ_NO)::smallint)+1 FROM epps_admin.epps_prog_mst)::SMALLINT, ---prog_disp_seq_no smallint,
  'S'::character varying, ---prog_app_flag character(1) DEFAULT 'S'::bpchar, -- Indicates that The screen having Screen Based Approval (S) or Role Based Approval(R)
  'N'::character varying, ---default_access_flag character(1) DEFAULT 'N'::bpchar,
  'N'::character varying, ---epps_admin_flag character varying(1) DEFAULT 'N'::character varying, -- Special Screen have access to Only EPPS ADMIN Role, that are identified by this flag (Y)
  'A'::character varying, ---MWA_FLAG ,
  NULL::character varying, ---mob_disp_name character varying(50),
  NULL::character varying, ---mob_sub_menu_flag character varying(1),
  NULL::character varying, ---mob_menu_pass_para character varying(30),
  NULL::character varying, ---screen_role_flag character varying(25),
  NULL::character varying, ---prog_doc_id character varying(25),
  'P0'::character varying, ---prog_developed_by character varying(10),
  'P0'::character varying, ---created_by character varying(10),
  NOW(),--created_dt timestamp without time zone,
  NULL::character varying, ---updated_by character varying(10),
  NULL,---updated_dt timestamp without time zone,
  'Sushant'::character varying, ---terminal_id character varying(100),
  1::smallint, ---active_yn character varying(1) DEFAULT 'Y'::character varying,
  NULL::INTEGER, ---rep_max_days integer,
  NULL::character varying, ---report_description character(250),
  'Y'::character varying, ---session_req_yn character(1), -- Used to set Session Parameter Active for the  Screen or not
  'N'::character varying, ---approval_flag character varying DEFAULT 'N'::character varying, -- Used to indicate Transaction Approval cycle is applied or not
  1::smallint, ---creator_role_cd smallint,
  NULL::SMALLINT, ---updator_role_cd smallint,
  'N'::character varying,---prog_tree_del_flag character varying(15),
  'E'::character varying---MOB_ECS_FLAG character varying(15),
  );   
  
  select  prog_cd into v_prog_cd
from epps_admin.epps_prog_mst 
where prog_id = 'loadAdminLoggedInUserDetails';


insert into epps_admin.epps_role_prog_lnk
	(comp_cd,div_cd,role_cd,prog_cd,created_by,created_dt,terminal_id,active_yn,insert_flag,
    update_flag,delete_flag,query_flag,creator_role_cd,default_yn)
values (1,1,2,v_prog_cd,'PO',now(),'Sushant'::character varying,1,1,1,1,1,1,0);

insert into epps_admin.epps_role_prog_lnk
	(comp_cd,div_cd,role_cd,prog_cd,created_by,created_dt,terminal_id,active_yn,insert_flag,
    update_flag,delete_flag,query_flag,creator_role_cd,default_yn)
values (1,1,410,v_prog_cd,'PO',now(),'Sushant'::character varying,1,1,1,1,1,1,0);


END IF ;
END;
$$;

do
$$
begin

IF NOT EXISTS(SELECT 1 FROM epps_admin.epps_prog_mst
				  WHERE comp_cd =1
				  AND prog_disp_name ='Role to Screen Access Link'
				  and prog_id ='listSysAdminRoleProgramLink') THEN 
	
				insert into epps_admin.epps_prog_mst
				(comp_cd,prog_cd,prog_id,prog_disp_name,prog_long_name,
				prog_type,parent_id,module_id,--tran_indicator,
				prog_mtqr_flag,rep_type,menu_pass_parameter,--prog_report_name,
				prog_menu_display_yn,prog_disp_seq_no,prog_app_flag,default_access_flag,epps_admin_flag,
				mwa_flag,prog_developed_by,created_by,created_dt,terminal_id,active_yn,--rep_max_days,
				session_req_yn,approval_flag,creator_role_cd,prog_tree_del_flag,allow_excel_down_yn,noti_config_yn,
				java_notification_cls_name,rep_disp_name,multi_screen_yn,db_ct_type,dt_param_type,view_disp_name,
				view_entity_name,chart_type,report_key) --select * from epps_admin.epps_prog_mst
				values
				(
				1,
				(select max(prog_cd)+ 1 from epps_admin.epps_prog_mst),
				'listSysAdminRoleProgramLink','Role to Screen Access Link','Role to Screen Access Link',
				'F',null,'ADMIN',--null,
				'M','N',null,--null,
				'Y',15,'S','N','N','W','P0000','P0000',now(),'UMA_BE',1,--null,
				'Y','N',1,null,'N','N',null,null,null,null,null,null,null,null,null);
				
				insert into epps_admin.epps_role_prog_lnk(comp_cd,div_cd,role_cd,prog_cd,created_by,created_dt,terminal_id,active_yn,insert_flag,update_flag,delete_flag,query_flag,creator_role_cd,default_yn)
				select comp_cd,div_cd,role_cd,(select prog_cd from epps_admin.epps_prog_mst where prog_id ='listSysAdminRoleProgramLink'),'P0000',now(),'uma',1,1,1,1,1,1,0
				from epps_admin.epps_role_mst
				where role_cd = 2;
			end if;
			
end;
$$;
