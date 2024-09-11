-- FUNCTION: inayu.t_appointment_dtl_alog()

-- DROP FUNCTION inayu.t_appointment_dtl_alog();

CREATE or replace FUNCTION inayu.t_appointment_dtl_alog()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
DECLARE
/*
Created by  Uma@10-Dec-2021
Purpose     Generate Audit trail log for transactions.
            Done only on header tables xxxxxxx_alog 
                      
*/
v_audit_flag      text;
	
BEGIN

 
  -------------------------------
 IF (TG_OP = 'INSERT') THEN
 	V_AUDIT_FLAG := 'Insert';
 END IF;
 ------------------------------

 IF (TG_OP = 'UPDATE') THEN
	
	if NEW.app_stage = OLD.app_stage and new.app_stage = 20 then 
		V_AUDIT_FLAG := null;
	elsIF new.app_stage = 10 and new.app_status = 30 then
	  V_AUDIT_FLAG := 'Confirm';
	ELSIF   new.app_stage = 20 and old.app_stage = 10  THEN
	  V_AUDIT_FLAG := 'Start Investigation';
	ELSIF   new.app_stage = 30 and old.app_stage = 20  THEN
	  V_AUDIT_FLAG := 'Submit';
	ELSIF  new.app_stage = 40 and old.app_stage = 30  THEN 
	 V_AUDIT_FLAG := 'Peruse';
	ELSIF (new.app_stage = 30 and old.app_stage = 30) and (new.next_modifier_role <> old.next_modifier_role)  THEN
	  V_AUDIT_FLAG := 'Approve';
	ELSE
	  V_AUDIT_FLAG := 'OTH';
	END IF;
END IF ;

if V_AUDIT_FLAG is not null then
		
	insert into inayu.appointment_dtl_alog
		( ref_sr_no ,    inv_stage ,    audit_flag ,    log_by ,    log_dt ,	log_by_role_cd ,    machine_id ,    amd_no		)
	values( new.sr_no::integer,
			new.app_stage::smallint,
			V_AUDIT_FLAG,
			(case when tg_op= 'INSERT' then new.created_by else new.modified_by end) ,
			(case when tg_op= 'INSERT' then new.created_dt else new.modified_dt end),
			(case when tg_op= 'INSERT' then new.creator_role_cd else new.modifier_role_cd end),
			new.machine_id::varchar,
			new.amd_no
		);                  
	
end if; 
RETURN new;  

EXCEPTION
   WHEN OTHERS THEN
   RAISE EXCEPTION 'Problem in trigger t_appointment_dtl_alog while inserting in inayu.appointment_dtl_alog---> %',SQLERRM;
END;
$BODY$;

ALTER FUNCTION inayu.t_appointment_dtl_alog()
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.t_appointment_dtl_alog() TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.t_appointment_dtl_alog() TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.t_appointment_dtl_alog() TO epps_programmer;

DROP TRIGGER if exists trg_appointment_dtl_alog ON inayu.appointment_dtl;

CREATE TRIGGER  trg_appointment_dtl_alog
    AFTER INSERT OR UPDATE OF app_stage, app_status,next_modifier_role
    ON inayu.appointment_dtl
    FOR EACH ROW
    EXECUTE PROCEDURE inayu.t_appointment_dtl_alog();