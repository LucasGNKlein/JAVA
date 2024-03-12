/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 *
 * @author It was me Lucas!
 */
public abstract class Pessoa {
    private String nome;
    private int idade;
    
    
    public Pessoa (String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
    
    public void apresentar() {
        
        System.out.println("chama o método apresentar");
    }
    
    public abstract void saudacao();
  
    
    public void profissao(String profissao) {
        if(profissao == null || profissao== ""){
            System.out.println("Ainda estou procurando.");
     } else {
         System.out.println("profissão informada: "+ profissao);   
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    
}
