
public class FirmaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(FirmaTest.test());
	}
	
	public static String test() {
	    try {
	        return "ok acabe";
	    }
	    finally {
	        System.out.println("finally trumps return.");
	    }
	}

}
