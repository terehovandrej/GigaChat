import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.http.client.HttpClientException;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.completion.CompletionResponse;

public class ChatWithHistoryExample {

    public static void main(String[] args) {

        GigaChatClient client = GigaChatClient.builder()
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey("YjQ2ODQ1YzgtMzUzYS00NWI0LWIzMmUtODg2MTRhMjlmMWZlOmIxMGFlYTIwLWNjMWItNGZiOC05NWE5LWM0YmMxNDJmNDIzMw==")
                                .build())
                        .build())
                .build();

        CompletionRequest.CompletionRequestBuilder builder = CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_MAX)
                .message(ChatMessage.builder()
                        .content("Какая веосия GigaCchat")
                        .role(ChatMessage.Role.USER)
                        .build())
                .message(ChatMessage.builder()
                        .content("До какой даты знаешь информацию")
                        .role(ChatMessage.Role.USER).build());

        try {
            for (int i = 0; i < 4; i++) {
                CompletionRequest request = builder.build();
                CompletionResponse response = client.completions(request);
                System.out.println(response);

                response.choices().forEach(e -> builder.message(e.message().ofAssistantMessage()));

                builder.message(ChatMessage.builder()
                        .content("Думаешь, у нас еще есть шанс?")
                        .role(ChatMessage.Role.USER).build());
            }
        } catch (HttpClientException ex) {
            System.out.println(ex.statusCode() + " " + ex.bodyAsString());
        }
    }
}