package ru.stqa.geometry.figures;

public class Triangle {

    private double sideA, sideB, sideC;


    public Triangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public static void printArea(Triangle area) {
        System.out.println("Площадь треугольника со сторонами " + area.sideA + ", " + area.sideB + ", " + area.sideC + " = " + area.getArea());
    }


    public static void printPerimeter(Triangle perimeter) {
        System.out.println("Периметр треугольника со сторонами " + perimeter.sideA + ", " + perimeter.sideB + ", " + perimeter.sideC + " = " + perimeter.getPerimeter());
    }

    public double getArea() {
        var halfPerimeter = getPerimeter() / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - this.sideA) * (halfPerimeter - this.sideB) * (halfPerimeter - this.sideC));
    }

    public double getPerimeter() {
        return this.sideA + this.sideB + this.sideC;
    }
}
