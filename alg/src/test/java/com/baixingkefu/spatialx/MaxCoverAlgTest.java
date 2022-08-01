package com.baixingkefu.spatialx;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.util.Random;
import java.security.MessageDigest;
import com.baixingkefu.spatialx.MaxCoverAlg;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MaxCoverAlgTest {
  /**
   * Rigorous Test :-)
   */
  @Test
  public void shouldAnswerWithTrue() {
    assertTrue(true);
  }

  @Test
  public void testMaxCoverAlgSpeed() {

    Date start_time = new Date();

    int tabu_limit = 100;
    int iterMax = 20;
    int p = 8;
    int dnum = 10000; // demandCoordinates.length;
    int cnum = 10000; // centerCoordinates.length;
    double r = 10;
    int num = 20;

    ArrayList<int[]> tabu_list = new ArrayList<int[]>();
    ArrayList<Integer> tabu_time = new ArrayList<Integer>();
    // double best_value = Double.MAX_VALUE;

    double[][] demandCoordinates = new double[dnum][2];
    for (int i = 0; i < dnum; i++) {
      demandCoordinates[i][0] = Math.random() * 100;
      demandCoordinates[i][1] = Math.random() * 100;
    }

    double[][] centerCoordinates = new double[cnum][2];
    for (int i = 0; i < cnum; i++) {
      centerCoordinates[i][0] = Math.random() * 100;
      centerCoordinates[i][1] = Math.random() * 100;
    }
    double[] d = new double[dnum];
    for (int i = 0; i < dnum; i++) {
      d[i] = Math.random() * 7;
    }
    double[] c = new double[cnum];
    for (int i = 0; i < cnum; i++) {
      c[i] = 300;
    }

    double[][] dis_matrix = new double[dnum][cnum];

    for (int i = 0; i < dnum; i++) {
      double xi = demandCoordinates[i][0];
      double yi = demandCoordinates[i][1];
      for (int j = 0; j < cnum; j++) {
        double xj = centerCoordinates[j][0];
        double yj = centerCoordinates[j][1];
        dis_matrix[i][j] = Math.sqrt(Math.pow(xi - xj, 2) + Math.pow(yi - yj, 2));
      }
    }

    Date middle_time = new Date();
    ArrayList<int[]> chroms = new ArrayList<int[]>();
    ArrayList<Double> values = new ArrayList<Double>();
    double best_value = Double.MAX_VALUE;
    int[] best_chrom = MaxCoverAlg.initialize(dnum, cnum, p);
    for (int i = 0; i < num; i++) {
      int[] chrom = MaxCoverAlg.initialize(dnum, cnum, p);
      chroms.add(chrom);
      double value = MaxCoverAlg.calFitness(chrom, dis_matrix, cnum, d, c, r);
      values.add(value);
      if (value < best_value) {
        best_value = value;
        best_chrom = chrom;
      }
    }
    ArrayList<Double> best_value_list = new ArrayList<Double>();
    best_value_list.add(best_value);
    tabu_list.add(best_chrom);
    tabu_time.add(tabu_limit);

    int[] chrom = best_chrom;
    int itera = 0;
    while (itera <= iterMax) {
      TwoTuple<Double, int[]> res = MaxCoverAlg.traversalSearch(chrom, dis_matrix, tabu_list, cnum, d, c, p, r);
      double new_value = res.getFirst();
      int[] new_chrom = res.getSecond();
      if (new_value < best_value) {
        best_value = new_value;
        best_chrom = new_chrom;
        best_value_list.add(best_value);
        System.out.println("更新最优值");
      }
      chrom = new_chrom;
      // value = new_value;
      int index = -1;
      for (int t : tabu_time) {
        index += 1;
        tabu_time.set(index, t - 1);
      }
      if (tabu_time.contains(0)) {
        tabu_list.remove(tabu_time.indexOf(0));
        tabu_time.remove(tabu_time.indexOf(0));
      }
      tabu_list.add(chrom);
      tabu_time.add(tabu_limit);
      itera += 1;
    }
    Date end_time = new Date();

    System.out.println(end_time.getTime() - start_time.getTime());
    assertTrue(end_time.getTime() - start_time.getTime() < 4000);
  }
}
