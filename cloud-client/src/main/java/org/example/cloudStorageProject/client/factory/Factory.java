package org.example.cloudStorageProject.client.factory;

import org.example.cloudStorageProject.client.service.NetworkService;
import org.example.cloudStorageProject.client.service.impl.IONetworkService;

public class Factory {

    public static NetworkService getNetworkService(){
        return IONetworkService.getInstance();
    }
}
