package lk.ijse.pos.bo;

import lk.ijse.pos.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public String generateNewItemCode();
    public ArrayList<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException;
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    public boolean removeItem(String itemCode) throws SQLException, ClassNotFoundException;
}
