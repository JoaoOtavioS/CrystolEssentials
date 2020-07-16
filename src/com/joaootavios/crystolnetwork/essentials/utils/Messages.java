package com.joaootavios.crystolnetwork.essentials.utils;

public enum Messages {

    ERRORLOC("&cLocalização não definida."),
    ERRORLOCDISABLE("&cLocalização desabilitada."),
    ERRORNOVIP("&cVocê não possuí o VIP necessário para tal ato"),
    ERRORGENERIC("&cUm erro não identificado aconteceu.");

    private String message;

    Messages(final String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
