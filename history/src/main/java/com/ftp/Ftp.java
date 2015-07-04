package com.ftp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by Santosh on 7/1/15.
 */
public class Ftp {

    private org.apache.commons.net.ftp.FTPClient  ftpClient =  new org.apache.commons.net.ftp.FTPClient();

    public String readRemoteFile(String server, String directory ,String fileName) {


        List<String> fileList = new ArrayList<>();

        try {

            ftpClient.connect(server);    //  "ftp://ftp.nasdaqtrader.com"
            ftpClient.login("anonymous", "anonymous");
            ftpClient.changeWorkingDirectory(directory);
            InputStream fileContent = new BufferedInputStream(ftpClient.retrieveFileStream(fileName));

            StringWriter writer = new StringWriter();
            IOUtils.copy(fileContent, writer);
            String fileContentAsString = writer.toString();


            fileContent.close();

            ftpClient.completePendingCommand();

            return fileContentAsString;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
