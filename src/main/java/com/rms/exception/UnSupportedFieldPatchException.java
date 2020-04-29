/**
 * 
 */
package com.rms.exception;

import java.util.Set;

/**
 * @author Kanitta Moonlapong
 *
 */
public class UnSupportedFieldPatchException extends RuntimeException {

    public UnSupportedFieldPatchException(Set<String> keys) {
        super("Value " + keys.toString() + " update is not allow.");
    }

}