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
	
	/*public static float floatConverter(String original){
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
	}*/
	
	public static String getType(String original){
		String type = "String";
		boolean boolTester;
		float floatNumber;
		int number;
		try {
			if (boolTester = Boolean.parseBoolean(original)){
				type = "Boolean";
			}
		}
		catch (Exception b){
			boolTester = false;
		}
		try {
			floatNumber = Float.valueOf(original);
			if(floatNumber == Math.round(floatNumber)){
				floatNumber = 0;
				type = "Float";
			}
		}
		catch (Exception f) {
			floatNumber = 0;
		}
		try {
			number = Integer.parseInt(original);
			if(number == floatNumber){
				number = 0;
				type = "Integer";
			}
			else{
				type = "Integer";
			}
		}
		catch (Exception i) {
			number = 0;
		}
		
		return type;
	}
	
	/*public static boolean booleanConverter(String original){
		boolean boolTester;
		try {
			boolTester = Boolean.parseBoolean(original);
		}
		catch (Exception e) {
			System.out.println("Wrong type");
			boolTester = false;
		}
		
		return boolTester;
	}*/
	

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
		
		System.out.println(getType(args[0]));
		System.out.println(getType(args[1]));
		System.out.println(getType(args[2]));
		
	}

}