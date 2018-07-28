package ru.stqa.home.sandbox;

class HelloWorld {
    public static void main(String[] args) {
        hello("Jessi");

        Square s = new Square(4 );
        System.out.println("Square: "  + s.area());

        Rectangle r = new Rectangle(4,6);
        System.out.println("Rectangle: "  + r.area());

        Point p = new Point(-1, 3, 6, 2);
        System.out.println("Distance between A(" + p.x1 + ", " + p.x2 + ") and B(" + p.y1 + ", " + p.y2 + ") : " + p.distance());

        int i = 19;
        double d = 2.3;
        float c = 20;
        double k = i + d + c;
        //System.out.println("Sum result: " + k);
    }

    public static void hello(String somebody) {
        String hello = "Hello,";
        System.out.println(hello + " " + somebody + "!");
    }

}