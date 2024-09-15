CREATE OR REPLACE FUNCTION hrcdf_staging.func_personal_detail(session_id integer)
 RETURNS void
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
/*
Created by : Akshay K
Purpose    : Insert personal details from hrcdf staging
date       : 16/05/2023
Call:
update hrcdf_staging.personal_details set stage = 1 , stage_remarks = null

select * from hrcdf_staging.func_personal_detail(123);

select * from hrcdf_staging.personal_details where pno = '242042Y'

select * from inmdb.per_mst
select * from inmdb.per_dtls where pno = ''

ALTER TABLE hrcdf_staging.personal_details ADD doj date NULL;
ALTER TABLE hrcdf_staging.personal_details ADD date_comm date NULL;
ALTER TABLE hrcdf_staging.personal_details ADD date_seniority date NULL;

*/
declare

        personal_detail cursor for
                select * from hrcdf_staging.personal_details
                where stage = 1 and pno  not like 'N%' and pno not in(select pno from inmdb.per_mst);

        verror_flag  smallint;
        verror_remarks text;
        v_psr_no integer;
        v_rank_code character varying;
        v_branch_code character varying;
        v_cadre_code character varying;
        v_unit_code character varying;
        v_cmnd_code character varying;
        v_gender_code character varying;
        v_entry_code character varying;
        v_cnt integer := 1;
        v_srno integer;

begin

        for pd in personal_detail loop

        verror_flag := 0;
        verror_remarks := '';
        v_psr_no :=0;
        v_rank_code := '';
        v_branch_code := '';
        v_cadre_code := '';
        v_unit_code := null;
        v_cmnd_code := '';


--if not exists (select * from inmdb.per_mst a where a.pno = pd.pno) then
        v_cnt = v_cnt +1;
