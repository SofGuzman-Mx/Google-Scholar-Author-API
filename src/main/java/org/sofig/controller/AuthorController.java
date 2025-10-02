package org.sofig.controller;

import org.sofig.model.Author;
import org.sofig.view.AuthorView;

public class AuthorController {
    //objetos vista y modelo
    private Author model;
    private AuthorView view;

    // Constructor: recibe el modelo y la vista
    public AuthorController(Author model, AuthorView view) {
        this.model = model;
        this.view = view;
    }

    //getters y setters para el modelo
    // Métodos para obtener datos del modelo (SET)
    public void setAuthorName(String name){
        this.model.setName(name);
    }

    // Metodo para actualizar la vista
    public void updateView() {
        view.printAuthorData(
                model.getName()
        );
    }
}
