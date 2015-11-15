
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jwhite
 */
public class TestDriver {

    /**
     * Execute all class tests
     *
     * @param args
     */
    public static void main(String[] args) throws IllegalArgumentException {

        Config.MemorizeSeconds = 1;

        args = new String[]{};
        TestDriver.classTest();
        GameGrid.main(args);
        GameLoop.main(args);
        TextGameDriver.main(args);
        MultiConcentration.classTest();
        //GUI driver will close the window and exit the application so order matters.
        GuiGameDriver.main(args);
    }


    /**
     * Print test case status and expected and actuals on failure.
     *
     * @param <T>
     * @param testCase
     * @param description
     * @param expected
     * @param actual
     */
    public static <T> void printTestCase(String testCase, String description, T expected, T actual) {
        if (expected.equals(actual)) {
            System.out.println(String.format("%s passed. %s", testCase, description));
        } else {
            System.out.println(String.format("%s FAILED. %s Expected: %s Actual: %s  ", testCase, description, expected, actual));
        }
    }

    /**
     * Run and print test case.
     * @param <T>
     * @param testCase
     * @param description
     * @param expected
     * @param test 
     */
    public static <T> void runTestCase(String testCase, String description, T expected, TestCase<T> test) {
        T actual;
        try {
            actual = test.Run();
        } catch (Exception ex) {            
            System.out.println(String.format("%s FAILED. %s Expected: %s Exception: %s  ", testCase, description, expected, ex.getMessage()));
            return;
        }        
        if (expected.equals(actual)) {
            System.out.println(String.format("%s passed. %s", testCase, description));
        } else {
            System.out.println(String.format("%s FAILED. %s Expected: %s Actual: %s  ", testCase, description, expected, actual));
        }
    }

    /**
     * print test case and if it threw an exception
     *
     * @param testCase
     * @param description
     * @param test
     */
    public static void printTestCaseThrowsException(String testCase, String description, TestCase test) {
        try {
            test.Run();
            System.out.println(String.format("%s FAILED. %s Expected: Exception Actual: none  ", testCase, description));
        } catch (Exception ex) {
            System.out.println(String.format("%s passed. %s Expected Exception: %s", testCase, description, ex.getMessage()));
        }
    }
    
    
    /**
     * Class test for testing methods
     */
    public static void classTest() {
        printTestCase("TD001", "TestDriver. Code coverage test.", true, true);
        printTestCase("TD002", "TestDriver. Code coverage test. (NOT A REAL FAILURE)", true, false);
        printTestCaseThrowsException("TD003", "TestDriver. Code coverage test.", new TestCase() {
            @Override
            public Boolean Run() throws Exception {
                throw new Exception("Test worked");
            }
        });
        printTestCaseThrowsException("TD004", "TestDriver. Code coverage test. (NOT A REAL FAILURE)", new TestCase() {
            @Override
            public Boolean Run() throws Exception {
                return false; //where's the error
            }
        });
        
        runTestCase("TD005", "TestDriver. Code coverage test.", true, new TestCase<Boolean>() {
            @Override
            public Boolean Run() throws Exception {
                return true;
            }
        });

        runTestCase("TD006", "TestDriver. Code coverage test. (NOT A REAL FAILURE)", true, new TestCase<Boolean>() {
            @Override
            public Boolean Run() throws Exception {
                return false;
            }
        });
        
        runTestCase("TD007", "TestDriver. Code coverage test. (NOT A REAL FAILURE)", true, new TestCase<Boolean>() {
            @Override
            public Boolean Run() throws Exception {
                throw new Exception("Test worked");
            }
        });

    }

}
