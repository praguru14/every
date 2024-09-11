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
/*
select * from inayu.func_query_report('{"vin_app_status":10,"vin_cmnd_code":"IHQ","vin_unit_code":"WESEE NEW DELHI",
												"vin_mi_unit_code":"WESEE NEW DELHI","vin_emp_gender":"M","vin_ama_pno":"100001",
												"vin_inv_from_dt":"2021-10-01","vin_inv_to_dt":"2021-11-30",
												"vin_from_age":56,"vin_to_age":64,"vin_prog_id":"listPerToRoleLnk"}');
												

select * from inayu.func_query_report('{"vin_app_status":10,"vin_from_age":56,"vin_to_age":64,
"vin_prog_id":"loadAppointmentStatusReport","vin_session_id":125}');
	
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
		
		
		
		v_query := 'select ad.pno, PGP_SYM_DECRYPT(coalesce(ap.full_nm,pm.full_name)::bytea, current_setting(''encrypt.key'')) as "Full Name",
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

