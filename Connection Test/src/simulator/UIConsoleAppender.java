package simulator;

import java.util.ArrayList;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.spi.LoggingEvent;
import userInterface.Console;

public class UIConsoleAppender extends ConsoleAppender
{
	public Console consoleToUpdate;
	
	public void setConsole(Console console)
	{
		//System.out.println("\r\n!!!!!!!!! Set Console To Update Was Called !!!!!!!!!!!\r\n");
		consoleToUpdate = console;
	}
	
    @Override
    protected void subAppend(LoggingEvent event)
    {
        //String modifiedMessage = ("****=========> " + event.getMessage());
        //System.out.println(modifiedMessage);
        consoleToUpdate.add(event.getMessage().toString());

        super.subAppend(event);
    }
}