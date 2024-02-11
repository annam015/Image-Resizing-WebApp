package com.example.imageresize;

import java.rmi.*;

public interface RmiServerInterface extends Remote {
    byte[] resizePhoto(int resizePercentage, byte[] imageBytes) throws RemoteException;
}