package com.schibsted.security.labs.dicey.firefox.nvd.impact.v2;

import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.Severity;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2.CvssV2;

public class BaseMetricV2 {
  private final CvssV2 cvssV2;
  private final Severity severity;
  private final double exploitabilityScore;
  private final double impactScore;
  private final boolean acInsufInfo;
  private final boolean obtainAllPrivilege;
  private final boolean obtainUserPrivilege;
  private final boolean obtainOtherPrivilege;
  private final boolean userInteractionRequired;

  public BaseMetricV2(CvssV2 cvssV2,
                      String severity,
                      double exploitabilityScore,
                      double impactScore,
                      boolean acInsufInfo,
                      boolean obtainAllPrivilege,
                      boolean obtainUserPrivilege,
                      boolean obtainOtherPrivilege,
                      boolean userInteractionRequired) {
    this.cvssV2 = cvssV2;
    this.severity = Severity.valueOf(severity);
    this.exploitabilityScore = exploitabilityScore;
    this.impactScore = impactScore;
    this.acInsufInfo = acInsufInfo;
    this.obtainAllPrivilege = obtainAllPrivilege;
    this.obtainUserPrivilege = obtainUserPrivilege;
    this.obtainOtherPrivilege = obtainOtherPrivilege;
    this.userInteractionRequired = userInteractionRequired;
  }

  public CvssV2 cvssV2() {
    return cvssV2;
  }

  public Severity severity() {
    return severity;
  }

  public double exploitabilityScore() {
    return exploitabilityScore;
  }

  public double impactScore() {
    return impactScore;
  }

  public boolean acInsufInfo() {
    return acInsufInfo;
  }

  public boolean obtainAllPrivilege() {
    return obtainAllPrivilege;
  }

  public boolean obtainUserPrivilege() {
    return obtainUserPrivilege;
  }

  public boolean obtainOtherPrivilege() {
    return obtainOtherPrivilege;
  }

  public boolean userInteractionRequired() {
    return userInteractionRequired;
  }
}
