package tobyspring.hellospring.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

public class SimpleApiExecutor implements ApiExecutor{

    @Override
    public String execute(URI uri) throws IOException {
        String response;
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            response = br.lines().collect(Collectors.joining()); // br.readLine을 안쓰고 lines를 쓰는 이유는..? readLine은 한줄한줄 읽지만 lines는 Stream화 하여 한번에 읽고 list로 만들어준다.
            // collecters.joining은 list를 하나의 String으로 조인해주는 것.
        }
        return response;
    }
}
