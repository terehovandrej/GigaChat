import okhttp3.*;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Настройка TrustManager, который принимает любой сертификат
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {}

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Создание контекста SSL с нашим TrustManager'ом
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Создание HostnameVerifier, который также разрешает любые имена хостов
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Настраиваем OkHttpClient с нашими настройками SSL
            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager)trustAllCerts[0])
                    .hostnameVerifier(hostnameVerifier)
                    .build();

           MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");

            // Форматируем data-параметры для передачи в теле запроса
            RequestBody body = RequestBody.create(mediaType, "scope=GIGACHAT_API_PERS");
            // Определяем заголовки
            Request request = new Request.Builder()
                    .url("https://ngw.devices.sberbank.ru:9443/api/v2/oauth")

                    .post(body)
                    .addHeader("RqUID", "6f0b1291-c7f3-43c6-bb2e-9f3efb2dc98e")
                    .addHeader("Authorization", "YjQ2ODQ1YzgtMzUzYS00NWI0LWIzMmUtODg2MTRhMjlmMWZlOjQ4OTQ0Y2MxLTg1ZGYtNGIxZC1hNDcyLTdjMDYxNGVmMTJhYg==")
                    .build();
            // Выполняем запрос
            Response response = client.newCall(request).execute();

            // Обрабатываем ответ
            if (response.isSuccessful()) {
                System.out.println(response.body().string()); // выводим тело ответа
            } else {
                System.err.println("Запрос завершился неудачей. Код ответа: " + response.code());
            }
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            e.printStackTrace(); // обработка ошибок
        }
    }
}