-- FUNCTION: inayu.t_inv_cardio_respiratory_dtl_alog()

-- DROP FUNCTION inayu.t_inv_cardio_respiratory_dtl_alog();

CREATE OR REPLACE FUNCTION inayu.t_inv_cardio_respiratory_dtl_alog()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
DECLARE
/*
Created by  Uma@13-Dec-2021
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
	
	V_AUDIT_FLAG := 'Update';
	
END IF ;

if V_AUDIT_FLAG is not null then
		
	insert into inayu.inv_cardio_respiratory_dtl_alog
		( ref_sr_no ,    inv_stage ,    audit_flag ,    log_by ,    log_dt ,	log_by_role_cd ,    machine_id 	)
	values( new.app_sr_no::integer,
			new.inv_cr_stage::smallint,
			V_AUDIT_FLAG,
			(case when tg_op= 'INSERT' then new.created_by else new.modified_by end) ,
			(case when tg_op= 'INSERT' then new.created_dt else new.modified_dt end),
			(case when tg_op= 'INSERT' then new.creator_role_cd else new.modifier_role_cd end),
			new.machine_id::varchar
		);                  
	
end if; 
RETURN new;  

EXCEPTION
   WHEN OTHERS THEN
   RAISE EXCEPTION 'Problem in trigger t_inv_cardio_respiratory_dtl_alog while inserting in inayu.inv_cardio_respiratory_dtl_alog---> %',SQLERRM;
END;
$BODY$;

ALTER FUNCTION inayu.t_inv_cardio_respiratory_dtl_alog()
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.t_inv_cardio_respiratory_dtl_alog() TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.t_inv_cardio_respiratory_dtl_alog() TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.t_inv_cardio_respiratory_dtl_alog() TO epps_programmer;

