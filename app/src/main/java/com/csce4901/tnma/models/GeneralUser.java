package com.csce4901.tnma.models;

import java.io.Serializable;

public class GeneralUser extends User implements Serializable {
    public GeneralUser(String email, boolean roleVerified) {
        super(email, roleVerified);
    }
    public GeneralUser() {
    }
}
