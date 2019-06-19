package com.palestra.notaria.bo;

import java.util.Calendar;

import com.palestra.notaria.exceptions.NotariaException;

public interface BonitaBPMBo extends GenericBo{

	Calendar addBusinessDay(int days, Calendar time) throws NotariaException;

}
