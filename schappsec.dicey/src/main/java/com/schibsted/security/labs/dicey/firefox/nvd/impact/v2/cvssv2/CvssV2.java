package com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2;

public class CvssV2 {
  private final AccessVector accessVector;
  private final AccessComplexity accessComplexity;
  private final Authentication authentication;
  private final ConfidentialityImpact confidentialityImpact;
  private final IntegrityImpact integrityImpact;
  private final AvailabilityImpact availabilityImpact;
  private final double baseScore;

  public CvssV2(AccessVector accessVector,
                AccessComplexity accessComplexity,
                Authentication authentication,
                ConfidentialityImpact confidentialityImpact,
                IntegrityImpact integrityImpact,
                AvailabilityImpact availabilityImpact,
                double baseScore) {
    this.accessVector = accessVector;
    this.accessComplexity = accessComplexity;
    this.authentication = authentication;
    this.confidentialityImpact = confidentialityImpact;
    this.integrityImpact = integrityImpact;
    this.availabilityImpact = availabilityImpact;
    this.baseScore = baseScore;
  }

  public AccessVector accessVector() {
    return accessVector;
  }

  public AccessComplexity accessComplexity() {
    return accessComplexity;
  }

  public Authentication authentication() {
    return authentication;
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
}
