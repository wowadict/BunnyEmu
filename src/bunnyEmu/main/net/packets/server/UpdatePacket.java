package bunnyEmu.main.net.packets.server;

import bunnyEmu.main.entities.character.Char;
import bunnyEmu.main.entities.packet.ServerPacket;
import bunnyEmu.main.utils.BitPack;
import bunnyEmu.main.utils.Log;
import bunnyEmu.main.utils.types.MovementSpeed;
import bunnyEmu.main.utils.types.MovementValues;
import bunnyEmu.main.utils.types.UpdateFlag;

/**
 * Adapted from Arctium, currently only used in the initial update packet.
 * 
 * @author Marijn
 * 
 */
public class UpdatePacket extends ServerPacket {

	public UpdatePacket(String sOpcode, int size) {
		super(sOpcode, size);
	}

	protected void writeUpdateObjectMovementMoP(Char character, byte updateFlags) {
		MovementSpeed movementSpeed = character.getMovement();
		MovementValues values = new MovementValues(updateFlags);

		BitPack bitPack = new BitPack(this);

		bitPack.setGuid(character.getGUID());

		/* TODO: comment these */
		bitPack.write(0);
		bitPack.write(values.BitCounter, 22);
		bitPack.write(values.IsVehicle);
		bitPack.write(0);
		bitPack.write(values.HasGoTransportPosition);
		bitPack.write(0);
		bitPack.write(0);
		bitPack.write(0);
		bitPack.write(0);
		bitPack.write(0);
		bitPack.write(false);	// is this a gameobject
		bitPack.write(values.HasTarget);
		bitPack.write(0);
		bitPack.write(0);
		bitPack.write(values.IsSelf);
		bitPack.write(0);
		bitPack.write(values.IsAlive);
		bitPack.write(0);
		bitPack.write(0);
		bitPack.write(values.HasAnimKits);
		bitPack.write(values.HasStationaryPosition);


		if (values.IsAlive) {

			bitPack.writeGuidMask(new byte[] { 0 });
			bitPack.write(1);
			bitPack.writeGuidMask(new byte[] { 4, 7 });
			bitPack.write(1);
			bitPack.writeGuidMask(new byte[] { 5, 2 });
			bitPack.write(0);
			bitPack.write(1);
			bitPack.write(0);
			bitPack.write(0);
			bitPack.write(0);
			bitPack.write(!values.HasRotation);
			bitPack.write(values.IsTransport);
			bitPack.write(1);
			bitPack.writeGuidMask(new byte[] { 6 });
			bitPack.write(0, 19);
			bitPack.writeGuidMask(new byte[] { 1 });
			bitPack.write(1);
			bitPack.writeGuidMask(new byte[] { 3 });
			bitPack.write(0, 22);
			bitPack.write(0);
			bitPack.write(0);
		}

		bitPack.flush();


		if (values.IsAlive) {

			packet.putInt(0);
			bitPack.writeGuidBytes(new byte[] { 2, 1 });
			
			packet.putFloat((float) movementSpeed.WalkSpeed);
			packet.putFloat((float) movementSpeed.RunSpeed);
			
			bitPack.writeGuidBytes(new byte[] { 0, 3 });
			
			packet.putFloat((float) movementSpeed.SwimBackSpeed);
			packet.putFloat((float) movementSpeed.TurnSpeed);
			
			bitPack.writeGuidBytes(new byte[] { 5 });
			
			packet.putFloat(character.getPosition().getZ());
			packet.putFloat(character.getPosition().getO());

			bitPack.writeGuidBytes(new byte[] { 6 });

			packet.putFloat((float) movementSpeed.PitchSpeed);
			packet.putFloat((float) movementSpeed.RunBackSpeed);
			
			packet.putFloat(character.getPosition().getY());
			
			packet.putFloat((float) movementSpeed.SwimSpeed);
			packet.putFloat((float) movementSpeed.FlyBackSpeed);
			
			bitPack.writeGuidBytes(new byte[] { 7 });

			packet.putFloat((float) movementSpeed.FlySpeed);
			packet.putFloat(character.getPosition().getX());

			bitPack.writeGuidBytes(new byte[] { 4 });
		}

		
		if (values.HasStationaryPosition) {

			packet.putFloat(character.getPosition().getX());
			packet.putFloat(character.getPosition().getZ());
			packet.putFloat(0);	// orientation
			packet.putFloat(character.getPosition().getY());
		}

		// TODO: implement?
		//if (values.HasRotation)
		//	packet.putLong(0);
		// packet.writeInt64(Quaternion.GetCompressed(wObject.Position.O));
	}
	

