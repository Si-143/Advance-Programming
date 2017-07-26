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
public class addOperation implements AddPetrol{//  overrides the AddPetrol methods and add the two values together. 
    @Override
    public int Add(int num1, int num2) {
      return num1 + num2;
   }
}