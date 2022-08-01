package com.baixingkefu.spatialx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;
import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

import com.baixingkefu.spatialx.TwoTuple;

import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Line;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.MultiPoint;
import com.esri.core.geometry.Operator;
import com.esri.core.geometry.OperatorBoundary;
import com.esri.core.geometry.OperatorFactoryLocal;
import com.esri.core.geometry.OperatorImportFromWkb;
import com.esri.core.geometry.OperatorSimplify;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.WkbImportFlags;
import com.esri.core.geometry.WktExportFlags;
import com.esri.core.geometry.WktImportFlags;
import com.esri.core.geometry.ogc.OGCGeometry;
import com.esri.core.geometry.ogc.OGCLineString;
import com.esri.core.geometry.ogc.OGCPolygon;

/**
 * Used at run time for cover alg Alg Class for solving max
 * cover problem {@link #ST_MaxCover} functions.
 */
public class MaxCoverAlg {
  public static double calFitness(int[] chrom, double[][] disMatrix, int cnum,
      double[] d, double[] c, double r) {
    double disSum = Double.MAX_VALUE;
    int count = 0;
    boolean flag = false;
    int[] decChrom = new int[chrom.length];
    System.arraycopy(chrom, 0, decChrom, 0, chrom.length);

    ArrayList<ArrayList<Integer>> dList = new ArrayList<ArrayList<Integer>>();
    IntStream.range(0, cnum).forEach(i -> dList.add(new ArrayList<Integer>()));

    ArrayList<Integer> decList = new ArrayList<Integer>();
    IntStream.range(0, cnum).forEach(i -> {
      if (chrom[i] == 1) {
        decList.add(i);
      }
    });

    int[] weight = new int[cnum];
    int[] demand = new int[cnum];

    for (int i = cnum; i < decChrom.length; i++) {
      if (disMatrix[i - cnum][decChrom[i]] <= r) {
        decChrom[i] = (int) decList.get(decChrom[i] - 1);
        dList.get(decChrom[i]).add(i - cnum);
        weight[decChrom[i]] += disMatrix[i - cnum][decChrom[i]];
        demand[decChrom[i]] += d[i - cnum];
      } else {
        count += 1;
      }
    }

    for (int i = 0; !flag && i < demand.length; i++) {
      if (demand[i] > c[i]) {
        flag = true;
      }
    }
    if (!flag) {
      disSum = Arrays.stream(weight).sum();
    }

    return count * 10000 + disSum;
  }

  public static TwoTuple<Double, int[]> traversalSearch(int[] chrom,
      double[][] disMatrix, ArrayList<int[]> tabuList, int cnum, double[] d, double[] c,
      int p, double r) {
    int traversal = 0;
    int traversalMax = 100;
    Random rand = new Random();
    ArrayList<int[]> traversal_list = new ArrayList<int[]>();
    ArrayList<Double> traversal_value = new ArrayList<Double>();
    while (traversal <= traversalMax) {
      int[] newChrom = new int[chrom.length];
      System.arraycopy(chrom, 0, newChrom, 0, chrom.length);
      int pos1 = cnum + rand.nextInt(cnum);
      int pos2 = cnum + rand.nextInt(cnum);
      int tmp = newChrom[pos1];
      newChrom[pos1] = newChrom[pos2];
      newChrom[pos2] = tmp;

      pos1 = cnum + rand.nextInt(chrom.length - cnum);
      pos2 = cnum + rand.nextInt(chrom.length - cnum);
      tmp = newChrom[pos1];
      newChrom[pos1] = newChrom[pos2];
      newChrom[pos2] = tmp;

      for (int i = cnum; i < chrom.length; i++) {
        if (rand.nextDouble() > 0.75) {
          newChrom[i] = rand.nextInt(p) + 1;
        }
      }
      double new_value = calFitness(newChrom, disMatrix, cnum, d, c, r);

      if (!traversal_list.contains(newChrom) && !tabuList.contains(newChrom)) {
        traversal_list.add(newChrom);
        traversal_value.add(new_value);
        traversal += 1;
      }

    }
    double traversalMinValue = traversal_value.stream().max(Double::compare).get();
    int traversalIdx = traversal_value.indexOf(traversalMinValue);

    return new TwoTuple<Double, int[]>(traversalMinValue,
        traversal_list.get(traversalIdx));
  }

