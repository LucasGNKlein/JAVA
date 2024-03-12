/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package view;

import model.Aluno;
import model.Pessoa;
import model.Professor;

/**
 *
 * @author It was me Lucas!
 */
public class Main {
    
     public static void main(String [] args) {
        
         Pessoa p1 = new Pessoa("Robson", 23) {
             @Override
             public void saudacao() {
                 throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
             }
         };
         p1.apresentar();
         
         Aluno a1 = new Aluno("Renato", 19);
//         a1.apresentar();
//         a1.apresentar("Santo Antonio da Patrulha");
           a1.saudacao();
         
         Professor p2 = new Professor("Romeu", 33);
//         p2.apresentar();
//         p2.apresentar("São Miguel das Missões");
           p2.saudacao();
                 
        
    }

}
