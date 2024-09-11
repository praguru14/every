
do
$$
begin

if not exists (select   1 from information_schema.columns where table_name = 'unit_mi_ama_lnk' and  column_name = 'cmnd_code') then
alter table inayu.unit_mi_ama_lnk
add column cmnd_code text;

update inayu.unit_mi_ama_lnk ul
set cmnd_code = u.cmnd_code
from inmdb.unit_mst  u
where ul.unit_code =u.code;

end if;
end ;
$$;