package com.example.until;

public class SystemFuncs {

    public static boolean isWindows(){
        String osstr = System.getProperty("os.name");
        if(osstr.contains("indows")) {
            return true;
        }
        return false;
    }

}
