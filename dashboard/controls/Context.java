/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.gre.comp1549.dashboard.controls;

/**
 *
 * @author ts348
 */
public class Context {
   private AddPetrol Add;

   public Context(AddPetrol Add){
      this.Add = Add;
   }// get the instance of add and set it to the add variable.

   public int executeStrategy(int num1, int num2){
      return Add.Add(num1, num2);
   }
}