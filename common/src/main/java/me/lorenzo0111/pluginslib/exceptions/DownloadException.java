package me.lorenzo0111.pluginslib.exceptions;

import me.lorenzo0111.pluginslib.dependency.objects.Dependency;

/**
 * Exception caused when dependency download went wrong
 */
public class DownloadException extends RuntimeException {

    /**
     * @param dependency dependency
     * @param message message of the exception
     */
    public DownloadException(Dependency dependency, String message) {
        super(String.format("An error has occurred while downloading %s: %s", dependency.getArtifact(), message));
    }

}
