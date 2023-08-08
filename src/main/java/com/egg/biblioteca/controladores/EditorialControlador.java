/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialS;
    
    @GetMapping("/registrar")
    public String registar(){
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre){
       try {
            editorialS.crearEditorial(nombre);
        } catch (MiException ex) {
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "editorial_form.html";
        }
        return "index.html"; 
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List <Editorial> editoriales = editorialS.listarEditoriales();
        
        modelo.addAttribute("editoriales", editoriales);
        
        return "editorial_list.html";
    }
}
