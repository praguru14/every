do
$$
begin

if not exists(select 1 from information_schema.columns
			 where column_name ='apply_before_date'
			 and table_name ='appointment_dtl') then
			 
			 alter table inayu.appointment_dtl
			 add column apply_before_date date;
	end if;
	
	if not exists(select 1 from information_schema.columns
			 where column_name ='per_preferred_date'
			 and table_name ='appointment_dtl') then
			 
			 alter table inayu.appointment_dtl
			 add column per_preferred_date text;
	end if;
	
	if not exists(select 1 from information_schema.columns
			 where column_name ='app_year'
			 and table_name ='appointment_dtl') then
			 
			 alter table inayu.appointment_dtl
			 add column app_year smallint;
	end if;
	
end;
$$;