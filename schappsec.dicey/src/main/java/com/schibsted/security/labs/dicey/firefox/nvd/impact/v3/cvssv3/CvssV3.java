package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

public class CvssV3 {
  private final AttackVector attackVector;
  private final AttackComplexity attackComplexity;
  private final PrivilegesRequired privilegesRequired;
  private final UserInteraction userInteraction;
  private final Scope scope;
  private final ConfidentialityImpact confidentialityImpact;
  private final IntegrityImpact integrityImpact;
  private final AvailabilityImpact availabilityImpact;
  private final double baseScore;
  private final BaseSeverity baseSeverity;

  public CvssV3(AttackVector attackVector,
                AttackComplexity attackComplexity,
                PrivilegesRequired privilegesRequired,
                UserInteraction userInteraction,
                Scope scope,
                ConfidentialityImpact confidentialityImpact,
                IntegrityImpact integrityImpact,
                AvailabilityImpact availabilityImpact,
                double baseScore,
                String baseSeverity) {
    this.attackVector = attackVector;
    this.attackComplexity = attackComplexity;
    this.privilegesRequired = privilegesRequired;
    this.userInteraction = userInteraction;
    this.scope = scope;
    this.confidentialityImpact = confidentialityImpact;
    this.integrityImpact = integrityImpact;
    this.availabilityImpact = availabilityImpact;
    this.baseScore = baseScore;
    this.baseSeverity = BaseSeverity.valueOf(baseSeverity);
  }

  public AttackVector attackVector() {
    return attackVector;
  }

  public AttackComplexity attackComplexity() {
    return attackComplexity;
  }

  public PrivilegesRequired privilegesRequired() {
    return privilegesRequired;
  }

  public UserInteraction userInteraction() {
    return userInteraction;
  }

  public Scope scope() {
    return scope;
  }

  public ConfidentialityImpact confidentialityImpact() {
    return confidentialityImpact;
  }

  public IntegrityImpact integrityImpact() {
    return integrityImpact;
  }

  public AvailabilityImpact availabilityImpact() {
    return availabilityImpact;
  }

  public double baseScore() {
    return baseScore;
  }

  public BaseSeverity baseSeverity() {
    return baseSeverity;
  }
}
