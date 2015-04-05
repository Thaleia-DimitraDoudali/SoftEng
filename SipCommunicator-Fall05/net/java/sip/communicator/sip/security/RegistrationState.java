package net.java.sip.communicator.sip.security;

public interface RegistrationState {
	 /**
     * Returns a Credentials object associated with the specified realm.
     * @param realm The realm that the credentials are needed for.
     * @param defaultValues the values to propose the user by default
     * @return The credentials associated with the specified realm or null if
     * none could be provided.
     */
    public UserCredentials getCredentials(String realm, UserCredentials defaultValues);
}
