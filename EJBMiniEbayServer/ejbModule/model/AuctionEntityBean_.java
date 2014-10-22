package model;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-04-20T17:20:42.080+0100")
@StaticMetamodel(AuctionEntityBean.class)
public class AuctionEntityBean_ {
	public static volatile SingularAttribute<AuctionEntityBean, Integer> auctionId;
	public static volatile SingularAttribute<AuctionEntityBean, Short> auctionStatus;
	public static volatile SingularAttribute<AuctionEntityBean, Date> endDate;
	public static volatile SingularAttribute<AuctionEntityBean, Time> endTime;
	public static volatile SingularAttribute<AuctionEntityBean, Integer> itemId;
	public static volatile SingularAttribute<AuctionEntityBean, Date> startDate;
	public static volatile SingularAttribute<AuctionEntityBean, BigDecimal> startPrice;
	public static volatile SingularAttribute<AuctionEntityBean, Time> startTime;
}
