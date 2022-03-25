package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3;

import com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3.CvssV3;

public class BaseMetricV3 {
  private final CvssV3 cvssV3;
  private final double exploitabilityScore;
  private final double impactScore;

  public BaseMetricV3(CvssV3 cvssV3, double exploitabilityScore, double impactScore) {
    this.cvssV3 = cvssV3;
    this.exploitabilityScore = exploitabilityScore;
    this.impactScore = impactScore;
  }

  public CvssV3 cvssV3() {
    return cvssV3;
  }

  public double exploitabilityScore() {
    return exploitabilityScore;
  }

  public double impactScore() {
    return impactScore;
  }
}
