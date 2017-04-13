package hbi.core.bom.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "bom_wnc_user")
public class WncUser extends BaseDTO {
     @Id
     @GeneratedValue
      private Long wncUserId;

     @NotEmpty
      private String oid;

     @NotEmpty
      private String name;

     @NotEmpty
      private String number;


     public void setWncUserId(Long wncUserId){
         this.wncUserId = wncUserId;
     }

     public Long getWncUserId(){
         return wncUserId;
     }

     public void setOid(String oid){
         this.oid = oid;
     }

     public String getOid(){
         return oid;
     }

     public void setName(String name){
         this.name = name;
     }

     public String getName(){
         return name;
     }

     public void setNumber(String number){
         this.number = number;
     }

     public String getNumber(){
         return number;
     }

     }
