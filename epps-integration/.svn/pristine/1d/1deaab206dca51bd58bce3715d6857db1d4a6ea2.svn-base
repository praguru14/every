-- FUNCTION: inmdb.trg_ai_unit_mst()

-- DROP FUNCTION inmdb.trg_ai_unit_mst();

CREATE or replace FUNCTION inmdb.trg_ai_unit_mst()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
DECLARE
/*created by: uma@28-Oct-2021: Trigger to insert data in location mst
*/

begin
if TG_OP ='INSERT' then
	insert into epps_admin.epps_location_mst
		(comp_cd, 		div_cd, 		loc_cd, 
		 loc_disp_name,	loc_long_name,	add1,	city_cd, 	country_cd,	
		 state_cd, 		created_by, 	created_dt,			active_yn)
	values(1,1,coalesce((select max(loc_cd)+1 from epps_admin.epps_location_mst),1),
		   new.code, 		new.name,			'',		1,			1,
		   1,			'P0000',		now(),				'Y');
		
elsif TG_OP ='UPDATE' then
	update epps_admin.epps_location_mst
	set 		loc_long_name = new.name
	where loc_disp_name = new.code;

end if;
	
RETURN new;  

EXCEPTION
   WHEN OTHERS THEN
   RAISE EXCEPTION 'Problem in trigger trg_ai_unit_mst  %',SQLERRM;
END;
$BODY$;

ALTER FUNCTION inmdb.trg_ai_unit_mst()
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inmdb.trg_ai_unit_mst() TO epps_developer;

GRANT EXECUTE ON FUNCTION inmdb.trg_ai_unit_mst() TO PUBLIC;

GRANT EXECUTE ON FUNCTION inmdb.trg_ai_unit_mst() TO epps_programmer;

