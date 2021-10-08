package com.swissre;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PrepareEmailService {

    private String citizensFullFilePath;
    private String citizensOptOutFile;
    private List<Citizen> citizens = new ArrayList<>();
    private List<String> optOutEmails = new ArrayList<>();

    // define static variables
    public static final int RECEIVE_EMAIL_AGE = 100;
    public static final int NUMBER_OF_WEEKDAYS_AHEAD_STANDARD = 7;
    public static final int NUMBER_OF_WEEKDAYS_AHEAD_EXTENDED = 14;
    public static final int THRESHOLD_NUMBER_OF_CITIZENS = 20;

    List<Citizen> readCitizensFullFile(String citizensFullFilePath) {

        List<String> lines = readInputFile( citizensFullFilePath );
        for (String line : lines ) {
            citizens.add( mapStringToCitizen(line));
        }
        return citizens;
    }

    List<String> readCitizensOptOutFile(String citizensOptOutFilePath) {

        List<String> lines = readInputFile( citizensOptOutFilePath );
        optOutEmails.addAll( lines );
        return optOutEmails;
    }

    List<String> findDuplicatedEmails(List<Citizen> citizens) {

        HashMap<String, Integer> emailMap = new HashMap<>();
        HashSet<String> duplicatedEmailSet = new HashSet<>();
        List<String> duplicatedEmailList = new ArrayList<>();

        for (Citizen citizen : citizens) {
            if (emailMap.containsKey( citizen.getEmail() )) {
                duplicatedEmailSet.add( citizen.getEmail() );
            } else {
                emailMap.put( citizen.getEmail(),  1);
            }
        }

        duplicatedEmailList.addAll(duplicatedEmailSet);
        return duplicatedEmailList;

    }

    List<Citizen> filterCitizensByEmails(List<String> emailFilterList, List<Citizen> citizens) {
        for (String emailFilter : emailFilterList ) {
            removeCitizenByEmail( emailFilter, citizens );
        }
        return citizens;
    }

    List<Citizen> createCitizenListToSendEmail(List<Citizen> citizens) {

        List<Citizen> citizenListToSendEmail = findCitizensByAgeForNextDays(NUMBER_OF_WEEKDAYS_AHEAD_STANDARD, citizens);
        List<Citizen> extendedCitizenListToSendEmail = findCitizensByAgeForNextDays(NUMBER_OF_WEEKDAYS_AHEAD_EXTENDED, citizens);

        if (extendedCitizenListToSendEmail.size() > THRESHOLD_NUMBER_OF_CITIZENS) {
            citizenListToSendEmail.addAll( extendedCitizenListToSendEmail );
        }
        return citizenListToSendEmail;

    }


    /**
     * Utility functions
     */


    private List<String> readInputFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                lines.add(line.replaceAll("\\s+",""));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private Citizen mapStringToCitizen(String line) {

        Citizen citizen = new Citizen();
        String[] parts = line.split(",");
        citizen.setLastName(parts[0]);
        citizen.setFirstName(parts[1]);
        citizen.setBirthday( mapStringToDate(parts[2]));
        citizen.setEmail(parts[3]);
        return citizen;

    }

    private LocalDate mapStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(dateString, formatter);
    }

    private void removeCitizenByEmail(String emailFilter, List<Citizen> citizens) {
        Iterator<Citizen> itr = citizens.iterator();
        while (itr.hasNext()){
            if (itr.next().getEmail().equals( emailFilter )) {
                itr.remove();
            }
        }
    }

    private List<Citizen> findCitizensByAgeForNextDays(int daysBefore, List<Citizen> citizens) {
        List<Citizen> citizenListToSendEmail = new ArrayList<>();
        for (Citizen citizen : citizens) {
            if (isReceiveEmailAgeWithinDays( daysBefore , citizen.getBirthday() )) {
                citizenListToSendEmail.add( citizen );
            }
        }
        return citizenListToSendEmail;
    }

    private boolean isReceiveEmailAgeWithinDays(int days, LocalDate birthDate) {
        return LocalDate.now().plusDays( days ).compareTo(  birthDate.plusYears( RECEIVE_EMAIL_AGE ) ) == 0;
    }

}
