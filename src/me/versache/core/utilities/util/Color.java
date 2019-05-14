package me.versache.core.utilities.util;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class Color
{
    public static String translate(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
    
    public static List<String> translateFromArray(final List<String> text) {
        final List<String> messages = new ArrayList<String>();
        for (final String string : text) {
            messages.add(translate(string));
        }
        return messages;
    }
}
