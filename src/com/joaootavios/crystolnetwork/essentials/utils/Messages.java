package com.joaootavios.crystolnetwork.essentials.utils;

public enum Messages {

    ERRORLOC("&cLocalização não definida."),
    ERRORLOCDISABLE("&cLocalização desabilitada."),
    ERRORNOVIP("&cVocê não possuí o VIP necessário para tal ato"),
    ERRORPLAYEROFFLINE("&cJogador inexistente."),
    ERRORGENERIC("&cUm erro não identificado aconteceu."),
    LANTERNDISABLE("<c>Você desativou o sistema de visão noturna."),
    LANTERNENABLE("<e>Você ativou o sistema de visão noturna.");

    private final String message;
    Messages(final String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
