/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 */
package server.controller;

/**
 * This controller control the connections of the server with the clients and
 * deposits.
 *
 * @author Gustavo Henrique
 */
public class Controller {

    /* Instace static for establish the design patter Singleton */
    private static Controller instance;
    
    /* Statement of variables */
    
    private final String TOKENSEPARATOR = "!=";
    private final int LENGTHSERVERPROTOCOL = 4;
    private final int LENGTHPROTOCOL = 2;

    private int posX;
    private int posY;

    /* Turn the constructor in private for use only an instance of the class */
    private Controller() {

    }

    /**
     * Return the instance of the class.
     * @return instance - Instance of the class.
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Reset the instance of controller.
     */
    public void resetController() {
        instance = null;
    }

    /**
     * Return the TOKENSEPARATOR.
     * @return the TOKENSEPARATOR
     */
    public String getTOKENSEPARATOR() {
        return TOKENSEPARATOR;
    }

    /**
     * Return the position X.
     * @return the posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Set the position X.
     * @param posX the posX to set
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Return the position Y.
     * @return the posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set the position Y.
     * @param posY the posY to set
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
}
