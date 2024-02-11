package com.example.imageresize;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiServerProgMain {
	public static void main(String args[]) {
		try {
	    	LocateRegistry.createRegistry(1099);
	    	RmiServerImpl server = new RmiServerImpl();
	        Naming.rebind("rmi://127.0.0.1:1099/SAMPLE-SERVER-ADDV1", server);
	        System.out.println("Server waiting.....");
	    } catch (java.net.MalformedURLException me) {
	         System.out.println("Malformed URL: " + me.toString());
	    } catch (RemoteException re) {
	         System.out.println("Remote exception: " + re.toString());
	    }
	}
}
