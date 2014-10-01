//package edu.jsu.mcis;

public class ArgumentParser{
	private String arg1;
	private String arg2;
	private String arg3;
	
	public ArgumentParser(String l, String w, String h){
		arg1 = l;
		arg2 = w;
		arg3 = h;
	}
	
	public String getArg1(){
		return arg1;
	}
	
	public String getArg2(){
		return arg2;
	}
	
	public String getArg3(){
		return arg3;
	}
	
	public int getArgsLength(String [] array)
	{
		return array.length;
	}
	
	public static float floatConverter(String original){
		float number;
		try {
			number = Float.valueOf(original);
		}
		catch (Exception e) {
			System.out.println("Wrong type");
			number = 0;
		}
		
		return number;
	}
	
	public static int intConverter(String original){
		int number;
		try {
			number = Integer.parseInt(original);
		}
		catch (Exception e) {
			System.out.println("Wrong type");
			number = 0;
		}
		
		return number;
	}
	
	public static boolean booleanConverter(String original){
		boolean boolTester;
		try {
			boolTester = Boolean.parseBoolean(original);
		}
		catch (Exception e) {
			System.out.println("Wrong type");
			boolTester = false;
		}
		
		return boolTester;
	}
	

	public static void main(String[] args){
		
		if( args.length == 3){
			ArgumentParser commandLine = new ArgumentParser(args[0], args[1], args[2]);
		}
		else if (args.length < 3){
			System.out.println("Not Enough Arguments");
			System.exit(0);
		}
		else if (args.length > 3 ){
			System.out.println("usage: java VolumeCalculator length width height");
			System.out.println("VolumeCalculator.java: error: unrecognized arguments: " + args[3]);
			System.exit(0);
		}
		
		System.out.println(floatConverter(args[0]));
		
	}

}