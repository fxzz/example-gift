package com.example.examplegift.infrastructure.retrofit;

import com.example.examplegift.common.response.CommonResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RetrofitUtils {
    private static final HttpLoggingInterceptor loggingInterceptor
            = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    // OkHttpClient 애플리케이션에서 네트워크 통신을 위해 사용되는 클라이언트
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            // 로깅 인터셉터(logging interceptor)를 추가하는 메서드입니다. 로깅 인터셉터는 네트워크 요청 및 응답의 로그를 출력하는 데 사용됩니다
            .addInterceptor(loggingInterceptor)
            // 타임아웃(connect timeout)을 3초로 설정하는 메서드입니다.
            // 연결 타임아웃은 서버와의 연결을 시도하는 최대 시간을 제한하는데 사용됩니다. 만약 지정된 시간 내에 서버와의 연결이 수립되지 않으면 예외가 발생합니다.
            .connectTimeout(3, TimeUnit.SECONDS)
            // 읽기 타임아웃(read timeout)을 10초로 설정하는 메서드입니다.
            // 읽기 타임아웃은 서버로부터 응답을 수신하는 최대 시간을 제한하는데 사용됩니다. 만약 지정된 시간 내에 응답이 도착하지 않으면 예외가 발생합니다.
            .readTimeout(10, TimeUnit.SECONDS);


    //  Gson 라이브러리를 사용하여 데이터를 시리얼라이즈(직렬화)할 때 사용할 컨버터 로직을 정의하고 있습니다.
    //  Gson 은 JSON 데이터를 자바 객체로 변환하거나 자바 객체를 JSON 데이터로 변환하기 위해 사용되는 라이브러리입니다.
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    // Retrofit 를 사용하기 위해 아래 객체 생성
    // baseUrl 지정 (기프트에서 주문 쪽으로 호출 할거니까 주문쪽 서버의 루트주소)
    // application.yml 에 base-url: http://localhost:8080/ 설정 참고
    public static Retrofit initRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }



    public <T extends CommonResponse> Optional<T> responseSync(Call<T> call) {
        try {
            Response<T> execute = call.execute();
            if (execute.isSuccessful()) {
                return Optional.ofNullable(execute.body());
            } else {
                log.error("requestSync errorBody = {}", execute.errorBody());
                throw new RuntimeException("retrofit execute response error");
            }
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException("retrofit execute IOException");
        }
    }

    public void responseVoid(Call<Void> call) {
        try {
            if (!call.execute().isSuccessful()) throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
