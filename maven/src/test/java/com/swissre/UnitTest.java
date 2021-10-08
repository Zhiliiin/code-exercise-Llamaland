package com.swissre;

import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Unit tests
 */
public class UnitTest {

    public UnitTest() {
    }

    // define test data
    final String FULL_FILE_PATH = new File("src/test/resources/testFullInputFile.txt").getAbsolutePath();
    final String OPT_OUT_FILE_PATH = new File("src/test/resources/testOptOutFile.txt").getAbsolutePath();
    final Citizen CITIZEN_BOBBY_BROWN = new Citizen("Brown","Bobby", LocalDate.of( 1950,11,10 ),"bobby.brown@ilovellamaland.com");
    final Citizen CITIZEN_TOM_FORD = new Citizen("Ford","Tom", LocalDate.of( 1921,10,15 ),"tom.ford@ilovellamaland.com");
    final String EMAIL_BETSY = "betsy@heyitsme.com";

    PrepareEmailService prepareEmailService = new PrepareEmailService();

    @Test
    public void testReadFullFile() {

        List<Citizen> citizens = prepareEmailService.readCitizensFullFile( FULL_FILE_PATH );

        assertEquals( CITIZEN_BOBBY_BROWN.toString(), citizens.get(0).toString());
    }

    @Test
    public void testReadOptOutFile() {

        List<String> optOutEmails = prepareEmailService.readCitizensOptOutFile( OPT_OUT_FILE_PATH );

        assertEquals( EMAIL_BETSY, optOutEmails.get(0));
    }

    @Test
    public void testFindDuplicatedEmails() {

        List<Citizen> citizens = prepareEmailService.readCitizensFullFile( FULL_FILE_PATH );

        List<String> duplicatedEmailList = prepareEmailService.findDuplicatedEmails( citizens );

        assertEquals( 1, duplicatedEmailList.size());
        assertEquals( EMAIL_BETSY, duplicatedEmailList.get(0));
    }

    @Test
    public void testFilterCitizensByEmails() {

        List<String> emailFilterList = new ArrayList<>();
        emailFilterList.add( EMAIL_BETSY );

        List<Citizen> citizens = prepareEmailService.readCitizensFullFile( FULL_FILE_PATH );

        List<Citizen> filteredCitizens = prepareEmailService.filterCitizensByEmails( emailFilterList, citizens );

        Set<String> emails = new HashSet<>();
        for (Citizen citizen : filteredCitizens ) {
            emails.add( citizen.getEmail() );
        }

        assertFalse( emails.contains( EMAIL_BETSY ) );

    }

    @Test
    public void testCreateFinalCitizenList() {

        List<Citizen> citizens = prepareEmailService.readCitizensFullFile( FULL_FILE_PATH );

        List<Citizen> finalCitizenListToSendEmail = prepareEmailService.createCitizenListToSendEmail( citizens );

        assertEquals( CITIZEN_TOM_FORD.toString(), finalCitizenListToSendEmail.get(0).toString());
    }


}


