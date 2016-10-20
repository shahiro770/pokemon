
//pokemonArena.java
//Nicholas Culmone

import java.util.*;
import java.io.*;
import java.lang.*;

public class PokemonArena{
	private static ArrayList<Pokemon> goodPoke = new ArrayList<Pokemon>();
	private static ArrayList<Pokemon> pokemons;
	private static ArrayList<Pokemon> battle;
	
	public static int turn;
	public static Random dice = new Random();
	
	public static final int ENEMY = 0;
	public static final int GOOD = 1;
	
	public static void main(String[]args) throws IOException{
		Scanner kb = new Scanner(System.in);
		Scanner pokemonFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		
		pokemons = new ArrayList<Pokemon>();
		battle = new ArrayList<Pokemon>();
		
		int num = Integer.parseInt(pokemonFile.nextLine());
		for(int i=0;i<num;i++){
			pokemons.add(new Pokemon(pokemonFile.nextLine()));
		}
		
		ascii("poketext");
		slowPrint("\n");
		ascii("slogan");
		enter();
		
		while(goodPoke.size()<4){
			space();
			String[]nums = {"First","Second","Third","Fourth"};
			
			slowPrint("Choose Your "+nums[goodPoke.size()]+" Pokemon:\n\n");
			for(int i=0;i<pokemons.size();i++){
				slowPrint((i+1)+". "+pokemons.get(i).getName()+"\n");
			}
			
			slowPrint("\nEnter Pokemon you want (1-" + pokemons.size() + "): ");
			int pNum = kb.nextInt();
			kb.nextLine();
			
			if(pNum <= pokemons.size() && pNum > 0){
			
				space();
				
				ascii(pokemons.get(pNum-1).getName());				
				printStats(pokemons.get(pNum-1));
				printAttacks(pokemons.get(pNum-1));
				
				slowPrint("\n\nAre you sure you want "+pokemons.get(pNum-1).getName()+" on your team? (y/n): ");
				String choice = kb.next();
				kb.nextLine();
				
				if(choice.equals("y")){
					goodPoke.add(pokemons.get(pNum-1));
					pokemons.remove(pNum-1);
				}
			}
			else{
				space();
				slowPrint("Invalid Pokemon, choose a Pokemon betwee 1-"+pokemons.size());
				enter();
			}
		}
			
		int ePoke = 12; //dice.nextInt(pokemons.size());
		battle.add(pokemons.get(ePoke));
		pokemons.remove(ePoke);
		
		//====================================
		//BATTLE
		while(goodPoke.size() > 0 && pokemons.size() > 0){
			space();
			slowPrint("An Enemy Appears!\n");
			enter();
			space();
						
			String[]user = {"stats",""};
			String str = "";
			
			while(user[0].equals("stats")){
				ascii(battle.get(ENEMY).getName());					
				printStats(battle.get(ENEMY));
				printAttacks(battle.get(ENEMY));
				
				slowPrint("\n\nYour Pokemon: ");
				
				for(int i=0;i<goodPoke.size();i++){
					slowPrint("\n   " + (i+1) + "." + goodPoke.get(i).getName()+" ");
				}
				
				slowPrint("\n\nSelect your Pokemon (1-" + goodPoke.size() +
					") or check a Pokemon's stats with 'stats (1-" + goodPoke.size() + ")': ");
					
				str = kb.nextLine();
				user = str.split(" ");
				
				//error where it would skip the kb.nextLine() for some reason; quick fix
				if(str.equals("")){
					str = kb.nextLine();
					user = str.split(" ");
				}
				
				if(user[0].equals("stats")){
					space();
					int p = Integer.parseInt(user[1])-1;
					
					if(p >= 0 && p < goodPoke.size()){
						ascii(goodPoke.get(p).getName());
						printStats(goodPoke.get(p));
						printAttacks(goodPoke.get(p));
					}
					else{
						slowPrint("Invalid Pokemon, choose a Pokemon betwee 1-"+goodPoke.size());
					}
					
					enter();
					space();
				}
				else{
					int p = Integer.parseInt(str);
					if(p < 1 || p > goodPoke.size()){
						space();
						user[0] = "stats";
						slowPrint("Invalid Pokemon, choose a Pokemon between 1-"+goodPoke.size());
						enter();
						space();
					}
				}
			}
			
			int gPoke = Integer.parseInt(str)-1;
			
			battle.add(goodPoke.get(gPoke));
			goodPoke.remove(gPoke);
			space();
			
			ascii(battle.get(GOOD).getName());
			slowPrint("\n\n						" + battle.get(GOOD).getName() + " VS. " + battle.get(ENEMY).getName() + "\n\n");
			ascii(battle.get(ENEMY).getName());
			
			slowPrint("\n"+battle.get(GOOD).getName() + ", I choose you!");
			enter();
			
			turn = dice.nextInt(2);
						
			while(battle.get(GOOD).getHp() > 0 && battle.get(ENEMY).getHp() > 0){
				
				
				if(turn % 2 == 0){
					space();
					ascii(battle.get(GOOD).getName());
					printStats(battle.get(GOOD));
					slowPrint("\n\nChoose an action:\n   1.Attack\n   2.Retreat\n   3.Pass\n\nChoose your action (1-3): ");
					int action = kb.nextInt();
					space();
					
					if(action == 1){
						ascii(battle.get(GOOD).getName());
						printStats(battle.get(GOOD));
						slowPrint("\n\n" + battle.get(GOOD).getName() + "'s available attacks:\n");
						
						int count = 0;
						for(int i=0;i<battle.get(GOOD).getNumAttacks();i++){
							if(battle.get(GOOD).getEnergy() >= battle.get(GOOD).getAttCost(i)){
								count += 1;
								slowPrint("\n      " + count + ". ");
								battle.get(GOOD).getAttack(i).printAttack();
							}
						}
						if(count == 0){
							slowPrint("\nThere are no attacks that " + battle.get(GOOD).getName() + " can use at the moment.");
							enter();
						}
						else{
							slowPrint("\nChoose an attack (1-" + battle.get(GOOD).getNumAttacks() + "): ");
							int att = kb.nextInt();
							int tot = 0,soFar = 0;
							
							//requires usage of brain
							for(int i=0;i<battle.get(GOOD).getNumAttacks();i++){
								if(battle.get(GOOD).getEnergy() >= battle.get(GOOD).getAttCost(i)){
									soFar += 1;
								}
								if(att == soFar){
									attack(battle.get(GOOD),battle.get(ENEMY),battle.get(GOOD).getAttack(att-1));
									break;
								}
								tot += 1;
							}
							if(att != soFar){
								slowPrint("Invalid attack, choose an attack between (1-" + battle.get(GOOD).getNumAttacks() + ")");
								enter();
							}
						}
					}
					
					
					else if(action == 2){
						ascii(battle.get(GOOD).getName());
						slowPrint("\n\nYour Pokemon:\n");
						
						for(int i=0;i<goodPoke.size();i++){
							slowPrint("   " + (i+1) + ". " + goodPoke.get(i).getName() + "\n");
						}
						slowPrint("\nChoose a Pokemon to take " + battle.get(GOOD).getName() + "'s place in battle (1-" + goodPoke.size() + "): ");
						int newPoke = kb.nextInt();
						space();
						
						if(newPoke > 0 && newPoke <= goodPoke.size()){
							goodPoke.add(battle.get(GOOD));
							battle.set(GOOD,goodPoke.get(newPoke-1));
							goodPoke.remove(newPoke-1);
							
							ascii(battle.get(GOOD).getName());
							slowPrint("\n"+battle.get(GOOD).getName() + ", I choose you!");
							turn += 1;
						}
						else slowPrint("Invalid Pokemon, choose an Pokemon between 1-" + goodPoke.size());
						
						enter();
					}
					
					
					else if(action == 3){
						slowPrint("You passed your attack.");
						turn += 1;
						enter();
					}
					else{
						slowPrint("Invalid action, choose an action between 1-3");
						enter();
					}
				}
				
				
				
				//ENEMY ATTACK
				else{
					boolean canAttack = false;
					
					for(int i=0;i<battle.get(ENEMY).getNumAttacks();i++){
						if(battle.get(ENEMY).getEnergy() >= battle.get(ENEMY).getAttCost(i)){
							canAttack = true;
						}
					}
										
					if(canAttack == false){
						space();
						ascii(battle.get(ENEMY).getName());
						printStats(battle.get(ENEMY));
						slowPrint("\n\n" + battle.get(ENEMY).getName() + " had to pass their attack because they were too lazy.");
						turn += 1;
						enter();
					}
					
					while(canAttack == true){
						int attack = dice.nextInt(battle.get(ENEMY).getNumAttacks());
						
						if(battle.get(ENEMY).getEnergy() >= battle.get(ENEMY).getAttCost(attack)){
							canAttack = false;
							
							attack(battle.get(ENEMY), battle.get(GOOD), battle.get(ENEMY).getAttack(attack));
						}
					}
					
					//round ending energy heal
					for(int i=0;i<goodPoke.size();i++) goodPoke.get(i).loseEnergy(-10);
					battle.get(GOOD).loseEnergy(-10);
					battle.get(ENEMY).loseEnergy(-10);
				}
				
			}
			space();
			
			if(battle.get(GOOD).getHp() <= 0){
				ascii(battle.get(ENEMY).getName());
				slowPrint("\n\n" + battle.get(ENEMY).getName() + " has defeated your Pokemon " + battle.get(GOOD).getName());
				battle.get(ENEMY).endBattle();
				
				battle.remove(GOOD);
				enter();
			}
			
			else {
				ascii(battle.get(GOOD).getName());
				slowPrint("\n\nYour Pokemon " + battle.get(GOOD).getName() + " has BRUTALLY MURDERED " + battle.get(ENEMY).getName() +
					"\n(...that's what the kids these days want right?)");
				
				goodPoke.add(battle.get(GOOD));
				battle.remove(GOOD);
				battle.remove(ENEMY);
				
				for(int i=0;i<goodPoke.size();i++){
					goodPoke.get(i).endBattle();
				}
				
				ePoke = dice.nextInt(pokemons.size());
				battle.add(pokemons.get(ePoke));
				pokemons.remove(ePoke);
				
				enter();
			}
			
			
			
			
			
			
			
		}
		space();
		
		if(goodPoke.size() == 0){
			slowPrint("You have lost, the enemy team still had " + pokemons.size() + " Pokemon remaining.\n");
			for(int i=0;i<pokemons.size();i++){
				slowPrint("\n   " + (i+1) + ". " + pokemons.get(i).getName());
			}
			enter();
		}
		else{
			slowPrint("Congratulations, you won by killing everything in your path!");
			enter();
		}
		ascii("gameover");		
		
		
		
	}
	
