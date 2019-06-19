package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;

public interface GenericBo<T> {

	public T save(T t)throws NotariaException;
	
	public  T update(T t)throws NotariaException;
	
	public boolean delete(T t)throws NotariaException;
	
	public List<T> findAll()throws NotariaException;
	
	public T findById(String id)throws NotariaException;


}
