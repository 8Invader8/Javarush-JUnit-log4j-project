import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    public void constructor_NullListParamPassed_throwsIllegalArgumentException(){
        String expectedMessage = "Horses cannot be null.";
        List<Horse> horses = null;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_EmptyListParamPassed_throwsIllegalArgumentException(){
        String expectedMessage = "Horses cannot be empty.";
        List<Horse> horses = new ArrayList<>();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getHorses_ReturnsListWithAllHorses() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            horses.add(new Horse("Horse" + i,i,i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertNotNull(hippodrome.getHorses());
        assertEquals(20, hippodrome.getHorses().size());
        assertEquals("Horse0", hippodrome.getHorses().get(0).getName());
        assertEquals("Horse10", hippodrome.getHorses().get(10).getName());
        assertEquals("Horse19", hippodrome.getHorses().get(19).getName());
    }

    @Test
    void move_CallsMethodForAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse hors : horses) {
            Mockito.verify(hors, Mockito.times(1)).move();
        }

    }

    @Test
    void getWinner_returnsCorrectWinner() {
        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse("horse5",1,5),
                new Horse("horse10",1,10),
                new Horse("horse15",1,15)
        ));

        assertEquals("horse15", hippodrome.getWinner().getName());
    }
}