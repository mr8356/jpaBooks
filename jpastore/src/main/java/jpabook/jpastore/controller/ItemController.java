package jpabook.jpastore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jpabook.jpastore.domain.item.Book;
import jpabook.jpastore.domain.item.Item;
import jpabook.jpastore.service.ItemService;
import jpabook.jpastore.web.ItemForm;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping(value="/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping(value="/items/new")
    public String create(ItemForm itemForm) {
        Book book = new Book();
        book.setName(itemForm.getName());
        book.setAuthor(itemForm.getAuthor());
        book.setIsbn(itemForm.getIsbn());
        book.setPrice(itemForm.getPrice());
        book.setStockQuantity(itemForm.getStockQuantity());
        itemService.saveItem(book);
        
        return "redirect:/";
    }

    @GetMapping(value="/items")
    public String list(Model model) {
        List<Item> itemList = itemService.findItems();
        model.addAttribute("items", itemList);
        return "items/itemList";
    }
    
}
