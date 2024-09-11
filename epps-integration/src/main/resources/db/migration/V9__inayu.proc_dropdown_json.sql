-- FUNCTION: inayu.proc_dropdown_json(json)

-- DROP FUNCTION inayu.proc_dropdown_json(json);

CREATE OR REPLACE FUNCTION inayu.proc_dropdown_json(
	vin_ip_param json,
	OUT vin_out_param json)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
/*
Created by : Sushant
Purpose    : for geting count of persons on flag basis.
date       : 21/07/2021 
Call :
	
select * from inayu.proc_dropdown_json('{"vin_gender":1,"vin_appointment_status":1,"vin_investigation_status":1,
"vin_mi_room":1,"vin_ama":1,"vin_command":1}');

select * from inayu.proc_dropdown_json('{}')
*/
	vin_gender smallint;
	vin_command smallint;
	vin_branch smallint;
	vin_user_type smallint;
	vin_pno character varying ='';
	v_final_query text;
	v_select_query text;
	v_val smallint;

	vin_appointment_status smallint;
	v_app_status character varying;
	vin_investigation_status smallint;
	v_inv_status character varying;
	vin_mi_room smallint;
	vin_ama smallint;
	vin_medical smallint;
	vin_role_code character varying;
begin
	v_final_query := 'select  json_agg(q) 
					  from( select 
					 ';
			
			v_select_query := concat(v_select_query,'(select json_agg(a) 
								from
								(select a.code"userTypeCode",a.name"userTypeName" 
								from inmdb.user_type_mst a
								where a.isactive =1)a)"userType"');
							
				raise notice 'v_select_query %',v_select_query;				
	
		
		if cast(vin_ip_param::json ->> 'vin_gender' as smallint) = 1 then
			v_select_query := concat(v_select_query,',(select json_agg(f) 
								from
								(select code "genderCode",name"genderName"
								from inmdb.gender_mst
								where isactive = 1)f)"gender"');
		end if;
		
		if cast(vin_ip_param::json ->> 'vin_appointment_status' as smallint) = 1 then
			v_app_status := 'APP_STATUS';
			v_app_status := quote_literal(v_app_status);
			raise notice 'v_app_status %',v_app_status;
			v_select_query := concat(v_select_query,',(select json_agg(f) 
								from
								(select  ag.stage_cd"stageCode", ag.stage_desc"stageDescription"
								from epps_admin.epps_stage_mst ag  
								where ti_code = '|| v_app_status ||')f)"appointmentStatus"');
		end if;
		
		if cast(vin_ip_param::json ->> 'vin_investigation_status' as smallint) = 1 then
			v_inv_status := 'INV_STAGE';
			v_inv_status := quote_literal(v_inv_status);
			v_select_query := concat(v_select_query,',(select json_agg(f) 
								from
								(select  ag.stage_cd"stageCode", ag.stage_desc"stageDescription"
								from epps_admin.epps_stage_mst ag  
								where ti_code = '|| v_inv_status ||')f)"investigationStatus"');
		end if;
		
		if cast(vin_ip_param::json ->> 'vin_mi_room' as smallint) = 1 then
			v_select_query := concat(v_select_query,',(select json_agg(f) 
								from
								(select mi_unit_code"miUnitCode", name"miUnitName" 
								from inayu.unit_mi_ama_lnk ua
								inner join inmdb.unit_mst u 
								on ua.mi_unit_code = u.code)f)"miRoom"');
		end if;
		
		if cast(vin_ip_param::json ->> 'vin_ama' as smallint) = 1 then
			v_select_query := concat(v_select_query,',(select json_agg(f) 
								from
								(select distinct ama_pno"amaPno", PGP_SYM_DECRYPT(full_name::bytea, current_setting(''encrypt.key'')) "amaFullName"
								from inayu.unit_mi_ama_lnk ua
								inner join inmdb.per_mst pm on ua.ama_pno = pm.pno)f)"ama"');
		end if;
		
		if cast(vin_ip_param::json ->> 'vin_command' as smallint) = 1 then
			
			v_select_query := concat(v_select_query,',(select json_agg(m) 
								from
								(select code "commandCode",name"commandName"
								from inmdb.command_mst
								where isactive = 1)m)"command"');
						
		end if;
		
		if cast(vin_ip_param::json ->> 'vin_medical' as smallint) = 1 then
			v_select_query := concat(v_select_query,',(select json_agg(g) 
								from
								(select code "medicalCode",name"medicalName"
								from inmdb.medical_mst
								where isactive = 1)g)"medical"');
		end if;
		
		
			v_final_query :=  v_final_query || v_select_query || ')q;';
							 
			execute v_final_query into vin_out_param  ;
		

EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN proc_dropdown: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.proc_dropdown_json(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_dropdown_json(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.proc_dropdown_json(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.proc_dropdown_json(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_dropdown_json(json) TO epps_readonly;

GRANT EXECUTE ON FUNCTION inayu.proc_dropdown_json(json) TO dbaactivity;

