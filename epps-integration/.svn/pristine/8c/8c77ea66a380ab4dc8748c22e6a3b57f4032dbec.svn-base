-- FUNCTION: inayu.proc_dropdown_filter(character varying, character varying[], character varying[], character varying[], character varying, character varying, character varying)

-- DROP FUNCTION inayu.proc_dropdown_filter(character varying, character varying[], character varying[], character varying[], character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION inayu.proc_dropdown_filter(
	vin_report character varying,
	vin_user_type character varying[],
	vin_ama_pno character varying[],
	vin_command_code character varying[],
	vin_prog_id character varying,
	vin_pno character varying,
	vin_role_code character varying,
	OUT vin_out_param json)
    RETURNS json
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    
AS $BODY$
Declare
/*

	
select * from inayu.proc_dropdown_filter('RANK' :: character varying,null :: character varying[],Null :: character varying[],Null :: character varying[],Null :: character varying);
select * from inayu.proc_dropdown_filter('UNIT' :: character varying,null :: character varying[],Null :: character varying[],Null :: character varying[],Null :: character varying);

	
*/
begin
		
		
		
		
		if vin_report = 'RANK' then
			
			select json_agg(z) into vin_out_param
			 from
			(select
			(select json_agg(row_to_json(d))
			 from
			(select code"rankCode",name"rankName"
			from inmdb.rank_mst
			where isactive = 1
			and case when vin_user_type::character varying[] is null then true
				else (case when source_app = 'DOP' then 'OFFIC' = any(vin_user_type::character varying[]) 
					       when source_app = 'CABS' then 'SAIL' = any(vin_user_type::character varying[]) 
					       when source_app = 'NCIMS' then 'CIVIL' = any(vin_user_type::character varying[]) 
					  end)
			    end)d)"rank")z;

		end if;
		
		
		
		if vin_report = 'UNIT' then
			
			select json_agg(z) into vin_out_param
			 from
			(select
			(select json_agg(row_to_json(d))
			 from
			(select u.code"unitCode",u.name"unitName"
			from inmdb.unit_mst u
			 inner join inayu.unit_mi_ama_lnk ul on u.code = ul.unit_code
			where u.isactive = 1
			and case when vin_command_code::character varying[] is null then true
				else u.cmnd_code = any(vin_command_code::character varying[]) end
			and case when vin_ama_pno::character varying[] is null then true
				else ul.ama_pno = any(vin_ama_pno::character varying[]) end)d)"unit")z;

		end if;
		
		if vin_report = 'UNIT_AMA_LINKED' then
			
			select json_agg(z) into vin_out_param
			 from
			(select
			(select json_agg(row_to_json(d))
			 from
			(select u.code"unitCode",u.name"unitName",ul.sr_no "serialNo"
			from inmdb.unit_mst u
			 inner join inayu.unit_mi_ama_lnk ul on u.code = ul.unit_code
			where u.isactive = 1
			and case when vin_command_code::character varying[] is null then true
				else u.cmnd_code = any(vin_command_code::character varying[]) end
			and case when vin_ama_pno::character varying[] is null then true
				else ul.ama_pno = any(vin_ama_pno::character varying[]) end)d)"unit")z;

		end if;
		
		if vin_report = 'UNIT_NOT_AMA_LINKED' then
			
			select json_agg(z) into vin_out_param
			 from
			(select
			(select json_agg(row_to_json(d))
			 from
			(select u.code"unitCode",u.name"unitName"
			from inmdb.unit_mst u
			where u.isactive = 1
			 and not exists(select 1 from inayu.unit_mi_ama_lnk ul
						   where u.code = ul.unit_code)
			and case when vin_command_code::character varying[] is null then true
				else u.cmnd_code = any(vin_command_code::character varying[]) end)d)"unit")z;

		end if;
		
		if vin_report = 'CHARTTYPE' then
			
			select json_agg(z) into vin_out_param
			 from
			(select
			(select json_agg(row_to_json(d))
			 from
			(select prog_id"progID",key"key",sub_key"sub_key",
			 sum_chart"sumChart",chart_type"chartType",chart_name"chartName",name"displayName"
			from epps_admin.epps_dashboard_dropdown
			where active_yn = 1
			and case when vin_prog_id::character varying is null then true
				else prog_id = vin_prog_id::character varying end)d)"charts")z;

		end if;
		
		if vin_report = 'DASHBOARD_FILTER_DROPDOWN' then
		
			select json_agg(u) into vin_out_param
			from
			(select a.base_name"baseName",json_agg(row_to_json((select ColumnName from (select a.filter_name"filterName",a.active_yn"activeYn")as ColumnName)))as "data"
			from epps_admin.epps_dashbord_filter_display a
			 inner join epps_admin.epps_prog_mst b on a.base_name = upper(b.prog_disp_name) and b.active_yn = 1
			where case when vin_prog_id::character varying is null then true
					   else	b.prog_id = vin_prog_id::character varying end
			group by a.base_name)u;
		
		end if;
		
EXCEPTION 
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN proc_dropdown_filter: % %', SQLERRM,SQLSTATE;	 
	
end;
$BODY$;

ALTER FUNCTION inayu.proc_dropdown_filter(character varying, character varying[], character varying[], character varying[], character varying, character varying, character varying)
    OWNER TO epps_programmer;


GRANT EXECUTE ON FUNCTION inayu.proc_dropdown_filter(character varying, character varying[], character varying[], character varying[], character varying, character varying, character varying) TO epps_programmer;

GRANT EXECUTE ON FUNCTION inayu.proc_dropdown_filter(character varying, character varying[], character varying[], character varying[], character varying, character varying, character varying) TO epps_developer;

GRANT EXECUTE ON FUNCTION inayu.proc_dropdown_filter(character varying, character varying[], character varying[], character varying[], character varying, character varying, character varying) TO PUBLIC;

