package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {
    
    @Mock
    VendorService vendorService;
    
    @InjectMocks
    VendorController vendorController;
    
    MockMvc mockMvc;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }
    
    @Test
    public void testListVendors() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(1l);
        vendor1.setName("Michale");
        vendor1.setVendorUrl(VendorController.BASE_URL + "/1");
        
        VendorDTO vendor2 = new VendorDTO();
        vendor2.setId(2l);
        vendor2.setName("Sam");
        vendor2.setVendorUrl(VendorController.BASE_URL + "/2");
    
        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendor1, vendor2));
    
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }
    
    @Test
    public void getVendorById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1l);
        vendorDTO.setName("Michale");
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");
    
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);
    
        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Michale")));
    }
    
    @Test
    public void createNewVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1l);
        vendorDTO.setName("Michale");
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");
    
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(vendorDTO);
    
        mockMvc.perform(post(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Michale")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }
    
    @Test
    public void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        verify(vendorService).deleteVendorById(anyLong());
    }
}