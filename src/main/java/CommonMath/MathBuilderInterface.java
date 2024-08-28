/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package CommonMath;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public interface MathBuilderInterface {
    MathBuilderInterface setDist(ArrayList<Object> objDist);
    MathBuilderInterface setKVar();
    MathBuilderInterface setCoinfInt();
    MathBuilderInterface setCov();
    MathBuilderInterface setVar();
    MathBuilderInterface setN();
    MathBuilderInterface setSpan();
    MathBuilderInterface setMax();
    MathBuilderInterface setMin();
    MathBuilderInterface setStandartDeviation();
    MathBuilderInterface setMean();
    MathBuilderInterface setGeomMean();
    MathBuilderInterface setStats();
    MathBuilderInterface setName(String name);
    CommonMath build();
}
