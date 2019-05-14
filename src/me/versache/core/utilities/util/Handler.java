package me.versache.core.utilities.util;

import me.versache.core.Base;

public class Handler
{
  private Base plugin;
  
  public Handler(Base paramNotorious) {
    this.plugin = paramNotorious;
  }
  
  public void enable() {}
  
  public void disable() {}
  
  public Base getInstance() {
    return this.plugin;
  }
}
