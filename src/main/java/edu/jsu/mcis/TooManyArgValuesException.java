package edu.jsu.mcis;

public class TooManyArgValuesException extends Exception

{
	public TooManyArgValuesException(){}
	
	public TooManyArgValuesException(String message)
	{
		super(message);
	}
}