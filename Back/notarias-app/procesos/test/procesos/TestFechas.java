package procesos;

import java.util.Calendar;
import java.util.GregorianCalendar;

import utilidades.victor.FechaUtils;

public class TestFechas {

	public static void main(String[] args) {
        Calendar cal = new GregorianCalendar();
        // cal now contains current date
        System.out.println(cal.getTime());
        FechaUtils.addNaturalDays(2, cal);
        System.out.println(cal.getTime());
        // add the working days
        FechaUtils.addBusinessDay(2, cal);
        System.out.println(cal.getTime());
    }

	

}
