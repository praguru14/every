-- FUNCTION: inayu.func_get_inv_status_due_dt(json)

-- DROP FUNCTION inayu.func_get_inv_status_due_dt(json);

CREATE OR REPLACE FUNCTION inayu.func_get_inv_status_due_dt(
	vin_ip_param json,
	OUT vin_out_param json)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
v_per_sr_no integer;
v_rank_code text;
v_app_year smallint;
v_app_no text; 
v_app_dt date;
v_app_status integer; 
v_app_stage    integer;
v_app_rank_cd text;
v_due_date date;
v_not_requested smallint;
begin

	select pm.sr_no, PGP_SYM_DECRYPT(pd.rank_code::bytea, current_setting('encrypt.key'))
	into /*v_age_yy,*/v_per_sr_no,v_rank_code
	from inmdb.per_mst pm
	inner join inmdb.per_dtls pd on pm.pno = pd.pno
	where pm.pno = vin_ip_param ->> 'vin_pno';
	
	select app_year, app_no, app_dt, app_status, app_stage, PGP_SYM_DECRYPT(ap.rank_cd::bytea, current_setting('encrypt.key'))
	into v_app_year, v_app_no, v_app_dt, v_app_status, v_app_stage,v_app_rank_cd
	from inayu.appointment_dtl ad
	left join inayu.app_per_dtl ap on ad.sr_no = ap.app_sr_no
	where ad.pno =vin_ip_param ->> 'vin_pno'
	and app_dt = (select max(app_dt)
							from inayu.appointment_dtl aa
							where aa.pno =vin_ip_param ->> 'vin_pno' );
	raise notice '% % % % % %',v_app_year, v_app_no, v_app_dt, v_app_status, v_app_stage,v_app_rank_cd;
	select coalesce(v_app_year,2021) into v_app_year;
	
	select (to_date(((v_app_year+1)::integer)::text||lpad(r.to_month::text,2,'0')||'01','yyyymmdd')+interval'1 months'-interval'1 day')::date 
	into v_due_date
	from inayu.rank_schedule_mst r
	where r.rank_code = v_rank_code;	
	raise notice ' %',v_due_date;
	if v_rank_code <> v_app_rank_cd and v_due_date is not null and v_app_rank_cd is not null then 
		if (v_due_date - v_app_dt ) > 365 then
			v_due_date := v_due_date - interval'1 year';
			v_not_requested  := 1;
		end if;
	end if;
	raise notice '% % %',v_rank_code,v_due_date,v_not_requested;
if v_due_date is not null then
	select json_agg(row_to_json(q)) into vin_out_param 
	from(select 
		 (select json_agg(row_to_json(row)) "appointmentStatus"
		 from(select (case when (date_part('year',now()) = date_part('year',v_due_date) or v_not_requested::integer = 1 ) or v_due_date < now()::date then 1 else 0 end) "notRequested",
			 	(case when (date_part('year',now()) <> date_part('year',v_due_date))  and v_app_status = 10 then 1 else 0 end) "requested",
			  	(case when (date_part('year',now()) <> date_part('year',v_due_date) ) and v_app_status = 30 then 1 else 0 end) "confirmation"
			 ) row),
		 (select json_agg(row_to_json(row)) "investigationStatus"
		 from(select (case when v_app_stage = 10 or (date_part('year',now()) = date_part('year',v_due_date) or v_not_requested::integer = 1 or v_due_date <= now()::date) then 1 else 0 end) "pending",
			 	(case when (date_part('year',now()) <> date_part('year',v_due_date) and v_due_date > now()::date) and v_app_stage = 20 then 1 else 0 end) "inProcess",
			  	(case when (date_part('year',now()) <> date_part('year',v_due_date) and v_due_date > now()::date ) and v_app_stage = 30 then 1 else 0 end) "submitted",
			  	(case when (date_part('year',now()) <> date_part('year',v_due_date) and v_due_date > now()::date) and v_app_stage = 40 then 1 else 0 end) "approved"
			 ) row),
		(case when (date_part('year',now()) <> date_part('year',v_due_date) and v_due_date > now()::date ) then v_app_no else null ::text end) "appointmentNumber",
		(case when (date_part('year',now()) <> date_part('year',v_due_date) and v_due_date > now()::date) then v_app_dt else null ::date end) "appointmentDate",
		 (v_due_date - now()::date) "nextDueDays"
		 )q;
else
	select json_agg(row_to_json(q)) into vin_out_param 
	from(select 
		 (select json_agg(row_to_json(row)) "appointmentStatus"
		 from(select 0 "notRequested",
			 	0 "requested",
			  	0 "confirmation"
			 ) row),
		 (select json_agg(row_to_json(row)) "investigationStatus"
		 from(select 0 "pending",
			 	0 "inProcess",
			  	0 "submitted",
			  	0 "approved"
			 ) row),
		 null  "appointmentNumber",
		 null  "appointmentDate",
		 null "nextDueDays"
		 )q;


end if;
		
		

EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN func_get_inv_status_due_dt: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.func_get_inv_status_due_dt(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.func_get_inv_status_due_dt(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.func_get_inv_status_due_dt(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.func_get_inv_status_due_dt(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.func_get_inv_status_due_dt(json) TO epps_readonly;
