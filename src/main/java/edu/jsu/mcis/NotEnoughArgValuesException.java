package edu.jsu.mcis;

public class NotEnoughArgValuesException extends Exception

{
	public NotEnoughArgValuesException(){}
	
	public NotEnoughArgValuesException(String message)
	{
		super(message);
	}
}