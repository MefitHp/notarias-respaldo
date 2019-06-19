package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.GenericBo;
import com.palestra.notaria.dao.GenericDao;
import com.palestra.notaria.exceptions.NotariaException;

public abstract class GenericBoImpl<T> implements GenericBo<T> {
	
	protected GenericDao<T, Integer> dao;

	@Override
	public T save(T t) throws NotariaException {
//		try{
		return this.dao.save(t);
		
//		}catch(NotariaException e){
//			e.printStackTrace();
//		}
	}

	@Override
	public T update(T t) throws NotariaException{
//		try{
		return this.dao.update(t);
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
	}

	@Override
	public boolean delete(T t) {
		try{
			this.dao.delete(t);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<T> findAll() throws NotariaException{
//		try{
			return this.dao.findAll();
//		}catch(NotariaException e){
//			e.printStackTrace();
//			return null;
//		}
	}

	@Override
	public T findById(String id) throws NotariaException {
		try{
			return this.dao.findById(id);
		}catch(Exception e){
			e.printStackTrace();
//			return null;
		}
		return null;
	}
	
	
}
