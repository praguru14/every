do
$$
begin

IF NOT EXISTS(SELECT 1 FROM epps_admin.epps_prog_mst
				  WHERE comp_cd =1
				  AND prog_disp_name ='Personnel Portal'
				  and prog_id ='loadPersonnelPortal') THEN 
	
				insert into epps_admin.epps_prog_mst
				(comp_cd,prog_cd,prog_id,prog_disp_name,prog_long_name,
				prog_type,parent_id,module_id,--tran_indicator,
				prog_mtqr_flag,rep_type,menu_pass_parameter,--prog_report_name,
				prog_menu_display_yn,prog_disp_seq_no,prog_app_flag,default_access_flag,epps_admin_flag,
				mwa_flag,prog_developed_by,created_by,created_dt,terminal_id,active_yn,--rep_max_days,
				session_req_yn,approval_flag,creator_role_cd,prog_tree_del_flag,allow_excel_down_yn,noti_config_yn,
				java_notification_cls_name,rep_disp_name,multi_screen_yn,db_ct_type,dt_param_type,view_disp_name,
				view_entity_name,chart_type,report_key) --select * from epps_admin.epps_prog_mst
				values
				(
				1,
				(select max(prog_cd)+ 1 from epps_admin.epps_prog_mst),
				'loadPersonnelPortal','Personnel Portal','Personnel Portal',
				'F',null,'INAYU',--null,
				'T','N',null,--null,
				'Y',1,'S','N','N','W','P0000','P0000',now(),'UMA_BE',0,--null,
				'Y','N',1,null,'N','N',null,null,null,null,null,null,null,null,null);
				
				insert into epps_admin.epps_role_prog_lnk(comp_cd,div_cd,role_cd,prog_cd,created_by,created_dt,terminal_id,active_yn,insert_flag,update_flag,delete_flag,query_flag,creator_role_cd,default_yn)
				select comp_cd,div_cd,role_cd,(select prog_cd from epps_admin.epps_prog_mst where prog_id ='loadPersonnelPortal'),'P0000',now(),'uma',1,1,1,1,1,1,0
				from epps_admin.epps_role_mst
				where role_disp_name = 'INDIVIDUAL';

	end if;
	
end;
$$;