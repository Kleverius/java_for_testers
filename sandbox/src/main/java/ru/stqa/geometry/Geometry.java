package ru.stqa.geometry;

import ru.stqa.geometry.figures.Triangle;

public class Geometry {
    public static void main(String[] args) {
        Triangle.printPerimeter(new Triangle(3.0, 4.0, 5.0));
        Triangle.printArea(new Triangle(3.0, 4.0, 5.0));
    }
}
