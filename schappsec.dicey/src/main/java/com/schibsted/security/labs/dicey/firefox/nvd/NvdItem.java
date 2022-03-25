package com.schibsted.security.labs.dicey.firefox.nvd;

import com.schibsted.security.labs.dicey.firefox.nvd.cve.Cve;
import com.schibsted.security.labs.dicey.firefox.nvd.cve.description.Description;
import com.schibsted.security.labs.dicey.firefox.nvd.cve.description.DescriptionEntry;
import com.schibsted.security.labs.dicey.firefox.nvd.cve.problemtype.ProblemType;
import com.schibsted.security.labs.dicey.firefox.nvd.cve.problemtype.ProblemTypeEntry;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.Impact;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2.CvssV2;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2.VectorStringV2;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3.CvssV3;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3.VectorStringV3;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.BaseMetricV2;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.BaseMetricV3;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class NvdItem {
  private final Cve cve;
  private final Impact impact;
  private final ZonedDateTime publishedDate;
  private final ZonedDateTime lastModifiedDate;

  public NvdItem(Nvd.CveItem item) {
    verifyOrThrow("CVE", item.cve.dataType);
    verifyOrThrow("MITRE", item.cve.dataFormat);
    verifyOrThrow("4.0", item.cve.dataVersion);
    verifyOrThrow("cve@mitre.org", item.cve.metadata.assigner);

    this.cve = cve(item.cve);

    this.impact = new Impact(baseMetricV3(item.impact), baseMetricV2(item.impact));
    this.publishedDate = ZonedDateTime.parse(item.publishedDate);
    this.lastModifiedDate = ZonedDateTime.parse(item.lastModifiedDate);
  }

  public Cve cve() {
    return cve;
  }

  public Impact impact() {
    return impact;
  }

  public ZonedDateTime publishedDate() {
    return publishedDate;
  }

  public ZonedDateTime lastModifiedDate() {
    return lastModifiedDate;
  }

  private Cve cve(Nvd.CveItem.Cve cve) {

    // TODO verify format of id
    var id = cve.metadata.id;

    var entries = cve.description.descriptionData.stream()
        .map(e -> new DescriptionEntry(e.lang, e.value))
        .collect(Collectors.toList());

    var problemTypeEntries = new ArrayList<ProblemTypeEntry>();
    for (var problemTypeData : cve.problemType.problemTypeData) {
      var descriptions = problemTypeData.descriptions.stream()
          .map(d -> new com.schibsted.security.labs.dicey.firefox.nvd.cve.problemtype.Description(d.lang, d.value))
          .collect(Collectors.toList());

      problemTypeEntries.add(new ProblemTypeEntry(descriptions));
    }

    return new Cve(id, new ProblemType(problemTypeEntries), new Description(entries));
  }

  private Optional<BaseMetricV3> baseMetricV3(Nvd.CveItem.Impact impact) {
    if (impact.baseMetricV3 == null) {
      return Optional.empty();
    }

    var vector = VectorStringV3.processVectorString(impact.baseMetricV3.cvssV3.vectorString);
    var cvssV3 = new CvssV3(vector.getAttackVector(),
        vector.getAttackComplexity(),
        vector.getPrivilegesRequired(),
        vector.getUserInteraction(),
        vector.getScope(),
        vector.getConfidentialityImpact(),
        vector.getIntegrityImpact(),
        vector.getAvailabilityImpact(),
        impact.baseMetricV3.cvssV3.baseScore,
        impact.baseMetricV3.cvssV3.baseSeverity);

    var baseMetricV3 = new BaseMetricV3(cvssV3,
        impact.baseMetricV3.exploitabilityScore,
        impact.baseMetricV3.impactScore);

    return Optional.of(baseMetricV3);
  }

  private Optional<BaseMetricV2> baseMetricV2(Nvd.CveItem.Impact impact) {
    if (impact.baseMetricV2 == null) {
      return Optional.empty();
    }

    verifyOrThrow("2.0", impact.baseMetricV2.cvssV2.version);

    var vector = VectorStringV2.processVectorString(impact.baseMetricV2.cvssV2.vectorString);

    var cvssV2 = new CvssV2(vector.getAccessVector(),
        vector.getAccessComplexity(),
        vector.getAuthentication(),
        vector.getConfidentialityImpact(),
        vector.getIntegrityImpact(),
        vector.getAvailabilityImpact(),
        impact.baseMetricV2.cvssV2.baseScore);

    var baseMetricV2 = new BaseMetricV2(cvssV2,
        impact.baseMetricV2.severity,
        impact.baseMetricV2.exploitabilityScore,
        impact.baseMetricV2.impactScore,
        impact.baseMetricV2.acInsufInfo,
        impact.baseMetricV2.obtainAllPrivilege,
        impact.baseMetricV2.obtainUserPrivilege,
        impact.baseMetricV2.obtainOtherPrivilege,
        impact.baseMetricV2.userInteractionRequired);

    return Optional.of(baseMetricV2);
  }


  private void verifyOrThrow(String expected, String actual) {
    if (!expected.equals(actual)) {
      throw new IllegalArgumentException(String.format("expected '%s', got '%s'", expected, actual));
    }
  }
}
