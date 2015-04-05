package net.java.sip.communicator.sip.security;

public class RegisterAndLogin implements RegistrationState {
	SecurityAuthority authority;

	public RegisterAndLogin(SecurityAuthority auth) {
		authority = auth;
	}

	@Override
	public UserCredentials getCredentials(String realm,
			UserCredentials defaultValues) {
		return authority.obtainCredentialsAndRegister();
	}
}