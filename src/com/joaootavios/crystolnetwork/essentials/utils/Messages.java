package com.joaootavios.crystolnetwork.essentials.utils;

public enum Messages {

    ERRORLOC("&cLocalização não definida."),
    ERRORLOCDISABLE("&cLocalização desabilitada."),
    ERRORNOVIP("&cVocê não possuí o VIP necessário para tal ato"),
    ERRORPLAYEROFFLINE("&cJogador inexistente."),

    // Messages - chats
    GLOBALCHAT("&cO chat global está desativado no momento."),
    LOCALCHAT("&cO chat local está desativado no momento."),
    TELLCMD("<c>Utilize: /tell <jogador> <mensagem>."),
    TELLNOHAVEFRIENDS("<c>Evite enviar mensagens para si mesmo, porque você não tenta arrumar amigos? =P"),
    TELLRECEIVE("<b>Você recebeu uma mensagem privada!"),
    TELLNOONLINE("<7>%s<c> não está online ou está com tell desativado."),
    NOHAVE250COINS("<c>Você não possuí 250 coins, portanto não pode enviar mensagens."),
    ERRORMUTED("<c>Você está silenciado, portanto não pode utilizar qualquer meio de comunicação."),
    GLOBALCHATCMD("<c>Use /g <mensagem> para enviar uma mensagem."),

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
