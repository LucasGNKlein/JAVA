/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 *
 * @author It was me Lucas!
 */
public class Aluno extends Pessoa {

    public Aluno(String nome, int idade) {
        super(nome, idade);
    }
    
    @Override
    public void apresentar(){
        System.out.println("Olá, sou um aluno");
    }

    public void apresentar(String cidade){
        System.out.println("Olá, meu nome é"+this.getNome()+ " e eu sou de"+cidade);
    }
    
    @Override
    public void saudacao(){
        System.out.println("Olá estou na classe aluno");
    }
}
