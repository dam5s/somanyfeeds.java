package com.somanyfeeds;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {
    public static String readInputStream(InputStream is) {
        Scanner s = new Scanner(is, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
