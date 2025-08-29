package com.threembank.interfaces.valueobject;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Error(String version, String root, String origin, Object data) {}
