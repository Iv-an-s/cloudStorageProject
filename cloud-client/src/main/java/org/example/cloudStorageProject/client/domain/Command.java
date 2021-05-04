package org.example.cloudStorageProject.client.domain;

import com.sun.org.apache.xpath.internal.operations.String;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Command implements Serializable {
    private String commandName;
    private String[] args;


}
