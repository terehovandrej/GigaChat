import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.http.client.HttpClientException;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;

public class CompletionWithSessionIdExample {

    public static void main(String[] args) {

        GigaChatClient client = GigaChatClient.builder()
                .verifySslCerts(false)
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey("YjQ2ODQ1YzgtMzUzYS00NWI0LWIzMmUtODg2MTRhMjlmMWZlOmIxMGFlYTIwLWNjMWItNGZiOC05NWE5LWM0YmMxNDJmNDIzMw==")
                                .build())
                        .build())
                .build();
        try {
            System.out.println(client.completions(CompletionRequest.builder()
                    .model(ModelName.GIGA_CHAT_MAX)
                    .message(ChatMessage.builder()
                            .content("Какие факторы влияют на стоимость страховки на дом?")
                            .role(ChatMessage.Role.USER)
                            .build())
                    .build(), "123"));
        } catch (HttpClientException ex) {
            System.out.println(ex.statusCode() + " " + ex.bodyAsString());
        }
    }
}