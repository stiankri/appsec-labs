package com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.first.org/cvss/v2/guide
 */
public class VectorStringV2 {
  private final AccessVector accessVector;
  private final AccessComplexity accessComplexity;
  private final Authentication authentication;
  private final ConfidentialityImpact confidentialityImpact;
  private final IntegrityImpact integrityImpact;
  private final AvailabilityImpact availabilityImpact;

  public VectorStringV2(AccessVector accessVector,
                        AccessComplexity accessComplexity,
                        Authentication authentication,
                        ConfidentialityImpact confidentialityImpact,
                        IntegrityImpact integrityImpact,
                        AvailabilityImpact availabilityImpact) {
    this.accessVector = accessVector;
    this.accessComplexity = accessComplexity;
    this.authentication = authentication;
    this.confidentialityImpact = confidentialityImpact;
    this.integrityImpact = integrityImpact;
    this.availabilityImpact = availabilityImpact;
  }

  public AccessVector getAccessVector() {
    return accessVector;
  }

  public AccessComplexity getAccessComplexity() {
    return accessComplexity;
  }

  public Authentication getAuthentication() {
    return authentication;
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

  public static Map<String, String> splitVectorString(String vector) {
    var parts = vector.split("/");

    var map = new HashMap<String, String>();

    for (var part : parts) {
      var p = part.split(":");
      var key = p[0];
      var value = p[1];
      map.put(key, value);
    }

    return map;
  }

  public static VectorStringV2 processVectorString(String vector) {
    var map = splitVectorString(vector);

    var accessVector = AccessVector.fromValue(map.get("AV"));
    var accessComplexity = AccessComplexity.fromValue(map.get("AC"));
    var authentication = Authentication.fromValue(map.get("Au"));
    var confidentiality = ConfidentialityImpact.fromValue(map.get("C"));
    var integrity = IntegrityImpact.fromValue(map.get("I"));
    var availability = AvailabilityImpact.fromValue(map.get("A"));

    return new VectorStringV2(accessVector,
        accessComplexity,
        authentication,
        confidentiality,
        integrity,
        availability);
  }
}
