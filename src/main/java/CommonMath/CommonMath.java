/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CommonMath;

import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author User
 */
public class CommonMath {
    private final double geomMean;
    private final double mean;
    private final double standartDeviation;
    private final double span;
    private final double Min;
    private final double Max;
    private final double[][] cov;
    private final double N;
    private final double coinfidenceInt;
    private final double var; 
    private final double kvar;
    private final String distName;
    
    public CommonMath (double geomMean,double mean,double standartDeviation,double span,double Min,double Max,double[][] cov,double N,double coinfidenceInt,double var,double kvar, String name){
        this.geomMean=geomMean;
        this.mean=mean;
        this.standartDeviation=standartDeviation;
        this.span=span;
        this.Min=Min;
        this.Max=Max;
        this.cov=cov;
        this.N=N;
        this.coinfidenceInt=coinfidenceInt;
        this.var=var;
        this.kvar=kvar;
        this.distName=name;
    }
    
    public String covMatrixtoString(double[][] covMatrix){
        StringBuilder output = new StringBuilder();
        for (double[] row : covMatrix) {
            for (double value : row) {
                output.append(String.format("%10.7f", value)).append(" "); 
            }
            output.append("\n");
        }
        return output.toString();
    }

    public double getGeomMean() {
        return geomMean;
    }

    public double getMean() {
        return mean;
    }

    public double getStandartDeviation() {
        return standartDeviation;
    }

    public double getSpan() {
        return span;
    }

    public double getMin() {
        return Min;
    }

    public double getMax() {
        return Max;
    }

    public double[][] getCov() {
        return cov;
    }

    public double getN() {
        return N;
    }

    public double getCoinfidenceInt() {
        return coinfidenceInt;
    }

    public double getVar() {
        return var;
    }

    public double getKvar() {
        return kvar;
    }
    
    public String getName() {
        return distName;
    }
    
    public String getStatisticCharact (){
        String staticCharact;
        staticCharact = "Среднее геометрическое"+"\t"+this.geomMean+"\n"+"Оценка стандартного отклонения"+"\t"+this.standartDeviation+"\n"+"Размах"+"\t"+this.span+"\n"+"Количество элементов в выборке"+"\t"+this.N+"\n"+"Коэффициент вариации"+"\t"+this.kvar+"\n"+"Доверительный интервал для мат. ожидания"+"\t"+this.coinfidenceInt+"\n"+"Оценка дисперсии"+"\t"+this.var+"\n"+"Максимум"+"\t"+this.Max+"\n"+"Минимум"+"\t"+this.Min+"\n"+"Коэффициенты ковариации для всех пар случайных чисел"+"\n"+covMatrixtoString(this.cov)+'\n';
        return staticCharact;    
    }
    
}
