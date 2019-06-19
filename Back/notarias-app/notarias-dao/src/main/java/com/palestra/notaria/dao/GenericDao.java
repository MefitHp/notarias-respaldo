package com.palestra.notaria.dao;

import java.io.Serializable;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;

public interface GenericDao<T, Id extends Serializable> {
	

    /**
     * Crea una instancia a ser gestionada y persistida
     *
     * @param objeto Objeto a persistir
     * @return Objeto persistido
     */
    public T save(T objeto) throws NotariaException;

    /**
     * Actualiza una instancia gestionada y persistida
     *
     * @param objeto Objeto a actualizar
     * @return Objeto actualizado
     */
    public T update(T objeto) throws NotariaException;

    /**
     * Borra una instancia gestionada y deja de ser persistida
     *
     * @param objeto Objeto a borrar
     */
    public void delete(T objeto) throws NotariaException;

    /**
     * Verifica que la instancia de un Objeto se encuentre gestionada y persistida
     *
     * @param objeto Objeto a verificar
     * @return True si esta gestionada y persistida, False de lo contario
     */
    public boolean contains(T objeto) throws NotariaException;

    /**
     * Busca todas las instancias que se encuentran gestionadas y persistidas
     *
     * @return Lista con todas las instancias
     */
    public List<T> findAll()throws NotariaException;

    /**
     * Busca una Instancia gestionada y persistida por su Id
     *
     * @param id Id de la Instancia
     * @return Instancia
     */
    public T findById(String id)throws NotariaException;


	

}
