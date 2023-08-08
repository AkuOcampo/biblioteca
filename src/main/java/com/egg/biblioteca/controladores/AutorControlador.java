/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorS;
    
    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar(){
        return "autor_form.html";
    }
    
    @PostMapping("/registro")//localhost:8080/autor/registro
    public String registro(@RequestParam String nombre, ModelMap modelo){
        //System.out.println("Nombre: "+nombre);
        try {
            autorS.crearAutor(nombre);
            modelo.put("exito","El autor fue registrado correctamente!");
        } catch (MiException ex) {
            modelo.put("error",ex.getMessage());
            return "autor_form.html";
        }
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List <Autor> autores = autorS.listarAutores();
        
        modelo.addAttribute("autores", autores);
        
        return "autor_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorS.getOne(id));
        
        return "autor_modificar.html";
    }
    
    @PostMapping("{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            autorS.modificarAutor(nombre, id);
            
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
        
    }
    
   // @GetMapping("{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws MiException{
        autorS.eliminar(id);
        
        return "autor_modificar.html";
    }
}
