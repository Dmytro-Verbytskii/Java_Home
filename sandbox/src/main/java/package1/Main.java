package package1;

//import package2.ExampleInpackage2;
import package2.*;

public class Main extends Parent{
    public static void main(String[] args) {
        ExampleInPackage1 ex1 = new ExampleInPackage1();
        // System.out.println(ex1.name1); // private
        System.out.println(ex1.name2); // package
        System.out.println(ex1.name3); // protected
        System.out.println(ex1.name4); // public

        ExampleInpackage2 ex2 = new ExampleInpackage2();
        // System.out.println(ex2.name1); // private
        // System.out.println(ex2.name2); // package
        // System.out.println(ex2.name3); // protected
        System.out.println(ex2.name4); // public

        Main p = new Main();
//        System.out.println(p.name1); // private
//        System.out.println(p.name2); // package
        System.out.println(p.name3); // protected
        System.out.println(p.name4); // public
    }
}
