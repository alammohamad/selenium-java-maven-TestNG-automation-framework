package utils;

import java.io.*;
import java.util.*;

public class CSVUtils {

    public static Object[][] getCSVData(String path) throws IOException {

        List<Object[]> records = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {

            // Skip header row
            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] fields = line.split(",");

            records.add(new Object[]{
                fields[0],   // username
                fields[1],   // password
                fields[2]    // expected result
            });
        }

        br.close();

        return records.toArray(new Object[0][]);
    }
}