raise notice 'AAA % %',v_cnt, pd.pno;
        --------------------------------------Rank--------------------------------------------------
        select code into v_rank_code
        from inmdb.rank_mst
        where code = pd.rank_code ;

        if v_rank_code is null then
        verror_flag := 1;
        verror_remarks := concat(verror_remarks,' Rank Code ',pd.rank_code,',');
        end if;

        --------------------------------------------Branch-------------------------------------------
        select code into v_branch_code
        from inmdb.branch_mst
        where code = pd.branch_code;

        if v_branch_code is null then
        verror_flag := 1;
        verror_remarks := concat(verror_remarks,' Branch Code ',pd.branch_code,',');
        end if;

        -----------------------------------------------Cadre--------------------------------------------
        select code  into v_cadre_code
        from inmdb.cadre_mst
        where code = pd.cadre_code and branch_code = v_branch_code;

        if v_cadre_code is null then
        verror_flag := 1;
        verror_remarks := concat(verror_remarks,' Cadre Code ',pd.cadre_code,',');
        end if;

        --------------------------------------------Unit-----------------------------------------------
        SELECT distinct hrcdf_unit_code   into v_unit_code
        FROM public.npo_unit_mst a
        inner join inmdb.unit_mst b on a.hrcdf_unit_code = b.code
        where a.npo_unit_code = pd.unit_code::integer
        and pd.unit_code::integer <> 0
        and a.sr_no = (select max(sr_no) from public.npo_unit_mst b where b.npo_unit_code = pd.unit_code::integer) ;

        if v_unit_code is null or v_unit_code = '' then
        verror_flag := 1;
        verror_remarks := concat(verror_remarks,' Unit Code ',pd.unit_code,',');
        end if;

        --------------------------------------------command-----------------------------------------------
        select code  into v_cmnd_code
        from inmdb.command_mst
        where code = pd.cmnd_code ;

        if v_cmnd_code is null then
        verror_flag := 1;
        verror_remarks := concat(verror_remarks,' Command Code ',pd.cmnd_code,',');
        end if;

        --------------------------------------------gender-----------------------------------------------
        select code into v_gender_code
        from inmdb.gender_mst
        where code = pd.emp_gender ;

        if v_cmnd_code is null then
        verror_flag := 1;
        verror_remarks := concat(verror_remarks,' Gender Code ',pd.emp_gender,',');
        end if;

        --------------------------------------------entry_type-----------------------------------------------
        select code into v_entry_code
        from inmdb.entry_mst
        where code = pd.entry_type ;

        if v_cmnd_code is null then
        verror_flag := 1;
        verror_remarks := concat(verror_remarks,' Entry Code ',pd.entry_type,',');
        end if;
        -----------------------------------------------------------------------------------------------

        if not exists (select 1 from inmdb.per_mst a where a.pno = pd.pno ) then

                insert into inmdb.per_mst (pno,first_name,mid_name,last_name,emp_gender,dob,placeofbirth,user_type_code,
                                          full_name,isactive,created_by,created_dt,machine_id,source_app)
                values(pd.pno,
                                PGP_SYM_ENCRYPT(coalesce(pd.first_name,'')::text,current_setting('encrypt.key')),
                                PGP_SYM_ENCRYPT(pd.mid_name::text,current_setting('encrypt.key')),
                                PGP_SYM_ENCRYPT(pd.last_name::text,current_setting('encrypt.key')),
                                (case when pd.user_type_code = 'SAIL' then  'M' else coalesce(v_gender_code,'NA') end),
                                pd.dob::date,        pd.placeofbirth,        pd.user_type_code,
                                   PGP_SYM_ENCRYPT(pd.full_name,current_setting('encrypt.key')),
                                   1,'P0000',now(),null::character varying,(case when pd.user_type_code = 'OFFIC' then 'DOP' else 'CABS' end)        );

                select sr_no into v_psr_no from inmdb.per_mst where pno = pd.pno;

                if exists(select 1 from inmdb.per_mst where pno = pd.pno ) then

                        insert into inmdb.per_dtls (
                                        psr_no,                                pno,                                blood_group_code,        pan_no,                                voter_id,                        religion_code,
                                        nationality_code,        marital_status,                off_mob_no,                        per_mob_no,                        unit_code,
                                        cmnd_code,                        guv_email_id,                nud_email_id,                pers_email_id,                doj,                                rank_code,
                                        date_comm,                        date_seniority,                cadre_code,                        isactive,                        created_by,                        created_dt,
                                        branch_code,                entery_type,                medi_code ,        source_app)
                        values (v_psr_no,                        pd.pno,                                pd.blood_group_code,
                                        PGP_SYM_ENCRYPT(null::character varying,current_setting('encrypt.key')), --pan_no
                                        PGP_SYM_ENCRYPT(null::character varying,current_setting('encrypt.key')), --voter_id
                                        coalesce(pd.religion_code,'NA'),                        'I',
                                        coalesce(pd.marital_status,'NA'),
                                        PGP_SYM_ENCRYPT(pd.off_mob_no,current_setting('encrypt.key')),
                                        PGP_SYM_ENCRYPT(pd.per_mob_no,current_setting('encrypt.key')),
                                        coalesce(v_unit_code,'NA'),                         coalesce(v_cmnd_code,'NA'),
                                        PGP_SYM_ENCRYPT(pd.guv_email_id,current_setting('encrypt.key')),
                                        PGP_SYM_ENCRYPT(concat(pd.pno,'@hq.indiannavy.mil'),current_setting('encrypt.key')),
                                        PGP_SYM_ENCRYPT(pd.pers_email_id,current_setting('encrypt.key')),
                                        pd.doj::date,
                                        PGP_SYM_ENCRYPT(coalesce(v_rank_code,'NA'),current_setting('encrypt.key')),
                                        pd.date_comm::date,
                                        pd.date_seniority::date,
                                        coalesce(v_cadre_code,'NA'), 1,                'P0000',                now(),                 coalesce(v_branch_code,'NA'),
                                coalesce(v_entry_code,'NA'),
                                PGP_SYM_ENCRYPT(pd.medi_code::character varying,current_setting('encrypt.key')),
                                (case when pd.user_type_code = 'OFFIC' then 'DOP' else 'CABS' end)        )        ;
                        end if;

                else if not exists(select 1 from inayu.inayu.appointment_dtl ad
                                                where pno = pd.pno
                                                and date_part('year',app_dt) = date_part('year',now())
                                                and app_stage > 40
                                                ) then

                        insert into inmdb.per_mst (pno,first_name,mid_name,last_name,emp_gender,dob,placeofbirth,user_type_code,
                                          full_name,isactive,created_by,created_dt,machine_id,source_app)
                values(pd.pno,
                                PGP_SYM_ENCRYPT(coalesce(pd.first_name,'')::text,current_setting('encrypt.key')),
                                PGP_SYM_ENCRYPT(pd.mid_name::text,current_setting('encrypt.key')),
                                PGP_SYM_ENCRYPT(pd.last_name::text,current_setting('encrypt.key')),
                                (case when pd.user_type_code = 'SAIL' then  'M' else coalesce(v_gender_code,'NA') end),
                                pd.dob::date,        pd.placeofbirth,        pd.user_type_code,
                                   PGP_SYM_ENCRYPT(pd.full_name,current_setting('encrypt.key')),
                                   1,'P0000',now(),null::character varying,(case when pd.user_type_code = 'OFFIC' then 'DOP' else 'CABS' end)        )
                        on conflict on constraint pno_uniq
                        do update set first_name = excluded.first_name,                        mid_name = excluded.mid_name,                        last_name = excluded.last_name,
                                      emp_gender = excluded.emp_gender,                                dob = excluded.dob,                                                full_name = excluded.full_name,
                                      modified_dt = now()
                    where PGP_SYM_DECRYPT(inmdb.per_mst.first_name::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.first_name::bytea,current_setting('encrypt.key')) or
                             PGP_SYM_DECRYPT(inmdb.per_mst.mid_name::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.mid_name::bytea,current_setting('encrypt.key')) or
                             PGP_SYM_DECRYPT(inmdb.per_mst.last_name::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.last_name::bytea,current_setting('encrypt.key')) or
                             inmdb.per_mst.emp_gender <> excluded.emp_gender or
                             inmdb.per_mst.dob <> excluded.dob or
                              PGP_SYM_DECRYPT(inmdb.per_mst.full_name::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.full_name::bytea,current_setting('encrypt.key'))
                               ;

                select sr_no into v_psr_no from inmdb.per_mst where pno = pd.pno;

                if exists(select 1 from inmdb.per_mst where pno = pd.pno ) then

                        insert into inmdb.per_dtls (
                                        psr_no,                                pno,                                blood_group_code,        aadhar_no,                        pan_no,                                voter_id,                        religion_code,
                                        nationality_code,        marital_status,                date_marriage,                off_mob_no,                        per_mob_no,                        unit_code,
                                        cmnd_code,                        guv_email_id,                nud_email_id,                pers_email_id,                doj,                                rank_code,
                                        date_comm,                        date_seniority,                cadre_code,                        isactive,                        created_by,                        created_dt,
                                        branch_code,                entery_type,                medi_code ,                        date_retire,                                         source_app)
                        values (v_psr_no,                        pd.pno,                                pd.blood_group_code,                        null::character varying,
                                        PGP_SYM_ENCRYPT(null::character varying,current_setting('encrypt.key')),
                                        PGP_SYM_ENCRYPT(null::character varying,current_setting('encrypt.key')),
                                        coalesce(pd.religion_code,'NA'),                        'I',
                                        coalesce(pd.marital_status,'NA'),                null::date,
                                        PGP_SYM_ENCRYPT(pd.off_mob_no,current_setting('encrypt.key')),
                                        PGP_SYM_ENCRYPT(pd.per_mob_no,current_setting('encrypt.key')),
                                        coalesce(v_unit_code,'NA'),
                                        coalesce(v_cmnd_code,'NA'),
                                        PGP_SYM_ENCRYPT(pd.guv_email_id,current_setting('encrypt.key')),
                                        PGP_SYM_ENCRYPT(concat(pd.pno,'@hq.indiannavy.mil'),current_setting('encrypt.key')),
                                        PGP_SYM_ENCRYPT(pd.pers_email_id,current_setting('encrypt.key')),
                                        pd.doj::date,
                                        PGP_SYM_ENCRYPT(coalesce(v_rank_code,'NA'),current_setting('encrypt.key')),
                                        pd.date_comm::date,
                                        pd.date_seniority::date,
                                        coalesce(v_cadre_code,'NA'), 1,                'P0000',                now(),
                                        coalesce(v_branch_code,'NA'),
                                coalesce(v_entry_code,'NA'),
                                PGP_SYM_ENCRYPT(pd.medi_code::character varying,current_setting('encrypt.key')),
                                pd.date_retire::date,
                                (case when pd.user_type_code = 'OFFIC' then 'DOP' else 'CABS' end)        )
                on conflict on constraint per_dtls_pno_ukey
                do update set
                        religion_code = excluded.religion_code,                        marital_status = excluded.marital_status,
                        off_mob_no = excluded.off_mob_no,                                per_mob_no = excluded.per_mob_no,                                        /*unit_code = excluded.unit_code,        */
                        cmnd_code = excluded.cmnd_code,                                        guv_email_id = excluded.guv_email_id,                                pers_email_id = excluded.pers_email_id,
                        doj = excluded.doj::date,                                                rank_code = excluded.rank_code,                                                date_comm = excluded.date_comm::date,
                        date_seniority= excluded.date_seniority::date,        cadre_code = excluded.cadre_code,                                        branch_code = excluded.branch_code,
                        date_retire = excluded.date_retire::date,                modified_dt = now()
          where inmdb.per_dtls.religion_code <> excluded.religion_code or
                          inmdb.per_dtls.marital_status <> excluded.marital_status or
                          PGP_SYM_DECRYPT(inmdb.per_dtls.off_mob_no::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.off_mob_no::bytea,current_setting('encrypt.key')) or
                          PGP_SYM_DECRYPT(inmdb.per_dtls.per_mob_no::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.per_mob_no::bytea,current_setting('encrypt.key')) or
                          PGP_SYM_DECRYPT(inmdb.per_dtls.guv_email_id::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.guv_email_id::bytea,current_setting('encrypt.key')) or
                          PGP_SYM_DECRYPT(inmdb.per_dtls.pers_email_id::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.pers_email_id::bytea,current_setting('encrypt.key')) or
                        /*inmdb.per_dtls.unit_code <> excluded.unit_code or */
                        inmdb.per_dtls.cmnd_code <> excluded.cmnd_code or
                        inmdb.per_dtls.doj <> excluded.doj::date or
                        PGP_SYM_DECRYPT(inmdb.per_dtls.rank_code::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.rank_code::bytea,current_setting('encrypt.key')) or
                        inmdb.per_dtls.date_comm <> excluded.date_comm::date or
                        inmdb.per_dtls.date_seniority<> excluded.date_seniority::date or
                        inmdb.per_dtls.cadre_code <> excluded.cadre_code or
                        inmdb.per_dtls.branch_code <> excluded.branch_code or
                        inmdb.per_dtls.date_retire <> excluded.date_retire::date or
                        PGP_SYM_DECRYPT(inmdb.per_dtls.medi_code::bytea,current_setting('encrypt.key')) <> PGP_SYM_DECRYPT(excluded.medi_code::bytea,current_setting('encrypt.key'))
                        ;

                        end if;

                end if;

        end if;



        update hrcdf_staging.personal_details a
                set stage_remarks = 'Sync Successfully',
                        stage = 6
                where a.pno = pd.pno;

        if verror_flag = 1 then

                update hrcdf_staging.personal_details a
                set stage_remarks = concat('Please validate the data',verror_remarks),
                          stage = 3
                where a.pno = pd.pno;

        end if;

