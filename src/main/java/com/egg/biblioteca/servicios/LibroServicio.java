package com.egg.biblioteca.servicios;
//----------------IMPORTAR DE JAVA-------------
import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepository;
import com.egg.biblioteca.repositorios.EditorialRepository;
import com.egg.biblioteca.repositorios.LibroRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
//----------------IMPORTAR DE SPRING-------------
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepository libroR;
    @Autowired
    private AutorRepository autorR;
    @Autowired
    private EditorialRepository editorialR;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        
        validarAtributos(isbn, titulo, idAutor, idEditorial, ejemplares);
        
        Libro l = new Libro();

        l.setIsbn(isbn);
        l.setTitulo(titulo);
        l.setEjemplares(ejemplares);
        
        l.setAlta(new Date());
        
        Autor a = autorR.findById(idAutor).get();
        l.setAutor(a);
        
        Editorial e = editorialR.findById(idEditorial).get();
        l.setEditorial(e);
        
        libroR.save(l);
        
        
    }
    
    public List<Libro> listarLibros(){
        List <Libro> libros = new ArrayList();
        libros= libroR.findAll();
        return libros;
    }
    
    @Transactional
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        
        validarAtributos(isbn, titulo, idAutor, idEditorial, ejemplares);
        
        Optional<Libro> respuestaL = libroR.findById(isbn);
        Optional<Autor> respuestaA = autorR.findById(idAutor);
        Optional<Editorial> respuestaE = editorialR.findById(idEditorial);
        
        if(respuestaL.isPresent() && respuestaA.isPresent() && respuestaE.isPresent()){
            
            Libro libroM = respuestaL.get();
            Autor autorM = respuestaA.get();
            Editorial editorialM = respuestaE.get();
            
            libroM.setTitulo(titulo);
            libroM.setAutor(autorM);
            libroM.setEditorial(editorialM);
            libroM.setEjemplares(ejemplares);
            
            libroR.save(libroM);
            
        }
    }
    
    private void validarAtributos(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        if(isbn==null){
            throw new MiException("El isbn no puede ser nulo");
        }
        if(titulo.isEmpty() || titulo==null){
            throw new MiException("El titulo no puede estar vacío o ser nulo");
        }
        if(ejemplares==null){
            throw new MiException("El ejemplares no puede ser nulo");
        }
        if(idAutor.isEmpty() || idAutor==null){
            throw new MiException("El id del autor no puede estar vacío o ser nulo");
        }
        if(idEditorial.isEmpty() || idEditorial==null){
            throw new MiException("El id de la editorial no puede estar vacío o ser nulo");
        }
    }
    
    public Libro getOne(Long isbn) {
        return libroR.getOne(isbn);
    }
}
