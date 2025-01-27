import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    private final String name = "TestName";
    private double speed;
    private double distance;

    @Test
    public void constructor_NullNameParam_throwsIllegalArgumentException(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1,2));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\n\n","\t","\t\t","\t \t","\r"})
    public void constructor_EmptyNameParam_throwsIllegalArgumentException(String name){
        String expectedMessage = "Name cannot be blank.";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1,2));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_NegativeSpeedParamPassed_throwsIllegalArgumentException(){
        String expectedMessage = "Speed cannot be negative.";
        speed = -3;
        distance = 2;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed,distance));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_NegativeDistanceParamPassed_throwsIllegalArgumentException(){
        String expectedMessage = "Distance cannot be negative.";
        speed = 1;
        distance = -2;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getName_ReturnsCorrectName() {
        speed = 1;
        distance = 2;
        Horse horse = new Horse(name, speed, distance);

        String actualName = horse.getName();

        assertEquals(name, actualName);
    }

    @Test
    void getSpeed_ReturnsCorrectSpeed() {
        speed = 1;
        distance = 2;
        Horse horse = new Horse(name, speed, distance);

        double actualSpeed = horse.getSpeed();

        assertEquals(speed, actualSpeed);
    }

    @Test
    void getDistance_ReturnsCorrectDistance() {
        speed = 1;
        distance = 2;
        Horse horse = new Horse(name, speed, distance);

        double actualDistance = horse.getDistance();

        assertEquals(distance, actualDistance);
    }

    @Test
    void move_CallsGetRandomDoubleMethodWithCorrectParam() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("TestName", 1,2);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2,0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4, 0.8, 10, 15, 128})
    public void move_UsedFormulaIsCorrect(double fakeRandomValue){
        double min = 0.2;
        double max = 0.9;
        speed = 2.5;
        distance = 250;
        Horse horse = new Horse(name,speed,distance);
        double expectedDistance = distance + speed * fakeRandomValue;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(()-> Horse.getRandomDouble(min,max)).thenReturn(fakeRandomValue);
            horse.move();
        }

        assertEquals(expectedDistance, horse.getDistance());

    }
}