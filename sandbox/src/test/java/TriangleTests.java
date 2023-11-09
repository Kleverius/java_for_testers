import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.geometry.figures.Triangle;

public class TriangleTests {
    @Test
    void canCalculatePerimeter() {
        Assertions.assertEquals(12.0, new Triangle(3.0, 4.0, 5.0).getPerimeter());
    }

    @Test
    void canCalculateArea() {
        var s = new Triangle(3.0, 4.0, 5.0);
        double result = s.getArea();
        Assertions.assertEquals(6.0, result);
    }
}
