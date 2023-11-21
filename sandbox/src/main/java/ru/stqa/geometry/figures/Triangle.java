package ru.stqa.geometry.figures;

import java.util.Objects;

public record Triangle(double sideA, double sideB, double sideC) {

    public Triangle {
        if (sideA < 0 || sideB < 0 || sideC < 0) {
            throw new IllegalArgumentException("Triangle side should be non-negative");
        }
        if ((sideA + sideB < sideC) || (sideB + sideC < sideA) || (sideA + sideC < sideB)) {
            throw new IllegalArgumentException("The sum of the two sides of the triangle must not be less than the third side");
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return (Double.compare(this.sideA, triangle.sideA) == 0 && Double.compare(this.sideB, triangle.sideB) == 0 && Double.compare(this.sideC, triangle.sideC) == 0)
                || (Double.compare(this.sideA, triangle.sideB) == 0 && Double.compare(this.sideB, triangle.sideC) == 0 && Double.compare(this.sideC, triangle.sideA) == 0)
                || (Double.compare(this.sideA, triangle.sideC) == 0 && Double.compare(this.sideB, triangle.sideA) == 0 && Double.compare(this.sideC, triangle.sideB) == 0)
                || (Double.compare(this.sideA, triangle.sideA) == 0 && Double.compare(this.sideB, triangle.sideC) == 0 && Double.compare(this.sideC, triangle.sideB) == 0)
                || (Double.compare(this.sideA, triangle.sideC) == 0 && Double.compare(this.sideB, triangle.sideB) == 0 && Double.compare(this.sideC, triangle.sideA) == 0)
                || (Double.compare(this.sideA, triangle.sideB) == 0 && Double.compare(this.sideB, triangle.sideA) == 0 && Double.compare(this.sideC, triangle.sideC) == 0);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
