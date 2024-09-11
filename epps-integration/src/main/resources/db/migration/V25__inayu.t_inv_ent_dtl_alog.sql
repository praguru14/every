do
$$
begin

CREATE OR REPLACE FUNCTION inayu.t_inv_ent_dtl_alog()
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
		
	insert into inayu.inv_ent_dtl_alog
		( ref_sr_no ,    inv_stage ,    audit_flag ,    log_by ,    log_dt ,	log_by_role_cd ,    machine_id 	)
	values( new.app_sr_no::integer,
			new.inv_stage::smallint,
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
   RAISE EXCEPTION 'Problem in trigger t_inv_ent_dtl_alog while inserting in inayu.inv_ent_dtl_alog---> %',SQLERRM;
END;
$BODY$;

ALTER FUNCTION inayu.t_inv_ent_dtl_alog()
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.t_inv_ent_dtl_alog() TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.t_inv_ent_dtl_alog() TO epps_programmer;

if not exists (select 1 from information_schema.triggers 
			   where trigger_schema ='inayu' and trigger_name = 'trg_inv_ent_dtl_alog') then
CREATE TRIGGER  trg_inv_ent_dtl_alog
    AFTER INSERT OR UPDATE 
    ON inayu.inv_ent_dtl
    FOR EACH ROW
    EXECUTE PROCEDURE inayu.t_inv_ent_dtl_alog();
end if;

end;
$$;