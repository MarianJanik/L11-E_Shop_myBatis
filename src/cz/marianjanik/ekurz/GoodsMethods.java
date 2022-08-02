package cz.marianjanik.ekurz;

import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface GoodsMethods {

    /**
     * This method loads an item with given id
     * @param id - id of the item which we want to load
     * @return
     */
    @Select("SELECT * FROM `item` WHERE `id`=#{id};")
    Item loadItemById(Integer id) throws SQLException;

    /**
     * This method deletes all items that are not in stock
     */
    @Delete("DELETE FROM `item` WHERE (`numberInStock`=0);")
    void deleteAllOutOfStockItems() throws SQLException;

    /**
     * This method loads all items that are in stock
     * @return
     */
    @Select("SELECT * FROM `item`;")
    List<Item> loadAllAvailableItems() throws SQLException;

    /**
     * This method saves the given item
     * @param item - item to be saved
     */
    @Insert("INSERT INTO `item` (`partNo`,`serialNo`,`name`,`description`,`numberInStock`,`price`) " +
            "VALUES (#{partNo},#{serialNo},#{name},#{description},#{numberInStock},#{price})")
    void saveItem(Item item) throws SQLException;

    /**
     * This method updates a price of an item
     * @param id - id of an item which price is to be updated
     * @param newPrice - new price
     * @param item - item with id and new price
     */
    @Update("UPDATE item SET price = #{price} WHERE id = #{id};")
    void updatePrice(Item item) throws SQLException;
}
