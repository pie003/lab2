/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CommonMath;

import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author User
 */
public class MathBuilder implements MathBuilderInterface{
    private double geomMean;
    private double mean;
    private double standartDeviation;
    private double span;
    private double Min;
    private double Max;
    private double[][] cov;
    private double N;
    private double coinfidenceInt;
    private double var; 
    private double kvar;
    private String name;
    
    private DescriptiveStatistics stats;
    private double[] doubleDist;

    @Override
    public MathBuilderInterface setDist(ArrayList<Object> objDist) {
        this.doubleDist = new double[objDist.size()-1];
        for (int i=0; i<objDist.size()-1;i++){
            this.doubleDist[i] = Double.valueOf(objDist.get(i+1).toString());
        }
        return this;
    }

    @Override
    public MathBuilderInterface setKVar() {
        if (mean != 0){
            this.kvar = standartDeviation/mean*100;
        }
        return this;
    }

    @Override
    public MathBuilderInterface setCoinfInt() {        
        NormalDistribution normDist = new NormalDistribution(mean,standartDeviation);
        coinfidenceInt = normDist.inverseCumulativeProbability(0.975)*this.standartDeviation/Math.sqrt(this.N);
        return this;
    }

    @Override
    public MathBuilderInterface setCov() {
        double[][] realMatrix = new double[this.doubleDist.length][this.doubleDist.length];
        for (int i=0; i<this.doubleDist.length;i++){
                realMatrix[i][i] = this.doubleDist[i];
        }
        Covariance Cov = new Covariance(realMatrix);
        cov=Cov.getCovarianceMatrix().getData();
        return this;
    }

    @Override
    public MathBuilderInterface setVar() {
        this.var=stats.getVariance();
        return this;
    }

    @Override
    public MathBuilderInterface setN() {
        this.N=stats.getN();
        return this;
    }

    @Override
    public MathBuilderInterface setSpan() {
        this.span = Max-Min;
        return this;
    }

    @Override
    public MathBuilderInterface setMax() {
        this.Max = stats.getMax();
        return this;
    }

    @Override
    public MathBuilderInterface setMin() {
        this.Min = stats.getMin();
        return this;
    }

    @Override
    public MathBuilderInterface setStandartDeviation() {
        this.standartDeviation = stats.getStandardDeviation();
        return this;
    }

    @Override
    public MathBuilderInterface setMean() {
        this.mean = stats.getMean();
        return this;
    }

    @Override
    public MathBuilderInterface setGeomMean() {
        this.geomMean = stats.getGeometricMean();
        return this;
    }

    @Override
    public MathBuilderInterface setStats() {
        this.stats = new DescriptiveStatistics();
        for (int i=0; i<this.doubleDist.length; i++){
            stats.addValue(this.doubleDist[i]);
        } 
        return this;
    }
    
    @Override
    public MathBuilderInterface setName(String name) {
        this.name=name;
        return this;
    }

    @Override
    public CommonMath build() {
        CommonMath math = new CommonMath(this.geomMean, this.mean, this.standartDeviation, this.span, this.Min, this.Max,this.cov, this.N,this.coinfidenceInt, this.var, this.kvar, this.name);
        return math;
    }

    
            
}
