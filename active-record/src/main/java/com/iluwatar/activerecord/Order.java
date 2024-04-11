package com.iluwatar.activerecord;

import com.iluwatar.activerecord.base.RecordBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * An order domain model.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends RecordBase<Order> {

  private Long id;
  private String orderNumber;

  @Override
  protected String getTableName() {
    return "order";
  }

  @Override
  protected void setFieldsFromResultSet(ResultSet rs) throws SQLException {
    this.id = rs.getLong("id");
    this.orderNumber = rs.getString("order_number");
  }

}
