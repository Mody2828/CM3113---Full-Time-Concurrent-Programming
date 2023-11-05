/*
 * Illustrates use of nested classes, which are defined "inside" the scope of an Outer class
 * and 
 */
package lab01;

/**
 *
 * @author DAVID
 */
public class Lab01 {

    public static void main(String[] args) {
        /* Use of (static) nested classes allows local thread classes to be used without creating separate files */
        Thread t1 = new MyThread1();
        t1.start();
       
        /* Use of inner classes allows local thread classes to be used without creating separate files,  
         * but syntax for instantiating one is messy since we have to  */
        Lab01 lab01 = new Lab01();
        Lab01.MyThread2 t2 = lab01.new MyThread2();
        t2.start(); 
        
        /* Use of local classes allows convenient local thread classes to be used without creating separate files */
        Thread t3 = new MyThread3();
        t3.start();
    }
    
    /* static nested classes can be used by main method with "normal" syntax
     * Can be accessed in Lab01 as MyThead1
     * Can be accessed in rest of package as Lab01.MyThread1*/
    static class MyThread1 extends Thread{
        @Override public void run(){
            for(int i=0; i<=1000; i++) System.out.println("1 at " + i);
        }
    }
    
    /* non-static nested classes are called Inner classes and 
     * require an instance of the Outer class to exist to access one */
    class MyThread2 extends Thread{
        @Override public void run(){
            for(int i=0; i<=1000; i++) System.out.println("2 at " + i);
        }
    } 
} // end of class Lab01.java



/* class files can contain multple classes, but only one can be public
 * Additional classes, such as this one will have package-level scope
 * (so in this case other classes in package lab01 can use them). 
 * But good-practice would be to use nested classes instead  */
class MyThread3 extends Thread {
    @Override
    public void run() {
        for(int i=0; i<=1000; i++) System.out.println("3 at " + i);
    }
}

