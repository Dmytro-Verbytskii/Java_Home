package package2;

public class Parent {
    private String name1 = "private"; // only in current class {...}
    String name2 = "package"; // only in current package (package1)
    protected String name3 = "protected"; // in current package and inheritable classes
    public String name4 = "public"; // anywhere
}
