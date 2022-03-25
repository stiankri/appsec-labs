package com.schibsted.security.labs.jack.gui;

import com.schibsted.security.labs.jack.ProcessNode;

import java.util.*;

public class NodeExtent {
  Map<ProcessNode, Double> widths = new HashMap<>();
  Map<ProcessNode, Double> xs = new HashMap<>();
  Map<ProcessNode, Double> ys = new HashMap<>();

  private double OFFSET_Y = .25;
  private double SCALE = 7;
  private double width;
  private double height;
  List<List<ProcessNode>> nodesPerLevel = new ArrayList<>();

  double WIDTH = 1280;
  private double NODE_WIDTH = 100 / WIDTH;
  private double NODE_HEIGHT = 100 / WIDTH;

  public NodeExtent(ProcessNode root) {
    width = dfsComputeWidths(root, 0) / SCALE;

    computePositions(root, 0, 0);
  }

  private void computePositions(ProcessNode node, double x, double y) {
    xs.put(node, x);
    ys.put(node, y);

    if (y > height) {
      height = y;
    }

    var width = widths.get(node) / SCALE;
    double currentLeft = x - width / 2.0;

    for (var child : node.children) {
      var childWidth = widths.get(child);
      var position = currentLeft + childWidth / (2.0 * SCALE);
      currentLeft += childWidth / SCALE;
      computePositions(child, position, y + OFFSET_Y);
    }
  }

  public double getX(ProcessNode node) {
    return xs.get(node);
  }

  public double getY(ProcessNode node) {
    return ys.get(node);
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public Optional<ProcessNode> selectNode(double x, double y) {
    var residual = y % OFFSET_Y;
    if (y > 0 && y < (height + NODE_HEIGHT) && residual < NODE_HEIGHT) {
      int band = (int) Math.round((y - residual) / OFFSET_Y);

      return selectNode(x, band);
    }
    return Optional.empty();
  }

  public Optional<ProcessNode> selectNode(double x, int level) {
    List<ProcessNode> nodesInLevel = nodesPerLevel.get(level);

    // TODO cleanup
    // TODO implement binary search
    Optional<ProcessNode> result = Optional.empty();
    for (var node : nodesInLevel) {
      var left = xs.get(node);
      var right = left + NODE_WIDTH;
      if (x >= left && x <= right) {
        result = Optional.of(node);
        break;
      }
    }

    return result;
  }

  private double dfsComputeWidths(ProcessNode node, int level) {
    if (nodesPerLevel.size() < level + 1) {
      nodesPerLevel.add(new ArrayList<>());
    }
    nodesPerLevel.get(level).add(node);

    double totalWidth = 0;
    for (var child : node.children) {
      totalWidth += dfsComputeWidths(child, level + 1);
    }

    if (totalWidth == 0) {
      totalWidth = 1;
    }

    widths.put(node, totalWidth);

    return totalWidth;
  }
}
