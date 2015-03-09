package se.jaygames.derelict.object.weapon;

import se.fredin.gdxtensions.input.BaseInput;
import se.fredin.gdxtensions.object.weapon.SemiAutomaticWeapon;

public class PeaShooter extends SemiAutomaticWeapon {

	public PeaShooter(BaseInput input) {
		super(0, 17, SHOOTING_INTERVAL_DEFAULT / 4f, input);
	}

	
}
