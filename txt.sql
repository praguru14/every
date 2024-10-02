-- Extract and process pastMedicalHistory from VIN_IP_PARAM JSON
IF CAST(VIN_IP_PARAM::JSON->>'pastMedicalHistory' AS character varying) IS NOT NULL THEN
    v_past_medical_hist := CAST(VIN_IP_PARAM::JSON->>'pastMedicalHistory' AS character varying);

    -- Update inayu.app_per_dtl table
    IF EXISTS (SELECT * FROM inayu.app_per_dtl a WHERE a.pno = v_pno) THEN
        UPDATE inayu.app_per_dtl 
        SET past_medical_hist = PGP_SYM_ENCRYPT(v_past_medical_hist::text, current_setting('encrypt.key'))
        WHERE pno = v_pno 
        AND app_sr_no = (SELECT max(sr_no) 
                         FROM inayu.appointment_dtl c
                         WHERE c.pno = v_pno
                         AND date_part('year', now()) = date_part('year', c.app_dt));
    END IF;

    -- Update inmdb.per_dtls table
    UPDATE inmdb.per_dtls
    SET past_medical_hist = PGP_SYM_ENCRYPT(v_past_medical_hist::text, current_setting('encrypt.key'))
    WHERE pno = v_pno;
END IF;