end loop;

--        update inmdb.per_dtls a
--                set date_retire = to_date(b.date_retire,'YYYY/MM/DD')
--        from hrcdf_staging.personal_details b
--        where a.pno = b.pno and a.date_retire <> to_date(b.date_retire,'YYYY/MM/DD');
--
--
--        update inmdb.per_dtls a
--                set isactive = 0
--        from hrcdf_staging.personal_details b
--        where a.pno = b.pno
--        and to_date(b.date_retire,'YYYY/MM/DD')::date < now()::date;
--
--        update inmdb.per_mst a
--                set isactive = 0
--        from hrcdf_staging.personal_details b
--        where a.pno = b.pno
--        and to_date(b.date_retire,'YYYY/MM/DD')::date < now()::date;
--------------------------------------------------------
--        update inmdb.per_dtls a
--                set isactive = 1
--        from hrcdf_staging.personal_details b
--        where a.pno = b.pno
--        and to_date(b.date_retire,'YYYY/MM/DD')::date > now()::date;
--
--        update inmdb.per_mst a
--                set isactive = 1
--        from hrcdf_staging.personal_details b
--        where a.pno = b.pno
--        and to_date(b.date_retire,'YYYY/MM/DD')::date > now()::date;
--


EXCEPTION
   WHEN OTHERS THEN
         RAISE EXCEPTION '-20099 ERROR IN func_personal_detail: % %', SQLERRM,SQLSTATE;

end;
$function$
;