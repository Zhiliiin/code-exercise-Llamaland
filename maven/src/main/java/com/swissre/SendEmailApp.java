package com.swissre;

import java.util.List;

public class SendEmailApp
{
    /**
     * Please use following command to run:
     *
     * mvn exec:java -Dexec.mainClass="com.swissre.SendEmailApp" -Dexec.args="arg1 arg2"
     *
     * arg1 = full citizen list file, absolute path
     * arg2 = opt out email list file, absolute path
     */

    public static void main( String[] args )
    {
        String citizensFullFilePath = args[0];
        String citizensOptOutFile = args[1];

        SendEmailService sendEmailService = new SendEmailService();
        List<Citizen> finalCitizenListToSendEmail = sendEmailService.createFinalEmailToSend( citizensFullFilePath, citizensOptOutFile );

        System.out.println( finalCitizenListToSendEmail );
    }
}
