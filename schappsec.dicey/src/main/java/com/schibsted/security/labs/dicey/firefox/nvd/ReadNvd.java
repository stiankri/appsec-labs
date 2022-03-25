package com.schibsted.security.labs.dicey.firefox.nvd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.security.labs.dicey.firefox.Config;
import com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.Severity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadNvd {
  public static void main(String[] args) {
    var reader = new ReadNvd();

    var webGl = reader.allFirefoxNvdItems()
        .stream()
        .filter(m -> m.cve().description().descriptions().stream().anyMatch(f -> f.description().contains("WebGL")))
        .collect(Collectors.toList());

    var webGlCritical = webGl.stream()
        .filter(c -> c.impact().baseMetricV2().get().severity().equals(Severity.HIGH))
        .collect(Collectors.toList());
  }

  public List<NvdItem> allFirefoxNvdItems() {
    var firefoxCves = allNvdPaths()
        .flatMap(f -> read(f).cveItems.stream())
        .filter(ReadNvd::matchesFirefox)
        .sorted(Comparator.comparing(Nvd.CveItem::getPublishedDate).reversed())
        .collect(Collectors.toList());

    return firefoxCves.stream()
        .map(NvdItem::new)
        .collect(Collectors.toList());
  }

  public static Stream<Path> allNvdPaths() {
    try {
      return Files.walk(Paths.get(Config.getUserHome() + "/data/nvd/"))
          .filter(f -> f.getFileName().toString().startsWith("nvdcve-1.1-") && f.toString().endsWith("json"));
    } catch (Exception e) {
      throw new RuntimeException("Unable to list NVD input files");
    }
  }

  public static Nvd read(Path path) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(path.toFile(), Nvd.class);
    } catch (Exception e) {
      throw new RuntimeException(String.format("Unable to read file '%s'", path), e);
    }
  }

  public static boolean matchesFirefox(Nvd.CveItem cveItem) {
    for (var node : cveItem.configurations.nodes) {
      if (matchesFirefox(node)) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchesFirefox(Nvd.CveItem.Configuration.Node node) {
    if (node.children != null) {
      for (var child : node.children) {
        if (matchesFirefox(child)) {
          return true;
        }
      }
    } else {
      if (node.operator.equals("AND") || node.operator.equals("OR")) {
        for (var cpeMatch : node.cpeMatches) {
          if (matchesFirefox(cpeMatch)) {
            return true;
          }
        }
      } else {
        throw new UnsupportedOperationException(node.operator);
      }
    }
    return false;
  }

  public static boolean matchesFirefox(Nvd.CveItem.Configuration.Node.CpeMatch cpeMatch) {
    return cpeMatch.cpe23Uri.startsWith("cpe:2.3:a:mozilla:firefox:")
        && cpeMatch.vulnerable;
  }
}
