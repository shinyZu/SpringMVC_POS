package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CustomerDTO;
import lk.ijse.spring.dto.ItemDTO;
import lk.ijse.spring.entity.Customer;
import lk.ijse.spring.entity.Item;
import lk.ijse.spring.repo.CustomerRepo;
import lk.ijse.spring.repo.ItemRepo;
import lk.ijse.spring.service.ItemService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ItemDTO> getAllItems() {
        return mapper.map(repo.findAll(), new TypeToken<List<ItemDTO>>() {
        }.getType());
    }

    @Override
    public ItemDTO searchItem(String itemCode) {
        if (repo.existsById(itemCode)) {
            return mapper.map(repo.findById(itemCode), ItemDTO.class);
        } else {
            throw new RuntimeException("No Item with Code " + itemCode);
        }
    }

    @Override
    public ItemDTO searchItemByDescription(String description) {
        return mapper.map(repo.getItemByDescription(description),ItemDTO.class);
    }

    @Override
    public String getLastItemCode() {
        long count = repo.count();
        System.out.println("count---------- "+count);
        if (count == 0) { // first item to be added
            return "I00-000";
        }
        List<Item> items = repo.findAll(Sort.by(Sort.Direction.DESC, "itemCode"));
        return items.get(0).getItemCode();
    }

    @Override
    public String getItemCount() {
        return String.valueOf(repo.count());
    }

    @Override
    public List<ItemDTO> getCodesAndDescriptions() {
        return mapper.map(repo.getItemCodeAndDescription(), new TypeToken<List<ItemDTO>>(){}.getType());
    }

    @Override
    public ItemDTO saveItem(ItemDTO dto) {
        if (!repo.existsById(dto.getItemCode())) {
            return mapper.map(repo.save(mapper.map(dto, Item.class)), ItemDTO.class);
        } else {
            throw new RuntimeException("Item Already Exists...");
        }
    }

    @Override
    public ItemDTO updateItem(ItemDTO dto) {
        if (repo.existsById(dto.getItemCode())) {
            return mapper.map(repo.save(mapper.map(dto, Item.class)), ItemDTO.class);
        } else {
            throw new RuntimeException("No Such Item..Please check the Item Code...");
        }
    }

    @Override
    public void deleteItem(String itemCode) {
        if (repo.existsById(itemCode)) {
            repo.deleteById(itemCode);
        } else {
            throw new RuntimeException("No Such Item..Please check the Item Code...");
        }
    }
}
