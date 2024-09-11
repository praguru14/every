-- FUNCTION: inayu.proc_per_investigation_print(json)

-- DROP FUNCTION inayu.proc_per_investigation_print(json);

CREATE OR REPLACE FUNCTION inayu.proc_per_investigation_print(
	vin_ip_param json,
	OUT vin_out_param json)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
/*created by: Uma@23-Sep-2021 : procedureto returns personnel's investigation data for print
*/
v_app_sr_no integer;
v_final_query text ;
v_block_query text :='';
v_gen_block_cnt smallint := 0;
v_app_no text;
i record;
j record;
v_medi_inv_type text;
v_per_sr_no integer;
v_unit_name text;
v_unit_code text;
v_curr_medi_catg text;
v_final_obs_remarks text;
v_last_ame_dt date;
v_last_inv_sr_no integer;
v_medi_catg_wef date;
begin
--check flyway script execution
	 if vin_ip_param ->> 'vin_app_no' is null then 
	 
	 else
	 	select sr_no,app_type,per_sr_no into v_app_sr_no,v_medi_inv_type,v_per_sr_no
		from inayu.appointment_dtl ad 
		where app_no = vin_ip_param ->> 'vin_app_no';
	 end if;
	 raise notice 'v_app_sr_no %',v_app_sr_no;
	 if v_app_sr_no is null then
	 	raise exception 'Invalid Request Number. ';
	 end if;
	 
	v_final_query := 'select json_agg(row_to_json(a)) vin_out_param
	from ( select (select watermark_image_path from epps_admin.epps_division_mst where div_cd = 1) "watermarkImagePath",
			'||quote_literal(v_medi_inv_type)||' "appointmentType",';
	
	if not exists (select 1 from inayu.app_per_dtl ap where ap.app_sr_no = v_app_sr_no) then			
			
			select max(sr_no) into v_last_inv_sr_no
			from inayu.appointment_dtl ad 
			where ad.per_sr_no = v_per_sr_no 
			and ad.app_stage = 40;

			select ap.inv_unit_name,ap.inv_unit_code , fd.curr_medi_catg, fd.final_obs_remarks,ap.inv_submit_dt,fd.medi_catg_wef
			into v_unit_name,v_unit_code, v_curr_medi_catg,v_final_obs_remarks,v_last_ame_dt,v_medi_catg_wef
			from inayu.app_per_dtl ap 
			left join inayu.inv_final_obs_dtl fd on ap.app_sr_no = fd.app_sr_no
			where ap.per_sr_no = v_per_sr_no
			and ap.app_sr_no = v_last_inv_sr_no;

				v_block_query := v_block_query|| '(select row_to_json(personnel) "appointmentPersonDetail"
				from(
					select pm.pno "personalNumber",
						PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting(''encrypt.key'')) "fullName",
						pm.emp_gender "employeeGender",
						pm.dob "dateOfBirth",
						(case when (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pm.dob)) < 0 then (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pm.dob::date) - 1) ||'' Years '' || (12 + (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pm.dob::date)))||'' months''
							else (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pm.dob::date) ) ||'' Years '' || ( (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pm.dob::date)))||'' months''
						end ) "age",
						pd.unit_code "personnelUnitCode",
						(select u.name from inmdb.unit_mst u where u.code = pd.unit_code) "personnelUnitName",
						PGP_SYM_DECRYPT(pd.rank_code::bytea, current_setting(''encrypt.key'')) "rankCode",
						(select r.name from inmdb.rank_mst r where r.code = PGP_SYM_DECRYPT(pd.rank_code::bytea, current_setting(''encrypt.key''))) "rankName",
						pd.branch_code "branchCode",
						(select b.name from inmdb.branch_mst b where b.code = pd.branch_code) "branchName",
						pd.date_comm "dateOfCommission",
						pd.service_join_date "dateOfJoining",
						(case when (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.service_join_date)) < 0 then (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.service_join_date::date) - 1) ||'' Years '' || (12 + (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.service_join_date::date)))||'' months''
							else (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.service_join_date::date) ) ||'' Years '' || ( (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.service_join_date::date)))||'' months''
						end ) "totalService",
						pd.service_code "serviceCode",
						(select s.name from inmdb.service_mst s where s.code = pd.service_code) "serviceName",
						pd.comn_type "commissionType",
						ad.app_status "appointmentStatus",
						ad.app_stage "appointmentStage",
						'||QUOTE_NULLABLE(v_last_ame_dt)||' "lastAmeDate",
						'||QUOTE_NULLABLE(v_unit_code)||' "lastAmeUnitCode",
						null::text "investigationUnitCode",
						null::text "medicalExaminationHeldAt",
						'||QUOTE_NULLABLE(v_unit_name)||' "lastAmeUnitName",
						'||QUOTE_NULLABLE(PGP_SYM_DECRYPT(v_final_obs_remarks::bytea, current_setting('encrypt.key')))||' "pastMedicalHistory",
						PGP_SYM_DECRYPT(coalesce('||QUOTE_NULLABLE(v_curr_medi_catg)||',pd.medi_code)::bytea, current_setting(''encrypt.key'')) "presentMedicalCategory",
						ad.app_no "appointmentNumber",
						null "medicalExaminationHeldOnDate",
						null "medicalExaminationHeldAt",
						null "investigationAuthorityPersonNumber",
						ad.app_stage "appointmentStage",
						PGP_SYM_DECRYPT(ad.per_mob_no::bytea, current_setting(''encrypt.key'')) "personalMobileNumber",
						ad.declaration "declaration",
						ad.app_type "appointmentType",
						'||QUOTE_NULLABLE(v_medi_catg_wef)||' "withEffectFrom" ';
					
					v_block_query := v_block_query||'from inmdb.per_mst pm 
					inner join inmdb.per_dtls pd on pm.sr_no = pd.psr_no
					left join inayu.appointment_dtl ad on ad.per_sr_no = pm.sr_no
					where  pm.sr_no = '||v_per_sr_no|| 'and ad.sr_no = '|| v_app_sr_no||')personnel),';
					
	else 
	v_block_query := v_block_query|| '(select row_to_json(personnel) "appointmentPersonDetail"
			from(
				select pd.pno "personnelNumber",
					PGP_SYM_DECRYPT(pd.full_nm::bytea, current_setting(''encrypt.key'')) "fullName",
					pd.emp_gender "employeeGender",
					pd.dob "dateOfBirth",
					(case when (DATE_PART(''month'', pd.inv_dt::date) - DATE_PART(''month'', pd.dob)) < 0 then (DATE_PART(''year'', pd.inv_dt::date) - DATE_PART(''year'', pd.dob::date) - 1) ||'' Years '' || (12 + (DATE_PART(''month'', pd.inv_dt::date) - DATE_PART(''month'', pd.dob::date)))||'' months''
						else (DATE_PART(''year'', pd.inv_dt::date) - DATE_PART(''year'', pd.dob::date) ) ||'' Years '' || ( (DATE_PART(''month'', pd.inv_dt::date) - DATE_PART(''month'', pd.dob::date)))||'' months''
					end ) "age",
					pd.unit_cd "personnelUnitCode",
					pd.unit_nm "personnelUnitName",
					PGP_SYM_DECRYPT(pd.rank_cd::bytea, current_setting(''encrypt.key'')) "rankCode",
					(select r.name from inmdb.rank_mst r where r.code = PGP_SYM_DECRYPT(pd.rank_cd::bytea, current_setting(''encrypt.key''))) "rankName",
					pd.branch_cd "branchCode",
					pd.branch_nm "branchName",
					pd.date_comm "dateOfCommission",
					pd.date_join "dateOfJoining",
					pd.service_cd "serviceCode",
					(select s.name from inmdb.service_mst s where s.code = pd.service_cd) "serviceName",
					(case when (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.date_join)) < 0 then (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.date_join::date) - 1) ||'' Years '' || (12 + (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.date_join::date)))||'' months''
							else (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.date_join::date) ) ||'' Years '' || ( (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.date_join::date)))||'' months''
						end ) "totalService",						
					pd.comn_type "commissionType",
					pd.last_inv_dt "lastAmeDate",
					pd.last_inv_unit_name "lastAmeUnitName",
					PGP_SYM_DECRYPT(pd.past_medical_hist::bytea, current_setting(''encrypt.key'')) "pastMedicalHistory",
					PGP_SYM_DECRYPT(pd.curr_medical_catg::bytea, current_setting(''encrypt.key'')) "presentMedicalCategory",
					pd.inv_dt "medicalExaminationHeldOnDate",
					pd.inv_unit_name "medicalExaminationHeldAt",
					pd.inv_authority_pno "investigationAuthorityPersonNumber",
					pd.medi_catg_wef "withEffectFrom",
					ad.app_stage "appointmentStage",
					PGP_SYM_DECRYPT(ad.per_mob_no::bytea, current_setting(''encrypt.key'')) "personalMobileNumber",
					ad.declaration "declaration",
					ad.app_type "appointmentType"
				from inayu.appointment_dtl ad
				inner join inayu.app_per_dtl pd on ad.sr_no = pd.app_sr_no 
				left join inayu.inv_final_obs_dtl fd on ad.sr_no = fd.app_sr_no
				where ad.sr_no = '||v_app_sr_no||'
				)personnel),';
	raise notice ' v_block_query %',v_block_query; 
	end if;
	raise notice ' v_block_query %',v_block_query; 
	for i in select distinct inv_type from inayu.inv_param_mst im where im.isactive = 1 loop
		
		if i.inv_type <> 'GENERAL' then
			select 	v_block_query ||'(select row_to_json(row) "investigation'||(case when i.inv_type = 'INVESTIGATION' then 'Inves' else initcap(i.inv_type) end)||'Detail" from( 
					select created_by "createdBy",
					created_dt "createdDate",
					crea_by_nm "createdByName",
					crea_unit_cd "createdByUnitCode",
					crea_unit_nm "createdByUnitName",
					inv_by "investigationBy",
					inv_by_nm "investigationByName",
					inv_unit_cd "investigatorUnitCode",
					inv_unit_nm "invstigatorUnitName",
					inv_dt "investigationDate",'|| ARRAY_TO_STRING(ARRAY_AGG(PARAM_COL_ID || ' AS "'||param_entity_id||'"'),',') ||' from '||param_tbl_id ||' where app_sr_no ='||QUOTE_NULLABLE(v_app_sr_no)||'::integer)row),'
				into v_block_query
				FROM inayu.inv_param_mst ip
				WHERE ip.inv_type =i.inv_type
				and fix_cust_flag = 0
				and medi_inv_type in (v_medi_inv_type,'ALL')
				group by param_tbl_id;
				
						
		else 
			--v_block_query := v_block_query|| '(select json_agg(row) "'||lower(i.inv_type)||'Detail" from( select';

				for j in select distinct inv_sub_type from inayu.inv_param_mst im where im.inv_type = i.inv_type and im.inv_sub_type <> 'RESPIRATORY SYSTEM' loop
					/*if v_gen_block_cnt > 0 then
						v_block_query := v_block_query|| ' ,';
					end if;*/
					select 	v_block_query ||'(select row_to_json(row)"investigation'||
						(case when j.inv_sub_type='GASTRO INTESTINAL SYSTEM' then 'GastroIntestinalSystem'
							when j.inv_sub_type='CENTRAL NERVOUS SYSTEM' then 'CentralNervousSystem'
							when j.inv_sub_type in ('CARDIO VASCULAR SYSTEM','RESPIRATORY SYSTEM') then 'CardioRespiratory'
							when j.inv_sub_type = 'PHYSICAL CAPACITY' then 'PhysicalCapacity' end)||'Detail" from( 
						select created_by "createdBy",
						created_dt "createdDate",
						crea_by_nm "createdByName",
						crea_unit_cd "createdByUnitCode",
						crea_unit_nm "createdByUnitName",
						inv_by "investigationBy",
						inv_by_nm "investigationByName",
						inv_unit_cd "investigatorUnitCode",
						inv_unit_nm "invstigatorUnitName",
						inv_dt "investigationDate",'|| ARRAY_TO_STRING(ARRAY_AGG(PARAM_COL_ID || ' AS "'||param_entity_id||'"'),',') ||' from '||param_tbl_id ||' where app_sr_no ='||QUOTE_NULLABLE(v_app_sr_no)||'::integer)row),'
					into v_block_query
					FROM inayu.inv_param_mst im
					WHERE im.inv_type =i.inv_type
					and (case when j.inv_sub_type = 'CARDIO VASCULAR SYSTEM' then im.inv_sub_type in ('CARDIO VASCULAR SYSTEM','RESPIRATORY SYSTEM') else im.inv_sub_type  =j.inv_sub_type end)
					and fix_cust_flag = 0
					and medi_inv_type in (v_medi_inv_type,'ALL')
					group by param_tbl_id;

					--v_gen_block_cnt := v_gen_block_cnt + 1;	
				end loop;
			--v_block_query := v_block_query|| ')row ),';
		end if; --if i.inv_type <> 'GENERAL' then
		
		v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "'||lower(i.inv_type)||'Param"
				from(select ap.inv_sub_type "investigationSubType",
						ap.inv_param "investigationParameter" ,
						ap.inv_param_val "investigationParameterValue",
						ap.add_inv_remarks "additionalInvestigationRemarks",
						ip.uom "unitOfMeasurement"
					from inayu.inv_add_param_dtl ap
					inner join inayu.inv_param_mst ip 
						on ap.inv_type = ip.inv_type
						and ap.inv_sub_type = ip.inv_sub_type
						and ap.inv_param = ip.param
					where ap.app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
					and ap.inv_type = '||quote_literal(i.inv_type)||'
				)row),'; 
		
		v_block_query:= v_block_query||'(select json_agg(row_to_json(row)) "investigation'||(case when i.inv_type = 'INVESTIGATION' then 'Inves' else initcap(i.inv_type) end)||'RemarksDtos"
				from(select remarks "remarks",
						created_by "createdBy",
						created_dt "createdDate",
						modified_by "modifiedBy",
						modified_dt "modifiedDate",
						crea_by_nm "createdByName",
						crea_unit_cd "createdByUnitCode",
						crea_unit_nm "createdByUnitName"
					from ';
				
		if  i.inv_type = 'INVESTIGATION' then
			v_block_query:= v_block_query||' inayu.inv_inves_remarks';
		elsif  i.inv_type = 'DENTAL' then
			v_block_query:= v_block_query||' inayu.inv_dental_remarks';
		elsif  i.inv_type = 'GYNAECOLOGY' then
			v_block_query:= v_block_query||' inayu.inv_gyn_remarks';
		elsif  i.inv_type = 'GENERAL' then
			v_block_query:= v_block_query||' inayu.inv_general_remarks';
		elsif  i.inv_type = 'SURGERY' then
			v_block_query:= v_block_query||' inayu.inv_sergery_remarks';
		elsif  i.inv_type = 'ENT' then
			v_block_query:= v_block_query||' inayu.inv_ent_remarks';
		elsif  i.inv_type = 'EYE' then
			v_block_query:= v_block_query||' inayu.inv_eye_remarks';
		end if;
		
		v_block_query:= v_block_query|| ' where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer					
		)row),';
				
	end loop;
	
	v_block_query:= v_block_query|| '(select row_to_json(row) "finalObservationDetail"
				from(select PGP_SYM_DECRYPT(final_obs_remarks::bytea, current_setting(''encrypt.key'')) "finalObservationRemarks",
						final_obs_place "finalObservationPlace",
						final_obs_dt "finalObservationDate",
						final_obs_by "finalObservationBy",
						final_obs_by_nm "finalObservationByName",
						rank_cd "rankCode",
						PGP_SYM_DECRYPT(curr_medi_catg::bytea, current_setting(''encrypt.key'')) "currentMedicalCategory" ,
						medi_catg_wef "medicalCategoryWithEffectFrom"
					from inayu.inv_final_obs_dtl
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
				)row),';
		
	v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "peruserDetail"
				from(select sr_no "serialNumber",
						approve_by "approvedBy",
						approve_by_nm "approvedByName",
						app_unit_cd "approverUnitCode",
						app_unit_nm "approverUnitName",
						app_rank_cd "rankCode",
						approve_dt "approvedByDate",
						approve_place "approvedByPlace",
						PGP_SYM_DECRYPT(approve_remarks::bytea, current_setting(''encrypt.key'')) "approveRemarks"
					from inayu.inv_approve_dtl ad
					left join epps_admin.epps_role_mst r
						on ad.approver_role_cd = r.role_cd
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
					and r.role_disp_name = ''SENIOR STAFF OFFICER(HEALTH)''					 
				)row),';
				
	v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "approverDetail"
				from(select sr_no "serialNumber",
						approve_by "approvedBy",
						approve_by_nm "approvedByName",
						app_unit_cd "approverUnitCode",
						app_unit_nm "approverUnitName",
						app_rank_cd "rankCode",
						approve_dt "approvedByDate",
						approve_place "approvedByPlace",
						PGP_SYM_DECRYPT(approve_remarks::bytea, current_setting(''encrypt.key'')) "approveRemarks"
					from inayu.inv_approve_dtl ad
					left join epps_admin.epps_role_mst r
						on ad.approver_role_cd = r.role_cd
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
					and r.role_disp_name <> ''SENIOR STAFF OFFICER(HEALTH)''					 
				)row),';
				
	v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "investigationVaccineDetail"
				from(select vaccine_type "vaccineType",
						vaccine_dtl "vaccineDetail",
						batch_no "batchNumber",
						vaccine_dt "vaccineDate",
						next_due_dt "nextDueDate",
						created_by "createdBy",
						created_dt "createdDate"
					from inayu.inv_vaccine_dtl
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
				)row)';
				
	v_final_query :=  v_final_query||v_block_query|| ') a';
	 	
raise notice 'v_final_query : %',v_final_query;
	
	execute v_final_query into vin_out_param;

EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN proc_per_investigation_print: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.proc_per_investigation_print(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_per_investigation_print(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.proc_per_investigation_print(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.proc_per_investigation_print(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_per_investigation_print(json) TO epps_readonly;

