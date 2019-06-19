package com.palestra.notaria.bo.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.palestra.notaria.bo.BonitaBPMBo;
import com.palestra.notaria.dao.BonitaBPMDao;
import com.palestra.notaria.dao.impl.BonitaBPMDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FechasBPM;

public class BonitaBPMBoImpl extends GenericBoImpl implements BonitaBPMBo {

	@Override
	public Calendar addBusinessDay(int days,Calendar cal) throws NotariaException{
		BonitaBPMDao dao = new BonitaBPMDaoImpl(FechasBPM.class);
		cal = setZeroHour(cal);
		Date date = cal.getTime();
		List<Date> holidays = dao.obtenerFechasInhabiles(date);
		 for (int i=0; i<days; i++)
	            do {
	                cal.add(Calendar.DAY_OF_MONTH, 1);
	            } while (!isWorkingDay(cal,holidays));	     
	     return cal;
	}
	
	public static Calendar setZeroHour(Calendar cal){
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
		System.out.println(cal.getTime());
		return cal;
	}
    private static boolean isWorkingDay(Calendar cal, List<Date> holidays) throws NotariaException {
    	
    	cal = setZeroHour(cal);
    	for(Date dia:holidays){
    		Date d2 = cal.getTime();
    		Calendar hday = Calendar.getInstance();
    		hday.setTime(dia);
    		hday = setZeroHour(hday);
    		if(hday.getTime().getTime() == d2.getTime()){
    			return false;
    		}
    	}
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
            return false;
        
        // ...
        return true;
    }
	
    public static void main(String[]args){
//    	try {
			setZeroHour(GregorianCalendar.getInstance());
//		} catch (NotariaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
