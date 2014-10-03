package edu.jsu.mcis;

public class TooManyArgsException extends Exception

{
	public TooManyArgsException(){}
	
	public TooManyArgsException(String message)
	{
		super(message);
	}
}