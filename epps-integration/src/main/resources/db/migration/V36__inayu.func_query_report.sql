-- FUNCTION: inayu.func_query_report(json)

-- DROP FUNCTION inayu.func_query_report(json);

CREATE OR REPLACE FUNCTION inayu.func_query_report(
	vin_ip_param json,
	OUT vin_out_param json,
	OUT vin_out_table_struc json,
	OUT vin_out_table_data json)
    RETURNS record
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
v_query character varying ;
vin_ip_query character varying ;
APP_STATUS character varying ;
INV_STAGE character varying;
vin_prog_id character varying;
vin_prog_id1 character varying;
vin_from_age smallint;
vin_to_age smallint;
vin_exam_from_dt date;
vin_exam_to_dt date;
vin_inv_from_dt date;
vin_inv_to_dt date;
vin_app_status character varying;
vin_app_stage character varying;
vin_ama_pno character varying;
vin_emp_gender character varying;
vin_user_type character varying;
vin_mi_unit_code character varying;
vin_unit_code character varying;
vin_rank_code character varying;
vin_cmnd_code character varying;
v_prog_disp_name character varying;
vin_pno character varying;
vin_role_code character varying;
vin_session_id integer;
vin_medical character varying;
vin_comp_cd smallint;
vin_div_cd smallint;
vin_from_date date;
vin_to_date date;
vin_investigation_block character varying;

/*
select * from inayu.func_query_report('{"vin_app_status":10,"vin_cmnd_code":"IHQ","vin_unit_code":"WESEE NEW DELHI",
												"vin_mi_unit_code":"WESEE NEW DELHI","vin_emp_gender":"M","vin_ama_pno":"100001",
												"vin_inv_from_dt":"2021-10-01","vin_inv_to_dt":"2021-11-30",
												"vin_from_age":56,"vin_to_age":64,"vin_prog_id":"listPerToRoleLnk"}');
												

select * from inayu.func_query_report('{"vin_app_status":10,"vin_from_age":56,"vin_to_age":64,
"vin_prog_id":"loadAppointmentStatusReport","vin_session_id":125}');

				
select * from inayu.func_query_report('{"vin_comp_cd":1,"vin_div_cd":1,"vin_from_date":"2021-12-03",
"vin_to_date":"2021-12-03","vin_prog_id":"loadAdminLoggedInUserDetails","vin_session_id":125}');	
	
		/*(select (row_to_json(a))
		from
		(select prog_id, prog_disp_name"chartDisplayName",prog_long_name"gridReportDisplayame"
		 from epps_admin.epps_prog_mst
		where prog_id = '|| vin_prog_id ||')a)"reportDetails",

		(select json_agg(row_to_json(b))
		from
		(select distinct "Cadre Code" "key","Cadre Name" "value"
		from appointments_date_wise
		 order by 1)b)"chartPanelXaxis",

		(select json_agg(row_to_json(c))
		from
		(select "Cadre Code" "key","Cadre Name" "name",count(distinct("Personal Count"))"value"
		from appointments_date_wise
		group by "Cadre Code","Cadre Name"
		 order by 1)c)"chartPanelYaxis",*/
*/
begin
		if CAST(VIN_IP_PARAM::JSON->>'vin_prog_id' AS character varying) is not null then
				vin_prog_id :=  cast(vin_ip_param::json->>'vin_prog_id' as character varying);
				vin_prog_id1 := vin_prog_id;
				vin_prog_id = quote_literal(vin_prog_id);
		end if;
		
		vin_session_id := cast(vin_ip_param::json ->> 'vin_session_id' as character varying);
	
		select prog_disp_name into v_prog_disp_name
		from epps_admin.epps_prog_mst where prog_id = vin_prog_id1;
		

