-- FUNCTION: epps_admin.proc_insert_update_mst_data(json)

-- DROP FUNCTION epps_admin.proc_insert_update_mst_data(json);

CREATE OR REPLACE FUNCTION epps_admin.proc_insert_update_mst_data(
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
v_prog_cd integer;
begin

FOR j in SELECT * FROM json_array_elements(vin_ip_param) loop
	select j->>'prog_id',j->>'operation' into v_prog_id,v_operation;
raise notice 'in J loop';
	if v_prog_id ='listSysAdminRoleProgramLink' then
		if upper(v_operation) = 'INSERT' then
			FOR i in SELECT * FROM json_array_elements(j->'vin_data') loop
				v_prog_cd := null;
				select prog_cd  into v_prog_cd
				from epps_admin.epps_prog_mst
				where prog_id = (i->>'prog_id');
				
				if not exists (select 1 from epps_admin.epps_role_prog_lnk rp where role_cd = (i->>'role_cd')::smallint and prog_cd = v_prog_cd::integer) then
					insert into epps_admin.epps_role_prog_lnk
						(comp_cd,		div_cd,		role_cd,		prog_cd,		created_by,
						created_dt, 	terminal_id,active_yn,		insert_flag,	update_flag,
						query_flag,	creator_role_cd,default_yn)
					values(1::smallint,
							1::smallint,
							(i->>'role_cd')::smallint,
							v_prog_cd::integer,
							(i->>'created_by')::text,
							now(),
							null::text,
							(i->>'active_yn')::integer,
							(i->>'insert_flag')::integer,
							(i->>'update_flag')::integer,
							(i->>'query_flag')::integer,
							(i->>'creator_role_cd')::smallint,
							(i->>'default_yn')::smallint);
				else
					v_error := v_error ||'Role ('||(i->>'role_cd')||') and screen ('||(i->>'prog_cd')||') link already exists. ';
				end if;
			end loop;

		elsif upper(v_operation) ='UPDATE' then
			FOR i in SELECT * FROM json_array_elements(j->'vin_data') loop
			raise notice 'uma';
				update epps_admin.epps_role_prog_lnk r
				set active_yn = coalesce((i->>'active_yn')::integer,r.active_yn),		
				insert_flag = coalesce((i->>'insert_flag')::integer,r.insert_flag),	
				update_flag = coalesce((i->>'update_flag')::integer,r.update_flag),
				query_flag  = coalesce((i->>'active_yn')::integer,r.active_yn),
				updated_by = (i->>'updated_by'),
				updated_dt = now(),
				updator_role_cd = (i->>'updator_role_cd')::smallint
				where r.role_cd = (i->>'role_cd')::smallint
				and r.prog_cd = (select prog_cd 
				from epps_admin.epps_prog_mst
				where prog_id = (i->>'prog_id'));
			end loop;
		end if;
	elsif v_prog_id ='listAdminRoleEmployeeLink' then
		if upper(v_operation) = 'INSERT' then
			FOR i in SELECT * FROM json_array_elements(j->'vin_data') loop
				if not exists (select 1 from epps_admin.epps_emp_loc_lnk rp 
							   where role_cd = (i->>'role_cd')::smallint and loc_cd = (select loc_cd from epps_admin.epps_location_mst where loc_disp_name =(j->>'unit_code')::text) 
							   and emp_cd = (i->>'emp_cd')) then
					insert into epps_admin.epps_emp_loc_lnk
						(comp_cd,		div_cd,		role_cd,		loc_cd,			emp_cd,
						created_by,		created_dt, terminal_id,	active_yn,		creator_role_cd)
					values(1::smallint,
							1::smallint,
							(i->>'role_cd')::smallint,
							(select loc_cd from epps_admin.epps_location_mst where loc_disp_name =(j->>'unit_code')::text),
							(i->>'emp_cd'),
							(i->>'created_by'),
							now(),
							(i->>'terminal_id'),
							(case when (i->>'active_yn') in ('1','Y') then 'Y' else 'N'  end),
							(i->>'creator_role_cd')::smallint);
				else
					v_error := v_error ||'Role ('||(i->>'role_cd')||') and Personnel ('||(i->>'emp_cd')||') link already exists. ';
				end if;
			end loop;

		elsif upper(v_operation) ='UPDATE' then
			FOR i in SELECT * FROM json_array_elements(j->'vin_data') loop
				update epps_admin.epps_emp_loc_lnk r
				set active_yn = coalesce((case when (i->>'active_yn') in ('1','Y') then 'Y'
										  when (i->>'active_yn') in('0','N') then 'N' else null end),r.active_yn),
				updated_by = (i->>'updated_by'),
				updated_dt = now(),
				updator_role_cd = (i->>'updator_role_cd')::smallint
				where r.role_cd = (i->>'role_cd')::smallint
				and r.loc_cd = (select loc_cd from epps_admin.epps_location_mst where loc_disp_name =(j->>'unit_code')::text)
				and r.emp_cd = (i->>'emp_cd');
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
         RAISE EXCEPTION '% %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION epps_admin.proc_insert_update_mst_data(json)
    OWNER TO epps_programmer;

GRANT EXECUTE ON FUNCTION epps_admin.proc_insert_update_mst_data(json) TO epps_developer;

GRANT EXECUTE ON FUNCTION epps_admin.proc_insert_update_mst_data(json) TO PUBLIC;

GRANT EXECUTE ON FUNCTION epps_admin.proc_insert_update_mst_data(json) TO epps_programmer;

GRANT EXECUTE ON FUNCTION epps_admin.proc_insert_update_mst_data(json) TO epps_readonly;

