package org.brittlawson.exception;

public class DropzoneNotFoundException extends Exception {

    public DropzoneNotFoundException() {
        super("Dropzone not found");
    }

}
