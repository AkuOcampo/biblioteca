package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    @Autowired
    private EditorialRepository editorialR;
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        if(nombre.isEmpty() || nombre==null){
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
        
        Editorial e = new Editorial();
        e.setNombre(nombre);
        
        editorialR.save(e);   
    } 
    
    public List<Editorial> listarEditoriales(){
        List <Editorial> editoriales = new ArrayList();
        editoriales= editorialR.findAll();
        return editoriales;
    }
    @Transactional
    public void modificarEditorial(String nombre, String id) throws MiException{
        
        validarDatos(nombre,id);
        
        Optional<Editorial> respuesta = editorialR.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorialM = respuesta.get();
            
            editorialM.setNombre(nombre);
            
            editorialR.save(editorialM);
        }
    }
    
    private void validarDatos(String nombre, String id) throws MiException{
        if(id.isEmpty() || id==null){
            throw new MiException("El id del editorial no puede estar vacío o ser nulo");
        }
        if(nombre.isEmpty() || nombre==null){
            throw new MiException("El nombre no puede estar vacío o ser nulo");
        }
    }
}
