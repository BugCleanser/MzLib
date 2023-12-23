package mz.mzlib.network;

import java.nio.ByteBuffer;

public abstract class Packet
{
	public abstract String getId();
	
	public abstract void deserialize(ByteBuffer buffer,int length);
	public abstract ByteBuffer serialize();
	
}