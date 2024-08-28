/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CommonMath;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class MathDirector {
    
    public CommonMath doAllMath (ArrayList<Object> objDist, MathBuilderInterface builder) {
        return builder.setDist(objDist).setStats().setName(objDist.get(0).toString()).setN().setMax().setMin().setVar().setMean().setStandartDeviation().setKVar().setGeomMean().setSpan().setCov().setCoinfInt().build();
    }
}
