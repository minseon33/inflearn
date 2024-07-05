package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class WebApiExRatePaymentService extends PaymentService{

    @Override
    BigDecimal getExRate(String currency) throws IOException {
            //환율 가져오기
            //https://open.er-api.com/v6/latest/USD
            URL url = new URL("https://open.er-api.com/v6/latest/"+ currency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = br.lines().collect(Collectors.joining()); // br.readLine을 안쓰고 lines를 쓰는 이유는..? readLine은 한줄한줄 읽지만 lines는 Stream화 하여 한번에 읽고 list로 만들어준다.
            // collecters.joining은 list를 하나의 String으로 조인해주는 것.
            br.close(); // 스트림을 열었으면 항상 닫아주어야 한다.

            ObjectMapper mapper = new ObjectMapper();
            ExRateData data = mapper.readValue(response, ExRateData.class);
            BigDecimal exRate = data.rates().get("KRW");
            return exRate;

    }
}
