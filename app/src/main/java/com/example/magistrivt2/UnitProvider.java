package com.example.magistrivt2;

public class UnitProvider {

	public static Unit instance(String unitType, int x, int y, Game game) {
		if(unitType=="Hoplit") {
			return new Hoplit(x,y,game);
		}else if(unitType=="Archer") {
			return new Archer(x,y,game);
		}else if(unitType=="Eagle") {
			return new Eagle(x,y,game);
		}else if(unitType=="Ballista") {
			return new Ballista(x,y,game);
		}
		return null;
	}

}
