package ru.atom.exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionHandler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionHandler.class);


    public static int testReturn(int i)
    {
        try {
            System.out.println("Block try " + i);
            return 1;
        }
        catch (Exception e)
        {
            System.out.println("Block cacth");
        }
        finally {
            System.out.println("Block finally " + (i-1));
            return 0;
        }
    }


    public static void simpleHandle() {
        try {
            // some statements here
            throw new SubException("problem in ExceptionHandler::simpleHandle");
        } catch (SubException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("Finally section");
        }
    }

    public static void exceptionThrower() throws SubException {
        try {
            Integer integer = null;
            // null pointer dereference
            integer.toString();
        } catch (NullPointerException npe) {
            System.out.println("fdlkgm");
            throw new SubException("Handled NPE");
        }
    }

    public static String multipleCatch(Throwable throwable) {
        try {
            throw throwable;
        } catch (BaseException baseEx) {
            return baseEx.getMessage();
        } catch (Exception ex) {
            return ex.getMessage();
        } catch (Throwable th) {
            return th.getMessage();
        }
    }

    public static String readOneLineFromFile(String filename) {
        File file = new File(filename);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            log.warn("FileNotFoundException in readOneLineFromFile, filename = {}", filename);
            return null;
        } catch (IOException e) {
            log.warn("IOException in readOneLineFromFile, filename = {}", filename);
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Try with resources.
     *
     * @See {@link java.lang.AutoCloseable}
     */
    public static String readOneLineFromFile_TheNewWay(String filename) {
        File file = new File(filename);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            log.warn("FileNotFoundException in readOneLineFromFile_TheNewWay, filename = {}", filename);
            return null;
        } catch (IOException e) {
            log.warn("IOException in readOneLineFromFile_TheNewWay, filename = {}", filename);
            return null;
        }
    }
}