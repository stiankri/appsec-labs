package com.schibsted.security.labs.dicey.firefox.nvd;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Nvd {
  @JsonProperty("CVE_data_type")
  public String cveDataType;

  @JsonProperty("CVE_data_format")
  public String cveDataFormat;

  @JsonProperty("CVE_data_version")
  public String cveDataVersion;

  @JsonProperty("CVE_data_numberOfCVEs")
  public String cveDataNumberOfCves;

  @JsonProperty("CVE_data_timestamp")
  public String cveDataTimestamp;

  @JsonProperty("CVE_Items")
  public List<CveItem> cveItems;

  public static class CveItem {
    public Cve cve;
    public Configuration configurations;
    public Impact impact;
    public String publishedDate;
    public String lastModifiedDate;

    public String getPublishedDate() {
      return publishedDate;
    }

    public static class Cve {
      @JsonProperty("data_type")
      public String dataType;

      @JsonProperty("data_format")
      public String dataFormat;

      @JsonProperty("data_version")
      public String dataVersion;

      @JsonProperty("CVE_data_meta")
      public CveDataMeta metadata;

      @JsonProperty("problemtype")
      public ProblemType problemType;

      public References references;

      public Description description;

      public static class CveDataMeta {
        @JsonProperty("ID")
        public String id;

        @JsonProperty("ASSIGNER")
        public String assigner;
      }

      public static class ProblemType {
        @JsonProperty("problemtype_data")
        public List<ProblemTypeData> problemTypeData;

        public static class ProblemTypeData {
          @JsonProperty("description")
          public List<Description> descriptions;

          public static class Description {
            public String lang;
            public String value;
          }
        }
      }

      public static class References {
        @JsonProperty("reference_data")
        public List<ReferenceData> referenceData;

        public static class ReferenceData {
          public String url;
          public String name;

          @JsonProperty("refsource")
          public String refSource;

          public List<String> tags;
        }
      }

      public static class Description {
        @JsonProperty("description_data")
        public List<DescriptionData> descriptionData;

        public static class DescriptionData {
          public String lang;
          public String value;
        }
      }
    }

    public static class Configuration {
      @JsonProperty("CVE_data_version")
      public String cveDataVersion;
      public List<Node> nodes;

      public static class Node {
        public String operator;

        public Boolean negate;

        @JsonProperty("cpe_match")
        public List<CpeMatch> cpeMatches;

        public List<Node> children;

        public static class CpeMatch {
          public boolean vulnerable;
          /**
           * a is for application
           * o is for operating system
           */
          public String cpe23Uri;
          public String versionStartIncluding;
          public String versionStartExcluding;
          public String versionEndIncluding;
          public String versionEndExcluding;
        }
      }
    }

    public static class Impact {
      public BaseMetricV3 baseMetricV3;
      public BaseMetricV2 baseMetricV2;

      /**
       * https://www.first.org/cvss/v3.1/specification-document
       */
      public static class BaseMetricV3 {
        public CvssV3 cvssV3;
        public double exploitabilityScore;
        public double impactScore;

        public static class CvssV3 {
          public String version;
          public String vectorString;
          public String attackVector;
          public String attackComplexity;
          public String privilegesRequired;
          public String userInteraction;
          public String scope;
          public String confidentialityImpact;
          public String integrityImpact;
          public String availabilityImpact;
          public double baseScore;
          public String baseSeverity;
        }
      }

      public static class BaseMetricV2 {
        public CvssV2 cvssV2;
        public String severity;
        public double exploitabilityScore;
        public double impactScore;
        public boolean acInsufInfo;
        public boolean obtainAllPrivilege;
        public boolean obtainUserPrivilege;
        public boolean obtainOtherPrivilege;
        public boolean userInteractionRequired;

        public static class CvssV2 {
          public String version;
          public String vectorString;
          public String accessVector;
          public String accessComplexity;
          public String authentication;
          public String confidentialityImpact;
          public String integrityImpact;
          public String availabilityImpact;
          public double baseScore;
        }
      }
    }
  }
}
