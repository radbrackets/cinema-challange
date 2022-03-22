package net.skarbek.cinemachallange.unit;


import net.skarbek.cinemachallange.domain.model.RoomId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoomIdTest {

    @Test
    public void shouldNotCreateNull(){
        //when
        Throwable exception = Assertions.catchThrowable(() -> new RoomId(null));
        //then
        Assertions.assertThat(exception)
                .isNotNull()
                .hasMessage("RoomId cannot be null");
    }
}
