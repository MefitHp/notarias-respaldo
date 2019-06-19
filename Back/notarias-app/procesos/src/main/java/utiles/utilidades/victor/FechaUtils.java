package utiles.utilidades.victor;

import java.util.Calendar;

public class FechaUtils {

	public static Calendar addNaturalDays(int days,Calendar cal){
		cal = setZeroHour(cal);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal;
	}
	
	public static Calendar addBusinessDay(int days,Calendar cal){
		cal = setZeroHour(cal);
		 for (int i=0; i<days; i++)
	            do {
	                cal.add(Calendar.DAY_OF_MONTH, 1);
	            } while (!isWorkingDay(cal));	     
	     return cal;
	}
	
	public static Calendar setZeroHour(Calendar cal){
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
		return cal;
	}
    private static boolean isWorkingDay(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
            return false;
        // tests for other holidays here
        // ...
        return true;
    }
}
