/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sem6lab2;

import CommonMath.*;
import Gui.*;

/**
 *
 * @author User
 */
public class main {

    public static void main(String[] args) {
        Xlsx xlsxworker = new Xlsx();
        MathDirector director = new MathDirector();
        Gui gui = new Gui(director, xlsxworker);
        gui.setVisible(true);
    }
}
