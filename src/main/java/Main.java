import br.com.mentorama.telegram.api.TelegramAPI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        TelegramAPI telegramAPI = new TelegramAPI("1732771037:AAFSbCKOwRxiFrT85-ADphTFCG9_OvUOQIw");

        System.out.println("Executando BOT");

        try {
            System.out.println("Iniciou");
            telegramAPI.run();
        } catch (UnirestException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
