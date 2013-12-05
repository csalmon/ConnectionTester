package simulator;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.spi.LoggingEvent;
import userInterface.Console;

public class UIConsoleAppender extends ConsoleAppender
{
	private Console consoleToUpdate;
	
	public void setConsole(Console console)
	{
		consoleToUpdate = console;
	}
	
    @Override
    protected void subAppend(LoggingEvent event)
    {
        consoleToUpdate.add(event.getMessage().toString());

        super.subAppend(event);
    }
}