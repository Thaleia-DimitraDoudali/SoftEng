package net.java.sip.communicator.sip.security;

public class OnlyLogin implements RegistrationState{
	SecurityAuthority authority;

	public OnlyLogin(SecurityAuthority auth) {
		authority = auth;
	}

	@Override
	public UserCredentials getCredentials(String realm,
			UserCredentials defaultValues) {
		return authority.obtainCredentials(realm, defaultValues);
	}
}
