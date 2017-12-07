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
 * Contributor(s): Gustavo Henrique.
 */
package server.model;

/**
 *
 * @author gustavo
 */
public class Client {
    
    private String cpf; // CPF of the client;
    private String nome; // Name of the client;
    private String numero; // Number of the client;
    private String posX; // Position in x of the client;
    private String posY; // Position in y of the client;
    private String password; // Password of the client;

    /**
     * Constructor default.
     */
    public Client(){
        
    }
    
    /**
     * Constructor of the class, get the parameters and put in instance.
     * @param cpf - CPF of the client;
     * @param nome - Name of the client;
     * @param numero - Number of the client;
     * @param posX - Position in x of the client;
     * @param posY - Position in y of the client;
     * @param password - Password of the client;
     */
    public Client(String cpf, String nome, String numero, String posX, String posY, String password){
        this.cpf = cpf;
        this.nome = nome;
        this.numero = numero;
        this.posX = posX;
        this.posY = posY;
        this.password = password;
    }
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the posX
     */
    public String getPosX() {
        return posX;
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(String posX) {
        this.posX = posX;
    }

    /**
     * @return the posY
     */
    public String getPosY() {
        return posY;
    }

    /**
     * @param posY the posY to set
     */
    public void setPosY(String posY) {
        this.posY = posY;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
