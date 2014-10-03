package edu.jsu.mcis;

public class NotEnoughArgsException extends Exception

{
	public NotEnoughArgsException(){}
	
	public NotEnoughArgsException(String message)
	{
		super(message);
	}
}