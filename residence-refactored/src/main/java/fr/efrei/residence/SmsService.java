package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class SmsService {

    private static final int LONGUEUR_NUMERO_MIN = 10;
    private static final int LONGUEUR_MESSAGE_MAX = 160;
    private static final String MARQUE_TRONCATURE = "...";

    public static int compteurSms = 0;
    public static List<String[]> journal = new ArrayList<String[]>();

    public void envoyerSms(String numero, String message) {
        if (numero == null || numero.length() < LONGUEUR_NUMERO_MIN) {
            System.out.println("[SMS] numéro invalide : " + numero);
            return;
        }
        if (message.length() > LONGUEUR_MESSAGE_MAX) {
            message = message.substring(0, LONGUEUR_MESSAGE_MAX - MARQUE_TRONCATURE.length()) + MARQUE_TRONCATURE;
        }
        compteurSms++;
        journal.add(new String[]{numero, message});
        System.out.println("[SMS] -> " + numero + " : " + message);
    }
}
