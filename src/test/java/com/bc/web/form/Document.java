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
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Chinomso Bassey Ikwuagwu on May 16, 2019 8:52:58 PM
 */
@Table(name = "document")
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByDocumentid", query = "SELECT d FROM Document d WHERE d.documentid = :documentid"),
    @NamedQuery(name = "Document.findByDocumentname", query = "SELECT d FROM Document d WHERE d.documentname = :documentname"),
    @NamedQuery(name = "Document.findBySubject", query = "SELECT d FROM Document d WHERE d.subject = :subject"),
    @NamedQuery(name = "Document.findByDatesigned", query = "SELECT d FROM Document d WHERE d.datesigned = :datesigned"),
    @NamedQuery(name = "Document.findByReferencenumber", query = "SELECT d FROM Document d WHERE d.referencenumber = :referencenumber"),
    @NamedQuery(name = "Document.findByLocation", query = "SELECT d FROM Document d WHERE d.location = :location"),
    @NamedQuery(name = "Document.findByTimecreated", query = "SELECT d FROM Document d WHERE d.timecreated = :timecreated"),
    @NamedQuery(name = "Document.findByTimemodified", query = "SELECT d FROM Document d WHERE d.timemodified = :timemodified")})
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer documentid;
    @Size(max = 128)
    private String documentname;
    @Size(max = 512)
    private String subject;
    @Temporal(TemporalType.DATE)
//    @javax.persistence.Convert(converter=com.bc.jpa.dateconverter.DateConverterJpa.class)
    private Date datesigned;
    @Size(max = 256)
    private String referencenumber;
    //@Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 512)
    private String location;
    @Basic(optional = false)
    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
//    @javax.persistence.Convert(converter=com.bc.jpa.dateconverter.DateConverterJpa.class)
    private Date timecreated;
    @Basic(optional = false)
    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
//    @javax.persistence.Convert(converter=com.bc.jpa.dateconverter.DateConverterJpa.class)
    private Date timemodified;
    @OneToMany(mappedBy = "parentdocument", fetch = FetchType.LAZY)
    private List<Document> documentList;
    @JoinColumn(name = "parentdocument", referencedColumnName = "documentid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Document parentdocument;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "testdocument", fetch = FetchType.LAZY)
    private Testdocument testdocument;
    @OneToMany(mappedBy = "documentresult", fetch = FetchType.LAZY)
    private List<Testdocument> testdocumentList;

    public Document() {
    }

    public Document(Integer documentid) {
        this.documentid = documentid;
    }

    public Document(Integer documentid, String location, Date timecreated, Date timemodified) {
        this.documentid = documentid;
        this.location = location;
        this.timecreated = timecreated;
        this.timemodified = timemodified;
    }

    public Integer getDocumentid() {
        return documentid;
    }

    public void setDocumentid(Integer documentid) {
        this.documentid = documentid;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDatesigned() {
        return datesigned;
    }

    public void setDatesigned(Date datesigned) {
        this.datesigned = datesigned;
    }

    public String getReferencenumber() {
        return referencenumber;
    }

    public void setReferencenumber(String referencenumber) {
        this.referencenumber = referencenumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Date timecreated) {
        this.timecreated = timecreated;
    }

    public Date getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(Date timemodified) {
        this.timemodified = timemodified;
    }

    @XmlTransient
    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Document getParentdocument() {
        return parentdocument;
    }

    public void setParentdocument(Document parentdocument) {
        this.parentdocument = parentdocument;
    }

    public Testdocument getTestdocument() {
        return testdocument;
    }

    public void setTestdocument(Testdocument testdocument) {
        this.testdocument = testdocument;
    }

    @XmlTransient
    public List<Testdocument> getTestdocumentList() {
        return testdocumentList;
    }

    public void setTestdocumentList(List<Testdocument> testdocumentList) {
        this.testdocumentList = testdocumentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentid != null ? documentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.documentid == null && other.documentid != null) || (this.documentid != null && !this.documentid.equals(other.documentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bc.elmi.pu.entities.Document[ documentid=" + documentid + " ]";
    }

}
