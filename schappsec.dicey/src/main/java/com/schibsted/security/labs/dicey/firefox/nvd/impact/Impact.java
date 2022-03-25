package com.schibsted.security.labs.dicey.firefox.nvd.impact;

import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.BaseMetricV2;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.BaseMetricV3;

import java.util.Optional;

public class Impact {
  private Optional<BaseMetricV3> baseMetricV3;
  private Optional<BaseMetricV2> baseMetricV2;

  public Impact(Optional<BaseMetricV3> baseMetricV3, Optional<BaseMetricV2> baseMetricV2) {
    this.baseMetricV3 = baseMetricV3;
    this.baseMetricV2 = baseMetricV2;
  }

  public Optional<BaseMetricV3> baseMetricV3() {
    return baseMetricV3;
  }

  public Optional<BaseMetricV2> baseMetricV2() {
    return baseMetricV2;
  }
}
