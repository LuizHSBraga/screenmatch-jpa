package br.com.alura.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.OpenAiHttpException;

public class ConsultaChatGPT {

    public static String obterTraducao(String texto) {
        try {
            OpenAiService service = new OpenAiService(System.getenv("OPENAI_APIKEY"));

            CompletionRequest requisicao = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("traduza para o português o texto: " + texto)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();
        } catch (OpenAiHttpException e) {
            // Tratamento para quando excede a cota na API OpenAI
            System.err.println("Você excedeu sua cota na API OpenAI. Por favor, verifique seu plano e detalhes de faturamento.");
            // Aqui você pode retornar uma mensagem indicando ao usuário que a cota foi excedida
            return "Erro: cota excedida na API OpenAI.";
        } catch (Exception e) {
            // Outras exceções que podem ocorrer
            e.printStackTrace();
            // Retorne uma mensagem genérica de erro para outras exceções
            return "Erro ao processar a solicitação.";
        }
    }
}
