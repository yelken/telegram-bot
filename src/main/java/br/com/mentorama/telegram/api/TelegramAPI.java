package br.com.mentorama.telegram.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public final class TelegramAPI {

    private final String endpoint = "https://api.telegram.org/";
    private final String token;

    public TelegramAPI(String token) {
        this.token = token;
    }

    public HttpResponse<JsonNode> enviarMensagem(Integer chatId, String texto) throws UnirestException {
        return Unirest.post(endpoint + "bot" + token + "/sendMessage")
                .field("chat_id", chatId)
                .field("text", texto)
                .asJson();
    }

    public HttpResponse<JsonNode> getAtualizacoes(Integer offset) throws UnirestException {
        return Unirest.post(endpoint + "bot" + token + "/getUpdates")
                .field("offset", offset)
                .asJson();
    }

    public void run() throws UnirestException {
        int last_update_id = 0;

        HttpResponse<JsonNode> response;

        while (true) {
            response = getAtualizacoes(last_update_id++);

            if (response.getStatus() == 200) {
                JSONArray responses = response.getBody().getObject().getJSONArray("result");

                // Se o array de mensagem for vazio
                if (responses.isNull(0)) {
                    continue;
                } else {
                    last_update_id = responses.getJSONObject(responses.length() - 1).getInt("update_id") + 1;
                }

                for (int i = 0; i < responses.length(); i++) {
                    JSONObject mensagem = responses.getJSONObject(i).getJSONObject("message");

                    int chatId = mensagem.getJSONObject("chat").getInt("id");

                    System.out.println("ChatId " + chatId);

//                    String usuario = mensagem.getJSONObject("chat").getString("username");

//                    System.out.println("Usuário " + usuario);

                    String texto = mensagem.getString("text");

                    System.out.println("Texto " + texto);

                    if (texto.contains("preço") || texto.equals("preço") || texto.equals("preco")) {
                        enviarMensagem(chatId, "Para consultar o preço, entrar no nosso site https://google.com.br");
                    }

                    if (texto.contains("loja") || texto.equals("loja") || texto.equals("lojao")) {
                        enviarMensagem(chatId, "Para consultar nossas lojas ...");
                    }

                    enviarMensagem(chatId, "Opa, estou aqui!!");
                }
            }
        }
    }
}