  public static int[] initialize(int dnum, int cnum, int p) {
    Random rand = new Random(666);
    int[] chrom = new int[dnum + cnum];
    ArrayList<Integer> candidates = new ArrayList<Integer>();
    IntStream.range(0, cnum).forEach(i -> candidates.add(i));

    for (int i = 0; i < p; i++) {
      int selected_idx = rand.nextInt(cnum - i);
      int selected = candidates.get(selected_idx);
      chrom[selected] = 1;
      candidates.remove(selected_idx);
    }
    for (int i = cnum; i < dnum + cnum; i++) {
      chrom[i] = rand.nextInt(p) + 1;
    }

    return chrom;
  }

  public static MultiPoint main(MultiPoint centerPoints, MultiPoint demandPoints,
      String demands, String capabilities, int p, double r) {

    int cnum = centerPoints.getPointCount();
    double[][] centerCoordinates = new double[cnum][2];
    for (int i = 0; i < cnum; i++) {
      Point p_ = centerPoints.getPoint(i);
      centerCoordinates[i][0] = p_.getX();
      centerCoordinates[i][1] = p_.getY();
    }

    int dnum = demandPoints.getPointCount();
    double[][] demandCoordinates = new double[dnum][2];
    for (int i = 0; i < dnum; i++) {
      Point p_ = demandPoints.getPoint(i);
      demandCoordinates[i][0] = p_.getX();
      demandCoordinates[i][1] = p_.getY();
    }

    int tabuLimit = 30;
    int iterMax = 50;
    int num = 20;

    ArrayList<int[]> tabuList = new ArrayList<int[]>();
    ArrayList<Integer> tabuTime = new ArrayList<Integer>();

    double[] d = new double[dnum];
    if (StringUtils.isBlank(demands)) {
      for (int i = 0; i < dnum; i++) {
        d[i] = 1.0;
      }
    } else {
      String[] str_d_list = demands.split(" ");
      for (int i = 0; i < dnum; i++) {
        d[i] = Double.parseDouble(str_d_list[i]);
      }
    }
    double[] c = new double[cnum];
    if (StringUtils.isBlank(capabilities)) {
      for (int i = 0; i < cnum; i++) {
        c[i] = 1000.0;
      }
    } else {
      String[] str_c_list = capabilities.split(" ");
      for (int i = 0; i < cnum; i++) {
        c[i] = Double.parseDouble(str_c_list[i]);
      }
    }

    double[][] disMatrix = new double[dnum][cnum];

    for (int i = 0; i < dnum; i++) {
      double xi = demandCoordinates[i][0];
      double yi = demandCoordinates[i][1];
      for (int j = 0; j < cnum; j++) {
        double xj = centerCoordinates[j][0];
        double yj = centerCoordinates[j][1];
        disMatrix[i][j] = Math.sqrt(Math.pow(xi - xj, 2) + Math.pow(yi - yj, 2));
      }
    }

    ArrayList<int[]> chroms = new ArrayList<int[]>();
    ArrayList<Double> values = new ArrayList<Double>();
    int[] bestChrom = initialize(dnum, cnum, p);
    double bestValue = calFitness(bestChrom, disMatrix, cnum, d, c, r);
    for (int i = 0; i < num; i++) {
      int[] chrom = initialize(dnum, cnum, p);
      chroms.add(chrom);
      double value = calFitness(chrom, disMatrix, cnum, d, c, r);
      values.add(value);
      if (value < bestValue) {
        bestValue = value;
        bestChrom = chrom;
      }
    }
    ArrayList<Double> bestValueList = new ArrayList<Double>();
    bestValueList.add(bestValue);
    tabuList.add(bestChrom);
    tabuTime.add(tabuLimit);

    int[] chrom = bestChrom;
    int iter = 0;
    while (iter <= iterMax) {
      TwoTuple<Double, int[]> res = traversalSearch(chrom, disMatrix, tabuList, cnum,
          d, c, p, r);
      double newValue = res.getFirst();
      int[] newChrom = res.getSecond();
      if (newValue < bestValue) {
        bestValue = newValue;
        bestChrom = newChrom;
        bestValueList.add(bestValue);
      }
      chrom = newChrom;
      int index = -1;
      for (int t : tabuTime) {
        index += 1;
        tabuTime.set(index, t - 1);
      }
      if (tabuTime.contains(0)) {
        tabuList.remove(tabuTime.indexOf(0));
        tabuTime.remove(tabuTime.indexOf(0));
      }
      tabuList.add(chrom);
      tabuTime.add(tabuLimit);
      iter += 1;
    }
    MultiPoint pnRes = new MultiPoint();
    for (int i = 0; i < cnum; i++) {
      if (bestChrom[i] == 1) {
        pnRes.add(centerPoints.getPoint(i));
      }
    }
    return pnRes;

  }
}