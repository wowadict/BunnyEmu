package bunnyEmu.main.net.packets.client;

import bunnyEmu.main.entities.character.Char;
import bunnyEmu.main.entities.packet.ClientPacket;
import bunnyEmu.main.net.packets.server.SMSG_MESSAGECHAT;
import bunnyEmu.main.utils.BitUnpack;

public class CMSG_MESSAGECHAT extends ClientPacket {

	private String message;
	private int language;
	
	public boolean readMoP() {
		BitUnpack bitUnpack = new BitUnpack(this);
        language = getInt();
        int messageLength = bitUnpack.GetBits((byte) 8);
        message = getString(messageLength);
        
		return true;
	}
	
	public String getMessage(){
		return message;
	}
	
	public int getLanguage(){
		return language;
	}
	
}
