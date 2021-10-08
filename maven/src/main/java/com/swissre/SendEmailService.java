package com.swissre;

import java.util.List;

public class SendEmailService {

    public List<Citizen> createFinalEmailToSend  (String citizensFullFilePath, String citizensOptOutFile) {

        PrepareEmailService prepareEmailService = new PrepareEmailService();
        List<Citizen> citizens = prepareEmailService.readCitizensFullFile( citizensFullFilePath );
        List<String> optOutEmails = prepareEmailService.readCitizensOptOutFile( citizensOptOutFile );
        List<String> duplicatedEmailList = prepareEmailService.findDuplicatedEmails( citizens );

        List<Citizen> deduplicatedCitizens = prepareEmailService.filterCitizensByEmails( duplicatedEmailList, citizens );
        List<Citizen> filteredCitizens = prepareEmailService.filterCitizensByEmails( optOutEmails, deduplicatedCitizens );

        List<Citizen> finalCitizenListToSendEmail = prepareEmailService.createCitizenListToSendEmail( filteredCitizens );

        return finalCitizenListToSendEmail;

    }
}
