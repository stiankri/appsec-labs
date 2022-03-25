package com.schibsted.security.labs.dicey.firefox.risk;

import com.schibsted.security.labs.dicey.firefox.nvd.NvdItem;
import com.schibsted.security.labs.dicey.firefox.nvd.ReadNvd;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.Severity;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class BasicBeta {

  public static void main(String[] args) {
    var basicBeta = new BasicBeta();
    basicBeta.stats();
  }

  public void stats() {
    var startDate = LocalDate.of(2011, 3, 3);
    var nvdLatestDataDate = LocalDate.of(2020, 6, 13);
    var totalMonths = Period.between(startDate, nvdLatestDataDate).toTotalMonths();

    var nvdReader = new ReadNvd();
    var allFirefoxItems = nvdReader.allFirefoxNvdItems();

    var webGlVulnerabilities = webGlHighSeverity(allFirefoxItems);
    var betaDistribution = new BetaDistribution(1 + webGlVulnerabilities,
        1 + totalMonths - webGlVulnerabilities);

    var p05 = betaDistribution.inverseCumulativeProbability(.05);
    var p95 = betaDistribution.inverseCumulativeProbability(.95);

    System.out.println("== WebGL in Firefox ==");
    System.out.println(String.format("90%% confidence interval for high severity (CVSS v2) per month: between %.2f and %.2f",
        p05,
        p95));

    System.out.println(String.format("Which means that we expect there to be a vulnerability every %.1f to %.1f months",
        1.0 / p95,
        1.0 / p05));

    var totalHighSeverity = allHighSeverity(allFirefoxItems, startDate);
    // assuming total number of flaws is so much larger it can be viewed as a single value
    // could also compute vulnerabilities per month for entire dataset
    System.out.println(String.format("Disabling it should reduce the risk of exposure by %.0f%% to %.0f%%",
        p05 * totalMonths / totalHighSeverity * 100,
        p95 * totalMonths / totalHighSeverity * 100));
  }

  public long webGlHighSeverity(List<NvdItem> allFirefoxItems) {
    return allFirefoxItems.stream()
        .filter(m -> m.cve().description().descriptions().stream().anyMatch(f -> f.description().contains("WebGL")))
        .filter(c -> c.impact().baseMetricV2().get().severity().equals(Severity.HIGH))
        .count();
  }

  public long allHighSeverity(List<NvdItem> allFirefoxItems, LocalDate startDate) {
    return allFirefoxItems.stream()
        .filter(c -> c.publishedDate().toLocalDate().isAfter(startDate))
        .filter(c -> c.impact().baseMetricV2().get().severity().equals(Severity.HIGH))
        .count();
  }
}
