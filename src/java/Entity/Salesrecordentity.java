/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jiayu
 */
@Entity
@Table(name = "salesrecordentity")

public class Salesrecordentity implements Serializable{
 
//    private Salesrecordentity  salesrecordentity;
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
  //  @NotNull
 
@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "AMOUNTDUE")
    private double amountdue;
    @Column(name = "AMOUNTPAID")
    private double amoutPaid;
    @Column(name = "AMOUNTPAIDUSINGPOINTS")
    private double amountPaidUsingPoints;   
    @Column(name = "CREATEDDATE")
     @Temporal(TemporalType.DATE)
     private Date createdDate;
    @Column(name = "CURRENCY")
     private String currency;
    @Column(name = "LOYALTYPOINTSDEDUCTED")
     private int loyaltyPointsDeducted;
    @Column(name = "POSNAME")
    private String posName;
    @Column(name = "RECEIPTNO")
    private String receiptNo;
    @Column(name = "SERVEDBYSTAFF")
     private String servedByStaff;
    
    @OneToMany
   
      @JoinTable
  (
      name="salesrecordentity_lineitementity",
      joinColumns={ @JoinColumn(name="SalesRecordEntity_ID") },
      inverseJoinColumns={ @JoinColumn(name="itemsPurchased_ID")  }
  )

    private List<Lineitementity> lineitementityList;
    
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "ID")
    @ManyToOne 
    private  Memberentity  memberId;
     @JoinColumn(name = "STORE_ID", referencedColumnName = "ID")
    @ManyToOne 
    private Storeentity storeId;
    

 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmountdue() {
        return amountdue;
    }

    public void setAmountdue(double amountdue) {
        this.amountdue = amountdue;
    }

    public double getAmoutPaid() {
        return amoutPaid;
    }

    public void setAmoutPaid(double amoutPaid) {
        this.amoutPaid = amoutPaid;
    }

    public double getAmountPaidUsingPoints() {
        return amountPaidUsingPoints;
    }

    public void setAmountPaidUsingPoints(double amountPaidUsingPoints) {
        this.amountPaidUsingPoints = amountPaidUsingPoints;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getLoyaltyPointsDeducted() {
        return loyaltyPointsDeducted;
    }

    public void setLoyaltyPointsDeducted(int loyaltyPointsDeducted) {
        this.loyaltyPointsDeducted = loyaltyPointsDeducted;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getServedByStaff() {
        return servedByStaff;
    }

    public void setServedByStaff(String servedByStaff) {
        this.servedByStaff = servedByStaff;
    }


    @Override
    public String toString() {
        return "Salesrecordentity{" + "id=" + id + '}';
    }
     
   
     @XmlTransient

    public Memberentity getMemberId() {
        return memberId;
    }

    public void setMemberId(Memberentity memberId) {
        this.memberId = memberId;
    }
    
        public Storeentity getStoreId() {
        return storeId;
    }

    public void setStoreId(Storeentity storeId) {
        this.storeId = storeId;
    }
    
    public List<Lineitementity> getLineitementityList() {
        return lineitementityList;
    }
    
    
}