	/**
	 * TODO: Make it working
	 */
	protected void writeUpdateObjectMovementCata(Char character, short updateFlags) {
		MovementSpeed movementSpeed = character.getMovement();
		this.putShort(updateFlags);  // update flags              
		MovementValues values = new MovementValues(updateFlags);

		short moveFlags = 0;
		
	    if (values.IsAlive){
	    	// Build Movement
	    	this.putInt(0); // flags2
	        this.putShort(moveFlags);      		// movement flags
	        this.putInt((int) 0);				// time (in milliseconds)
	        
	        packet.putFloat(character.getPosition().getX());
	        packet.putFloat(character.getPosition().getY());
	        packet.putFloat(character.getPosition().getZ());
	        packet.putFloat(0); // orientation
	        
	        // Movement 2
			packet.putFloat((float) movementSpeed.WalkSpeed);
			packet.putFloat((float) movementSpeed.RunSpeed);
			packet.putFloat((float) movementSpeed.RunBackSpeed); // walk/run back ?
			packet.putFloat((float) movementSpeed.SwimSpeed);
			packet.putFloat((float) movementSpeed.SwimBackSpeed);
			packet.putFloat((float) movementSpeed.FlySpeed);
			packet.putFloat((float) movementSpeed.FlyBackSpeed);
	        packet.putFloat((float) movementSpeed.TurnSpeed);
	        packet.putFloat((float) movementSpeed.PitchSpeed);
	    }
		
	}
	
	/**
	 * TODO: make it working
	 */
	protected void writeUpdateObjectMovementWotLK(Char character, short updateFlags) {
		MovementSpeed movementSpeed = character.getMovement();
		this.putShort((short) 0x71);  // update flags              
		MovementValues values = new MovementValues(updateFlags);
		
		Log.log("VALUES: ");
		Log.log("test: " + values.IsSelf);
		Log.log("test: " + values.IsAlive);
		Log.log("test: " + values.HasRotation);
		Log.log("test: " + values.HasStationaryPosition);
		Log.log("test: " + values.HasTarget);
		Log.log("test: " + values.IsTransport);
		Log.log("test: " + values.HasGoTransportPosition);
		Log.log("test: " + values.HasAnimKits);
		Log.log("test: " + values.HasUnknown);
		Log.log("test: " + values.HasUnknown2);
		Log.log("test: " + values.HasUnknown4);
	    if (values.IsAlive){
	    	// Build Movement
	    	this.putInt(0); // flags2
	        this.putShort((short) 0);      		// movement flags
	        this.putInt((int) 0);				// time (in milliseconds) TODO: actual time?
	        
	        packet.putFloat(character.getPosition().getX());
	        packet.putFloat(character.getPosition().getY());
	        packet.putFloat(character.getPosition().getZ());
	        packet.putFloat(0); // orientation
	        
	        packet.putInt(0); // Fall time
	        /* 32 bytes so far */
	        // Movement 2
	        
			packet.putFloat((float) movementSpeed.WalkSpeed);
			packet.putFloat((float) movementSpeed.RunSpeed);
			packet.putFloat((float) movementSpeed.RunBackSpeed); // walk/run back ?
			packet.putFloat((float) movementSpeed.SwimSpeed);
			packet.putFloat((float) movementSpeed.SwimBackSpeed);
			packet.putFloat((float) movementSpeed.FlySpeed);
			packet.putFloat((float) movementSpeed.FlyBackSpeed);
	        packet.putFloat((float) movementSpeed.TurnSpeed);
	        packet.putFloat((float) movementSpeed.PitchSpeed);
	        
	        /* 32+36 = 68 bytes so far */
	    }
	}

}
