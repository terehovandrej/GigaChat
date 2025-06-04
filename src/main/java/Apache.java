import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Apache {
    public static void main(String[] args) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Создаем POST-запрос
            HttpPost httpPost = new HttpPost("https://your-api-endpoint.com/path");

            // Создаем тело запроса в формате x-www-form-urlencoded
            StringEntity requestBody = new StringEntity("scope=GIGACHAT_API_PERS",
                    ContentType.APPLICATION_FORM_URLENCODED.withCharset("UTF-8"));

            // Присваиваем тело запросу
            httpPost.setEntity(requestBody);

            // Устанавливаем заголовки
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString());
            httpPost.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());

            // Отправляем запрос и получаем ответ
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Выводим статус ответа

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}