-- FUNCTION: inayu.proc_email_notification_dtl(json)

-- DROP FUNCTION inayu.proc_email_notification_dtl(json);

CREATE OR REPLACE FUNCTION inayu.proc_email_notification_dtl(
	vin_ip_param json,
	OUT vin_out_param json)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
/*created by: Uma@20-Oct-2021 : procedure to returns email details
*/
v_block_query text;
v_final_query text;
v_app_sr_no integer;
v_ama_pno text;
v_pno text;
v_out_param json;
v_ama_name text;
v_ama_rank text;
v_ama_unit text;
begin
	select sr_no, pno, ama_pno
	into v_app_sr_no, v_pno, v_ama_pno
	from inayu.appointment_dtl ad
	where ad.app_no = (vin_ip_param ->>'vin_app_no');
	
	select full_name, rank_code,unit_code into v_ama_name, v_ama_rank,v_ama_unit
	from inmdb.per_mst pm
	inner join inmdb.per_dtls pd
		on pm.pno = pd.pno
	where pm.pno = v_ama_pno;
	
	if v_app_sr_no is not null then
	select json_agg(row_to_json(row)) into v_out_param
	from (
			select pm.pno "personalNumber",
				PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting('encrypt.key')) "personalName",
				PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting('encrypt.key')) "recipientName",
				PGP_SYM_DECRYPT(pd.per_mob_no::bytea, current_setting('encrypt.key')) "contactNo",
				(vin_ip_param ->>'vin_app_no') "appointmentNumber",
				
				pd.nud_email_id "to",
				(select a.nud_email_id from inmdb.per_dtls a where a.pno = v_ama_pno) "from",
				null::text "cc",
				null::text "bcc",
		ac.mi_unit_name "miRoom",
		ac.mi_date "dateOfInvestigation",
		mi_exam_date "dateOfExamination",
		mi_inv_type "examinationDetails",
		ac.nh_unit_name "navalHospital",
		ac.nh_date "navalHospitalDateOfInvestigaton",
		nh_exam_date "navalHospitalDateOfExamination",
		nh_inv_type "navalHospitalExaminationDetails",
		ac.ext_hosp_name "externalHospital",
		ac.ext_hosp_date "externalHospitalDateOfInvestigaton",
		ext_hosp_exam_date "externalHospitalDateOfExamination",
		ext_hosp_inv_type "externalHospitalExaminationDetails",
		PGP_SYM_DECRYPT(v_ama_name::bytea, current_setting('encrypt.key')) "amaName",
		PGP_SYM_DECRYPT(v_ama_rank::bytea, current_setting('encrypt.key')) "amaRank",
		v_ama_unit "amaUnit",
		(select inv_prof_desc
						from inayu.inv_prof_mst ipm
						inner join inayu.agewise_inv_prof_dtl aip
						on ipm.name = aip.agewise_profile_chk
						where aip.age = extract('year' from age(now(),pm.dob))::smallint) "examinations",
				'Appointment confirmation -'|| (vin_ip_param ->>'vin_app_no') "subject",
				(select concat('Dear ',pm.full_name,' (',pm.pno,') \n ',pd.unit_code,' \n ',
					'Your appointment has been confirmed!',' \n ',
					'Refer to the details below:',' \n ',
					'Appointment Number:',(vin_ip_param ->>'vin_app_no'),' \n ',
				 	(case when mi_unit_code is not null then concat('MI ROOM: ',ac.mi_unit_name,' Date of investigation: ',coalesce(mi_date::text,''),
							' Date of examination: ',coalesce(mi_exam_date::text,''), ' Examination Details: ',array_to_string(mi_inv_type,','))
					else '' end),
					(case when nh_unit_code is not null then concat(' \n ','Naval Hospital: ',ac.nh_unit_name,' Date of investigation: ',coalesce(nh_date::text,''),
							' Date of examination: ',coalesce(nh_exam_date::text,''), ' Examination Details: ',array_to_string(nh_inv_type,','))
					else '' end),
				 	(case when ext_hosp_name is not null then concat(' \n ','External Hospital: ',ac.ext_hosp_name,' Date of investigation: ',coalesce(ext_hosp_date::text,''),
							' Date of examination: ',coalesce(ext_hosp_exam_date::text,''), ' Examination Details: ',array_to_string(ext_hosp_inv_type,','))
					else '' end),' \n ',
				 	'You will undergo the following examinations: ',' \n ',
				 	(select inv_prof_desc
						from inayu.inv_prof_mst ipm
						inner join inayu.agewise_inv_prof_dtl aip
						on ipm.name = aip.agewise_profile_chk
						where aip.age = extract('year' from age(now(),pm.dob))::smallint),' \n ',
				 	'Regards,',' \n ',
				 	v_ama_name,' \n ',
				 	'Rank: ',v_ama_rank,' Unit: ',v_ama_unit)
				 from inayu.app_confirm_dtl ac
				 where ac.app_sr_no = v_app_sr_no
				 ) "mailContent",				
				--'Hi Your Appointment has been generated....' "mailContent",
				null::text "attachemnts",
				null::text "mailAttachemnts",
				null::text "mimeBodyContents",
      			null::text "mimeBodyAttachemnt"
			from inmdb.per_mst pm
			inner join inmdb.per_dtls pd
				on pm.pno = pd.pno
			left join inayu.app_confirm_dtl ac
				on ac.pno = pm.pno
			where pm.pno = v_pno
			and ac.app_sr_no = v_app_sr_no 
		)row;
	end if;	
	if vin_ip_param ->>'vin_examination' ='1' then
		select json_agg(row_to_json(row)) into v_out_param
		from (
				select pm.pno "personalNumber",
				PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting('encrypt.key')) "personalName",
				PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting('encrypt.key')) "recipientName",
				(select r.name from inmdb.rank_mst r where r.code = PGP_SYM_DECRYPT(pd.rank_code::bytea, current_setting('encrypt.key'))) "personnelRank",
				(select u.name from inmdb.unit_mst u where u.code =pd.unit_code) "personnelUnit",
				ad.app_no "appointmentNumber",
				ac.mi_unit_name "miRoom",
				(select PGP_SYM_DECRYPT(an.full_name::bytea, current_setting('encrypt.key')) from inmdb.per_mst an where an.pno = ad.ama_pno) "amaName",
				(select u.name from inmdb.unit_mst u where u.code =apd.unit_code) "amaUnit",
				pd.nud_email_id "to",
				apd.nud_email_id  "from",
				null::text "cc",
				null::text "bcc",
				'Reminder for your upcoming medical examination' "subject",
				concat('Dear ',pm.full_name,' (',pm.pno,') \n ',
					   (select r.name from inmdb.rank_mst r where r.code = pd.rank_code),',',
					   (select u.name from inmdb.unit_mst u where u.code =pd.unit_code),' \n ',
					   'Gentle Reminder!',' \n ','This email is with reference to your appointment no. ',ad.app_no,'. \n ',
					   'Please visit the MI Room ',ac.mi_unit_name,' as per the schedule shared by your AMA.',' \n ', 
					   'Please contact your Authorized Medical Attendant (AMA) for other details.',' \n ', 
						'Regards, ',' \n ',
						(select an.full_name from inmdb.per_mst an where an.pno = ad.ama_pno),',',
					    (select u.name from inmdb.unit_mst u where u.code =apd.unit_code)) "mailContent",				
				
				null::text "attachemnts",
				null::text "mailAttachemnts",
				null::text "mimeBodyContents",
      			null::text "mimeBodyAttachemnt"
				from inayu.appointment_dtl ad
				inner join inmdb.per_mst pm on ad.pno = pm.pno
				inner join inmdb.per_dtls pd on ad.pno  = pd.pno
				inner join inmdb.per_dtls apd on ad.ama_pno  = apd.pno
				inner join inayu.app_confirm_dtl ac on ad.sr_no = ac.app_sr_no
				where ad.app_stage = 10
				and ad.app_status = 30
				and coalesce(ac.mi_exam_date,ac.nh_exam_date,ac.ext_hosp_exam_date) - now()::date between 0 and 5
			)row;
				
	end if;
	
	if vin_ip_param ->>'vin_examination_overdue' ='1' then
		select json_agg(row_to_json(row)) into v_out_param
		from (
				select pm.pno "personalNumber",
				PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting('encrypt.key')) "personalName",
				PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting('encrypt.key')) "recipientName",
				(select r.name from inmdb.rank_mst r where r.code = PGP_SYM_DECRYPT(pd.rank_code::bytea, current_setting('encrypt.key'))) "personnelRank",
				(select u.name from inmdb.unit_mst u where u.code =pd.unit_code) "personnelUnit",
				ad.app_no "appointmentNumber",
				coalesce(ac.mi_date,ac.nh_date,ac.ext_hosp_date) "dateOfInvestigation",
				coalesce(ac.mi_exam_date,ac.nh_exam_date,ac.ext_hosp_exam_date) "dateOfExamination",
				(select PGP_SYM_DECRYPT(an.full_name::bytea, current_setting('encrypt.key')) from inmdb.per_mst an where an.pno = ad.ama_pno) "amaName",
				 (select u.name from inmdb.unit_mst u where u.code =apd.unit_code) "amaUnit",
				pd.nud_email_id "to",
				apd.nud_email_id  "from",
				null::text "cc",
				null::text "bcc",
				'Reminder for your upcoming medical examination' "subject",
				concat('Dear ',pm.full_name,' (',pm.pno,') \n ',
					   (select r.name from inmdb.rank_mst r  where r.code = pd.rank_code),',',
					   (select u.name from inmdb.unit_mst u where u.code =pd.unit_code),' \n ',
					   'Gentle Reminder!',' \n ','This email is with reference to your appointment no. ',ad.app_no,'. \n ',
					   'Your appointment was confirmed for the date ',coalesce(ac.mi_date,ac.nh_date,ac.ext_hosp_date),
					   ' (date of investigation) for investigation and your medical examination was scheduled on ',
					   coalesce(ac.mi_exam_date,ac.nh_exam_date,ac.ext_hosp_exam_date),'. \n ', 
					   'It has been observed that you didn’t report to the MI Room to undergo the medical examination as per the schedule shared in the appointment confirmation email.',' \n ', 
						'Please contact your Authorized Medical Attendant (AMA) and undergo your medical examination at the earliest. ',' \n ',
					   'Regards, ',' \n ',
						(select an.full_name from inmdb.per_mst an where an.pno = ad.ama_pno),',',
					    (select u.name from inmdb.unit_mst u where u.code =apd.unit_code)) "mailContent",				
				
				null::text "attachemnts",
				null::text "mailAttachemnts",
				null::text "mimeBodyContents",
      			null::text "mimeBodyAttachemnt"
				from inayu.appointment_dtl ad
				inner join inmdb.per_mst pm on ad.pno = pm.pno
				inner join inmdb.per_dtls pd on ad.pno  = pd.pno
				inner join inmdb.per_dtls apd on ad.ama_pno  = apd.pno
				inner join inayu.app_confirm_dtl ac on ad.sr_no = ac.app_sr_no
				where ad.app_stage = 10
				and ad.app_status = 30
				and coalesce(ac.mi_exam_date,ac.nh_exam_date,ac.ext_hosp_exam_date) - now()::date < 0
			)row;
				
	end if;
	
	if vin_ip_param ->>'vin_request' ='1' then
		select json_agg(row_to_json(row)) into v_out_param
		from (
				select pm.pno "personalNumber",
				PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting('encrypt.key')) "personalName",
				PGP_SYM_DECRYPT(pm.full_name::bytea, current_setting('encrypt.key')) "recipientName",
				pd.nud_email_id "to",
				apd.nud_email_id  "from",
				null::text "cc",
				null::text "bcc",
				'Reminder to book appointment for AME/PME' "subject",
				"Hi" "mailContent",				
				
				null::text "attachemnts",
				null::text "mailAttachemnts",
				null::text "mimeBodyContents",
      			null::text "mimeBodyAttachemnt"
				from inmdb.per_mst pm 
				inner join inmdb.per_dtls pd on ad.pno  = pd.pno
				inner join inayu.unit_ama_mi_lnk ua on pd.unit_code = ua.unit_code
				inner join inmdb.per_dtls apd on ua.ama_pno  = apd.pno
				inner join inayu.rank_schedule_mst rm on pd.rank_code = rm.rank_code
				where (now()::date - to_date(to_char(now(),'yyyy')||lpad(rm.to_month::text,2,'0')||'01','yyyymmdd') ) <= 31
				--and not exists (select 1 from )
			)row;
				
	end if;
	vin_out_param := v_out_param;

EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN proc_email_notification_dtl: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.proc_email_notification_dtl(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_email_notification_dtl(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.proc_email_notification_dtl(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.proc_email_notification_dtl(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_email_notification_dtl(json) TO epps_readonly;

