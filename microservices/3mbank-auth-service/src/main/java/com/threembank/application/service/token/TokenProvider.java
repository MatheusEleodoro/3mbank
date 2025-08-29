package com.threembank.application.service.token;

import com.threembank.infrastructure.security.user.BasicUserDetails;

public interface TokenProvider {
    TokenResult generate(BasicUserDetails userDetails);
}
