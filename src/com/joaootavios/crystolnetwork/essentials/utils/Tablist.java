package com.joaootavios.crystolnetwork.essentials.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Tablist {

    public static void sendTablist(Player player, List<String> header, List<String> footer) {
        sendtablist(player, String.join("\n", header), String.join("\n", footer));
    }

    private static void sendtablist(Player player, String headertext, String footertext) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;

//        IChatBaseComponent header = ChatSerializer.a("{\"text\": \"" + headertext + "\"}");
//        IChatBaseComponent footer = ChatSerializer.a("{\"text\": \"" + footertext + "\"}");

        if(headertext == null) {
            headertext = new String();
        }
        if(footertext == null) {
            footertext = new String();
        }

        IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"translate\": \"" + headertext + "\"}");
        IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"translate\": \"" + footertext + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(header);

        try {
            Field headerField = packet.getClass().getDeclaredField("b");
            headerField.setAccessible(true);
            headerField.set(packet, footer);
            headerField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        connection.sendPacket(packet);
    }
}