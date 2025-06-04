import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.http.client.HttpClientException;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.completion.CompletionResponse;

public class SingleChatRequestExample {

    public static void main(String[] args) {

        // Создаем клиент GigaChat с заданным токеном аутентификации
        GigaChatClient client = GigaChatClient.builder()
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey("YjQ2ODQ1YzgtMzUzYS00NWI0LWIzMmUtODg2MTRhMjlmMWZlOmIxMGFlYTIwLWNjMWItNGZiOC05NWE5LWM0YmMxNDJmNDIzMw==")
                                .build())
                        .build())
                .build();

        // Формируем структуру запроса
        CompletionRequest request = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_MAX)
                .message(ChatMessage.builder()
                        .content("Кто круче DeepSeek или Gigachat")
                        .role(ChatMessage.Role.USER)
                        .build())
                .build();

        try {
            // Выполняем запрос и получаем ответ
            CompletionResponse response = client.completions(request);

            // Выводим первый выбор из списка возможных вариантов ответов
            if (!response.choices().isEmpty()) {
                String assistantReply = response.choices().get(0).message().content();
                System.out.println("Ответ GigaChat:\n" + assistantReply);
            } else {
                System.out.println("Нет выбора в ответе.");
            }
        } catch (HttpClientException e) {
            System.err.println("Ошибка HTTP-запроса: ");
        }
    }
}