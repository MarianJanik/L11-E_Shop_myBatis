package cz.marianjanik.ekurz;

import com.mysql.cj.Session;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static java.lang.System.lineSeparator;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        final String MYBATIS_CONFIG_FILE = "MyBatisConfig.xml";
        final String SEPARATOR = lineSeparator() + "---------------------";
        final int ELEMENT_ORDER_FOR_PRINTING = 3;
        final int NEW_PRICE_FOR_CHOOSE_ELEMENT = 2222;

        Reader reader = Resources.getResourceAsReader(MYBATIS_CONFIG_FILE);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        sqlSessionFactory.getConfiguration().addMapper(GoodsMethods.class);
        SqlSession session = sqlSessionFactory.openSession();
        GoodsMethods methods = session.getMapper(GoodsMethods.class);

        System.out.println(SEPARATOR + "Printing the " + ELEMENT_ORDER_FOR_PRINTING + "rd record:");
        Item item = methods.loadItemById(ELEMENT_ORDER_FOR_PRINTING);
        System.out.println(item.getAllInfo());

        System.out.println(SEPARATOR + "Printing all records:");
        printAllInfo(methods);

        System.out.println(SEPARATOR + "Insert new items: ");
        Item newElement = createNewItem();
        System.out.println(newElement.getAllInfo());
        methods.saveItem(newElement);
        methods.saveItem(newElement);
        methods.saveItem(newElement);
        System.out.println();
        printAllInfo(methods);

        System.out.println(SEPARATOR + "Update price in " + ELEMENT_ORDER_FOR_PRINTING + "rd record:");
        newElement.setPrice(BigDecimal.valueOf(NEW_PRICE_FOR_CHOOSE_ELEMENT));
        newElement.setId(ELEMENT_ORDER_FOR_PRINTING);
        methods.updatePrice(newElement);
        printAllInfo(methods);

        System.out.println(SEPARATOR + "Delete items which is not in stock: ");
        methods.deleteAllOutOfStockItems();
        printAllInfo(methods);
    }

    private static void printAllInfo(GoodsMethods methods) throws SQLException {
        List<Item> items = methods.loadAllAvailableItems();
        for (Item item:items) {
            System.out.println(item.getAllInfo());
        }
    }

    private static Item createNewItem() {
        Item newItem = new Item();
        newItem.setId(30);
        newItem.setPartNo("86");
        newItem.setSerialNo("dvd456");
        newItem.setName("DVD ROM");
        newItem.setDescription("Medium to software.");
        newItem.setNumberInStock(0);
        newItem.setPrice(BigDecimal.valueOf(36));
        return newItem;
    }
}
