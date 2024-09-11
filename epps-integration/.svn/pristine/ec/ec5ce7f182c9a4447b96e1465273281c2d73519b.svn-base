-- FUNCTION: inayu.proc_get_per_medexam_info(json)

-- DROP FUNCTION inayu.proc_get_per_medexam_info(json);

CREATE OR REPLACE FUNCTION inayu.proc_get_per_medexam_info(
	vin_ip_param json,
	OUT vin_out_param json)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
/*created by: Uma@22-Sep-2021 : Procedure to get list of AME/PME examination details of personnel
*/
v_count smallint;
v_final_query text ;
v_block_query text :='';
begin
	
	select count(1) into v_count
	from inayu.appointment_dtl ad
	inner join inmdb.per_mst pm on ad.per_sr_no = pm.sr_no
	where pm.pno = vin_ip_param ->> 'vin_pno'
	and ad.app_stage <> 40;
	
	v_block_query := ' 
		select json_agg(row_to_json(row)) vin_out_param
		from(
			select (case when '||v_count||' > 0 then 0 else 1 end) "newYn",
				(select json_agg(row_to_json(row)) "medicalExaminationInfo"
				from(select pm.pno "personalNumber",
						pm.sr_no "personalSerialNumber",
						PGP_SYM_DECRYPT(coalesce(ap.full_nm,pm.full_name)::bytea, current_setting(''encrypt.key'')) "fullName",
						ad.app_type "amePmeFlag",
						ad.app_no "appointmentNumber",
						coalesce(ap.unit_nm,(select u.name from inmdb.unit_mst u where u.code = ad.unit_cd)) "unitName",
						ap.inv_dt "investigationHeldOnDate",
						ap.inv_unit_name "investigationHeldAt",
						app_stage "appointmentStage",
						app_status "appointmentStatus",
						PGP_SYM_DECRYPT(coalesce(ap.rank_cd,pd.rank_code)::bytea, current_setting(''encrypt.key'')) "rankCode",
						ad.app_no "appointmentNumber",
						ad.sr_no "appointmentSerialNumber",
						ap.inv_authority_pno "authority",
						extract(''year'' from age(coalesce(ap.inv_dt,now()),pm.dob)) "age",
						(select inv_prof_desc
						from inayu.inv_prof_mst ipm
						inner join inayu.agewise_inv_prof_dtl aip
						on ipm.name = aip.agewise_profile_chk
						where aip.age = extract(''year'' from age(coalesce(ap.inv_dt,now()),pm.dob))::smallint) "agewiseInvestigation",
						ac.mi_unit_code "medicalInvestigationUnitCode",
						ac.mi_unit_name "medicalInvestigationUnitName",
						ac.mi_date "medicalInvestigationDate",
						ac.nh_unit_code "navalHospitalUnitCode",
						ac.nh_unit_name "navalHospitalUnitName",
						ac.nh_date "navalHospitalDate",
						ac.ext_hosp_name "externalHospitalName",
						ac.ext_hosp_date "externalHospitalDate",
						ac.mi_exam_date "medicalInvestigationExaminationDate",
						ac.nh_exam_date "navalHospitalExaminationDate",
						ac.ext_hosp_exam_date "externalHospitalExaminationDate",
						ac.mi_inv_type "medicalInvestigationType",
						ac.nh_inv_type "navalHospitalInvestigationType",
						ac.ext_hosp_inv_type "externalHospitalInvestigationType",						
						ad.per_preferred_date "personalPreferredDate"
					from inayu.appointment_dtl ad
					inner join inmdb.per_mst pm on ad.pno = pm.pno
					left join inmdb.per_dtls pd on pm.pno = pd.pno
					left join inayu.app_per_dtl ap on ad.sr_no = ap.app_sr_no
					left join inayu.app_confirm_dtl ac on ad.sr_no= ac.app_sr_no
					where 1=1';
					
					if vin_ip_param ->> 'vin_pno' is not null then 
						v_block_query := v_block_query||' and pm.pno = '||quote_literal(vin_ip_param ->> 'vin_pno');
					end if;
					
					if vin_ip_param ->> 'vin_per_unit_code' is not null then 
						v_block_query := v_block_query||' and pm.pno = '||quote_literal(vin_ip_param ->> 'vin_per_unit_code');
					end if;
					if vin_ip_param ->> 'vin_inv_unit_code' is not null then 
						v_block_query := v_block_query||' and ap.inv_unit_code = '||quote_literal(vin_ip_param ->> 'vin_inv_unit_code');
					end if;
					if vin_ip_param ->> 'vin_from_dt' is not null then 
						if vin_ip_param ->> 'vin_inv_app_wise_flag' = 'A' then
							v_block_query := v_block_query||' and ad.created_dt >= '||quote_literal(vin_ip_param ->> 'vin_from_dt')||'::date';
						else 
							v_block_query := v_block_query||' and ap.inv_dt >= '||quote_literal(vin_ip_param ->> 'vin_from_dt')||'::date';
						end if;
					end if;
					if vin_ip_param ->> 'vin_to_dt' is not null then 
						if vin_ip_param ->> 'vin_inv_app_wise_flag' = 'A' then
							v_block_query := v_block_query||' and ad.created_dt >= '||quote_literal(vin_ip_param ->> 'vin_to_dt')||'::date';
						else 
							v_block_query := v_block_query||' and ap.inv_dt >= '||quote_literal(vin_ip_param ->> 'vin_to_dt')||'::date';
						end if;
					end if;
					
					if vin_ip_param ->> 'vin_pend_approval' = '1' then
						v_block_query := v_block_query || ' and ad.app_stage = 30
										and ad.next_modifier_role in (select role_cd from epps_admin.epps_emp_loc_lnk el
										   inner join epps_admin.epps_location_mst l on l.comp_cd = 1 and l.div_cd = 1 and l.loc_cd = el.loc_cd
										   where el.emp_cd = '||quote_literal(vin_ip_param ->>'vin_login_pno')||'
										   and l.loc_disp_name = '||quote_literal(vin_ip_param ->>'vin_login_unit_code')||'
										   and el.active_yn = ''Y'')'  ;									 
					end if;
					if vin_ip_param ->> 'vin_reschedule' = '1' then
						v_block_query := v_block_query || ' and ad.app_status = 30
										and ad.app_stage = 10';
										
					end if;
					if vin_ip_param ->> 'vin_pend_confirm' = '1' then
						v_block_query := v_block_query || ' and ad.app_status = 10
										and ad.ama_pno = '||quote_literal(vin_ip_param ->>'vin_login_pno')  ;									 
					end if;
					
					if vin_ip_param ->> 'vin_stage' is not null then
						v_block_query := v_block_query || ' and ad.app_stage = '||quote_literal(vin_ip_param ->>'vin_stage')||'::smallint';
					end if;
					
				v_block_query = v_block_query ||')row)		
		)row';
		raise notice '  v_block_query  %',v_block_query;
	execute v_block_query into vin_out_param;
EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN proc_get_per_medexam_info: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.proc_get_per_medexam_info(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_get_per_medexam_info(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.proc_get_per_medexam_info(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.proc_get_per_medexam_info(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_get_per_medexam_info(json) TO epps_readonly;

