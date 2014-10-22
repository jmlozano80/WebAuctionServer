package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-04-23T04:28:14.631+0100")
@StaticMetamodel(BidEntityBean.class)
public class BidEntityBean_ {
	public static volatile SingularAttribute<BidEntityBean, Integer> bidId;
	public static volatile SingularAttribute<BidEntityBean, Integer> auctionId;
	public static volatile SingularAttribute<BidEntityBean, BigDecimal> bidPrice;
	public static volatile SingularAttribute<BidEntityBean, Timestamp> bidTime;
	public static volatile SingularAttribute<BidEntityBean, String> email;
}
