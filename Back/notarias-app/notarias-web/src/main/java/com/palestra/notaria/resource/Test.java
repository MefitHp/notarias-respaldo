package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test implements CharSequence {

	static int a = 2437;
	static int b = 875;

	public static int calcula(int x, int y) {
		if (x == y) {
			return x;
		} else if (x > y) {
			x = x - y;
		} else if (x < y) {
			y = y - x;
		}
		return calcula(x, y);
	}

	public static int fizzBuzz(Integer count) {
		if (count == 26)
			return 0;
		String fizzBuzz = count % 15 == 0 ? "FizzBuzz"
				: (count % 3 == 0 ? "Fizz" : (count % 5 == 0 ? "Buzz" : count.toString()));
		System.out.print(fizzBuzz + " ");

		return fizzBuzz(count += 1);
	}

	// public int[] cellCompete(int[]states, int days){
	// if(days>0){
	// result = new int[8];
	// int izq;
	// int der;
	// for(int i=0;i<states.length;i++){
	// if(i==0){
	// izq=0;
	// }else{
	// izq=states[i-1];
	// }
	// if(i==states.length-1){
	// der=0;
	// }else{
	// der=states[i+1];
	// }
	// if(izq==der){
	// result[i]=0;
	// }else{
	// result[i]=1;
	// }
	//
	// }
	// System.out.println(Arrays.toString(result));
	// return cellCompete(result, days-1);
	// }else{
	// System.out.println(Arrays.toString(result));
	// return result;
	// }
	// }
	public static void main(String[] args) {
		// System.out.println(calcula(a,b));
		// fizzBuzz(0);
		// int [][] data = { {1487799425, 14, 1},
		// {1487799425, 4, 0},
		// {1487799425, 2, 0},
		// {1487800378, 10, 1},
		// {1487801478, 18, 0},
		// {1487801478, 18, 1},
		// {1487901013, 1, 0},
		// {1487901211, 7, 1},
		// {1487901211, 7, 0} };
		// System.out.println(findBusiestPeriod(data));

		System.out.println(validateIP("192.168.15.20"));
	}

	@Override
	public char charAt(int index) {
		return s.charAt(index);
	}

	private String s;

	public Test(String s) {
		this.s = s;
	}

	@Override
	public int length() {
		return s.length();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	static boolean validateIP(String ip) {
		String regex = "^[0-255].[0-255].[0-255].[0-255]";
		return ip.matches(regex);
	}

	static int findBusiestPeriod(int[][] data) {
		// your code goes here
		int maxPeople = 0;
		int crowdest = 0;
		for (int i = 0; i < data.length; i++) {
			int bigpoint = 0;
			for (int j = 0; j < data[i].length; j++) {
				int tmp = data[i][1];
				if (data[i][2] == 1) {
					maxPeople += tmp;
				} else if (data[i][2] == 0) {
					maxPeople -= tmp;
				}
			}
		}
		// System.out.println(data.length);
		return crowdest;
	}
}

class Java {
	public void printMe() {
		System.out.println("Java print");
	}
}

class Android extends Java {
	public void printMe() {
		System.out.println("android print");
	}
}

class JavaEE extends Android {
	public void printMe() {
		System.out.println("JavaEE print");
	}
}

class Main {
	public static void main(String[] args) {

		List<Integer> list = new ArrayList<Integer>();
		list.add(20);
		list.add(4);
		list.add(8);
		list.add(2);
		Main m = new Main();
		System.out.println(m.minTime(4, list));

	}

	int minimumTime(int numParts, List<Integer> parts) {
		if (numParts == 0) {
			return -1;
		}
		int leftElement = 0;
		int newPart = 0;
		List<Integer> assembledParts = new ArrayList<>();
		Collections.sort(parts);
		System.out.println("list " + parts);
		for (int i = 1; i <= parts.size() - 1; i++) {
			System.out.println("i " + parts.get(i));
			leftElement = parts.get(i - 1);
			newPart = parts.get(i) + leftElement;
			parts.remove(i);
			parts.add(i, newPart);
			assembledParts.add(newPart);
		}

		for (int i = 1; i <= numParts - 1; i++) {

		}
		int totalTime = 0;
		for (Integer part : assembledParts) {
			totalTime += part;
		}
		return totalTime;
	}

	int minTime(int numParts, List<Integer> parts) {
		Collections.sort(parts, Collections.reverseOrder());

		System.out.println(parts);
		if (numParts == 1) {
			System.out.println("entro aqui");
			return parts.get(0);
		}
		int leftElement = parts.get(numParts - 2);
		int newPart = parts.get(numParts - 1) + leftElement;
		System.out.println("leftElement " + leftElement);
		System.out.println("newPart " + newPart);
		parts.remove(numParts - 2);
		parts.add(numParts - 2, newPart);
		return minTime(numParts - 1, parts);
	}
}