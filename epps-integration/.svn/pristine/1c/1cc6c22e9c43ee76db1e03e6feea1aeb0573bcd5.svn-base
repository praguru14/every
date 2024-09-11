-- FUNCTION: inayu.trg_ai_inv_approve_dtl()

-- DROP FUNCTION inayu.trg_ai_inv_approve_dtl();

CREATE OR REPLACE FUNCTION inayu.trg_ai_inv_approve_dtl()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
DECLARE
/*created by: uma@4-Oct-2021: Update next_modifier_role/app_stage on Approval 
							--updaing inv_submit_dt
*/
v_next_modifier_role smallint;
v_old_next_modifier_role smallint;
v_approver json;
v_app_no text;
begin

	select app_no,next_modifier_role
	into v_app_no,v_old_next_modifier_role
	from inayu.appointment_dtl ad
	where ad.sr_no = new.app_sr_no;
	
	select inayu.func_get_trans_approver((select row_to_json(row) from (select v_app_no as "vin_app_no") row)::json )
	into v_approver;

	select (v_role ->> 'nextUpdatorRoleCode')::smallint into v_next_modifier_role
	from json_array_elements(v_approver) v_role ;
	raise notice 'v_next_modifier_role % %',v_next_modifier_role, v_approver;
	
	if v_next_modifier_role <> 0 then
		update inayu.appointment_dtl ad
		set next_modifier_role = v_next_modifier_role
		where ad.sr_no = new.app_sr_no;
	else
		update inayu.appointment_dtl ad
		set app_stage = 40
		where ad.sr_no = new.app_sr_no;
	end if;
	
	if (select role_disp_name 
		from epps_admin.epps_role_mst 
		where role_cd = new.approver_role_cd) in ('PMO','MEMBER MEDICAL BOARD') then 
		
		update inayu.app_per_dtl ap
		set inv_submit_dt = now()::date 
		where ap.app_sr_no = new.app_sr_no;
	end if;
		
RETURN new;  

EXCEPTION
   WHEN OTHERS THEN
   RAISE EXCEPTION 'Problem in trigger trg_ai_inv_approve_dtl  %',SQLERRM;
END;
$BODY$;

ALTER FUNCTION inayu.trg_ai_inv_approve_dtl()
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.trg_ai_inv_approve_dtl() TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.trg_ai_inv_approve_dtl() TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.trg_ai_inv_approve_dtl() TO epps_programmer;

