package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import tobyspring.hellospring.api.*;
import tobyspring.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Component
public class WebApiExRatePaymentProvider implements ExRateProvider {

    ApiTemplate apiTemplate = new ApiTemplate();

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
//        return apiTemplate.getExRate(url, uri -> {
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(uri)
//                    .GET()
//                    .build();
//
//            try {
//                return HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString()).body();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }, new ErApiExRateExtractor());




        return apiTemplate.getExRate(url, new HttpClientApiExercutor(), new ErApiExRateExtractor());



    }



    private BigDecimal extractExRate(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        System.out.println("API ExRate : "+data.rates().get("KRW"));
        return data.rates().get("KRW");
    }


}
