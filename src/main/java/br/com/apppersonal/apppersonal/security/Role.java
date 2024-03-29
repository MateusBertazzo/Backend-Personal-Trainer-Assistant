package br.com.apppersonal.apppersonal.security;


public enum Role {
    ADMIN("ROLE_ADMIN"),
    PERSONAL("ROLE_PERSONAL"),
    USER("ROLE_USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAuthority() {
        return this.getName();
    }
}
