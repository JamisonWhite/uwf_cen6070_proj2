/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Test case interface
 * 
 * @author jamie
 * @param <T>
 */
public interface TestCase<T>  {
    
    /**
     * Execute the test case
     * @return
     * @throws Exception 
     */
    public T Run() throws Exception;
}
