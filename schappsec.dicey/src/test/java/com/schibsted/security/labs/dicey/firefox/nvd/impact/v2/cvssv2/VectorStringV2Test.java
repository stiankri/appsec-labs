package com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorStringV2Test {
  @Test
  void basic() {
    var vectorString = VectorStringV2.processVectorString("AV:N/AC:M/Au:N/C:C/I:C/A:C");

    assertEquals(AccessVector.NETWORK, vectorString.getAccessVector());
    assertEquals(AccessComplexity.MEDIUM, vectorString.getAccessComplexity());
    assertEquals(Authentication.NONE, vectorString.getAuthentication());
    assertEquals(ConfidentialityImpact.COMPLETE, vectorString.getConfidentialityImpact());
    assertEquals(IntegrityImpact.COMPLETE, vectorString.getIntegrityImpact());
    assertEquals(AvailabilityImpact.COMPLETE, vectorString.getAvailabilityImpact());
  }
}
