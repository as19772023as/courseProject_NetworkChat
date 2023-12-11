package ru.strebkov.logger;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

/*import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;*/

class ServerLogTest {
    private static ServerLog serverLog;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Start Test !  Начало теста\n");
        serverLog = Mockito.mock(ServerLog.class);

    }

    @AfterEach
    void tearDown() {
        System.out.println("END Test !  Конец теста\n");
    }

    @DisplayName("Test for IOException: ")
    @Test
    void writeLogTestIOException() {
        serverLog.writeLog(Mockito.anyString());

        Assertions.assertDoesNotThrow(()->serverLog.writeLog(Mockito.anyString()));
        //IOException ioException = Assertions.assertThrows(IOException.class, ()->serverLog.writeLog(Mockito.anyString()));

    }
    @DisplayName("Test run 1 time: ")
    @Test
    void writeLogTest() {
        serverLog.writeLog( "A");         //Mockito.anyString());

        Mockito.verify(serverLog, Mockito.times(1)).writeLog("A"); //  Mockito.anyString());
    }

/* //   @Test
//    void logTest() {
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        String level = "INFO";
//        String msg = "Hello";
//
//        String expected = String.format("[%s %3d] \n%s (%d) : %s\n",
//                dtf.format(LocalDateTime.now()), 1, level, 1, msg);
//
//        String actual = ServerLog.getInstance().log(level, msg);
//
//        assertEquals(expected, actual);
//    }*/
}