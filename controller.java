package com.epps.module.staging.hrcdf.interfaces.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epps.module.master.personnel.application.services.query.IHrcdfPersonnelMaster;
import com.epps.module.master.personnel.interfaces.dtos.PersonnelDetail;
import com.epps.module.staging.hrcdf.application.command.service.ICDFStagingCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableScheduling
@EnableAsync
@Controller
@RequestMapping("/hrcdf/staging/")
@Tag(name = "HRCDF STAGING API", description = "HRCDF STAGING API")
public class CDFStagingController {

    private static final Logger logger = LoggerFactory.getLogger(CDFStagingController.class);

    @Autowired
    private ICDFStagingCommandService cdfStagingCommandServiceImpl;

    @Autowired
    private IHrcdfPersonnelMaster hrcdfPersonnelMasterImpl;

    @Async
    @Scheduled(cron = "${cron.execution.hrcdf}")
    public void integrateHRCDFStagingData() throws JsonProcessingException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        logger.info("Cron job started at: {}", new java.util.Date());

        try {
            Integer sessionId = new Random().nextInt(1202);
            callHRCDFStagingDeleteProcedure(sessionId);
            savePersonnelDetails();
            callHRCDFStagingProcedure(sessionId);
            logger.info("Cron job completed at: {}", new java.util.Date());
        } catch (Exception e) {
            logger.error("Error occurred during cron job execution: {}", e.getMessage(), e);
        }
    }

    @PostMapping("v1")
    public void integrateHRCDFStagingDataViaPost() throws JsonProcessingException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        integrateHRCDFStagingData();
    }

    private void callHRCDFStagingProcedure(Integer sessionId) {
        cdfStagingCommandServiceImpl.callHRCDFStagingProcedure(sessionId);
    }

    private void callHRCDFStagingDeleteProcedure(Integer sessionId) {
        cdfStagingCommandServiceImpl.callHRCDFStagingDeleteProcedure(sessionId);
    }

    private void savePersonnelDetails() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        List<String> userTypes = Arrays.asList("OFFIC", "SAIL");
        List<PersonnelDetail> list = hrcdfPersonnelMasterImpl.getPersonnelInfoFromHrcdf(null, null, 1, null, null, userTypes, null, null);
        cdfStagingCommandServiceImpl.saveDopStagingPersonnelReleaseData(list);
    }
}
------------------------------------------------------------------------------------------------------------------------------------
    import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class CDFStagingCommandServiceImpl implements ICDFStagingCommandService {

    private static final Logger logger = LoggerFactory.getLogger(CDFStagingCommandServiceImpl.class);
    
    @Autowired
    private IHrcdfPersonnelMaster hrcdfPersonnelMasterImpl;

    @Autowired
    private ICDFStagingCommandService cdfStagingCommandServiceImpl;

    private static final List<String> USER_TYPES = Arrays.asList("OFFIC", "SAIL");

    private void savePersonnelDetails() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        try {
            for (String userType : USER_TYPES) {
                // Fetch personnel details for each user type
                List<PersonnelDetail> list = hrcdfPersonnelMasterImpl.getPersonnelInfoFromHrcdf(
                    null, null, 1, null, null, Arrays.asList(userType), null, null
                );

                // Check if the list is not empty before saving
                if (list != null && !list.isEmpty()) {
                    cdfStagingCommandServiceImpl.saveDopStagingPersonnelReleaseData(list);
                    logger.info("Successfully saved personnel details for user type: {}", userType);
                } else {
                    logger.info("No personnel details found for user type: {}", userType);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred while saving personnel details: {}", e.getMessage(), e);
            // Optionally rethrow or handle the exception
        }
    }
}
----------------------------------------------------------------------------------------------------------------
    cron:
  execution:
    hrcdf: "0 19 * * 1,3,5"
----------------------------------------------------------------------------------------------------------------------
