update epps_admin.epps_role_tree_dtl
set active_yn = 'N'
where role_cd = (select role_cd from epps_admin.epps_role_mst where role_disp_name = 'PMO');