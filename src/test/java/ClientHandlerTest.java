import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Constants.*;

class ClientHandlerTest {

  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Socket serverSideSocket;
  private Map<String, String> store = new HashMap<>();

  @BeforeEach
  void setUp() throws IOException {
    serverSocket = new ServerSocket(0); // Bind to any available port
    int port = serverSocket.getLocalPort();

    new Thread(() -> {
      try {
        serverSideSocket = serverSocket.accept();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();

    clientSocket = new Socket("localhost", port);
  }

  @AfterEach
  void tearDown() throws IOException {
    if (clientSocket != null) {
      clientSocket.close();
    }
    if (serverSideSocket != null) {
      serverSideSocket.close();
    }
    if (serverSocket != null) {
      serverSocket.close();
    }
  }

  @ParameterizedTest
  @MethodSource(value = "redisCommandsWithExpectedResult")
  void shouldExecuteTheRedisCommand(String command, String expectedResponse) throws IOException {
    ClientHandler clientHandler = new ClientHandler(serverSideSocket, store);
    clientHandler.start();

    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    writer.println(command);

    writer.flush();
    clientSocket.shutdownOutput();

    assertEquals(expectedResponse, reader.readLine());

    clientSocket.close();
  }

  public static Stream<Arguments> redisCommandsWithExpectedResult() {
    return Stream.of(
      Arguments.of(PING_COMMAND + CRLF, SIMPLE_STRING + "PONG"),
      Arguments.of(GET_COMMAND + CRLF + "foo"+ CRLF, BULK_STRINGS + "-1"),
      Arguments.of(SET_COMMAND + CRLF+ "foo" + CRLF + "bar "+ CRLF, OK_RESPONSE),
      Arguments.of("DEL\r\nfoo\r\n", SIMPLE_INTEGER + "0"),
      Arguments.of("STRLEN\r\nfoo\r\n", SIMPLE_INTEGER + "0"),
      Arguments.of("MGET\r\nfoo\r\n", ARRAY + "0")
      );
  }
}
