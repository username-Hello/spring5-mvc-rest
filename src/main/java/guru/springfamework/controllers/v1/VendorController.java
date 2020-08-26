package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    
    public static final String BASE_URL = "/api/v1/vendors";
    
    private final VendorService vendorService;
    
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    
    @GetMapping
    public VendorListDTO getListOfVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }
}
