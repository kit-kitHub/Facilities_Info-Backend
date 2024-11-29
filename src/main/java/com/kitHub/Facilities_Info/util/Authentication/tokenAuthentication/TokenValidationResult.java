package com.kitHub.Facilities_Info.util.Authentication.tokenAuthentication;

public enum TokenValidationResult {
    VALID,
    EXPIRED,
    SIGNATURE_INVALID,
    MALFORMED,
    UNSUPPORTED,
    INVALID,
    MISSING
}