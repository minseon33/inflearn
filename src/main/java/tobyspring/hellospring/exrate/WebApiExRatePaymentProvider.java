package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import tobyspring.hellospring.api.ApiExecutor;
import tobyspring.hellospring.api.ErApiExRateExtractor;
import tobyspring.hellospring.api.ExRateExtractor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;


@Component
public class WebApiExRatePaymentProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
            //환율 가져오기
            //https://open.er-api.com/v6/latest/USD

        String url = "https://open.er-api.com/v6/latest/" + currency;

//        return runApiForExRate(url, new SimpleApiExecutor(), response ->  {
//                ObjectMapper mapper = new ObjectMapper();
//                ExRateData data = mapper.readValue(response, ExRateData.class);
//                System.out.println("API ExRate : "+data.rates().get("KRW"));
//                return data.rates().get("KRW");
//        }); 이렇게 써도 됨. 하지만 코드가 지저분하지...
        return runApiForExRate(url, new SimpleApiExecutor(), new ErApiExRateExtractor());


    }

    private BigDecimal runApiForExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String response;
        try{
            response = apiExecutor.execute(uri);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try {
            return exRateExtractor.extracts(response);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    private BigDecimal extractExRate(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        System.out.println("API ExRate : "+data.rates().get("KRW"));
        return data.rates().get("KRW");
    }


}
