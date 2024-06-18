package com.example.javanetworking.StudentServerInterface;

import java.rmi.*;
public interface StudentServerInterface extends Remote {
    public double findScore(String name) throws RemoteException;
}
