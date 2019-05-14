package me.versache.core.hcf.event.conquest;

import me.versache.core.Base;
import me.versache.core.utilities.util.command.ArgumentExecutor;

public class ConquestExecutor
  extends ArgumentExecutor
{
  public ConquestExecutor(Base plugin)
  {
    super("conquest");
    addArgument(new ConquestSetpointsArgument(plugin));
  }
}
