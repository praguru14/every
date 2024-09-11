-- FUNCTION: inayu.proc_per_investigation_dtl(json)

-- DROP FUNCTION inayu.proc_per_investigation_dtl(json);

CREATE OR REPLACE FUNCTION inayu.proc_per_investigation_dtl(
	vin_ip_param json,
	OUT vin_out_param json)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
/*created by: Uma@7-Sep-2021 : procedureto returns person and investigation deatail
*/

v_per_gender character varying(1);
v_per_sr_no integer;
v_app_sr_no integer;
v_final_query text ;
v_block_query text :='';
v_block_cnt smallint :=0;
v_gen_block_cnt smallint :=0;
v_age_yy smallint;
v_app_no text;
i record;
j record;
v_last_ame_dt date;
v_unit_name text; 
v_unit_code text;
v_curr_medi_catg text;
v_final_obs_remarks text;
v_last_inv_sr_no integer;
v_medi_catg_wef date;
v_inv_unit_code text;
v_inv_unit_name text;
v_due_date date;
v_apply_before_date date;
v_rank_code text;
v_error_msg text;
v_app_stage smallint;
v_app_status smallint;
begin
raise notice '111 %',vin_ip_param ->> 'vin_app_no';
	if vin_ip_param ->> 'vin_app_no' is not null then 
		raise notice '111';
		select (case when upper(pm.emp_gender) = 'MALE' or upper(pm.emp_gender) = 'M' then 'M' else 'F' end), ad.sr_no,pm.sr_no ,
		(case when (DATE_PART('month', now()::date) - DATE_PART('month', pm.dob)) < 0 then (DATE_PART('year', now()::date) - DATE_PART('year', pm.dob::date) - 1)
		 	else DATE_PART('year', now()::date) - DATE_PART('year', pm.dob::date) end),ad.app_no
		into v_per_gender ,v_app_sr_no,v_per_sr_no, v_age_yy,v_app_no
		from inayu.appointment_dtl ad
		inner join inmdb.per_mst pm on ad.per_sr_no = pm.sr_no
		where ad.app_no = vin_ip_param ->> 'vin_app_no';
		
	elsif vin_ip_param ->> 'vin_pno' is not null then
		select (case when upper(pm.emp_gender) = 'MALE' or upper(pm.emp_gender) = 'M' then 'M' else 'F' end),pm.sr_no ,
		(case when (DATE_PART('month', now()::date) - DATE_PART('month', pm.dob)) < 0 then (DATE_PART('year', now()::date) - DATE_PART('year', pm.dob::date) - 1)
		 	else DATE_PART('year', now()::date) - DATE_PART('year', pm.dob::date) end),pd.rank_code
		into v_per_gender ,v_per_sr_no, v_age_yy,v_rank_code
		from inmdb.per_mst pm 
		inner join inmdb.per_dtls pd on pm.pno = pd.pno
		where pm.pno = vin_ip_param ->> 'vin_pno';
		
		/*select ad.sr_no , ad.app_no into v_app_sr_no,v_app_no
		from inayu.appointment_dtl ad
		where ad.per_sr_no = v_per_sr_no
		and ad.app_stage <> 40;*/
		
		if vin_ip_param ->> 'vin_app_year' is not null then
		
			if exists (select 1 from inayu.appointment_dtl ad
					   left join inayu.app_per_dtl ap on ad.sr_no = ap.app_sr_no
					   where ad.pno = vin_ip_param ->> 'vin_pno' and ad.app_year = (vin_ip_param ->> 'vin_app_year')::smallint
					   and coalesce(ap.rank_cd,v_rank_code)= v_rank_code ) then
				select app_status, app_stage into v_app_status, v_app_stage 
				from inayu.appointment_dtl ad
			    left join inayu.app_per_dtl ap on ad.sr_no = ap.app_sr_no
			    where ad.pno = vin_ip_param ->> 'vin_pno' and ad.app_year = (vin_ip_param ->> 'vin_app_year')::smallint
			    and coalesce(ap.rank_cd,v_rank_code)= v_rank_code ;
				
				if v_app_status = 10 then
					v_error_msg := 'Your request is under process';
				elsif v_app_stage = 10 then 
					v_error_msg := 'Please undergo The examination as per the appointment date';
				else
					v_error_msg := 'You have already undergone medical examination for this year';
				end if;
			else 
				select (to_date((vin_ip_param ->> 'vin_app_year')::text||lpad(r.to_month::text,2,'0')||'01','yyyymmdd')+interval'4 months'-interval'1 day')::date ,
				(to_date((vin_ip_param ->> 'vin_app_year')::text||lpad(r.to_month::text,2,'0')||'01','yyyymmdd')+interval'1 months'-interval'1 day')::date 
				into v_apply_before_date,v_due_date
				from inayu.rank_schedule_mst r
				where r.rank_code = PGP_SYM_DECRYPT(v_rank_code::bytea, current_setting('encrypt.key'));

			end if; --if exists (select 1 from inayu
		end if;--if vin_ip_param ->> 'vin_app_yea
	end if;--if vin_ip_param ->> 'vin_app_no' 
	
	
raise notice 'v_app_sr_no % %',v_app_sr_no,v_per_sr_no;

raise notice 'v_age_yy %',v_age_yy;
	v_final_query := 'select json_agg(row_to_json(a)) vin_out_param
	from ( select ';
	
if v_error_msg is not null then
	v_final_query := v_final_query|| quote_literal(v_error_msg) ||' "errorMessage" '; 
	
elsif coalesce((vin_ip_param ->> 'vin_edossier'),'0')<> '1' then
	for j in select json_array_elements_text(vin_ip_param -> 'vin_dtl_req') vin_dtl_req  loop
	
	raise notice ' vin_dtl_req %',j.vin_dtl_req;
		if v_block_cnt > 0 then
			v_block_query := v_block_query|| ' ,';
		end if;
		
		if UPPER(j.vin_dtl_req) = 'PERSONNEL' then
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
			
			select coalesce(mi_unit_code,nh_unit_code,ext_hosp_name), coalesce(mi_unit_name,nh_unit_name,ext_hosp_name)
			into v_inv_unit_code,v_inv_unit_name
			from inayu.app_confirm_dtl ac
			where ac.app_sr_no = v_app_sr_no;
			
raise notice 'v_last_ame_dt %',v_last_ame_dt;
				v_block_query := v_block_query|| '(select row_to_json(personnel) "personnel"
				from(
					select null::integer "serialNumber",
						pm.sr_no "personalSerialNumber",
						pm.pno "personalNumber",
						PGP_SYM_DECRYPT(pm.first_name::bytea, current_setting(''encrypt.key'')) "firstName",
						PGP_SYM_DECRYPT(pm.mid_name::bytea, current_setting(''encrypt.key'')) "middleName",
						PGP_SYM_DECRYPT(pm.last_name::bytea, current_setting(''encrypt.key'')) "lastName",
						PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting(''encrypt.key'')) "fullName",
						(case when upper(pm.emp_gender) in (''M'',''MALE'') then ''Male'' else ''Female'' end) "employeeGender",
						pm.dob "dateOfBirth",
						(case when (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pm.dob)) < 0 then (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pm.dob::date) - 1) ||'' Years '' || (12 + (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pm.dob::date)))||'' months''
							else (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pm.dob::date) ) ||'' Years '' || ( (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pm.dob::date)))||'' months''
						end ) "age",
						pd.unit_code "unitCode",
						(select u.name from inmdb.unit_mst u where u.code = pd.unit_code) "unitName",
						PGP_SYM_DECRYPT(pd.rank_code::bytea, current_setting(''encrypt.key'')) "rankCode",
						(select r.name from inmdb.rank_mst r where r.code = PGP_SYM_DECRYPT(pd.rank_code::bytea, current_setting(''encrypt.key''))) "rankName",
						pd.branch_code "branchCode",
						(select b.name from inmdb.branch_mst b where b.code = pd.branch_code) "branchName",
						pd.date_comm "dateOfCommissioning",
						pd.service_join_date "dateOfJoining",
						(case when (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.service_join_date)) < 0 then (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.service_join_date::date) - 1) ||'' Years '' || (12 + (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.service_join_date::date)))||'' months''
							else (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.service_join_date::date) ) ||'' Years '' || ( (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.service_join_date::date)))||'' months''
						end ) "totalService",
						pd.service_code "serviceCode",
						(select s.name from inmdb.service_mst s where s.code = pd.service_code) "serviceName",
						pd.comn_type "typeOfCommission",
						/*ad.app_status*/ null::integer "appointmentStatus",
						/*ad.app_stage*/ null::integer "appointmentStage",
						'||QUOTE_NULLABLE(v_last_ame_dt)||' "lastInvestigationDate",
						'||QUOTE_NULLABLE(v_unit_code)||' "lastInvestigationUnitCode",
						null::text "investigationAuthorityPersonNumber",
						'||QUOTE_NULLABLE(v_inv_unit_code)||' "investigationUnitCode",
						'||QUOTE_NULLABLE(v_inv_unit_name)||' "investigationUnitName",
						'||QUOTE_NULLABLE(v_unit_name)||' "lastInvestigationUnitName",
						'||QUOTE_NULLABLE(PGP_SYM_DECRYPT(v_final_obs_remarks::bytea, current_setting('encrypt.key')))||' "pastMedicalHistory",
						PGP_SYM_DECRYPT(coalesce('||QUOTE_NULLABLE(v_curr_medi_catg)||',pd.medi_code)::bytea, current_setting(''encrypt.key'')) "currentMedicalCategory",
						'||QUOTE_NULLABLE(v_app_no)||' "appointmentNumber",
						'||QUOTE_NULLABLE(v_app_sr_no)||' "appointmentSerialNumber",
						null::date "investigationDate",
						'||QUOTE_NULLABLE(v_medi_catg_wef)||' "medicalCategoryWithEffectFrom",
						PGP_SYM_DECRYPT(pd.per_mob_no::bytea, current_setting(''encrypt.key''))  "personalMobileNumber",
						'||QUOTE_NULLABLE(v_due_date)||' "dueDate",
						'||quote_nullable(v_apply_before_date)||' "applyBeforeDate" ';
						
					v_block_query := v_block_query||'from inmdb.per_mst pm 
					inner join inmdb.per_dtls pd on pm.sr_no = pd.psr_no
					------left join inayu.appointment_dtl ad on ad.per_sr_no = pm.sr_no
					where  pm.sr_no = '||v_per_sr_no;
					
					/*if v_app_sr_no is not null then 
						v_block_query := v_block_query|| ' and ad.sr_no = '||v_app_sr_no;
					end if;*/
					
					v_block_query := v_block_query|| ')personnel)';
					raise notice 'v_block_query % ',v_block_query;
			else
				v_block_query := v_block_query|| '(select  row_to_json(personnel) "personnel"
					from(
						select pd.sr_no "serialNumber",
							pd.per_sr_no "personalSerialNumber", 
							pd.pno "personalNumber",
							PGP_SYM_DECRYPT(pd.full_nm::bytea, current_setting(''encrypt.key'')) "fullName",
							PGP_SYM_DECRYPT(pd.first_nm::bytea, current_setting(''encrypt.key'')) "firstName",
							PGP_SYM_DECRYPT(pd.mid_nm::bytea, current_setting(''encrypt.key'')) "middleName",
							PGP_SYM_DECRYPT(pd.last_nm::bytea, current_setting(''encrypt.key'')) "lastName",
							(case when upper(pd.emp_gender) in (''M'',''MALE'') then ''Male'' else ''Female'' end) "employeeGender",
							pd.dob "dateOfBirth",
							(case when (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.dob)) < 0 then (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.dob::date) - 1) ||'' Years '' || (12 + (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.dob::date)))||'' months''
								else (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.dob::date) ) ||'' Years '' || ( (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.dob::date)))||'' months''
							end ) "age",
							pd.unit_cd "unitCode",
							pd.unit_nm "unitName",
							PGP_SYM_DECRYPT(pd.rank_cd::bytea, current_setting(''encrypt.key'')) "rankCode",
							(select r.name from inmdb.rank_mst r where r.code = PGP_SYM_DECRYPT(pd.rank_cd::bytea, current_setting(''encrypt.key''))) "rankName",
							pd.branch_cd "branchCode",
							pd.branch_nm "branchName",
							pd.date_comm "dateOfCommissioning",
							pd.date_join "dateOfJoining",
							(case when (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.date_join)) < 0 then (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.date_join::date) - 1) ||'' Years '' || (12 + (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.date_join::date)))||'' months''
								else (DATE_PART(''year'', now()::date) - DATE_PART(''year'', pd.date_join::date) ) ||'' Years '' || ( (DATE_PART(''month'', now()::date) - DATE_PART(''month'', pd.date_join::date)))||'' months''
							end ) "totalService",
							pd.service_cd "serviceCode",
							pd.cmnd_cd "commandCode",
							(select s.name from inmdb.service_mst s where s.code = pd.service_cd) "serviceName",
							pd.comn_type "typeOfCommission",
							pd.last_inv_dt "lastInvestigationDate",
							pd.last_inv_unit_name "lastInvestigationUnitName",
							pd.last_inv_unit_code "lastInvestigationUnitCode",
							PGP_SYM_DECRYPT(pd.past_medical_hist::bytea, current_setting(''encrypt.key'')) "pastMedicalHistory",
							PGP_SYM_DECRYPT(pd.curr_medical_catg::bytea, current_setting(''encrypt.key'')) "currentMedicalCategory",
							pd.inv_dt "investigationDate",
							pd.inv_unit_code "investigationUnitCode",
							pd.inv_unit_name "investigationUnitName",
							pd.inv_authority_pno "investigationAuthorityPersonNumber",
							ad.app_no "appointmentNumber",
							ad.sr_no "appointmentSerialNumber",
							ad.app_status "appointmentStatus",
							ad.app_stage "appointmentStage",
							pd.medi_catg_wef "medicalCategoryWithEffectFrom",
							ad.next_modifier_role "nextUpdatorRoleCode",
							ad.per_mob_no "personalMobileNumber",
							ad.due_date "dueDate",
							ad.apply_before_date "applyBeforeDate"
						from inayu.appointment_dtl ad
						inner join inayu.app_per_dtl pd on ad.sr_no = pd.app_sr_no 
						left join inayu.inv_final_obs_dtl fd on ad.sr_no = fd.app_sr_no
						where ad.sr_no = '||v_app_sr_no||'
						)personnel)';
			end if;
		else --if j.vin_dtl_req = 'personnel' then
			if j.vin_dtl_req <> 'GENERAL' then
				select 	v_block_query ||'(select row_to_json(row) "'||lower(j.vin_dtl_req)||'Dtl" from( 
					select sr_no "serialNumber",
					created_by "createdBy",
					created_dt "createdDate",
					modified_by "modifiedBy",
					modified_dt "modifiedDate",
					crea_by_nm "createdByName",
					crea_unit_cd "createdByUnitCode",
					crea_unit_nm "createdByUnitName",
					inv_by "investigationBy",
					inv_by_nm "investigationByName",
					inv_unit_cd "investigatorUnitCode",
					inv_unit_nm "invstigatorUnitName",'||
					(case param_tbl_id when 'inayu.inv_inves_dtl' then 'inv_inves_stage as "investigationStage",'
					when 'inayu.inv_gyn_dtl' then 'inv_gyn_stage as "investigationStage",'
					when 'inayu.inv_sergery_dtl' then 'inv_sur_stage as "investigationStage",'
					when 'inayu.inv_ent_dtl' then 'inv_ent_stage as "investigationStage",'
					when 'inayu.inv_eye_dtl' then 'inv_eye_stage as "investigationStage",'
					when 'inayu.inv_dental_dtl' then 'inv_dental_stage as "investigationStage",' end)||
					'inv_dt "investigationDate",'|| 
					ARRAY_TO_STRING(ARRAY_AGG((case when PARAM_COL_ID in ('mc_hist','vagn_dis','prolapse','usg_abdomen') then 
								'PGP_SYM_DECRYPT('||PARAM_COL_ID||'::bytea, current_setting(''encrypt.key''))' else PARAM_COL_ID end)			   
							|| ' AS "'||param_entity_id||'"'),',') ||' from '||param_tbl_id ||' where app_sr_no ='||QUOTE_NULLABLE(v_app_sr_no)||'::integer)row),'
				into v_block_query
				FROM inayu.inv_param_mst 
				WHERE inv_type =j.vin_dtl_req
				and fix_cust_flag = 0
				and medi_inv_type in (vin_ip_param->>'vin_app_type','ALL')
				group by param_tbl_id;
				raise notice 'v_block_query % ',v_block_query;
			else 
				v_block_query := v_block_query|| '(select json_agg(row) "'||lower(j.vin_dtl_req)||'Dtl" from( select';
					
					for i in select distinct inv_sub_type from inayu.inv_param_mst im 
							 where im.inv_type = j.vin_dtl_req and im.inv_sub_type <> 'RESPIRATORY SYSTEM' loop
						if v_gen_block_cnt > 0 then
							v_block_query := v_block_query|| ' ,';
						end if;
						select 	v_block_query ||'(select row_to_json(row) "'||
							(case i.inv_sub_type when 'GASTRO INTESTINAL SYSTEM' then 'gastroIntestinalSystem'
								when 'CENTRAL NERVOUS SYSTEM' then 'centralNervousSystem'
								when 'CARDIO VASCULAR SYSTEM' then 'cardioVascularSystem'
								when 'RESPIRATORY SYSTEM' then 'respiratorySystem'
								when 'PHYSICAL CAPACITY' then 'physicalCapacity' end)||'Dtl" from( 
							select sr_no "serialNumber",
							created_by "createdBy",
							created_dt "createdDate",
							modified_by "modifiedBy",
							modified_dt "modifiedDate",
							crea_by_nm "createdByName",
							crea_unit_cd "createdByUnitCode",
							crea_unit_nm "createdByUnitName",
							inv_by "investigationBy",
							inv_by_nm "investigationByName",
							inv_unit_cd "investigatorUnitCode",
							inv_unit_nm "invstigatorUnitName",'||
							(case param_tbl_id when 'inayu.inv_cardio_respiratory_dtl' then 'inv_cr_stage as "investigationStage",'
							when 'inayu.inv_gis_dtl' then 'inv_gis_stage as "investigationStage",'
							when 'inayu.inv_cns_dtl' then 'inv_cns_stage as "investigationStage",'
							when 'inayu.inv_phy_capacity_dtl' then 'inv_pc_stage as "investigationStage",' end)||
							'inv_dt "investigationDate",'|| ARRAY_TO_STRING(ARRAY_AGG(PARAM_COL_ID || ' AS "'||param_entity_id||'"'),',') ||' from '||param_tbl_id ||' where app_sr_no ='||QUOTE_NULLABLE(v_app_sr_no)||'::integer)row)'
						into v_block_query
						FROM inayu.inv_param_mst im
						WHERE inv_type =j.vin_dtl_req
						and (case when i.inv_sub_type = 'CARDIO VASCULAR SYSTEM' then im.inv_sub_type in ('CARDIO VASCULAR SYSTEM','RESPIRATORY SYSTEM') else im.inv_sub_type  =i.inv_sub_type end)
						and fix_cust_flag = 0
						and medi_inv_type in (vin_ip_param->>'vin_app_type','ALL')
						group by param_tbl_id;
						

						v_gen_block_cnt := v_gen_block_cnt + 1;	
					end loop;
				v_block_query := v_block_query|| ')row ),';
			end if;
		
		v_block_query:= v_block_query||'(select json_agg(row_to_json(row)) "'||lower(j.vin_dtl_req)||'Param"
		from(
			select ip.inv_sub_type "investigationSubType",
				ip.param "parameter",';
		
		if v_per_gender = 'M' then 
			v_block_query:= v_block_query|| 'ap.m_param_from_range "maleParameterFromRange",ap.m_param_to_range "maleParameterToRange",';
		else 
			v_block_query:= v_block_query|| 'ap.f_param_from_range "femaleParameterFromRange",ap.f_param_to_range "femaleParameterToRange",';
		end if;
				
		v_block_query:= v_block_query||'uom "unitOfMeasurement",
				ip.fix_cust_flag "fixedCustomFlag"
			from inayu.inv_param_mst ip
			left join  inayu.agewise_param_range_dtl ap
				on ip.inv_type = ap.inv_type
 				and coalesce(ip.inv_sub_type,'''') = coalesce(ap.inv_sub_type,'''')
				and ip.param = ap.param 
			where upper(ip.inv_type) =upper('||quote_literal(j.vin_dtl_req)||')
			and ip.isactive = 1
			and medi_inv_type in ('||quote_literal(vin_ip_param->>'vin_app_type')||',''ALL'')
			and (case when ap.inv_type is not null then ap.isactive = 1 and '||coalesce(v_age_yy,-1) ||' between ap.from_age and ap.to_age else true end) )row)';
			
			
		end if;
		
		v_block_cnt := v_block_cnt + 1;	
	end loop;
	
	--if vin_ip_param ->>'vin_remarks' is not null then
	for j in select json_array_elements_text(vin_ip_param -> 'vin_dtl_req') vin_dtl_req  loop
	raise notice 'in remarks';
		if j.vin_dtl_req <> 'PERSONNEL' then
			if v_block_cnt > 0 then
				v_block_query := v_block_query|| ' ,';
			end if;

			v_block_query:= v_block_query||'(select json_agg(row_to_json(row)) "investigation'||(case when j.vin_dtl_req = 'INVESTIGATION' then 'Inves' else initcap(j.vin_dtl_req) end)||'RemarksDtos"
					from(select sr_no "serialNumber",
							remarks "remarks",
							created_by "createdBy",
							created_dt "createdDate",
							modified_by "modifiedBy",
							modified_dt "modifiedDate",
							crea_by_nm "createdByName",
							crea_unit_cd "createdByUnitCode",
							crea_unit_nm "createdByUnitName"
						from ';

			if upper(j.vin_dtl_req) = 'INVESTIGATION' then
				v_block_query:= v_block_query||' inayu.inv_inves_remarks';
			elsif upper(j.vin_dtl_req) = 'DENTAL' then
				v_block_query:= v_block_query||' inayu.inv_dental_remarks';
			elsif upper(j.vin_dtl_req) = 'GYNAECOLOGY' then
				v_block_query:= v_block_query||' inayu.inv_gyn_remarks';
			elsif upper(j.vin_dtl_req) = 'GENERAL' then
				v_block_query:= v_block_query||' inayu.inv_general_remarks';
			elsif upper(j.vin_dtl_req) = 'SURGERY' then
				v_block_query:= v_block_query||' inayu.inv_sergery_remarks';
			elsif upper(j.vin_dtl_req) = 'ENT' then
				v_block_query:= v_block_query||' inayu.inv_ent_remarks';
			elsif upper(j.vin_dtl_req) = 'EYE' then
				v_block_query:= v_block_query||' inayu.inv_eye_remarks';
			end if;

			v_block_query:= v_block_query|| ' where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer					
			)row)';
		
			v_block_cnt := v_block_cnt + 1;
		end if; --if vin_ip_param ->> 'vin_remarks'
	end loop;
	
	if vin_ip_param ->> 'vin_add_param' is not null then
		if v_block_cnt > 0 then
			v_block_query := v_block_query|| ' ,';
		end if;
		raise notice ' param ';	
		v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "'||lower(vin_ip_param::json ->> 'vin_add_param')||'Param"
				from(select sr_no "serialNumber",
						inv_sub_type "investigationSubType",
						inv_param "investigationParameter" ,
						inv_param_val "investigationParameterValue",
						add_inv_remarks "additionalInvestigationRemarks",
						created_by "createdBy",
						created_dt "createdDate",
						modified_by "modifiedBy",
						modified_dt "modifiedDate",
						crea_by_nm "createdByName",
						crea_unit_cd "createdByUnitCode",
						crea_unit_nm "createdByUnitName"
					from inayu.inv_add_param_dtl
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
					and inv_type = '||quote_literal(vin_ip_param ->> 'vin_add_param')||'
				)row)';
			v_block_cnt := v_block_cnt + 1;
	end if; --if vin_ip_param ->> 'vin_add_param' is not null
	
	
	if vin_ip_param ->> 'vin_doc_dtl' is not null then
	  for j in select json_array_elements_text(vin_ip_param -> 'vin_doc_dtl') vin_doc_dtl  loop
		if v_block_cnt > 0 then
			v_block_query := v_block_query|| ' ,';
		end if;
		raise notice ' doc ';	
		v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "'||lower(j.vin_doc_dtl)||'Doc"
				from(select sr_no "serialNumber",
						inv_type "investigationType",
						inv_sub_type "investigationSubType",
						param "parameter" ,
						doc_path "documentPath",
    					doc_nm "documentName",
    					doc_remarks "documentRemarks",
						created_by "createdBy",
						created_dt "createdDate",
						modified_by "modifiedBy",
						modified_dt "modifiedDate",
						crea_by_nm "createdByName",
						crea_unit_cd "createdByUnitCode",
						crea_unit_nm "createdByUnitName"
					from inayu.inv_doc_dtl
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
					and inv_type = '||quote_literal(j.vin_doc_dtl)||'
				)row)';
				
			v_block_cnt := v_block_cnt + 1;
		end loop;
	end if; --if vin_ip_param ->> 'vin_doc_dtl' is not null
	
	
	if vin_ip_param ->> 'vin_presc_dtl' is not null then
		if v_block_cnt > 0 then
			v_block_query := v_block_query|| ' ,';
		end if;
		raise notice ' presc ';	
		v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "'||lower(vin_ip_param::json ->> 'vin_presc_dtl')||'Prescription"
				from(select sr_no "serialNumber",
						presc_dtl "prescriptionDetail",
						presc_remarks "prescriptionRemarks",
						created_by "createdBy",
						created_dt "createdDate",
						modified_by "modifiedBy",
						modified_dt "modifiedDate",
						crea_by_nm "createdByName",
						crea_unit_cd "createdByUnitCode",
						crea_unit_nm "createdByUnitName"
					from inayu.inv_presc_dtl
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
					and inv_type = '||quote_literal(vin_ip_param ->> 'vin_presc_dtl')||'
				)row)';
				
			v_block_cnt := v_block_cnt + 1;
	end if; --if vin_ip_param ->> 'vin_presc_dtl' is not null
	
	--if vin_ip_param ->> 'vin_final_obs' = '1' then
		if v_block_cnt > 0 then
			v_block_query := v_block_query|| ' ,';
		end if;
		raise notice ' final ';	
		v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "finalObservation"
				from(select sr_no "serialNumber",
						PGP_SYM_DECRYPT(final_obs_remarks::bytea, current_setting(''encrypt.key'')) "finalObservationRemarks",
						final_obs_place "finalObservationPlace",
						final_obs_dt "finalObservationDate",
						final_obs_by "finalObservationBy",
						final_obs_by_nm "finalObservationByName",
						rank_cd "rankCode",
						PGP_SYM_DECRYPT(medi_catg::bytea, current_setting(''encrypt.key'')) "medicalCategory",
						PGP_SYM_DECRYPT(curr_medi_catg::bytea, current_setting(''encrypt.key'')) "currentMedicalCategory" ,
						created_by "createdBy",
						created_dt "createdDate",
						modified_by "modifiedBy",
						modified_dt "modifiedDate",
						crea_by_nm "createdByName",
						crea_unit_cd "createdByUnitCode",
						crea_unit_nm "createdByUnitName",
						medi_catg_wef "medicalCategoryWithEffectFrom",
						PGP_SYM_DECRYPT(diagnosis::bytea, current_setting(''encrypt.key'')) "diagnosis",
						PGP_SYM_DECRYPT(duration_of_lmc::bytea, current_setting(''encrypt.key'')) "durationOfLowMedicalCategory",
						s_shape_factor "sShapeFactor",
						h_shape_factor "hShapeFactor",
						a_shape_factor "aShapeFactor",
						p_shape_factor "pShapeFactor",
						e_shape_factor "eShapeFactor",
						PGP_SYM_DECRYPT(type_of_lmc::bytea, current_setting(''encrypt.key'')) "typeOfLowMedicalCategory",
						PGP_SYM_DECRYPT(lmc::bytea, current_setting(''encrypt.key'')) "lowMedicalCategory"
					from inayu.inv_final_obs_dtl
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
				)row)';
				
			v_block_cnt := v_block_cnt + 1;
	--end if; --if vin_ip_param ->> 'vin_final_obs' is not null then
	
	--if vin_ip_param ->> 'vin_app_dtl' = '1' then
		if v_block_cnt > 0 then
			v_block_query := v_block_query|| ' ,';
		end if;
		raise notice ' app ';	
		v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "approverDetails"
				from(select sr_no "serialNumber",
						approve_by "approvedBy",
						approve_by_nm "approvedByName",
						app_unit_cd "approverUnitCode",
						app_unit_nm "approverUnitName",
						app_rank_cd "rankCode",
						approve_dt "approvedByDate",
						approve_place "approvedByPlace",
						PGP_SYM_DECRYPT(approve_remarks::bytea, current_setting(''encrypt.key'')) "approvedByRemarks",
						created_by "createdBy",
						created_dt "createdDate",
						modified_by "modifiedBy",
						modified_dt "modifiedDate",
						app_designation "approverDesignation"
					from inayu.inv_approve_dtl
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
				)row)';
				
			v_block_cnt := v_block_cnt + 1;
	--end if; --if vin_ip_param ->> 'vin_app_dtl' is not null
	
	--if vin_ip_param ->> 'vin_vaccine_dtl' = '1' then
		if v_block_cnt > 0 then
			v_block_query := v_block_query|| ' ,';
		end if;
		raise notice ' vacc ';	
		v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "vaccineDetails"
				from(select sr_no "serialNumber",
						vaccine_type "vaccineType",
						vaccine_dtl "vaccineDetail",
						batch_no "batchNumber",
						vaccine_dt "vaccineDate",
						next_due_dt "nextDueDate",
						created_by "createdBy",
						created_dt "createdDate",
						modified_by "modifiedBy",
						modified_dt "modifiedDate",
						inv_by "investigationBy",
						inv_dt "investigationByDate"
					from inayu.inv_vaccine_dtl
					where app_sr_no = '||QUOTE_NULLABLE(v_app_sr_no)||'::integer
				)row)';
				
			v_block_cnt := v_block_cnt + 1;
	--end if; --if vin_ip_param ->> 'vin_vaccine_dtl' = '1' then
	
	if v_block_cnt > 0 then
			v_block_query := v_block_query|| ' ,';
	end if;
	v_block_query:= v_block_query|| '(select inv_type from inayu.per_specialisation_lnk
										where pno = '||QUOTE_NULLABLE(vin_ip_param ->>'vin_login_pno')||'
										and isactive = 1)  "accessControl"';
	
	
	else --if coalesce((vin_ip_param ->> 'edossier'),0)<> 1 then
		v_block_query:= v_block_query|| '(select json_agg(row_to_json(row)) "finalObservationEdossier"
				from(select fe.sr_no "serialNumber",
						fe.final_obs_remarks "finalObservationRemarks",
						fe.final_obs_place "finalObservationPlace",
						fe.final_obs_dt "finalObservationDate",
						fe.final_obs_by "finalObservationBy",
						fe.final_obs_by_nm "finalObservationByName",
						fe.rank_cd "rankCode",
						fe.medi_catg "medicalCategory",
						fe.curr_medi_catg "currentMedicalCategory" ,
						fe.created_by "createdBy",
						fe.created_dt "createdDate",
						fe.modified_by "modifiedBy",
						fe.modified_dt "modifiedDate",
						fe.crea_by_nm "createdByName",
						fe.crea_unit_cd "createdByUnitCode",
						fe.crea_unit_nm "createdByUnitName",
						fe.medi_catg_wef "medicalCategoryWithEffectFrom",
						fe.diagnosis "diagnosis",
						fe.duration_of_lmc "durationOfLowMedicalCategory",
						fe.s_shape_factor "sShapeFactor",
						fe.h_shape_factor "hShapeFactor",
						fe.a_shape_factor "aShapeFactor",
						fe.p_shape_factor "pShapeFactor",
						fe.e_shape_factor "eShapeFactor",
						fe.type_of_lmc "typeOfLowMedicalCategory",
						fe.lmc "lowMedicalCategory",
						fe.doc_path "documentPath",
    					fe.doc_nm "documentName",
    					fe.doc_file "documentFile",
						ad.app_no "appointmentNumber",
						ad.app_type "appointmentType",
						fe.exam_date "examinationDate"
					from inayu.inv_final_obs_4_edossier fe
					inner join inayu.appointment_dtl ad on fe.app_sr_no = ad.sr_no
					where fe.pno = '||QUOTE_NULLABLE(vin_ip_param ->> 'vin_pno')||'::text
				)row)';
	end if;
	
	v_final_query :=  v_final_query||v_block_query|| ') a';
	
	raise notice 'v_final_query : %',v_final_query;
	execute v_final_query into vin_out_param;

EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN proc_per_investigation_dtl: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.proc_per_investigation_dtl(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_per_investigation_dtl(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.proc_per_investigation_dtl(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.proc_per_investigation_dtl(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_per_investigation_dtl(json) TO epps_readonly;

