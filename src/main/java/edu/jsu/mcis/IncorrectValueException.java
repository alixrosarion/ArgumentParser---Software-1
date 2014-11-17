package edu.jsu.mcis;

public class IncorrectValueException extends RuntimeException

{
	public IncorrectValueException(String message)
	{
		super(message);
	}
	public IncorrectValueException (){}
}