package me.versache.core.hcf.listener;

public class KillStreaks
{
    private String name;
    private int number;
    private String command;
    
    public KillStreaks(final String name, final int number, final String command) {
        this.name = name;
        this.number = number;
        this.command = command;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public String getCommand() {
        return this.command;
    }
}
