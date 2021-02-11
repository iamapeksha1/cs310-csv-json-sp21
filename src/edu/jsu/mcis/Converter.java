package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
            JSONObject jsonObject = new JSONObject();
            
            String[] headings = iterator.next();
            
            JSONArray colHeaders = new JSONArray();
            JSONArray rowHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            
            for( int i = 0; i < headings.length; i++ ) {
                colHeaders.add( headings[i] );
            }
           
            while( iterator.hasNext() ) 
            {
                
                String [] rows = iterator.next();
                JSONArray list = new JSONArray();
                rowHeaders.add( rows[0] );
                
                for( int i = 0; i < (rows.length) - 1 ; i++ ) {   
                   list.add(Integer.parseInt(rows[i+1])) ;
                   
                }
                
                data.add( list );  
                
            }
                                   
            jsonObject.put("colHeaders", colHeaders);
            jsonObject.put("rowHeaders", rowHeaders);
            jsonObject.put("data", data);
        
            results = JSONValue.toJSONString(jsonObject); 
            
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            
            JSONArray colHeaders = (JSONArray) jsonObject.get("colHeaders");
            JSONArray rowHeaders = (JSONArray) jsonObject.get("rowHeaders");
            JSONArray Data = (JSONArray) jsonObject.get("data");
            
            String[] heading = new String[colHeaders.size()];  
            
            for(int i = 0; i < colHeaders.size(); i++) 
            {
                heading[i] = (String) colHeaders.get(i);
            }
            
            csvWriter.writeNext(heading);
            
            for(int j = 0; j < Data.size(); j++)
            {
                
                JSONArray array = (JSONArray) Data.get(j);
                String[] rows = new String[array.size()+1];
                rows[0] = (String) rowHeaders.get(j);

                for(int k = 0; k < array.size(); k++) 
                {
                    rows[k+1] = Long.toString((long) array.get(k));
                }

                csvWriter.writeNext(rows);

            }

            results = writer.toString();
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}