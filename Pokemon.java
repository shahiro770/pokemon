//pokemon.java
//Nicholas Culmone

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pokemon{
	private String name,type,resistance,weakness;
	private int hp,energy,numAttacks,maxHp,disable = 0;
	private ArrayList<Attack> attacks = new ArrayList<Attack>();
	
	private static Scanner inFile;
	
	public Pokemon(String stats){
		String[]items = stats.split(",");
		name = items[0];
		hp = Integer.parseInt(items[1]);
		maxHp = Integer.parseInt(items[1]);
		type = items[2];
		resistance = items[3];
		weakness = items[4];
		numAttacks = Integer.parseInt(items[5]);
		energy = 50;
		
		for(int i=0;i<numAttacks;i++){
			String[]att = {items[(i*4)+6],items[(i*4)+7],items[(i*4)+8],items[(i*4)+9]};
			attacks.add(new Attack(att));
		}
	}
	
	
	
	public String getName(){
		return name;
	}
	public int getHp(){
		return hp;
	}
	public String getType(){
		return type;
	}
	public String getResistance(){
		return resistance;
	}
	public String getWeakness(){
		return weakness;
	}
	public int getNumAttacks(){
		return numAttacks;
	}
	public int getEnergy(){
		return energy;
	}
	public int getMaxHp(){
		return maxHp;
	}
	public int getDisable(){
		return disable;
	}
	
	public String getAttName(int n){
		return attacks.get(n).getName();
	}
	public int getAttCost(int n){
		return attacks.get(n).getCost();
	}
	public int getAttDamage(int n){
		return attacks.get(n).getDamage();
	}
	public String getAttSpecial(int n){
		return attacks.get(n).getSpecial();
	}
	
	public Attack getAttack(int n){
		return attacks.get(n);
	}
	//Get Statements
	
	public void endBattle(){
		energy += 10;
		hp += 20;
		if(energy > 50) energy = 50;
		if(hp > maxHp) hp = maxHp;
	}
	public void attack(int damage, String special){
		hp -= damage;
		if(special.equals("disable")){
			disable = 10;
		}
		else if(special.equals("disable") && disable == 10){
			slowPrint(name + " is already disabled\n");
		}
	}
	public void loseEnergy(int cost){
		energy -= cost;
		if(energy < 0){
			energy = 0;
		}
		else if(energy > 50){
			energy = 50;
		}
	}
	
	public static void slowPrint(String str){
		char[]charArray = str.toCharArray();
		
		for(int i=0;i<charArray.length;i++){
			System.out.print(charArray[i]);
			
			//wait(5); uncomment when done testing
		}
	}
}