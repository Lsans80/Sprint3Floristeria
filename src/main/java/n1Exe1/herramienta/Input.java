package n1Exe1.herramienta;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
	
	private static final Scanner input = new Scanner(System.in); 
	private static final String INT_FORMAT_ERR_MSG = "There is a format error on your response. Enter a integer";
	private static final String DOUBLE_FORMAT_ERR_MSG = "There is a format error on your response. Enter a double";
	private static final String FLOAT_FORMAT_ERR_MSG = "There is a format error on your response. Enter a float";
	private static final String EMPTY_STRING_ERR_MSG = "Your respose must not be empty.";
	
	
	public static int inputInt (String pregunta) {
		int response = 0;
		boolean okey = false;
		do {
			System.out.println(pregunta);
			try {
				response = input.nextInt();
				okey = true;
			} catch (InputMismatchException ex) {
				System.err.println(INT_FORMAT_ERR_MSG);
			}
			input.nextLine();
		} while (!okey);
		return response;
	}
	
	public static String inputString (String pregunta) {
		String response = "";
		boolean okey = false;
		do {
			System.out.println(pregunta);
			try {
				response = input.nextLine();
				if (response.isEmpty()) { 
					throw new Exception(EMPTY_STRING_ERR_MSG); 
				} else {
					
				okey = true;
				}
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		} while (!okey);
		return response;	
	}
	
	public static double inputDouble (String pregunta) {
		double response = 0.0;
		boolean okey = false;
		do {
			System.out.println(pregunta);
			try {
				response = input.nextDouble();
				okey = true;
			} catch (InputMismatchException ex) {
				System.err.println(DOUBLE_FORMAT_ERR_MSG);
			}
			input.nextLine();
		} while (!okey);		
		return response;	
	}
	
	public static float inputFloat (String pregunta) {
		float response = 0.0F;
		boolean okey = false;
		do {
			System.out.println(pregunta);
			try {
				response = input.nextFloat();
				okey = true;
			} catch (InputMismatchException ex) {
				System.err.println(FLOAT_FORMAT_ERR_MSG);
			}
			input.nextLine();
		} while (!okey);		
		return response;	
	}

}
