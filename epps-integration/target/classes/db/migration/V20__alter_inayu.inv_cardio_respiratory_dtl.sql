do
$$
begin

if not exists (select 1 from information_schema.columns where column_name = 'creator_role_cd' and table_name = 'inv_cardio_respiratory_dtl') then
	alter table inayu.inv_cardio_respiratory_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_cns_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_dental_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_ent_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_eye_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_gis_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_gyn_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_inves_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_phy_capacity_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
	
	alter table inayu.inv_sergery_dtl
	add column creator_role_cd smallint,
	add column modifier_role_cd smallint;
end if;

end;
$$;