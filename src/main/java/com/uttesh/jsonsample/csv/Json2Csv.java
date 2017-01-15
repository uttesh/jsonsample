/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uttesh.jsonsample.csv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author uttesh
 */
public class Json2Csv {

    public static void main(String[] args) throws IOException {

        String sample = "["
                + "{\"name\":\"test1\",\"email\":\"test1@gmail.com\",\"id\":1},"
                + "{\"name\":\"test2\",\"email\":\"test2@gmail.com\",\"id\":2}"
                + "]";

        ObjectMapper mapper = new ObjectMapper();
        List<User> users = new ArrayList<User>();

        // convert JSON string to List
        users = mapper.readValue(sample, new TypeReference<List<User>>() {
        });

        for (User user : users) {
            System.out.println("name:" + user.getName());
            System.out.println("email:" + user.getEmail());
            System.out.println("id:" + user.getId());
        }

        write2CSV(users);
    }

    public static void write2CSV(List<User> users) {
        String NEW_LINE_SEPARATOR = "\n";
        //CSV file header
        Object[] FILE_HEADER = {"name", "email", "id"};

        FileWriter fileWriter = null;

        CSVPrinter csvFilePrinter = null;

        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        try {

            //initialize FileWriter object
            fileWriter = new FileWriter("sample_user.csv");

            //initialize CSVPrinter object 
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            //Create CSV file header
            csvFilePrinter.printRecord(FILE_HEADER);

            for (User user : users) {
                List userDataRecord = new ArrayList();
                userDataRecord.add(user.getName());
                userDataRecord.add(user.getEmail());
                userDataRecord.add(String.valueOf(user.getId()));
                csvFilePrinter.printRecord(userDataRecord);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
            }
        }
    }
}