if v_prog_disp_name = 'Appointment Status Report' then

		APP_STATUS := quote_literal('APP_STATUS');
		INV_STAGE := quote_literal('INV_STAGE');
		
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_app_status' AS character varying) is not null then
				vin_app_status :=  replace(replace((replace (cast(vin_ip_param::json->>'vin_app_status' as character varying),'[','')),']',''),'"','');
		end if;
		
		
		
		v_query := 'select ad.pno , PGP_SYM_DECRYPT(coalesce(ap.full_nm,pm.full_name)::bytea, current_setting(''encrypt.key'')) as "Full Name",
					(select name from inmdb.rank_mst r where r.code = PGP_SYM_DECRYPT(coalesce(ap.rank_cd,pd.rank_code)::bytea, current_setting(''encrypt.key'')) ) as "Rank Name",
					(select name from inmdb.unit_mst u where u.code =coalesce(ap.unit_cd,pd.unit_code)) as "Unit Name",
					(select name from inmdb.command_mst m where m.code = coalesce(ap.cmnd_cd,pd.cmnd_code)) as "Command Name",
					ag.stage_desc as "Appointment Status",
					ast.stage_desc as "Investigation Status",
					PGP_SYM_DECRYPT(pa.full_name::bytea, current_setting(''encrypt.key'')) as "Ama",
					ac.mi_unit_name as "Mi Room",
					ac.mi_date as "Invastigation Date",
					ac.mi_exam_date as "Examination Date",
					ac.nh_unit_name as "Naval Hospital"
					from inayu.appointment_dtl ad
					inner join inmdb.per_mst pm on ad.pno = pm.pno
					inner join inmdb.per_dtls pd on ad.pno = pd.pno
					inner join inmdb.per_mst pa on ad.ama_pno = pa.pno 
					inner join epps_admin.epps_stage_mst ag  on ag.stage_cd = ad.app_status and ti_code = ' || APP_STATUS || '
					inner join epps_admin.epps_stage_mst ast  on ast.stage_cd = ad.app_stage and ast.ti_code = ' || INV_STAGE || '
					left join inayu.app_per_dtl ap on ad.sr_no = ap.app_sr_no
					left join inayu.app_confirm_dtl ac on ad.sr_no = ac.app_sr_no
					where ad.app_status in ( ' || vin_app_status || ')' ; 
					
				if CAST(VIN_IP_PARAM::JSON->>'vin_cmnd_code' AS character varying) is not null then
				vin_cmnd_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_cmnd_code' as character varying),'[','')),']',''),'"','''');
				
				v_query := (v_query||' and coalesce(ap.cmnd_cd,pd.cmnd_code) in (' || vin_cmnd_code ||')' );
				end if;	
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_unit_code' AS character varying) is not null then
				vin_unit_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_unit_code' as character varying),'[','')),']',''),'"','''');
				
				v_query := (v_query||' and coalesce(ap.unit_cd,pd.unit_code) in ( ' || vin_unit_code || ')');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_mi_unit_code' AS character varying) is not null then
				vin_mi_unit_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_mi_unit_code' as character varying),'[','')),']',''),'"','''');
			
				
				v_query := (v_query||' and ac.mi_unit_code in (' || vin_mi_unit_code || ')');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_user_type' AS character varying) is not null then
				vin_user_type := replace(replace((replace (cast(vin_ip_param::json->>'vin_user_type' as character varying),'[','')),']',''),'"','''');
				
				v_query := (v_query||' and pm.user_type_code in ( ' || vin_user_type || ')');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_emp_gender' AS character varying) is not null then
				vin_emp_gender := replace(replace((replace (cast(vin_ip_param::json->>'vin_emp_gender' as character varying),'[','')),']',''),'"','''');
				
				v_query := (v_query||' and pm.emp_gender in (' || vin_emp_gender || ')');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_ama_pno' AS character varying) is not null then
				vin_ama_pno := replace(replace((replace (cast(vin_ip_param::json->>'vin_ama_pno' as character varying),'[','')),']',''),'"','''');
				
				v_query := (v_query||' and ad.ama_pno in (' || vin_ama_pno || ')');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_rank_code' AS character varying) is not null then
				vin_rank_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_ama_pno' as character varying),'[','')),']',''),'"','''');
				
				v_query := (v_query||' and PGP_SYM_DECRYPT(coalesce(ap.rank_cd,pd.rank_code)::bytea, current_setting(''encrypt.key'')) in (' || vin_rank_code || ')');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_app_stage' AS character varying) is not null then
				vin_app_stage := replace(replace((replace (cast(vin_ip_param::json->>'vin_app_stage' as character varying),'[','')),']',''),'"','''');
				
				v_query := (v_query||' and ad.app_stage in ( ' || vin_app_stage || ')');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_inv_from_dt' AS date) is not null then
				vin_inv_from_dt := cast(vin_ip_param::json->>'vin_inv_from_dt' as date);
				
				v_query := (v_query||' and ac.mi_date >= coalesce( ''' || vin_inv_from_dt || ''',ac.mi_date)');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_inv_to_dt' AS date) is not null then
				vin_inv_to_dt := cast(vin_ip_param::json->>'vin_inv_to_dt' as date);
				
				v_query := (v_query||' and ac.mi_date <= coalesce( ''' || vin_inv_to_dt || ''',ac.mi_date)');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_exam_from_dt' AS date) is not null then
				vin_exam_from_dt := cast(vin_ip_param::json->>'vin_exam_from_dt' as date);
				
				v_query := (v_query||' and ac.mi_exam_date >= coalesce( ''' || vin_exam_from_dt || ''',ac.mi_exam_date) ');
				end if;

				if CAST(VIN_IP_PARAM::JSON->>'vin_exam_to_dt' AS date) is not null then
				vin_exam_to_dt := cast(vin_ip_param::json->>'vin_exam_to_dt' as date);
				
				v_query := (v_query||' and ac.mi_exam_date <= coalesce( ''' || vin_exam_to_dt || ''',ac.mi_exam_date)');
				end if;
				
				if CAST(VIN_IP_PARAM::JSON->>'vin_from_age' AS smallint) is not null then
				vin_from_age :=  cast(vin_ip_param::json->>'vin_from_age' as smallint);
				
				v_query := (v_query||' and extract(year from age(now(),pm.dob))::smallint >= ' || vin_from_age  );
				end if;

				if CAST(VIN_IP_PARAM::JSON->>'vin_to_age' AS smallint) is not null then
				vin_to_age :=  cast(vin_ip_param::json->>'vin_to_age' as smallint);
				
				v_query := (v_query||' and extract(year from age(now(),pm.dob))::smallint <= ' || vin_to_age  );
				end if;

				vin_ip_query := (v_query);
				raise notice 'v_query %',v_query;
				execute ' drop table if exists appointments_date_wise_'||vin_session_id ;
				execute ' create temp table if not exists appointments_date_wise_'|| vin_session_id ||' as '|| vin_ip_query ;	

		execute	'select json_agg(z) 
				from
				(select
				(select (row_to_json(a))
				from
				(select prog_disp_name"chartDisplayName",prog_long_name"gridReportDisplayame"
				 from epps_admin.epps_prog_mst
				 where prog_id = '|| vin_prog_id ||')a)"reportDetails",
				
				(select json_agg(row_to_json(b))
				from
				(select distinct 1 from appointments_date_wise_'||vin_session_id ||')b)"chartPanelXaxis",
				
				(select json_agg(row_to_json(c))
				from
				(select distinct 1 from appointments_date_wise_'||vin_session_id ||')c)"chartPanelYaxis"
				)z;' into vin_out_param;
				
				execute 'select (json_agg(row_to_json(d)))
				 from
				 ('||vin_ip_query||')d  ;'  into vin_out_table_data;

			execute	'select (json_agg(row_to_json(e)))
				from
				(select table_name,column_name,data_type,character_maximum_length,ordinal_position
				from INFORMATION_SCHEMA.COLUMNS
				where TABLE_NAME = ''appointments_date_wise_'||vin_session_id ||''' 
				 order by ordinal_position)e ;' into vin_out_table_struc;
end if;
------------------------------------------------------------------------------------------------------------
if v_prog_disp_name = 'Logged In User Details' then

	if CAST(VIN_IP_PARAM::JSON->>'vin_comp_cd' AS SMALLINT) is not null then
		vin_comp_cd := cast(vin_ip_param::json->>'vin_comp_cd' as SMALLINT);
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_div_cd' AS SMALLINT) is not null then
			vin_div_cd := cast(vin_ip_param::json->>'vin_div_cd' as SMALLINT);
	end if;
	
	v_query :=	'select	u.role_cd as "Role Code",r.role_disp_name as "Role Name",u.emp_cd as "Personnel no",
						concat_ws('' '',e.emp_title,e.emp_first_name,e.emp_middle_name,e.emp_last_name) as "Name",
						u.mail_id as "Mail Id",u.terminal_id as "Terminal Id",date_trunc(''second'', u.login_time) as "Login Time",
						date_trunc(''second'', u.logout_time) as "Logout Time",
						k.emp_cd as "Session End By",
						concat_ws('' '', k.emp_title,k.emp_first_name,k.emp_middle_name,k.emp_last_name) as "Session End By Name"
				from epps_admin.epps_user_login_dtl u
				inner join epps_admin.epps_role_mst r on u.comp_cd	= r.comp_cd
				and u.div_cd	= r.div_cd  and u.role_cd	= r.role_cd
				inner join epps_admin.epps_hr_emp_mst e on u.comp_cd	= e.comp_cd
				and u.div_cd	= e.div_cd and u.emp_cd	= e.emp_cd
				left join epps_admin.epps_hr_emp_mst k on u.comp_cd	= k.comp_cd
				and u.div_cd	= k.div_cd and u.killed_by_mail_id = k.emp_mail_id
				where 1=1 ' ;
				
		if CAST(VIN_IP_PARAM::JSON->>'vin_from_dt' AS date) is not null then
		vin_from_date := cast(vin_ip_param::json->>'vin_from_dt' as date);

		v_query := (v_query||' and to_char(u.login_time,''YYYY-MM-DD'') >= coalesce( ''' || vin_from_dt || ''',to_char(u.login_time,''YYYY-MM-DD'')) ');
		end if;

		if CAST(VIN_IP_PARAM::JSON->>'vin_to_dt' AS date) is not null then
		vin_exam_to_dt := cast(vin_ip_param::json->>'vin_to_dt' as date);

		v_query := (v_query||' and to_char(u.logout_time,''YYYY-MM-DD'') <= coalesce( ''' || vin_exam_to_dt || ''',to_char(u.logout_time,''YYYY-MM-DD''))');
		end if;		
				
	 	vin_ip_query := (v_query);
		raise notice 'v_query %',v_query;
		execute ' drop table if exists user_login_dtl_'||vin_session_id ;
		execute ' create temp table if not exists user_login_dtl_'|| vin_session_id ||' as '|| vin_ip_query ;	
			
		execute	'select json_agg(z) 
				from
				(select
				(select (row_to_json(a))
				from
				(select prog_disp_name"chartDisplayName",prog_long_name"gridReportDisplayame"
				 from epps_admin.epps_prog_mst
				 where prog_id = '|| vin_prog_id ||')a)"reportDetails",
				
				(select json_agg(row_to_json(b))
				from
				(select distinct 1 from user_login_dtl_'||vin_session_id ||')b)"chartPanelXaxis",
				
				(select json_agg(row_to_json(c))
				from
				(select distinct 1 from user_login_dtl_'||vin_session_id ||')c)"chartPanelYaxis"
				)z;' into vin_out_param;
				
				execute 'select (json_agg(row_to_json(d)))
				 from
				 ('||vin_ip_query||')d  ;'  into vin_out_table_data;

			execute	'select (json_agg(row_to_json(e)))
				from
				(select table_name,column_name,data_type,character_maximum_length,ordinal_position
				from INFORMATION_SCHEMA.COLUMNS
				where TABLE_NAME = ''user_login_dtl_'||vin_session_id ||''' 
				 order by ordinal_position)e ;' into vin_out_table_struc;
				 
end if;
------------------------------------------------------------------------------------------------------------
if v_prog_disp_name = 'Audit Trail Report' then

vin_investigation_block :=  replace(replace((replace (cast(vin_ip_param::json->>'vin_investigation_block' as character varying),'[','')),']',''),'"','''');

	vin_investigation_block := quote_literal(vin_investigation_block);

	if CAST(VIN_IP_PARAM::JSON->>'vin_app_stage' AS character varying) is not null then
			vin_app_stage :=  replace(replace((replace (cast(vin_ip_param::json->>'vin_app_stage' as character varying),'[','')),']',''),'"','');
	end if;
	
		
		
	v_query := 'select p.unit_name as "Unit Name",a.pno as "Personnel No",p.rank_name as "Rank Name",
				PGP_SYM_DECRYPT(p.full_name::bytea, current_setting(''encrypt.key''))as "Name",a.app_no as "Appoinment No",a.app_dt as "Request Date",
				a.ama_pno as "AMA",d.mi_unit_name as "MI Room",d.mi_date as "Investigation Date",e.log_dt  as "Actual Investigation Date",
				e.log_dt  as "Actual Examination Date",f.stage_desc as "Investigation Stage",
				s.log_dt  as "Examination Submit Date",PGP_SYM_DECRYPT(sc.full_name::bytea, current_setting(''encrypt.key'')) as "Examination Submit By",
				ap.log_dt  as "Examination Approved Date",PGP_SYM_DECRYPT(ac.full_name::bytea, current_setting(''encrypt.key'')) as "Examination Approved By", '
				|| vin_investigation_block || ' as "Investigation Block",g.log_by_role_cd as "Medical Specialist Role"
				from inayu.appointment_dtl a
				inner join inayu.per_mst_vw p on a.pno = p.pno
				left join inayu.app_confirm_dtl d on a.sr_no = d.app_sr_no
				left join (select ref_sr_no,log_by,log_dt from inayu.appointment_dtl_alog where audit_flag =  ''Start Investigation'' ) e on a.sr_no = e.ref_sr_no
				left join (select ref_sr_no,log_by,log_dt from inayu.appointment_dtl_alog where audit_flag =  ''Submit'' ) s on a.sr_no = s.ref_sr_no
				left join (select ref_sr_no,log_by,log_dt from inayu.appointment_dtl_alog where audit_flag =  ''Peruse'' ) ap on a.sr_no = ap.ref_sr_no
				left join inmdb.per_mst sc on sc.pno = s.log_by
				left join inmdb.per_mst ac on ac.pno = ap.log_by
				inner join epps_admin.epps_stage_mst f  on a.app_stage = f.stage_cd and f.ti_code = ''INV_STAGE''' ;
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_investigation_block' AS character varying) = 'EYE' then
		v_query := concat(v_query,' left join (SELECT log_by_role_cd,log_by,log_dt,ref_sr_no from inayu.inv_eye_dtl_alog  WHERE inv_stage = 30) g on a.sr_no = g.ref_sr_no');
		end if;
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_investigation_block' AS character varying) = 'DENTAL' then
		v_query := concat(v_query,'left join (SELECT log_by_role_cd,log_by,log_dt,ref_sr_no from inayu.inv_dental_dtl_alog  WHERE inv_stage = 30) g on a.sr_no = g.ref_sr_no');
		end if;
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_investigation_block' AS character varying) = 'INVESTIGATION' then
		v_query := concat(v_query,' left join (SELECT log_by_role_cd,log_by,log_dt,ref_sr_no from inayu.inv_inves_dtl_alog  WHERE inv_stage = 30) g on a.sr_no = g.ref_sr_no');
		end if;
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_investigation_block' AS character varying) = 'GYNAECOLOGY' then
		v_query := concat(v_query,' left join (SELECT log_by_role_cd,log_by,log_dt,ref_sr_no from inayu.inv_gyn_dtl_alog  WHERE inv_stage = 30) g on a.sr_no = g.ref_sr_no');
		end if;
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_investigation_block' AS character varying) = 'ENT' then
		v_query := concat(v_query,' left join (SELECT log_by_role_cd,log_by,log_dt,ref_sr_no from inayu.inv_ent_dtl_alog  WHERE inv_stage = 30) g on a.sr_no = g.ref_sr_no');
		end if;
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_investigation_block' AS character varying) = 'SURGERY' then
		v_query := concat(v_query,' left join (SELECT log_by_role_cd,log_by,log_dt,ref_sr_no from inayu.inv_sergery_dtl_alog  WHERE inv_stage = 30) g on a.sr_no = g.ref_sr_no');
		end if;
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_investigation_block' AS character varying) = 'GENERAL' then
		v_query := concat(v_query,'left join (SELECT log_by_role_cd,log_by,log_dt,ref_sr_no from inayu.inv_phy_capacity_dtl_alog  WHERE inv_stage = 30) g on a.sr_no = g.ref_sr_no');
		end if;
		
		
		v_query := v_query||' where a.app_stage::text in('||replace(replace((replace (cast(vin_ip_param::json->>'vin_app_stage' as character varying),'[','')),']',''),'"','''')||') ';
		
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_pno' AS character varying) is not null then
		vin_pno := replace(replace((replace (cast(vin_ip_param::json->>'vin_pno' as character varying),'[','')),']',''),'"','''');
		
		v_query := (v_query||' and p.pno in ( ' || vin_pno || ')');
		end if;			
				
		if CAST(VIN_IP_PARAM::JSON->>'vin_user_type' AS character varying) is not null then
		vin_user_type := replace(replace((replace (cast(vin_ip_param::json->>'vin_user_type' as character varying),'[','')),']',''),'"','''');

		v_query := (v_query||' and p.user_type_code in ( ' || vin_user_type || ')');
		end if;	
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_cmnd_code' AS character varying) is not null then
		vin_cmnd_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_cmnd_code' as character varying),'[','')),']',''),'"','''');

		v_query := (v_query||' and p.cmnd_code in (' || vin_cmnd_code ||')' );
		end if;	

		if CAST(VIN_IP_PARAM::JSON->>'vin_unit_code' AS character varying) is not null then
		vin_unit_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_unit_code' as character varying),'[','')),']',''),'"','''');

		v_query := (v_query||' and p.unit_code in ( ' || vin_unit_code || ')');
		end if;
		
		if CAST(VIN_IP_PARAM::JSON->>'vin_rank_code' AS character varying) is not null then
		vin_rank_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_rank_code' as character varying),'[','')),']',''),'"','''');

		v_query := (v_query||' and PGP_SYM_DECRYPT(p.rank_cd::bytea, current_setting(''encrypt.key'')) in (' || vin_rank_code || ')');
		end if;
		
		--v_query := concat(v_query,' where a.app_stage in ( ' || vin_app_stage || ')');			
		vin_ip_query := (v_query);
		raise notice 'v_query %',v_query;
		execute ' drop table if exists audittrail_report_'||vin_session_id ;
		execute ' create temp table if not exists audittrail_report_'|| vin_session_id ||' as '|| vin_ip_query ;	
			
		execute	'select json_agg(z) 
				from
				(select
				(select (row_to_json(a))
				from
				(select prog_disp_name"chartDisplayName",prog_long_name"gridReportDisplayame"
				 from epps_admin.epps_prog_mst
				 where prog_id = '|| vin_prog_id ||')a)"reportDetails",
				
				(select json_agg(row_to_json(b))
				from
				(select distinct 1 from audittrail_report_'||vin_session_id ||')b)"chartPanelXaxis",
				
				(select json_agg(row_to_json(c))
				from
				(select distinct 1 from audittrail_report_'||vin_session_id ||')c)"chartPanelYaxis"
				)z;' into vin_out_param;
				
				execute 'select (json_agg(row_to_json(d)))
				 from
				 ('||vin_ip_query||')d  ;'  into vin_out_table_data;

				execute	'select (json_agg(row_to_json(e)))
				from
				(select table_name,column_name,data_type,character_maximum_length,ordinal_position
				from INFORMATION_SCHEMA.COLUMNS
				where TABLE_NAME = ''audittrail_report_'||vin_session_id ||''' 
				 order by ordinal_position)e ;' into vin_out_table_struc;			

end if;

if v_prog_disp_name = 'Medical Examination Overdue Report' then

	v_query := 'create temp table per_dtl as
					select pm.pno,
					 PGP_SYM_DECRYPT(pm.rank_code::bytea, current_setting(''encrypt.key'')) rank_code
					 from inayu.per_mst_vw pm 
					 where 1=1 ';
	if CAST(VIN_IP_PARAM::JSON->>'vin_cmnd_code' AS character varying) is not null then
	vin_cmnd_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_cmnd_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.cmnd_code in (' || vin_cmnd_code ||')' );
	end if;	
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_unit_code' AS character varying) is not null then
	vin_unit_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_unit_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.unit_code in ( ' || vin_unit_code || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_user_type' AS character varying) is not null then
	vin_user_type := replace(replace((replace (cast(vin_ip_param::json->>'vin_user_type' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.user_type_code in ( ' || vin_user_type || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_emp_gender' AS character varying) is not null then
	vin_emp_gender := replace(replace((replace (cast(vin_ip_param::json->>'vin_emp_gender' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.emp_gender in (' || vin_emp_gender || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_rank_code' AS character varying) is not null then
	vin_rank_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_rank_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and PGP_SYM_DECRYPT(pm.rank_code::bytea, current_setting(''encrypt.key'')) in (' || vin_rank_code || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_from_age' AS smallint) is not null then
	vin_from_age :=  cast(vin_ip_param::json->>'vin_from_age' as smallint);
	
	v_query := (v_query||' and extract(year from age(now(),pm.dob))::smallint >= ' || vin_from_age  );
	end if;

	if CAST(VIN_IP_PARAM::JSON->>'vin_to_age' AS smallint) is not null then
	vin_to_age :=  cast(vin_ip_param::json->>'vin_to_age' as smallint);
	
	v_query := (v_query||' and extract(year from age(now(),pm.dob))::smallint <= ' || vin_to_age  );
	end if;	
	
	execute v_query;
	v_query:= null;
	
	v_query := 'select pm.pno "Personnel No",
					PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting(''encrypt.key'')) "Name",
					pm.rank_name  "Rank Name",
					pm.unit_name  "Unit Name",
					(case when now()::date > due_dt and app_status is null then ''Y''
					when now()::date > due_dt and app_status is not null then ''N''
					when now()::date <= due_dt then ''N'' end) "Over Due(Y/N)",
					due_dt "Due Date",
					due_dt + interval ''4 months'' - interval''1 day'' "Apply Before Date",
					(case when now()::date > due_dt and app_status is null then now()::date - due_dt end) "Due Since(age)",
					(case when app_status is null then ''Not Requested''
					 when app_status = 10 then ''Requested''
					 when app_status = 20 then ''Confirmed'' end) "Appointment Status",
					(select PGP_SYM_DECRYPT(an.full_name::bytea, current_setting(''encrypt.key'')) from inmdb.per_mst an where an.pno = ua.ama_pno)  "Linked AMA",
					 (select u.name from inmdb.unit_mst u where u.code =apd.unit_code) "Linked MI Room"

					from (select pd1.pno,
							ad1.app_status,
							to_date(to_char(now(),''yyyy'')||lpad(rm1.to_month::text,2,''0'')||''01'',''yyyymmdd'')+interval ''1 month'' - interval ''1 day'' due_dt
							from per_dtl pd1
							inner join inayu.rank_schedule_mst rm1 on pd1.rank_code = rm1.rank_code
							left join inayu.appointment_dtl ad1 on pd1.pno=ad1.pno and ad1.app_year = to_char(now(),''yyyy'')::smallint
							where (to_date(to_char(now(),''yyyy'')||lpad(rm1.to_month::text,2,''0'')||''01'',''yyyymmdd'')+interval''1 month'' - interval''1 day'' ) <= (date_trunc(''month'' ,now())::date+interval''3 months''-interval''1 day'')
							and coalesce(ad1.app_stage,10) =10

							union all

							select pd2.pno,
							ad2.app_status,
							to_date(to_char(now()+interval''1 year'',''yyyy'')||lpad(rm2.to_month::text,2,''0'')||''01'',''yyyymmdd'')+interval ''1 month'' - interval ''1 day'' due_dt
							from per_dtl pd2
							inner join inayu.rank_schedule_mst rm2 on pd2.rank_code = rm2.rank_code
							left join inayu.appointment_dtl ad2 on pd2.pno=ad2.pno and ad2.app_year = to_char(now()+interval''1 year'',''yyyy'')::smallint
							where (to_date(to_char(now()+interval''1 year'',''yyyy'')||lpad(rm2.to_month::text,2,''0'')||''01'',''yyyymmdd'')+interval''1 month'' - interval''1 day'' ) <= (date_trunc(''month'' ,now())::date+interval''3 months''-interval''1 day'')
							and coalesce(ad2.app_stage,10) =10

							union all

							select pd3.pno,
							ad3.app_status,
							to_date(to_char(now()-interval''1 year'',''yyyy'')||lpad(rm3.to_month::text,2,''0'')||''01'',''yyyymmdd'')+interval ''1 month'' - interval ''1 day'' due_dt
							from per_dtl pd3
							inner join inayu.rank_schedule_mst rm3 on pd3.rank_code = rm3.rank_code
							left join inayu.appointment_dtl ad3 on pd3.pno=ad3.pno and ad3.app_year = to_char(now()-interval''1 year'',''yyyy'')::smallint
							where (to_date(to_char(now()-interval''1 year'',''yyyy'')||lpad(rm3.to_month::text,2,''0'')||''01'',''yyyymmdd'')+interval''1 month'' - interval''1 day'' ) <= (date_trunc(''month'' ,now())::date+interval''3 months''-interval''1 day'')
							and coalesce(ad3.app_stage,10) =10
					)q
					inner join inayu.per_mst_vw pm on pm.pno  = q.pno
					inner join inayu.unit_mi_ama_lnk ua on pm.unit_code = ua.unit_code
					inner join inmdb.per_dtls apd on ua.ama_pno  = apd.pno
					where 1=1 ';
					
	if CAST(VIN_IP_PARAM::JSON->>'vin_cmnd_code' AS character varying) is not null then
	vin_cmnd_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_cmnd_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.cmnd_code in (' || vin_cmnd_code ||')' );
	end if;	
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_unit_code' AS character varying) is not null then
	vin_unit_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_unit_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.unit_code in ( ' || vin_unit_code || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_mi_unit_code' AS character varying) is not null then
	vin_mi_unit_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_mi_unit_code' as character varying),'[','')),']',''),'"','''');

	
	v_query := (v_query||' and ua.mi_unit_code in (' || vin_mi_unit_code || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_user_type' AS character varying) is not null then
	vin_user_type := replace(replace((replace (cast(vin_ip_param::json->>'vin_user_type' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.user_type_code in ( ' || vin_user_type || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_emp_gender' AS character varying) is not null then
	vin_emp_gender := replace(replace((replace (cast(vin_ip_param::json->>'vin_emp_gender' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.emp_gender in (' || vin_emp_gender || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_ama_pno' AS character varying) is not null then
	vin_ama_pno := replace(replace((replace (cast(vin_ip_param::json->>'vin_ama_pno' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and ua.ama_pno in (' || vin_ama_pno || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_rank_code' AS character varying) is not null then
	vin_rank_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_rank_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and PGP_SYM_DECRYPT(pm.rank_code::bytea, current_setting(''encrypt.key'')) in (' || vin_rank_code || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_app_status' AS character varying) is not null then
	vin_app_status := replace(replace((replace (cast(vin_ip_param::json->>'vin_app_status' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and q.app_status::text in (' || vin_app_status || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_from_age' AS smallint) is not null then
	vin_from_age :=  cast(vin_ip_param::json->>'vin_from_age' as smallint);
	
	v_query := (v_query||' and extract(year from age(now(),pm.dob))::smallint >= ' || vin_from_age  );
	end if;

	if CAST(VIN_IP_PARAM::JSON->>'vin_to_age' AS smallint) is not null then
	vin_to_age :=  cast(vin_ip_param::json->>'vin_to_age' as smallint);
	
	v_query := (v_query||' and extract(year from age(now(),pm.dob))::smallint <= ' || vin_to_age  );
	end if;	

	vin_ip_query := (v_query);
	raise notice 'v_query %',v_query;
	execute ' drop table if exists mediexam_overdue_report_'||vin_session_id ;
	execute ' create temp table if not exists mediexam_overdue_report_'|| vin_session_id ||' as '|| vin_ip_query ;	
	
	execute	'select json_agg(z) 
				from
				(select
				(select (row_to_json(a))
				from
				(select prog_disp_name"chartDisplayName",prog_long_name"gridReportDisplayame"
				 from epps_admin.epps_prog_mst
				 where prog_id = '|| vin_prog_id ||')a)"reportDetails",
				
				(select json_agg(row_to_json(b))
				from
				(select distinct 1 from mediexam_overdue_report_'||vin_session_id ||')b)"chartPanelXaxis",
				
				(select json_agg(row_to_json(c))
				from
				(select distinct 1 from mediexam_overdue_report_'||vin_session_id ||')c)"chartPanelYaxis"
				)z;' into vin_out_param;
				
				execute 'select (json_agg(row_to_json(d)))
				 from
				 ('||vin_ip_query||')d  ;'  into vin_out_table_data;

				execute	'select (json_agg(row_to_json(e)))
				from
				(select table_name,column_name,data_type,character_maximum_length,ordinal_position
				from INFORMATION_SCHEMA.COLUMNS
				where TABLE_NAME = ''mediexam_overdue_report_'||vin_session_id ||''' 
				order by ordinal_position)e ;' into vin_out_table_struc;	
end if;

if v_prog_disp_name = 'Examination Status Report' then
	v_query := 'select pm.pno "Personnel No",					
					PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting(''encrypt.key'')) "Name",
					pm.rank_name  "Rank Name",
					pm.unit_name  "Unit Name",
					ad.app_no "Appointment No",
					asm.stage_desc "Apointment Stage",
					PGP_SYM_DECRYPT(apm.full_name::bytea, current_setting(''encrypt.key'')) "AMA",
					ism.stage_desc "Investigation Stage",
					(case when ad.app_stage = 30 then PGP_SYM_DECRYPT(apm.full_name::bytea, current_setting(''encrypt.key'')) else null end) "Examination Conducted By",
					(select PGP_SYM_DECRYPT(pa.full_name::bytea, current_setting(''encrypt.key'')) from inmdb.per_mst pa where pa.pno = ada.log_by) "Approving Authority",
					ada.log_dt "Approved Date",
					(select PGP_SYM_DECRYPT(pa.full_name::bytea, current_setting(''encrypt.key'')) from inmdb.per_mst pa where pa.pno = adp.log_by) "Perusing Authority",
					adp.log_dt "Perused Date",
					coalesce(r.role_disp_name,''AMA'') "Next Updator"
				from (select sr_no, pno, app_no,app_stage,app_status,next_modifier_role,ama_pno  from inayu.appointment_dtl 
						where 1=1';
	if CAST(VIN_IP_PARAM::JSON->>'vin_req_from_dt' AS date) is not null then
		v_query := (v_query||' and app_dt >= ''' || cast(vin_ip_param::json->>'vin_req_from_dt' as date) || '''::date');
	end if;					
	if CAST(VIN_IP_PARAM::JSON->>'vin_req_to_dt' AS date) is not null then
		v_query := (v_query||' and app_dt <= ''' || cast(vin_ip_param::json->>'vin_req_to_dt' as date)|| '''::date');
	end if;	
	if CAST(VIN_IP_PARAM::JSON->>'vin_ama_pno' AS character varying) is not null then
		vin_ama_pno := replace(replace((replace (cast(vin_ip_param::json->>'vin_ama_pno' as character varying),'[','')),']',''),'"','''');

		v_query := (v_query||' and ama_pno in (' || vin_ama_pno || ')');
	end if;
	v_query:= v_query||')ad
				inner join inayu.per_mst_vw pm on ad.pno = pm.pno
				inner join epps_admin.epps_stage_mst asm on ad.app_status = asm.stage_cd and asm.ti_code = ''APP_STATUS''
				inner join epps_admin.epps_stage_mst ism on ad.app_stage = ism.stage_cd and ism.ti_code = ''INV_STAGE''
				inner join inmdb.per_mst apm on ad.ama_pno =    apm.pno
				left join inayu.appointment_dtl_alog adp on adp.ref_sr_no = ad.sr_no and adp.audit_flag = ''Peruse''
				left join inayu.appointment_dtl_alog ada on ada.ref_sr_no = ad.sr_no and ada.audit_flag = ''Approve''
				left join epps_admin.epps_role_mst r on r.role_cd = ad.next_modifier_role
				where 1=1';
	if CAST(VIN_IP_PARAM::JSON->>'vin_cmnd_code' AS character varying) is not null then
	vin_cmnd_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_cmnd_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.cmnd_code in (' || vin_cmnd_code ||')' );
	end if;	
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_unit_code' AS character varying) is not null then
	vin_unit_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_unit_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.unit_code in ( ' || vin_unit_code || ')');
	end if;			
	if CAST(VIN_IP_PARAM::JSON->>'vin_from_age' AS smallint) is not null then
	vin_from_age :=  cast(vin_ip_param::json->>'vin_from_age' as smallint);
	
	v_query := (v_query||' and extract(year from age(now(),pm.dob))::smallint >= ' || vin_from_age  );
	end if;
	if CAST(VIN_IP_PARAM::JSON->>'vin_rank_code' AS character varying) is not null then
	vin_rank_code := replace(replace((replace (cast(vin_ip_param::json->>'vin_rank_code' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and PGP_SYM_DECRYPT(pm.rank_code::bytea, current_setting(''encrypt.key'')) in (' || vin_rank_code || ')');
	end if;

	if CAST(VIN_IP_PARAM::JSON->>'vin_to_age' AS smallint) is not null then
	vin_to_age :=  cast(vin_ip_param::json->>'vin_to_age' as smallint);
	
	v_query := (v_query||' and extract(year from age(now(),pm.dob))::smallint <= ' || vin_to_age  );
	end if;	
	if CAST(VIN_IP_PARAM::JSON->>'vin_user_type' AS character varying) is not null then
	vin_user_type := replace(replace((replace (cast(vin_ip_param::json->>'vin_user_type' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.user_type_code in ( ' || vin_user_type || ')');
	end if;
	
	if CAST(VIN_IP_PARAM::JSON->>'vin_emp_gender' AS character varying) is not null then
	vin_emp_gender := replace(replace((replace (cast(vin_ip_param::json->>'vin_emp_gender' as character varying),'[','')),']',''),'"','''');
	
	v_query := (v_query||' and pm.emp_gender in (' || vin_emp_gender || ')');
	end if;
	
	vin_ip_query := (v_query);
	raise notice 'v_query %',v_query;
	execute ' drop table if exists exam_status_report_'||vin_session_id ;
	execute ' create temp table if not exists exam_status_report_'|| vin_session_id ||' as '|| vin_ip_query ;	
	
	execute	'select json_agg(z) 
				from
				(select
				(select (row_to_json(a))
				from
				(select prog_disp_name"chartDisplayName",prog_long_name"gridReportDisplayame"
				 from epps_admin.epps_prog_mst
				 where prog_id = '|| vin_prog_id ||')a)"reportDetails",
				
				(select json_agg(row_to_json(b))
				from
				(select distinct 1 from exam_status_report_'||vin_session_id ||')b)"chartPanelXaxis",
				
				(select json_agg(row_to_json(c))
				from
				(select distinct 1 from exam_status_report_'||vin_session_id ||')c)"chartPanelYaxis"
				)z;' into vin_out_param;
				
				execute 'select (json_agg(row_to_json(d)))
				 from
				 ('||vin_ip_query||')d  ;'  into vin_out_table_data;

				execute	'select (json_agg(row_to_json(e)))
				from
				(select table_name,column_name,data_type,character_maximum_length,ordinal_position
				from INFORMATION_SCHEMA.COLUMNS
				where TABLE_NAME = ''exam_status_report_'||vin_session_id ||''' 
				order by ordinal_position)e ;' into vin_out_table_struc;
end if;

EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN func_query_report: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.func_query_report(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.func_query_report(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.func_query_report(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.func_query_report(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.func_query_report(json) TO epps_readonly;

GRANT EXECUTE ON FUNCTION inayu.func_query_report(json) TO dbaactivity;

