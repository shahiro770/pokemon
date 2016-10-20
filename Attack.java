//Attack.java
//Nicholas Culmone

public class Attack{
	private String name,special;
	private int cost,damage;
		
	public Attack(String[]stats){
		name = stats[0];
		cost = Integer.parseInt(stats[1]);
		damage = Integer.parseInt(stats[2]);
		special = stats[3];
	}
	
	public String getName(){
		return name;
	}
	public int getCost(){
		return cost;
	}
	public int getDamage(){
		return damage;
	}
	public String getSpecial(){
		return special;
	}
	//Get Statements
	
	public void printAttack(){
		slowPrint(name +"\n      Energy Cost: "+ cost +
				"\n      Damage: "+ damage +"\n      Special: "+ special + "\n");
	}
	
	public static void slowPrint(String str){
		char[]charArray = str.toCharArray();
		
		for(int i=0;i<charArray.length;i++){
			System.out.print(charArray[i]);
			
			//wait(5); uncomment when done testing
		}
	}
}