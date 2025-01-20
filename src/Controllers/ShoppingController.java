package Controllers;

import Models.Shopping;
import Services.MaintainanceService;

import java.io.IOException;
import java.util.List;

public class ShoppingController {

    private MaintainanceService maintainanceService;

    public ShoppingController() {
        this.maintainanceService = new MaintainanceService();
    }

    public void addShopping(String name, int price) throws IOException {
        maintainanceService.addShopping(new Shopping(name, price));
    }

    public void removeShopping(Shopping shopping) throws IOException {
        maintainanceService.removeShopping(shopping);
    }

    public List<Shopping> getAllShopping() throws IOException {
        return maintainanceService.getShoppings();
    }
}


