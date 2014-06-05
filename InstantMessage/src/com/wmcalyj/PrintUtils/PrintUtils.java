package com.wmcalyj.PrintUtils;

import java.util.Arrays;
import java.util.List;

public class PrintUtils {
	public static final String END = "---------------------------";

	public static void printByte(String description, List<byte[]> bytes) {
		System.out.println(description);
		System.out.println("The length of array is: " + bytes.size());
		for (byte[] _byte : bytes) {
			System.out.println(Arrays.toString(_byte));
		}
		System.out.println(END);
	}
}
