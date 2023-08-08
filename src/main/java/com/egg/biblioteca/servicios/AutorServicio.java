
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepository autorR;
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        if(nombre.isEmpty() || nombre==null){
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
        
        Autor a = new Autor();
        a.setNombre(nombre);
        
        autorR.save(a);
        
    } 
     @Transactional(readOnly = true)
    public List<Autor> listarAutores(){
        List <Autor> autores = new ArrayList();
        autores= autorR.findAll();
        return autores;
    }
    
    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException{
        if(nombre.isEmpty() || nombre==null){
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
        
        Optional<Autor> respuesta = autorR.findById(id);
        
        if (respuesta.isPresent()) {
            Autor autorM = respuesta.get();
            
            autorM.setNombre(nombre);
            
            autorR.save(autorM);
        }
    }
    
     @Transactional(readOnly = true)
    public Autor getOne(String id){
        return autorR.getOne(id);
    }
    
   
    @Transactional
    public void eliminar(String id) throws MiException{
        
        Autor autor = autorR.getById(id);
        
        autorR.delete(autor);

    }
    
    private void validarDatos(String nombre, String id) throws MiException{
        if(id.isEmpty() || id==null){
            throw new MiException("El id del autor no puede estar vacío o ser nulo");
        }
        if(nombre.isEmpty() || nombre==null){
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
    }
}
