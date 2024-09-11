-- FUNCTION: inayu.proc_insert_update_mst_data(json)

-- DROP FUNCTION inayu.proc_insert_update_mst_data(json);

CREATE OR REPLACE FUNCTION inayu.proc_insert_update_mst_data(
	vin_ip_param json,
	OUT vin_out_param json)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
/*created by: Uma@2-Dec-2021 : procedure to insert update master data
*/
v_operation text;
v_prog_id text;
i json;
v_error text :='';
j json;
begin
FOR j in SELECT * FROM json_array_elements(vin_ip_param) loop

	select j->>'prog_id',j->>'operation' into v_prog_id,v_operation;

	if v_prog_id ='listInayuUnitMiAmaLink' then

	raise notice '11';
		if upper(v_operation) = 'INSERT' then
			FOR i in SELECT * FROM json_array_elements(j->'vin_data') loop
				if not exists (select 1 from inayu.unit_mi_ama_lnk rp 
							   where unit_code = i->>'unit_code') then
					insert into inayu.unit_mi_ama_lnk
						(unit_code ,    ama_per_sr_no ,    ismi_room ,    created_by ,
						created_dt ,    machine_id ,	   is_naval_hosp ,ama_pno ,
						isactive ,	    mi_unit_code ,     nh_unit_code)
					values(i->>'unit_code',
							(i->>'ama_per_sr_no')::integer,
							(case when (i->>'mi_unit_code') is not null then 1 else 0 end)::smallint,
							i->>'created_by',
							now(),
							i->>'machine_id',
							(case when (i->>'nh_unit_code') is not null then 1 else 0 end)::smallint,
							i->>'ama_pno',
							(i->>'isactive')::smallint,
							i->>'mi_unit_code',
							i->>'nh_unit_code');
							
						
				else
					v_error := v_error ||'Unit ('||(i->>'unit_code')||') MI AMA link already exists. ';
				end if;
			end loop;

		elsif upper(v_operation) ='UPDATE' then
			FOR i in SELECT * FROM json_array_elements(j->'vin_data') loop

			raise notice '1';
				update inayu.unit_mi_ama_lnk r
				set ama_per_sr_no =coalesce((i->>'ama_per_sr_no')::integer,r.ama_per_sr_no),
				ismi_room =(case when coalesce((i->>'mi_unit_code'),r.mi_unit_code) is not null then 1 else 0 end),
				is_naval_hosp =(case when coalesce((i->>'nh_unit_code'),r.nh_unit_code) is not null then 1 else 0 end),
				ama_pno =coalesce(i->>'ama_pno',r.ama_pno),
				isactive =coalesce((i->>'isactive')::smallint,r.isactive),	    
				mi_unit_code =coalesce((i->>'mi_unit_code'),r.mi_unit_code),     
				nh_unit_code = coalesce(i->>'nh_unit_code',r.nh_unit_code),
				modified_by = i->>'updated_by',
				modified_dt = now()
				where r.unit_code = (i->>'unit_code');
				
				
			end loop;
		end if;

	end if;
end loop;
if v_error <> '' then
	select json_agg(row_to_json(row)) into vin_out_param 
	from (select v_error "message") row;  
else 
	select json_agg(row_to_json(row)) into vin_out_param 
	from (select 'Data saved successfully' "message") row;
end if;

EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN proc_insert_update_mst_data: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.proc_insert_update_mst_data(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_insert_update_mst_data(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.proc_insert_update_mst_data(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION inayu.proc_insert_update_mst_data(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_insert_update_mst_data(json) TO epps_readonly;

