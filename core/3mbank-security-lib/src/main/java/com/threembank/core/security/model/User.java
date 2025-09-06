package com.threembank.core.security.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;


public record User(UUID id, String username, Set<GrantedAuthority> authorities)  {}
