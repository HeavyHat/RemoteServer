package remoteServer;

import java.io.Serializable;


//Creates a class of objects that implements the empty serializable interface so this object can be sent accross the network
public class Command implements Serializable {	//TODO: Research Serializable interface
	
	public int command;
	public int parameter;
	public String metadata;
	public String[] programArray;
	
	public Command(int commandin,int parameterin,String metadatain,int arraySize) {
		this.command = commandin;
		this.parameter = parameterin;
		this.metadata = metadatain;
		this.programArray = new String[arraySize];					
	}
}
