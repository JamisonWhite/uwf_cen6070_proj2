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
     * @param args 
     */
    public static void main(String[] args) {
        
        args = new String[] {"-test","4"};
        MultiConcentration.main(args);
        MultiConcentration.classTest();
        
        GameGrid.main(args);
        GameLoop.main(args);
        TestGameDriver.main(args);
        TextGameDriver.main(args);
        GuiGameDriver.main(args);
        
        printTestCase("TC000", "TestDriver. Code coverage test for positive test case.", true, true);
        printTestCase("TC000", "TestDriver. Code coverage test for negative test case. (This is not really a failure.)", true, false);
        
        assert args.length > 0;
    }
    
    /**
     * Print test case status and expected and actuals on failure.
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
            System.out.println(String.format("%s FAILED. %s Expceted: %s Actual: %s  ", testCase, description, expected, actual));
        }
    }

}
