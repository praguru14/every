do
$$
begin

if not exists (select 1 from information_schema.columns where column_name = 'log_by_role_cd' and table_name = 'inv_cardio_respiratory_dtl_alog') then
	alter table inayu.inv_cardio_respiratory_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_cns_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_dental_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_ent_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_eye_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_gis_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_gyn_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_inves_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_phy_capacity_dtl_alog
	add column log_by_role_cd smallint;
	
	alter table inayu.inv_sergery_dtl_alog
	add column log_by_role_cd smallint;
end if;

end;
$$;