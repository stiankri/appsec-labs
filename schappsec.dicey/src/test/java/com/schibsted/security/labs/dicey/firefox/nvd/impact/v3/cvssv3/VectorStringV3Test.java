package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorStringV3Test {
  @Test
  void basic31() {
    var vectorString = VectorStringV3.processVectorString("CVSS:3.1/AV:L/AC:L/PR:L/UI:N/S:U/C:H/I:H/A:H");

    assertEquals(AttackVector.LOCAL, vectorString.getAttackVector());
    assertEquals(AttackComplexity.LOW, vectorString.getAttackComplexity());
    assertEquals(PrivilegesRequired.LOW, vectorString.getPrivilegesRequired());
    assertEquals(UserInteraction.NONE, vectorString.getUserInteraction());
    assertEquals(Scope.UNCHANGED, vectorString.getScope());
    assertEquals(ConfidentialityImpact.HIGH, vectorString.getConfidentialityImpact());
    assertEquals(IntegrityImpact.HIGH, vectorString.getIntegrityImpact());
    assertEquals(AvailabilityImpact.HIGH, vectorString.getAvailabilityImpact());
  }

  @Test
  void basic30() {
    var vectorString = VectorStringV3.processVectorString("CVSS:3.0/AV:L/AC:L/PR:L/UI:N/S:U/C:N/I:H/A:N");

    assertEquals(AttackVector.LOCAL, vectorString.getAttackVector());
    assertEquals(AttackComplexity.LOW, vectorString.getAttackComplexity());
    assertEquals(PrivilegesRequired.LOW, vectorString.getPrivilegesRequired());
    assertEquals(UserInteraction.NONE, vectorString.getUserInteraction());
    assertEquals(Scope.UNCHANGED, vectorString.getScope());
    assertEquals(ConfidentialityImpact.NONE, vectorString.getConfidentialityImpact());
    assertEquals(IntegrityImpact.HIGH, vectorString.getIntegrityImpact());
    assertEquals(AvailabilityImpact.NONE, vectorString.getAvailabilityImpact());
  }
}
