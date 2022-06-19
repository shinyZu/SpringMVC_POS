package lk.ijse.spring.service;

import lk.ijse.spring.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> getAllItems();

    ItemDTO searchItem(String itemCode);

    String getLastItemCode();

    String getItemCount();

    ItemDTO saveItem(ItemDTO dto);

    ItemDTO updateItem(ItemDTO dto);

    void deleteItem(String itemCode);
}
