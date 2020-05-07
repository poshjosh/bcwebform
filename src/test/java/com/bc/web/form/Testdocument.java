/*
 * Copyright 2019 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bc.web.form;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Chinomso Bassey Ikwuagwu on May 16, 2019 8:52:58 PM
 */
@Table(name = "testdocument")
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Testdocument.findAll", query = "SELECT t FROM Testdocument t"),
    @NamedQuery(name = "Testdocument.findByTestdocumentid", query = "SELECT t FROM Testdocument t WHERE t.testdocumentid = :testdocumentid")})
public class Testdocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer testdocumentid;
    @JoinColumn(name = "documenttest", referencedColumnName = "testid")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Test documenttest;
    @JoinColumn(name = "testdocument", referencedColumnName = "documentid")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Document testdocument;
    @JoinColumn(name = "documentresult", referencedColumnName = "documentid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Document documentresult;

    public Testdocument() {
    }

    public Testdocument(Integer testdocumentid) {
        this.testdocumentid = testdocumentid;
    }

    public Integer getTestdocumentid() {
        return testdocumentid;
    }

    public void setTestdocumentid(Integer testdocumentid) {
        this.testdocumentid = testdocumentid;
    }

    public Test getDocumenttest() {
        return documenttest;
    }

    public void setDocumenttest(Test documenttest) {
        this.documenttest = documenttest;
    }

    public Document getTestdocument() {
        return testdocument;
    }

    public void setTestdocument(Document testdocument) {
        this.testdocument = testdocument;
    }

    public Document getDocumentresult() {
        return documentresult;
    }

    public void setDocumentresult(Document documentresult) {
        this.documentresult = documentresult;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testdocumentid != null ? testdocumentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testdocument)) {
            return false;
        }
        Testdocument other = (Testdocument) object;
        if ((this.testdocumentid == null && other.testdocumentid != null) || (this.testdocumentid != null && !this.testdocumentid.equals(other.testdocumentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bc.elmi.pu.entities.Testdocument[ testdocumentid=" + testdocumentid + " ]";
    }

}
