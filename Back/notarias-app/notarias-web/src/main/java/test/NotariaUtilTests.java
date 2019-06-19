package test;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.util.NotariaUtils;

public class NotariaUtilTests {

	public NotariaUtilTests() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws NotariaException {
		// TODO Auto-generated method stub
		String dato = NotariaUtils.getProperties("dito.password");
		System.out.println(dato);
	}

}