	public static void slowPrint(String str){
		char[]charArray = str.toCharArray();
		
		for(int i=0;i<charArray.length;i++){
			System.out.print(charArray[i]);
			
			wait(5); //uncomment when done testing
		}
	}	
	
	
	public static void ascii(String name) throws IOException{
		String fName = name+".txt";
		Scanner asciiFile = new Scanner(new BufferedReader(new FileReader(fName)));
				
		while(asciiFile.hasNextLine()){
			asciiPrint(asciiFile.nextLine());
		}
	}
	
	
	public static void asciiPrint(String str){
		char[]charArray = str.toCharArray();
		
		for(int i=0;i<charArray.length;i++){
			System.out.print(charArray[i]);
			
			wait(1); //uncomment when done testing
		}
		System.out.println("");
	}	
	
	
	
	public static void wait(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void printStats(Pokemon poke){
		slowPrint("\n   Name: "+poke.getName()+"\n   HP: "+poke.getHp()+
				"\n   Type: "+poke.getType()+"\n   Resistance: "+poke.getResistance()+
				"\n   Weakness: "+poke.getWeakness() + "\n   Energy: "+poke.getEnergy());
	}
	
	public static void printAttacks(Pokemon poke){
		for(int i=0;i<poke.getNumAttacks();i++){
			slowPrint("\n\n      "+ (i+1) +". "+ poke.getAttName(i) +"\n      Energy Cost: "+ poke.getAttCost(i) +
				"\n      Damage: "+ poke.getAttDamage(i) +"\n      Special: "+ poke.getAttSpecial(i));
		}
	}
		
	public static void space(){
		for(int i=0;i<60;i++){
			System.out.println("");
		}
	}
	
	public static void enter(){
		Scanner kb = new Scanner(System.in);
		slowPrint("\n\nPress Enter to continue...");
		kb.nextLine();
	}
	
	public static void attack(Pokemon attacker, Pokemon hit, Attack atk) throws IOException{
		
		space();
		ascii(attacker.getName());
		
		attacker.loseEnergy(atk.getCost());
		printStats(attacker);
		attacker.loseEnergy(atk.getCost()*(-1));
		slowPrint("\n\n" + attacker.getName() + " attacks with " + atk.getName() + "!\n\n");
		
		int atkRand = dice.nextInt(2);
		int totalAttack;
		
		if(hit.getWeakness().equals(attacker.getType())) totalAttack = (atk.getDamage() - attacker.getDisable()) * 2;
		else if(hit.getResistance().equals(attacker.getType())) totalAttack = (atk.getDamage() - attacker.getDisable()) / 2;
		else totalAttack = atk.getDamage() - attacker.getDisable();
		
		
		if(totalAttack < 0){
			totalAttack = 0;
		}
		
		if(atk.getSpecial().equals("recharge")){
			int oldEnergy = attacker.getEnergy();
			attacker.loseEnergy(-20);
			int gained = attacker.getEnergy() - oldEnergy; //The recharge may max out Energy, so it wont always recharge 20 Energy
			slowPrint(attacker.getName() + " recharged " + gained + " Energy after their attack.\n");
		}
		
		if(atkRand == 1 && atk.getSpecial().equals("stun")){
			turn += 1;
			slowPrint("The attack stunned " + hit.getName() + ", and they will miss their next turn (but their aim is gettin' better).\n");
		}
		
		//==================
		
		if(atkRand == 1 && atk.getSpecial().equals("disable")){
			hit.attack(totalAttack,"disable");
			slowPrint("The attack disabled " + hit.getName() + ".\nThe attack dealt " + totalAttack + " HP of damage.");
			enter();
			turn += 1;
		}
		else if((atk.getSpecial().equals("wild card") || atk.getSpecial().equals("wild storm")) && atkRand == 0){
			slowPrint("The attack missed!");
			enter();
			turn += 1;
		}
		else if(atk.getSpecial().equals("wild storm") && atkRand == 1){
			hit.attack(totalAttack,"");
			slowPrint("The attack was a Wild Storm and will be used again.\nThe attack dealt " + totalAttack + " HP of damage.");
			enter();
			attack(attacker,hit,atk);
		}
		else{
			hit.attack(totalAttack,"");
			slowPrint("The attack dealt " + totalAttack + " HP of damage.");
			enter();
			turn += 1;
		}
		attacker.loseEnergy(atk.getCost());
		//=================
	}
}