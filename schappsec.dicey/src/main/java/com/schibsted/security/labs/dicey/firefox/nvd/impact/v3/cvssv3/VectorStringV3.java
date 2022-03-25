package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2.VectorStringV2;

/**
 * https://www.first.org/cvss/v3.1/specification-document
 * https://www.first.org/cvss/v3.0/specification-document
 */
public class VectorStringV3 {
  private final AttackVector attackVector;
  private final AttackComplexity attackComplexity;
  private final PrivilegesRequired privilegesRequired;
  private final UserInteraction userInteraction;
  private final Scope scope;
  private final ConfidentialityImpact confidentialityImpact;
  private final IntegrityImpact integrityImpact;
  private final AvailabilityImpact availabilityImpact;

  public VectorStringV3(AttackVector attackVector,
                        AttackComplexity attackComplexity,
                        PrivilegesRequired privilegesRequired,
                        UserInteraction userInteraction,
                        Scope scope,
                        ConfidentialityImpact confidentialityImpact,
                        IntegrityImpact integrityImpact,
                        AvailabilityImpact availabilityImpact) {
    this.attackVector = attackVector;
    this.attackComplexity = attackComplexity;
    this.privilegesRequired = privilegesRequired;
    this.userInteraction = userInteraction;
    this.scope = scope;
    this.confidentialityImpact = confidentialityImpact;
    this.integrityImpact = integrityImpact;
    this.availabilityImpact = availabilityImpact;
  }

  public AttackVector getAttackVector() {
    return attackVector;
  }

  public PrivilegesRequired getPrivilegesRequired() {
    return privilegesRequired;
  }

  public AttackComplexity getAttackComplexity() {
    return attackComplexity;
  }

  public UserInteraction getUserInteraction() {
    return userInteraction;
  }

  public Scope getScope() {
    return scope;
  }

  public ConfidentialityImpact getConfidentialityImpact() {
    return confidentialityImpact;
  }

  public IntegrityImpact getIntegrityImpact() {
    return integrityImpact;
  }

  public AvailabilityImpact getAvailabilityImpact() {
    return availabilityImpact;
  }

  public static VectorStringV3 processVectorString(String vector) {
    var map = VectorStringV2.splitVectorString(vector);

    var version = map.get("CVSS");
    if (!(version.equals("3.1") || version.equals("3.0"))) {
      throw new RuntimeException(String.format("Unrecognized version '%s'", version));
    }

    var attackVector = AttackVector.fromValue(map.get("AV"));
    var attackComplexity = AttackComplexity.fromValue(map.get("AC"));
    var privilegesRequired = PrivilegesRequired.fromValue(map.get("PR"));
    var userInteraction = UserInteraction.fromValue(map.get("UI"));
    var scope = Scope.fromValue(map.get("S"));
    var confidentiality = ConfidentialityImpact.fromValue(map.get("C"));
    var integrity = IntegrityImpact.fromValue(map.get("I"));
    var availability = AvailabilityImpact.fromValue(map.get("A"));

    return new VectorStringV3(attackVector,
        attackComplexity,
        privilegesRequired,
        userInteraction,
        scope,
        confidentiality,
        integrity,
        availability);
  }



}
